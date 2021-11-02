package com.mckesson.eig.roi.requestor.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mckesson.eig.roi.admin.model.ReasonsList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdjustmentInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdjustmentInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reasonsList" type="{urn:eig.mckesson.com}ReasonsList"/>
 *         &lt;element name="requestorAdjustment" type="{urn:eig.mckesson.com}RequestorAdjustment"/>
 *         &lt;element name="requestorInvoicesList" type="{urn:eig.mckesson.com}RequestorInvoicesList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdjustmentInfo", propOrder = {
    "_reasonsList",
    "_requestorAdjustment",
    "_requestorInvoicesList"
})

public class AdjustmentInfo {

    @XmlElement(name="reasonsList")
    private ReasonsList _reasonsList;
    
    @XmlElement(name="requestorAdjustment")
    private RequestorAdjustment _requestorAdjustment;
    
    @XmlElement(name="requestorInvoicesList")
    private RequestorInvoicesList _requestorInvoicesList;
    
    @XmlTransient
    private List<AdjustmentType> _adjustmentTypes;

    public ReasonsList getReasonsList() { return _reasonsList; }
    public void setReasonsList(ReasonsList reasonsList) { _reasonsList = reasonsList; }

    public RequestorAdjustment getRequestorAdjustment() { return _requestorAdjustment; }
    public void setRequestorAdjustment(RequestorAdjustment requestorAdjustment) {
        _requestorAdjustment = requestorAdjustment;
    }

    public RequestorInvoicesList getRequestorInvoicesList() { return _requestorInvoicesList; }
    public void setRequestorInvoicesList(RequestorInvoicesList requestorInvoicesList) {
        _requestorInvoicesList = requestorInvoicesList;
    }

    public List<String> getAdjustmentTypes() {

        List<AdjustmentType> asList = Arrays.asList(AdjustmentType.values());
        List<String> typeAsString = new ArrayList<String>();
        for (AdjustmentType type : asList) {
            typeAsString.add(type.toString());
        }
        return typeAsString;
    }
    public void setAdjustmentTypes(List<AdjustmentType> adjustmentTypes) {
        _adjustmentTypes = adjustmentTypes;
    }

}
