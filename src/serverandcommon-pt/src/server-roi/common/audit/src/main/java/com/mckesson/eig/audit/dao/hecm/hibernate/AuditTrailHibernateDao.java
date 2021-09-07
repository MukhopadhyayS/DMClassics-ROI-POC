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


import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.mckesson.eig.audit.dao.hecm.AuditTrail;
import com.mckesson.eig.audit.dao.hecm.AuditTrailDao;
import com.mckesson.eig.audit.model.AuditEvent;

/**
 * AuditTrailHibernateDao is a Hibernate implementation for AuditTrailDao
 * handling AuditEvent objects.
 *
 */
public class AuditTrailHibernateDao extends HibernateDaoSupport
        implements AuditTrailDao {

    /**
     * Creates an instance of AuditEvent.
     *
     * @return AuditEvent - returns the newly created AuditEvent object.
     */
    public AuditEvent create() {
        return new AuditEvent();
    }

    /**
     * Deletes the AuditEvent.
     *
     * @param auditTrail
     *            AuditEvent object to be deleted.
     */
    public void delete(AuditTrail auditTrail) {
    	getHibernateTemplate().delete(auditTrail);
        //deleteObject(auditTrail);
    }

    /**
     * Saves the AuditEvent.
     *
     * @param object -
     *            AuditEvent object to be saved.
     * @return AuditTrail object.
     *
     */
    public AuditTrail save(AuditEvent object) {
        AuditTrail auditTrail = new AuditTrail(object);
        getHibernateTemplate().save(object);
        //saveObject(auditTrail);
        return auditTrail;
    }

    /**
     * Gets the AuditEvent object with the specified auditTrailSeq value.
     *
     * @param auditTrailSeq
     *            passed as a Long type.
     *
     * @return AuditEvent - returns the AuditEvent object which corresponds to
     *         the given auditTrailSeq
     */
    public AuditTrail get(Long auditTrailSeq) {
    	return (AuditTrail) getHibernateTemplate().get(com.mckesson.eig.audit.dao.hecm.AuditTrail.class, auditTrailSeq);
        //return (AuditTrail) getObject(auditTrailSeq);
    }

    /**
     * Returns the object type of Class.
     *
     * @return Class type object.
     */
//    @Override
//	public Class<?> getObjectType() {
//        return DATA_CLASS;
//    }

}
