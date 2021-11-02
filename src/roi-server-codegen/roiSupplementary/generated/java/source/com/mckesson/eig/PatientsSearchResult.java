
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


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
    "patients",
    "maxCountExceeded"
})
public class PatientsSearchResult {

    protected List<SupplementalPatient> patients;
    protected boolean maxCountExceeded;

    /**
     * Gets the value of the patients property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the patients property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPatients().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupplementalPatient }
     * 
     * 
     */
    public List<SupplementalPatient> getPatients() {
        if (patients == null) {
            patients = new ArrayList<SupplementalPatient>();
        }
        return this.patients;
    }

    /**
     * Gets the value of the maxCountExceeded property.
     * 
     */
    public boolean isMaxCountExceeded() {
        return maxCountExceeded;
    }

    /**
     * Sets the value of the maxCountExceeded property.
     * 
     */
    public void setMaxCountExceeded(boolean value) {
        this.maxCountExceeded = value;
    }

}
