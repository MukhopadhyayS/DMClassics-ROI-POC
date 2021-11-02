
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
 *         &lt;element name="requestorSearchCriteria" type="{urn:eig.mckesson.com}RequestorSearchCriteria"/>
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
    "requestorSearchCriteria"
})
@XmlRootElement(name = "findRequestor")
public class FindRequestor {

    @XmlElement(required = true)
    protected RequestorSearchCriteria requestorSearchCriteria;

    /**
     * Gets the value of the requestorSearchCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link RequestorSearchCriteria }
     *     
     */
    public RequestorSearchCriteria getRequestorSearchCriteria() {
        return requestorSearchCriteria;
    }

    /**
     * Sets the value of the requestorSearchCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestorSearchCriteria }
     *     
     */
    public void setRequestorSearchCriteria(RequestorSearchCriteria value) {
        this.requestorSearchCriteria = value;
    }

}
