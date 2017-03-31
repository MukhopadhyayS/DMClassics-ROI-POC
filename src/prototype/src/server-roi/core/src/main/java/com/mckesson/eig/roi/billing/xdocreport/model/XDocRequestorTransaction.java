/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2014 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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

import com.mckesson.eig.roi.billing.letter.model.RequestorTransaction;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 * @author OFS
 * @date   Mar 17, 2014
 * @since  HPF 16.2 [ROI]; Mar 17, 2014
 */
public class XDocRequestorTransaction {

    private String _date = StringUtilities.EMPTYSTRING;
    private String _type = StringUtilities.EMPTYSTRING;
    private String _description = StringUtilities.EMPTYSTRING;
    private String _charges = StringUtilities.EMPTYSTRING;
    private String _balances = StringUtilities.EMPTYSTRING;
    private String _adjustment = StringUtilities.EMPTYSTRING;
    private String _payment = StringUtilities.EMPTYSTRING;
    private String _refund = StringUtilities.EMPTYSTRING;
    private String _adjPay = StringUtilities.EMPTYSTRING;
    private String _invoiceAmount = StringUtilities.EMPTYSTRING;
    private String _unBillable = StringUtilities.EMPTYSTRING;
    
    public XDocRequestorTransaction(RequestorTransaction requestorTxn) {
        
        if (requestorTxn == null) {
            return;
        }
            
        setAdjPay(StringUtilities.safe(requestorTxn.getAdjPay()));
        setAdjustment(StringUtilities.safe(requestorTxn.getAdjustment()));
        setBalances(StringUtilities.safe(requestorTxn.getBalances()));
        setCharges(StringUtilities.safe(requestorTxn.getCharges()));
        setDate(StringUtilities.safe(requestorTxn.getDate()));
        setDescription(StringUtilities.safe(requestorTxn.getDescription()));
        setAmount(StringUtilities.safe(requestorTxn.getAmount()));
        setPayment(StringUtilities.safe(requestorTxn.getPayment()));
        setRefund(StringUtilities.safe(requestorTxn.getRefund()));
        setType(StringUtilities.safe(requestorTxn.getType()));
        setUnBillable(StringUtilities.safe(requestorTxn.getUnBillable()));            
    }

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
