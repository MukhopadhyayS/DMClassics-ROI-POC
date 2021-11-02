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

import java.util.Date;

import com.mckesson.eig.roi.base.model.BaseModel;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ChargeHistory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChargeHistory">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="totalFeeCharge" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalDocumentCharge" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalShippingCharge" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalSalesTax" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="releaseDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="unbillableAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChargeHistory", propOrder = {
    "_totalFeeCharge",
    "_totalDocumentCharge",
    "_totalShippingCharge",
    "_totalSalesTax",
    "_releaseDate",
    "_unbillableAmount"
})
public class ChargeHistory extends BaseModel{

    private static final long serialVersionUID = 1L;

    @XmlElement(name="totalFeeCharge")
    private double _totalFeeCharge;
    
    @XmlElement(name="totalDocumentCharge")
    private double _totalDocumentCharge;
    
    @XmlElement(name="totalShippingCharge")
    private double _totalShippingCharge;
    
    @XmlElement(name="totalSalesTax")
    private double _totalSalesTax;
    
    @XmlElement(name="releaseDate", required = true)
    private Date _releaseDate;
    
    @XmlTransient
    private boolean _unbillable;
    
    @XmlElement(name="unbillableAmount")
    private double _unbillableAmount;
    
    
    public double getTotalFeeCharge() { return _totalFeeCharge; }
    public void setTotalFeeCharge(double totalFeeCharge) { _totalFeeCharge = totalFeeCharge; }

    public double getTotalDocumentCharge() { return _totalDocumentCharge; }
    public void setTotalDocumentCharge(double totalDocumentCharge) {
        _totalDocumentCharge = totalDocumentCharge;
    }

    public double getTotalShippingCharge() { return _totalShippingCharge; }
    public void setTotalShippingCharge(double totalShippingCharge) {
        _totalShippingCharge = totalShippingCharge;
    }

    public double getTotalSalesTax() { return _totalSalesTax; }
    public void setTotalSalesTax(double totalSalesTax) {
        _totalSalesTax = totalSalesTax;
    }

    public Date getReleaseDate() { return _releaseDate; }
    public void setReleaseDate(Date releaseDate) { _releaseDate = releaseDate; }

    public boolean isUnbillable() { return _unbillable; }
    public void setUnbillable(boolean unbillable) { _unbillable = unbillable; }

    public void setUnbillableAmount(double unbillableAmount) { _unbillableAmount = unbillableAmount; }
    public double getUnbillableAmount() {

        if (_unbillable) {
            setUnbillableAmount((_totalFeeCharge + _totalDocumentCharge
                                                 + _totalSalesTax
                                                 + _totalShippingCharge));
        }
        return _unbillableAmount;
    }


    @Override
    public String toString() {
        return new StringBuffer()
                        .append("ReleaseDate:")
                        .append(_releaseDate)
                        .append(", FeeCharge:")
                        .append(_totalFeeCharge)
                        .append(", DocumentCharge:")
                        .append(_totalDocumentCharge)
                        .append(", ShippingCharge:")
                        .append(_totalShippingCharge)
                        .append(", SalesTax:")
                        .append(_totalSalesTax)
                        .toString();
    }

}
