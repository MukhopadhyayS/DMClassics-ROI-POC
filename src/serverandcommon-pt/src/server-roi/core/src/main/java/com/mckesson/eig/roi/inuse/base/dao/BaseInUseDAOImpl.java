/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
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


package com.mckesson.eig.roi.inuse.base.dao;

import java.io.Serializable;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.mckesson.eig.roi.inuse.base.api.InUseClientErrorCodes;
import com.mckesson.eig.roi.inuse.base.api.InUseException;


/**
 * @author OFS
 * @date   Nov 05, 2008
 * @since  HPF 13.1 [InUse]; Feb 14, 2008
 */
public class BaseInUseDAOImpl
extends HibernateDaoSupport {

    public BaseInUseDAOImpl() {
        super();
    }
    /**
     * This method is used for storing individual objects and handling data
     * integrity violation exceptions.
     *
     * @param object Object to create in the database.
     * @return The primary key value of the object inserted.
     */
    public Serializable create(Object object) {

        try {
            return getHibernateTemplate().save(object);
        } catch (DataIntegrityViolationException e) {
            throw new InUseException(InUseClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                                     e.getMessage());
        }
    }

    /**
     * This method is used for updating the object passed.
     *
     * @param object -Object to be merged in the database.
     */
    public Object merge(Object object) {

        try {

            return getHibernateTemplate().merge(object);
        } catch (DataIntegrityViolationException e) {
            throw new InUseException(InUseClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                                     e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new InUseException(InUseClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        }
    }

    /**
     * This method is used for delete of individual objects.
     *
     * @param object - Object to delete in the database.
     */
    public void delete(Object object) {

        try {
            getHibernateTemplate().delete(object);
        } catch (DataIntegrityViolationException e) {
            throw new InUseException(InUseClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                                     e.getMessage());
        }
    }
}
