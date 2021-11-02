
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
 *         &lt;element name="requestorPaymentList" type="{urn:eig.mckesson.com}RequestorPaymentList"/>
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
    "requestorPaymentList"
})
@XmlRootElement(name = "updateRequestorPayment")
public class UpdateRequestorPayment {

    @XmlElement(required = true)
    protected RequestorPaymentList requestorPaymentList;

    /**
     * Gets the value of the requestorPaymentList property.
     * 
     * @return
     *     possible object is
     *     {@link RequestorPaymentList }
     *     
     */
    public RequestorPaymentList getRequestorPaymentList() {
        return requestorPaymentList;
    }

    /**
     * Sets the value of the requestorPaymentList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestorPaymentList }
     *     
     */
    public void setRequestorPaymentList(RequestorPaymentList value) {
        this.requestorPaymentList = value;
    }

}
