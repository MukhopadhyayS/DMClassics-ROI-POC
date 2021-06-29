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
package com.mckesson.eig.audit.dao.hecm;

import java.util.Date;

import com.mckesson.eig.audit.model.AuditEvent;

/**
 * This is the audit trail persistence class. This is the class used within
 * Hibernate to map to the the audit trail table.
 * 
 */
public class AuditTrail {
    /**
     * Member variable.
     */
    private Long _auditTrailSeq;
    /**
     * Member variable.
     */
    private Date _modifyDateTime;

    /**
     * Member variable.
     */
    private Long _eventId;
    // ORG_ID of AUDIT_TRAIL
    /**
     * Member variable.
     */
    private Long _domainId;
    // COMPONENT_SEQ of AUDIT_TRAIL
    /**
     * Member variable.
     */
    private Long _componentId;
    /**
     * Member variable.
     */
    private Long _userId;
    /**
     * Member variable.
     */
    private Date _eventStart;
    /**
     * Member variable.
     */
    private Date _eventEnd;
    // AUDIT_DESC of AUDIT_TRAIL
    /**
     * Member variable.
     */
    private String _comment;
    /**
     * Member variable.
     */
    private Long _eventStatus;
    /**
     * Member variable.
     */
    private String _location;
    /**
     * Default Constructor.
     */
    public AuditTrail() {
    }

    /**
     * Constructor with single argument having <code>AuditEvent</code>
     * reference.
     * 
     * @param auditEvent
     *            Map with member variables of AuditTrail.
     */
    public AuditTrail(AuditEvent auditEvent) {
        _eventId = auditEvent.getEventId();
        _domainId = auditEvent.getDomainId();
        _componentId = auditEvent.getComponentId();
        _userId = auditEvent.getUserId();
        _comment = auditEvent.getComment();
        _eventStatus = auditEvent.getEventStatus();
        _location = auditEvent.getLocation();
        initializeDates(auditEvent);
    }

    /**
     * Initialize the date fields for the audit.  Modify datetime
     * is always set to the current timestamp.  If only one
     * of event start and stop is passed, then both will be set 
     * to the same value.  If neither is passed in, then both
     * are set to the current timestamp.
     * 
     * @param auditEvent
     *            Map with member variables of AuditTrail.
     */
    private void initializeDates(AuditEvent event) {
        Date now = new Date();
        _modifyDateTime = now;

        if ((event.getEventStart() == null) && (event.getEventEnd() == null)) {
            _eventStart = now;
            _eventEnd = now;
        } else if (event.getEventStart() == null) {
            _eventStart = event.getEventEnd();
            _eventEnd = event.getEventEnd();
        } else if (event.getEventEnd() == null) {
            _eventStart = event.getEventStart();
            _eventEnd = event.getEventStart();
        } else {
            _eventStart = event.getEventStart();
            _eventEnd = event.getEventEnd();
        }
    }

    /**
     * Getter(Accessor) for _auditTrailSeq.
     * 
     * @return _auditTrailSeq
     */
    public Long getAuditTrailSeq() {
        return _auditTrailSeq;
    }

    /**
     * Setter(Mutator) for _auditTrailSeq.
     * 
     * @param auditTrailSeq
     *            assign to member variable.
     */
    public void setAuditTrailSeq(Long auditTrailSeq) {
        _auditTrailSeq = auditTrailSeq;
    }

    /**
     * Getter(Accessor) for _modifyDateTime.
     * 
     * @return _modifyDateTime
     * 
     */
    public Date getModifyDateTime() {
        return _modifyDateTime;
    }

    /**
     * Setter(Mutator) for _modifyDateTime.
     * 
     * @param modifyDateTime
     *            assign to member variable.
     */
    public void setModifyDateTime(Date modifyDateTime) {
        _modifyDateTime = modifyDateTime;
    }

    /**
     * Getter(Accessor) for _eventId.
     * 
     * @return _eventId
     */
    public Long getEventId() {
        return _eventId;
    }

    /**
     * Setter(Mutator) for _eventId.
     * 
     * @param eventId
     *            assign to member variable.
     */
    public void setEventId(Long eventId) {
        _eventId = eventId;
    }

    /**
     * Getter(Accessor) for _domainId.
     * 
     * @return _domainId
     */
    public Long getDomainId() {
        return _domainId;
    }

    /**
     * Setter(Mutator) for _domainId.
     * 
     * @param domainId
     *            assign to member variable.
     */
    public void setDomainId(Long domainId) {
        _domainId = domainId;
    }

    /**
     * Getter(Accessor) for _componentId.
     * 
     * @return __componentId
     */
    public Long getComponentId() {
        return _componentId;
    }

    /**
     * Setter(Mutator) for _componentId.
     * 
     * @param componentId
     *            assign to member variable.
     */
    public void setComponentId(Long componentId) {
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
     *            assign to member variable.
     */
    public void setUserId(Long userId) {
        _userId = userId;
    }

    // Database generated

    /**
     * Setter(Mutator) for _eventStart.
     * 
     * @param eventStart
     *            assign to member variable.
     */
    public void setEventStart(Date eventStart) {
        _eventStart = eventStart;
    }

    // Database generated
    /**
     * Getter(Accessor) for _eventStart.
     * 
     * @return _eventStart assign to member variable.
     */
    public Date getEventStart() {
        return _eventStart;
    }

    /**
     * Setter(Mutator) for _eventEnd.
     * 
     * @param eventEnd
     *            assign to member variable.
     */
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
     * @return _eventStatus assign to member variable.
     */
    public Long getEventStatus() {
        return _eventStatus;
    }

    /**
     * Setter(Mutator) for _eventStatus.
     * 
     * @param eventStatus
     *            assign to member variable..
     */
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
     *            assign to member variable.
     */
    public void setLocation(String location) {
        _location = location;
    }

    /**
     * Getter(Accessor) for _comment.
     * 
     * @return _comment
     */
    public String getComment() {
        return _comment;
    }

    /**
     * Setter(Mutator) for _comment.
     * 
     * @param comment
     *            assign to member variable.
     */
    public void setComment(String comment) {
        _comment = comment;
    }

    /**
     * @param arg0
     *            type of <code>Object</code>.
     * @return <code>true</code> if match with current object, otherwise
     *         <code>false</code>.
     */
    public boolean equals(Object arg0) {
        if (arg0 == this) {
            return true;
        }
        
        if (!(arg0 instanceof AuditTrail)) {
            return false;
        }
        AuditTrail auditTrail = (AuditTrail) arg0;

        return (this._auditTrailSeq == auditTrail._auditTrailSeq
                && this._eventId == auditTrail._eventId
                && this._domainId == auditTrail._domainId
                && this._componentId == auditTrail._componentId
                && this._userId == auditTrail._userId
                && this._comment.equals(auditTrail._comment) && this._location
                .equals(auditTrail._location));
    }
    
    /**
     * Returns a hash code value for the object. This method is 
     * supported for the benefit of hashtables such as those provided by 
     * <code>java.util.Hashtable</code>. 
     *
     * @return  a hash code value for this object.
     * @see     java.lang.Object#equals(java.lang.Object)
     * @see     java.util.Hashtable
     */
    public int hashCode() {
        return super.hashCode();
    }
}
