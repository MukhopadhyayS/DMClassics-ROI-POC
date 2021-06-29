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

import com.mckesson.eig.roi.billing.letter.model.ChargeItem;
import com.mckesson.eig.utility.util.StringUtilities;



/**
 * @author OFS
 * @date   Mar 17, 2014
 * @since  HPF 16.2 [ROI]; Mar 17, 2014
 */
public class XDocChargeItem {

    private String _name = StringUtilities.EMPTYSTRING;
    private String _amount = StringUtilities.EMPTYSTRING;
    private String _type = StringUtilities.EMPTYSTRING;
    private String _date = StringUtilities.EMPTYSTRING;
    private String _description = StringUtilities.EMPTYSTRING;
    private String _pages = StringUtilities.EMPTYSTRING;
    private String _copies = StringUtilities.EMPTYSTRING;
    private String _paymentMode = StringUtilities.EMPTYSTRING;
    private String _transactionType = StringUtilities.EMPTYSTRING;
    private String _debt = StringUtilities.EMPTYSTRING;
    
    //Document Charge
    private String _billingTierName = StringUtilities.EMPTYSTRING;
    private String _billingTierId = StringUtilities.EMPTYSTRING;

    // Fee Charge
    private String _feeType = StringUtilities.EMPTYSTRING;
    
    public XDocChargeItem(ChargeItem chargeItem) {
        
        if (null == chargeItem) {
            return;
        }
        
        setName(StringUtilities.safe(chargeItem.getName()));
        setAmount(StringUtilities.safe(chargeItem.getAmount()));
        setType(StringUtilities.safe(chargeItem.getType()));
        setDate(StringUtilities.safe(chargeItem.getDate()));
        setDescription(StringUtilities.safe(chargeItem.getDescription()));
        setPages(StringUtilities.safe(chargeItem.getPages()));
        setCopies(StringUtilities.safe(chargeItem.getCopies()));
        setPaymentMode(StringUtilities.safe(chargeItem.getPaymentMode()));
        setTransactionType(StringUtilities.safe(chargeItem.getTransactionType()));
        setDebt(StringUtilities.safe(chargeItem.getDebt()));
    }
    

    public String getName() { return _name; }
    public void setName(String name) { _name = name; }

    public String getAmount() { return _amount; }
    public void setAmount(String amount) { _amount = amount; }

    public String getType() { return _type; }
    public void setType(String type) { _type = type; }

    public String getDate() { return _date; }
    public void setDate(String date) { _date = date; }

    public String getDescription() { return _description; }
    public void setDescription(String description) { _description = description; }

    public String getPages() { return _pages; }
    public void setPages(String pages) { _pages = pages; }

    public String getCopies() { return _copies; }
    public void setCopies(String copies) { _copies = copies; }

    public String getPaymentMode() { return _paymentMode; }
    public void setPaymentMode(String paymentMode) { _paymentMode = paymentMode; }
    
    public String getTransactionType() { return _transactionType; }
    public void setTransactionType(String transactionType) { _transactionType = transactionType; }
    
    public String getDebt() { return _debt; }
    public void setDebt(String debt) { _debt = debt; }

    public String getBillingTierName() { return _billingTierName; }
    public void setBillingTierName(String billingTierName) { _billingTierName = billingTierName; }
    
    public String getBillingTierId() { return _billingTierId; }
    public void setBillingTierId(String billingTierId) { _billingTierId = billingTierId; }
    
    public String getFeeType() { return _feeType; }
    public void setFeeType(String feeType) { _feeType = feeType; }
}
