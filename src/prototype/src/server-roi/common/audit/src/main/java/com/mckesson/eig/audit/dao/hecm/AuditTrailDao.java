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

import com.mckesson.eig.audit.model.AuditEvent;

/**
 * AuditTrailDao is a Dao interface which will handle AuditEvent.
 *
 */
public interface AuditTrailDao {
    /**
     * Constant declaration DATA_CLASS.
     */
    Class<?> DATA_CLASS = com.mckesson.eig.audit.dao.hecm.AuditTrail.class;

    /**
     * Creates an instance of AuditEvent.
     *
     * @return AuditEvent - returns the newly created AuditEvent object.
     */
    AuditEvent create();

    /**
     * Deletes the AuditEvent.
     *
     * @param object -
     *            AuditEvent object to be deleted
     */
    void delete(AuditTrail object);

    /**
     * Saves the AuditEvent.
     *
     * @return AuditTrail
     *
     * @param object -
     *            AuditEvent object to be saved.
     */
    AuditTrail save(AuditEvent object);

    /**
     * Gets the AuditEvent object with the specified auditTrailSeq value.
     *
     * @param auditTrailSeq .
     *
     * @return AuditTrail - returns the AuditTrail object which corresponds to
     *         the given auditTrailSeq
     */
    AuditTrail get(Long auditTrailSeq);
}
