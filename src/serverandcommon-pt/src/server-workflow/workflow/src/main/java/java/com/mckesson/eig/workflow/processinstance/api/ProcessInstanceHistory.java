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

package com.mckesson.eig.workflow.processinstance.api;

import java.io.Serializable;
import java.util.Date;

/**
 * This class models process instance history.
 *
 * @author McKesson
 * @date   March 4, 2009
 * @since  HECM 2.0; March 4, 2009
 */
public class ProcessInstanceHistory implements Serializable {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = 1L;

    /**
     * id of process in WF_PORCESS table; name of the process in JBPM
     */
    private long _processId;

    /**
     * version of process
     */
    private int _versionId;

    /**
     * process instance id
     */
    private long _processInstanceId;

    /**
     * Either Process / Activity
     */
    private String _eventLevel;

    /**
     * name of the process or activity level event
     */
    private String _eventName;

    /**
     * Date/time event occurred
     */
    private Date _eventDatetime;

    /**
     * User name or System component that triggered the event
     */
    private String _eventOriginator;

    /**
     * event dependent
     */
    private String _eventStatus;

    /**
     * event dependent
     */
    private String _eventComments;

    /**
     * Last modified user id
     */
    private long _modifiedByUserId;

    /**
     * Created date
     */
    private Date _createDateTime;

    /**
     * Last modified date
     */
    private Date _modifyDateTime;

    /**
     * This method is used to get process instance Id.
     *
     * @return processInstanceId
     */
    public long getProcessInstanceId() {
        return _processInstanceId;
    }

    /**
     *  This method is used to set process instance Id
     *
     * @param instanceId
     */
    public void setProcessInstanceId(long instanceId) {
        _processInstanceId = instanceId;
    }

    /**
     * This method is used to get process Id
     *
     * @return processId
     */
    public long getProcessId() {
        return _processId;
    }

    /**
     * This method is used to set process Id
     *
     * @param processId
     */
    public void setProcessId(long processId) {
        _processId = processId;
    }

    /**
     * This method is used to get versionId
     *
     * @return versionId
     */
    public int getVersionId() {
        return _versionId;
    }

    /**
     * This method is used to set versionId
     *
     * @param versionId
     */
    public void setVersionId(int versionId) {
        _versionId = versionId;
    }

    /**
     * This method is used to get Event Level.
     *
     * @return eventLevel
     */
    public String getEventLevel() {
        return _eventLevel;
    }

    /**
     * This method is used to set Event Level
     *
     * @param eventLevel
     */
    public void setEventLevel(String level) {
        _eventLevel = level;
    }

    /**
     * This method is used to get Event Name.
     *
     * @return eventName
     */
    public String getEventName() {
        return _eventName;
    }

    /**
     * This method is used to set event name
     *
     * @param eventName
     */
    public void setEventName(String name) {
        _eventName = name;
    }

    /**
     * This method is used to get Event date/time
     *
     * @return eventDatetime
     */
    public Date getEventDatetime() {
        return _eventDatetime;
    }

    /**
     * This method is used to set Event date/time
     *
     * @param eventDatetime
     */
    public void setEventDatetime(Date eventDatetime) {
        _eventDatetime = eventDatetime;
    }

    /**
     * This method is used to get Event originator.
     *
     * @return
     */
    public String getEventOriginator() {
        return _eventOriginator;
    }

    /**
     * This method is used to set Event originator.
     *
     * @param originator
     */
    public void setEventOriginator(String originator) {
        _eventOriginator = originator;
    }

    /**
     * This method is used to get Event Status.
     *
     * @return eventStatus
     */
    public String getEventStatus() {
        return _eventStatus;
    }

    /**
     * This method is used to set Event status.
     *
     * @param eventStatus
     */
    public void setEventStatus(String status) {
        _eventStatus = status;
    }

    /**
     * This method is used to get eventComments.
     *
     * @return eventComments
     */
    public String getEventComments() {
        return _eventComments;
    }

    /**
     * This method is used to set eventComments.
     *
     * @param eventComments
     */
    public void setEventComments(String eventComments) {
        this._eventComments = eventComments;
    }

    /**
     * This method is used to get modifiedByUserId
     *
     * @return modifiedByUserId
     */
    public long getModifiedByUserId() {
        return _modifiedByUserId;
    }

    /**
     * This method is used to set modifiedByUserId
     *
     * @param modifiedByUserId
     */
    public void setModifiedByUserId(long modifiedByUserId) {
        _modifiedByUserId = modifiedByUserId;
    }

    /**
     * This method is used to get createDateTime
     *
     * @return createDateTime
     */
    public Date getCreateDateTime() {
        return _createDateTime;
    }

    /**
     * This method is used to set createDateTime
     *
     * @param createDateTime
     */
    public void setCreateDateTime(Date createDateTime) {
        _createDateTime = createDateTime;
    }

    /**
     * This method is used to get modifyDateTime
     *
     * @return modifyDateTime
     */
    public Date getModifyDateTime() {
        return _modifyDateTime;
    }

    /**
     * This method is used to set modifyDateTime
     *
     * @param modifyDateTime
     */
    public void setModifyDateTime(Date modifyDateTime) {
        _modifyDateTime = modifyDateTime;
    }
}
