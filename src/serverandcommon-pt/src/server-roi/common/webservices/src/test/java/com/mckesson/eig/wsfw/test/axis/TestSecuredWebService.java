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
import java.util.List;

import junit.framework.TestCase;


import com.mckesson.eig.iws.security.Ticket;
import com.mckesson.eig.wsfw.model.authentication.AuthenticatedResult;
import com.mckesson.eig.wsfw.session.WsSession;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * Testcase which tests a secured web service. The web service is deployed using
 * the WsSecurity handlers to validate username and passwords.
 * 
 */
public class TestSecuredWebService extends TestCase {

    /**
     * variable holding the size of the room list defined in
     * <code>HouseService</code> class.
     */
    private static final int ROOM_SIZE = 14;
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
     * Sends the SOAP message without the security header and checks the
     * faultCode in the response.
     */
    public void testNoSecurityHeader() {
        try {
            _requestBuilder.setOperationData("getMansion",
                    "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder.buildSoapRequest();
            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/secureHouse",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
            XMLProcessor xpath = new XMLProcessor(response.getText());
            String baseQuery = "//soapenv:Envelope/soapenv:Body/soapenv:Fault";
            String faultCode = xpath.getValue(baseQuery + "/faultcode");
            assertEquals("soapenv:Client", faultCode);
            String faultString = xpath.getValue(baseQuery + "/faultstring");
            assertEquals("UsernameTokenException", faultString);
 
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    
    /**
     * It validates security service by passing a username which is NULL or
     * empty and checks the faultcode for security message.
     */
    public void testEmptyUsername() {
        try {
            _requestBuilder.setOperationData("getMansion",
                    "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder
                    .buildSoapRequestWithSecurityHeader("", "gank");
            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/secureHouse",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
            XMLProcessor xpath = new XMLProcessor(response.getText());
            String baseQuery = "//soapenv:Envelope/soapenv:Body/soapenv:Fault";
            String faultCode = xpath.getValue(baseQuery + "/faultcode");
            assertEquals("soapenv:Client", faultCode);
            String faultString = xpath.getValue(baseQuery + "/faultstring");
            assertEquals("UsernameTokenException", faultString);
            String detailMessages = xpath.getValue(baseQuery
                    + "/detail/eig:message");
            assertTrue(detailMessages
                    .indexOf("Username and/or password is NULL!!") >= 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * It validates security service by passing an invalid username and password
     * and checks the faultcode for security message.
     */
    public void testBadUsername() {
        try {
            _requestBuilder.setOperationData("getMansion",
                    "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder
                    .buildSoapRequestWithSecurityHeader("Mckesson22",
                            "mckesson");
            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/secureHouse",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
            XMLProcessor xpath = new XMLProcessor(response.getText());
            String baseQuery = "//soapenv:Envelope/soapenv:Body/soapenv:Fault";
            String faultCode = xpath.getValue(baseQuery + "/faultcode");
            assertEquals("soapenv:Server", faultCode);
            String faultString = xpath.getValue(baseQuery + "/faultstring");
            assertEquals("InvalidUserException", faultString);
            String detailMessages = xpath.getValue(baseQuery
                    + "/detail/eig:message");
            assertTrue(detailMessages.indexOf("Invalid User Credentials") > 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * It validates security service by passing an invalid password and checks
     * the faultcode for security message.
     */
    public void testBadPassword() {
        try {
            _requestBuilder.setOperationData("getMansion",
                    "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder
                    .buildSoapRequestWithSecurityHeader("system", "gank");
            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/secureHouse",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
            XMLProcessor xpath = new XMLProcessor(response.getText());
            String baseQuery = "//soapenv:Envelope/soapenv:Body/soapenv:Fault";
            String faultCode = xpath.getValue(baseQuery + "/faultcode");
            assertEquals("soapenv:Server", faultCode);
            String faultString = xpath.getValue(baseQuery + "/faultstring");
            assertEquals("NotAuthenticatedException", faultString);
            String detailMessages = xpath.getValue(baseQuery
                    + "/detail/eig:message");
            assertTrue(detailMessages
                    .indexOf("User account is not authenticated") > 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * It sets the right username and password and validates the data retrieved.
     */
    public void testValidCredentials() {
        try {
            _requestBuilder.setOperationData("getMansion",
                    "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder
                    .buildSoapRequestWithSecurityHeader("system", "admin");
            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/secureHouse",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
            XMLProcessor xpath = new XMLProcessor(response.getText());
            String baseQuery = "//soapenv:Envelope/soapenv:Body/"
                    + "eig:getMansionResponse/eig:house";
            assertEquals("22 Tuxedo Dr.", xpath.getValue(baseQuery
                    + "/eig:streetAddress"));
            assertEquals("Atlanta", xpath.getValue(baseQuery + "/eig:city"));
            assertEquals("GA", xpath.getValue(baseQuery + "/eig:state"));
            assertEquals("30000", xpath.getValue(baseQuery + "/eig:zip"));
            List rooms = xpath.getValuesAsList(baseQuery
                    + "/eig:rooms/eig:room/eig:name");
            assertEquals(ROOM_SIZE, rooms.size());
            assertTrue(rooms.contains("kitchen"));
            assertTrue(rooms.contains("master bath"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test a service that uses the LogonCallbackHandler.
     * 
     */
    public void testLogonService() {
        try {
            _requestBuilder.setOperationData("hello", "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder
                    .buildSoapRequestWithSecurityHeader("system", "admin");
            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/helloLogon",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
            XMLProcessor xpath = new XMLProcessor(response.getText());
            String baseQuery = "//soapenv:Envelope/soapenv:Body/"
                    + "eig:helloResponse";
            assertEquals("Hello World", xpath.getValue(baseQuery
                    + "/eig:helloResult"));
            AuthenticatedResult result = (AuthenticatedResult) WsSession
                    .getSessionData(WsSession.AUTHRESULT);
            assertTrue(Ticket.isValid(result.getTicket(), "system"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test a service that uses the LogoffCallbackHandler. No password is
     * required to access the challenge services.
     * 
     */
    public void testLogoffService() {
        try {
            _requestBuilder.setOperationData("isAnybodyOutThere",
                    "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder
                    .buildSoapRequestWithSecurityHeader("system", "");
            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/helloLogoff",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
            XMLProcessor xpath = new XMLProcessor(response.getText());
            String baseQuery = "//soapenv:Envelope/soapenv:Body/"
                    + "eig:isAnybodyOutThereResponse";
            assertEquals("false", xpath.getValue(baseQuery
                    + "/eig:isAnybodyOutThereResult"));
            assertEquals("system", WsSession
                    .getSessionData(WsSession.USER_NAME));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test a service that uses the LogoffCallbackHandler. No password is
     * required to access the challenge services, but username is required. We
     * should get a fault back.
     * 
     */
    public void testLogoffServiceWithEmptyUsername() {
        try {
            _requestBuilder.setOperationData("isAnybodyOutThere",
                    "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder
                    .buildSoapRequestWithSecurityHeader("", "");
            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/helloLogoff",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
            XMLProcessor xpath = new XMLProcessor(response.getText());
            String baseQuery = "//soapenv:Envelope/soapenv:Body/soapenv:Fault";
            String faultCode = xpath.getValue(baseQuery + "/faultcode");
            assertEquals("soapenv:Client", faultCode);
            String faultString = xpath.getValue(baseQuery + "/faultstring");
            assertEquals("UsernameTokenException", faultString);
            String detailMessages = xpath.getValue(baseQuery
                    + "/detail/eig:message");
            assertTrue(detailMessages.indexOf("Username is NULL!!") >= 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
