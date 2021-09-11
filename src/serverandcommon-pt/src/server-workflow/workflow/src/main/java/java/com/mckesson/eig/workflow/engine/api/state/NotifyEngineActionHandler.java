/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries. All
 * Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of McKesson
 * Information Solutions and is protected under United States and international
 * copyright and other intellectual property laws. Use, disclosure,
 * reproduction, modification, distribution, or storage in a retrieval system in
 * any form or by any means is prohibited without the prior express written
 * permission of McKesson Information Solutions.
 */

package com.mckesson.eig.workflow.engine.api.state;

import java.util.HashSet;
import java.util.Set;

import com.mckesson.dm.core.common.logging.OCLogger;
import org.jbpm.graph.exe.ExecutionContext;

import com.mckesson.eig.workflow.engine.BaseActionHandler;
import com.mckesson.eig.workflow.processinstance.api.ProcessInstanceHistory;
import com.mckesson.eig.workflow.util.JAXBUtil;
import com.mckesson.eig.workflow.util.ProcessInstanceUtil;
import com.mckesson.eig.workflow.util.QueueUtil;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.WorkflowEC;
import com.mckesson.eig.workflow.api.WorkflowEngineException;
import com.mckesson.eig.workflow.api.WorkflowException;


/**
 * The NotifyEngineActionHandler class implements notification event to parent
 * process instance.
 *
 * @author McKesson
 * @date March 4, 2009
 * @since HECM 2.0; March 4, 2009
 */
public class NotifyEngineActionHandler extends BaseActionHandler {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( NotifyEngineActionHandler.class);

    /**
     * @see org.jbpm.graph.def.ActionHandler
     * @param ExecutionContext
     *            will have execution context details
     *
     *            #execute(org.jbpm.graph.exe.ExecutionContext)
     *
     */
    public void executeAction(ExecutionContext context) {

        final String logSourceMethod =
            "NotifyEngineActionHandler:Execute ";
        LOG.debug(logSourceMethod + ">>Start");

        try {

            logProcessInstanceHistory(context);
            notifyProcessInstance(context.getProcessInstance().getId(), context.getToken().getId());

        } catch (WorkflowEngineException e) {
            LOG.error("NotifyEngineActionHandler: Execute failure. Exception was: "
                            + e.toString());
            throw e;
        }

        LOG.debug(logSourceMethod + "<<End");
    }

    /**
     * Nothing to validate for NotifyEngineActionHandler
     */
    public void validate() {

    }

    /**
     * Notify process instance that scheduled event has occurred on instance / token.
     *
     * @param instanceId
     * @param token
     */
    private void notifyProcessInstance(long instanceId, long token) {

        try {
            /*
             * Need to figure out actor
             */
            Actor userActor = new Actor();
            userActor.setActorID(1);
            userActor.setAppID(1);
            userActor.setEntityID(1);

            /*
             * Need to figure out aclActors
             */
            Set <Actor> actors = new HashSet <Actor>();
            actors.add(userActor);
            Actors aclActors = new Actors();
            aclActors.setActors(actors);
            aclActors.setSize(1);

            // write to JMS queue
            String soapMessage = new StringBuffer().append(
                            " <notifyProcessInstance>").append(
                            "<notificationType>EngineNotification</notificationType>").append(
                            userActor == null
                                    ? "<actor></actor>"
                                    : JAXBUtil.marshallObject(userActor)).append(
                            aclActors == null
                                    ? "<actors></actors>"
                                    : JAXBUtil.marshallObject(aclActors)).append(
                            "<sourceProcInstId>" + instanceId + "</sourceProcInstId>").append(
                            "<notifiedProcInstId>" + instanceId + "</notifiedProcInstId>").append(
                            "<token>" + token + "</token>").append(
                            " </notifyProcessInstance>").toString();

            LOG.debug("Notification message to processInstanceQueue= " + soapMessage);
            QueueUtil.writeMessageToQueue(soapMessage, "processInstanceProducer");
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("NotifyEngineActionHandler:notifyProcessInstance request processing"
                            + " exception: " + e.getMessage());
            WorkflowException we = new WorkflowException(
                    "NotifyEngineActionHandler:notifyProcessInstance request"
                    + " processing exception",
                    WorkflowEC.START_PROCESS_INSTANCE_ERROR);
            throw we;
        }
    }

    /**
     * Log process instance history when needed based on notifying event.
     *
     * @param context
     */
    private void logProcessInstanceHistory(ExecutionContext context) {

        final String logSourceMethod =
            "NotifyEngineActionHandler:logProcessInstanceHistory ";
        LOG.debug(logSourceMethod + ">>Start");

        ProcessInstanceHistory pih = new ProcessInstanceHistory();

        if ("TimedWaitActionHandler".equals(
                context.getContextInstance().getVariable("EngineIPC.Notifier"))) {

            LOG.debug("Recieve event from TimedWaitActionHandler");
            pih.setProcessId(Long.parseLong(context.getProcessDefinition().getName()));
            pih.setVersionId(Integer.parseInt((String)
                    context.getVariable(PROCESS_VERSION_VARIABLE)));
            pih.setProcessInstanceId(context.getProcessInstance().getId());
            pih.setEventLevel("Action");
            pih.setEventName("Wait Timer");
            pih.setEventDatetime(new java.util.Date());
            pih.setEventOriginator(context.getToken().getNode().getName());
            pih.setEventComments(context.getContextInstance().getVariable("EngineIPC.TimerNumber")
                                 + " "
                                 + context.getContextInstance().getVariable("EngineIPC.TimerUOM"));
            pih.setCreateDateTime(new java.util.Date());
            pih.setEventStatus("Wait Timer Completed");
            ProcessInstanceUtil.createProcessInstanceHistory(pih);
            LOG.debug("Done logging ProcessInstanceHistory");

        }
        LOG.debug(logSourceMethod + ">>End");
    }

 }
