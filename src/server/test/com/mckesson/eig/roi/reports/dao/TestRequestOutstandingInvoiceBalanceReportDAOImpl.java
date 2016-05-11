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
 * @date   Nov 13, 2008
 * @since  HPF 13.1 [ROI]; Nov 13, 2008
 */
public class TestRequestOutstandingInvoiceBalanceReportDAOImpl
extends BaseROITestCase {

    /**
     * The name of the download servlet
     */
    protected static final String SERVLET_NAME = "ReportConfigurationServlet";

    private static IDatabaseConnection conn = null;
    private static FlatXmlDataSet dataSet = null;
    private static final String invalidInteger = "1234567890123456";
    private static final String invalidFloat = String.valueOf(Float.MAX_VALUE*100);

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
     * Test case to generate request outstanding invoice balance report with valid input
     */
    public void testReportWithValidInput() {

        try {

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setCalendar(cal);
            String url = constructURLString("Logged",
                                            "IE",
                                            "Attorney,Insurance",
                                            null,
                                            "0",
                                            "120" ,
                                            "At Least",
                                            "1",
                                            "6",
                                            "1");

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            assertNotNull("Response was null", response);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test case to generate request outstanding invoice balance report with invalid from age
     */
    public void testReportWithInvalidFromAge() {

        try {
            String url = constructURLString("Logged",
                                            "IE",
                                            "Attorney,Insurance",
                                            null,
                                            "FromAge",
                                            "120" ,
                                            "At Least",
                                            "1",
                                            "6",
                                            "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            fail("Generate Outstanding Invoice Balance Report should have failed");

        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }

    /**
     * Test case to generate request outstanding invoice balance report with invalid toAge
     */
    public void testReportWithInvalidToAge() {

        try {
            String url = constructURLString("Logged",
                                            "IE",
                                            "Attorney,Insurance",
                                            null,
                                            "0",
                                            "ToAge" ,
                                            "At Least",
                                            "1",
                                            "6",
                                            "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            fail("Generate Outstanding Invoice Balance Report should have failed");

        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }

    /**
     * Test case to generate request outstanding invoice balance report with invalid balance due
     */
    public void testReportWithInvalidBalanceDue() {

        try {
            String url = constructURLString("Logged",
                                            "IE",
                                            "Attorney,Insurance",
                                            null,
                                            "0",
                                            "120" ,
                                            "At Least",
                                            "BalanceDue",
                                            "6",
                                            "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            fail("Generate Outstanding Invoice Balance Report should have failed");

        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }

    /**
     * Test case to generate request outstanding invoice balance report with invalid report id
     */
    public void testReportWithInvalidReportId() {

        try {
            String url = constructURLString("Logged",
                                            "IE",
                                            "Attorney,Insurance",
                                            null,
                                            "0",
                                            "120" ,
                                            "At Least",
                                            "1",
                                            "A",
                                            "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            fail("Generate Outstanding Invoice Balance Report should have failed");

        } catch (Exception e) {
            assertTrue(e instanceof NumberFormatException);
        }
    }

    /**
     * Test case to generate request outstanding invoice balance report without status
     */
    public void testReportWithoutStatus() {

        try {
            String url = constructURLStringWithoutStatus("IE",
                                                         "Attorney,Insurance",
                                                         null,
                                                         "0",
                                                         "120" ,
                                                         "At Least",
                                                         "1",
                                                         "6",
                                                         "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            assertNotNull("Response was null", response);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test case to generate request outstanding invoice balance report with invalid range of from age
     */
    public void testReportWithInvalidRangeOfFromAge() {

        try {
            String url = constructURLStringWithoutStatus("IE",
                                                         "Attorney,Insurance",
                                                         null,
                                                         invalidInteger,
                                                         "120" ,
                                                         "At Least",
                                                         "1",
                                                         "6",
                                                         "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            fail("Generate Outstanding Invoice Balance Report should have failed");

        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }

    /**
     * Test case to generate request outstanding invoice balance report invalid range of to age
     */
    public void testReportWithInvalidRangeOfToage() {

        try {
            String url = constructURLStringWithoutStatus("IE",
                                                         "Attorney,Insurance",
                                                         null,
                                                         "0",
                                                         invalidInteger,
                                                         "At Least",
                                                         "1",
                                                         "6",
                                                         "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            fail("Generate Outstanding Invoice Balance Report should have failed");

        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }

    /**
     * Test case to generate request outstanding invoice balance report invalid range of balance due
     */
    public void testReportWithInvalidRangeOfBalanceDue() {

        try {
            String url = constructURLStringWithoutStatus("IE",
                                                         "Attorney,Insurance",
                                                         null,
                                                         "0",
                                                         "120",
                                                         "At Least",
                                                         invalidFloat,
                                                         "6",
                                                         "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            fail("Generate Outstanding Invoice Balance Report should have failed");

        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }
    }

    /**
     * This method is used to generate the URL that will hit the servlet.
     *
     * @return URL
     */
    protected String constructURLString(String status,
                                        String facilities,
                                        String requestorTypes,
                                        String requestorName,
                                        String fromage,
                                        String toAge,
                                        String balanceLevel,
                                        String balanceDue,
                                        String reportId,
                                        String userInstanceId) {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                    + "transId=3&appId=roi.report"
                    + "&reportId=" + reportId
                    + "&BLOCKSIZE=8000"
                    + "&userId=ADMIN"
                    + "&userInstanceId=" + userInstanceId
                    + "&status=" + status
                    + "&facilities=" + facilities
                    + "&requestorTypes=" + requestorTypes
                    + "requestorName" + requestorName
                    + "&invoiceFromAge=" + fromage
                    + "&invoiceToAge=" + toAge
                    + "&balanceLevel=" + balanceLevel
                    + "&balanceDue=" + balanceDue;
        return url;
    }

    protected String constructURLStringWithoutStatus(String facilities,
                                                     String requestorTypes,
                                                     String requestorName,
                                                     String fromage,
                                                     String toAge,
                                                     String balanceLevel,
                                                     String balanceDue,
                                                     String reportId,
                                                     String userInstanceId) {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                    + "transId=3&appId=roi.report"
                    + "&reportId=" + reportId
                    + "&BLOCKSIZE=8000"
                    + "&userId=ADMIN"
                    + "&userInstanceId=" + userInstanceId
                    + "&facilities=" + facilities
                    + "&requestorTypes=" + requestorTypes
                    + "requestorName" + requestorName
                    + "&invoiceFromAge=" + fromage
                    + "&invoiceToAge=" + toAge
                    + "&balanceLevel=" + balanceLevel
                    + "&balanceDue=" + balanceDue;
        return url;
    }



}
