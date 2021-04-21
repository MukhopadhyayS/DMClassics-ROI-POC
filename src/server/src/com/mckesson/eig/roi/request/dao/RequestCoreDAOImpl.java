/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright � 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.request.model.PaginationData;
import com.mckesson.eig.roi.request.model.ProductivityReportDetails;
import com.mckesson.eig.roi.request.model.RequestCore;
import com.mckesson.eig.roi.request.model.RequestCoreChargesDocument;
import com.mckesson.eig.roi.request.model.RequestCoreSearchCriteria;
import com.mckesson.eig.roi.request.model.RequestCoreSearchResult;
import com.mckesson.eig.roi.request.model.RequestEvent;
import com.mckesson.eig.roi.request.model.RequestEvent.TYPE;
import com.mckesson.eig.roi.request.model.RequestEventCriteria;
import com.mckesson.eig.roi.requestor.model.RequestorCore;
import com.mckesson.eig.roi.requestor.model.RequestorInvoice;
import com.mckesson.eig.roi.requestor.model.RequestorInvoicesList;
import com.mckesson.eig.roi.utils.SqlEncoderAdvanced;
import com.mckesson.eig.utility.util.CollectionUtilities;


/**
 * @author OFS
 * @date Jun 29, 2012
 * @since Jun 29, 2012
 *
 */
public class RequestCoreDAOImpl
extends ROIDAOImpl
implements RequestCoreDAO {

    private static final OCLogger LOG = new OCLogger(RequestCoreDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static RequestCoreDAOHelper _helper = new RequestCoreDAOHelper();
    private static final SqlEncoderAdvanced ENCODER_ADVANCED = new SqlEncoderAdvanced();

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestCoreDAO
     * #searchRequest(com.mckesson.eig.roi.request.model.RequestCoreSearchCriteria)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<RequestCoreSearchResult> searchRequest(RequestCoreSearchCriteria searchCriteria) {

        final String logSM = "searchRequest(RequestCoreSearchCriteria searchCriteria)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Request searchCriteria:" + searchCriteria);
        }

        try {

            PaginationData paginationData = searchCriteria.getPaginationData();
            boolean hasPagination = (paginationData != null);
            HashMap<String, String> parameters =  new HashMap<String, String>();
            Session session = getSession();
            String finalQuery =  _helper.constructSearchSQLQuery(searchCriteria, session, parameters);
            finalQuery = _helper.constructRetrieveRequestDataQuery(searchCriteria, session, finalQuery);
            NativeQuery sqlQuery = session.createSQLQuery(finalQuery);
            sqlQuery.addScalar("requestId", LongType.INSTANCE);
            sqlQuery.addScalar("receiptDate", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("requestStatus", StringType.INSTANCE);
            sqlQuery.addScalar("subtitle", StringType.INSTANCE);
            sqlQuery.addScalar("lastUpdated", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("updatedBy", StringType.INSTANCE);
            sqlQuery.addScalar("requestorName", StringType.INSTANCE);
            sqlQuery.addScalar("requestorTypeName", StringType.INSTANCE);
            sqlQuery.addScalar("requestorType", LongType.INSTANCE);
            sqlQuery.addScalar("patientsString", StringType.INSTANCE);
            sqlQuery.addScalar("facility", StringType.INSTANCE);
            sqlQuery.addScalar("encounterString", StringType.INSTANCE);
            sqlQuery.addScalar("patientLocked", BooleanType.INSTANCE);
            sqlQuery.addScalar("vip", BooleanType.INSTANCE);
            sqlQuery.addScalar("balance", DoubleType.INSTANCE);

            Set<String> keys = parameters.keySet();
            for(String key: keys){
                String value = parameters.get(key);
                String encodedValue = ENCODER_ADVANCED.encodeForSql(value);
                sqlQuery.setParameter(key, encodedValue);
            }

            sqlQuery.setResultTransformer(Transformers.aliasToBean(RequestCoreSearchResult.class));

            if (hasPagination) {

                sqlQuery.setFirstResult(paginationData.getStartIndex());
                sqlQuery.setMaxResults(paginationData.getCount());
            } else {
                sqlQuery.setMaxResults(((int) searchCriteria.getMaxCount()) + 1);
            }

            List<RequestCoreSearchResult> list = sqlQuery.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

            return list;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.RETRIEVE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public long getRequestCount(RequestCoreSearchCriteria searchCriteria) {

        final String logSM = "getRequestCount(RequestCoreSearchCriteria searchCriteria)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Request searchCriteria:" + searchCriteria);
        }

        try {
            Session session = getSession();
            HashMap<String, String> parameters =  new HashMap<String, String>();
            String finalQuery = _helper.constructSearchSQLQuery(searchCriteria, session, parameters);
            NativeQuery sqlQuery = session.createSQLQuery(finalQuery);
            sqlQuery.addScalar("requestId", LongType.INSTANCE);
            Set<String> keys = parameters.keySet();
            for(String key: keys){
                String value = parameters.get(key);
                String encodedValue = ENCODER_ADVANCED.encodeForSql(value);
                sqlQuery.setParameter(key, encodedValue);
            }

            List<Long> list = sqlQuery.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

            return list.size();

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.RETRIEVE_OPERATION_FAILED,
                    e.getMessage());
        }
    }
    /**
     * @see com.mckesson.eig.roi.request.dao.RequestCoreDAO
     * #createRequest(com.mckesson.eig.roi.request.model.RequestInfo)
     */
    @Override
    public RequestCore createRequest(RequestCore request) {

        final String logSM = "createRequest(request)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Request :" + request);
        }

        try {

            Session session = getSession();
            Query query = session.getNamedQuery("createRequestCore");

            query.setProperties(request);

            BigDecimal requestCoreId = (BigDecimal) query.uniqueResult();

            if (null == requestCoreId || requestCoreId.longValue() <= 0) {
                throw new ROIException(ROIClientErrorCodes.CREATE_REQUEST_CORE_OPERATION_FAILED,
                                       "Create Request Operation failed");
            }

            long requestId =  requestCoreId.longValue();
            //sets the requestId
            request.setId(requestId);

            RequestorCore requestor = createRequestorCore(request.getRequestorDetail(), requestId);

            if (null == requestor) {
                throw new ROIException(ROIClientErrorCodes.INVALID_ID,
                                       "Requestor does not exists for Id:" + requestId);
            }

            //sets Requestor details
            request.setRequestorDetail(requestor);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:RequestId " + requestId);
            }

            return request;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.INSERT_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    public RequestorCore createRequestorCore(RequestorCore requestor, long requestId) {

        final String logSM = "createRequestorCore(request)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Request :" + requestor);
        }

        try {

            Session session = getSession();
            Query query = session.getNamedQuery("createRequestorCore");

            requestor.setRequestId(requestId);
            query.setProperties(requestor);

            BigDecimal requestorId = (BigDecimal) query.uniqueResult();

            if (null == requestorId || requestorId.longValue() <= 0) {
               throw new ROIException(ROIClientErrorCodes.CREATE_REQUEST_REQUESTOR_OPERATION_FAILED,
                                       "Create Request Operation failed");
            }

            long id =  requestorId.longValue();
            requestor.setId(id);

            return requestor;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.INSERT_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestDAO#retrieveRequest(long)
     */
    @Override
    public RequestCore retrieveRequest(long requestId) {

        final String logSM = "retrieveRequest(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestId " + requestId);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveCreatedRequest").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.addScalar("id", LongType.INSTANCE);
            query.addScalar("status", StringType.INSTANCE);
            query.addScalar("receiptDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("statusReason", StringType.INSTANCE);
            query.addScalar("requestReason", StringType.INSTANCE);
            query.addScalar("requestReasonAttribute", StringType.INSTANCE);
            query.addScalar("statusChangedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("requestPassword", StringType.INSTANCE);

            query.addScalar("releaseCount", LongType.INSTANCE);
            query.addScalar("hasDraftRelease", BooleanType.INSTANCE);
            query.addScalar("balanceDue", DoubleType.INSTANCE);

            query.addScalar("authDoc", StringType.INSTANCE);
            query.addScalar("authDocName", StringType.INSTANCE);
            query.addScalar("authDocSubtitle", StringType.INSTANCE);
            query.addScalar("authDocDateTime", StandardBasicTypes.TIMESTAMP);
            query.addScalar("conversionSource", StringType.INSTANCE);
            query.addScalar("unbillable", BooleanType.INSTANCE);

            query.addScalar("createdDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", IntegerType.INSTANCE);
            query.addScalar("modifiedDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", IntegerType.INSTANCE);
            query.addScalar("modifiedByUser", StringType.INSTANCE);

            query.setParameter("requestId", requestId, LongType.INSTANCE);
            query.setResultTransformer(Transformers.aliasToBean(RequestCore.class));

            RequestCore requestDetails = (RequestCore) query.uniqueResult();

            if (null == requestDetails) {
                throw new ROIException(ROIClientErrorCodes.INVALID_ID,
                                       "Request Id:" + requestId + " does not exists");
            }

            requestDetails.setId(requestId);
            requestDetails.setRequestorDetail(retrieveRequestor(requestId));

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:RequestId " + requestDetails);
            }

            return requestDetails;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                      e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.RETRIEVE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestCoreDAO#deleteRequest(long)
     */
    @Override
    public void deleteRequest(long requestId) {

        final String logSM = "deleteRequest(request)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:request: " + requestId);
        }

        try {

          //This method deletes pages for the request
           _helper.deleteAllPagesForRequest(getSession(), requestId);
          //This method deletes version for the request
           _helper.deleteAllVersionsForRequest(getSession(), requestId);
          //This method deletes documents for the request
           _helper.deleteAllDocumentsForRequest(getSession(), requestId);
          //This method deletes encounters for the request
           _helper.deleteAllEncountersForRequest(getSession(), requestId);
           //This method deletes all non hpfdocuments for the request
           _helper.deleteAllNonHpfDocumentsForRequest(getSession(), requestId);
           //This method deletes all non hpfdocuments for the request
           _helper.deleteAllAttachmentsForRequest(getSession(), requestId);
           //This method deletes patients for the request
           _helper.deleteAllRequestToPatients(getSession(), requestId);
        // deletes all the letter information for the request
           _helper.deleteAllLetterForRequest(getSession(), requestId);
          //This method deletes patients for the request
           _helper.deleteAllPatientsForRequest(getSession(), requestId);
          //This method deletes RequestCore to Patient for the request
           _helper.deleteRequestorForRequest(getSession(), requestId);
          //This method deletes event for the request
           _helper.deleteRequestEventForRequest(getSession(), requestId);
           // deletes all the billing payment information for the request
           _helper.deleteAllBillingPaymentInfoForRequest(getSession(), requestId);
           // deletes all invoices corresponding for the given request
           _helper.deleteAllInvoicesForRequest(getSession(), requestId);
          //This method deletes the given requestId from request core
           _helper.deleteRequestCorForRequest(getSession(), requestId);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Successfully Deleted RequestId :" + requestId);
            }

        } catch (Exception e) {
           throw new ROIException(e, ROIClientErrorCodes.DELETE_OPERATION_FAILED,
                                  "Failed to delete the Request");
        }

    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestDAO
     * #updateRequest(com.mckesson.eig.roi.request.model.Request)
     */
    @Override
    public RequestCore updateRequest(RequestCore request) {

        final String logSM = "updateRequest(request)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Request: " + request);
        }

        try {

            RequestorCore requestor = request.getRequestorDetail();

            Session session = getSession();
            String queryString = session.getNamedQuery("updateRequestCore").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            
            query.setParameter("requestId", request.getId(), LongType.INSTANCE);

            query.setParameter("requestStatus", request.getStatus(), StringType.INSTANCE);
            query.setParameter("receiptDate", request.getReceiptDate(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("statusReason", request.getStatusReason(), StringType.INSTANCE);
            query.setParameter("requestReason", request.getRequestReason(), StringType.INSTANCE);
            query.setParameter("requestReasonAttribute", request.getRequestReasonAttribute());
            query.setParameter("statusChangedDt", request.getStatusChangedDt(),
                                                                              StandardBasicTypes.TIMESTAMP);
            query.setParameter("requestPassword", request.getRequestPassword(), StringType.INSTANCE);

            query.setParameter("modifiedDate", request.getModifiedDate(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", request.getModifiedBy(), IntegerType.INSTANCE);

            //update completed date if exists
            if (request.getCompletedDate() != null) {
	            queryString = session.getNamedQuery("updateRequestCoreCompletedDate").getQueryString();
	            query = session.createSQLQuery(queryString);
	            query.setParameter("requestId", request.getId(), LongType.INSTANCE);
	            query.setParameter("completedDate", request.getCompletedDate(), StandardBasicTypes.TIMESTAMP);
	            query.setParameter("modifiedDate", request.getModifiedDate(), StandardBasicTypes.TIMESTAMP);
	            query.setParameter("modifiedBy", request.getModifiedBy(), IntegerType.INSTANCE);
	            
            }


            if (requestor.getId() <= 0) {
                createRequestorCore(requestor, request.getId());
            } else {
                _helper.updateRequestor(getSession(), requestor, request.getId());
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:UpdatedRequest: " + request.getId());
            }

            return retrieveRequest(request.getId());

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

    //This method retrieves the Requestor details
    @Override
    public RequestorCore retrieveRequestor(long requestId) {

        final String logSM = "retrieveRequestor(request)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Request: " + requestId);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveCoreRequestor").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.addScalar("id", LongType.INSTANCE);
            query.addScalar("requestorSeq", LongType.INSTANCE);
            query.addScalar("requestorType", LongType.INSTANCE);
            query.addScalar("requestorTypeName", StringType.INSTANCE);
            query.addScalar("workPhone", StringType.INSTANCE);
            query.addScalar("HomePhone", StringType.INSTANCE);
            query.addScalar("cellPhone", StringType.INSTANCE);
            query.addScalar("contactPhone", StringType.INSTANCE);
            query.addScalar("contactName", StringType.INSTANCE);
            query.addScalar("fax", StringType.INSTANCE);
            query.addScalar("firstName", StringType.INSTANCE);
            query.addScalar("middleName", StringType.INSTANCE);
            query.addScalar("lastName", StringType.INSTANCE);
            query.addScalar("suffix", StringType.INSTANCE);
            query.addScalar("createdDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", IntegerType.INSTANCE);
            query.addScalar("modifiedDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", IntegerType.INSTANCE);
            query.addScalar("mainAddress1", StringType.INSTANCE);
            query.addScalar("mainAddress2", StringType.INSTANCE);
            query.addScalar("mainAddress3", StringType.INSTANCE);
            query.addScalar("mainCity", StringType.INSTANCE);
            query.addScalar("mainPostalCode", StringType.INSTANCE);
            query.addScalar("mainState", StringType.INSTANCE);
            query.addScalar("mainCountryName", StringType.INSTANCE);
            query.addScalar("mainCountryCode", StringType.INSTANCE);

            query.setParameter("requestId", requestId, LongType.INSTANCE);
            query.setResultTransformer(Transformers.aliasToBean(RequestorCore.class));

            RequestorCore requestorDetails = (RequestorCore) query.uniqueResult();
            return requestorDetails;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.RETRIEVE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestDAO
     * #createRequestEvent(com.mckesson.eig.roi.request.model.RequestEvent)
     */
    @Override
    public void createRequestEvent(RequestEvent event) {

        final String logSM = "createRequestEvent(event)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestEvent :" + event);
        }

        try {

            Session session = getSession();
            
            String queryString = session.getNamedQuery("createRequestEvent")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("createdDate", getDate(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("CreatedBy", event.getCreatedBy(), IntegerType.INSTANCE);
            query.setParameter("modifiedDate", event.getModifiedDate(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", event.getModifiedBy(), IntegerType.INSTANCE);
            query.setParameter("recordVersion", event.getRecordVersion(), IntegerType.INSTANCE);
            query.setParameter("requestCoreSeq", event.getRequestId(), LongType.INSTANCE);
            query.setParameter("name", event.getName(), StringType.INSTANCE);
            query.setParameter("description", event.getDescription(), StringType.INSTANCE);           

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:RequestEventId :" + event);
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.INSERT_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    /**
     * deletes the request event based on the requestId and the eventType
     * @param requestId
     * @param eventType
     */
    @Override
    public void deleteLatestRequestEvent(long requestId, TYPE eventType) {

        final String logSM = "deleteRequestEvent(requestId, eventType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:REquestId :" + requestId + ", EventType" + eventType);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("deleteLatestRequestEventByRequestIdAndEvent").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);
            query.setParameter("eventType", eventType.toString(), StringType.INSTANCE);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.INSERT_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    //This method retrieves the Requestor details
    @Override
    @SuppressWarnings("unchecked")
    public List<RequestorCore> retrieveAllRequestorByInvoiceIds(List<Long> invoiceIds) {

        final String logSM = "retrieveAllRequestorByInvoiceIds(invoiceIds)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Request: " + invoiceIds);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveCoreRequestorByInvoiceIds").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.addScalar("id", LongType.INSTANCE);
            query.addScalar("requestId", LongType.INSTANCE);
            query.addScalar("requestorSeq", LongType.INSTANCE);
            query.addScalar("requestorType", LongType.INSTANCE);
            query.addScalar("requestorTypeName", StringType.INSTANCE);
            query.addScalar("workPhone", StringType.INSTANCE);
            query.addScalar("HomePhone", StringType.INSTANCE);
            query.addScalar("cellPhone", StringType.INSTANCE);
            query.addScalar("contactPhone", StringType.INSTANCE);
            query.addScalar("contactName", StringType.INSTANCE);
            query.addScalar("fax", StringType.INSTANCE);
            query.addScalar("firstName", StringType.INSTANCE);
            query.addScalar("middleName", StringType.INSTANCE);
            query.addScalar("lastName", StringType.INSTANCE);
            query.addScalar("suffix", StringType.INSTANCE);
            query.addScalar("createdDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", IntegerType.INSTANCE);
            query.addScalar("modifiedDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", IntegerType.INSTANCE);
            query.addScalar("mainAddress1", StringType.INSTANCE);
            query.addScalar("mainAddress2", StringType.INSTANCE);
            query.addScalar("mainAddress3", StringType.INSTANCE);
            query.addScalar("mainCity", StringType.INSTANCE);
            query.addScalar("mainPostalCode", StringType.INSTANCE);
            query.addScalar("mainState", StringType.INSTANCE);
            query.addScalar("mainCountryName", StringType.INSTANCE);
            query.addScalar("mainCountryCode", StringType.INSTANCE);

            query.setParameterList("invoiceIds", invoiceIds);
            query.setResultTransformer(Transformers.aliasToBean(RequestorCore.class));

            List<RequestorCore> requestorDetails = query.list();
            return requestorDetails;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.RETRIEVE_OPERATION_FAILED,
                    e.getMessage());
        }

    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestDAO
     * #getEventHistory(com.mckesson.eig.roi.request.model.RequestEventCriteria)
     */
    @Override
    public List< ? extends RequestEvent> getEventHistory(RequestEventCriteria criteria) {

        final String logSM = "viewEventHistory(criteria)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: EventType = " + criteria.getType());
        }

        String type = (RequestEvent.TYPE.NA == criteria.getType())
                      ? ""
                      : criteria.getType().toString();

        Object[] params = {criteria.getRequestId(), "%" + type + "%"};

        @SuppressWarnings("unchecked") // not supported by 3rd party API
        List< ? extends RequestEvent> events
        = (List<? extends RequestEvent>) getHibernateTemplate().findByNamedQuery("retrieveEventHistory", params);

        List<RequestEvent> eves = new ArrayList<RequestEvent>();
        for (RequestEvent eve : events) {

            if (eve.getName().equals(RequestEvent.TYPE.REASON_ADDED.toString())) {
               eve.setDescription(eve.getDescription().
                                       replaceAll(ROIConstants.FIELD_DELIMITER, ","));
            }
            if (null == eve.getOriginator()) {
                eve.setOriginator("Deleted User - " + eve.getCreatedBy());
            }
            eves.add(eve);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Record Count = " + events.size());
        }
        return eves;
    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestDAO#createAllRequestEvent(java.util.List)
     */
    @Override
    public void createAllRequestEvent(List<RequestEvent> reqEvents) {

        final String logSM = "createAllRequestEvent(reqEvents)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: " + reqEvents.size());
        }

        try {

            for (Iterator it = reqEvents.iterator(); it.hasNext();) {
                getHibernateTemplate().saveOrUpdate(it.next());
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                   e.getMessage());
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
    }


    /**
     * @see com.mckesson.eig.roi.request.dao.RequestCoreDAO#retrieveAllDocumentCharges(long)
     */
    @Override
    public List<RequestCoreChargesDocument> retrieveAllDocumentCharges(long requestId) {

        final String logSM = "retrieveAllDocumentCharges(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: " + requestId);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveReleasedDocumentCharges")
                                        .getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(queryString);
            sqlQuery.setParameter("requestId", requestId);
            sqlQuery.addScalar("id", LongType.INSTANCE);
            sqlQuery.addScalar("amount", DoubleType.INSTANCE);
            sqlQuery.addScalar("copies", IntegerType.INSTANCE);
            sqlQuery.addScalar("billingTierName", StringType.INSTANCE);
            sqlQuery.addScalar("pages", IntegerType.INSTANCE);
            sqlQuery.addScalar("billingtierId", StringType.INSTANCE);
            sqlQuery.addScalar("isElectronic", BooleanType.INSTANCE);
            sqlQuery.addScalar("removeBaseCharge", BooleanType.INSTANCE);
            sqlQuery.addScalar("hasSalesTax", BooleanType.INSTANCE);
            sqlQuery.addScalar("salesTaxAmount", DoubleType.INSTANCE);
            sqlQuery.addScalar("releaseCount", IntegerType.INSTANCE);

            sqlQuery.setResultTransformer(Transformers.aliasToBean(RequestCoreChargesDocument.class));
            @SuppressWarnings("unchecked")
            List<RequestCoreChargesDocument> result = sqlQuery.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

            return result;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

	/**
     * @see com.mckesson.eig.roi.request.dao.RequestCoreDAO#retrieveRequestInvoiceDetails(long)
     */
    @Override
    public RequestorInvoicesList retrieveRequestInvoiceDetails(long requestId) {

        final String logSM = "retrieveRequestInvoiceDetails(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: " + requestId);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestInvoiceDetails")
                                        .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

            query.addScalar("id", LongType.INSTANCE);
            query.addScalar("requestId", LongType.INSTANCE);
            query.addScalar("invoiceType", StringType.INSTANCE);
            query.addScalar("charge", DoubleType.INSTANCE);
            query.addScalar("balance", DoubleType.INSTANCE);
            query.addScalar("paymentAmount", DoubleType.INSTANCE);
            query.addScalar("adjustmentAmount", DoubleType.INSTANCE);
            query.addScalar("description", StringType.INSTANCE);
            query.addScalar("paymentDescription", StringType.INSTANCE);
            query.addScalar("paymentMethod", StringType.INSTANCE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);
            query.setResultTransformer(Transformers.aliasToBean(RequestorInvoice.class));
            List<RequestorInvoice> requestInvoiceList = query.list();

            if (CollectionUtilities.isEmpty(requestInvoiceList)) {
                return null;
            }
            RequestorInvoicesList requestInvoiceDetailList = new RequestorInvoicesList();
            requestInvoiceDetailList.setRequestorInvoices(requestInvoiceList);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

            return requestInvoiceDetailList;

        } catch (DataIntegrityViolationException e) {
                  throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO#updateInvoiceReleased(long,
     *      boolean)
     */
    @Override
    public void updateRequestCoreUnbillable(long requestCoreId,
            boolean unbillable, Timestamp date, User user) {
        final String logSM = "updateRequestCoreUnbillable(requestCoreId, unbillable)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreId);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("updateRequestCoreUnbillable")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreId", requestCoreId, LongType.INSTANCE);
            query.setParameter("unbillable", unbillable, BooleanType.INSTANCE);
            query.setParameter("modifiedDt", date, StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", user.getInstanceId(),
                    IntegerType.INSTANCE);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    /**
     * Method to ProductivityReportDetails
     *
     * @param facility
     * @param username
     * @param requestorType
     * @param fromDate
     * @param toDate
     * @param resultType
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<ProductivityReportDetails> retriveProductivityReportDetails(
            String[] facility, List<String> userName, String[] requestorType,
            Date fromDate, Date toDate,String resultType) {
        final String logSM = "retriveProductivityReportDetails()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + facility + userName + requestorType
                    + fromDate + toDate);
        }
        try{
            Session session = getSession();
            String queryString = null;
            if("\"Username\"".equalsIgnoreCase(resultType))
            queryString = session.getNamedQuery("retriveProductivityReportDetailsFirstLevel")
                                        .getQueryString();
            else if("\"Requestor Type\"".equalsIgnoreCase(resultType))
                queryString = session.getNamedQuery("retriveProductivityReportDetailsSecondLevel")
                                            .getQueryString();
            else if("\"Request ID,Page Type\"".equalsIgnoreCase(resultType))
                queryString = session.getNamedQuery("retriveProductivityReportDetailsThirdLevel")
                                            .getQueryString();
            else
                queryString = session.getNamedQuery("retriveProductivityReportDetailsFourthLevel")
                                            .getQueryString();

            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameterList("facility",facility);
            query.setParameterList("userName",userName);
            query.setParameterList("requestorType",requestorType);
            query.setParameter("fromDate",fromDate, StandardBasicTypes.TIMESTAMP);
            query.setParameter("toDate",toDate , StandardBasicTypes.TIMESTAMP);

            query.addScalar("reqID", StringType.INSTANCE);
            query.addScalar("reqIDCount", IntegerType.INSTANCE);
            query.addScalar("mrn", StringType.INSTANCE);
            query.addScalar("facility", StringType.INSTANCE);
            query.addScalar("userName", StringType.INSTANCE);
            query.addScalar("patientName", StringType.INSTANCE);
            query.addScalar("requestorType", StringType.INSTANCE);
            //if("\"Request ID,MRN\"".equalsIgnoreCase(resultType)){
               query.addScalar("requestorName", StringType.INSTANCE);
            //}
            query.addScalar("billable", StringType.INSTANCE);
            query.addScalar("createDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("pageType", StringType.INSTANCE);
            query.addScalar("pages", IntegerType.INSTANCE);

            query.setResultTransformer(Transformers.aliasToBean(ProductivityReportDetails.class));
            List<ProductivityReportDetails> productivityReportDetailsList = query.list();

            if (CollectionUtilities.isEmpty(productivityReportDetailsList)) {
                return null;
            }
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return productivityReportDetailsList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }
}
