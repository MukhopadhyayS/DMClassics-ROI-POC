/*
 * Copyright 2009-2010 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.workflow.process.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;
import javax.xml.transform.TransformerException;

import com.mckesson.dm.core.common.logging.OCLogger;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.jpdl.JpdlException;
import org.springframework.beans.factory.BeanFactory;

import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.ObjectUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.workflow.Utilities.ServiceNames;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Domain;
import com.mckesson.eig.workflow.api.DomainList;
import com.mckesson.eig.workflow.api.WorkflowEC;
import com.mckesson.eig.workflow.api.WorkflowException;
import com.mckesson.eig.workflow.engine.service.ProcessEngineImpl;
import com.mckesson.eig.workflow.process.ProcessService;
import com.mckesson.eig.workflow.process.api.ActionHandler;
import com.mckesson.eig.workflow.process.api.ActionHandlerAttribute;
import com.mckesson.eig.workflow.process.api.ActionHandlerAttributeList;
import com.mckesson.eig.workflow.process.api.ActionHandlerList;
import com.mckesson.eig.workflow.process.api.Process;
import com.mckesson.eig.workflow.process.api.ProcessVersion;
import com.mckesson.eig.workflow.process.dao.ProcessDAO;

/**
 * @author McKesson Corporation
 * @version 1.0
 * @created 23-January-2009 9:51:50 AM
 */
@WebService(
        name = "ProcessPortType_v1_0",
        portName = "process_v1_0",
        serviceName = "ProcessService_v1_0",
        targetNamespace = "http://eig.mckesson.com/wsdl/process-v1",
        endpointInterface = "com.mckesson.eig.workflow.process.ProcessService")
public class ProcessServiceImpl extends BaseProcessServiceImpl
        implements
            ProcessService {
    private static final String ERROR_GET_PROCESS =
                        "Error Getting the process with ProcessId : ";
    private static final String PROCESS_DEFINITION_ERROR =
                                "Process definition could not be deployed";
    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( ProcessServiceImpl.class);

    public ProcessServiceImpl() {
        super();
    }
    /**
     * @see com.mckesson.eig.workflow.process.ProcessService #getDomains()
     */
    public DomainList getDomains() {
        if (LOG.isDebugEnabled()) {
            LOG.debug(">>>>>>>>>>>>>  IN WEBSERVICE getDomains()");
        }
        List<Domain> theList = new ArrayList<Domain>();
        Domain theDomain = new Domain();
        final long long77 = 77L;
        theDomain.setDomainId(long77);
        theDomain.setDomainName("Domain Name 77");
        theDomain.setDomainDescription("Domain Description 77");
        theList.add(theDomain);

        theDomain = new Domain();
        final long long88 = 88L;
        theDomain.setDomainId(long88);
        theDomain.setDomainName("Domain Name 88");
        theDomain.setDomainDescription("Domain Description 88");
        theList.add(theDomain);

        DomainList theDomainList = new DomainList();
        theDomainList.setDomainList(theList);
        theDomainList.setSize(theList.size());

        return theDomainList;
    }

    /**
     *
     * @param domainID
     */
    public ActionHandlerList getActionHandlers(long domainID) {
        ActionHandlerList theAHList = new ActionHandlerList();
        List<ActionHandler> aAHList = new ArrayList<ActionHandler>();

        // Atrributes
        ActionHandlerAttributeList theAHAList = new ActionHandlerAttributeList();
        List<ActionHandlerAttribute> aAHAList = new ArrayList<ActionHandlerAttribute>();
        ActionHandlerAttribute aAHA = new ActionHandlerAttribute();
        aAHA.setActionHandlerName("actionHandlerName");
        aAHA.setAttributeName("AttributeName1");
        aAHA.setAttributeDefaultValue("AttributeDefaultValue1");
        aAHA.setAttributeType("AttributeType1");
        aAHA.setCreatedTS(new java.util.Date());
        aAHA.setUpdatedTS(new java.util.Date());
        aAHAList.add(aAHA);
        aAHA = new ActionHandlerAttribute();
        aAHA.setActionHandlerName("actionHandlerName");
        aAHA.setAttributeName("AttributeName2");
        aAHA.setAttributeDefaultValue("AttributeDefaultValue2");
        aAHA.setAttributeType("AttributeType2");
        aAHA.setCreatedTS(new java.util.Date());
        aAHA.setUpdatedTS(new java.util.Date());
        aAHAList.add(aAHA);
        theAHAList.setSize(2);
        theAHAList.setActionHandlerAttributeList(aAHAList);

        // ActionHandlers
        ActionHandler aAH = new ActionHandler();
        aAH.setActionHandlerName("actionHandlerName");
        aAH.setImplementationClass("com.mckesson.implementationClass1");
        aAH.setCreatedTS(new java.util.Date());
        aAH.setUpdatedTS(new java.util.Date());
        aAH.setActionHandlerAttributeList(theAHAList);
        aAHList.add(aAH);

        aAH = new ActionHandler();
        aAH.setActionHandlerName("actionHandlerName");
        aAH.setImplementationClass("com.mckesson.implementationClass1");
        aAH.setCreatedTS(new java.util.Date());
        aAH.setUpdatedTS(new java.util.Date());
        aAH.setActionHandlerAttributeList(theAHAList);
        aAHList.add(aAH);

        theAHList.setActionHandlerList(aAHList);
        theAHList.setSize(2);

        return theAHList;
    }

    /**
     * Get the process for owner/domainId and processId
     *
     * @param domainId
     * @param processName
     */
    public Process getProcess(Actor owner, long processId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Enter getProcess()" + processId);
        }

        Process process = null;
        try {
            process = doGetProcess(owner, processId);
        } catch (Exception e) {
            LOG.error(ERROR_GET_PROCESS + processId);
            throw new WorkflowException(e);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Leave getProcess()");
        }
        return process;
    }
    /**
     * @see com.mckesson.eig.workflow.process.ProcessService
     *      #deployProcess(long, com.mckesson.eig.workflow.process.api.Process)
     */
    public void deployProcess(Actor assignedTo, Process processDetails) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Enter deployProcess()");
        }

        try {
            doDeployProcess(assignedTo, processDetails);
        } catch (Exception e) {
            LOG.error(PROCESS_DEFINITION_ERROR);
            throw new WorkflowException(e);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Leave deployProcess()");
        }
    }
    private void doDeployProcess(Actor assignedTo, Process processDetails) {
        this.validateActor(assignedTo);
        this.validateProcessObject(processDetails);
        this.validateProcessID(processDetails.getProcessId());
        /*
         * Retrieve the process from db - ProcessDao
         */
        Process processToDeploy = this.getProcessDAO().getProcess(
                assignedTo, processDetails.getProcessId());

        /*
         * Delegate to ProcessEngine
         */
        String processDefinitionXml = extractProcessDefinition(processToDeploy);
        /*
         * validateProcess() if deployable
         */
        ProcessDefinition processDefinition = isValidJbpmProcessDefinition(processDefinitionXml);
        /*
         * Deploy the process
         */
        if (processDefinition != null) {
            getProcessEngine().deployProcess(processDefinition);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.process.ProcessService
     *      #validateProcess(long,
     *      com.mckesson.eig.workflow.process.api.Process)
     */
    public void validateProcess(Actor owner, Process processToValidate) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Enter validateProcess()");
        }
        this.validateActor(owner);
        long processId = processToValidate.getProcessId();
        validateProcessID(processId);
        /* Dont get the process from DB from here - Designer may want
         * to validate the process they are designing
         * this.getProcessDAO().getProcess(actor, processId);
         *
         */
        String processDefinitionXml = this.extractProcessDefinition(processToValidate);
        isValidJbpmProcessDefinition(processDefinitionXml);

        /*
         * Perform any workflow rules that JBpm allows but our workflow restricts
         */

        if (LOG.isDebugEnabled()) {
            LOG.debug("Leave validateProcess()");
        }
    }

    /**
     * @see com.mckesson.eig.workflow.process.ProcessService #saveProcess(long,
     *      com.mckesson.eig.workflow.process.api.Process) SaveProcess is used
     *      to save a existing process if the process is not deployed to jBPM No
     *      new version will be created. each time the designer saves the
     *      process
     */
    public void saveProcess(Actor owner, Process processDetails) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("saveProcess()");
        }

        try {
            validateForSaveProcess(owner, processDetails);
            // validate if the process is NOT deployed
            if (isAlreadyDeployed(processDetails)) {
                // increment the version for deployed process & save
                createNewVersion(owner, processDetails);
            } else {
                updateExistingProcess(owner, processDetails);
            }
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            LOG.error(e);
            throw new WorkflowException(WorkflowEC.MSG_OTHER_SERVER_ERROR,
                    WorkflowEC.EC_OTHER_SERVER_ERROR);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Leave saveProcess()");
        }
    }

    private void validateForSaveProcess(Actor owner, Process processDetails) {
        validateProcessID(processDetails.getProcessId());
        doesProcessExist(processDetails.getProcessId());
        // Validate the creatorActor
        validateActor(owner);
    }

    /**
     * @see com.mckesson.eig.workflow.process.ProcessService
     *      #createProcess(long, com.mckesson.eig.workflow.process.api.Process)
     */
    public void createProcess(Actor owner, Process processDetails) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("createProcess()");
        }
        // Validate id the User has rights to create a process
        try {

            validateInputForCreateProcess(owner, processDetails);

            HashSet<Actor> ownerActors = new HashSet<Actor>();
            ownerActors.add(owner);
            //processDetails.setOwnerActors(ownerActors);

            // Delegate to ProcessDao
            ProcessDAO dao = getProcessDAO();
            dao.createProcess(owner, processDetails);
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            LOG.error(e);
            throw new WorkflowException(WorkflowEC.MSG_OTHER_SERVER_ERROR,
                    WorkflowEC.EC_OTHER_SERVER_ERROR);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Leave CreateProcess()");
        }
    }

    protected void deleteProcess(Actor owner, Process processDetails) {
        // Delegate to ProcessDao
        ProcessDAO dao = getProcessDAO();
        dao.deleteProcess(owner, processDetails);

    }
    private void validateInputForCreateProcess(Actor owner, Process processDetails) {
        // Validate the creating actor
        validateActor(owner);
        // Validate processName
        validateProcessName(processDetails);
    }

    private void validateProcessName(Process processDetails) {
        Set<ProcessVersion> versions = processDetails.getProcessVersions();
        if (CollectionUtilities.isEmpty(versions)) {
            String message = "Error: Process does not have versions and hence invalid process name";
            LOG.error(message);
            throw new WorkflowException(WorkflowEC.INVALID_PROCESS,
                    WorkflowEC.INVALID_PROCESS);
        }

        Iterator<ProcessVersion> iter = versions.iterator();
        ProcessVersion version = null;
        while (iter.hasNext()) {
            version = iter.next();
            if (StringUtilities.isEmpty(version.getProcessName())) {
                String message = "Error: Process does not have a valid process name";
                LOG.error(message);
                throw new WorkflowException(WorkflowEC.INVALID_PROCESS,
                        WorkflowEC.INVALID_PROCESS);
            }
        }
    }

    /**
     * This method returns the ProcessEngine implementation.
     *
     * @return ProcessEngineImpl
     */
    private ProcessEngineImpl getProcessEngine() {
        BeanFactory beanFactory = getSpringBeanFactory();
        return (ProcessEngineImpl) beanFactory
                .getBean(ServiceNames.PROCESS_ENGINE_IMPL);
    }

    private String extractProcessDefinition(Process processToDeploy) {
        ObjectUtilities.verifyNotNull(processToDeploy);
        // Extract the XML from Process
        ProcessVersion version = processToDeploy.getLatestProcessVersion();
        ObjectUtilities.verifyNotNull(version);
        try {
            return version.getProcessDefinition();
        } catch (TransformerException e) {
            String errorMessage = "Extraction of the process definition failed fro process name-id"
                    + processToDeploy.getProcessId();
            throw new WorkflowException(e, errorMessage);

        }
    }

    private ProcessDefinition isValidJbpmProcessDefinition(
            String processDefinitionXml) {
        ProcessDefinition processDefinition = null;
        try {
            processDefinition = ProcessDefinition
                    .parseXmlString(processDefinitionXml);
        } catch (JpdlException e) {
            String message = "Parsing the XML caused errors and XML is not deployable";
            LOG.error(message);
            throw new WorkflowException(e, WorkflowEC.INVALID_PROCESS);
        } catch (Exception e) {
            throw new WorkflowException(WorkflowEC.INVALID_PROCESS,
                    WorkflowEC.INVALID_PROCESS);
        }
        return processDefinition;
    }

    private void createNewVersion(Actor owner, Process processDetails) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Actor Owner : " + owner.getActorID());
            LOG.debug("Process Id  : " + processDetails.getProcessId());
        }
        /*
         * Increment the version
         * Update the process Version
         */
        //Expecting the modified version from the client
        //Have to do this to make the process version as additive
        ProcessVersion version = extractProcessVersion(processDetails);
        Long currentVersion = version.getVersionId();
        long newVersionNumber = currentVersion.longValue() + 1;
        LOG.debug("Incrementing version from " + currentVersion + " to "
                + newVersionNumber);
        ProcessVersion v2 = new ProcessVersion();
        v2.setProcessVersionId(version.getProcessVersionId());

        Process processFromDb = getProcessDAO().getProcess(owner, processDetails.getProcessId());
        Set<ProcessVersion> versionFromDb = processFromDb.getProcessVersions();
        versionFromDb.add(version);
        processFromDb.setProcessVersions(versionFromDb);

        createNewProcessVersion(owner, processDetails);

    }

    private void createNewProcessVersion(Actor owner, Process processDetails) {
        getProcessDAO().createNewProcessVersion(owner, processDetails);

    }
    private void updateExistingProcess(Actor owner, Process processDetails) {
        getProcessDAO().saveProcess(owner, processDetails);
    }

    private void incrementVersionNumber(Process processDetails) {
        ProcessVersion version = extractProcessVersion(processDetails);
        Long currentVersion = version.getVersionId();
        long newVersionNumber = currentVersion.longValue() + 1;
        LOG.debug("Incrementing version from " + currentVersion + " to "
                + newVersionNumber);

        version.setVersionId(new Long(newVersionNumber));
    }

    private ProcessVersion extractProcessVersion(Process processDetails) {
        Set<ProcessVersion> versions = processDetails.getProcessVersions();
        Iterator<ProcessVersion> iter = versions.iterator();
        ProcessVersion version = null;
        if (iter.hasNext()) {
            version = iter.next();
        }
        return version;
    }
    private boolean isAlreadyDeployed(Process processDetails) {
        ObjectUtilities.verifyNotNull(processDetails);
        Set<ProcessVersion> pv = processDetails.getProcessVersions();
        Iterator<ProcessVersion> iter = pv.iterator();
        ProcessVersion version = null;
        while (iter.hasNext()) {
            version = iter.next();
        }
        return version.isDeployed();
    }

    private Process doGetProcess(Actor owner, long processId) {
        validateActor(owner);
        validateProcessID(processId);

        Process process = getProcessDAO().getProcess(owner, processId);
        return process;
    }


}
