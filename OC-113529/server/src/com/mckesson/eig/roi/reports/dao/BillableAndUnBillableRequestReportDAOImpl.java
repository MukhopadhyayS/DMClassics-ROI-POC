package com.mckesson.eig.roi.reports.dao;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.reports.service.ROIReportUtil;
import com.mckesson.dm.core.common.logging.OCLogger;

import edu.emory.mathcs.backport.java.util.Arrays;

public class BillableAndUnBillableRequestReportDAOImpl
extends ROIReportDAOImpl {

    private static final OCLogger LOG
    = new OCLogger(BillableAndUnBillableRequestReportDAOImpl.class);

    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static final String KEY_FACILITIES = "facilities";
    private static final String KEY_REQUESTOR_TYPES = "requestorTypes";
    private static final String KEY_REQUEST_TYPE = "requestType";
    private static final String KEY_FROM_DT = "fromDt";
    private static final String KEY_TO_DT = "toDt";

    private static final int FACILITY = 0;
    private static final int REQUESTOR_TYPE = 1;
    private static final int REQUESTOR_NAME = 2;
    private static final int REQUEST_ID = 3;
    private static final int BILLABLE = 4;
    private static final int REQUEST_COST = 5;
    private static final int TOTAL_PAGES = 6;
    private static final int PATIENT_NAME = 7;
    private static final int PATIENT_MRN = 8;



    /**
     * Method to generate Billable\UnBillable report
     * @param reportID
     * @param params
     * @param outputStream
     * @throws IOException
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void generateReport(String reportID, Map params,
                               OutputStream outputStream)
                               throws IOException {

        final String logSM = "generateReport(reportID, params, outputStream)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:ReportId :" + reportID);
        }
        try {

            List<String> facilities = ROIReportUtil.constructListContent(ROIReportUtil
                                            .getStringParam(params, KEY_FACILITIES));
            facilities = Arrays.asList(ROIReportUtil
                    .convertASCIIValues(facilities.toArray(new String[facilities.size()])));
            List<String> requestorTypes = ROIReportUtil.constructListContent(ROIReportUtil
                                            .getStringParam(params, KEY_REQUESTOR_TYPES));
            String requestType =  ROIReportUtil.getStringParam(params, KEY_REQUEST_TYPE);
            Date startDate = ROIReportUtil.convertToStartDate(ROIReportUtil
                                            .getStringParam(params, KEY_FROM_DT));
            Date endDate = ROIReportUtil.convertToEndDate(ROIReportUtil
                                            .getStringParam(params, KEY_TO_DT));

            Session session = getSession();
            String queryString = session.getNamedQuery("ROI_Generate_UnBillableAndBillableReport")
                                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameterList("facilities", facilities);
            query.setParameterList("requestorTypes", requestorTypes);
            query.setParameter("requestType",
                               requestType.equals("Both") ? null : requestType,
                               Hibernate.STRING);

            query.setParameter("fromDate", startDate, Hibernate.TIMESTAMP);
            query.setParameter("toDate", endDate, Hibernate.TIMESTAMP);

            query.addScalar("facility", Hibernate.STRING);
            query.addScalar("requestorType", Hibernate.STRING);
            query.addScalar("requestorName", Hibernate.STRING);
            query.addScalar("requestId", Hibernate.INTEGER);
            query.addScalar("billable", Hibernate.STRING);
            query.addScalar("requestCost", Hibernate.DOUBLE);
            query.addScalar("totalPages", Hibernate.INTEGER);
            query.addScalar("patientName", Hibernate.STRING);
            query.addScalar("patientMRN", Hibernate.STRING);

            List<Object[]> results = query.list();

            PrintStream out = new PrintStream(outputStream, true);
            generateCSV(results, out, getHeaders());

            if (DO_DEBUG) {
                LOG.debug(logSM + "End<<");
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
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
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_TYPE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, REQUESTOR_NAME))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getIntegerCsv(result, REQUEST_ID))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, BILLABLE))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getDecimalCsv(result, REQUEST_COST))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getIntegerCsv(result, TOTAL_PAGES))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PATIENT_NAME))
                             .append(CSV_DELIM)
                             .append(ROIReportUtil.getStringCsv(result, PATIENT_MRN))
                             .toString();

           out.print(csvData);
           out.println();
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End");
        }
    }

    private String getHeaders() {

        return new StringBuffer()
               .append("Facility,RequestorType,RequestorName,RequestId,Billable,")
               .append("RequestCost,TotalPages,PatientName,PatientMRN")
               .toString();
    }

}
