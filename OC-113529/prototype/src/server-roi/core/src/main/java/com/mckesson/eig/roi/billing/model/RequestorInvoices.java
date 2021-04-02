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

/**
 * @author OFS
 * @date   Sep 26, 2011
 * @since  Sep 26, 2011
 */
public class RequestorInvoices {
    
    private long[] _invoiceIds;
    private long _requestorId;
    private String _requestorName;
    private String _requestorType;
    
    public long[] getInvoiceIds() { return _invoiceIds; }
    public void setInvoiceIds(long[] invoiceIds) { _invoiceIds = invoiceIds; }
    
    public long getRequestorId() { return _requestorId; }
    public void setRequestorId(long requestorId) { _requestorId = requestorId; }
    
    public String getRequestorName() { return _requestorName; }
    public void setRequestorName(String requestorName) { _requestorName = requestorName; }
    
    public void setRequestorType(String requestorType) { _requestorType = requestorType; }
    public String getRequestorType() { return _requestorType; }
}
