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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.owasp.esapi.ESAPI;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

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
import com.mckesson.eig.roi.utils.MSSQLCodec;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
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

    private static final Log LOG = LogFactory.getLogger(RequestCoreDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static RequestCoreDAOHelper _helper = new RequestCoreDAOHelper();

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
            Session session = getSessionFactory().getCurrentSession();
            MSSQLCodec codec = new MSSQLCodec();
            String finalQuery = ESAPI.encoder().encodeForSQL(codec, _helper.constructSearchSQLQuery(searchCriteria, session, parameters));
            finalQuery = ESAPI.encoder().encodeForSQL(codec, _helper.constructRetrieveRequestDataQuery(searchCriteria, session, finalQuery));
            String updateSQL = ESAPI.encoder().encodeForSQL(codec, finalQuery);
            SQLQuery sqlQuery = session.createSQLQuery(updateSQL);
            sqlQuery.addScalar("requestId", StandardBasicTypes.LONG);
            sqlQuery.addScalar("receiptDate", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("requestStatus", StandardBasicTypes.STRING);
            sqlQuery.addScalar("subtitle", StandardBasicTypes.STRING);
            sqlQuery.addScalar("lastUpdated", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("updatedBy", StandardBasicTypes.STRING);
            sqlQuery.addScalar("requestorName", StandardBasicTypes.STRING);
            sqlQuery.addScalar("requestorTypeName", StandardBasicTypes.STRING);
            sqlQuery.addScalar("requestorType", StandardBasicTypes.LONG);
            sqlQuery.addScalar("patientsString", StandardBasicTypes.STRING);
            sqlQuery.addScalar("facility", StandardBasicTypes.STRING);
            sqlQuery.addScalar("encounterString", StandardBasicTypes.STRING);
            sqlQuery.addScalar("patientLocked", StandardBasicTypes.BOOLEAN);
            sqlQuery.addScalar("vip", StandardBasicTypes.BOOLEAN);
            sqlQuery.addScalar("balance", StandardBasicTypes.DOUBLE);

            Set<String> keys = parameters.keySet();
            for(String key: keys){
                String value = parameters.get(key);
                sqlQuery.setParameter(key, value);
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
            Session session = getSessionFactory().getCurrentSession();
            HashMap<String, String> parameters =  new HashMap<String, String>();
            MSSQLCodec codec = new MSSQLCodec();
            String finalQuery = ESAPI.encoder().encodeForSQL(codec, _helper.constructSearchSQLQuery(searchCriteria, session, parameters));
            String updateSQL = ESAPI.encoder().encodeForSQL(codec, finalQuery);
            SQLQuery sqlQuery = session.createSQLQuery(updateSQL);
            sqlQuery.addScalar("requestId", StandardBasicTypes.LONG);
            Set<String> keys = parameters.keySet();
            for(String key: keys){
                String value = parameters.get(key);
                sqlQuery.setParameter(key, value);
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

            Session session = getSessionFactory().getCurrentSession();
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

            Session session = getSessionFactory().getCurrentSession();
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

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveCreatedRequest").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("status", StandardBasicTypes.STRING);
            query.addScalar("receiptDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("statusReason", StandardBasicTypes.STRING);
            query.addScalar("requestReason", StandardBasicTypes.STRING);
            query.addScalar("requestReasonAttribute", StandardBasicTypes.STRING);
            query.addScalar("statusChangedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("requestPassword", StandardBasicTypes.STRING);

            query.addScalar("releaseCount", StandardBasicTypes.LONG);
            query.addScalar("hasDraftRelease", StandardBasicTypes.BOOLEAN);
            query.addScalar("balanceDue", StandardBasicTypes.DOUBLE);

            query.addScalar("authDoc", StandardBasicTypes.STRING);
            query.addScalar("authDocName", StandardBasicTypes.STRING);
            query.addScalar("authDocSubtitle", StandardBasicTypes.STRING);
            query.addScalar("authDocDateTime", StandardBasicTypes.TIMESTAMP);
            query.addScalar("conversionSource", StandardBasicTypes.STRING);
            query.addScalar("unbillable", StandardBasicTypes.BOOLEAN);

            query.addScalar("createdDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.INTEGER);
            query.addScalar("modifiedDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.INTEGER);
            query.addScalar("modifiedByUser", StandardBasicTypes.STRING);

            query.setParameter("requestId", requestId, StandardBasicTypes.LONG);
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

        	Session session = getSessionFactory().getCurrentSession();
          //This method deletes pages for the request
           _helper.deleteAllPagesForRequest(session, requestId);
          //This method deletes version for the request
           _helper.deleteAllVersionsForRequest(session, requestId);
          //This method deletes documents for the request
           _helper.deleteAllDocumentsForRequest(session, requestId);
          //This method deletes encounters for the request
           _helper.deleteAllEncountersForRequest(session, requestId);
           //This method deletes all non hpfdocuments for the request
           _helper.deleteAllNonHpfDocumentsForRequest(session, requestId);
           //This method deletes all non hpfdocuments for the request
           _helper.deleteAllAttachmentsForRequest(session, requestId);
           //This method deletes patients for the request
           _helper.deleteAllRequestToPatients(session, requestId);
        // deletes all the letter information for the request
           _helper.deleteAllLetterForRequest(session, requestId);
          //This method deletes patients for the request
           _helper.deleteAllPatientsForRequest(session, requestId);
          //This method deletes RequestCore to Patient for the request
           _helper.deleteRequestorForRequest(session, requestId);
          //This method deletes event for the request
           _helper.deleteRequestEventForRequest(session, requestId);
           // deletes all the billing payment information for the request
           _helper.deleteAllBillingPaymentInfoForRequest(session, requestId);
           // deletes all invoices corresponding for the given request
           _helper.deleteAllInvoicesForRequest(session, requestId);
          //This method deletes the given requestId from request core
           _helper.deleteRequestCorForRequest(session, requestId);

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

            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("updateRequestCore");

            query.setParameter("requestId", request.getId(), StandardBasicTypes.LONG);

            query.setParameter("requestStatus", request.getStatus(), StandardBasicTypes.STRING);
            query.setParameter("receiptDate", request.getReceiptDate(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("statusReason", request.getStatusReason(), StandardBasicTypes.STRING);
            query.setParameter("requestReason", request.getRequestReason(), StandardBasicTypes.STRING);
            query.setParameter("requestReasonAttribute", request.getRequestReasonAttribute());
            query.setParameter("statusChangedDt", request.getStatusChangedDt(),
                                                                              StandardBasicTypes.TIMESTAMP);
            query.setParameter("requestPassword", request.getRequestPassword(), StandardBasicTypes.STRING);

            query.setParameter("modifiedDate", request.getModifiedDate(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", request.getModifiedBy(), StandardBasicTypes.INTEGER);

            long noOfRowsUpdated = query.executeUpdate();

            if (noOfRowsUpdated <= 0) {
                throw new ROIException(ROIClientErrorCodes.UPDATE_REQUEST_CORE_OPERATION_FAILED);
            }

            //update completed date if exists
            if (request.getCompletedDate() != null) {
	            query = session.getNamedQuery("updateRequestCoreCompletedDate");
	            query.setParameter("requestId", request.getId(), StandardBasicTypes.LONG);
	            query.setParameter("completedDate", request.getCompletedDate(), StandardBasicTypes.TIMESTAMP);
	            query.setParameter("modifiedDate", request.getModifiedDate(), StandardBasicTypes.TIMESTAMP);
	            query.setParameter("modifiedBy", request.getModifiedBy(), StandardBasicTypes.INTEGER);
	            noOfRowsUpdated = query.executeUpdate();

	            if (noOfRowsUpdated <= 0) {
	                throw new ROIException(ROIClientErrorCodes.UPDATE_REQUEST_CORE_OPERATION_FAILED);
	            }
            }


            if (requestor.getId() <= 0) {
                createRequestorCore(requestor, request.getId());
            } else {
                _helper.updateRequestor(session, requestor, request.getId());
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

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveCoreRequestor").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("requestorSeq", StandardBasicTypes.LONG);
            query.addScalar("requestorType", StandardBasicTypes.LONG);
            query.addScalar("requestorTypeName", StandardBasicTypes.STRING);
            query.addScalar("workPhone", StandardBasicTypes.STRING);
            query.addScalar("HomePhone", StandardBasicTypes.STRING);
            query.addScalar("cellPhone", StandardBasicTypes.STRING);
            query.addScalar("contactPhone", StandardBasicTypes.STRING);
            query.addScalar("contactName", StandardBasicTypes.STRING);
            query.addScalar("fax", StandardBasicTypes.STRING);
            query.addScalar("firstName", StandardBasicTypes.STRING);
            query.addScalar("middleName", StandardBasicTypes.STRING);
            query.addScalar("lastName", StandardBasicTypes.STRING);
            query.addScalar("suffix", StandardBasicTypes.STRING);
            query.addScalar("createdDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.INTEGER);
            query.addScalar("modifiedDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.INTEGER);
            query.addScalar("mainAddress1", StandardBasicTypes.STRING);
            query.addScalar("mainAddress2", StandardBasicTypes.STRING);
            query.addScalar("mainAddress3", StandardBasicTypes.STRING);
            query.addScalar("mainCity", StandardBasicTypes.STRING);
            query.addScalar("mainPostalCode", StandardBasicTypes.STRING);
            query.addScalar("mainState", StandardBasicTypes.STRING);
            query.addScalar("mainCountryName", StandardBasicTypes.STRING);
            query.addScalar("mainCountryCode", StandardBasicTypes.STRING);

            query.setParameter("requestId", requestId, StandardBasicTypes.LONG);
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

            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("createRequestEvent");

            query.setParameter("createdDate", getDate(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("CreatedBy", event.getCreatedBy(), StandardBasicTypes.INTEGER);
            query.setParameter("modifiedDate", event.getModifiedDate(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", event.getModifiedBy(), StandardBasicTypes.INTEGER);
            query.setParameter("recordVersion", event.getRecordVersion(), StandardBasicTypes.INTEGER);
            query.setParameter("requestCoreSeq", event.getRequestId(), StandardBasicTypes.LONG);
            query.setParameter("name", event.getName(), StandardBasicTypes.STRING);
            query.setParameter("description", event.getDescription(), StandardBasicTypes.STRING);

            query.executeUpdate();

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

            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("deleteLatestRequestEventByRequestIdAndEvent");

            query.setParameter("requestId", requestId, StandardBasicTypes.LONG);
            query.setParameter("eventType", eventType.toString(), StandardBasicTypes.STRING);

            query.executeUpdate();

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

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveCoreRequestorByInvoiceIds").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("requestId", StandardBasicTypes.LONG);
            query.addScalar("requestorSeq", StandardBasicTypes.LONG);
            query.addScalar("requestorType", StandardBasicTypes.LONG);
            query.addScalar("requestorTypeName", StandardBasicTypes.STRING);
            query.addScalar("workPhone", StandardBasicTypes.STRING);
            query.addScalar("HomePhone", StandardBasicTypes.STRING);
            query.addScalar("cellPhone", StandardBasicTypes.STRING);
            query.addScalar("contactPhone", StandardBasicTypes.STRING);
            query.addScalar("contactName", StandardBasicTypes.STRING);
            query.addScalar("fax", StandardBasicTypes.STRING);
            query.addScalar("firstName", StandardBasicTypes.STRING);
            query.addScalar("middleName", StandardBasicTypes.STRING);
            query.addScalar("lastName", StandardBasicTypes.STRING);
            query.addScalar("suffix", StandardBasicTypes.STRING);
            query.addScalar("createdDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.INTEGER);
            query.addScalar("modifiedDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.INTEGER);
            query.addScalar("mainAddress1", StandardBasicTypes.STRING);
            query.addScalar("mainAddress2", StandardBasicTypes.STRING);
            query.addScalar("mainAddress3", StandardBasicTypes.STRING);
            query.addScalar("mainCity", StandardBasicTypes.STRING);
            query.addScalar("mainPostalCode", StandardBasicTypes.STRING);
            query.addScalar("mainState", StandardBasicTypes.STRING);
            query.addScalar("mainCountryName", StandardBasicTypes.STRING);
            query.addScalar("mainCountryCode", StandardBasicTypes.STRING);

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
        = (List< ? extends RequestEvent>) getHibernateTemplate().findByNamedQuery("retrieveEventHistory", params);

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

            getHibernateTemplate().saveOrUpdate(reqEvents);
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

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveReleasedDocumentCharges")
                                        .getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(queryString);
            sqlQuery.setParameter("requestId", requestId);
            sqlQuery.addScalar("id", StandardBasicTypes.LONG);
            sqlQuery.addScalar("amount", StandardBasicTypes.DOUBLE);
            sqlQuery.addScalar("copies", StandardBasicTypes.INTEGER);
            sqlQuery.addScalar("billingTierName", StandardBasicTypes.STRING);
            sqlQuery.addScalar("pages", StandardBasicTypes.INTEGER);
            sqlQuery.addScalar("billingtierId", StandardBasicTypes.STRING);
            sqlQuery.addScalar("isElectronic", StandardBasicTypes.BOOLEAN);
            sqlQuery.addScalar("removeBaseCharge", StandardBasicTypes.BOOLEAN);
            sqlQuery.addScalar("hasSalesTax", StandardBasicTypes.BOOLEAN);
            sqlQuery.addScalar("salesTaxAmount", StandardBasicTypes.DOUBLE);
            sqlQuery.addScalar("releaseCount", StandardBasicTypes.INTEGER);

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

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestInvoiceDetails")
                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, StandardBasicTypes.LONG);

            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("requestId", StandardBasicTypes.LONG);
            query.addScalar("invoiceType", StandardBasicTypes.STRING);
            query.addScalar("charge", StandardBasicTypes.DOUBLE);
            query.addScalar("balance", StandardBasicTypes.DOUBLE);
            query.addScalar("paymentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("adjustmentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("description", StandardBasicTypes.STRING);
            query.addScalar("paymentDescription", StandardBasicTypes.STRING);
            query.addScalar("paymentMethod", StandardBasicTypes.STRING);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("recordVersion", StandardBasicTypes.INTEGER);
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
            Session session = getSessionFactory().getCurrentSession();
            Query query;
            query = session.getNamedQuery("updateRequestCoreUnbillable");
            query.setParameter("requestCoreId", requestCoreId, StandardBasicTypes.LONG);
            query.setParameter("unbillable", unbillable, StandardBasicTypes.BOOLEAN);
            query.setParameter("modifiedDt", date, StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", user.getInstanceId(),
                    StandardBasicTypes.INTEGER);
            query.executeUpdate();

            query.executeUpdate();
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
            Session session = getSessionFactory().getCurrentSession();
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

            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameterList("facility",facility);
            query.setParameterList("userName",userName);
            query.setParameterList("requestorType",requestorType);
            query.setParameter("fromDate",fromDate, StandardBasicTypes.TIMESTAMP);
            query.setParameter("toDate",toDate , StandardBasicTypes.TIMESTAMP);

            query.addScalar("reqID", StandardBasicTypes.STRING);
            query.addScalar("reqIDCount", StandardBasicTypes.INTEGER);
            query.addScalar("mrn", StandardBasicTypes.STRING);
            query.addScalar("facility", StandardBasicTypes.STRING);
            query.addScalar("userName", StandardBasicTypes.STRING);
            query.addScalar("patientName", StandardBasicTypes.STRING);
            query.addScalar("requestorType", StandardBasicTypes.STRING);
            //if("\"Request ID,MRN\"".equalsIgnoreCase(resultType)){
               query.addScalar("requestorName", StandardBasicTypes.STRING);
            //}
            query.addScalar("billable", StandardBasicTypes.STRING);
            query.addScalar("createDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("pageType", StandardBasicTypes.STRING);
            query.addScalar("pages", StandardBasicTypes.INTEGER);

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
