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

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 * @author OFS
 * @date   Aug 25, 2009
 * @since  HPF 13.1 [ROI]; Feb 19, 2009
 */
public class Charge {
    
    private static final OCLogger LOG = new OCLogger(Charge.class);
    private static final NumberFormat CURRENCY_FORMAT_US = 
                                    NumberFormat.getCurrencyInstance(ROIConstants.INVOICE_LOCALE);

    private String _name;
    private String  _chargeTotal;
    private String  _charges;
    private List<ChargeItem> _chargeItems;
    private List<Charge> _docCharge;
    private List<Charge> _feeCharge;
    private List<Charge> _txns;
    private List<Charge> _shippingCharge;
    private List<ReleaseInfo> _releases;

    // Txns details
    private String _adjustmentTotal;
    private String _creditAdjustmentTotal;
    private String _debitAdjustmentTotal;
    private String _paymentTotal;
    private String _adjustmentPaymentTotal;
    private String _taxableAmount;
    private String _nonTaxableAmount;
    private String _taxCharge;
    private InvoiceCharge _invoices;

    // document charge details
    private String _pages;
    private String _copies;
    private String _documentChargeTotal;
    private String _documentChargeTaxTotal;
    
    // fee charge
    private String _feeChargeTotal;
    private String _feeChargeTaxTotal;
    private String _customFeeChargeTaxTotal;

    public String getName() { return _name; }
    public void setName(String name) { _name = name; }

    public String getChargeTotal() { return _chargeTotal; }
    public void setChargeTotal(String total) { _chargeTotal = total; }

    public String getCharges() {
        
        if (null != _charges) {
            return _charges;
        }
        
        if (StringUtilities.isEmpty(_chargeTotal)) {

            _charges = "$0.00";
            return _charges;
        } 
        
        if (_chargeTotal.startsWith(ROIConstants.DOLLAR)) {
        
            _charges = _chargeTotal;
            return _charges;
        }
        double amount = 0.00;
        try {
            amount = Double.valueOf(_chargeTotal);
        } catch (Exception e) {
            LOG.error("Exception occured in getCharges",e);
        }
        _charges = appendDollarSymbol(amount); 
        return _charges;
    }
    public void setCharges(String charges) { _charges = charges; }
    public void setCharges(double charges) {
        _charges = ROIConstants.CURRENCY_FORMAT.format(charges);
    }

    public void setCharges(long charges) {
        _charges = ROIConstants.CURRENCY_FORMAT.format(charges);
    }

    public List<ChargeItem> getChargeItems() { return _chargeItems;  }
    public void setChargeItems(List<ChargeItem> items) { _chargeItems = items; }

    public List<Charge> getDocCharge() { return _docCharge; }
    public void setDocCharge(List<Charge> charge) { _docCharge = charge; }

    public List<Charge> getFeeCharge() { return _feeCharge; }
    public void setFeeCharge(List<Charge> charge) { _feeCharge = charge; }

    public List<Charge> getTxns() { return _txns; }
    public void setTxns(List<Charge> txns) { _txns = txns; }

    public List<Charge> getShippingCharge() { return _shippingCharge; }
    public void setShippingCharge(List<Charge> charge) { _shippingCharge = charge; }

    public List<ReleaseInfo> getReleases() { return _releases; }
    public void setReleases(List<ReleaseInfo> releases) { _releases = releases; }

    public void setAdjustmentTotal(String total) { _adjustmentTotal = total; }
    public String getAdjustmentTotal() {
        
        if (null == _adjustmentTotal) {
            double totalAdjustment = supressDollarSymbol(getCreditAdjustmentTotal())
                                                + supressDollarSymbol(getDebitAdjustmentTotal());
            return appendDollarSymbol(totalAdjustment);
        }
        return _adjustmentTotal;
    }

    public String getPaymentTotal() { return _paymentTotal; }
    public void setPaymentTotal(String total) { _paymentTotal = total; }

    public String getAdjustmentPaymentTotal() { return _adjustmentPaymentTotal; }
    public void setAdjustmentPaymentTotal(String paymentTotal) {
        _adjustmentPaymentTotal = paymentTotal;
    }

    public String getPages() { return _pages; }
    public void setPages(String pages) { _pages = pages; }

    public String getCopies() { return _copies; }
    public void setCopies(String copies) { _copies = copies; }
    
    public InvoiceCharge getInvoices() { return _invoices; }
    public void setInvoices(InvoiceCharge invoices) { _invoices = invoices; }
    
    public String getCreditAdjustmentTotal() { return _creditAdjustmentTotal; }
    public void setCreditAdjustmentTotal(String creditAdjustmentTotal) { 
        _creditAdjustmentTotal = creditAdjustmentTotal;
    }
    
    public String getDebitAdjustmentTotal() { return _debitAdjustmentTotal; }
    public void setDebitAdjustmentTotal(String debitAdjustmentTotal) { 
        _debitAdjustmentTotal = StringUtilities.safe(debitAdjustmentTotal);
    }
    
    public String getTaxableAmount() { return _taxableAmount; }
    public void setTaxableAmount(String taxableAmount) { 
        _taxableAmount = taxableAmount;
    }
    
    public String getNonTaxableAmount() { return _nonTaxableAmount; }
    public void setNonTaxableAmount(String nonTaxableAmount) { 
        _nonTaxableAmount = nonTaxableAmount;
    }
    
    public String getTaxCharge() { return _taxCharge; }
    public void setTaxCharge(String taxCharge) { _taxCharge = taxCharge; }
    
    public String getDocumentChargeTotal() { return _documentChargeTotal; }
    public void setDocumentChargeTotal(String documentChargeTotal) { 
        _documentChargeTotal = documentChargeTotal;
    }
    
    public String getDocumentChargeTaxTotal() { return _documentChargeTaxTotal; }
    public void setDocumentChargeTaxTotal(String documentChargeTaxTotal) {
        _documentChargeTaxTotal = documentChargeTaxTotal;
    }
    
    public String getFeeChargeTotal() { return _feeChargeTotal; }
    public void setFeeChargeTotal(String feeChargeTotal) { _feeChargeTotal = feeChargeTotal; }

    public String getFeeChargeTaxTotal() { return _feeChargeTaxTotal; }
    public void setFeeChargeTaxTotal(String feeChargeTaxTotal) { 
        _feeChargeTaxTotal = feeChargeTaxTotal;
    }

    public String getCustomFeeChargeTaxTotal() { return _customFeeChargeTaxTotal; }
    public void setCustomFeeChargeTaxTotal(String customFeeChargeTaxTotal) {
        _customFeeChargeTaxTotal = customFeeChargeTaxTotal;
    }
    
    private double supressDollarSymbol(String amount) {
        
        try {
            
            return (StringUtilities.isEmpty(amount)) ? 0.00 
                    : CURRENCY_FORMAT_US.parse(amount).doubleValue();
        } catch (ParseException e) {
            throw new ROIException(ROIClientErrorCodes.INVALID_CURRENCY_TYPE);
        } 
    }
    
    private String appendDollarSymbol(double amount) {
        return CURRENCY_FORMAT_US.format(amount);
    }
}
