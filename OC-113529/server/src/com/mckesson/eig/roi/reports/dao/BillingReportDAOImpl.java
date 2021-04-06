package com.mckesson.eig.roi.reports.dao;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.LongType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.springframework.dao.DataIntegrityViolationException;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.journal.model.LedgerAccountDTO;
import com.mckesson.eig.roi.reports.service.ROIReportUtil;

public class BillingReportDAOImpl extends ROIReportDAOImpl {

    private static final OCLogger LOG = new OCLogger(BillingReportDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final String KEY_FROM_DT = "fromDt";
    private static final String KEY_TO_DT = "toDt";

    /**
     * Method to generate billing report
     * 
     * @param reportID
     * @param params
     * @param outputStream
     */
    @Override
    public void generateReport(String reportID, Map params,
            OutputStream outputStream) throws IOException {

        final String logSM = "generateReport(reportID, params, outputStream)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:ReportId :" + reportID);
        }
        try {

            String fromDt = ROIReportUtil.getStringParam(params, KEY_FROM_DT);
            String toDt = ROIReportUtil.getStringParam(params, KEY_TO_DT);
            Date startDate = ROIReportUtil.convertToStartDate(fromDt);
            Date endDate = ROIReportUtil.convertToEndDate(toDt);

            List<String> wholeAccountNames = retrieveAccountNames();
            List<String> accountNames = new ArrayList<String>();
            for(String ledger : wholeAccountNames)
            {
                String[] accountName = ledger.split(",");
                accountNames.add(accountName[2]);
            }
            List<Object[]> results = retrieveAccountDetails(
                    startDate, endDate, accountNames);

            Map<String, Double> acctAmountCombination = new LinkedHashMap<String, Double>();
            PrintStream out = new PrintStream(outputStream, true);
            out.println(getHeaders());
            if (results != null && results.size() > 0) {
                for (Object[] object : results) {
                    if (null != object[0] && null != object[1]) {
                        acctAmountCombination.put(object[0].toString().trim(), Double.parseDouble(object[1].toString()));
                    }
                }   
            }  
           generateCSV(results,out,wholeAccountNames,acctAmountCombination);
           if (DO_DEBUG) {
                LOG.debug(logSM + "End<<");
           }

        } catch (IOException ioe) {
            throw ioe;
        } catch (Exception e) {
            throw new IOException("Billing report generation failed");
        }
    }

    /**
     * Method to process the result set and generate csv
     * 
     * @param results
     * @param out
     */
    private void generateCSV(List<Object[]> results, PrintStream out,
            List<String> accountNames,Map<String, Double> acctAmountCombination) {

        final String logSM = "generateCsv(List<Object[]> results, PrintStream out,List<String> accountNames,Map<String, Double> acctAmountCombination)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }
        
        StringBuffer csvData = new StringBuffer();
        
            for(String ledger : accountNames){ 
                String[] accountName = ledger.split(",");
                double amount = 0;
                if(acctAmountCombination.get(accountName[2]) != null) {
                    amount = acctAmountCombination.get(accountName[2]);
                }
                csvData.append(accountName[0]).append(CSV_DELIM).append(accountName[1]).append(CSV_DELIM);
                csvData.append(accountName[2]).append(CSV_DELIM);
                csvData.append(amount);
                out.print(csvData);
                csvData = new StringBuffer();
                out.println();
            }   
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End");
        }
    }

   
    private List<Object[]> retrieveAccountDetails(
            Date startDate, Date endDate, List<String> accountNames) {
        try {
            Session session = getSession();

            String queryStringValue = session.getNamedQuery(
                    "ROI_Generate_BillingReport").getQueryString();

            NativeQuery queryValue = session.createSQLQuery(queryStringValue);
           // queryValue.setParameterList("names", accountNames);
            queryValue.setParameter("startDate", startDate, StandardBasicTypes.TIMESTAMP);
            queryValue.setParameter("endDate", endDate, StandardBasicTypes.TIMESTAMP);
            queryValue.addScalar("name", StringType.INSTANCE);
            queryValue.addScalar("amount", DoubleType.INSTANCE);
            queryValue.addScalar("ledgerSeq", LongType.INSTANCE);

            @SuppressWarnings("unchecked")
            List<Object[]> results = queryValue.list();
            return results;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
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

    private List<String> retrieveAccountNames() {
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery(
                    "select_all_from_Ledger_Account").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.addScalar("balance", LongType.INSTANCE);
            query.addScalar("id", LongType.INSTANCE);
            query.addScalar("code", StringType.INSTANCE);
            query.addScalar("name", StringType.INSTANCE);
            query.addScalar("liquidityOrder", LongType.INSTANCE);

            query.setResultTransformer(Transformers
                    .aliasToBean(LedgerAccountDTO.class));

            @SuppressWarnings("unchecked")
            List<LedgerAccountDTO> ledgerAccountList = query.list();
            List<String> accountNames = new ArrayList<String>();
            for (LedgerAccountDTO ledgerAccountDTO : ledgerAccountList) {
                 String accountName = ledgerAccountDTO.getName();
                 accountNames.add(accountName);
            }
            return accountNames;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
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
     * Method to get the Headers for Billing Report Details View
     */
    private String getHeaders() {

        return new StringBuffer()
                .append("MainType,SubType,FeeType,Amount")
                .toString();
    }

}
