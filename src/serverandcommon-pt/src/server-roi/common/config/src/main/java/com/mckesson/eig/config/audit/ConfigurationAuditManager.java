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

package com.mckesson.eig.config.audit;

import java.util.Date;

import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.dm.core.common.logging.OCLogger;

public class ConfigurationAuditManager {

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( ConfigurationAuditManager.class);

    /**
     * It creates and initializes the audit event object
     * @param domainId
     * @param componentId
     * @param userId
     * @param eventId
     * @param comment
     * @param successState
     * @return
     */
    public AuditEvent prepareApplicationSettingEvent(long domainId,
                                                     long componentId,
                                                     long userId,
                                                     long eventId,
                                                     String comment,
                                                     long successState) {

        final String logSourceMethod = "prepareApplicationSettingEvent"
            + "    (domainId, componentId, userId, eventId, comment, successState)";
        LOG.debug(logSourceMethod + ">>Start");

        AuditEvent auditEvent = new AuditEvent();
        auditEvent.setComponentId(componentId);
        auditEvent.setEventStart(new Date(System.currentTimeMillis()));
        auditEvent.setDomainId(domainId);
        auditEvent.setUserId(userId);
        auditEvent.setEventId(eventId);
        auditEvent.setComment(comment);
        auditEvent.setEventStatus(successState);
        LOG.debug(logSourceMethod + "<<End");
        return auditEvent;
    }
}
