/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and/or one of its subsidiaries and is protected
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.utility.jms;

import java.awt.Button;

import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.QueueSender;

import junit.framework.TestCase;

/**
 * Test case to test the class JmsService.
 *
 */
public class TestJmsService extends TestCase {

    /**
     * Holds the reference of the JmsService class.
     */
    private JmsService _service;

    /**
     * Holds the reference of the JmsConfig class.
     */
    private static JmsConfig _config;

    /**
     * Represent String type constant.
     */
    private String _testMessage = "This is our test Message";

    private static int _mode = 1;

    public TestJmsService(String arg0) {
        super(arg0);
    }
    /**
     * Set up the test. Creates an instance of the class that needs to be
     * tested.
     *
     * @throws Exception
     *             if the set up is not made properly.
     */
    protected void setUp() throws Exception {
        super.setUp();
        InitResources.getInstance().initialiseContainer();
        _config = new JmsConfig(JmsConfig.URL, "java:comp/env/queue/eigAudit",
                JmsConfig.CONNECTION_FACTORY);
        _config.setPhysicalName("queue/eigAudit");
        _service = new JmsService(_config);

    }
    /**
     * This will remove all the data associated with the test.
     *
     * @throws Exception
     *             if the tear down is not made properly.
     */
    protected void tearDown() throws Exception {
        super.tearDown();

    }

    /**
     * Test method to test the constructor.
     */
    public void testJmsService() {
        _service = new JmsService();
        assertNotNull(_service);
    }

    /**
     * Tests the lookupConnectionFactory method.
     *
     * @return an instance of the QueueConnectionFactory
     */
    public QueueConnectionFactory getConnectionFactory() {

        return (_service.lookupConnectionFactory(_service.getDefaultContext()));
    }

    /**
     * This method tests a connection.
     *
     * @return a QueueConnection
     */
    public QueueConnection getConnection() {
        return (_service.createConnection(getConnectionFactory()));
    }
    /**
     * Tests if a session is created.
     *
     * @return a session
     */
    public QueueSession getSession() {
        return (_service.createSession(getConnection(), 1));
    }
    /**
     * Tests the method getConnectionFactory().
     */
    public void testQueueConnectionFactory() {
        assertNotNull(getConnectionFactory());
    }

    /**
     * Tests the method getConnection().
     */
    public void testCreateQueueConnection() {
        assertNotNull(getConnection());
    }
    /**
     * Tests the method getSession().
     */
    public void testQueueSession() {
        assertNotNull(getSession());
    }
    /**
     * Tests the start() method.
     */
    public void testStart() {
        _service.start(getConnection());
    }
    /**
     * Tests the stop() method.
     */
    public void testStop() {
        _service.stop(getConnection());
    }

    /**
     * Tests the close() method.
     */
    public void testCloseQueueSession() {
        _service.close(getSession());
    }

    /**
     * Tests the method close for connection.
     */
    public void testCloseConnection() {
        _service.close(getConnection());
    }

    /**
     * Tests the createTextMessage() method.
     */
    public void testCreateTextMessage() {
        _service.createTextMessage(getSession(), _testMessage);
    }

    /**
     * Tests the createObjectMessage() method.
     */
    public void testCreateObjectMessage() {
        _service.createObjectMessage(getSession(), _testMessage);
    }

    /**
     * Tests the sendMessage() method.
     */
    public void testSendMessage() {
        _service.sendMessage(_testMessage);
    }

    /**
     * Tests the sendMessage() method with parameters - context,queue name and
     * the message.
     */
    public void testSendMessageWithContext() {
        _service.sendMessage(_service.getDefaultContext(), "queue/eigAudit",
                _testMessage);
    }

    /**
     * Tests the sendMessage()with parameters - context,queue name and a
     * serializable object.
     */
    public void testSendMessageWithObject() {
        _service.sendMessage(_service.getDefaultContext(), "queue/eigAudit",
                new Button());
    }

    public void testGetObject() {
        ObjectMessage msg = _service.createObjectMessage(getSession(),
                _testMessage);
        Object ret = _service.getObject(msg);
        assertEquals(_testMessage, ret);
    }

    public void testGetObjectFail() {
        try {
            _service.getObject(null);
            fail("Exception was expected");
        } catch (JmsException ex) {
            assertNotNull(ex);
        }
    }

    public void testCreateConnectionException() {
        try {
            _service.createConnection(null);
            fail("Exception was expected");
        } catch (JmsException ex) {
            assertNotNull(ex);
        }
    }

    public void testCreateSessionException() {
        try {
            _service.createSession(null, true, _mode);
            fail("Exception was expected");
        } catch (JmsException ex) {
            assertNotNull(ex);
        }
    }

    public void testStartException() {
        try {
            _service.start(null);
            fail("Exception was expected");
        } catch (JmsException ex) {
            assertNotNull(ex);
        }
    }

    public void testStopException() {
        try {
            _service.stop(null);
            fail("Exception was expected");
        } catch (JmsException ex) {
            assertNotNull(ex);
        }
    }

    public void testCreateSenderException() {
        try {
        	Queue q = null;
            _service.createSender(null, q);
            fail("Exception was expected");
        } catch (JmsException ex) {
            assertNotNull(ex);
        }
    }

    public void testCreateTextMessageException() {
        try {
            _service.createTextMessage(null, _testMessage);
            fail("Exception was expected");
        } catch (JmsException ex) {
            assertNotNull(ex);
        }
    }

    public void testCreateObjectMessageException() {
        try {
            _service.createObjectMessage(null, null);
            fail("Exception was expected");
        } catch (JmsException ex) {
            assertNotNull(ex);
        }
    }

    public void testSendException() {
        try {
            _service.send(null, null);
            fail("Exception was expected");
        } catch (JmsException ex) {
            assertNotNull(ex);
        }
    }

    public void testCloseQSException() {
        try {
            _service.close((QueueSession) null);
            fail("Exception was expected");
        } catch (JmsException ex) {
            assertNotNull(ex);
        }
    }

    public void testCloseQCException() {
        try {
            _service.close((QueueConnection) null);
            fail("Exception was expected");
        } catch (JmsException ex) {
            assertNotNull(ex);
        }
    }

    public void testCloseQueueSenderException() {
        try {
            _service.close((QueueSender) null);
            fail("Exception was expected");
        } catch (JmsException ex) {
            assertNotNull(ex);
        }
    }

    public void testLookupConnectionFactoryException() {
        try {
            _service.lookupConnectionFactory(null);
            fail("Exception was expected");
        } catch (JmsException ex) {
            assertNotNull(ex);
        }
    }

    public void testLookupQueueException() {
        try {
            _service.lookupQueue(null, null);
            fail("Exception was expected");
        } catch (JmsException ex) {
            assertNotNull(ex);
        }
    }

    public void testVerifyQueueException() {
        try {
            _service.verifyQueue(null, null);
            fail("Exception was expected");
        } catch (NullPointerException ex) {
            assertNotNull(ex);
        }
    }
}
