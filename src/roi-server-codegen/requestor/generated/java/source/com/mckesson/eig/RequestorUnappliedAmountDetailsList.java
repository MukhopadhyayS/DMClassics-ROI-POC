
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestorUnappliedAmountDetailsList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorUnappliedAmountDetailsList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestorUnappliedAmountDetails" type="{urn:eig.mckesson.com}RequestorUnappliedAmountDetails" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorUnappliedAmountDetailsList", propOrder = {
    "requestorUnappliedAmountDetails"
})
public class RequestorUnappliedAmountDetailsList {

    protected List<RequestorUnappliedAmountDetails> requestorUnappliedAmountDetails;

    /**
     * Gets the value of the requestorUnappliedAmountDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestorUnappliedAmountDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestorUnappliedAmountDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestorUnappliedAmountDetails }
     * 
     * 
     */
    public List<RequestorUnappliedAmountDetails> getRequestorUnappliedAmountDetails() {
        if (requestorUnappliedAmountDetails == null) {
            requestorUnappliedAmountDetails = new ArrayList<RequestorUnappliedAmountDetails>();
        }
        return this.requestorUnappliedAmountDetails;
    }

}
