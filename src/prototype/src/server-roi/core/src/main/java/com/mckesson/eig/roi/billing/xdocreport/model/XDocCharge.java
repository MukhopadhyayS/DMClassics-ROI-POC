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

import java.text.NumberFormat;
import java.text.ParseException;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.billing.letter.model.Charge;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 * @author OFS
 * @date   Mar 17, 2014
 * @since  HPF 16.2 [ROI]; Mar 17, 2014
 */
public class XDocCharge {
    
    private static final Log LOG = LogFactory.getLogger(XDocCharge.class);
    private static final NumberFormat CURRENCY_FORMAT_US = 
                                    NumberFormat.getCurrencyInstance(ROIConstants.INVOICE_LOCALE);

    private String _name = StringUtilities.EMPTYSTRING;
    private String  _chargeTotal = StringUtilities.EMPTYSTRING;
    private String  _charges = StringUtilities.EMPTYSTRING;

    // Txns details
    private String _adjustmentTotal = StringUtilities.EMPTYSTRING;
    private String _creditAdjustmentTotal = StringUtilities.EMPTYSTRING;
    private String _debitAdjustmentTotal = StringUtilities.EMPTYSTRING;
    private String _paymentTotal = StringUtilities.EMPTYSTRING;
    private String _adjustmentPaymentTotal = StringUtilities.EMPTYSTRING;
    private String _taxableAmount = StringUtilities.EMPTYSTRING;
    private String _nonTaxableAmount = StringUtilities.EMPTYSTRING;
    private String _taxCharge = StringUtilities.EMPTYSTRING;

    // document charge details
    private String _pages = StringUtilities.EMPTYSTRING;
    private String _copies = StringUtilities.EMPTYSTRING;
    private String _documentChargeTotal = StringUtilities.EMPTYSTRING;
    private String _documentChargeTaxTotal = StringUtilities.EMPTYSTRING;
    
    // fee charge
    private String _feeChargeTotal = StringUtilities.EMPTYSTRING;
    private String _feeChargeTaxTotal = StringUtilities.EMPTYSTRING;
    private String _customFeeChargeTaxTotal = StringUtilities.EMPTYSTRING;
    
    public XDocCharge() { }
    public XDocCharge(Charge charge) { 
        
        if (null == charge) {
            return;
        }
        
        _name = StringUtilities.safe(charge.getName());
        _chargeTotal = StringUtilities.safe(charge.getChargeTotal());
        _charges = StringUtilities.safe(charge.getCharges());
        
        // billing details
        _adjustmentTotal = StringUtilities.safe(charge.getAdjustmentTotal());
        _creditAdjustmentTotal = StringUtilities.safe(charge.getCreditAdjustmentTotal());
        _debitAdjustmentTotal = StringUtilities.safe(charge.getDebitAdjustmentTotal());
        _paymentTotal = StringUtilities.safe(charge.getPaymentTotal());
        _adjustmentPaymentTotal = StringUtilities.safe(charge.getAdjustmentPaymentTotal());
        _taxableAmount = StringUtilities.safe(charge.getTaxableAmount());
        _nonTaxableAmount = StringUtilities.safe(charge.getNonTaxableAmount());
        _taxCharge = StringUtilities.safe(charge.getTaxCharge());

        // document charge details
        _pages = StringUtilities.safe(charge.getPages());
        _copies = StringUtilities.safe(charge.getCopies());
        _documentChargeTotal = StringUtilities.safe(charge.getDocumentChargeTotal());
        _documentChargeTaxTotal = StringUtilities.safe(charge.getDocumentChargeTaxTotal());
        
        // fee charge
        _feeChargeTotal = StringUtilities.safe(charge.getFeeChargeTotal());
        _feeChargeTaxTotal = StringUtilities.safe(charge.getFeeChargeTaxTotal());
        _customFeeChargeTaxTotal = StringUtilities.safe(charge.getCustomFeeChargeTaxTotal());
    }
    

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
            LOG.error(e);
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
    
    public String getCreditAdjustmentTotal() { return _creditAdjustmentTotal; }
    public void setCreditAdjustmentTotal(String creditAdjustmentTotal) { 
        _creditAdjustmentTotal = creditAdjustmentTotal;
    }
    
    public String getDebitAdjustmentTotal() { return _debitAdjustmentTotal; }
    public void setDebitAdjustmentTotal(String debitAdjustmentTotal) { 
        _debitAdjustmentTotal = debitAdjustmentTotal;
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
