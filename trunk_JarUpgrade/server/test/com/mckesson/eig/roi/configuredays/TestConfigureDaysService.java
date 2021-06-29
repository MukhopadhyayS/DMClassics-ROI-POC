package com.mckesson.eig.roi.configuredays;

import java.util.ArrayList;
import java.util.List;

import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.configuredays.model.ConfigureDaysDto;
import com.mckesson.eig.roi.configuredays.model.ConfigureDaysDtoList;
import com.mckesson.eig.roi.configuredays.service.ConfigureDaysServiceImpl;
import com.mckesson.eig.roi.test.BaseROITestCase;

public class TestConfigureDaysService extends BaseROITestCase {

    protected static final String CONFIGUREDAYS_SERVICE = "com.mckesson.eig.roi.configuredays.service.ConfigureDaysServiceImpl";

    private static ConfigureDaysServiceImpl _configureDaysServiceImpl;

    protected static final String SYSPARAM_DAO = "SysParamDAO";

    public void initializeTestData() throws Exception {
        _configureDaysServiceImpl = (ConfigureDaysServiceImpl) getService(CONFIGUREDAYS_SERVICE);

    }

    /*Method to test Retrieve No of Week End Days*/

    public void testRetrieveWeekendDays() {
        try {
            int count = _configureDaysServiceImpl.retrieveWeekendDays();
            assertNotSame(0, count);
        } catch (ROIException e) {
            fail("Retrieving No of Week End Days should not thrown exception."
                    + e.getErrorCode());
        }
    }

    /*Method to test Update Day Status*/

    public void testUpdateDaysStatus() {
        try {

            ConfigureDaysDto configureDaysDto = new ConfigureDaysDto();
            configureDaysDto.setDayName("Saturday");
            configureDaysDto.setDayStatus("BUSINESS DAY");
            List<ConfigureDaysDto> configureDaysDtoList = new ArrayList<ConfigureDaysDto>();
            configureDaysDtoList.add(0, configureDaysDto);

            ConfigureDaysDtoList configureDaysDtoListObj = new ConfigureDaysDtoList();
            configureDaysDtoListObj
                    .setConfigureDaysDtoList(configureDaysDtoList);

            boolean result = _configureDaysServiceImpl
                    .updateDaysStatus(configureDaysDtoListObj);
            assertEquals(true, result);

        } catch (ROIException e) {
            fail("Creating or Updating Configuring Days Should not throw Exception."
                    + e.getErrorCode());
        }
    }

    /*Method to test Retrieve Configured Days Status*/

    public void testRetrieveConfigureDaysStatus() {
        try {
            ConfigureDaysDtoList configureDaysDtoList = _configureDaysServiceImpl
                    .retrieveConfigureDaysStatus();
            assertNotSame(0, configureDaysDtoList.getConfigureDaysDtoList()
                    .size());
        } catch (ROIException e) {
            fail("Retrieving Configuring Days Should not throw Exception."
                    + e.getErrorCode());
        }
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        // TODO Auto-generated method stub
        return null;
    }

}
