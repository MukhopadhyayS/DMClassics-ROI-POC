package com.mckesson.eig.roi.ccd.provider.local;

public class CcdSourceConfigDto {
    private String _configKey;
    private String _configValue;
    private String _configLabel;
    
    
    public String getConfigKey() {
        return _configKey;
    }
    
    public void setConfigKey(String configKey) {
        _configKey = configKey;
    }
    
    public String getConfigValue() {
        return _configValue;
    }
    
    public void setConfigValue(String configValue) {
        _configValue = configValue;
    }

    public String getConfigLabel() {
        return _configLabel;
    }

    public void setConfigLabel(String configLabel) {
        _configLabel = configLabel;
    }
    
   

}
