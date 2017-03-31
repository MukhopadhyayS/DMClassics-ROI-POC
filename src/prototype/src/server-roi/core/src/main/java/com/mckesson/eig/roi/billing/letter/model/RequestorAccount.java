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

package com.mckesson.eig.roi.billing.letter.model;

import java.io.Serializable;

/**
 * @author Karthik Easwaran
 * @date   Dec 17, 2012
 * @since  Dec 17, 2012
 */
public class RequestorAccount
implements Serializable {

    private static final long serialVersionUID = 1L;

    private String _charge;
    private String _paymentAmount;
    private String _creditAdjustmentAmount;
    private String _debitAdjustmentAmount;
    private String _adjustmentAmount;
    private String _balances;
    private String _unAppliedPayment;
    private String _unAppliedAdjustment;
    private String _unAppliedAmount;

    public String getCharge() { return _charge; }
    public void setCharge(String baseCharge) { _charge = baseCharge; }

    public String getPaymentAmount() { return _paymentAmount; }
    public void setPaymentAmount(String paymentAmount) { _paymentAmount = paymentAmount; }

    public String getCreditAdjustmentAmount() { return _creditAdjustmentAmount; }
    public void setCreditAdjustmentAmount(String creditAdjustmentAmount) {
        _creditAdjustmentAmount = creditAdjustmentAmount;
    }

    public String getDebitAdjustmentAmount() { return _debitAdjustmentAmount; }
    public void setDebitAdjustmentAmount(String debitAdjustmentAmount) {
        _debitAdjustmentAmount = debitAdjustmentAmount;
    }

    public void setAdjustmentAmount(String adjAmount) { _adjustmentAmount = adjAmount; }
    public String getAdjustmentAmount() { return _adjustmentAmount; }

    public String getBalances() { return _balances; }
    public void setBalances(String balances) { _balances = balances; }

    public String getUnAppliedPayment() { return _unAppliedPayment; }
    public void setUnAppliedPayment(String unAppliedPayment) {
        _unAppliedPayment = unAppliedPayment;
    }

    public String getUnAppliedAdjustment() { return _unAppliedAdjustment; }
    public void setUnAppliedAdjustment(String unAppliedAdjustment) {
        _unAppliedAdjustment = unAppliedAdjustment;
    }

    public void setUnAppliedAmount(String unAppliedAmount) { _unAppliedAmount = unAppliedAmount; }
    public String getUnAppliedAmount() { return _unAppliedAmount; }

}
