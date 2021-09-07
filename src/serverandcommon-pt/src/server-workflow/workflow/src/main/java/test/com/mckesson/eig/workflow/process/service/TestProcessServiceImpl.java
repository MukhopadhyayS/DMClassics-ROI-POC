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

import java.util.Set;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mckesson.eig.utility.testing.UnitTest;
import com.mckesson.eig.workflow.Utilities.ServiceNames;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.process.api.Process;
import com.mckesson.eig.workflow.process.api.ProcessVersion;
import com.mckesson.eig.workflow.process.dao.BaseProcessTest;
import com.mckesson.eig.workflow.process.dao.ProcessDAO;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author epvk3hf
 *
 */
public class TestProcessServiceImpl extends BaseProcessTest {

    private static final int THIRTY = 30;
    private ProcessServiceImpl _processService = null;
    private Actor _actor = null;
    private ProcessDAO _dao;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
        init();
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(new Long(1));

        _actor = new Actor();
        _actor.setActorID(THIRTY);
        _actor.setEntityType(Actor.DOMAIN_ENTITY_TYPE);
        _actor.setAppID(UnitTest.ONE);
        _actor.setEntityID(THIRTY);

        _processService = (ProcessServiceImpl) this
                .getSpringBean(ServiceNames.PROCESS_SERVICE);
        assertNotNull(_processService);

        // Set up the session for the Tests
        _dao = (ProcessDAO) this.getDAO(ProcessDAO.class.getName());
        HibernateTemplate hTemplate = new HibernateTemplate(
                getHibernateSessionFactory());
        _dao.setHibernateTemplate(hTemplate);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	    _processService = null;
	    _actor = null;
	}


    public void testCreateProcessSuceess() {
        String processName = generateUniqueName("CreateProcessTest");
        Process process = buildNewProcessObject(_actor, processName);
        try {
            _processService.createProcess(_actor, process);
        } catch (Exception e) {
            fail("Should be able to create a process");
        }

        //clean the data created for the test
        _processService.deleteProcess(_actor, process);

    }

    public void testGetProcessSuccess() {

        String expectedProcessName = generateUniqueName("GetProcessTest");
        Process expectedProcessDetails = buildProcessObject(_actor, expectedProcessName, false);
        //Create the process
        createProcessForTest(expectedProcessDetails);

        //get the process
        long processId = expectedProcessDetails.getProcessId();
        Session session = this.createAndBindHibernateSession();
        Process p = _processService.getProcess(_actor, processId);

        verifyProcess(p);

        verifyProcessVersion(expectedProcessName, p);

        this.releaseAndUnbindHibernateSession(session);
        // clean the data created for the test
        _processService.deleteProcess(_actor, expectedProcessDetails);

    }

    private void verifyProcess(Process p) {
        assertNotNull(p);
        assertTrue("primary key assigned", p.getProcessId() > 0);
    }

    public void testDeployProcess() {
        String processName = generateUniqueName("testDeployProcess");
        Process expectedProcessDetails = buildProcessObject(_actor, processName, false);
        //Create the process
        createProcessForTest(expectedProcessDetails);

        long pId = expectedProcessDetails.getProcessId();
        Process fetchedProcess = _processService.getProcess(_actor, pId);
        verifyProcess(fetchedProcess);

        try {
            this._processService.deployProcess(_actor, expectedProcessDetails);
        } catch (Exception e) {
            fail();
        }
    }

    public void testSaveProcess() {
        Session session = this.createAndBindHibernateSession();
        String expectedProcessName = generateUniqueName("UpdateProcessTest");
        Process expectedProcessDetails = buildNewProcessObject(this._actor, expectedProcessName);
        expectedProcessDetails .getProcessVersions();
        //Create the process
        createProcessForTest(expectedProcessDetails);

        //get the process
        long processId = expectedProcessDetails.getProcessId();
        Process p = _processService.getProcess(_actor, processId);
        verifyProcess(p);

        ProcessVersion version = verifyProcessVersion(expectedProcessName, p);

        modifyProcessVersion(version);

        //Save
        try {
            _processService.saveProcess(_actor, p);
        } catch (Exception e) {
            fail();
        } finally {
            this.releaseAndUnbindHibernateSession(session);
        }
    }

    public void testUpdatingAnDeployedProcess() {
        Session session = this.createAndBindHibernateSession();
        String expectedProcessName = generateUniqueName("UpdateDeployedProcessTest");
        Process expectedProcessDetails = buildDeployedProcessObject(
                this._actor, expectedProcessName);

        //Create the process
        createProcessForTest(expectedProcessDetails);
        verifyProcess(expectedProcessDetails);

        //get the process
        long processId = expectedProcessDetails.getProcessId();
        Process p = _processService.getProcess(_actor, processId);
        verifyProcess(p);

        ProcessVersion version = verifyProcessVersion(expectedProcessName, p);

        modifyProcessVersion(version);

        //Save
        try {
            _processService.saveProcess(_actor, p);
        } catch (Exception e) {
            fail();
        } finally {
            this.releaseAndUnbindHibernateSession(session);
        }
    }

    private void modifyProcessVersion(ProcessVersion version) {
        version.setProcessDescription(version.getProcessDescription()
                + "Updated");
    }

    private ProcessVersion verifyProcessVersion(String expectedProcessName,
            Process p) {
        Set<ProcessVersion> versions = p.getProcessVersions();
        assertTrue(versions.size()  ==  1);
        ProcessVersion version = versions.iterator().next();
        assertEquals(expectedProcessName, version.getProcessName());
        return version;
    }

    private void createProcessForTest(Process processDetails) {
        try {
            this._processService.createProcess(_actor, processDetails);
        } catch (Exception e) {
            fail("Should be able to create a process");
        }
    }
}
