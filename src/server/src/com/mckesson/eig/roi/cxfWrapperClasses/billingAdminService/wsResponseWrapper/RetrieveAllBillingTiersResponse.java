
package com.mckesson.eig.roi.cxfWrapperClasses.billingAdminService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.admin.model.BillingTiersList;


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
 *         &lt;element name="billingTiers" type="{urn:eig.mckesson.com}BillingTierList"/>
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
    "billingTiers"
})
@XmlRootElement(name = "retrieveAllBillingTiersResponse")
public class RetrieveAllBillingTiersResponse {

    @XmlElement(required = true)
    protected BillingTiersList billingTiers;

    /**
     * Gets the value of the billingTiers property.
     * 
     * @return
     *     possible object is
     *     {@link BillingTierList }
     *     
     */
    public BillingTiersList getBillingTiers() {
        return billingTiers;
    }

    /**
     * Sets the value of the billingTiers property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingTierList }
     *     
     */
    public void setBillingTiers(BillingTiersList value) {
        this.billingTiers = value;
    }

}
