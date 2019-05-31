package com.mckesson.eig.roi.admin.model;

import java.io.Serializable;
import java.util.List;

public class GenderList implements Serializable {
    
    private List<Gender> _genderDetails;
    
    public GenderList() { 
        
    }

    public GenderList(List<Gender> list) { 
        setGenderDetails(list); 
    }

    public List<Gender> getGenderDetails() {
        return _genderDetails;
    }

    public void setGenderDetails(List<Gender> genderDetails) {
        _genderDetails = genderDetails;
    }

    
    
    

}
