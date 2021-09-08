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
import com.mckesson.dm.core.common.logging.OCLogger;


/**
 * @author OFS
 * @date   Nov 13, 2008
 * @since  HPF 13.1 [ROI]; Nov 13, 2008
 */
public class RequestAccountReceivableAgingReportDAOImpl
extends ROIReportDAOImpl {


    private static final OCLogger LOG
    = new OCLogger(RequestAccountReceivableAgingReportDAOImpl.class);

    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static final String KEY_REQUESTOR_TYPES = "requestorTypes";
    private static final String KEY_FACILITIES = "facilities";
    private static final String KEY_FROM_DT = "fromDt";
    private static final String KEY_TO_DT = "toDt";

    private static final int FACILITY = 0;
    private static final int REQUESTOR_TYPE_POSITION = 1;
    private static final int REQUESTOR_NAME_POSITION = 2;
    private static final int REQUSTOR_ID = 3;
    private static final int AGING_REQUEST = 4;
    private static final int TOTAL_COST = 5;
    private static final int PAYMENT = 6;
    private static final int ADJUSTMENT = 7;
    private static final int BALANCE = 8;
    private static final int REQUEST_ID = 9;

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

            List<Object[]> results = processNamedQuery("ROI_GenerateAccountReceivableAgingReport",
                                                       criterias);

            PrintStream out = new PrintStream(outputStream, true);
            generateCSV(results, out, getHeaders());

            if (DO_DEBUG) {
                LOG.debug(logSM + "End<<");
            }

        } catch (IOException ioe) {

            throw ioe;
        } catch (Exception e) {

            throw new IOException("AccountReceivableAgingReport generation failed");
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
                             // Requestor Id is unquoted because Aging value not displayed 
                             // corretly(30+, 60+..)
                             .append(ROIReportUtil.getUnQuotedStringCsv(result, REQUSTOR_ID))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, AGING_REQUEST))
                             .append(CSV_DELIM)
                             // Added for CR# 346,830
                             // The TotalCost, Payment, Adjustment, Balance Cannot be 
                             // converted to Integer CSV because it contains the value as "$10.00"
                             .append(ROIReportUtil.getUnitResolvedCsv(result, TOTAL_COST))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getUnitResolvedCsv(result, PAYMENT))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getUnitResolvedCsv(result, ADJUSTMENT))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getUnitResolvedCsv(result, BALANCE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUEST_ID))
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
                                                       PROCEDURE_DATE_FORMAT)};

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + criterias.toString());
        }

        return criterias;
    }

    private String getHeaders() {

        return new StringBuffer()
               .append("Facility,RequestorType,RequestorName,RequestorId,")
               .append("AgingRequestType,TotalCost,Payement,")
               .append("Adjustment,BalanceDue,RequestId")

        .toString();
    }
}
