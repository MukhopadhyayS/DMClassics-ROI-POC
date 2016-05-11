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

import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.model.BaseModel;

/**
 * @author OFS
 * @date Nov 16, 2012
 * @since HPF 16.0 [ROI]; Nov 16, 2012
 *
 */
public class RequestorPaymentList 
extends BaseModel {
    
    private static final long serialVersionUID = 1L;
    private long _requestorId;
    private String _requestorName;
    private String _requestorType;
    private long _paymentId;
    private double _paymentAmount;
    private double _unAppliedAmount;
    private String _paymentMode;
    private String _description;
    private Date _paymentDate;
    private List<RequestorPayment> _paymentList;
       
    public long getRequestorId() { return _requestorId; }
    public void setRequestorId(long requestorId) { _requestorId = requestorId; }
    
    public long getPaymentId() { return _paymentId; }
    public void setPaymentId(long paymentId) { _paymentId = paymentId; }
    
    public double getPaymentAmount() { return _paymentAmount; }
    public void setPaymentAmount(double paymentAmount) { _paymentAmount = paymentAmount; }
   
    public double getUnAppliedAmount() { return _unAppliedAmount; }
    public void setUnAppliedAmount(double unAppliedAmount) { _unAppliedAmount = unAppliedAmount; }
    
    public String getPaymentMode() { return _paymentMode; }
    public void setPaymentMode(String paymentMode) { _paymentMode = paymentMode; }
    
    public String getDescription() { return _description; }
    public void setDescription(String description) { _description = description; }
    
    public Date getPaymentDate() { return _paymentDate; }
    public void setPaymentDate(Date paymentDate) { _paymentDate = paymentDate; }
    
    public List<RequestorPayment> getPaymentList() { return _paymentList; }
    public void setPaymentList(List<RequestorPayment> paymentList) { _paymentList = paymentList; }
    
    public String getRequestorName() { return _requestorName; }
    public void setRequestorName(String requestorName) { _requestorName = requestorName; }
    
    public String getRequestorType() { return _requestorType; }
    public void setRequestorType(String requestorType) { _requestorType = requestorType; }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    /**
     * This method create audit comment for Requestor Post Payment
     * @return audit comment for retrieve event
     */
    public String constructPostPaymentAuditComment(String userName) {
        return new StringBuffer().append("A  ")
                .append(_paymentMode)
                .append(" payment of ")
                .append(ROIConstants.CURRENCY_FORMAT.format(_paymentAmount))
                .append(" was posted for  ")
                .append(_requestorName)
                .append(" by ")
                .append(userName.trim())
                .append(".")
                .toString();
    }
    
    /**
     * This method create audit comment for Requestor Delete Payment
     * @return audit comment for retrieve event
     */
    public String constructDeletePaymentAuditComment() {
        return new StringBuffer().append("A  ")
                .append(ROIConstants.CURRENCY_FORMAT.format(_unAppliedAmount))
                .append(" payment was deleted from ")
                .append(_requestorName)
                .append("'s account.")
                .toString();
    }

}
