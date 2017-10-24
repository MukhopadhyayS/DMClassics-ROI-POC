package com.mckesson.eig.roi.reports.dao;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mckesson.eig.roi.muroioutbound.dao.MUROIOutboundDAO;
import com.mckesson.eig.roi.reports.service.ROIReportUtil;
import com.mckesson.eig.roi.request.service.RequestCoreServiceImpl;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.utils.SpringUtil;

public class ProductivityReportDAOImpl extends ROIReportDAOImpl {

    private static final OCLogger LOG = new OCLogger(ProductivityReportDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final String KEY_FACILITIES = "facilityNames";
    private static final String KEY_ACTOR_IDS = "actorIds";
    private static final String KEY_FROM_DT = "fromDt";
    private static final String KEY_TO_DT = "toDt";
    private static final String KEY_REQUESTOR_TYPES = "requestorTypes";
    private static final String RESULT_TYPE = "resultType";

    private static final int FACILITY_POSITION = 0;
    private static final int USER_NAME = 1;
    private static final int REQUESTOR_TYPE = 2;
    private static final int REQUESTOR_NAME = 3;
    private static final int PATIENT_NAME = 4;
    private static final int REQUEST_ID = 5;
    private static final int PAGES = 6;
    private static final int MRN = 7;
    private static final int BILLABLE = 8;
    private static final int CREATEDATE = 9;
    private static final int PAGETYPE = 10;
    private static final int REQUESTS = 11;
    
    private static final String DUMMY_DATE = "01/01/2012 01:01:01";

    /**
     * Method to generate Productivity Report
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
            List<String> userNames = (List<String>) criterias[1];
            String[] requestorType = (String[]) criterias[2];
            Date fromDate = (Date) criterias[3];
            Date toDate = (Date) criterias[4];
            String resultType = (String) criterias[5];
            PrintStream printStream = new PrintStream(outputStream, true);
            boolean resultsFlag = false;
            List<String> facilityWithResults = new ArrayList<String>();
            List<Object[]> results = getRequestCoreServiceImpl()
                    .retrieveProductivityReportDetails(facList, userNames,
                            requestorType,fromDate,
                            toDate, resultType);
            printStream.println(getHeaders());
            if (results != null && results.size() > 0) {
                for (Object[] object : results) {
                    if (null != object[0]) {
                        facilityWithResults.add(object[0].toString());
                    }
                }
            }
            for (int i = 0; i < facList.length; i++) {
                if (facilityWithResults.contains(facList[i]) && !resultsFlag) {
                    resultsFlag = true;
                    generateCSV(results, printStream);
                } else if (!facilityWithResults.contains(facList[i])) {
                    generateCSVForNoReport(printStream, facList[i].toString()
                            .trim());
                }
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "End<<");
            }
        } catch (IOException ioe) {

            throw ioe;
        } catch (Exception e) {

            throw new IOException("Productivity report generation failed");
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
        facilityList = ROIReportUtil.convertASCIIValues(facilityList);
        String userName = ROIReportUtil.getStringParam(reportParams,
                KEY_ACTOR_IDS);
        String[] userNameList = userName.split(",");
        List<String> userNames = new ArrayList<String>();
        for (String str : userNameList) {
            String value = getDao().getUserName(Integer.parseInt(str));
            userNames.add(value);
        }
        String requestortype = ROIReportUtil.getStringParam(reportParams,
                KEY_REQUESTOR_TYPES);
        String[] requestortypeList = requestortype.split(",");
        String fromDt = ROIReportUtil.getStringParam(reportParams, KEY_FROM_DT);
        String toDt = ROIReportUtil.getStringParam(reportParams, KEY_TO_DT);
        Date startDate = ROIReportUtil.convertToStartDate(fromDt);
        Date endDate = ROIReportUtil.convertToEndDate(toDt);
        String resultType = ROIReportUtil.getStringParam(reportParams,
                RESULT_TYPE);

        Object[] criterias = {facilityList, userNames, requestortypeList,
                startDate, endDate, resultType};

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + criterias.toString());
        }

        return criterias;
    }

    /**
     * Method to get the Headers for Productivity Report
     */
    private String getHeaders() {

        return new StringBuffer()
                .append("Facility,UserName,RequestorType,RequestorName,PatientName,RequestID,Pages,MRN,Billable,CreateDate,Page Type,Requests")
                .toString();
    }

    /**
     * Method to generate CSV for Productivity Report
     * 
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
                    .append(ROIReportUtil.getStringCsv(result,
                            FACILITY_POSITION)).append(CSV_DELIM)
                    .append(ROIReportUtil.getStringCsv(result, USER_NAME))
                    .append(CSV_DELIM)
                    .append(ROIReportUtil.getStringCsv(result, REQUESTOR_TYPE))
                    .append(CSV_DELIM)
                    .append(ROIReportUtil.getStringCsv(result, REQUESTOR_NAME))
                    .append(CSV_DELIM)
                    .append(ROIReportUtil.getStringCsv(result, PATIENT_NAME))
                    .append(CSV_DELIM)
                    .append(ROIReportUtil.getStringCsv(result, REQUEST_ID))
                    .append(CSV_DELIM)
                    .append(ROIReportUtil.getIntegerCsv(result, PAGES))
                    .append(CSV_DELIM)
                    .append(ROIReportUtil.getStringCsv(result, MRN))
                    .append(CSV_DELIM)
                    .append(ROIReportUtil.getStringCsv(result, BILLABLE))
                    .append(CSV_DELIM)
                    .append(ROIReportUtil.getDateTimeCsv(result, CREATEDATE))
                    .append(CSV_DELIM)
                    .append(ROIReportUtil.getStringCsv(result, PAGETYPE))
                    .append(CSV_DELIM)
                    .append(ROIReportUtil.getIntegerCsv(result, REQUESTS))
                    .toString();
            out.print(csvData);
            out.println();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

        }
    }
    
    /**
     * Method to generate CSV for Productivity Report
     * 
     * @param out
     * @param facilityName
     */
    private void generateCSVForNoReport(PrintStream out, String facilityName) {

        final String logSM = "generateCSVForNoReport(PrintStream out,String facilityName)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }
        Object[] result = {facilityName, null, null, null, null, null, null,
                null, DUMMY_DATE, null, null, null};
        String csvData = new StringBuffer()
                .append(ROIReportUtil.getStringCsv(result, FACILITY_POSITION))
                .append(CSV_DELIM)
                .append(ROIReportUtil.getStringCsv(result, USER_NAME))
                .append(CSV_DELIM)
                .append(ROIReportUtil.getStringCsv(result, REQUESTOR_TYPE))
                .append(CSV_DELIM)
                .append(ROIReportUtil.getStringCsv(result, REQUESTOR_NAME))
                .append(CSV_DELIM)
                .append(ROIReportUtil.getStringCsv(result, PATIENT_NAME))
                .append(CSV_DELIM)
                .append(ROIReportUtil.getStringCsv(result, REQUEST_ID))
                .append(CSV_DELIM)
                .append(ROIReportUtil.getIntegerCsv(result, PAGES))
                .append(CSV_DELIM)
                .append(ROIReportUtil.getStringCsv(result, MRN))
                .append(CSV_DELIM)
                .append(ROIReportUtil.getStringCsv(result, BILLABLE))
                .append(CSV_DELIM)
                .append(result[8].toString())
                .append(CSV_DELIM)
                .append(ROIReportUtil.getStringCsv(result, PAGETYPE))
                .append(CSV_DELIM)
                .append(ROIReportUtil.getIntegerCsv(result, REQUESTS))
                .toString();
        out.print(csvData);
        out.println();
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

    /**
     * This method is used to convert the date to string
     * 
     * @param date
     * @return String
     */
    private String convertDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String convertedDate = sdf.format(date);
        return convertedDate;
    }

    /**
     * Method to get the instance for RequestCoreServiceImpl
     */
    private RequestCoreServiceImpl getRequestCoreServiceImpl() {
        return (RequestCoreServiceImpl) SpringUtil
                .getObjectFromSpring("com.mckesson.eig.roi.request.service.RequestCoreServiceImpl");
    }

}
