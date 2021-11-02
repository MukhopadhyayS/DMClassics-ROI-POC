
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
 *         &lt;element name="requestorHistoryList" type="{urn:eig.mckesson.com}RequestorHistoryList"/>
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
    "requestorHistoryList"
})
@XmlRootElement(name = "retrieveRequestorSummariesResponse")
public class RetrieveRequestorSummariesResponse {

    @XmlElement(required = true)
    protected RequestorHistoryList requestorHistoryList;

    /**
     * Gets the value of the requestorHistoryList property.
     * 
     * @return
     *     possible object is
     *     {@link RequestorHistoryList }
     *     
     */
    public RequestorHistoryList getRequestorHistoryList() {
        return requestorHistoryList;
    }

    /**
     * Sets the value of the requestorHistoryList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestorHistoryList }
     *     
     */
    public void setRequestorHistoryList(RequestorHistoryList value) {
        this.requestorHistoryList = value;
    }

}
