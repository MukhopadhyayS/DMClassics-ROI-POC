/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.wsfw.test;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.meterware.httpunit.WebResponse;
import com.sun.xml.messaging.saaj.soap.ver1_1.Fault1_1Impl;

/**
 * @author sahuly
 * @date   Jan 21, 2009
 * @since  HECM 1.0; Jan 21, 2009
 */
public final class ResponseProcessor {

    private ResponseProcessor() {
    }

    public static JAXBContext getJAXBContext(Class< ? > clazz)
    throws JAXBException {
        return JAXBContext.newInstance(clazz);
    }

    public static String prepareResponse(WebResponse webResponse)
    throws Exception {

        MessageFactory mf = MessageFactory.newInstance();
        SOAPMessage soapMsg = mf.createMessage(new MimeHeaders(), webResponse.getInputStream());
        Node nResponse = soapMsg.getSOAPBody().getChildNodes().item(0);

        if (nResponse instanceof Fault1_1Impl) { // SOAPException
            return nResponse.getTextContent();
        }
        NodeList nResult = nResponse.getChildNodes();
        if (nResult.getLength() == 0) { // for void return type
            return "";
        }

        return nResponse.getChildNodes().item(0).getTextContent();
    }

    public static Object unMarshallObject(String response, Class< ? > boundClass)
    throws JAXBException {

        Unmarshaller um = getJAXBContext(boundClass).createUnmarshaller();
        return um.unmarshal(new StringReader(response));
    }

    public static String assertError(String response, String expectedErrorCode)
    throws Exception {

        boolean result = (response.indexOf("ApplicationException") > 0)
                          && (response.indexOf(">" + expectedErrorCode + "<") > 0);

        return (result) ? null : "Should throw Exception with error code:" + expectedErrorCode;
    }

    public static String assertError(String response, ArrayList<String> expectedErrorCodes)
    throws Exception {

        ArrayList<String> notFound = new ArrayList<String>(expectedErrorCodes);

        for (String code : expectedErrorCodes) {
            if (response.indexOf(">" + code + "<") > 0) {
                notFound.remove(code);
            }
        }

        boolean result = (response.indexOf("ApplicationException") > 0) && notFound.isEmpty();

        return (result) ? null
                : "Should throw Exception with error codes:" + expectedErrorCodes
                   + ". Following error codes not found:" + notFound;
    }
}
