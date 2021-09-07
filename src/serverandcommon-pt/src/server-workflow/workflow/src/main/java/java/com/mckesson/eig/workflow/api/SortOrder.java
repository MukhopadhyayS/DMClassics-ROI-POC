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
 * This will be used to specify the sort order for any entity. Each SortOrder
 * will have a respective reference to the attribute based on which the sorting
 * has to be done.
 */
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@XmlType(name = "SortOrder", namespace = EIGConstants.TYPE_NS_V1)
public class SortOrder
extends BasicWorkflowDO {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = -435519526130112201L;

    /**
     * Represents an attribute of any entity defined in Workflow.
     * 
     */
    private Attribute _attr;

    /**
     * Holds if the attribute has to sorted in descending order or not.
     */
    private boolean _isDesc;

    /**
     * Instantiates a Sort Order.
     */
    public SortOrder() {
        super();
    }

    /**
     * Instantiates a Sort Order with the specified attribute and
     * the ordering by default is considered as ascending.
     */
    public SortOrder(Attribute attr) {
        this(attr, false);
    }

    /**
     * Instantiates a Sort Order with the specified attribute and ordering.
     */
    public SortOrder(Attribute attribute, boolean isDesc) {

        this();
        setAttr(attribute);
        setIsDesc(isDesc);
    }

    /**
     * This method is used to retrieve the Attribute
     * @return attribute
     */
    public Attribute getAttr() {
        return _attr;
    }

    /**
     * This method is used to set the Attribute.
     * @param attr
     */
    @XmlElement(name = "attribute", type = Attribute.class)
    public void setAttr(Attribute attr) {
        _attr = attr;
    }

    /**
     * This method is used to retrieve the ordering.
     * @return isDescending
     */
    public boolean getIsDesc() {
        return _isDesc;
    }

    /**
     * This method is used to set the ordering.
     * @param isDesc
     */
    @XmlElement(name = "isDesc")
    public void setIsDesc(boolean isDesc) {
        _isDesc = isDesc;
    }
}
