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
import java.util.List;
import java.util.Map;

import com.mckesson.eig.roi.reports.service.ROIReportUtil;
import com.mckesson.dm.core.common.logging.OCLogger;


/**
 * @author OFS
 * @date   Nov 13, 2008
 * @since  HPF 13.1 [ROI]; Nov 13, 2008
 */
public class RequestOutstandingInvBalanceReportDAOImpl
extends ROIReportDAOImpl {


    private static final OCLogger LOG
    = new OCLogger(RequestOutstandingInvBalanceReportDAOImpl.class);

    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static final String KEY_STATUS = "status";
    private static final String KEY_FACILITIES = "facilities";
    private static final String KEY_FROM_DT = "fromDt";
    private static final String KEY_TO_DT = "toDt";
    private static final String KEY_REQUESTOR_TYPES = "requestorTypes";
    private static final String KEY_REQUESTOR_NAME = "requestorName";
    private static final String KEY_FROM_AGE = "invoiceFromAge";
    private static final String KEY_TO_AGE = "invoiceToAge";
    private static final String KEY_BALANCE_LEVEL = "balanceLevel";
    private static final String KEY_BALANCE_DUE = "balanceDue";

    private static final int FACILITY = 0;
    private static final int REQUESTOR_TYPE_POSITION = 1;
    private static final int REQUESTOR_NAME_POSITION = 2;
    private static final int REQUESTOR_PHONE = 3;
    private static final int REQUESTOR_ID = 4;
    private static final int REQUEST_ID = 5;
    private static final int INVOICE_NO = 6;
    private static final int INVOICE_DATE = 7;
    private static final int INVOICE_COST = 8;
    private static final int AMOUNT_PAID = 9;
    private static final int INVOICE_BALANCE = 10;
    private static final int INVOICE_PAYMENT = 11;
    private static final int INVOICE_ADJUSTMENT = 12;
    private static final int AGING_REQUEST = 13;
    private static final int PATIENT_NAME = 14;
    private static final int PATIENT_MRN = 15;
    private static final int PATIENT_ENCOUNTER = 16;
    private static final int IS_VIP = 17;

    /**
     * @see com.mckesson.eig.reports.dao.ReportDAO
     *  #generateReport(java.lang.String, java.util.Map, java.io.OutputStream)
     */
    @Override
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
            String facilities = ROIReportUtil.getStringParam(params, KEY_FACILITIES);
            String[] facList = facilities.split(",");
            facList = ROIReportUtil.convertASCIIValues(facList);

            List<Object[]> results =
                processNamedQuery("ROI_GenerateOutstandingInvoiceBalanceReport", criterias);

            // CR# 382746 - Fix
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
     * @param headers
     */
    private void generateCSV(List<Object[]> results, PrintStream out, String headers) {


        final String logSM = "generateCsv(List<Object[]> results, PrintStream out)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        // CR# 382746 - Fix
        out.println(headers);
        for (Object[] result : results) {

            String csvData = new StringBuffer()
                             .append(ROIReportUtil.getStringCsv(result, FACILITY))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_TYPE_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_NAME_POSITION))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_PHONE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getUnitResolvedCsv(result, REQUESTOR_ID))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getUnitResolvedCsv(result, REQUEST_ID))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getUnitResolvedCsv(result, INVOICE_NO))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getDateCsv(result, INVOICE_DATE))
                             .append(CSV_DELIM)
                              // The TotalCost, Payment, Balance Cannot be
                              // converted to Integer CSV because it contains the value as "$10.00"
                             .append(ROIReportUtil.getUnitResolvedCsv(result, INVOICE_COST))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getUnitResolvedCsv(result, AMOUNT_PAID))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getUnitResolvedCsv(result, INVOICE_BALANCE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getUnitResolvedCsv(result, INVOICE_PAYMENT))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getUnitResolvedCsv(result, INVOICE_ADJUSTMENT))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getUnitResolvedCsv(result, AGING_REQUEST))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PATIENT_NAME))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PATIENT_MRN))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PATIENT_ENCOUNTER))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, IS_VIP))
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

        String status = ROIReportUtil.getStringParam(reportParams, KEY_STATUS);
        String facilities = ROIReportUtil.getStringParam(reportParams, KEY_FACILITIES);
        String requestorTypes = ROIReportUtil.getStringParam(reportParams, KEY_REQUESTOR_TYPES);
        String requestorName = ROIReportUtil.getStringParam(reportParams, KEY_REQUESTOR_NAME);
        String balanceLevel = ROIReportUtil.getStringParam(reportParams, KEY_BALANCE_LEVEL);
        String balanceDue = ROIReportUtil.getStringParam(reportParams, KEY_BALANCE_DUE);
        String invoiceFromAge = ROIReportUtil.getStringParam(reportParams, KEY_FROM_AGE);
        String invoiceToAge = ROIReportUtil.getStringParam(reportParams, KEY_TO_AGE);

        Object[] criterias = {ROIReportUtil.constructListContentXML(status, "status"),
                              ROIReportUtil.constructListContentXML(facilities, "facility"),
                              ROIReportUtil.formatDate(ROIReportUtil.getCurrentDate(),
                                                       PROCEDURE_DATE_FORMAT),
                              ROIReportUtil.constructListContentXML(requestorTypes,
                                                      "requestor-type"),
                              getSpecialCharSearchStr(requestorName),
                              Integer.parseInt(invoiceFromAge == null ? "0" : invoiceFromAge),
                              Integer.parseInt(invoiceToAge == null ? "-1" : invoiceToAge),
                              balanceLevel,
                              Float.parseFloat(balanceDue == null ? "0" : balanceDue)
                              };

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + criterias.toString());
        }

        return criterias;
    }

    private String getHeaders() {

        return new StringBuffer()
               .append("Facility,RequestorType,RequestorName,RequestorPhone,RequestorId,RequestId,")
               .append("InvoiceNo,InvoiceDate,TotalCost,AmountPaid,BalanceDue,InvoicePayment,")
               .append("InvoiceAdjustment,DaysOutstanding,PatientName,PatientMRN,")
               .append("PatientEncounter,Vip Patient")
               .toString();
    }

}
