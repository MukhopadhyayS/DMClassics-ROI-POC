package com.mckesson.eig.roi.muroioutbound.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.muroioutbound.dao.MUROIOutboundDAO;
import com.mckesson.eig.roi.muroioutbound.model.MUROIOutboundStatistics;
import com.mckesson.eig.roi.reports.service.ROIReportUtil;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.utils.SpringUtil;

public class MUROIOutboundServiceImpl implements MUROIOutboundService {
    private static final OCLogger LOG = new OCLogger(MUROIOutboundServiceImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     * Method to get the details for Details Screen for Report Generation
     * 
     * @param fromDate
     * @param toDate
     * @param facList
     * @param mudoctype
     * @return
     */
    public List<Object[]> retriveMURequestDetailsForReport(Date fromDate,
            Date toDate, String[] facList, String mudoctype) {
        final String logSM = "retriveMURequestDetailsForReport()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + fromDate + toDate + facList
                    + mudoctype);
        }
        List<Object[]> results = new ArrayList<Object[]>();
        int flag = 0;
        String patientName = null;
        String patientFirstName = null;
        String patientLastName = null;
        List<String> facilityWithResults = new ArrayList<String>();
        for (int i = 0; facList.length != 0 && i < facList.length; i++) {
            if(!facilityWithResults.contains(facList[i]))
            {
            List<MUROIOutboundStatistics> muOutboundList = getDao()
                    .retriveFromROIOutboundForReport(fromDate, toDate,
                            mudoctype, facList[i]);
            if(muOutboundList != null && muOutboundList.size() > 0)
            facilityWithResults.add(facList[i]);
            for (int j = 0; muOutboundList != null && j < muOutboundList.size(); j++) {

                patientFirstName = muOutboundList.get(j).getPatientFirstName();
                patientLastName = muOutboundList.get(j).getPatientLastName();
                if ((patientFirstName != null && patientFirstName.length() > 0)
                        && (patientLastName != null && patientLastName.length() > 0)) {
                    patientName = patientLastName + "," + patientFirstName;
                } else if (patientFirstName != null
                        && patientFirstName.length() > 0) {
                    patientName = patientFirstName;
                } else {
                    patientName = patientLastName;
                }
                
                int userID=muOutboundList.get(j).getUserName();
                String userName=getDao().getUserName(userID);
                Double ta = Double.valueOf(muOutboundList.get(j).getTa());
                Double totalDays = Double.valueOf(muOutboundList.get(j).getTotalDays());

                Object[] reportArray = {ta.toString(),
                        totalDays.toString(),
                        muOutboundList.get(j).getReqDate(),
                        muOutboundList.get(j).getFulfilledDate(),
                        muOutboundList.get(j).getFacility(),
                        userName, patientName,
                        muOutboundList.get(j).getReqID(),
                        muOutboundList.get(j).getExternalSource(),
                        muOutboundList.get(j).getReqStatus()};

                results.add(flag, reportArray);
                flag++;
            }
        }
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        }
        return results;

    }

    /**
     * Method to get details for totalsPerFacility view
     * 
     * @param fromDate
     * @param toDate
     * @param facList
     * @param mudoctype
     */
    private List<Object[]> totalsPerFacility(Date fromDate, Date toDate,
            String[] facList, String mudoctype) {
        List<Object[]> resultsForFacility = new ArrayList<Object[]>();
        int result = 0;

        for (int i = 0; facList.length != 0 && i < facList.length; i++) {

            String facility = facList[i];

            List<MUROIOutboundStatistics> muRequestList = getDao()
                    .totalNumberOfReleases(fromDate, toDate, mudoctype,
                            facility);

            int totalNoOfReleases = muRequestList.size();
            int totalNumberCompliant = getDao().totalNumberCompliant(fromDate,
                    toDate, mudoctype, facility);
            double percentageCompliance = 0;
            double percentageComplianceRoundedOff = 0;
            String percentageComp = null;
            if (totalNoOfReleases != 0) {
                percentageCompliance = (double)totalNumberCompliant / totalNoOfReleases * 100;
                percentageComplianceRoundedOff = roundOffToTwoDigits(percentageCompliance);
                percentageComp = String.valueOf(percentageComplianceRoundedOff).concat(
                        "%");
            } else {
                percentageCompliance = 0;
                percentageComp = String.valueOf(percentageCompliance).concat(
                        "%");
            }

            Object[] reportArray = {facility, totalNoOfReleases,
                    totalNumberCompliant, percentageComp};
            resultsForFacility.add(result, reportArray);
            result++;

        }

        return resultsForFacility;
    }
    /**
     * Method to get the details for TotalsPerFacility Screen with GrandTotal
     * for Report Generation
     * 
     * @param fromDate
     * @param toDate
     * @param facList
     * @param mudoctype
     * @return
     */
    public List<Object[]> grandTotalsPerFacility(Date fromDate, Date toDate,
            String[] facList, String mudoctype) {
        final String logSM = "grandTotalsPerFacility()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + fromDate + toDate + facList
                    + mudoctype);
        }
        int sumOfReleases = 0;
        int sumOfCompliants = 0;
        double sumOfPercentage = 0;
        double percentageComplianceRoundedOff = 0;
        List<Object[]> grandTotal = totalsPerFacility(fromDate, toDate,
                facList, mudoctype);
        for (int i = 0; grandTotal != null && i < grandTotal.size(); i++) {
            Object[] facRow = grandTotal.get(i);
            int totalRelease = ROIReportUtil.getIntegerValue(facRow, 1);
            int totalCompliant = ROIReportUtil.getIntegerValue(facRow, 2);
           
            if (i == 0) {
                sumOfReleases = totalRelease;
                sumOfCompliants = totalCompliant;
                if (sumOfReleases != 0) {
                    sumOfPercentage = (double) sumOfCompliants / sumOfReleases
                            * 100;
                }

            } else {
                sumOfReleases = sumOfReleases + totalRelease;
                sumOfCompliants = sumOfCompliants + totalCompliant;
                if (sumOfReleases != 0) {
                    sumOfPercentage = (double) sumOfCompliants / sumOfReleases
                            * 100;
                }
            }

        }
        percentageComplianceRoundedOff = roundOffToTwoDigits(sumOfPercentage);
        String percentageComp = String.valueOf(percentageComplianceRoundedOff)
                .concat("%");

        Object[] reportArray = {"Grand Total", sumOfReleases, sumOfCompliants,
                percentageComp};

        grandTotal.add(grandTotal.size(), reportArray);
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return grandTotal;

    }

    /**
     * Method to get the instance for MUROIOutboundDAOImpl
     */
    private MUROIOutboundDAO getDao() {
        return (MUROIOutboundDAO) SpringUtil
                .getObjectFromSpring("MUROIOutboundDAO");
    }
    
    private double roundOffToTwoDigits(double percentageCompliance)
    {
        DecimalFormat twoDecimalFormat = new DecimalFormat("#.##");         
        double roundedOffValue = Double.valueOf(twoDecimalFormat.format(percentageCompliance)); 
        return roundedOffValue;
    }
}
