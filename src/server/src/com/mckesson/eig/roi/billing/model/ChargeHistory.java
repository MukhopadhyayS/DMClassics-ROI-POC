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

/**
 * @author OFS
 * @date Sep 24, 2012
 * @since Sep 24, 2012
 *
 */
public class ChargeHistory
extends BaseModel {

    private static final long serialVersionUID = 1L;

    private double _totalFeeCharge;
    private double _totalDocumentCharge;
    private double _totalShippingCharge;
    private double _totalSalesTax;
    private Date _releaseDate;
    private boolean _unbillable;
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
