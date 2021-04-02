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

import com.mckesson.eig.roi.supplementary.model.ROIDocumentCommon;

/**
 * @author OFS
 * @date   Aug 9, 2012
 * @since  Aug 9, 2012
 */
public class RequestSupplementalDocument 
extends ROIDocumentCommon {

    private static final long serialVersionUID = 1L;

    private Long _documentCoreSeq;
    private Long _documentSeq;
    // HPF patients Sequence
    private Long _patientSeq;
    // Suppplemental Patients sequence
    private Long _supplementalId;
    
    private String _mrn;
    private String _facility;
    private Long _billingTierId;
    
    private boolean  _isSelectedForRelease;
    private boolean  _isReleased;
    
    public Long getDocumentCoreSeq() { return _documentCoreSeq; }
    public void setDocumentCoreSeq(Long documentCoreSeq) { _documentCoreSeq = documentCoreSeq; }
    
    public Long getDocumentSeq() { return _documentSeq; }
    public void setDocumentSeq(Long documentSeq) { _documentSeq = documentSeq; }
    
    public Long getPatientSeq() { return _patientSeq; }
    public void setPatientSeq(Long patientSeq) { _patientSeq = patientSeq; }
   
    public Long getSupplementalId() { return _supplementalId; }
    public void setSupplementalId(Long supplementalId) { _supplementalId = supplementalId; }
   
    public String getMrn() { return _mrn; }
    public void setMrn(String mrn) { _mrn = mrn; }
   
    public String getFacility() { return _facility; }
    public void setFacility(String facility) { _facility = facility; }
    
    public Long getBillingTierId() { return _billingTierId; }
    public void setBillingTierId(Long billingTierId) { _billingTierId = billingTierId; }
    
    public void setSelectedForRelease(boolean isSelectedForRelease) {
        _isSelectedForRelease = isSelectedForRelease; }
    public boolean isSelectedForRelease() { return _isSelectedForRelease; }

    public void setReleased(boolean isReleased) { _isReleased = isReleased; }
    public boolean isReleased() { return _isReleased; }
    
    public String toString() {
        return new StringBuffer()
                        .append("PatientSeq:")
                        .append(_patientSeq)
                        .append(", DocumentSeq:")
                        .append(_documentSeq)
                        .append(", DocName:")
                        .append(getDocName())
                        .append(", DocEncounter:")
                        .append(getEncounter())
                        .append(", BillingTierId:")
                        .append(_billingTierId)
                        .append(", Mrn:")
                        .append(_mrn)
                        .append(", Facility:")
                        .append(_facility)
                        .toString();
    }
    
}
