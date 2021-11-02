
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patient" type="{urn:eig.mckesson.com}SupplementalPatient"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "patient"
})
@XmlRootElement(name = "createSupplementalPatient")
public class CreateSupplementalPatient {

    @XmlElement(required = true)
    protected SupplementalPatient patient;

    /**
     * Gets the value of the patient property.
     * 
     * @return
     *     possible object is
     *     {@link SupplementalPatient }
     *     
     */
    public SupplementalPatient getPatient() {
        return patient;
    }

    /**
     * Sets the value of the patient property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplementalPatient }
     *     
     */
    public void setPatient(SupplementalPatient value) {
        this.patient = value;
    }

}
