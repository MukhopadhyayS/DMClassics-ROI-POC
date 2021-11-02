package com.mckesson.eig.roi.requestor.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
    "_requestorUnappliedAmountDetails"
})

public class RequestorUnappliedAmountDetailsList {
    
    @XmlElement(name= "requestorUnappliedAmountDetails")
    private List<RequestorUnappliedAmountDetails> _requestorUnappliedAmountDetails;

    public List<RequestorUnappliedAmountDetails> getRequestorUnappliedAmountDetails() {
        return _requestorUnappliedAmountDetails;
    }
    public void setRequestorUnappliedAmountDetails(
            List<RequestorUnappliedAmountDetails> reqUnappliedAmountList) {
        this._requestorUnappliedAmountDetails = reqUnappliedAmountList;
    }

}
