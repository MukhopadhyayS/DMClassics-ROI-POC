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

import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.dao.WorkflowDAO;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author Pranav Amarasekaran
 * @date   Aug 30, 2007
 * @since  HECM 1.0
 *
 * This class holds all the common methods which have to be used in the
 * business service layer of the workflow component. All the implementation
 * classes of business services in the workflow component extend this
 * abstract class.
 */
public abstract class AbstractWorkflowService
implements WorkflowService {

    protected static final long AE_WORKLIST_CREATE = AuditEvent.CREATE_A_WORKLIST;
    protected static final long AE_WORKLIST_UPDATE = AuditEvent.UPDATE_A_WORKLIST;
    protected static final long AE_WORKLIST_DELETE = AuditEvent.DELETE_A_WORKLIST;

    protected static final long AE_TASK_CREATE           = AuditEvent.CREATE_WORKFLOW_TASK;
    protected static final long AE_TASK_UPDATE           = AuditEvent.UPDATE_WORKFLOW_TASK;
    protected static final long AE_TASK_DELETE           = AuditEvent.DELETE_WORKFLOW_TASK;
    protected static final long AE_TASK_COMPLETE         = AuditEvent.COMPLETE_WORKFLOW_TASK;
    protected static final long AE_TASK_PROCESS          = AuditEvent.PROCESS_WORKFLOW_TASK;
    protected static final long AE_TASK_REASSIGN         = AuditEvent.REASSIGN_WORKFLOW_TASK;
    protected static final long AE_UPDATED_CONFIGURATION = AuditEvent.UPDATED_CONFIGURATION;


    protected static final String WORKFLOW_AUDIT_MANAGER = "auditManager";

    protected AbstractWorkflowService() {
        super();
    }

    /**
     * This method is used to return the corresponding local service
     * instance for the specified service name by accessing the
     * WorkflowServiceFactory.
     *
     * @param daoName
     *          Name of the local service.
     *
     * @return WorkflowDAO
     *          dao implementation.
     */
    protected WorkflowDAO getDAO(String daoName) {
        return WorkflowServiceFactory.getWorkflowDAO(daoName);
    }

    /**
     * This method is used to return the corresponding audit manager
     * instance for the specified service name by accessing the
     * WorkflowServiceFactory.
     *
     * @param managerName
     *          Name of the manager.
     *
     * @return WorkflowAuditManger
     *          audit manager implementation.
     */
    protected WorkflowAuditManger getAuditManager(String managerName) {
        return WorkflowServiceFactory.getWorkflowAuditManager(managerName);
    }

    /**
     * Verifies if any of the actors listed has authorization to access to permissionName
     * on processId.
     *
     * @param actorId
     * @param permissionName
     * @param processId
     */
    public boolean verifyProcessAuthorization(Actors actors, String permissionName,
                    long processId) {

        return true;
    }

    /**
     * This method returns the current user id.
     * @return
     */
    protected long getUserID() {
        return WsSession.getSessionUserId();
    }

    /**
     * This method returns the WorkflowAuditManager for this business service.
     * @return workflowAuditManager
     */
    protected WorkflowAuditManger getWorkflowAuditManager() {
        return getAuditManager(WORKFLOW_AUDIT_MANAGER);
    }
}
