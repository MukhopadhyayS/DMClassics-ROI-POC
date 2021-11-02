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
 * @date   June 10, 2009
 * @since  HPF 13.1 [ROI]; Dec 9, 2008
 */
@Transactional
public class AODReportDAOImpl
extends ROIReportDAOImpl {

    private static final OCLogger LOG
    = new OCLogger(AODReportDAOImpl.class);

    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static final String KEY_MRN = "mrn";
    private static final String KEY_FACILITY = "facilities";
    private static final String KEY_PATIENT_ID = "patientId";
    private static final String KEY_REQUEST_TYPE = "requestType";
    private static final String KEY_FROM_DT = "fromDt";
    private static final String KEY_TO_DT = "toDt";

    private static final int PATIENT_NAME = 0;
    private static final int PATIENT_DOB = 1;
    private static final int GENDER = 2;

    private static final int FACILITY = 3;
    private static final int MRN = 4;
    private static final int REQUESTOR_ID = 5;
    private static final int REQUESTOR_NAME = 6;

    // US4859 - TA14124 Altered Stored proc for adding address
    private static final int REQUESTOR_ADDRESSLINE1 = 7;
    private static final int REQUESTOR_ADDRESSLINE2 = 8;
    private static final int REQUESTOR_ADDRESSLINE3 = 9;
    private static final int REQUESTOR_CITYSTATE = 10;
    private static final int RELEASE_DT = 11;
    private static final int DISCHARGE_DT = 12;
    private static final int VISIT_TYPE = 13;
    private static final int REQUEST_REASON = 14;
    private static final int REQUEST_TYPE = 15;
    private static final int ENCOUNTER = 16;

    // US4860 - TA14135 Alter Store proc. To include self pay on encounter
    private static final int SELF_PAY = 17;
    private static final int DOCUMENT_TYPE = 18;
    private static final int DOC_NAME = 19;
    private static final int PAGES = 20;
    private static final int REQUEST_ID = 21;
    private static final int SUBTITLE = 22;
    private static final int IS_VIP = 23;
    private static final int IS_LOCKED = 24;
    private static final int PATIENT_EPN = 25;

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
            long start  = System.currentTimeMillis();

            List<Object[]> results
            = processNamedQuery("ROI_Generate_AODReport", criterias);
            LOG.debug("END OF query processing<<" + (System.currentTimeMillis() - start));

            PrintStream out = new PrintStream(outputStream, true);
            generateCSV(results, out, getHeaders());

            if (DO_DEBUG) {
                LOG.debug(logSM + "End<<");
            }

        } catch (IOException ioe) {

            throw ioe;
        } catch (Exception e) {

            throw new IOException("AODReport generation failed");
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

                             .append(ROIReportUtil.getStringCsv(result, PATIENT_NAME))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getDateCsv(result, PATIENT_DOB))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, GENDER))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, MRN))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, FACILITY))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_ID))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_NAME))
                             .append(CSV_DELIM)

                             // US4859 - TA14124 Altered Stored proc for adding address
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_ADDRESSLINE1))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_ADDRESSLINE2))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_ADDRESSLINE3))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_CITYSTATE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getDateTimeCsv(result, RELEASE_DT))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.addDoubleQuotes(
                                     ROIReportUtil.getDateCsv(result, DISCHARGE_DT)))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, VISIT_TYPE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUEST_REASON))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUEST_TYPE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, ENCOUNTER))
                             .append(CSV_DELIM)

                             // US4860 - TA14135 Alter Store proc. To include self pay on encounter
                             .append(ROIReportUtil.getIntegerCsv(result, SELF_PAY))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, DOCUMENT_TYPE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, DOC_NAME))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PAGES))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUEST_ID))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, SUBTITLE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, IS_VIP))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, IS_LOCKED))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PATIENT_EPN))
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

        String facility = ROIReportUtil.getStringParam(reportParams, KEY_FACILITY);
        String mrn = ROIReportUtil.getStringParam(reportParams, KEY_MRN);
        String patientId = ROIReportUtil.getStringParam(reportParams, KEY_PATIENT_ID);
        String requestType = ROIReportUtil.getStringParam(reportParams, KEY_REQUEST_TYPE);
        String fromDt = ROIReportUtil.getStringParam(reportParams, KEY_FROM_DT);
        String toDt = ROIReportUtil.getStringParam(reportParams, KEY_TO_DT);
        Date startDate = ROIReportUtil.convertToStartDate(fromDt);
        Date endDate = ROIReportUtil.convertToEndDate(toDt);

        Object[] criterias = {mrn,
                              facility,
                              patientId,
                              requestType,
                              ROIReportUtil.formatDate(startDate, PROCEDURE_DATE_FORMAT),
                              ROIReportUtil.formatDate(endDate, PROCEDURE_DATE_FORMAT)};

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + criterias.toString());
        }
        return criterias;
    }

    private String getHeaders() {

        return new StringBuffer()
               .append("PatientName,DateOfBirth,Gender,MRN,Facility,RequestorId,RequestorName,")
               .append("RequestorAddressLine1,RequestorAddressLine2,RequestorAddressLine3,RequestorAddressCityState,")
               .append("DisclosureDate,DischargeDate,VisitType,RequestReason,RequestType,")
               .append("Encounter,SelfPay,DocType,DocName,Pages,RequestId,SubTitle,Vip Patient,")
               .append("Locked Patient,Patient Epn")

        .toString();
    }
}
