/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2011 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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

import java.util.Arrays;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SearchPastDueInvoiceCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchPastDueInvoiceCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="billingLocations" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="requestorTypes" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="requestorTypeNames" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="requestorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="overDueFrom" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="overDueTo" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="overDueRestriction" type="{urn:eig.mckesson.com}OverDueRestriction"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchPastDueInvoiceCriteria", propOrder = {
    "_billingLocations",
    "_requestorTypes",
    "_requestorTypeNames",
    "_requestorName",
    "_overDueFrom",
    "_overDueTo",
    "_overdueRestriction"
})
public class SearchPastDueInvoiceCriteria {
    
    @XmlElement(name="billingLocations", required = true)
    private String[] _billingLocations;
    
    @XmlElement(name="requestorTypes")
    private Long[] _requestorTypes;
    
    @XmlElement(name="requestorTypeNames", required = true)
    private String[] _requestorTypeNames;
    
    @XmlElement(name="requestorName")
    private String _requestorName;
    
    @XmlElement(name="overDueFrom")
    private long _overDueFrom;
    
    @XmlElement(name="overDueTo")
    private long _overDueTo;
    
    @XmlElement(name="overDueRestriction", required = true)
    @XmlSchemaType(name = "string")
    private OverDueRestriction _overdueRestriction;
    
    
    
    
    public String[] getRequestorTypeNames() {
        return _requestorTypeNames;
    }
    public void setRequestorTypeNames(String[] requestorTypeNames) {
        this._requestorTypeNames = requestorTypeNames;
    }
    
    public String[] getBillingLocations() { return _billingLocations; }
    public void setBillingLocations(String[] billingLocs) { _billingLocations = billingLocs; }
    
    public Long[] getRequestorTypes() { return _requestorTypes; }
    public void setRequestorTypes(Long[] requestortypes) { _requestorTypes = requestortypes; }
    
    public String getRequestorName() { return _requestorName; }
    public void setRequestorName(String requestorName) { _requestorName = requestorName; }
    
    public long getOverDueFrom() { return _overDueFrom; }
    public void setOverDueFrom(long overDueFrom) { _overDueFrom = overDueFrom; }
    
    public long getOverDueTo() { return _overDueTo; }
    public void setOverDueTo(long overDueTo) { _overDueTo = overDueTo; }
    
    public OverDueRestriction getOverDueRestriction() { return _overdueRestriction; }
    public void setOverDueRestriction(OverDueRestriction overdueRestriction) { 
        _overdueRestriction = overdueRestriction;
    }
    
    @Override
    public String toString() {
        return new StringBuffer()
                    .append("Billing Locations:")
                    .append(Arrays.toString(_billingLocations))
                    .append(", RequestorTypes:")
                    .append(Arrays.toString(_requestorTypes))
                    .append(", RequestoName:")
                    .append(_requestorName)
                    .append(", OverDue Days ")
                    .append(_overdueRestriction)
                    .append(_overDueFrom + " TO ")
                    .append(_overDueTo)
                    .toString();
    }

}
