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

package com.mckesson.eig.roi.billing.model;

import com.mckesson.eig.roi.base.model.BaseModel;

/**
 * This class  is a persistent model used to store requestor summary Invoices for hibernate.
 * @author OFS
 * @date   Sep 15, 2011
 * @since  HPF 15.2 [ROI]; Sep 15, 2011
 */
public class RequestorLetterInvoice
extends BaseModel {

    private static final long serialVersionUID = 1L;
    
    private Long _requestorLetterId;
    private Long _invoiceId;
    private Long _requestId;
    private String _billingLocation;
    private Double _overDueAmount;
    private Long _overDueDays;

    public Long getRequestorLetterId() { return _requestorLetterId; }
    public void setRequestorLetterId(Long requestorLetterId) {
        _requestorLetterId = requestorLetterId;
    }
    
    public Long getInvoiceId() { return _invoiceId; }
    public void setInvoiceId(Long invoiceId) { _invoiceId = invoiceId; }
    
    public Double getOverDueAmount() { return _overDueAmount; }
    public void setOverDueAmount(Double overDueAmount) { _overDueAmount = overDueAmount; }
    
    public Long getOverDueDays() { return _overDueDays; }
    public void setOverDueDays(Long overDueDays) { _overDueDays = overDueDays; }
    
    public Long getRequestId() { return _requestId; }
    public void setRequestId(Long requestId) { _requestId = requestId; }
    
    public String getBillingLocation() { return _billingLocation; }
    public void setBillingLocation(String billingLocation) { _billingLocation = billingLocation; }
    
    @Override
    public String toString() {
        return new StringBuffer()
                        .append("InvoiceId:")
                        .append(_invoiceId)
                        .append(", RequestId:")
                        .append(_requestId)
                        .append(", OverDueAmount:")
                        .append(_overDueAmount)
                        .append(", OverDueDays")
                        .append(_overDueDays)
                        .toString();
    }
    
}
