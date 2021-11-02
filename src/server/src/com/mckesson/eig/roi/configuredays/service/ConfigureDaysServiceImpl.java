package com.mckesson.eig.roi.configuredays.service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import com.mckesson.eig.roi.admin.dao.SysParamDAO;
import com.mckesson.eig.roi.admin.model.SysParam;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.service.BaseROIService;
import com.mckesson.eig.roi.configuredays.model.ConfigureDaysDto;
import com.mckesson.eig.roi.configuredays.model.ConfigureDaysDtoList;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.CollectionUtilities;

@WebService(serviceName="ConfigureDaysService", endpointInterface="com.mckesson.eig.roi.configuredays.service.ConfigureDaysService",
targetNamespace="urn:eig.mckesson.com", portName="configureDaysService", name="ConfigureDaysServiceImpl")
public class ConfigureDaysServiceImpl extends BaseROIService
        implements
            ConfigureDaysService {

    private static final OCLogger LOG = new OCLogger(ConfigureDaysServiceImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    //SysParamDAO dao = (SysParamDAO) getDAO(DAOName.SYSPARAM_DAO);

    /**
     * Method to Retrive Number of Week End Days
     * 
     * @param
     * @return int(Number of Weekend days)
     */
    public int retrieveWeekendDays() {
        SysParamDAO dao = (SysParamDAO) getDAO(DAOName.SYSPARAM_DAO);
        final String logSM = "retrieveWeekendDays()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {
            int noOfWeekEndDays = dao.retrieveWeekendDays();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End :" + noOfWeekEndDays);
            }
            return noOfWeekEndDays;
        } catch (Throwable e) {
            throw new ROIException(ROIClientErrorCodes.CONFIGURE_DAYS_RETRIEVE_WEEKENDDAYS);
        }
    }

    /**
     * Method to Update Days Status
     * 
     * @param ConfigureDaysDtoList
     *            obj
     * @return boolean
     */
    public boolean updateDaysStatus(ConfigureDaysDtoList configureDaysDtoList) {
        SysParamDAO dao = (SysParamDAO) getDAO(DAOName.SYSPARAM_DAO);
        final String logSM = "updateDayStatus(configureDaysDtoList)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        try {
            User user = getUser();
            ConfigureDaysDto configureDaysDto = null;
            String dayName = null;
            String dayStatus = null;
            List<ConfigureDaysDto> configureDaysDtoListReq = configureDaysDtoList
                    .getConfigureDaysDtoList();
            if (!CollectionUtilities.isEmpty(configureDaysDtoListReq)) {
                for (int i = 0; i < configureDaysDtoListReq.size(); i++) {
                    configureDaysDto = configureDaysDtoListReq.get(i);
                    dayName = configureDaysDto.getDayName();
                    dayStatus = configureDaysDto.getDayStatus();

                    SysParam sysParam = dao.getDayStatusObj(dayName);

                    if (!(sysParam.getGlobalVariant()
                            .equalsIgnoreCase(dayStatus))) {

                        sysParam.setGlobalVariant(dayStatus);

                        dao.updateDaysStatus(sysParam, user);
                    }

                }
            }
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End :");
            }
            return true;
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            throw new ROIException(ROIClientErrorCodes.CONFIGURE_DAYS_CREATE_OR_UPDATE_DAYSTATUS);
        }

    }
    /**
     * Method to retrieve Configured days Status
     * 
     * @param
     * @return ConfigureDaysDtoList
     */
    public ConfigureDaysDtoList retrieveConfigureDaysStatus() {
        final String logSM = "retrieveConfigureDaysStatus";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {
            SysParamDAO dao = (SysParamDAO) getDAO(DAOName.SYSPARAM_DAO);
            ConfigureDaysDtoList configureDaysDtoList = new ConfigureDaysDtoList();
            List<ConfigureDaysDto> configureDaysList = new ArrayList<ConfigureDaysDto>();

            List<SysParam> sysParams = dao.retrieveConfigureDaysStatus();

            if (!CollectionUtilities.isEmpty(sysParams)) {

                for (SysParam sysParam : sysParams) {
                    ConfigureDaysDto configureDaysDto = new ConfigureDaysDto();
                    configureDaysDto.setDayName(sysParam.getGlobalName());
                    configureDaysDto.setDayStatus(sysParam.getGlobalVariant());
                    configureDaysList.add(configureDaysDto);
                }
                configureDaysDtoList.setConfigureDaysDtoList(configureDaysList);
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End :");
            }
            return configureDaysDtoList;
        } catch (Throwable e) {
          throw new ROIException(ROIClientErrorCodes.CONFIGURE_DAYS_RETRIEVE_CONFIGURED_DAYS);
        }

    }

}
