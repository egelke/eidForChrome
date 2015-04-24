/*
 * eID for Chrole Project.
 * Copyright (C) 2015 e-Contract.be BVBA.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License version
 * 3.0 as published by the Free Software Foundation.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, see 
 * http://www.gnu.org/licenses/.
 */
package test.integ.eid.e_contract.eid;

import be.e_contract.eid.Main;
import be.e_contract.eid.protocol.AuthenticationRequestType;
import be.e_contract.eid.protocol.AuthenticationResponseType;
import be.e_contract.eid.protocol.ObjectFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainTest.class);

    @Test
    public void testStart() throws Exception {
        Main.main(null);
    }

    @Test
    public void testAuthentication() throws Exception {
        // setup
        JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        ObjectFactory objectFactory = new ObjectFactory();
        AuthenticationRequestType authenticationRequest = objectFactory.createAuthenticationRequestType();
        byte[] challenge = new byte[20];
        authenticationRequest.setChallenge(challenge);
        ByteArrayOutputStream request = new ByteArrayOutputStream();
        marshaller.marshal(objectFactory.createAuthenticationRequest(authenticationRequest), request);

        // prepare
        System.setIn(new ByteArrayInputStream(request.toByteArray()));
        ByteArrayOutputStream response = new ByteArrayOutputStream();
        System.setOut(new PrintStream(response));

        // operate
        Main.main(null);

        // verify
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<AuthenticationResponseType> authenticationResponseElement
                = (JAXBElement<AuthenticationResponseType>) unmarshaller.unmarshal(new ByteArrayInputStream(response.toByteArray()));
        // TODO
        int certCount = authenticationResponseElement.getValue().getCertificateChain().getCertificate().size();
        LOGGER.debug("number of authn certs: {}", certCount);
        assertEquals(3, certCount);
    }
}
