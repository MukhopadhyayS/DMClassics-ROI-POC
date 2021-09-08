/*
 * Copyright 2007-2008 McKesson Corporation and/or one of its subsidiaries.
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
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author Pranav Amarasekaran
 * @date   Aug 30, 2007
 * @since  HECM 1.0
 *
 * Represents an attribute of any entity defined in Workflow.
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlType(name = "Attribute", namespace = EIGConstants.TYPE_NS_V1)
public class Attribute
extends BasicWorkflowDO {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = -266681816423628640L;

    /**
     * Holds the name of the stored attribute.
     */
    private String _name;

    /**
     * This constructor instantiates an Attribute.
     */
    public Attribute() {
        super();
    }

    /**
     * This constructor instantiates an Attribute with the specified name.
     */
    public Attribute(String name) {
        this();
        setName(name);
    }

    /**
     * This method is used to retrieve the name of the Attribute.
     * @return name
     */
    public String getName() {
        return _name;
    }

    /**
     * This method is used to set the name of the Attribute.
     * @param name
     */
    @XmlElement(name = "name")
    public void setName(String name) {
        _name = name;
    }
}
