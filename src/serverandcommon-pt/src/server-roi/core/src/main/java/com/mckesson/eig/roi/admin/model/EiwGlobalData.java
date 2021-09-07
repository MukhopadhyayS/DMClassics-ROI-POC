package com.mckesson.eig.roi.admin.model;

import java.io.Serializable;

public class EiwGlobalData
implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String _globalName;
    private Integer _globalIntValue;
    private String _globalCharValue;
    private String _globalStrValue;
    
    public String getGlobalName() { return _globalName; }
    public void setGlobalName(String globalName) { _globalName = globalName; }
    
    public Integer getGlobalIntValue() { return _globalIntValue; }
    public void setGlobalIntValue(Integer globalIntValue) { _globalIntValue = globalIntValue; }
    
    public String getGlobalCharValue() { return _globalCharValue; }
    public void setGlobalCharValue(String globalCharValue) { _globalCharValue = globalCharValue; }
     
    public String getGlobalStrValue() { return _globalStrValue; }
    public void setGlobalStrValue(String globalStrValue) { _globalStrValue = globalStrValue; }

}
