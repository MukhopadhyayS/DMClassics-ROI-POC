package com.mckesson.eig.roi.supplementary.service;

import java.io.File;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.mckesson.eig.roi.admin.dao.AttachmentDAO;
import com.mckesson.eig.roi.admin.model.AttachmentLocation;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.base.model.ROIAuditConstants;
import com.mckesson.eig.roi.base.model.ROIAuditConstants.SupplementaryAudit;
import com.mckesson.eig.roi.base.model.SearchCondition;
import com.mckesson.eig.roi.base.model.SearchCriteria;
import com.mckesson.eig.roi.base.service.BaseROIService;
import com.mckesson.eig.roi.ccd.provider.CcdProviderFactory;
import com.mckesson.eig.roi.ccd.provider.dao.CcdProviderDAO;
import com.mckesson.eig.roi.muroioutbound.model.ExternalSourceDocument;
import com.mckesson.eig.roi.request.dao.RequestCoreDAO;
import com.mckesson.eig.roi.request.model.FreeFormFacility;
import com.mckesson.eig.roi.request.model.RequestCore;
import com.mckesson.eig.roi.request.model.RequestSupplementalAttachment;
import com.mckesson.eig.roi.request.model.RequestSupplementalDocument;
import com.mckesson.eig.roi.supplementary.dao.ROISupplementaryDAO;
import com.mckesson.eig.roi.supplementary.model.ROIAttachmentCommon;
import com.mckesson.eig.roi.supplementary.model.ROIDocumentCommon;
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
import com.mckesson.eig.roi.utils.AccessFileLoader;
import com.mckesson.eig.roi.utils.DirectoryUtil;
import com.mckesson.eig.roi.utils.FileUtilities;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.utils.SpringUtil;
import com.mckesson.eig.utility.util.ConversionUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

public class ROISupplementaryServiceImpl
extends BaseROIService
implements ROISupplementaryService {

    private static final OCLogger LOG = new OCLogger(ROISupplementaryServiceImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static final String ENCOUNTER_SEARCH = "encounter";
    private static final String FACILITY_SEARCH = "facility";
    private static final String MRN_SEARCH = "mrn";

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #createSupplementalPatient(com.mckesson.eig.roi.supplementary.model.ROISupplementalPatient)
     */
    @Override
    public long createSupplementalPatient(ROISupplementalPatient patient) {
        final String logSM = "createSupplementalPatient(patient)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + patient);
        }
        try {

            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);

            retrieveOrCreateFreeFormFacility(patient.getFreeformFacility());
            setDefaultValues(dao, patient, true);
            long id = dao.createSupplementalPatient(patient);

            Timestamp date = dao.getDate();
            doAudit(getCreatePatientAuditMessage(patient), getUser()
                    .getInstanceId(), date,
                    SupplementaryAudit.CREATE_PATIENT.auditCode(),
                    patient.getFacility(), patient.getMrn() != null && !"".equalsIgnoreCase(patient.getMrn())
                    ? patient.getMrn() : null,
                    null);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + id);
            }
            return id;
        } catch (ROIException e) {
            LOG.warn(e.getLocalizedMessage());
            throw e;
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * retrievs the freeform facility is already exists
     * otherwise creates a new freeform facility.
     *
     * @param freeformFacility the freeform facility
     * @return retrieved or created freeform facility
     */
    private FreeFormFacility retrieveOrCreateFreeFormFacility(FreeFormFacility freeformFacility) {

        if (null == freeformFacility || null == freeformFacility.getFreeFormFacilityName()) {
            return null;
        }

        ROIDAO dao = getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
        String facilityName = freeformFacility.getFreeFormFacilityName();
        long userId = getUser().getInstanceId();

        FreeFormFacility facility = dao.retrieveFreeFormFacilitiesByName(facilityName, userId);

        if (null == facility) {

            setDefaultValues(dao, freeformFacility, true);
            dao.createFreeFormFacilities(freeformFacility);
        } else {

            LOG.debug("Freeform Facility:" + facilityName
                        + " already exists, Id:" + facility.getId());
            freeformFacility.setId(facility.getId());
        }
        return freeformFacility;
    }


    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #createSupplementalDocument(com.mckesson.eig.roi.supplementary.model.ROISupplementalDocument)
     */
    @Override
    public long createSupplementalDocument(ROISupplementalDocument document) {

        final String logSM = "createSupplementalDocument(document)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + document);
        }
        try {

            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);

            retrieveOrCreateFreeFormFacility(document.getFreeformFacility());
            setDefaultValues(dao, document, true);
            long id = dao.createSupplementalDocument(document);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + id);
            }
            Timestamp date = dao.getDate();
            ROISupplementalPatient patient = dao.getSupplementalPatient(document.getPatientId());
            doAudit(getCreateDocumentAuditMessage(document), getUser()
                    .getInstanceId(), date,
                    SupplementaryAudit.CREATE_DOCUMENT.auditCode(),
                    patient.getFacility(), patient.getMrn(),
                    document.getEncounter());

            return id;
        } catch (ROIException e) {
            LOG.warn(e.getLocalizedMessage());
            throw e;
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #createSupplementalAttachment(
     * com.mckesson.eig.roi.supplementary.model.ROISupplementalAttachment)
     */
    @Override
    public ROISupplementalAttachments createSupplementalAttachment(ROISupplementalAttachment attachment) {

        final String logSM = "createSupplementalAttachment(patient)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + attachment);
        }
        try {

            createAttachmentFile(attachment);
            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);

            retrieveOrCreateFreeFormFacility(attachment.getFreeformFacility());
            //Document Facility and Patient Facility are same while adding the attachment to nonHPF Patient
            attachment.setFacility(attachment.getFacility() == null ? attachment.getDocFacility()
                                                                : attachment.getFacility());
            setDefaultValues(dao, attachment, true);
            long id = dao.createSupplementalAttachment(attachment);
            attachment.setId(id);

            Timestamp date = dao.getDate();
            ROISupplementalPatient patient = dao.getSupplementalPatient(attachment.getPatientId());
            if (StringUtilities.isEmpty(attachment.getExternalSource())) {
                doAudit(getCreateAttachmentAuditMessage(attachment), getUser()
                        .getInstanceId(), date,
                        SupplementaryAudit.CREATE_ATTACHMENT.auditCode(),
                        patient.getFacility(), patient.getMrn(),
                        attachment.getEncounter());
            } else {
                doAudit(getCreateAttachmentExtSourceAuditMessage(attachment,
                        attachment.getExternalSource()), getUser()
                        .getInstanceId(), date,
                        SupplementaryAudit.CREATE_ATTACHMENT_EXT_SOURCE
                                .auditCode(), patient.getFacility(),
                        patient.getMrn(), attachment.getEncounter());
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + id);
            }
            List<ROISupplementalAttachment> attachments = new ArrayList<ROISupplementalAttachment>();
            attachments.add(0, attachment);
            return new ROISupplementalAttachments(attachments);
        } catch (ROIException e) {
            LOG.warn(e.getLocalizedMessage());
            throw e;
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #createSupplementarityDocument(
     * com.mckesson.eig.roi.supplementary.model.ROISupplementarityDocument)
     */
    @Override
    public long createSupplementarityDocument(ROISupplementarityDocument document) {

        final String logSM = "createSupplementarityDocument(document)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + document);
        }
        try {

            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);

            retrieveOrCreateFreeFormFacility(document.getFreeformFacility());
            setDefaultValues(dao, document, true);
            long id = dao.createSupplementarityDocument(document);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + id);
            }
            Timestamp date = dao.getDate();
            doAudit(getCreateDocumentAuditMessage(document), getUser()
                    .getInstanceId(), date,
                    SupplementaryAudit.CREATE_DOCUMENT.auditCode(),
                    document.getFacility(), document.getMrn(),
                    document.getEncounter());

            return id;
        } catch (ROIException e) {
            LOG.warn(e.getLocalizedMessage());
            throw e;
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #createSupplementarityAttachment(
     * com.mckesson.eig.roi.supplementary.model.ROISupplementarityAttachment)
     */
    @Override
    public ROISupplementarityAttachments createSupplementarityAttachment(ROISupplementarityAttachment attachment) {

        final String logSM = "createSupplementarityAttachment(attachment)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + attachment);
        }

        try {

            createAttachmentFile(attachment);
            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            setDefaultValues(dao, attachment, true);
            long id = dao.createSupplementarityAttachment(attachment);
            attachment.setId(id);

            Timestamp date = dao.getDate();
            if (StringUtilities.isEmpty(attachment.getExternalSource())) {
                doAudit(getCreateAttachmentAuditMessage(attachment), getUser()
                        .getInstanceId(), date,
                        SupplementaryAudit.CREATE_ATTACHMENT.auditCode(),
                        attachment.getFacility(), attachment.getMrn(),
                        attachment.getEncounter());
            } else {
                doAudit(getCreateAttachmentExtSourceAuditMessage(attachment,
                        attachment.getExternalSource()), getUser()
                        .getInstanceId(), date,
                        SupplementaryAudit.CREATE_ATTACHMENT_EXT_SOURCE
                                .auditCode(), attachment.getFacility(),
                        attachment.getMrn(), attachment.getEncounter());
            }
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + id);
            }

            List<ROISupplementarityAttachment> attachments = new ArrayList<ROISupplementarityAttachment>();
            attachments.add(0, attachment);
            return new ROISupplementarityAttachments(attachments);
        } catch (ROIException e) {
            LOG.warn(e.getLocalizedMessage());
            throw e;
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #getSupplementalPatient(long)
     */
    @Override
    public ROISupplementalPatient getSupplementalPatient(long id, boolean isSearchRetrieve) {

        final String logSM = "getROISupplementalPatient(id)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + id);
        }

        try {

            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            ROISupplementalPatient patient = dao.getSupplementalPatient(id);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + patient);
            }
            return patient;
        } catch (ROIException e) {
            LOG.warn(e.getLocalizedMessage());
            throw e;
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #getSupplementalDocuments(long)
     */
    @Override
    public ROISupplementalDocuments getSupplementalDocuments(long patientId) {

        final String logSM = "getSupplementalDocuments(patientId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + patientId);
        }

        try {

            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            List<ROISupplementalDocument> documents = dao
                    .getSupplementalDocumentsByPatientId(patientId);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + documents);
            }
            return new ROISupplementalDocuments(documents);

        } catch (ROIException e) {
            LOG.warn(e.getLocalizedMessage());
            throw e;
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #getSupplementalAttachments(long)
     */
    @Override
    public ROISupplementalAttachments getSupplementalAttachments(long patientId) {

        final String logSM = "getSupplementalAttachments(patientId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + patientId);
        }

        try {

            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            List<ROISupplementalAttachment> attachments = dao
                    .getSupplementalAttachmentsByPatientId(patientId);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + attachments);
            }
            return new ROISupplementalAttachments(attachments);

        } catch (ROIException e) {
            LOG.warn(e.getLocalizedMessage());
            throw e;
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #getSupplementarityDocuments(java.lang.String, java.lang.String)
     */
    @Override
    public ROISupplementarityDocuments getSupplementarityDocuments(String mrn, String facility) {

        final String logSM = "getSupplementarityDocuments(mrn, facility)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + mrn + "," + facility);
        }

        try {

            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            List<ROISupplementarityDocument> documents = dao
                    .getSupplementarityDocuments(mrn, facility);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + documents);
            }

            return new ROISupplementarityDocuments(documents);

        } catch (ROIException e) {
            LOG.warn(e.getLocalizedMessage());
            throw e;
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #getSupplementarityAttachments(java.lang.String, java.lang.String)
     */
    @Override
    public ROISupplementarityAttachments getSupplementarityAttachments(String mrn,
                                                                       String facility) {

        final String logSM = "getSupplementarityAttachments(mrn, facility)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + mrn + "," + facility);
        }

        try {

            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            List<ROISupplementarityAttachment> attachments = dao
                    .getSupplementarityAttachments(mrn, facility);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + attachments);
            }

            return new ROISupplementarityAttachments(attachments);

        } catch (ROIException e) {
            LOG.warn(e.getLocalizedMessage());
            throw e;
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #deleteSupplementalPatient(long)
     */
    @Override
    public long deleteSupplementalPatient(long patientId) {

        final String logSM = "deleteSupplementalPatient(patientId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + patientId);
        }

        try {

            // TODO: check the patient has been released document or attachment
            // and cannot be deleted.
            ROISupplementalPatient patient = getSupplementalPatient(patientId,false);
            deleteAttachmentFilesByPatientId(patientId);
            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            dao.deleteSupplementalDocumentByPatientId(patientId);
            dao.deleteSupplementalAttachmentByPatientId(patientId);
            long result = dao.deleteSupplementalPatient(patientId);
            if (patient != null) {
                Timestamp date = dao.getDate();
                doAudit(getDeletePatientAuditMessage(patient), getUser()
                        .getInstanceId(), date,
                        SupplementaryAudit.DELETE_PATIENT.auditCode(),
                        patient.getFacility(), patient.getMrn(),
                        ROIAuditConstants.GLOBAL_FACILITY);
            }
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return result;
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in deleteSupplementalPatient",e);
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #deleteSupplementalDocument(long,long)
     */
    @Override
    public long deleteSupplementalDocument(long docId,long requestId) {

        final String logSM = "deleteSupplementalDocument(docId,requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + docId + requestId);
        }

        try {

            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            ROISupplementalDocument document = dao
                    .getSupplementalDocument(docId);
            List<RequestSupplementalDocument> documentCore = new ArrayList<RequestSupplementalDocument>();
            List<BigInteger> patientCoreSeqList = dao.retrievePatientCoreIdByPatientId(document
                    .getPatientId());
            for(BigInteger patientCoreSeq : patientCoreSeqList)
            {
                documentCore = dao.getDocumentsCore(patientCoreSeq.longValue(),true);
                break;
            }
            ROISupplementalPatient patient = dao.getSupplementalPatient(document
                    .getPatientId());
            // isReleased flag will check whether the document or attachment has been released or not
            boolean isReleased = isAttachmentOrDocumentReleased(documentCore,requestId,true);
            if(isReleased)
               throw new ROIException(ROIClientErrorCodes.ROI_ALREADY_RELEASED_DOCUMENT);
            Timestamp date = dao.getDate();
            doAudit(getDeleteDocumentAuditMessage(document), getUser()
                    .getInstanceId(), date,
                    SupplementaryAudit.DELETE_DOCUMENT.auditCode(),
                    patient.getFacility(), patient.getMrn(),
                    document.getEncounter());
            long result = dao.deleteSupplementalDocument(docId);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return result;
        } catch (ROIException e) {
            if(ROIClientErrorCodes.ROI_ALREADY_RELEASED_DOCUMENT.toString()
                    .equalsIgnoreCase(e.getErrorCode()))
               throw new ROIException(ROIClientErrorCodes.ROI_ALREADY_RELEASED_DOCUMENT);
            else
                throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in deleteSupplementalDocument",e);
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #deleteSupplementalAttachment(long,long)
     */
    @Override
    public long deleteSupplementalAttachment(long attachmentId,long requestId) {

        final String logSM = "deleteSupplementalAttachment(attachmentId,requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + attachmentId + requestId);
        }

        try {

            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            ROISupplementalAttachment p = dao
                    .getSupplementalAttachment(attachmentId);
            List<RequestSupplementalAttachment> attachmentCore = new ArrayList<RequestSupplementalAttachment>();
            List<BigInteger> patientCoreSeqList = dao.retrievePatientCoreIdByPatientId(p
                    .getPatientId());
            for(BigInteger patientCoreSeq : patientCoreSeqList)
            {
                attachmentCore = dao.getAttachmentsCore(patientCoreSeq.longValue(),true);
                break;
            }
            // isReleased flag will check whether the document or attachment has been released or not
            boolean isReleased = isAttachmentOrDocumentReleased(attachmentCore,requestId,false);
            if(isReleased)
               throw new ROIException(ROIClientErrorCodes.ROI_ALREADY_RELEASED_ATTACHMENT);
            if (p != null) {
                deleteAttachmentFile(p);
            }
            ROISupplementalPatient patient = dao.getSupplementalPatient(p
                    .getPatientId());
            Timestamp date = dao.getDate();
            doAudit(getDeleteAttachmentAuditMessage(p), getUser()
                    .getInstanceId(), date,
                    SupplementaryAudit.DELETE_ATTACHMENT.auditCode(),
                    patient.getFacility(), patient.getMrn(), p.getEncounter());
            long result = dao.deleteSupplementalAttachment(attachmentId);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return result;
        } catch (ROIException e) {
            if(ROIClientErrorCodes.ROI_ALREADY_RELEASED_ATTACHMENT.toString()
                    .equalsIgnoreCase(e.getErrorCode()))
               throw new ROIException(ROIClientErrorCodes.ROI_ALREADY_RELEASED_ATTACHMENT);
            else
                throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in deleteSupplementalAttachment",e);
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #deleteSupplementarityDocument(long,long,long)
     */
    @Override
    public long deleteSupplementarityDocument(long docId,long requestId,long patientId) {

        final String logSM = "deleteSupplementarityDocument(docId,requestId,patientId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + docId + requestId + patientId);
        }

        try {

            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            ROISupplementarityDocument document = dao
                    .getSupplementarityDocument(docId);
            List<RequestSupplementalDocument> documentCore = dao.getDocumentsCore(patientId,false);
            // isReleased flag will check whether the document or attachment has been released or not
            boolean isReleased = isAttachmentOrDocumentReleased(documentCore,requestId,true);
            if(isReleased)
               throw new ROIException(ROIClientErrorCodes.ROI_ALREADY_RELEASED_DOCUMENT);
            Timestamp date = dao.getDate();
            doAudit(getDeleteDocumentAuditMessage(document), getUser()
                    .getInstanceId(), date,
                    SupplementaryAudit.DELETE_DOCUMENT.auditCode(),
                    document.getFacility(), document.getMrn(),
                    document.getEncounter());
            long result = dao.deleteSupplementarityDocument(docId);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return result;
        } catch (ROIException e) {
            if(ROIClientErrorCodes.ROI_ALREADY_RELEASED_DOCUMENT.toString()
                    .equalsIgnoreCase(e.getErrorCode()))
               throw new ROIException(ROIClientErrorCodes.ROI_ALREADY_RELEASED_DOCUMENT);
            else
                throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in deleteSupplementarityDocument",e);
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #deleteSupplementarityAttachment(long,long,long)
     */
    @Override
    public long deleteSupplementarityAttachment(long attachmentId,long requestId,long patientId) {

        final String logSM = "deleteSupplementarityAttachment(attachmentId,requestId,patientId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + attachmentId + requestId + patientId);
        }

        try {

            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            ROISupplementarityAttachment p = dao
                    .getSupplementarityAttachment(attachmentId);
            List<RequestSupplementalAttachment> attachmentCore = dao.getAttachmentsCore(patientId,false);
            // isReleased flag will check whether the document or attachment has been released or not
            boolean isReleased = isAttachmentOrDocumentReleased(attachmentCore,requestId,false);
            if(isReleased)
               throw new ROIException(ROIClientErrorCodes.ROI_ALREADY_RELEASED_ATTACHMENT);
            if (p != null) {
                deleteAttachmentFile(p);
            }
            Timestamp date = dao.getDate();
            doAudit(getDeleteAttachmentAuditMessage(p), getUser()
                    .getInstanceId(), date,
                    SupplementaryAudit.DELETE_ATTACHMENT.auditCode(),
                    p.getFacility(), p.getMrn(), p.getEncounter());
            long result = dao.deleteSupplementarityAttachment(attachmentId);
            sendExternalSourceCancelledStatistic(p, date);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return result;
        } catch (ROIException e) {
            if(ROIClientErrorCodes.ROI_ALREADY_RELEASED_ATTACHMENT.toString()
                    .equalsIgnoreCase(e.getErrorCode()))
               throw new ROIException(ROIClientErrorCodes.ROI_ALREADY_RELEASED_ATTACHMENT);
            else
                throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in deleteSupplementarityAttachment",e);
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #updateSupplementalPatient(com.mckesson.eig.roi.supplementary.model.ROISupplementalPatient)
     */
    @Override
    public long updateSupplementalPatient(ROISupplementalPatient patient) {

        final String logSM = "updateSupplementalPatient(patient)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + patient);
        }

        try {

            retrieveOrCreateFreeFormFacility(patient.getFreeformFacility());
            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            ROISupplementalPatient p = dao.getSupplementalPatient(patient.getId());
            if (p == null) {
                throw new ROIException(
                        ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
            }
            Timestamp date = dao.getDate();
            doAudit(getEditPatientAuditMessage(p, patient), getUser()
                    .getInstanceId(), date,
                    SupplementaryAudit.EDIT_PATIENT.auditCode(),
                    patient.getFacility(), patient.getMrn() != null && !"".equalsIgnoreCase(patient.getMrn())
                    ? patient.getMrn() : null,
                    null);
            p.copy(patient);
            setDefaultValues(dao, p, false);
            dao.updateSupplementalPatient(p);
        } catch (ROIException e) {
            LOG.warn(e.getLocalizedMessage());
            throw e;
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
        return 0;
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #updateSupplementalDocument(com.mckesson.eig.roi.supplementary.model.ROISupplementalDocument)
     */
    @Override
    public long updateSupplementalDocument(ROISupplementalDocument document) {

        final String logSM = "updateSupplementalDocument(patient)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + document);
        }

        try {

            retrieveOrCreateFreeFormFacility(document.getFreeformFacility());
            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            ROISupplementalDocument p = dao.getSupplementalDocument(document
                    .getId());
            if (p == null) {
                throw new ROIException(
                        ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
            }
            ROISupplementalPatient patient = getSupplementalPatient(document.getPatientId(), false);
            Timestamp date = dao.getDate();
            doAudit(getEditDocumentAuditMessage(p, document), getUser()
                    .getInstanceId(), date,
                    SupplementaryAudit.EDIT_DOCUMENT.auditCode(),
                    patient.getFacility(), patient.getMrn(),
                    document.getEncounter());
            p.copy(document);
            setDefaultValues(dao, p, false);
            dao.updateSupplementalDocument(p);
        } catch (ROIException e) {
            LOG.warn(e.getLocalizedMessage());
            throw e;
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
        return 0;
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #updateSupplementalAttachment(
     * com.mckesson.eig.roi.supplementary.model.ROISupplementalAttachment)
     */
    @Override
    public ROISupplementalAttachments updateSupplementalAttachment(ROISupplementalAttachment attachment) {

        final String logSM = "updateSupplementalAttachment(attachment)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + attachment);
        }

        try {

            retrieveOrCreateFreeFormFacility(attachment.getFreeformFacility());
            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            ROISupplementalAttachment p = dao
                    .getSupplementalAttachment(attachment.getId());
            if (p == null) {
                throw new ROIException(
                        ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
            }
            ROISupplementalPatient patient = getSupplementalPatient(attachment.getPatientId(),
                                                                    false);
            Timestamp date = dao.getDate();
            doAudit(getEditAttachmentAuditMessage(attachment), getUser()
                    .getInstanceId(), date,
                    SupplementaryAudit.EDIT_ATTACHMENT.auditCode(),
                    patient.getFacility(), patient.getMrn(),
                    attachment.getEncounter());
            p.copy(attachment);
            setDefaultValues(dao, p, false);
            dao.updateSupplementalAttachment(p);
            List<ROISupplementalAttachment> attachments = new ArrayList<ROISupplementalAttachment>();
            attachments.add(0, p);

            return new ROISupplementalAttachments(attachments);

        } catch (ROIException e) {
            LOG.warn(e.getLocalizedMessage());
            throw e;
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #updateSupplementarityDocument(
     * com.mckesson.eig.roi.supplementary.model.ROISupplementarityDocument)
     */
    @Override
    public long updateSupplementarityDocument(ROISupplementarityDocument document) {

        final String logSM = "updateSupplementarityDocument(patient)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + document);
        }

        try {

            retrieveOrCreateFreeFormFacility(document.getFreeformFacility());

            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            ROISupplementarityDocument p = dao
                    .getSupplementarityDocument(document.getId());
            if (p == null) {
                throw new ROIException(
                        ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
            }
            Timestamp date = dao.getDate();
            doAudit(getEditDocumentAuditMessage(p, document), getUser()
                    .getInstanceId(), date,
                    SupplementaryAudit.EDIT_DOCUMENT.auditCode(),
                    document.getFacility(), document.getMrn(),
                    document.getEncounter());
            p.copy(document);
            setDefaultValues(dao, p, false);
            dao.updateSupplementarityDocument(p);
        } catch (ROIException e) {
            LOG.warn(e.getLocalizedMessage());
            throw e;
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
        return 0;
    }

    /**
     * Update supplementarity attachment.
     *
     * @param attachment the attachment
     * @return the long
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #updateSupplementarityAttachment(
     * com.mckesson.eig.roi.supplementary.model.ROISupplementarityAttachment)
     */
    @Override
    public ROISupplementarityAttachments updateSupplementarityAttachment(ROISupplementarityAttachment attachment) {

        final String logSM = "updateSupplementalAttachment(attachment)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + attachment);
        }

        try {

            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            ROISupplementarityAttachment p = dao
                    .getSupplementarityAttachment(attachment.getId());
            if (p == null) {
                throw new ROIException(
                        ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
            }
            Timestamp date = dao.getDate();
            doAudit(getEditAttachmentAuditMessage(attachment), getUser()
                    .getInstanceId(), date,
                    SupplementaryAudit.EDIT_ATTACHMENT.auditCode(),
                    attachment.getFacility(), attachment.getMrn(),
                    attachment.getEncounter());
            p.copy(attachment);
            setDefaultValues(dao, p, false);
            dao.updateSupplementarityAttachment(p);

            List<ROISupplementarityAttachment> attachments = new ArrayList<ROISupplementarityAttachment>();
            attachments.add(0, p);

            return new ROISupplementarityAttachments(attachments);

        } catch (ROIException e) {
            LOG.warn(e.getLocalizedMessage());
            throw e;
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #searchSupplementalPatient(com.mckesson.eig.roi.base.model.SearchCriteria)
     */
    @Override
    public ROISupplementalPatients searchSupplementalPatient(SearchCriteria criteria) {

        final String logSM = "searchSupplementalPatient(criteria)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + criteria);
        }
        String encounter = null;
        String facility = null;
        String mrn = null;

        try {

            StringBuffer comment = new StringBuffer("Search Performed in ROI. ")
                                        .append("Type of Search:- Patient. ")
                                        .append("Search Criteria entered:- ");

            for (SearchCondition condition : criteria.getConditions()) {

                comment.append(condition.getKey())
                       .append(": ")
                       .append(condition.getValue())
                       .append(", ");

                if (ENCOUNTER_SEARCH.equalsIgnoreCase(condition.getKey())) {
                    encounter = condition.getValue();
                } else if (FACILITY_SEARCH.equalsIgnoreCase(condition.getKey())) {
                    facility = condition.getValue();
                } else if (MRN_SEARCH.equalsIgnoreCase(condition.getKey())) {
                    mrn = condition.getValue();
                }
            }

            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            List<ROISupplementalPatient> patients = dao.searchSupplementalPatients(criteria);

            if (StringUtilities.isEmpty(facility)) { comment.append("Facility: All, "); }
            comment = comment.replace(comment.length() - 2, comment.length() - 1,
                                      ROIConstants.SEPERATOR);

            // It will create the search patient audit information in Audit_Trail table.
            doAudit(comment.toString(),
                    getUser().getInstanceId(),
                    dao.getDate(),
                    ROIConstants.AUDIT_ACTION_CODE_SEARCH,
                    facility == null ? ROIConstants.DEFAULT_FACILITY : facility.trim(),
                    mrn,
                    encounter);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + patients);
            }
            return new ROISupplementalPatients(patients);

        } catch (ROIException e) {
            LOG.warn(e.getLocalizedMessage());
            throw e;
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * Check duplicates.
     *
     * @param lastName the last name
     * @param firstName the first name
     * @param supplementalId the supplemental id
     * @return true, if successful
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #checkDuplicates(java.lang.String, java.lang.String, long)
     */
    @Override
    public boolean checkDuplicates(String lastName, String firstName, long supplementalId) {

        try {

            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            return dao.checkDuplicates(lastName, firstName, supplementalId);
        } catch (ROIException e) {
            LOG.warn(e.getLocalizedMessage());
            throw e;
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * Delete attachment files by patient id.
     *
     * @param patientId the patient id
     */
    private void deleteAttachmentFilesByPatientId(long patientId) {

        ROISupplementalAttachments attachments = getSupplementalAttachments(patientId);
        for (ROISupplementalAttachment a : attachments.getAttachments()) {
            deleteAttachmentFile(a);
        }
    }

    /**
     * Creates the attachment file.
     *
     * @param attachment the attachment
     */
    private void createAttachmentFile(ROIAttachmentCommon attachment) {
        try {
            String volume = getVolume();
            if (StringUtilities.isEmpty(volume)) {
                throw new ROIException(ROIClientErrorCodes.ATTACHMENT_LOCATION_NULL);
            }
            String tempPath = DirectoryUtil.getCacheDirectory();
            copyToDestination(volume, tempPath, attachment);
        } catch (Exception e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * Gets the volume.
     *
     * @return the volume
     */
    private String getVolume() {
        AttachmentDAO dao = (AttachmentDAO) getDAO(DAOName.ATTACHMENT_DAO);
        AttachmentLocation location = dao.retrieveAttachmentLocation();
        if (location == null) {
            return null;
        }
        return location.getAttachmentLocation();
    }

    /**
     * Copy to destination.
     *
     * @param volume the volume
     * @param tempPath the temp path
     * @param attachment the attachment
     * @throws Exception the exception
     */
    private void copyToDestination(String volume, String tempPath,
            ROIAttachmentCommon attachment) throws Exception {
        String filename = attachment.getUuid();
        attachment.setVolume(volume.trim());
        attachment.setPathForUuid(filename);
		//DE7315 External Control of File Name or Path
        File src = AccessFileLoader.getFile(tempPath + File.separator + filename);
        File destDir = AccessFileLoader.getFile(volume.trim() + File.separator + attachment.getPath());
        FileUtilities.move(src, destDir);
    }

    /**
     * Delete attachment file.
     *
     * @param attachment the attachment
     */
    private void deleteAttachmentFile(ROIAttachmentCommon attachment) {
        try {
            File destFileName = AccessFileLoader.getFile(attachment.getVolume()
                    + File.separator + attachment.getPath() + File.separator
                    + attachment.getUuid());
            FileUtilities.delete(destFileName);
        } catch (Exception e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * Send external source cancelled statistic.
     *
     * @param attachment the attachment
     * @param date the date
     */
    private void sendExternalSourceCancelledStatistic(ROIAttachmentCommon attachment,
                                                      Timestamp date) {

        List<ExternalSourceDocument> extSourceDocument =
                            getDao().getExternalSourceDocumentsByFileName(attachment.getUuid());

        for (ExternalSourceDocument extSource : extSourceDocument) {
            getCcdProviderFactory().sendStatisticsForDeleteAttachment(extSource);
            break;
        }
    }

    /**
     * Gets the creates the patient audit message.
     *
     * @param patient the patient
     * @return the creates the patient audit message
     */
    private String getCreatePatientAuditMessage(ROISupplementalPatient patient) {
        String fullName = patient.getLastName() + ", " + patient.getFirstName();
        String userName = getUser().getLoginId().trim();
        String auditMsg = SupplementaryAudit.CREATE_PATIENT.auditString();
        MessageFormat mf = new MessageFormat(auditMsg);
        Object[] params = {userName, fullName};
        String result = mf.format(params);
        if (patient.isNewCountry()) {
            result += ". Country Code-Country set to "
                    + patient.getCountryCode() + " - "
                    + patient.getCountryName();
        }
        return result;
    }

    /**
     * Gets the edits the patient audit message.
     *
     * @param existingPatient the existing patient
     * @param newPatient the new patient
     * @return the edits the patient audit message
     */
    private String getEditPatientAuditMessage(
            ROISupplementalPatient existingPatient,
            ROISupplementalPatient newPatient) {
        String oldFullName = existingPatient.getLastName() + ", "
                + existingPatient.getFirstName();
        String newFullName = newPatient.getLastName() + ", "
                + newPatient.getFirstName();
        String oldAddress = getAddress(existingPatient);
        String newAddress = getAddress(newPatient);
        String oldVip = String.valueOf(ConversionUtilities.toBooleanValue(
                existingPatient.isVip(), false));
        String newVip = String.valueOf(ConversionUtilities.toBooleanValue(
                newPatient.isVip(), false));
        String userName = getUser().getLoginId().trim();
        String auditMsg = SupplementaryAudit.EDIT_PATIENT.auditString();
        MessageFormat mf = new MessageFormat(auditMsg);
        Object[] params = {userName, oldFullName, oldAddress, oldVip,
                newFullName, newAddress, newVip};
        return mf.format(params);
    }

    /**
     * Gets the address.
     *
     * @param patient the patient
     * @return the address
     */
    private String getAddress(ROISupplementalPatient patient) {
        String address1 = patient.getAddress1();
        String address2 = patient.getAddress2();
        String address3 = patient.getAddress3();
        String city = patient.getCity();
        String state = patient.getState();
        String zip = patient.getZip();
        String result = "";
        if (!StringUtilities.isEmpty(address1)) {
            result += address1;
        }
        if (!StringUtilities.isEmpty(address2)) {
            result += "," + address2;
        }
        if (!StringUtilities.isEmpty(address3)) {
            result += "," + address3;
        }
        if (!StringUtilities.isEmpty(city)) {
            result += "," + city;
        }
        if (!StringUtilities.isEmpty(state)) {
            result += "," + state;
        }
        if (!StringUtilities.isEmpty(zip)) {
            result += "," + zip;
        }
        if (patient.isNewCountry()) {
            result += "," + patient.getCountryCode() + " - "
                    + patient.getCountryName();
        }
        return result;
    }

    /**
     * Gets the delete patient audit message.
     *
     * @param patient the patient
     * @return the delete patient audit message
     */
    private String getDeletePatientAuditMessage(ROISupplementalPatient patient) {
        String fullName = patient.getLastName() + ", " + patient.getFirstName();
        String userName = getUser().getLoginId().trim();
        String auditMsg = SupplementaryAudit.DELETE_PATIENT.auditString();
        MessageFormat mf = new MessageFormat(auditMsg);
        Object[] params = {userName, fullName};
        return mf.format(params);
    }

    /**
     * Gets the creates the document audit message.
     *
     * @param document the document
     * @return the creates the document audit message
     */
    private String getCreateDocumentAuditMessage(ROIDocumentCommon document) {
        String docName = document.getDocName();
        String userName = getUser().getLoginId().trim();
        String auditMsg = SupplementaryAudit.CREATE_DOCUMENT.auditString();
        MessageFormat mf = new MessageFormat(auditMsg);
        Object[] params = {userName, docName};
        return mf.format(params);
    }

    /**
     * Gets the edits the document audit message.
     *
     * @param existingDocument the existing document
     * @param newDocument the new document
     * @return the edits the document audit message
     */
    private String getEditDocumentAuditMessage(
            ROIDocumentCommon existingDocument, ROIDocumentCommon newDocument) {
        String oldName = getEmptyStrForNull(existingDocument.getDocName());
        String oldFacility = getEmptyStrForNull(existingDocument
                .getDocFacility());
        String oldDepartment = getEmptyStrForNull(existingDocument
                .getDepartment());
        String oldDateofService = getEmptyStrForNull(existingDocument
                .getDateOfService());
        String oldComment = getEmptyStrForNull(existingDocument.getComment());
        String newName = getEmptyStrForNull(newDocument.getDocName());
        String newFacility = getEmptyStrForNull(newDocument.getDocFacility());
        String newDepartment = getEmptyStrForNull(newDocument.getDepartment());
        String newDateofService = getEmptyStrForNull(newDocument
                .getDateOfService());
        String newComment = getEmptyStrForNull(existingDocument.getComment());

        String userName = getUser().getLoginId().trim();
        String auditMsg = SupplementaryAudit.EDIT_DOCUMENT.auditString();
        MessageFormat mf = new MessageFormat(auditMsg);
        Object[] params = {userName, oldName, oldFacility, oldDepartment,
                oldDateofService, oldComment, newName, newFacility,
                newDepartment, newDateofService, newComment};
        return mf.format(params);
    }

    /**
     * Gets the empty str for null.
     *
     * @param str the str
     * @return the empty str for null
     */
    private String getEmptyStrForNull(String str) {
        return StringUtilities.isEmpty(str) ? StringUtilities.EMPTYSTRING : str;
    }

    /**
     * Gets the empty str for null.
     *
     * @param obj the obj
     * @return the empty str for null
     */
    private String getEmptyStrForNull(Object obj) {
        return obj == null ? StringUtilities.EMPTYSTRING : obj.toString();
    }

    /**
     * Gets the delete document audit message.
     *
     * @param document the document
     * @return the delete document audit message
     */
    private String getDeleteDocumentAuditMessage(ROIDocumentCommon document) {
        String docName = document.getDocName();
        String userName = getUser().getLoginId().trim();
        String auditMsg = SupplementaryAudit.DELETE_DOCUMENT.auditString();
        MessageFormat mf = new MessageFormat(auditMsg);
        Object[] params = {userName, docName};
        return mf.format(params);
    }

    /**
     * Gets the creates the attachment audit message.
     *
     * @param attachment the attachment
     * @return the creates the attachment audit message
     */
    private String getCreateAttachmentAuditMessage(
            ROIAttachmentCommon attachment) {
        String docName = attachment.getType();
        String subtitle = attachment.getSubtitle();
        String auditMsg = SupplementaryAudit.CREATE_DOCUMENT.auditString();
        MessageFormat mf = new MessageFormat(auditMsg);
        Object[] params = {docName, subtitle};
        return mf.format(params);
    }

    /**
     * Gets the creates the attachment ext source audit message.
     *
     * @param attachment the attachment
     * @param externalSource the external source
     * @return the creates the attachment ext source audit message
     */
    private String getCreateAttachmentExtSourceAuditMessage(
            ROIAttachmentCommon attachment, String externalSource) {
        String type = attachment.getType();
        String subtitle = attachment.getSubtitle();
        String auditMsg = SupplementaryAudit.CREATE_ATTACHMENT_EXT_SOURCE
                .auditString();
        MessageFormat mf = new MessageFormat(auditMsg);
        Object[] params = {type, subtitle, externalSource};
        return mf.format(params);
    }

    /**
     * Gets the edits the attachment audit message.
     *
     * @param attachment the attachment
     * @return the edits the attachment audit message
     */
    private String getEditAttachmentAuditMessage(ROIAttachmentCommon attachment) {
        String type = attachment.getType();
        String subtitle = attachment.getSubtitle();
        String auditMsg = SupplementaryAudit.EDIT_DOCUMENT.auditString();
        MessageFormat mf = new MessageFormat(auditMsg);
        Object[] params = {type, subtitle};
        return mf.format(params);
    }

    /**
     * Gets the delete attachment audit message.
     *
     * @param attachment the attachment
     * @return the delete attachment audit message
     */
    private String getDeleteAttachmentAuditMessage(
            ROIAttachmentCommon attachment) {
        String type = attachment.getType();
        String subtitle = attachment.getSubtitle();
        String auditMsg = SupplementaryAudit.DELETE_ATTACHMENT.auditString();
        MessageFormat mf = new MessageFormat(auditMsg);
        Object[] params = {type, subtitle};
        return mf.format(params);
    }

    /**
     * Gets the ccd provider factory.
     *
     * @return the ccd provider factory
     */
    private CcdProviderFactory getCcdProviderFactory() {
        return (CcdProviderFactory) SpringUtil
                .getObjectFromSpring("ccdProviderFactory");
    }

    /**
     * Gets the dao.
     *
     * @return the dao
     */
    private CcdProviderDAO getDao() {
        return (CcdProviderDAO) SpringUtil
                .getObjectFromSpring("CcdProviderDAO");
    }

    /**
     * @see com.mckesson.eig.roi.supplementary.service.ROISupplementaryService
     * #retrieveAttachmentPath(java.lang.String)
     */
    @Override
    public String retrieveAttachmentPath(String uuid) {
        final String logSM = "retrieveAttachment(uuid)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + uuid);
        }
        try {
            ROISupplementaryDAO dao = (ROISupplementaryDAO) getDAO(DAOName.ROI_SUPPLEMENTARY_DAO);
            String attachment = dao.retrieveAttachmentPath(uuid);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + attachment);
            }
            return attachment;

        } catch (ROIException e) {
            LOG.warn(e.getLocalizedMessage());
            throw e;
        } catch (Throwable e) {
            LOG.warn(e.getLocalizedMessage());
            throw new ROIException(e,
                    ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    /**
     * This method validates whether the document or attachment has been released or not
     * @param obj
     * @param requestId
     * @return boolean
     */
    @SuppressWarnings("unchecked")
    private boolean isAttachmentOrDocumentReleased(Object obj,long requestId,boolean isDocument) {
        boolean isReleased = false;
        RequestCoreDAO dao = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
        List<RequestSupplementalAttachment> reqSuppAttachment = new ArrayList<RequestSupplementalAttachment>();
        List<RequestSupplementalDocument> reqSuppDocument = new ArrayList<RequestSupplementalDocument>();
        if(null != obj && !isDocument)
           reqSuppAttachment = (List<RequestSupplementalAttachment>) obj;
        else if(null != obj)
           reqSuppDocument = (List<RequestSupplementalDocument>) obj;
        if((null != reqSuppAttachment && reqSuppAttachment.size() > 0) || (null != reqSuppDocument && reqSuppDocument.size() > 0))
        {
           if(requestId != 0)
           {
              RequestCore request = dao.retrieveRequest(requestId);
              if(ROIConstants.COMPLETED_STATUS.equalsIgnoreCase(request.getStatus()))
              {
                 isReleased = true;
              }
           }
        }
        return isReleased;
    }
}
