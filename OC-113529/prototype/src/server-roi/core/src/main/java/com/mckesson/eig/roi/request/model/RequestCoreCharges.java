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

import java.util.Date;

import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.billing.model.SalesTaxSummary;
/**
 * @author Keane
 * @date July 24, 2012
 */
public class RequestCoreCharges extends BaseModel {

    private static final long serialVersionUID = 1L;

    private long _requestCoreSeq;
    private double _previouslyReleasedCost;
    private Date _releaseDate;
    private double _releaseCost;
    private int _totalPages;
    private double _totalRequestCost;
    private double _balanceDue;
    private int _totalPagesReleased;
    private double _salesTaxAmount;
    private String _billingType;
    private double _salesTaxPercentage;

    private double _originalBalance;
    private double _paymentAmount;
    private double _creditAdjustmentAmount;
    private double _debitAdjustmentAmount;
    private String _billingLocCode;
    private String _billingLocName;
    private boolean _applySalesTax;
    private boolean _displayBillingPaymentInfo;
    private boolean _released;
    private boolean _hasInvoices;
    private double _invoiceBaseCharge;
    private double _invoiceAutoAdjustment;

    //ROI Financial Enhancement Changes
    private boolean _hasUnReleasedInvoices;
    private double _invoicesBalance;
    private double _invoicesSalesTaxAmount;

    private double _totalUnappliedPaymentAmount;
    private double _totalUnappliedAdjustmentAmount;
    private double _totalUnappliedAmount;

    private boolean _unbillable;

    private RequestCoreChargesShipping _requestCoreChargesShipping;
    private RequestCoreChargesBilling _requestCoreChargesBilling;
    private SalesTaxSummary _salesTaxSummary;

    public double getOriginalBalance() { return _originalBalance; }
    public void setOriginalBalance(double originalBalance) { _originalBalance = originalBalance; }

    public double getPaymentAmount() { return _paymentAmount; }
    public void setPaymentAmount(double paymentAmount) { _paymentAmount = paymentAmount; }

    public double getCreditAdjustmentAmount() { return _creditAdjustmentAmount; }
    public void setCreditAdjustmentAmount(double creditAdjustmentAmount) {
        _creditAdjustmentAmount = creditAdjustmentAmount;
    }

    public double getDebitAdjustmentAmount() { return _debitAdjustmentAmount; }
    public void setDebitAdjustmentAmount(double debitAdjustmentAmount) {
        _debitAdjustmentAmount = debitAdjustmentAmount;
    }

    public String getBillingLocCode() { return _billingLocCode; }
    public void setBillingLocCode(String billingLocCode) { _billingLocCode = billingLocCode; }

    public String getBillingLocName() { return _billingLocName; }
    public void setBillingLocName(String billingLocName) { _billingLocName = billingLocName; }

    public boolean getApplySalesTax() { return _applySalesTax; }
    public void setApplySalesTax(boolean applySalesTax) { _applySalesTax = applySalesTax; }

    public String getBillingType() { return _billingType; }
    public void setBillingType(String billingType) { _billingType = billingType; }

    public double getPreviouslyReleasedCost() { return _previouslyReleasedCost; }
    public void setPreviouslyReleasedCost(double previouslyReleasedCost) {
        _previouslyReleasedCost = previouslyReleasedCost;
    }

    public Date getReleaseDate() { return _releaseDate; }
    public void setReleaseDate(Date releaseDate) { _releaseDate = releaseDate; }

    public double getReleaseCost() { return _releaseCost; }
    public void setReleaseCost(double releaseCost) { _releaseCost = releaseCost; }

    public int getTotalPages() { return _totalPages; }
    public void setTotalPages(int totalPages) { _totalPages = totalPages; }

    public double getTotalRequestCost() { return _previouslyReleasedCost + _releaseCost; }
    public void setTotalRequestCost(double totalRequestCost) {
        _totalRequestCost = totalRequestCost;
    }

    public double getBalanceDue() { return _balanceDue; }
    public void setBalanceDue(double balanceDue) { _balanceDue = balanceDue; }

    public int getTotalPagesReleased() { return _totalPagesReleased; }
    public void setTotalPagesReleased(int totalPagesReleased) {
        _totalPagesReleased = totalPagesReleased;
    }

    public double getSalesTaxAmount() { return _salesTaxAmount; }
    public void setSalesTaxAmount(double salesTaxAmount) { _salesTaxAmount = salesTaxAmount; }

    public double getSalesTaxPercentage() { return _salesTaxPercentage; }
    public void setSalesTaxPercentage(double salestaxPercentage) {
        _salesTaxPercentage = salestaxPercentage;
    }

    public long getRequestCoreSeq() { return _requestCoreSeq; }
    public void setRequestCoreSeq(long requestCoreSeq) { _requestCoreSeq = requestCoreSeq; }

    public RequestCoreChargesShipping getRequestCoreChargesShipping() {
        return _requestCoreChargesShipping;
    }
    public void setRequestCoreChargesShipping(RequestCoreChargesShipping reqCoreChargesShipping) {
        _requestCoreChargesShipping = reqCoreChargesShipping;
    }

    public RequestCoreChargesBilling getRequestCoreChargesBilling() {
        return _requestCoreChargesBilling;
    }
    public void setRequestCoreChargesBilling(RequestCoreChargesBilling requestCoreChargesBilling) {
        _requestCoreChargesBilling = requestCoreChargesBilling;
    }

    public boolean isDisplayBillingPaymentInfo() { return _displayBillingPaymentInfo; }
    public void setDisplayBillingPaymentInfo(boolean displayBillingPaymentInfo) {

        _displayBillingPaymentInfo = displayBillingPaymentInfo;
        setReleased(!displayBillingPaymentInfo);
    }

    public boolean isReleased() { return _released; }
    public void setReleased(boolean released) { _released = released; }

    public boolean isHasInvoices() { return _hasInvoices; }
    public void setHasInvoices(boolean hasInvoices) { _hasInvoices = hasInvoices; }

    public double getInvoiceBaseCharge() { return _invoiceBaseCharge; }
    public void setInvoiceBaseCharge(double invoiceBaseCharge) {
        _invoiceBaseCharge = invoiceBaseCharge;
    }

    public void setInvoiceAutoAdjustment(double invoiceAutoAdjustment) {
        _invoiceAutoAdjustment = invoiceAutoAdjustment;
    }
    public double getInvoiceAutoAdjustment() { return _invoiceAutoAdjustment; }

    public boolean isHasUnReleasedInvoices() { return _hasUnReleasedInvoices; }
    public void setHasUnReleasedInvoices(boolean hasUnReleasedInvoices) {
        _hasUnReleasedInvoices = hasUnReleasedInvoices;
    }

    public double getInvoicesBalance() { return _invoicesBalance; }
    public void setInvoicesBalance(double invoicesBalance) { _invoicesBalance = invoicesBalance; }

    public SalesTaxSummary getSalesTaxSummary() { return _salesTaxSummary; }
    public void setSalesTaxSummary(SalesTaxSummary salesTaxSummary) {
        _salesTaxSummary = salesTaxSummary;
    }

    public double getInvoicesSalesTaxAmount() { return _invoicesSalesTaxAmount; }
    public void setInvoicesSalesTaxAmount(double invoicesSalesTaxAmount) {
        _invoicesSalesTaxAmount = invoicesSalesTaxAmount;
    }

    public double getUnbillableAmount() {
        if (_unbillable) {
            return _releaseCost;
        }
        return 0;
    }

    public boolean getUnbillable() { return _unbillable; }
    public void setUnbillable(boolean unbillable) { _unbillable = unbillable; }

    public double getTotalUnappliedPaymentAmount() { return _totalUnappliedPaymentAmount; }
    public void setTotalUnappliedPaymentAmount(double totalUnappliedPaymentAmount) {
        _totalUnappliedPaymentAmount =  totalUnappliedPaymentAmount;
    }

    public double getTotalUnappliedAdjustmentAmount() { return _totalUnappliedAdjustmentAmount; }
    public void setTotalUnappliedAdjustmentAmount(double totalUnappliedAdjustmentAmount) {
        _totalUnappliedAdjustmentAmount = totalUnappliedAdjustmentAmount;
    }

    public double getTotalUnappliedAmount() {
        return getTotalUnappliedAdjustmentAmount() + getTotalUnappliedPaymentAmount();
    }
    public void setTotalUnappliedAmount(double totalUnappliedAmount) {
        _totalUnappliedAmount = totalUnappliedAmount;
    }


}
