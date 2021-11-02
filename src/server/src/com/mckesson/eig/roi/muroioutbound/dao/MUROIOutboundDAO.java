package com.mckesson.eig.roi.muroioutbound.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.muroioutbound.model.MUROIOutboundStatistics;

public interface MUROIOutboundDAO {
    /**
     * Method to insert ROIOutbound Details
     *
     * @param List
     *            <MUROIOutboundStatistics> muOutboundStatistics
     * @return
     */
    public void createROIOutbound(
            List<MUROIOutboundStatistics> muOutboundStatistics);
    /**
     * Method to update ROIOutbound details
     *
     * @param muROIOutboundStatistics
     * @return muROIOutboundStatisticsObj
     */
    public MUROIOutboundStatistics updateROIOutbound(
            MUROIOutboundStatistics muROIOutboundStatistics);

    /**
     * Method to retrieve ROIOutboundDetails for Report
     *
     * @param fromDate
     * @param toDate
     * @param muDocName
     * @param facility
     * @return
     */
    public List<MUROIOutboundStatistics> retriveFromROIOutboundForReport(
            Date fromDate, Date toDate, String muDocName, String facility);

    @SuppressWarnings("unchecked")
    public List<MUROIOutboundStatistics> totalNumberOfReleases(Date fromDate,
            Date toDate, String muDocName, String facility);

    /**
     * Method to get TotalNumberCompliant for Report
     *
     * @param fromDate
     * @param toDate
     * @param muDocType
     * @param facility
     * @return
     */
    public int totalNumberCompliant(Date fromDate, Date toDate,
            String muDocType, String facility);
    /**
     * Method to delete ROIOutbound Details based on requestId
     *
     * @param requestId
     *
     * @return
     */
    public void deleteROIOutbound(int requestId);
//    /**
//     * Method to retrieve Release Details based on requestId
//     *
//     * @param requestId
//     *
//     * @return Release
//     */
//    public Release retrieveReleaseIdByRequestIdForLatestRequest(long requestId);
//    /**
//     * Method to retrieve ReleaseInfo Details based on releaseId
//     *
//     * @param releaseId
//     *
//     * @return ReleaseInfo
//     */
//    public ReleaseInfo retrieveXMLDetailsByLatestReleaseId(long releaseId);
    /**
     * Method to get UserName by passing UserInstanceId
     * @param userID
     * @return
     */
    public String getUserName(int userID);
    /**
     * Method to retrieve Facility Name
     * @param facilityCode
     * @return
     */
    public String getFacilityName(String facilityCode);
    
    /**
     * This method processes the date to set the time
     * difference in milliseconds b/w Database server and JVM
     *
     * @return Database server date
     */
    // Bhaskar
    // I think date and timestamp are two different things
    Timestamp getDate();

}
