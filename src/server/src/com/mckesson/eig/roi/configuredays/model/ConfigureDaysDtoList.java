package com.mckesson.eig.roi.configuredays.model;

import java.util.List;



public class ConfigureDaysDtoList {
    
    private List<ConfigureDaysDto> _configureDaysDtoList;

    public ConfigureDaysDtoList() {
    }

    public List<ConfigureDaysDto> getConfigureDaysDtoList() {
        return _configureDaysDtoList;
    }

    public void setConfigureDaysDtoList(List<ConfigureDaysDto> daysDtoList) {
        _configureDaysDtoList = daysDtoList;
    }
    
    public ConfigureDaysDtoList(List<ConfigureDaysDto> status) {
        setConfigureDaysDtoList(status);
}

  

    
    

}
