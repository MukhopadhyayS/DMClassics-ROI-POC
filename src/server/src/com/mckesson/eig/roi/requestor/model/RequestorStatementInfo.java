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

package com.mckesson.eig.roi.requestor.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.mckesson.dm.core.common.util.StringUtilities;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria.DateRange;
import com.mckesson.eig.roi.utils.SecureStringAccessor;
import com.mckesson.eig.utility.util.CollectionUtilities;


/**
 * @author Karthik Easwaran
 * @date   Dec 17, 2012
 * @since  Dec 17, 2012
 */
public class RequestorStatementInfo {

    private long _id;
    private String _name;
    private long _templateFileId;
    private long _requestorTypeId;
    private String _templateName;
    private String _requestorTypeName;
    private String _phone;
    private String _homePhone;
    private String _workPhone;
    private String _cellPhone;
    private String _address1;
    private String _address2;
    private String _address3;
    private String _city;
    private String _state;
    private String _postalCode;
    private String _country;
    private String _countryCode;
    private String _outputMethod;
    private SecureStringAccessor _queuePassword;
    private Date _resendDate;
    private Date _createdDate;
    private DateRange _dateRange;

    private double _invoiceAmountTotal;
    private double _adjustmentTotal;
    private double _paymentTotal;
    private double _unAppliedAmountTotal;
    private double _refundAmountTotal;

    private List<RequestorTransaction> _transactions;
    private List<String> _notes;
    private RequestorAccount _account;
    private RequestorAging _aging;

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getName() { return _name; }
    public void setName(String name) { _name = name; }

    public String getRequestorTypeName() { return _requestorTypeName; }
    public void setRequestorTypeName(String type) { _requestorTypeName = type; }

    public void setPhone(String phone) { _phone = phone; }
    public String getPhone() {

        if (ROIConstants.REQUESTOR_TYPE_PATIENT.equalsIgnoreCase(_requestorTypeName)) {
            _phone =  _workPhone;
        } else {
            _phone = _homePhone;
        }
        return _phone;
    }

    public List<RequestorTransaction> getTransactions() { return _transactions; }
    public void setTransactions(List<RequestorTransaction> transactions) {

        _transactions = transactions;
        if (CollectionUtilities.isEmpty(transactions)) {
            return;
        }

        double invoiceAmount = 0.00;
        double adjustment = 0.00;
        double payment = 0.00;
        double unAppliedAmount = 0.00;
        double refund = 0.00;
        for (RequestorTransaction trans : _transactions) {

            String type = trans.getType();
            if (ROIConstants.INVOICE.equalsIgnoreCase(type)) {

                // DE1560/CR# 384,396 - Fix
                if (!trans.isUnbillable()) {
                    invoiceAmount += trans.getCharges();
                }
            } else if (ROIConstants.ADJUSTMENT_TYPE.equalsIgnoreCase(type)) {
                adjustment += trans.getCharges();
            } else if (ROIConstants.PAYMENT_TYPE.equalsIgnoreCase(type)) {
                payment += trans.getCharges();
            } else if (ROIConstants.REFUND_TYPE.equalsIgnoreCase(type)) {
                refund += trans.getCharges();
            } else {
                unAppliedAmount += trans.getCharges();
            }
        }

        setInvoiceAmountTotal(invoiceAmount);
        setAdjustmentTotal(adjustment);
        setPaymentTotal(payment);
        setUnAppliedAmountTotal(unAppliedAmount);
        setRefundAmountTotal(refund);
        
        Collections.sort(_transactions);
    }

    public String getAddress1() { return _address1; }
    public void setAddress1(String address1) { _address1 = address1; }

    public String getAddress2() { return _address2; }
    public void setAddress2(String address2) { _address2 = address2; }

    public String getAddress3() { return _address3; }
    public void setAddress3(String address3) { _address3 = address3; }

    public String getCity() { return _city; }
    public void setCity(String city) { _city = city; }

    public String getState() { return _state; }
    public void setState(String state) { _state = state; }

    public String getPostalCode() { return _postalCode; }
    public void setPostalCode(String postalCode) { _postalCode = postalCode; }

    public RequestorAccount getRequestorAccount() { return _account; }
    public void setRequestorAccount(RequestorAccount account) { _account = account; }

    public RequestorAging getRequestorAging() { return _aging; }
    public void setRequestorAging(RequestorAging aging) { _aging = aging; }

    public String getHomePhone() { return _homePhone; }
    public void setHomePhone(String homePhone) { _homePhone = homePhone; }

    public String getWorkPhone() { return _workPhone; }
    public void setWorkPhone(String workPhone) { _workPhone = workPhone; }

    public String getCellPhone() { return _cellPhone; }
    public void setCellPhone(String cellPhone) { _cellPhone = cellPhone; }

    public String getCountry() { return _country; }
    public void setCountry(String country) { _country = country; }

    public String getCountryCode() { return _countryCode; }
    public void setCountryCode(String countryCode) { _countryCode = countryCode; }

    public long getTemplateFileId() { return _templateFileId; }
    public void setTemplateFileId(long templateFileId) { _templateFileId = templateFileId; }

    public String getTemplateName() { return _templateName; }
    public void setTemplateName(String templateName) { _templateName = templateName; }

    public List<String> getNotes() { return _notes; }
    public void setNotes(List<String> notes) { _notes = notes; }

    public String getOutputMethod() { return _outputMethod; }
    public void setOutputMethod(String outputMethod) { _outputMethod = outputMethod; }

    public String getQueuePassword() { 
        if (_queuePassword == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        _queuePassword.DoHylandAccess((chars, tempStr) -> {
            builder.append(chars);
        });
        return builder.toString();
    }
    public void setQueuePassword(String queuePassword) { 
        queuePassword = StringUtilities.safe(queuePassword);
        _queuePassword = new SecureStringAccessor(queuePassword.toCharArray());
    }

    public Date getResendDate() { return _resendDate; }
    public void setResendDate(Date resendDate) { _resendDate = resendDate; }

    public double getInvoiceAmountTotal() { return _invoiceAmountTotal; }
    public void setInvoiceAmountTotal(double invoiceAmountTotal) {
        _invoiceAmountTotal = invoiceAmountTotal;
    }

    public double getAdjustmentTotal() { return _adjustmentTotal; }
    public void setAdjustmentTotal(double adjustmentTotal) { _adjustmentTotal = adjustmentTotal; }

    public double getPaymentTotal() { return _paymentTotal; }
    public void setPaymentTotal(double paymentTotal) { _paymentTotal = paymentTotal; }

    public double getAdjustmentPaymentTotal() { return (_adjustmentTotal + _paymentTotal); }

    public double getUnAppliedAmountTotal() { return _unAppliedAmountTotal; }
    public void setUnAppliedAmountTotal(double unAppliedAmountTotal) {
        _unAppliedAmountTotal = unAppliedAmountTotal;
    }

    public double getRefundAmountTotal() { return _refundAmountTotal; }
    public void setRefundAmountTotal(double refundAmountTotal) {
        _refundAmountTotal = refundAmountTotal;
    }
    
    public DateRange getDateRange() { return _dateRange; }
    public void setDateRange(DateRange dateRange) { _dateRange = dateRange; }

    public long getRequestorTypeId() { return _requestorTypeId; }
    public void setRequestorTypeId(long requestorTypeId) { _requestorTypeId = requestorTypeId; }
    
    public Date getCreatedDate() { return _createdDate; }
    public void setCreatedDate(Date createdDate) { _createdDate = createdDate; }

}
