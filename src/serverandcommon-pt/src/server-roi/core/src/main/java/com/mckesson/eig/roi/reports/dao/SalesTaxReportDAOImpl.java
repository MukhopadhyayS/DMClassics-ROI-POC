/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2011 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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
 * @date   Nov 4, 2011
 * @since  HPF 15.1.1 [ROI]; Nov 4, 2011
 */
public class SalesTaxReportDAOImpl
extends ROIReportDAOImpl {


    private static final OCLogger LOG
    = new OCLogger(SalesTaxReportDAOImpl.class);

    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static final String KEY_REQUESTOR_TYPES = "requestorTypes";
    private static final String KEY_FACILITIES = "facilities";
    private static final String KEY_REQUESTOR_NAME = "requestorName";
    private static final String KEY_FROM_DT = "fromDt";
    private static final String KEY_TO_DT = "toDt";

    private static final int FACILITY = 0;
    private static final int REQUESTOR_TYPE_POSITION = 1;
    private static final int REQUESTOR_NAME_POSITION = 2;
    private static final int REQUEST_ID_POSITION = 3;
    private static final int INVOICE_NUMBER_POSITION = 4;
    private static final int INVOICE_AMOUNT_POSITION = 5;
    private static final int TAX_PERCENTAGE_POSITION = 6;
    private static final int TAX_AMOUNT_POSITION = 7;
    private static final int FEE_TYPE_POSITION = 8;
    private static final int FEE_AMOUNT_POSITION = 9;
    private static final int FEE_TAX_AMOUNT_POSITION = 10;
    private static final int TAXABLE_POSITION = 11;
    private static final int REQUEST_TAX_AMOUNT_POSITION = 12;

    /**
     * @see com.mckesson.eig.reports.dao.ReportDAO
     *  #generateReport(java.lang.String, java.util.Map, java.io.OutputStream)
     */
    @Override
    @SuppressWarnings({ "rawtypes" }) //Not supported by framework
    public void generateReport(String reportID, Map params,
                               OutputStream outputStream)
                               throws IOException {

        final String logSM = "generateReport(reportID, params, outputStream)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:ReportId :" + reportID);
        }
        try {

            Object[] criterias = getCriteria(params);

            List<Object[]> results = processNamedQuery("ROI_Generate_SalesTaxReport",
                                                       criterias);

            PrintStream out = new PrintStream(outputStream, true);
            generateCSV(results, out, getHeaders());

            if (DO_DEBUG) {
                LOG.debug(logSM + "End<<");
            }

        } catch (IOException ioe) {

            throw ioe;
        } catch (Exception e) {

            throw new IOException("Request Detail report generation failed", e);
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
                             .append(ROIReportUtil.getIntegerCsv(result, REQUEST_ID_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getIntegerCsv(result, INVOICE_NUMBER_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getDecimalCsv(result, INVOICE_AMOUNT_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getDecimalCsv(result, TAX_PERCENTAGE_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getDecimalCsv(result, TAX_AMOUNT_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, FEE_TYPE_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getDecimalCsv(result, FEE_AMOUNT_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, TAXABLE_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getDecimalCsv(result, REQUEST_TAX_AMOUNT_POSITION))
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
        String facilities = ROIReportUtil.getStringParam(reportParams, KEY_FACILITIES);
        String requestorName = ROIReportUtil.getStringParam(reportParams, KEY_REQUESTOR_NAME);
        String fromDt = ROIReportUtil.getStringParam(reportParams, KEY_FROM_DT);
        String toDt = ROIReportUtil.getStringParam(reportParams, KEY_TO_DT);
        Date startDate = ROIReportUtil.convertToStartDate(fromDt);
        Date endDate = ROIReportUtil.convertToEndDate(toDt);

        Object[] criterias = {ROIReportUtil.constructListContentXML(requestorTypes,
                                                                    "requestor-type"),
                              ROIReportUtil.constructListContentXML(facilities,
                                                                    "facility"),
                              //  CR# 375059 - Fix
                              ROIReportUtil.constructListContentXML("requestorName",
                                                                  new String[] { requestorName }),

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
               .append("Facility,Requestor Type,Requestor Name,Request ID,")
               .append("Invoice Number,Invoice Amount,")
               .append("Tax Percentage,Tax Amount,")
               .append("Fee Type,Fee Amount,Taxable,Request Tax Amount")
               .toString();

    }
}
