/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PastDueInvoice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PastDueInvoice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="billingLocation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="overdueAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="requestorName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="phoneNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="overDueDays" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="invoiceAge" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PastDueInvoice", propOrder = {
    "_billingLocation",
    "_invoiceNumber",
    "_requestId",
    "_overdueAmount",
    "_requestorName",
    "_phoneNumber",
    "_requestorType",
    "_requestorId",
    "_overDueDays",
    "_invoiceAge"
})
public class PastDueInvoice
implements Serializable {

    private static final long serialVersionUID = -2409127221006485571L;

    
    @XmlElement(name="billingLocation", required = true)
    private String _billingLocation;
    
    @XmlElement(name="invoiceNumber")
    private long _invoiceNumber;
    
    @XmlElement(name="requestId")
    private long _requestId;
    
    @XmlElement(name="overdueAmount")
    private double _overdueAmount;
    
    @XmlElement(name="requestorName", required = true)
    private String _requestorName;
    
    @XmlElement(name="phoneNumber", required = true)
    private String _phoneNumber;
    
    @XmlElement(name="requestorType", required = true)
    private String _requestorType;
    
    @XmlElement(name="requestorId")
    private long _requestorId;
    
    @XmlElement(name="overDueDays")
    private long _overDueDays;
    
    @XmlElement(name="invoiceAge")
    private long _invoiceAge;

    
    
    public String getBillingLocation() { return _billingLocation; }
    public void setBillingLocation(String billingLocation) { _billingLocation = billingLocation; }

    public long getInvoiceNumber() { return _invoiceNumber; }
    public void setInvoiceNumber(long invoiceNumber) { _invoiceNumber = invoiceNumber; }

    public long getRequestId() { return _requestId; }
    public void setRequestId(long requestId) { _requestId = requestId; }

    public double getOverdueAmount() { return _overdueAmount; }
    public void setOverdueAmount(double overdueAmount) { _overdueAmount = overdueAmount; }

    public String getRequestorName() { return _requestorName; }
    public void setRequestorName(String requestorName) { _requestorName = requestorName; }

    public String getPhoneNumber() { return _phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { _phoneNumber = phoneNumber; }

    public String getRequestorType() { return _requestorType; }
    public void setRequestorType(String requestorType) { _requestorType = requestorType; }

    public long getRequestorId() { return _requestorId; }
    public void setRequestorId(long requestorId) { _requestorId = requestorId; }

    public long getOverDueDays() { return _overDueDays; }
    public void setOverDueDays(long exceededDueDays) { _overDueDays = exceededDueDays; }

    public long getInvoiceAge() { return _invoiceAge; }
    public void setInvoiceAge(long invoiceAge) { _invoiceAge = invoiceAge; }
}
