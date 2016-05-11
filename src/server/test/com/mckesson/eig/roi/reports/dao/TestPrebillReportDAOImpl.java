/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/

package com.mckesson.eig.roi.reports.dao;

import java.io.File;
import java.io.IOException;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.junit.Assert;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.reports.servlet.ROIReportServlet;
import com.mckesson.eig.roi.test.BaseROITestCase;
import com.mckesson.eig.roi.utils.AccessFileLoader;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
*
* @author OFS
* @date   June 03, 2013
* @since  HPF 16.0 [ROI]; June 03, 2013
*/
public class TestPrebillReportDAOImpl 
extends BaseROITestCase {

    
    /**
     * The name of the download servlet
     */
    protected static final String SERVLET_NAME = "ReportConfigurationServlet";
    private static final String REPORT_ID = "14";
    private static final String INVALID_REPORT_ID = "100";

    /**
     * Holds the instance of ServletUnitClient.
     */
    private ServletUnitClient _servletUntClient;

    /**
     * The current servlet runner
     */
    private ServletRunner _servletRunner;
    
    private static IDatabaseConnection conn = null;
    private static FlatXmlDataSet dataSet = null;    

    @Override
    public void setUp() throws Exception {

        super.setUp();
        initServlet();
    }

    public void testSetUp()
    throws Exception {

        setUp();
        initSession(new User(1, "ADMIN", "1234"));
        insertDataSet();
    }
    
    private void insertDataSet() throws Exception {  
        
        if (conn == null) {
            conn = new DatabaseConnection(getConnection());         
        }
        if (dataSet == null) {
            // get insert data  
            File dataSetFile = AccessFileLoader.getFile("test/resources/reports/reportsDataSet.xml");  
            dataSet = new FlatXmlDataSetBuilder().build(dataSetFile);         
        }
        
        InsertIdentityOperation.REFRESH.execute(conn,dataSet);
    }    
    

    /**
     * This method is used to initialize the servlet which will be used for unit testing.
     */
    protected void initServlet() {

        _servletRunner = new ServletRunner();
        _servletRunner.registerServlet(SERVLET_NAME, ROIReportServlet.class.getName());
        _servletUntClient = _servletRunner.newClient();
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        throw new UnsupportedOperationException("TestROIReportServlet.getServiceURL()");
    }

    /**
     * Test case to generate logged status report with valid input
     */
    public void testReportWithValidInput() {

        try {

            String url = constructURLString("A,AD",
                                            "Attorney",
                                            "",
                                            "Logged,Pended,PreBilled",
                                            "160",
                                            "0",
                                            "",
                                            "",
                                            "Summary - Requestor Type",
                                            "1");

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            Assert.assertNotNull("Response was null", response);

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
    
    /**
     * Test case to generate logged status report with Requestor name view
     */
    public void testReportWithRequestorNameView() {

        try {

            String url = constructURLString("A,AD",
                                            "Attorney",
                                            "",
                                            "Logged,Pended,PreBilled",
                                            "160",
                                            "0",
                                            "",
                                            "",
                                            "Detailed - Requestor name",
                                            "1");

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            Assert.assertNotNull("Response was null", response);

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
    
    /**
     * Test case to generate logged status report with Request id view
     */
    public void testReportWithRequestIdView() {

        try {

            String url = constructURLString("A,AD",
                                            "Attorney",
                                            "",
                                            "Logged,Pended,PreBilled",
                                            "160",
                                            "0",
                                            "",
                                            "",
                                            "Detailed - Request ID",
                                            "1");

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            Assert.assertNotNull("Response was null", response);

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
    

    /**
     * Test case to generate logged status report with valid input having requestor name
     */
    public void testReportWithValidInputHavingRequestorName() {

        try {

            String url = constructURLString("A,AD",
                                            "Attorney",
                                            "Requestor",
                                            "Logged,Pended,PreBilled",
                                            "160",
                                            "0",
                                            "",
                                            "",
                                            "",
                                            "1");

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            Assert.assertNotNull("Response was null", response);

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }


    /**
     * Test case to generate logged status report with requestor type as null
     */
    public void testReportWithNullRequestorTypes() {

        try {
            String url = constructURLString("A,AD",
                                            null,
                                            "",
                                            "Logged,Pended,PreBilled",
                                            "160",
                                            "0",
                                            "",
                                            "",
                                            "",
                                            "1");

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            Assert.assertNotNull("Response was null", response);

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test case to generate logged status report with invalid from age
     */
    public void testReportWithInvalidFromAge() {

        try {
            String url = constructURLString("A,AD",
                                            null,
                                            "",
                                            "Logged,Pended,PreBilled",
                                            "1.2145687",
                                            "0",
                                            "",
                                            "",
                                            "",
                                            "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            Assert.fail("Generate Logged Status Report should have failed");

        } catch (Exception e) {
            Assert.assertTrue(e instanceof IOException);
        }
    }
    
    /**
     * Test case to generate logged status report without from age
     */
    public void testReportWithWithoutFromAge() {

        try {
            String url = constructURLStringWithoutFromAge("A,AD",
                                                          null,
                                                          "",
                                                          "Logged,Pended,PreBilled",
                                                          "0",
                                                          "",
                                                          "",
                                                          "",
                                                          "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            Assert.fail("Generate Logged Status Report should have failed");

        } catch (Exception e) {
            Assert.assertTrue(e instanceof IOException);
        }
    }
    
    /**
     * Test case to generate logged status report with invalid to age
     */
    public void testReportWithInvalidToAge() {

        try {
            String url = constructURLStringWithoutToAge("A,AD",
                                                        "Attorney",
                                                        null,
                                                        "Logged,Pended,PreBilled",
                                                        "invalid",
                                                        "160",
                                                        "",
                                                        "",
                                                        "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            Assert.fail("Generate Logged Status Report should have failed");

        } catch (Exception e) {
            Assert.assertTrue(e instanceof IOException);
        }
    }

    /**
     * Test case to generate logged status report with invalid report id
     */
    public void testReportWithInvalidReportId() {

        try {
            String url = constructURLStringWithInvalidReportId("A,AD",
                                                            "Attorney",
                                                            null,
                                                            "Logged,Pended,PreBilled",
                                                            "160",
                                                            "0",
                                                            "0",
                                                            "",
                                                            "",
                                                            "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            Assert.fail("Generate Logged Status Report should have failed");

        } catch (Exception e) {
            Assert.assertTrue(e instanceof NoSuchBeanDefinitionException);
        }
    }

    /**
     * Test case to generate logged status report with invalid user input
     */
    public void testReportWithInvalidUserId() {

        try {
            String url = constructURLString("A,AD",
                                            "Attorney",
                                            "",
                                            "Logged,Pended,PreBilled",
                                            "160",
                                            "0",
                                            "0",
                                            "",
                                            "",
                                            "100");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);

        } catch (Exception e) {
            assertTrue(e instanceof ROIException);
            assertEquals("T031", ((ROIException) e).getErrorCode());
        }
    }    

    /**
     * Test case to generate logged status report without proper input
     */
    public void testReportWithoutInputCriteries() {

        try {
            String url = constructURLStringWithoutInputCriteria("A,AD",
                                                                "Attorney",
                                                                "",
                                                                "",
                                                                "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            Assert.fail("Generate Logged Status Report should have failed");

        } catch (Exception e) {
            Assert.assertTrue(e instanceof IOException);
        }
    }

    /**
     * This method is used to generate the URL that will hit the servlet.
     *
     * @return URL
     */
    protected String constructURLString(String facility,
                                        String requestorTypes,
                                        String requestorName,
                                        String requestStatus,
                                        String startAge,
                                        String endAge,
                                        String balance,
                                        String balanceCriterion,
                                        String resultType,
                                        String userInstanceId
                                        ) {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                + "transId=3&appId=roi.report"
                + "&reportId=" + REPORT_ID
                + "&BLOCKSIZE=8000"
                + "&userId=ADMIN"
                + "&userInstanceId=" + userInstanceId
                + "&facilities=" + facility
                + "&requestorTypes=" + requestorTypes
                + "&requestorName=" + requestorName
                + "&status=" + requestStatus
                + "&agingStart=" + startAge
                + "&agingEnd=" + endAge
                + "&balance=" + balance
                + "&balanceCriterion=" + balanceCriterion
                + "&resultType=" + resultType;
        return url;
    }

    /**
     * This method is used to generate the URL that will hit the servlet.
     *
     * @return URL
     */
    protected String constructURLStringWithInvalidReportId(String facility,
                                                    String requestorTypes,
                                                    String requestorName,
                                                    String requestStatus,
                                                    String startAge,
                                                    String endAge,
                                                    String balance,
                                                    String balanceCriterion,
                                                    String resultType,
                                                    String userInstanceId
                                                    ) {

                String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                        + "transId=3&appId=roi.report"
                        + "&reportId=" + INVALID_REPORT_ID
                        + "&BLOCKSIZE=8000"
                        + "&userId=ADMIN"
                        + "&userInstanceId=" + userInstanceId
                        + "&facilities=" + facility
                        + "&requestorTypes=" + requestorTypes
                        + "&requestorName=" + requestorName
                        + "&status=" + requestStatus
                        + "&agingStart=" + startAge
                        + "&agingEnd=" + endAge
                        + "&balance=" + balance
                        + "&balanceCriterion=" + balanceCriterion
                        + "&resultType=" + resultType;
                return url;
    }

    protected String constructURLStringWithoutFromAge(String facility,
                                                      String requestorTypes,
                                                      String requestorName,
                                                      String requestStatus,
                                                      String endAge,
                                                      String balance,
                                                      String balanceCriterion,
                                                      String resultType,
                                                      String userInstanceId) {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                + "transId=3&appId=roi.report"
                + "&reportId=" + REPORT_ID
                + "&BLOCKSIZE=8000"
                + "&userId=ADMIN"
                + "&userInstanceId=" + userInstanceId
                + "&facilities=" + facility
                + "&requestorTypes=" + requestorTypes
                + "&requestorName=" + requestorName
                + "&status=" + requestStatus
                + "&agingEnd=" + endAge
                + "&balance=" + balance
                + "&balanceCriterion=" + balanceCriterion
                + "&resultType=" + resultType;

        return url;
    }

    protected String constructURLStringWithoutToAge(String facility,
                                                    String requestorTypes,
                                                    String requestorName,
                                                    String requestStatus,
                                                    String startAge,
                                                    String balance,
                                                    String balanceCriterion,
                                                    String resultType,
                                                    String userInstanceId) {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                + "transId=3&appId=roi.report"
                + "&reportId=" + REPORT_ID
                + "&BLOCKSIZE=8000"
                + "&userId=ADMIN"
                + "&userInstanceId=" + userInstanceId
                + "&facilities=" + facility
                + "&requestorTypes=" + requestorTypes
                + "&requestorName=" + requestorName
                + "&status=" + requestStatus
                + "&agingStart=" + startAge
                + "&balance=" + balance
                + "&balanceCriterion=" + balanceCriterion
                + "&resultType=" + resultType;
        return url;
    }
    
    protected String constructURLStringWithoutInputCriteria(String facility,
                                                            String requestorTypes,
                                                            String requestorName,
                                                            String requestStatus,
                                                            String userInstanceId) {
                                        
            String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
            + "transId=3&appId=roi.report"
            + "&reportId=" + REPORT_ID
            + "&BLOCKSIZE=8000"
            + "&userId=ADMIN"
            + "&userInstanceId=" + userInstanceId
            + "&facilities=" + facility
            + "&requestorTypes=" + requestorTypes
            + "&requestorName=" + requestorName
            + "&status=" + requestStatus;
            return url;
    }
}
