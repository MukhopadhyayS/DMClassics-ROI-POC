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

package com.mckesson.eig.roi.requestor.model;

import java.io.Serializable;


/**
 * @author OFS
 * @date   Jan 11, 2013
 */
public class RequestorCharges
implements Serializable {

    private static final long serialVersionUID = 1L;

    private double _unAppliedPayment;
    private double _unAppliedAdjustment;
    private double _invoiceBalance;
    private double _totalPyment;
    private double _totalAdjustment;
    private double _totalUnapplied;

    public double getUnAppliedPayment() { return _unAppliedPayment; }
    public void setUnAppliedPayment(double unAppliedPayment) {
        _unAppliedPayment = unAppliedPayment;
    }

    public double getUnAppliedAdjustment() { return _unAppliedAdjustment; }
    public void setUnAppliedAdjustment(double unAppliedAdjustment) {
        _unAppliedAdjustment = unAppliedAdjustment;
    }

    public double getInvoiceBalance() { return _invoiceBalance; }
    public void setInvoiceBalance(double invoiceBalance) { _invoiceBalance = invoiceBalance; }

    public double getTotalPayment() { return _totalPyment; }
    public void setTotalPyment(double totalPyment) { _totalPyment = totalPyment; }

    public double getTotalAdjustment() { return _totalAdjustment; }
    public void setTotalAdjustment(double totalAdjustment) { _totalAdjustment = totalAdjustment; }

    public double getTotalUnapplied() {
        _totalUnapplied = getUnAppliedAdjustment() + getUnAppliedPayment();
        return _totalUnapplied;
        }
    public void setTotalUnapplied(double totalUnapplied) { _totalUnapplied = totalUnapplied; }

}
