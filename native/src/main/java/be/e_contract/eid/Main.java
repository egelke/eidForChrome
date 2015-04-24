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
package be.e_contract.eid;

import be.e_contract.eid.protocol.AuthenticationRequestType;
import be.e_contract.eid.protocol.AuthenticationResponseType;
import be.e_contract.eid.protocol.CertificateChainType;
import be.e_contract.eid.protocol.ObjectFactory;
import be.fedict.commons.eid.client.BeIDCard;
import be.fedict.commons.eid.client.BeIDCards;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Main {

    public static void main(String[] args) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement element = (JAXBElement) unmarshaller.unmarshal(System.in);

        Object requestObject = element.getValue();
        if (requestObject instanceof AuthenticationRequestType) {
            AuthenticationRequestType authenticationRequest = (AuthenticationRequestType) requestObject;

            BeIDCards beIDCards = new BeIDCards();
            BeIDCard beIDCard = beIDCards.getOneBeIDCard();
            byte[] authnSignature = beIDCard.signAuthn(authenticationRequest.getChallenge(), false);
            List<X509Certificate> authnCertificateChain = beIDCard.getAuthenticationCertificateChain();

            ObjectFactory objectFactory = new ObjectFactory();
            AuthenticationResponseType authenticationResponse = objectFactory.createAuthenticationResponseType();
            authenticationResponse.setChallengeResponse(authnSignature);
            CertificateChainType certificateChain = objectFactory.createCertificateChainType();
            authenticationResponse.setCertificateChain(certificateChain);
            for (X509Certificate certificate : authnCertificateChain) {
                certificateChain.getCertificate().add(certificate.getEncoded());
            }

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.marshal(objectFactory.createAuthenticationResponse(authenticationResponse), System.out);
        }
    }
}
