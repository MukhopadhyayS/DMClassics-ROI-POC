/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

 * Copyright © 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
 * Use of this software and related documentation is governed by a license agreement.
 * This material contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States
 * and international copyright and other intellectual property laws.
 * Use, disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without the
 * prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
 */

package com.mckesson.eig.roi.request.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;

import com.google.common.base.CharMatcher;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.PlainSqlBatchProcessor;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.muroioutbound.model.MUROIOutboundStatistics;
import com.mckesson.eig.roi.request.model.DeletePatientList;
import com.mckesson.eig.roi.request.model.RequestDocument;
import com.mckesson.eig.roi.request.model.RequestEncounter;
import com.mckesson.eig.roi.request.model.RequestPage;
import com.mckesson.eig.roi.request.model.RequestPatient;
import com.mckesson.eig.roi.request.model.RequestSupplementalAttachment;
import com.mckesson.eig.roi.request.model.RequestSupplementalDocument;
import com.mckesson.eig.roi.request.model.RequestVersion;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.BeanUtilities;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author OFS
 * @date Jun 29, 2012
 * @since Jun 29, 2012
 *
 */
public class RequestCorePatientDAOImpl
        extends ROIDAOImpl
        implements RequestCorePatientDAO {

    private static final OCLogger LOG = new OCLogger(RequestCorePatientDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static RequestCorePatientDAOHelper _helper = new RequestCorePatientDAOHelper();

    /**
     * The list of request patients are added to batch, And executed only if the
     * maximum batch size is reached or at the end.
     *
     * The list of all the patients are inserted and then inserts all the
     * patients encounters, then all the documents, all the versions and all the
     * pages respectively.
     *
     */
    @Override
    public <T extends BaseModel> void updatePatient(final long requestId,
            final List<RequestPatient> patients,
            final T baseModel) {

        final String logSM = "updatePatient(patients, requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestPatient :" + patients);
        }

        if (null == patients || patients.isEmpty()) {

            LOG.debug("The Patients List is empty");
            return;
        }

        try {

            final List<RequestEncounter> encounters = new ArrayList<RequestEncounter>();
            final List<RequestDocument> globalDocuments = new ArrayList<RequestDocument>();
            final List<RequestSupplementalAttachment> attachments =
                    new ArrayList<RequestSupplementalAttachment>();

            final List<RequestPatient> supplementalPatients = new ArrayList<RequestPatient>();
            final List<RequestPatient> hpfPatients = new ArrayList<RequestPatient>();
            final List<RequestSupplementalDocument> documents =
                    new ArrayList<RequestSupplementalDocument>();

            for (RequestPatient patient : patients) {

                if (!patient.isHpf()) {
                    supplementalPatients.add(patient);
                } else {
                    hpfPatients.add(patient);
                }

                List<RequestEncounter> roiEncounters = patient
                        .getRoiEncounters();
                if (null != roiEncounters) {

                    for (RequestEncounter enc : roiEncounters) {
                        // the basic encounter details are set for getting
                        // encounter sequence
                        enc.setMrn(patient.getMrn());
                        enc.setPatientFacility(patient.getFacility());
                        encounters.add(enc);
                    }
                }

                List<RequestDocument> globalDocs = patient.getGlobalDocuments();
                if (null != globalDocs) {

                    for (RequestDocument enc : globalDocs) {
                        // the basic encounter details are set for getting
                        // encounter sequence
                        enc.setMrn(patient.getMrn());
                        enc.setFacility(patient.getFacility());
                        globalDocuments.add(enc);
                    }
                }

                List<RequestSupplementalAttachment> roiAttachments = patient
                        .getAttachments();
                if (null != roiAttachments) {
                    attachments.addAll(roiAttachments);
                }

                List<RequestSupplementalDocument> roiDocuments = patient
                        .getNonHpfDocuments();
                if (null != roiDocuments) {
                    documents.addAll(roiDocuments);
                }
            }

            Session session = getSession();
            // Batch insertion for patients
            PlainSqlBatchProcessor processor = new SQLBatchProcessor(session) {

                @Override
                protected <T extends BaseModel> void addToBatch(
                        PreparedStatement pStmt,
                        T object) throws SQLException {

                    RequestPatient patient = (RequestPatient) object;
                    BeanUtilities.copyProperties(baseModel, patient);

                    int index = 1;
                    pStmt.setString(index++, patient.getMrn());
                    pStmt.setString(index++, patient.getFacility());
                    pStmt.setString(index++, patient.getName());
                    pStmt.setString(index++, patient.getLastName());
                    pStmt.setString(index++, patient.getFirstName());
                    pStmt.setString(index++, patient.getSsn());
                    pStmt.setString(index++, patient.getGender());
                    pStmt.setString(index++, patient.getEpn());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(patient.getDob()));
                    pStmt.setBoolean(index++, patient.isVip());
                    pStmt.setBoolean(index++, patient.isPatientLocked());

                }
            };
            // inserts the Hpf patients into requestDAR
            String query = session.getNamedQuery("insertPatient")
                    .getQueryString();
            processor.execute(hpfPatients, query);

            processor = new SQLBatchProcessor(session) {

                @Override
                protected <T extends BaseModel> void addToBatch(
                        PreparedStatement pStmt,
                        T object) throws SQLException {

                    RequestPatient patient = (RequestPatient) object;

                    int index = 1;
                    pStmt.setLong(index++, requestId);
                    pStmt.setString(index++, patient.getMrn());
                    pStmt.setString(index++, patient.getFacility());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(patient.getCreatedDt()));
                    pStmt.setLong(index++, patient.getCreatedBy());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(patient.getModifiedDt()));
                    pStmt.setLong(index++, patient.getModifiedBy());

                }
            };
            // creates the requestMapping to HPF patients
            query = session.getNamedQuery("insertRequestCoreToPatient")
                    .getQueryString();
            processor.execute(hpfPatients, query);
            // creates non- hPF patients
            addSupplementalPatientBatch(requestId, supplementalPatients,
                    baseModel);
            updateEncounter(requestId, encounters);
            updateGlobalDocuments(requestId, globalDocuments);
            updateSupplementalAttachment(requestId, attachments, baseModel);
            updateSupplementalDocument(requestId, documents, baseModel);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:patient: " + patients);
            }

        } catch (ROIException ex) {
            throw ex;

        } catch (Exception ex) {
            throw new ROIException(ex,
                    ROIClientErrorCodes.UPDATE_REQUEST_PATIENT_FAILED,
                    "Failed to update the request Patients for the request :"
                            + requestId);
        }
    }

    /**
     * The Request patient details are added to the batch until the maximum
     * batch size specified is reached.
     *
     * Once the maximum batch size is reached the batch is executed
     *
     * @param supplementalPatients
     * @return whether the given batch is executed i.e maximum batch size is
     *         reached
     */
    public void addSupplementalPatientBatch(final long requestId,
            final List<RequestPatient> supplementalPatients,
            final BaseModel baseModel) {

        final String logSM = "addSupplementalPatientBatch(patient)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestPatient :" + supplementalPatients);
        }

        try {

            Session session = getSession();
            PlainSqlBatchProcessor processor = new SQLBatchProcessor(session) {

                @Override
                protected <T extends BaseModel> void addToBatch(
                        PreparedStatement pStmt,
                        T object) throws SQLException {

                    RequestPatient patient = (RequestPatient) object;
                    BeanUtilities.copyProperties(baseModel, patient);

                    int index = 1;
                    pStmt.setLong(index++, patient.getSupplementalId());
                    pStmt.setString(index++, patient.getMrn());
                    pStmt.setString(index++, patient.getFacility());
                    pStmt.setString(index++, patient.getFreeformFacility());
                    pStmt.setString(index++, patient.getSsn());
                    pStmt.setString(index++, patient.getGender());
                    pStmt.setString(index++, patient.getEpn());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(patient.getDob()));
                    pStmt.setBoolean(index++, patient.isVip());
                    pStmt.setBoolean(index++, patient.isPatientLocked());
                    pStmt.setBoolean(index++, patient.isEncounterLocked());
                    pStmt.setString(index++, patient.getFirstName());
                    pStmt.setString(index++, patient.getLastName());
                    pStmt.setString(index++, patient.getAddress1());
                    pStmt.setString(index++, patient.getAddress2());
                    pStmt.setString(index++, patient.getAddress3());
                    pStmt.setString(index++, patient.getCity());
                    pStmt.setString(index++, patient.getState());
                    pStmt.setString(index++, patient.getZip());
                    pStmt.setString(index++, patient.getHomePhone());
                    pStmt.setString(index++, patient.getWorkPhone());

                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(patient.getCreatedDt()));
                    pStmt.setLong(index++, patient.getCreatedBy());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(patient.getModifiedDt()));
                    pStmt.setLong(index++, patient.getModifiedBy());

                }
            };

            String query = session.getNamedQuery("insertSupplementalPatient")
                    .getQueryString();
            processor.execute(supplementalPatients, query);

            processor = new SQLBatchProcessor(session) {

                @Override
                protected <T extends BaseModel> void addToBatch(
                        PreparedStatement pStmt,
                        T object) throws SQLException {

                    RequestPatient patient = (RequestPatient) object;

                    int index = 1;
                    pStmt.setLong(index++, requestId);
                    pStmt.setLong(index++, patient.getSupplementalId());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(patient.getCreatedDt()));
                    pStmt.setLong(index++, patient.getCreatedBy());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(patient.getModifiedDt()));
                    pStmt.setLong(index++, patient.getModifiedBy());

                }
            };
            // creates the requestMapping to NonHPF patients
            query = session.getNamedQuery(
                    "insertRequestCoreToSupplementalPatient").getQueryString();
            processor.execute(supplementalPatients, query);

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:RequestPatient :"
                        + supplementalPatients);
            }

        } catch (ROIException ex) {
            throw ex;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.INSERT_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    /**
     * @param requestId
     * @see com.mckesson.eig.roi.request.dao.RequestCorePatientDAO#updateEncounter(long,
     *      List)
     */
    @Override
    public <T extends BaseModel> void updateEncounter(final long requestId,
            final List<RequestEncounter> encounters) {

        final String logSM = "updateEncounter(requestId, encounters)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:requestId :" + requestId
                    + ", RequestEncounters :"
                    + encounters);
        }

        if (null == encounters || encounters.isEmpty()) {
            return;
        }

        try {

            Session session = getSession();
            final List<RequestDocument> documents = new ArrayList<RequestDocument>();
            // Batch insertion for patients
            PlainSqlBatchProcessor processor = new SQLBatchProcessor(session) {

                @Override
                protected <T extends BaseModel> void addToBatch(
                        PreparedStatement pStmt,
                        T object) throws SQLException {

                    RequestEncounter encounter = (RequestEncounter) object;

                    int i = 1;
                    if (encounter.getPatientSeq() <= 0) {

                        pStmt.setString(i++, encounter.getMrn());
                        pStmt.setString(i++, encounter.getPatientFacility());
                        pStmt.setLong(i++, requestId);
                    } else {

                        pStmt.setLong(i++, encounter.getPatientSeq());
                    }

                    pStmt.setString(i++, encounter.getMrn());
                    pStmt.setString(i++, encounter.getName());
                    pStmt.setString(i++, encounter.getFacility());
                    pStmt.setTimestamp(i++,
                            getSQLTimeStamp(encounter.getAdmitdate()));
                    pStmt.setTimestamp(i++,
                            getSQLTimeStamp(encounter.getDischargeDate()));
                    pStmt.setBoolean(i++, encounter.isLocked());
                    pStmt.setString(i++, encounter.getPatientService());
                    pStmt.setBoolean(i++, encounter.isVip());
                    pStmt.setBoolean(i++, encounter.isHasDeficiency());
                    pStmt.setString(i++, encounter.getPatientType());

                    List<RequestDocument> roiDocuments = encounter
                            .getRoiDocuments();
                    if (null == roiDocuments) {
                        return;
                    }

                    for (RequestDocument doc : roiDocuments) {
                        // the basic encounter details are set for getting
                        // encounter sequence
                        doc.setEncounter(encounter.getName());
                        doc.setFacility(encounter.getFacility());
                        documents.add(doc);
                    }

                }
            };

            String query;

            // if the given list of patient does not contains the patient
            // sequence,
            // the patient sequence should be retrieved from the database
            if (encounters.get(0).getPatientSeq() <= 0) {

                query = session.getNamedQuery("insertEncounter")
                        .getQueryString();
            } else {
                query = session.getNamedQuery("insertEncounterWithPatientSeq")
                        .getQueryString();
            }

            processor.execute(encounters, query);
            updateDocument(requestId, documents);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

        } catch (ROIException ex) {
            throw ex;

        } catch (Exception ex) {
            throw new ROIException(ex,
                    ROIClientErrorCodes.UPDATE_REQUEST_PATIENT_FAILED,
                    "Failed to update the request Patients for the encounter");
        }
    }

    /**
     * @param requestId
     * @see com.mckesson.eig.roi.request.dao.RequestCorePatientDAO#updateEncounter(long,
     *      List)
     */
    @Override
    public <T extends BaseModel> void updateGlobalDocuments(
            final long requestId,
            final List<RequestDocument> globalDocs) {

        final String logSM = "updateGlobalDocuments(requestId, encounters, baseModel)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:requestId :" + requestId
                    + ", RequestEncounters :"
                    + globalDocs);
        }

        if (null == globalDocs || globalDocs.isEmpty()) {
            return;
        }

        try {

            Session session = getSession();
            final List<RequestVersion> versions = new ArrayList<RequestVersion>();
            // Batch insertion for patients
            PlainSqlBatchProcessor processor = new SQLBatchProcessor(session) {

                @Override
                protected <T extends BaseModel> void addToBatch(
                        PreparedStatement pStmt,
                        T object) throws SQLException {

                    RequestDocument document = (RequestDocument) object;

                    int index = 1;
                    if (document.getPatientSeq() <= 0) {

                        pStmt.setString(index++, document.getMrn());
                        pStmt.setString(index++, document.getFacility());
                        pStmt.setLong(index++, requestId);
                    } else {

                        pStmt.setLong(index++, document.getPatientSeq());
                    }

                    pStmt.setString(index++, document.getEncounter());
                    pStmt.setString(index++, document.getMrn());
                    pStmt.setString(index++, document.getFacility());
                    // pStmt.setString(index++, document.getDuid());
                    pStmt.setString(index++, document.getName());
                    pStmt.setString(index++, document.getSubtitle());
                    pStmt.setLong(index++, document.getDocId());
                    pStmt.setLong(index++, document.getDocTypeId());
                    pStmt.setString(index++, document.getChartOrder());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(document.getDateTime()));

                    List<RequestVersion> roiVersions = document
                            .getRoiVersions();
                    if (null == roiVersions) {
                        return;
                    }

                    for (RequestVersion version : roiVersions) {

                        version.setDocId(document.getDocId());
                        versions.add(version);
                    }
                }
            };

            String query;

            // if the given list of global documents does not contains the
            // patient sequence,
            // the patient sequence should be retrieved from the database
            if (globalDocs.get(0).getPatientSeq() <= 0) {

                query = session.getNamedQuery("insertGlobalDocument")
                        .getQueryString();
            } else {
                query = session.getNamedQuery(
                        "insertGlobalDocumentWithPatientSeq").getQueryString();
            }

            processor.execute(globalDocs, query);
            updateGlobalDocumentsVersion(requestId, versions);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

        } catch (ROIException ex) {
            throw ex;

        } catch (Exception ex) {
            throw new ROIException(ex,
                    ROIClientErrorCodes.UPDATE_REQUEST_PATIENT_FAILED,
                    "Failed to update the request Patients for the encounter");
        }
    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestCorePatientDAO#updateDocument(long,
     *      List)
     */
    @Override
    public <T extends BaseModel> void updateDocument(final long requestId,
            final List<RequestDocument> documents) {

        final String logSM = "updateDocument(encounterId, documents, hasEncounterSeq)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:requestId:" + requestId + ", document:"
                    + documents);
        }

        if (null == documents || documents.isEmpty()) {
            return;
        }

        try {

            final List<RequestVersion> versions = new ArrayList<RequestVersion>();
            final List<RequestDocument> globalDocuments = new ArrayList<RequestDocument>();
            Iterator<RequestDocument> iterator = documents.iterator();
            while (iterator.hasNext()) {

                RequestDocument document = iterator.next();
                if (document.isGlobalDocument()) {
                    globalDocuments.add(document);
                    iterator.remove();
                }
            }

            Session session = getSession();
            // Batch insertion for patients
            PlainSqlBatchProcessor processor = new SQLBatchProcessor(session) {

                @Override
                protected <T extends BaseModel> void addToBatch(
                        PreparedStatement pStmt,
                        T object) throws SQLException {

                    RequestDocument document = (RequestDocument) object;

                    int index = 1;
                    if (document.getEncounterSeq() <= 0) {

                        pStmt.setString(index++, document.getFacility());
                        pStmt.setString(index++, document.getEncounter());
                        pStmt.setLong(index++, requestId);
                        pStmt.setString(index++, document.getMrn());
                        pStmt.setString(index++, document.getFacility());
                        pStmt.setLong(index++, requestId);
                    } else {

                        pStmt.setLong(index++, document.getEncounterSeq());
                        pStmt.setLong(index++, document.getPatientSeq());
                    }

                    pStmt.setString(index++, document.getEncounter());
                    pStmt.setString(index++, document.getMrn());
                    pStmt.setString(index++, document.getFacility());
                    // pStmt.setString(index++, document.getDuid());
                    pStmt.setString(index++, stripJunkCharacters(document.getName()));
                    pStmt.setString(index++, document.getSubtitle());
                    pStmt.setLong(index++, document.getDocId());
                    pStmt.setLong(index++, document.getDocTypeId());
                    pStmt.setString(index++, document.getChartOrder());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(document.getDateTime()));

                    List<RequestVersion> roiVersions = document
                            .getRoiVersions();
                    if (null == roiVersions) {
                        return;
                    }

                    for (RequestVersion version : roiVersions) {

                        version.setDocId(document.getDocId());
                        versions.add(version);
                    }
                }
            };

            String query;
            // if the list of patients does not contains the encounter sequence
            // then the encounter sequence should be taken from the database
            if (CollectionUtilities.hasContent(documents)) {

                if (documents.get(0).getEncounterSeq() <= 0) {
                    query = session.getNamedQuery("insertDocument")
                            .getQueryString();
                } else {
                    query = session.getNamedQuery(
                            "insertDocumentWithEncounterSeq").getQueryString();
                }

                processor.execute(documents, query);
            }
            updateVersion(requestId, versions);
            updateGlobalDocuments(requestId, globalDocuments);

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:Documents :" + documents);
            }

        } catch (ROIException ex) {
            throw ex;

        } catch (Exception ex) {
            throw new ROIException(ex,
                    ROIClientErrorCodes.UPDATE_REQUEST_PATIENT_FAILED,
                    "Failed to update the request Documents :" + documents);
        }

    }
    
    private String stripJunkCharacters(String documentName) {
        if (!StringUtilities.isEmpty(documentName)) {
            documentName = CharMatcher.anyOf("Â").removeFrom(documentName);
        }
        return documentName;
    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestCorePatientDAO#updateVersion(long,
     *      List)
     */
    @Override
    public <T extends BaseModel> void updateVersion(final long requestId,
            final List<RequestVersion> versions) {

        final String logSM = "updateVersion(encounterId, encounters)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:version :" + versions);
        }

        if (null == versions || versions.isEmpty()) {
            return;
        }

        try {

            final List<RequestPage> pages = new ArrayList<RequestPage>();
            final List<RequestVersion> globalVersions = new ArrayList<RequestVersion>();

            Iterator<RequestVersion> iterator = versions.iterator();
            while (iterator.hasNext()) {

                RequestVersion version = iterator.next();
                if (version.isGlobalDocument()) {
                    globalVersions.add(version);
                    iterator.remove();
                }
            }

            // Batch insertion for versions
            Session session = getSession();
            PlainSqlBatchProcessor processor = getRequestVersionSqlBatchProcessor(
                    requestId, pages);

            String query;
            // if version does not contains the document sequence(i.e call from
            // update Documents)
            // then the document sequence should be retrieved by SQL Query
            if (CollectionUtilities.hasContent(versions)) {

                if (versions.get(0).getDocumentSeq() <= 0) {
                    query = session.getNamedQuery("insertVersion")
                            .getQueryString();
                } else {
                    query = session.getNamedQuery("insertVersionWithDocSeq")
                            .getQueryString();
                }
                processor.execute(versions, query);
            }

            insertPage(requestId, pages);
            updateGlobalDocumentsVersion(requestId, globalVersions);

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:version :" + versions);
            }

        } catch (ROIException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ROIException(ex,
                    ROIClientErrorCodes.UPDATE_REQUEST_PATIENT_FAILED,
                    "Failed to update the request version:" + versions);
        }
    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestCorePatientDAO#updateVersion(long,
     *      List)
     */
    @Override
    public <T extends BaseModel> void updateGlobalDocumentsVersion(
            final long requestId,
            final List<RequestVersion> versions) {

        final String logSM = "updateGlobalDocumentsVersion(encounterId, encounters)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:version :" + versions);
        }

        if (null == versions || versions.isEmpty()) {
            return;
        }

        try {

            final List<RequestPage> pages = new ArrayList<RequestPage>();
            // Batch insertion for versions
            Session session = getSession();
            PlainSqlBatchProcessor processor = getRequestVersionSqlBatchProcessor(
                    requestId, pages);

            String query;
            // if version does not contains the document sequence(i.e call from
            // update Documents)
            // then the document sequence should be retrieved by SQL Query
            if (versions.get(0).getDocumentSeq() <= 0) {
                query = session.getNamedQuery("insertGlobalDocumentVersion")
                        .getQueryString();
            } else {
                query = session.getNamedQuery("insertVersionWithDocSeq")
                        .getQueryString();
            }

            processor.execute(versions, query);
            insertGlobalDocumentsPage(requestId, pages);

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:version :" + versions);
            }

        } catch (ROIException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ROIException(ex,
                    ROIClientErrorCodes.UPDATE_REQUEST_PATIENT_FAILED,
                    "Failed to update the request version:" + versions);
        }
    }

    /**
     * @param requestId
     * @param baseModel
     * @param pages
     * @return
     */
    private <T> PlainSqlBatchProcessor getRequestVersionSqlBatchProcessor(
            final long requestId,
            final List<RequestPage> pages) {

        PlainSqlBatchProcessor processor = new SQLBatchProcessor(getSession()) {

            @Override
            protected <T extends BaseModel> void addToBatch(
                    PreparedStatement pStmt,
                    T object) throws SQLException {

                RequestVersion version = (RequestVersion) object;

                int index = 1;
                if (version.getDocumentSeq() <= 0) {

                    pStmt.setLong(index++, version.getDocId());
                    pStmt.setLong(index++, requestId);
                } else {

                    pStmt.setLong(index++, version.getDocumentSeq());
                }

                pStmt.setLong(index++, version.getVersionNumber());
                pStmt.setLong(index++, version.getDocId());

                List<RequestPage> roipages = version.getRoiPages();
                if (null == roipages) {
                    return;
                }

                for (RequestPage page : roipages) {

                    page.setDocId(version.getDocId());
                    page.setVersionNumber(version.getVersionNumber());
                    pages.add(page);
                }
            }
        };

        return processor;
    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestCorePatientDAO#updatePage(long,
     *      List)
     */
    @Override
    public <T extends BaseModel> void insertPage(final long requestId,
            final List<RequestPage> pages) {

        final String logSM = "updatePage(encounterId, encounters, baseModel)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:requestId :" + requestId + ", pages :"
                    + pages);
        }

        if (null == pages || pages.isEmpty()) {
            return;
        }

        try {

            List<RequestPage> updatePage = new ArrayList<RequestPage>();
            List<RequestPage> insertPage = new ArrayList<RequestPage>();
            List<RequestPage> globalDocsPage = new ArrayList<RequestPage>();
            for (RequestPage page : pages) {

                if (page.isGlobalDocument()) {
                    globalDocsPage.add(page);
                } else if (page.getPageSeq() > 0) {
                    updatePage.add(page);
                } else {
                    insertPage.add(page);
                }
            }
            // Batch insertion for versions
            Session session = getSession();
            PlainSqlBatchProcessor processor = getRequestPageSqlBatchProcessor(requestId);

            String query;
            if (CollectionUtilities.hasContent(insertPage)) {

                RequestPage page = insertPage.get(0);
                if (page.getVersionSeq() <= 0) {
                    query = session.getNamedQuery("insertPage")
                            .getQueryString();
                } else {
                    query = session.getNamedQuery("insertPageWithVersionSeq")
                            .getQueryString();
                }

                processor.execute(insertPage, query);
            }

            // updates the pages
            updatePage(requestId, updatePage);
            insertGlobalDocumentsPage(requestId, globalDocsPage);

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:version :" + pages);
            }

        } catch (ROIException ex) {
            throw ex;

        } catch (Exception ex) {
            throw new ROIException(ex,
                    ROIClientErrorCodes.UPDATE_REQUEST_PATIENT_FAILED,
                    "Failed to update the request pages");
        }
    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestCorePatientDAO#updatePage(long,
     *      List)
     */
    @Override
    public <T extends BaseModel> void insertGlobalDocumentsPage(
            final long requestId,
            final List<RequestPage> pages) {

        final String logSM = "insertGlobalDocumentsPage(requestId, pages, baseModel)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:requestId :" + requestId + ", pages :"
                    + pages);
        }

        if (null == pages || pages.isEmpty()) {
            return;
        }

        try {

            List<RequestPage> updatePage = new ArrayList<RequestPage>();
            List<RequestPage> insertPage = new ArrayList<RequestPage>();
            for (RequestPage page : pages) {

                if (page.getPageSeq() > 0) {
                    updatePage.add(page);
                } else {
                    insertPage.add(page);
                }
            }
            // Batch insertion for versions
            Session session = getSession();
            PlainSqlBatchProcessor processor = getRequestPageSqlBatchProcessor(requestId);

            String query;
            RequestPage page = pages.get(0);
            if (page.getVersionSeq() <= 0) {

                query = session.getNamedQuery("insertGlobalDocumentPage")
                        .getQueryString();
            } else {

                query = session.getNamedQuery("insertPageWithVersionSeq")
                        .getQueryString();
            }
            processor.execute(insertPage, query);

            // updates the pages
            updatePage(requestId, updatePage);

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:version :" + pages);
            }

        } catch (ROIException ex) {
            throw ex;

        } catch (Exception ex) {
            throw new ROIException(ex,
                    ROIClientErrorCodes.UPDATE_REQUEST_PATIENT_FAILED,
                    "Failed to update the request pages");
        }
    }

    /**
     * @param requestId
     * @param baseModel
     * @return
     */
    private <T> PlainSqlBatchProcessor getRequestPageSqlBatchProcessor(
            final long requestId) {

        PlainSqlBatchProcessor processor = new SQLBatchProcessor(getSession()) {

            @Override
            protected <T extends BaseModel> void addToBatch(
                    PreparedStatement pStmt,
                    T object) throws SQLException {

                RequestPage page = (RequestPage) object;

                int index = 1;
                if (page.getVersionSeq() <= 0) {

                    pStmt.setLong(index++, page.getVersionNumber());
                    pStmt.setLong(index++, page.getDocId());
                    pStmt.setLong(index++, requestId);
                } else {

                    pStmt.setLong(index++, page.getVersionSeq());
                }

                pStmt.setString(index++, page.getImnetId());
                pStmt.setInt(index++, page.getPageNumber());
                pStmt.setLong(index++, page.getContentCount());
                pStmt.setBoolean(index++, page.isSelectedForRelease());
                pStmt.setBoolean(index++, page.isReleased());
                pStmt.setInt(index++, page.getPageNumberRequested());
            }
        };

        return processor;
    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestCorePatientDAO#updatePage(long,
     *      List)
     */
    @Override
    public <T extends BaseModel> void updatePage(final long requestId,
            final List<RequestPage> pages) {

        final String logSM = "updatePage(requestId, pages)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:requestId :" + requestId + ", pages :"
                    + pages);
        }

        if (null == pages || pages.isEmpty()) {
            return;
        }

        try {

            // Batch insertion for versions
            Session session = getSession();
            PlainSqlBatchProcessor processor = new SQLBatchProcessor(
                    getSession()) {

                @Override
                protected <T extends BaseModel> void addToBatch(
                        PreparedStatement pStmt,
                        T object) throws SQLException {

                    RequestPage page = (RequestPage) object;

                    int index = 1;

                    pStmt.setBoolean(index++, page.isSelectedForRelease());
                    pStmt.setLong(index++, page.getPageSeq());
                }
            };

            String query = session.getNamedQuery("updatePage").getQueryString();
            processor.execute(pages, query);

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:version :" + pages);
            }

        } catch (ROIException ex) {
            throw ex;

        } catch (Exception ex) {
            throw new ROIException(ex,
                    ROIClientErrorCodes.UPDATE_REQUEST_PATIENT_FAILED,
                    "Failed to update the request pages");
        }
    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestCorePatientDAO
     *      #updateSupplementalDocument(long, List)
     */
    @Override
    public <T extends BaseModel> void updateSupplementalDocument(
            long requestId,
            List<RequestSupplementalDocument> supplementalDocuments,
            T baseModel) {

        final String logSM = "updateSupplementalDocument(requestId,"
                + " supplementalDocuments, baseModel)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:requestId :" + requestId
                    + ", SupplementalDocument :" + supplementalDocuments);
        }

        if (null == supplementalDocuments || supplementalDocuments.isEmpty()) {
            return;
        }

        try {

            List<RequestSupplementalDocument> supplementalDocs =
                    new ArrayList<RequestSupplementalDocument>();
            List<RequestSupplementalDocument> supplementaryDocs =
                    new ArrayList<RequestSupplementalDocument>();
            List<RequestSupplementalDocument> updateSupplementalDocs =
                    new ArrayList<RequestSupplementalDocument>();
            List<RequestSupplementalDocument> updateSupplementaryDocs =
                    new ArrayList<RequestSupplementalDocument>();

            for (RequestSupplementalDocument document : supplementalDocuments) {

                if (toPlong(document.getSupplementalId()) > 0
                        && toPlong(document.getDocumentCoreSeq()) <= 0) {
                    supplementalDocs.add(document);

                } else if (toPlong(document.getSupplementalId()) > 0
                        && toPlong(document.getDocumentCoreSeq()) > 0) {

                    updateSupplementalDocs.add(document);
                } else if (toPlong(document.getSupplementalId()) <= 0
                        && toPlong(document.getDocumentCoreSeq()) <= 0) {

                    supplementaryDocs.add(document);
                } else {

                    updateSupplementaryDocs.add(document);
                }
            }
            Session session = getSession();
            // inserts the new entry into the databasde
            _helper.addSupplementalDocument(session, requestId,
                    supplementalDocs, baseModel);
            _helper.addSupplementarityDocuments(session, requestId,
                    supplementaryDocs, baseModel);

            // updates the existing supplemental documents entry into the
            // database
            _helper.updateSupplementalDocumentBatch(session,
                    updateSupplementalDocs, baseModel);
            // updates the existing supplementary documents entry into the
            // database
            _helper.updateSupplementalDocumentBatch(session,
                    updateSupplementaryDocs, baseModel);

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:version :" + supplementalDocuments);
            }

        } catch (ROIException ex) {
            throw ex;

        } catch (Exception ex) {
            throw new ROIException(ex,
                    ROIClientErrorCodes.UPDATE_REQUEST_PATIENT_FAILED,
                    "Failed to update the request pages");
        }
    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestCorePatientDAO
     *      #updateSupplementalDocument(long, List)
     */
    @Override
    public <T extends BaseModel> void updateSupplementalAttachment(
            final long requestId,
            final List<RequestSupplementalAttachment> suppAttachments,
            final T baseModel) {

        final String logSM = "updateSupplementalAttachment(requestId, "
                + "supplementalAttachment, baseModel)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:requestId :" + requestId
                    + ", SupplementalAttcahments :" + suppAttachments);
        }

        if (null == suppAttachments || suppAttachments.isEmpty()) {
            return;
        }

        try {

            List<RequestSupplementalAttachment> supplementalAttachments =
                    new ArrayList<RequestSupplementalAttachment>();
            List<RequestSupplementalAttachment> supplementaryAttachments =
                    new ArrayList<RequestSupplementalAttachment>();
            List<RequestSupplementalAttachment> updateSupplementalAttachments =
                    new ArrayList<RequestSupplementalAttachment>();
            List<RequestSupplementalAttachment> updateSupplementaryAttachments =
                    new ArrayList<RequestSupplementalAttachment>();

            for (RequestSupplementalAttachment document : suppAttachments) {

                if (toPlong(document.getSupplementalId()) > 0
                        && toPlong(document.getAttachmentCoreSeq()) <= 0) {

                    supplementalAttachments.add(document);

                } else if (toPlong(document.getSupplementalId()) > 0
                        && toPlong(document.getAttachmentCoreSeq()) > 0) {

                    updateSupplementalAttachments.add(document);
                } else if (toPlong(document.getSupplementalId()) <= 0
                        && toPlong(document.getAttachmentCoreSeq()) <= 0) {

                    supplementaryAttachments.add(document);
                } else {

                    updateSupplementaryAttachments.add(document);
                }
            }
            Session session = getSession();
            // inserts the supplemental attachments
            _helper.addSupplementalAttachment(session, requestId,
                    supplementalAttachments, baseModel);
            // inserts the supplementarity attachments
            _helper.addSupplementarityAttachment(session, requestId,
                    supplementaryAttachments, baseModel);

            // updates the supplemental attachments
            _helper.updateSupplementalAttachmentBatch(session,
                    updateSupplementalAttachments, baseModel);
            // updates the supplementary attachments
            _helper.updateSupplementalAttachmentBatch(session,
                    updateSupplementaryAttachments, baseModel);

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:requestId :" + requestId
                        + ", SupplementalAttcahments :" + suppAttachments);
            }

        } catch (ROIException ex) {
            throw ex;

        } catch (Exception ex) {
            throw new ROIException(ex,
                    ROIClientErrorCodes.UPDATE_REQUEST_PATIENT_FAILED,
                    "Failed to update the request pages");
        }
    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestDAO#retrievePatient(long)
     */
    @Override
    public Map<RequestDSRMappingKeys, List<? extends BaseModel>> retrieveRequestPatient(
            long requestId) {

        final String logSM = "retrieveRequestPatient(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestId " + requestId);
        }

        try {

            Map<RequestDSRMappingKeys, List<? extends BaseModel>> requestPatients =
                    new HashMap<RequestDSRMappingKeys, List<? extends BaseModel>>();

            Session session = getSession();
            // Retrieving all page details for the requestid based on versions
            requestPatients.put(RequestDSRMappingKeys.PAGES,
                    _helper.retrieveAllPageForRequest(session, requestId));

            // Retrieving all version details for the requestid based on
            // documents
            requestPatients.put(RequestDSRMappingKeys.VERSIONS,
                    _helper.retrieveAllVersionForRequest(session, requestId));

            // Retrieving all document details for the requestid based on
            // encounter
            requestPatients.put(RequestDSRMappingKeys.DOCUMENTS,
                    _helper.retrieveAllDocumentForRequest(session, requestId));

            // Retrieving all document details for the requestid based on
            // encounter
            requestPatients.put(RequestDSRMappingKeys.GLOBALDOCUMENTS,
                    _helper.retrieveAllGlobalDocumentForRequest(session,
                            requestId));

            // Retrieving all document details for the requestid based on
            // encounter
            requestPatients.put(RequestDSRMappingKeys.GLOBALDOCUMENTSVERSIONS,
                    _helper.retrieveAllGlobalDocumentVersionsForRequest(
                            session, requestId));

            // Retrieving all document details for the requestid based on
            // encounter
            requestPatients.put(RequestDSRMappingKeys.GLOBALDOCUMENTSPAGES,
                    _helper.retrieveAllGlobalDocumentPagesForRequest(session,
                            requestId));

            // Retrieving all encounter details for the requestid based on
            // patients
            requestPatients.put(RequestDSRMappingKeys.ENCOUNTERS,
                    _helper.retrieveAllEncounterForRequest(session, requestId));

            // Retrieving all patient details for the requestid
            requestPatients.put(RequestDSRMappingKeys.PATIENTS,
                    retrieveAllPatientForRequest(requestId));

            // Retrieving all patient's nonhpf documents details for the
            // requestid
            requestPatients.put(RequestDSRMappingKeys.NONHPF_DOCUMENTS,
                    _helper.retrieveAllNonHpfDocumentsForRequest(session,
                            requestId));

            // Retrieving all patient's attachments details for the requestid
            requestPatients
                    .put(RequestDSRMappingKeys.ATTACHMENTS,
                            _helper.retrieveAllAttachmentsForRequest(session,
                                    requestId));

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:RequestId " + requestId);
            }

            return requestPatients;

        } catch (ROIException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ROIException(ex,
                    ROIClientErrorCodes.RETRIEVE_REQUEST_PATIENT_FAILED,
                    "Failed to retrieve the request Patients for the requestId:"
                            + requestId);
        }
    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestCorePatientDAO#removePatientInfo
     *      (DeletePatientList, long)
     */
    public void removePatientInfo(DeletePatientList patientsInfo, long requestId) {

        final String logSM = "removePatientInfo(List<DeletePatientList> patientsInfo)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:PatientsInfo :" + patientsInfo);
        }

        try {
            Session session = getSession();

            // Deletes the page for the versions
            _helper.deletePages(session, patientsInfo.getPageSeq());

            // Deletes the version details for the encounter
            _helper.deleteVersions(session, patientsInfo.getVersionSeq());

            // Deletes the document details for the encounter
            _helper.deleteDocuments(session, patientsInfo.getDocSeq());

            // Deletes the global document details for the patient
            _helper.deleteGlobalDocuments(session, patientsInfo.getPatientSeq());

            // Deletes the encounter details for the patient
            _helper.deleteEncounters(session, patientsInfo.getEncounterSeq());

            // Deletes the patient details for the request
            _helper.deletePatients(session, patientsInfo.getPatientSeq(),
                    requestId, false);

            // All the supplementarity attachments are deleted using the given
            // named Query
            _helper.deleteRequestPatientDetailsById(session,
                    patientsInfo.getAttachmentSeq(),
                    "deleteSupplementarityAttachmentsBySeq");

            // All the supplementarity documents are deleted using the given
            // named Query
            _helper.deleteRequestPatientDetailsById(session,
                    patientsInfo.getDocumentSeq(),
                    "deleteSupplementarityDocumentsBySeq");

            // All the supplemental attachments are deleted using the given
            // named Query
            _helper.deleteRequestPatientDetailsById(session,
                    patientsInfo.getSupplementalAttachmentSeq(),
                    "deleteSupplementalAttachmentsBySeq");

            // All the supplemental documents are deleted using the given named
            // Query
            _helper.deleteRequestPatientDetailsById(session,
                    patientsInfo.getSupplementalDocumentSeq(),
                    "deleteSupplementalDocumentsBySeq");

            // Deletes the global document details for the patient
            _helper.deleteGlobalDocuments(session,
                    patientsInfo.getDarPatientSeq());

            // Deletes the DAR patient details for the request
            _helper.deletePatients(session, patientsInfo.getDarPatientSeq(),
                    requestId, true);

            // Deletes the supplemental patient details for the request
            _helper.deleteSupplementalPatients(session,
                    patientsInfo.getSupplementalPatientSeq(),
                    requestId,
                    false);

            // Deletes the DAR supplemental patient details for the request
            _helper.deleteSupplementalPatients(session,
                    patientsInfo.getDarSuppPatientSeq(), requestId, true);

        } catch (Exception ex) {
            throw new ROIException(ex,
                    ROIClientErrorCodes.DELETE_REQUEST_PATIENT_FAILED,
                    "Failed to delete the request Patients, patientInfo : "
                            + patientsInfo);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:PatientsInfo " + patientsInfo);
        }
    }

    /**
     * This method Retrieves the list of patients that are related to the
     * request.
     *
     * @param requestId
     * @return list of patients
     */
    public List<RequestPatient> retrieveAllPatientForRequest(long requestId) {

        final String logSM = "retrieveAllPatientForRequest(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestId " + requestId);
        }

        try {

            Session session = getSession();
            String query = session.getNamedQuery("retrieveRequestPatient")
                    .getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);

            sqlQuery.addScalar("patientSeq", LongType.INSTANCE);
            sqlQuery.addScalar("requestId", LongType.INSTANCE);
            sqlQuery.addScalar("supplementalId", LongType.INSTANCE);
            sqlQuery.addScalar("mrn", StringType.INSTANCE);
            sqlQuery.addScalar("facility", StringType.INSTANCE);
            sqlQuery.addScalar("freeformFacility", StringType.INSTANCE);
            sqlQuery.addScalar("name", StringType.INSTANCE);
            sqlQuery.addScalar("lastName", StringType.INSTANCE);
            sqlQuery.addScalar("firstName", StringType.INSTANCE);
            sqlQuery.addScalar("gender", StringType.INSTANCE);
            sqlQuery.addScalar("epn", StringType.INSTANCE);
            sqlQuery.addScalar("ssn", StringType.INSTANCE);
            sqlQuery.addScalar("patientLocked", BooleanType.INSTANCE);
            sqlQuery.addScalar("encounterLocked", BooleanType.INSTANCE);
            sqlQuery.addScalar("vip", BooleanType.INSTANCE);
            sqlQuery.addScalar("hpf", BooleanType.INSTANCE);
            sqlQuery.addScalar("dob", StandardBasicTypes.TIMESTAMP);

            sqlQuery.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("modifiedBy", IntegerType.INSTANCE);
            sqlQuery.addScalar("createdBy", IntegerType.INSTANCE);

            sqlQuery.setParameter("requestId", requestId, LongType.INSTANCE);
            sqlQuery.setResultTransformer(Transformers
                    .aliasToBean(RequestPatient.class));

            @SuppressWarnings("unchecked")
            List<RequestPatient> requestPatientDetails = sqlQuery.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:RequestId " + requestId);
            }

            return requestPatientDetails;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.RETRIEVE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    /**
     * This method Retrieves the list of patients that are related to the given
     * InvoiceIds.
     *
     * @param invoiceIds
     * @return list of patients
     */
    public List<RequestPatient> retrieveAllInvoicePatientsByInvoiceIds(
            List<Long> invoiceIds) {

        final String logSM = "retrieveAllInvoicePatientByInvoiceIds(invoiceIds)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:invoiceIds: " + invoiceIds);
        }

        try {

            Session session = getSession();
            String query = session.getNamedQuery(
                    "retrieveInvoiceRequestPatientByInvoiceIds")
                    .getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);

            sqlQuery.addScalar("patientSeq", LongType.INSTANCE);
            sqlQuery.addScalar("requestId", LongType.INSTANCE);
            sqlQuery.addScalar("supplementalId", LongType.INSTANCE);
            sqlQuery.addScalar("invoiceId", LongType.INSTANCE);
            sqlQuery.addScalar("mrn", StringType.INSTANCE);
            sqlQuery.addScalar("facility", StringType.INSTANCE);
            sqlQuery.addScalar("name", StringType.INSTANCE);
            sqlQuery.addScalar("gender", StringType.INSTANCE);
            sqlQuery.addScalar("epn", StringType.INSTANCE);
            sqlQuery.addScalar("ssn", StringType.INSTANCE);
            sqlQuery.addScalar("patientLocked", BooleanType.INSTANCE);
            sqlQuery.addScalar("encounterLocked", BooleanType.INSTANCE);
            sqlQuery.addScalar("vip", BooleanType.INSTANCE);
            sqlQuery.addScalar("hpf", BooleanType.INSTANCE);
            sqlQuery.addScalar("dob", StandardBasicTypes.TIMESTAMP);

            sqlQuery.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("modifiedBy", IntegerType.INSTANCE);
            sqlQuery.addScalar("createdBy", IntegerType.INSTANCE);

            sqlQuery.setParameterList("invoiceIds", invoiceIds, LongType.INSTANCE);
            sqlQuery.setResultTransformer(Transformers
                    .aliasToBean(RequestPatient.class));

            @SuppressWarnings("unchecked")
            List<RequestPatient> requestPatientDetails = sqlQuery.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:RequestId " + invoiceIds);
            }

            return requestPatientDetails;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.RETRIEVE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    /**
     * This method Retrieves the list of all the statistics that are related to
     * the request.
     *
     * @param requestId
     * @return list of statistics
     */
    public List<MUROIOutboundStatistics> retrieveAllStatisticsDetailsByRequestId(
            long requestId) {

        final String logSM = "retrieveAllStatisticsDetails(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start" + requestId);
        }
        String documentType = "MU";
        try {

            Session session = getSession();
            String query = session.getNamedQuery(
                    "retrieveAllStatisticsDetailsByRequestId").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);

            sqlQuery.addScalar("mrn", StringType.INSTANCE);
            sqlQuery.addScalar("facility", StringType.INSTANCE);
            sqlQuery.addScalar("patientName", StringType.INSTANCE);
            sqlQuery.addScalar("patientDOB", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("patientSex", StringType.INSTANCE);
            sqlQuery.addScalar("encounter", StringType.INSTANCE);
            sqlQuery.addScalar("dischargeDate", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("hpfMuDocumentType", StringType.INSTANCE);
            sqlQuery.addScalar("selectedForRelease", BooleanType.INSTANCE);
            sqlQuery.addScalar("type", StringType.INSTANCE);

            sqlQuery.setParameter("docType", documentType, StringType.INSTANCE);
            sqlQuery.setParameter("requestId", requestId, LongType.INSTANCE);
            sqlQuery.setResultTransformer(Transformers
                    .aliasToBean(MUROIOutboundStatistics.class));

            @SuppressWarnings("unchecked")
            List<MUROIOutboundStatistics> staticticsDetails = sqlQuery.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:requestId " + requestId);
            }

            return staticticsDetails;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.RETRIEVE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    /**
     * Method to get the mrn from external source documents table
     *
     * @param requestId
     * @param patientId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<String> retrieveExternalSourceDocuments(long requestId,
            List<Long> patientIds)
    {
        final String logSM = "retrieveAllStatisticsDetails(requestId,patientId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start" + requestId + patientIds);
        }
        List<String> mrn = null;
        try
        {
            Session session = getSession();
            String query = session.getNamedQuery(
                    "retrieveExternalSourceDocuments").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.addScalar("mrn", StringType.INSTANCE);

            sqlQuery.setParameterList("patientId", patientIds, LongType.INSTANCE);
            sqlQuery.setParameter("requestId", (int) requestId,
                    IntegerType.INSTANCE);

            mrn = sqlQuery.list();
            return mrn;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.RETRIEVE_OPERATION_FAILED,
                    e.getMessage());
        }
    }
    private abstract class SQLBatchProcessor
            extends PlainSqlBatchProcessor {

        private Session _session;
        public SQLBatchProcessor(Session session) {
            _session = session;
        }

        @Override
        protected long getBatchSize() {
            return ROIConstants.SQL_BATCH_SIZE;
        }

        @Override
        protected Session getSession() {
            return _session;
        }

    }
}
