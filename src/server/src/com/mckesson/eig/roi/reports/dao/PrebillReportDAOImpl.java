package com.mckesson.eig.roi.reports.dao;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mckesson.eig.roi.billing.service.BillingCoreServiceImpl;
import com.mckesson.eig.roi.reports.service.ROIReportUtil;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.utils.SpringUtil;


/**
 * Class to generate Report for Prebill Records
 * Per facility
 * 
 */
public class PrebillReportDAOImpl extends ROIReportDAOImpl {
    
    private static final OCLogger LOG = new OCLogger(PrebillReportDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    
    
    
    private static final String KEY_FACILITIES = "facilities";
    private static final String KEY_REQUESTOR_TYPES = "requestorTypes";
    private static final String KEY_REQUESTOR_NAME = "requestorName";
    private static final String KEY_REQUESTSTATUS = "status";
    private static final String KEY_AGING_START = "agingStart";
    private static final String KEY_AGING_END = "agingEnd";
    private static final String KEY_BALANCE = "balance";
    private static final String KEY_BALANCE_CRITERION = "balanceCriterion";
    private static final String RESULT_TYPE = "resultType";
    
    
    private static final int FACILITY_POSITION = 0;
    private static final int REQUESTOR_TYPE_POSITION = 1;
    private static final int REQUESTOR_NAME_POSITION = 2;
    private static final int REQUESTOR_PHONE_POSITION = 3;
    private static final int REQUEST_ID_POSITION = 4;
    private static final int PREBILL_NUMBER_POSITION = 5;
    private static final int PREBILL_DATE_POSITION = 6;
    private static final int PREBILL_AMOUNT_POSITION = 7;
    private static final int AGING_POSITION = 8;


    /**
     * Method to generate prebill report
     * 
     * @param reportID
     * @param params
     * @param outputStream
     */
    @Override
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
            String[] requestorType = (String[]) criterias[1];
            String requestorName = (String) criterias[2];
            String[] requestStatus = (String[]) criterias[3];
            String agingStart = (String) criterias[4];
            String agingEnd = (String) criterias[5];
            String balance = (String) criterias[6];
            String balanceCriterion = (String) criterias[7];
            String resultType = (String) criterias[8];
            String startAging = "";
            String endAging = "";
            if("0".equalsIgnoreCase(agingStart) && "0".equalsIgnoreCase(agingEnd)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date currentDate = new Date();
                startAging = sdf.format(currentDate) + " 00:00:00";
                endAging = sdf.format(currentDate) + " 23:59:59";
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar calStart = Calendar.getInstance();
                calStart.add(Calendar.DATE, -Integer.parseInt(agingStart));
                startAging = sdf.format(calStart.getTime());
                Calendar calEnd = Calendar.getInstance();
                calEnd.add(Calendar.DATE, -Integer.parseInt(agingEnd));
                endAging = sdf.format(calEnd.getTime());
            }
            List<Object[]> results = new ArrayList<Object[]>();
            List<String> facilityWithResults = new ArrayList<String>();
            boolean resultsFlag = false;
            if(null != requestorName && !"".equalsIgnoreCase(requestorName))
               results = getBillingCoreServiceImpl().
                    retrievePrebillReportDetailsWithRequestorName(facList,requestorType,requestorName,requestStatus,startAging,endAging,balance,balanceCriterion,resultType);   
            else
               results = getBillingCoreServiceImpl().
                    retrievePrebillReportDetailsWithoutRequestorName(facList,requestorType,requestStatus,startAging,endAging,balance,balanceCriterion,resultType); 
            PrintStream out = new PrintStream(outputStream, true);
            out.println(getHeaders());
            if(null != results && results.size() > 0)
            {
                for (Object[] object : results) {
                     if (null != object[0])
                         facilityWithResults.add(object[0].toString());
                }
                for (int i = 0; i < facList.length; i++) {
                     if (facilityWithResults.contains(facList[i])&& !resultsFlag) {
                         resultsFlag = true;
                         generateCSV(results, out);
                     }
                     else if (!facilityWithResults.contains(facList[i])) 
                     {
                        generateCSVForNoReport(
                                out,facList[i].toString().trim());
                     }
                }
            }
            else
            {
                for(int i = 0;i < facList.length; i++)
                {
                    generateCSVForNoReport(
                            out,facList[i].toString().trim());
                }
            }
            
            if (DO_DEBUG) {
                LOG.debug(logSM + "End<<");
            }

        } catch (IOException ioe) {
                throw ioe;
        } catch (Exception e) {
                 throw new IOException("Prebill report generation failed");
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
            String requestorType = ROIReportUtil.getStringParam(reportParams,
                    KEY_REQUESTOR_TYPES);
            String[] requestorTypeList = requestorType.split(",");
            String requestorName = ROIReportUtil.getStringParam(reportParams,
                    KEY_REQUESTOR_NAME);
            String requestStatus = ROIReportUtil.getStringParam(reportParams,
                    KEY_REQUESTSTATUS);
            String[] requestStatusList = requestStatus.split(",");
            String agingStart = ROIReportUtil.getStringParam(reportParams,
                    KEY_AGING_START);
            String agingEnd = ROIReportUtil.getStringParam(reportParams, KEY_AGING_END);
            String balance = ROIReportUtil.getStringParam(reportParams, KEY_BALANCE);
            String balanceCriterion = ROIReportUtil.getStringParam(reportParams, KEY_BALANCE_CRITERION);
            String resultType = ROIReportUtil.getStringParam(reportParams,
                    RESULT_TYPE);

            Object[] criterias = {facilityList, requestorTypeList, requestorName, requestStatusList, agingStart, agingEnd, balance,balanceCriterion,resultType}; 
                                 
            

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + criterias.toString());
            }

            return criterias;
        } 
        
        /**
         * Method to generate CSV for Prebill Report Details view
         * 
         * @param results
         * @param out
         * 
         */
        private void generateCSV(List<Object[]> results, PrintStream out) {

            final String logSM = "generateCsv(List<Object[]> results, PrintStream out)";
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>Start");
            } 
            for (Object[] result : results) {
                String csvData = new StringBuffer().append(
                        ROIReportUtil.getStringCsv(result, FACILITY_POSITION)).append(
                        CSV_DELIM).append(
                        ROIReportUtil.getStringCsv(result, REQUESTOR_TYPE_POSITION))
                        .append(CSV_DELIM).append(
                                ROIReportUtil.getStringCsv(result,
                                        REQUESTOR_NAME_POSITION)).append(CSV_DELIM)
                        .append(
                                ROIReportUtil.getStringCsv(result,
                                        REQUESTOR_PHONE_POSITION)).append(CSV_DELIM)
                        .append(ROIReportUtil.getStringCsv(result,
                                REQUEST_ID_POSITION)).append(CSV_DELIM)
                        .append(
                                ROIReportUtil.getStringCsv(result,
                                        PREBILL_NUMBER_POSITION)).append(CSV_DELIM)
                        .append(
                                ROIReportUtil.getDateCsv(result,
                                        PREBILL_DATE_POSITION)).append(CSV_DELIM)
                        .append(
                                ROIReportUtil.getDecimalCsv(result,
                                        PREBILL_AMOUNT_POSITION)).append(CSV_DELIM)
                        .append(
                                ROIReportUtil.getStringCsv(result,
                                        AGING_POSITION))
                        .toString();

                out.print(csvData);
                out.println();
                if (DO_DEBUG) {
                    LOG.debug(logSM + "<<End");
                }

            }
        }
        
        /**
         * This method is used to generate the CSV file for no data
         * @param out
         * @param facilityName
         */
        private void generateCSVForNoReport(
                PrintStream out,String facilityName) {
            final String logSM = "generateCSVForNoReport(PrintStream out,String facList)";
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>Start");
            }

            Object[] result = {facilityName, null, null, null, null, null, null, null,
                    null, null};
            String csvData = new StringBuffer().append(
                    ROIReportUtil.getStringCsv(result, FACILITY_POSITION)).append(
                    CSV_DELIM).append(
                    ROIReportUtil.getStringCsv(result, REQUESTOR_TYPE_POSITION)).append(
                    CSV_DELIM).append(
                    ROIReportUtil.getStringCsv(result, REQUESTOR_NAME_POSITION)).append(
                    CSV_DELIM).append(
                    ROIReportUtil.getStringCsv(result, REQUESTOR_PHONE_POSITION))
                    .append(CSV_DELIM).append(
                            ROIReportUtil.getStringCsv(result, REQUEST_ID_POSITION))
                    .append(CSV_DELIM).append(
                            ROIReportUtil.getStringCsv(result,
                                    PREBILL_NUMBER_POSITION)).append(CSV_DELIM)
                    .append(
                            ROIReportUtil
                                    .getDateCsv(result, PREBILL_DATE_POSITION))
                    .append(CSV_DELIM).append(
                            ROIReportUtil.getDecimalCsv(result,
                                    PREBILL_AMOUNT_POSITION)).append(CSV_DELIM)
                    .append(
                            ROIReportUtil.getStringCsv(result,
                                    AGING_POSITION))

                    .toString();

            out.print(csvData);
            out.println();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

        }
    
    /**
     * Method to get the Headers for Prebill Report Details View
     */
    private String getHeaders() {

        return new StringBuffer()
                .append(
                        "Facility,Requestor Type,Requestor name,Requestor Phone,Request ID,Pre-bill#,Pre-bill date,Pre-bill amount,Aging(Days)")

                .toString();
    }
    
    /**
     * Method to get the instance for BillingCoreServiceImpl
     */
    private BillingCoreServiceImpl getBillingCoreServiceImpl() {
        return (BillingCoreServiceImpl) SpringUtil
                .getObjectFromSpring("com.mckesson.eig.roi.billing.service.BillingCoreServiceImpl");
    }
   
}
