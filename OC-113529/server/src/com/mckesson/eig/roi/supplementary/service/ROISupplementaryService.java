package com.mckesson.eig.roi.supplementary.service;

import com.mckesson.eig.roi.base.model.SearchCriteria;
import com.mckesson.eig.roi.supplementary.model.ROISupplementalAttachment;
import com.mckesson.eig.roi.supplementary.model.ROISupplementalAttachments;
import com.mckesson.eig.roi.supplementary.model.ROISupplementalDocument;
import com.mckesson.eig.roi.supplementary.model.ROISupplementalDocuments;
import com.mckesson.eig.roi.supplementary.model.ROISupplementalPatient;
import com.mckesson.eig.roi.supplementary.model.ROISupplementalPatients;
import com.mckesson.eig.roi.supplementary.model.ROISupplementarityAttachment;
import com.mckesson.eig.roi.supplementary.model.ROISupplementarityAttachments;
import com.mckesson.eig.roi.supplementary.model.ROISupplementarityDocument;
import com.mckesson.eig.roi.supplementary.model.ROISupplementarityDocuments;

public interface ROISupplementaryService {

    long createSupplementalPatient(ROISupplementalPatient patient);
    long createSupplementalDocument(ROISupplementalDocument document);
    ROISupplementalAttachments createSupplementalAttachment(ROISupplementalAttachment attachment);
    long createSupplementarityDocument(ROISupplementarityDocument document);
    ROISupplementarityAttachments createSupplementarityAttachment(ROISupplementarityAttachment attachment);

    ROISupplementalPatient getSupplementalPatient(long id, boolean isSearchRetrieve);
    ROISupplementalDocuments getSupplementalDocuments(long patientId);
    ROISupplementalAttachments getSupplementalAttachments(long patientId);
    ROISupplementarityDocuments getSupplementarityDocuments(String mrn, String facility);
    ROISupplementarityAttachments getSupplementarityAttachments(String mrn, String facility);

    long deleteSupplementalPatient (long patientId);
    long deleteSupplementalDocument (long docId,long requestId);
    long deleteSupplementalAttachment (long attachmentId,long requestId);
    long deleteSupplementarityDocument (long docId,long requestId,long patientId);
    long deleteSupplementarityAttachment (long attachmentId,long requestId,long patientId);

    long updateSupplementalPatient(ROISupplementalPatient patient);
    long updateSupplementalDocument(ROISupplementalDocument document);
    ROISupplementalAttachments updateSupplementalAttachment(ROISupplementalAttachment attachment);
    long updateSupplementarityDocument(ROISupplementarityDocument document);
    ROISupplementarityAttachments updateSupplementarityAttachment(ROISupplementarityAttachment attachment);

    ROISupplementalPatients searchSupplementalPatient(SearchCriteria criteria);
    boolean checkDuplicates(String lastName, String firstName, long supplementalId);
     
    String retrieveAttachmentPath(String uuid);
}
