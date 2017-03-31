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

import java.io.IOException;

import javax.xml.namespace.QName;

import org.apache.axis.Constants;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.castor.AxisContentHandler;
import org.apache.axis.wsdl.fromJava.Types;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * construction of generic factories that introspect how determine how to
 * construct a serializer. The xmlType, javaType arguments are filled in with
 * the values known by the factory.
 */
public class CastorSerializer implements Serializer {
    
    /**
     * Creates an instance, which may be shared or independent, of the given
     * bean name.
     */
    private final CastorContext _context = (CastorContext) SpringUtilities
            .getInstance().getBeanFactory().getBean("castor_context");
    
    /**
     * Sole constructor.
     */
    public CastorSerializer() {
    }
    
    /**
     * Serialize an element named name, with the indicated attributes and value.
     * 
     * @param name
     *            is the element name
     * @param attributes
     *            are the attributes...serialize is free to add more.
     * @param value
     *            is the value
     * @param context
     *            is the SerializationContext
     * @throws IOException
     *             If an I/O error occurs.
     */
    public void serialize(QName name, Attributes attributes, Object value,
            SerializationContext context) throws IOException {
    	
        try {
        	
            AxisContentHandler handler = new AxisContentHandler(context);
            Marshaller m = new Marshaller(handler);
            m.setResolver(_context.getClassDescriptorResolver());
            m.setMarshalAsDocument(false);
            m.setSuppressXSIType(true);
            m.setMarshalExtendedType(false);
            m.marshal(value);
            
        } catch (MarshalException e) {
            throw new IOException(_context.report(e));
        } catch (ValidationException e) {
            throw new IOException(_context.report(e));
        }
    }
    
    /**
     * Returns the Mechanism Type.
     * 
     * @return type
     * 
     * @see javax.xml.rpc.encoding.Serializer#getMechanismType()
     */
    public String getMechanismType() {
        return Constants.AXIS_SAX;
    }
    
    /**
     * This has not been used in our service. Returns null.
     * 
     * @param javaType
     *            the Java Class writing out schema for
     * @param types
     *            the Java2WSDL Types object which holds the context for the
     *            WSDL being generated.
     * @return null
     * @throws Exception
     *             general Exception.
     */
    public Element writeSchema(Class javaType, Types types) throws Exception {
        return null;
    }
}
