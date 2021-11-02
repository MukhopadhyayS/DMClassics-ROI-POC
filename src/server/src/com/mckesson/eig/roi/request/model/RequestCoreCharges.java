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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestCoreCharges complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestCoreCharges">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createdBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="modifiedBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createdDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="modifiedDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="requestCoreSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="previouslyReleasedCost" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="releaseDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="releaseCost" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalPages" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="totalRequestCost" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="balanceDue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalPagesReleased" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="salesTaxAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="salesTaxPercentage" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="billingType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="originalBalance" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="paymentAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="creditAdjustmentAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="debitAdjustmentAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="billingLocCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="billingLocName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="applySalesTax" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="displayBillingPaymentInfo" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="invoiceBaseCharge" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="invoiceAutoAdjustment" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="released" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hasUnReleasedInvoices" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="invoicesBalance" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="invoicesSalesTaxAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="unbillable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hasInvoices" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="totalUnappliedPaymentAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalUnappliedAdjustmentAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalUnappliedAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="salesTaxSummary" type="{urn:eig.mckesson.com}SalesTaxSummary"/>
 *         &lt;element name="requestCoreChargesBilling" type="{urn:eig.mckesson.com}RequestCoreChargesBilling"/>
 *         &lt;element name="requestCoreChargesShipping" type="{urn:eig.mckesson.com}RequestCoreChargesShipping"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestCoreCharges", propOrder = {
    "_id",
    "_createdBy",
    "_modifiedBy",
    "_createdDt",
    "_modifiedDt",
    "_recordVersion",
    "_requestCoreSeq",
    "_previouslyReleasedCost",
    "_releaseDate",
    "_releaseCost",
    "_totalPages",
    "_totalRequestCost",
    "_balanceDue",
    "_totalPagesReleased",
    "_salesTaxAmount",
    "_salesTaxPercentage",
    "_billingType",
    "_originalBalance",
    "_paymentAmount",
    "_creditAdjustmentAmount",
    "_debitAdjustmentAmount",
    "_billingLocCode",
    "_billingLocName",
    "_applySalesTax",
    "_displayBillingPaymentInfo",
    "_invoiceBaseCharge",
    "_invoiceAutoAdjustment",
    "_released",
    "_hasUnReleasedInvoices",
    "_invoicesBalance",
    "_invoicesSalesTaxAmount",
    "_unbillable",
    "_hasInvoices",
    "_totalUnappliedPaymentAmount",
    "_totalUnappliedAdjustmentAmount",
    "_totalUnappliedAmount",
    "_salesTaxSummary",
    "_requestCoreChargesBilling",
    "_requestCoreChargesShipping"
})
public class RequestCoreCharges extends BaseModel{

    private static final long serialVersionUID = 1L;

    @XmlElement(name="requestCoreSeq")
    private long _requestCoreSeq;
    
    @XmlElement(name="previouslyReleasedCost")
    private double _previouslyReleasedCost;
    
    @XmlElement(name="releaseDate", required = true, nillable = true)
    private Date _releaseDate;
    
    @XmlElement(name="releaseCost")
    private double _releaseCost;
    
    @XmlElement(name="totalPages")
    private int _totalPages;
    
    @XmlElement(name="totalRequestCost")
    private double _totalRequestCost;
    
    @XmlElement(name="balanceDue")
    private double _balanceDue;
    
    @XmlElement(name="totalPagesReleased")
    private int _totalPagesReleased;
    
    @XmlElement(name="salesTaxAmount")
    private double _salesTaxAmount;
    
    @XmlElement(name="salesTaxPercentage")
    private double _salesTaxPercentage;
    
    @XmlElement(name="billingType", required = true)
    private String _billingType;

    @XmlElement(name="originalBalance")
    private double _originalBalance;
    
    @XmlElement(name="paymentAmount")
    private double _paymentAmount;
    
    @XmlElement(name="creditAdjustmentAmount")
    private double _creditAdjustmentAmount;
    
    @XmlElement(name="debitAdjustmentAmount")
    private double _debitAdjustmentAmount;
    
    @XmlElement(name="billingLocCode", required = true)
    private String _billingLocCode;
    
    @XmlElement(name="billingLocName", required = true)
    private String _billingLocName;
    
    @XmlElement(name="applySalesTax")
    private boolean _applySalesTax;
    
    @XmlElement(name="displayBillingPaymentInfo")
    private boolean _displayBillingPaymentInfo;
    
    @XmlElement(name="invoiceBaseCharge")
    private double _invoiceBaseCharge;
    
    @XmlElement(name="invoiceAutoAdjustment")
    private double _invoiceAutoAdjustment;
    
    @XmlElement(name="released")
    private boolean _released;
    
    @XmlElement(name="hasInvoices")
    private boolean _hasInvoices;
    


    //ROI Financial Enhancement Changes
    @XmlElement(name="hasUnReleasedInvoices")
    private boolean _hasUnReleasedInvoices;
    
    @XmlElement(name="invoicesBalance")
    private double _invoicesBalance;
    
    @XmlElement(name="invoicesSalesTaxAmount")
    private double _invoicesSalesTaxAmount;
    
    @XmlElement(name="unbillable")
    private boolean _unbillable;
    
    @XmlElement(name="totalUnappliedPaymentAmount")
    private double _totalUnappliedPaymentAmount;
    
    @XmlElement(name="totalUnappliedAdjustmentAmount")
    private double _totalUnappliedAdjustmentAmount;
    
    @XmlElement(name="totalUnappliedAmount")
    private double _totalUnappliedAmount;

    
    @XmlElement(name="salesTaxSummary", required = true)
    private SalesTaxSummary _salesTaxSummary;
    
    @XmlElement(name="requestCoreChargesBilling", required = true)
    private RequestCoreChargesBilling _requestCoreChargesBilling;
    
    @XmlElement(name="requestCoreChargesShipping", required = true)
    private RequestCoreChargesShipping _requestCoreChargesShipping;
    
    
    
    @XmlElement(name="id")
    private long _id;
    
    @XmlElement(name="createdBy")
    private long _createdBy;
    
    @XmlElement(name="modifiedBy")
    private long _modifiedBy;
    
    @XmlElement(name="createdDt", required = true, nillable = true)
    private Date _createdDt;
    
    @XmlElement(name="modifiedDt", required = true, nillable = true)
    private Date _modifiedDt;
    
    @XmlElement(name="recordVersion")
    private int _recordVersion; 


    public long getId() {
        return _id;
    }

    public void setId(long id) {
        _id = id;
    }

    public long getCreatedBy() {
        return _createdBy;
    }

    public void setCreatedBy(long createdBy) {
        _createdBy = createdBy;
    }

    public long getModifiedBy() {
        return _modifiedBy;
    }

    public void setModifiedBy(long modifiedBy) {
        _modifiedBy = modifiedBy;
    }

    public Date getCreatedDt() {
        return _createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        _createdDt = createdDt;
    }

    public Date getModifiedDt() {
        return _modifiedDt;
    }

    public void setModifiedDt(Date modifiedDt) {
        _modifiedDt = modifiedDt;
    }

    public int getRecordVersion() {
        return _recordVersion;
    }

    public void setRecordVersion(int recordVersion) {
        _recordVersion = recordVersion;
    }
    

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

    public double getTotalRequestCost() { return _totalRequestCost; }
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
