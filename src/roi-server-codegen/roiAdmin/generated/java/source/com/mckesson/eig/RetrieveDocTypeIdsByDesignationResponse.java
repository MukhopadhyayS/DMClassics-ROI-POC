
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
 *         &lt;element name="designation" type="{urn:eig.mckesson.com}Designation"/>
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
    "designation"
})
@XmlRootElement(name = "retrieveDocTypeIdsByDesignationResponse")
public class RetrieveDocTypeIdsByDesignationResponse {

    @XmlElement(required = true)
    protected Designation designation;

    /**
     * Gets the value of the designation property.
     * 
     * @return
     *     possible object is
     *     {@link Designation }
     *     
     */
    public Designation getDesignation() {
        return designation;
    }

    /**
     * Sets the value of the designation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Designation }
     *     
     */
    public void setDesignation(Designation value) {
        this.designation = value;
    }

}
