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

package com.mckesson.eig.config.servlet;

import java.net.URLEncoder;

import com.mckesson.eig.config.audit.UnitSpringInitialization;
import com.mckesson.eig.config.constants.ConfigurationConstants;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * @author Sahul Hameed Y
 * @date   Feb 11, 2008
 * @since  HECM 1.0; Feb 11, 2008
 */
public class TestViewLogServlet 
extends junit.framework.TestCase {

    protected static final String SERVLET_NAME = "MockConfigLogServlet";
    private static final long HECM_APP_ID = 1;
    private ServletUnitClient _servletUntClient;
    private ServletRunner _servletRunner;
    
    private static String _urlString;
    
    private static final String USER_NAME = "harpo";

    // password = swordfish
    private static final String PWD_ENC = "2008-03-06T22:19:56Z" 
                                        + "ybQQD3v7DCKFHqLf9r/pzBNbFCEbUk2Mo//qC5dJMyE=";
    private String _encodedPwd;

    public TestViewLogServlet() {
        super();
    }

    /**
     * @see com.mckesson.hecm.reports.test.AbstractHECMReportTestCase#setUp()
     */
    @Override
    public void setUp()
    throws Exception {

        super.setUp();
        System.setProperty("application.home", System.getenv("JBOSS_HOME") + "\\server\\default");
        initServlet();
        
        if (_encodedPwd == null) {
            _encodedPwd = URLEncoder.encode(PWD_ENC, "UTF-8");
        }
        
        _urlString = getURLString();
    }
    
    /**
     * This method is used to initialize the servlet which will be used for unit testing.
     */
    protected void initServlet() 
    throws Exception {
        
        super.setUp();
        UnitSpringInitialization.init();
        _servletRunner = new ServletRunner();
        _servletRunner.registerServlet(SERVLET_NAME, MockConfigLogServlet.class.getName());
        _servletUntClient = _servletRunner.newClient();
    }
    
    /**
     * @see com.mckesson.eig.utility.testing.UnitTest#tearDown()
     */
    @Override
    protected void tearDown()
    throws Exception {

        super.tearDown();
        _servletUntClient = null;
        _servletRunner = null;
    }

    /**
     * This method is used to generate the URL that will hit the servlet.
     *
     * @return URL
     */
    protected String getURLString() {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
        + "transId=2&AppId=" + HECM_APP_ID 
        + "&USER=" + USER_NAME 
        + "&PASSWORD=" + _encodedPwd  
        + "&AuthTicket=VnZcfDs0JVZ5fnxBM003TTNDQUVATDxJJzFUNFIhVDx7V3hUIll6" 
        + "&UserName=Ron&BLOCKSIZE=200&fileName=eig.log&OFFSET=0&componentSeq=1";

        return url;
    }
    
    /**
     * This method is used to test the get the log file.
     */
    public void testViewLog() {

        try {
            
            WebRequest request = new GetMethodWebRequest(_urlString);
            WebResponse response = _servletUntClient.sendRequest(request);
            assertNotNull("response was null", response);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    /**
     * This method is used to test the get the log file.
     */
    public void testViewLogWithNullFileName() {

        try {
            
            String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                       + "transId=2&AppId=" + HECM_APP_ID 
                       + "&USER=" + USER_NAME 
                       + "&PASSWORD=" + _encodedPwd
                       + "&UserName=2&BLOCKSIZE=200&fileName=&OFFSET=0&componentSeq=1";
            
            WebRequest request = new GetMethodWebRequest(url);
            _servletUntClient.sendRequest(request);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertEquals(ConfigurationConstants.INVALID_FILE_NAME, e.getMessage());
        }
    }
    
    /**
     * This method is used to test the get the log file.
     */
    public void testViewLogWithInvalidOffset() {

        try {
            
            String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                       + "transId=2&AppId=" + HECM_APP_ID 
                       + "&USER=" + USER_NAME 
                       + "&PASSWORD=" + _encodedPwd
                       + "&UserName=2&BLOCKSIZE=200&fileName=testFile&OFFSET=&componentSeq=1";
            
            WebRequest request = new GetMethodWebRequest(url);
            _servletUntClient.sendRequest(request);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertEquals(ConfigurationConstants.INVALID_OFFSET, e.getMessage());
        }
    }
    
    /**
     * This method is used to test the get the log file.
     */
    public void testViewLogWithInvalidBlockSize() {

        try {
            
            String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                       + "transId=2&AppId=" + HECM_APP_ID 
                       + "&USER=" + USER_NAME 
                       + "&PASSWORD=" + _encodedPwd
                       + "&UserName=2&BLOCKSIZE=&fileName=testFile&OFFSET=0&componentSeq=1";
            
            WebRequest request = new GetMethodWebRequest(url);
            _servletUntClient.sendRequest(request); 
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertEquals(ConfigurationConstants.INVALID_BLOCK_SIZE, e.getMessage());
        }
    }
    
    /**
     * This method is used to test the get the log file.
     */
    public void testViewLogWithInvalidComponentSequence() {

        try {
            
            String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                       + "transId=2&AppId=" + HECM_APP_ID 
                       + "&USER=" + USER_NAME 
                       + "&PASSWORD=" + _encodedPwd
                       + "&UserName=2&BLOCKSIZE=200&fileName=eig.log&OFFSET=0&componentSeq=";
            
            WebRequest request = new GetMethodWebRequest(url);
            _servletUntClient.sendRequest(request); 
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertEquals(ConfigurationConstants.INVALID_COMPONENT_SEQ, e.getMessage());
        }
    }
    
    /**
     * This method is used to test the get the log file.
     */
    public void testViewLogWithInvalidPrivilegeFile() {

        try {
            
            String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                       + "transId=2&AppId=" + HECM_APP_ID 
                       + "&USER=" + USER_NAME 
                       + "&PASSWORD=" + _encodedPwd
                       + "&UserName=2&BLOCKSIZE=200&fileName=eig.log&OFFSET=0&componentSeq=";
            
            WebRequest request = new GetMethodWebRequest(url);
            _servletUntClient.sendRequest(request); 
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertEquals(ConfigurationConstants.INVALID_COMPONENT_SEQ, e.getMessage());
        }
    }
}
