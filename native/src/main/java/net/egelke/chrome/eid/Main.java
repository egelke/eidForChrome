/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.egelke.chrome.eid;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bryan_000
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) throws Throwable {
        try {
            logger.debug("Start native eid process");
            
            ByteBuffer bb = ByteBuffer.allocate(4);
            bb.order(ByteOrder.nativeOrder());
            
            byte[] buffer = new byte[4];
            IOUtils.readFully(System.in, buffer);
            bb.put(buffer);
            bb.flip();

            buffer = new byte[bb.getInt()];
            IOUtils.readFully(System.in, buffer);
            
            JsonReader inputReader = Json.createReader(new ByteArrayInputStream(buffer));
            JsonObject input = inputReader.readObject();

            //do the business logic
            logger.info("Received action: " + input.getString("action"));
            
            JsonObjectBuilder outputBuilder = Json.createObjectBuilder();
            outputBuilder.add("type", "eid-rsp");
            outputBuilder.add("action", "hello");
            JsonObject output = outputBuilder.build();
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JsonWriter outputWriter = Json.createWriter(outputStream);
            outputWriter.write(output);
            outputWriter.close();
            buffer = outputStream.toByteArray();

            bb.rewind();
            bb.putInt(buffer.length);
            IOUtils.write(bb.array(), System.out);
            IOUtils.write(buffer, System.out);
            
            logger.debug("End native eid process");
        } catch (Throwable e) {
            logger.error("Fatal error", e);
            throw e;
        }
    }
}
