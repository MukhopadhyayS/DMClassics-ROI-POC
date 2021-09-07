/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2011 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.billing.letter.model.InvoiceCharge;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author OFS
 * @date   Oct 6, 2011
 * @since  Oct 6, 2011
 */
public class XDocInvoiceCharge {
    
    private String _invoiceId = StringUtilities.EMPTYSTRING;
    private String _requestId = StringUtilities.EMPTYSTRING;
    private String _invoiceBillingLocation = StringUtilities.EMPTYSTRING;
    private String _invoiceDate = StringUtilities.EMPTYSTRING;
    private String _overDueDays = StringUtilities.EMPTYSTRING;
    private String _baseCharge = StringUtilities.EMPTYSTRING;
    private String _amountPaid = StringUtilities.EMPTYSTRING;
    private String _invoiceBalanceDue = StringUtilities.EMPTYSTRING;
    private String _invoiceSalesTax = StringUtilities.EMPTYSTRING;
    
    public XDocInvoiceCharge(InvoiceCharge invoicecharge) {
        
        if (null == invoicecharge) {
            return;
        }
        
        setInvoiceId(StringUtilities.safe(invoicecharge.getInvoiceId()));
        setRequestId(StringUtilities.safe(invoicecharge.getRequestId()));
        setInvoiceBillingLocation(StringUtilities.safe(invoicecharge.getInvoiceBillingLocation()));
        setInvoiceDate(StringUtilities.safe(invoicecharge.getInvoiceDate()));
        setOverDueDays(StringUtilities.safe(invoicecharge.getOverDueDays()));
        setBaseCharge(StringUtilities.safe(invoicecharge.getBaseCharge()));
        setAmountPaid(StringUtilities.safe(invoicecharge.getAmountPaid()));
        setInvoiceBalanceDue(StringUtilities.safe(invoicecharge.getInvoiceBalanceDue()));
        setInvoiceSalesTax(StringUtilities.safe(invoicecharge.getInvoiceSalesTax()));
    }
    
    public String getInvoiceId() { return _invoiceId; }
    public void setInvoiceId(String invoiceId) { _invoiceId = invoiceId; }
    
    public String getInvoiceDate() { return _invoiceDate; }
    public void setInvoiceDate(String invoiceDate) { _invoiceDate = invoiceDate; }
     
    public String getBaseCharge() { return _baseCharge; }
    public void setBaseCharge(String baseCharge) {
        _baseCharge = baseCharge;
    }
    
    public String getAmountPaid() { return _amountPaid; }
    public void setAmountPaid(String amountPaid) { _amountPaid = amountPaid; }
    
    public String getInvoiceBalanceDue() { return _invoiceBalanceDue; }
    public void setInvoiceBalanceDue(String invBalanceDue) { _invoiceBalanceDue = invBalanceDue; }
    
    public String getInvoiceSalesTax() { return _invoiceSalesTax; }
    public void setInvoiceSalesTax(String invoiceSalesTax) { _invoiceSalesTax = invoiceSalesTax; }
    
    public String getInvoiceBillingLocation() { return _invoiceBillingLocation; }
    public void setInvoiceBillingLocation(String invoiceBillingLocation) { 
                                _invoiceBillingLocation = invoiceBillingLocation; }
    
    public String getRequestId() { return _requestId; }
    public void setRequestId(String requestId) { _requestId = requestId; }
    
    public String getOverDueDays() { return _overDueDays; }
    public void setOverDueDays(String overDueDays) { _overDueDays = overDueDays; }
    
    public String getInvoiceItemXml() {
        
        StringBuffer xml = new StringBuffer();
        xml.append("<" + ROIConstants.RELEASE_ITEM_TAG + " " + ROIConstants.TYPE_ATTRIBUTE + "=\"")
           .append(ROIConstants.INVOICE_INFO_TYPE + "\" > \n");
        
        xml.append(createAttributeElement(ROIConstants.INVOICE_ID_ATTR_KEY,
                                          checkNull(_invoiceId)))
            .append(createAttributeElement(ROIConstants.INVOICE_DATE_ATTR_KEY, 
                                          checkNull(_invoiceDate)))
            .append(createAttributeElement(ROIConstants.INVOICE_BILLING_LOC_NAME_ATTR_KEY,
                                          checkNull(_invoiceBillingLocation)))
            .append(createAttributeElement(ROIConstants.INVOICE_BASE_CHARGE_ATTR_KEY,
                                          checkNull(_baseCharge)))
            .append(createAttributeElement(ROIConstants.INVOICE_PAID_AMOUNT_ATTR_KEY,
                                          checkNull(_amountPaid)))
            .append(createAttributeElement(ROIConstants.INVOICE_BALANCE_DUE_ATTR_KEY,
                                          checkNull(_invoiceBalanceDue)))
            .append(createAttributeElement(ROIConstants.INVOICE_SALES_TAX_ATTR_KEY,
                                           checkNull(_invoiceSalesTax)))
            .append(createAttributeElement(ROIConstants.ROI_REQUEST_ID_KEY,
                                           checkNull(_requestId)))
            .append(createAttributeElement(ROIConstants.INVOICE_OVERDUE_DAYS_ATTR_KEY,
                                           checkNull(_overDueDays)));
            
        xml.append("</" + ROIConstants.RELEASE_ITEM_TAG + "> \n");
        return xml.toString();
    }
    
    private String checkNull(String value) {
        return (null == value) ? "" : value;
    }
    
    private String createAttributeElement(String key, String value) {
        
        StringBuffer buf = new StringBuffer();
        buf.append("<" + ROIConstants.ATTRIBUTE_TAG + " ")
            .append(ROIConstants.NAME_ATTRIBUTE + "=\"" + key + "\" ")
            .append(ROIConstants.VALUE_ATTRIBUTE + "=\"" + toASCII(value) + "\" ")
            .append("/> \n");
        
        return buf.toString();
    }
    
    private String toASCII(String str) {

        String ascii;
        ascii = str.replaceAll("&", "&amp;");
        ascii = ascii.replaceAll("\"", "&quot;");
        ascii = ascii.replaceAll("<", "&#60;");
        ascii = ascii.replaceAll(">", "&#62;");

        return ascii;
    }
}
