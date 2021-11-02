
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
 *         &lt;element name="requestorSearchResult" type="{urn:eig.mckesson.com}RequestorSearchResult"/>
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
    "requestorSearchResult"
})
@XmlRootElement(name = "searchMatchingRequestorsResponse")
public class SearchMatchingRequestorsResponse {

    @XmlElement(required = true)
    protected RequestorSearchResult requestorSearchResult;

    /**
     * Gets the value of the requestorSearchResult property.
     * 
     * @return
     *     possible object is
     *     {@link RequestorSearchResult }
     *     
     */
    public RequestorSearchResult getRequestorSearchResult() {
        return requestorSearchResult;
    }

    /**
     * Sets the value of the requestorSearchResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestorSearchResult }
     *     
     */
    public void setRequestorSearchResult(RequestorSearchResult value) {
        this.requestorSearchResult = value;
    }

}
