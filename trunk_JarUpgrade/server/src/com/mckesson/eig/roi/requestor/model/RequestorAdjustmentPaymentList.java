/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2013 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.requestor.model;

import java.util.List;

import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.utility.util.CollectionUtilities;

/**
 * @author Karthik Easwaran
 * @date   Jan 4, 2013
 * @since  Jan 4, 2013
 */
public class RequestorAdjustmentPaymentList
extends BaseModel {

    private static final long serialVersionUID = 1L;

    private List<RequestorAdjustment> _adjustments;
    private List<RequestorPayment> _payments;
    private double _adjustmentAmount;
    private double _paymentAmount;
    private double _amount;


    public List<RequestorAdjustment> getAdjustments() { return _adjustments; }
    public void setAdjustments(List<RequestorAdjustment> adjustments) {
        _adjustments = adjustments;
    }

    public List<RequestorPayment> getPayments() { return _payments; }
    public void setPayments(List<RequestorPayment> payments) { _payments = payments; }

    public double getAmount() { return _amount; }
    public void setAmount(double amount) { _amount = amount; }

    public double getAdjustmentAmount() { return _adjustmentAmount; }
    public void setAdjustmentAmount(double adjustmentAmount) {
        _adjustmentAmount = adjustmentAmount;
    }

    public double getPaymentAmount() { return _paymentAmount; }
    public void setPaymentAmount(double paymentAmount) { _paymentAmount = paymentAmount; }

    @Override
    public String toString() {
        return new StringBuffer()
                        .append("Size of Adjustments List: ")
                        .append(CollectionUtilities.size(_adjustments))
                        .append(", Size of Payments List: ")
                        .append(CollectionUtilities.size(_payments))
                        .append(".")
                        .toString();
    }
}
