/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.workflow.worklist.api;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author sahuly
 * @Nov 21, 2008
 * @cxf.with.header Nov 21, 2008
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "StatusCountPair", namespace = EIGConstants.TYPE_NS_V1)
public class StatusCountPair {

    private String _key;

    private Long _value;

    public StatusCountPair() {
    }

    public StatusCountPair(String key, Long value) {
        this._key = key;
        this._value = value;
    }

    public String getKey() {
        return _key;
    }
    
    @XmlElement(name = "key")
    public void setKey(String key) {
        this._key = key;
    }

    public Long getValue() {
        return _value;
    }
    
    @XmlElement(name = "value")
    public void setValue(Long value) {
        this._value = value;
    }
}
