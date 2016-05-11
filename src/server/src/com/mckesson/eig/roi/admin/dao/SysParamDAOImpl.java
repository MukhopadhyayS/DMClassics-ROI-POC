/**
 * 
 */
package com.mckesson.eig.roi.admin.dao;

import java.util.ArrayList;
import java.util.List;

import com.mckesson.eig.roi.admin.model.SysParam;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.hpf.model.User;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author Shah Mohamed.N
 *
 */
public class SysParamDAOImpl extends ROIDAOImpl implements SysParamDAO {

    private static final Log LOG = LogFactory.getLogger(AttachmentDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    
    private static final String KEY_REQUEST_PASWORD = "roi.request.password%";

    /*
     * (non-Javadoc)
     * 
     * @see com.mckesson.eig.roi.admin.dao.SysParamDAO#retrieveROISysParams()
     */
    @Override
    public List<SysParam> retrieveROISysParams() {

        final String logSM = "retrieveROISysParams";
        
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        
        // Currently passing the key as roi.request.password. later on if the key is roi this
        // query will return all the roi specific configurations.
        @SuppressWarnings("unchecked")
        List<SysParam> sysParams = getHibernateTemplate().findByNamedQuery("retrieveSysParams",
                                                                           KEY_REQUEST_PASWORD);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return sysParams;
    }
    /**
     * Method to retrieve Configured days Status
     * 
     * @param
     * @return List<SysParam>
     */

    @SuppressWarnings("unchecked")
    public List<SysParam> retrieveConfigureDaysStatus() {

        final String logSM = "retrieveConfigureDaysStatus()";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        List<SysParam> sysParams = getHibernateTemplate().findByNamedQuery(
                "retrieveConfigureDaysStatus");
        List<SysParam> sysParamsList = new ArrayList<SysParam>();
        String dayName = null;
        String dayStatus = null;

        for (int i = 0; i < sysParams.size(); i++) {
            dayName = sysParams.get(i).getGlobalName();
            dayStatus = sysParams.get(i).getGlobalVariant();
            SysParam sysParam = new SysParam();
            sysParam.setGlobalName(dayName);
            sysParam.setGlobalVariant(dayStatus);
            sysParamsList.add(i, sysParam);

        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return sysParamsList;
    }

    /**
     * Method to Retrieve Number of WeekEnd Days
     * 
     * @param
     * @return int(Number of WeekEnd Days)
     */

    @SuppressWarnings("unchecked")
    public int retrieveWeekendDays() {
        int noOfWeekEndDays = 0;
        final String logSM = "retrieveWeekendDays()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        List<Long> ids = getHibernateTemplate().findByNamedQuery(
                "retrieveNoOfWeekEndDays");
        if (ids.size() > 0 && ids.get(0) != null) {

            noOfWeekEndDays = ids.get(0).intValue();
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + noOfWeekEndDays);
        }
        return noOfWeekEndDays;
    }
    /**
     * Method to get DayStatus Obj
     * 
     * @param dayName
     * @return SysParam Obj
     */
    @SuppressWarnings("unchecked")
    public SysParam getDayStatusObj(String dayName) throws ROIException {
        final String logSM = "getDayStatusObj()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        if (StringUtilities.isEmpty(dayName)) {
            throw new ROIException(
                    ROIClientErrorCodes.CONFIGURE_GETDAYS_OBJECT);
        }

        List<SysParam> sysParams = getHibernateTemplate().findByNamedQuery(
                "getDayStatusObj", dayName);

        SysParam sysParam = (sysParams.size() == 0) ? null : sysParams.get(0);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + sysParam);
        }

        return sysParam;
    }

    /**
     * Method to Update Days Status
     * 
     * @param SysParam
     *            Obj
     * @return SysParam Obj
     */
    public SysParam updateDaysStatus(SysParam sysParam, User user) {
        final String logSM = "updateDayStatus()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + sysParam);
        }
        sysParam.setModifiedDate(getDate());
        sysParam.setModifiedBy(user.getInstanceIdValue());
        SysParam updatedSysParam = (SysParam) merge(sysParam);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + updatedSysParam);
        }
        return updatedSysParam;
    }

}
