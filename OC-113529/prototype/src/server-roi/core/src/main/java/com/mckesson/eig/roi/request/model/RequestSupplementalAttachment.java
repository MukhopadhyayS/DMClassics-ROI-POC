/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.request.model;

import com.mckesson.eig.roi.supplementary.model.ROIAttachmentCommon;

/**
 * @author OFS
 * @date   Aug 9, 2012
 * @since  Aug 9, 2012
 */
public class RequestSupplementalAttachment 
extends ROIAttachmentCommon {

    private static final long serialVersionUID = 1L;

    private Long _attachmentCoreSeq;
    private String _facility;
    private String _mrn;
    private Long _patientSeq;
    private Long _attachmentSeq;
    private Long _supplementalId;
    private Long _billingTierId;
    private boolean  _isSelectedForRelease;
    private boolean  _isReleased;
    
    public Long getAttachmentCoreSeq() { return _attachmentCoreSeq; }
    public void setAttachmentCoreSeq(Long attachmentCoreSeq) {
        _attachmentCoreSeq = attachmentCoreSeq;
    }
    
    public String getFacility() { return _facility; }
    public void setFacility(String facility) { _facility = facility; }
   
    public String getMrn() { return _mrn; }
    public void setMrn(String mrn) { _mrn = mrn; }
   
    public Long getPatientSeq() { return _patientSeq; }
    public void setPatientSeq(Long patientSeq) { _patientSeq = patientSeq; }
    
    public Long getAttachmentSeq() { return _attachmentSeq; }
    public void setAttachmentSeq(Long attachmentSeq) { _attachmentSeq = attachmentSeq; }

    public Long getBillingTierId() { return _billingTierId; }
    public void setBillingTierId(Long billingTierId) { _billingTierId = billingTierId; }
    
    public Long getSupplementalId() { return _supplementalId; }
    public void setSupplementalId(Long supplementalId) { _supplementalId = supplementalId; }
    
    public void setSelectedForRelease(boolean isSelectedForRelease) {
        _isSelectedForRelease = isSelectedForRelease; }
    public boolean isSelectedForRelease() { return _isSelectedForRelease; }

    public void setReleased(boolean isReleased) { _isReleased = isReleased; }
    public boolean isReleased() { return _isReleased; }
    
    public String toString() {
        return new StringBuffer()
                        .append("PatientSeq:")
                        .append(_patientSeq)
                        .append(", AttachmentSeq:")
                        .append(_attachmentSeq)
                        .append(", UUID:")
                        .append(getUuid())
                        .append(", Attachment Path:")
                        .append(getVolume() + "\\")
                        .append(getPath() + getFilename())
                        .append("." + getFileext())
                        .append(", Mrn:")
                        .append(_mrn)
                        .append(", BillingTierId:")
                        .append(_billingTierId)
                        .append(", Facility:")
                        .append(_facility)
                        .toString();
    }
    
    
}
