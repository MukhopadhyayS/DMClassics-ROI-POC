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
package com.mckesson.eig.reports.servlet.test;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pranav Amarasekaran
 * @date   Jan 9, 2008
 * @since  HECM 1.0; Jan 9, 2008
 */
public class ReportServletImpl
extends com.mckesson.eig.reports.servlet.ReportServlet {

    /**
     * Parameter key for transaction id which will be obtained from the client.
     */
    private static final String KEY_TRANS_ID = "transId";

    /**
     * Parameter key for application id which will be obtained from the client.
     */
    private static final String KEY_APP_ID = "appId";

    /**
     * Parameter key for logged in user id which will be obtained from the client.
     */
    private static final String KEY_LOGGEDIN_USERID = "UserName";

    /**
     * @see com.mckesson.eig.reports.servlet.ReportServlet
     *  #getApplicationID(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected String getApplicationID(HttpServletRequest req) {
        return req.getParameter(KEY_APP_ID);
    }

    /**
     * @see com.mckesson.eig.reports.servlet.ReportServlet
     *  #getCurrentUserID(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected String getCurrentUserID(HttpServletRequest req) {
        return req.getParameter(KEY_LOGGEDIN_USERID);
    }

    /**
     * @see com.mckesson.eig.reports.servlet.ReportServlet
     *  #getTransactionID(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected String getTransactionID(HttpServletRequest req) {
        return req.getParameter(KEY_TRANS_ID);
    }

    /**
     * @see com.mckesson.eig.reports.servlet.ReportServlet#initFilePath()
     * @throws IOException
     */
    @Override
    protected String initFilePath()
    throws IOException {

        String dirPath = "C:/temp/report-test";

        File myDir = new File(dirPath);
        if (!myDir.exists()) {
            myDir.mkdirs();
        }

        return dirPath;
    }

    /**
     * @see javax.servlet.GenericServlet#getInitParameter(java.lang.String)
     */
    @Override
    public String getInitParameter(String paramName) {
        return "/com/mckesson/eig/reports/config/test/spring.xml";
    }
}
