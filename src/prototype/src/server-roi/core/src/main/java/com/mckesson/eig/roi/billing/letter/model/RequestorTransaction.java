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
public class RequestorTransaction
implements Serializable {

    private static final long serialVersionUID = 1L;

    private String _date;
    private String _type;
    private String _description;
    private String _charges;
    private String _balances;
    private String _adjustment;
    private String _payment;
    private String _refund;
    private String _adjPay;
    private String _invoiceAmount;
    private String _unBillable;

    public String getDate() { return _date; }
    public void setDate(String date) { _date = date; }

    public String getType() { return _type; }
    public void setType(String type) { _type = type; }

    public String getDescription() { return _description; }
    public void setDescription(String description) { _description = description; }

    public String getCharges() { return _charges; }
    public void setCharges(String charges) { _charges = charges; }

    public String getBalances() { return _balances; }
    public void setBalances(String balances) { _balances = balances; }

    public void setAdjustment(String adjustment) { _adjustment = adjustment; }
    public String getAdjustment() { return _adjustment; }

    public void setPayment(String payment) { _payment = payment; }
    public String getPayment() { return _payment; }

    public void setAdjPay(String adjPay) { _adjPay = adjPay; }
    public String getAdjPay() { return _adjPay; }

    public void setAmount(String invoiceAmount) { _invoiceAmount = invoiceAmount; }
    public String getAmount() { return _invoiceAmount; }

    /**
     * CR # 375,537 Fix
     */
    public String getRefund() { return _refund; }
    public void setRefund(String refund) { _refund = refund; }

    // DE1560/CR# 384,396 - Fix
    public String getUnBillable() { return _unBillable; }
    public void setUnBillable(String unBillable) { _unBillable = unBillable; }

}
