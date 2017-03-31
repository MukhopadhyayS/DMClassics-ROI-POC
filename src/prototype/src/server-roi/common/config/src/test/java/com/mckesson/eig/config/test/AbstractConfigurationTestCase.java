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
package com.mckesson.eig.config.test;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.mockejb.jndi.MockContextFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mckesson.eig.config.dao.AbstractConfigurationDAOImpl;
import com.mckesson.eig.config.service.ApplicationSettingServiceImpl;
import com.mckesson.eig.config.service.ConfigService;
import com.mckesson.eig.utility.util.SpringUtilities;



public abstract class AbstractConfigurationTestCase
extends junit.framework.TestCase {

    /**
     * Location of the Spring configuration file.
     */
    private static final String CONFIG_OFS =
        "com/mckesson/eig/config/test/configserver.app.context.oracle.ofs.xml";
    private static final String CONFIG_MCK =
        "com/mckesson/eig/config/test/configserver.app.context.oracle.mck.xml";
    private static final String CONFIG_LOCAL =
        "com/mckesson/eig/config/test/configserver.app.context.oracle.local.xml";

   protected static final String APPLICATION_SETTING_MANAGER =
                                ApplicationSettingServiceImpl.class.getName();

    private Context _ctx = null;

    /**
     * BeanFactory that holds the instance of the beans loaded by Spring.
     */
    private static BeanFactory _configBeanFactory;

    public AbstractConfigurationTestCase() {
        super();
    }

    public AbstractConfigurationTestCase(String name) {
        super(name);
    }

    /**
     * Sets the Application Context and loads the test environment
     * for all the workflow test cases.
     * @param ofs
     * @param runLocal
     *
     * @throws Exception when connection fails.
     */
    protected void init() {

        System.setProperty("application.home", System.getenv("JBOSS_HOME") + "\\server\\default");

        boolean local;
        boolean ofs;

        setupTestQueue();
        if (Boolean.getBoolean("from.console")) {
            local = Boolean.getBoolean("run.local");
            ofs = Boolean.getBoolean("run.ofs");
        } else {
            local = true;
            ofs = false;
        }

        String configFile = (local) ? CONFIG_LOCAL
                                    : (ofs) ? CONFIG_OFS : CONFIG_MCK;

         _configBeanFactory = new ClassPathXmlApplicationContext(configFile);

        // to initialize logger
        _configBeanFactory.getBean("log_initializer");
        SpringUtilities.getInstance().setBeanFactory(_configBeanFactory);
        System.setProperty("unit.test", "true");
    }

    protected ConfigService getManager(String serviceName) {
        return (ConfigService) SpringUtilities.getInstance().getBeanFactory().getBean(serviceName);
    }

    protected AbstractConfigurationDAOImpl getDAO(String daoName) {
        return (AbstractConfigurationDAOImpl) SpringUtilities.getInstance()
                                                             .getBeanFactory()
                                                             .getBean(daoName);
    }

    /**
     * Create a test queue using MockEJB.
     *
     * @throws NamingException
     */
    public void setupTestQueue() {

        // Setup JNDI context using MockEJB
        try {

            MockContextFactory.setAsInitial();
            Context ctx = new InitialContext();

            // Setup the Active MQ queue
            ConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
            ctx.rebind("java:activemq/QueueConnectionFactory", factory);
            Queue queue = new ActiveMQQueue("queue.testQueue");
            ctx.rebind("queue/testQueue", queue);
        } catch (NamingException e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * This method registers the queue name with JNDI name in mock
     * container.
     *
     * @throws NamingException
     */
    public void initializeQueue(String queueName) throws NamingException, JMSException {

        // Setup the JNDI environment to use the MockEJB context factory
        MockContextFactory.setAsInitial();
        _ctx = new InitialContext();

        // Create the requested queue
        ConnectionFactory factory = new ActiveMQConnectionFactory(
                "vm://localhost");
        _ctx.rebind("java:activemq/QueueConnectionFactory", factory);
        Queue queue = new ActiveMQQueue("queue." + queueName);
        _ctx.rebind("java:comp/env/queue/" + queueName, queue);
    }


    protected static void log(Object o) {

        if (o instanceof Throwable) {
            ((Throwable) o).printStackTrace(System.err);
        } else {
            System.out.println(o + "");
        }
    }
}
