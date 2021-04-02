/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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
* @date   Oct 14, 2008
* @since  HPF 13.1 [ROI]; Oct 14, 2008
*/
public class TestTurnAroundTimeReportDAOImpl
extends BaseROITestCase {

    /**
     * The name of the download servlet
     */
    protected static final String SERVLET_NAME = "ReportConfigurationServlet";

    private static IDatabaseConnection _conn = null;
    private static FlatXmlDataSet _dataSet = null;

    private static final String REPORT_ID = "11";
    private static final String INVALID_REPORT_ID = "100";
    private static final String FACILITIES = "A,AD,AWL,DWL,IE,IE2,test,B,C,E,G,H,HKR";


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

    private void insertDataSet() throws Exception {

        if (_conn == null) {
            _conn = new DatabaseConnection(getConnection());
        }
        if (_dataSet == null) {
            // get insert data
            File dataSetFile = AccessFileLoader.getFile("test/resources/reports/reportsDataSet.xml");
            _dataSet = new FlatXmlDataSetBuilder().build(dataSetFile);
        }

        InsertIdentityOperation.REFRESH.execute(_conn, _dataSet);
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setCalendar(cal);
            String url = constructURLString("Patient,Attorney",
                                            "1/1/2011",
                                            dateFormat.format(cal.getTime()).toString(),
                                            "1",
                                            FACILITIES,
                                            "Received",
                                            "Logged");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            assertNotNull("Response was null", response);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    /**
     * Test case to generate logged status report with valid input
     */
    public void testReportWithValidInputForDetails() {

        try {

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setCalendar(cal);
            String url = constructURLString("Patient",
                                            "1/1/2009",
                                            dateFormat.format(cal.getTime()).toString(),
                                            "1",
                                            FACILITIES,
                                            "Received",
                                            "Logged");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            assertNotNull("Response was null", response);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test case to generate logged status report with invalid report id
     */
    public void testReportWithInvalidReportId() {

        try {
            String url = constructURLStringInvalidId("Patient",
                                                     "10/13/2007",
                                                     "10/14/2009",
                                                     "1",
                                                     "1",
                                                     "Received",
                                                     "Pended");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            fail("Generate Logged Status Report should have failed");

        } catch (Exception e) {
            assertTrue(e instanceof NoSuchBeanDefinitionException);
        }
    }

    /**
     * Test case to generate logged status report without requestor type
     */
    public void testReportWithoutRequestorTypes() {

        try {
            String url = constructURLStringWithoutReqTypes("10/13/2007",
                                                           "10/14/2009",
                                                           "1",
                                                           "1",
                                                           "Received",
                                                           "Pended");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            assertNotNull("Response was null", response);

        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }

    /**
     * Test case to generate logged status report without from date
     */
    public void testReportWithoutInputCriteries() {

        try {
            String url = constructURLStringWithoutFromDt("Patient", "10/14/2008", "8", "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            fail("Generate Logged Status Report should have failed");

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
                                        String fromDt,
                                        String toDt,
                                        String userInstanceId,
                                        String facilities,
                                        String fromStatus,
                                        String toStatus) {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                + "transId=3&appId=roi.report"
                + "&reportId=" + REPORT_ID
                + "&BLOCKSIZE=8000"
                + "&userId=ADMIN"
                + "&userInstanceId=" + userInstanceId
                + "&requestorTypes=" + requestorTypes
                + "&fromDt=" + fromDt
                + "&toDt=" + toDt
                + "&fromStatus=" + fromStatus
                + "&toStatus=" + toStatus
                + "&resultType=" + "details"
                + "&facilities=" + facilities;
        return url;
    }

    /**
     * This method is used to generate the URL that will hit the servlet.
     *
     * @return URL
     */
   /* protected String constructURLStringForSummary(String requestorTypes,
                                        String fromDt,
                                        String toDt,
                                        String userInstanceId,
                                        String ids,
                                        String facilities,
                                        String fromStatus,
                                        String disposition) {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                    + "transId=3&appId=roi.report"
                    + "&reportId=" + REPORT_ID
                    + "&BLOCKSIZE=8000"
                    + "&userId=ADMIN"
                    + "&userInstanceId=" + userInstanceId
                    + "&requestorTypes=" + requestorTypes
                    + "&fromDt=" + fromDt
                    + "&toDt=" + toDt
                    + "&actorIds=" + ids
                    + "&fromStatus=" + fromStatus
                    + "&disposition=" + disposition
                    + "&facilities=" + facilities
                    + "&resultType=" + "details";
        return url;
    }
*/
    /**
     * This method is used to generate the URL that will hit the servlet.
     *
     * @return URL
     */
    protected String constructURLStringInvalidId(String requestorTypes,
                                        String fromDt,
                                        String toDt,
                                        String userInstanceId,
                                        String ids,
                                        String fromStatus,
                                        String disposition) {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                    + "transId=3&appId=roi.report"
                    + "&reportId=" + INVALID_REPORT_ID
                    + "&BLOCKSIZE=8000"
                    + "&userId=ADMIN"
                    + "&userInstanceId=" + userInstanceId
                    + "&requestorTypes=" + requestorTypes
                    + "&fromDt=" + fromDt
                    + "&toDt=" + toDt
                    + "&actorIds=" + ids
                    + "&fromStatus=" + fromStatus
                    + "&disposition=" + disposition;
        return url;
    }
    protected String constructURLStringWithoutReqTypes(String fromDt,
                                                       String toDt,
                                                       String userInstanceId,
                                                       String ids,
                                                       String fromStatus,
                                                       String disposition) {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                    + "transId=3&appId=roi.report"
                    + "&reportId=" + REPORT_ID
                    + "&BLOCKSIZE=8000"
                    + "&userId=ADMIN"
                    + "&userInstanceId=" + userInstanceId
                    + "&fromDt=" + fromDt
                    + "&toDt=" + toDt
                    + "&actorIds=" + ids
                    + "&fromStatus=" + fromStatus
                    + "&disposition=" + disposition;
        return url;
    }

    protected String constructURLStringWithoutFromDt(String requestorTypes,
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
                    + "&toDt=" + toDt;
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
