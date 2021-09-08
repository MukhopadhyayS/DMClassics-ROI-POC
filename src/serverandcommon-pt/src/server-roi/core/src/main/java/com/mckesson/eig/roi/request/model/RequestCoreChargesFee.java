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
package com.mckesson.eig.roi.request.model;

import java.io.Serializable;

import com.mckesson.eig.roi.base.model.BaseModel;
/**
 * @author Keane
 * @date July 24, 2012
 */

public class RequestCoreChargesFee extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private long _requestCoreChargesSeq;
    private double _amount;
    private boolean _isCustomFee;
    private boolean _hasSalesTax;
    private double _salesTaxAmount;
    private String _feeType;
    
    
    public boolean getHasSalesTax() {
        return _hasSalesTax;
    }
    public void setHasSalesTax(boolean hasSalesTax) {
        _hasSalesTax = hasSalesTax;
    }
    public double getSalesTaxAmount() {
        return _salesTaxAmount;
    }
    public void setSalesTaxAmount(double salesTaxAmount) {
        _salesTaxAmount = salesTaxAmount;
    }
    public long getRequestCoreChargesSeq() {
        return _requestCoreChargesSeq;
    }
    public void setRequestCoreChargesSeq(long requestCoreChargesSeq) {
        _requestCoreChargesSeq = requestCoreChargesSeq;
    }
    public double getAmount() {
        return _amount;
    }
    public void setAmount(double amount) {
        _amount = amount;
    }
    public boolean getIsCustomFee() {
        return _isCustomFee;
    }
    public void setIsCustomFee(boolean isCustomFee) {
        _isCustomFee = isCustomFee;
    }
    public String getFeeType() {
        return _feeType;
    }
    public void setFeeType(String feeType) {
        _feeType = feeType;
    }

}
