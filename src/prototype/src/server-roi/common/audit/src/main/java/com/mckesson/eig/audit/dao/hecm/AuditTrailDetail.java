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

/**
 * This is the audit trail detail persistence class. This is the class used
 * within Hibernate to map to the the AUDIT_TRAIL_DETAIL table.
 * 
 */
public class AuditTrailDetail {

    /**
     * Member variable.
     */
    private Long _auditTrailDetailSeq;
    
    /**
     * Member variable.
     */
    private Long _auditTrailSeq;   
    
    /**
     * Member variable.
     */
    private Long _auditColumnSeq;
    
    /**
     * Member Variable.
     */
    private Long _auditIntVal;
    
    /**
     * Member variable.
     */
    private Date _auditDateVal;
    /**
     * Member variable.
     */
    private String _auditCharVal;
    /**
     * Member variable.
     */
    private String _auditVarCharVal;
    /**
     * Member variable.
     */
    private String _auditNVarCharVal;
    /**
     * Member variable.
     */
    private Date _modifyDateTime;

    /**
     * Getter(Accessor) for _auditTrailDetailSeq.
     * 
     * @return _auditTrailDetailSeq
     */
    public Long getAuditTrailDetailSeq() {
        return _auditTrailDetailSeq;
    }

    /**
     * Setter(Mutator) for _auditTrailDetailSeq.
     * 
     * @param auditTrailDetailSeq -
     *            new value to be set
     */
    public void setAuditTrailDetailSeq(Long auditTrailDetailSeq) {
        _auditTrailDetailSeq = auditTrailDetailSeq;
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
     * @param auditTrailSeq -
     *            new value to be set
     */
    public void setAuditTrailSeq(Long auditTrailSeq) {
        _auditTrailSeq = auditTrailSeq;
    }

    /**
     * Getter(Accessor) for _auditColumnSeq.
     * 
     * @return _auditColumnSeq
     */
    public Long getAuditColumnSeq() {
        return _auditColumnSeq;
    }

    /**
     * Setter(Mutator) for _auditColumnSeq.
     * 
     * @param auditColumnSeq -
     *            new value to be set
     */
    public void setAuditColumnSeq(Long auditColumnSeq) {
        _auditColumnSeq = auditColumnSeq;
    }

    /**
     * Getter(Accessor) for _auditIntVal.
     * 
     * @return _auditIntVal
     */
    public Long getAuditIntVal() {
        return _auditIntVal;
    }

    /**
     * Setter(Mutator) for _auditIntVal.
     * 
     * @param auditIntVal -
     *            new value to be set
     */
    public void setAuditIntVal(Long auditIntVal) {
        _auditIntVal = auditIntVal;
    }

    /**
     * Getter(Accessor) for _auditDateVal.
     * 
     * @return _auditDateVal
     */
    public Date getAuditDateVal() {
        return _auditDateVal;
    }

    /**
     * Setter(Mutator) for _auditDateVal.
     * 
     * @param auditDateVal -
     *            new value to be set
     */
    public void setAuditDateVal(Date auditDateVal) {
        _auditDateVal = auditDateVal;
    }

    /**
     * Getter(Accessor) for _auditCharVal.
     * 
     * @return _auditCharVal
     */
    public String getAuditCharVal() {
        return _auditCharVal;
    }

    /**
     * Setter(Mutator) for _auditCharVal.
     * 
     * @param auditCharVal -
     *            new value to be set
     */
    public void setAuditCharVal(String auditCharVal) {
        _auditCharVal = auditCharVal;
    }

    /**
     * Getter(Accessor) for _auditVarCharVal.
     * 
     * @return _auditVarCharVal
     */
    public String getAuditVarCharVal() {
        return _auditVarCharVal;
    }

    /**
     * Setter(Mutator) for _auditVarCharVal.
     * 
     * @param auditVarCharVal -
     *            new value to be set
     */
    public void setAuditVarCharVal(String auditVarCharVal) {
        _auditVarCharVal = auditVarCharVal;
    }

    /**
     * Getter(Accessor) for _auditNVarCharVal.
     * 
     * @return _auditNVarCharVal
     */
    public String getAuditNVarCharVal() {
        return _auditNVarCharVal;
    }

    /**
     * Setter(Mutator) for _auditNVarCharVal.
     * 
     * @param auditNVarCharVal -
     *            new value to be set
     */
    public void setAuditNVarCharVal(String auditNVarCharVal) {
        _auditNVarCharVal = auditNVarCharVal;
    }

    /**
     * Getter(Accessor) for _modifyDateTime.
     * 
     * @return _modifyDateTime
     */
    public Date getModifyDateTime() {
        return _modifyDateTime;
    }

    /**
     * Setter(Mutator) for _modifyDateTime.
     * 
     * @param modifyDateTime -
     *            new value to be set
     */
    public void setModifyDateTime(Date modifyDateTime) {
        _modifyDateTime = modifyDateTime;
    }
}
