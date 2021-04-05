/**
 * 
 */
package com.mckesson.eig.roi.admin.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.admin.model.SysParam;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author Shah Mohamed.N
 *
 */
public class SysParamDAOImpl extends ROIDAOImpl implements SysParamDAO {

    private static final OCLogger LOG = new OCLogger(AttachmentDAOImpl.class);
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
    
    /** 
     * This method is used to update the Unbillable RequestFlag in SysParms_Global table
     * 
     * @param checked
     * 
     */
    public void updateUnbillableRequestFlag(boolean checked) {
        final String logSM = "updateUnbillableRequestFlag()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + checked);
        }
        try {
            Session session = getSession();
            Query query = session.getNamedQuery("updateROIRequestUnbillable");
            query.setParameter("unbillable", checked ? "true" : "false", StringType.INSTANCE);
            query.setParameter("modifiedDt", getDate(), StandardBasicTypes.TIMESTAMP);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
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
     * This method is used to retrieve the Unbillable RequestFlag in SysParms_Global table
     * 
     * @return unbillableFlag
     */
    public boolean retrieveUnbillableRequestFlag() {
        final String logSM = "retrieveUnbillableRequestFlag()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        boolean unbillableFlag = false;
        try {
            Session session = getSession();
            String query = session.getNamedQuery("retrieveROIRequestUnbillable").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.addScalar("unbillable", StringType.INSTANCE);
            String unbillable = (String) sqlQuery.uniqueResult();
            if (!StringUtilities.isEmpty(unbillable)) {
                if ("true".equalsIgnoreCase(unbillable)) {
                    unbillableFlag = true;
                } 
            }
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return unbillableFlag;
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

}
