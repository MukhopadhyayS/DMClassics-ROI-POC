package com.mckesson.eig.roi.requestor.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestorHistoryList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorHistoryList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestorHistory" type="{urn:eig.mckesson.com}RequestorHistory" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorHistoryList", propOrder = {
    "_requestorHistory"
})

public class RequestorHistoryList {
    
    public RequestorHistoryList() { };
    public RequestorHistoryList(List<RequestorHistory> list) { setRequestorHistory(list); };

    @XmlElement(name="requestorHistory")
    private List<RequestorHistory> _requestorHistory;

    public List<RequestorHistory> getRequestorHistory() { return _requestorHistory; }
    public void setRequestorHistory(List<RequestorHistory> list) { _requestorHistory = list; }

}


