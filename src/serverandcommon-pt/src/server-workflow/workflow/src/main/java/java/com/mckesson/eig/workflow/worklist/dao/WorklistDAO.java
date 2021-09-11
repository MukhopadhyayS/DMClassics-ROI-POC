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
package com.mckesson.eig.workflow.worklist.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mckesson.dm.core.common.logging.OCLogger;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateCallback;

import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.IDListResult;
import com.mckesson.eig.workflow.api.SortOrder;
import com.mckesson.eig.workflow.api.WorkflowException;
import com.mckesson.eig.workflow.dao.AbstractWorkflowDAO;
import com.mckesson.eig.workflow.worklist.api.AssignedWLCriteria;
import com.mckesson.eig.workflow.worklist.api.CreateWLCriteria;
import com.mckesson.eig.workflow.worklist.api.ListACLs;
import com.mckesson.eig.workflow.worklist.api.ListWorklist;
import com.mckesson.eig.workflow.worklist.api.StatusCountPair;
import com.mckesson.eig.workflow.worklist.api.TaskACL;
import com.mckesson.eig.workflow.worklist.api.TaskACLResolved;
import com.mckesson.eig.workflow.worklist.api.TaskACLs;
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.api.WorklistEC;
import com.mckesson.eig.workflow.worklist.api.WorklistException;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author Pranav Amarasekaran
 * @author Ghazni
 * @date   Dec 6, 2007
 * @modified Nov 12, 2010 
 * @since  HECM 1.0
 *
 * This class defines the business methods the business service
 * implementation will invoke in order to view and manage
 * worklists.
 */
public class WorklistDAO
extends AbstractWorkflowDAO {

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( WorklistDAO.class);

    private static final String T_OWNER_ID    = "T_OWNER_ID";
    private static final String T_ACTOR_ID    = "T_ACTOR_ID";
    private static final String T_WORKLIST_ID = "T_WORKLIST_ID_";
    private static final String T_CREATED_BY  = "T_CREATED_BY";
    private static final String W_ACTORID     = "W_ACTOR_ID";
    private static final String KEY_ACTOR     = "Key_Actor";
    private static final String KEY_ACTORS    = "Actors";

    private static final byte TYPE_USER       = 3;

    private static final String ASCENDING     = " asc";
    private static final String DESCENDING    = " desc";

    /**
     * This constructor instantates this implementation of the DAO.
     */
    public WorklistDAO() {
        super();
    }

    /**
     * This method loads all the actors associated to the worklist. The actors
     * which are owning the worklist and the actors to whom the worklist is
     * assigned are also loaded. If the actor is not available in the system
     * then a new actor is created with the specified details and associated
     * to the worklist.
     *
     * @param worklist
     *          Worklist whose associated actors that are to be loaded.
     */
    protected void loadActors(Worklist worklist) {

        if (worklist == null) {
            return;
        }

        Set actors = worklist.getOwnerActors();
        if (actors != null) {

            HashSet<Actor> loadedActors = new HashSet<Actor>(actors.size());
            for (Iterator i = actors.iterator(); i.hasNext();) {
                Actor actor = (Actor) i.next();
                loadedActors.add(loadActor(actor));
            }
            worklist.setOwnerActors(loadedActors);
        }

        if (worklist.getAcls() == null) {
            /* 
             * Need to delete all the acls if available, and hibernate does not accept
             * if its value is null, rather it works if an new instance of TaskACL has been passed. 
             */
            worklist.setAcls(new HashSet<TaskACL>());
            return;
        }

        for (Iterator i = worklist.getAcls().iterator(); i.hasNext();) {
            TaskACL acl = (TaskACL) i.next();
            acl.setActor(loadActor(acl.getActor()));
        }
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService#
     *      createWorklist(com.mckesson.eig.workflow.worklist.api.Worklist)
     */
    public long createWorklist(final Worklist worklist) {

        final String logSourceMethod = "createWorklist(worklist)";
        LOG.debug(logSourceMethod + ">>Start");

        loadActors(worklist);
        validateWorklist(worklist);
        long id = (Long) create(worklist);

        LOG.debug(logSourceMethod + "<<End");
        return id;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService#
     *      createNewWorklist(com.mckesson.eig.workflow.worklist.api.Worklist)
     */
    public Worklist createNewWorklist(final Worklist worklist) {

        final String logSourceMethod = "createNewWorklist(worklist)";
        LOG.debug(logSourceMethod + ">>Start");

        loadActors(worklist);
        validateWorklist(worklist);
        create(worklist);

        LOG.debug(logSourceMethod + "<<End");
        return worklist;
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
    private void validateWorklist(Worklist worklist) {

        if (worklistNameExists(worklist)) {

            WorklistException we =
                new WorklistException(WorklistEC.MSG_WORKLIST_EXISTS,
                                      WorklistEC.EC_WORKLIST_EXISTS);
            LOG.error(we);
            throw we;
        }
    }

    /**
     * This method is used to verify if the worklist with the same name is
     * already owned by the owners that are associated to this worklist.
     *
     * @param worklist
     *          Worklist which has to be verified.
     *
     * @return isExixting
     *          true if worklist with the name exists.
     */
    private boolean worklistNameExists(final Worklist worklist) {

        return ((Integer) getHibernateTemplate().execute(
          new HibernateCallback() {
                public Object doInHibernate(Session s) {

                    Long[] ownerIDs = worklist.getOwners().getActorIDs();

                    return s.createCriteria(Worklist.class)
                            .setProjection(Projections.rowCount())
                            .add(Restrictions.ne("worklistID", worklist.getWorklistID()))
                            .add(Restrictions.eq("name", worklist.getName()).ignoreCase())
                            .createCriteria("ownerActors")
                            .add(Restrictions.in("actorID", ownerIDs))
                            .uniqueResult();
                }
        })).intValue() > 0;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getWorklist(long)
     */
    public Worklist getWorklist(final long worklistID, boolean fetchAllWLInfo) {

        final String logSourceMethod = "getWorklist(worklistID)";
        LOG.debug(logSourceMethod + ">>Start");

        Worklist worklist = (Worklist) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                return s.createCriteria(Worklist.class)
                        .setFetchMode("ownerActors", FetchMode.JOIN)
                        .setFetchMode("acls",        FetchMode.JOIN)
                        .setFetchMode("acls.actor",  FetchMode.JOIN)
                        .add(Restrictions.idEq(worklistID))
                        .uniqueResult();
            }
        });

        if (worklist == null) {

            WorklistException we = new WorklistException(WorklistEC.MSG_WL_NT_AVAILABLE,
                                                         WorklistEC.EC_WL_NT_AVAILABLE);
            LOG.error(we);
            throw we;
        }

        LOG.debug(logSourceMethod + "<<End");
        if (fetchAllWLInfo) {
            return worklist;
        }
        return fetchWorklist(worklist);
    }

    private Worklist fetchWorklist(Worklist worklist) {

        Worklist wl = new Worklist();
        
        wl.setWorklistID(worklist.getWorklistID());
        wl.setName(worklist.getName());
        wl.setDesc(worklist.getDesc());
        wl.setTotalTaskCount(worklist.getTotalTaskCount());
        wl.setOwners(worklist.getOwners());
        wl.setAssignedTo(worklist.getAssignedTo());
        wl.setVersion(worklist.getVersion());
        return wl;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     * #updateWorklist(com.mckesson.eig.workflow.worklist.api.Worklist)
     */
    public Worklist updateWorklist(Worklist worklist) {

        final String logSourceMethod = "updateWorklist(worklist)";
        LOG.debug(logSourceMethod + ">>Start");

        loadActors(worklist);
        validateWorklist(worklist);

        LOG.debug(logSourceMethod + "<<End");
        return (Worklist) merge(worklist);
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService#deleteWorklists(long[])
     */
    public IDListResult deleteWorklists(long[] worklistIDs) {

        final String logSourceMethod = "deleteWorklists(worklistIDs)";
        LOG.debug(logSourceMethod + ">>Start");

        ArrayList<Worklist> deleted    = new ArrayList<Worklist>();
        ArrayList<Long> deletedIDs     = new ArrayList<Long>();
        ArrayList<Worklist> undeleted  = new ArrayList<Worklist>();
        ArrayList<Long> undeletedIds   = new ArrayList<Long>();
        ArrayList<String> errorCodes   = new ArrayList<String>();

        Worklist wl = null;
        for (Long worklistID : worklistIDs) {

            try {

                wl = getWorklist(worklistID, true);
                
                if (isTasksAvaialable(wl.getWorklistID())) {
                    
                    undeleted.add(fetchWorklist(wl));
                    undeletedIds.add(wl.getWorklistID());
                    errorCodes.add(WorklistEC.EC_TASK_FOUND);
                    continue;
                }
                super.delete(wl);
                deleted.add(fetchWorklist(wl));
                wl.getAcls().clear();
                deletedIDs.add(worklistID);
            } catch (Exception e) {

                LOG.error(e);
                if (wl != null) {
                    undeleted.add(fetchWorklist(wl));
                }
                undeletedIds.add(worklistID);
                errorCodes.add(e instanceof WorkflowException
                           ?  ((WorkflowException) e).getErrorCode()
                           :    e.getMessage());
            }
        }

        IDListResult result = new IDListResult();

        result.setProcessed(deleted);
        result.setProcessedIDs(deletedIDs);
        result.setFailed(undeleted);
        result.setFailedIDs(undeletedIds);
        result.setErrorCodes(errorCodes);

        removeTaskAclByWorklistID(result.getProcessedIDs().toArray());

        LOG.debug(logSourceMethod + "<<End");
        return result;
    }
    
    /**
     * Validates whether tasks are available in a particular worklist.
     * 
     * @param worklistID
     *        unique ID of the worklist.
     *          
     * @return isTasksAvaiable
     *         returns true if and only tasks are available otherwise false. 
     */
    private boolean isTasksAvaialable(long worklistID) {
        
        return (((Long) getHibernateTemplate().findByNamedQuery("retreiveTasksCount", 
                                                                String.valueOf(worklistID))
                                      .get(0)) > 0);
    }
    
    /**
     * 
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *      #getOwnedWorklists(com.mckesson.eig.workflow.api.Actor)
     */
    public ListWorklist getOwnedWorklists(Actor owner) {

        final String logSourceMethod = "getOwnedWorklists(owner)";
        LOG.debug(logSourceMethod + ">>Start");

        final Actor lOwner    = loadActor(owner);
        List ownedWorklistIDs = getHibernateTemplate()
                                     .findByNamedQuery("retrieveOwnedWorklists",
                                                        new Long(lOwner.getActorID()));

        LOG.debug(logSourceMethod + "Owned Worklists Size = " + ownedWorklistIDs.size() +  "<<End");
        return new ListWorklist(fetchOwnedWorklistsIDs(ownedWorklistIDs));
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getWorklistsCount(com.mckesson.eig.workflow.api.Actor)
     */
    public long getWorklistsCount(Actor owner) {

        final String logSourceMethod = "getWorklistsCount(owner)";
        LOG.debug(logSourceMethod + ">>Start");

        final Actor lOwner = loadActor(owner);

        long count = (Long) getHibernateTemplate()
                              .findByNamedQuery("retrieveWorklistsCount",
                                                new Long(lOwner.getActorID()))
                              .get(0);

        LOG.debug(logSourceMethod + "<<End");
        return count;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getAllWorklistsCount()
     */
    public long getAllWorklistsCount() {

        final String logSourceMethod = "getAllWorklistsCount()";
        LOG.debug(logSourceMethod + ">>Start");

        final StringBuffer hqlQuery =
            new StringBuffer()
                .append(" SELECT count(w) FROM                                       \n ")
                .append(" com.mckesson.eig.workflow.worklist.api.Worklist AS w       \n ")
                .append(" left outer join w.ownerActors as ownerActors               \n ")
                .append(" WHERE ownerActors.entityType not in (                      \n ")
                .append(TYPE_USER + " )");

        long count = ((Long) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                return s.createQuery(hqlQuery.toString())
                        .uniqueResult();
            }
        })).longValue();

        LOG.debug(logSourceMethod + "<<End");
        return count;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getWorklists(com.mckesson.eig.workflow.api.Actor,
     *                long, long, com.mckesson.eig.workflow.api.SortOrder)
     */
    public ListWorklist getWorklists(Actor owner,
                                     final int startIndex,
                                     final int count,
                                     final SortOrder sortOrder) {

        final String logSourceMethod = "getWorklists(owner, startIndex, count, sortOrder)";
        LOG.debug(logSourceMethod + ">>Start");

        final Actor lOwner            = loadActor(owner);
        final StringBuffer hqlQuery =
            new StringBuffer()
                .append(" SELECT w.id, w.name, w.desc, COUNT(pa.actorId), w.version  \n ")
                .append(" FROM com.mckesson.eig.workflow.worklist.api.Worklist AS w  \n ")
                .append(" INNER      JOIN w.ownerActors    AS owners                 \n ")
                .append(" LEFT OUTER JOIN w.tasks          AS pa                     \n ")
                .append(" LEFT OUTER JOIN pa.taskInstances AS ti                     \n ")
                .append(" WHERE owners.actorID = :T_OWNER_ID                         \n ")
                .append(" GROUP BY pa.actorId, w.id, w.name, w.desc, w.version ")
                .append(" ORDER BY upper(w.")
                .append(sortOrder.getAttr().getName() + ")");

        if (sortOrder != null) {
            hqlQuery.append(sortOrder.getIsDesc() ? DESCENDING : ASCENDING);
        }

        List worklists = (List) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                List list = s.createQuery(hqlQuery.toString())
                             .setLong(T_OWNER_ID, lOwner.getActorID())
                             .setFirstResult(startIndex)
                             .setMaxResults(count)
                             .list();

                return fetchWorklistWithTaskCount(list);
            }
        });

        ListWorklist listWorklist = new ListWorklist();
        listWorklist.setWorklists(worklists);

        LOG.debug(logSourceMethod + "<<End");
        return listWorklist;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getAllWorklists(long, long, com.mckesson.eig.workflow.api.SortOrder)
     */
    public ListWorklist getAllWorklists(final int startIndex,
                                        final int count,
                                        final SortOrder sortOrder) {

        final String logSourceMethod = "getAllWorklists(startIndex, count, sortOrder)";
        LOG.debug(logSourceMethod + ">>Start");

        final StringBuffer hqlQuery =
            new StringBuffer()
                .append(" SELECT wl.id, wl.name, wl.desc                             \n ")
                .append(" FROM com.mckesson.eig.workflow.worklist.api.Worklist AS wl \n ")
                .append(" left outer join wl.ownerActors as ownerActors              \n ")
                .append(" WHERE ownerActors.entityType not in (                      \n ")
                .append(TYPE_USER + ") ORDER BY upper(wl.")
                .append(sortOrder.getAttr().getName() + ")");

        if (sortOrder != null) {
            hqlQuery.append(sortOrder.getIsDesc() ? DESCENDING : ASCENDING);
        }

        List worklists = (List) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                List list = s.createQuery(hqlQuery.toString())
                             .setFirstResult(startIndex)
                             .setMaxResults(count)
                             .list();

                return fetchWorklists(list);
            }
        });

        LOG.debug(logSourceMethod + "<<End");
        return new ListWorklist(worklists);
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #resolveTaskAclsByActors(actors)
     */
    @SuppressWarnings("unchecked")
    public void resolveTaskAclsByActors() {

        final String logSourceMethod = "resolveTaskAclsByActors(actors)";
        LOG.debug(logSourceMethod + ">>Start");

        final String sessionId = WsSession.getSessionId();
        final Object[] actorIDs = getActorIDs();

        List result = (List) getHibernateTemplate().execute(

            new HibernateCallback() {

                public Object doInHibernate(Session s) {

                  Query query = s.getNamedQuery("resolveTaskAcls");
                  //query.setParameter("SESSION_ID", sessionId);
                  query.setParameterList("ACTOR_IDS", actorIDs);
                  //query.setResultTransformer(Transformers.aliasToBean(TaskACLResolved.class));
                  return query.list();
                }
            }
         );

        if (CollectionUtilities.hasContent(result)) {

            List<TaskACLResolved> resolvedList = fetchTaskAclResolved(sessionId, result);
            for(TaskACLResolved tar : resolvedList) {
                getHibernateTemplate().saveOrUpdate(tar);
            }
            //getHibernateTemplate().saveOrUpdateAll(result);
        }
        LOG.debug(logSourceMethod + "<<End");
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getAssignedWorklistsCount(
     *      com.mckesson.eig.workflow.worklist.api.AssignedWLCriteria)
     */
    public long getAssignedWorklistsCount(AssignedWLCriteria assignedWLC) {

        final String logSourceMethod = "getAssignedWorklistsCount(assignedWLC)";
        LOG.debug(logSourceMethod + ">>Start");
        
        final StringBuffer hqlQuery =
            new StringBuffer()
                .append(" SELECT COUNT (DISTINCT w) FROM                       \n ")
                .append(" com.mckesson.eig.workflow.worklist.api.Worklist AS w \n ")
                .append(" INNER JOIN w.ownerActors AS owners                   \n ");

        if (!assignedWLC.getShowEmptyWorklists()) {
            hqlQuery.append("INNER JOIN w.tasks AS pa ");
        }

        hqlQuery.append(" WHERE owners.actorID IN (:T_OWNER_ID) AND    \n ")
                .append("(  w.worklistID IN (")
                .append(" SELECT worklistID FROM com.mckesson.eig.workflow.worklist.api.TaskACLResolved WHERE sessionID='")
                .append(WsSession.getSessionId())
                .append("'))");

        Actors owners     = assignedWLC.getOwners();
        Actors assignedTo = assignedWLC.getAssignedTo();

        loadActors(owners);
        loadActors(assignedTo);

        final Long[] ownerIDs  = owners.getActorIDs();

        long count = ((Long) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                Query query = s.createQuery(hqlQuery.toString())
                               .setParameterList(T_OWNER_ID, ownerIDs);

                return query.uniqueResult();
            }
        })).longValue();

        LOG.debug(logSourceMethod + "<<End");
        return count;
    }
    
    private List<List<Long>> getChunkedWorklistIDs(List<Long> worklistIDs) {
        
        List<List<Long>> chunkedWorklistIDs  = new ArrayList<List<Long>>();
        
        int chunkedCount = 0;
        final int defaultSize = 998;
        List<Long> chunkedPiece = new ArrayList<Long>();
        for (int i = 0; i < worklistIDs.size(); i++) {
            
            chunkedPiece.add(worklistIDs.get(i));
            chunkedCount += 1;
            if (chunkedCount == defaultSize) {
                
                chunkedWorklistIDs.add(chunkedPiece);
                chunkedCount = 0;
                chunkedPiece = new ArrayList<Long>();
            }
        }
        if (!chunkedPiece.isEmpty()) {
            chunkedWorklistIDs.add(chunkedPiece);
        }
        return chunkedWorklistIDs;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getAllAssignedWorklistsCount(com.mckesson.eig.workflow.api.Actor)
     */
    public long getAllAssignedWorklistsCount(final Actor createdBy) {

        final String logSourceMethod = "getAllAssignedWorklistsCount(createdBy)";
        LOG.debug(logSourceMethod + ">>Start");

        final StringBuffer hqlQuery =
            new StringBuffer()
                .append(" SELECT COUNT (DISTINCT w) FROM                       \n ")
                .append(" com.mckesson.eig.workflow.worklist.api.Worklist AS w \n ")
                .append(" INNER JOIN w.acls AS acl                             \n ")
                .append(" WHERE acl.actor.actorID IN (:T_ACTOR_ID)             \n ");

        final Actor actor = loadActor(createdBy);

        long count = ((Long) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                return s.createQuery(hqlQuery.toString())
                        .setLong(T_ACTOR_ID, actor.getActorID())
                        .uniqueResult();
            }
        })).longValue();

        LOG.debug(logSourceMethod + "<<End");
        return count;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getAssignedWorklists(
     *      com.mckesson.eig.workflow.worklist.api.AssignedWLCriteria,
     *      long, long, com.mckesson.eig.workflow.api.SortOrder)
     */
    public ListWorklist getAssignedWorklists(final AssignedWLCriteria assignedWLC,
                                             final int startIndex,
                                             final int count,
                                             final SortOrder sortOrder) {

        final String logSourceMethod =
            "getAssignedWorklists(assignedWLC, startIndex, count, sortOrder)";
        LOG.debug(logSourceMethod + ">>Start");

        final StringBuffer getWorklistQuery =
            new StringBuffer()
            .append(" SELECT DISTINCT(w) FROM                              \n ")
            .append(" com.mckesson.eig.workflow.worklist.api.Worklist AS w \n ")
            .append(" INNER JOIN w.ownerActors AS owners                   \n ");

        if (!assignedWLC.getShowEmptyWorklists()) {
            getWorklistQuery.append("INNER JOIN w.tasks AS pa ");
        }

        getWorklistQuery.append(" WHERE owners.actorID IN (:T_OWNER_ID) AND \n ")
                        .append("( w.worklistID IN (")
                        .append(" SELECT worklistID FROM com.mckesson.eig.workflow.worklist.api.TaskACLResolved WHERE sessionID='")
                        .append(WsSession.getSessionId())
                        .append("'))");

        if (sortOrder != null) {

            getWorklistQuery.append(" ORDER BY upper(w.")
                            .append(sortOrder.getAttr().getName() + ")")
                            .append(sortOrder.getIsDesc() ? DESCENDING : ASCENDING);
        }
        
        Actors owners = assignedWLC.getOwners();
        loadActors(owners);

        final Long[] ownerIDs  = owners.getActorIDs();

        List worklists = (List) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                Query query = s.createQuery(getWorklistQuery.toString())
                               .setParameterList(T_OWNER_ID, ownerIDs)
                               .setFirstResult(startIndex)
                               .setMaxResults(count);
                
                List list = query.list();
                if (list.size() != 0) {
                    getAssignedWorklistsTasks(list, assignedWLC.getShowOnlyOwnerWL());
                }
                return fetchWorklistWithStatusCount(list);
            }
        });

        LOG.debug(logSourceMethod + "<<End");
        return new ListWorklist(worklists);
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getAllAssignedWorklists(
     *      com.mckesson.eig.workflow.api.Actor,
     *      long, long, com.mckesson.eig.workflow.api.SortOrder)
     */
    public ListWorklist getAllAssignedWorklists(final Actor createdBy,
                                                final int startIndex,
                                                final int count,
                                                final SortOrder sortOrder) {

        final String logSourceMethod =
            "getAllAssignedWorklists(createdBy, startIndex, count, sortOrder)";
        LOG.debug(logSourceMethod + ">>Start");

        final StringBuffer getWorklistQuery =
            new StringBuffer()
            .append(" SELECT DISTINCT(w) FROM                               \n ")
            .append(" com.mckesson.eig.workflow.worklist.api.Worklist AS w  \n ")
            .append(" INNER JOIN w.acls AS acl WHERE                        \n ")
            .append(" acl.actor.actorID in (:T_ACTOR_ID)                    \n ")
            .append(" ORDER BY w.")
            .append(sortOrder.getAttr().getName())
            .append(sortOrder.getIsDesc() ? DESCENDING : ASCENDING);

        final Actor actor = loadActor(createdBy);

        List worklists = (List) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                List list = s.createQuery(getWorklistQuery.toString())
                             .setLong(T_ACTOR_ID, actor.getActorID())
                             .setFirstResult(startIndex)
                             .setMaxResults(count)
                             .list();

                return fetchAssignedWorklist(list);
            }
        });

        LOG.debug(logSourceMethod + "<<End");
        return new ListWorklist(worklists);
    }

    /**
     * get the set of worklist with the corresponding status count
     *
     * @param list
     *           list of worklist without status count.
     * @return worklistsWithStatusCount
     *           list of worklist with status count.
     */
    @SuppressWarnings("unchecked")
    private List getAssignedWorklistsTasks(List list, final boolean showOnlyOwnerWL) {

        final int worklistSize  = list.size();
        final int statusMapSize = 5;
        List<Long> worklistIDs = new ArrayList<Long>(worklistSize);
        final Long[] ids         = new Long[worklistSize];
        Map<Long, Worklist> wl  = new HashMap<Long, Worklist>(worklistSize);

        Worklist worklist  = null;
        for (int i = 0; i < worklistSize; i++) {

            worklist = (Worklist) list.get(i);
            ids[i] = worklist.getWorklistID();
            worklistIDs.add(worklist.getWorklistID());
            wl.put(ids[i], worklist);
            worklist.setStatusMap(new HashMap<String, Long>(statusMapSize));
        }
        
        final List<List<Long>> chunkedWorklistIDs = getChunkedWorklistIDs(worklistIDs); 

        final StringBuffer hqlQuery =
            new StringBuffer()
               .append(" SELECT worklist.id, n.name, count(n.name) FROM              \n ")
               .append(" com.mckesson.eig.workflow.worklist.api.Worklist AS worklist \n ")
               .append(" LEFT OUTER JOIN worklist.tasks   AS pa                      \n ")
               .append(" LEFT OUTER JOIN pa.taskInstances AS ti                      \n ")
               .append(" INNER      JOIN ti.token         AS t                       \n ")
               .append(" INNER      JOIN t.node           AS n                       \n ")
               .append(" WHERE (                                                     \n ");
        
        for (int i = 0; i < chunkedWorklistIDs.size(); i++) {
            
            if (i == 0) {
                hqlQuery.append(" worklist.id IN (:T_WORKLIST_ID_").append(i).append(")");
            } else {
                hqlQuery.append(" OR worklist.id IN (:T_WORKLIST_ID_").append(i).append(")");
            }
        }
        hqlQuery.append(")");

        if (showOnlyOwnerWL) {
        	 hqlQuery.append(" AND ti.actorId = :W_ACTOR_ID                          \n");
        }
        hqlQuery.append(" GROUP BY n.name, worklist.id                               \n ");

        final String strActorID = worklist.getOwnerActors().iterator().next().toString();
        List worklistsWithStatusCount = (List) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                Query query = s.createQuery(hqlQuery.toString());
                for (int i = 0; i < chunkedWorklistIDs.size(); i++) {
                    
                    query.setParameterList(T_WORKLIST_ID + i, chunkedWorklistIDs.get(i));
                }
                if (showOnlyOwnerWL) {
                	query.setParameter(W_ACTORID, strActorID);
                }
                return query.list();
            }
        });

        for (int i = 0; i < worklistsWithStatusCount.size(); i++) {

            Object[] wList = (Object[]) worklistsWithStatusCount.get(i);

            if (wl.containsKey(wList[0])) {

                Worklist workList = wl.get(wList[0]);
                workList.getStatusMap().put((String) wList[1], (Long) wList[2]);
            }
        }
        return worklistsWithStatusCount;
    }

    /**
     * set the worklist only with the id, name, desc, status and total count
     *
     * @param list
     *           list of worklist containing all the data corresponding to worklist
     * @return
     *           list of worklist containing only id, name, desc,
     *           status and total count.
     */
    private List fetchWorklistWithStatusCount(List list) {

        int size = list.size();
        long totalTasksCount = 0;

        List<Worklist> retList = new ArrayList<Worklist>(size);
        Worklist worklist;
        //fectch task acl map for current user from task acl table
        HashMap taskAclsMap     = getTaskACLSMapForUser();
        TaskACLs taskACLs;
        Set<TaskACL> taskAclSet;

        for (int i = 0; i < size; i++) {

            worklist = new Worklist();

            Worklist wl = (Worklist) list.get(i);

            totalTasksCount = 0;
            worklist.setWorklistID(wl.getWorklistID());
            worklist.setName(wl.getName());
            worklist.setDesc(wl.getDesc());
            worklist.setStatusMap(wl.getStatusMap());
            worklist.setStatusList(new ArrayList<StatusCountPair>(worklist.getStatusMap().size()));
            
            taskACLs   = new TaskACLs();
            taskAclSet = new HashSet<TaskACL>();
            taskAclSet.add((TaskACL) taskAclsMap.get(wl.getWorklistID()));
            taskACLs.setACLs(taskAclSet);
            
            worklist.setAssignedTo(taskACLs);

            for (String key : wl.getStatusMap().keySet()) {
                totalTasksCount = totalTasksCount + wl.getStatusMap().get(key);
            }

            worklist.setTotalTaskCount(totalTasksCount);
            retList.add(worklist);
        }
        return retList;
    }

    /**
     * set the worklist only with the id, name, desc, and taskAcls.
     *
     * @param list
     *           list of worklist containing all the data corresponding to worklist.
     * @return
     *           list of worklist containing only id, name, desc and taskAcls.
     */
    private List fetchAssignedWorklist(List list) {

        int size = list.size();

        List<Worklist> retList = new ArrayList<Worklist>(size);
        Worklist worklist;

        for (int i = 0; i < size; i++) {

            worklist    = new Worklist();
            Worklist wl = (Worklist) list.get(i);

            worklist.setWorklistID(wl.getWorklistID());
            worklist.setName(wl.getName());
            worklist.setDesc(wl.getDesc());
            worklist.setAssignedTo(fetchTaskAcls(wl.getAssignedTo()));
            worklist.setVersion(wl.getVersion());
            retList.add(worklist);
        }
        return retList;
    }

    /**
     * set the worklist only with the id, name, desc.
     *
     * @param list
     *           list of worklist containing all the data corresponding to worklist.
     * @return
     *           list of worklist containing only id, name and desc.
     */
    private List fetchWorklists(List list) {

        int size = list.size();

        List<Worklist> retList = new ArrayList<Worklist>(size);
        Worklist worklist;

        for (int i = 0; i < size; i++) {

            worklist = new Worklist();

            Object[] object = (Object[]) list.get(i);

            worklist.setWorklistID((Long) object[0]);
            worklist.setName((String) object[1]);
            worklist.setDesc((String) object[2]);

            retList.add(worklist);
        }
        return retList;
    }

    /**
     * set the list of worklistID and the taskACL for the corresponding actor into a set.
     *
     * @param list
     *        list of worklistID and taskACL.
     *
     * @return taskACLS
     *         set of taskACL
     */
    private HashMap<Long, TaskACL> fetchTaskAcls(List list) {

        TaskACL taskACL;
        TaskACL acl;
        Object[] objects;
        Worklist worklist;
        
        HashMap<Long, TaskACL> taskACLS = new HashMap<Long, TaskACL>();
        
        for (int i = list.size(); --i >= 0;) {
            
            objects = (Object[]) list.get(i);
            acl = (TaskACL) objects[0];
            Long worklistID = (Long) objects [1];
            
            if (taskACLS.containsKey(worklistID)) {
                
                TaskACL taskPrivilege = taskACLS.get(worklistID);
                if (!taskPrivilege.getCanComplete()) {
                    taskPrivilege.setCanComplete(acl.getCanComplete());
                }
                if (!taskPrivilege.getCanCreate()) {
                    taskPrivilege.setCanCreate(acl.getCanCreate());
                }
                if (!taskPrivilege.getCanDelete()) {
                    taskPrivilege.setCanDelete(acl.getCanDelete());
                }
                if (!taskPrivilege.getCanReassign()) {
                    taskPrivilege.setCanReassign(acl.getCanReassign());
                }
                if (!taskPrivilege.getDoEmailAlert()) {
                	taskPrivilege.setDoEmailAlert(acl.getDoEmailAlert());
                }
            } else {
                
                worklist = new Worklist();
                taskACL  = new TaskACL();
                
                worklist.setWorklistID(worklistID);
                taskACL.setTaskACLID(-1);
                taskACL.setHasMerged(true);
                taskACL.setCanComplete(acl.getCanComplete());
                taskACL.setCanCreate(acl.getCanCreate());
                taskACL.setCanDelete(acl.getCanDelete());
                taskACL.setCanReassign(acl.getCanReassign());
                taskACL.setDoEmailAlert(acl.getDoEmailAlert());
                taskACL.setWorklist(worklist);
                
                taskACLS.put(worklistID, taskACL);
            }
        }
        return taskACLS;
    }
    
    /**
     * fetch the taskACL from the TaskAcls and set the taskACL in the new TaskAcls Instance
     *
     * @param assignedWorklistAcls
     *        TaskAcls containing taskAcl.
     *
     * @return taskAcls
     *         TaskAcls containing taskAcl which holds the canCreate, canDelete, canReassign
     *         and canComplete values.
     *
     */
    private TaskACLs fetchTaskAcls(TaskACLs assignedWorklistAcls) {

        TaskACLs taskACLs = new TaskACLs();
        Set<TaskACL> taskAclSet = new HashSet<TaskACL>();
        TaskACL acl;
        Actor actor;

        for (Iterator i = assignedWorklistAcls.getACLs().iterator(); i.hasNext();) {

            TaskACL taskACL = (TaskACL) i.next();

            actor = new Actor();
            acl = new TaskACL();

            actor.setAppID(taskACL.getActor().getAppID());
            actor.setEntityType(taskACL.getActor().getEntityType());
            actor.setEntityID(taskACL.getActor().getEntityID());

            acl.setActor(actor);
            acl.setTaskACLID(taskACL.getTaskACLID());
            acl.setCanComplete(taskACL.getCanComplete());
            acl.setCanCreate(taskACL.getCanCreate());
            acl.setCanDelete(taskACL.getCanDelete());
            acl.setCanReassign(taskACL.getCanReassign());
            acl.setVersion(taskACL.getVersion());
            acl.setDoEmailAlert(taskACL.getDoEmailAlert());
            taskAclSet.add(acl);
        }

        taskACLs.setACLs(taskAclSet);
        return taskACLs;
    }
    
    /**
     * fetch the set of owned worklists.
     * 
     * @param ownedWorklists 
     *        list of Owned worklists.
     *             
     * @return ownedWorklistIDs
     *         list of owned worklists with id and name. 
     */
    private List<Worklist> fetchOwnedWorklistsIDs(List ownedWorklists) {
        
        List<Worklist> worklists = new ArrayList<Worklist>();
        Worklist worklist;
        
        for (int i = ownedWorklists.size(); --i >= 0;) {
            
            worklist = new Worklist();
            Object[] object = (Object[]) ownedWorklists.get(i);
            worklist.setWorklistID((Long) object[0]);
            worklist.setName((String) object[1]);
            worklists.add(worklist);
        }
        return worklists;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getCreatableWorklistsCount(com.mckesson.eig.workflow.worklist.api.CreateWLCriteria)
     */
    public long getCreatableWorklistsCount(final CreateWLCriteria createWLCriteria) {

        final String logSourceMethod = "getCreatableWorklistsCount(createWLCriteria)";
        LOG.debug(logSourceMethod + ">>Start");

        Actors owners     = createWLCriteria.getOwners();
        Actors lCreatedBy = createWLCriteria.getCreatedBy();

        loadActors(owners);
        loadActors(lCreatedBy);

        final Long[] ownerIDs        = owners.getActorIDs();
        final Long[] assignedTo      = lCreatedBy.getActorIDs();
        final String[] createdBy     = lCreatedBy.getAllActorDetails();
        
        final StringBuffer hqlQuery =
            new StringBuffer()
                .append(" SELECT COUNT(DISTINCT w.id) FROM                     \n ")
                .append(" com.mckesson.eig.workflow.worklist.api.Worklist AS w \n ")
                .append(" INNER JOIN w.ownerActors AS owners                   \n ")
                .append(" LEFT OUTER JOIN w.acls AS acls                       \n ")
                .append(" WHERE (w.worklistID IN (                             \n ");

        StringBuffer getTasksQuery =
            new StringBuffer()
                .append(" SELECT pa.actorId FROM                  \n ")
                .append(" org.jbpm.taskmgmt.exe.PooledActor AS pa \n ")
                .append(" INNER JOIN pa.taskInstances AS ti WHERE \n ")
                .append(" ti.actorId IN (:T_CREATED_BY))          \n ");

        StringBuffer accessQuery = new StringBuffer()
            .append(" OR (")
            .append("  w.worklistID IN (")
            .append(" SELECT worklistID FROM com.mckesson.eig.workflow.worklist.api.TaskACLResolved WHERE sessionID='")
            .append(WsSession.getSessionId())
            .append("')))");

        hqlQuery.append(createWLCriteria.getShowEmptyWorklists()
                                        ? getTasksQuery.append(accessQuery)
                                        : getTasksQuery.append(")"));

        hqlQuery.append(" AND (owners.actorID IN (:T_OWNER_ID)          \n ")
                .append(" OR acls.actor.actorID = owners.actorID) AND  \n ") 
                .append(" owners.actorID <> (:T_ACTOR_ID)              \n ");
        
        long count = ((Long) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                return s.createQuery(hqlQuery.toString())
                        .setParameterList("T_CREATED_BY", createdBy)
                        .setParameterList(T_OWNER_ID, ownerIDs)
                        .setParameterList(T_ACTOR_ID, assignedTo)
                        .uniqueResult();
            }
        })).longValue();

        LOG.debug(logSourceMethod + "<<End");
        return count;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #getCreatableWorklists(CreateWLCriteria, long, long, SortOrder)
     */
    public ListWorklist getCreatableWorklists(final CreateWLCriteria createWLCriteria,
                                              final int startIndex,
                                              final int count,
                                              SortOrder sortOrder) {

        final String logSourceMethod =
            "getCreatableWorklists(createWLCriteria, startIndex, count, sortOrder)";
        LOG.debug(logSourceMethod + ">>Start");

        Actors owners     = createWLCriteria.getOwners();
        Actors lCreatedBy = createWLCriteria.getCreatedBy();

        loadActors(owners);
        loadActors(lCreatedBy);

        final Long[] ownerIDs        = owners.getActorIDs();
        final Long[] assignedTO      = lCreatedBy.getActorIDs();
        final String[] createdBy     = lCreatedBy.getAllActorDetails();

        StringBuffer getWorklistIDWithTasksQuery =
            new StringBuffer()
                .append(" SELECT pa.actorId FROM                                 \n ")
                .append(" org.jbpm.taskmgmt.exe.PooledActor AS pa                \n ")
                .append(" INNER JOIN pa.taskInstances       AS ti WHERE          \n ")
                .append(" ti.actorId IN (:T_CREATED_BY)))                        \n ");

        StringBuffer accessQuery = new StringBuffer().append(" OR (")
            .append("  worklist.worklistID IN (")
            .append(" SELECT worklistID FROM com.mckesson.eig.workflow.worklist.api.TaskACLResolved WHERE sessionID='")
            .append(WsSession.getSessionId())
            .append("'))");

        getWorklistIDWithTasksQuery.append(createWLCriteria.getShowEmptyWorklists()
                                        ? accessQuery
                                        : "");

        final StringBuffer hqlQuery =
            new StringBuffer()
                .append(" SELECT distinct(worklist.id), worklist.name, worklist.desc,         \n ")
                .append(" (SELECT COUNT(pa.id) from worklist.tasks AS pa                      \n ")
                .append(" INNER JOIN pa.taskInstances AS ti                                   \n ")
                .append(" where ti.actorId IN (:T_CREATED_BY) and pa.actorId=worklist.id      \n ")
                .append(" ) as totalTaskCount     FROM                                        \n ")
                .append(" com.mckesson.eig.workflow.worklist.api.Worklist AS worklist         \n ")
                .append(" INNER JOIN worklist.ownerActors  AS owners                          \n ")
                .append(" LEFT OUTER JOIN worklist.acls    AS acls                            \n ")
                .append(" WHERE  ((worklist.id IN (                                           \n ")
                .append(getWorklistIDWithTasksQuery.toString())
                .append(" ) AND (owners.actorID IN (:T_OWNER_ID)                              \n ")
                .append("   OR acls.actor.actorID = owners.actorID) AND                       \n ") 
                .append("   owners.actorID <> (:T_ACTOR_ID)                                   \n ");

        if (sortOrder != null) {

            if ("count".equalsIgnoreCase(sortOrder.getAttr().getName())) {
                hqlQuery.append(" ORDER BY count");

            } else {
                
                hqlQuery.append(" ORDER BY upper(worklist.")
                        .append(sortOrder.getAttr().getName() + ")");
            }
            hqlQuery.append(sortOrder.getIsDesc() ? DESCENDING : ASCENDING);
        }
        
        List list = (List) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                Query q = s.createQuery(hqlQuery.toString())
                           .setParameterList(T_CREATED_BY, createdBy)
                           .setParameterList(T_OWNER_ID, ownerIDs)
                           .setParameterList(T_ACTOR_ID, assignedTO);

                List resultList = q.setFirstResult(startIndex)
                                .setMaxResults(count)
                                .list();

                return fetchCreatableWorklists(resultList);
            }
        });

        LOG.debug(logSourceMethod + "<<End");
        return new ListWorklist(list);
    }
 
    /**
     * converts the list of object array owing worklist record to list of
     * worklist objects
     *
     * @param creatableWorklist
     *           contains list of object array each object array owing the worklist record
     * @return
     *           list of creatableWorklists
     */
    private List fetchCreatableWorklists(List creatableWorklist) {


        int size = creatableWorklist.size();

        List<Worklist> retList = new ArrayList<Worklist>(size);
        Worklist workList;
        TaskACL taskACL;
        TaskACLs taskACLs = new TaskACLs();
        Set<TaskACL> taskACLSet = new HashSet<TaskACL>();
        int index;

        HashMap taskAclsMap = getTaskACLSMapForUser();
        Long[] ownedWorklistIDs = new Long[size];
        
        for (int i = 0, j = 0; i < size; i++) {

            index      = 0;
            workList   = new Worklist();
            taskACL    = new TaskACL();
            taskACLs   = new TaskACLs();
            taskACLSet = new HashSet<TaskACL>();

            Object[] object = (Object[]) creatableWorklist.get(i);

            workList.setWorklistID((Long) object[index]);
            workList.setName((String) object[++index]);
            workList.setDesc((String) object[++index]);
            
            taskACL = (TaskACL) taskAclsMap.get(workList.getWorklistID());
            
            if (taskACL == null) {
                
                workList.setAssignedTo(null);
                ownedWorklistIDs[j++] = workList.getWorklistID();
            } else {
                
                taskACL.setActor((Actor) WsSession.getSessionData(KEY_ACTOR));
                taskACLSet.add(taskACL);
                taskACLs.setACLs(taskACLSet);
                
                workList.setAssignedTo(taskACLs);
            }
            
            workList.setTotalTaskCount((Long) object[++index]);
            retList.add(workList);
        }
        
        if (size == 0) {
            return retList;
        }
        
        HashMap<Long, Actor> ownedActors = getOwnerWorklistsActor(ownedWorklistIDs);
        Set<Actor> owners;
        for (int i = retList.size(); --i >= 0;) {
            
            Worklist worklist = retList.get(i);
            Actor ownerActor  = ownedActors.get(worklist.getWorklistID());
            if (worklist.getAcls().isEmpty() && ownerActor != null) {
                
                owners = new HashSet<Actor>();
                owners.add(ownerActor);
                worklist.setOwnerActors(owners);
            }
        }
        return retList;
    }
    
    /**
     * fetch the taskACL from WF_ACL_RESOLVED table for the passed worklistID
     * @return Task acl resolved map.
     */
    private HashMap<Long, TaskACL> getTaskACLSMapForUser() {
        
        final String sessionId = WsSession.getSessionId();
        
        List<TaskACLResolved> list = (List) getHibernateTemplate().findByNamedQuery("getTaskACLSForUser", sessionId);
        
        HashMap<Long, TaskACL> taskACLS = new HashMap<Long, TaskACL>();
        TaskACL taskACL;
        Worklist worklist;

        long worklistID;
        for (Iterator<TaskACLResolved> i = list.iterator(); i.hasNext();) {
            
            TaskACLResolved acl = i.next();
            
            worklist = new Worklist();
            taskACL  = new TaskACL();

            worklistID = acl.getWorklistID();
            worklist.setWorklistID(worklistID);
            taskACL.setTaskACLID(-1);
            taskACL.setHasMerged(true);
            taskACL.setCanCreate(acl.getCanCreate());
            taskACL.setCanComplete(acl.getCanComplete());
            taskACL.setCanDelete(acl.getCanDelete());
            taskACL.setCanReassign(acl.getCanReassign());
            taskACL.setDoEmailAlert(acl.getDoEmailAlert());
            taskACL.setWorklist(worklist);

            taskACLS.put(worklistID, taskACL);
        }
        return taskACLS;
    }

    /**
     * Retrieve the actor who is assigned to the particular worklist.
     *  
     * @param worklistIDs
     *        set of worklist ID
     *          
     * @return ownerWorklistsActor
     */
    private HashMap<Long, Actor> getOwnerWorklistsActor(Long[] worklistIDs) {
        
        final List<Long[]> chunkedWorklistIDs = new ArrayList<Long[]>();
        int chunkedCount          = 0;
        final int defaultSize    = 998;
        Long[] chunkedPiece      = 
            new Long[worklistIDs.length >= defaultSize ? defaultSize : worklistIDs.length];
        
        for (int i = 0; i < worklistIDs.length; i++) {
            
            chunkedPiece[chunkedCount] = worklistIDs[i];
            chunkedCount += 1;
            if (chunkedCount == defaultSize) {
                
                chunkedWorklistIDs.add(chunkedPiece);
                chunkedCount = 0;
                chunkedPiece = new Long[(worklistIDs.length - i - 1) >= defaultSize 
                                        ? defaultSize : (worklistIDs.length - i - 1)];
            }
        }
        if (chunkedPiece.length != 0) {
            
            chunkedWorklistIDs.add(chunkedPiece);
        }
        
        final StringBuffer hqlQuery =
            new StringBuffer()
                .append(" SELECT worklist.id, owners                                        \n ")
                .append(" FROM com.mckesson.eig.workflow.worklist.api.Worklist AS worklist  \n ")
                .append(" INNER JOIN worklist.acls         AS acls                          \n ")
                .append(" INNER JOIN worklist.ownerActors  AS owners                        \n ")
                .append(" WHERE                                                             \n ");
        
        for (int i = 0; i < chunkedWorklistIDs.size(); i++) {
            
            if (i == 0) {
                hqlQuery.append(" worklist.id IN (:T_WORKLIST_IDS_").append(i).append(")");
            } else {
                hqlQuery.append(" OR worklist.id IN (:T_WORKLIST_IDS_").append(i).append(")");
            }
        }
        
        List worklists = (List) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                Query query = s.createQuery(hqlQuery.toString());
                for (int i = 0; i < chunkedWorklistIDs.size(); i++) {
                    query.setParameterList("T_WORKLIST_IDS_" + i, chunkedWorklistIDs.get(i));
                }
                return query.list();
            }
        });
        
        return fetchOwnerWorklistsActor(worklists);
    }
    
    /**
     * set the worklistID as key and the assigned actor as value
     * 
     * @param worklists
     *        list of owner worklists
     *          
     * @return ownerActors
     */
    private HashMap<Long, Actor> fetchOwnerWorklistsActor(List worklists) {
        
        HashMap<Long, Actor> ownedActors = new HashMap<Long, Actor>();
        for (int i = worklists.size(); --i >= 0;) {

            Object[] object = (Object[]) worklists.get(i);
            ownedActors.put((Long) object[0], (Actor) object[1]);
        }
        return ownedActors;
    }

    /**
     * converts the list of object array owing worklist record to list of
     * worklist objects
     *
     * @param worklist
     *           contains list of object array each object array owing the worklist record
     * @return
     *           list of creatableWorklists
     */
    private List fetchWorklistWithTaskCount(List worklist) {

        final int taskCountPosition = 3;

        int size = worklist.size();

        List<Worklist> retList = new ArrayList<Worklist>(size);
        Worklist workList;

        for (int i = 0; i < size; i++) {

            workList = new Worklist();

            Object[] object = (Object[]) worklist.get(i);

            workList.setWorklistID((Long) object[0]);
            workList.setName((String) object[1]);
            workList.setDesc((String) object[2]);
            workList.setTotalTaskCount((Long) object[taskCountPosition]);
            workList.setVersion((Integer) object[taskCountPosition + 1]);

            retList.add(workList);
        }
        return retList;
    }

    /**
     * @see com.mckesson.eig.workflow.worklist.service.WorklistService
     *  #updateWorklistACLs(ListACLs, long[], Actor)
     */
    public void updateWorklistACLs(ListACLs listACL, final long[] worklistIDs, Actor assignedTo) {

        final String logSourceMethod = "updateWorklistACLs(listACL, worklistIDs, assignedTo)";
        LOG.debug(logSourceMethod + ">>Start");

        List acls = getACLs(listACL);

        assignedTo = loadActor(assignedTo);
        final ArrayList<Long> wlIDList = new ArrayList<Long>();

        if (worklistIDs != null) {
            for (long wlID : worklistIDs) {
                wlIDList.add(wlID);
            }
        }

        Set wLIDs = getAllAssignedWorklistIDs(assignedTo);

        wLIDs.addAll(wlIDList);

        for (Object wLID : wLIDs) {
            Worklist wList = getWorklist((Long) wLID, true);

            if (wList == null) {
                continue;
            }
            Set assignedAcls = wList.getAcls();
            TaskACL acl = null;

            if (wList.getOwnerID() == assignedTo.getActorID()) {
                continue;
            }
            //REMOVING THE TASKACL ASSOCIATED WITH THE ACTOR AND WORKLIST
            for (Iterator iter = assignedAcls.iterator(); iter.hasNext();) {
                acl = (TaskACL) iter.next();
                if (acl.getActor().getActorID() == assignedTo.getActorID()) {
                    iter.remove();
                    break;
                }
            }

            Long wlID = wList.getWorklistID();
            int inputWLIndex = wlIDList.indexOf(wlID);
            //IF WORKLIST IS GIVEN AS INPUT, PROCEED ... GET THE TASKACL OBJECT AND ADD IT
            if (inputWLIndex >= 0 && (acls.size() > inputWLIndex)) {
                acl = (TaskACL) acls.get(inputWLIndex);
                assignedAcls.add(acl);
                acl.setActor(assignedTo);
            }

            merge(wList);
        }

        LOG.debug(logSourceMethod + "<<End");
    }

    /**
     * This will get the ACLs from the wrapper object ListACLs. If no ACL is given in ListACLs,
     * it will return an empty List.
     *
     * @param listACL
     * @return
     */
    private List getACLs(ListACLs listACL) {

        List acls = null;

        if (listACL != null && listACL.getACLs() != null) {
            acls = listACL.getACLs();
        } else {
            acls = new ArrayList();
        }

        return acls;
    }
    /**
     * Returns all worklist IDs for which the actor has ACL.
     *
     * @param assignedTo
     * @return
     */
    private Set getAllAssignedWorklistIDs(final Actor assignedTo) {

        final StringBuffer hqlQuery =
            new StringBuffer()
                .append(" SELECT distinct worklist.id                                       \n ")
                .append(" FROM com.mckesson.eig.workflow.worklist.api.Worklist AS worklist  \n ")
                .append(" INNER JOIN worklist.acls      AS acls                             \n ")
                .append(" INNER JOIN acls.actor         AS actor                            \n ")
                .append(" WHERE actor.actorID = :T_ASSIGNED_TO                              \n ");

        List wIDs = (List) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                List list = s.createQuery(hqlQuery.toString())
                             .setLong("T_ASSIGNED_TO", assignedTo.getActorID())
                             .list();

                return list;
            }
        });

        return new HashSet(wIDs);
    }

    /**
     * This method used to remove the taskacl's for the currently logged-in user in this session.
     */
    public void removeTaskAcls() {
       
        final String sessionId = WsSession.getSessionId();

        getHibernateTemplate().execute(

            new HibernateCallback() {

                public Object doInHibernate(Session s) {
              
                  Query query = s.getNamedQuery("removeTaskAcls");
                  query.setParameter("SESSION_ID", sessionId);

                  return query.executeUpdate();
                }
            }
         );
    }

    /**
     * This method used to remove and reload the worklist acls in to WF_ACL_RESOLVED for this session.
     */
    public void updateWorklistACLs() {

        removeTaskAcls();
        resolveTaskAclsByActors();
    }

    /**
     * Helper method used to fetch the actor IDs which are stored in the the session
     * 
     * @return
     */
    public Object[] getActorIDs() {

        Actors actors = (Actors) WsSession.getSessionData(KEY_ACTORS);
        if (null == actors) {
            actors = new Actors(new HashSet<Actor>());
        }
        Actor a = (Actor) WsSession.getSessionData(KEY_ACTOR);
        actors.getActors().add(a);

        List<Long> actorIdList = new ArrayList<Long>();
        for (Iterator<Actor> i = actors.getActors().iterator(); i.hasNext();) {
            actorIdList.add(getActorID(i.next()));
        }
        return actorIdList.toArray();
    }

    /**
     * This method used to remove and reload the worklist acls in to WF_ACL_RESOLVED for this session.
     */
    @SuppressWarnings("unchecked")
    public void resolveTaskAclsByWorklistID(final long worklistId) {

        final String sessionId = WsSession.getSessionId();
        final Object[] actorIds  = getActorIDs();

        List result = (List) getHibernateTemplate().execute(

            new HibernateCallback() {

                public Object doInHibernate(Session s) {

                  Query query = s.getNamedQuery("resolveTaskAclsByWorklistID");
                  query.setParameterList("ACTOR_IDS", actorIds);
                  query.setParameter("WORKLIST_ID", worklistId);

                  return query.list();
                }
            }
         );
        
        if (CollectionUtilities.isEmpty(result)) {
            removeTaskAclByWorklistID(new Object[] {worklistId});
        } else {

            List<TaskACLResolved> resolvedList = fetchTaskAclResolved(sessionId, result);
            for(TaskACLResolved tar : resolvedList) {
                getHibernateTemplate().saveOrUpdate(tar);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private List<TaskACLResolved> fetchTaskAclResolved(String sessionId, List result) {

        List<TaskACLResolved> aclResolvedList = new ArrayList<TaskACLResolved>();
        TaskACLResolved aclResolved = null;
        for (Iterator i = result.iterator(); i.hasNext();) {

            Object[] obj = (Object[]) i.next();
            aclResolved = new TaskACLResolved(sessionId, obj);
            aclResolvedList.add(aclResolved);
        }
        return aclResolvedList;
    }

    /**
     * This method used to remove and reload the worklist acls in to WF_ACL_RESOLVED for this session.
     */
    public void removeTaskAclByWorklistID(final Object[] worklistIds) {

        final String sessionId = WsSession.getSessionId();

        getHibernateTemplate().execute(

            new HibernateCallback() {

                public Object doInHibernate(Session s) {

                  Query query = s.getNamedQuery("removeTaskAclByWorklistID");
                  query.setParameter("SESSION_ID", sessionId);
                  query.setParameterList("WORKLIST_IDs", worklistIds);

                  return query.executeUpdate();
                }
            }
        );
    }
}
