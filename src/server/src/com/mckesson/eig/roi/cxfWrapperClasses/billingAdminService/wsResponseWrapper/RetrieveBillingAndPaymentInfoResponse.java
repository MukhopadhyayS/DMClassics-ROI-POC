
package com.mckesson.eig.roi.cxfWrapperClasses.billingAdminService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.admin.model.BillingPaymentInfo;


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
 *         &lt;element name="billingPaymentInfo" type="{urn:eig.mckesson.com}BillingPaymentInfo"/>
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
    "billingPaymentInfo"
})
@XmlRootElement(name = "retrieveBillingAndPaymentInfoResponse")
public class RetrieveBillingAndPaymentInfoResponse {

    @XmlElement(required = true)
    protected BillingPaymentInfo billingPaymentInfo;

    /**
     * Gets the value of the billingPaymentInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BillingPaymentInfo }
     *     
     */
    public BillingPaymentInfo getBillingPaymentInfo() {
        return billingPaymentInfo;
    }

    /**
     * Sets the value of the billingPaymentInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingPaymentInfo }
     *     
     */
    public void setBillingPaymentInfo(BillingPaymentInfo value) {
        this.billingPaymentInfo = value;
    }

}
