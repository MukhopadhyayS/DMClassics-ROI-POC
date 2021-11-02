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

import org.springframework.transaction.annotation.Transactional;

import com.mckesson.eig.roi.reports.service.ROIReportUtil;
import com.mckesson.dm.core.common.logging.OCLogger;


/**
 * @author OFS
 * @date   Dec 9, 2008
 * @since  HPF 13.1 [ROI]; Dec 9, 2008
 */
@Transactional
public class DocumentsReleasedByMRNReportDAOImpl
extends ROIReportDAOImpl {


    private static final OCLogger LOG
    = new OCLogger(DocumentsReleasedByMRNReportDAOImpl.class);

    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static final String KEY_MRN = "mrn";
    private static final String KEY_FACILITIES = "facilities";
    private static final int PATIENT_ID = 0;
    private static final int PATIENT_NAME = 1;
    private static final int EPN = 2;
    private static final int FACILITY = 3;
    private static final int MRN = 4;
    private static final int GENDER = 5;
    private static final int REQUESTOR_TYPE = 6;
    private static final int REQUESTOR_NAME = 7;
    private static final int REQUEST_ID = 8;
    private static final int ENCOUNTER = 9;
    private static final int DOCUMENT_TYPE = 10;
    private static final int DOC_NAME = 11;
    private static final int PAGES = 12;
    private static final int RELEASE_DATE = 13;
    private static final int SUBTITLE = 14;
    private static final int IS_VIP = 15;
    private static final int IS_LOCKED = 16;

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

            List<Object[]> results
            = processNamedQuery("ROI_Generate_DocumentsReleasedByMRNReport", criterias);

            PrintStream out = new PrintStream(outputStream, true);
            generateCSV(results, out, getHeaders());

            if (DO_DEBUG) {
                LOG.debug(logSM + "End<<");
            }

        } catch (Throwable e) {
            throw new IOException("Documents released by MRN report generation failed");
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
                             .append(ROIReportUtil.getStringCsv(result, PATIENT_ID))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PATIENT_NAME))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, EPN))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, FACILITY))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, MRN))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, GENDER))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_TYPE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_NAME))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUEST_ID))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, ENCOUNTER))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, DOCUMENT_TYPE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, DOC_NAME))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PAGES))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getDateCsv(result, RELEASE_DATE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, SUBTITLE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, IS_VIP))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, IS_LOCKED))
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

        String facilities = ROIReportUtil.getStringParam(reportParams, KEY_FACILITIES);
        String mrn = ROIReportUtil.getStringParam(reportParams, KEY_MRN);

        Object[] criterias = {mrn,
                              ROIReportUtil.constructListContentXML(facilities, "facility")
                             };

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + criterias.toString());
        }

        return criterias;
    }

    private String getHeaders() {

        return new StringBuffer()
               .append("PatientId,Patient,EPN,Facility,MRN,Gender,RequestorType,")
               .append("RequestorName,RequestId,Encounter,DocType,DocName,Pages,DisclosureDate,")
               .append("SubTitle,Vip Patient,Locked Patient")

        .toString();
    }
}
