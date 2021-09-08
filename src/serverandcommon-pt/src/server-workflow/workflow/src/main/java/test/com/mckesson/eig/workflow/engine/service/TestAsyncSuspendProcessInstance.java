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

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.test.AbstractWorkflowTestCase;

/**
 * Unit test class for testing ProcessInstanceEngineImpl start instance
 * functionality.
 */
public class TestAsyncSuspendProcessInstance extends AbstractWorkflowTestCase {

    private static final int APP_ID = 1;
    private static final int ET_DOMAIN = 1;
    private static final long PROCESS_INSTANCE_ID = 1141;

    private static Actor _userActor;
    private static Actors _aclActors;

    protected void setUp() throws Exception {
    	final int domainID = 5001;
        super.setUp();
        init();
        _userActor = new Actor(APP_ID, ET_DOMAIN, domainID);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(_userActor);
        _aclActors = new Actors(actors);

    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSuspendProcessInstance() {

        final String operation = "testAsyncSuspendProcessInstance";

        System.out.println(operation + ">> Start");

        Session session = createAndBindHibernateSession();

        try {

            ProcessInstanceJmsConsumer service = new ProcessInstanceJmsConsumer();

            service.suspendProcessInstance(_userActor, _aclActors, PROCESS_INSTANCE_ID);
        } catch (Exception e) {

            fail("Exception excecuting suspend operation on asynchronous process instance service:"
                                    + PROCESS_INSTANCE_ID);
        } finally {
            releaseAndUnbindHibernateSession(session);
        }

        System.out.println(operation + ">> End");
    }
}

