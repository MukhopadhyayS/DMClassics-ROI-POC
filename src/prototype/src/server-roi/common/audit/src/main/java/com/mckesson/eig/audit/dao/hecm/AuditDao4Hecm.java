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

import org.springframework.beans.factory.BeanFactory;

import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.iws.orm.DaoRegistry;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * This is the persistence component that handles all persistence to the
 * database.
 * 
 */
public class AuditDao4Hecm {
    /**
     * This variable contains columnseq for objectId.
     */
    private static final long COLUMNSEQ_OBJECTID = 1;
    /**
     * This variable contains columnseq for revision1.
     */
    private static final long COLUMNSEQ_REVISION1 = 2;
    /**
     * This variable contains columnseq for revision2.
     */
    private static final long COLUMNSEQ_REVISION2 = 3;
    /**
     * This variable contains columnseq for revision3.
     */
    private static final long COLUMNSEQ_REVISION3 = 4;
    /**
     * This variable contains columnseq for workflowreason.
     */
    private static final long COLUMNSEQ_WORKFLOWREASON = 5;
    /**
     * Instance of BeanFactory for loading the Configuration file for Spring and
     * Hibernate.
     */
    private static final BeanFactory BEAN_FACTORY = 
        SpringUtilities.getInstance().getBeanFactory();
    /**
     * Instance of DaoRegistry used to obtain instance of Dao's.
     */
    private DaoRegistry _daoregistry;
    /**
     * AudiTrailDao - To handle persistence of AuditEvent.
     */
    private AuditTrailDao _auditTrailDao;
    /**
     * AudiTrailDetailDao - To handle persistence of AuditTrailDetail.
     */
    private AuditTrailDetailDao _auditTrailDetailDao;

    /**
     * Default constructor. Creates the instance of the daoRegistry and in-turn
     * the DAO's
     * 
     */
    public AuditDao4Hecm() {
        _daoregistry = (DaoRegistry) BEAN_FACTORY.getBean("daoRegistry");
        _auditTrailDao = (AuditTrailDao) _daoregistry
                .getDao(AuditTrailDao.class.getName());
        _auditTrailDetailDao = (AuditTrailDetailDao) _daoregistry
                .getDao(AuditTrailDetailDao.class.getName());
    }

    /**
     * Inserts an AuditEvent entry in AUDIT_TRAIL table.
     * 
     * @return The AuditTrail object that corresponds to the AuditEvent entry
     *         created.
     * @param auditEvent .
     */
    public boolean insertEntry(AuditEvent auditEvent) {
    	AuditTrail auditTrail = _auditTrailDao.save(auditEvent);
        insertAuditDetail(auditTrail, COLUMNSEQ_OBJECTID, auditEvent
                .getObjectId());
        insertAuditDetail(auditTrail, COLUMNSEQ_REVISION1, auditEvent
                .getRevision1());
        insertAuditDetail(auditTrail, COLUMNSEQ_REVISION2, auditEvent
                .getRevision2());
        insertAuditDetail(auditTrail, COLUMNSEQ_REVISION3, auditEvent
                .getRevision3());
        insertAuditDetail(auditTrail, COLUMNSEQ_WORKFLOWREASON, auditEvent
                .getWorkflowReason());
        return true;
    }

    /**
     * Deletes the AuditEvent from the AUDIT_TRAIL table.
     * 
     * @param auditTrail .
     */
    public void deleteEntries(AuditTrail auditTrail) {
        _auditTrailDao.delete(auditTrail);
    }

    /**
     * Inserts an AuditTrailDetail entry in AUDIT_TRAIL_DETAIL table.
     * 
     * @param auditTrailDetail .
     */

    public void insertDetailEntry(AuditTrailDetail auditTrailDetail) {
        _auditTrailDetailDao.save(auditTrailDetail);
    }

    /**
     * Deletes the AuditTrailDetail from the AUDIT_TRAIL_DETAIL table.
     * 
     * @param auditTrailDetail .
     */
    public void deleteDetailEntries(AuditTrailDetail auditTrailDetail) {
        _auditTrailDetailDao.delete(auditTrailDetail);
    }

    /**
     * Retrieves the AuditTrailDetail entry for the given auditTrailDetailSeq.
     * 
     * @param auditTrailDetailSeq .
     * @return AuditTrailDetail
     */
    public AuditTrailDetail retrieveDetailEntry(long auditTrailDetailSeq) {
        return _auditTrailDetailDao.get(auditTrailDetailSeq);
    }

    /**
     * Retrieves the AuditTrail entry for the given auditTrailSeq.
     * 
     * @param auditTrailSeq .
     * @return AuditTrail
     */
    public AuditTrail retrieveEntry(long auditTrailSeq) {
        return _auditTrailDao.get(auditTrailSeq);
    }

    /**
     * Create a detail record for a Long value. The row is only inserted if the
     * value is not null.
     * 
     * @param auditTrail -
     *            the parent record in AUDIT_TRAIL
     * @param columnSeq -
     *            column sequence constant to use for this detail.
     * @param value -
     *            the Long value
     */
    private void insertAuditDetail(AuditTrail auditTrail, long columnSeq,
            Long value) {
        if (value != null) {
            AuditTrailDetail auditTrailDetail = new AuditTrailDetail();
            auditTrailDetail.setAuditTrailSeq(auditTrail.getAuditTrailSeq());
            auditTrailDetail.setAuditColumnSeq(columnSeq);
            auditTrailDetail.setAuditIntVal(value.longValue());
            auditTrailDetail.setModifyDateTime(auditTrail.getModifyDateTime());
            insertDetailEntry(auditTrailDetail);
        }
    }

    /**
     * Create a detail record for a String value. The row is only inserted if
     * the value is not null.
     * 
     * @param auditTrail -
     *            the parent record in AUDIT_TRAIL
     * @param columnSeq -
     *            column sequence constant to use for this detail.
     * @param value -
     *            the string value
     */
    private void insertAuditDetail(AuditTrail auditTrail, long columnSeq,
            String value) {
        if (StringUtilities.hasContent(value)) {
            AuditTrailDetail auditTrailDetail = new AuditTrailDetail();
            auditTrailDetail.setAuditTrailSeq(auditTrail.getAuditTrailSeq());
            auditTrailDetail.setAuditColumnSeq(columnSeq);
            auditTrailDetail.setAuditNVarCharVal(value);
            auditTrailDetail.setModifyDateTime(auditTrail.getModifyDateTime());
            insertDetailEntry(auditTrailDetail);
        }
    }

    
}
