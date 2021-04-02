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

package com.mckesson.eig.roi.reports.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.mckesson.eig.roi.reports.service.ROIReportServiceImpl;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;


/**
*
* @author OFS
* @date   April 06, 2009
* @since  HPF 13.1 [ROI]; Oct 14, 2008
*/
public class ROIReportServlet
extends com.mckesson.eig.reports.servlet.ReportServlet {

    /**
     * Object represents the Log4JWrapper object.
     */
    private static final Log LOG = LogFactory.getLogger(ROIReportServlet.class);

    /**
     * Parameter key for transaction id which will be obtained from the client.
     */
    private static final String KEY_TRANS_ID = "TransactionID";

    /**
     * Parameter key for application id which will be obtained from the client.
     */
    private static final String KEY_APP_ID = "appId";

    /**
     * Parameter key for logged in user id which will be obtained from the client.
     */
    private static final String KEY_LOGGEDIN_USERID = "userId";

    /**
     * Parameter key for logged in user id which will be obtained from the client.
     */
    private static final String PROPS_FILE_PATH = "/com/mckesson/eig/roi/reports.properties";

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

        final String filePathKey = "temp.file.path";
        Properties props = new Properties();
        props.load(getClass().getResourceAsStream(PROPS_FILE_PATH));

        String dirPath = props.getProperty(filePathKey);
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
    protected void initConfig() {

        final String logSourceMethod = "initConfig()";
        LOG.debug(logSourceMethod + ">>Start");
        // overridden to avoid loading spring config file
        LOG.debug(logSourceMethod + "<<End");
    }

    /**
     * This method queries the report data based on the report ID, params and sends the csv file
     * path back to the getRemoteFile method.
     *
     * @param reportID
     * @param params
     * @param file
     * @return csv file name
     * @throws IOException
     */
    @Override
    protected void generateReport(String reportID, Map params, File file)
    throws IOException {

        final String logSourceMethod = "generateReport(reportID, params, file)";
        LOG.debug(logSourceMethod + ">>Start");

        //Auditing report generation
        // MU2 Audit Report
        ROIReportServiceImpl reportService = (ROIReportServiceImpl) getService();
        reportService.auditReport(params);

        super.generateReport(reportID, params, file);

        LOG.debug(logSourceMethod + "<<End");
    }
}
