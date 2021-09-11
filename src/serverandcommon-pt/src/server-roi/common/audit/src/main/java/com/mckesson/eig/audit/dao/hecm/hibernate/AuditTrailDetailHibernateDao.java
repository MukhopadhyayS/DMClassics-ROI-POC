/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.audit.dao.hecm.hibernate;


import com.mckesson.eig.audit.dao.hecm.AuditTrailDetail;
import com.mckesson.eig.audit.dao.hecm.AuditTrailDetailDao;
import com.mckesson.eig.iws.orm.hibernate.HibernateDaoSupport;

/**
 * AuditTrailDetailHibernateDao is a Hibernate implementation for
 * AuditTrailDetailDao handling AuditTrailDetail objects.
 *
 */
public class AuditTrailDetailHibernateDao extends HibernateDaoSupport
        implements AuditTrailDetailDao {
    /**
     * Creates an instance of AuditTrailDetail.
     *
     * @return AuditTrailDetail - returns the newly created AuditTrailDetail
     *         object.
     */
    public AuditTrailDetail create() {
        return new AuditTrailDetail();
    }

    /**
     * Deletes the AuditTrailDetail.
     *
     * @param object -
     *            AuditTrailDetail object to be deleted.
     */
    public void delete(AuditTrailDetail object) {
        deleteObject(object);
    }

    /**
     * Saves the AuditTrailDetail.
     *
     * @param object -
     *            AuditTrailDetail object to be saved.
     */
    public void save(AuditTrailDetail object) {
        saveObject(object);
    }

    /**
     * Gets the AuditTrailDetail object with the specified auditTrailDetailSeq
     * value.
     *
     * @param auditTrailDetailSeq
     *            passed as a Long.
     *
     * @return AuditTrailDetail - returns the AuditTrailDetail object which
     *         corresponds to the given auditTrailDetailSeq.
     */
    public AuditTrailDetail get(Long auditTrailDetailSeq) {
        return (AuditTrailDetail) getObject(auditTrailDetailSeq);
    }

    /**
     * Returns the object type of Class.
     *
     * @return Class
     */
    @Override
	public Class<?> getObjectType() {
        return DATA_CLASS;
    }
}
