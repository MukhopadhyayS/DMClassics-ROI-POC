/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.request.model;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestCoreChargesInvoicesList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestCoreChargesInvoicesList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestCoreChargesInvoice" type="{urn:eig.mckesson.com}RequestCoreChargesInvoice" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="isInvoiced" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestCoreChargesInvoicesList", propOrder = {
    "_invoicesList",
    "_isInvoiced"
})
public class RequestCoreChargesInvoicesList
implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement(name="requestCoreChargesInvoice")
    private List<RequestCoreChargesInvoice> _invoicesList;
    
    @XmlElement(name="isInvoiced")
    private boolean _isInvoiced;

    public RequestCoreChargesInvoicesList() { }
    public RequestCoreChargesInvoicesList(List<RequestCoreChargesInvoice> invoicesList) {
        _invoicesList = invoicesList;
    }

    public List<RequestCoreChargesInvoice> getInvoicesList() { return _invoicesList; }
    public void setInvoicesList(List<RequestCoreChargesInvoice> invoicesList) {
        _invoicesList = invoicesList;
    }

    public boolean getIsInvoiced() { return _isInvoiced; }
    public void setIsInvoiced(boolean isInvoiced) { _isInvoiced = isInvoiced; }

}
