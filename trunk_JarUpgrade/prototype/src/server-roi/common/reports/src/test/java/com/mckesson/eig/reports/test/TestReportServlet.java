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
package com.mckesson.eig.reports.test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import com.mckesson.eig.reports.service.ReportServiceImpl;
import com.mckesson.eig.reports.servlet.test.ReportServletImpl;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * @author Pranav Amarasekaran
 * @date   Jan 9, 2008
 * @since  HECM 1.0; Jan 9, 2008
 */
public class TestReportServlet
extends junit.framework.TestCase {

    /**
     * The name of the download servlet
     */
    protected static final String SERVLET_NAME = "ReportConfigurationServlet";

    /**
     * Holds the instance of ServletUnitClient.
     */
    private ServletUnitClient _servletUntClient;

    /**
     * The current servlet runner
     */
    private ServletRunner _servletRunner;

    private static final String USER_NAME = "harpo";
    private static final String TIMESTAMP = "2008-03-06T22:19:56Z";

    // password = swordfish
    private static final String PWD_ENC = "2008-03-06T22:19:56Z"
                                        + "n7bRrkbr54JxuDsHqzsOLjoMDNJreU5D0CxTjKY2oTs=";

    /**
     * @throws java.lang.Exception
     */
    @Override
    protected void setUp()
    throws Exception {

        super.setUp();
        initServlet();
    }

    /**
     * This method is used to initialize the servlet which will be used for unit testing.
     */
    protected void initServlet() {

        _servletRunner = new ServletRunner();
        _servletRunner.registerServlet(SERVLET_NAME, ReportServletImpl.class.getName());
        _servletUntClient = _servletRunner.newClient();
    }

    /**
     * This method is used to generate the URL that will hit the servlet.
     *
     * @return URL
     */
    protected String getURLString()
    throws UnsupportedEncodingException {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
        + "transId=2&appId=report&USERNAME=" + USER_NAME + "&reportId=reportid&BLOCKSIZE=200"
        + "&TIMESTAMP=" + TIMESTAMP 
        + "&PASSWORD=" + URLEncoder.encode(PWD_ENC, "UTF-8");
        return url;
    }

    /**
     * Basic Test method to test the report generation.
     */
    public void testReport() {

        try {
            WebRequest request = new GetMethodWebRequest(getURLString());
            WebResponse response = _servletUntClient.sendRequest(request);
            assertNotNull("response was null", response);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Basic Test method to test the report generation.
     */
    public void testGenerateReport() {

        try {

            ReportServiceImpl reportServiceImpl = new ReportServiceImpl();
            reportServiceImpl.generateReport(getURLString(), new HashMap(), new File("g:/sa"));
            fail(" Should have thrown IOException");
        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }

    /**
     * @throws java.lang.Exception
     */
    @Override
    protected void tearDown()
    throws Exception {

        super.tearDown();
        _servletUntClient = null;
        _servletRunner = null;
    }
}
