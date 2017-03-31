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


package com.mckesson.eig.roi.request.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author OFS
 * @date Jul 26, 2012
 * @since Jul 26, 2012
 *
 */
public class DeletePatientList 
implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private List<Long> _pageSeq;
    private List<Long> _encounterSeq;
    private List<Long> _patientSeq;
    private List<Long> _docSeq;
    private List<Long> _versionSeq;
    private List<Long> _attachmentSeq;
    private List<Long> _documentSeq;

    // below are the sequence for the non hpf patients
    private List<Long> _supplementalAttachmentSeq;
    private List<Long> _supplementalDocumentSeq;
    private List<Long> _supplementalPatientSeq;
    
    private List<Long> _darPatientSeq;
    private List<Long> _darSuppPatientSeq;
    
    public List<Long> getPageSeq() { return _pageSeq; }
    public void setPageSeq(List<Long> pageSeq) { _pageSeq = pageSeq; }
    
    public List<Long> getEncounterSeq() { return _encounterSeq; }
    public void setEncounterSeq(List<Long> encounterSeq) { _encounterSeq = encounterSeq; }
    
    public List<Long> getPatientSeq() { return _patientSeq; }
    public void setPatientSeq(List<Long> patientSeq) { _patientSeq = patientSeq; }
    
    public List<Long> getDocSeq() { return _docSeq; }
    public void setDocSeq(List<Long> docSeq) { _docSeq = docSeq; }

    public void setVersionSeq(List<Long> versionSeq) { _versionSeq = versionSeq; }
    public List<Long> getVersionSeq() { return _versionSeq; }

    public void setDarPatientSeq(List<Long> darPatientSeq) { _darPatientSeq = darPatientSeq; }
    public List<Long> getDarPatientSeq() { return _darPatientSeq; }
    
    public List<Long> getDarSuppPatientSeq() { return _darSuppPatientSeq; }
    public void setDarSuppPatientSeq(List<Long> darSuppPatientSeq) {
        _darSuppPatientSeq = darSuppPatientSeq;
    }
    
    public List<Long> getAttachmentSeq() { return _attachmentSeq; }
    public void setAttachmentSeq(List<Long> attachmentSeq) { _attachmentSeq = attachmentSeq; }
    
    public List<Long> getDocumentSeq() { return _documentSeq; }
    public void setDocumentSeq(List<Long> documentSeq) { _documentSeq = documentSeq; }
    
    public List<Long> getSupplementalPatientSeq() { return _supplementalPatientSeq; }
    public void setSupplementalPatientSeq(List<Long> supplementalPatientSeq) {
        _supplementalPatientSeq = supplementalPatientSeq;
    }
    
    public List<Long> getSupplementalAttachmentSeq() { return _supplementalAttachmentSeq; }
    public void setSupplementalAttachmentSeq(List<Long> supplementalAttachmentSeq) {
        _supplementalAttachmentSeq = supplementalAttachmentSeq;
    }
    
    public List<Long> getSupplementalDocumentSeq() { return _supplementalDocumentSeq; }
    public void setSupplementalDocumentSeq(List<Long> supplementalDocumentSeq) {
        _supplementalDocumentSeq = supplementalDocumentSeq;
    }
    
}
