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

package com.mckesson.eig.audit.dao.hecm;


/**
 * AuditTrailDetailDao is a Dao interface which will handle AuditTrailDetail.
 */
public interface AuditTrailDetailDao {
    /**
     * Constant declaration DATA_CLASS.
     */
    Class<?> DATA_CLASS = com.mckesson.eig.audit.dao.hecm.AuditTrailDetail.class;

    /**
     * Creates an instance of AuditTrailDetail.
     *
     * @return AuditTrailDetail - returns the newly created AuditTrailDetail
     *         object
     */
    AuditTrailDetail create();

    /**
     * Deletes the AuditTrailDetail.
     *
     * @param object -
     *            AuditTrailDetail object to be deleted
     */
    void delete(AuditTrailDetail object);

    /**
     * Saves the AuditTrailDetail.
     *
     * @param object -
     *            AuditTrailDetail object to be saved
     */
    void save(AuditTrailDetail object);

    /**
     * Gets the AuditTrailDetail object with the specified auditTrailDetailSeq
     * value.
     *
     * @param auditTrailDetailSeq .
     *
     * @return AuditTrailDetail - returns the AuditTrailDetail object which
     *         corresponds to the given auditTrailDetailSeq
     */
    AuditTrailDetail get(Long auditTrailDetailSeq);
}
