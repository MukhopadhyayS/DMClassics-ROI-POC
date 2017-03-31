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

import com.mckesson.eig.wsfw.test.axis.SoapRequestBuilder.Parameter;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * Testcase which tests our service without deploying it in any container.
 * 
 */
public class TestSampleWebService extends TestCase {

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
        File webxml = new File("WEB-INF/web.xml");
        ServletRunner servletRunner = new ServletRunner(webxml);
        _client = servletRunner.newClient();
        _client.setExceptionsThrownOnErrorStatus(false);
        _requestBuilder = new SoapRequestBuilder();
    }

    /**
     * Validates whether the right attributes are retrieved from the WSDL file.
     */
    public void testGetWsdl() {
        WebRequest request = new GetMethodWebRequest(
                "http://hostname.ingored.com/services/house");
        request.setParameter("wsdl", "true");
        try {
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
            XMLProcessor xpath = new XMLProcessor(response.getText());
            String baseQuery = "/wsdl:definitions/wsdl:service";
            assertEquals("HouseService", xpath.getAttribute(baseQuery, "name"));
            assertEquals("house", xpath.getAttribute(baseQuery + "/wsdl:port",
                    "name"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Gets the wsdl file for a service using the custom eig load of the wsdl
     * file.
     */
    public void testGetEigWsdl() {
        WebRequest request = new GetMethodWebRequest(
                "http://hostname.ingored.com/services/helloLogon");
        request.setParameter("wsdl", "true");
        try {
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
            XMLProcessor xpath = new XMLProcessor(response.getText());
            String baseQuery = "/wsdl:definitions/wsdl:service";
            assertEquals("HelloService", xpath.getAttribute(baseQuery, "name"));
            assertEquals("hello", xpath.getAttribute(baseQuery + "/wsdl:port",
                    "name"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Validates the getMansion method of our service.Builds a SOAPRequest to be
     * sent and tests the response whether the exact values are retrieved from
     * the service.
     */
    public void testGetMansion() {
        try {
            _requestBuilder.setOperationData("getMansion",
                    "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder.buildSoapRequest();
            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/house",
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
     * Adds desired parameter to the SOAPBody and tests whether same data are
     * retrieved from the service.
     */
    public void testBuildHouse() {
        try {
            _requestBuilder.setOperationData("buildHouse",
                    "urn:eig.mckesson.com");
            _requestBuilder.addParameter("streetAddress", "111 Main");
            _requestBuilder.addParameter("city", "Pawtucket");
            _requestBuilder.addParameter("state", "MA");
            _requestBuilder.addParameter("zip", "10101");
            Parameter room = _requestBuilder.addParameter("room", "");
            room.addParameter("name", "studio");
            room.addParameter("length", "20");
            room.addParameter("width", "33");
            InputStream requestMessage = _requestBuilder.buildSoapRequest();
            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/house",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
            XMLProcessor xpath = new XMLProcessor(response.getText());
            String baseQuery = "//soapenv:Envelope/soapenv:Body/"
                    + "eig:buildHouseResponse/eig:house";
            assertEquals("111 Main", xpath.getValue(baseQuery
                    + "/eig:streetAddress"));
            assertEquals("Pawtucket", xpath.getValue(baseQuery + "/eig:city"));
            assertEquals("MA", xpath.getValue(baseQuery + "/eig:state"));
            assertEquals("10101", xpath.getValue(baseQuery + "/eig:zip"));
            List rooms = xpath.getValuesAsList(baseQuery
                    + "/eig:rooms/eig:room/eig:name");
            assertEquals(1, rooms.size());
            assertTrue(rooms.contains("studio"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Tests UserNameTokenException.
     */
    public void testThrowUsernameTokenException() {
        try {
            _requestBuilder.setOperationData("throwUsernameTokenException",
                    "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder.buildSoapRequest();
            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/house",
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
            assertTrue(faultString.indexOf("UsernameTokenException") >= 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Tests Error with a brief message.
     */
    public void testThrowError() {
        try {
            _requestBuilder.setOperationData("throwError",
                    "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder.buildSoapRequest();
            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/house",
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
            assertTrue(faultString.indexOf("Error") >= 0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Tests CastorContextException by loading a web.xml which points to an
     * invalid Spring Config File.
     */
    public void testCastorException() {

        try {
            File webxml = new File("WEB-INF/testExceptionweb.xml");
            ServletRunner servletRunner = new ServletRunner(webxml);
            _client = servletRunner.newClient();
            _client.setExceptionsThrownOnErrorStatus(false);
            _requestBuilder = new SoapRequestBuilder();
            _requestBuilder.setOperationData("getMansion",
                    "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder.buildSoapRequest();
            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/house",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
            fail("Throws CastorContextException!!!!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests MappingException by loading a web.xml which points to an invalid
     * data type.
     */
    public void testMappingException() {
        try {
            File webxml = new File("WEB-INF/testMappingExceptionweb.xml");
            ServletRunner servletRunner = new ServletRunner(webxml);
            _client = servletRunner.newClient();
            _client.setExceptionsThrownOnErrorStatus(false);
            _requestBuilder = new SoapRequestBuilder();
            _requestBuilder.setOperationData("getMansion",
                    "urn:eig.mckesson.com");
            InputStream requestMessage = _requestBuilder.buildSoapRequest();
            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/house",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
        } catch (Exception e) {
            fail("Mapping Exception is thrown");
        }
    }

}
