package com.mckesson.eig.roi.muroioutbound.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.service.BaseROIService;
import com.mckesson.eig.roi.billing.model.ReleaseCore;
import com.mckesson.eig.roi.ccd.provider.CcdProviderFactory;
import com.mckesson.eig.roi.ccd.provider.dao.CcdProviderDAO;
import com.mckesson.eig.roi.muroioutbound.dao.MUROIOutboundDAOImpl;
import com.mckesson.eig.roi.muroioutbound.model.MUROIOutboundStatistics;
import com.mckesson.eig.roi.reports.service.ROIReportUtil;
import com.mckesson.eig.roi.request.dao.RequestCoreChargesDAO;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.utils.SpringUtil;
import com.mckesson.eig.utility.util.CollectionUtilities;

public class MUUpdateROIOutboundServiceCoreImpl 
extends BaseROIService
implements MUUpdateROIOutboundServiceCore {

    private static final OCLogger LOG = new OCLogger(MUUpdateROIOutboundServiceCoreImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     * Method to Update ROIOUTBOUND STATISTICS table on release of documents
     */
    public List<MUROIOutboundStatistics> updateROIOutboundStatistics(ReleaseCore release) {
        
        final String logSM = "updateROIOutboundStatistics(release)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + release);
        }
        
        int requestId = 0;
        try {
            List<MUROIOutboundStatistics> muROIOutboundStatisticsList = new ArrayList<MUROIOutboundStatistics>();
            if (release != null) {
                requestId = (int) release.getRequestId();
            }
            List<MUROIOutboundStatistics> statistics = getCcdProviderDao()
                    .getOutboundStatistics(requestId);
            if (!CollectionUtilities.isEmpty(statistics)) {
                List<MUROIOutboundStatistics> result = new ArrayList<MUROIOutboundStatistics>();
                for (MUROIOutboundStatistics s : statistics) {
                    if (s.getTa() <= 0) {
                        result.add(s);
                    }
                }
                if (!CollectionUtilities.isEmpty(result)) {
                    Timestamp t = getDao().getDate();
                    muROIOutboundStatisticsList = updateROIOutbound(result, t);
                    getCcdProviderFactory().sendCompletedStatistics(requestId, t);
                }
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return muROIOutboundStatisticsList;
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            throw new ROIException(e, ROIClientErrorCodes.MU_UPDATEROIOUTBOUND);
        }
    }

    /**
     * Method to update ROIOutbound details
     * 
     * @param requestForTurnAroundTimeList
     */
    private List<MUROIOutboundStatistics> updateROIOutbound(
            List<MUROIOutboundStatistics> requestForTurnAroundTimeList, Timestamp t) {
        List<MUROIOutboundStatistics> muROIOutboundStatisticsList = new ArrayList<MUROIOutboundStatistics>();
        RequestCoreChargesDAO requestCoreChargesDao = (RequestCoreChargesDAO) getDAO(DAOName.REQUEST_CORE_CHARGES);
        for (MUROIOutboundStatistics s : requestForTurnAroundTimeList) {
            MUROIOutboundStatistics muROIOutboundStatistics = null;
            double ta = calculateTurnAroundTime(s.getReqDate(), t);
            s.setAvailDate(t);
            s.setFulfilledDate(t);
            s.setTa(ta);
            s.setWeekEndDays(ROIReportUtil.getNoOfWeekEndDays(s.getReqDate(), t));
            s.setTotalDays(ROIReportUtil.calculateBusDays(s.getReqDate(), t));
            s.setReqStatus(ROIConstants.FULFILLED_STATUS);
            s.setDocumentType(requestCoreChargesDao.retrieveOutputType((long)s.getReqID()));
            muROIOutboundStatistics = getDao().updateROIOutbound(s);
            muROIOutboundStatisticsList.add(muROIOutboundStatistics);
        }
        return muROIOutboundStatisticsList;
    }

    /**
     * Method to calculate the TurnAroundTime
     * 
     * @param reqDate
     * @param fullFilledDate
     * @return
     */
    private double calculateTurnAroundTime(Date reqDate, Date fullFilledDate) {
        double turnAroundTime;
        int weekEndDays = ROIReportUtil.getNoOfWeekEndDays(reqDate, fullFilledDate);
        double diffInDays = ((double)(fullFilledDate.getTime() - reqDate.getTime()) / (ROIConstants.ONE_DAY));
        if (diffInDays > weekEndDays) {
            double businessDays = diffInDays - weekEndDays;
            turnAroundTime = (double) (businessDays * 24);
        } else {
            turnAroundTime = 0.0;
        }
        return turnAroundTime;
    }

    /**
     * Method to get the instance for MUROIOutboundDAOImpl
     * 
     * @return
     */
    private MUROIOutboundDAOImpl getDao() {
        return (MUROIOutboundDAOImpl) SpringUtil
                .getObjectFromSpring("MUROIOutboundDAO");
    }

    /**
     * Method to get the Dao information
     * 
     * @return CcdProviderDAOImpl
     */
    private CcdProviderDAO getCcdProviderDao() {
        return (CcdProviderDAO) SpringUtil
                .getObjectFromSpring("CcdProviderDAO");
    }

    /**
     * Method to get the CcdProviderFactory instance
     */
    private CcdProviderFactory getCcdProviderFactory() {
        return (CcdProviderFactory) SpringUtil
                .getObjectFromSpring("ccdProviderFactory");
    }
}
