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

package com.mckesson.eig.workflow.process.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;

import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.Variable;
import com.mckesson.eig.workflow.api.VariableList;
import com.mckesson.eig.workflow.api.WorkflowEC;
import com.mckesson.eig.workflow.api.WorkflowException;
import com.mckesson.eig.workflow.process.ProcessListService;
import com.mckesson.eig.workflow.process.api.ProcessEC;
import com.mckesson.eig.workflow.process.api.ProcessInfoList;
import com.mckesson.eig.workflow.service.WorkflowAuditManger;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author McKesson
 * @date   Feb 10, 2009
 * @since  HECM 2.0; Feb 10, 2009
 */
@WebService(
name              = "ProcessListPortType_v1_0",
portName          = "processlist_v1_0",
serviceName       = "ProcessListService_v1_0",
targetNamespace   = "http://eig.mckesson.com/wsdl/processlist-v1",
endpointInterface = "com.mckesson.eig.workflow.process.ProcessListService")
public class ProcessListServiceImpl extends BaseProcessServiceImpl implements ProcessListService {

    /**
     * Object represents the Log4JWrapper object.
     */
    private static final Log LOG = LogFactory.getLogger(ProcessListServiceImpl.class);

    /**
     * Key used to store group actors of the logged in user actor
     */
    private static final String ACTORS = "Actors";

    /**
     * Process Assignments audit message
     */
    private final String _assignmentAuditComment = "Assigned Processes :";

    /**
     * Process Un-Assignments audit message
     */
    private final String _unAssignmentAuditComment = "Un-Assigned Processes :";

    /**
     *
     * @see com.mckesson.eig.workflow.process.ProcessListService
     *                  #getAllProcesses(int, boolean, com.mckesson.eig.workflow.api.VariableList)
     */
    public ProcessInfoList getAllProcesses(int applicationId,
                                       boolean canIncludeExpired,
                                       VariableList variableList) {

        final String logSourceMethod =
            "getAllProcesses(applicationId, canIncludeExpired, filterByProperties)";
        LOG.debug(logSourceMethod + ">>Start");

        ProcessInfoList processInfoList;
        try {

            validateApplicationID(applicationId);
            validateProcessType(variableList);

            processInfoList = getProcessListDAO().getAllProcesses(canIncludeExpired,
                                                          variableList);
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {

            LOG.error(e);
            throw new WorkflowException(WorkflowEC.MSG_OTHER_SERVER_ERROR,
                                        WorkflowEC.EC_OTHER_SERVER_ERROR);
        }

        LOG.debug(logSourceMethod + "<<End");
        return processInfoList;
    }

    /**
     *
     * @see com.mckesson.eig.workflow.process.ProcessListService
     *                         #getAssignedProcesses(int, com.mckesson.eig.workflow.api.Actor)
     */
    public ProcessInfoList getAssignedProcesses(int applicationID, Actor assignedTo) {

        final String logSourceMethod = "getAssignedProcesses(applicationID, assignedTo)";
        LOG.debug(logSourceMethod + ">>Start");

        ProcessInfoList processInfoList;
        try {

            validateApplicationID(applicationID);
            validateActor(assignedTo);

            processInfoList = getProcessListDAO().getAssignedProcesses(assignedTo);
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {

            LOG.error(e);
            throw new WorkflowException(WorkflowEC.MSG_OTHER_SERVER_ERROR,
                                        WorkflowEC.EC_OTHER_SERVER_ERROR);
        }

        LOG.debug(logSourceMethod + "<<End");
        return processInfoList;
    }

    /**
     *
     * @see com.mckesson.eig.workflow.process.ProcessListService
     *              #updateProcessAssignements(com.mckesson.eig.workflow.api.Actor, java.util.List)
     */
    public void updateProcessAssignments(Actor assignedTo, List<Long> processListIds) {

        final String logSourceMethod = "updateProcessAssignements(assignedTo, processListIds)";
        LOG.debug(logSourceMethod + ">>Start");

        try {

            validateActor(assignedTo);
            processListIds = (processListIds == null) ? new ArrayList<Long>() : processListIds;
            validateProcessIDsList(processListIds);

            Map<String, List<String>> assignedUnassignedprocessNames =
                    getProcessListDAO().updateProcessAssignments(assignedTo, processListIds);


            WorkflowAuditManger auditMgr = getWorkflowAuditManager();
            AuditEvent ae = auditMgr.prepareSucessFailAuditEvent(
                              0,
                              getUserID(),
                              AE_UPDATED_CONFIGURATION,
                              getProcessAssignmentsAuditComment(
                            		  assignedTo.getEntityID(), assignedUnassignedprocessNames),
                              AuditEvent.SUCCESS);
            auditMgr.createAuditEntry(ae);
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {

            LOG.error(e);
            throw new WorkflowException(WorkflowEC.MSG_OTHER_SERVER_ERROR,
                                        WorkflowEC.EC_OTHER_SERVER_ERROR);
        }
        LOG.debug(logSourceMethod + "<<End");
    }

    /**
     * @see com.mckesson.eig.workflow.process.ProcessListService
     *              #getProcesses(Actors,Actors,VariableList)
     */
    public ProcessInfoList getProcesses(Actors ownedBy,
                                    Actors assignedTo,
                                    VariableList filterByProperties) {

        final String logSourceMethod = "getProcesses(ownedBy, assignedTo, filterByProperties)";
        LOG.debug(logSourceMethod + ">>Start");

        ProcessInfoList processInfoList = null;
        try {

        	// get group actors from wssession
        	Actors actors = (Actors) WsSession.getSessionData(ACTORS);
        	Set<Actor> acts = actors.getActors();
        	if (assignedTo != null) {

        		Set<Actor> actorSet = assignedTo.getActors();
    			if (actorSet != null) {

                    for (Iterator<Actor> iterator = actorSet.iterator(); iterator.hasNext();) {

        				Actor actor = iterator.next();
						acts.add(actor);
					}
                    assignedTo.setActors(acts);
    			}
        	}
            validateActors(assignedTo);
            validateActors(ownedBy);
            validateProcessType(filterByProperties);

            processInfoList = getProcessListDAO().getProcesses(ownedBy,
                                                       assignedTo, filterByProperties);
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {

            LOG.error(e);
            throw new WorkflowException(WorkflowEC.MSG_OTHER_SERVER_ERROR,
                                        WorkflowEC.EC_OTHER_SERVER_ERROR);
        }
        LOG.debug(logSourceMethod + "<<End");
        return processInfoList;
    }

   /**
    *
    * @see com.mckesson.eig.workflow.process.ProcessListService
    *                  #getOwnedProcesses(int, boolean, Actors, VariableList)
    */
    public ProcessInfoList getOwnedProcesses(int applicationId,
            boolean canIncludeExpired, Actors ownedBy, VariableList variableList) {
        final String logSourceMethod = "getOwnedProcesses(applicationId, "
            + "canIncludeExpired, ownedBy, filterByProperties)";
        LOG.debug(logSourceMethod + ">>Start");

        ProcessInfoList processInfoList;
        try {

            validateApplicationID(applicationId);
            validateActors(ownedBy);
            validateProcessType(variableList);
            processInfoList = getProcessListDAO().getOwnedProcesses(canIncludeExpired,
                    ownedBy, variableList);
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {

            LOG.error(e);
            throw new WorkflowException(WorkflowEC.MSG_OTHER_SERVER_ERROR,
                                        WorkflowEC.EC_OTHER_SERVER_ERROR);
        }

        LOG.debug(logSourceMethod + "<<End");
        return processInfoList;
    }

    /**
    *
    * @see com.mckesson.eig.workflow.process.ProcessListService
    *                  #getOwnedProcessesInfo(int, boolean, Actors, VariableList)
    */
    public ProcessInfoList getOwnedProcessesInfo(int applicationId,
            boolean canIncludeExpired, Actors ownedBy, VariableList variableList) {
        final String logSourceMethod = "getOwnedProcessesInfo(applicationId, "
            + "canIncludeExpired, ownedBy, filterByProperties)";
        LOG.debug(logSourceMethod + ">>Start");

        ProcessInfoList processInfoList;
        try {

            validateApplicationID(applicationId);
            validateActors(ownedBy);
            validateProcessType(variableList);
            processInfoList = getProcessListDAO().getOwnedProcessesInfo(canIncludeExpired,
                    ownedBy, variableList);
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {

            LOG.error(e);
            throw new WorkflowException(WorkflowEC.MSG_OTHER_SERVER_ERROR,
                                        WorkflowEC.EC_OTHER_SERVER_ERROR);
        }

        LOG.debug(logSourceMethod + "<<End");
        return processInfoList;
    }

    /**
     * Checks whether the filter properties holds the proper values like manual, auto and both.
     * It holds other than these values ProcessException will be thrown
     *
     * @param filterByProperties
     */
    @SuppressWarnings("unchecked")
    private void validateProcessType(VariableList filterByProperties) {

        List<String> processTypes = CollectionUtilities.buildStringList("MANUAL,SYSTEM,BOTH", ",");

        if (filterByProperties == null || CollectionUtilities.isEmpty(
                                                             filterByProperties.getVariables())) {

            WorkflowException we = new WorkflowException(ProcessEC.MSG_PROCESS_TYPE_NOT_SPECIFIED,
                                                         ProcessEC.EC_PROCESS_TYPE_NOT_SPECIFIED);
            LOG.error(we);
            throw we;
        }

        List<Variable> variables = filterByProperties.getVariables();
        boolean isProcessTypeAvailable = false;
        for (Iterator<Variable> i = variables.iterator(); i.hasNext();) {

            String variable = i.next().getValue();
            if (processTypes.contains(variable.toLowerCase())) {

                isProcessTypeAvailable = true;
                break;
            }
        }

        if (!isProcessTypeAvailable) {

            WorkflowException we = new WorkflowException(ProcessEC.MSG_INVALID_PROCESS_TYPE,
                                                         ProcessEC.EC_INVALID_PROCESS_TYPE);
            LOG.error(we);
            throw we;
        }
    }

    /**
     * This method validates if the Process IDs is null or negative. If the ids
     * passed is null or negative, ProcessException will be thrown.
     *
     * @param processListIds
     *        list of process IDs
     */
    private void validateProcessIDsList(List<Long> processListIds) {

        for (Long processID : processListIds) {
            validateProcessID(processID);
            doesProcessExist(processID);
        }
    }

    /**
     *
     * @param entityID
     * @param processNames
     * @return
     */
    private String getProcessAssignmentsAuditComment(long entityID, List<String> processNames,
    		                                                       String assignmentComment) {

    	StringBuffer processNamesComment = new StringBuffer();
    	if (processNames.size() > 0) {
    		processNamesComment.append(assignmentComment);
    	}
    	for (int i = processNames.size(); --i >= 0;) {

             processNamesComment.append(entityID)
                                .append("=")
                                .append(processNames.get(i))
                                .append(i == 0 ? StringUtilities.EMPTYSTRING : ",");
         }
    	 return processNamesComment.toString();
    }

    /**
     *
     * @param entityID
     * @param assignedUnassignedprocessNames
     * @return
     */

    private String getProcessAssignmentsAuditComment(long entityID,
    		             Map<String, List<String>> assignedUnassignedprocessNames) {

    	StringBuffer processAssignmentsComment = new StringBuffer();
        List<String> processNames = assignedUnassignedprocessNames.get("assigned");
        processAssignmentsComment.append(getProcessAssignmentsAuditComment(
        		                       entityID, processNames, _assignmentAuditComment));
        processNames = assignedUnassignedprocessNames.get("unassigned");
        processAssignmentsComment.append(StringUtilities.EMPTYSTRING)
                                 .append(getProcessAssignmentsAuditComment(
        		                       entityID, processNames, _unAssignmentAuditComment));
        return processAssignmentsComment.toString();
    }

}
