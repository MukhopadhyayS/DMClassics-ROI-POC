package com.mckesson.eig.roi.admin.model;

import java.io.Serializable;
import java.util.List;

public class GenderList implements Serializable {
    
    private List<Gender> _genders;
    
    public GenderList() { 
        
    }

    public GenderList(List<Gender> list) { 
        setGenders(list); 
    }

    public List<Gender> getGenders() {
        return _genders;
    }

    public void setGenders(List<Gender> genders) {
        _genders = genders;
    } 
    
    

}
