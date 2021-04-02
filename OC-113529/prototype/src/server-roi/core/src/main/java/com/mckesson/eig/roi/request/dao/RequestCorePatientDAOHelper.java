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

package com.mckesson.eig.roi.request.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.PlainSqlBatchProcessor;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.request.model.RequestDocument;
import com.mckesson.eig.roi.request.model.RequestEncounter;
import com.mckesson.eig.roi.request.model.RequestPage;
import com.mckesson.eig.roi.request.model.RequestSupplementalAttachment;
import com.mckesson.eig.roi.request.model.RequestSupplementalDocument;
import com.mckesson.eig.roi.request.model.RequestVersion;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.BeanUtilities;

/**
 * @author OFS
 * @date Aug 23, 2012
 * @since Aug 23, 2012
 * 
 */

public class RequestCorePatientDAOHelper
        extends ROIDAOImpl {

    private static final Log LOG = LogFactory
            .getLogger(RequestCoreDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     * This method updates the existing supplemental documents entry into the
     * database
     * 
     * @param suppDocuments
     * @param baseModel
     */
    public void updateSupplementalDocumentBatch(final Session session,
            final List<RequestSupplementalDocument> suppDocuments,
            final BaseModel baseModel) {

        final String logSM = "updateSupplementalDocumentBatch(supplementalAttachment, baseModel)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestSupplementalDocument :"
                    + suppDocuments);
        }

        if (null == suppDocuments || suppDocuments.isEmpty()) {
            return;
        }

        try {

            PlainSqlBatchProcessor processor = new PlainSqlBatchProcessor() {

                @Override
                protected long getBatchSize() {
                    return ROIConstants.SQL_BATCH_SIZE;
                }

                @Override
                protected Session getSession() {
                    return session;
                }

                @Override
                protected <T extends BaseModel> void addToBatch(
                        PreparedStatement pStmt,
                        T object) throws SQLException {

                    RequestSupplementalDocument supplementalDocument =
                            (RequestSupplementalDocument) object;
                    BeanUtilities.copyProperties(baseModel,
                            supplementalDocument);

                    int index = 1;
                    /*
                     * Validation to classify the supplemental attachments are
                     * belongs to HPF or NonHPF patient based on that data will
                     * insert in the different tables.
                     */

                    if (toPlong(supplementalDocument.getSupplementalId()) <= 0) {

                        // if the supplemental is zero, then it is Hpf patients
                        pStmt.setBoolean(index++,
                                supplementalDocument.isSelectedForRelease());
                        pStmt.setLong(index++,
                                supplementalDocument.getBillingTierId());
                        pStmt.setTimestamp(index++,
                                getSQLTimeStamp(supplementalDocument
                                        .getModifiedDt()));
                        pStmt.setLong(index++,
                                supplementalDocument.getModifiedBy());
                        pStmt.setLong(index++,
                                supplementalDocument.getDocumentCoreSeq());

                    } else {

                        /*
                         * If the patientSeq is not zero or less than zero, It
                         * denotes HPF user is already exists with the request
                         * and we are just adding the attachment.
                         */
                        pStmt.setLong(index++,
                                supplementalDocument.getBillingTierId());
                        pStmt.setString(index++,
                                supplementalDocument.getDocName());
                        pStmt.setString(index++,
                                supplementalDocument.getEncounter());
                        pStmt.setString(index++,
                                supplementalDocument.getDepartment());
                        pStmt.setString(index++,
                                supplementalDocument.getSubtitle());
                        pStmt.setString(index++,
                                supplementalDocument.getPageCount());
                        pStmt.setTimestamp(index++,
                                getSQLTimeStamp(supplementalDocument
                                        .getDateOfService()));
                        pStmt.setString(index++,
                                supplementalDocument.getComment());
                        pStmt.setBoolean(index++,
                                supplementalDocument.isSelectedForRelease());

                        pStmt.setTimestamp(index++,
                                getSQLTimeStamp(supplementalDocument
                                        .getModifiedDt()));
                        pStmt.setLong(index++,
                                supplementalDocument.getModifiedBy());
                        pStmt.setLong(index++,
                                supplementalDocument.getDocumentCoreSeq());
                    }
                }
            };

            String query;
            if (toPlong(suppDocuments.get(0).getSupplementalId()) <= 0) {

                query = session.getNamedQuery("updateSupplementarityDocument")
                        .getQueryString();
            } else {

                query = session.getNamedQuery("updateSupplementalDocument")
                        .getQueryString();
            }

            processor.execute(suppDocuments, query);

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>Start:RequestSupplementalAttachment :"
                        + suppDocuments);
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
     * This method updates the supplemental attachments
     * 
     * @param supplementalAttachments
     * @param baseModel
     */
    public void updateSupplementalAttachmentBatch(final Session session,
            final List<RequestSupplementalAttachment> supplementalAttachments,
            final BaseModel baseModel) {

        final String logSM = "updateSupplementalAttachmentBatch(supplementalAttachments,baseModel)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestSupplementalAttachment :"
                    + supplementalAttachments);
        }

        if (null == supplementalAttachments
                || supplementalAttachments.isEmpty()) {
            return;
        }
        try {

            PlainSqlBatchProcessor processor = new PlainSqlBatchProcessor() {

                @Override
                protected long getBatchSize() {
                    return ROIConstants.SQL_BATCH_SIZE;
                }

                @Override
                protected Session getSession() {
                    return session;
                }

                @Override
                protected <T extends BaseModel> void addToBatch(
                        PreparedStatement pStmt,
                        T object) throws SQLException {

                    RequestSupplementalAttachment supplementalAttachment =
                            (RequestSupplementalAttachment) object;
                    BeanUtilities.copyProperties(baseModel,
                            supplementalAttachment);
                    int index = 1;
                    /*
                     * Validation to classify the supplemental attachments are
                     * belongs to HPF or NonHPF patient based on that data will
                     * insert in the different tables.
                     */

                    if (toPlong(supplementalAttachment.getSupplementalId()) <= 0) {

                        // if the supplemental is zero, then it is Hpf patients
                        pStmt.setBoolean(index++,
                                supplementalAttachment.isSelectedForRelease());
                        pStmt.setLong(index++,
                                supplementalAttachment.getBillingTierId());
                        pStmt.setTimestamp(index++,
                                getSQLTimeStamp(supplementalAttachment
                                        .getModifiedDt()));
                        pStmt.setLong(index++,
                                supplementalAttachment.getModifiedBy());
                        pStmt.setString(index++, supplementalAttachment.getExternalSource());
                        pStmt.setLong(index++,
                                supplementalAttachment.getAttachmentCoreSeq());

                    } else {

                        /*
                         * If the patientSeq is not zero or less than zero, It
                         * denotes HPF user is already exists with the request
                         * and we are just adding the attachment.
                         */

                        pStmt.setLong(index++,
                                supplementalAttachment.getBillingTierId());
                        pStmt.setString(index++,
                                supplementalAttachment.getType());
                        pStmt.setString(index++,
                                supplementalAttachment.getEncounter());
                        pStmt.setString(index++,
                                supplementalAttachment.getDocFacility());
                        pStmt.setString(index++,
                                supplementalAttachment.getSubtitle());
                        pStmt.setString(index++,
                                supplementalAttachment.getPageCount());
                        pStmt.setString(index++,
                                supplementalAttachment.getIsDeleted());
                        pStmt.setTimestamp(index++,
                                getSQLTimeStamp(supplementalAttachment
                                        .getDateOfService()));
                        pStmt.setTimestamp(index++,
                                getSQLTimeStamp(supplementalAttachment
                                        .getAttachmentDate()));
                        pStmt.setString(index++,
                                supplementalAttachment.getUuid());
                        pStmt.setString(index++,
                                supplementalAttachment.getFilename());
                        pStmt.setString(index++,
                                supplementalAttachment.getVolume());
                        pStmt.setString(index++,
                                supplementalAttachment.getPath());
                        pStmt.setString(index++,
                                supplementalAttachment.getFiletype());
                        pStmt.setString(index++,
                                supplementalAttachment.getFileext());
                        pStmt.setString(index++,
                                supplementalAttachment.getPrintable());
                        pStmt.setString(index++,
                                supplementalAttachment.getSubmittedBy());
                        pStmt.setString(index++,
                                supplementalAttachment.getComment());
                        pStmt.setBoolean(index++,
                                supplementalAttachment.isSelectedForRelease());
                        pStmt.setBoolean(index++,
                                supplementalAttachment.isReleased());
                        pStmt.setTimestamp(index++,
                                getSQLTimeStamp(supplementalAttachment
                                        .getModifiedDt()));
                        pStmt.setLong(index++,
                                supplementalAttachment.getModifiedBy());
                        pStmt.setString(index++, supplementalAttachment.getExternalSource());
                        pStmt.setLong(index++,
                                supplementalAttachment.getAttachmentCoreSeq());
                    }
                }
            };

            String query;
            if (toPlong(supplementalAttachments.get(0).getSupplementalId()) <= 0) {
                // query for supplementarity attachments
                query = session
                        .getNamedQuery("updateSupplementarityAttachment")
                        .getQueryString();
            } else {
                // query for supplemental attachments
                query = session.getNamedQuery("updateSupplementalAttachment")
                        .getQueryString();
            }

            processor.execute(supplementalAttachments, query);
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>Start:RequestSupplementalAttachment :"
                        + supplementalAttachments);
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
     * The Request patient's Supplemental Document details are added to the
     * batch until the maximum batch size specified is reached.
     * 
     * Once the maximum batch size is reached the batch is executed
     * 
     * @param requestId
     * @param supplementalDocument
     * @return whether the given batch is executed i.e maximum batch size is
     *         reached
     */
    public void addSupplementalDocument(final Session session,
            final long requestId,
            final List<RequestSupplementalDocument> supplementalDocuments,
            final BaseModel baseModel) {

        final String logSM = "addSupplementalDocument(requestId, supplementalDocuments, baseModel)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestSupplementalDocument :"
                    + supplementalDocuments);
        }

        if (null == supplementalDocuments || supplementalDocuments.isEmpty()) {
            return;
        }

        try {

            PlainSqlBatchProcessor processor = new PlainSqlBatchProcessor() {

                @Override
                protected long getBatchSize() {
                    return ROIConstants.SQL_BATCH_SIZE;
                }

                @Override
                protected Session getSession() {
                    return session;
                }

                @Override
                protected <T extends BaseModel> void addToBatch(
                        PreparedStatement pStmt,
                        T object) throws SQLException {

                    RequestSupplementalDocument supplementalDocument =
                            (RequestSupplementalDocument) object;

                    BeanUtilities.copyProperties(baseModel,
                            supplementalDocument);

                    int index = 1;

                    BeanUtilities.copyProperties(baseModel,
                            supplementalDocument);
                    if (toPlong(supplementalDocument.getPatientSeq()) <= 0) {

                        // If the patientSeq is zero or less than zero,
                        // It denotes this is a new HPF User for the request.

                        pStmt.setLong(index++,
                                supplementalDocument.getSupplementalId());
                        pStmt.setLong(index++, requestId);

                    } else {

                        /*
                         * If the patientSeq is not zero or less than zero, It
                         * denotes HPF user is already exists with the request
                         * and we are just adding the document.
                         */
                        pStmt.setLong(index++,
                                supplementalDocument.getPatientSeq());
                    }

                    pStmt.setLong(index++,
                            supplementalDocument.getDocumentSeq());
                    pStmt.setLong(index++,
                            supplementalDocument.getBillingTierId());
                    pStmt.setString(index++, supplementalDocument.getDocName());
                    pStmt.setString(index++,
                            supplementalDocument.getEncounter());
                    pStmt.setString(index++,
                            supplementalDocument.getDepartment());
                    pStmt.setString(index++, supplementalDocument.getSubtitle());
                    pStmt.setString(index++,
                            supplementalDocument.getPageCount());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(supplementalDocument
                                    .getDateOfService()));
                    pStmt.setString(index++, supplementalDocument.getComment());
                    pStmt.setBoolean(index++,
                            supplementalDocument.isSelectedForRelease());
                    pStmt.setString(index++, supplementalDocument.getDocFacility());
                    pStmt.setString(index++, supplementalDocument.getFacility());
                    pStmt.setTimestamp(
                            index++,
                            getSQLTimeStamp(supplementalDocument.getCreatedDt()));
                    pStmt.setLong(index++, supplementalDocument.getCreatedBy());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(supplementalDocument
                                    .getModifiedDt()));
                    pStmt.setLong(index++, supplementalDocument.getModifiedBy());
                }
            };

            String query;
            if (toPlong(supplementalDocuments.get(0).getPatientSeq()) <= 0) {
                query = session.getNamedQuery("insertSupplementalDoucment")
                        .getQueryString();
            } else {
                query = session.getNamedQuery(
                        "insertSupplementalDoucmentWithpatientSeq")
                        .getQueryString();
            }

            processor.execute(supplementalDocuments, query);

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:");
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
     * The Request patient's Supplemental Document details are added to the
     * batch until the maximum batch size specified is reached.
     * 
     * Once the maximum batch size is reached the batch is executed
     * 
     * @param requestId
     * @param suppDocuments
     * @return whether the given batch is executed i.e maximum batch size is
     *         reached
     */
    public void addSupplementarityDocuments(final Session session,
            final long requestId,
            final List<RequestSupplementalDocument> suppDocuments,
            final BaseModel baseModel) {

        final String logSM = "addSupplementarityDocuments(requestId, suppDocuments, basseModel)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestSupplementalDocuments:"
                    + suppDocuments);
        }

        if (null == suppDocuments || suppDocuments.isEmpty()) {
            return;
        }

        try {

            PlainSqlBatchProcessor processor = new PlainSqlBatchProcessor() {

                @Override
                protected long getBatchSize() {
                    return ROIConstants.SQL_BATCH_SIZE;
                }

                @Override
                protected Session getSession() {
                    return session;
                }

                @Override
                protected <T extends BaseModel> void addToBatch(
                        PreparedStatement pStmt,
                        T object) throws SQLException {

                    RequestSupplementalDocument suppDocument = (RequestSupplementalDocument) object;
                    BeanUtilities.copyProperties(baseModel, suppDocument);

                    int index = 1;

                    /*
                     * Validation to classify the supplemental documents are
                     * belongs to HPF or NonHPF patient based on that data will
                     * insert in the different tables.
                     */

                    pStmt.setString(index++, suppDocument.getMrn());
                    pStmt.setString(index++, suppDocument.getFacility());
                    if (toPlong(suppDocument.getPatientSeq()) <= 0) {

                        // If the patientSeq is zero or less than zero,
                        // It denotes this is a new HPF User for the request.
                        pStmt.setString(index++, suppDocument.getMrn());
                        pStmt.setString(index++, suppDocument.getFacility());
                        pStmt.setLong(index++, requestId);

                    } else {

                        /*
                         * If the patientSeq is not zero or less than zero, It
                         * denotes HPF user is already exists with the request
                         * and we are just adding the document.
                         */
                        pStmt.setLong(index++, suppDocument.getPatientSeq());

                    }

                    pStmt.setLong(index++, suppDocument.getDocumentSeq());
                    pStmt.setLong(index++, suppDocument.getBillingTierId());
                    pStmt.setString(index++, suppDocument.getDocName());
                    pStmt.setString(index++, suppDocument.getEncounter());
                    pStmt.setString(index++, suppDocument.getDocFacility());
                    pStmt.setString(index++, suppDocument.getDepartment());
                    pStmt.setString(index++, suppDocument.getSubtitle());
                    pStmt.setString(index++, suppDocument.getPageCount());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(suppDocument.getDateOfService()));
                    pStmt.setString(index++, suppDocument.getComment());
                    pStmt.setBoolean(index++,
                            suppDocument.isSelectedForRelease());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(suppDocument.getCreatedDt()));
                    pStmt.setLong(index++, suppDocument.getCreatedBy());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(suppDocument.getModifiedDt()));
                    pStmt.setLong(index++, suppDocument.getModifiedBy());

                }
            };

            String query;
            if (toPlong(suppDocuments.get(0).getPatientSeq()) <= 0) {
                query = session.getNamedQuery("insertSupplementarityDoucment")
                        .getQueryString();
            } else {
                query = session.getNamedQuery(
                        "insertSupplementarityDoucmentWithpatientSeq")
                        .getQueryString();
            }

            processor.execute(suppDocuments, query);

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:RequestSupplementalDocument :"
                        + suppDocuments);
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
     * The Request patient's Supplemental Attachment details are added to the
     * batch until the maximum batch size specified is reached.
     * 
     * Once the maximum batch size is reached the batch is executed
     * 
     * @param requestId
     * @param supplementalAttachment
     *            .
     * @return whether the given batch is executed i.e maximum batch size is
     *         reached
     */
    public void addSupplementalAttachment(final Session session,
            final long requestId,
            final List<RequestSupplementalAttachment> supplementalAttachments,
            final BaseModel baseModel) {

        final String logSM = "addSupplementalAttachmentBatch(requestId, supplementalAttachment)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestSupplementalAttachment :"
                    + supplementalAttachments);
        }

        if (null == supplementalAttachments
                || supplementalAttachments.isEmpty()) {
            return;
        }

        try {

            PlainSqlBatchProcessor processor = new PlainSqlBatchProcessor() {

                @Override
                protected long getBatchSize() {
                    return ROIConstants.SQL_BATCH_SIZE;
                }

                @Override
                protected Session getSession() {
                    return session;
                }

                @Override
                protected <T extends BaseModel> void addToBatch(
                        PreparedStatement pStmt,
                        T object) throws SQLException {

                    RequestSupplementalAttachment suppAttachment =
                            (RequestSupplementalAttachment) object;
                    BeanUtilities.copyProperties(baseModel, suppAttachment);

                    int index = 1;
                    /*
                     * Validation to classify the supplemental attachments are
                     * belongs to HPF or NonHPF patient based on that data will
                     * insert in the different tables.
                     */

                    if (toPlong(suppAttachment.getPatientSeq()) <= 0) {

                        // If the patientSeq is zero or less than zero,
                        // It denotes this is a new HPF User for the request.
                        pStmt.setLong(index++,
                                suppAttachment.getSupplementalId());
                        pStmt.setLong(index++, requestId);

                    } else {

                        /*
                         * If the patientSeq is not zero or less than zero, It
                         * denotes HPF user is already exists with the request
                         * and we are just adding the attachment.
                         */
                        pStmt.setLong(index++, suppAttachment.getPatientSeq());
                    }

                    pStmt.setLong(index++, suppAttachment.getAttachmentSeq());
                    pStmt.setLong(index++, suppAttachment.getBillingTierId());
                    pStmt.setString(index++, suppAttachment.getType());
                    pStmt.setString(index++, suppAttachment.getEncounter());
                    pStmt.setString(index++, suppAttachment.getDocFacility());
                    pStmt.setString(index++, suppAttachment.getFacility());
                    pStmt.setString(index++, suppAttachment.getSubtitle());
                    pStmt.setString(index++, suppAttachment.getPageCount());
                    pStmt.setString(index++, suppAttachment.getIsDeleted());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(suppAttachment.getDateOfService()));
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(suppAttachment.getAttachmentDate()));
                    pStmt.setString(index++, suppAttachment.getUuid());
                    pStmt.setString(index++, suppAttachment.getFilename());
                    pStmt.setString(index++, suppAttachment.getVolume());
                    pStmt.setString(index++, suppAttachment.getPath());
                    pStmt.setString(index++, suppAttachment.getFiletype());
                    pStmt.setString(index++, suppAttachment.getFileext());
                    pStmt.setString(index++, suppAttachment.getPrintable());
                    pStmt.setString(index++, suppAttachment.getSubmittedBy());
                    pStmt.setString(index++, suppAttachment.getComment());
                    pStmt.setBoolean(index++,
                            suppAttachment.isSelectedForRelease());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(suppAttachment.getCreatedDt()));
                    pStmt.setLong(index++, suppAttachment.getCreatedBy());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(suppAttachment.getModifiedDt()));
                    pStmt.setLong(index++, suppAttachment.getModifiedBy());
                    pStmt.setString(index++, suppAttachment.getExternalSource());

                }
            };

            String query;
            if (toPlong(supplementalAttachments.get(0).getPatientSeq()) <= 0) {

                query = session.getNamedQuery("insertSupplementalAttachment")
                        .getQueryString();
            } else {

                query = session.getNamedQuery(
                        "insertSupplementalAttachmentWithpatientSeq")
                        .getQueryString();
            }
            processor.execute(supplementalAttachments, query);

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:RequestSupplementalAttachment :"
                        + supplementalAttachments);
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
     * The Request patient's Supplemental Attachment details are added to the
     * batch until the maximum batch size specified is reached.
     * 
     * Once the maximum batch size is reached the batch is executed
     * 
     * @param requestId
     * @param supplementalAttachment
     *            .
     * @return whether the given batch is executed i.e maximum batch size is
     *         reached
     */
    public void addSupplementarityAttachment(final Session session,
            final long requestId,
            final List<RequestSupplementalAttachment> supplementalAttachments,
            final BaseModel baseModel) {

        final String logSM = "addSupplementarityAttachment(requestId, supplementalAttachment, "
                + "baseModel)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestSupplementalAttachment :"
                    + supplementalAttachments);
        }

        if (null == supplementalAttachments
                || supplementalAttachments.isEmpty()) {
            return;
        }

        try {

            PlainSqlBatchProcessor processor = new PlainSqlBatchProcessor() {

                @Override
                protected long getBatchSize() {
                    return ROIConstants.SQL_BATCH_SIZE;
                }

                @Override
                protected Session getSession() {
                    return session;
                }

                @Override
                protected <T extends BaseModel> void addToBatch(
                        PreparedStatement pStmt,
                        T object) throws SQLException {

                    RequestSupplementalAttachment suppAttachment =
                            (RequestSupplementalAttachment) object;
                    BeanUtilities.copyProperties(baseModel, suppAttachment);

                    int index = 1;
                    /*
                     * Validation to classify the supplemental attachments are
                     * belongs to HPF or NonHPF patient based on that data will
                     * insert in the different tables.
                     */

                    pStmt.setString(index++, suppAttachment.getMrn());
                    pStmt.setString(index++, suppAttachment.getFacility());
                    if (toPlong(suppAttachment.getPatientSeq()) <= 0) {

                        pStmt.setString(index++, suppAttachment.getMrn());
                        pStmt.setString(index++, suppAttachment.getFacility());
                        pStmt.setLong(index++, requestId);

                    } else {

                        /*
                         * If the patientSeq is not zero or less than zero, It
                         * denotes HPF user is already exists with the request
                         * and we are just adding the attachment.
                         */
                        pStmt.setLong(index++, suppAttachment.getPatientSeq());
                    }

                    pStmt.setLong(index++, suppAttachment.getAttachmentSeq());
                    pStmt.setLong(index++, suppAttachment.getBillingTierId());
                    pStmt.setString(index++, suppAttachment.getType());
                    pStmt.setString(index++, suppAttachment.getEncounter());
                    pStmt.setString(index++, suppAttachment.getDocFacility());
                    pStmt.setString(index++, suppAttachment.getSubtitle());
                    pStmt.setString(index++, suppAttachment.getPageCount());
                    pStmt.setString(index++, suppAttachment.getIsDeleted());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(suppAttachment.getDateOfService()));
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(suppAttachment.getAttachmentDate()));
                    pStmt.setString(index++, suppAttachment.getUuid());
                    pStmt.setString(index++, suppAttachment.getFilename());
                    pStmt.setString(index++, suppAttachment.getVolume());
                    pStmt.setString(index++, suppAttachment.getPath());
                    pStmt.setString(index++, suppAttachment.getFiletype());
                    pStmt.setString(index++, suppAttachment.getFileext());
                    pStmt.setString(index++, suppAttachment.getPrintable());
                    pStmt.setString(index++, suppAttachment.getSubmittedBy());
                    pStmt.setString(index++, suppAttachment.getComment());
                    pStmt.setBoolean(index++,
                            suppAttachment.isSelectedForRelease());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(suppAttachment.getCreatedDt()));
                    pStmt.setLong(index++, suppAttachment.getCreatedBy());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(suppAttachment.getModifiedDt()));
                    pStmt.setLong(index++, suppAttachment.getModifiedBy());
                    pStmt.setString(index++, suppAttachment.getExternalSource());
                }
            };

            // If the patientSeq is zero or less than zero,
            // It denotes this is a new HPF User for the request.
            String query;
            if (toPlong(supplementalAttachments.get(0).getPatientSeq()) <= 0) {
                query = session
                        .getNamedQuery("insertSupplementarityAttachment")
                        .getQueryString();
            } else {
                query = session.getNamedQuery(
                        "insertSupplementarityAttachmentWithpatientSeq")
                        .getQueryString();
            }
            processor.execute(supplementalAttachments, query);

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:RequestSupplementalAttachment :"
                        + supplementalAttachments);
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
     * This method deletes the patient details for the request
     * 
     * @param patients
     * @param requestId
     * @param b
     */
    public void deletePatients(Session session, List<Long> patients,
            long requestId,
            boolean isDARPatientDeleted) {

        final String logSM = "deletePatients(List<Long> patients)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Patients :" + patients);
        }

        if (patients == null || patients.isEmpty()) {
            return;
        }
        try {

            String query = session.getNamedQuery("getEncounterListForPatient")
                    .getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.addScalar("encounterSeq", StandardBasicTypes.LONG);
            sqlQuery.setParameterList("patientSeqList", patients);

            @SuppressWarnings("unchecked")
            List<Long> encounterList = sqlQuery.list();

            // Deleting all Documents,Versions & Pages from the Encounter
            deleteEncounters(session, encounterList);

            // All the supplementarity attachments are deleted using the given
            // named Query
            deleteRequestPatientDetailsById(session, patients,
                    "deleteAllSupplementarityAttachments");

            // All the supplemental attachments are deleted using the given
            // named Query
            deleteRequestPatientDetailsById(session, patients,
                    "deleteAllSupplementarityDocuments");

            // All the invoice related hpf patients are deleted using the given
            // named Query
            deleteRequestPatientDetailsById(session, patients,
                    "deleteInvoicePatientsBySeq");

            // if the patient is not removed from the DAR then the patient is
            // not deleted.
            if (!isDARPatientDeleted) {

                if (DO_DEBUG) {
                    LOG.debug(logSM + "<<End:Patients " + patients);
                }
                return;
            }

            deleteRequestPatients(session, patients, requestId);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Patients " + patients);
            }

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
                    ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    /**
     * This method deletes the patient details for the request
     * 
     * @param patients
     * @param requestId
     * @param b
     */
    public void deleteSupplementalPatients(Session session,
            List<Long> patients,
            long requestId,
            boolean isDARPatientDeleted) {

        final String logSM = "deleteSupplementalPatients(patientseq, requestId, idDarSelected)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Patients :" + patients);
        }

        if (patients == null || patients.isEmpty()) {
            return;
        }
        try {

            // All the supplementarity attachments are deleted using the given
            // named Query
            deleteRequestPatientDetailsById(session, patients,
                    "deleteAllSupplementalAttachments");

            // All the supplemental attachments are deleted using the given
            // named Query
            deleteRequestPatientDetailsById(session, patients,
                    "deleteAllSupplementalDocuments");

            // if the patient is not removed from the DAR then the patient is
            // not deleted.
            if (!isDARPatientDeleted) {

                if (DO_DEBUG) {
                    LOG.debug(logSM + "<<End:Patients " + patients);
                }
                return;
            }
            // deletes the mapping values from requestCore to
            // supplementalPatients
            deleteRequestPatientDetailsById(session, patients,
                    "deleteRequestCoreToSupplementalPatientsBySeq");
            // deletes the invoice supplemental patients
            deleteRequestPatientDetailsById(session, patients,
                    "deleteInvoiceSupplementalPatientsBySeq");
            // deletes the cover letter associated with the supplemental patients
            deleteRequestPatientDetailsById(session, patients,
                    "deleteCoverLetterByRequestNonHpfPatientId");
            // deletes the supplementalPatients
            deleteRequestPatientDetailsById(session, patients,
                    "deleteSupplementalPatientsBySeq");

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

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
                    ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    public void deleteRequestPatientDetailsById(Session session,
            List<Long> sequenceList,
            String queryName) {

        final String logSM = "deleteRequestPatientDetailsById(sequenceList, queryName)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:SequenceList :" + sequenceList
                    + ", QueryName:" + queryName);
        }

        if (sequenceList == null || sequenceList.isEmpty()) {

            LOG.debug("Sequence list is empty, Hence Skipped");
            return;
        }
        try {

            Query sqlQuery = session.getNamedQuery(queryName);
            
            List<Long> paramList = new ArrayList<Long>();
            int size = sequenceList.size();
            int noOfRowsDeleted = 0;
            
            /*
             *  As prepared statement has not been allowed to execute with more than 2000 parameters markers,
             *  we are splitting the total parameters as 2000 per iteration, and finally consolidate all the return results 
             *  from each iterations.
             *  
             */
            for (int i = 0; i < size; i += ROIConstants.SQL_PARAMETERS_SIZE) {
                
                paramList.clear();
                int toIndex = ((i + ROIConstants.SQL_PARAMETERS_SIZE) > size) ? size 
                                        : (i + ROIConstants.SQL_PARAMETERS_SIZE);
                
                paramList.addAll(sequenceList.subList(i, toIndex));
                
                sqlQuery.setParameterList("seqList", paramList);
                noOfRowsDeleted += sqlQuery.executeUpdate();
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:No.Of Rows affected: "
                        + noOfRowsDeleted);
            }

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
                    ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    /**
     * Deletes the patients information
     * 
     * @param patients
     * @param requestId
     */
    public void deleteRequestPatients(Session session, List<Long> patients,
            long requestId) {

        try {

            // Delete the Entry in ROI_RequestCoretoROI_Patients_Seq
            Query query = session.getNamedQuery("deleteRequestCoreRoiPatient");
            query.setLong("requestId", requestId);
            query.setParameterList("seqList", patients);
            query.executeUpdate();

            // deletes the cover letter associated with the patient
            deleteRequestPatientDetailsById(session, patients, "deleteCoverLetterByRequestHpfPatientId");
            
            // Delete the Patient
            deleteRequestPatientDetailsById(session, patients, "deletePatients");

        } catch (Exception cause) {
            throw new ROIException(ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    "Failed to delete Patients");
        }
    }

    /**
     * This method deletes the encounter based on the patient
     * 
     * @param encounters
     */
    @SuppressWarnings("unchecked")
    public void deleteEncounters(Session session, List<Long> encounters) {

        final String logSM = "deleteEncounter(List<Long> encounter)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:encounter :" + encounters);
        }

        if (encounters == null || encounters.isEmpty()) {
            return;
        }

        try {

            String query = session
                    .getNamedQuery("getDocumentsListForEncounter")
                    .getQueryString();

            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.addScalar("documentSeq", StandardBasicTypes.LONG);
            List<Long> docList = new ArrayList<Long>();
            
            List<Long> paramList = new ArrayList<Long>();
            int size = encounters.size();
            
            /*
             *  Unable to apply batch process because execute method returns number of rows affected as integer value only.
             *  But we need list of documents based on respective scenario's.
             *  As prepared statement has not been allowed to execute with more than 2000 parameters markers,
             *  we are splitting the total parameters as 2000 per iteration, and finally consolidate all the return results 
             *  from each iterations.
             *  
             */
            for (int i = 0; i < size; i += ROIConstants.SQL_PARAMETERS_SIZE) {
                
                paramList.clear();
                int toIndex = ((i + ROIConstants.SQL_PARAMETERS_SIZE) > size) ? size : (i + ROIConstants.SQL_PARAMETERS_SIZE);
                
                paramList.addAll(encounters.subList(i, toIndex));
                
                sqlQuery.setParameterList("encounterSeqList", paramList);
                List<Long> temp = sqlQuery.list();
                docList.addAll(temp);
            }

            // Deleting all Documents,Versions & Pages from the Encounter
            deleteDocuments(session, docList);

            // Delete the Encounter
            deleteRequestPatientDetailsById(session, encounters,
                    "deleteEncounters");

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Encounter " + encounters);
            }

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
                    ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    /**
     * This method deletes the document based on the encounter
     * 
     * @param documents
     */
    @SuppressWarnings("unchecked")
    public void deleteDocuments(Session session, List<Long> documents) {

        final String logSM = "deleteDocuments(List<Long> documents)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:documentSeq:" + documents);
        }

        if (documents == null || documents.isEmpty()) {
            return;
        }

        try {

            String query = session.getNamedQuery("getVersionListForDocument")
                    .getQueryString();

            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.addScalar("versionSeq", StandardBasicTypes.LONG);
            
            List<Long> versionList = new ArrayList<Long>();
            List<Long> paramList = new ArrayList<Long>();
            int size = documents.size();
            
            /*
             *  Unable to apply batch process because execute method returns number of rows affected as integer value only.
             *  But we need list of versions based on respective scenario's.
             *  As prepared statement has not been allowed to execute with more than 2000 parameters markers,
             *  we are splitting the total parameters as 2000 per iteration, and finally consolidate all the return results 
             *  from each iterations.
             *  
             */
            for (int i = 0; i < size; i += ROIConstants.SQL_PARAMETERS_SIZE) {
                
                paramList.clear();
                int toIndex = ((i + ROIConstants.SQL_PARAMETERS_SIZE) > size) ? size : (i + ROIConstants.SQL_PARAMETERS_SIZE);
                
                paramList.addAll(documents.subList(i, toIndex));
                
                sqlQuery.setParameterList("documentSeqList", paramList);
                List<Long> temp = sqlQuery.list();
                versionList.addAll(temp);
            }
            
            deleteVersions(session, versionList);

            // Delete the Document
            deleteRequestPatientDetailsById(session, documents,
                    "deleteDocuments");

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:DocumentId :" + documents);
            }

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
                    ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    /**
     * This method deletes the document based on the encounter
     * 
     * @param documents
     */
    @SuppressWarnings("unchecked")
    public void deleteGlobalDocuments(Session session, List<Long> patients) {

        final String logSM = "deleteDocuments(List<Long> documents)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:PatientSeq:" + patients);
        }

        if (patients == null || patients.isEmpty()) {
            return;
        }

        try {

            String query = session.getNamedQuery(
                    "getGlobalDocumentsListForPatient").getQueryString();

            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.addScalar("documentSeq", StandardBasicTypes.LONG);
            sqlQuery.setParameterList("patientSeqList", patients);
            List<Long> documents = sqlQuery.list();

            if (documents == null || documents.isEmpty()) {
                return;
            }
            query = session.getNamedQuery("getVersionListForDocument")
                    .getQueryString();

            sqlQuery = session.createSQLQuery(query);
            sqlQuery.addScalar("versionSeq", StandardBasicTypes.LONG);
            sqlQuery.setParameterList("documentSeqList", documents);

            List<Long> versionList = sqlQuery.list();
            deleteVersions(session, versionList);

            // Delete the Document
            deleteRequestPatientDetailsById(session, documents,
                    "deleteDocuments");

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:DocumentId :" + documents);
            }

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
                    ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    /**
     * This method deletes the version based on the documents
     * 
     * @param versions
     */
    @SuppressWarnings("unchecked")
    public void deleteVersions(Session session, List<Long> versions) {

        final String logSM = "deleteVersions(List<Long> versions)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Version :" + versions);
        }

        if (versions == null || versions.isEmpty()) {
            return;
        }

        try {

            String query = session.getNamedQuery("getPageListForVersion")
                    .getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.addScalar("pageSeq", StandardBasicTypes.LONG);

            List<Long> pageList = new ArrayList<Long>();
            List<Long> paramList = new ArrayList<Long>();
            int size = versions.size();
            /*
             *  Unable to apply batch process because execute method returns number of rows affected as integer value only.
             *  But we need list of pages based on respective scenario's.
             *  As prepared statement has not been allowed to execute with more than 2000 parameters markers,
             *  we are splitting the total parameters as 2000 per iteration, and finally consolidate all the return results 
             *  from each iterations.
             *  
             */
            for (int i = 0; i < size; i += ROIConstants.SQL_PARAMETERS_SIZE) {
                
                paramList.clear();
                int toIndex = ((i + ROIConstants.SQL_PARAMETERS_SIZE) > size) ? size : (i + ROIConstants.SQL_PARAMETERS_SIZE);
                
                paramList.addAll(versions.subList(i, toIndex));
                
                sqlQuery.setParameterList("versionSeqList", paramList);
                List<Long> temp = sqlQuery.list();
                pageList.addAll(temp);
            }
            
            deletePages(session, pageList);

            // Delete the Document
            deleteRequestPatientDetailsById(session, versions, "deleteVersions");

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>END :");
            }

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
                    ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    /**
     * This method deletes the page based on version
     * 
     * @param pages
     */
    public void deletePages(Session session, List<Long> pages) {

        final String logSM = "deletePages(List<Long> pages)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: Page :" + pages);
        }

        if (pages == null || pages.isEmpty()) {
            return;
        }

        try {

            // Delete the Page
            deleteRequestPatientDetailsById(session, pages, "deletePages");

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<END:");
            }

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
                    ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    /**
     * This method Retrieves the list of patients that are related to the
     * request.
     * 
     * @param requestId
     * @return list of patients
     */
    public List<RequestSupplementalDocument> retrieveAllAttachmentsForRequest(
            Session session,
            long requestId) {

        final String logSM = "retrieveAllAttachmentsForRequest(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestId " + requestId);
        }

        try {

            String query = session.getNamedQuery(
                    "retrieveAllAttachmentsByRequest")
                    .getQueryString();

            SQLQuery sqlQuery = session.createSQLQuery(query);

            sqlQuery.addScalar("attachmentCoreSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("attachmentSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("patientSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("supplementalId", StandardBasicTypes.LONG);
            sqlQuery.addScalar("mrn", StandardBasicTypes.STRING);
            sqlQuery.addScalar("facility", StandardBasicTypes.STRING);
            sqlQuery.addScalar("type", StandardBasicTypes.STRING);
            sqlQuery.addScalar("encounter", StandardBasicTypes.STRING);
            sqlQuery.addScalar("docFacility", StandardBasicTypes.STRING);
            sqlQuery.addScalar("subtitle", StandardBasicTypes.STRING);
            sqlQuery.addScalar("pageCount", StandardBasicTypes.STRING);
            sqlQuery.addScalar("isDeleted", StandardBasicTypes.STRING);
            sqlQuery.addScalar("dateOfService", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("attachmentDate", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("uuid", StandardBasicTypes.STRING);
            sqlQuery.addScalar("filename", StandardBasicTypes.STRING);
            sqlQuery.addScalar("volume", StandardBasicTypes.STRING);
            sqlQuery.addScalar("path", StandardBasicTypes.STRING);
            sqlQuery.addScalar("filetype", StandardBasicTypes.STRING);
            sqlQuery.addScalar("fileext", StandardBasicTypes.STRING);
            sqlQuery.addScalar("printable", StandardBasicTypes.STRING);
            sqlQuery.addScalar("submittedBy", StandardBasicTypes.STRING);
            sqlQuery.addScalar("comment", StandardBasicTypes.STRING);
            sqlQuery.addScalar("selectedForRelease", StandardBasicTypes.BOOLEAN);
            sqlQuery.addScalar("released", StandardBasicTypes.BOOLEAN);
            sqlQuery.addScalar("billingTierId", StandardBasicTypes.LONG);

            sqlQuery.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("modifiedBy", StandardBasicTypes.INTEGER);
            sqlQuery.addScalar("createdBy", StandardBasicTypes.INTEGER);

            sqlQuery.setParameter("requestId", requestId, StandardBasicTypes.LONG);
            sqlQuery.setResultTransformer(Transformers.aliasToBean(
                    RequestSupplementalAttachment.class));

            @SuppressWarnings("unchecked")
            List<RequestSupplementalDocument> requestNonhpfDocs =
                    sqlQuery.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:RequestId " + requestId);
            }

            return requestNonhpfDocs;

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
     * This method Retrieves the list of patients that are related to the
     * request.
     * 
     * @param requestId
     * @return list of patients
     */
    public List<RequestSupplementalDocument> retrieveAllNonHpfDocumentsForRequest(
            Session session,
            long requestId) {

        final String logSM = "retrieveAllNonHpfDocumentsForRequest(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestId " + requestId);
        }

        try {

            String query = session.getNamedQuery(
                    "retrieveAllNonHpfDocumentsByRequest")
                    .getQueryString();

            SQLQuery sqlQuery = session.createSQLQuery(query);

            sqlQuery.addScalar("documentCoreSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("documentSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("patientSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("supplementalId", StandardBasicTypes.LONG);
            sqlQuery.addScalar("mrn", StandardBasicTypes.STRING);
            sqlQuery.addScalar("facility", StandardBasicTypes.STRING);
            sqlQuery.addScalar("docName", StandardBasicTypes.STRING);
            sqlQuery.addScalar("encounter", StandardBasicTypes.STRING);
            sqlQuery.addScalar("docFacility", StandardBasicTypes.STRING);
            sqlQuery.addScalar("department", StandardBasicTypes.STRING);
            sqlQuery.addScalar("subtitle", StandardBasicTypes.STRING);
            sqlQuery.addScalar("pageCount", StandardBasicTypes.STRING);
            sqlQuery.addScalar("dateOfService", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("comment", StandardBasicTypes.STRING);
            sqlQuery.addScalar("selectedForRelease", StandardBasicTypes.BOOLEAN);
            sqlQuery.addScalar("released", StandardBasicTypes.BOOLEAN);
            sqlQuery.addScalar("billingTierId", StandardBasicTypes.LONG);

            sqlQuery.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("modifiedBy", StandardBasicTypes.INTEGER);
            sqlQuery.addScalar("createdBy", StandardBasicTypes.INTEGER);

            sqlQuery.setParameter("requestId", requestId, StandardBasicTypes.LONG);
            sqlQuery.setResultTransformer(Transformers.aliasToBean(
                    RequestSupplementalDocument.class));

            @SuppressWarnings("unchecked")
            List<RequestSupplementalDocument> requestNonhpfDocs =
                    sqlQuery.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:RequestId " + requestId);
            }

            return requestNonhpfDocs;

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
     * This method Retrieves the list of all encounters for the request
     * 
     * @param requestId
     * @return list of request encounters
     */
    public List<RequestEncounter> retrieveAllEncounterForRequest(
            Session session, long requestId) {

        final String logSM = "retrieveAllEncounterForRequest(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestId " + requestId);
        }

        try {

            String query =
                    session.getNamedQuery("retrieveRequestPatientEncounter")
                            .getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);

            sqlQuery.addScalar("encounterSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("patientSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("name", StandardBasicTypes.STRING);
            sqlQuery.addScalar("facility", StandardBasicTypes.STRING);
            sqlQuery.addScalar("patientType", StandardBasicTypes.STRING);
            sqlQuery.addScalar("patientService", StandardBasicTypes.STRING);
            sqlQuery.addScalar("mrn", StandardBasicTypes.STRING);

            sqlQuery.addScalar("vip", StandardBasicTypes.BOOLEAN);
            sqlQuery.addScalar("locked", StandardBasicTypes.BOOLEAN);
            sqlQuery.addScalar("hasDeficiency", StandardBasicTypes.BOOLEAN);
            sqlQuery.addScalar("admitdate", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("dischargeDate", StandardBasicTypes.TIMESTAMP);

            sqlQuery.setParameter("requestid", requestId, StandardBasicTypes.LONG);
            sqlQuery.setResultTransformer(Transformers
                    .aliasToBean(RequestEncounter.class));

            @SuppressWarnings("unchecked")
            List<RequestEncounter> encounterDetails = sqlQuery.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:requestId " + requestId);
            }

            return encounterDetails;

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
     * retrieves the list of all documents for the request
     * 
     * @param requestId
     * @return list of request documents
     */
    public List<RequestDocument> retrieveAllDocumentForRequest(Session session,
            long requestId) {

        final String logSM = "retrieveAllDocumentForRequest(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestId" + requestId);
        }

        try {

            String query = session.getNamedQuery(
                    "retrieveRequestPatientDocument").getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);

            sqlQuery.addScalar("documentSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("encounterSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("patientSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("name", StandardBasicTypes.STRING);
            sqlQuery.addScalar("chartOrder", StandardBasicTypes.STRING);
            sqlQuery.addScalar("subtitle", StandardBasicTypes.STRING);
            // sqlQuery.addScalar("duid", StandardBasicTypes.STRING);
            sqlQuery.addScalar("docId", StandardBasicTypes.LONG);
            sqlQuery.addScalar("docTypeId", StandardBasicTypes.LONG);
            sqlQuery.addScalar("dateTime", StandardBasicTypes.TIMESTAMP);

            sqlQuery.setParameter("requestid", requestId, StandardBasicTypes.LONG);
            sqlQuery.setResultTransformer(Transformers
                    .aliasToBean(RequestDocument.class));

            @SuppressWarnings("unchecked")
            List<RequestDocument> documentDetails = sqlQuery.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:RequestId : " + requestId);
            }

            return documentDetails;

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
     * retrieves the list of all documents for the request
     * 
     * @param requestId
     * @return list of request documents
     */
    public List<RequestDocument> retrieveAllGlobalDocumentForRequest(
            Session session,
            long requestId) {

        final String logSM = "retrieveAllGlobalDocumentForRequest(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestId" + requestId);
        }

        try {

            String query = session.getNamedQuery(
                    "retrieveRequestPatientGlobalDocument")
                    .getQueryString();

            SQLQuery sqlQuery = session.createSQLQuery(query);

            sqlQuery.addScalar("documentSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("encounterSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("patientSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("name", StandardBasicTypes.STRING);
            sqlQuery.addScalar("chartOrder", StandardBasicTypes.STRING);
            sqlQuery.addScalar("subtitle", StandardBasicTypes.STRING);
            // sqlQuery.addScalar("duid", StandardBasicTypes.STRING);
            sqlQuery.addScalar("docId", StandardBasicTypes.LONG);
            sqlQuery.addScalar("docTypeId", StandardBasicTypes.LONG);
            sqlQuery.addScalar("dateTime", StandardBasicTypes.TIMESTAMP);

            sqlQuery.setParameter("requestid", requestId, StandardBasicTypes.LONG);
            sqlQuery.setResultTransformer(Transformers
                    .aliasToBean(RequestDocument.class));

            @SuppressWarnings("unchecked")
            List<RequestDocument> documentDetails = sqlQuery.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:RequestId : " + requestId);
            }

            return documentDetails;

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
     * retrieves the list of all documents for the request
     * 
     * @param requestId
     * @return list of request documents
     */
    public List<RequestVersion> retrieveAllGlobalDocumentVersionsForRequest(
            Session session,
            long requestId) {

        final String logSM = "retrieveAllGlobalDocumentVersionsForRequest(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestId" + requestId);
        }

        try {

            String query = session.getNamedQuery(
                    "retrieveRequestPatientGlobalDocumentVersions")
                    .getQueryString();

            SQLQuery sqlQuery = session.createSQLQuery(query);

            sqlQuery.addScalar("versionSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("documentSeq", StandardBasicTypes.LONG);

            sqlQuery.addScalar("versionNumber", StandardBasicTypes.LONG);
            sqlQuery.addScalar("docId", StandardBasicTypes.LONG);

            sqlQuery.setParameter("requestid", requestId, StandardBasicTypes.LONG);
            sqlQuery.setResultTransformer(Transformers
                    .aliasToBean(RequestVersion.class));

            @SuppressWarnings("unchecked")
            List<RequestVersion> versionDetails = sqlQuery.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:RequestId : " + requestId);
            }

            return versionDetails;

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
     * retrieves the list of all documents for the request
     * 
     * @param requestId
     * @return list of request documents
     */
    public List<RequestPage> retrieveAllGlobalDocumentPagesForRequest(
            Session session,
            long requestId) {

        final String logSM = "retrieveAllGlobalDocumentPagesForRequest(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestId" + requestId);
        }

        try {

            String query = session.getNamedQuery(
                    "retrieveRequestPatientGlobalDocumentPages")
                    .getQueryString();

            SQLQuery sqlQuery = session.createSQLQuery(query);

            sqlQuery.addScalar("pageSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("versionSeq", StandardBasicTypes.LONG);

            sqlQuery.addScalar("imnetId", StandardBasicTypes.STRING);
            sqlQuery.addScalar("contentCount", StandardBasicTypes.LONG);
            sqlQuery.addScalar("selectedForRelease", StandardBasicTypes.BOOLEAN);
            sqlQuery.addScalar("released", StandardBasicTypes.BOOLEAN);
            sqlQuery.addScalar("pageNumber", StandardBasicTypes.INTEGER);
            sqlQuery.addScalar("pageNumberRequested", StandardBasicTypes.INTEGER);
            sqlQuery.addScalar("deleted", StandardBasicTypes.BOOLEAN);

            sqlQuery.setParameter("requestid", requestId, StandardBasicTypes.LONG);
            sqlQuery.setResultTransformer(Transformers
                    .aliasToBean(RequestPage.class));

            @SuppressWarnings("unchecked")
            List<RequestPage> pageDetails = sqlQuery.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:requestid " + requestId);
            }
            return pageDetails;

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
     * retrieves the list of all versions for the request
     * 
     * @param requestId
     * @return list of all request versions
     */
    public List<RequestVersion> retrieveAllVersionForRequest(Session session,
            long requestId) {

        final String logSM = "retrieveAllVersionForRequest(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestId" + requestId);
        }

        try {

            String query =
                    session.getNamedQuery("retrieveRequestDocumentsVersion")
                            .getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);

            sqlQuery.addScalar("versionSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("documentSeq", StandardBasicTypes.LONG);

            sqlQuery.addScalar("versionNumber", StandardBasicTypes.LONG);
            sqlQuery.addScalar("docId", StandardBasicTypes.LONG);

            sqlQuery.setParameter("requestid", requestId, StandardBasicTypes.LONG);
            sqlQuery.setResultTransformer(Transformers
                    .aliasToBean(RequestVersion.class));

            @SuppressWarnings("unchecked")
            List<RequestVersion> versionDetails = sqlQuery.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:versionSeq " + requestId);
            }
            return versionDetails;

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
     * retrieves the list of all pages for the request
     * 
     * @param requestId
     * @return list of request pages
     */
    public List<RequestPage> retrieveAllPageForRequest(Session session,
            long requestId) {

        final String logSM = "retrieveAllPageForRequest(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Requestid " + requestId);
        }

        try {

            String query = session
                    .getNamedQuery("retrieveRequestDocumentsPage")
                    .getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);

            sqlQuery.addScalar("pageSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("versionSeq", StandardBasicTypes.LONG);

            sqlQuery.addScalar("imnetId", StandardBasicTypes.STRING);
            sqlQuery.addScalar("contentCount", StandardBasicTypes.LONG);
            sqlQuery.addScalar("selectedForRelease", StandardBasicTypes.BOOLEAN);
            sqlQuery.addScalar("released", StandardBasicTypes.BOOLEAN);
            sqlQuery.addScalar("pageNumber", StandardBasicTypes.INTEGER);
            sqlQuery.addScalar("pageNumberRequested", StandardBasicTypes.INTEGER);
            sqlQuery.addScalar("deleted", StandardBasicTypes.BOOLEAN);

            sqlQuery.setParameter("requestid", requestId, StandardBasicTypes.LONG);
            sqlQuery.setResultTransformer(Transformers
                    .aliasToBean(RequestPage.class));

            @SuppressWarnings("unchecked")
            List<RequestPage> pageDetails = sqlQuery.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:requestid " + requestId);
            }
            return pageDetails;

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
}
