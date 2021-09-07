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

import java.util.List;
import java.util.Map;

import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria;

/**
 * @author OFS
 * @date   Sep 26, 2011
 * @since  Sep 26, 2011
 */
public class InvoiceAndLetterInfo {

    private List<RequestorInvoices> _invoices;
    private long _invoiceTemplateId;
    private long _requestorLetterTemplateId;
    private long _letterTemplateId;
    private List<String> _reqLetterNotes;
    private List<String> _invoiceNotes;
    private long[] _pastInvIds;
    private boolean _isLetter;
    private boolean _isNewInvoice;
    private boolean _isPastInvoice;
	// CR# 375,961
    private boolean _isOutputInvoice;
    private Map<String, String> _properties;
    private RequestorStatementCriteria _statementCriteria;

    public List<String> getReqLetterNotes() { return _reqLetterNotes; }
    public void setReqLetterNotes(List<String> reqNotes) { _reqLetterNotes = reqNotes; }

    public List<String> getInvoiceNotes() { return _invoiceNotes; }
    public void setInvoiceNotes(List<String> invoiceNotes) { _invoiceNotes = invoiceNotes; }

    public List<RequestorInvoices> getInvoices() { return _invoices; }
    public void setInvoices(List<RequestorInvoices> invoices) { _invoices = invoices; }

    public long getInvoiceTemplateId() { return _invoiceTemplateId; }
    public void setInvoiceTemplateId(long invoiceTemplateId) {
        _invoiceTemplateId = invoiceTemplateId;
    }

    public long getRequestorLetterTemplateId() { return _requestorLetterTemplateId; }
    public void setRequestorLetterTemplateId(long requestorLetterTemplateId) {
        _requestorLetterTemplateId = requestorLetterTemplateId;
    }
    public Map<String, String> getProperties() { return _properties; }
    public void setProperties(Map<String, String> properties) { _properties = properties; }

    public void setIsLetter(boolean isLetter) { _isLetter = isLetter; }
    public boolean getIsLetter() { return _isLetter; }

    public void setIsNewInvoice(boolean isNewInvoice) { _isNewInvoice = isNewInvoice; }
    public boolean getIsNewInvoice() { return _isNewInvoice; }

    public void setIsPastInvoice(boolean isPastInvoice) { _isPastInvoice = isPastInvoice; }
    public boolean getIsPastInvoice() { return _isPastInvoice; }

    public void setLetterTemplateId(long letterTemplateId) { _letterTemplateId = letterTemplateId; }
    public long getLetterTemplateId() { return _letterTemplateId; }

    public void setPastInvIds(long[] pastInvIds) { _pastInvIds = pastInvIds; }
    public long[] getPastInvIds() { return _pastInvIds; }

    public RequestorStatementCriteria getStatementCriteria() { return _statementCriteria; }
    public void setStatementCriteria(RequestorStatementCriteria statementCriteria) {
        _statementCriteria = statementCriteria;
    }

    public boolean isOutputInvoice() { return _isOutputInvoice; }
    public void setOutputInvoice(boolean isOutputInvoice) { _isOutputInvoice = isOutputInvoice; }
}
