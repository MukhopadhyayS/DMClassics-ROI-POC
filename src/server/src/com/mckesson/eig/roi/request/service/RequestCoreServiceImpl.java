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

package com.mckesson.eig.roi.request.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;

import org.apache.commons.beanutils.BeanUtils;

import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.roi.admin.dao.SysParamDAO;
import com.mckesson.eig.roi.admin.dao.TaxPerFacilityDAO;
import com.mckesson.eig.roi.admin.model.SysParam;
import com.mckesson.eig.roi.admin.model.TaxPerFacility;
import com.mckesson.eig.roi.admin.service.BillingAdminServiceValidator;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.base.service.BaseROIService;
import com.mckesson.eig.roi.base.service.ROIAuditManager;
import com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryCharges;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesAdjustmentPayment;
import com.mckesson.eig.roi.billing.model.SalesTaxReason;
import com.mckesson.eig.roi.billing.model.SalesTaxSummary;
import com.mckesson.eig.roi.ccd.provider.CcdProviderFactory;
import com.mckesson.eig.roi.ccd.provider.dao.CcdProviderDAO;
import com.mckesson.eig.roi.muroioutbound.model.ExternalSourceDocument;
import com.mckesson.eig.roi.muroioutbound.service.MUCreateROIOutboundServiceCore;
import com.mckesson.eig.roi.request.dao.RequestCoreChargesDAO;
import com.mckesson.eig.roi.request.dao.RequestCoreDAO;
import com.mckesson.eig.roi.request.dao.RequestCorePatientDAO;
import com.mckesson.eig.roi.request.dao.RequestCorePatientDAO.RequestDSRMappingKeys;
import com.mckesson.eig.roi.request.model.AuditAndEventList;
import com.mckesson.eig.roi.request.model.Comment;
import com.mckesson.eig.roi.request.model.Comments;
import com.mckesson.eig.roi.request.model.DeletePatientList;
import com.mckesson.eig.roi.request.model.EventTypes;
import com.mckesson.eig.roi.request.model.ProductivityReportDetails;
import com.mckesson.eig.roi.request.model.RequestCore;
import com.mckesson.eig.roi.request.model.RequestCoreCharges;
import com.mckesson.eig.roi.request.model.RequestCoreChargesBilling;
import com.mckesson.eig.roi.request.model.RequestCoreChargesDocument;
import com.mckesson.eig.roi.request.model.RequestCoreChargesFee;
import com.mckesson.eig.roi.request.model.RequestCoreChargesInvoice;
import com.mckesson.eig.roi.request.model.RequestCoreChargesInvoicesList;
import com.mckesson.eig.roi.request.model.RequestCoreChargesShipping;
import com.mckesson.eig.roi.request.model.RequestCoreSearchCriteria;
import com.mckesson.eig.roi.request.model.RequestCoreSearchResult;
import com.mckesson.eig.roi.request.model.RequestCoreSearchResultList;
import com.mckesson.eig.roi.request.model.RequestEvent;
import com.mckesson.eig.roi.request.model.RequestEventCriteria;
import com.mckesson.eig.roi.request.model.RequestEvents;
import com.mckesson.eig.roi.request.model.RequestPatient;
import com.mckesson.eig.roi.request.model.RequestPatientsList;
import com.mckesson.eig.roi.request.model.SaveRequestPatientsList;
import com.mckesson.eig.roi.requestor.model.RequestorCore;
import com.mckesson.eig.roi.requestor.model.RequestorInvoicesList;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.utils.PassPhrase;
import com.mckesson.eig.roi.utils.SpringUtil;
import com.mckesson.eig.utility.encryption.AESEncryptor;
import com.mckesson.eig.utility.util.CollectionUtilities;

/**
 * @author OFS
 * @date Jun 29, 2012
 * @since Jun 29, 2012
 *
 */

@WebService(serviceName="RequestCoreService", endpointInterface="com.mckesson.eig.roi.request.service.RequestCoreService",
targetNamespace="urn:eig.mckesson.com", portName="requestCorePort", name="RequestCoreServiceImpl")
public class RequestCoreServiceImpl
extends BaseROIService
implements RequestCoreService {

    private static final OCLogger LOG = new OCLogger(RequestCoreServiceImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final String ROI_MU_OUTBOUND_SERVICE =
                   "com.mckesson.eig.roi.muroioutbound.service.MUCreateROIOutboundServiceCoreImpl";

    private static final String REQUEST_RECEIVED_STATUS = "Received";
    private final RequestCoreServiceHelper _reqCoreServiceHelper = new RequestCoreServiceHelper();
    
    private AESEncryptor _encryptor;

    /**
     * gets the AES encryptor used to encrypt text 
     * @return
     * @throws Exception
     */
    public AESEncryptor getEncryptor() throws Exception {
        
        if (null == _encryptor) {
            _encryptor = new AESEncryptor();
        }
        return _encryptor;
    }


    public RequestCoreSearchResultList searchRequest(RequestCoreSearchCriteria searchCriteria) {

        final String logSM = "searchRequest(searchCriteria)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {

            RequestServiceValidator validator = new RequestServiceValidator();
            if (!validator.validateSearchCriteria(searchCriteria)) {
                throw validator.getException();
            }

            RequestCoreDAO dao = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
            List<RequestCoreSearchResult> searchResult = dao.searchRequest(searchCriteria);

            RequestCoreSearchResultList resultList = new RequestCoreSearchResultList(searchResult);
            resultList.setMaxCountExceeded(searchResult.size() > searchCriteria.getMaxCount());

            String comment = new StringBuffer("Search Performed in ROI. ")
                                .append("Type of Search:- Request. ")
                                .append("Search Criteria entered:- ")
                                .append(searchCriteria.constructAuditSearchComment())
                                .toString();

          //It will create the search request audit information in Audit_Trail table.
            doAudit(comment,
                    getUser().getInstanceId(),
                    dao.getDate(),
                    ROIConstants.AUDIT_ACTION_CODE_SEARCH,
                    searchCriteria.getFacility() == null ? ROIConstants.DEFAULT_FACILITY
                            : searchCriteria.getFacility().trim(),
                    searchCriteria.getMrn(),
                    searchCriteria.getEncounter());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End: No. of Records = "
                                + resultList.getRequestCoreSearchResult().size());
            }

            return resultList;
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in searchRequest",e);
            throw new ROIException(ROIClientErrorCodes.SEARCH_REQUEST_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.request.service.RequestCoreService
     *      #createRequest(com.mckesson.eig.roi.request.model.RequestCores)
     */
    public RequestCore createRequest(RequestCore request) {

        final String logSM = "createRequest(request)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: Request :" + request);
        }

        try {

            RequestCoreDAO dao = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
            RequestServiceValidator validator = new RequestServiceValidator();

            if (!validator.validateRequestCore(request)) {
                throw validator.getException();
            }

            Timestamp date = dao.getDate();
            setDefaultDetails(request, date, true);
            setDefaultDetails(request.getRequestorDetail(), date, true);
            RequestCore createdRequest = dao.createRequest(request);
            RequestCore updatedRequest = setDefaultFacility(createdRequest);
            _reqCoreServiceHelper.createEvent(request, REQUEST_RECEIVED_STATUS,
                                              dao,
                                              true,
                                              request.getStatusReason() != null);

            auditRequest(request.createAuditComment(),
                         getUser().getInstanceId(),
                         date,
                         ROIConstants.REQUEST_AUDIT_ACTION_CODE_CREATE);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End: Created Request Id :"
                                + createdRequest.getId());
            }

            return updatedRequest;

        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {

            LOG.error("Exception occured in createRequest",e);
            throw new ROIException(e, ROIClientErrorCodes.REQUEST_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.request.service.RequestCoreService
     *      #RequestCore(com.mckesson.eig.roi.request.model.RequestCores)
     */
    @Override
    public RequestCore retrieveRequest(long requestId, boolean isSearchRetrieve) {

        final String logSM = "retrieveRequest(request)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: Request ID:" + requestId);
        }

        if (requestId <= 0) {
            throw new ROIException(ROIClientErrorCodes.INVALID_REQUEST_ID,
                                   String.valueOf(requestId));
        }

        try {

            RequestCoreDAO dao = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);

            RequestCore request = dao.retrieveRequest(requestId);
            RequestCore updatedRequest = setDefaultFacility(request);

            // MU2 Use case - Audit message Changed
            if (isSearchRetrieve) {
                auditRequest(request.constructSearchRetrieveAudit(),
                             getUser().getInstanceId(),
                             dao.getDate(),
                             ROIConstants.REQUEST_AUDIT_ACTION_CODE_VIEW);
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End: Retrieve Request Id :" + requestId);
            }

            return updatedRequest;

        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {

            LOG.error("Exception occured in retrieveRequest",e);
            throw new ROIException(e, ROIClientErrorCodes.REQUEST_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.request.service.RequestCoreService
     */
    @Override
    public void deleteRequest(long requestId) {

        final String logSM = "DeleteRequest(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: Request ID:" + requestId);
        }

        try {

            RequestCoreDAO dao = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);

            if (requestId <= 0) {
                throw new ROIException(ROIClientErrorCodes.INVALID_REQUEST_ID,
                                       String.valueOf(requestId));
            }

            Timestamp date = dao.getDate();
            dao.deleteRequest(requestId);
            getCcdProviderFactory().sendCancelledStatistics((int) requestId,
                                                            date,
                                                            ROIConstants.DELETED_STATUS);

            auditRequest("Request Deleted - " + requestId,
                         getUser().getInstanceId(),
                         date,
                         ROIConstants.REQUEST_AUDIT_ACTION_CODE_DELETE);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End: Delete Request Id :" + requestId);
            }

        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in deleteRequest",e);
            throw new ROIException(e,
                    ROIClientErrorCodes.REQUEST_OPERATION_FAILED);
        }

    }

    /**
     *
     * @see com.mckesson.eig.roi.request.service.RequestCoreService
     *      #updateRequest(com.mckesson.eig.roi.request.model.RequestCore)
     */
    public RequestCore updateRequest(RequestCore request) {

        final String logSM = "updateRequest(request)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + request);
        }

        try {

            RequestCoreDAO dao = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
            RequestServiceValidator validator = new RequestServiceValidator();

            if (!validator.validateUpdateRequestCore(request)) {
                throw validator.getException();
            }

            Timestamp date = dao.getDate();
            setDefaultDetails(request, date, false);

            if (!ROIConstants.DEFAULT_STATUS_COMPLETED.equalsIgnoreCase(request.getStatus())) {
                getMUCreateROIOutboundServiceCore().createROIOutboundStatistics(request);
            }

            if (ROIConstants.DEFAULT_STATUS_DENIED.equalsIgnoreCase(request.getStatus()) ||
                ROIConstants.DEFAULT_STATUS_CANCELED.equalsIgnoreCase(request.getStatus()) ||
                ROIConstants.DEFAULT_STATUS_PENDED.equalsIgnoreCase(request.getStatus())){
                RequestCoreDeliveryDAO rCDeliveryDAO =
                        (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);
                List<RequestCoreDeliveryCharges> requestCoreDeliveryChargesList =  rCDeliveryDAO
                        .retrieveRequestCoreDeliveryChargesPrebill(request.getId());

                for (RequestCoreDeliveryCharges rc : requestCoreDeliveryChargesList){

                    if  ("Active".equalsIgnoreCase(rc.getPrebillStatus())){
                        rc.setPrebillStatus("InActive");
                        rc.setRequestCoreId(request.getId());
                        rCDeliveryDAO.updatePrebillStatusInvoice(rc);
                    }
                }
            }

            RequestCore oldReq = dao.retrieveRequest(request.getId());


            boolean isReasonChanged = _reqCoreServiceHelper.isReasonChanged(request,
                                                                          oldReq.getStatusReason(),
                                                                          request.getStatus());

            // for auth request there won't be any requestor associated initially before save
            RequestorCore requestorDetail = request.getRequestorDetail();
            if (oldReq.getRequestorDetail() != null) {

                long requestorCoreSeq = oldReq.getRequestorDetail().getId();
                requestorDetail.setId(requestorCoreSeq);
            }

            // if the request password is null, then new request password is set
            if (request.getRequestPassword() == null) {
                request.setRequestPassword(getGeneratedPassword());
            }

            setDefaultDetails(requestorDetail, date, (requestorDetail.getId() <= 0));
            RequestCore updatedRequest = dao.updateRequest(request);
            updatedRequest.setReleaseCount(oldReq.getReleaseCount());
            updatedRequest.setHasDraftRelease(oldReq.isHasDraftRelease());

            RequestCore updatedRequestWithDefaultFacility = setDefaultFacility(updatedRequest);

            _reqCoreServiceHelper.createEvent(request,
                                         oldReq.getStatus(),
                                         dao,
                                         !oldReq.getStatus().equalsIgnoreCase(request.getStatus()),
                                         isReasonChanged);

            auditRequest(request.updateAuditComment(),
                         getUser().getInstanceId(),
                         date,
                         ROIConstants.REQUEST_AUDIT_ACTION_CODE_UPDATE);

            LOG.debug(logSM + "<<End:" + updatedRequest);
            return updatedRequestWithDefaultFacility;

        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {

            LOG.error("Exception occured in updateRequest",e);
            throw new ROIException(e, ROIClientErrorCodes.REQUEST_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.request.service.RequestCoreService
     *      #updateRequest(com.mckesson.eig.roi.request.model.RequestPatientsList)
     */
    @Override
    public RequestPatientsList retrieveRequestPatient(long requestId) {

        final String logSM = "retrieveRequestPatient(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: requestId: " + requestId);
        }

        if (requestId <= 0) {
            throw new ROIException(ROIClientErrorCodes.INVALID_REQUEST_ID,
                    "Request Id should not be less than 1:" + requestId);
        }

        try {

            RequestCorePatientDAO dao = (RequestCorePatientDAO) getDAO(DAOName.REQUEST_PATIENT_DAO);
            Map<RequestDSRMappingKeys, List<? extends BaseModel>> patients =
                                            dao.retrieveRequestPatient(requestId);

            List<RequestPatient> requestPatients =
                                    _reqCoreServiceHelper.contructRequestPatientDetails(patients);

            RequestPatientsList patientsList = new RequestPatientsList(requestPatients);
            patientsList.setRequestId(requestId);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End: Retrieved requestId :" + requestId);
            }

            return patientsList;

        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {

            LOG.error("Exception occured in retrieveRequestPatient",e);
            throw new ROIException(e, ROIClientErrorCodes.REQUEST_OPERATION_FAILED);
        }
    }

    @Override
    public RequestPatientsList saveRequestPatient(SaveRequestPatientsList patientDetails) {

        final String logSM = "saveRequestPatient(patientDetails)";

        /*
         * if (DO_DEBUG) { LOG.debug(logSM + ">>Start: RequestPatientsList: " +
         * patientDetails); }
         */

        try {

            RequestCorePatientDAO dao = (RequestCorePatientDAO) getDAO(DAOName.REQUEST_PATIENT_DAO);
            RequestServiceValidator validator = new RequestServiceValidator();

            if (!validator.validateRequestPatient(patientDetails)) {
                throw validator.getException();
            }

            DeletePatientList deletePatient = patientDetails.getDeletePatient();
            if (deletePatient != null) {
                //Deletes the patient information
                dao.removePatientInfo(deletePatient,
                                      patientDetails.getRequestId());
            }

            Timestamp date = dao.getDate();

            long userId = getUser().getInstanceId();
            // sets default values
            BaseModel baseModal = new SaveRequestPatientsList();
            baseModal.setCreatedBy(userId);
            baseModal.setCreatedDt(date);
            baseModal.setModifiedBy(userId);
            baseModal.setModifiedDt(date);

            // inserts the list of newly added patients and its subtree to the
            // release
            dao.updatePatient(patientDetails.getRequestId(), patientDetails.getUpdatePatients(),
                              baseModal);

            // inserts the list of newly added encounter and its subtree to the
            // release
            dao.updateEncounter(patientDetails.getRequestId(),
                                patientDetails.getUpdateEncounters());

            // inserts the list of newly added documents and its subtree to the
            // release
            dao.updateDocument(patientDetails.getRequestId(), patientDetails.getUpdateDocuments());

            // inserts the list of newly added versions and its subtree to the
            // release
            dao.updateVersion(patientDetails.getRequestId(), patientDetails.getUpdateVersions());

            // updates the pages
            //CR# 381316 - Fix.
            dao.insertPage(patientDetails.getRequestId(), patientDetails.getUpdatePages());

            // update the supplemental Documents
            dao.updateSupplementalDocument(patientDetails.getRequestId(),
                                           patientDetails.getUpdateSupplementalDocument(),
                                           baseModal);

            // update the supplemental Attachments
            dao.updateSupplementalAttachment(patientDetails.getRequestId(),
                                             patientDetails.getUpdateSupplementalAttachement(),
                                             baseModal);

            if (null != deletePatient
                      && CollectionUtilities.hasContent(deletePatient.getPatientSeq())) {

                List<String> mrnList =
                        dao.retrieveExternalSourceDocuments(patientDetails.getRequestId(),
                                                            deletePatient.getPatientSeq());

                int requestId = (int) patientDetails.getRequestId();
                for (String mrn : mrnList) {

                    List<ExternalSourceDocument> extList =
                            getCcdProviderDao().getExternalSourceDocumentByReqIdAndMrn(requestId,
                                                                                       mrn);

                    for (ExternalSourceDocument ext : extList) {

                        ext.setReqStatus(ROIConstants.NEW_STATUS);
                        getCcdProviderDao().updateExternalSourceDocument(ext);
                    }
                }

              }

            RequestCoreDAO requestCoreDao = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
            RequestCore requestCore = requestCoreDao.retrieveRequest(patientDetails.getRequestId());

            getMUCreateROIOutboundServiceCore().createROIOutboundStatistics(requestCore);

            RequestPatientsList requestPatientList =
                                        retrieveRequestPatient(patientDetails.getRequestId());

            if (requestPatientList == null) {
                throw new ROIException(ROIClientErrorCodes.INVALID_PATIENT_DETAILS,
                                       "Unable to save request patient" + requestPatientList);
            }

            // Bhaskar
            // Any specific reason for commenting these logs?
            // Is it because of checkmarx?
            // This is seen in multiple places. So are the reasons same for all of them?
            /*
             * if (DO_DEBUG) { LOG.debug(logSM +
             * "<<End: Retrieved RequestPatientsList :" + patientDetails); }
             */

            return requestPatientList;

        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in saveRequestPatient",e);
            throw new ROIException(e, ROIClientErrorCodes.REQUEST_OPERATION_FAILED);
        }
    }

    /**
     * This method retrieves the RequestBillingPayment values
     *
     * @param requestId
     * @return RequestCoreChargesBillingInfo
     */
    @Override
    public RequestCoreCharges retrieveRequestCoreCharges(long requestCoreId) {

        final String logSM = "retrieveRequestBillingPaymentInfo(requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreId);
        }

        try {

            // retrieves the requestCoreBilling payment Info
            // which retrieves the requestCoreCharges, document charges, Fee charges
            RequestCoreChargesDAO dao =
                    (RequestCoreChargesDAO) getDAO(DAOName.REQUEST_CORE_CHARGES);
            RequestCoreCharges requestCoreCharges =
                    dao.retrieveRequestCoreBillingPaymentInfo(requestCoreId);

            // retrieves the list of invoices for the given requestId
            RequestCoreDeliveryDAO deliveryDao =
                    (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

            // retrieves all the information from the Sales Tax Reason Table.
            if (requestCoreCharges != null) {

                List<SalesTaxReason> salesTaxReasonList =
                        deliveryDao.retrieveSalesTaxReason(requestCoreCharges.getId());

                SalesTaxSummary salesTaxSummary = new SalesTaxSummary();
                salesTaxSummary.setSalesTaxReason(salesTaxReasonList);
                requestCoreCharges.setSalesTaxSummary(salesTaxSummary);
            }


            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

            /*
             * This fix pertains to the functionality during save and release-invoice. JIRA ID - HC-163533
             */
            return (requestCoreCharges!=null)? requestCoreCharges : new RequestCoreCharges();

        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Exception occured in retrieveRequestCoreCharges",e);
            throw new ROIException(e, ROIClientErrorCodes.REQUEST_BILLING_PAYMENT_OPERATION_FAILED);
        }
    }

    /**
     * This method saves the RequestCoreCharges values
     *
     * @param requestCoreCharges
     * @return
     */
    public void saveRequestCoreCharges(RequestCoreCharges requestCoreCharges) {

        final String logSM = "saveRequestCoreCharges(requestCoreCharges)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreCharges);
        }

        try {

            RequestCoreChargesDAO dao =
                                (RequestCoreChargesDAO) getDAO(DAOName.REQUEST_CORE_CHARGES);

            RequestCoreCharges requestCoreChargesOld =
                            dao.retrieveRequestCoreCharges(requestCoreCharges.getRequestCoreSeq()); 
            

            // if the request core charges is empty the new request core charges is created
            long requestCoreChargesId = 0;
            if (requestCoreChargesOld == null) {

                requestCoreCharges.setDisplayBillingPaymentInfo(true);
                requestCoreChargesId = createRequestCoreCharges(requestCoreCharges, dao);
            } else {

                RequestCoreDeliveryDAO deliveryDao =
                        (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

                //deleting the entries in shipping, fee, document, salesTaxReason details
                dao.deleteRequestCoreChargesShipping(requestCoreChargesOld.getId());
                dao.deleteRequestCoreChargesFee(requestCoreChargesOld.getId());
                dao.deleteRequestCoreChargesDocument(requestCoreChargesOld.getId());
                deliveryDao.deleteSalesTaxReason(requestCoreChargesOld.getId());

                dao.deleteRequestCoreCharges(requestCoreCharges.getRequestCoreSeq());
                updateRequestChargeDetails(requestCoreCharges, requestCoreChargesOld);

                requestCoreChargesId = createRequestCoreCharges(requestCoreCharges, dao);
            } 
            updateRequestCoreUnbillable(requestCoreCharges.getRequestCoreSeq(), requestCoreCharges.getUnbillable());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End: Created RequestCore Charges Id:" + requestCoreChargesId);
            }

        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            LOG.error("Exception occured in saveRequestCoreCharges",e);
            throw new ROIException(e,
                    ROIClientErrorCodes.REQUEST_CORE_CHARGES_OPERATION_FAILED);
        }
    }

    private void updateRequestCoreUnbillable(long requestCoreId, boolean unbillable) {
        RequestCoreDAO dao =
            (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
        dao.updateRequestCoreUnbillable(requestCoreId, unbillable, dao.getDate(),getUser());
    }

    /**
     * @param requestCoreCharges
     * @param requestCoreChargesOld
     */
    private void updateRequestChargeDetails(RequestCoreCharges requestCoreCharges,
                                               RequestCoreCharges requestCoreChargesOld) {

        requestCoreCharges.setDisplayBillingPaymentInfo(
                                    requestCoreChargesOld.isDisplayBillingPaymentInfo());

        requestCoreCharges.setPaymentAmount(requestCoreChargesOld.getPaymentAmount());
        requestCoreCharges.setCreditAdjustmentAmount(
                                            requestCoreChargesOld.getCreditAdjustmentAmount());
        requestCoreCharges.setDebitAdjustmentAmount(
                                            requestCoreChargesOld.getDebitAdjustmentAmount());
    }

    /**
     * creates the request core charges and its details such as shipping details, document details,
     * fee charge details
     * @param requestCoreCharges
     * @param dao
     * @return created Request Core Charges Id
     */
    private long createRequestCoreCharges(RequestCoreCharges requestCoreCharges,
                                          RequestCoreChargesDAO dao) {

        Date date = dao.getDate();
        setDefaultDetails(requestCoreCharges, date, true);

        // set default details for the shipping charges
        RequestCoreChargesShipping shippingCharges =
                        requestCoreCharges.getRequestCoreChargesShipping();
        if (shippingCharges != null) {
            setDefaultDetails(shippingCharges, date, true);
        }

        // creates the requestCore Charges as well as the shipping charges
        long requestCoreChargesId = dao.saveRequestCoreCharges(requestCoreCharges);
        RequestCoreChargesBilling billingCharges =
                                        requestCoreCharges.getRequestCoreChargesBilling();

        if (billingCharges == null) {
            return requestCoreChargesId;
        }

        createRequestCoreChargesDocument(billingCharges, requestCoreChargesId, date);
        createRequestCoreChargesFee(billingCharges, requestCoreChargesId, date);
        createRequestCoreChargesShipping(requestCoreCharges, requestCoreChargesId, date);

        return requestCoreChargesId;
    }

    /**
     * creates the list of all document charges details
     * @param billingCharges
     * @param requestCoreChargesId
     * @param date
     */
    private void createRequestCoreChargesDocument(RequestCoreChargesBilling billingCharges,
                                                  long requestCoreChargesId,
                                                  Date date) {

        Set<RequestCoreChargesDocument> requestCoreChargesDocumentSet =
                                            billingCharges.getRequestCoreChargesDocument();

        if (CollectionUtilities.isEmpty(requestCoreChargesDocumentSet)) {
            return;

        }
        // saves the document charges
        RequestCoreChargesDAO dao = (RequestCoreChargesDAO) getDAO(DAOName.REQUEST_CORE_CHARGES);

        RequestCoreChargesDocument requestCoreChargesDocument = null;
        Iterator<RequestCoreChargesDocument> iter = requestCoreChargesDocumentSet.iterator();
        while (iter.hasNext()) {

            requestCoreChargesDocument = iter.next();
            requestCoreChargesDocument.setRequestCoreChargesSeq(requestCoreChargesId);
            setDefaultDetails(requestCoreChargesDocument, date, true);
            dao.createRequestCoreChargesDocument(requestCoreChargesDocument);
        }
    }

    /**
     * creates the request Core Charges fee charges details
     * @param billingCharges
     * @param requestCoreChargesId
     * @param date
     */
    private void createRequestCoreChargesFee(RequestCoreChargesBilling billingCharges,
                                             long requestCoreChargesId,
                                             Date date) {
        // saves the fee charges
        Set<RequestCoreChargesFee> requestCoreChargesFeeSet =
                                            billingCharges.getRequestCoreChargesFee();

        if (CollectionUtilities.isEmpty(requestCoreChargesFeeSet)) {
            return;
        }

        RequestCoreChargesDAO dao =
                (RequestCoreChargesDAO) getDAO(DAOName.REQUEST_CORE_CHARGES);

        RequestCoreChargesFee requestCoreChargesFee = null;
        Iterator<RequestCoreChargesFee> iter = requestCoreChargesFeeSet.iterator();
        while (iter.hasNext()) {

            requestCoreChargesFee = iter.next();
            requestCoreChargesFee.setRequestCoreChargesSeq(requestCoreChargesId);
            setDefaultDetails(requestCoreChargesFee, date, true);
            dao.createRequestCoreChargesFee(requestCoreChargesFee);
        }
    }

    /**
     * Creates the Request Core charges shipping details
     * @param requestCoreCharges
     * @param requestCoreChargesId
     */
    private void createRequestCoreChargesShipping(RequestCoreCharges requestCoreCharges,
                                                  long requestCoreChargesId,
                                                  Date date) {

        RequestCoreDeliveryDAO dao =
                (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

        SalesTaxSummary salesTaxSummary = requestCoreCharges.getSalesTaxSummary();
        if (null == salesTaxSummary) {
            return;
        }

        List<SalesTaxReason> salesTaxReasonList = salesTaxSummary.getSalesTaxReason();
        if (CollectionUtilities.hasContent(salesTaxReasonList)) {

            for (SalesTaxReason salesTaxReason : salesTaxReasonList) {

                setDefaultDetails(salesTaxReason, date, true);
                salesTaxReason.setRequestCoreChargesSeq(requestCoreChargesId);
                dao.createSalesTaxReason(salesTaxReason);
            }
        }
    }

    /**
     * This method is to retrieve InvoicesAndAdjPay
     * @param requestCoreId
     * @return List<RequestCoreChargesInvoice>
     */
    public RequestCoreChargesInvoicesList retrieveInvoicesAndAdjPay(long requestCoreId) {

        final String logSM = "retrieveInvoicesAndAdjPay(requestCoreId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreId);
        }

        try {

            List<RequestCoreChargesInvoice> requestCoreChargesInvoicesList =
                                                    new ArrayList<RequestCoreChargesInvoice>();

            RequestCoreDeliveryDAO requestCoreDeliveryDAO =
                        (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

            List<RequestCoreDeliveryCharges>  requestCoreDeliveryChargesList =
                          requestCoreDeliveryDAO.retrieveRequestCoreDeliveryCharges(requestCoreId);

            RequestCoreChargesInvoice requestCoreChargesInvoice = null;
            if (requestCoreDeliveryChargesList != null) {

                Iterator<RequestCoreDeliveryCharges> it = requestCoreDeliveryChargesList.iterator();
                while (it.hasNext()) {

                    RequestCoreDeliveryCharges invoice = it.next();

                    requestCoreChargesInvoice = new RequestCoreChargesInvoice();

                    long invoiceId = invoice.getId();

                    requestCoreChargesInvoice.setRequestCoreDeliveryChargesId(invoiceId);
                    requestCoreChargesInvoice.setInvoiceCreatedDt(invoice.getCreatedDt());
                    requestCoreChargesInvoice.setPaymentAmount(invoice.getPaymentAmount());
                    requestCoreChargesInvoice.setInvoicedAmount(invoice.getInvoiceBalanceDue());

                    boolean isInvoice = ROIConstants.INVOICE.equalsIgnoreCase(invoice.getType());
                    requestCoreChargesInvoice.setIsInvoice(isInvoice);

                    //retrieves the adjustment payments for the given invoices
                    List<RequestCoreDeliveryChargesAdjustmentPayment> invoiceAdjPay =
                        requestCoreDeliveryDAO.retrieveRequestCoreDeliveryChargesAdjustmentPayment(
                                                                                        invoiceId);

                    Set<RequestCoreDeliveryChargesAdjustmentPayment> invoicesAdjPaySet =
                                        new HashSet<RequestCoreDeliveryChargesAdjustmentPayment>();
                    invoicesAdjPaySet.addAll(invoiceAdjPay);
                    requestCoreChargesInvoice.setRequestCoreDeliveryChargesAdjustmentPayment(
                                                                               invoicesAdjPaySet);

                    requestCoreChargesInvoicesList.add(requestCoreChargesInvoice);
                }
            }

            // determine if there are any invoices which are not attached to a release
            boolean isInvoiced =
                    requestCoreDeliveryDAO.isRequestContainsNonReleasedInvoices(requestCoreId);

            RequestCoreChargesInvoicesList invoicesList = new RequestCoreChargesInvoicesList();
            invoicesList.setInvoicesList(requestCoreChargesInvoicesList);
            invoicesList.setIsInvoiced(isInvoiced);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

            return invoicesList;
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in retrieveInvoicesAndAdjPay",e);
            throw new ROIException(e, ROIClientErrorCodes.VIEW_INVOICE_OPERATION_FAILED);
        }

    }
    /**
     * This method is used to set the default facility name and facility code in request Object
     * @param request
     * @return
     */
    private RequestCore setDefaultFacility(RequestCore request) {

        final String logSM = "setDefaultFacility(request)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + request);
        }

        try {

            String defaultFacility = null;
            String defaultFacilityCode = null;

            TaxPerFacilityDAO taxDao = (TaxPerFacilityDAO) getDAO(DAOName.TAXPERFACILITY_DAO);
            TaxPerFacility taxPerFacility = taxDao.getDefaultTaxPerFacility();

            if (taxPerFacility != null) {

                defaultFacility = taxPerFacility.getName().trim();
                defaultFacilityCode = taxPerFacility.getCode().trim();
            }

            request.setDefaultFacility(defaultFacility);
            request.setDefaultFacilityCode(defaultFacilityCode);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return request;
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in setDefaultFacility",e);
            throw new ROIException(e,
                    ROIClientErrorCodes.VIEW_INVOICE_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.request.service.RequestCoreService#retrieveComment(long)
     */
    @Override
    public Comments retrieveComments(long requestId) {

        final String logSM = "retrieveComments(requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start : " + requestId);
        }

        try {

            RequestEventCriteria criteria
                 = new RequestEventCriteria(requestId, RequestEvent.TYPE.COMMENT_ADDED);

            RequestCoreDAO dao = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
/*Note For Comment*/
            @SuppressWarnings("unchecked") // not supported by 3rd party API
            List<RequestEvent> eventHistory = (List<RequestEvent>) dao.getEventHistory(criteria);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End: No of Comments Fetched: " + eventHistory.size());
            }
            
            List<Comment> commentHistory = new ArrayList<>();
            
            for (RequestEvent reqObj : eventHistory) {
                
                Comment commentObj = new Comment();

                BeanUtils.copyProperties(commentObj, reqObj);
                commentObj.setCreatedDate(reqObj.getModifiedDate());
                commentHistory.add(commentObj);
            }

            return new Comments(commentHistory);
            
        } catch (Throwable e) {
            LOG.error("Exception occured in retrieveComments",e);
            throw new ROIException(ROIClientErrorCodes.SEARCH_REQUEST_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.request.service.RequestCoreService#getEventHistory(long)
     */
    @Override
    public RequestEvents getEventHistory(long requestId) {

        final String logSM = "getEventHistory(requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start : " + requestId);
        }

        try {

            RequestEventCriteria criteria = new RequestEventCriteria(requestId,
                                                                     RequestEvent.TYPE.NA);

            RequestCoreDAO dao = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
            @SuppressWarnings("unchecked") // not supported by 3rd party API
            List<RequestEvent> eventHistory = (List<RequestEvent>) dao.getEventHistory(criteria);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End: No of Events Fetched: " + eventHistory.size());
            }

            return new RequestEvents(eventHistory);

        } catch (Throwable e) {

            LOG.error("Exception occured in getEventHistory",e);
            throw new ROIException(ROIClientErrorCodes.SEARCH_REQUEST_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.request.service.RequestCoreService#retrieveAllEventTypes()
     */
    @Override
     public EventTypes retrieveAllEventTypes() {

         final String logSM = "retrieveAllEventTypes()";
         if (DO_DEBUG) {
             LOG.debug(logSM + ">>Start:");
         }

         List<String> eventTypes = RequestEvent.getEventTypes();

         if (DO_DEBUG) {
             LOG.debug(logSM + "<<End: No. of EventTypes = " + eventTypes.size());
         }
         return new EventTypes(eventTypes);
     }

    /**
     *
     * @see com.mckesson.eig.roi.request.service.RequestCoreService#getRecordCount
     * (com.mckesson.eig.roi.base.model.SearchLoVCriteria)
     */
    @Override
    public long getRequestCount(RequestCoreSearchCriteria criteria) {

        try {

            final String logSM = "getRequestCount(criteria)";
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>Start:");
            }
            RequestServiceValidator validator = new RequestServiceValidator();
            if (!validator.validateSearchCriteria(criteria)) {
                throw validator.getException();
            }

            RequestCoreDAO dao = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
            long count = dao.getRequestCount(criteria);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Request Count = " + count);
            }
            return count;
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in getRequestCount",e);
            throw new ROIException(ROIClientErrorCodes.GET_REQUEST_COUNT_OPERATION_FAILED);
        }
    }


    /**
     * @see com.mckesson.eig.roi.request.service.RequestCoreService
     * #retrieveReleasedDocumentChargesByBillingTier(long)
     */
    @Override
    public List<RequestCoreChargesDocument> retrieveReleasedDocumentChargesByBillingTier(
                                                                                  long requestId) {

        final String logSM = "retrieveReleasedDocumentChargesByBillingTier(requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {

            if (requestId <= 0) {
                throw new ROIException(ROIClientErrorCodes.INVALID_REQUEST_ID);
            }

            RequestCoreDAO dao = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
            List<RequestCoreChargesDocument> documentCharges =
                                                        dao.retrieveAllDocumentCharges(requestId);

            documentCharges = groupDocumentChargesByBillingTier(documentCharges);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return documentCharges;

        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in retrieveReleasedDocumentChargesByBillingTier",e);
            throw new ROIException(
                        ROIClientErrorCodes.RETRIEVE_DOCUMENT_CHARGES_BY_BILLING_TIER_FAILED);
        }
    }

    /**
     * groups the list of document charges by billing tier
     * the total pages corresponding for the billing tier is calculated and
     * the assigned to the latest document charges
     *
     * @param documentCharges
     * @return list of document charges
     */
    private List<RequestCoreChargesDocument> groupDocumentChargesByBillingTier(
                                            List<RequestCoreChargesDocument> documentCharges) {

        List<RequestCoreChargesDocument> documents = new ArrayList<RequestCoreChargesDocument>();
        if (CollectionUtilities.isEmpty(documentCharges)) {
            return documents;
        }

        Map<String, RequestCoreChargesDocument> map =
                                            new HashMap<String, RequestCoreChargesDocument>();
        for (RequestCoreChargesDocument document : documentCharges) {

            String billingtierId = document.getBillingtierId();
            if (map.containsKey(billingtierId)) {

                RequestCoreChargesDocument documentChrg = map.get(billingtierId);
                int totalPage = documentChrg.getPages() + document.getPages();

                // latest document charges is added to the map,
                // hence if the previous document charge is latest, it would be added to map
                if (documentChrg.getId() > document.getId()) {
                    document = documentChrg;
                }
                document.setTotalPages(totalPage);
            } else {
                document.setTotalPages(document.getPages());
            }

            map.put(billingtierId, document);
        }

        Collection<RequestCoreChargesDocument> values = map.values();
        documents.addAll(values);

        return documents;
    }

    /**
    *
    * @see com.mckesson.eig.roi.request.service.RequestService#addEvent
    * (com.mckesson.eig.roi.request.model.RequestEvent)
    */
    @Override
   public RequestEvent addEvent(RequestEvent event) {

       final String logSM = "addEvent(event)";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start: Event = " + event.toString());
       }

       try {

           RequestServiceValidator validator = new RequestServiceValidator();
           if (!validator.validRequestEventFields(event)) {
               throw validator.getException();
           }
           RequestCoreDAO dao = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
           Timestamp date = dao.getDate();
           setDefaultDetails(event, date, true);
           dao.createRequestEvent(event);

           if (DO_DEBUG) {
               LOG.debug(logSM + "<<End: Event Added");
           }
           return event;
       } catch (ROIException e) {
           throw e;
       } catch (Throwable e) {
           LOG.error("Exception occured in addEvent",e);
           throw new ROIException(ROIClientErrorCodes.REQUEST_OPERATION_FAILED);
       }
   }

    /**
     * @see com.mckesson.eig.roi.request.service.RequestService#getGeneratedPassword
     */
    public String getGeneratedPassword() throws Exception {

        SysParamDAO dao = (SysParamDAO) getDAO(DAOName.SYSPARAM_DAO);
        List<SysParam> sysParams = dao.retrieveROISysParams();

        PassPhrase.TYPE passwordType = null;
        int passwordLength = 0;

        for (SysParam sysParam : sysParams) {

            if (ROIConstants.KEY_SYSPARAM_REQUEST_PD_TYPE
                            .equals(sysParam.getGlobalName().trim())) {

                if (ROIConstants.KEY_PD.equals(sysParam.getGlobalVariant())) {
                    passwordType = PassPhrase.TYPE.PASSWORD;
                }
                continue;
            }

            if (ROIConstants.KEY_SYSPARAM_REQUEST_PD_LENGTH
                            .equals(sysParam.getGlobalName().trim())) {

                passwordLength = Integer.parseInt(sysParam.getGlobalVariant());
                continue;
            }
        }

        String plainPwd = PassPhrase.generate(passwordType, passwordLength);
        return getEncryptor().encrypt(plainPwd, ROIConstants.AES_SECRET_KEY, ROIConstants.AES_SECRET_IV);
    }


    /**
     * @see com.mckesson.eig.roi.request.service.RequestService
     * #addAuditAndEvent(com.mckesson.eig.roi.request.model.AuditAndEventList)
     */
    public void addAuditAndEvent(AuditAndEventList auditAndEventList) {

        final String logSM = "addAuditAndEvent(reqEvent, auditEvent)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: Event = ");
        }

        if (null == auditAndEventList) {
            return;
        }

        try {

            List<AuditEvent> auditEvents = auditAndEventList.getAuditEvent();
            List<RequestEvent> requestEvents = auditAndEventList.getRequestEvent();

            if (null != auditEvents && !auditEvents.isEmpty()) {

                ROIAuditManager auditMgr = getROIAuditManager();

                for (AuditEvent audit : auditEvents) {
                    auditMgr.createAuditEntry(audit);
                }
            }

            if (null != requestEvents && !requestEvents.isEmpty()) {

                RequestServiceValidator validator = new RequestServiceValidator();
                RequestCoreDAO dao = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
                Timestamp date = dao.getDate();

                List<RequestEvent> reqEvents = new ArrayList<RequestEvent>();
                for (RequestEvent reqEvent : requestEvents) {

                    validateRequestEvent(validator, reqEvent);
                    setDefaultDetails(reqEvent, date, true);
                    reqEvents.add(reqEvent);
                }

                // creates the list of events
                dao.createAllRequestEvent(reqEvents);
            }
        } catch (Exception ex) {
            throw new ROIException(ROIClientErrorCodes.CREATE_AUDIT_AND_REQUEST_EVENT_FAILED);
        }

    }

    /**
     * @param validator
     * @param date
     * @param reqEvent
     */
    private void validateRequestEvent(RequestServiceValidator validator,
                                      RequestEvent reqEvent) {

        if (!validator.validRequestEventFields(reqEvent)) {
            throw validator.getException();
        }
    }

    /**
     * Method to get the CcdProviderFactory instance
     */
     private CcdProviderFactory getCcdProviderFactory() {
             return (CcdProviderFactory) SpringUtil
                        .getObjectFromSpring("ccdProviderFactory");
     }

     /**
      * Method to get the Dao information
      *
      * @return CcdProviderDAOImpl
      */
      private CcdProviderDAO getCcdProviderDao() {
              return (CcdProviderDAO) SpringUtil
                         .getObjectFromSpring("CcdProviderDAO");
      }

      /**
       * Method to get the instance for MUCreateROIOutboundServiceImpl
       */
       private MUCreateROIOutboundServiceCore getMUCreateROIOutboundServiceCore() {
               return (MUCreateROIOutboundServiceCore) SpringUtil
                                                .getObjectFromSpring(ROI_MU_OUTBOUND_SERVICE);
       }

       /**
        * Retrieves the list of all invoices for the given request Id
        * @param requestId
        * @return
        */
       @Override
       public RequestorInvoicesList retrieveRequestInvoices(long requestId) {

           final String logSM = "retrieveRequestInvoices(requestId)";

           if (DO_DEBUG) {
               LOG.debug(logSM + ">>Start: RequestId:" + requestId);
           }

           if (requestId <= 0) {
               throw new ROIException(ROIClientErrorCodes.INVALID_REQUEST_ID,
                                      String.valueOf(requestId));
           }

           try {

               RequestCoreDAO dao = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
               RequestorInvoicesList invoiceDetails = dao.retrieveRequestInvoiceDetails(requestId);

               if (DO_DEBUG) {
                   LOG.debug(logSM + "<<End: Size of retrieved invoices:" + requestId);
               }

               return invoiceDetails;
           } catch (ROIException e) {
               throw e;
           } catch (Exception ex) {
               throw new ROIException(ROIClientErrorCodes.FAILED_TO_RETRIEVE_REQUEST_INVOICES);
           }
       }

       /**
        * Method to retrieve ProductivityReportDetails
        *
        * @param fromDate
        * @param toDate
        * @param muDocName
        * @param facility
        * @param resultType
        * @return list
        */
       public List<Object[]> retrieveProductivityReportDetails( String[] facility, List<String> userName,
               String[] requestorType,Date fromDate,Date toDate,String resultType) {
          final String logSM = "retrieveProductivityReportDetails()";
          if (DO_DEBUG) {
              LOG.debug(logSM + ">>Start:" + facility + userName + requestorType
                      + fromDate + toDate);
          }
          try
          {
          List<Object[]> results = new ArrayList<Object[]>();
          int flag = 0;
          RequestCoreDAO dao = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);

          List<ProductivityReportDetails> rptList = dao
                  .retriveProductivityReportDetails(facility,userName,
                          requestorType,fromDate, toDate, resultType);
          if (rptList != null && rptList.size() > 0){
          for(int i = 0; i < rptList.size(); i++){
              Object[] reportArray = {
                      rptList.get(i).getFacility().trim(),
                      rptList.get(i).getUserName(),
                      rptList.get(i).getRequestorType(),
                      rptList.get(i).getRequestorName(),
                      rptList.get(i).getPatientName().trim(),
                      rptList.get(i).getReqID(),
                      rptList.get(i).getPages(),
                      rptList.get(i).getMrn().trim(),
                      rptList.get(i).getBillable(),
                      rptList.get(i).getCreateDate(),
                      rptList.get(i).getPageType(),
                      rptList.get(i).getReqIDCount()};

              results.add(flag, reportArray);
              flag++;
          }
          }
          if (DO_DEBUG) {
              LOG.debug(logSM + "<<End:");
          }

          return results;

      }catch (ROIException e) {
          throw e;
      }
     }
       /**
        * This method validates whether the user has security rights to release a document
        * @return boolean
        */
       public boolean hasSecurityRightsForRelease()
       {
        BillingAdminServiceValidator validator = new BillingAdminServiceValidator();
        RequestCoreDAO dao = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
        // to check whether the user has security rights to release or not by
        // any output method
        boolean rightsFlag = validator
                .validateSecurityRightsForOutputMethod(getUser().getLoginId(),
                        dao);
        return rightsFlag;
    }
}
