package com.mckesson.eig.roi.requestor.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestorInvoicesList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorInvoicesList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestorInvoices" type="{urn:eig.mckesson.com}RequestorInvoice" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorInvoicesList", propOrder = {
    "_requestorInvoices"
})
public class RequestorInvoicesList {

    @XmlElement(name="requestorInvoices")
    private List<RequestorInvoice> _requestorInvoices;
    

    public RequestorInvoicesList() { }
    public RequestorInvoicesList(List<RequestorInvoice> requestorInvoices) {
        setRequestorInvoices(requestorInvoices);
    }

    public List<RequestorInvoice> getRequestorInvoices() { return _requestorInvoices; }
    public void setRequestorInvoices(List<RequestorInvoice> requestorInvoices) {
        _requestorInvoices = requestorInvoices;
    }


}
