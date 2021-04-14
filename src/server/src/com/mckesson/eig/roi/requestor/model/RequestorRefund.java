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

import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.utils.SecureStringAccessor;


/**
 * @author OFS
 * @date   Jan 04, 2013
 */

public class RequestorRefund
extends BaseModel {

    private static final long serialVersionUID = 1L;

    private long _requestorId;
    private String _note;
    private String _requestorName;
    private String _requestorType;
    private Date _refundDate;
    private double _refundAmount;

    private RequestorStatementCriteria _statementCriteria;

    private long _templateId;
    private String _templateName;
    private String _outputMethod;
    private SecureStringAccessor _queuePassword;
    private List<String> _notes;

    public long getRequestorId() { return _requestorId; }
    public void setRequestorId(long requestorId) { _requestorId = requestorId; }

    public String getNote() { return _note; }
    public void setNote(String note) { _note = note; }

    public String getRequestorName() { return _requestorName; }
    public void setRequestorName(String requestorName) { _requestorName = requestorName; }

    public String getRequestorType() { return _requestorType; }
    public void setRequestorType(String requestorType) { _requestorType = requestorType; }

    public Date getRefundDate() { return _refundDate; }
    public void setRefundDate(Date refundDate) { _refundDate = refundDate; }

    public double getRefundAmount() { return _refundAmount; }
    public void setRefundAmount(double refundAmount) { _refundAmount = refundAmount; }

    public long getTemplateId() { return _templateId; }
    public void setTemplateId(long templateId) { _templateId = templateId; }

    public String getTemplateName() { return _templateName; }
    public void setTemplateName(String templateName) { _templateName = templateName; }

    public String getOutputMethod() { return _outputMethod; }
    public void setOutputMethod(String outputMethod) { _outputMethod = outputMethod; }

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


    public RequestorStatementCriteria getStatementCriteria() { return _statementCriteria; }
    public void setStatementCriteria(RequestorStatementCriteria statementCriteria) {
        _statementCriteria = statementCriteria;
    }

    public List<String> getNotes() { return _notes; }
    public void setNotes(List<String> notes) { _notes = notes; }

    /**
     * This method create audit commend for create Requestor Refund
     * @return audit comment for retrieve event
     */
    public String constructRefundAuditComment() {

        return new StringBuffer().append("Refund has been applied to ")
                .append(_requestorName)
                .append(" belonging to  ")
                .append(_requestorType + ".")
                .append("Refunded amount = ")
                .append(_refundAmount)
                .append(".")
                .toString();

    }

}
