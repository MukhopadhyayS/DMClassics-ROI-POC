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

package com.mckesson.eig.workflow.engine.service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.persistence.db.DbPersistenceServiceFactory;
import org.jbpm.svc.Services;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.workflow.Utilities.ServiceNames;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.Variable;
import com.mckesson.eig.workflow.api.VariableList;
import com.mckesson.eig.workflow.api.WorkflowEC;
import com.mckesson.eig.workflow.api.WorkflowEngineException;
import com.mckesson.eig.workflow.process.api.ProcessEC;
import com.mckesson.eig.workflow.process.api.ProcessInstance;
import com.mckesson.eig.workflow.process.api.ProcessVersion;
import com.mckesson.eig.workflow.process.dao.ProcessDAO;
import com.mckesson.eig.workflow.processinstance.ProcessInstanceService;
import com.mckesson.eig.workflow.processinstance.dao.ProcessInstanceDAO;
import com.mckesson.eig.workflow.service.AbstractWorkflowService;


/**
 * JMS Consumer for process instances.
 *
 */
@WebService(
	name              = "ProcessInstancePortType_v1_0",
	portName          = "processinstance_v1_0",
	serviceName       = "ProcessInstanceService_v1_0",
	targetNamespace   = "http://eig.mckesson.com/wsdl/processinstance-v1",
	endpointInterface = "com.mckesson.eig.workflow.processinstance.ProcessInstanceService")
public class ProcessInstanceJmsConsumer
extends AbstractWorkflowService
implements ProcessInstanceService {

    private static final Log LOG = LogFactory.getLogger(ProcessInstanceJmsConsumer.class);

    private static final String KEY_PROCESSNAME = "Process";
    private static final String KEY_APPLICATION = "Application";

    private JbpmContext _jBPMContext = null;
    private Session _session = null;

    /**
     * (non-Javadoc)
     * @see com.mckesson.eig.workflow.processinstance.ProcessInstanceService
     * #startProcessInstance(com.mckesson.eig.workflow.api.Actor,
     * com.mckesson.eig.workflow.api.Actors, long, com.mckesson.eig.workflow.api.VariableList)
     *
     */
    public void startProcessInstance(Actor userActor,
                                     Actors aclActors,
                                     long processId,
                                     VariableList variableList) {

        final String logSourceMethod =
            "startProcessInstance(userActor,aclActors,processId,variableList)";
        LOG.debug(logSourceMethod + ">>Start");

        try {

            SessionFactory hibernateSessionFactory = initializeJbpmContext();
            //initialize dao session factory with JBPM's session factory.
            getProcessDAO().setSessionFactory(hibernateSessionFactory);

        	ProcessVersion processVersion = getProcessDAO().getProcessVersion(processId);
        	validateProcessVersion(processVersion);
        	if (variableList != null) {

        		List<Variable> variables = variableList.getVariables();
        		if (variables == null) {
        			variables = new ArrayList<Variable>();
        		}
                variables.add(getAsVariable(KEY_PROCESSNAME, processVersion.getProcessName()));
                variables.add(getAsVariable(KEY_APPLICATION,
                                     getProcessDAO().getApplicationName(userActor.getAppID())));
                variableList.setVariables(variables);
        	}

    		ProcessInstanceEngineImpl engine = getProcessInstanceEngine(userActor, _jBPMContext,
                              "" + processVersion.getProcess().getProcessId(), variableList);

    		engine.startProcessInstance();
        } catch (WorkflowEngineException we) {
            LOG.error("WorkflowEngineException in ProcessInstanceJmsConsumer::startProcessInstance:"
                    + we.getMessage()
                    , we);
            throw we;
        } catch (Exception e) {
            LOG.error("Exception in ProcessInstanceJmsConsumer::startProcessInstance: "
                    + e.getMessage()
                    , e);
            throw new WorkflowEngineException(WorkflowEC.MSG_OTHER_SERVER_ERROR,
                                              WorkflowEC.EC_OTHER_SERVER_ERROR);
        } finally {

            if (_jBPMContext != null) {
                _jBPMContext.close();
            }

            if (_session != null && _session.isOpen()) {
                LOG.debug(logSourceMethod
                        + "Session is open after JBPM is closed; closing session explicitly");
                _session.close();
        }
        }

        LOG.debug(logSourceMethod + "<<End");
    }

    /**
     * Entry point for notifying a process instance to continue execution.
     *
     * @param notificationType
     * @param userActor
     * @param aclActors
     * @param sourceProcInstId
     * @param notifiedProcInstId
     * @param token
     */
    public void notifyProcessInstance(String notificationType,
                                      Actor userActor,
                                      Actors aclActors,
                                      long sourceProcInstId,
                                      long notifiedProcInstId,
                                      long token) {
        final String logSourceMethod =
            "notifyProcessInstance(aclActors,sourceProcInstId,notifiedProcInstId,token)";
        LOG.debug(logSourceMethod + ">>Start");

        try {

            SessionFactory hibernateSessionFactory = initializeJbpmContext();
            //initialize dao session factory with JBPM's session factory.
            getProcessInstanceDAO().setSessionFactory(hibernateSessionFactory);

            ProcessInstanceEngineImpl engine =
                new ProcessInstanceEngineImpl(token, notifiedProcInstId, _jBPMContext);

            engine.notifyProcessInstance();
        } catch (WorkflowEngineException we) {
            LOG.error("WorkflowEngineException in ProcessInstanceJmsConsumer:notifyProcessInstance:"
                + we.getMessage()
                , we);
            throw we;
        } catch (Exception e) {
            LOG.error("Exception in ProcessInstanceJmsConsumer:notifyProcessInstance: "
                + e.getMessage()
                , e);
            throw new WorkflowEngineException(WorkflowEC.MSG_OTHER_SERVER_ERROR,
                                          WorkflowEC.EC_OTHER_SERVER_ERROR);
        } finally {
            if (_jBPMContext != null) {
                _jBPMContext.close();
            }

            if (_session != null && _session.isOpen()) {
                LOG.debug(logSourceMethod
                        + "Session is open after JBPM is closed; closing session explicitly");
                _session.close();
        }
        }

        LOG.debug(logSourceMethod + "<<End");
    }

    /**
     * (non-Javadoc)
     * @see com.mckesson.eig.workflow.processinstance.ProcessInstanceService
     * #suspendProcessInstance(com.mckesson.eig.workflow.api.Actor,
     * com.mckesson.eig.workflow.api.Actors, long)
     *
     */
    public void suspendProcessInstance(Actor userActor,
                                       Actors aclActors,
                                       long processInstanceId) {
        final String logSourceMethod =
            "suspendProcessInstance(aclActors,sourceProcInstId,notifiedProcInstId,token)";
        LOG.debug(logSourceMethod + ">>Start");

        try {

            SessionFactory hibernateSessionFactory = initializeJbpmContext();
            //initialize dao session factory with JBPM's session factory.
            getProcessInstanceDAO().setSessionFactory(hibernateSessionFactory);

            ProcessInstanceEngineImpl engine =
                new ProcessInstanceEngineImpl(processInstanceId, _jBPMContext);
            engine.suspendProcessInstance(userActor);

        } catch (WorkflowEngineException we) {
            LOG.error("WorkflowEngineException in ProcessInstanceJmsConsumer:"
                     + "suspendProcessInstance" + we.getMessage(), we);
            throw we;
        } catch (Exception e) {
            LOG.error("Exception in ProcessInstanceJmsConsumer:suspendProcessInstance: "
                + e.getMessage(), e);
            throw new WorkflowEngineException(WorkflowEC.MSG_OTHER_SERVER_ERROR,
                                          WorkflowEC.EC_OTHER_SERVER_ERROR);
        } finally {
            if (_jBPMContext != null) {
                _jBPMContext.close();
            }

            if (_session != null && _session.isOpen()) {
                LOG.debug(logSourceMethod
                        + "Session is open after JBPM is closed; closing session explicitly");
                _session.close();
            }
        }

        LOG.debug(logSourceMethod + "<<End");
    }

    /**
     * (non-Javadoc)
     * @see com.mckesson.eig.workflow.processinstance.ProcessInstanceService
     * #resumeProcessInstance(com.mckesson.eig.workflow.api.Actor,
     * com.mckesson.eig.workflow.api.Actors, long)
     */
    public void resumeProcessInstance(Actor userActor,
                                      Actors aclActors,
                                      long processInstanceId) {

        final String logSourceMethod =
            "resumeProcessInstance(aclActors,sourceProcInstId,notifiedProcInstId,token)";
        LOG.debug(logSourceMethod + ">>Start");

        try {

            SessionFactory hibernateSessionFactory = initializeJbpmContext();
            //initialize dao session factory with JBPM's session factory.
            getProcessInstanceDAO().setSessionFactory(hibernateSessionFactory);

            ProcessInstanceEngineImpl engine =
                new ProcessInstanceEngineImpl(processInstanceId, _jBPMContext);
            engine.resumeProcessInstance(userActor);
            engine.notifyProcessInstance();
        } catch (WorkflowEngineException we) {
            LOG.error("WorkflowEngineException in ProcessInstanceJmsConsumer:"
                     + "resumeProcessInstance" + we.getMessage(), we);
            throw we;
        } catch (Exception e) {
            LOG.error("Exception in ProcessInstanceJmsConsumer:resumeProcessInstance: "
                + e.getMessage(), e);
            throw new WorkflowEngineException(WorkflowEC.MSG_OTHER_SERVER_ERROR,
                                          WorkflowEC.EC_OTHER_SERVER_ERROR);
        } finally {
            if (_jBPMContext != null) {
                _jBPMContext.close();
            }

            if (_session != null && _session.isOpen()) {
                LOG.debug(logSourceMethod
                        + "Session is open after JBPM is closed; closing session explicitly");
                _session.close();
            }
        }

        LOG.debug(logSourceMethod + "<<End");
    }

    /**
     * (non-Javadoc)
     * @see com.mckesson.eig.workflow.processinstance.ProcessInstanceService
     * #terminateProcessInstance(com.mckesson.eig.workflow.api.Actor,
     * com.mckesson.eig.workflow.api.Actors, long)
     */
	public void terminateProcessInstance(Actor userActor,
	                                  Actors aclActors,
	                                  long processInstanceId) {
        final String logSourceMethod =
            "terminateProcessInstance(aclActors,sourceProcInstId,notifiedProcInstId,token)";
        LOG.debug(logSourceMethod + ">>Start");

        try {

            SessionFactory hibernateSessionFactory = initializeJbpmContext();
            //initialize dao session factory with JBPM's session factory.
            getProcessInstanceDAO().setSessionFactory(hibernateSessionFactory);

            ProcessInstanceEngineImpl engine =
                new ProcessInstanceEngineImpl(processInstanceId, _jBPMContext);
            engine.terminateProcessInstance(userActor);

        } catch (WorkflowEngineException we) {
            LOG.error("WorkflowEngineException in ProcessInstanceJmsConsumer:"
                     + "terminateProcessInstance" + we.getMessage(), we);
            throw we;
        } catch (Exception e) {
            LOG.error("Exception in ProcessInstanceJmsConsumer:terminateProcessInstance: "
                + e.getMessage(), e);
            throw new WorkflowEngineException(WorkflowEC.MSG_OTHER_SERVER_ERROR,
                                          WorkflowEC.EC_OTHER_SERVER_ERROR);
        } finally {
            if (_jBPMContext != null) {
                _jBPMContext.close();
            }

            if (_session != null && _session.isOpen()) {
                LOG.debug(logSourceMethod
                        + "Session is open after JBPM is closed; closing session explicitly");
                _session.close();
            }
        }

        LOG.debug(logSourceMethod + "<<End");
    }

	public void deleteProcessInstance(Actor userActor,
	                                  Actors aclActors,
	                                  long processInstanceId) {
        throw new UnsupportedOperationException("ProcessInstanceJmsConsumer.deleteProcessInstance");

	}

	public ProcessInstance getProcessInstance(Actor userActor,
	                                          Actors aclActors,
	                                          long processInstanceId) {
        throw new UnsupportedOperationException("ProcessInstanceJmsConsumer.getProcessInstance");
	}

    /**
     * This method returns the DAO implementation for process.
     * @return ProcessDAO
     */
    private ProcessDAO getProcessDAO() {
        return (ProcessDAO) getDAO(ProcessDAO.class.getName());
    }

    /**
     * This method returns the DAO implementation for process instance.
     * @return ProcessInstanceDAO
     */
    private ProcessInstanceDAO getProcessInstanceDAO() {
        return (ProcessInstanceDAO) getDAO(ProcessInstanceDAO.class.getName());
    }

    /**
     * Creates ProcessInstanceEngineImpl object.
     *
     * @param userActor
     * @param jBPMContext
     * @param processName
     * @param variables
     * @return ProcessInstanceEngineImpl
     */
    private ProcessInstanceEngineImpl getProcessInstanceEngine(Actor userActor,
    		                                                   JbpmContext jBPMContext,
    		                                                   String processName,
                                                               VariableList variables) {
		return new ProcessInstanceEngineImpl(userActor, jBPMContext,
    	                                     processName,
    	                                     variables == null ? null : variables.toMap());
    }

    /**
     * Method to validate the
     * @param processVersion
     */
    private void validateProcessVersion(ProcessVersion processVersion) {

        if (processVersion == null || processVersion.getProcessName() == null) {

        	WorkflowEngineException we =
        	    new WorkflowEngineException(ProcessEC.EC_PROCESS_NOT_AVAILABLE,
		        			                ProcessEC.EC_PROCESS_NOT_AVAILABLE);
            LOG.error(we);
            throw we;
        }
    }

    /**
     * Initializes jbpmContext locally for transaction control.
     *
     * @return SessionFactory used in jbpmContext
     */
    private SessionFactory initializeJbpmContext() {

        //open a session to database
        SessionFactory hibernateSessionFactory =
            (SessionFactory) getSpringBean(ServiceNames.HIBERNATE_SESSION_FACTORY);
        _session = hibernateSessionFactory.openSession();

        //initialize jbpm persistence layer
        JbpmConfiguration jc = JbpmConfiguration.getInstance();
        DbPersistenceServiceFactory dbPSF =  (DbPersistenceServiceFactory)
                                       jc.getServiceFactory(Services.SERVICENAME_PERSISTENCE);
        dbPSF.setSessionFactory(hibernateSessionFactory);
        _jBPMContext = jc.createJbpmContext();

        return hibernateSessionFactory;
}

    /**
     * Retrieves spring bean from configuration.
     *
     * @param beanName
     * @return beanObject
     */
    private Object getSpringBean(String beanName) {
        return SpringUtilities
                .getInstance().getBeanFactory().getBean(
                        beanName);
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    private Variable getAsVariable(String key, String value) {

    	Variable variable = new Variable();
    	variable.setKey(key);
    	variable.setValue(value);
    	return variable;
}


}
