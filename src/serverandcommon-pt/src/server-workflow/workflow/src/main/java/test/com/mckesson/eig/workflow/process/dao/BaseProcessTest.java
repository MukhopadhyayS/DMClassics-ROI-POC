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

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.jpdl.xml.JpdlXmlWriter;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.xml.sax.SAXException;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.workflow.Utilities.ServiceNames;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.process.api.Process;
import com.mckesson.eig.workflow.process.api.ProcessAttribute;
import com.mckesson.eig.workflow.process.api.ProcessVersion;
import com.mckesson.eig.workflow.test.AbstractWorkflowTestCase;

/**
 * @author Senthil Paramasivam
 * Mar 14, 2009
 *
 */
public class BaseProcessTest extends AbstractWorkflowTestCase {

    /**
     *
     */

    public BaseProcessTest() {
    }

    /**
     * @param name
     */
    public BaseProcessTest(String name) {
        super(name);
    }


    protected Process buildProcessObject(Actor owner, String processName, boolean deployed) {
        /*
         * build the process object
         */
        Set actorsSet = new HashSet();
        actorsSet.add(owner);
        Actors owners = new Actors(actorsSet);


        Process processDetails = new com.mckesson.eig.workflow.process.api.Process();
        processDetails.setOwners(owners);

        Date date = new Date();
        processDetails.setCreateDateTime(date);
        processDetails.setModifiedDateTime(date);
        processDetails.setModifiedUserId(owner.getActorID());
        Set<ProcessVersion> processVersions = new HashSet<ProcessVersion>();
        ProcessVersion v1 = buildProcessVersion(processName, deployed, processDetails);
        processVersions.add(v1);

        processDetails.setProcessVersions(processVersions);
        return processDetails;
    }

    private String convertToXml(ProcessDefinition pd) {
        StringWriter stringWriter = new StringWriter();
        JpdlXmlWriter jpdlWriter = new JpdlXmlWriter(stringWriter);
        jpdlWriter.write(pd);
        String  xml = stringWriter.toString();
        return xml;
    }

    private ProcessDefinition buildProcessDefintionFromPdlFile(String jpdlFile) {
        log(jpdlFile);
        ProcessDefinition pd = ProcessDefinition.parseXmlResource(jpdlFile);
        return pd;
    }


    protected void releaseAndUnbindHibernateSession(Session session) {
        TransactionSynchronizationManager
                .unbindResource(getHibernateSessionFactory());
        SessionFactoryUtils.releaseSession(session ,
                getHibernateSessionFactory());
    }

    protected Session createAndBindHibernateSession() {
        Session session =
            SessionFactoryUtils.getSession(this
                .getHibernateSessionFactory(), true);
        TransactionSynchronizationManager.bindResource(this
                .getHibernateSessionFactory(), new SessionHolder(session));

        return session;
    }

    protected SessionFactory getHibernateSessionFactory() {
        return (SessionFactory) getSpringBean(ServiceNames.HIBERNATE_SESSION_FACTORY);
    }

    protected Object getSpringBean(String beanName) {
        return SpringUtilities
                .getInstance().getBeanFactory().getBean(
                        beanName);
    }

    protected Process buildNewProcessObject(Actor owner, String expectedProcessName) {
        return buildProcessObject(owner, expectedProcessName, false);
    }

    protected Process buildDeployedProcessObject(Actor owner, String expectedProcessName) {
        return buildProcessObject(owner, expectedProcessName, true);
    }

    protected Process buildProcessObjectWithVersions(Actor owner,
            String processName, boolean deployed) {
        /*
         * build the process object
         */
        Set actorsSet = new HashSet();
        actorsSet.add(owner);
        Actors owners = new Actors(actorsSet);


        Process processDetails = new com.mckesson.eig.workflow.process.api.Process();
        processDetails.setOwners(owners);

        Date date = new Date();
        processDetails.setCreateDateTime(date);
        processDetails.setModifiedDateTime(date);
        processDetails.setModifiedUserId(owner.getActorID());
        Set<ProcessVersion> processVersions = new HashSet<ProcessVersion>();
        ProcessVersion v1 = buildProcessVersion(processName, deployed, processDetails);
        processVersions.add(v1);
        ProcessVersion v2 = buildProcessVersion(processName, deployed, processDetails);
        v2.setVersionId(new Long(2));
        processVersions.add(v2);
        processDetails.setProcessVersions(processVersions);
        return processDetails;
    }

    private ProcessVersion buildProcessVersion(String processName, boolean deployed,
            Process processDetails) {
        ProcessVersion processVersion = new ProcessVersion();
        processVersion.setProcessName(processName);
        processVersion.setProcessDescription("testCreateProcess- Junit test case ");
        processVersion.setNewStatus();

        if (deployed) {
            processVersion.setDeployedStatus();
        }
        /**
         * build a process definition object
         */

        String jpdlFile =
            "com/mckesson/eig/workflow/process/test/testtaskprocess/processdefinition.xml";
        ProcessDefinition pd = buildProcessDefintionFromPdlFile(jpdlFile);

        /**
         * Convert the PD to xml to store as CLOB
         */
        String xml = convertToXml(pd);
        log(xml);


        try {
            processVersion.setProcessDefinition(xml);
            processVersion.setProcessGraph(xml);
        } catch (SAXException e) {
            throw new ApplicationException(e);
        } catch (ParserConfigurationException e) {
            throw new ApplicationException(e);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
        processVersion.setVersionId(new Long(1));

        Set<ProcessAttribute> processAttributes = new HashSet<ProcessAttribute>();
        ProcessAttribute attribute = new ProcessAttribute();
        attribute.setAttributeName("USE_CONTENT");
        attribute.setAttributeName("OPTIONAL");
        processAttributes.add(attribute);
        processVersion.setProcessAttributes(processAttributes);
        processVersion.setProcess(processDetails);
        return processVersion;
    }

}
