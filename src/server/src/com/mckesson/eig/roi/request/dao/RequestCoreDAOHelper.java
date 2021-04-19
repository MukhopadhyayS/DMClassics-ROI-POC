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

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.request.model.RequestCoreSearchCriteria;
import com.mckesson.eig.roi.requestor.model.RequestorCore;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.utils.StringValidation;

/**
 * @author OFS
 * @date Aug 23, 2012
 * @since Aug 23, 2012
 *
 */
public class RequestCoreDAOHelper
extends ROIDAOImpl {

    private static final OCLogger LOG = new OCLogger(RequestCoreDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    

  //This method updates the Requestor details
    public void updateRequestor(Session session, RequestorCore requestor, long requestId) {

        final String logSM = "updateRequestor(request)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Request: " + requestor);
        }

        try {

            Query query = session.getNamedQuery("updateRequestorCore");

            query.setParameter("requestId", requestId, LongType.INSTANCE);
            query.setParameter("requestorSeq", requestor.getRequestorSeq(), LongType.INSTANCE);
            query.setParameter("requestorType", requestor.getRequestorType(), LongType.INSTANCE);
            query.setParameter("requestorTypeName", requestor.getRequestorTypeName(),
                                                    StringType.INSTANCE);
            query.setParameter("workPhone", requestor.getWorkPhone(), StringType.INSTANCE);
            query.setParameter("homePhone", requestor.getHomePhone(), StringType.INSTANCE);
            query.setParameter("cellPhone", requestor.getCellPhone(), StringType.INSTANCE);
            query.setParameter("contactName", requestor.getContactName(), StringType.INSTANCE);
            query.setParameter("contactPhone", requestor.getContactPhone(), StringType.INSTANCE);
            query.setParameter("fax", requestor.getFax(), StringType.INSTANCE);
            query.setParameter("firstName", requestor.getFirstName(), StringType.INSTANCE);
            query.setParameter("middleName", requestor.getMiddleName(), StringType.INSTANCE);
            query.setParameter("lastName", requestor.getLastName(), StringType.INSTANCE);
            query.setParameter("suffix", null, StringType.INSTANCE);
            query.setParameter("modifiedDt", requestor.getModifiedDate(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedSeq", requestor.getModifiedBy(), IntegerType.INSTANCE);

            long noOfRowsUpdated = query.executeUpdate();

            if (noOfRowsUpdated <= 0) {
                throw new ROIException(ROIClientErrorCodes.UPDATE_REQUEST_CORE_OPERATION_FAILED);
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.UPDATE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    //This method deletes pages for the request
    public void deleteAllPagesForRequest(Session session, long requestId) {

        final String logSM = "deleteAllPagesForRequest(request)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:request: " + requestId);
        }

        try {

            String queryString = session.getNamedQuery("deleteAllPagesByRequestId").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);


            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Deleted AllPagesForRequest RequestId :" + requestId);
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

  //This method deletes version for the request
    public void deleteAllVersionsForRequest(Session session, long requestId) {

        final String logSM = "deleteAllVersionsForRequest(request)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:request: " + requestId);
        }

        try {

            String queryString = session.getNamedQuery("deleteAllVersionsByRequestId").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestId", requestId, LongType.INSTANCE);


            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Deleted AllVersionsForRequest RequestId :" + requestId);
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

  //This method deletes documents for the request
    public void deleteAllDocumentsForRequest(Session session, long requestId) {

        final String logSM = "deleteAllDocumentsForRequest(request)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:request: " + requestId);
        }

        try {

            String queryString = session.getNamedQuery("deleteAllDocumentsByRequestId").getQueryString();

            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);


            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Deleted AllDocumentsForRequest RequestId :" + requestId);
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

  //This method deletes encounters for the request
    public void deleteAllEncountersForRequest(Session session, long requestId) {

        final String logSM = "deleteAllEncountersForRequest(request)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:request: " + requestId);
        }

        try {
            String queryString = session.getNamedQuery("deleteAllEncountersByRequestId").getQueryString();

            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);


            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Deleted AllEncountersForRequest RequestId :" + requestId);
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    //This method deletes atttachments for the request
    public void deleteAllAttachmentsForRequest(Session session, long requestId) {

        final String logSM = "deleteAllAttachmentsForRequest(request)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:request: " + requestId);
        }

        try {
            // delete Supplemental Attachments
            String queryString = session.getNamedQuery("deleteAllSupplementalAttachmentsByRequestId").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            // delete Supplementarity Attachments
            queryString = session.getNamedQuery("deleteAllSupplementarityAttachmentsByRequestId").getQueryString();
            query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Deleted AllEncountersForRequest RequestId :" + requestId);
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    //This method deletes non hpf documents for the request
    public void deleteAllNonHpfDocumentsForRequest(Session session, long requestId) {

        final String logSM = "deleteAllNonHpfDocumentsForRequest(request)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:request: " + requestId);
        }

        try {
            // delete Supplemental NonHpf Documents
            String queryString = session.getNamedQuery("deleteAllSupplementalDocumentsByRequestId").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            
            query.setParameter("requestId", requestId, LongType.INSTANCE);


            // delete Supplementarity Non-Hpf Documents
            queryString = session.getNamedQuery("deleteAllSupplementarityDocumentsByRequestId").getQueryString();
            query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Deleted AllEncountersForRequest RequestId :" + requestId);
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

  //This method deletes patients for the request
    public void deleteAllPatientsForRequest(Session session, long requestId) {

        final String logSM = "deleteAllPatientsForRequest(request)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:request: " + requestId);
        }

        try {

            // deletes the hpf patients for the request
            String queryString = session.getNamedQuery("deleteAllPatientsByRequestId").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            // deletes the suppelmental patients for the request
            queryString = session.getNamedQuery("deleteAllSupplementalPatientsByRequestId").getQueryString();
            query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Deleted AllPatientsForRequest RequestId :" + requestId);
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

  //This method deletes patients for the request
    public void deleteAllRequestToPatients(Session session, long requestId) {

        final String logSM = "deleteAllRequestPatients(request)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:request: " + requestId);
        }

        try {

            // deletes the hpf patients request mapping table
            String queryString = session.getNamedQuery("deleteAllRequestToPatientsByRequestId").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            // deletes the supplemental patients request mapping table
            queryString = session.getNamedQuery("deleteAllRequestToSupplementalPatientsByRequestId").getQueryString();
            query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Deleted AllRequestPatients RequestId :" + requestId);
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

  //This method deletes requestor for the request
    public void deleteRequestorForRequest(Session session, long requestId) {

        final String logSM = "deleteRequestorForRequest(request)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:request: " + requestId);
        }

        try {

            String queryString = session.getNamedQuery("deleteRequestorByRequestId").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestId", requestId, LongType.INSTANCE);


            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Deleted RequestorForRequest RequestId :" + requestId);
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

  //This method deletes the given request Id
    public void deleteRequestCorForRequest(Session session, long requestId) {

        final String logSM = "deleteRequestCorForRequest(request)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:request: " + requestId);
        }

        try {

            String queryString = session.getNamedQuery("deleteRequestById").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestId", requestId, LongType.INSTANCE);


            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Deleted RequestCorForRequest RequestId :" + requestId);
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

  //This method deletes Events for the request
    public void deleteRequestEventForRequest(Session session, long requestId) {

        final String logSM = "deleteRequesteventForRequest(request)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:request: " + requestId);
        }

        try {

            String queryString = session.getNamedQuery("deleteRequestCoreEventById").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestId", requestId, LongType.INSTANCE);


            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Deleted RequestEventForRequest RequestId :" + requestId);
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    /**
     * @param searchCriteria
     * @param session
     * @return
     */
    public String constructSearchSQLQuery(RequestCoreSearchCriteria searchCriteria,
                                          Session session, HashMap<String, String> parameters) {
        // Query for hpf patients search
        String searchQuery = session.getNamedQuery("searchRequestCoreSeq").getQueryString();
        StringBuffer query = new StringBuffer(searchQuery);

        // Query for non hpf patients search
        String suppSearchQuery = session.getNamedQuery("searchRequestCoreSupplementalSeq")
                                        .getQueryString();
        StringBuffer suppQuery = new StringBuffer(suppSearchQuery);

        StringBuffer hpfWhereQuery = new StringBuffer();
        StringBuffer suppWhereQuery = new StringBuffer();

        constructQueryFromClause(searchCriteria, query, suppQuery);

        constructQueryWhereClause(searchCriteria, hpfWhereQuery, suppWhereQuery, parameters);

        StringBuffer finalQuery = new StringBuffer();
        if (searchCriteria.hasEncounterCriteria()) {

            StringBuffer hpfEncounterQuery = new StringBuffer(query);
            StringBuffer hpfAttachQuery = new StringBuffer(query);
            StringBuffer hpfDocumentQuery = new StringBuffer(query);
            StringBuffer suppAttachQuery = new StringBuffer(suppQuery);
            StringBuffer suppDocumentQuery = new StringBuffer(suppQuery);

            // creates query for the union operation for ROI_Encounters table
            hpfEncounterQuery.append(", \n\t cabinet..ROI_Encounters encounter")
                             .append(hpfWhereQuery);

            // creates query for the union operation for ROI_SupplementarityAttachmentsCore table
            hpfAttachQuery.append(", \n\t cabinet..ROI_SupplementarityAttachmentsCore encounter")
                          .append(hpfWhereQuery);
            // creates query for the union operation for ROI_SupplementarityDocumentsCore table
            hpfDocumentQuery.append(", \n\t cabinet..ROI_SupplementarityDocumentsCore encounter")
                            .append(hpfWhereQuery);

            // creates query for the union operation for ROI_SupplementalDocumentsCore table
            suppDocumentQuery.append(", \n\t cabinet..ROI_SupplementalDocumentsCore encounter")
                             .append(suppWhereQuery);

            // creates query for the union operation for ROI_SupplementalAttachmentsCore table
            suppAttachQuery.append(", \n\t cabinet..ROI_SupplementalAttachmentsCore encounter")
                           .append(suppWhereQuery);

            String tempQuery = createHpfEncounterBasedWhereClause(searchCriteria, parameters);

            appendSQLQueryAndClause(hpfEncounterQuery);
            hpfEncounterQuery.append(tempQuery);

            appendSQLQueryAndClause(hpfAttachQuery);
            hpfAttachQuery.append(tempQuery);

            appendSQLQueryAndClause(hpfDocumentQuery);
            hpfDocumentQuery.append(tempQuery);

            tempQuery = createSuppEncounterBasedWhereClause(searchCriteria, parameters);

            appendSQLQueryAndClause(suppDocumentQuery);
            suppDocumentQuery.append(tempQuery);

            appendSQLQueryAndClause(suppAttachQuery);
            suppAttachQuery.append(tempQuery);

            // to skip the hpf patient search if supplemental patient Id is given in serahcCriteria
            if (searchCriteria.getPatientId() <= 0) {

                finalQuery.append(hpfEncounterQuery);
                finalQuery.append("\n UNION \n\n\t").append(hpfAttachQuery);
                finalQuery.append("\n UNION \n\n\t").append(hpfDocumentQuery);
            }
            finalQuery.append("\n UNION \n\n\t").append(suppDocumentQuery);
            finalQuery.append("\n UNION \n\n\t").append(suppAttachQuery);

        } else if (searchCriteria.hasPatientsBasedCriteria()
                        || null != searchCriteria.getPatientDob()
                        || null != searchCriteria.getFacility()) {

            // to skip the hpf patient search if supplemental patient Id is given in serahcCriteria
            if (searchCriteria.getPatientId() <= 0) {

                finalQuery.append(query).append(hpfWhereQuery);
                finalQuery.append("\n UNION \n\n\t");
            }
            finalQuery.append(suppQuery).append(suppWhereQuery);
        } else {
            finalQuery.append(query).append(hpfWhereQuery);
        }


        return finalQuery.toString();
    }

    /**
     * @param searchCriteria
     * @param session
     * @param finalQuery
     * @return
     */
    public String constructRetrieveRequestDataQuery(RequestCoreSearchCriteria searchCriteria,
                                                     Session session,
                                                     String finalQuery) {
        String searchRequestQuery;

        if (searchCriteria.getPaginationData() == null) {
            searchRequestQuery = session.getNamedQuery("searchRequestCore").getQueryString();
        } else {
            searchRequestQuery = session.getNamedQuery("searchRequestCoreWithEncounters").getQueryString();
        }

        StringBuffer searchRequestQueryBuffer = new StringBuffer(searchRequestQuery);
        searchRequestQueryBuffer.append("\n\n\t INNER JOIN")
                            .append("\n\n\t (")
                            .append(finalQuery)
                            .append(" ) as search")
                            .append("\n\t ON search.requestId = requestCore.ROI_RequestCore_Seq");

        return searchRequestQueryBuffer.toString();
    }

    /**
     * Constructs the SQL query where clause based on the search criteria
     *
     * @param searchCriteria
     * @param query
     * @param suppQuery
     */
    public void constructQueryWhereClause(RequestCoreSearchCriteria searchCriteria,
                                   StringBuffer query,
                                   StringBuffer suppQuery,
                                   HashMap<String, String> parameters) {

        query.append("\n WHERE ");
        suppQuery.append("\n WHERE ");

        String tempQuery = "";
        // constructs the request based Query
        if (searchCriteria.hasRequestBasedCriteria()) {
            tempQuery = createRequestBasedWhereClause(searchCriteria, parameters);
            query.append(tempQuery);
            suppQuery.append(tempQuery);
        }

        // constructs the patient based query
        if (searchCriteria.hasPatientsBasedCriteria()
                || searchCriteria.hasEncounterCriteria()
                || null != searchCriteria.getPatientDob()
                || null != searchCriteria.getFacility()) {

            tempQuery = createPatientBasedWhereClause(searchCriteria, parameters);

            appendSQLQueryAndClause(query);
            // replace the query with name for HPF patients since name column will be populated with data
            // in ROI_Patients table
            String tempHpfQuery = replaceName(tempQuery);

            query.append("\n\t coreToPatient.ROI_RequestCore_Seq = request.ROI_RequestCore_Seq")
                 .append("\n\t AND patients.ROI_Patients_Seq = coreToPatient.ROI_Patients_Seq ")
                 .append(tempHpfQuery);

            appendSQLQueryAndClause(suppQuery);
            suppQuery.append(" coreToPatient.ROI_RequestCore_Seq = ")
                     .append("request.ROI_RequestCore_Seq")
                     .append("\n\t AND patients.ROI_SupplementalPatientsCore_Seq = ")
                     .append("coreToPatient.ROI_SupplementalPatientsCore_Seq ")
                     .append(tempQuery);

        }

        // constructs the requestor based search criteria
        if (searchCriteria.hasRequestorBasedcriteria()) {

            tempQuery = createRequestorBasedWhereClause(searchCriteria, parameters);

            appendSQLQueryAndClause(query);
            query.append("\n requestor.ROI_RequestCore_Seq = request.ROI_RequestCore_Seq");
            query.append(tempQuery);

            appendSQLQueryAndClause(suppQuery);
            suppQuery.append("\n requestor.ROI_RequestCore_Seq = request.ROI_RequestCore_Seq");
            suppQuery.append(tempQuery);
        }

        // constructs the invoice based search query
        if (searchCriteria.hasInvoiceBasedCriteria()) {

            tempQuery = createInvoiceBasedWhereClause(searchCriteria, parameters);

            appendSQLQueryAndClause(query);
            query.append(tempQuery);

            appendSQLQueryAndClause(suppQuery);
            suppQuery.append(tempQuery);
        }

    }

    /**
     * This method is used to replace LastName and FirstName Column with Name in the query for ROI_Patients table
     * @param tempQuery
     * @return String
     */
    private String replaceName(String tempQuery) {
        String replaceNameQuery = "";
        if(tempQuery.contains("patients.LastName") && tempQuery.contains("patients.FirstName"))
           replaceNameQuery = tempQuery.replaceAll("patients.LastName","patients.Name").replaceAll("patients.FirstName","patients.Name");
        else if(tempQuery.contains("patients.LastName"))
                replaceNameQuery = tempQuery.replaceAll("patients.LastName","patients.Name");
        else
            replaceNameQuery = tempQuery.replaceAll("patients.FirstName","patients.Name");
        return replaceNameQuery;
    }

    /**
     * Construct the where clause for the invoice table
     * @param searchCriteria
     * @return
     */
    public String createInvoiceBasedWhereClause(RequestCoreSearchCriteria searchCriteria, HashMap<String, String> parameters) {

        StringBuffer query = new StringBuffer();
        if (searchCriteria.getInvoiceNumber() > 0) {
            
            appendSQLQueryAndClause(query);

            String key = putParameters(parameters, String.valueOf(searchCriteria.getInvoiceNumber()), "InvoiceNumber");
            query.append(" invoice.ROI_RequestCore_Seq = request.ROI_RequestCore_Seq")
                 .append("\n\t AND invoice.ROI_RequestCoreDeliveryCharges_Seq in (:")
                 .append(key)
                 .append(",")
                 .append("(SELECT ROI_RequestCoreDeliveryCharges_Seq")
                 .append("\n\t FROM ROI_RequestCoreDeliveryChargesMigrated")
                 .append("\n\t WHERE ROI_RequestDeliveryCharges_Seq = :")
                 .append(key)
                 .append("))");
        }

        if (searchCriteria.getBalanceDueOperator() != null) {

            appendSQLQueryAndClause(query);
            
            //check for input
            String balanceDueOperator = searchCriteria.getBalanceDueOperator();
            boolean passedTest  = StringValidation.validateBalanceDuePerator(balanceDueOperator);
            if(!passedTest){
                throw new ROIException(ROIClientErrorCodes.INVALID_INPUT, 
                        "The input for BalanceDueOperator is not valid: " + balanceDueOperator +
                        "  - should be '+' or '-' only.");
            }
            
            double balanceDue = searchCriteria.getBalanceDue();
            
            query.append(" ((COALESCE((SELECT SUM(Invoice_Balance_Due) ")
                 .append("FROM ROI_RequestCoreDeliveryCharges ")
                 .append("WHERE ROI_RequestCore_Seq = request.ROI_RequestCore_Seq), 0)) ")
                 .append("+")
                 .append(" (COALESCE((SELECT SUM(Release_Cost) ")
                 .append("FROM ROI_RequestCoreCharges ")
                 .append("WHERE ROI_RequestCore_Seq = request.ROI_RequestCore_Seq), 0))) ")
                 .append(balanceDueOperator)
                 .append(" ")
                 .append(balanceDue);
        }

        return query.toString();
    }

    public String createHpfEncounterBasedWhereClause(RequestCoreSearchCriteria searchCriteria, HashMap<String, String> parameters) {

        StringBuffer query = new StringBuffer();
        String key = putParameters(parameters, searchCriteria.getEncounter() +"%", "Encounter");
        
        query.append("\n\t encounter.ROI_Patients_Seq = ")
             .append("patients.ROI_Patients_Seq")
             .append("\n\t AND encounter.ENCOUNTER like ")
             .append(":").append(key);

        return query.toString();
    }

    public String createSuppEncounterBasedWhereClause(RequestCoreSearchCriteria searchCriteria, HashMap<String, String> parameters) {

        StringBuffer query = new StringBuffer();
        String key = putParameters(parameters, searchCriteria.getEncounter() +"%", "Encounter");
        query.append("\n\t encounter.ROI_SupplementalPatientsCore_Seq = ")
             .append("patients.ROI_SupplementalPatientsCore_Seq")
             .append("\n\t AND encounter.ENCOUNTER like ")
             .append(":").append(key);

        return query.toString();
    }

    public String createRequestorBasedWhereClause(RequestCoreSearchCriteria searchCriteria, HashMap<String, String> parameters)  {

        StringBuffer query = new StringBuffer();
        if (null != searchCriteria.getRequestorName()) {
            
            String key = putParameters(parameters, getSpecialCharSearchStr(searchCriteria.getRequestorName())+"%", "RequestorName");
            query.append(" AND requestor.NameLast like (")
                 .append(":")
                 .append(key)
                 .append(") ESCAPE('+')");

        }

        if (0 != searchCriteria.getRequestorType()) {
            
            String key = putParameters(parameters, String.valueOf(searchCriteria.getRequestorType()), "RequestorType");
            query.append(" AND requestor.RequestorType = ");
            query.append(":").append(key);
        }

        if (0 < searchCriteria.getRequestorId()) {
            String key = putParameters(parameters, String.valueOf(searchCriteria.getRequestorId()), "RequestorId");
            query.append(" AND requestor.ROI_Requestor_Seq = ")
            .append(":").append(key);
        }

        return query.toString();
    }

    public String createPatientBasedWhereClause(RequestCoreSearchCriteria searchCriteria, HashMap<String, String> parameters) {

        StringBuffer query = new StringBuffer();
        boolean isPatientRequestHistory = false;

        // CR# 371,269 code merge - When performing request history in ROI,
        //  multiple MRN results can be displayed
        if (null != searchCriteria.getPaginationData()) {
            isPatientRequestHistory = true;
        }

        if (0 != searchCriteria.getPatientId()) {

            query.append("\n\t AND patients.ROI_SupplementalPatients_Seq = ");
            String key = putParameters(parameters, String.valueOf(searchCriteria.getPatientId()), "PatientId");
            query.append(":" + key);
         }

        if (null != searchCriteria.getMrn()) {

            query.append("\n\t AND patients.MRN ")
                 .append(getQueryConditionValue(isPatientRequestHistory,
                                                searchCriteria.getMrn(),
                                                false,
                                                true,
                                                "MRN",
                                                parameters));
        }

        if (null != searchCriteria.getPatientSsn()) {

            query.append("\n\t AND patients.SSN ")
                 .append(getQueryConditionValue(isPatientRequestHistory,
                                                searchCriteria.getPatientSsn(),
                                                false,
                                                true, 
                                                "SSN",
                                                parameters));
        }

        if (null != searchCriteria.getPatientEpn()) {

            query.append("\n\t AND patients.EPN ")
                 .append(getQueryConditionValue(isPatientRequestHistory,
                                                searchCriteria.getPatientEpn(),
                                                false,
                                                true,
                                                "EPN",
                                                parameters));
        }

        if (null != searchCriteria.getPatientFirstName()) {

            String key = putParameters(parameters, "%"+ searchCriteria.getPatientFirstName()+ "%", "PatientFirstName");
            query.append("\n\t AND patients.FirstName LIKE :" + key);

        }

        if (null != searchCriteria.getPatientLastName()) {

            query.append("\n\t AND patients.LastName ")
                 .append(getQueryConditionValue(isPatientRequestHistory,
                                                searchCriteria.getPatientLastName(),
                                                false,
                                                true,
                                                "LastName",
                                                parameters));

        }

        SimpleDateFormat format = new SimpleDateFormat(ROIConstants.INVOICE_DATE_FORMAT);
        if (null != searchCriteria.getPatientDob()) {

            query.append("\n\t AND CONVERT(VARCHAR(10), patients.DOB, 101) = '")
            .append(format.format(searchCriteria.getPatientDob()))
            .append("'");
        }

        if (null != searchCriteria.getFacility()) {

            query.append("\n\t AND patients.FACILITY ")
                 .append(getQueryConditionValue(isPatientRequestHistory,
                                                searchCriteria.getFacility(),
                                                false,
                                                true,
                                                "Facility",
                                                parameters));
        }


        return query.toString();
    }
    
    private String putParameters(HashMap<String, String> map, String value, String key) {
        String updatedKey  = key + (UUID.randomUUID().toString().replace("-", "_"));
        map.put(updatedKey, value);
        return updatedKey;
        
    }

    /**
     * CR# 371,269 code merge - When performing request history in ROI,
     *  multiple MRN results can be displayed
     *
     *  if the Request Search is from the Patient module, the like condition should be replaced
     *  with the equal (=) condition
     *
     * @param isPatientRequestHistory
     * @param value
     * @param queryLikeAtStart
     * @param queryLikeAtEnd
     * @return constructed String
     */

    private String getQueryConditionValue(boolean isPatientRequestHistory,
                                          String value,
                                          boolean queryLikeAtStart,
                                          boolean queryLikeAtEnd,
                                          String keyPrefix,
                                          HashMap<String, String> parameters) {

        if (isPatientRequestHistory) {
            String key  = putParameters(parameters, value, keyPrefix);    
            return " = :" + key;
        }
        
        StringBuffer sb =  new StringBuffer(value);
        
        if(queryLikeAtStart){
           sb.insert(0, "%");
        }

        if(queryLikeAtEnd) {
            sb.append("%");
        }
        
        String key  = putParameters(parameters, sb.toString(), keyPrefix);    
        
        return  new StringBuffer()
                .append(" LIKE :")
                .append(key)
                .toString();

    }

    public String createRequestBasedWhereClause(RequestCoreSearchCriteria searchCriteria, HashMap<String, String> parameters) {

        StringBuffer query = new StringBuffer();
        if (searchCriteria.getRequestId() > 0) {

            appendSQLQueryAndClause(query);
            query.append(" request.ROI_RequestCore_Seq = ");
            String key  = putParameters(parameters, String.valueOf(searchCriteria.getRequestId()), "RequestId");
            query.append(":" + key);
            
        }

        if (null != searchCriteria.getRequestStatus()) {

            appendSQLQueryAndClause(query);
            query.append(" request.RequestStatus = ");
            String key  = putParameters(parameters, searchCriteria.getRequestStatus(), "RequestStatus");
            query.append(":" + key);
            
        }

        if (null != searchCriteria.getRequestReason()) {

            appendSQLQueryAndClause(query);
            query.append(" request.ROI_Reason = ");
            String key  = putParameters(parameters, searchCriteria.getRequestReason(), "RequestReason");
            query.append(":" + key);
        }

        SimpleDateFormat format = new SimpleDateFormat(ROIConstants.INVOICE_DATE_FORMAT);
        if (null != searchCriteria.getCompletedDateFrom()) {

            appendSQLQueryAndClause(query);
            query.append(" request.CompletedDate BETWEEN '")
                 .append(format.format(searchCriteria.getCompletedDateFrom()))
                 .append(" 00:00:00'  AND '" + format.format(searchCriteria.getCompletedDateTo()))
                 .append(" 23:59:59'");
        }

        if (null != searchCriteria.getReceiptDateFrom()) {

            appendSQLQueryAndClause(query);
            query.append(" request.ReceiptDate BETWEEN '")
                 .append(format.format(searchCriteria.getReceiptDateFrom()))
                 .append(" 00:00:00'  AND '" + format.format(searchCriteria.getReceiptDateTo()))
                 .append(" 23:59:59'");
        }

        return query.toString();
    }

    /**
     * Appends the and clause to the query if the
     * @param query
     */
    private void appendSQLQueryAndClause(StringBuffer query) {


        if (null == query) {
            return;
        }

        String queryString = query.toString();
        if (queryString.trim().isEmpty()) {
            return;
        }

        if (!queryString.trim().endsWith("WHERE")) {
            query.append("\n\t AND ");
        }
    }

    public void constructQueryFromClause(RequestCoreSearchCriteria searchCriteria,
                                          StringBuffer query,
                                          StringBuffer suppQuery) {

        if (searchCriteria.hasRequestorBasedcriteria()) {

            query.append(", \n\t cabinet..ROI_RequestCoreRequestor requestor");
            suppQuery.append(", \n\t cabinet..ROI_RequestCoreRequestor requestor");
        }

        if (searchCriteria.hasPatientsBasedCriteria()
                || searchCriteria.hasEncounterCriteria()
                || null != searchCriteria.getPatientDob()
                || null != searchCriteria.getFacility()) {

            query.append(", \n\t cabinet..ROI_RequestCoretoROI_Patients coreToPatient");
            query.append(", \n\t cabinet..ROI_Patients patients");
            suppQuery.append(", \n\t cabinet..ROI_RequestCoretoROI_SupplementalPatientsCore ")
                     .append("coreToPatient")
                     .append(", \n\t cabinet..ROI_SupplementalPatientsCore patients");
        }

        if (searchCriteria.hasInvoiceBasedCriteria()) {

            query.append(", \n\t cabinet..ROI_RequestCoreDeliveryCharges invoice");
            suppQuery.append(", \n\t cabinet..ROI_RequestCoreDeliveryCharges invoice");
        }
    }

    public void deleteAllInvoicesForRequest(Session session, long requestId) {

        final String logSM = "deleteAllInvoicesForRequest(requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:requestId: " + requestId);
        }

        try {

            String queryString = session.getNamedQuery("deleteAllShippingByRequestId").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            queryString = session.getNamedQuery("deleteAllDocumentByRequestId").getQueryString();
            query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            queryString = session.getNamedQuery("deleteAllFeeByRequestId").getQueryString();
            query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            queryString = session.getNamedQuery("deleteAllInvoicePatientsByRequestId").getQueryString();
            query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            queryString = session.getNamedQuery("deleteAllInvoicesByRequestId").getQueryString();
            query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Deleted AllInvoicesForRequest RequestId :" + requestId);
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    public void deleteAllBillingPaymentInfoForRequest(Session session, long requestId) {

        final String logSM = "deleteAllBillingPaymentInfoForRequest(requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:requestId: " + requestId);
        }

        try {

            String queryString = session.getNamedQuery("deleteAllBillingPaymentShippingInfoByRequest").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            queryString = session.getNamedQuery("deleteAllBillingPaymentFeeChargesInfoByRequest").getQueryString();
            query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            queryString = session.getNamedQuery("deleteAllBillingPaymentDocChargesInfoByRequest").getQueryString();
            query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            queryString = session.getNamedQuery("deleteAllBillingPaymentsalesTaxInfoByRequest").getQueryString();
            query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            queryString = session.getNamedQuery("deleteRequestCoreCharges").getQueryString();
            query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreSeq", requestId, LongType.INSTANCE);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Deleted BillingPaymentInfoForRequest RequestId :"
                                + requestId);
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    public void deleteAllLetterForRequest(Session session, long requestId) {

        final String logSM = "deleteAllLetterForRequest(requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:requestId: " + requestId);
        }

        try {

            String queryString = session.getNamedQuery("deleteAllCoverLetterRequestHpfPatientByRequestId").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            queryString = session.getNamedQuery("deleteAllCoverLetterRequestSupplementalPatientByRequestId").getQueryString();
            query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            queryString = session.getNamedQuery("deleteAllCoverLetterByRequestId").getQueryString();
            query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Deleted AllLetterForRequest RequestId :"
                        + requestId);
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

}
