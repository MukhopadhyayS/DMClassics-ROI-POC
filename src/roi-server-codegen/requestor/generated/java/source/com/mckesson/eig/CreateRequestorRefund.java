
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
 *         &lt;element name="requestorRefund" type="{urn:eig.mckesson.com}RequestorRefund"/>
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
    "requestorRefund"
})
@XmlRootElement(name = "createRequestorRefund")
public class CreateRequestorRefund {

    @XmlElement(required = true)
    protected RequestorRefund requestorRefund;

    /**
     * Gets the value of the requestorRefund property.
     * 
     * @return
     *     possible object is
     *     {@link RequestorRefund }
     *     
     */
    public RequestorRefund getRequestorRefund() {
        return requestorRefund;
    }

    /**
     * Sets the value of the requestorRefund property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestorRefund }
     *     
     */
    public void setRequestorRefund(RequestorRefund value) {
        this.requestorRefund = value;
    }

}
