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
import java.util.List;

import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.request.model.RequestCoreCharges;
import com.mckesson.eig.roi.request.model.RequestCoreChargesShipping;
import com.mckesson.eig.roi.request.model.RequestPatient;
import com.mckesson.eig.roi.requestor.model.RequestorCore;

/**
 * @author OFS
 * @date Sep 11, 2012
 * @since Sep 11, 2012
 *
 */
public class CoverLetterCore 
extends BaseModel
implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private long _requestCoreChargesId;
    private long _requestId;
    private long _requestorId;
    private long _letterTemplateId;
    private  String _notes;
    private String _letterType;
    private String _queuePassword;
    private List <RequestPatient> _requestSupplementalPatients;
    private List <RequestPatient> _requestHpfPatients;
    private RequestCoreCharges _chargesDetails;
    private RequestCoreChargesShipping _shippingDetails;
    private RequestorCore _requestor;
    
    
    public long getRequestCoreChargesId() { return _requestCoreChargesId; }
    public void setRequestCoreChargesId(long requestCoreChargesId) {
        _requestCoreChargesId = requestCoreChargesId;
    }

    public long getRequestId() { return _requestId; }
    public void setRequestId(long requestId) { _requestId = requestId; }

    public long getRequestorId() { return _requestorId; }
    public void setRequestorId(long requestorId) { _requestorId = requestorId; }
    
    public long getLetterTemplateId() { return _letterTemplateId; }
    public void setLetterTemplateId(long letterTemplateId) { _letterTemplateId = letterTemplateId; }
    
    public String getNotes() { return _notes; }
    public void setNotes(String notes) { _notes = notes; }
    
    public List<RequestPatient> getRequestSupplementalPatients() { return _requestSupplementalPatients; }
    public void setRequestSupplementalPatients( List<RequestPatient> requestSupplementalPatients) {
        _requestSupplementalPatients = requestSupplementalPatients;
    }
    
    public List<RequestPatient> getRequestHpfPatients() { return _requestHpfPatients; }
    public void setRequestHpfPatients(List<RequestPatient> requestHpfPatients) {
        _requestHpfPatients = requestHpfPatients;
    }
    
    public String getLetterType() { return _letterType; }
    public void setLetterType(String letterType) { _letterType = letterType; }
    
    public String getQueuePassword() { return _queuePassword; }
    public void setQueuePassword(String queuePassword) { _queuePassword = queuePassword; }
    
    public RequestCoreChargesShipping getShippingDetails() { return _shippingDetails; }
    public void setShippingDetails(RequestCoreChargesShipping shippingDetails) {
        _shippingDetails = shippingDetails;
    }
    public RequestCoreCharges getChargesDetails() {
        return _chargesDetails;
    }
    public void setChargesDetails(RequestCoreCharges chargesDetails) {
        _chargesDetails = chargesDetails;
    }
    
    public RequestorCore getRequestor() { return _requestor; }
    public void setRequestor(RequestorCore requestor) { _requestor = requestor; }
    
    @Override
    public String toString() {
        return new StringBuffer()
                    .append("Id:")
                    .append(getId())
                    .append(", RequestId:")
                    .append(_requestId)
                    .append(", RequestorId:")
                    .append(_requestorId)
                    .append(", RequestCoreChargesId")
                    .append(_requestCoreChargesId)
                    .append(", LetterTemplateId:")
                    .append(_letterTemplateId)
                    .toString();
    }

}
