/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
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
package com.mckesson.eig.wsfw.axis;

import javax.xml.namespace.QName;

import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.DeserializerImpl;
import org.apache.axis.message.MessageElement;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.SAXException;

import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * construction of generic factories that introspect how determine how to
 * construct a Deserializer. The xmlType, javaType arguments are filled in with
 * the values known by the factory.
 */
public class CastorDeserializer extends DeserializerImpl
        implements
            Deserializer {

    /**
     * Creates an instance, which may be shared or independent, of the given
     * bean name.
     */
    private final CastorContext _context = (CastorContext) SpringUtilities
            .getInstance().getBeanFactory().getBean("castor_context");

    /**
     * Constructor which expects an Class and QName.
     * 
     * @param javaType
     *            Entity type.
     * @param xmlType
     *            XML representation.
     */
    public CastorDeserializer(Class javaType, QName xmlType) {
    }

    /**
     * onEndElement is called by endElement. It is not called if the element has
     * an href.
     * 
     * @param namespace
     *            is the namespace of the child element
     * @param localName
     *            is the local name of the child element
     * @param context
     *            is the deserialization context
     * @throws SAXException
     *             if an exception occurs during Unmarshal of an object.
     * @see org.apache.axis.encoding.Deserializer#onEndElement(java.lang.String,
     *      java.lang.String, org.apache.axis.encoding.DeserializationContext)
     */
    public void onEndElement(String namespace, String localName,
            DeserializationContext context) throws SAXException {
        try {
            MessageElement msgElem = context.getCurElement();
            if (msgElem != null) {
                Unmarshaller um = new Unmarshaller();
                um.setResolver(_context.getClassDescriptorResolver());
                um.setClassLoader(getClass().getClassLoader());
                um.setIgnoreExtraAttributes(true);
                um.setIgnoreExtraElements(true);
                super.value = um.unmarshal(msgElem.getAsDOM());
            }
        } catch (MarshalException e) {
            throw new SAXException(_context.report(e));
        } catch (ValidationException e) {
            throw new SAXException(_context.report(e));
        } catch (MappingException e) {
            throw new SAXException(_context.report(e));
        } catch (Exception e) {
            throw new SAXException(_context.report(e));
        }
    }
}
