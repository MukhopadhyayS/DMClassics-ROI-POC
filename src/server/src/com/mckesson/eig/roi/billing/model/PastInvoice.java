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
 * <p>Java class for PastInvoice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PastInvoice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="invoiceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createdDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PastInvoice", propOrder = {
    "_invoiceId",
    "_createdDate",
    "_amount"
})
public class PastInvoice
implements Serializable {

    private static final long serialVersionUID = 7442357003367239848L;

    @XmlElement(name="invoiceId")
    private long _invoiceId;
    
    @XmlElement(name="createdDate", required = true)
    private String _createdDate;
    
    @XmlElement(name="amount")
    private Double _amount;
    
    

    public long getInvoiceId() { return _invoiceId; }
    public void setInvoiceId(long invoiceId) { _invoiceId = invoiceId; }

    public String getCreatedDate() { return _createdDate; }
    public void setCreatedDate(String createdDate) { _createdDate = createdDate; }

    public Double getAmount() { return _amount; }
    public void setAmount(Double amount) { _amount = amount; }

}
