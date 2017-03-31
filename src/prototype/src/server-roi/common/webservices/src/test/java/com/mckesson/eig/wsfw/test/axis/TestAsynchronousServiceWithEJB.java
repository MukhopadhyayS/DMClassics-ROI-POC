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

package com.mckesson.eig.wsfw.test.axis;

import java.io.File;
import java.io.InputStream;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.mockejb.MDBDescriptor;
import org.mockejb.MockContainer;
import org.mockejb.interceptor.AspectSystem;
import org.mockejb.interceptor.AspectSystemFactory;
import org.mockejb.interceptor.ClassPointcut;
import org.mockejb.interceptor.InvocationRecorder;
import org.mockejb.jndi.MockContextFactory;

import com.mckesson.eig.wsfw.axis.ServiceQueueListener;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * Testcase which tests an asynchronous web service that inserts the HTTP SOAP
 * request into a JMS queue for later processing.
 * 
 */
public class TestAsynchronousServiceWithEJB extends TestCase {

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
        UnitSpringInitialization.init();

        File webxml = new File("WEB-INF/web.xml");
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
            InvocationRecorder recorder = setupTestQueue();
            _requestBuilder.setOperationData("isAnybodyOutThere",
                    "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder
                    .buildSoapRequestWithSecurityHeader("system", "admin");
            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/helloAsynch",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
            XMLProcessor xpath = new XMLProcessor(response.getText());
            String baseQuery = "//soapenv:Envelope/soapenv:Body/"
                    + "eig:isAnybodyOutThereResponse";
            // note that true is returned telling that message was
            // successfully put in the queue.
            assertEquals("true", xpath.getValue(baseQuery
                    + "/eig:isAnybodyOutThereResult"));
            // make sure that the MDB received a message.
            assertNotNull(recorder
                    .findByTargetMethod("ServiceQueueListener.onMessage"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Create a test queue using MockEJB.
     * 
     * @throws NamingException
     */
    private InvocationRecorder setupTestQueue() throws NamingException,
            JMSException {
        // Setup JNDI context using MockEJB
        MockContextFactory.setAsInitial();
        Context ctx = new InitialContext();
        MockContainer mockContainer = new MockContainer(ctx);

        // Setup the Active MQ queue
        ConnectionFactory factory = new ActiveMQConnectionFactory(
                "vm://localhost");
        ctx.rebind("java:activemq/QueueConnectionFactory", factory);
        Queue queue = new ActiveMQQueue("queue.testQueue");
        ctx.rebind("queue/testQueue", queue);

        // Define env-entry settings for the MDB
        ctx.rebind("java:comp/env/axis-server-config",
                "server-config.wsdd");
        ctx.rebind("java:comp/env/target-service", "helloJmsProcessor");

        // Create and deploy the MDB that listens on the queue
        ServiceQueueListener beanObj = new ServiceQueueListener();
        MDBDescriptor foreignProviderMDBDescriptor = new MDBDescriptor(
                "java:activemq/QueueConnectionFactory", "queue/testQueue",
                beanObj);
        foreignProviderMDBDescriptor.setIsAlreadyBound(true);
        mockContainer.deploy(foreignProviderMDBDescriptor);

        // Use MockEJB AOP to make sure the MDB received a msg.
        InvocationRecorder recorder = new InvocationRecorder();
        AspectSystem aspectSystem = AspectSystemFactory.getAspectSystem();
        aspectSystem.add(new ClassPointcut(MessageListener.class, false),
                recorder);
        return recorder;
    }

}
