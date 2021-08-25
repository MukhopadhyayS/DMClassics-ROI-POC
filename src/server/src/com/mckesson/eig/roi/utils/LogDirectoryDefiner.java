package com.mckesson.eig.roi.utils;

import java.io.File;
import org.springframework.util.StringUtils;
import ch.qos.logback.core.PropertyDefinerBase;

public class LogDirectoryDefiner extends PropertyDefinerBase {

    private String _defaultLogDirectory = "../logs/";
    private String _logDirectory = null;
    
    public String getDefaultLogDirectory() {
        return _defaultLogDirectory;
    }
    
    public void setPropValue(String propValue) {
        _logDirectory = _defaultLogDirectory;

        if (StringUtils.hasLength(propValue)) {
            try {
                File f = new File(propValue);
                if (f.isDirectory() || f.mkdirs()) {
                    _logDirectory = propValue;
                } else {
                    outputLogDirError(propValue);
                }
            } catch (Exception e) {
                outputLogDirError(propValue);
            }
        }
    }
    
    private void outputLogDirError(String value) {
        System.out.println("Error creating log directory at: " + value + ".  Using default location: " + _logDirectory);
    }

    @Override
    public String getPropertyValue() {
        return _logDirectory;
    }
    
}
