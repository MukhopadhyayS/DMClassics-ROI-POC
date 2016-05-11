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
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

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
 * @author OFS
 * @date   Nov 5, 2008
 * @since  HPF 13.1 [ROI]; Nov 5, 2008
 */
public class TestRequestStatusSummaryReportDAOImpl
extends BaseROITestCase {

    /**
     * The name of the download servlet
     */
    protected static final String SERVLET_NAME = "ReportConfigurationServlet";

    private static IDatabaseConnection conn = null;
    private static FlatXmlDataSet dataSet = null;

    private static final String facilities = "A,AD,AWL,DWL,IE,IE2,test,B,C,E,G,H,HKR";

    /**
     * Holds the instance of ServletUnitClient.
     */
    private ServletUnitClient _servletUntClient;

    /**
     * The current servlet runner
     */
    private ServletRunner _servletRunner;

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
     * Test case to generate request status summary report with valid input
     */
    public void testReportWithValidInput() {

        try {

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setCalendar(cal);
            String url = constructURLString("Patient",
                                            "Logged",
                                            "10/13/2007",
                                            dateFormat.format(cal.getTime()).toString(),
                                            "4",
                                            facilities,
                                            "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            assertNotNull("Response was null", response);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test case to generate request status summary report with invalid from date
     */
    public void testReportWithInvalidFromDate() {

        try {
            String url = constructURLString("Patient",
                                            "Logged",
                                            "invalidDate",
                                            "10/14/2008",
                                            "4",
                                            facilities,
                                            "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            fail("Generate Request Status Summary Report should have failed");

        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }

    /**
     * Test case to generate request status summary report with from date as null
     */
    public void testReportWithNullFromDate() {

        try {
            String url = constructURLString("Patient", "Logged", null, "10/14/2008", "4", facilities, "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            fail("Generate Request Status Summary Report should have failed");

        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }

    /**
     * Test case to generate request status summary report with to date as null
     */
    public void testReportWithNullToDate() {

        try {
            String url = constructURLString("Patient", "Logged", "10/14/2008", null, "4", facilities, "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            fail("Generate Request Status Summary Report should have failed");

        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }

    /**
     * Test case to generate request status summary report with invalid report id
     */
    public void testReportWithInvalidReportId() {

        try {
            String url = constructURLString("Patient",
                                            "Logged",
                                            "10/13/2008",
                                            "10/14/2008",
                                            "100",
                                            facilities,
                                            "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            fail("Generate Request Status Summary Report should have failed");

        } catch (Exception e) {
            assertTrue(e instanceof NoSuchBeanDefinitionException);
        }
    }

    /**
     * Test case to generate request status summary report without status
     */
    public void testReportWithoutStatus() {

        try {
            String url = constructURLStringWithoutStatus("Patient",
                                                         "10/13/2008",
                                                         "10/14/2008",
                                                         "4",
                                                         "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            assertNotNull("Response was null", response);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test case to generate request status summary report without from date
     */
    public void testReportWithoutFromDate() {

        try {
            String url = constructURLStringWithoutFromDt("Patient",
                                                         "Logged",
                                                         "10/14/2008",
                                                         "4",
                                                         "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            fail("Generate Request Status Summary Report should have failed");

        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }

    /**
     * Test case to generate request status summary report without to date
     */
    public void testReportWithoutToDate() {

        try {
            String url = constructURLStringWithoutToDt("Patient",
                                                       "Logged",
                                                       "10/14/2008",
                                                       "4",
                                                       "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            fail("Generate Request Status Summary Report should have failed");

        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }

    /**
     * This method is used to generate the URL that will hit the servlet.
     *
     * @return URL
     */
    protected String constructURLString(String requestorTypes,
                                        String status,
                                        String fromDt,
                                        String toDt,
                                        String reportId,
                                        String facilities,
                                        String userInstanceId) {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                    + "transId=3&appId=roi.report"
                    + "&reportId=" + reportId
                    + "&BLOCKSIZE=8000"
                    + "&userId=ADMIN"
                    + "&userInstanceId=" + userInstanceId
                    + "&requestorTypes=" + requestorTypes
                    + "&status=" + status
                    + "&facilities=" + facilities
                    + "&fromDt=" + fromDt
                    + "&toDt=" + toDt;
        return url;
    }

    protected String constructURLStringWithoutStatus(String reqTypes,
                                                     String fromDt,
                                                     String toDt,
                                                     String reportId,
                                                     String userInstanceId) {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                    + "transId=3&appId=roi.report"
                    + "&reportId=" + reportId
                    + "&BLOCKSIZE=8000"
                    + "&userId=ADMIN"
                    + "&userInstanceId=" + userInstanceId
                    + "&requestorTypes=" + reqTypes
                    + "&fromDt=" + fromDt
                    + "&toDt=" + toDt;
        return url;
    }

    protected String constructURLStringWithoutFromDt(String requestorTypes,
                                                     String status,
                                                     String toDt,
                                                     String reportId,
                                                     String userInstanceId) {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                    + "transId=3&appId=roi.report"
                    + "&reportId=" + reportId
                    + "&BLOCKSIZE=8000"
                    + "&userId=ADMIN"
                    + "&userInstanceId=" + userInstanceId
                    + "&requestorTypes=" + requestorTypes
                    + "&status=" + status
                    + "&toDt=" + toDt;
        return url;
    }

    protected String constructURLStringWithoutToDt(String requestorTypes,
                                                   String status,
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
                    + "&status=" + status
                    + "&fromDt=" + fromDt;
        return url;
    }

}
