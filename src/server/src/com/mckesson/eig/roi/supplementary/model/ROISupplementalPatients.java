
package com.mckesson.eig.roi.supplementary.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.supplementary.model.ROISupplementalPatient;


/**
 * <p>Java class for PatientsSearchResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PatientsSearchResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patients" type="{urn:eig.mckesson.com}SupplementalPatient" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="maxCountExceeded" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatientsSearchResult", propOrder = {
    "maxCountExceeded",
    "patients"
})
public class ROISupplementalPatients {

    protected boolean maxCountExceeded;
    protected List<ROISupplementalPatient> patients;
    
    

    public ROISupplementalPatients() {
        super();
    }
    
    public boolean getMaxCountExceeded() { 
        return maxCountExceeded; 
    }
    
    public void setMaxCountExceeded(boolean maxCountExceeded) {
        this.maxCountExceeded = maxCountExceeded;
    }

    public ROISupplementalPatients(List<ROISupplementalPatient> patients) {
        super();
        setPatients(patients);
    }
    
    public List<ROISupplementalPatient> getPatients() {
        return patients == null ? new ArrayList<ROISupplementalPatient>() : patients;
    }

    public void setPatients(List<ROISupplementalPatient> patients) {
        this.patients = patients;
    }

    public void add(ROISupplementalPatient patient) {
        if(patients == null) {
            patients = new ArrayList<ROISupplementalPatient>();
        }
        patients.add(patient);
    }

   
}
