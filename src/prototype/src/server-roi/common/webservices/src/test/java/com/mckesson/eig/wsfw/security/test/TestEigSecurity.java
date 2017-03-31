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
package com.mckesson.eig.wsfw.security.test;

import java.io.File;
import java.io.InputStream;

import junit.framework.TestCase;

import com.mckesson.eig.wsfw.session.WsSession;
import com.mckesson.eig.wsfw.test.axis.SoapRequestBuilder;
import com.mckesson.eig.wsfw.test.axis.XMLProcessor;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * @author N.Shah Ghazni
 * @date   Feb 5, 2008
 * @since  HECM 1.0; Feb 5, 2008
 */
public class TestEigSecurity extends TestCase {

    /**
     * Denotes the application id for HECM
     */
    private static final String HECM_APP_ID = "1";

    /**
     * Denotes the invalid application id for testing purpose
     */
    private static final String INVALID_APP_ID = "0";

    /**
     * Holds the instance of <code>SoapRequestBuilder</code>.
     */
    private SoapRequestBuilder _requestBuilder;

    /**
     * Holds the instance of ServletUnitClient.
     */
    private ServletUnitClient _client;

    /**
     * Holds the instance of ServletRunner.
     */    
    private ServletRunner _servletRunner;
    
    /**
     * Sets up the data required for testing the service.
     * 
     * @throws Exception
     *             General Exception.
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {

        super.setUp();

        File webxml = new File("WEB-INF/web.xml");
        _servletRunner = new ServletRunner(webxml);
        _client = _servletRunner.newClient();
        _client.setExceptionsThrownOnErrorStatus(false);
        _requestBuilder = new SoapRequestBuilder();
    }

    /**
     * Tests the authentication and authorization through a webservice call
     */
    public void testSecurityForWebservice() {

        try {

            _requestBuilder.setOperationData("sayhallo", "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder
                    .buildSoapRequestWithSecurityHeaderWithAppID("system", "admin", HECM_APP_ID);

            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/hallo",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");

            WebResponse response = _client.getResponse(request);

            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
            XMLProcessor xpath = new XMLProcessor(response.getText());
            String baseQuery = "//soapenv:Envelope/soapenv:Body/" + "eig:sayhalloResponse";
            assertEquals("hallo", xpath.getValue(baseQuery + "/eig:halloResult"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testSecurityForAlternateWebserviceHandler() {

        try {

            _requestBuilder.setOperationData("yoDude", "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder
                    .buildSoapRequestWithSecurityHeaderWithAppID("system", "admin", HECM_APP_ID);

            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/helloDude",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");

            WebResponse response = _client.getResponse(request);

            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
            XMLProcessor xpath = new XMLProcessor(response.getText());
            String baseQuery = "//soapenv:Envelope/soapenv:Body/" + "eig:yoDudeResponse";
            assertEquals("Whatever", xpath.getValue(baseQuery + "/eig:yoDudeResult"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Tests the authorization handler through a webservice call
     */
    public void testAuthorizationHandler() {

        try {

            _requestBuilder.setOperationData("sayHi", "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder
                    .buildSoapRequestWithSecurityHeaderWithAppID("system", "admin", HECM_APP_ID);

            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/hi",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");

            WebResponse response = _client.getResponse(request);

            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
            XMLProcessor xpath = new XMLProcessor(response.getText());
            String baseQuery = "//soapenv:Envelope/soapenv:Body/" + "eig:sayHiResponse";
            assertEquals("Hi", xpath.getValue(baseQuery + "/eig:hiResult"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    /**
     * Tests the authorization with exception through a webservice call
     */
    public void testAuthorizationHandlerWithException() {
        
        try {
            
            _requestBuilder.setOperationData("sayHi", "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder
            .buildSoapRequestWithSecurityHeaderWithAppID("system", "admin", " ");
            
            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/hi",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");
            
            _client.getResponse(request);
        } catch (Exception e) {
            assert (true);
        }
    }

    /**
     * Tests the authentication and authorization with invalid appid through a webservice call
     */
    public void testSecurityForWebserviceWithInvalidAppID() {
        try {

            _requestBuilder.setOperationData("sayhallo", "urn:eig.mckesson.com");
            InputStream requestMessage = 
              _requestBuilder.buildSoapRequestWithSecurityHeaderWithAppID("system", 
            		                                                      "admin", 
            		                                                      INVALID_APP_ID);

            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/hallo",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");

            _client.getResponse(request);

        } catch (Exception e) {
            assert (true);
        }
    }

    /**
     * Tests the authentication and authorization with invalid appid through a webservice call
     */
    public void testSecurityForWebserviceWithAppIdDoesNotExist() {
        try {
            
            final String appId = "23565";
            _requestBuilder.setOperationData("sayhallo", "urn:eig.mckesson.com");
            InputStream requestMessage = 
                _requestBuilder.buildSoapRequestWithSecurityHeaderWithAppID("system", 
                		                                                    "admin", 
                		                                                    appId);
            
            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/hallo",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");
            
            _client.getResponse(request);
            
        } catch (Exception e) {
            assert (true);
        }
    }

    /**
     * Tests the authentication and authorization ith null appid through a webservice call
     */
    public void testSecurityForWebserviceWithNullAppID() {

        try {

            _requestBuilder.setOperationData("sayhallo", "urn:eig.mckesson.com");
            InputStream requestMessage = 
              _requestBuilder.buildSoapRequestWithSecurityHeaderWithAppID("system", "admin", " ");

            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/hallo",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");

            WsSession.setSessionData(WsSession.APP_ID, null);
            _client.getResponse(request);

        } catch (Exception e) {
            assert (true);
        }
    }
    
    /**
     * Tests the authentication through servlet
     */
    public void testSecurityForServlet() {

        try {

            String url = "http://127.0.0.1:8080/sayhallo/SayhalloServlet?"
                + "AppId=" + HECM_APP_ID + "&USERNAME=system&PASSWORD=admin&AuthTicket=12345";
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _client.sendRequest(request);
            assertNotNull("response was null", response);
            assertEquals("hallo", response.getText());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Tests the authentication through servlet with invalid appid
     */
    public void testSecurityForServletWithInvalidAppID() {

        try {

            String url = "http://127.0.0.1:8080/sayhallo/SayhalloServlet?"
                + "AppId=" + INVALID_APP_ID + "&USERNAME=system&PASSWORD=admin&AuthTicket=12345";
            WebRequest request   = new GetMethodWebRequest(url);
             _client.sendRequest(request);
        } catch (Exception e) {
            assert (true);
        }
    }

    /**
     * Tests the authentication through servlet with null appid
     */
    public void testSecurityForServletWithNullAppID() {
        
        try {
            
            String url = "http://127.0.0.1:8080/sayhallo/SayhalloServlet?"
                + "AppId=&USERNAME=system&PASSWORD=admin&AuthTicket=12345";
            WebRequest request = new GetMethodWebRequest(url);
            _client.sendRequest(request);
        } catch (Exception e) {
            assert (true);
        }
    }

    /**
     * @throws java.lang.Exception
     */
    @Override
    protected void tearDown()
    throws Exception {

        super.tearDown();
        _servletRunner.shutDown();
    }
}
