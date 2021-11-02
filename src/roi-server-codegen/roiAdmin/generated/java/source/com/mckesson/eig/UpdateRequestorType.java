
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
 *         &lt;element name="requestorType" type="{urn:eig.mckesson.com}RequestorType"/>
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
    "requestorType"
})
@XmlRootElement(name = "updateRequestorType")
public class UpdateRequestorType {

    @XmlElement(required = true)
    protected RequestorType requestorType;

    /**
     * Gets the value of the requestorType property.
     * 
     * @return
     *     possible object is
     *     {@link RequestorType }
     *     
     */
    public RequestorType getRequestorType() {
        return requestorType;
    }

    /**
     * Sets the value of the requestorType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestorType }
     *     
     */
    public void setRequestorType(RequestorType value) {
        this.requestorType = value;
    }

}
