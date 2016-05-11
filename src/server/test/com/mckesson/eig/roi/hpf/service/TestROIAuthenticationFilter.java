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

package com.mckesson.eig.roi.hpf.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.mckesson.eig.roi.test.BaseROITestCase;
import com.mckesson.eig.wsfw.session.WsSession;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

/**
 * Testcase which tests the service without deploying it in any container.
 *
 */
public class TestROIAuthenticationFilter
extends BaseROITestCase {


    /**
     * Sets up the data required for testing the service.
     *
     * @throws Exception
     *             General Exception.
     * @see junit.framework.TestCase#setUp()
     */
    public void initializeTestData() throws Exception {
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
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
                                            "2/13/2009",
                                            dateFormat.format(cal.getTime()).toString(),
                                            "8",
                                            "1",
                                            "roi",
                                            getTestUsername(getName()));

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = getClient().sendRequest(request);
            assertNotNull("Response was null", response);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    /**
     * Test case to generate logged status report with Invalid input
     */
    public void testReportWithValidTicket() {

        try {

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setCalendar(cal);
            String url = constructURLString("Patient,Attorney",
                                            "2/13/2009",
                                            dateFormat.format(cal.getTime()).toString(),
                                            "8",
                                            "1",
                                            "roi",
                                            getTestUsername(getName()));

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = getClient().sendRequest(request);
            assertNotNull("Response was null", response);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    /**
     * Test case to generate logged status report with Invalid input
     */
    public void testReportWithInvalidUsername() {

        try {

            WsSession.removeAllSessionData();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setCalendar(cal);
            String url = constructURLString("Patient,Attorney",
                                            "2/13/2009",
                                            dateFormat.format(cal.getTime()).toString(),
                                            "8",
                                            "1",
                                            "roi",
                                            "tesmp");

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = getClient().sendRequest(request);
            assertNotNull("Response was null", response);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }


    /**
     * Test case to generate logged status report with Invalid input
     */
    public void testReportWithInvalidTicketInput() {

        try {

            WsSession.removeAllSessionData();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setCalendar(cal);
            String url = constructTicketURLString("Patient,Attorney",
                                            "2/13/2009",
                                            dateFormat.format(cal.getTime()).toString(),
                                            "8",
                                            "1",
                                            "test",
                                            "tesmp");

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = getClient().sendRequest(request);
            assertNotNull("Response was null", response);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test case to generate logged status report with Invalid input
     */
    public void testReportWithInvalidInput() {

        try {

            WsSession.removeAllSessionData();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setCalendar(cal);
            String url = constructURLString("Patient,Attorney",
                                            "2/13/2009",
                                            dateFormat.format(cal.getTime()).toString(),
                                            "8",
                                            "1",
                                            "test",
                                            "tesmp");

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = getClient().sendRequest(request);
            assertNotNull("Response was null", response);

        } catch (Exception e) {
            fail(e.getMessage());
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
                                        String reportId,
                                        String userInstanceId,
                                        String appId,
                                        String userName) {

        String url = "http://127.0.0.1:8080/reports?"
                    + "TransactionID=3"
                    + "&appId=roi.report"
                    + "&reportId=" + reportId
                    + "&BLOCKSIZE=8000"
                    + "&userId=ADMIN"
                    + "&AppId=" + appId
                    + "&USERNAME=" + userName
                    + "&PASSWORD=" + getTestPassword(userInstanceId)
                    + "&userInstanceId=" + userInstanceId
                    + "&requestorTypes=" + requestorTypes
                    + "&fromDt=" + fromDt
                    + "&toDt=" + toDt;
        return url;
    }

    /**
     * This method is used to generate the URL that will hit the servlet.
     *
     * @return URL
     */
    protected String constructTicketURLString(String requestorTypes,
                                        String fromDt,
                                        String toDt,
                                        String reportId,
                                        String userInstanceId,
                                        String appId,
                                        String userName) {

        String url = "http://127.0.0.1:8080/reports?"
                    + "transId=3&appId=roi.report"
                    + "&reportId=" + reportId
                    + "&BLOCKSIZE=8000"
                    + "&userId=ADMIN"
                    + "&AuthTicket=" + "1234"
                    + "&AppId=" + "test"
                    + "&USERNAME=" + null
                    + "&PASSWORD=" + getTestPassword(userInstanceId)
                    + "&userInstanceId=" + userInstanceId
                    + "&requestorTypes=" + requestorTypes
                    + "&fromDt=" + fromDt
                    + "&toDt=" + toDt;
        return url;
    }
}
