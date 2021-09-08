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

import com.mckesson.eig.wsfw.EIGConstants;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author 
 * @date   17 Nov 2010
 * @since  HECM 1.4
 *
 * Represents the various privileges of Task management for each Worklist.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TaskACLResolved", namespace = EIGConstants.TYPE_NS_V1)
public class TaskACLResolved
extends BasicWorklistDO {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = 3303204855605398322L;


    /**
     * Indicates if the actor can create the tasks in the associated worklist.
     */
    private String _sessionID;
    
    /**
     * Indicates if the actor can create the tasks in the associated worklist.
     */
    private long _worklistID;
    
    /**
     * Indicates if the actor can create the tasks in the associated worklist.
     */
    private boolean _canComplete;

    /**
     * Indicates if the actor can complete the tasks in the associated worklist.
     */
    private boolean _canCreate;

    /**
     * Indicates if the actor can reassign the tasks in the associated worklist.
     */
    private boolean _canReassign;

    /**
     * Indicates if the actor can delete the tasks in the associated worklist.
     */
    private boolean _canDelete;


    /**
     * Indicates the actor needs email alert, if the task has been assigned to this worklist.
     */
    private boolean _doEmailAlert;

    /**
     * This constructor instantiates a taskACL.
     */
    public TaskACLResolved() {
        super();
    }
    
    public TaskACLResolved(String sessionId, Object[] values) {

        _sessionID    = sessionId;

        _worklistID   = (Long) values[0];

        _canCreate    = (Boolean) values[1];
        _canComplete  = (Boolean) values[2];
        _canDelete    = (Boolean) values[3];
        _canReassign  = (Boolean) values[4];
        _doEmailAlert = (Boolean) values[5];
    }

    /**
     * This method is used to retrieve the complete privilege of the actor
     * on the worklist.
     * @return canComplete
     */
    public boolean getCanComplete() {
        return _canComplete;
    }

    /**
     * This method is used to set the complete privilege of the actor
     * on the worklist.
     * @param canComplete
     */
    @XmlElement(name = "canComplete")
    public void setCanComplete(boolean canComplete) {
        _canComplete = canComplete;
    }

    /**
     * This method is used to retrieve the create privilege of the actor
     * on the worklist.
     * @return canCreate
     */
    public boolean getCanCreate() {
        return _canCreate;
    }

    /**
     * This method is used to set the create privilege of the actor
     * on the worklist.
     * @param canCreate
     */
    @XmlElement(name = "canCreate")
    public void setCanCreate(boolean canCreate) {
        _canCreate = canCreate;
    }

    /**
     * This method is used to retrieve the delete privilege of the actor
     * on the worklist.
     * @return canDelete
     */
    public boolean getCanDelete() {
        return _canDelete;
    }

    /**
     * This method is used to set the delete privilege of the actor
     * on the worklist.
     * @param canDelete
     */
    @XmlElement(name = "canDelete")
    public void setCanDelete(boolean canDelete) {
        _canDelete = canDelete;
    }

    /**
     * This method is used to retrieve the reassign privilege of the actor
     * on the worklist.
     * @return canReassign
     */
    public boolean getCanReassign() {
        return _canReassign;
    }

    /**
     * This method is used to set the reassign privilege of the actor
     * on the worklist.
     * @param canReassign
     */
    @XmlElement(name = "canReassign")
    public void setCanReassign(boolean canReassign) {
        _canReassign = canReassign;
    }

	/**
	 * This method is used to retrieve the email alert.
	 * 
	 * @return doEmailAlert
	 */
	public boolean getDoEmailAlert() {
		return _doEmailAlert;
	}

	/**
	 * This method is used to set the email alert.
	 * 
	 * @param doEmailAlert
	 */
	@XmlElement(name = "doEmailAlert")
	public void setDoEmailAlert(boolean doEmailAlert) {
		this._doEmailAlert = doEmailAlert;
	}
	
	public String getSessionID() {
        return _sessionID;
    }



	@XmlElement(name = "sessionID")
    public void setSessionID(String sessionID) {
        _sessionID = sessionID;
    }




    public long getWorklistID() {
        return _worklistID;
    }



    @XmlElement(name = "worklistID")
    public void setWorklistID(long worklistID) {
        this._worklistID = worklistID;
    }

}
