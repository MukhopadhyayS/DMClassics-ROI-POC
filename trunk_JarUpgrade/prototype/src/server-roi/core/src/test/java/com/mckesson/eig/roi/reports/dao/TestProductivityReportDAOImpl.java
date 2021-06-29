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
* @since  HPF 16.0 [ROI]; June 04, 2013
*/
public class TestProductivityReportDAOImpl 
extends BaseROITestCase {
    
    /**
     * The name of the download servlet
     */
    protected static final String SERVLET_NAME = "ReportConfigurationServlet";
    private static final String REPORT_ID = "15";
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

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.add(Calendar.DATE, 1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setCalendar(cal);
            String url = constructURLString("A,AD",
                                            "1",
                                            "Attorney",
                                            "10/13/2007",
                                            dateFormat.format(cal.getTime()).toString(),
                                            "Username",
                                            "1");

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            Assert.assertNotNull("Response was null", response);            

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
    
    /**
     * Test case to generate logged status report with requestor type view
     */
    public void testReportWithRequestorTypeView() {

        try {

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.add(Calendar.DATE, 1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setCalendar(cal);
            String url = constructURLString("A,AD",
                                            "1",
                                            "Attorney",
                                            "10/13/2007",
                                            dateFormat.format(cal.getTime()).toString(),
                                            "Requestor Type",
                                            "1");

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            Assert.assertNotNull("Response was null", response);            

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
    
    /**
     * Test case to generate logged status report with requestid and page type view
     */
    public void testReportWithRequestIdAndPageTypeView() {

        try {

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.add(Calendar.DATE, 1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setCalendar(cal);
            String url = constructURLString("A,AD",
                                            "1",
                                            "Attorney",
                                            "10/13/2007",
                                            dateFormat.format(cal.getTime()).toString(),
                                            "Request ID,Page Type",
                                            "1");

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            Assert.assertNotNull("Response was null", response);            

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
    
    /**
     * Test case to generate logged status report with requestid and mrn type view
     */
    public void testReportWithRequestIdAndMrnView() {

        try {

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.add(Calendar.DATE, 1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setCalendar(cal);
            String url = constructURLString("A,AD",
                                            "1",
                                            "Attorney",
                                            "10/13/2007",
                                            dateFormat.format(cal.getTime()).toString(),
                                            "Request ID,MRN",
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
            
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.add(Calendar.DATE, 1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setCalendar(cal);
            String url = constructURLString("A,AD",
                                            "1",
                                            null,
                                            "10/13/2007",
                                            dateFormat.format(cal.getTime()).toString(),
                                            "Request ID,MRN",
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
            
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.add(Calendar.DATE, 1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setCalendar(cal);
            String url = constructURLString("A,AD",
                                            "1",
                                            "Attorney",
                                            "invalid",
                                            dateFormat.format(cal.getTime()).toString(),
                                            "Request ID,MRN",
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
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.add(Calendar.DATE, 1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setCalendar(cal);
            String url = constructURLString("A,AD",
                                            "1",
                                            "Attorney",
                                            null,
                                            dateFormat.format(cal.getTime()).toString(),
                                            "Request ID,MRN",
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
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.add(Calendar.DATE, 1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setCalendar(cal);
            String url = constructURLStringWithInvalidReportId("A,AD",
                                                                "1",
                                                                "Attorney",
                                                                "10/13/2007",
                                                                dateFormat.format(cal.getTime()).toString(),
                                                                "Request ID,MRN",
                                                                "1");
            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);
            Assert.fail("Generate Logged Status Report should have failed");

        } catch (Exception e) {
            Assert.assertTrue(e instanceof NoSuchBeanDefinitionException);
        }
    }

    /**
     * Test case to generate logged status report without from date
     */
    public void testReportWithoutInputCriteries() {

        try {
            String url = constructURLStringWithoutInputCriteries("A,AD",
                                                                "1",
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
                                        String userId,
                                        String requestorType,
                                        String fromDt,
                                        String toDt,
                                        String resultType,
                                        String userInstanceId
                                        ) {

        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                    + "transId=3&appId=roi.report"
                    + "&reportId=" + REPORT_ID
                    + "&BLOCKSIZE=8000"
                    + "&userId=ADMIN"
                    + "&userInstanceId=" + userInstanceId
                    + "&facilityNames=" + facility
                    + "&actorIds=" + userId
                    + "&requestorTypes=" + requestorType
                    + "&fromDt=" + fromDt
                    + "&toDt=" + toDt
                    + "&resultType=" + resultType;
        return url;
    }

    /**
     * This method is used to generate the URL that will hit the servlet.
     *
     * @return URL
     */
    protected String constructURLStringInvalidId(String facility,
                                                 String userId,
                                                 String requestorType,
                                                 String fromDt,
                                                 String toDt,
                                                 String resultType,
                                                 String userInstanceId
                                                 ) {

            String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                        + "transId=3&appId=roi.report"
                        + "&reportId=" + INVALID_REPORT_ID
                        + "&BLOCKSIZE=8000"
                        + "&userId=ADMIN"
                        + "&userInstanceId=" + userInstanceId
                        + "&facilityNames=" + facility
                        + "&actorIds=" + userId
                        + "&requestorTypes=" + requestorType
                        + "&fromDt=" + fromDt
                        + "&toDt=" + toDt
                        + "&resultType=" + resultType;
            return url;
    }
    
    protected String constructURLStringWithInvalidReportId(String facility,
                                                         String userId,
                                                         String requestorType,
                                                         String fromDt,
                                                         String toDt,
                                                         String resultType,
                                                         String userInstanceId
                                                         ) {

            String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                        + "transId=3&appId=roi.report"
                        + "&reportId=" + INVALID_REPORT_ID
                        + "&BLOCKSIZE=8000"
                        + "&userId=ADMIN"
                        + "&userInstanceId=" + userInstanceId
                        + "&facilityNames=" + facility
                        + "&actorIds=" + userId
                        + "&requestorTypes=" + requestorType
                        + "&fromDt=" + fromDt
                        + "&toDt=" + toDt
                        + "&resultType=" + resultType;
            return url;
    }

    protected String constructURLStringWithoutFromDate(String facility,
                                                       String userId,
                                                       String requestorType,
                                                       String toDt,
                                                       String resultType,
                                                       String userInstanceId
                                                       ) {

            String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                        + "transId=3&appId=roi.report"
                        + "&reportId=" + INVALID_REPORT_ID
                        + "&BLOCKSIZE=8000"
                        + "&userId=ADMIN"
                        + "&userInstanceId=" + userInstanceId
                        + "&facilityNames=" + facility
                        + "&actorIds=" + userId
                        + "&requestorTypes=" + requestorType
                        + "&toDt=" + toDt
                        + "&resultType=" + resultType;
            return url;
    }

    protected String constructURLStringWithoutToDt(String facility,
                                                   String userId,
                                                   String requestorType,
                                                   String fromDt,
                                                   String resultType,
                                                   String userInstanceId
                                                   ) {

             String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                        + "transId=3&appId=roi.report"
                        + "&reportId=" + INVALID_REPORT_ID
                        + "&BLOCKSIZE=8000"
                        + "&userId=ADMIN"
                        + "&userInstanceId=" + userInstanceId
                        + "&facilityNames=" + facility
                        + "&actorIds=" + userId
                        + "&requestorTypes=" + requestorType
                        + "&fromDt=" + fromDt
                        + "&resultType=" + resultType;
            return url;
    }
    
    protected String constructURLStringWithoutInputCriteries(String facility,
                                                             String userId,
                                                             String userInstanceId
                                                           ) {

              String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
                        + "transId=3&appId=roi.report"
                        + "&reportId=" + REPORT_ID
                        + "&BLOCKSIZE=8000"
                        + "&userId=ADMIN"
                        + "&userInstanceId=" + userInstanceId
                        + "&facilityNames=" + facility
                        + "&actorIds=" + userId;
              return url;
    }
    


}
