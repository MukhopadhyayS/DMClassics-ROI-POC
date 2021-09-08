/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.workflow.processinstance.service;

import javax.jws.WebService;

import org.jbpm.JbpmContext;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.VariableList;
import com.mckesson.eig.workflow.api.WorkflowEC;
import com.mckesson.eig.workflow.api.WorkflowException;
import com.mckesson.eig.workflow.process.api.ProcessInstance;
import com.mckesson.eig.workflow.processinstance.ProcessInstanceService;
import com.mckesson.eig.workflow.processinstance.api.ProcessInstanceHistory;
import com.mckesson.eig.workflow.processinstance.dao.ProcessInstanceDAO;
import com.mckesson.eig.workflow.service.AbstractWorkflowService;
import com.mckesson.eig.workflow.util.JAXBUtil;
import com.mckesson.eig.workflow.util.ProcessInstanceUtil;
import com.mckesson.eig.workflow.util.QueueUtil;

/**
 * @author McKesson
 * @date   Feb 13, 2009
 * @since  HECM 1.0; Feb 13, 2009
 */
@WebService(
name              = "ProcessInstancePortType_v1_0",
portName          = "http://eig.mckesson.com/wsdl/processinstance_v1_0",
serviceName       = "ProcessInstanceService_v1_0",
targetNamespace   = "http://eig.mckesson.com/wsdl/processinstance-v1",
endpointInterface = "com.mckesson.eig.workflow.processinstance.ProcessInstanceService")
public class ProcessInstanceServiceImpl extends AbstractWorkflowService
                implements ProcessInstanceService {

    /**
     * Object represents the Log4JWrapper object.
     */
    private static final Log LOG = LogFactory.getLogger(ProcessInstanceServiceImpl.class);

    //process states
    private static final String SUSPEND = "suspend";
    private static final String RESUME = "resume";
    private static final String TERMINATE = "terminate";


    public ProcessInstanceServiceImpl() {
    }

    /**
     * @see com.mckesson.eig.workflow.processinstance.service.ProcessInstanceService
     *      #startProcessInstance(Actor actor,
     *      					  com.mckesson.eig.workflow.api.Actors,
     *                            java.lang.String,
     *                            com.mckesson.eig.workflow.api.VariableList)
     */
    public void startProcessInstance(Actor userActor,
                                     Actors aclActors,
									 long processId,
                                     VariableList variableList) {

        final String logSourceMethod =
                            "startProcessInstance(userActor, aclActors, processId, variableList)";
        LOG.debug(logSourceMethod + ">>Start");

        try {

            validateActor(userActor);
            //validateActors(aclActors);
            validateProcessId(processId);

            // write to JMS queue
            String soapMessage = new StringBuffer().append(
                            "<startProcessInstance>").append(
                            userActor == null
                                    ? "<actor></actor>"
                                    : JAXBUtil.marshallObject(userActor)).append(
                            aclActors == null
                                    ? "<actors></actors>"
                                    : JAXBUtil.marshallObject(aclActors)).append(
                            "<processId>" + processId + "</processId>").append(
                                    variableList == null
                                    ? "<variableList></variableList>"
                                    : JAXBUtil.marshallObject(variableList)).append(
                            "</startProcessInstance>").toString();

            LOG.debug("startProcessInstance message to processInstanceQueue= " + soapMessage);
            QueueUtil.writeMessageToQueue(soapMessage, "processInstanceProducer");
        } catch (Exception e) {
            LOG.error("ProcessInstanceServiceImpl:startProcessInstance request processing"
                    + " exception: " + e.getMessage(), e);
            WorkflowException we = new WorkflowException("startProcessInstance request"
                    + " processing exception", WorkflowEC.START_PROCESS_INSTANCE_ERROR);
            throw we;
        }

        LOG.debug(logSourceMethod + "<<End");
    }

    /**
     * This method is not supported for synchronous processing.
     * @see com.mckesson.eig.workflow.engine.service.ProcessInstanceJmsConsumer
     */
    public void notifyProcessInstance(String notificationType,
                                      Actor userActor,
                                      Actors aclActors,
                                      long sourceProcInstId,
                                      long notifiedProcInstId,
                                      long token) {
        final String logSourceMethod =
                "startProcessInstance(userActor, aclActors, processId, variableList)";
        LOG.debug(logSourceMethod + ">>Start");

        try {

            validateActor(userActor);
            validateActors(aclActors);
            validateProcessInstanceId(sourceProcInstId);
            validateProcessInstanceId(notifiedProcInstId);
            validateToken(token);

            /**
             * Asynchronous notification to process instance engine
             * for executing a token after a user task is finished.
             */
            String soapMessage = new StringBuffer().append(
                " <notifyProcessInstance>").append(
                "<notificationType>" + notificationType + "</notificationType>").append(
                userActor == null
                    ? "<actor></actor>"
                    : JAXBUtil.marshallObject(userActor)).append(
                aclActors == null
                    ? "<actors></actors>"
                    : JAXBUtil.marshallObject(aclActors)).append(
                "<sourceProcInstId>" + sourceProcInstId + "</sourceProcInstId>").append(
                "<notifiedProcInstId>" + notifiedProcInstId + "</notifiedProcInstId>").append(
                "<token>" + token + "</token>").append(
                "</notifyProcessInstance>").toString();

            LOG.debug("notifyProcessInstance message to processInstanceQueue= " + soapMessage);
            QueueUtil.writeMessageToQueue(soapMessage, "processInstanceProducer");
        } catch (Exception e) {
            LOG.error("ProcessInstanceServiceImpl:notifyProcessInstance request processing"
                    + " exception: " + e.getMessage(), e);
            WorkflowException we = new WorkflowException("notifyProcessInstance request"
                    + " processing exception", WorkflowEC.NOTIFY_PROCESS_INSTANCE_ERROR);
            throw we;
        }

        LOG.debug(logSourceMethod + "<<End");
    }

    /**
     * @see com.mckesson.eig.workflow.processinstance.service.ProcessInstanceService
     *      #getProcessInstance(com.mckesson.eig.workflow.api.Actor,
     *                            com.mckesson.eig.workflow.api.Actors, long)
     */
	public ProcessInstance getProcessInstance(Actor userActor,
	                                          Actors aclActors,
	                                          long processInstanceId) {
	    final String logSourceMethod =
	        "getProcessInstance(userActor, aclActors, processInstanceId)";
	    LOG.debug(logSourceMethod + ">>Start");

        validateActor(userActor);
        validateActors(aclActors);
        validateProcessInstanceId(processInstanceId);

        ProcessInstance pi = null;
        JbpmContext context = getProcessInstanceDAO().createJbpmContext();

        org.jbpm.graph.exe.ProcessInstance processInstance =
                context.getProcessInstance(processInstanceId);


        //map to com.mckesson.eig.workflow.process.api.ProcessInstance

        LOG.debug(logSourceMethod + "<<End");
        return pi;
	}

	/**
     * @see com.mckesson.eig.workflow.processinstance.service.ProcessInstanceService
     *      #suspendProcessInstance(com.mckesson.eig.workflow.api.Actor,
     *                            com.mckesson.eig.workflow.api.Actors, long)
     */
    public void suspendProcessInstance(Actor userActor,
                                       Actors aclActors,
                                       long processInstanceId) {

        final String logSourceMethod =
            "suspendProcessInstance(userActor, aclActors, processInstanceId)";
        LOG.debug(logSourceMethod + ">>Start");

        try {
            validateActor(userActor);
            validateActors(aclActors);
            validateProcessId(processInstanceId);

            JbpmContext context = getProcessInstanceDAO().createJbpmContext();
            org.jbpm.graph.exe.ProcessInstance jBPMProcessInstance =
                        context.getProcessInstance(processInstanceId);

            if (jBPMProcessInstance.hasEnded()) {
                throw new WorkflowException("Process instance "
                        + processInstanceId + " has already ended",
                        WorkflowEC.SUSPEND_PROCESS_INSTANCE_ERROR);
            }

            //set engine IPC variable instance to suspend.
            jBPMProcessInstance.getContextInstance().setVariable("EngineIPC.execution", SUSPEND);

            /**
             * Since a process could be executing
             * Suspending a process instance is always asynchronous
             */
            String soapMessage = new StringBuffer().append(
                " <suspendProcessInstance>").append(
                userActor == null
                    ? "<actor></actor>"
                    : JAXBUtil.marshallObject(userActor)).append(
                aclActors == null
                    ? "<actors></actors>"
                    : JAXBUtil.marshallObject(aclActors)).append(
                "<processId>" + processInstanceId + "</processId>").append(
                "</suspendProcessInstance>").toString();

            LOG.debug("suspendProcessInstance message to processInstanceQueue= " + soapMessage);
            QueueUtil.writeMessageToQueue(soapMessage, "processInstanceProducer");

            /**
             * Create suspend process instance history record.
             */
            ProcessInstanceHistory pih = new ProcessInstanceHistory();
            pih.setProcessId(Long.parseLong(jBPMProcessInstance.getProcessDefinition().getName()));
            pih.setVersionId(Integer.parseInt((String)
                    jBPMProcessInstance.getContextInstance().getVariable("ProcessVersion")));
            pih.setProcessInstanceId(processInstanceId);
            pih.setEventLevel("Process");
            pih.setEventName("Instance Suspended");
            pih.setEventDatetime(new java.util.Date());
            pih.setEventOriginator("" + userActor.getActorID());
            pih.setEventStatus("Suspended");
            pih.setCreateDateTime(new java.util.Date());
            ProcessInstanceUtil.createProcessInstanceHistory(pih);
        } catch (Exception e) {
            LOG.error("ProcessInstanceServiceImpl:suspendProcessInstance request processing"
                    + " exception: " + e.getMessage(), e);
            WorkflowException we = new WorkflowException("suspendProcessInstance request"
                    + " processing exception", WorkflowEC.SUSPEND_PROCESS_INSTANCE_ERROR);
            throw we;
        }
        LOG.debug(logSourceMethod + "<<End");
	}

    /**
     * @see com.mckesson.eig.workflow.processinstance.service.ProcessInstanceService
     *      #resumeProcessInstance(com.mckesson.eig.workflow.api.Actor,
     *                            com.mckesson.eig.workflow.api.Actors, long)
     */
    public void resumeProcessInstance(Actor userActor,
                                      Actors aclActors,
                                      long processInstanceId) {

        final String logSourceMethod =
            "resumeProcessInstance(userActor, aclActors, processInstanceId)";
        LOG.debug(logSourceMethod + ">>Start");

        try {
            validateActor(userActor);
            validateActors(aclActors);
            validateProcessId(processInstanceId);

            JbpmContext context = getProcessInstanceDAO().createJbpmContext();
            org.jbpm.graph.exe.ProcessInstance jBPMProcessInstance =
                            context.getProcessInstance(processInstanceId);

            if (jBPMProcessInstance.hasEnded()) {
                throw new WorkflowException("Process instance "
                        + processInstanceId + " has already ended",
                        WorkflowEC.RESUME_PROCESS_INSTANCE_ERROR);
            }

            //set engine IPC variable instance to resume.
            jBPMProcessInstance.getContextInstance().setVariable("EngineIPC.execution", RESUME);

            /**
             * Since a process could be executing
             * Suspending a process instance is always asynchronous
             */
            String soapMessage = new StringBuffer().append(
                " <resumeProcessInstance>").append(
                userActor == null
                    ? "<actor></actor>"
                    : JAXBUtil.marshallObject(userActor)).append(
                aclActors == null
                    ? "<actors></actors>"
                    : JAXBUtil.marshallObject(aclActors)).append(
                "<processInstanceId>" + processInstanceId + "</processInstanceId>").append(
                "</resumeProcessInstance>").toString();

            LOG.debug("resumeProcessInstance message to processInstanceQueue= " + soapMessage);
            QueueUtil.writeMessageToQueue(soapMessage, "processInstanceProducer");

            /**
             * Create resume process instance history record.
             */
            ProcessInstanceHistory pih = new ProcessInstanceHistory();
            pih.setProcessId(Long.parseLong(jBPMProcessInstance.getProcessDefinition().getName()));
            pih.setVersionId(Integer.parseInt((String)
                    jBPMProcessInstance.getContextInstance().getVariable("ProcessVersion")));
            pih.setProcessInstanceId(processInstanceId);
            pih.setEventLevel("Process");
            pih.setEventName("Instance Resumed");
            pih.setEventDatetime(new java.util.Date());
            pih.setEventOriginator("" + userActor.getActorID());
            pih.setEventStatus("Resumed");
            pih.setCreateDateTime(new java.util.Date());
            ProcessInstanceUtil.createProcessInstanceHistory(pih);
        } catch (Exception e) {
            LOG.error("ProcessInstanceServiceImpl:resumeProcessInstance request processing"
                    + " exception: " + e.getMessage(), e);
            WorkflowException we = new WorkflowException("resumeProcessInstance request"
                    + " processing exception", WorkflowEC.RESUME_PROCESS_INSTANCE_ERROR);
            throw we;
        }
        LOG.debug(logSourceMethod + "<<End");
    }

	/**
     * @see com.mckesson.eig.workflow.processinstance.service.ProcessInstanceService
     *      #terminateProcessInstance(com.mckesson.eig.workflow.api.Actor,
     *                            com.mckesson.eig.workflow.api.Actors, long)
     */
    public void terminateProcessInstance(Actor userActor,
                                         Actors aclActors,
                                         long processInstanceId) {
        final String logSourceMethod =
            "terminateProcessInstance(userActor, aclActors, processInstanceId)";
        LOG.debug(logSourceMethod + ">>Start");

        try {
            validateActor(userActor);
            validateActors(aclActors);
            validateProcessId(processInstanceId);

            JbpmContext context = getProcessInstanceDAO().createJbpmContext();
            org.jbpm.graph.exe.ProcessInstance jBPMProcessInstance =
                        context.getProcessInstance(processInstanceId);

            if (jBPMProcessInstance.hasEnded()) {
                throw new WorkflowException("Process instance "
                        + processInstanceId + " has already ended",
                        WorkflowEC.TERMINATE_PROCESS_INSTANCE_ERROR);
            }

            //set engine IPC variable instance to terminate.
            jBPMProcessInstance.getContextInstance().setVariable("EngineIPC.execution", TERMINATE);

            /**
             * Since a process could be executing
             * Suspending a process instance is always asynchronous
             */
            String soapMessage = new StringBuffer().append(
                " <terminateProcessInstance>").append(
                userActor == null
                    ? "<actor></actor>"
                    : JAXBUtil.marshallObject(userActor)).append(
                aclActors == null
                    ? "<actors></actors>"
                    : JAXBUtil.marshallObject(aclActors)).append(
                "<processId>" + processInstanceId + "</processId>").append(
                "</terminateProcessInstance>").toString();

            LOG.debug("terminateProcessInstance message to processInstanceQueue= " + soapMessage);
            QueueUtil.writeMessageToQueue(soapMessage, "processInstanceProducer");

            /**
             * Create resume process instance history record.
             */
            ProcessInstanceHistory pih = new ProcessInstanceHistory();
            pih.setProcessId(Long.parseLong(jBPMProcessInstance.getProcessDefinition().getName()));
            pih.setVersionId(Integer.parseInt((String)
                    jBPMProcessInstance.getContextInstance().getVariable("ProcessVersion")));
            pih.setProcessInstanceId(processInstanceId);
            pih.setEventLevel("Process");
            pih.setEventName("Instance Terminated");
            pih.setEventDatetime(new java.util.Date());
            pih.setEventOriginator("" + userActor.getActorID());
            pih.setEventStatus("Terminated Before Completion");
            pih.setEventComments("The instance was terminated from the Workflow Monitor");
            pih.setCreateDateTime(new java.util.Date());
            ProcessInstanceUtil.createProcessInstanceHistory(pih);

        } catch (Exception e) {
            LOG.error("ProcessInstanceServiceImpl:terminateProcessInstance request processing"
                    + " exception: " + e.getMessage(), e);
            WorkflowException we = new WorkflowException("terminateProcessInstance request"
                    + " processing exception", WorkflowEC.TERMINATE_PROCESS_INSTANCE_ERROR);
            throw we;
        }
        LOG.debug(logSourceMethod + "<<End");
	}

	/**
     * @see com.mckesson.eig.workflow.processinstance.service.ProcessInstanceService
     *      #deleteProcessInstance(com.mckesson.eig.workflow.api.Actor,
     *                            com.mckesson.eig.workflow.api.Actors, long)
     */
    public void deleteProcessInstance(Actor userActor,
                                      Actors aclActors,
                                      long processInstanceId) {
		// TODO Auto-generated method stub

	}

    /**
     * Checks whether the process id passed is less than zero or not.
     * If less then zero, WorkflowException will be thrown.
     *
     * @param processId
     *        processId to be checked
     */
    private void validateProcessId(long processId) {

        if (processId < 0) {

            WorkflowException we = new WorkflowException("Invalid process id:" + processId,
                                                         WorkflowEC.INVALID_PROCESS);
            LOG.error(we);
            throw we;
        }
    }

    /**
     * Checks whether the process instance id passed is less than zero or not.
     * If less then zero, WorkflowException will be thrown.
     *
     * @param processId
     *        processId to be checked
     */
    private void validateProcessInstanceId(long id) {

        if (id < 0) {

            WorkflowException we = new WorkflowException("Invalid process instance id:" + id,
                                                         WorkflowEC.INVALID_PROCESS_INSTANCE);
            LOG.error(we);
            throw we;
        }
    }

    /**
     * Checks whether the token id passed is less than zero or not.
     * If less then zero, WorkflowException will be thrown.
     *
     * @param processId
     *        processId to be checked
     */
    private void validateToken(long token) {

        if (token < 0) {

            WorkflowException we = new WorkflowException("Invalid token:" + token,
                                                         WorkflowEC.INVALID_TOKEN);
            LOG.error(we);
            throw we;
        }
    }

    /**
     * This method verifies if a passed actor is null or not.
     * @param userActor
     */
    private void validateActor(Actor userActor) {

        if (userActor == null) {

            WorkflowException we = new WorkflowException(WorkflowEC.MSG_NULL_ACTOR,
                                                         WorkflowEC.EC_NULL_ACTOR);
            LOG.error(we);
            throw we;
        }
        userActor.validateActor();
    }

    /**
     * This method verifies if a passed actors is null or not.
     * @param aclActors
     */
    private void validateActors(Actors aclActors) {

        if (aclActors == null || aclActors.getActors() == null) {

            WorkflowException we = new WorkflowException(WorkflowEC.MSG_NULL_ACTORS,
                                                         WorkflowEC.EC_NULL_ACTORS);
            LOG.error(we);
            throw we;
        }
        aclActors.validateActors();
    }

    /**
     * This method returns the DAO implementation for this business service.
     * @return ProcessDAO
     */
    private ProcessInstanceDAO getProcessInstanceDAO() {
        return (ProcessInstanceDAO) getDAO(ProcessInstanceDAO.class.getName());
    }

}
