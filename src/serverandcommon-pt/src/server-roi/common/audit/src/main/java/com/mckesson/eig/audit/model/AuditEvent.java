/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and/or one of its subsidiaries and is protected
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.audit.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

/**
 * Transport class that contains the web service call data.
 *
 */
@XmlRootElement(name = "auditEvent")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "AuditEventType")
public class AuditEvent {

    // Logger used to record messages.
    /**
     * Loading the Configuration file for Spring and Hibernate.
     */
    private static final Log LOG = LogFactory.getLogger(AuditEvent.class
            .getName());

    /**
     * @return _log return reference of <code>config</code> file.
     */
    protected static final Log getLogger() {
        return LOG;
    }
    // Valid event ids
    /**
     * Member Variable.
     */
    public static final long LOGON = 1;
    /**
     * Member Variable.
     */
    public static final long LOGOFF = 2;
    /**
     * Member Variable.
     */
    public static final long CHANGE_PASSWORD = 3;
    /**
     * Member Variable.
     */
    public static final long CONFIG_SIGNATURE_PASS_PHRASE = 4;
    /**
     * Member Variable.
     */
    public static final long SEARCH_CONTENT = 5;
    /**
     * Member Variable.
     */
    public static final long REVIEW_CONTENT = 6;
    /**
     * Member Variable.
     */
    public static final long SUBMIT_OUTPUT_REQUEST = 7;
    /**
     * Member Variable.
     */
    public static final long CANCEL_OUTPUT_REQUEST = 8;
    /**
     * Member Variable.
     */
    public static final long CHECK_OUT_CONTENT = 9;
    /**
     * Member Variable.
     */
    public static final long CHECK_IN_CONTENT = 10;
    /**
     * Member Variable.
     */
    public static final long UPDATED_CONFIGURATION = 11;
    /**
     * Member Variable.
     */
    public static final long CREATE_A_DOMAIN = 12;
    /**
     * Member Variable.
     */
    public static final long UPDATE_A_DOMAIN = 13;
    /**
     * Member Variable.
     */
    public static final long DELETE_A_DOMAIN = 14;
    /**
     * Member Variable.
     */
    public static final long CREATE_A_FOLDER = 15;
    /**
     * Member Variable.
     */
    public static final long UPDATE_A_FOLDER = 16;
    /**
     * Member Variable.
     */
    public static final long DELETE_A_FOLDER = 17;
    /**
     * Member Variable.
     */
    public static final long CREATE_A_METADATA_FIELD = 18;
    /**
     * Member Variable.
     */
    public static final long UPDATE_A_METADATA_FIELD = 19;
    /**
     * Member Variable.
     */
    public static final long DELETE_A_METADATA_FIELD = 20;
    /**
     * Member Variable.
     */
    public static final long CREATE_A_METADATA_TEMPLATE = 21;
    /**
     * Member Variable.
     */
    public static final long UPDATE_A_METADATA_TEMPLATE = 22;
    /**
     * Member Variable.
     */
    public static final long DELETE_A_METADATA_TEMPLATE = 23;
    /**
     * Member Variable.
     */
    public static final long SIGN_CONTENT = 24;
    /**
     * Member Variable.
     */
    public static final long MODIFY_CONTENT_METADATA = 25;
    /**
     * Member Variable.
     */
    public static final long CREATE_COMMENTS = 26;
    /**
     * Member Variable.
     */
    public static final long UPDATE_COMMENTS = 27;
    /**
     * Member Variable.
     */
    public static final long DELETE_COMMENTS = 28;
    /**
     * Member Variable.
     */
    public static final long CREATE_ANNOTATION = 29;
    /**
     * Member Variable.
     */
    public static final long UPDATE_ANNOTATION = 30;
    /**
     * Member Variable.
     */
    public static final long DELETE_ANNOTATION = 31;
    /**
     * Member Variable.
     */
    public static final long DELETE_CONTENT = 32;
    /**
     * Member Variable.
     */
    public static final long DELETE_CONTENT_VERSION = 33;
    /**
     * Member Variable.
     */
    public static final long SUBSCRIBE_TO_CONTENT_CHANGES_FOLDER = 34;
    /**
     * Member Variable.
     */
    public static final long SUBSCRIBE_TO_CONTENT_CHANGES_FILE = 35;
    /**
     * Member Variable.
     */
    public static final long SUBMIT_CONTENT = 36;
    /**
     * Member Variable.
     */
    public static final long ADD_CONTENT = 37;
    /**
     * Member Variable.
     */
    public static final long CREATE_A_WORKFLOW = 38;
    /**
     * Member Variable.
     */
    public static final long UPDATE_A_WORKFLOW = 39;
    /**
     * Member Variable.
     */
    public static final long DELETE_A_WORKFLOW = 40;
    /**
     * Member Variable.
     */
    public static final long CREATE_A_WORKLIST = 41;
    /**
     * Member Variable.
     */
    public static final long UPDATE_A_WORKLIST = 42;
    /**
     * Member Variable.
     */
    public static final long DELETE_A_WORKLIST = 43;
    /**
     * Member Variable.
     */
    public static final long CREATE_WORKFLOW_TASK = 44;
    /**
     * Member Variable.
     */
    public static final long UPDATE_WORKFLOW_TASK = 45;
    /**
     * Member Variable.
     */
    public static final long DELETE_WORKFLOW_TASK = 46;
    /**
     * Member Variable.
     */
    public static final long COMPLETE_WORKFLOW_TASK = 47;
    /**
     * Member Variable.
     */
    public static final long REASSIGN_WORKFLOW_TASK = 48;
    /**
     * Member Variable.
     */
    public static final long PEND_WORKFLOW_TASK = 49;
    /**
     * Member Variable.
     */
    public static final long ESCALATE_WORKFLOW_TASK = 50;
    /**
     * Member Variable.
     */
    public static final long MIGRATE_CONTENT = 51;
    /**
     * Member Variable.
     */
    public static final long ARCHIVE_CONTENT = 52;
    /**
     * Member Variable.
     */
    public static final long DESTROY_CONTENT = 53;
    /**
     * Member Variable.
     */
    public static final long COPY_CONTENT = 54;
    /**
     * Member Variable.
     */
    public static final long MOVE_CONTENT = 55;
    /**
     * Member Variable.
     */
    public static final long USER_ACCOUNT_LOCKED = 56;
    /**
     * Member Variable.
     */
    public static final long USER_ACCOUNT_UNLOCKED = 57;
    /**
     * Member Variable.
     */
    public static final long USER_PASSWORD_EXPIRED = 58;
    /**
     * Member Variable.
     */
    public static final long USER_PASSWORD_RESET = 59;
    /**
     * Member Variable.
     */
    public static final long RUN_USER_REPORT = 60;
    /**
     * Member Variable.
     */
    public static final long RUN_ADMIN_REPORT = 61;
    /**
     * Member Variable.
     */
    public static final long USER_PASSWORD_EXPIRING = 62;
    /**
     * Member Variable.
     */
    public static final long PROCESS_WORKFLOW_TASK = 63;
    /**
     * Member Variable.
     */
    public static final long RENAME_CONTENT = 64;

    /**
     * Member Variable.
     */
    public static final long FAIL = 0;

    /**
     * Member Variable.
     */
    public static final long SUCCESS = 1;

    // Valid COMPONENTS correspondiong to the COMPONENT_INFO table
    public static final long CONTENT_MANAGER = 1;
    public static final long SYSTEM_ADMINISTRATION = 2;
    public static final long REPORT_MANAGER = 3;
    public static final long CONTENT_CAPTURE = 4;
    public static final long CONTENT_LOADER = 5;
    public static final long WORKFLOW = 6;
    public static final long OUTPUT = 7;


    /**
     * Member Variable.
     */
    private long _eventId;

    /**
     * Member Variable.
     */
    private long _domainId;

    /**
     * Member variable.
     */
    private long _componentId;

    /**
     * Member Variable.
     */
    private Date _eventStart;

    /**
     * Member Variable.
     */
    private Date _eventEnd;

    /**
     * Member Variable.
     */
    private Long _objectId;

    /**
     * Member Variable.
     */
    private Long _revision1;

    /**
     * Member Variable.
     */
    private Long _revision2;

    /**
     * Member Variable.
     */
    private Long _revision3;

    /**
     * Member Variable.
     */
    private String _comment;

    /**
     * Member Variable.
     */
    private Long _eventStatus;

    /**
     * Member Variable.
     */
    private String _location;
    
    /**
     * Member Variable.
     */
    private Long _userId;
    /**
     * Member Variable.
     */
    private String _workflowReason;

    private String _actionCode;

    private String _mrn;

    private String _encounter;

    private String _facility;

    /**
     * Default constructor for web service.
     */
    public AuditEvent() {
    }

    /**
     * both arguments may be object, revisit once database complete. Assumption
     * that we want to validate against database with all require id values.
     *
     * @param eventId
     *            id of type <code>long</code>.
     * @param domainId
     *            id of type <code>long</code>.
     */
    public AuditEvent(long eventId, long domainId) {
        _eventId = eventId;
        _domainId = domainId;
    }

    /**
     * Getter(Accessor) for _eventId.
     *
     * @return _eventId
     */
    public long getEventId() {
        return _eventId;
    }

    /**
     * Setter(Mutator) for _eventId.
     *
     * @param eventId
     *            Assign to member variable.
     */
    @XmlElement(name = "eventId")
    public void setEventId(long eventId) {
        _eventId = eventId;
    }

    /**
     * Getter(Accessor) for _domainId..
     *
     * @return _domainId
     */
    public long getDomainId() {
        return _domainId;
    }

    /**
     * Setter(Mutator) for _domainId.
     *
     * @param domainId
     *            Assign to member variable.
     */
    @XmlElement(name = "domainId")
    public void setDomainId(long domainId) {
        _domainId = domainId;
    }

    /**
     * Getter(Accessor) for _componentId.
     *
     * @return __componentId
     */
    public long getComponentId() {
        return _componentId;
    }

    /**
     * Setter(Mutator) for _componentId.
     *
     * @param componentId
     *            assign to member variable.
     */
    @XmlElement(name = "componentId")
    public void setComponentId(long componentId) {
        _componentId = componentId;
    }

    /**
     * Getter(Accessor) for _userId.
     *
     * @return _userId
     */
    public Long getUserId() {
        return _userId;
    }

    /**
     * Setter(Mutator) for _userId.
     *
     * @param userId
     *            Assign to member variable.
     */
    @XmlElement(name = "userId")
    public void setUserId(Long userId) {
        _userId = userId;
    }

    // Database generated
    /**
     * Setter(Mutator) for _eventStart.
     *
     * @param eventStart
     *            Assign to member variable.
     */
    @XmlElement(name = "eventStart")
    public void setEventStart(Date eventStart) {
        _eventStart = eventStart;
    }

    // Database generated
    /**
     * Getter(Accessor) for _eventStart.
     *
     * @return _eventStart
     */
    public Date getEventStart() {
        return _eventStart;
    }

    /**
     * Setter(Mutator) for _eventEnd.
     *
     * @param eventEnd
     *            Assign to member variable.
     */
    @XmlElement(name = "eventEnd")
    public void setEventEnd(Date eventEnd) {
        _eventEnd = eventEnd;
    }

    /**
     * Getter(Accessor) for _eventEnd.
     *
     * @return _eventEnd
     */
    public Date getEventEnd() {
        return _eventEnd;
    }

    /**
     * Getter(Accessor) for _eventStatus.
     *
     * @return _eventStatus Assign to member variable.
     */
    public Long getEventStatus() {
        return _eventStatus;
    }

    /**
     * Setter(Mutator) for _eventStatus.
     *
     * @param eventStatus
     *            Assign to member variable.
     */
    @XmlElement(name = "eventStatus")
    public void setEventStatus(Long eventStatus) {
        _eventStatus = eventStatus;
    }

    /**
     * Getter(Accessor) for _location.
     *
     * @return _location
     */
    public String getLocation() {
        return _location;
    }

    /**
     * Setter(Mutator) for _location.
     *
     * @param location
     *            Assign to member variable.
     */
    @XmlElement(name = "location")
    public void setLocation(String location) {
        _location = location;
    }

    /**
     * Getter(Accessor) for _objectId.
     *
     * @return _objectId
     */
    public Long getObjectId() {
        return _objectId;
    }

    /**
     * Setter(Mutator) for _objectId.
     *
     * @param objectId
     *            Assign to member variable.
     */
    @XmlElement(name = "objectId")
    public void setObjectId(Long objectId) {
        _objectId = objectId;
    }

    /**
     * Getter(Accessor) for _revision1.
     *
     * @return _revision1
     */

    public Long getRevision1() {
        return _revision1;
    }

    /**
     * Setter(Mutator) for _revision1.
     *
     * @param revision1
     *            Assign to member variable.
     */
    @XmlElement(name = "revision1")
    public void setRevision1(Long revision1) {
        _revision1 = revision1;
    }

    /**
     * Getter(Accessor) for _revision2.
     *
     * @return _revision2
     */
    public Long getRevision2() {
        return _revision2;
    }

    /**
     *
     * _revision2.
     *
     * @param revision2
     *            Assign to member variable.
     */
    @XmlElement(name = "revision2")
    public void setRevision2(Long revision2) {
        _revision2 = revision2;
    }

    /**
     * @return _revision3
     */
    public Long getRevision3() {
        return _revision3;
    }

    /**
     * Setter(Mutator) for _revision3.
     *
     * @param revision3
     *            Assign to member variable.
     */
    @XmlElement(name = "revision3")
    public void setRevision3(Long revision3) {
        _revision3 = revision3;
    }

    /**
     * @return _comment
     */
    public String getComment() {
        return _comment;
    }

    /**
     * Setter(Mutator) for _comment.
     *
     * @param comment
     *            Assign to member variable.
     */
    @XmlElement(name = "comment")
    public void setComment(String comment) {
        _comment = comment;
    }

    /**
     * @return _workflowReason
     */
    public String getWorkflowReason() {
        return _workflowReason;
    }

    /**
     * Setter(Mutator) for _workflowReason.
     *
     * @param workflowReason
     *            Assign to member variable.
     */
    @XmlElement(name = "workflowReason")
    public void setWorkflowReason(String workflowReason) {
        _workflowReason = workflowReason;
    }

	public String getActionCode() {
		return _actionCode;
	}

	@XmlElement(name = "actionCode")
	public void setActionCode(String action) {
		_actionCode = action;
	}

	public String getEncounter() {
		return _encounter;
	}

	@XmlElement(name = "encounter")
	public void setEncounter(String encounter) {
		_encounter = encounter;
	}

	public String getFacility() {
		return _facility;
	}

	@XmlElement(name = "facility")
	public void setFacility(String facility) {
		_facility = facility;
	}

	public String getMrn() {
		return _mrn;
	}

	@XmlElement(name = "mrn")
	public void setMrn(String mrn) {
		_mrn = mrn;
	}
}
