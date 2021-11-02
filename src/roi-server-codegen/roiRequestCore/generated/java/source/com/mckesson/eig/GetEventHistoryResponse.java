
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
 *         &lt;element name="requestEvents" type="{urn:eig.mckesson.com}RequestEvents"/>
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
    "requestEvents"
})
@XmlRootElement(name = "getEventHistoryResponse")
public class GetEventHistoryResponse {

    @XmlElement(required = true)
    protected RequestEvents requestEvents;

    /**
     * Gets the value of the requestEvents property.
     * 
     * @return
     *     possible object is
     *     {@link RequestEvents }
     *     
     */
    public RequestEvents getRequestEvents() {
        return requestEvents;
    }

    /**
     * Sets the value of the requestEvents property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestEvents }
     *     
     */
    public void setRequestEvents(RequestEvents value) {
        this.requestEvents = value;
    }

}
