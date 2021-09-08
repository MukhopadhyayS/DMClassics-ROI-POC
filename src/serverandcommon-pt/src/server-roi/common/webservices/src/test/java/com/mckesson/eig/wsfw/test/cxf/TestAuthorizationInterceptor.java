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

import junit.framework.TestCase;

import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.wsfw.test.SoapRequestBuilder;
import com.mckesson.eig.wsfw.test.axis.MockAuthorizationStrategy;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * @author sahuly
 * @date   Dec 30, 2008
 * @since  HECM 1.0; Dec 30, 2008
 */
public class TestAuthorizationInterceptor extends TestCase {
    
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
        File webxml = new File("WEB-INF/testCXFAuthorizeweb.xml");
        _servletRunner = new ServletRunner(webxml);
        _client = _servletRunner.newClient();
        _client.setExceptionsThrownOnErrorStatus(false);
        _requestBuilder = new SoapRequestBuilder();
    }

    public void testAuthorizedServiceName() {

        try {

            _requestBuilder.setOperationData("getEmployeeInfo");
            _requestBuilder.addParameter("employeeId", "1");
            InputStream requestMessage = 
                _requestBuilder.buildSoapRequestWithSecurityHeader("system", "hecmadmin");

            WebRequest request = new PostMethodWebRequest("http://hostname.ingored.com/employee",
                                                          requestMessage, 
                                                          "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    public void testExtendedAuthorizedServiceName() {

        try {

            _requestBuilder.setOperationData("getEmployeeInfo");
            _requestBuilder.addParameter("employeeId", "1");
            InputStream requestMessage = 
                _requestBuilder.buildSoapRequestWithSecurityHeader("system", "hecmadmin");

            WebRequest request = 
                new PostMethodWebRequest("http://hostname.ingored.com/eig.employee",
                                          requestMessage, 
                                          "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    public void testExtendedAuthorizedServiceNameWithoutAppID() {

        try {

            _requestBuilder.setOperationData("getEmployeeInfo");
            _requestBuilder.addParameter("employeeId", "1");
            InputStream requestMessage = 
                _requestBuilder.buildSoapRequestWithTransIDAndSecurityHeader("system", "hecmadmin");

            WebRequest request = 
                new PostMethodWebRequest("http://hostname.ingored.com/eig.employee",
                                          requestMessage, 
                                          "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    public void testUnAuthorizedServiceName() {

        try {

            _requestBuilder.setOperationData("getEmployeeInfo");
            _requestBuilder.addParameter("employeeId", "1");
            InputStream requestMessage = 
                _requestBuilder.buildSoapRequestWithSecurityHeader("system", "hecmadmin");
            
            MockAuthorizationStrategy strategy = (MockAuthorizationStrategy) 
                            SpringUtilities.getInstance()
                                           .getBeanFactory()
                                           .getBean("AuthorizationStrategy");
        
            boolean holdState = strategy.getAuthState();
            strategy.setAuthState(false);

            WebRequest request = new PostMethodWebRequest("http://hostname.ingored.com/employee",
                                                          requestMessage, 
                                                          "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
            System.out.println("SOAP Response: " + response.getText());
            strategy.setAuthState(holdState);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
