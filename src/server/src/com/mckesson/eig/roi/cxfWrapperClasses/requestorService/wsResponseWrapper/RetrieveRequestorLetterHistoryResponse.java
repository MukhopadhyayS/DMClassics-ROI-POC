
package com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.billing.model.RequestorLetterHistoryList;


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
 *         &lt;element name="requestorLetterHistoryList" type="{urn:eig.mckesson.com}RequestorLetterHistoryList"/>
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
    "requestorLetterHistoryList"
})
@XmlRootElement(name = "retrieveRequestorLetterHistoryResponse")
public class RetrieveRequestorLetterHistoryResponse {

    @XmlElement(required = true)
    protected RequestorLetterHistoryList requestorLetterHistoryList;

    /**
     * Gets the value of the requestorLetterHistoryList property.
     * 
     * @return
     *     possible object is
     *     {@link RequestorLetterHistoryList }
     *     
     */
    public RequestorLetterHistoryList getRequestorLetterHistoryList() {
        return requestorLetterHistoryList;
    }

    /**
     * Sets the value of the requestorLetterHistoryList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestorLetterHistoryList }
     *     
     */
    public void setRequestorLetterHistoryList(RequestorLetterHistoryList value) {
        this.requestorLetterHistoryList = value;
    }

}
