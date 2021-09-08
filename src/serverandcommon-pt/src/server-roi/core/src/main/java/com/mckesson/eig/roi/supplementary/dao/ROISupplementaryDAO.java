package com.mckesson.eig.roi.supplementary.dao;

import java.math.BigInteger;
import java.util.List;

import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.base.model.SearchCriteria;
import com.mckesson.eig.roi.request.model.RequestSupplementalAttachment;
import com.mckesson.eig.roi.request.model.RequestSupplementalDocument;
import com.mckesson.eig.roi.supplementary.model.ROISupplementalAttachment;
import com.mckesson.eig.roi.supplementary.model.ROISupplementalDocument;
import com.mckesson.eig.roi.supplementary.model.ROISupplementalPatient;
import com.mckesson.eig.roi.supplementary.model.ROISupplementarityAttachment;
import com.mckesson.eig.roi.supplementary.model.ROISupplementarityDocument;

public interface ROISupplementaryDAO extends ROIDAO {


    long createSupplementalPatient(ROISupplementalPatient patient);
    long createSupplementalDocument(ROISupplementalDocument patient);
    long createSupplementalAttachment(ROISupplementalAttachment patient);
    long createSupplementarityDocument(ROISupplementarityDocument patient);
    long createSupplementarityAttachment(ROISupplementarityAttachment patient);
    
    ROISupplementalPatient getSupplementalPatient(long id);
    List<ROISupplementalDocument> getSupplementalDocumentsByPatientId(long patientId);
    List<ROISupplementalAttachment> getSupplementalAttachmentsByPatientId(long patientId);
    List<ROISupplementarityDocument> getSupplementarityDocuments(String mrn, String facility);
    List<ROISupplementarityAttachment> getSupplementarityAttachments(String mrn, String facility);
    ROISupplementalDocument getSupplementalDocument(long id);
    ROISupplementalAttachment getSupplementalAttachment(long id);
    ROISupplementarityDocument getSupplementarityDocument(long id);
    ROISupplementarityAttachment getSupplementarityAttachment(long id);
    
    int deleteSupplementalPatient (long patientId);
    int deleteSupplementalDocument (long docId);
    int deleteSupplementalAttachment (long attachmentId);
    int deleteSupplementalDocumentByPatientId (long patientId);
    int deleteSupplementalAttachmentByPatientId (long patientId);
    int deleteSupplementarityDocument (long docId);
    int deleteSupplementarityAttachment (long attachmentId);

    boolean updateSupplementalPatient(ROISupplementalPatient patient);
    boolean updateSupplementalDocument(ROISupplementalDocument patient);
    boolean updateSupplementalAttachment(ROISupplementalAttachment patient);
    boolean updateSupplementarityDocument(ROISupplementarityDocument patient);
    boolean updateSupplementarityAttachment(ROISupplementarityAttachment patient);
    
    List<ROISupplementalPatient> searchSupplementalPatients(SearchCriteria criteria);
    
    boolean checkDuplicates(String lastName, String firstName, long supplementalId);
    
    String retrieveAttachmentPath(String uuid);
    public List<BigInteger> retrievePatientCoreIdByPatientId(long patientId);
    public List<RequestSupplementalDocument> getDocumentsCore(long patientId,boolean isSupplemental);
    public List<RequestSupplementalAttachment> getAttachmentsCore(long patientId,boolean isSupplemental);
}
