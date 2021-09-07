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

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.process.api.Process;
import com.mckesson.eig.workflow.process.api.ProcessAttribute;
import com.mckesson.eig.workflow.process.api.ProcessOwner;
import com.mckesson.eig.workflow.process.api.ProcessVersion;

/**
 * Class for all ProcessDAO calls
 */
public class ProcessDAO extends AbstractProcessDAO {

	/**
	 * Object represents the Log4JWrapper object.
	 */
	private static final Log LOG = LogFactory.getLogger(ProcessDAO.class);

    public void createProcess(final Actor assignedTo, final Process processDetails) {
        LOG.debug("enter ProcessDao::createProcess()");

         getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session s) {
                            s.save(processDetails);
                            saveProcessVersions(processDetails, s);
                            //Associate Actors to Process
                            assignProcessActor(assignedTo, processDetails , s);
                        return s;
                    }
                });

        LOG.debug("leave ProcessDao::createProcess()");
    }

    /**
     * Method returns the latest process version for the corresponding processId passed.
     *
     * @param processId
     * @return
     */
    public ProcessVersion getProcessVersion(final long processId) {

        final String logSourceMethod = "getProcessName(processId)";
        LOG.debug(logSourceMethod + ">>Start");

        ProcessVersion processVersion = (ProcessVersion) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                return s.getNamedQuery("getProcessVersion")
                        .setParameter("PROCESS_ID", processId)
                        .uniqueResult();
            }
        });

        LOG.debug(logSourceMethod + "<<End");
        return processVersion;
    }

    /**
     * Fetch the complete Process object by Process Id and Domain Id actor
     * @param domainId
     * @param processName
     * @return
     */
    public Process getProcess(final Actor actor, final Long processId) {
        final String logSourceMethod = "getProcess(processID)";
        LOG.debug(logSourceMethod + ">>Start");
        if (LOG.isDebugEnabled()) {
            LOG.debug("Actor Details : " + actor.toString());
            LOG.debug("Process ID    : " + processId);
        }
        Process process = (Process) getHibernateTemplate().execute(
            new HibernateCallback() {
                public Object doInHibernate(Session s) {

                    Query query = s.getNamedQuery("getProcessVersionAndAttributes");
                    query.setParameter("PROCESS_ID", processId);
                    //get the latest version
                    ProcessVersion processVersion = (ProcessVersion) query.uniqueResult();
                    return processVersion.getProcess();
                }
        });

        LOG.debug(logSourceMethod + "<<End");
        return process;
    }

    public Process saveProcess(Actor creatorActor, final Process processDetails) {
        final String logSourceMethod = "getProcess(processID)";
        LOG.debug(logSourceMethod + ">>Start");
        if (LOG.isDebugEnabled()) {
            LOG.debug("Actor Details : " + creatorActor.toString());
            LOG.debug("Process ID    : " + processDetails.toString());
        }
        Process updatedProcess = (Process) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session s) {
                            return s.merge(processDetails);
                    }
                });
        LOG.debug(logSourceMethod + "<<End");
        return updatedProcess;
    }


    public void deleteProcess(final Actor owner, final Process processDetails) {
        LOG.debug("enter ProcessDao::createProcess()");
         getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session s) {
                        //disassociate owner to Process
                        unAssignProcessOwner(owner, processDetails , s);
                        deleteProcessVersions(processDetails, s);
                        s.delete(processDetails);
                        return s;
                    }
                });
        LOG.debug("leave ProcessDao::createProcess()");
    }

    public Process createNewProcessVersion(final Actor creatorActor,
            final Process processDetails) {
        final String logSourceMethod = "getProcess(processID)";
        LOG.debug(logSourceMethod + ">>Start");
        if (LOG.isDebugEnabled()) {
            LOG.debug("Actor Details : " + creatorActor.toString());
            LOG.debug("Process ID    : " + processDetails.toString());
        }

        Process process = (Process) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session s) {
                        s.merge(processDetails);
                        return processDetails;
                    }
                });
        LOG.debug(logSourceMethod + "<<End");
        return process;
    }

    private void deleteProcessVersions(Process processDetails, Session s) {
        Set<ProcessVersion> versions = processDetails.getProcessVersions();
        Iterator<ProcessVersion> iter = versions.iterator();
        while (iter.hasNext()) {
            ProcessVersion version = iter.next();
            version.setProcess(processDetails);
            deleteProcessAttributes(version, s);
            s.delete(version);
        }
    }

    private void deleteProcessAttributes(ProcessVersion version, Session s) {
        Set<ProcessAttribute> processsAttributes = version.getProcessAttributes();
        Iterator<ProcessAttribute> iter = processsAttributes.iterator();
        while (iter.hasNext()) {
            ProcessAttribute singleAttribute = iter.next();
            singleAttribute.setVersionId(version.getVersionId());
            singleAttribute.setProcessId(version.getProcess().getProcessId());
            s.delete(singleAttribute);
        }
    }

    private void unAssignProcessOwner(Actor owner, Process processDetails,
            Session s) {
        ProcessOwner processOwner = new ProcessOwner();
        processOwner.setActor(owner);
        processOwner.setProcessId(processDetails.getProcessId());
        s.delete(processOwner);
    }

    private void saveProcessVersions(final Process processDetails, Session s) {

        Set<ProcessVersion> versions = processDetails.getProcessVersions();
        if (CollectionUtilities.hasContent(versions)) {

            Iterator<ProcessVersion> iter = versions.iterator();
            while (iter.hasNext()) {
                ProcessVersion processVersion = iter.next();
                processVersion.setProcess(processDetails);
                processVersion.setCreatedTS(new Date());
                s.save(processVersion);
                saveProcessAttributes(processVersion, s);
            }
        }
    }

    private void saveProcessAttributes(ProcessVersion version, Session s) {
        Set<ProcessAttribute> processsAttributes = version
                .getProcessAttributes();
        if (CollectionUtilities.isEmpty(processsAttributes)) {
            //ProcessVersion with no process attributes
            return;
        }
        Iterator<ProcessAttribute> iter = processsAttributes.iterator();
        while (iter.hasNext()) {
            ProcessAttribute singleAttribute = iter.next();
            singleAttribute.setVersionId(version.getVersionId());
            singleAttribute.setProcessId(version.getProcess().getProcessId());
            singleAttribute.setCreatedTS(new Date());
            s.save(singleAttribute);
        }
    }

    private void assignProcessActor(Actor actor, Process processDetails,
            Session s) {
        ProcessOwner processOwner = new ProcessOwner();
        processOwner.setActor(actor);
        processOwner.setProcessId(processDetails.getProcessId());
        processOwner.setCreatedTS(new Date());
        s.save(processOwner);
    }
}
