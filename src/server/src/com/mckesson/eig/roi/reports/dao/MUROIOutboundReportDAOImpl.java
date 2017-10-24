package com.mckesson.eig.roi.reports.dao;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mckesson.eig.roi.muroioutbound.dao.MUROIOutboundDAO;
import com.mckesson.eig.roi.muroioutbound.service.MUROIOutboundServiceImpl;
import com.mckesson.eig.roi.reports.service.ROIReportUtil;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.utils.SpringUtil;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * Class to generate Report for MURequest TurnAroundTime for Details and Totals
 * Per facility
 * 
 */
public class MUROIOutboundReportDAOImpl extends ROIReportDAOImpl {

    private static final OCLogger LOG = new OCLogger(MUROIOutboundReportDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final String DETAILS = "Details";

    private static final String KEY_FACILITIES = "facilities";
    private static final String KEY_MUDOCTYPE = "muDocType";
    private static final String KEY_FROM_DT = "fromDt";
    private static final String KEY_TO_DT = "toDt";
    private static final String RESULT_TYPE = "resultType";

    private static final int TA_POSITION = 0;
    private static final int BUSDAYS_POSITION = 1;
    private static final int DATE_RECVD_POSITION = 2;
    private static final int DATE_RELEASED_POSITION = 3;
    private static final int FACILITY_POSITION = 4;
    private static final int USER_NAME_POSITION = 5;
    private static final int PATIENT_NAME_POSITION = 6;
    private static final int REQUEST_ID_POSITION = 7;
    private static final int DOCUMENT_SOURCE_POSITION = 8;
    private static final int REQUEST_STATUS_POSITION = 9;

    private static final int FACILITY = 0;
    private static final int TOTAL_RELEASES = 1;
    private static final int TOTAL_COMPLIANT = 2;
    private static final int PERCENTAGE_COMPLIANT = 3;

    /**
     * Method to get the instance for MUROIOutboundServiceImpl
     */
    private MUROIOutboundServiceImpl getMUROIOutboundServiceImpl() {
        return (MUROIOutboundServiceImpl) SpringUtil
                .getObjectFromSpring("com.mckesson.eig.roi.muroioutbound.service.MUROIOutboundServiceImpl");
    }
    
    /**
     * Method to get the instance for MUROIOutboundDAOImpl
     */
    private MUROIOutboundDAO getDao() {
        return (MUROIOutboundDAO) SpringUtil
                .getObjectFromSpring("MUROIOutboundDAO");
    }
    

    /**
     * Method to generate report for both the views
     * 
     * @param reportID
     * @param params
     * @param outputStream
     */
    @SuppressWarnings("unchecked")
    public void generateReport(String reportID, Map params,
            OutputStream outputStream) throws IOException {

        final String logSM = "generateReport(String reportID, Map params, "
                + "OutputStream outputStream)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:ReportId :" + reportID);
        }
        try {

            Object[] criterias = getCriteria(params);

            String[] facList = (String[]) criterias[0];
            facList = ROIReportUtil.convertASCIIValues(facList);
            String mudoctype = (String) criterias[1];
            Date fromDate = (Date) criterias[2];
            Date toDate = (Date) criterias[3];
            String resultType = (String) criterias[4];
            List<String> facilityWithResults = new ArrayList<String>();
            boolean resultsFlag = false;
            PrintStream printStream = new PrintStream(outputStream, true);
            String facilityName = null;
            if (DETAILS.equalsIgnoreCase(resultType)) {

                List<Object[]> results = getMUROIOutboundServiceImpl()
                        .retriveMURequestDetailsForReport(fromDate, toDate,
                                facList, mudoctype);
                printStream.println(getHeaders());
                if (results != null && results.size() > 0) {
                    for (Object[] object : results) {
                        if (null != object[4])
                            facilityWithResults.add(object[4].toString().trim());
                    }
                }
                
                String facility = "";
                for (int i = 0; i < facList.length; i++) {
                    
                    facility = "";
                    if (StringUtilities.hasContent(facList[i])) {
                        facility = facList[i].trim();
                    }
                    
                    if (facilityWithResults.contains(facility) && !resultsFlag) {
                        resultsFlag = true;
                        generateCSV(results, printStream, getHeaders());
                    } else if (!facilityWithResults.contains(facility)) {
                        
                        facilityName = getDao().getFacilityName(facility);
                        if (facilityName == null) {
                           facilityName = facility;
                        }
                        generateCSVForNoReport(getHeaders(),
                                printStream, results, facilityName.trim());
                    } else  {
                        facilityName = facList[i].toString();
                        generateCSVForNoReport(getHeaders(),
                                printStream, results, facilityName.trim());
                    }
                }

            } else {
                List<Object[]> results = getMUROIOutboundServiceImpl()
                        .grandTotalsPerFacility(fromDate, toDate, facList,
                                mudoctype);
                PrintStream out = new PrintStream(outputStream, true);
                generateCSVForFacilityView(results, out,
                        getFacilityViewHeaders());

            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "End<<");
            }
        } catch (IOException ioe) {

            throw ioe;
        } catch (Exception e) {

            throw new IOException("MURequest report generation failed");
        }

    }

    /**
     * Method to generate CSV for MU Details view
     * 
     * @param results
     * @param out
     * @param headers
     */
    private void generateCSV(List<Object[]> results, PrintStream out,
            String headers) {

        final String logSM = "generateCsv(List<Object[]> results, PrintStream out)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }
        String facilityName = null;
        for (Object[] result : results) {
            facilityName = getDao().getFacilityName(result[4].toString());
            String csvData = new StringBuffer().append(
                    ROIReportUtil.getStringCsv(result, TA_POSITION)).append(
                    CSV_DELIM).append(
                    ROIReportUtil.getStringCsv(result, BUSDAYS_POSITION))
                    .append(CSV_DELIM).append(
                            ROIReportUtil.getDateCsv(result,
                                    DATE_RECVD_POSITION)).append(CSV_DELIM)
                    .append(
                            ROIReportUtil.getDateCsv(result,
                                    DATE_RELEASED_POSITION)).append(CSV_DELIM)
                    .append("\"" + facilityName.trim() + "\"").append(CSV_DELIM)
                    .append(
                            ROIReportUtil.getStringCsv(result,
                                    USER_NAME_POSITION)).append(CSV_DELIM)
                    .append(
                            ROIReportUtil.getStringCsv(result,
                                    PATIENT_NAME_POSITION)).append(CSV_DELIM)
                    .append(
                            ROIReportUtil.getIntegerCsv(result,
                                    REQUEST_ID_POSITION)).append(CSV_DELIM)
                    .append(
                            ROIReportUtil.getStringCsv(result,
                                    DOCUMENT_SOURCE_POSITION))
                    .append(CSV_DELIM).append(
                            ROIReportUtil.getStringCsv(result,
                                    REQUEST_STATUS_POSITION))

                    .toString();

            out.print(csvData);
            out.println();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

        }
    }

    /**
     * Method to generate CSV for Totals Per Facility view
     * 
     * @param results
     * @param out
     * @param headers
     */
    private void generateCSVForFacilityView(List<Object[]> results,
            PrintStream out, String headers) {

        final String logSM = "generateCsvForFacility(List<Object[]> results, PrintStream out)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }
        String facilityName = null;
        List<String> resultList = new ArrayList<String>();
        out.println(headers);

        for (Object[] result : results) {
            
            if(!"Grand Total".equalsIgnoreCase(result[0].toString()))
            {
            facilityName = getDao().getFacilityName(result[0].toString());
            if(facilityName == null)
            {
               facilityName = result[0].toString();
            }
            }
            else
               facilityName = result[0].toString();
            if(!resultList.contains(result[0].toString()))
            {
            String csvData = new StringBuffer().append(
                    "\"" + facilityName.trim() + "\"").append(
                    CSV_DELIM).append(
                    ROIReportUtil.getIntegerCsv(result, TOTAL_RELEASES))
                    .append(CSV_DELIM).append(
                            ROIReportUtil
                                    .getIntegerCsv(result, TOTAL_COMPLIANT))
                    .append(CSV_DELIM)

                    .append(
                            ROIReportUtil.getStringCsv(result,
                                    PERCENTAGE_COMPLIANT))

                    .toString();
            
            resultList.add(result[0].toString());
            out.print(csvData);
            out.println();
            }
            else
            {
                String csvData = new StringBuffer().append(
                        "\"" + result[0].toString() + "\"").append(
                        CSV_DELIM).append("0")
                        .append(CSV_DELIM).append(
                                "0")
                        .append(CSV_DELIM)

                        .append("0%")

                        .toString();
                out.print(csvData);
                out.println();
            }
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        }
    }
    /**
     * Method to get the Criteria Parameters
     * 
     * @param reportParams
     * @return Object[] criterias
     * @throws IOException
     */
    private Object[] getCriteria(Map reportParams) throws IOException {

        final String logSM = "processCriterias(HashMap<String, Object> reportParams)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        String facility = ROIReportUtil.getStringParam(reportParams,
                KEY_FACILITIES);
        String[] facilityList = facility.split(",");

        String muDocType = ROIReportUtil.getStringParam(reportParams,
                KEY_MUDOCTYPE);
        String fromDt = ROIReportUtil.getStringParam(reportParams, KEY_FROM_DT);
        String toDt = ROIReportUtil.getStringParam(reportParams, KEY_TO_DT);
        Date startDate = ROIReportUtil.convertToStartDate(fromDt);
        Date endDate = ROIReportUtil.convertToEndDate(toDt);
        String resultType = ROIReportUtil.getStringParam(reportParams,
                RESULT_TYPE);

        Object[] criterias = {facilityList, muDocType, startDate, endDate,
                resultType};

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + criterias.toString());
        }

        return criterias;
    }
    /**
     * Method to get the Headers for MU Details View
     */
    private String getHeaders() {

        return new StringBuffer()
                .append(
                        "TA Hours,Bus Days,Date Rcvd,Date Released,Facility,User Name,Patient Name,Request ID,Document Source,Request Status")

                .toString();
    }
    /**
     * Method to get the Headers for MU Totals Per Facility View
     */
    private String getFacilityViewHeaders() {
        return new StringBuffer()
                .append(
                        "Facility,Total No. Releases,Total No. Compliant,Percentage Compliant")

                .toString();

    }

    private void generateCSVForNoReport(String headers,
            PrintStream out, List<Object[]> results,String facilityName) {
        final String logSM = "generateCSVForNoReport(String[] facList,String headers,PrintStream out)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        Object[] result = {null, null, null, null, facilityName, null, null, null,
                null, null};
        String csvData = new StringBuffer().append(
                ROIReportUtil.getStringCsv(result, TA_POSITION)).append(
                CSV_DELIM).append(
                ROIReportUtil.getStringCsv(result, BUSDAYS_POSITION)).append(
                CSV_DELIM).append(
                ROIReportUtil.getDateCsv(result, DATE_RECVD_POSITION)).append(
                CSV_DELIM).append(
                ROIReportUtil.getDateCsv(result, DATE_RELEASED_POSITION))
                .append(CSV_DELIM).append(ROIReportUtil.getStringCsv(result, FACILITY_POSITION)).append(CSV_DELIM).append(
                        ROIReportUtil.getStringCsv(result, USER_NAME_POSITION))
                .append(CSV_DELIM).append(
                        ROIReportUtil.getStringCsv(result,
                                PATIENT_NAME_POSITION)).append(CSV_DELIM)
                .append(
                        ROIReportUtil
                                .getIntegerCsv(result, REQUEST_ID_POSITION))
                .append(CSV_DELIM).append(
                        ROIReportUtil.getStringCsv(result,
                                DOCUMENT_SOURCE_POSITION)).append(CSV_DELIM)
                .append(
                        ROIReportUtil.getStringCsv(result,
                                REQUEST_STATUS_POSITION))

                .toString();

        out.print(csvData);
        out.println();
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End");
        }

    }

}
