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

package com.mckesson.eig.roi.billing.letter.model;



/**
 * @author OFS
 * @date   Feb 19, 2009
 * @since  HPF 13.1 [ROI]; Feb 19, 2009
 */
public class ChargeItem {

    private String _name;
    private String _amount;
    private String _type;
    private String _date;
    private String _description;
    private String _pages;
    private String _copies;
    private String _paymentMode;
    private String _transactionType;
    private String _debt;
    
    //Document Charge
    private String _billingTierName;
    private String _billingTierId;

    // Fee Charge
    private String _feeType;

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
