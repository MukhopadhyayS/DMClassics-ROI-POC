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

package com.mckesson.eig.roi.requestor.model;

import java.io.Serializable;

/**
 * @author Karthik Easwaran
 * @date   Dec 17, 2012
 * @since  Dec 17, 2012
 */
public class RequestorAccount
implements Serializable {

    private static final long serialVersionUID = 1L;

    private double _charge;
    private double _paymentAmount;
    private double _creditAdjustmentAmount;
    private double _debitAdjustmentAmount;
    private double _adjustmentAmount;
    private double _balances;
    private double _unAppliedPayment;
    private double _unAppliedAdjustment;
    private double _unAppliedAmount;

    public double getCharge() { return _charge; }
    public void setCharge(double baseCharge) { _charge = baseCharge; }

    public double getPaymentAmount() { return _paymentAmount; }
    public void setPaymentAmount(double paymentAmount) { _paymentAmount = paymentAmount; }

    public double getCreditAdjustmentAmount() { return _creditAdjustmentAmount; }
    public void setCreditAdjustmentAmount(double creditAdjustmentAmount) {
        _creditAdjustmentAmount = creditAdjustmentAmount;
    }

    public double getDebitAdjustmentAmount() { return _debitAdjustmentAmount; }
    public void setDebitAdjustmentAmount(double debitAdjustmentAmount) {
        _debitAdjustmentAmount = debitAdjustmentAmount;
    }

    public void setAdjustmentAmount(double adjAmount) { _adjustmentAmount = adjAmount; }
    public double getAdjustmentAmount() {

        _adjustmentAmount = getCreditAdjustmentAmount() + getDebitAdjustmentAmount();
        return _adjustmentAmount;
    }

    public double getBalances() { return _balances; }
    public void setBalances(double balances) { _balances = balances; }

    public double getUnAppliedPayment() { return _unAppliedPayment; }
    public void setUnAppliedPayment(double unAppliedPayment) {
        _unAppliedPayment = unAppliedPayment;
    }

    public double getUnAppliedAdjustment() { return _unAppliedAdjustment; }
    public void setUnAppliedAdjustment(double unAppliedAdjustment) {
        _unAppliedAdjustment = unAppliedAdjustment;
    }

    public void setUnAppliedAmount(double unAppliedAmount) { _unAppliedAmount = unAppliedAmount; }
    public double getUnAppliedAmount() {

        _unAppliedAmount = getUnAppliedAdjustment() + getUnAppliedPayment();
        return _unAppliedAmount;
    }

}
