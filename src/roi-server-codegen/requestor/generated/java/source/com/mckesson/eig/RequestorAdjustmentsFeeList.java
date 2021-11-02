
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestorAdjustmentsFeeList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorAdjustmentsFeeList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestorAdjustmentsFee" type="{urn:eig.mckesson.com}RequestorAdjustmentsFee" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorAdjustmentsFeeList", propOrder = {
    "requestorAdjustmentsFee"
})
public class RequestorAdjustmentsFeeList {

    protected List<RequestorAdjustmentsFee> requestorAdjustmentsFee;

    /**
     * Gets the value of the requestorAdjustmentsFee property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestorAdjustmentsFee property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestorAdjustmentsFee().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestorAdjustmentsFee }
     * 
     * 
     */
    public List<RequestorAdjustmentsFee> getRequestorAdjustmentsFee() {
        if (requestorAdjustmentsFee == null) {
            requestorAdjustmentsFee = new ArrayList<RequestorAdjustmentsFee>();
        }
        return this.requestorAdjustmentsFee;
    }

}
