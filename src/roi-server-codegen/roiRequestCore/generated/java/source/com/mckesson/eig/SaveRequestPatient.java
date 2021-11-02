
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
 *         &lt;element name="saveRequestPatientsList" type="{urn:eig.mckesson.com}SaveRequestPatientList"/>
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
    "saveRequestPatientsList"
})
@XmlRootElement(name = "saveRequestPatient")
public class SaveRequestPatient {

    @XmlElement(required = true)
    protected SaveRequestPatientList saveRequestPatientsList;

    /**
     * Gets the value of the saveRequestPatientsList property.
     * 
     * @return
     *     possible object is
     *     {@link SaveRequestPatientList }
     *     
     */
    public SaveRequestPatientList getSaveRequestPatientsList() {
        return saveRequestPatientsList;
    }

    /**
     * Sets the value of the saveRequestPatientsList property.
     * 
     * @param value
     *     allowed object is
     *     {@link SaveRequestPatientList }
     *     
     */
    public void setSaveRequestPatientsList(SaveRequestPatientList value) {
        this.saveRequestPatientsList = value;
    }

}
