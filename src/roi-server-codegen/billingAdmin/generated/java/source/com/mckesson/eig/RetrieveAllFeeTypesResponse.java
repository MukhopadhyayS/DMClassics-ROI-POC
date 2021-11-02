
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
 *         &lt;element name="feeTypes" type="{urn:eig.mckesson.com}FeeTypeList"/>
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
    "feeTypes"
})
@XmlRootElement(name = "retrieveAllFeeTypesResponse")
public class RetrieveAllFeeTypesResponse {

    @XmlElement(required = true)
    protected FeeTypeList feeTypes;

    /**
     * Gets the value of the feeTypes property.
     * 
     * @return
     *     possible object is
     *     {@link FeeTypeList }
     *     
     */
    public FeeTypeList getFeeTypes() {
        return feeTypes;
    }

    /**
     * Sets the value of the feeTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link FeeTypeList }
     *     
     */
    public void setFeeTypes(FeeTypeList value) {
        this.feeTypes = value;
    }

}
