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

package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;


/**
 * @author OFS
 * @date   Sep 08, 2011
 * @since  HPF 15.1 [ROI]
 */
public class PastDueInvoice
implements Serializable {

    private static final long serialVersionUID = -2409127221006485571L;

    private String _billingLocation;
    private long _invoiceNumber;
    private long _requestId;
    private double _overdueAmount;
    private String _requestorName;
    private String _phoneNumber;
    private String _requestorType;
    private long _requestorId;
    private long _overDueDays;
    private long _invoiceAge;

    public String getBillingLocation() { return _billingLocation; }
    public void setBillingLocation(String billingLocation) { _billingLocation = billingLocation; }

    public long getInvoiceNumber() { return _invoiceNumber; }
    public void setInvoiceNumber(long invoiceNumber) { _invoiceNumber = invoiceNumber; }

    public long getRequestId() { return _requestId; }
    public void setRequestId(long requestId) { _requestId = requestId; }

    public double getOverdueAmount() { return _overdueAmount; }
    public void setOverdueAmount(double overdueAmount) { _overdueAmount = overdueAmount; }

    public String getRequestorName() { return _requestorName; }
    public void setRequestorName(String requestorName) { _requestorName = requestorName; }

    public String getPhoneNumber() { return _phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { _phoneNumber = phoneNumber; }

    public String getRequestorType() { return _requestorType; }
    public void setRequestorType(String requestorType) { _requestorType = requestorType; }

    public long getRequestorId() { return _requestorId; }
    public void setRequestorId(long requestorId) { _requestorId = requestorId; }

    public long getOverDueDays() { return _overDueDays; }
    public void setOverDueDays(long exceededDueDays) { _overDueDays = exceededDueDays; }

    public long getInvoiceAge() { return _invoiceAge; }
    public void setInvoiceAge(long invoiceAge) { _invoiceAge = invoiceAge; }
}
