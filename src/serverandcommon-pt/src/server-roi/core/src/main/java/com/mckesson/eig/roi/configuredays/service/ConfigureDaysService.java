package com.mckesson.eig.roi.configuredays.service;

import com.mckesson.eig.roi.configuredays.model.ConfigureDaysDtoList;

public interface ConfigureDaysService {

    //method to retieve weekend days
    int retrieveWeekendDays();
    //method to update days status
    boolean updateDaysStatus(ConfigureDaysDtoList configureDaysDtoList);
    //method to retrieve configured days status
    ConfigureDaysDtoList retrieveConfigureDaysStatus();

}
