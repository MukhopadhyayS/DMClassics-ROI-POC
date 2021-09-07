/*
 * Copyright 2007-2009 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.workflow.test;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mockejb.jndi.MockContextFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.mckesson.eig.utility.util.DateUtilities;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.workflow.Utilities.ServiceNames;
import com.mckesson.eig.workflow.dao.AbstractWorkflowDAO;
import com.mckesson.eig.workflow.process.service.ProcessListServiceImpl;
import com.mckesson.eig.workflow.service.WorkflowService;


/**
 * @author Pranav Amarasekaran
 * @date   Sep 09, 2007
 * @since  HECM 1.0
 */
public abstract class AbstractWorkflowTestCase
extends junit.framework.TestCase {

    /**
     * Location of the Spring configuration file.
     */
    private static final String CONFIG_OFS =
        "com/mckesson/eig/workflow/test/workflow.app.context.oracle.ofs.xml";
    private static final String CONFIG_MCK =
        "com/mckesson/eig/workflow/test/workflow.app.context.oracle.mck.xml";
    private static final String CONFIG_LOCAL =
        "com/mckesson/eig/workflow/test/workflow.app.context.oracle.local.xml";

    protected static final String WORKLIST_MANAGER =
        "com.mckesson.eig.workflow.worklist.service.WorklistServiceImpl";

    protected static final String TASK_MANAGER =
        "com.mckesson.eig.workflow.worklist.service.TaskServiceImpl";

    protected static final String PROCESS_LIST_MANAGER = ProcessListServiceImpl.class.getName();

    private Context _ctx = null;

    /**
     * BeanFactory that holds the instance of the beans loaded by Spring.
     */
    private static BeanFactory _wfBeanFactory;

    public AbstractWorkflowTestCase() {
        super();
    }

    public AbstractWorkflowTestCase(String name) {
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

         _wfBeanFactory = new ClassPathXmlApplicationContext(configFile);

        // to initialize logger
        _wfBeanFactory.getBean("log_initializer");
        SpringUtilities.getInstance().setBeanFactory(_wfBeanFactory);
        System.setProperty("unit.test", "true");
    }

    protected WorkflowService getManager(String serviceName) {
        return (WorkflowService) SpringUtilities
                            .getInstance()
                            .getBeanFactory()
                            .getBean(serviceName);
    }

    protected AbstractWorkflowDAO getDAO(String daoName) {
        return (AbstractWorkflowDAO) SpringUtilities
                            .getInstance()
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

            // Setup the Active MQ Topic
            ctx.rebind("java:activemq/TopicConnectionFactory", factory);
            Topic topic = new ActiveMQTopic("topic.testTopic");
            ctx.rebind("topic/testTopic", topic);
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

    public static String generateUniqueName(String prefix) {
        long time  = DateUtilities.getGmtTimeInMillis();
        String uniqueName = prefix + Long.toString(time);
        log(uniqueName);
        return uniqueName;
    }

    protected SessionFactory getHibernateSessionFactory() {
        return (SessionFactory) getSpringBean(ServiceNames.HIBERNATE_SESSION_FACTORY);
    }

    protected Object getSpringBean(String beanName) {
        return SpringUtilities
                .getInstance().getBeanFactory().getBean(
                        beanName);
    }

    protected Session createAndBindHibernateSession() {
        Session session =
            SessionFactoryUtils.getSession(this
                .getHibernateSessionFactory(), true);
        TransactionSynchronizationManager.bindResource(this
                .getHibernateSessionFactory(), new SessionHolder(session));

        return session;
    }

    protected void releaseAndUnbindHibernateSession(Session session) {
        TransactionSynchronizationManager
                .unbindResource(getHibernateSessionFactory());
        SessionFactoryUtils.releaseSession(session ,
                getHibernateSessionFactory());
    }

}
