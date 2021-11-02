package com.mckesson.eig.roi.admin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GenderList", propOrder = {
    "genderDetails"
})
public class GenderList implements Serializable {
    
    @XmlElement(nillable = true)
    private List<Gender> genderDetails;
    
    public GenderList() { 
        
    }

    public GenderList(List<Gender> list) { 
        setGenderDetails(list); 
    }

    public List<Gender> getGenderDetails() {
        if (genderDetails == null) {
            genderDetails = new ArrayList<Gender>();
        }
        return this.genderDetails;
    }

    public void setGenderDetails(List<Gender> genderDetails) {
        this.genderDetails = genderDetails;
    }


}
