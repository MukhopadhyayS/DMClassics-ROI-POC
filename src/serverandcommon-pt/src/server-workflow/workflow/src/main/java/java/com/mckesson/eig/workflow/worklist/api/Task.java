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

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jbpm.taskmgmt.exe.PooledActor;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author sahuly
 * @date   Dec 3, 2007
 * @since  HECM 1.0
 *
 * This class represents the Task associated with the worklist.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Task", namespace = EIGConstants.TYPE_NS_V1)
public class Task
extends BasicWorklistDO {

    private static final long serialVersionUID = 5066369285302482609L;

    private static final int TASK_NAME_LENGTH = 80;
    private static final int TASK_DESC_LENGTH = 256;
    private static final int TASK_COMMENTS_LENGTH = 256;

    public static final String VK_CAN_START_EARLY = "canStartEarly";
    public static final String VK_COMMENTS = "comments";
    public static final String VK_COTENT_IDS = "contentIDs";
    public static final String VK_OWNED_BY = "ownedBy";
    public static final String VK_REASSIGN_REASON = "reassignReason";

    private boolean _canStartEarly;

    private long _taskID;

    private long _priorityID;

    private long _worklistID;

    private String _statusID;

    private String _taskName;

    private String _taskDescription;

    private String _comments;

    private String _reassignReason;

    private Date _startDate;

    private Date _endDate;

    private Actor _ownedBy;

    private Actor _taskCreator;

    /**
     * Instantiates an task
     */
    public Task() {
        super();
    }

    /**
     * Construct task from JBPM task instance
     */
    public Task(TaskInstance ti) {

        if (ti != null) {

            PooledActor pooledActor = (PooledActor) ti.getPooledActors().iterator().next();

            this.setCanStartEarly((Boolean) ti.getVariable(VK_CAN_START_EARLY));
            this.setEndDate(ti.getDueDate());
            this.setPriorityID(ti.getPriority());
            this.setReassignReason((String) ti.getVariable(VK_REASSIGN_REASON));
            this.setStartDate(ti.getStart());
            this.setStatusID(ti.getToken().getNode().getName());
            this.setTaskDescription(ti.getDescription());
            this.setTaskID(ti.getId());
            this.setTaskName(ti.getName());
            this.setOwnedBy(new Actor((String) ti.getVariable(VK_OWNED_BY)));
            this.setComments((String) ti.getVariable(VK_COMMENTS));
            this.setTaskCreator(new Actor(ti.getActorId()));
            this.setWorklistID(Long.parseLong(pooledActor.getActorId()));
        }
    }

    /**
     * Set the task values into the TaskInstance object
     *
     * @param taskInstance
     *        taskInstance holding the task related values.
     *
     * @return
     *      updated task values set into the taskInstance object.
     */
    public TaskInstance toTaskInstance(TaskInstance taskInstance) {


        taskInstance.setVariable(VK_CAN_START_EARLY, _canStartEarly);
        taskInstance.setVariable(VK_COMMENTS, _comments);
        taskInstance.setVariable(VK_REASSIGN_REASON, _reassignReason);
        taskInstance.setDueDate(_endDate);
        taskInstance.setPriority((int) _priorityID);
        taskInstance.setDescription(_taskDescription);
        taskInstance.setName(_taskName);
        ((PooledActor) taskInstance.getPooledActors().iterator().next())
                                   .setActorId(String.valueOf(_worklistID));
        return taskInstance;
    }

    /**
     * This method is used to get whether the can start early or not.
     *
     * @return canStartEarly
     */
    public boolean isCanStartEarly() {
        return _canStartEarly;
    }

    /**
     * This method is used to set whether the can start early or not.
     *
     * @param startEarly
     */
    @XmlElement(name = "canStartEarly")
    public void setCanStartEarly(boolean startEarly) {
        _canStartEarly = startEarly;
    }

    /**
     * This method is used to get the task ID.
     *
     * @return taskID
     */
    public long getTaskID() {
        return _taskID;
    }

    /**
     * This method is used to set the task ID.
     *
     * @param taskID
     */
    @XmlElement(name = "taskID")
    public void setTaskID(long taskID) {
        _taskID = taskID;
    }

    /**
     * This method is used to get the priorityID.
     *
     * @return priorityID
     */
    public long getPriorityID() {
        return _priorityID;
    }

    /**
     * This method is used to set the priorityID.
     *
     * @param priorityID
     */
    @XmlElement(name = "priorityID")
    public void setPriorityID(long priorityID) {
        _priorityID = priorityID;
    }

    /**
     * This method is used to get the worklistID.
     *
     * @return worklistID
     */
    public long getWorklistID() {
        return _worklistID;
    }

    /**
     * This method is used to set the worklistID.
     *
     * @param worklistID
     */
    @XmlElement(name = "worklistID")
    public void setWorklistID(long worklistID) {
        _worklistID = worklistID;
    }

    /**
     * This method is used to get the statusID.
     *
     * @return
     */
    public String getStatusID() {
        return _statusID;
    }

    /**
     * This method is used to set the statusID.
     *
     * @param statusID
     */
    @XmlElement(name = "statusID")
    public void setStatusID(String statusID) {
        _statusID = statusID;
    }

    /**
     * This method is used to get the task name.
     *
     * @return
     */
    public String getTaskName() {
        return _taskName;
    }

    /**
     * This method is used to set the task name.
     *
     * @param name
     */
    @XmlElement(name = "taskName")
    public void setTaskName(String name) {
        _taskName = name;
    }

    /**
     * This method is used to get the description of the task.
     *
     * @return taskDescription
     */
    public String getTaskDescription() {
        return _taskDescription;
    }

    /**
     * This method is used to set the task description.
     *
     * @param description
     */
    @XmlElement(name = "taskDescription")
    public void setTaskDescription(String description) {
        _taskDescription = description;
    }

    /**
     * This method is used to get the comments for the particular task.
     *
     * @return comments
     */
    public String getComments() {
        return _comments;
    }

    /**
     * This method is used to set the comments for the particular task.
     *
     * @param comments
     */
    @XmlElement(name = "comments")
    public void setComments(String comments) {
        this._comments = comments;
    }

    /**
     * This method is used to get the reason for the reassign the task.
     *
     * @return reassignReason
     */
    public String getReassignReason() {
        return _reassignReason;
    }

    /**
     * This method is used to set the reason for to reassign the task.
     *
     * @param reason
     */
    @XmlElement(name = "reassignReason")
    public void setReassignReason(String reason) {
        _reassignReason = reason;
    }

    /**
     * This method is used to get the starting date of a task.
     *
     * @return startDate
     */
    public Date getStartDate() {
        return _startDate;
    }

    /**
     * This method is used to set the starting date for a task.
     *
     * @param date
     */
    @XmlElement(name = "startDate")
    public void setStartDate(Date date) {
        _startDate = date;
    }

    /**
     * This method is used to get the end date for a particular task.
     *
     * @return endDate
     */
    public Date getEndDate() {
        return _endDate;
    }

    /**
     * This method is used to set the end date for a particular task.
     *
     * @param date
     */
    @XmlElement(name = "endDate")
    public void setEndDate(Date date) {
        _endDate = date;
    }

    /**
     * This method is used to get the actor who owned the task.
     *
     * @return ownedBy
     */
    public Actor getOwnedBy() {
        return _ownedBy;
    }

    /**
     * This method is used to set the actor who owned the task.
     *
     * @param by
     */
    @XmlElement(name = "taskOwner", type = Actor.class)
    public void setOwnedBy(Actor by) {
        _ownedBy = by;
    }

    /**
     * This method is used to get the actor who created the task.
     *
     * @return taskCreator
     */
    public Actor getTaskCreator() {
        return _taskCreator;
    }

    /**
     * This method is used to set the actor who create the task.
     *
     * @param creator
     */
    @XmlElement(name = "createBy", type = Actor.class)
    public void setTaskCreator(Actor creator) {
        _taskCreator = creator;
    }

    /**
     * This method is used to get the audit comment for the newly created task.
     * @return auditComment
     */
    public String toCreateTaskAuditComment() {

        return new StringBuffer("Task=")
               .append(_taskName).append(F_DELIM)
               .append(_worklistID).append(F_DELIM)
               .append(_priorityID).append(F_DELIM)
               .append(_statusID).append(F_DELIM)
               .append(_taskDescription).append(F_DELIM)
               .append(_startDate).append(F_DELIM)
               .append(_endDate).append(F_DELIM)
               .append(_canStartEarly)
               .toString();
    }
    
    /**
     * This method is used to get the audit comment for the update a task.
     * @return auditComment
     */
    public String toUpdateTaskAuditComment() {

        return new StringBuffer("Task=")
               .append(_taskName).append(F_DELIM)
               .append(_worklistID).append(F_DELIM)
               .append(_priorityID).append(F_DELIM)
               .append(_statusID).append(F_DELIM)
               .append(_reassignReason).append(F_DELIM)
               .append(_taskDescription).append(F_DELIM)
               .append(_startDate).append(F_DELIM)
               .append(_endDate).append(F_DELIM)
               .append(_canStartEarly)
               .toString();
    }

    /**
     * This method is used to get the audit process tasks comment for the worklist.
     * @return auditComment
     */
    public String toProcessAuditComment(String prevStatus) {

        return new StringBuffer("Task=")
               .append(_taskID).append(F_DELIM)
               .append(_taskName).append(F_DELIM)
               .append(_taskDescription).append(R_DELIM)
               .append("Status Before=")
               .append(prevStatus).append(F_DELIM)
               .append("Status After=")
               .append(_statusID)
               .toString();
    }

    /**
     * This method is used to get the audit comment for deleted task.
     * @return auditComment
     */
    public String toDeleteTaskAuditComment() {

        return new StringBuffer("Task=")
               .append(_taskName).append(F_DELIM)
               .append(_statusID)
               .toString();
    }
    
    /**
     * This method is used to get the audit comment for reassign task.
     * @return auditComment
     */
    public String toReassignTaskAuditComment(long toWorklist) {

        return new StringBuffer("Task=")
               .append(_worklistID).append(F_DELIM)
               .append(toWorklist)
               .toString();
    }
    
    

    /**
     * This method is used to validate the task is a valid one or not. If it
     * is not valid the corresponding error message is returned. An empty String
     * is returned if it is a valid task. The validity for tasks are
     * performed by checking the if the mandatory attributes such as name and
     * priority, start and end date are present and the length of the name
     * and description are valid.
     *
     * @return errorCode
     *          error code depending on the validation.
     */
    public String validate() {

        final int maxPriorityID = 3;

        if (StringUtilities.isEmpty(_taskName)) {
            return WorklistEC.EC_NO_TASK_NAME;
        }
        if (_priorityID <= 0 || _priorityID > maxPriorityID) {
            return WorklistEC.EC_NO_TASK_PRIORITY;
        }

        if (_taskName.trim().length() > TASK_NAME_LENGTH) {
            return WorklistEC.EC_INVALID_TASK_NAME_LEN;
        }
        if (StringUtilities.hasContent(_taskDescription) 
                    && _taskDescription.trim().length() > TASK_DESC_LENGTH) {
            return WorklistEC.EC_INVALID_TASK_DESC_LEN;
        }
        if (StringUtilities.hasContent(_comments) 
                && _comments.trim().length() > TASK_COMMENTS_LENGTH) {
            return WorklistEC.EC_INVALID_TASK_COMMENTS_LEN;
        }
        return validateStartAndEndDate();
    }
    
    /**
     * This method is used to validate the task start Date and end Date
     * 
     * @return errorCode
     *          error code depending on the validation.
     */
    private String validateStartAndEndDate() {
        
        if (_startDate == null) {
            return WorklistEC.EC_NO_TASK_START_DATE;
        }
        if (_endDate == null) {
            return WorklistEC.EC_NO_TASK_END_DATE;
        }
        return "";
    }
}
