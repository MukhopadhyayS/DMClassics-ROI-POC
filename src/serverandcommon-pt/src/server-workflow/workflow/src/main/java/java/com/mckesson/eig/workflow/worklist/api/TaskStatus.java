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
 * This class represents Status of a task
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TaskStatus", namespace = EIGConstants.TYPE_NS_V1)
public class TaskStatus
extends BasicWorklistDO {

    /**
    * Serial Version ID for this Serializable.
    */
    private static final long serialVersionUID = 8122054703358648725L;

    /**
    * Holds the status of the ID.
    */
    private String _statusID;

    /**
    * Indicates whether the corresponding status is active or not.
    */
    private boolean _isActive;

    /**
    * Holds the display name of the status.
    */
    private String _displayNameKey;

    /**
    * Holds the sort order of the status.
    */
    private int _sortOrder;

    /**
    * Instantiates an task status.
    */
    public TaskStatus() {
        super();
    }

    /**
    * This method is used to get the statusID.
    * @return statusID
    */
    public String getStatusID() {
        return _statusID;
    }

    /**
    * This method is used to set the statusID.
    * @param statusID
    */
    @XmlElement(name = "statusID")
    public void setStatusID(String id) {
        _statusID = id;
    }

    /**
    * This method is used to get the particular status is active or not.
    * @return isActive
    */
    public boolean getIsActive() {
        return _isActive;
    }

    /**
    * This method is used to set the particular task is active or not.
    * @param isActive
    */
    @XmlElement(name = "isActive")
    public void setIsActive(boolean isActive) {
        _isActive = isActive;
    }

    /**
    * This method is used to get the display name of the status.
    * @return displayName
    */
    public String getDisplayNameKey() {
        return _displayNameKey;
    }

    /**
    * This method is used to set the display name of the status.
    * @param displayName
    */
    @XmlElement(name = "displayNameKey")
    public void setDisplayNameKey(String displayName) {
        _displayNameKey = displayName;
    }

    /**
    * This method is used to get the sort order for a particular status.
    * @return sortOrder
    */
    public int getSortOrder() {
        return _sortOrder;
    }

    /**
    * This method is used to set the sort order for a particular status.
    * @param sortOrder
    */
    @XmlElement(name = "sortOrder")
    public void setSortOrder(int sortOrder) {
        this._sortOrder = sortOrder;
    }
}
