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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.mckesson.eig.roi.reports.service.ROIReportUtil;
import com.mckesson.dm.core.common.logging.OCLogger;


/**
 * @author OFS
 * @date   Nov 5, 2008
 * @since  HPF 13.1 [ROI]; Nov 5, 2008
 */
@Transactional
public class RequestPendingAgingSummaryReportDAOImpl
extends ROIReportDAOImpl {


    private static final OCLogger LOG
    = new OCLogger(RequestPendingAgingSummaryReportDAOImpl.class);

    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static final String KEY_REQUESTOR_TYPES = "requestorTypes";
    private static final String KEY_FACILITIES = "facilities";
    private static final String KEY_FROM_DT = "fromDt";
    private static final String KEY_TO_DT = "toDt";

    private static final int FACILITY = 0;
    private static final int REQUESTOR_TYPE_POSITION = 1;
    private static final int REQUESTOR_NAME_POSITION = 2;
    private static final int DAYS_0_5_POSITION = 3;
    private static final int DAYS_6_10_POSITION = 4;
    private static final int DAYS_11_15_POSITION = 5;
    private static final int DAYS_16_20_POSITION = 6;
    private static final int DAYS_21_25_POSITION = 7;
    private static final int DAYS_26_30_POSITION = 8;
    private static final int DAYS_30_ABOVE_POSITION = 9;

    /**
     * @see com.mckesson.eig.reports.dao.ReportDAO
     *  #generateReport(java.lang.String, java.util.Map, java.io.OutputStream)
     */
    @SuppressWarnings("unchecked") //Not supported by framework
    public void generateReport(String reportID, Map params,
                               OutputStream outputStream)
                               throws IOException {

        final String logSM = "generateReport(reportID, params, outputStream)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:ReportId :" + reportID);
        }
        try {

            Object[] criterias = getCriteria(params);

            List<Object[]> results = processNamedQuery("ROI_Generate_PendingAgingReport",
                                                       criterias);

            PrintStream out = new PrintStream(outputStream, true);
            generateCSV(results, out, getHeaders());

            if (DO_DEBUG) {
                LOG.debug(logSM + "End<<");
            }

        } catch (IOException ioe) {

            throw ioe;
        } catch (Exception e) {

            throw new IOException("Request Detail report generation failed");
        }
    }

    /**
     * Method to process the result set and generate csv
     * @param results
     * @param out
     */
    private void generateCSV(List<Object[]> results, PrintStream out, String headers) {


        final String logSM = "generateCsv(List<Object[]> results, PrintStream out)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        out.println(headers);

        for (Object[] result : results) {

            String csvData = new StringBuffer()
                             .append(ROIReportUtil.getStringCsv(result, FACILITY))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_TYPE_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_NAME_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getIntegerCsv(result, DAYS_0_5_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getIntegerCsv(result, DAYS_6_10_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getIntegerCsv(result, DAYS_11_15_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getIntegerCsv(result, DAYS_16_20_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getIntegerCsv(result, DAYS_21_25_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getIntegerCsv(result, DAYS_26_30_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getIntegerCsv(result, DAYS_30_ABOVE_POSITION))
                             .toString();

           out.print(csvData);
           out.println();
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End");
        }
    }

    /** Method to process the required criteria
     *  from the HashMap
     *
     * @param reportParams
     * @return
     */
    @SuppressWarnings("unchecked")
    private Object[] getCriteria(Map reportParams) throws IOException {

        final String logSM = "processCriterias(HashMap<String, Object> reportParams)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        String requestorTypes = ROIReportUtil.getStringParam(reportParams, KEY_REQUESTOR_TYPES);
        String facilities = ROIReportUtil.getStringParam(reportParams, KEY_FACILITIES);
        String fromDt = ROIReportUtil.getStringParam(reportParams, KEY_FROM_DT);
        String toDt = ROIReportUtil.getStringParam(reportParams, KEY_TO_DT);
        Date startDate = ROIReportUtil.convertToStartDate(fromDt);
        Date endDate = ROIReportUtil.convertToEndDate(toDt);

        Object[] criterias = {ROIReportUtil.constructListContentXML(requestorTypes,
                                                                    "requestor-type"),
                              ROIReportUtil.constructListContentXML(facilities,
                                                                    "facility"),
                              ROIReportUtil.formatDate(startDate, PROCEDURE_DATE_FORMAT),
                              ROIReportUtil.formatDate(endDate, PROCEDURE_DATE_FORMAT),
                              ROIReportUtil.formatDate(ROIReportUtil.getCurrentDate(),
                                                       PROCEDURE_DATE_FORMAT)
                             };

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + criterias.toString());
        }

        return criterias;
    }

    private String getHeaders() {

        return new StringBuffer()
               .append("Facility, Requestor Type,Requestor Name,")
               .append("PendingDays_0_to_5,PendingDays_6_to_10,PendingDays_11_to_15,")
               .append("PendingDays_16_to_20,PendingDays_21_to_25,PendingDays_26_to_30,")
               .append("PendingDays_30_above")

        .toString();
    }
}
