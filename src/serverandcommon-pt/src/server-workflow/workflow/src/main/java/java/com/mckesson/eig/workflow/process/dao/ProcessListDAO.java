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

package com.mckesson.eig.workflow.process.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mckesson.dm.core.common.logging.OCLogger;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateCallback;

import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.VariableList;
import com.mckesson.eig.workflow.process.api.ProcessActorACL;
import com.mckesson.eig.workflow.process.api.ProcessAttribute;
import com.mckesson.eig.workflow.process.api.ProcessAttributeList;
import com.mckesson.eig.workflow.process.api.ProcessInfo;
import com.mckesson.eig.workflow.process.api.ProcessInfoList;
import com.mckesson.eig.workflow.process.api.ProcessVersionInfo;

/**
 * @author McKesson
 * @date   Feb 10, 2009
 * @since  HECM 2.0; Feb 10, 2009
 * Class for all ProcessList DAO calls
 */
public class ProcessListDAO extends AbstractProcessDAO {

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( ProcessListDAO.class);

    private static final String P_OWNER_ID 		= "P_OWNER_ID";
    private static final String P_ACTOR_ID      = "P_ACTOR_ID";
    private static final String P_ASSIGNED_ID 	= "P_ASSIGNED_ID";
    private static final String P_ATTR_NAME 	= "P_ATTRIBUTE_NAME";
    private static final String P_ATTR_VALUE 	= "P_ATTRIBUTE_VALUE";
    private static final String PROCESS_TYPE	= "PROCESS_TYPE";

    /**
     * This method returns the list of available process irrespective of the domain.
     * Based on the filter property it will returns the list of process of type manual,
     * auto or both and also it will returns only the active processes based on the
     * canIncludeExpired value.
     *
     * @param canIncludeExpired
     *        Whether to list the expired process or only the active process
     *
     * @param variableList
     *        Holding the key,value like processType=Both.Based on this, return the list of process
     *
     */
    public ProcessInfoList getAllProcesses(boolean canIncludeExpired,
                                       final VariableList variableList) {

        final String logSourceMethod =
            "getAllProcesses(canIncludeExpired, variableList)";
        LOG.debug(logSourceMethod + ">>Start");

        final StringBuffer getAllProcessesQuery =
            new StringBuffer()
            .append(" SELECT p                                                             \n ")
            .append(" FROM   com.mckesson.eig.workflow.process.api.ProcessVersionInfo AS p \n ")
            .append("   LEFT OUTER JOIN p.processAttributesSet AS pa                       \n ")
            .append(" WHERE pa.attributeName  =  UPPER(:P_ATTRIBUTE_NAME)                  \n ")
            .append("   AND pa.attributeValue IN (:P_ATTRIBUTE_VALUE)                      \n ")
            .append("   AND p.effectiveDateTime >= sysdate                                 \n ")
            .append("   AND p.versionId = (SELECT MAX(procV.versionId) FROM                \n ")
            .append(" com.mckesson.eig.workflow.process.api.ProcessVersionInfo procV       \n ")
            .append(" WHERE procV.processInfo.processId = p.processInfo.processId)         \n ");

        getAllProcessesQuery.append(canIncludeExpired ? StringUtilities.EMPTYSTRING
                                                      : " AND p.expireDateTime >= sysdate \n");

        ProcessInfoList processList = (ProcessInfoList) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                List list = s.createQuery(getAllProcessesQuery.toString())
                             .setParameter(P_ATTR_NAME, PROCESS_TYPE)
                             .setParameterList(P_ATTR_VALUE, variableList.getVariablesValue())
                             .list();
                return new ProcessInfoList(fetchProcessInfoList(list, true));
            }
        });

        LOG.debug(logSourceMethod + "<<End");
        return processList;
    }

    /**
     * This Method returns the list of process which belongs to the current actor
     *
     * @param assignedTo
     *        The assigned actor of the process
     */
    public ProcessInfoList getAssignedProcesses(Actor assignedTo) {

        final String logSourceMethod = "getAssignedProcessList(assignedTo)";
        LOG.debug(logSourceMethod + ">>Start");

        assignedTo = loadActor(assignedTo);
        final Long actorID = assignedTo.getActorID();
        ProcessInfoList processList = (ProcessInfoList) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                List list = s.getNamedQuery("getAssignedProcesses")
                             .setLong(P_ACTOR_ID, actorID)
                             .list();

                return new ProcessInfoList(fetchProcessInfoList(list, false));
            }
        });

        LOG.debug(logSourceMethod + "<<End");
        return processList;
    }

    /**
     * This Method assigns the specified processes to the current actor
     *
     * @param assignedTo
     *        actor need to associate with list of specified processes
     *
     * @param processIds
     *        list of process unique IDs
     *
     * @return processNames
     *         assigned and unassigned process names for auditing purpose.
     */
    public Map<String, List<String>> updateProcessAssignments(Actor assignedTo,
                                                              List<Long> processIds) {

        final String logSourceMethod = "updateProcessAssignments(assignedTo, processListIds)";
        LOG.debug(logSourceMethod + ">>Start");

        assignedTo = loadActor(assignedTo);
        List<ProcessInfo> processList = getAssignedProcesses(assignedTo).getProcessList();

        List<Long> pIDs                      = new ArrayList<Long>();
        List<String> assignedPNames          = new ArrayList<String>();
        List<String> unAssignedPNames        = new ArrayList<String>();
        HashMap<String, List<String>> processNames = new HashMap<String, List<String>>();
        pIDs.addAll(processIds);

        for (int i = processList.size(); --i >= 0;) {

            ProcessInfo processInfo = processList.get(i);
            if (!pIDs.contains(processInfo.getProcessId())) {
                pIDs.add(processInfo.getProcessId());
            }
        }

        assignProcessesToActor(assignedTo, processIds, pIDs, assignedPNames, unAssignedPNames);
        processNames.put("assigned",   assignedPNames);
        processNames.put("unassigned", unAssignedPNames);
        LOG.debug(logSourceMethod + "<<End");
        return processNames;
    }

    /**
     * This Method will assign the list of processes to the actor specified and also unassign the
     * processes whichever processes are not passed.
     *
     * @param assignedTo
     *        actor need to assign/unassign with the processes
     *
     * @param processListIds
     *        list of process ids need to be assigned
     *
     * @param processIDs
     *        list of process ids need to be assigned/unassigned
     *
     * @param assignedProcessNames
     *        assigned process names which are used for auditing
     *
     * @param unAssignedProcessNames
     *        unassigned process names which are used for auditing
     */
    private void assignProcessesToActor(Actor assignedTo,
                                      List<Long> processListIds,
                                      List<Long> processIDs,
                                      List<String> assignedProcessNames,
                                      List<String> unAssignedProcessNames) {
        ProcessInfo process;
        for (Long processID : processIDs) {

            ProcessActorACL acl = null;
            process = getProcess(processID);

            Set<ProcessActorACL> assignedAcls = process.getAssignedActors();
            for (Iterator<ProcessActorACL> i = assignedAcls.iterator(); i.hasNext();) {

                acl = i.next();
                if (acl.getActor().getActorID() == assignedTo.getActorID()) {
                    i.remove();
                    break;
                }
            }

            Date currentTime = new Date();
            if (acl == null || acl.getActor().getActorID() != assignedTo.getActorID()) {

                acl = new ProcessActorACL(processID, "Assigned", assignedTo);
                acl.setCreatedTS(currentTime);
            } else {
            	acl.setUpdatedTS(currentTime);
            }

            if (processListIds.contains(process.getProcessId())) {

                process.getAssignedActors().add(acl);
                assignedProcessNames.add(process.getProcessVersionInfo().iterator().next()
                                                                                .getProcessName());
            } else {
                unAssignedProcessNames.add(process.getProcessVersionInfo().iterator().next()
                                                                       .getProcessName());
            }
            saveOrUpdate(process);
        }
    }

    /**
     * Returns a list of all applicable processes which are assigned to the list of users & groups
     * specified by the assignedTo parameter. It is also specific to the list of domains
     * (specified by the ownedBy parameter).It could be further filtered out by the
     * filterByProperties parameter which has has a key, value pair used to filter out processes
     * basis its attributes.
     *
     * @param ownedBy
     *        list of domain Actors
     *
     * @param assignedTo
     *        list of users & groups Actors
     *
     * @param filterByProperties
     *        Holding the key,value like processType=Both.Based on this, return the list of process
     */
    public ProcessInfoList getProcesses(Actors ownedBy,
                                    Actors assignedTo,
                                    final VariableList filterByProperties) {

        final String logSourceMethod = "getProcesses(ownedBy, assignedTo, filterByProperties)";
        LOG.debug(logSourceMethod + ">>Start");

        loadActors(ownedBy);
        loadActors(assignedTo);

        final Long[] assignedActorIDs = assignedTo.getActorIDs();
        final Long[] ownedActorIDs    = ownedBy.getActorIDs();

        ProcessInfoList processList = (ProcessInfoList) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                List processes = s.getNamedQuery("getProcesses")
                                  .setParameterList(P_OWNER_ID,    ownedActorIDs)
                                  .setParameterList(P_ASSIGNED_ID, assignedActorIDs)
                                  .setString(P_ATTR_NAME, PROCESS_TYPE)
                                  .setParameterList(P_ATTR_VALUE,
                                                    filterByProperties.getVariablesValue())
                                  .list();

                return new ProcessInfoList(fetchProcessInfoList(processes, false));
            }
        });

        LOG.debug(logSourceMethod + "<<End");
        return processList;
    }

    /**
     * This method returns the list of processes Owned by the list of domains.
     * Based on the filter property it will returns the list of process of type manual,
     * auto or both and also it will return only the active / expired processes based on the
     * canIncludeExpired value.
     *
     * @param canIncludeExpired
     *        Whether to list the expired process or only the active process
     *
     * @param ownedBy
     *        List of domain Actors
     *
     * @param variableList
     *        Holding the key,value like triggerType=Both.Based on this, return the list of process
     */

    public ProcessInfoList getOwnedProcesses(boolean canIncludeExpired,
                                         Actors ownedBy,
                                         final VariableList variableList) {

        final String logSourceMethod =
            "getOwnedProcesses(canIncludeExpired, ownedBy, variableList)";
        LOG.debug(logSourceMethod + ">>Start");

        final StringBuffer getProcessesQuery =
            new StringBuffer()
            .append(" SELECT p                                                              \n ")
            .append(" FROM   com.mckesson.eig.workflow.process.api.ProcessVersionInfo as p  \n ")
            .append(" LEFT OUTER JOIN p.processAttributesSet as pa                          \n ")
            .append(" INNER JOIN p.processInfo.ownerActors AS ownerActors                   \n ")
            .append(" WHERE ownerActors.actorID IN (:P_OWNER_ID)                            \n ")
            .append(" AND pa.attributeName  =  UPPER(:P_ATTRIBUTE_NAME)                     \n ")
            .append(" AND pa.attributeValue IN (:P_ATTRIBUTE_VALUE)                         \n ")
            .append(" AND p.versionId = (SELECT MAX(procV.versionId) FROM                   \n ")
            .append(" com.mckesson.eig.workflow.process.api.ProcessVersionInfo procV        \n ")
            .append(" WHERE procV.processInfo.processId = p.processInfo.processId)          \n ");

        getProcessesQuery.append(canIncludeExpired ? StringUtilities.EMPTYSTRING
                                                      : " AND p.expireDateTime >= sysdate \n");
        loadActors(ownedBy);
        final Long[] ownedActorIDs    = ownedBy.getActorIDs();

        ProcessInfoList processList = (ProcessInfoList) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                List list = s.createQuery(getProcessesQuery.toString())
                             .setParameterList(P_OWNER_ID, ownedActorIDs)
                             .setParameter(P_ATTR_NAME, PROCESS_TYPE)
                             .setParameterList(P_ATTR_VALUE,
                                               variableList.getVariablesValue())
                             .list();
                return new ProcessInfoList(fetchProcessInfoList(list, false));
            }
        });

        LOG.debug(logSourceMethod + "<<End");
        return processList;
    }

    /**
     * This method returns the list of processes Owned by the list of domains.
     * Based on the filter property it will returns the list of process of type manual,
     * auto or both and also it will return only the active / expired processes based on the
     * canIncludeExpired value.
     *
     * @param canIncludeExpired
     *        Whether to list the expired process or only the active process
     *
     * @param ownedBy
     *        List of domain Actors
     *
     * @param variableList
     *        Holding the key,value like triggerType=Both.Based on this, return the list of process
     */

    public ProcessInfoList getOwnedProcessesInfo(boolean canIncludeExpired,
                                                 Actors ownedBy,
                                                 final VariableList variableList) {

        final String logSourceMethod =
            "getOwnedProcesses(canIncludeExpired, ownedBy, variableList)";
        LOG.debug(logSourceMethod + ">>Start");

        final StringBuffer getProcessesQuery =
            new StringBuffer()
            .append(" SELECT p                                                             \n ")
            .append(" FROM   com.mckesson.eig.workflow.process.api.ProcessVersionInfo as p \n ")
            .append(" LEFT  OUTER JOIN p.processAttributesSet as pa                        \n ")
            .append(" INNER JOIN p.processInfo.ownerActors AS ownerActors                  \n ")
            .append(" WHERE ownerActors.actorID IN (:P_OWNER_ID)                           \n ")
            .append(" AND pa.attributeName  =  UPPER(:P_ATTRIBUTE_NAME)                    \n ")
            .append(" AND pa.attributeValue IN (:P_ATTRIBUTE_VALUE)                        \n ")
            .append(" AND p.versionId = (SELECT MAX(procV.versionId) FROM                  \n ")
            .append(" com.mckesson.eig.workflow.process.api.ProcessVersionInfo procV       \n ")
            .append(" WHERE procV.processInfo.processId = p.processInfo.processId)         \n ");

        getProcessesQuery.append(canIncludeExpired ? StringUtilities.EMPTYSTRING
                                                      : " AND p.expireDateTime >= sysdate \n");
        loadActors(ownedBy);
        final Long[] ownedActorIDs    = ownedBy.getActorIDs();

        ProcessInfoList processList = (ProcessInfoList) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                List list = s.createQuery(getProcessesQuery.toString())
                             .setParameterList(P_OWNER_ID, ownedActorIDs)
                             .setParameter(P_ATTR_NAME, PROCESS_TYPE)
                             .setParameterList(P_ATTR_VALUE,
                                               variableList.getVariablesValue())
                             .list();
                return new ProcessInfoList(fetchProcessInfoList(list, false));
            }
        });

        LOG.debug(logSourceMethod + "<<End");
        return processList;
    }

    /**
     * This method returns the details of the process along with the owners, process attributes
     * and the access controls of the users/groups on the process for the processID passed.
     *
     * @param processID
     * @return
     */
    private ProcessInfo getProcess(final Long processID) {

        final String logSourceMethod = "getProcess(processID)";
        LOG.debug(logSourceMethod + ">>Start");

        ProcessInfo process = (ProcessInfo) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                return s.createCriteria(ProcessInfo.class)
                        .setFetchMode("ownerActors",          FetchMode.JOIN)
                        .setFetchMode("assignedActors",       FetchMode.JOIN)
                        .setFetchMode("assignedActors.actor", FetchMode.JOIN)
                        .add(Restrictions.idEq(processID))
                        .uniqueResult();
            }
        });

        LOG.debug(logSourceMethod + "<<End");
        return process;
    }

    /**
     * set the process only with the id, name, description and ownerActors
     * @param list
     *          list of process with required values
     *
     * @param fetchOwnerActors
     *      True/False - specify whether the OwnerActors are to be set in the process
     *
     * @return
     *          list of process info with required values
     */
    private List<ProcessInfo> fetchProcessInfoList(List list, boolean fetchOwnerActors) {

        List<ProcessInfo> processInfoList = new ArrayList<ProcessInfo>();

        for (int i = list.size(); --i >= 0;) {

        	ProcessVersionInfo pi = (ProcessVersionInfo) list.get(i);

        	ProcessInfo processInfo = new ProcessInfo();
            processInfo.setProcessId(pi.getProcessInfo().getProcessId());

            processInfo.setProcessVersionInfo(setProcessVersionInfo(pi, true));

            setAssignedActor(pi.getProcessInfo().getAssignedActors(), processInfo);

            if (fetchOwnerActors) {

                Set<Actor> ownerActors = new HashSet<Actor>();
                fetchOwnerActors(pi.getProcessInfo().getOwnerActors(), ownerActors);
                processInfo.setOwnerActors(ownerActors);
            }
            processInfoList.add(processInfo);
        }
        return processInfoList;
    }

    /**
     * @param assignedActors
     * @param processInfo
     */
    private void setAssignedActor(Set<ProcessActorACL> assignedActors,
								  ProcessInfo processInfo) {

         Iterator<ProcessActorACL> iterator  = assignedActors.iterator();
         Set<ProcessActorACL> assignedACLSet = new HashSet<ProcessActorACL>();
         while (iterator.hasNext()) {

         	ProcessActorACL assignedACL = iterator.next();
         	ProcessActorACL assignACL = new ProcessActorACL();
         	assignACL.setProcessId(assignedACL.getProcessId());
         	assignACL.setPermissionName(assignedACL.getPermissionName());
         	assignACL.setCreatedTS(assignedACL.getCreatedTS());
         	assignACL.setUpdatedTS(assignedACL.getUpdatedTS());

         	Actor actor  = assignedACL.getActor();
         	Actor newAct = new Actor();
         	newAct.setActorID(actor.getActorID());
         	newAct.setAppID(actor.getAppID());
         	newAct.setEntityID(actor.getEntityID());
         	newAct.setEntityType(actor.getEntityType());
         	newAct.setVersion(actor.getVersion());
         	assignACL.setActor(newAct);

         	assignedACLSet.add(assignACL);
         }
         processInfo.setAssignedActors(assignedACLSet);
	}

	/**
     * @param procVersionInfo ProcessVersionInfo
     * @param fetchProcessAttributes boolean
     * 
     * @return Set<ProcessVersionInfo>
     */
    private Set<ProcessVersionInfo> setProcessVersionInfo(ProcessVersionInfo procVersionInfo,
                                                  		  boolean fetchProcessAttributes) {

        Set<ProcessVersionInfo> processVersionsInfoSet = new HashSet<ProcessVersionInfo>();
        ProcessVersionInfo processVersionInfo = new ProcessVersionInfo();
        processVersionInfo.setProcessName(procVersionInfo.getProcessName());
        processVersionInfo.setProcessDescription(procVersionInfo.getProcessDescription());
        processVersionInfo.setVersionId(procVersionInfo.getVersionId());

        if (fetchProcessAttributes) {

            List<ProcessAttribute> processAttributeList = new ArrayList<ProcessAttribute>();
            fetchProcessAttributes(procVersionInfo.getProcessAttributesSet(), processAttributeList);
            processVersionInfo.setProcessAttributeList(
            		new ProcessAttributeList(processAttributeList));
        }

        processVersionsInfoSet.add(processVersionInfo);
        return processVersionsInfoSet;
    }

    /**
     * move the list of process attributes to the non hibernate session object
     *
     * @param processAttributes
     *        set of process attributes in hibernate session
     *
     * @param processAttributeList
     *       list of hibernate session in non hibernate session
     */
    private void fetchProcessAttributes(Set<ProcessAttribute> processAttributes,
                                        List<ProcessAttribute> processAttributeList) {

        for (Iterator<ProcessAttribute> pAttributes = processAttributes.iterator();
                                        pAttributes.hasNext();) {

            ProcessAttribute processAttribute = pAttributes.next();
            ProcessAttribute pa = new ProcessAttribute();
            pa.setProcessId(processAttribute.getProcessId());
            pa.setAttributeName(processAttribute.getAttributeName());
            pa.setAttributeType(processAttribute.getAttributeType());
            pa.setAttributeValue(processAttribute.getAttributeValue());
            processAttributeList.add(pa);
        }
    }

    /**
     * move the set of owner actors to the non hibernate session object
     *
     * @param processOwnerActors
     *        set of actors in hibernate session
     *
     * @param ownerActors
     *        set of actors in non hibernate session
     */
    private void fetchOwnerActors(Set<Actor> processOwnerActors, Set<Actor> ownerActors) {

        for (Iterator<Actor> owners = processOwnerActors.iterator(); owners.hasNext();) {

            Actor ownerActor = owners.next();
            Actor actor = new Actor();
            actor.setActorID(ownerActor.getActorID());
            actor.setAppID(ownerActor.getAppID());
            actor.setEntityID(ownerActor.getEntityID());
            actor.setEntityType(ownerActor.getEntityType());
            ownerActors.add(actor);
        }
    }
}
