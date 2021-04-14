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

import java.io.Serializable;
import java.util.Date;

import com.mckesson.eig.roi.utils.SecureStringAccessor;

/**
 * This class  is a persistent model used to view the requestor letter history for hibernate.
 * @author rajeshkumarg
 * @date   Oct 18, 2011
 * @since  HPF 15.2 [ROI]; Oct 18, 2011
 */
public class RequestorLetterHistory 
implements Serializable {
    
    private static final long serialVersionUID = 9086690815566101758L;
    
    private long _requestorLetterId;
    private Date _createdDate;
    private String _resendDate;
    private String _outputMethod;
    private String _createdBy;
    private long _requestTemplateId;
    private String _templateUsed;
    private SecureStringAccessor _requestPassword;
    private SecureStringAccessor _queuePassword;
    
    public long getRequestorLetterId() { return _requestorLetterId; }
    public void setRequestorLetterId(long requestorLetterId) {
        _requestorLetterId = requestorLetterId;
    }
    
    public Date getCreatedDate() { return _createdDate; }
    public void setCreatedDate(Date createdDate) { _createdDate = createdDate; }
    
    public String getResendDate() { return _resendDate; }
    public void setResendDate(String resendDate) { _resendDate = resendDate; }
    
    public String getOutputMethod() { return _outputMethod; }
    public void setOutputMethod(String outputMethod) { _outputMethod = outputMethod; }
    
    public String getCreatedBy() { return _createdBy; }
    public void setCreatedBy(String createdBy) { _createdBy = createdBy; }
    
    public long getRequestTemplateId() { return _requestTemplateId; }
    public void setRequestTemplateId(long requestTemplateId) {
        _requestTemplateId = requestTemplateId;
    }
    
    public String getTemplateUsed() { return _templateUsed; }
    public void setTemplateUsed(String templateUsed) { _templateUsed = templateUsed; }
    
    public String getRequestPassword() {
        StringBuilder builder = new StringBuilder();
        _requestPassword.DoHylandAccess((chars, tempStr) -> {
            builder.append(chars);
        });
        return builder.toString();
    }
    public void setRequestPassword(String requestPassword) {
        _requestPassword =  new SecureStringAccessor(requestPassword.toCharArray()); 
    }
    
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

}
