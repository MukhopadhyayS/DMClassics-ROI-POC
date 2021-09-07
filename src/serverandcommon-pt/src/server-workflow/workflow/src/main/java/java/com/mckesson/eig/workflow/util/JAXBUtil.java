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

package com.mckesson.eig.workflow.util;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


public final class JAXBUtil {

    private JAXBUtil() {

    }
    /**
     * Utilizes JAXBContext for converting java objects to XML string.
     */
    public static String marshallObject(Object obj) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());

        StringWriter stringWriter = new StringWriter();
        Marshaller m = jaxbContext.createMarshaller();
        m.marshal(obj, stringWriter);
        String message = stringWriter.toString();

        //strip XML declaration
        int startIndex = message.indexOf("?>");
        if (startIndex != -1) {
            message = message.substring(startIndex + 2, message.length());
        }

        return message;
    }
}
