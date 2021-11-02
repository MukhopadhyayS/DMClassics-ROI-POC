package com.mckesson.eig.roi.request.model;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestCoreChargesBilling complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestCoreChargesBilling">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestCoreChargesFee" type="{urn:eig.mckesson.com}RequestCoreChargesFee" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="requestCoreChargesDocument" type="{urn:eig.mckesson.com}RequestCoreChargesDocument" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestCoreChargesBilling", propOrder = {
    "_requestCoreChargesFee",
    "_requestCoreChargesDocument"
})
public class RequestCoreChargesBilling {
    
    @XmlElement(name="requestCoreChargesFee")
    private Set<RequestCoreChargesFee> _requestCoreChargesFee;
    
    @XmlElement(name="requestCoreChargesDocument")
    private Set<RequestCoreChargesDocument> _requestCoreChargesDocument;
    
    
    public Set<RequestCoreChargesFee> getRequestCoreChargesFee() {
        return _requestCoreChargesFee;
    }
    public void setRequestCoreChargesFee(Set<RequestCoreChargesFee> requestCoreChargesFee) {
        _requestCoreChargesFee = requestCoreChargesFee;
    }
    public Set<RequestCoreChargesDocument> getRequestCoreChargesDocument() {
        return _requestCoreChargesDocument;
    }
    public void setRequestCoreChargesDocument(Set<RequestCoreChargesDocument> requestCoreChargesDocument) {
        _requestCoreChargesDocument = requestCoreChargesDocument;
    }
   
}
