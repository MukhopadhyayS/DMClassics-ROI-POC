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
public class RequestCoreChargesDocument extends BaseModel
        implements
            Serializable {

    private static final long serialVersionUID = 1L;
    private long _requestCoreChargesSeq;
    private double _amount;
    private int _copies;
    private String _billingTierName;
    private int _totalPages;
    private int _pages;
    private String _billingtierId;
    private int _releaseCount;
    private boolean _isElectronic;
    private boolean _removeBaseCharge;
    private boolean _hasSalesTax;
    private double _salesTaxAmount;
    
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
    public void setRequestCoreChargesSeq(long _requestCoreChargesSeq) {
        this._requestCoreChargesSeq = _requestCoreChargesSeq;
    }
    
    public double getAmount() {
        return _amount;
    }
    public void setAmount(double amount) {
        _amount = amount;
    }
    public String getBillingtierId() {
        return _billingtierId;
    }
    public void setBillingtierId(String billingtierId) {
        _billingtierId = billingtierId;
    }
    public int getCopies() {
        return _copies;
    }
    public void setCopies(int _copies) {
        this._copies = _copies;
    }
    public String getBillingTierName() {
        return _billingTierName;
    }
    public void setBillingTierName(String _billingTierName) {
        this._billingTierName = _billingTierName;
    }
    public int getTotalPages() {
        return _totalPages;
    }
    public void setTotalPages(int _totalPages) {
        this._totalPages = _totalPages;
    }
    public int getPages() {
        return _pages;
    }
    public void setPages(int _pages) {
        this._pages = _pages;
    }
    
    public int getReleaseCount() {
        return _releaseCount;
    }
    public void setReleaseCount(int _releaseCount) {
        this._releaseCount = _releaseCount;
    }
    public boolean isIsElectronic() {
        return _isElectronic;
    }
    public void setIsElectronic(boolean _isElectronic) {
        this._isElectronic = _isElectronic;
    }
    public boolean isRemoveBaseCharge() {
        return _removeBaseCharge;
    }
    public void setRemoveBaseCharge(boolean _removeBaseCharge) {
        this._removeBaseCharge = _removeBaseCharge;
    }

}
