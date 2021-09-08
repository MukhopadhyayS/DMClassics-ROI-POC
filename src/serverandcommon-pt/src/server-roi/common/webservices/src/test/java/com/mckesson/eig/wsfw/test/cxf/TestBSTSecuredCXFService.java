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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.mckesson.eig.wsfw.test.CXFBSTSoapRequestBuilder;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

import junit.framework.TestCase;

/**
 * @author Srini Paduri
 * @date   Jan 16, 2009
 * @since  HECM 2.0; Jan 16, 2009
 */
public class TestBSTSecuredCXFService extends TestCase {

    /**
     * Holds the instance of ServletUnitClient.
     */
    private ServletUnitClient _client;

    /**
     * Holds the instance of ServletRunner.
     */
    private ServletRunner _servletRunner;

    private CXFBSTSoapRequestBuilder _requestBuilder;

    /**
     * Sets up the data required for testing the service.
     *
     * @throws Exception
     *             General Exception.
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {

        super.setUp();
        File webxml = new File("WEB-INF/testCXFBSTSecuredweb.xml");
        _servletRunner = new ServletRunner(webxml);
        _client = _servletRunner.newClient();
        _client.setExceptionsThrownOnErrorStatus(false);
        _requestBuilder = new CXFBSTSoapRequestBuilder();
    }

    /*
     * Tests BinarySecurityToken scenario with private key.
     */
    public void testBSTSecuredGetEmployeeInfo() {

        try {

            String xmlSource = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<SOAP-ENV:Envelope"
                + "   xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<SOAP-ENV:Body>"
                + "<getEmployeeInfo><employeeId>1</employeeId></getEmployeeInfo>"
                + "</SOAP-ENV:Body>" + "</SOAP-ENV:Envelope>";

            Document doc = _requestBuilder.createDocumentFromString(xmlSource);
            _requestBuilder.addBSTSecurityHeader(doc, "wfprivkey", "wfpazz");

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            XMLUtils.outputDOM(doc, os, true);
            InputStream requestMessage = new ByteArrayInputStream(os.toString().getBytes());

            WebRequest request         =
                new PostMethodWebRequest("http://hostname.ingored.com/employee",
                                          requestMessage,
                                          "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            Document resDoc = builder.parse(new InputSource(new StringReader(response.getText())));
           // Document resDoc         = XMLUtils.parse(response.getText());
            NodeList nodeList    =
                (resDoc.getElementsByTagName("employee").item(0)).getChildNodes();

            assertEquals("text/xml", response.getContentType());
            assertEquals("SE", nodeList.item(0).getFirstChild().getNodeValue());
            assertEquals("1", nodeList.item(1).getFirstChild().getNodeValue());

            System.out.println("SOAP Response: " + response.getText());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    /*
     * Tests UsernameToken scenario
     *
     */
    public void testUsernameTokenSecuredGetEmployeeInfo() {

        try {

            _requestBuilder.setOperationData("getEmployeeInfo");
            _requestBuilder.addParameter("employeeId", "1");
            InputStream requestMessage =
                _requestBuilder.buildSoapRequestWithSecurityHeader("system", "hecmadmin");
            WebRequest request         =
                new PostMethodWebRequest("http://hostname.ingored.com/employee",
                                          requestMessage,
                                          "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response.getText())));
            //Document doc         = XMLUtils.parse(response.getText());
            NodeList nodeList    =
                (doc.getElementsByTagName("employee").item(0)).getChildNodes();

            assertEquals("text/xml", response.getContentType());
            assertEquals("SE", nodeList.item(0).getFirstChild().getNodeValue());
            assertEquals("1", nodeList.item(1).getFirstChild().getNodeValue());

            System.out.println("SOAP Response: " + response.getText());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /*
     * Tests negative test case when security header is absent.
     */
    public void testUnSecuredGetEmployeeInfo() {

        try {

            _requestBuilder.setOperationData("getEmployeeInfo");
            _requestBuilder.addParameter("employeeId", "1");
            InputStream requestMessage =
                _requestBuilder.buildSoapRequest();

            WebRequest request = new PostMethodWebRequest("http://hostname.ingored.com/employee",
                                                          requestMessage,
                                                          "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response.getText())));
            //Document doc         = XMLUtils.parse(response.getText());
            NodeList nodeList    =
                (doc.getElementsByTagName("detail").item(0)).getChildNodes();

            assertEquals("text/xml", response.getContentType());
            assertEquals("soap:Server",
                          (doc.getElementsByTagName("faultcode").item(0)).getFirstChild()
                                                                         .getNodeValue());
            assertEquals("WSSecurityException",
                          nodeList.item(0).getFirstChild().getNodeValue());
            System.out.println("SOAP Response: " + response.getText());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
