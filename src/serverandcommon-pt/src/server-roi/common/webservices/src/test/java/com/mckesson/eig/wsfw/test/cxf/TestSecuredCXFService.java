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
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.mckesson.eig.wsfw.test.ResponseProcessor;
import com.mckesson.eig.wsfw.test.SoapRequestBuilder;
import com.mckesson.eig.wsfw.test.service.Employee;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

import junit.framework.TestCase;

/**
 * @author sahuly
 * @date   Dec 30, 2008
 * @since  HECM 1.0; Dec 30, 2008
 */
public class TestSecuredCXFService extends TestCase {
    
    /**
     * Holds the instance of ServletUnitClient.
     */
    private ServletUnitClient _client;

    /**
     * Holds the instance of ServletRunner.
     */    
    private ServletRunner _servletRunner;
    
    private SoapRequestBuilder _requestBuilder;
    
    /**
     * Sets up the data required for testing the service.
     * 
     * @throws Exception
     *             General Exception.
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {

        super.setUp();
        File webxml = new File("WEB-INF/testCXFSecuredweb.xml");
        _servletRunner = new ServletRunner(webxml);
        _client = _servletRunner.newClient();
        _client.setExceptionsThrownOnErrorStatus(false);
        _requestBuilder = new SoapRequestBuilder();
    }
    
    public void testSecuredGetEmployeeInfo() {

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

            Employee emp = (Employee) ResponseProcessor.unMarshallObject(
                                            ResponseProcessor.prepareResponse(response), 
                                            Employee.class);

            assertEquals("text/xml", response.getContentType());
            assertEquals("SE", emp.getDesignation());
            assertEquals("1", emp.getEmployeeNumber());

            System.out.println("SOAP Response: " + response.getText());            
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
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
            Node node            = nodeList.item(0).getFirstChild();

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
