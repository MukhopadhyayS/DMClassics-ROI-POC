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

import java.util.Comparator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author Pranav Amarasekaran
 * @date   Aug 30, 2007
 * @since  HECM 1.0
 *
 * Represents the various privileges of Task management for each Worklist.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TaskACL", namespace = EIGConstants.TYPE_NS_V1)
public class TaskACL
extends BasicWorklistDO {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = 3303204855605398322L;

    public static final Comparator<TaskACL> COMPARATOR;

    static {
        COMPARATOR = new Comparator() {
            public int compare(Object o1, Object o2) {

                TaskACL acl1 = (TaskACL) o1;
                TaskACL acl2 = (TaskACL) o2;

                boolean result = true;
                result = (acl1._actor.getActorID() == acl2._actor.getActorID());
                result = result && (acl1._canComplete == acl2._canComplete);
                result = result && (acl1._canCreate   == acl2._canCreate);
                result = result && (acl1._canDelete   == acl2._canDelete);
                result = result && (acl1._canReassign == acl2._canReassign);
                result = result && (acl1._doEmailAlert == acl2._doEmailAlert);

                if (acl1._worklist != null) {
                    result = result && (acl1._worklist.getWorklistID()
                                        == acl2._worklist.getWorklistID());
                }

                return (result) ? 0 : -1;
            }
        };
    }

    /**
     * Holds the id of the ACL.
     */
    private long _aclID;

    /**
     * Holds the actor which has these privileges.
     */
    private Actor _actor;

    /**
     * Holds the worklist for which these privileges are assigned.
     */
    private Worklist _worklist;

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
     * Indicates if the actor having the acls are merged depend on the group privileges.
     */
    private boolean _hasMerged;

    /**
     * Indicates the actor needs email alert, if the task has been assigned to this worklist.
     */
    private boolean _doEmailAlert;

    /**
     * To hold the version attribute of the entity.
     */
    private int _version;

    /**
     * This constructor instantiates a taskACL.
     */
    public TaskACL() {
        super();
    }

    /**
     * This constructor instantiates a taskACL with the specified privileges.
     */
    public TaskACL(boolean canComplete,
                   boolean canCreate,
                   boolean canReassign,
                   boolean canDelete) {

        _canComplete = canComplete;
        _canCreate   = canCreate;
        _canReassign = canReassign;
        _canDelete   = canDelete;
    }

    /**
     * This constructor instantiates a taskACL with the specified privileges
     * and the associated actor.
     */
    public TaskACL(boolean canComplete,
                   boolean canCreate,
                   boolean canReassign,
                   boolean canDelete,
                   Actor ofActor) {

        this(canComplete, canCreate, canReassign, canDelete);
        _actor = ofActor;
    }

    /**
     * This method is used to retrieve the aclID.
     * @return taskACLID
     */
    public long getTaskACLID() {
        return _aclID;
    }

    /**
     * This method is used to set the aclID.
     * @param taskACLID
     */
    @XmlElement(name = "taskACLID")
    public void setTaskACLID(long taskACLID) {
        _aclID = taskACLID;
    }

    /**
     * This method is used to retrieve the associated Actor.
     * @return actor
     */
    public Actor getActor() {
        return _actor;
    }

    /**
     * This method is used to set the associated Actor.
     * @param actor
     */
    @XmlElement(name = "actor", type = Actor.class)
    public void setActor(Actor actor) {
        _actor = actor;
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
     * This method is used to retrieve the associated worklist.
     * @return
     */
    public Worklist getWorklist() {
        return _worklist;
    }

    /**
     * This method is used to set the associated worklist.
     * @param worklist
     */
    public void setWorklist(Worklist worklist) {
        _worklist = worklist;
    }

    /**
     * This method is used to retrieve the version.
     * @return
     */
    public int getVersion() {
        return _version;
    }
    
    /**
     * This method is used to retrieve has merged or not
     * @return
     */
    public boolean getHasMerged() {
        return _hasMerged;
    }
    
    /**
     * This method is used to set the merge acls are done or not
     * @param merged
     */
    @XmlElement(name = "hasMerged")
    public void setHasMerged(boolean merged) {
        _hasMerged = merged;
    }

    /**
     * This method is used to set the Version.
     * @param version
     */
    @XmlElement(name = "version")
    public void setVersion(int version) {
        _version = version;
    }

    @Override
    public String toString() {
        return _aclID +  "," + _actor.getActorID();
    }

    @Override
    public boolean equals(Object o) {

        try {

            return (_aclID > 0) && (_aclID == ((TaskACL) o)._aclID);
        } catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {

        if (_worklist == null || _actor == null) {
            return 0;
        }
        return _actor.hashCode() + _worklist.hashCode();
    }

    /**
     * This method is used to get the audit comment for a ACL.
     */
    public void appendAuditComment(StringBuffer sb) {

        sb.append(getActor()).append(F_DELIM)
          .append(getCanCreate()).append(F_DELIM)
          .append(getCanDelete()).append(F_DELIM)
          .append(getCanComplete()).append(F_DELIM)
          .append(getCanReassign());
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
}
