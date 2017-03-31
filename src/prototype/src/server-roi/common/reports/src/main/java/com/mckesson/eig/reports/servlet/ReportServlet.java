/*
 * Copyright 2007-2008 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.reports.servlet;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mckesson.eig.common.filetransfer.services.BaseFileTransferData;
import com.mckesson.eig.reports.service.ReportService;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * @author Pranav Amarasekaran
 * @date   Dec 13, 2007
 * @since  HECM 1.0
 *
 * This handles the request and response of the report data. This takes care of invoking the report
 *  service and transmitting the report data in the servlet output stream.
 */
public abstract class ReportServlet
extends com.mckesson.eig.common.filetransfer.services.BaseFileDownloader {

    /**
     * Parameter key for report id which will be obtained from the client.
     */
    private static final String KEY_REPORT_ID = "reportId";

    /**
     * Spring Config file.
     */
    private static final String INIT_SPRING_CONFIG_FILE = "eig.Report.SpringConfigFile";

    /**
     * Holds the extension of the report file.
     */
    private static final String FILE_EXTENSION = "csv";

    /**
     * Object represents the Log4JWrapper object.
     */
    private static final Log LOG = LogFactory.getLogger(ReportServlet.class);

    /**
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init()
    throws ServletException {

        super.init();
        initConfig();
    }

    /**
     * This method junks the data and sends it back to the requester.
     */
    @Override
    public void getRemoteFile(BaseFileTransferData servData)
    throws IOException {

        final String logSourceMethod = "getRemoteFile(servData)";
        LOG.debug(logSourceMethod + ">>Start");

        HttpServletRequest req = servData.getRequest();
        String filePath = getFileName(req);
        File file = getFile(filePath);

        generateReport(getReportID(req), req.getParameterMap(), file);
        if (writeToOutputStream(servData, filePath)) {
            file.delete();
        }

        LOG.debug(logSourceMethod + "<<End");
    }

    /**
     * This method reads the transaction id, logged in user id, application ID and report type from
     * the request and creates the unique file name and returns back.
     *
     * @param request
     *          Servlet Request.
     *
     * @return complete file path
     * @throws IOException
     */
    protected String getFileName(HttpServletRequest req) throws IOException {

        return initFilePath() + File.separatorChar + getApplicationID(req)
                              + '.'  + getCurrentUserID(req)
                              + '.'  + getTransactionID(req)
                              + '.'  + getReportID(req)
                              + '.'  + FILE_EXTENSION;
    }

    /**
     * This method fetches the transaction id from the request parameter. This method has to be
     * overriden so that the key for fetching the transaction id is made application specific.
     *
     * @param req
     *          Servlet Request.
     *
     * @return Transaction ID
     *          Identification for the transaction.
     */
    protected abstract String getTransactionID(HttpServletRequest req);

    /**
     * This method fetches the application id from the request parameter. This method has to be
     * overriden so that the key for fetching the application id is made application specific.
     *
     * @param req
     *          Servlet Request.
     *
     * @return Application ID
     *          ID of the application.
     */
    protected abstract String getApplicationID(HttpServletRequest req);

    /**
     * This method fetches the current user id from the request parameter. This method has to be
     * overriden so that the key for fetching the current user id is made application specific.
     *
     * @param req
     *          Servlet Request.
     *
     * @return user id
     *          The current user.
     */
    protected abstract String getCurrentUserID(HttpServletRequest req);

    /**
     * This method fetches the report id from the request parameter. This method can be overriden if
     * the key for fetching the report type is made application specific.
     *
     * @param req
     *         Servlet Request.
     *
     * @return report ID
     *          The type of the report to be generated.
     */
    protected String getReportID(HttpServletRequest req) {
        return req.getParameter(KEY_REPORT_ID);
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
    protected void generateReport(String reportID, Map params, File file)
    throws IOException {

        final String logSourceMethod = "generateReport(reportID, params, file)";
        LOG.debug(logSourceMethod + ">>Start");

        getService().generateReport(reportID, params, file);

        LOG.debug(logSourceMethod + "<<End");
    }

    /**
     * This method performs the spring look up for the passed service name and returns a
     * ReportService instance.
     *
     * @return ReportService
     *          The implementation of report service available in the application context.
     */
    protected ReportService getService() {
        return (ReportService) SpringUtilities.getInstance()
                                              .getBeanFactory()
                                              .getBean(ReportService.class.getName());
    }

    /**
     * Initialize the spring container.
     */
    protected void initConfig() {

        final String logSourceMethod = "initConfig()";
        LOG.debug(logSourceMethod + ">>Start");

        String springConfigFile = getInitParameter(INIT_SPRING_CONFIG_FILE);
        BeanFactory springContext = new ClassPathXmlApplicationContext(springConfigFile);

        SpringUtilities.getInstance().setBeanFactory(springContext);

        LOG.debug(logSourceMethod + "<<End");
    }

    /**
     * This method initializes the directory location of the temporary files that are created during
     * report generation.
     *
     * @return dir location
     *          Directory location as a String
     *
     * @throws IOException
     */
    protected abstract String initFilePath() throws IOException;
    
    private static File getFile(final String file) throws IOException {
	//DE7315 External Control of File Name or Path
        try {
            return (File) AccessController
                    .doPrivileged(new PrivilegedExceptionAction<File>() {
                        public File run() throws IOException {
                            return new File(file);
                        }
                    });
        } catch (PrivilegedActionException e) {
            throw (IOException) e.getCause();
        }

    }
    
}
