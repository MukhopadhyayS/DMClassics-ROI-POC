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
package com.mckesson.eig.workflow.service;

import java.util.Date;

import org.springframework.beans.factory.BeanFactory;

import com.mckesson.eig.audit.local.AuditLocalService;
import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * @author Pranav Amarasekaran
 * @date   Sep 20, 2007
 * @since  HECM 1.0; Sep 20, 2007
 *
 * This class handles the creation of an workflow audit entry and persisting it
 * into the Database.
 */
public class WorkflowAuditManger {

    /**
     * Object represents the Log4JWrapper object.
     */
    private static final Log LOG = LogFactory.getLogger(WorkflowAuditManger.class);

    private static final boolean UNIT_TESTING = Boolean.getBoolean("unit.test");

    /**
     * This method creates and initialises the audit event object. It sets the
     * Component id to CONTENT_MANAGER and the event start date to current time.
     *
     * @return auditEvent
     *          Audit Event with common attributes like component id
     *          and start time.
     */
    private AuditEvent prepareinitialAuditEvent() {

        final String logSourceMethod = "prepareinitialAuditEvent()";
        LOG.debug(logSourceMethod + ">>Start");

        AuditEvent auditEvent = new AuditEvent();
        auditEvent.setComponentId(AuditEvent.WORKFLOW);
        auditEvent.setEventStart(new Date(System.currentTimeMillis()));

        LOG.debug(logSourceMethod + "<<End");
        return auditEvent;
    }

    /**
     * This method creates and initialises the audit event object.
     *
     * @param domainId
     *            Domain code for domain where event is performed.
     * @param userId
     *            Current UserId.
     * @param eventId
     *            Event code for an event.
     * @param comment
     *            Description about the event.
     *
     * @return auditEvent.
     */
    private AuditEvent prepareWorkflowEvent(long domainId,
                                            long userId,
                                            long eventId,
                                            String comment) {

        final String logSourceMethod = "prepareWorkflowEvent(domainId, userId, eventId, comment)";
        LOG.debug(logSourceMethod + ">>Start");

        AuditEvent auditEvent = prepareinitialAuditEvent();
        auditEvent.setDomainId(domainId);
        auditEvent.setUserId(userId);
        auditEvent.setEventId(eventId);
        auditEvent.setComment(comment);

        LOG.debug(logSourceMethod + "<<End");
        return auditEvent;
    }

    /**
     * It sets the status to success/failure and prepares an audit event.
     *
     * @param domainId
     *            Domain code for domain where event is performed.
     *
     * @param userId
     *            Current userId.
     * @param eventId
     *            Event code for an event.
     * @param comment
     *            Description about the event.
     *
     * @return auditEvent.
     */
    public AuditEvent prepareSucessFailAuditEvent(long domainId,
                                                  long userId,
                                                  long eventId,
                                                  String comment,
                                                  long successState) {

        final String logSourceMethod =
            "prepareSucessFailAuditEvent(domainId, userId, eventId, comment, successState)";
        LOG.debug(logSourceMethod + ">>Start");

        AuditEvent auditEvent = prepareWorkflowEvent(domainId, userId, eventId, comment);
        auditEvent.setEventStatus(successState);

        LOG.debug(logSourceMethod + "<<End");
        return auditEvent;
    }

    /**
     * This method creates the audit event entry by invoking the
     * AuditLocalService.
     *
     * @param auditEvent
     *            The audit event object that has to be persisted.
     *
     * @return status.
     *          The status of the audit entry is returned.
     */
    public boolean createAuditEntry(AuditEvent auditEvent) {

        final String logSourceMethod = "createAuditEntry(auditEvent)";
        LOG.debug(logSourceMethod + ">>Start");

        if (UNIT_TESTING) {
            // will be used to run the test cases
            return true;
        }

        BeanFactory beanFactory = SpringUtilities.getInstance().getBeanFactory();
        AuditLocalService service =
            (AuditLocalService) beanFactory.getBean(AuditLocalService.class.getName());

        boolean isSuccess = service.createAuditEntry(auditEvent);

        LOG.debug(logSourceMethod + "<<End");
        return isSuccess;
    }
}
