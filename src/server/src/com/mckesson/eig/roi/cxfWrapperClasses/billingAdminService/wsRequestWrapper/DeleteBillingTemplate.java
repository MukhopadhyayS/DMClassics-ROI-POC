
package com.mckesson.eig.roi.cxfWrapperClasses.billingAdminService.wsRequestWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="billingTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "billingTemplateId"
})
@XmlRootElement(name = "deleteBillingTemplate")
public class DeleteBillingTemplate {

    protected long billingTemplateId;

    /**
     * Gets the value of the billingTemplateId property.
     * 
     */
    public long getBillingTemplateId() {
        return billingTemplateId;
    }

    /**
     * Sets the value of the billingTemplateId property.
     * 
     */
    public void setBillingTemplateId(long value) {
        this.billingTemplateId = value;
    }

}
