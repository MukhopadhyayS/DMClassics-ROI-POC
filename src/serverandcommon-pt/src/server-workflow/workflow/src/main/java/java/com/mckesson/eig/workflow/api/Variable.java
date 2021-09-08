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

package com.mckesson.eig.workflow.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author sahuly
 * @date   Feb 10, 2009
 * @since  HECM 2.0; Feb 10, 2009
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Variable", namespace = EIGConstants.TYPE_NS_V1)
@XmlRootElement(name = "Variable")
public class Variable extends BasicWorkflowDO {

    private String _key;
    private String _value;

    public String getKey() {
        return _key;
    }

    @XmlElement(name = "key")
    public void setKey(String key) {
        this._key = key;
    }

    public String getValue() {
        return _value;
    }

    @XmlElement(name = "value")
    public void setValue(String value) {
        this._value = value;
    }
}
