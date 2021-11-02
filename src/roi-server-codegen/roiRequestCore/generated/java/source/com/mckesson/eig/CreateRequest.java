
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
 *         &lt;element name="requestCore" type="{urn:eig.mckesson.com}Request"/>
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
    "requestCore"
})
@XmlRootElement(name = "createRequest")
public class CreateRequest {

    @XmlElement(required = true)
    protected Request requestCore;

    /**
     * Gets the value of the requestCore property.
     * 
     * @return
     *     possible object is
     *     {@link Request }
     *     
     */
    public Request getRequestCore() {
        return requestCore;
    }

    /**
     * Sets the value of the requestCore property.
     * 
     * @param value
     *     allowed object is
     *     {@link Request }
     *     
     */
    public void setRequestCore(Request value) {
        this.requestCore = value;
    }

}
