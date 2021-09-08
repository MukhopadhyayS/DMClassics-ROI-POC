/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. All
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

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author sahuly
 * @date   Dec 5, 2007
 * @since  HECM 1.0
 *
 * This is a wrapper class, this class encapsulates
 * the parameters which are specific to getCreatedTasks.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "CreatedTasksCriteria", namespace = EIGConstants.TYPE_NS_V1)
public class CreatedTasksCriteria
extends BasicWorklistDO {

    /**
    * Serial Version ID for this Serializable.
    */
    private static final long serialVersionUID = -9150831066358111348L;

    /**
    * Holds the ID of the worklist.
    */
    private long _worklistID;

    /**
    * Holds the set of status ID available.
    */
    private String[] _statusIDs;

    /**
    * Holds the Actor who created the task.
    */
    private Actor _createdBy;

    /**
     * Instantiates the CreatedTasksCriteria.
     */
    public CreatedTasksCriteria() {
        super();
    }

    /**
     * Instantiates an CreatedTasksCriteria with the worklistID, set of status
     * the actor who created the task.
     */
    public CreatedTasksCriteria(long worklistID, String[] statusIDs, Actor actor) {

        this();
        setWorklistID(worklistID);
        setStatusIDs(statusIDs);
        setCreatedBy(actor);
    }

    /**
    * This method is used to get the worklist ID.
    * @return worklistID
    */
    public long getWorklistID() {
        return _worklistID;
    }

    /**
    * This method is used to set the worklist ID.
    * @param worklistID
    */
    @XmlElement(name = "worklistID")
    public void setWorklistID(long worklistID) {
        _worklistID = worklistID;
    }

    /**
    * This method is used to get the status IDs.
    * @return statusIDs
    */
    public String[] getStatusIDs() {
        return _statusIDs;
    }

    /**
    * This method is used to set the status IDs
    * @param statusIDs.
    */
    @XmlElement(name = "statusIDs", type = String.class)
    public void setStatusIDs(String[] statusIDs) {
        _statusIDs = statusIDs;
    }

    /**
    * This method is used to get the actor who created the task.
    * @return createdBy
    */
    public Actor getCreatedBy() {
        return _createdBy;
    }

    /**
    * This method is used to set the actor who created the task.
    * @param createdBy
    */
    @XmlElement(name = "createdBy", type = Actor.class)
    public void setCreatedBy(Actor createdBy) {
        _createdBy = createdBy;
    }
}
