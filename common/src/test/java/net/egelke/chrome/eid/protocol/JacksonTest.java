/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.egelke.chrome.eid.protocol;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author bryan_000
 */
public class JacksonTest {
    
    ObjectMapper mapper;
    
    
    @Before
    public void init() {
        mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }
    
    @Test
    public void serializeStartMsg() throws Exception {
        Message<StartAction> msg = new Message<>();
        msg.setType(Message.Type.EID_INPUT);
        msg.setAction(new StartAction());
        msg.getAction().setLanuage("nl");
        
        String json = mapper.writeValueAsString(msg);

        assertEquals("{\"type\":\"EID_INPUT\",\"action\":{\"type\":\"START\",\"language\":\"nl\"}}", json);
    }
    
    @Test
    public void deserializeSignMsg() throws Exception {
        String json = "{"
                + "type: \"EID_INPUT\","
                + "action: {"
                + "type: \"SIGN\","
                + "key: \"AUTHENTICATION\","
                + "scheme: \"RAW\","
                + "value: \"c2lnbiBkYXRh\""
                + "}}";
        
        Message msg = mapper.readValue(json, Message.class);
        
        assertEquals(Message.Type.EID_INPUT, msg.getType());
        
        assertTrue(msg.getAction()instanceof SignAction);
        Message<SignAction> signMsg = (Message<SignAction>) msg;
        
        assertEquals(SignAction.Key.AUTHENTICATION, signMsg.getAction().getKey());
        assertEquals(SignAction.Scheme.RAW, signMsg.getAction().getScheme());
        assertNull(signMsg.getAction().getHashAlgorithm());
        assertArrayEquals("sign data".getBytes(), signMsg.getAction().getValue());
    }
    
    @Test
    public void serializeResponseMsg() throws Exception {
        Message<Response> msg = new Message<>();
        msg.setType(Message.Type.EID_OUTPUT);
        msg.setAction(new Response());
        msg.getAction().setData(new byte[2][]);
        msg.getAction().getData()[0] = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 9 };
        msg.getAction().getData()[1] = new byte[0];
        
        String json = mapper.writeValueAsString(msg);

        assertEquals("{\"type\":\"EID_OUTPUT\",\"action\":{\"type\":\"RESPONSE\",\"data\":[\"AAECAwQFBgcJ\",\"\"]}}", json);
    }
}
