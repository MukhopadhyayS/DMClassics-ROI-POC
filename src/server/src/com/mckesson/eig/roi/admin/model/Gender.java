package com.mckesson.eig.roi.admin.model;

import java.io.Serializable;

public class Gender implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String _code;
    private String _description;
    
    public String getCode() {
        return _code;
    }
    
    public void setCode(String code) {
        _code = code;
    }
    
    public String getDescription() {
        return _description;
    }
    
    public void setDescription(String description) {
        _description = description;
    }

}
