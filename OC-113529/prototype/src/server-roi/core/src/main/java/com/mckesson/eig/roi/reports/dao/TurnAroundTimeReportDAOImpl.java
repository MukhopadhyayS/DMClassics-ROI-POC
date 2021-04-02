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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mckesson.eig.roi.reports.service.ROIReportUtil;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;


/**
 * @author OFS
 * @date   Dec 17, 2008
 * @since  HPF 13.1 [ROI]; Dec 17, 2008
 */
public class TurnAroundTimeReportDAOImpl
extends ROIReportDAOImpl {


    private static final Log LOG = LogFactory.getLogger(TurnAroundTimeReportDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final String KEY_REQUESTOR_TYPES = "requestorTypes";
    private static final String KEY_FROM_STATUS = "fromStatus";
    private static final String KEY_TO_STATUS = "toStatus";
    private static final String KEY_FROM_DT = "fromDt";
    private static final String KEY_TO_DT = "toDt";
    private static final String KEY_RESULT_TYPE = "resultType";
    private static final String KEY_FACILITIES = "facilities";

    private static final int REQUEST_ID = 0;
    private static final int REQUESTOR_TYPE = 1;
    private static final int REQUESTOR_NAME = 2;
    private static final int REQUEST_CREATED_DATE = 3;
    private static final int REQUEST_CREATED_BY = 4;
    private static final int REQUEST_CURRENT_STATUS = 5;
    private static final int TOTAL_PAGES = 6;
    private static final int TOTAL_DAYS = 7;
    private static final int TAT = 8;
    private static final int FROM_STATUS_CHANGE_USER = 9;
    private static final int FROM_STATUS_CHANGE_DATE = 10;
    private static final int TO_STATUS_CHANGE_USER = 11;
    private static final int TO_STATUS_CHANGE_DATE = 12;
    private static final int INTERIUM_STATUS_CHANGE = 13;
    private static final int FACILITY_CODE = 14;
    private static final int FACILITY_NAME = 15;

    private static final int SUMMARY_REQUESTOR_TYPE = 0;
    private static final int SUMMARY_REQUESTOR_NAME = 1;
    private static final int SUMMARY_REQUEST_COUNT = 2;
    private static final int SUMMARY_TOTAL_PAGES = 3;
    private static final int SUMMARY_TAT = 4;
    private static final int SUMMARY_FACILITY = 5;

    /**
     * @see com.mckesson.eig.reports.dao.ReportDAO
     *  #generateReport(java.lang.String, java.util.Map, java.io.OutputStream)
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void generateReport(String reportID, Map params,
                               OutputStream outputStream)
                               throws IOException {

        final String logSM = "generateReport(String reportID, Map params, "
        		                           + "OutputStream outputStream)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:ReportId :" + reportID);
        }

        try {

            Object[] criterias = getCriteria(params);
            List<Object[]> results = new ArrayList <Object[]>();
            String resultType = ROIReportUtil.getStringParam(params, KEY_RESULT_TYPE);

            PrintStream out = new PrintStream(outputStream, true);
            if ("summary".equalsIgnoreCase(resultType)) {

                results = processNamedQuery("Generate_TurnAroundTimeReport_Summary_View",
                                             criterias);
                generateSummaryCSV(results, out, getSummaryHeaders(), params);

            } else {

                results = processNamedQuery("Generate_TurnAroundTimeReport", criterias);
                generateDetailCSV(results, out, getHeaders(), params);
            }


            if (DO_DEBUG) {
                LOG.debug(logSM + "End<<");
            }

        } catch (IOException ioe) {

            throw ioe;
        } catch (Exception e) {

            throw new IOException("TurnAroundTimeReport report generation failed", e);
        }
    }

    /**
     * Method to process the result set and generate csv
     * @param results
     * @param out
     */
    @SuppressWarnings("rawtypes")
    private void generateSummaryCSV(List<Object[]> results,
                             PrintStream out,
                             String headers, Map params) {


        final String logSM = "generateSummaryCSV(List<Object[]> results, PrintStream out)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        out.println(headers);

        for (Object[] result : results) {

            String csvData =
                    new StringBuffer()

                            .append(ROIReportUtil.getStringCsv(result, SUMMARY_REQUESTOR_TYPE))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getStringCsv(result, SUMMARY_REQUESTOR_NAME))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getIntegerCsv(result, SUMMARY_REQUEST_COUNT))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getIntegerCsv(result, SUMMARY_TOTAL_PAGES))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getBigDecimalCsv(result, SUMMARY_TAT))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getStringCsv(result, SUMMARY_FACILITY))
                            .toString();

            out.print(csvData);
            out.println();
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End");
        }
    }


    /**
     * Method to process the result set and generate csv
     * @param results
     * @param out
     */
    @SuppressWarnings("rawtypes")
    private void generateDetailCSV(List<Object[]> results,
                             PrintStream out,
                             String headers, Map params) {


        final String logSM = "generateDetailCSV(List<Object[]> results, PrintStream out)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        out.println(headers);
        for (Object[] result : results) {

            String csvData =
                    new StringBuffer()
                            .append(ROIReportUtil.getIntegerCsv(result, REQUEST_ID))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getStringCsv(result, REQUESTOR_TYPE))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getStringCsv(result, REQUESTOR_NAME))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getDateTimeCsv(result, REQUEST_CREATED_DATE))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getStringCsv(result, REQUEST_CREATED_BY))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getStringCsv(result, REQUEST_CURRENT_STATUS))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getIntegerCsv(result, TOTAL_PAGES))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getIntegerCsv(result, TOTAL_DAYS))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getBigDecimalCsv(result, TAT))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getStringCsv(result, FROM_STATUS_CHANGE_USER))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getDateTimeCsv(result, FROM_STATUS_CHANGE_DATE))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getStringCsv(result, TO_STATUS_CHANGE_USER))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getDateTimeCsv(result, TO_STATUS_CHANGE_DATE))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getStringCsv(result, INTERIUM_STATUS_CHANGE))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getStringCsv(result, FACILITY_CODE))
                            .append(CSV_DELIM)
                            .append(ROIReportUtil.getStringCsv(result, FACILITY_NAME))
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
    @SuppressWarnings("rawtypes")
    private Object[] getCriteria(Map reportParams) throws IOException {

        final String logSM = "processCriterias(HashMap<String, Object> reportParams)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        String requestorTypes = ROIReportUtil.getStringParam(reportParams, KEY_REQUESTOR_TYPES);
        String fromStatus = ROIReportUtil.getStringParam(reportParams, KEY_FROM_STATUS);
        String toStatus = ROIReportUtil.getStringParam(reportParams, KEY_TO_STATUS);
        String facilities = ROIReportUtil.getStringParam(reportParams, KEY_FACILITIES);


        String fromDt = ROIReportUtil.getStringParam(reportParams, KEY_FROM_DT);
        String toDt = ROIReportUtil.getStringParam(reportParams, KEY_TO_DT);

        Date startDate = ROIReportUtil.convertToStartDate(fromDt);
        Date endDate = ROIReportUtil.convertToEndDate(toDt);

        Object[] criterias = {ROIReportUtil.constructListContentXML(requestorTypes,
                                                                    "requestor-type"),
                              fromStatus,
                              toStatus,
                              ROIReportUtil.formatDate(startDate, PROCEDURE_DATE_FORMAT),
                              ROIReportUtil.formatDate(endDate, PROCEDURE_DATE_FORMAT),
                              ROIReportUtil.constructListContentXML(facilities, "facility")};

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + criterias.toString());
        }
        return criterias;
    }

    private String getHeaders() {

        return new StringBuffer()
                       .append("RequestId,")
                       .append("RequestorType,")
                       .append("RequestorName,")
                       .append("RequestCreatedDate,")
                       .append("RequestCreatedBy,")
                       .append("RequestCurrentStatus,")
                       .append("Pages,")
                       .append("Totaldays,")
                       .append("TAT,")
                       .append("FromStatusChangedUser,")
                       .append("FromStatusChangedDate,")
                       .append("ToStatusChangedUser,")
                       .append("ToStatusChangedDate,")
                       .append("InteriumStatusChange,")
                       .append("FacilityCode,")
                       .append("FacilityName")
                       .toString();
    }

    private String getSummaryHeaders() {

        return new StringBuffer()

                       .append("RequestorType,")
                       .append("RequestorName,")
                       .append("TotalRequestsCount,")
                       .append("TotalPages,")
                       .append("TAT,")
                       .append("Facility")
                       .toString();
    }
}
