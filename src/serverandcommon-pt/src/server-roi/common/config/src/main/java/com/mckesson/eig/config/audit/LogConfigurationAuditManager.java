/* Copyright 2007 McKesson Corporation and/or one of its subsidiaries.
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

import java.util.Calendar;

import org.springframework.beans.factory.BeanFactory;

import com.mckesson.eig.audit.local.AuditLocalService;
import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * Auditing for <code>saveServerComponent</code> operation. This class handles
 * the creation of an audit entry and its persistence in to the Database.
 *
 */
public final class LogConfigurationAuditManager {

    /**
     * No argument constructor.
     */
    private LogConfigurationAuditManager() {
    }

    /**
     * Initialize the logger.
     */
    private static final Log LOG = LogFactory.getLogger(LogConfigurationAuditManager.class);

    /**
     * Creates and initialises the audit event object. It sets the Component id
     * to <code>SYSTEM_ADMINISTRATION</code> and the event start date to the
     * current time. It also sets the event status to success and the Event Id
     * to <code>UPDATED_CONFIGURATION</code>.
     *
     * @return The audit event object.
     */
    public static AuditEvent prepareinitialAuditEvent() {
        
        LOG.debug("Preparing initial audit events for LogConfiguration");
        
        AuditEvent auditEvent = new AuditEvent();
        
        auditEvent.setComponentId(AuditEvent.SYSTEM_ADMINISTRATION);
        auditEvent.setEventStart(Calendar.getInstance().getTime());
        auditEvent.setEventId(AuditEvent.UPDATED_CONFIGURATION);
        auditEvent.setEventStatus(AuditEvent.SUCCESS);
        auditEvent.setUserId(WsSession.getSessionUserId());
        
        return auditEvent;
    }

    /**
     * This method creates and initialises the audit event object. It sets the
     * input params to the audit event for persisting the audit specific data.
     * This method allows us to set the comment.
     *
     * TODO The workflow reason should be set in future.
     *
     * @param objectId Object id that needs to be persisted.
     *
     * @param comment
     *            Comment to be set to the audit event object.
     *
     * @return The audit event object.
     */
    public static AuditEvent prepareLogAuditEvent(long objectId, String comment) {
        
        LOG.debug("Preparing Audit Events For LogConfiguration");
        
        AuditEvent auditEvent = prepareinitialAuditEvent();
        auditEvent.setObjectId(objectId);
        auditEvent.setComment(comment);
        
        return auditEvent;
    }

    /**
     * This method creates the audit event entry by invoking the
     * AuditLocalService.
     *
     * @param auditEvent
     *            The audit event object that has to be persisted.
     *
     * @return <code>true</code> if created sucessfully <code>false</code>
     *         otherwise.
     */
    public static boolean createAuditEntry(AuditEvent auditEvent) {
        
        try {
            
            LOG.debug("Creating Audit Entries For LogConfiguration");
            
            BeanFactory beanFactory   = SpringUtilities.getInstance().getBeanFactory();
            AuditLocalService service = (AuditLocalService) 
                                        beanFactory.getBean(AuditLocalService.class.getName());
            return service.createAuditEntry(auditEvent);
        } catch (Exception exp) {
            
            LOG.error(exp);
            return false;
        }
    }
}
