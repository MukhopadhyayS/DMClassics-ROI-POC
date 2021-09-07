/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.reports.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.mckesson.eig.reports.dao.ReportDAO;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * @author Pranav Amarasekaran
 * @date   Dec 13, 2007
 * @since  HECM 1.0
 *
 * This class provides the business methods implementations, which the client will invoke in order
 * to view the reports.
 */
public class ReportServiceImpl
implements com.mckesson.eig.reports.service.ReportService {

    /**
     * Object represents the Log4JWrapper object.
     */
    private static final OCLogger LOG = new OCLogger(ReportServiceImpl.class);

    /**
     * @see com.mckesson.eig.reports.service.ReportService
     *  #generateReport(java.lang.String, java.util.Map)
     *
     *  @throws IOException
     */
    public void generateReport(String reportID, Map params, File file) throws IOException {

        final String logSourceMethod = "generateReport(reportID, params, file)";
        LOG.debug(logSourceMethod + ">>Start");

        OutputStream out = null;

        try {
            out = new FileOutputStream(file);
        } catch (Exception e) {
            throw new IOException("Invalid File Path");
        }

        getReportDAO(getReportType(reportID)).generateReport(reportID, params, out);
        out.close();
        
        LOG.debug(logSourceMethod + "<<End");
    }

    /**
     * This method returns the application bean id to look up the service implementation. This
     * method can be used if the bean id is the same as that of the report id/type. If it is not the
     * case then this method can be overriden in the implementing services.
     *
     * @param reportID
     *          Report Type
     *
     * @return appBeanID
     *          The bean id which has to be looked up.
     *
     * @throws IOException
     */
    protected String getReportType(String reportID) throws IOException {
        return reportID + ".dao";
    }

    /**
     * This method performs the spring look up for the passed dao name and returns a
     * ReportDAO instance.
     *
     * @param serviceName
     *          Name of the Service
     *
     * @return instance of ReportDAO
     */
    protected ReportDAO getReportDAO(String daoName) {
        return (ReportDAO) SpringUtilities.getInstance().getBeanFactory().getBean(daoName);
    }
}
