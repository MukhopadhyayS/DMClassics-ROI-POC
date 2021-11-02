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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import com.mckesson.eig.roi.billing.service.BillingCoreServiceImpl;
import com.mckesson.eig.roi.muroioutbound.dao.MUROIOutboundDAO;
import com.mckesson.eig.roi.reports.service.ROIReportUtil;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.utils.SpringUtil;


/**
 * @author OFS
 * @date   Dec 1, 2008
 * @since  HPF 13.1 [ROI]; Dec 1, 2008
 */
@Transactional
public class PostedPaymentsSummaryReportDAOImpl
extends ROIReportDAOImpl {


    private static final OCLogger LOG
    = new OCLogger(PostedPaymentsSummaryReportDAOImpl.class);

    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static final String KEY_FACILITIES = "facilities";
    private static final String KEY_REQUESTOR_TYPES= "requestorTypes";
    private static final String KEY_ACTOR_IDS= "actorIds";
    private static final String KEY_FROM_DT = "fromDt";
    private static final String KEY_TO_DT = "toDt";
    private static final String RESULT_TYPE = "resultType";

    private static final int FACILITY = 0;
    private static final int DATE = 1;
    private static final int POSTED_BY = 2;
    private static final int REQUESTOR_TYPE = 3;
    private static final int REQUESTOR_NAME = 4;
    private static final int REQUEST_ID = 5;
    private static final int MRN = 6;
    private static final int INVOICE_NUMBER = 7;
    private static final int PAYMENT_METHOD = 8;
    private static final int PAYMENT_DETAILS = 9;
    private static final int PAYMENT_ID = 10;
    private static final int PAYMENT = 11;

    /**
     * Method to get the instance for BillingCoreServiceImpl
     */
    private BillingCoreServiceImpl getBillingCoreServiceImpl() {
        return (BillingCoreServiceImpl) SpringUtil
                .getObjectFromSpring("com.mckesson.eig.roi.billing.service.BillingCoreServiceImpl");
    }

    /**
     * @see com.mckesson.eig.reports.dao.ReportDAO
     *  #generateReport(java.lang.String, java.util.Map, java.io.OutputStream)
     */
    @SuppressWarnings("unchecked")
    public void generateReport(String reportID, Map params,
                               OutputStream outputStream)
                               throws IOException {

        final String logSM = "generateReport(reportID, params, outputStream)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:ReportId :" + reportID);
        }
        try {
            Object[] criterias = getCriteria(params);
            String[] facList = (String[]) criterias[0];
            List<String> userNames = (List<String>) criterias[1];
            String[] requestorType = (String[]) criterias[2];
            Date fromDate = (Date) criterias[3];
            Date toDate = (Date) criterias[4];
            String resultType = (String) criterias[5];
            PrintStream printStream = new PrintStream(outputStream, true);
            List<String> facilityWithResults = new ArrayList<String>();
            List<String> userNamesWithResults = new ArrayList<String>();
            List<Object[]> results = getBillingCoreServiceImpl()
                    .retrievePostPaymentReportDetails(facList,userNames,requestorType,fromDate,toDate,resultType);
            printStream.println(getHeaders());
            if (results != null && results.size() > 0) {
                for (Object[] object : results) {
                    if (null != object[0] && null != object[2]){
                        facilityWithResults.add(object[0].toString());
                        userNamesWithResults.add(object[2].toString());
                    }
                }

                   generateCSV(results, printStream);
                }
            Collection<String> subFacList = CollectionUtils.subtract(Arrays.asList(facList), facilityWithResults);
            Collection<String> subUserNamesList = CollectionUtils.subtract(userNames, userNamesWithResults);

            if ("\"Posted By\"".equalsIgnoreCase(resultType)) {
                generateCSVForNoReportFirstLevel(printStream, subUserNamesList);
            } else {
                 generateCSVForNoReportSecondLevel(printStream, subFacList);
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "End<<");
            }

        } catch (IOException ioe) {

            throw ioe;
        } catch (Exception e) {

            throw new IOException("Posted Payments Summary report generation failed");
        }
    }

    /**
     * Method to process the result set and generate csv
     * @param results
     * @param out
     */
    private void generateCSV(List<Object[]> results, PrintStream out) {


        final String logSM = "generateCsv(List<Object[]> results, PrintStream out)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        for (Object[] result : results) {

            String csvData = new StringBuffer()
                             .append(ROIReportUtil.getStringCsv(result, FACILITY))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getDateCsv(result, DATE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, POSTED_BY))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_TYPE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_NAME))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUEST_ID))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, MRN))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, INVOICE_NUMBER))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PAYMENT_METHOD))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PAYMENT_DETAILS))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PAYMENT_ID))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getDecimalCsv(result, PAYMENT))
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

        String facility = ROIReportUtil.getStringParam(reportParams,KEY_FACILITIES);
        String[] facilityList = facility.split(",");
        String userName = ROIReportUtil.getStringParam(reportParams,KEY_ACTOR_IDS);
        String[] userNameList = userName.split(",");
        List<String> userNames = new ArrayList<String>();
        for(String str : userNameList)
        {
            String value = getDao().getUserName(Integer.parseInt(str));
            userNames.add(value);
        }
        String requestortype = ROIReportUtil.getStringParam(reportParams,KEY_REQUESTOR_TYPES);
        String[] requestortypeList = requestortype.split(",");
        String fromDt = ROIReportUtil.getStringParam(reportParams, KEY_FROM_DT);
        String toDt = ROIReportUtil.getStringParam(reportParams, KEY_TO_DT);
        Date startDate = ROIReportUtil.convertToStartDate(fromDt);
        Date endDate = ROIReportUtil.convertToEndDate(toDt);
        String resultType = ROIReportUtil.getStringParam(reportParams,
                RESULT_TYPE);

        Object[] criterias = {facilityList, userNames,requestortypeList, startDate, endDate, resultType};

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + criterias.toString());
        }

        return criterias;
    }

    /**
     * Method to process the result set and generate csv
     * @param results
     * @param out
     */
    private void generateCSVForNoReportSecondLevel(PrintStream out,Collection<String> subFacList) {


        final String logSM = "generateCSVForNoReport(PrintStream out,String facilityName)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }
       for (Iterator<String> iterator = subFacList.iterator(); iterator.hasNext();) {
        String facilityName = (String) iterator.next();
        Object[] result = {facilityName, null, null, null, null, null, null, null,
                null, null, null, null};

            String csvData = new StringBuffer()
                             .append(ROIReportUtil.getStringCsv(result, FACILITY))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getDateTimeCsv(result, DATE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, POSTED_BY))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_TYPE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_NAME))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUEST_ID))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, MRN))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, INVOICE_NUMBER))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PAYMENT_METHOD))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PAYMENT_DETAILS))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PAYMENT_ID))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getDecimalCsv(result, PAYMENT))
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
    private void generateCSVForNoReportFirstLevel(PrintStream out,Collection<String> subUserNamesList) {


        final String logSM = "generateCSVForNoReport(PrintStream out,String facilityName)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }
       for (Iterator<String> iterator = subUserNamesList.iterator(); iterator.hasNext();) {
        String userName = (String) iterator.next();
        Object[] result = {null, null, userName, null, null, null, null, null,
                null, null, null, 0.00D};

            String csvData = new StringBuffer()
                             .append(ROIReportUtil.getStringCsv(result, FACILITY))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getDateTimeCsv(result, DATE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, POSTED_BY))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_TYPE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_NAME))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUEST_ID))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, MRN))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, INVOICE_NUMBER))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PAYMENT_METHOD))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PAYMENT_DETAILS))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PAYMENT_DETAILS))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PAYMENT_ID))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getDecimalCsv(result, PAYMENT))
                             .toString();
           out.print(csvData);
           out.println();
       }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End");
        }
    }
    
    /**
     * Method to get the instance for MUROIOutboundDAOImpl
     */
    private MUROIOutboundDAO getDao() {
        return (MUROIOutboundDAO) SpringUtil
                .getObjectFromSpring("MUROIOutboundDAO");
    }

    private String getHeaders() {

        return new StringBuffer()
               .append("Facility,Date,PostedBy,RequestorType,RequestorName,RequestID,MRN,InvoiceNo,PaymentMethod,PaymentDetails,PaymentID,Payment")
        .toString();
    }
}


