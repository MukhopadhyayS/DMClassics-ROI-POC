/*
 * Copyright 2007-2008 McKesson Corporation and/or one of its subsidiaries. All
 * Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of McKesson
 * Information Solutions and is protected under United States and international
 * copyright and other intellectual property laws. Use, disclosure,
 * reproduction, modification, distribution, or storage in a retrieval system in
 * any form or by any means is prohibited without the prior express written
 * permission of McKesson Information Solutions.
 */
package com.mckesson.eig.workflow.worklist.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author sahuly
 * @date   Dec 3, 2007
 * @since  HECM 1.0
 *
 * This class represents Priority of a task
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Priority", namespace = EIGConstants.TYPE_NS_V1)
public class Priority
extends BasicWorklistDO {

    /**
    * Serial Version ID for this Serializable.
    */
    private static final long serialVersionUID = 6117742361658486786L;

    /**
    * Holds the priority ID.
    */
    private long _priorityID;

    /**
    * Holds the Display Name.
    */
    private String _displayNameKey;

    /**
    * Holds the sorting order.
    */
    private int _sortOrder;

    /**
    * Instantiates an priority.
    */
    public Priority() {
        super();
    }

    /**
    * This method is used to get the priority ID.
    * @return priorityID
    */
    public long getPriorityID() {
        return _priorityID;
    }

    /**
    * This method is used to set the priorityID.
    * @param priorityID
    */
    @XmlElement(name = "priorityID")
    public void setPriorityID(long iD) {
        _priorityID = iD;
    }

    /**
    * This methos is used to get the sort order.
    * @return
    */
    public int getSortOrder() {
        return _sortOrder;
    }

    /**
    * This method is used to set the sort order.
    * @param sortOrder
    */
    @XmlElement(name = "sortOrder")
    public void setSortOrder(int sort) {
        this._sortOrder = sort;
    }

    /**
    * This method is used to get the Display Name.
    * @return displayNameKey
    */
    public String getDisplayNameKey() {
        return _displayNameKey;
    }

    /**
    * This method is used to set the Display Name.
    * @param nameKey
    */
    @XmlElement(name = "displayNameKey")
    public void setDisplayNameKey(String nameKey) {
        _displayNameKey = nameKey;
    }
}
