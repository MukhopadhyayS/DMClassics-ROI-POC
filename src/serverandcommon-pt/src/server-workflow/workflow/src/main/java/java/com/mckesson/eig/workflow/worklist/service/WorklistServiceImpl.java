/*
 * Copyright 2007-2010 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.workflow.worklist.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;

import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.IDListResult;
import com.mckesson.eig.workflow.api.SortOrder;
import com.mckesson.eig.workflow.api.WorkflowException;
import com.mckesson.eig.workflow.service.AbstractWorkflowService;
import com.mckesson.eig.workflow.service.WorkflowAuditManger;
import com.mckesson.eig.workflow.worklist.api.AssignedWLCriteria;
import com.mckesson.eig.workflow.worklist.api.CreateWLCriteria;
import com.mckesson.eig.workflow.worklist.api.ListACLs;
import com.mckesson.eig.workflow.worklist.api.ListWorklist;
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.api.WorklistEC;
import com.mckesson.eig.workflow.worklist.api.WorklistException;
import com.mckesson.eig.workflow.worklist.dao.WorklistDAO;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author Pranav Amarasekaran
 * @date   Dec 6, 2007
 * @since  HECM 1.0
 *
 * This class defines the business methods the client will invoke in order
 * to view and manage worklists.
 */
@WebService(
        name              = "WorklistPortType_v1_0",
        portName          = "worklist_v1_0",
        serviceName       = "WorklistService_v1_0",
        targetNamespace   = "http://eig.mckesson.com/wsdl/worklist-v1",
        endpointInterface = "com.mckesson.eig.workflow.worklist.service.WorklistService")
public class WorklistServiceImpl
extends AbstractWorkflowService
implements WorklistService {

    private static final String WORKLIST_DAO_ID = "WorklistDAO";

    /**
     * Object represents the Log4JWrapper object.
     */
    private static final Log LOG = LogFactory.getLogger(WorklistServiceImpl.class);

    /**
     * Instantiates this implementation of business service.
     */
    public WorklistServiceImpl() {
        super();
    }

    /**
     * This method returns the current user id.
     * @return
     */
    public long getUserID() {
        return WsSession.getSessionUserId();
    }
    
    /**
     * This method returns the domain id of the current actor.
     * @return
     */
    public long getDomainID(Set< ? > ownerActors) {
        return ((Actor) ownerActors.iterator().next()).getEntityID();
    }
    
    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getWorklists(com.mckesson.eig.workflow.api.Actor,
     *                long, long, com.mckesson.eig.workflow.api.SortOrder)
     */
    public ListWorklist getWorklists(Actor owner,
                                     int startIndex,
                                     int count,
                                     SortOrder sortOrder) {

        final String logSourceMethod = "getWorklists(belongsTo, startIndex, count, sortOrder)";
        LOG.debug(logSourceMethod + ">>Start");

        ListWorklist listWL = null;

        try {

            sortOrder = getValidatedSortOrder(sortOrder);
            validateStartandCount(startIndex, count);
            validateActor(owner);

            listWL = getWorklistDAO().getWorklists(owner, startIndex, count, sortOrder);

            LOG.debug(logSourceMethod + "<<End");
            return listWL;

        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR, 
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }
    
    /**
     * 
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *      #getOwnedWorklists(com.mckesson.eig.workflow.api.Actor)
     */
    public ListWorklist getOwnedWorklists(Actor owner) {
        
        final String logSourceMethod = "getOwnedWorklists(owner)";
        LOG.debug(logSourceMethod + ">>Start");
        
        try {
            
            validateActor(owner);
            ListWorklist ownedWorklists = getWorklistDAO().getOwnedWorklists(owner);
            
            LOG.debug(logSourceMethod + "<<End");
            return ownedWorklists;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR, 
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
        
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getWorklists(long, long, com.mckesson.eig.workflow.api.SortOrder)
     */
    public ListWorklist getAllWorklists(int startIndex,
                                        int count,
                                        SortOrder sortOrder) {

        final String logSourceMethod = "getAllWorklists(startIndex, count, sortOrder)";

        LOG.debug(logSourceMethod + ">>Start");

        ListWorklist listWL = null;

        try {

            sortOrder = getValidatedSortOrder(sortOrder);
            validateStartandCount(startIndex, count);

            listWL = getWorklistDAO().getAllWorklists(startIndex, count, sortOrder);

            LOG.debug(logSourceMethod + "<<End");
            return listWL;

        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,  
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }
    
    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #resolveTaskAclsByActors(createdBy)
     */
    public void resolveTaskAclsByActors(Actors actors) {

        final String logSourceMethod = "resolveTaskAclsByActors(createdBy)";
        LOG.debug(logSourceMethod + ">>Start");

        try {

            validateActors(actors);
            getWorklistDAO().resolveTaskAclsByActors();

            LOG.debug(logSourceMethod + "<<End");

        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,  
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }
    
    /**
     * This method validates the sort order specified for the worklist. If the
     * sort order is found to be invalid the sort order is set to sort by the
     * default atrribute(name) of the worklist.
     *
     * @param sortOrder
     */
    private SortOrder getValidatedSortOrder(SortOrder sortOrder) {

        if ((sortOrder == null)
            || (sortOrder.getAttr() == null)
            || (StringUtilities.isEmpty(sortOrder.getAttr().getName()))
            || !(("name".equals(sortOrder.getAttr().getName().trim()))
                || ("desc".equals(sortOrder.getAttr().getName().trim()))
                || ("taskcount".equals(sortOrder.getAttr().getName().trim())))) {

            throw new WorklistException(WorklistEC.MSG_INVALID_SORTORDER,
                                        WorklistEC.EC_INVALID_SORTORDER);  
        }
        return sortOrder;
    }

    /**
     * This method checks if the start value is not negative and the
     * count is no less than zero.
     * @param startIndex
     * @param count
     */
    private void validateStartandCount(int startIndex, int count) {

        if (startIndex < 0 || count < 1) {

            WorklistException we = new WorklistException(WorklistEC.MSG_INVALID_START_COUNT,
                                                         WorklistEC.EC_INVALID_START_COUNT);
            LOG.error(we);
            throw we;
        }
    }

    /**
     * This method verifies if a passed actor is null or not.
     * @param owner
     */
    private void validateActor(Actor owner) {

        if (owner == null) {

            WorklistException we = new WorklistException(WorklistEC.MSG_NULL_ACTOR,
                                                         WorklistEC.EC_NULL_ACTOR);
            LOG.error(we);
            throw we;
        }
        owner.validateActor();
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getWorklistsCount(com.mckesson.eig.workflow.api.Actor)
     */
    public long getWorklistsCount(Actor owner)  {

        final String logSourceMethod = "getWorklistsCount(belongsTo)";
        LOG.debug(logSourceMethod + ">>Start");

        long count = 0;

        try {

            validateActor(owner);
            count = getWorklistDAO().getWorklistsCount(owner);

            LOG.debug(logSourceMethod + "<<End");
            return count;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,  
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }


    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getAllWorklistsCount()
     */
    public long getAllWorklistsCount()  {

        final String logSourceMethod = "getAllWorklistsCount()";
        LOG.debug(logSourceMethod + ">>Start");

        long count = 0;

        try {

            count = getWorklistDAO().getAllWorklistsCount();

            LOG.debug(logSourceMethod + "<<End");
            return count;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getAssignedWorklists(
     *      com.mckesson.eig.workflow.worklist.api.AssignedWLCriteria,
     *      long, long, com.mckesson.eig.workflow.api.SortOrder)
     */
    public ListWorklist getAssignedWorklists(AssignedWLCriteria assignedWLC,
                                             int startIndex,
                                             int count,
                                             SortOrder sortOrder) {

        final String logSourceMethod =
            "getAssignedWorklists(assignedWLC, startIndex, count, sortOrder)";
        LOG.debug(logSourceMethod + ">>Start");

        ListWorklist listWL;

        try {

            sortOrder = getValidatedSortOrder(sortOrder);
            validateStartandCount(startIndex, count);
            validateCriteria(assignedWLC);

            listWL = getWorklistDAO().getAssignedWorklists(assignedWLC,
                                                          startIndex,
                                                          count,
                                                          sortOrder);

            LOG.debug(logSourceMethod + "<<End");
            return listWL;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getAllAssignedWorklists(com.mckesson.eig.workflow.api.Actor,
     *      long, long, com.mckesson.eig.workflow.api.SortOrder)
     */
    public ListWorklist getAllAssignedWorklists(Actor createdBy,
                                                int startIndex,
                                                int count,
                                                SortOrder sortOrder) {

        final String logSourceMethod =
            "getAllAssignedWorklists(createdBy, startIndex, count, sortOrder)";
        LOG.debug(logSourceMethod + ">>Start");

        ListWorklist listWL;

        try {

            sortOrder = getValidatedSortOrder(sortOrder);
            validateStartandCount(startIndex, count);
            validateActor(createdBy);

            listWL = getWorklistDAO().getAllAssignedWorklists(createdBy,
                                                           startIndex,
                                                           count,
                                                           sortOrder);

            LOG.debug(logSourceMethod + "<<End");
            return listWL;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * This method verifies if the passed criteria is null.
     * @param assignedWLC
     */
    private void validateCriteria(AssignedWLCriteria assignedWLC) {

        if (assignedWLC == null) {

            WorklistException we = new WorklistException(WorklistEC.MSG_NULL_CRITERIA,
                                                         WorklistEC.EC_NULL_CRITERIA);
            LOG.error(we);
            throw we;
        }

        validateActors(assignedWLC.getOwners());
        validateActors(assignedWLC.getAssignedTo());
    }

    /**
     * This method verifies if the passed wrapper of actors is valid or not. This method throws a
     * WorklistException if the passed wrapper is invalid.
     *
     * @param actors
     */
    private void validateActors(Actors actors) {

        if (actors == null) {

            WorklistException we = new WorklistException(WorklistEC.MSG_NULL_ACTORS,
                                                         WorklistEC.EC_NULL_ACTORS);
            LOG.error(we);
            throw we;
        }

        for (Iterator< ? > iterator = actors.getActors().iterator(); iterator.hasNext();) {
            Actor actR = (Actor) iterator.next();
            validateActor(actR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getAssignedWorklistsCount(
     *      com.mckesson.eig.workflow.worklist.api.AssignedWLCriteria)
     */
    public long getAssignedWorklistsCount(AssignedWLCriteria assignedWLC) {

        final String logSourceMethod = "getAssignedWorklistsCount(assignedWLC)";
        LOG.debug(logSourceMethod + ">>Start");

        long count = 0;

        try {

            validateCriteria(assignedWLC);
            count = getWorklistDAO().getAssignedWorklistsCount(assignedWLC);

            LOG.debug(logSourceMethod + "<<End");
            return count;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,  
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
            
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getAllAssignedWorklistsCount(com.mckesson.eig.workflow.api.Actor)
     */
    public long getAllAssignedWorklistsCount(Actor createdBy) {

        final String logSourceMethod = "getAllAssignedWorklistsCount(createdBy)";
        LOG.debug(logSourceMethod + ">>Start");

        long count = 0;

        try {

            validateActor(createdBy);
            count = getWorklistDAO().getAllAssignedWorklistsCount(createdBy);

            LOG.debug(logSourceMethod + "<<End");
            return count;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,  
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #createWorklist(com.mckesson.eig.workflow.worklist.api.Worklist)
     */
    public long createWorklist(Worklist worklist)  {

        final String logSourceMethod = "createWorklist(worklist)";
        LOG.debug(logSourceMethod + ">>Start");

        long worklistID = 0;

        try {

            validateWorklist(worklist, false);

            worklistID = getWorklistDAO().createWorklist(worklist);

            WorkflowAuditManger auditMgr = getWorkflowAuditManager();
            AuditEvent ae =
            auditMgr.prepareSucessFailAuditEvent(getDomainID(worklist.getOwnerActors()),
                                                 getUserID(),
                                                 AE_WORKLIST_CREATE,
                                                 worklist.toAuditComment(),
                                                 AuditEvent.SUCCESS);
            auditMgr.createAuditEntry(ae);

            LOG.debug(logSourceMethod + "<<End");
            return worklistID;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }
    
    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #createNewWorklist(com.mckesson.eig.workflow.worklist.api.Worklist)
     */
    public Worklist createNewWorklist(Worklist worklist)  {

        final String logSourceMethod = "createNewWorklist(worklist)";
        LOG.debug(logSourceMethod + ">>Start");

        try {

            validateWorklist(worklist, false);

            worklist = getWorklistDAO().createNewWorklist(worklist);

            WorkflowAuditManger auditMgr = getWorkflowAuditManager();
            AuditEvent ae =
                auditMgr.prepareSucessFailAuditEvent(getDomainID(worklist.getOwnerActors()),
                                                     getUserID(),
                                                     AE_WORKLIST_CREATE,
                                                     worklist.toAuditComment(),
                                                     AuditEvent.SUCCESS);
            auditMgr.createAuditEntry(ae);

            LOG.debug(logSourceMethod + "<<End");
            return worklist;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {

            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,
                    WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * This method is used to validate the worklist before creating/updating
     * a worklist. The worklist is validated for all the mandatory attributes
     * and maximum allowed length. The existence of the worklist with the same
     * name is checked while creating/updating a worklist.
     *
     * @param worklist
     *          Worklist to be created.
     *
     * @param isUpdate
     *          Denotes an update operation on the Worklist.
     */
    private void validateWorklist(Worklist worklist, boolean isUpdate) {

        if (worklist == null) {

            WorklistException we = new WorklistException(WorklistEC.MSG_NULL_WL,
                                                         WorklistEC.EC_NULL_WL);
            LOG.error(we);
            throw we;
        }

        if (isUpdate) {
            validateWorklistID(worklist.getWorklistID());
        }

        String errorCode = worklist.validate();

        if (errorCode.trim().length() > 0) {

            WorklistException we = new WorklistException(WorklistEC.MSG_INV_WL, errorCode);
            LOG.error(we);
            throw we;
        }
    }

    /**
     * This method validates the worklist ID passed to see if the worklistID
     * is less than or equal to zero.
     *
     * @param worklistID
     *          WorklistID to be verified.
     */
    private void validateWorklistID(long worklistID) {

        if (worklistID < 1) {
            WorklistException we = new WorklistException(WorklistEC.MSG_INVALID_WORKLIST_ID,
                                                         WorklistEC.EC_INVALID_WORKLIST_ID);
            LOG.error(we);
            throw we;
        }

        try {
            getWorklistDAO().get(Worklist.class, worklistID);
        } catch (WorkflowException e) {
            throw new WorklistException(WorklistEC.MSG_WL_NT_AVAILABLE, 
                                        WorklistEC.EC_WL_NT_AVAILABLE);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getWorklist(long)
     */
    public Worklist getWorklist(long worklistID)  {

        final String logSourceMethod = "getWorklist(worklistID)";
        LOG.debug(logSourceMethod + ">>Start");

        Worklist worklist = null;

        try {

            validateWorklistID(worklistID);
            worklist = getWorklistDAO().getWorklist(worklistID, false);

            LOG.debug(logSourceMethod + "<<End");
            return worklist;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR, 
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #updateWorklist(com.mckesson.eig.workflow.worklist.api.Worklist)
     */
    public void updateWorklist(Worklist worklist)  {

        final String logSourceMethod = "updateWorklist(worklist)";
        LOG.debug(logSourceMethod + ">>Start");

        try {

            validateWorklist(worklist, true);

            getWorklistDAO().updateWorklist(worklist);

            WorkflowAuditManger auditMgr = getWorkflowAuditManager();
            AuditEvent ae =
            auditMgr.prepareSucessFailAuditEvent(getDomainID(worklist.getOwnerActors()),
                                                 getUserID(),
                                                 AE_WORKLIST_UPDATE,
                                                 worklist.toAuditComment(),
                                                 AuditEvent.SUCCESS);
            auditMgr.createAuditEntry(ae);

            LOG.debug(logSourceMethod + "<<End");
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #updateExistingWorklist(com.mckesson.eig.workflow.worklist.api.Worklist)
     */
    public Worklist updateExistingWorklist(Worklist worklist)  {

        final String logSourceMethod = "updateExistingWorklist(worklist)";
        LOG.debug(logSourceMethod + ">>Start");

        try {

            validateWorklist(worklist, true);

            worklist = getWorklistDAO().updateWorklist(worklist);

            WorkflowAuditManger auditMgr = getWorkflowAuditManager();
            AuditEvent ae =
                auditMgr.prepareSucessFailAuditEvent(getDomainID(worklist.getOwnerActors()),
                        getUserID(),
                        AE_WORKLIST_UPDATE,
                        worklist.toAuditComment(),
                        AuditEvent.SUCCESS);
            auditMgr.createAuditEntry(ae);

            LOG.debug(logSourceMethod + "<<End");
            return worklist;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {

            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,
                    WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #deleteWorklists(long[])
     */
    public IDListResult deleteWorklists(long[] ids) {

        final String logSourceMethod = "deleteWorklists(ids)";
        LOG.debug(logSourceMethod + ">>Start");

        IDListResult idList = null;

        try {

            validateWorklistIDs(ids, false);

            idList = getWorklistDAO().deleteWorklists(ids);

            WorkflowAuditManger auditMgr = getWorkflowAuditManager();

            List< ? > processed = idList.getProcessed();
            Worklist wl;
            for (int i = processed.size(); --i >= 0;) {

                wl = (Worklist) processed.get(i);

                AuditEvent ae =
                auditMgr.prepareSucessFailAuditEvent(getDomainID(wl.getOwnerActors()),
                                                     getUserID(),
                                                     AE_WORKLIST_DELETE,
                                                     wl.toAuditComment(),
                                                     AuditEvent.SUCCESS);
                auditMgr.createAuditEntry(ae);
            }

            idList.setProcessed(null);
            idList.setProcessedIDs(null);

            LOG.debug(logSourceMethod + "<<End");
            return idList;
        } catch (WorkflowException we) {
            throw we;
        } catch (Exception e) {
            
            LOG.error(e);
            throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,
                                        WorklistEC.EC_OTHER_SERVER_ERROR);
        }
    }

    /**
     * This method validates if the Worklist IDs is null or not. If the ids
     * passed is null a corresponding WorkflowException is thrown.
     *
     * @param worklistIDs
     * @param validateIndividualIDs false to skip validating individual Worklist IDs
     */
    private void validateWorklistIDs(long[] worklistIDs, boolean validateIndividualIDs) {

        if (worklistIDs == null) {

            WorklistException we = new WorklistException(WorklistEC.MSG_NULL_IDS,
                                                         WorklistEC.EC_NULL_WL_IDS);
            LOG.error(we);
            throw we;
        }

        if (!validateIndividualIDs) {
            return;
        }

        for (long id : worklistIDs) {
            validateWorklistID(id);
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getCreatableWorklistsCount(com.mckesson.eig.workflow.worklist.api.CreateWLCriteria)
     */
    public long getCreatableWorklistsCount(CreateWLCriteria createWLCriteria) {

        final String logSourceMethod = "getWorklistsCountWithCreatedTasks(createWLCriteria)";
        LOG.debug(logSourceMethod + ">>Start");

        long count = 0;

        try {

            validateCreateWlCriteria(createWLCriteria);
            count = getWorklistDAO().getCreatableWorklistsCount(createWLCriteria);

            LOG.debug(logSourceMethod + "<<End");
            return count;
         } catch (WorkflowException we) {
             throw we;
         } catch (Exception e) {
             
             LOG.error(e);
             throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,
                                         WorklistEC.EC_OTHER_SERVER_ERROR);
         }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getCreatableWorklists(
     *      com.mckesson.eig.workflow.worklist.api.CreateWLCriteria,
     *      long, long, com.mckesson.eig.workflow.api.SortOrder)
     */
    public ListWorklist getCreatableWorklists(CreateWLCriteria createWLCriteria,
                                              int startIndex,
                                              int count,
                                              SortOrder sortOrder) {

        final String logSourceMethod =
            "getCreatableWorklists(createWLCriteria, startIndex, count, sortOrder)";

        LOG.debug(logSourceMethod + ">>Start");

        ListWorklist listWL = null;

        try {

            sortOrder = getValidatedSortOrder(sortOrder);
            validateStartandCount(startIndex, count);
            validateCreateWlCriteria(createWLCriteria);

            listWL = getWorklistDAO().getCreatableWorklists(createWLCriteria,
                                                            startIndex,
                                                            count,
                                                            sortOrder);

            LOG.debug(logSourceMethod + "<<End");
            return listWL;
         } catch (WorkflowException we) {
             throw we;
         } catch (Exception e) {
             
             LOG.error(e);
             throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,
                                         WorklistEC.EC_OTHER_SERVER_ERROR);
         }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     * #updateWorklistACLs(ListACLs, long[], Actor)
     *
     */
    public void updateWorklistACLs(ListACLs listACL, long[] worklistIDs, Actor assignedTo) {

        final String logSourceMethod = "updateWorklistACLs(listACL, worklistIDs)";
        LOG.debug(logSourceMethod + ">>Start");

        try {

            if (worklistIDs != null) {
                validateWorklistIDs(worklistIDs, true);
            }
            validateActor(assignedTo);
            getWorklistDAO().updateWorklistACLs(listACL, worklistIDs, assignedTo);

            LOG.debug(logSourceMethod + "<<End");
         } catch (WorkflowException we) {
             throw we;
         } catch (Exception e) {
             
             LOG.error(e);
             throw new WorklistException(WorklistEC.MSG_OTHER_SERVER_ERROR,
                                         WorklistEC.EC_OTHER_SERVER_ERROR);
         }
    }
    
    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService#getOwner(long)
     */
    public Actor getOwner(long worklistId) {

        final String logSourceMethod = "getOwner(worklistId)";
        LOG.debug(logSourceMethod + ">>Start");

        if (worklistId < 1) {
            WorklistException we = new WorklistException(WorklistEC.MSG_INVALID_WORKLIST_ID,
                                                         WorklistEC.EC_INVALID_WORKLIST_ID);
            LOG.error(we);
            throw we;
        }

        try {
            Worklist worklist = (Worklist) getWorklistDAO().get(Worklist.class, worklistId);
            return worklist.getOwnerActors().iterator().next();
        } catch (WorkflowException e) {
            throw new WorklistException(WorklistEC.MSG_WL_NT_AVAILABLE, 
                                        WorklistEC.EC_WL_NT_AVAILABLE);
        }
    }

    /**
     * This method verifies if the passed criteria is null.
     * @param assignedWLC
     */
    private void validateCreateWlCriteria(CreateWLCriteria createWLC) {

        if (createWLC == null) {

            WorklistException we = new WorklistException(WorklistEC.MSG_NULL_CRITERIA,
                                                         WorklistEC.EC_NULL_CRITERIA);
            LOG.error(we);
            throw we;
        }

        validateActors(createWLC.getOwners());
        validateActors(createWLC.getCreatedBy());
    }

    /**
     * This method returns the DAO implementation for this business service.
     * @return worklistDAO
     */
    private WorklistDAO getWorklistDAO() {
        return (WorklistDAO) getDAO(WORKLIST_DAO_ID);
    }

}
