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
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PastInvoiceList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PastInvoiceList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PastInvoices" type="{urn:eig.mckesson.com}PastInvoice" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PastInvoiceList", propOrder = {
    "_pastInvoices"
})
public class PastInvoiceList
implements Serializable {
    
    private static final long serialVersionUID = -4893414889567374220L;
    
    @XmlElement(name = "PastInvoices")
    private List<PastInvoice> _pastInvoices;

    
    public PastInvoiceList() { };
    
    public PastInvoiceList(List<PastInvoice> list) {
        setPastInvoices(list);
    }

    public List<PastInvoice> getPastInvoices() { return _pastInvoices; }
    public void setPastInvoices(List<PastInvoice> pastInvoices) {
        _pastInvoices = pastInvoices;
    }

}
