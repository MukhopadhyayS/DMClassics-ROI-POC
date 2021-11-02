
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestorLetterHistoryList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorLetterHistoryList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestorLetterHistory" type="{urn:eig.mckesson.com}RequestorLetterHistory" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorLetterHistoryList", propOrder = {
    "requestorLetterHistory"
})
public class RequestorLetterHistoryList {

    protected List<RequestorLetterHistory> requestorLetterHistory;

    /**
     * Gets the value of the requestorLetterHistory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestorLetterHistory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestorLetterHistory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestorLetterHistory }
     * 
     * 
     */
    public List<RequestorLetterHistory> getRequestorLetterHistory() {
        if (requestorLetterHistory == null) {
            requestorLetterHistory = new ArrayList<RequestorLetterHistory>();
        }
        return this.requestorLetterHistory;
    }

}
