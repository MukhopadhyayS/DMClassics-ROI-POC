/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.log.log4j.appender;

import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.mockejb.MockContainer;
import org.mockejb.jms.MockQueue;
import org.mockejb.jms.QueueConnectionFactoryImpl;
import org.mockejb.jndi.MockContextFactory;

public final class InitResources {
    /* Reference to the singleton object */
    private static InitResources _init;

    private Context _ctx = null;

    /**
     * Private constructor to create a singleton object
     *
     */
    private InitResources() {
    }

    /**
     * Creates a singleton class object for the first time. Everyother
     * invokation returns the same reference
     *
     * @return
     */
    public static InitResources getInstance() {
        synchronized (InitResources.class) {
            if (InitResources._init == null) {
                InitResources._init = new InitResources();
            }
        }
        return InitResources._init;
    }

    /**
     * Setup the MockContainer
     *
     */
    public void initialiseContainer() {

        try {
            // Setup the JNDI environment to use the MockEJB
            // context factory
            MockContextFactory.setAsInitial();
            // Create the initial context that will be used for binding EJBs
            _ctx = new InitialContext();
            // Create an instance of the MockContainer
            new MockContainer(_ctx);
            // Create the initial context that will be used for binding EJBs
            _ctx.rebind("java:/ConnectionFactory", new QueueConnectionFactoryImpl());
            Queue queue = new MockQueue("queue/eigAlert");
            _ctx.rebind("java:comp/env/queue/eigAlert", queue);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
