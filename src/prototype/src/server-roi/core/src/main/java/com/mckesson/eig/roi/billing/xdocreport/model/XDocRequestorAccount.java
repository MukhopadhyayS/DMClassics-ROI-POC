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

package com.mckesson.eig.roi.billing.xdocreport.model;

import java.io.Serializable;

import com.mckesson.eig.roi.billing.letter.model.RequestorAccount;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author Karthik Easwaran
 * @date   Dec 17, 2012
 * @since  Dec 17, 2012
 */
public class XDocRequestorAccount
implements Serializable {

    private static final long serialVersionUID = 1L;

    private String _charge = StringUtilities.EMPTYSTRING;
    private String _paymentAmount = StringUtilities.EMPTYSTRING;
    private String _creditAdjustmentAmount = StringUtilities.EMPTYSTRING;
    private String _debitAdjustmentAmount = StringUtilities.EMPTYSTRING;
    private String _adjustmentAmount = StringUtilities.EMPTYSTRING;
    private String _balances = StringUtilities.EMPTYSTRING;
    private String _unAppliedPayment = StringUtilities.EMPTYSTRING;
    private String _unAppliedAdjustment = StringUtilities.EMPTYSTRING;
    private String _unAppliedAmount = StringUtilities.EMPTYSTRING;
    
    public XDocRequestorAccount(RequestorAccount account) {

        if (null == account) {
            return;
        }
        
        setCharge(StringUtilities.safe(account.getCharge()));
        setPaymentAmount(StringUtilities.safe(account.getPaymentAmount()));
        setCreditAdjustmentAmount(StringUtilities.safe(account.getCreditAdjustmentAmount()));
        setDebitAdjustmentAmount(StringUtilities.safe(account.getDebitAdjustmentAmount()));
        setAdjustmentAmount(StringUtilities.safe(account.getAdjustmentAmount()));
        setBalances(StringUtilities.safe(account.getBalances()));
        setUnAppliedAmount(StringUtilities.safe(account.getUnAppliedAmount()));
        setUnAppliedAdjustment(StringUtilities.safe(account.getUnAppliedAdjustment()));
        setUnAppliedPayment(StringUtilities.safe(account.getUnAppliedPayment()));
    }

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
