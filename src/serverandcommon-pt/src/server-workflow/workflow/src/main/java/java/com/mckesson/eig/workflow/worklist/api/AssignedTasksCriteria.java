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
 * @date   Dec 5, 2007
 * @since  HECM 1.0
 *
 * This is a wraper class, this class encapsulates the parameters
 * which are specific to getAssignedWorklistTasks.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "AssignedTasksCriteria", namespace = EIGConstants.TYPE_NS_V1)
public class AssignedTasksCriteria
extends BasicWorklistDO {

    /**
    * Serial Version ID for this Serializable.
    */
    private static final long serialVersionUID = -4374604761850007746L;

    /**
    * Holds the ID of the worklist
    */
    private long _worklistID;

    /**
    * Holds the set of status ID available.
    */
    private String[] _statusIDs;


    /**
     * Instantiates an AssignedTasksCriteria.
     */
    public AssignedTasksCriteria() {
        super();
    }

    /**
     * Instantiates an AssignedTasksCriteria with the worklistID and
     * set of statusIDs
     */
    public AssignedTasksCriteria(long worklistID, String[] statusIDs) {

        this();
        setWorklistID(worklistID);
        setStatusIDs(statusIDs);
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
}
