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

import com.mckesson.eig.roi.reports.service.ROIReportUtil;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;


/**
*
* @author OFS
* @date   Oct 14, 2008
* @since  HPF 13.1 [ROI]; Oct 14, 2008
*/
public class RequestDetailReportDAOImpl
extends ROIReportDAOImpl {


    private static final Log LOG = LogFactory.getLogger(RequestDetailReportDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final String KEY_REQUESTOR_TYPES = "requestorTypes";
    private static final String KEY_STATUS = "status";
    private static final String KEY_FROM_DT = "fromDt";
    private static final String KEY_TO_DT = "toDt";
    private static final String KEY_FACILITIES = "facilities";

    private static final int REQUESTOR_TYPE_POSITION = 0;
    private static final int REQUESTOR_NAME_POSITION = 1;
    private static final int CREATED_DATE_POSITION = 2;
    private static final int STATUS_POSITION = 3;
    private static final int REQUEST_ID_POSITION = 4;
    private static final int FACILITY_POSITION = 5;
    private static final int PATIENT_NAME_POSITION = 6;
    private static final int ENCOUNTER_ID_POSITION = 7;
    private static final int ADMIT_DATE_POSITION = 8;
    private static final int PATIENT_TYPE_POSITION = 9;
    private static final int PAGES_POSITION = 10;
    private static final int STATUS_CREATED_POSITION = 11;
    private static final int IS_VIP_POSITION = 12;
    private static final int IS_LOCKED_POSITION = 13;
    private static final int PREBILL_ID_POSITION = 15;
    private static final int BALANCE_DUE_POSITION = 16;


    /**
     * @see com.mckesson.eig.reports.dao.ReportDAO
     *  #generateReport(java.lang.String, java.util.Map, java.io.OutputStream)
     */
    @SuppressWarnings("unchecked") //Not supported by framework
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

            List<Object[]> results = processNamedQuery("Generate_RequestDetailReport", criterias);

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
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_TYPE_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_NAME_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getDateCsv(result, CREATED_DATE_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, STATUS_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getIntegerCsv(result, REQUEST_ID_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, FACILITY_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PATIENT_NAME_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, ENCOUNTER_ID_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.addDoubleQuotes(
                                     ROIReportUtil.getDateCsv(result, ADMIT_DATE_POSITION)))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PATIENT_TYPE_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PAGES_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getDateCsv(result, STATUS_CREATED_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, IS_VIP_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, IS_LOCKED_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getIntegerCsv(result, PREBILL_ID_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getBalanceCsv(result, BALANCE_DUE_POSITION))
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
        String status = ROIReportUtil.getStringParam(reportParams, KEY_STATUS);
        String facilities = ROIReportUtil.getStringParam(reportParams, KEY_FACILITIES);
        String fromDt = ROIReportUtil.getStringParam(reportParams, KEY_FROM_DT);
        String toDt = ROIReportUtil.getStringParam(reportParams, KEY_TO_DT);
        Date startDate = ROIReportUtil.convertToStartDate(fromDt);
        Date endDate = ROIReportUtil.convertToEndDate(toDt);

        Object[] criterias = {ROIReportUtil.constructListContentXML(requestorTypes,
                                                                    "requestor-type"),
                              ROIReportUtil.constructListContentXML(status,
                                                                    "request-status"),
                              ROIReportUtil.formatDate(startDate, PROCEDURE_DATE_FORMAT),
                              ROIReportUtil.formatDate(endDate, PROCEDURE_DATE_FORMAT),
                              ROIReportUtil.constructListContentXML(facilities,
                              "facility")};

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + criterias.toString());
        }

        return criterias;
    }

    private String getHeaders() {

        return new StringBuffer()
               .append("Requestor Type,Requestor Name,")
               .append("Date Created,Request Status,Request Id,Facility,")
               .append("Patient Name,Encounter Id,")
               .append("Admit Date,Patient Type,Pages,Status Created,")
               .append("Vip Patient,Locked Patient,Invoice Number,PreBill Amount")
               .toString();
    }
}
