
package com.mckesson.eig.roi.cxfWrapperClasses.billingAdminService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.admin.model.TaxPerFacilityList;


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
 *         &lt;element name="TaxPerFacilityList" type="{urn:eig.mckesson.com}TaxPerFacilityList"/>
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
    "taxPerFacilityList"
})
@XmlRootElement(name = "retrieveAllTaxPerFacilitiesByUserResponse")
public class RetrieveAllTaxPerFacilitiesByUserResponse {

    @XmlElement(name = "TaxPerFacilityList", required = true)
    protected TaxPerFacilityList taxPerFacilityList;

    /**
     * Gets the value of the taxPerFacilityList property.
     * 
     * @return
     *     possible object is
     *     {@link TaxPerFacilityList }
     *     
     */
    public TaxPerFacilityList getTaxPerFacilityList() {
        return taxPerFacilityList;
    }

    /**
     * Sets the value of the taxPerFacilityList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxPerFacilityList }
     *     
     */
    public void setTaxPerFacilityList(TaxPerFacilityList value) {
        this.taxPerFacilityList = value;
    }

}
