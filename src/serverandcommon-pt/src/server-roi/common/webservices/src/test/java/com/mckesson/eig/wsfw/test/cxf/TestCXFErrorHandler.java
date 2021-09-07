/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
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

package com.mckesson.eig.wsfw.test.cxf;

import java.io.File;
import java.io.InputStream;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.mockejb.jndi.MockContextFactory;

import com.mckesson.eig.wsfw.test.SoapRequestBuilder;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * @author sahuly
 * @date   Dec 29, 2008
 * @since  HECM 1.0; Dec 29, 2008
 */
public class TestCXFErrorHandler extends TestCase {

    /**
     * Holds the instance of <code>SoapRequestBuilder</code>.
     */
    private SoapRequestBuilder _requestBuilder;

    /**
     * Holds the instance of ServletUnitClient.
     */
    private ServletUnitClient _client;

    /**
     * Sets up the data required for testing the service.
     * 
     * @throws Exception
     *             General Exception.
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {

        super.setUp();
        setupTestQueue();
        File webxml = new File("WEB-INF/testCXFErrorweb.xml");
        ServletRunner servletRunner = new ServletRunner(webxml);
        _client = servletRunner.newClient();
        _client.setExceptionsThrownOnErrorStatus(false);
        _requestBuilder = new SoapRequestBuilder();
    }

    /**
     * Tests an asynchronous service that inserts into a JMS Queue.
     */
    public void testAsynchronousService() {

        try {

            final long waitTime = 1000;
            _requestBuilder.setOperationData("throwUsernameTokenException");
            InputStream requestMessage = _requestBuilder.buildSoapRequestWithTransactionIdHeader();

            WebRequest request = 
                new PostMethodWebRequest("http://hostname.ingored.com/employee.async.error",
                                          requestMessage, 
                                          "text/xml");

            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            Thread.sleep(waitTime);
            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Create a test queue using MockEJB.
     * 
     * @throws NamingException
     */
    private void setupTestQueue() throws NamingException, JMSException {

        // Setup JNDI context using MockEJB
        MockContextFactory.setAsInitial();
        Context ctx = new InitialContext();

        // Setup the Active MQ queue
        ConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
        ctx.rebind("java:activemq/QueueConnectionFactory", factory);
        Queue queue = new ActiveMQQueue("queue.testQueue");
        ctx.rebind("queue/testQueue", queue);
    }
}
