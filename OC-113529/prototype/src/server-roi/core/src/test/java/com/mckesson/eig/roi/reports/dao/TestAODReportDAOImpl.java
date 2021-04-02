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
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
* @date   Aug 24, 2008
* @since  HPF 13.1 [ROI]; Oct 14, 2008
*/
public class TestAODReportDAOImpl
extends BaseROITestCase {

    /**
     * The name of the download servlet
     */
    protected static final String SERVLET_NAME = "ReportConfigurationServlet";
    private static final String REPORT_ID = "1";
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

    @Override
    public void initializeTestData()
    throws Exception {

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

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.add(Calendar.DATE, 1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setCalendar(cal);
            String url = constructURLString("ROIM00008",
                                            "IE",
                                            "0",
                                            "TPO",
                                            "10/13/2007",
                                            dateFormat.format(cal.getTime()).toString(),
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
            String url = constructURLString(null,
                                            "IE",
                                            "0",
                                            "",
                                            "10/13/2007",
                                            "10/14/2009",
                                            "1");

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            Assert.assertNotNull("Response was null", response);

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test case to generate logged status report with invalid from date
     */
    public void testReportWithInvalidFromDate() {

        try {
            String url = constructURLString("mrn1",
                                            "IE",
                                            "0",
                                            "",
                                            "invalid",
                                            "10/14/2009",
                                            "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            Assert.fail("Generate Logged Status Report should have failed");

        } catch (Exception e) {
            Assert.assertTrue(e instanceof IOException);
        }
    }

    /**
     * Test case to generate logged status report with from date as null
     */
    public void testReportWithNullFromDate() {

        try {
            String url = constructURLString("mrn1",
                                            "IE",
                                            "0",
                                            "",
                                            null,
                                            "10/14/2009",
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
            String url = constructURLStringInvalidId("mrn1",
                                                     "IE",
                                                     "0",
                                                     "",
                                                     "10/13/2007",
                                                     "10/14/2009",
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
            String url = constructURLStringWithInvalidUserId("mrn1",
                                                             "IE",
                                                             "0",
                                                             "10/13/2007",
                                                             "10/14/2009",
                                                             "100");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);

        } catch (Exception e) {
            assertTrue(e instanceof ROIException);
            assertEquals("T031", ((ROIException) e).getErrorCode());
        }
    }

    /**
     * Test case to generate logged status report without from date
     */
    public void testReportWithoutInputCriteries() {

        try {
            String url = constructURLStringWithoutFromDt("mrn1",
                                                         "IE",
                                                         "0",
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
    protected String constructURLString(String mrn,
                                        String facility,
                                        String patientId,
                                        String requestType,
                                        String fromDt,
                                        String toDt,
                                        String userInstanceId
                                        ) {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                    + "transId=3&appId=roi.report"
                    + "&reportId=" + REPORT_ID
                    + "&BLOCKSIZE=8000"
                    + "&userId=ADMIN"
                    + "&userInstanceId=" + userInstanceId
                    + "&mrn=" + mrn
                    + "&facilities=" + facility
                    + "&patientId=" + patientId
                    + "&requestType=" + requestType
                    + "&fromDt=" + fromDt
                    + "&toDt=" + toDt;
        return url;
    }

    /**
     * This method is used to generate the URL that will hit the servlet.
     *
     * @return URL
     */
    protected String constructURLStringInvalidId(String mrn,
                                                 String facility,
                                                 String patientId,
                                                 String requestType,
                                                 String fromDt,
                                                 String toDt,
                                                 String userInstanceId) {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                    + "transId=3&appId=roi.report"
                    + "&reportId=" + INVALID_REPORT_ID
                    + "&BLOCKSIZE=8000"
                    + "&userId=ADMIN"
                    + "&userInstanceId=" + userInstanceId
                    + "&mrn=" + mrn
                    + "&facilities=" + facility
                    + "&patientId=" + patientId
                    + "&requestType=" + requestType
                    + "&fromDt=" + fromDt
                    + "&toDt=" + toDt;
        return url;
    }
    protected String constructURLStringWithInvalidUserId(String mrn,
                                                         String facility,
                                                         String patientId,
                                                         String fromDt,
                                                         String toDt,
                                                         String userInstanceId) {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                    + "transId=3&appId=roi.report"
                    + "&reportId=" + REPORT_ID
                    + "&BLOCKSIZE=8000"
                    + "&userId=ADMIN"
                    + "&userInstanceId=" + userInstanceId
                    + "&mrn=" + mrn
                    + "&facilities=" + facility
                    + "&patientId=" + patientId
                    + "&fromDt=" + fromDt
                    + "&toDt=" + toDt;
        return url;
    }

    protected String constructURLStringWithoutFromDt(String mrn,
                                                     String facility,
                                                     String patientId,
                                                     String userInstanceId) {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                    + "transId=3&appId=roi.report"
                    + "&reportId=" + REPORT_ID
                    + "&BLOCKSIZE=8000"
                    + "&userId=ADMIN"
                    + "&userInstanceId=" + userInstanceId
                    + "&mrn=" + mrn
                    + "&facilities=" + facility
                    + "&patientId=" + patientId;

        return url;
    }

    protected String constructURLStringWithoutToDt(String requestorTypes,
                                                   String fromDt,
                                                   String reportId,
                                                   String userInstanceId) {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                    + "transId=3&appId=roi.report"
                    + "&reportId=" + reportId
                    + "&BLOCKSIZE=8000"
                    + "&userId=ADMIN"
                    + "&userInstanceId=" + userInstanceId
                    + "&requestorTypes=" + requestorTypes
                    + "&fromDt=" + fromDt;
        return url;
    }
}
