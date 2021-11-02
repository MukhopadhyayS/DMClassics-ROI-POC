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
 * <p>Java class for SearchPastDueInvoiceResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchPastDueInvoiceResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="maxCountExceeded" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PastDueInvoiceList" type="{urn:eig.mckesson.com}PastDueInvoices"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchPastDueInvoiceResult", propOrder = {
    "_maxCountExceeded",
    "_pastDueInvoices"
})
public class SearchPastDueInvoiceResult
implements Serializable {

    private static final long serialVersionUID = 8301182555899423931L;
    
    @XmlElement(name="maxCountExceeded")
    private boolean _maxCountExceeded;
    
    @XmlElement(name = "PastDueInvoiceList", required = true)
    private List<PastDueInvoice> _pastDueInvoices;
    
    

    public List<PastDueInvoice> getPastDueInvoices() { return _pastDueInvoices; }
    public void setPastDueInvoices(List<PastDueInvoice> pastDueInvoices) {
        _pastDueInvoices = pastDueInvoices;
    }

    public boolean getMaxCountExceeded() { return _maxCountExceeded; }
    public void setMaxCountExceeded(boolean maxCountExceeded) {
        _maxCountExceeded = maxCountExceeded;
    }

    @Override
    public String toString() {

        return new StringBuffer().append(" Size of SearchPastDueInvoice List:")
                                 .append(_pastDueInvoices.size())
                                 .toString();
    }
}
