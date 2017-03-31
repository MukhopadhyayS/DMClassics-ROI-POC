package com.mckesson.eig.roi.muroioutbound.dao;

import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.hpf.model.Facility;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.muroioutbound.model.MUROIOutboundStatistics;
import com.mckesson.eig.roi.reports.service.ROIReportUtil;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

public class MUROIOutboundDAOImpl extends ROIDAOImpl
        implements
            MUROIOutboundDAO {

    private static final Log LOG = LogFactory
            .getLogger(MUROIOutboundDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    /**
     * Method to insert ROIOutbound Details
     *
     * @param List
     *            <MUROIOutboundStatistics> muOutboundStatistics
     * @return
     */
    @Override
    public void createROIOutbound(
            List<MUROIOutboundStatistics> muOutboundStatistics) {
        final String logSM = "createROIOutbound()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + muOutboundStatistics);
        }
        bulkInsert(muOutboundStatistics);
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
    }

    /**
     * Method to update ROIOutbound details
     *
     * @param muROIOutboundStatistics
     * @return muROIOutboundStatisticsObj
     */
    @Override
    public MUROIOutboundStatistics updateROIOutbound(
            MUROIOutboundStatistics muROIOutboundStatistics) {

        final String logSM = "updateROIOutbound()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + muROIOutboundStatistics);
        }

        MUROIOutboundStatistics muROIOutboundStatisticsObj = (MUROIOutboundStatistics) merge(muROIOutboundStatistics);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return muROIOutboundStatisticsObj;
    }

     /**
     * Method to retrieve ROIOutboundDetails for Report
     * 
     * @param fromDate
     * @param toDate
     * @param muDocName
     * @param facility
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<MUROIOutboundStatistics> retriveFromROIOutboundForReport(
            Date fromDate, Date toDate, String muDocName, String facility) {
        final String logSM = "retriveFromROIOutboundForReport()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + fromDate + toDate + muDocName
                    + facility);
        }
        Object[] values = {fromDate, toDate, muDocName, facility};
        List<MUROIOutboundStatistics> retriveMURequestDetailsForReport = (List<MUROIOutboundStatistics>)getHibernateTemplate()
                .findByNamedQuery("retriveFromROIOutboundForDetailsView",
                        values);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return retriveMURequestDetailsForReport;

    }
    /**

    /**
     * Method to retrieve totalNumberOfReleases for Report
     *
     * @param fromDate
     * @param toDate
     * @param muDocName
     * @param facility
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<MUROIOutboundStatistics> totalNumberOfReleases(Date fromDate,
            Date toDate, String muDocName, String facility) {
        final String logSM = "totalNumberOfReleases()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + fromDate + toDate + muDocName
                    + facility);
        }
        Object[] values = {fromDate, toDate, muDocName, facility};
        List<MUROIOutboundStatistics> totalNumberOfReleasesList = (List<MUROIOutboundStatistics>) getHibernateTemplate()
                .findByNamedQuery("retriveFromROIOutboundForFacilityView",
                        values);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return totalNumberOfReleasesList;

    }

    /**
     * Method to get TotalNumberCompliant for Report
     *
     * @param fromDate
     * @param toDate
     * @param muDocType
     * @param facility
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public int totalNumberCompliant(Date fromDate, Date toDate,
            String muDocType, String facility) {
        final String logSM = "totalNumberCompliant()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + toDate + fromDate + muDocType
                    + facility);
        }
        int totalNumberCompliant = 0;

        Object[] values = {fromDate, toDate, muDocType, facility};
        List<MUROIOutboundStatistics> totalNumberCompliantList = (List<MUROIOutboundStatistics>) getHibernateTemplate()
                .findByNamedQuery("retriveFromROIOutboundForFacilityView",
                        values);
        if (totalNumberCompliantList.size() > 0) {

            for (int i = 0; i < totalNumberCompliantList.size(); i++) {
                Date reqDate = totalNumberCompliantList.get(i).getReqDate();
                Date fullfilledDate = totalNumberCompliantList.get(i)
                        .getFulfilledDate();
                double diffInDays = (fullfilledDate.getTime() - reqDate
                        .getTime()) / (ROIConstants.ONE_DAY);
                int weekendDays = ROIReportUtil.getNoOfWeekEndDays(reqDate,fullfilledDate);
                double businessDays = ROIReportUtil.calculateBusDays(reqDate,fullfilledDate);
                if(weekendDays != 0)
                {
                   diffInDays = businessDays;
                }
                if (diffInDays <= ROIConstants.DAYS_FOR_MINIMUM_COMPLIANCE) {
                    totalNumberCompliant++;

                }

            }
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + totalNumberCompliant);
        }
        return totalNumberCompliant;

    }
    /**
     * Method to delete ROIOutbound Details based on requestId
     *
     * @param requestId
     *
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public void deleteROIOutbound(int requestId) {
        final String logSM = "deleteROIOutbound()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }
        Object[] values = {requestId,requestId};
        List<MUROIOutboundStatistics> muROIOutboundStatisticsList = (List<MUROIOutboundStatistics>) getHibernateTemplate()
                .findByNamedQuery("getOutboundStatisticsByRequestIdandStatus",
                        values);
        for (int i = 0; muROIOutboundStatisticsList != null
                && i < muROIOutboundStatisticsList.size(); i++) {
            MUROIOutboundStatistics muROIOutboundStatistics = muROIOutboundStatisticsList
                    .get(i);
            getHibernateTemplate().delete(muROIOutboundStatistics);
        }
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
    }

//    /**
//     * Method to retrieve Release Details based on requestId
//     *
//     * @param requestId
//     *
//     * @return Release
//     */
//    @SuppressWarnings("unchecked")
//    public Release retrieveReleaseIdByRequestIdForLatestRequest(long requestId) {
//        final String logSM = "retrieveReleaseIdByRequestIdForLatestRequest()";
//        if (DO_DEBUG) {
//            LOG.debug(logSM + ">>Start:" + requestId);
//        }
//        Object[] values = {requestId};
//        List<Release> releaseList = (List<Integer>) getHibernateTemplate().findByNamedQuery(
//                "retrieveReleaseIdByRequestIdForLatestRequest", values);
//        Release release = new Release();
//        if (releaseList != null && releaseList.size() > 0) {
//            release = releaseList.get(0);
//        }
//        return release;
//    }
//    /**
//     * Method to retrieve ReleaseInfo Details based on releaseId
//     *
//     * @param releaseId
//     *
//     * @return ReleaseInfo
//     */
//    @SuppressWarnings("unchecked")
//    public ReleaseInfo retrieveXMLDetailsByLatestReleaseId(long releaseId) {
//        final String logSM = "retrieveXMLDetailsByLatestReleaseId()";
//        if (DO_DEBUG) {
//            LOG.debug(logSM + ">>Start:" + releaseId);
//        }
//        Object[] values = {releaseId};
//        List<ReleaseInfo> releaseInfoList = (List<Integer>) getHibernateTemplate()
//                .findByNamedQuery("retrieveReleaseInfoDetailsForLatestRelease",
//                        values);
//        ReleaseInfo releaseInfo = new ReleaseInfo();
//        if (releaseInfoList != null && releaseInfoList.size() > 0) {
//            releaseInfo = releaseInfoList.get(0);
//        }
//        return releaseInfo;
//    }

    /**
     * Method to get UserName by passing userInstanceId
     *
     * @param userID
     *
     * @return UserName
     */
    @Override
    @SuppressWarnings("unchecked")
    public String getUserName(int userID) {
        final String logSM = "getUserName()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        Object[] values = {userID};
        List<User> userList = (List<User>) getHibernateTemplate()
                .findByNamedQuery("getUserName", values);

        User user = new User();
        if (userList.size() > 0) {
            user = userList.get(0);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return user.getFullName();

    }

    /**
     * Method to get FacilityName
     *
     * @param facilityCode
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public String getFacilityName(String facilityCode) {
        final String logSM = "getFacilityName()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + facilityCode);
        }

        List<Facility> facilityList = (List<Facility>) getHibernateTemplate().findByNamedQuery(
                "getCodeSetIdForFacility", facilityCode);
        Facility facilityModel = new Facility();
        if (facilityList.size() > 0) {
            facilityModel = facilityList.get(0);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return facilityModel.getFacilityName();

    }

}
