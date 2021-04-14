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

import com.mckesson.eig.roi.utils.SecureStringAccessor;


/**
 * @author OFS
 * @date   Nov 17, 2008
 * @since  HPF 13.1 [ROI]; Nov 11, 2008
 */
public class Invoice
implements Serializable {

    private static final long serialVersionUID = 8865820133570176997L;
    private long _id;
    private String _type;
    private String _creatorName;
    private String _createdDate;
    private SecureStringAccessor _queuePassword;
    private String _resendDate;
    private String _outputMethod;
    private String _invoiceDueDate;
    private double _baseCharge;
    private double _totalAdjustments;
    private double _totalPayments;
    private double _currentBalance;

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getType() { return _type; }
    public void setType(String type) { _type = type; }

    public String getCreatorName() { return _creatorName; }
    public void setCreatorName(String creatorName) { _creatorName = creatorName; }

    public String getCreatedDate() { return _createdDate; }
    public void setCreatedDate(String createdDate) { _createdDate = createdDate; }
    
    public String getQueuePassword() { 
        StringBuilder builder = new StringBuilder();
        _queuePassword.DoHylandAccess((chars, tempStr) -> {
            builder.append(chars);
        });
        return builder.toString();
    }
    public void setQueuePassword(String queuePassword) {  
        _queuePassword = new SecureStringAccessor(queuePassword.toCharArray());
    }
    
    public String getResendDate() { return _resendDate; }
    public void setResendDate(String resendDate) { _resendDate = resendDate; }

    public String getOutputMethod() { return _outputMethod; }
    public void setOutputMethod(String outputMethod) { _outputMethod = outputMethod; }
    
    public String getInvoiceDueDate() { return _invoiceDueDate; }
    public void setInvoiceDueDate(String invoiceDueDate) { _invoiceDueDate = invoiceDueDate; }
    
    public double getBaseCharge() { return _baseCharge; }
    public void setBaseCharge(double baseCharge) { _baseCharge = baseCharge; }
    
    public double getTotalAdjustments() { return _totalAdjustments; }
    public void setTotalAdjustments(double totalAdjustments) { 
        _totalAdjustments = totalAdjustments; }
    
    public double getTotalPayments() { return _totalPayments; }
    public void setTotalPayments(double totalPayments) { _totalPayments = totalPayments; }
    
    public double getCurrentBalance() { return _currentBalance; }
    public void setCurrentBalance(double currentBalance) { _currentBalance = currentBalance; }
    
}
