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
import java.util.Date;

/**
 * @author Karthik Easwaran
 * @date   Dec 17, 2012
 * @since  Dec 17, 2012
 */
public class RequestorTransaction
implements Comparable<RequestorTransaction>, Serializable {

    private static final long serialVersionUID = 1L;

    private Date _date;
    private long _invoiceId;
    private String _type;
    private String _description;
    private double _charges;
    private double _balances;
    private double _adjustment;
    private double _payment;
    private double _adjPay;
    private double _refund;
    private double _invoiceAmount;
    // DE1560/CR# 384,396 - Fix
    private boolean _unbillable;

    public Date getDate() { return _date; }
    public void setDate(Date date) { _date = date; }

    public String getType() { return _type; }
    public void setType(String type) { _type = type; }

    public String getDescription() { return _description; }
    public void setDescription(String description) { _description = description; }

    public double getCharges() { return _charges; }
    public void setCharges(double charges) { _charges = charges; }

    public double getBalances() { return _balances; }
    public void setBalances(double balances) { _balances = balances; }

    public double getInvoiceAmount() { return _invoiceAmount; }
    public void setInvoiceAmount(double invoiceAmount) { _invoiceAmount = invoiceAmount; }

    public void setAdjustment(double adjustment) { _adjustment = adjustment; }
    public double getAdjustment() { return _adjustment; }

    public void setPayment(double payment) { _payment = payment; }
    public double getPayment() { return _payment; }

    public void setAdjPay(double adjPay) { _adjPay = adjPay; }
    public double getAdjPay() { return getAdjustment() + getPayment(); }

    public long getInvoiceId() { return _invoiceId; }
    public void setInvoiceId(long invoiceId) { _invoiceId = invoiceId; }

    // CR# 375,537 FIX
    public double getRefund() { return _refund; }
    public void setRefund(double refund) { _refund = refund; }
    
    // DE1560/CR# 384,396 - Fix
    public boolean isUnbillable() { return _unbillable; }
    public void setUnbillable(boolean unbillable) { _unbillable = unbillable; }

    public int compareTo(RequestorTransaction o2) {

        if (o2 == null) {
            return 1;
        }

        long time1 = Long.MIN_VALUE;
        long time2 = Long.MIN_VALUE;


        Date  d1 = getDate();
        if (null != d1) {
            time1 = d1.getTime();
        }

        Date  d2 = o2.getDate();
        if (null != d2) {
            time2 = d2.getTime();
        }

        return d1.compareTo(d2);
    }
}
