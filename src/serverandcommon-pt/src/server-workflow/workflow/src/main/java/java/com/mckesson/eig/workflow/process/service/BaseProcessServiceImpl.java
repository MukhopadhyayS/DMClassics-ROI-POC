package com.mckesson.eig.workflow.process.service;

import com.mckesson.dm.core.common.logging.OCLogger;
import org.springframework.beans.factory.BeanFactory;

import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.workflow.Utilities.ServiceNames;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.WorkflowEC;
import com.mckesson.eig.workflow.api.WorkflowException;
import com.mckesson.eig.workflow.process.api.Process;
import com.mckesson.eig.workflow.process.api.ProcessEC;
import com.mckesson.eig.workflow.process.dao.ProcessDAO;
import com.mckesson.eig.workflow.process.dao.ProcessListDAO;
import com.mckesson.eig.workflow.service.AbstractWorkflowService;

public class BaseProcessServiceImpl extends AbstractWorkflowService {

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( BaseProcessServiceImpl.class);

    /**
     * Checks whether the application id passed is less than zero or not.
     * If less then zero, ProcessException will be thrown otherwise not
     *
     * @param applicationId
     *        applicationID to be checked
     */
    protected void validateApplicationID(int applicationId) {

        if (applicationId < 0) {

            WorkflowException we = new WorkflowException(ProcessEC.MSG_INVALID_APPLICATION_ID,
                                                         ProcessEC.EC_INVALID_APPLICATION_ID);
            LOG.error(we);
            throw we;
        }
    }

    /**
     * This method validates the processID is less than or equal to zero
     *
     *
     * @param processID
     *          processID to be verified.
     */
    protected void validateProcessID(long processID) {

        if (processID < 1) {
            WorkflowException we = new WorkflowException(ProcessEC.MSG_INVALID_PROCESS_ID,
                                                         ProcessEC.EC_INVALID_PROCESS_ID);
            LOG.error(we);
            throw we;
        }
    }

    /**
     * This method validates the process object
     *
     *
     * @param process
     *          process to be verified.
     */
    protected void validateProcessObject(Process processObject) {

        if (processObject == null) {
            LOG.error("Process object is null");
            WorkflowException we = new WorkflowException(ProcessEC.MSG_INVALID_PROCESS_ID,
                                                         ProcessEC.EC_INVALID_PROCESS_ID);
            throw we;
        }
    }

    /**
     * This method validates the processID exist in the the system
     *
     * @param processID
     *          processID to be verified.
     */
    protected void doesProcessExist(long processID) {
        try {
            getProcessListDAO().get(Process.class, processID);
        } catch (WorkflowException e) {
            throw new WorkflowException(ProcessEC.MSG_PROCESS_NOT_AVAILABLE,
                                        ProcessEC.EC_PROCESS_NOT_AVAILABLE);
        }
    }

    /**
     * This method returns the DAO implementation for this business service.
     * @return ProcessListDAO
     */
    protected ProcessListDAO getProcessListDAO() {
        return (ProcessListDAO) getDAO(ServiceNames.PROCESSLIST_DAO);
    }

    /**
     * This method returns the DAO implementation for this business service.
     * @return ProcessDAO
     */
    protected ProcessDAO getProcessDAO() {
        return (ProcessDAO) getDAO(ServiceNames.PROCESS_DAO);
    }

    /**
     * This method verifies if a passed actor is null or not.
     * @param owner
     */
    protected void validateActors(Actors assignedTo) {

        if (assignedTo == null || assignedTo.getActors() == null) {

            WorkflowException we = new WorkflowException(WorkflowEC.MSG_NULL_ACTORS,
                                                         WorkflowEC.EC_NULL_ACTORS);
            LOG.error(we);
            throw we;
        }
        assignedTo.validateActors();
    }

    /**
     * This method verifies if a passed actor is null or not.
     * @param owner
     */
    protected void validateActor(Actor assignedTo) {

        if (assignedTo == null) {

            WorkflowException we = new WorkflowException(WorkflowEC.MSG_NULL_ACTOR,
                                                         WorkflowEC.EC_NULL_ACTOR);
            LOG.error(we);
            throw we;
        }
        assignedTo.validateActor();
    }

    /**
     * This method is used to get the spring bean factory
     * @return
     */
    protected BeanFactory getSpringBeanFactory() {
        return SpringUtilities.getInstance().getBeanFactory();
    }


}
