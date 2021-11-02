
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
 *         &lt;element name="billingTemplates" type="{urn:eig.mckesson.com}BillingTemplateList"/>
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
    "billingTemplates"
})
@XmlRootElement(name = "retrieveAllBillingTemplatesResponse")
public class RetrieveAllBillingTemplatesResponse {

    @XmlElement(required = true)
    protected BillingTemplateList billingTemplates;

    /**
     * Gets the value of the billingTemplates property.
     * 
     * @return
     *     possible object is
     *     {@link BillingTemplateList }
     *     
     */
    public BillingTemplateList getBillingTemplates() {
        return billingTemplates;
    }

    /**
     * Sets the value of the billingTemplates property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingTemplateList }
     *     
     */
    public void setBillingTemplates(BillingTemplateList value) {
        this.billingTemplates = value;
    }

}
