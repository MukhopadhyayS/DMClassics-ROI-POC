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

import java.util.ArrayList;
import java.util.List;

import com.mckesson.eig.roi.base.model.BaseModel;

/**
 * @author OFS
 * @date Jul 26, 2012
 * @since Jul 26, 2012
 *
 */
public class SaveRequestPatientsList 
extends BaseModel {
    
    private static final long serialVersionUID = 1L;

    private long _requestId;
    
    private List<RequestPatient> _updatePatients = new ArrayList<RequestPatient>();

    private List <RequestEncounter> _updateEncounters =  new ArrayList<RequestEncounter>();
    private List <RequestDocument> _updateDocuments =  new ArrayList<RequestDocument>();
    private List <RequestVersion> _updateVersions =  new ArrayList<RequestVersion>();
    private List <RequestPage> _updatePages =  new ArrayList<RequestPage>();
    private List <RequestSupplementalDocument> _updateSupplementalDocument =
                                                       new ArrayList<RequestSupplementalDocument>();
    private List <RequestSupplementalAttachment> _updateSupplementalAttachement =
                                                     new ArrayList<RequestSupplementalAttachment>();
    
    private DeletePatientList _deletePatient;
    
    public List<RequestPatient> getUpdatePatients() {  return _updatePatients;   }
    public void setUpdatePatients(List<RequestPatient> updatePatients) {
        _updatePatients = updatePatients;  }

    public void setRequestId(long requestId) { this._requestId = requestId; }
    public long getRequestId() { return _requestId; }

    public DeletePatientList getDeletePatient() { return _deletePatient; }
    public void setDeletePatient(DeletePatientList deletePatient) { 
        _deletePatient = deletePatient;
    }
    
    public void setUpdateEncounters(List <RequestEncounter> updateEncounters) {
        _updateEncounters = updateEncounters; }
    public List <RequestEncounter> getUpdateEncounters() { return _updateEncounters; }

    public void setUpdateDocuments(List <RequestDocument> updateDocuments) {
        _updateDocuments = updateDocuments; }
    public List <RequestDocument> getUpdateDocuments() { return _updateDocuments; }

    public void setUpdateVersions(List <RequestVersion> updateVersions) {
        _updateVersions = updateVersions; }
    public List <RequestVersion> getUpdateVersions() { return _updateVersions; }

    public void setUpdatePages(List <RequestPage> updatePages) {
        _updatePages = updatePages; }
    public List <RequestPage> getUpdatePages() { return _updatePages; }
    
    public List <RequestSupplementalDocument> getUpdateSupplementalDocument() {
        return _updateSupplementalDocument;
    }
    public void setUpdateSupplementalDocument(
            List <RequestSupplementalDocument> updateSupplementalDocument) {
        _updateSupplementalDocument = updateSupplementalDocument;
    }
    
    public List <RequestSupplementalAttachment> getUpdateSupplementalAttachement() {
        return _updateSupplementalAttachement;
    }
    public void setUpdateSupplementalAttachement(
            List <RequestSupplementalAttachment> updateSupplementalAttachement) {
        _updateSupplementalAttachement = updateSupplementalAttachement;
    }
    
    @Override
    public String toString() {
        
        return new StringBuffer()
                    .append("RequestId:")
                    .append(_requestId)
                    .append(" , UpdatePatients List size :")
                    .append(_updatePatients.size())
                    .append(" , UpdateEncounters List size :")
                    .append(getUpdateEncounters().size())
                    .append(" , UpdateDocuments List size :")
                    .append(getUpdateDocuments().size())
                    .append(" , UpdateVersions List size :")
                    .append(getUpdateVersions().size())
                    .append(" , UpdatePages List size :")
                    .append(getUpdatePages().size())
                    .append(" , DeletePatient :")
                    .append(getDeletePatient())
                .toString();
    }

}
