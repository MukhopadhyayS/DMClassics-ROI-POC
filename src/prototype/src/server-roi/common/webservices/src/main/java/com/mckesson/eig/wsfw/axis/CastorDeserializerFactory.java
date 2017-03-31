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

import org.apache.axis.encoding.ser.BaseDeserializerFactory;

/**
 * Class for AxisDeserializationFactory(Deserialization) for code reuse.
 *
 */
public class CastorDeserializerFactory extends BaseDeserializerFactory {
    
    /**
     * Sets this JavaType and XMLType for Serialization.
     * 
     * @param theJavaType
     *            Class/Interface type.
     * @param theXMLType
     *            Qualfied Name as specified in the XML.
     */
    public CastorDeserializerFactory(Class theJavaType, QName theXMLType) {
        super(CastorDeserializer.class, theXMLType, theJavaType);
    }
}
