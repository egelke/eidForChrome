/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.egelke.chrome.eid;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import net.egelke.chrome.eid.protocol.EndAction;
import net.egelke.chrome.eid.protocol.Message;
import net.egelke.chrome.eid.protocol.StartAction;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bryan_000
 */
public class Main {

    private final static Logger logger = LoggerFactory.getLogger(Main.class);
    
    private final static ObjectMapper mapper = new ObjectMapper();
    
    private final static ByteBuffer bb = ByteBuffer.allocate(4);
    
    public static void main(String[] args) throws Throwable {
        try {
            logger.debug("Start native eid process");
            bb.order(ByteOrder.nativeOrder());
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

            Message input;
            do {
                input = nextMessage();
                Message output = new Message();
                output.setType(Message.Type.EID_OUTPUT);
                
                //TODO:others
                if (input.getAction() instanceof StartAction) {
                    output.setAction(input.getAction());
                } else {
                    throw new UnsupportedOperationException();
                }
                
                respond(output);
            } while (!(input.getAction() instanceof EndAction));
            
            logger.debug("End native eid process");
        } catch (Throwable e) {
            logger.error("Fatal error", e);
            throw e;
        }
    }
    
    private static Message nextMessage() throws IOException {
        byte[] buffer = new byte[4];
        IOUtils.readFully(System.in, buffer);
        bb.rewind();
        bb.put(buffer);
        bb.flip();

        buffer = new byte[bb.getInt()];
        IOUtils.readFully(System.in, buffer);

        return mapper.readValue(buffer, Message.class);
    }
    
    private static void respond(Message msg) throws IOException {
        byte[] buffer = mapper.writeValueAsBytes(msg);
        
        bb.rewind();
        bb.putInt(buffer.length);
        IOUtils.write(bb.array(), System.out);
        IOUtils.write(buffer, System.out);
    }
}
