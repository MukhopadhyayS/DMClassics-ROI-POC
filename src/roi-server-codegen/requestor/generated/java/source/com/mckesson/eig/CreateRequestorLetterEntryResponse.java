
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
 *         &lt;element name="requestorLetterHistory" type="{urn:eig.mckesson.com}RequestorLetterHistory"/>
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
    "requestorLetterHistory"
})
@XmlRootElement(name = "createRequestorLetterEntryResponse")
public class CreateRequestorLetterEntryResponse {

    @XmlElement(required = true)
    protected RequestorLetterHistory requestorLetterHistory;

    /**
     * Gets the value of the requestorLetterHistory property.
     * 
     * @return
     *     possible object is
     *     {@link RequestorLetterHistory }
     *     
     */
    public RequestorLetterHistory getRequestorLetterHistory() {
        return requestorLetterHistory;
    }

    /**
     * Sets the value of the requestorLetterHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestorLetterHistory }
     *     
     */
    public void setRequestorLetterHistory(RequestorLetterHistory value) {
        this.requestorLetterHistory = value;
    }

}
