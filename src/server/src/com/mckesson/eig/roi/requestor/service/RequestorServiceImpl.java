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

package com.mckesson.eig.roi.requestor.service;


import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mckesson.eig.roi.admin.dao.ReasonDAO;
import com.mckesson.eig.roi.admin.dao.RequestorTypeDAO;
import com.mckesson.eig.roi.admin.model.LetterTemplateDocument;
import com.mckesson.eig.roi.admin.model.ReasonsList;
import com.mckesson.eig.roi.admin.model.RequestorType;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.LetterDataRetriever;
import com.mckesson.eig.roi.base.model.Address;
import com.mckesson.eig.roi.base.model.MatchCriteriaList;
import com.mckesson.eig.roi.billing.dao.OverDueInvoiceCoreDAO;
import com.mckesson.eig.roi.billing.dao.RequestCoreDeliveryDAO;
import com.mckesson.eig.roi.billing.letter.model.LetterData;
import com.mckesson.eig.roi.billing.model.DocInfo;
import com.mckesson.eig.roi.billing.model.DocInfoList;
import com.mckesson.eig.roi.billing.model.PastInvoice;
import com.mckesson.eig.roi.billing.model.PastInvoiceList;
import com.mckesson.eig.roi.billing.model.RegeneratedInvoiceInfo;
import com.mckesson.eig.roi.billing.model.RequestorLetter;
import com.mckesson.eig.roi.billing.model.RequestorLetterHistory;
import com.mckesson.eig.roi.billing.model.RequestorLetterHistoryList;
import com.mckesson.eig.roi.billing.service.AbstractBillingService;
import com.mckesson.eig.roi.billing.service.BillingCoreServiceValidator;
import com.mckesson.eig.roi.billing.service.PdfUtilities;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.journal.service.JournalService;
import com.mckesson.eig.roi.request.dao.RequestCoreDAO;
import com.mckesson.eig.roi.request.model.RequestEvent;
import com.mckesson.eig.roi.request.model.RequestEvent.TYPE;
import com.mckesson.eig.roi.requestor.dao.RequestorDAO;
import com.mckesson.eig.roi.requestor.dao.RequestorStatementDAO;
import com.mckesson.eig.roi.requestor.model.AdjustmentInfo;
import com.mckesson.eig.roi.requestor.model.AdjustmentType;
import com.mckesson.eig.roi.requestor.model.ContactEmailPhone;
import com.mckesson.eig.roi.requestor.model.RefundLetter;
import com.mckesson.eig.roi.requestor.model.RelatedAddress;
import com.mckesson.eig.roi.requestor.model.RelatedContact;
import com.mckesson.eig.roi.requestor.model.RelatedEmailPhone;
import com.mckesson.eig.roi.requestor.model.Requestor;
import com.mckesson.eig.roi.requestor.model.RequestorAdjustment;
import com.mckesson.eig.roi.requestor.model.RequestorDetail;
import com.mckesson.eig.roi.requestor.model.RequestorHistoryList;
import com.mckesson.eig.roi.requestor.model.RequestorInvoice;
import com.mckesson.eig.roi.requestor.model.RequestorInvoicesList;
import com.mckesson.eig.roi.requestor.model.RequestorPayment;
import com.mckesson.eig.roi.requestor.model.RequestorPaymentList;
import com.mckesson.eig.roi.requestor.model.RequestorRefund;
import com.mckesson.eig.roi.requestor.model.RequestorSearchCriteria;
import com.mckesson.eig.roi.requestor.model.RequestorSearchResult;
import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria;
import com.mckesson.eig.roi.requestor.model.RequestorStatementInfo;
import com.mckesson.eig.roi.requestor.model.RequestorUnappliedAmountDetailsList;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 * @author OFS
 * @date   Nov 03, 2008
 * @since  HPF 13.1 [ROI]; Jun 17, 2008
 */
public class RequestorServiceImpl
extends AbstractBillingService
implements RequestorService {

    /**
     * Object represents the Log4JWrapper object.
     */
    private static final Log LOG = LogFactory.getLogger(RequestorServiceImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final String FILENAME_DATE_FORMAT = "yyyy.MM.dd.hh.mm.ss.SSS";


   /**
    *
    * @see com.mckesson.eig.roi.requestor.service.RequestorService
    * #checkDuplicateRequestorName(long, java.lang.String)
    */
   @Override
   public boolean checkDuplicateRequestorName(long id, String name) {

       final String logSM = "checkDuplicateRequestorName(id, name)";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start:RequestorId: " + id + "Name:" + name);
       }

       try {

           RequestorDAO dao = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
           RequestorServiceValidator validator = new RequestorServiceValidator();
           if (!validator.validateName(name)) {
               throw validator.getException();
           }

           List<Long> requestorIds = dao.retrieveRequestorIdsByName(name);
           boolean hasDuplicate = (requestorIds.size() > 0);
           if (id > 0) { // if name is not changed while update
               hasDuplicate = hasDuplicate && !requestorIds.contains(id);
           }

           if (DO_DEBUG) {
               LOG.debug(logSM + "<<End: Is Duplicate : " + hasDuplicate);
           }
           return hasDuplicate;
       } catch (ROIException e) {
           throw e;
       } catch (Throwable e) {
           LOG.error(logSM + "Error :" + e);
           throw new ROIException(ROIClientErrorCodes.REQUESTOR_OPERATION_FAILED);
       }
   }

    /**
     *
     * @see com.mckesson.eig.roi.requestor.service.RequestorService
     * #createRequestor(com.mckesson.eig.roi.requestor.model.Requestor)
     */
    @Override
    public long createRequestor(Requestor requestor) {

        final String logSM = "createRequestor(requestor)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestor);
        }

        try {

            RequestorDAO dao = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
            RequestorServiceValidator validator = new RequestorServiceValidator();
            User user = getUser();

            if (!validator.validateRequestor(requestor, dao, true, user)) {
                throw validator.getException();
            }

            Timestamp date = dao.getDate();
            mapRequestorModel(true, requestor, null, dao, date);

            long id = dao.createRequestor(requestor);

            RequestorTypeDAO reqTypeDao = (RequestorTypeDAO) getDAO(DAOName.REQUESTOR_TYPE_DAO);
            RequestorType rt = reqTypeDao.retrieveRequestorType(requestor.getType());

            audit(requestor.toCreateAudit(user.getFullName(), rt.getName()),
                  user.getInstanceId(),
                  date);

            //For Audit - Testing
            if (null != requestor.getMainAddress()
                    && requestor.getMainAddress().isNewCountry()) {

                String remark = new StringBuffer()
                                        .append("Country Code-Country set to ")
                                        .append(requestor.getMainAddress().getCountryCode())
                                        .append(" - ")
                                        .append(requestor.getMainAddress().getCountryName())
                                        .toString();

                audit(remark, user.getInstanceId(), date);
            }

            if (null != requestor.getAltAddress()
                    && requestor.getAltAddress().isNewCountry()) {

                String remark = new StringBuffer()
                                        .append("Country Code-Country set to ")
                                        .append(requestor.getAltAddress().getCountryCode())
                                        .append(" - ")
                                        .append(requestor.getAltAddress().getCountryName())
                                        .toString();

                audit(remark, user.getInstanceId(), date);
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Created RequestorId :" + id);
            }
            return id;
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error(logSM + "Error :" + e);
            throw new ROIException(ROIClientErrorCodes.REQUESTOR_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.requestor.service.RequestorService#retrieveRequestor(long)
     */
    @Override
    public Requestor retrieveRequestor(long requestorId, boolean isSearchRetrieve) {

        final String logSM = "retrieveRequestor(requestorId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: Requestor Id: " + requestorId);
        }

        RequestorDAO dao = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
        Requestor retrivedRequestor = dao.retrieveRequestor(requestorId);

        mapRetrievedRequestorModel(retrivedRequestor, dao);
        boolean associated = (dao.getAssociatedRequestCount(requestorId) > 0)
                                    || (dao.retrieveRequestorAdjPaymentCount(requestorId) > 0);
        retrivedRequestor.setAssociated(associated);

        // MU2 Use case - Audit message
        if (isSearchRetrieve) {
            auditRequest(retrivedRequestor.constructSearchRetrieveAudit(),
                         getUser().getInstanceId(),
                         dao.getDate(),
                         ROIConstants.REQUEST_AUDIT_ACTION_CODE_VIEW);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:Retrieved Requestor : " + retrivedRequestor);
        }
        return retrivedRequestor;
    }

    /**
     *
     * @see com.mckesson.eig.roi.requestor.service.RequestorService
     * #updateRequestor(com.mckesson.eig.roi.requestor.model.Requestor)
     */
    @Override
    public Requestor updateRequestor(Requestor requestor) {

        final String logSM = "updateRequestor(requestor)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Requestor to be updated: " + requestor);
        }

        try {

            RequestorDAO dao = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
            RequestorServiceValidator validator = new RequestorServiceValidator();

            Requestor old = retrieveRequestor(requestor.getId(), false);
            User user = getUser();

            if (!validator.validateRequestor(requestor, old, dao, false, user)) {
                throw validator.getException();
            }

            Timestamp date = dao.getDate();
            String comments = requestor.toUpdateAudit(user.getFullName(),
                                                      old,
                                                      old.getRequestorType());

            mapRequestorModel(false, requestor, old, dao, date);
            requestor.setCreatedBy(old.getCreatedBy());
            Requestor updatedRequestor = dao.updateRequestor(requestor);

            audit(comments, user.getInstanceId(), date);
            mapRetrievedRequestorModel(updatedRequestor, dao);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Updated Requestor :" + updatedRequestor);
            }
            return updatedRequestor;
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error(logSM + "Error :" + e);
            throw new ROIException(ROIClientErrorCodes.REQUESTOR_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.requestor.service.RequestorService
     * #deleteRequestor(long)
     */
    @Override
    public void deleteRequestor(long requestorId) {

        final String logSM = "deleteRequestor(requestorId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: RequestorId : " + requestorId);
        }

        try {

            RequestorDAO dao = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);

            RequestorServiceValidator validator = new RequestorServiceValidator();
            User user = getUser();

            if (!validator.validateRequestorDeletion(requestorId, dao)) {
                throw validator.getException();
            }

            Requestor req = dao.deleteRequestor(requestorId);
            Timestamp date = dao.getDate();
            audit(req.toDeleteAudit(user.getFullName(), req.getRequestorType()),
                  user.getInstanceId(),
                  date);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End: Deleted RequestorId : " + requestorId);
            }
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error(logSM + "Error :" + e);
            throw new ROIException(ROIClientErrorCodes.REQUESTOR_OPERATION_FAILED);
        }
    }

    /**
     * This is the service method to search requestor based on the searchCriteria
     * @see com.mckesson.eig.roi.requestor.service.RequestorService
     * #findRequestor(com.mckesson.eig.roi.requestor.model.RequestorSearchCriteria)
     */
    @Override
    public RequestorSearchResult findRequestor(RequestorSearchCriteria searchCriteria) {

        final String logSM = "findRequestor(searchCriteria)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + searchCriteria);
        }

        try {

            RequestorDAO dao = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
            RequestorTypeDAO reqTypeDao = (RequestorTypeDAO) getDAO(DAOName.REQUESTOR_TYPE_DAO);

            RequestorServiceValidator validator = new RequestorServiceValidator();

            if (!validator.validateSearchCriteria(searchCriteria, reqTypeDao, getUser())) {
                throw validator.getException();
            }

            Timestamp date = dao.getDate();
            searchCriteria.normalize(date);
            
            // retrieves the patietn requestor type and assign the requestor typeId to the serach criteria model
            RequestorType rt =  reqTypeDao.getRequestorTypeByName(ROIConstants.REQUESTOR_TYPE_PATIENT);
            searchCriteria.setPatientRequestorTypeId(rt.getId());

            List<Requestor> requestors = dao.findRequestor(searchCriteria);

            RequestorSearchResult searchResult = new RequestorSearchResult();
            searchResult.setMaxCountExceeded(requestors.size() > searchCriteria.getMaxCount());
            searchResult.setRequestorType(searchCriteria.getType());
            searchResult.setSearchResults(requestors);

            String comment = new StringBuffer("Search Performed in \"ROI\". ")
                                .append("Type of Search:- Requestor. ")
                                .append("Search Criteria entered:- ")
                                .append(searchCriteria.constructSearchAuditComment())
                                .toString();

            //It will create the search requestor audit information in Audit_Trail table.
            doAudit(comment,
                    getUser().getInstanceId(),
                    dao.getDate(),
                    ROIConstants.AUDIT_ACTION_CODE_SEARCH,
                    searchCriteria.getFacility() == null ? ROIConstants.DEFAULT_FACILITY
                            : searchCriteria.getFacility().trim(),
                    searchCriteria.getMrn(),
                    searchCriteria.getEpn());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End: No.of Records: " + searchResult.getSearchResults()
                                                                         .size());
            }
            return searchResult;
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error(logSM + "Error :" + e);
            throw new ROIException(e, ROIClientErrorCodes.REQUESTOR_SEARCH_OPERATION_FAILED);
        }
    }

    /**
     * This method retrieves matching requestors based on MatchCriteria list
     * @param mcl MatchCriteriaList contains a list of MatchCriteria
     * @return RequestorSearchResult
     */
    @Override
    public RequestorSearchResult searchMatchingRequestors(MatchCriteriaList mcl) {

        final String logSM = "searchMatchingRequestors(mcl)";

        try {

            RequestorServiceValidator validator = new RequestorServiceValidator();
            //User user = getUser();

            if (!validator.validateMatchCriteriaList(mcl)) {
                throw validator.getException();
            }

            RequestorDAO dao    = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
            RequestorTypeDAO rt = (RequestorTypeDAO) getDAO(DAOName.REQUESTOR_TYPE_DAO);

            List<Requestor> list = dao.getMatchingRequestors(mcl, rt);
            ArrayList<Requestor> matchList = new ArrayList<Requestor>(list.size());
            for (Requestor req : list) {

                mapRetrievedRequestorModel(req, dao);
                matchList.add(req);
                // Removed comparision of atleast 2 matching
//                if (mcl.contains(req)) {ddd
//                    mapRetrievedRequestorModel(req, dao);
//                    matchList.add(req);
//                }
            }

            RequestorSearchResult res = new RequestorSearchResult();
            res.setSearchResults(matchList);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Input list" + list.size()
                          + " Matched List:" + matchList.size());
            }

            return res;

        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error(logSM + "Error :" + e);
            throw new ROIException(ROIClientErrorCodes.REQUESTOR_OPERATION_FAILED);
        }
    }

    /**
     * This method lists the summary letters for a requestor.
     * @param requestorId
     * @return RequestorLetterHistoryList
     */
    @Override
    public RequestorLetterHistoryList retrieveRequestorLetterHistory(long requestorId) {

        final String logSM = "retrieveRequestorLetterHistory(requestorId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        // validates the requestorId
        RequestorServiceValidator requestorServicevalidator = new RequestorServiceValidator();
        if (!requestorServicevalidator.validateRequestorId(requestorId)) {
            throw requestorServicevalidator.getException();
        }

        RequestorDAO requestorDAO  = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);

        List<RequestorLetterHistory> overDueList = null;

        try {
            overDueList = requestorDAO.retrieveRequestorLetterHistory(requestorId);
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error(logSM + "Error :" + e);
            throw new ROIException(e, ROIClientErrorCodes.REQUESTOR_OPERATION_FAILED);
        }

        RequestorLetterHistoryList requestorHistoryList = null;

        if (overDueList != null) {

            requestorHistoryList = new RequestorLetterHistoryList();
            requestorHistoryList.setRequestorLetterHistoryList(overDueList);
        }

        return requestorHistoryList;
    }

    /**
     * This service can be used in case of viewing requestor letter history
     *
     * @param requestorLetterId
     *            invoice id
     * @param docType
     * @return DocInfo details
     */
    @Override
    public DocInfo viewRequestorLetter(long requestorLetterId, String docType) {

        final String logSM = "viewRequestorLetter(requestorLetterId, docType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>invoice Letter Id : " + requestorLetterId);
        }

        try {

            RequestorDAO requestorDAO  = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
            RequestorServiceValidator requestorServicevalidator = new RequestorServiceValidator();

            if (!requestorServicevalidator.validateRequestorLetterId(requestorLetterId)) {
                throw requestorServicevalidator.getException();
            }

            docType = StringUtilities.isEmpty(docType) ? ROIConstants.TEMPLATE_FILE_TYPE : docType;
            String fileName = generateInvoiceLetter(requestorLetterId,
                                                    docType,
                                                    ROIConstants.REQUESTORLETTER,
                                                    requestorDAO.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Invoice Letter File Name : " + fileName);
            }
            PdfUtilities.encrypt(getCacheFileName(fileName));
            return new DocInfo(requestorLetterId, fileName,
                                    ROIConstants.REQUESTOR_LETTER_FILE);
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {

            LOG.error(e);
            throw new ROIException(e,
                    ROIClientErrorCodes.GENERATE_LETTER_OPERATION_FAILED);
        }
    }

    /**
     * This is a model mapper method to map the retrieved Requestor model
     * @param requestor requestor details
     * @param dao RequestorDAO details
     */
    private void mapRetrievedRequestorModel(Requestor requestor, RequestorDAO dao) {

        long mainAddressId = dao.getAddressTypeId(ROIConstants.MAIN_ADDR_TYPE);
        requestor.setMainAddress(requestor.getAddressOfType(mainAddressId));

        long altAddressId = dao.getAddressTypeId(ROIConstants.ALT_ADDR_TYPE);
        requestor.setAltAddress(requestor.getAddressOfType(altAddressId));

        long homePhoneId = dao.getEmailPhoneTypeId(ROIConstants.HM_PHONE);
        requestor.setHomePhone(requestor.getEmailPhoneOfType(homePhoneId));

        long workPhoneId = dao.getEmailPhoneTypeId(ROIConstants.WK_PHONE);
        requestor.setWorkPhone(requestor.getEmailPhoneOfType(workPhoneId));

        long cellPhoneId = dao.getEmailPhoneTypeId(ROIConstants.CELL_PHONE);
        requestor.setCellPhone(requestor.getEmailPhoneOfType(cellPhoneId));

        long primaryEmailId = dao.getEmailPhoneTypeId(ROIConstants.PRI_EMAIL);
        requestor.setEmail(requestor.getEmailPhoneOfType(primaryEmailId));

        long faxId = dao.getEmailPhoneTypeId(ROIConstants.FAX_TYPE);
        requestor.setFax(requestor.getEmailPhoneOfType(faxId));

        long defaultContactId = dao.getContactTypeId(ROIConstants.DEFAULT_CONTACT);
        requestor.setContactName(requestor.getContactOfType(defaultContactId));

        requestor.setContactPhone(requestor.getContactEmailPhoneOfType(defaultContactId,
                                                                       homePhoneId));

        requestor.setContactEmail(requestor.getContactEmailPhoneOfType(defaultContactId,
                                                                       primaryEmailId));

        requestor.setDob(requestor.getDateOfBirth(), ROIConstants.ROI_DATE_FORMAT);
        requestor.setFreeFormFacility(Boolean.valueOf(requestor.getFreeFormFacilityExists()));
    }

    private void mapRequestorModel(boolean isNew,
                                   Requestor neu,
                                   Requestor old,
                                   RequestorDAO dao,
                                   Timestamp date) {

        mapAddress(isNew, neu, old, neu.getMainAddress(), ROIConstants.MAIN_ADDR_TYPE, dao, date);
        mapAddress(isNew, neu, old,  neu.getAltAddress(), ROIConstants.ALT_ADDR_TYPE,  dao, date);

        mapEmailPhone(isNew, neu, old, neu.getHomePhone(), ROIConstants.HM_PHONE,   dao, date);
        mapEmailPhone(isNew, neu, old, neu.getWorkPhone(), ROIConstants.WK_PHONE,   dao, date);
        mapEmailPhone(isNew, neu, old, neu.getCellPhone(), ROIConstants.CELL_PHONE, dao, date);
        mapEmailPhone(isNew, neu, old, neu.getEmail(),     ROIConstants.PRI_EMAIL,  dao, date);
        mapEmailPhone(isNew, neu, old, neu.getFax(),       ROIConstants.FAX_TYPE,   dao, date);

        mapContact(isNew, neu, old, neu.getContactName(),  ROIConstants.DEFAULT_CONTACT, dao, date);
        mapContactEPhone(isNew, neu, old, neu.getContactPhone(), ROIConstants.HM_PHONE,  dao, date);
        mapContactEPhone(isNew, neu, old, neu.getContactEmail(), ROIConstants.PRI_EMAIL, dao, date);

        mapRequestorDetail(isNew, neu, old, neu.getEpn(), ROIConstants.EPN_TYPE, dao, date);
        mapRequestorDetail(isNew, neu, old, neu.getSsn(), ROIConstants.SSN_TYPE, dao, date);

        mapRequestorDetail(isNew, neu, old, neu.getFacility(), ROIConstants.FAC_CODE, dao, date);

        mapRequestorDetail(isNew, neu, old, String.valueOf(neu.isFreeFormFacility()),
                                            ROIConstants.IS_FREEE_FORM_FAC, dao, date);

        neu.setFreeFormFacilityExists(String.valueOf(neu.isFreeFormFacility()));
        mapRequestorDetail(isNew, neu, old, neu.getMrn(), ROIConstants.MRN_TYPE, dao, date);

        neu.formatDateOfBirth(ROIConstants.ROI_DATE_FORMAT);
        mapRequestorDetail(isNew, neu, old, neu.getDateOfBirth(), ROIConstants.DOB_TYPE, dao, date);

        setDefaultDetails(neu, isNew, date);
    }

    private void mapAddress(boolean isNew,
                            Requestor neu,
                            Requestor old,
                            Address address,
                            String type,
                            RequestorDAO dao,
                            Timestamp date) {

        long addressTypeId = dao.getAddressTypeId(type);

        // If the new value is null, no need to map and the old value will be removed
        if (address == null) {
            return;
        }

        boolean noOldValue = (old == null) || (old.getAddressOfType(addressTypeId) == null);
        boolean createNew = (isNew || noOldValue);

        RelatedAddress relAddress = (createNew) ? new RelatedAddress(addressTypeId)
                                                : old.getRelatedAddressOfType(addressTypeId);

        relAddress.getAddress().setAddress1(address.getAddress1());
        relAddress.getAddress().setAddress2(address.getAddress2());
        relAddress.getAddress().setAddress3(address.getAddress3());
        relAddress.getAddress().setCity(address.getCity());
        relAddress.getAddress().setState(address.getState());
        relAddress.getAddress().setPostalCode(address.getPostalCode());
        relAddress.getAddress().setCountryCode(address.getCountryCode());
        relAddress.getAddress().setCountryName(address.getCountryName());
        relAddress.getAddress().setNewCountry(address.isNewCountry());
        relAddress.getAddress().setCountrySeq(address.getCountrySeq());

        User user = getUser();
        setDefaultDetails(relAddress, createNew, user, date);
        neu.getRelatedAddress().add(relAddress);
    }

    private void mapEmailPhone(boolean isNew,
                               Requestor neu,
                               Requestor old,
                               String ePhone,
                               String type,
                               RequestorDAO dao,
                               Timestamp date) {

        long ePhoneTypeId = dao.getEmailPhoneTypeId(type);
        User user = getUser();

        // If the new value is null, no need to map and the old value will be removed
        if (StringUtilities.isEmpty(ePhone)) {
            return;
        }

        boolean noOldValue = (old == null) || (old.getEmailPhoneOfType(ePhoneTypeId) == null);
        boolean createNew = (isNew || noOldValue);

        RelatedEmailPhone relEphone = (createNew) ? new RelatedEmailPhone(ePhoneTypeId)
                                                  : old.getRelatedEmailPhoneOfType(ePhoneTypeId);

        relEphone.getEmailPhone().setEmailPhone(ePhone);
        setDefaultDetails(relEphone, createNew, user, date);
        neu.getRelatedEmailPhones().add(relEphone);
    }

    private void mapContact(boolean isNew,
                            Requestor neu,
                            Requestor old,
                            String contactName,
                            String type,
                            RequestorDAO dao,
                            Timestamp date) {

        long contactTypeId = dao.getContactTypeId(type);
        User user = getUser();

        // If the new value is null, no need to map and the old value will be removed
        if (!neu.shouldMapContact()) {
            return;
        }

        boolean noOldValue = (old == null) || (old.getRelatedContactOfType(contactTypeId) == null);
        boolean createNew = (isNew || noOldValue);

        RelatedContact relContact = (createNew) ? new RelatedContact(contactTypeId)
                                                : old.getRelatedContactOfType(contactTypeId);

        relContact.getContact().setLastName(contactName);
        setDefaultDetails(relContact, createNew, user, date);
        neu.getRelatedContacts().add(relContact);
    }

    private void mapContactEPhone(boolean isNew,
                                  Requestor neu,
                                  Requestor old,
                                  String contactEPhone,
                                  String type,
                                  RequestorDAO dao,
                                  Timestamp date) {

        long ePhoneTypeId = dao.getEmailPhoneTypeId(type);
        long contactId = dao.getContactTypeId(ROIConstants.DEFAULT_CONTACT);
        User user = getUser();

        // If the new value is null, no need to map and the old value will be removed
        if (StringUtilities.isEmpty(contactEPhone)) {
            return;
        }

        boolean noOldValue = (old == null)
                              || (old.getContactEmailPhoneOfType(contactId, ePhoneTypeId) == null);

        boolean createNew = (isNew || noOldValue);

        ContactEmailPhone cep = (createNew) ? new ContactEmailPhone(ePhoneTypeId)
                                            : old.getRelatedContactEmailPhoneOfType(contactId,
                                                                                    ePhoneTypeId);

        cep.getEmailPhone().setEmailPhone(contactEPhone);
        setDefaultDetails(cep, createNew, user, date);
        neu.getRelatedContactOfType(contactId).getContact().getContactEmailPhones().add(cep);
    }

    private void mapRequestorDetail(boolean isNew,
                                    Requestor neu,
                                    Requestor old,
                                    String value,
                                    String type,
                                    RequestorDAO dao,
                                    Timestamp date) {

        User user = getUser();

        // If the new value is null, no need to map and the old value will be removed
        if (StringUtilities.isEmpty(value)) {
            return;
        }

        boolean noOldValue = (old == null) || (old.getRequestorDetailOfType(type) == null);
        boolean createNew = (isNew || noOldValue);

        RequestorDetail rDet = (createNew) ? new RequestorDetail(type)
                                           : old.getRelatedRequestorDetailOfType(type);

        rDet.setSystemType((type.equalsIgnoreCase(ROIConstants.DOB_TYPE))
                                   ? ROIConstants.DB_TYPE_DATETIME
                                   : ROIConstants.DB_TYPE_VARCHAR);

        rDet.setValue(value);
        setDefaultDetails(rDet, createNew, user, date);
        neu.getRequestorDetails().add(rDet);
    }

    private void setDefaultDetails(RelatedAddress relAddress,
                                   boolean isNew,
                                   User user,
                                   Timestamp date) {

        Integer userId = user.getInstanceId();
        if (isNew) {
            relAddress.setCreatedBy(userId);
            relAddress.getAddress().setCreatedBy(userId);
        } else {
            relAddress.setModifiedBy(userId);
            relAddress.getAddress().setModifiedBy(userId);
        }
        relAddress.setModifiedDate(date);
        relAddress.getAddress().setModifiedDate(date);
    }

    private void setDefaultDetails(RelatedEmailPhone rep,
                                   boolean isNew,
                                   User user,
                                   Timestamp date) {

        Integer userId = user.getInstanceId();

        if (isNew) {
            rep.setCreatedBy(userId);
            rep.getEmailPhone().setCreatedBy(userId);
        } else {
            rep.setModifiedBy(userId);
            rep.getEmailPhone().setModifiedBy(userId);
        }
        rep.setModifiedDate(date);
        rep.getEmailPhone().setModifiedDate(date);
    }

    private void setDefaultDetails(RelatedContact relContact,
                                   boolean isNew,
                                   User user,
                                   Timestamp date) {

        Integer userId = user.getInstanceId();

        if (isNew) {
            relContact.setCreatedBy(userId);
            relContact.getContact().setCreatedBy(userId);
        } else {
            relContact.setModifiedBy(userId);
            relContact.getContact().setModifiedBy(userId);
        }
        relContact.setModifiedDate(date);
        relContact.getContact().setModifiedDate(date);
    }

    private void setDefaultDetails(ContactEmailPhone cep,
                                   boolean isNew,
                                   User user,
                                   Timestamp date) {

        Integer userId = user.getInstanceId();

        if (isNew) {
            cep.setCreatedBy(userId);
            cep.getEmailPhone().setCreatedBy(userId);
        } else {
            cep.setModifiedBy(userId);
            cep.getEmailPhone().setModifiedBy(userId);
        }
        cep.setModifiedDate(date);
        cep.getEmailPhone().setModifiedDate(date);
    }

    private void setDefaultDetails(RequestorDetail pDet,
                                   boolean isNew,
                                   User user,
                                   Timestamp date) {

        Integer userId = user.getInstanceId();
        if (isNew) {
            pDet.setCreatedBy(userId);
        } else {
            pDet.setModifiedBy(userId);
        }
        pDet.setModifiedDate(date);
        pDet.setSystem(ROIConstants.SYSTEM_HPF);
        pDet.setSystemDb(ROIConstants.SYSTEM_DB_HIS);
        pDet.setSystemTable(ROIConstants.SYSTEM_TABLE_PATIENTS);
        pDet.setReason(ROIConstants.REQUESTOR_DETAIL_REASON);
    }

    private void setDefaultDetails(Requestor req,
                                   boolean isNew,
                                   Timestamp date) {

        User user = getUser();
        Integer userId = user.getInstanceId();

        if (isNew) {
            req.setCreatedBy(userId);
        } else {
            req.setModifiedBy(userId);
        }
        req.setModifiedDate(date);
        req.setFrequent(true);
    }

    private String generateInvoiceLetter(long id, String docType, String type, Date date) {

        try {

            LetterDataRetriever dataRetriever = getLetterDataRetriever(type);
            Object letterData = dataRetriever.retrieveLetterData(id, 0);

            LetterData letter = (LetterData) letterData;
            String templateFileId = letter.getTemplateFileId();
            if (null == templateFileId || templateFileId.trim().isEmpty()) {
                throw new ROIException(ROIClientErrorCodes.TEMPLATE_ID_IS_BLANK);
            }

            long templateId = Long.parseLong(templateFileId);

            // Retrieve the corresponding template from the database
            LetterTemplateDocument templateDoc = dataRetriever.retrieveLetterTemplate(templateId);

            String fileName = type + "." + id + "."
                                + formatDate(date, FILENAME_DATE_FORMAT)
                                + ROIConstants.OUTPUT_FILE_EXT;

            String path = getCacheFileName(fileName);

            // Get the letter generator and generates the letter with the
            // template data and
            // corresponding template
            getLetterGenerator(docType).generateLetter(letterData, templateDoc, path);
            return fileName;

        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            throw new ROIException(e, ROIClientErrorCodes.GENERATE_LETTER_OPERATION_FAILED);
        }
    }


    /**
     * @see com.mckesson.eig.roi.requestor.service.RequestorService
     * #createRequestorLetterEntry(com.mckesson.eig.roi.billing.model.RegeneratedInvoiceInfo)
     */
    @Override
    public RequestorLetterHistory createRequestorLetterEntry(RegeneratedInvoiceInfo invoiceInfo) {

        final String logSM = "createRequestorLetterEntry(regInvocieInfo)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        RequestorServiceValidator validator = new RequestorServiceValidator();

        if (!validator.validateRequestorInvoiceInfo(invoiceInfo)) {
            throw validator.getException();
        }

        try {

            OverDueInvoiceCoreDAO dao =
                    (OverDueInvoiceCoreDAO) getDAO(DAOName.OVERDUE_INVOICE_CORE_DAO);

            RequestorLetter reqLetter = dao.retrieveRequestorLetter(invoiceInfo.getId());

            Timestamp dbDate = dao.getDate();
            String date = ROIConstants.DATETIME_FORMATTER.format(dbDate);
            Map<String, String> properties = invoiceInfo.getProperties();
            properties.put(ROIConstants.RESEND_DATE, date);

            if (null != reqLetter && null != properties) {
                reqLetter = updateRequestorLetterInfo(reqLetter, properties);
            }

            setDefaultDetails(reqLetter, dbDate);
            List<RequestorLetter> reqLetterList = new ArrayList<RequestorLetter>();
            reqLetterList.add(reqLetter);

            List<RequestorLetter> reqLetters = dao.createRequestorLetter(reqLetterList);
            long reqLetterId = reqLetters.get(0).getRequestorLetterId();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return constructRequestorLetterHistory(reqLetterId, reqLetter, properties);

        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            throw new ROIException(e, ROIClientErrorCodes.UPDATE_REQUESTOR_LETTER_OPERATION_FAILED);
        }
    }

    private void setDefaultDetails(RequestorLetter reqLetter, Timestamp dbDate) {

        reqLetter.setCreatedDate(dbDate);
        reqLetter.setModifiedDt(dbDate);
        reqLetter.setCreatedBy(getUser().getInstanceId());
        reqLetter.setModifiedBy(getUser().getInstanceId());
    }

    private RequestorLetterHistory constructRequestorLetterHistory(long reqLetterId,
                                                                   RequestorLetter reqLetter,
                                                                   Map<String, String> properties) {

        RequestorLetterHistory reqLetterHistory = new RequestorLetterHistory();

        reqLetterHistory.setCreatedBy(getUser().getDisplayName());
        reqLetterHistory.setCreatedDate(reqLetter.getCreatedDate());

        String queuePassword = properties.get(ROIConstants.QUEUE_PD);
        reqLetterHistory.setQueuePassword(queuePassword);

        String outputMethod = properties.get(ROIConstants.OUTPUT_METHOD);
        reqLetterHistory.setOutputMethod(outputMethod);

        reqLetterHistory.setRequestorLetterId(reqLetterId);
        reqLetterHistory.setRequestTemplateId(reqLetter.getRequestTemplateId());

        String resendDate = properties.get(ROIConstants.RESEND_DATE);
        if (!StringUtilities.isEmpty(resendDate)) {
            reqLetterHistory.setResendDate(resendDate);
        }

        reqLetterHistory.setTemplateUsed(properties.get(ROIConstants.LETTER_TEMPLATE_NAME));

        return reqLetterHistory;
    }

    /*
     * This method updates the requestor letter information.
     *
     */
    private RequestorLetter updateRequestorLetterInfo(RequestorLetter requestorLetter,
                                                      Map<String, String> properties) {

        try {

            String queuePassword = properties.get(ROIConstants.QUEUE_PD);
            requestorLetter.setQueuePassword(queuePassword);

            String outputMethod = properties.get(ROIConstants.OUTPUT_METHOD);
            requestorLetter.setOutputMethod(outputMethod);

            String resendDate = properties.get(ROIConstants.RESEND_DATE);
            Date  date = ROIConstants.DATETIME_FORMATTER.parse(resendDate);
            requestorLetter.setResendDate(date);
            return requestorLetter;

        } catch (ParseException ex) {
            throw new ROIException(ROIClientErrorCodes.INVALID_INVOICE_DATE_FORMAT);
        }


    }

    /**
     * This method retrieve the list of invoices for a requestor
     * @param requestorId
     * @return RequestorInvoicesList
     */
    @Override
    public RequestorInvoicesList retrieveRequestorInvoices(long requestorId) {

        final String logSM = "retrieveRequestorInvoices(requestorId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        // validates the requestorId
        RequestorServiceValidator requestorServicevalidator = new RequestorServiceValidator();
        if (!requestorServicevalidator.validateRequestorId(requestorId)) {
            throw requestorServicevalidator.getException();
        }

        RequestorDAO requestorDAO  = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);

        RequestorInvoicesList requestorInvoicesList = null;

        try {

            requestorInvoicesList = requestorDAO.retrieveRequestorInvoices(requestorId);
            return requestorInvoicesList;

        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error(logSM + "Error :" + e);
            throw new ROIException(e, ROIClientErrorCodes.REQUESTOR_OPERATION_FAILED);
        }
    }
    /**
     * This method is used to retrieve the AdjustmentInfo
     * @param requestorId
     * @return AdjustmentInfo
     */
    @Override
    public AdjustmentInfo retrieveAdjustmentInfo(long requestorId) {

        final String logSM = "retrieveAdjustmentInfo()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        AdjustmentInfo adjustmentInfo = new AdjustmentInfo();
        //retrieveReasons
        ReasonDAO reasonDao = (ReasonDAO) getDAO(DAOName.REASON_DAO);
        ReasonsList reasonsList = reasonDao.retrieveAllReasonsByType(ROIConstants.ADJUSTMENT_REASON);

        RequestorDAO requestorDAO  = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);

        //retrieve all invoices for that requestor
        long adjustmentId = 0;
        List<RequestorInvoice> requestorInvoices =
                              requestorDAO.retrieveOnlyRequestorInvoices(requestorId, adjustmentId);
        adjustmentInfo.setReasonsList(reasonsList);
        adjustmentInfo.setRequestorInvoicesList(new RequestorInvoicesList(requestorInvoices));
        return adjustmentInfo;
    }

    /**
     * This method is to save the AdjustmentInfo
     * @param adjustmentInfo
     *
     */
    @Override
    public void saveAdjustmentInfo(AdjustmentInfo adjustmentInfo) {

        final String logSM = "saveAdjustmentInfo()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {

            RequestorDAO requestorDAO  = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
            RequestorAdjustment requestorAdjustment = adjustmentInfo.getRequestorAdjustment();
            AdjustmentType adjType = requestorAdjustment.getAdjustmentType();

            // to delete the Adjustment entry into database
            long adjustmentId = requestorAdjustment.getId();
            if (requestorAdjustment.isDelete()) {

                deleteRequestorAdjustment(adjustmentId,
                                          requestorDAO,
                                          requestorAdjustment.getRequestorName());

                createJournalEntryForAdjustment(
                        ROIConstants.REMOVE_UNAPPLIED_ADJUSTMENT, adjType, adjustmentId);
                return;
            }

            // date where adjustment is created/modified
            Timestamp date = requestorDAO.getDate();
            if (adjustmentId == 0) {

                setDefaultDetails(requestorAdjustment, date, true);
                adjustmentId = requestorDAO.saveAdjustmentInfo(requestorAdjustment);

                createJournalEntryForAdjustment(
                        ROIConstants.CREATE_ADJUSTMENT, adjType, adjustmentId);

                //Audits the Requestor Post Adjustment operation
                doAudit(requestorAdjustment
                        .constructPostAdjustmentAuditComment(getUser().getFullName(),
                                requestorAdjustment.getAdjustmentType().toString()),
                        getUser().getInstanceId(), date,
                        ROIConstants.AUDIT_ACTION_CODE_ROI_POST,
                        ROIConstants.DEFAULT_FACILITY, null, null);

            } else {

                setDefaultDetails(requestorAdjustment, date, false);
                requestorDAO.updateRequestorAdjustment(requestorAdjustment);

            }

            if (adjustmentInfo.getRequestorInvoicesList() != null) {
                processInvoiceToAdjustment(adjustmentInfo, adjustmentId, date);
            }

        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e);
            throw new ROIException(e,
                                 ROIClientErrorCodes.UNABLE_TO_SAVE_ADJUSTMENT,
                                 e.getMessage());
        }

    }

    /**
     * create (or) update the payment related to the requestor.
     * @param paymentInfo
     * @param createPayment
     * @return paymentId
     */
    private long saveRequestorPayment(RequestorPaymentList paymentInfo, boolean createPayment) {

        final String logSM = "saveRequestorPayment(paymentInfo)";
        long paymentId = 0L;
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + paymentInfo);
        }

        try {

            RequestorDAO requestorDAO  = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);

            if (createPayment) {
                paymentId = requestorDAO.createRequestorPayment(paymentInfo);
            } else {
                paymentId = requestorDAO.updateRequestorPayment(paymentInfo);
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

            return paymentId;

        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e);
            throw new ROIException(e,
                                 ROIClientErrorCodes.CREATE_REQUESTOR_PAYMENT,
                                 e.getMessage());
        }
    }

    /**
     * This method creates an entries into RequestorPayment Table
     * and apply to invoice.
     * @param RequestorPaymentList paymentInfoList
     */
    @Override
    public void createRequestorPayment(RequestorPaymentList paymentInfoList) {

        final String logSM = "createRequestorPayment(paymentInfoList)";
        long paymentId = 0L;
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + paymentInfoList);
        }

        try {

            BillingCoreServiceValidator validator = new BillingCoreServiceValidator();
            if (!validator.validatePaymentDetails(paymentInfoList, true)) {
                throw validator.getException();
            }

            RequestorDAO requestorDAO  = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
            Timestamp date = requestorDAO.getDate();
            setDefaultDetails(paymentInfoList, date, true);

            paymentId = saveRequestorPayment(paymentInfoList, true);

            if (paymentId == 0L) {
                throw new ROIException(
                             ROIClientErrorCodes.INVALID_REQUESTOR_PAYMENT_ID);
            }

            //Audits the Requestor Post Payment operation
            doAudit(paymentInfoList.constructPostPaymentAuditComment(getUser()
                    .getFullName()), getUser().getInstanceId(), date,
                    ROIConstants.AUDIT_ACTION_CODE_ROI_POST,
                    ROIConstants.DEFAULT_FACILITY, null, null);

            //Payment added - Add Corresponding Journal entries for the Payment
            JournalService journalService =
                    (JournalService) getService(ServiceName.JOURNEL_SERVICE);
            journalService.createAcceptPaymentJE(paymentId);

            List<RequestorPayment> invoicePaymentsList = paymentInfoList.getPaymentList();
            if (CollectionUtilities.isEmpty(invoicePaymentsList)) {

                if (DO_DEBUG) {
                    LOG.debug(logSM
                                + "<<End: Payment Amount are not applied to any of the invoices");
                }
                return;
            }

            for (RequestorPayment paymentInfo : invoicePaymentsList) {

                paymentInfo.setPaymentId(paymentId);
                setDefaultDetails(paymentInfo, date, true);

                long paymentToInvoiceId = requestorDAO.createInvoiceToPayment(paymentInfo);

                //Audits the Requestor Apply Payment operation
                doAudit(paymentInfo.constructApplyPaymentAuditComment(
                        paymentInfoList.getPaymentMode(),
                        paymentInfoList.getRequestorId()),
                        getUser().getInstanceId(), date,
                        ROIConstants.AUDIT_ACTION_CODE_ROI_APPLY_PYMT,
                        paymentInfo.getFacility(), null, null);

                //Event the Requestor Apply Payment operation
                createEvent(paymentInfo, 0, paymentInfoList.getPaymentMode(),
                        ROIConstants.APPLY_PAYMENT_EVENT, paymentInfo.getRequestId(),
                        date, null);

                if (paymentInfo.getInvoiceBalance() == 0) {
                    //Event the Closed Invoice operation
                    createEvent(paymentInfo, 0, null, ROIConstants.CLOSED_INVOICE_EVENT,
                            paymentInfo.getRequestId(), date, null);
                }

                //Payment applied - Add Corresponding Journal entries for the invoice
                journalService.createApplyPaymentToInvoiceJE(paymentToInvoiceId);
            }

            requestorDAO.updateRequestorInvoice(invoicePaymentsList);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e);
            throw new ROIException(e,
                                ROIClientErrorCodes.CREATE_REQUESTOR_PAYMENT,
                                e.getMessage());
        }
    }

    /**
     * This method creates an entries into RequestorPayment Table
     * and apply to invoice.
     * @param RequestorPaymentList paymentInfoList
     */
    @Override
    public void updateRequestorPayment(RequestorPaymentList paymentInfoList) {

        final String logSM = "updateRequestorPayment(paymentInfoList)";
        long paymentId = 0;
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + paymentInfoList);
        }
        try {
            BillingCoreServiceValidator validator = new BillingCoreServiceValidator();
            if (!validator.validatePaymentDetails(paymentInfoList, false)) {
                throw validator.getException();
            }
            RequestorDAO requestorDAO  = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
            RequestCoreDAO requestCoreDAO  = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
            Timestamp date = requestorDAO.getDate();
            setDefaultDetails(paymentInfoList, date, false);
            paymentId = saveRequestorPayment(paymentInfoList, false);
            if (paymentId == 0) {
                throw new ROIException(
                        ROIClientErrorCodes.INVALID_REQUESTOR_PAYMENT_ID);
            }
            List<RequestorPayment> dbInvoiceToPayment =
                        requestorDAO.retrieveInvoiceToPayment(paymentInfoList.getPaymentId());
            processInvoiceToPayment(paymentInfoList, dbInvoiceToPayment, date);
            requestorDAO.updateRequestorInvoice(paymentInfoList.getPaymentList());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e);
            throw new ROIException(e,
                                 ROIClientErrorCodes.UPDATE_REQUESTOR_PAYMENT,
                                 e.getMessage());
        }
    }

    /**
     * This method retrieves all the fee information for a specific adjustment
     * @param adjustmentId
     * @return AdjustmentInfo
     */
    @Override
    public AdjustmentInfo retrieveAdjustmentInfoByAdjustmentId(long adjustmentId, long requestorId)
    {
        final String logSM = "retrieveAdjustmentInfoByAdjustmentId(adjustmentId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + adjustmentId);
        }
        try
        {
            RequestorDAO requestorDAO  = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
            AdjustmentInfo adjustmentInfo = new AdjustmentInfo();
            RequestorAdjustment requestorAdjustment = requestorDAO.retrieveRequestorAdjustment(adjustmentId);

            //retrieve all invoices for that requestor
            RequestorInvoicesList requestorInvoicesList = new RequestorInvoicesList();
            List<RequestorInvoice> requestorInvoices = null;

            requestorInvoices = requestorDAO.retrieveOnlyRequestorInvoices(requestorId,adjustmentId);
            requestorInvoicesList.setRequestorInvoices(requestorInvoices);

            adjustmentInfo.setRequestorInvoicesList(requestorInvoicesList);
            adjustmentInfo.setRequestorAdjustment(requestorAdjustment);

            return adjustmentInfo;

        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e);
            throw new ROIException(e,
                                 ROIClientErrorCodes.RETRIEVE_UNAPPLIED_ADJUSTMENT, e.getMessage());
        }
    }

    /**
     * This method retrieves the past invoices for a request.
     * @param requestId
     * @return PastInvoiceList
     */
    @Override
    public PastInvoiceList retrieveRequestorPastInvoices(long requestorId) {

        final String logSM = "retrieveRequestorPastInvoices(requestorId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestorId);
        }

        if (0 == requestorId) {
            throw new ROIException(ROIClientErrorCodes.INVALID_REQUESTOR_ID);
        }

        try {

            RequestorStatementDAO dao =
                                (RequestorStatementDAO) getDAO(DAOName.REQUESTOR_STATEMENT_DAO);

            List<PastInvoice> pastInvoices = dao.retrieveRequestorPastInvoices(requestorId);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + pastInvoices.size());
            }

            return new PastInvoiceList(pastInvoices);

        } catch (Throwable e) {

            LOG.error(e);
            throw new ROIException(ROIClientErrorCodes.RETRIEVE_REQUESTOR_OPERATION_FAILED);
        }
    }

    @Override
    public DocInfoList generateRequestorStatement(RequestorStatementCriteria criteria) {

        final String logSM = "generateRequestorStatement(criteria)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + criteria);
        }

        if (null == criteria) {
            throw new ROIException(ROIClientErrorCodes.INVALID_STATEMENT_CRITERIA);
        }

        try {

            DocInfo docInfo = null;
            if (null != criteria.getDateRange()) {
                docInfo = previewRequestorStatement(criteria);
            }

            // generate the past invoices
            List<DocInfo> docInfos = generatePastInvoices(criteria.getPastInvIds());
            if (null != docInfo) {
                docInfos.add(0, docInfo);
            }

            DocInfoList docInfoList = mergeDocInfos(docInfos, ROIConstants.REQUESTOR_LETTER_FILE);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

            return docInfoList;

        } catch (Throwable e) {

            LOG.error(e);
            throw new ROIException(
                                ROIClientErrorCodes.GENERATE_REQUESTOR_STATEMENT_OPERATION_FAILED);
        }
    }

    /**
     * creates requestor statements details entry in RequestorLetterCore table.
     * @param statementCriteria RequestorStatementCriteria object.
     */
    @Override
    public long createRequestorStatement(RequestorStatementCriteria statementCriteria) {

        final String logSM = "createRequestorStatement(statementCriteria)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + statementCriteria);
        }

        RequestorServiceValidator validator = new RequestorServiceValidator();

        if (!validator.validateStatementCriteria(statementCriteria)) {
            throw validator.getException();
        }

        try {

            RequestorStatementDAO dao =
                                (RequestorStatementDAO) getDAO(DAOName.REQUESTOR_STATEMENT_DAO);

            RequestorStatementInfo requestorInfo =
                                            retrieveRequestorStatementInfo(statementCriteria);
            RequestorLetter requestorLetter =
                            constructRequestorLetterFromStatementInfo(requestorInfo);

            setDefaultDetails(requestorLetter, dao.getDate());

            OverDueInvoiceCoreDAO overDueInvDao
                            = (OverDueInvoiceCoreDAO) getDAO(DAOName.OVERDUE_INVOICE_CORE_DAO);

            List<RequestorLetter> requestorLetters = new ArrayList<RequestorLetter>();
            requestorLetters.add(requestorLetter);
            requestorLetters = overDueInvDao.createRequestorLetter(requestorLetters);

            long requestorLetterId = CollectionUtilities.isEmpty(requestorLetters) ? 0
                                    : requestorLetters.get(0).getRequestorLetterId();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:RequestorLetterId:" + requestorLetterId);
            }

            return requestorLetterId;

        } catch (Throwable e) {

            LOG.error(e);
            throw new ROIException(ROIClientErrorCodes.CREATE_REQUESTOR_STATEMENT_FAILED);
        }

    }

	/**
     * This method will load the Requestor History
     * @param requestorId
     * @return RequestorHistory
     */
    @Override
    public RequestorHistoryList retrieveRequestorSummaries(long requestorId) {

        final String logSM = "retrieveRequestorSummaries(requestorId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: " + requestorId);
        }

        try {

            RequestorDAO requestorDao =
                                (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);

            RequestorHistoryList history = requestorDao.retrieveRequestorSummaries(requestorId);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

            return history;
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e,
                                   ROIClientErrorCodes.RETRIEVE_REQUESTOR_HISTORY_OPERATION_FAILED);
        }
    }

    /**
     * This method will separate the paymentList as newInvoiceToPayments, modifiedInvoiceToPayments
     * and deletedInvoiceToPayments and save to database.
     * @param paymentList
     * @param dbPaymentList
     * @param date
     * @return list of all modified invoiceIds
     */
    private void processInvoiceToPayment(RequestorPaymentList paymentList,
                                           List<RequestorPayment> dbPaymentList,
                                           Timestamp date) {

        if (CollectionUtilities.isEmpty(paymentList.getPaymentList())) {
            return;
        }

        RequestorDAO requestorDAO = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);

        try {

            JournalService journalService = (JournalService) getService(ServiceName.JOURNEL_SERVICE);

            for (RequestorPayment invoiceToPayment : paymentList.getPaymentList()) {

                double totalAppliedAmount = invoiceToPayment.getTotalAppliedAmount();
                double applyAmount = invoiceToPayment.getLastAppliedAmount();
                long invoiceId = invoiceToPayment.getRequestCoreDeliveryChargesId();
                long paymentId = invoiceToPayment.getPaymentId();
                boolean isNew = true;
                boolean doAudit = false;
                long paymentToInvoiceId = 0;

                setDefaultDetails(invoiceToPayment, date, false);

                for (RequestorPayment dbInvoiceToPayment : dbPaymentList) {

                    long dbInvoiceId = dbInvoiceToPayment.getRequestCoreDeliveryChargesId();
                    long dbPaymentId = dbInvoiceToPayment.getPaymentId();
                    double oldAppliedAmt = dbInvoiceToPayment.getTotalAppliedAmount();

                    if (dbInvoiceId == invoiceId && dbPaymentId == paymentId) {

                        // Delete applied amount from invoice
                        if ((totalAppliedAmount == 0 || totalAppliedAmount == applyAmount)
                                                            && oldAppliedAmt > 0){

                            List<Long> removedMappedIds =
                                    requestorDAO.retrieveMappedInvoicesByPaymentAndInvoiceId(
                                                                                      dbPaymentId,
                                                                                      dbInvoiceId);

                            requestorDAO.deleteInvoiceToPayment(invoiceToPayment);
                            for (Long removedMapId : removedMappedIds) {
                                //Payment removed - Add Corresponding Journal entries for the invoice
                                journalService.createUnApplyPaymentFromInvoiceJE(removedMapId);
                            }
                            doAudit = true;
                        }

                        // Apply amount to invoice
                        if (applyAmount > 0) {

                            setDefaultDetails(invoiceToPayment, date, true);
                            paymentToInvoiceId = requestorDAO.createInvoiceToPayment(invoiceToPayment);
                            //Payment applied - Add Corresponding Journal entries for the invoice
                            journalService.createApplyPaymentToInvoiceJE(paymentToInvoiceId);
                            doAudit = true;
                        }

                        if (doAudit) {
                            //Audits the Requestor Update Payment operation
                            doAudit(invoiceToPayment.constructUpdatePaymentAuditComment(
                                    dbInvoiceToPayment.getTotalAppliedAmount(),
                                    paymentList.getRequestorName(),
                                    paymentList.getRequestorType()),
                                    getUser().getInstanceId(),
                                    date, ROIConstants.AUDIT_ACTION_CODE_ROI_MODIFY_PYMT,
                                    invoiceToPayment.getFacility(), null, null);

                            //Event the Requestor Update Payment operation
                            createEvent(invoiceToPayment,
                                    dbInvoiceToPayment.getTotalAppliedAmount(),
                                    paymentList.getPaymentMode(),
                                    ROIConstants.UPDATE_PAYMENT_EVENT,
                                    invoiceToPayment.getRequestId(), date, null);

                            if (invoiceToPayment.getInvoiceBalance() == 0) {
                                //Event the Closed Invoice operation
                                createEvent(invoiceToPayment, 0, null,
                                        ROIConstants.CLOSED_INVOICE_EVENT,
                                        invoiceToPayment.getRequestId(), date, null);
                            }
                        }

                        isNew = false;
                        break;
                    }
                }

                //Apply amount to invoice
                if (isNew) {

                    setDefaultDetails(invoiceToPayment, date, true);
                    paymentToInvoiceId = requestorDAO.createInvoiceToPayment(invoiceToPayment);
                    //Payment applied - Add Corresponding Journal entries for the invoice
                    journalService.createApplyPaymentToInvoiceJE(paymentToInvoiceId);

                    //Audits the Requestor Post Payment operation
                    doAudit(invoiceToPayment.constructApplyPaymentAuditComment(
                            paymentList.getPaymentMode(), paymentList.getRequestorId()),
                            getUser().getInstanceId(),
                            date, ROIConstants.AUDIT_ACTION_CODE_ROI_APPLY_PYMT,
                            ROIConstants.DEFAULT_FACILITY, null, null);

                    //Event the Requestor Apply Payment operation
                    createEvent(invoiceToPayment, 0, paymentList.getPaymentMode(),
                            ROIConstants.APPLY_PAYMENT_EVENT,
                            invoiceToPayment.getRequestId(), date, null);

                    if (invoiceToPayment.getInvoiceBalance() == 0) {
                        //Event the Closed Invoice operation
                        createEvent(invoiceToPayment, 0, null,
                                ROIConstants.CLOSED_INVOICE_EVENT,
                                invoiceToPayment.getRequestId(), date, null);
                    }
                }
            }

        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e);
            throw new ROIException(e,
                                 ROIClientErrorCodes.UPDATE_REQUESTOR_PAYMENT,
                                 e.getMessage());
        }
    }

    /**
     * This method creates an Invoice/PreBill/Letters with all the details persisted earlier
     * This service can be used in case of viewing Requestor History
     *
     * @param invoiceId
     * @param requestId
     * @param docType
     * @param retrieverType
     * @param letterType
     * @return DocInfo details
     */
    @Override
    public DocInfo viewRequestorDetails(long invoiceId, long requestId,String docType,String retrieverType,
            String letterType) {

        final String logSM = "viewRequestorDetails(invoiceId,requestId,docType,retrieverType,letterType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + invoiceId + requestId + docType + retrieverType + letterType);
        }

        try {
            
			// Removed the logic to retrieve the letter template file id from the invoice table
            // 1. This method is also used to view request statement.    
			// 2. The Letter Template file id is also retrieved in the data retriever itself. 
            docType = StringUtilities.isEmpty(docType) ? ROIConstants.TEMPLATE_FILE_TYPE : docType;
            List<String> retVals = generateLetter(invoiceId, 
                                                  requestId, 
                                                  docType, 
                                                  retrieverType, 
                                                  letterType,
                                                  0,  
                                                  null);

            String type = null;
            if (ROIConstants.PREBILL.equalsIgnoreCase(letterType)) {
                type = ROIConstants.PREBILL_FILE;
            } else if (ROIConstants.INVOICE.equalsIgnoreCase(letterType)) {
                type = ROIConstants.INVOICE_FILE;
            } else {
                type = ROIConstants.LETTER_FILE;
            }
            
            PdfUtilities.encrypt(getCacheFileName(retVals.get(0)));
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + retVals.get(0));
            }
            
            return new DocInfo(invoiceId, retVals.get(0), type);
            
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.VIEW_REQUESTOR_HISTORY_OPERATION_FAILED);
        }
        }

    @Override
    public DocInfoList createRequestorRefund(RequestorRefund requestorRefund) {

        final String logSM = "createRequestorRefund(requestorRefund)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: " + requestorRefund);
        }

        RequestorServiceValidator validator = new RequestorServiceValidator();
        RequestorDAO requestorDao = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
        if (!validator.validateRefundDetails(requestorRefund, requestorDao)) {
            throw validator.getException();
        }

        try {

            List<RequestorPayment> unappliedPayList
                                    = requestorDao.retrieveRequestorUnappliedPayments(
                                                             requestorRefund.getRequestorId());
            Timestamp date = requestorDao.getDate();

            RequestorPaymentList requestorPayList = new RequestorPaymentList();
            requestorPayList.setPaymentAmount(requestorRefund.getRefundAmount());

            applyPayments(requestorPayList, unappliedPayList, date);
            requestorDao.updateRequestorPayment(requestorPayList.getPaymentList(), true);

            setDefaultDetails(requestorRefund, date, true);
            long refundId = requestorDao.createRequestorRefund(requestorRefund);

            List<DocInfo> docInfos = new ArrayList<DocInfo>();
            docInfos.add(new DocInfo(refundId, null, ROIConstants.REQUESTOR_REFUND_FILE));

            // statement generation
            RequestorStatementCriteria statementCriteria = requestorRefund.getStatementCriteria();
            if (null != statementCriteria) {

                statementCriteria.setOutputMethod(requestorRefund.getOutputMethod());
                statementCriteria.setQueuePassword(requestorRefund.getQueuePassword());
                RequestorStatementInfo requestorInfo =
                        retrieveRequestorStatementInfo(statementCriteria);

                RequestorLetter requestorLetter =
                                constructRequestorLetterFromStatementInfo(requestorInfo);

                setDefaultDetails(requestorLetter, requestorDao.getDate());

                OverDueInvoiceCoreDAO overDueInvDao
                                = (OverDueInvoiceCoreDAO) getDAO(DAOName.OVERDUE_INVOICE_CORE_DAO);

                List<RequestorLetter> requestorLetters = new ArrayList<RequestorLetter>();
                requestorLetters.add(requestorLetter);
                requestorLetters = overDueInvDao.createRequestorLetter(requestorLetters);

                long statementId = CollectionUtilities.isEmpty(requestorLetters) ? 0
                                        : requestorLetters.get(0).getRequestorLetterId();

                docInfos.add(new DocInfo(statementId, null, ROIConstants.REQUESTORLETTER));
            }

            //Refund issued - Add Corresponding Journal entries for Refund
            JournalService journalService = (JournalService) getService(ServiceName.JOURNEL_SERVICE);
            journalService.createIssueRefundJE(refundId);

            //Audits the Requestor Refund operation
            doAudit(requestorRefund.constructRefundAuditComment(), getUser().getInstanceId(), date,
                                    ROIConstants.AUDIT_ACTION_CODE_REFUND_INVOICE,
                                    ROIConstants.DEFAULT_FACILITY, null, null);

            return new DocInfoList(docInfos);

        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e);
            throw new ROIException(e,
                      ROIClientErrorCodes.CREATE_REQUESTOR_REFUND_OPERTARION_FAILED,
                      e.getMessage());
        }

    }

    /**
     * This method is used to retrieve the unapplied amount details for a particular requestor
     * @param requestId
     * @return RequestorUnappliedAmountDetailsList
     */
    @Override
    public RequestorUnappliedAmountDetailsList retrieveUnappliedAmountDetails(long requestId)
    {
        final String logSM = "retrieveUnappliedAmountDetails(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }

        try {

            RequestorDAO requestorDAO  = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
            RequestorUnappliedAmountDetailsList reqAmtList = requestorDAO.retrieveUnappliedAmountDetails(requestId);
            return reqAmtList;
    }catch (ROIException e) {
        throw e;
    } catch (Throwable e) {
        LOG.error(e);
        throw new ROIException(e, ROIClientErrorCodes.RETRIEVE_UNAPPLIED_AMOUNT_DETAILS);
    }
    }

    @Override
    public DocInfoList viewRequestorRefund(RequestorRefund requestorRefund) {

        final String logSM = "viewRequestorRefund(requestorRefund)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestorRefund);
        }

        RequestorServiceValidator validator = new RequestorServiceValidator();

        RequestorDAO requestorDao = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
        if (!validator.validateRefundDetails(requestorRefund, requestorDao)) {
            throw validator.getException();
        }

        try {

            RequestorStatementDAO dao =
                    (RequestorStatementDAO) getDAO(DAOName.REQUESTOR_STATEMENT_DAO);

            long templateId = requestorRefund.getTemplateId();
            List<DocInfo> docInfos = new ArrayList<DocInfo>();

            if (templateId != 0) {

                RefundLetter refundLetter = constructRefundLetterModel(requestorRefund);
                LetterDataRetriever  retriever = getLetterDataRetriever(ROIConstants.REQUESTORREFUND);
                LetterData letterData = (LetterData) retriever.constructTemplateDataModel(refundLetter);

                String fileName = generateLetter(templateId,
                                                 ROIConstants.TEMPLATE_FILE_TYPE,
                                                 ROIConstants.REQUESTORREFUND,
                                                 letterData,
                                                 dao.getDate());

                docInfos.add(new DocInfo(0, fileName, ROIConstants.REQUESTOR_REFUND_FILE));
            }

            RequestorStatementCriteria statementCriteria = requestorRefund.getStatementCriteria();
            if (statementCriteria!= null) {
                docInfos.add(previewRequestorStatement(statementCriteria));
            }
            DocInfoList docInfoList = mergeDocInfos(docInfos, ROIConstants.REQUESTOR_REFUND_FILE);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Refund Letter File Name : " + docInfoList.getName());
            }

            return docInfoList;

        } catch(ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(ROIClientErrorCodes.VIEW_REFUND_OPERATION_FAILED);
        }
    }

    /**
     * This method delete the RequestorPayment.
     * @param paymentId
     */
    @Override
    public void deleteRequestorPayment(long paymentId, String requestorName) {

        final String logSM = "deleteRequestorPayment(paymentId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + paymentId);
        }

        if (paymentId == 0) {
            throw new ROIException(
                    ROIClientErrorCodes.INVALID_REQUESTOR_PAYMENT_ID);
        }

        try {

            RequestorDAO requestorDao = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
            RequestorPaymentList paymentDetails = requestorDao.retrieveRequestorPayment(paymentId);

            if (null == paymentDetails
                    || (paymentDetails.getUnAppliedAmount() != paymentDetails.getPaymentAmount())) {
                throw new ROIException(
                        ROIClientErrorCodes.INVALID_REQUESTOR_PAYMENT_ID);
            }

            //Payment removed - Add Corresponding Reversal Journal entries for Payment
            //Do the journal entries before deleting the payment
            JournalService journalService =
                                    (JournalService) getService(ServiceName.JOURNEL_SERVICE);
           journalService.createVoidPaymentJE(paymentId);

            //Delete the payment
            requestorDao.deleteRequestorPayment(paymentId);
            paymentDetails.setRequestorName(requestorName);

            //Audits the Requestor Delete Payment operation
            doAudit(paymentDetails.constructDeletePaymentAuditComment(),
                    getUser().getInstanceId(), requestorDao.getDate(),
                    ROIConstants.AUDIT_ACTION_CODE_ROI_REMOVE_PYMT,
                    ROIConstants.DEFAULT_FACILITY, null, null);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Deleted Requestor Payment Id : " +paymentId);
            }

        } catch(ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(ROIClientErrorCodes.DELETE_REQUESTOR_PAYMENT_OPERATION_FAILED);
        }

    }

    /**
     * This method construct Refund letter model
     * @param requestorRefund
     * @return
     */
    private RefundLetter constructRefundLetterModel(RequestorRefund requestorRefund) {

        RequestorStatementDAO dao = (RequestorStatementDAO) getDAO(DAOName.REQUESTOR_STATEMENT_DAO);

        long requestorId = requestorRefund.getRequestorId();

        RequestorStatementInfo requestorInfo = dao.retrieveRequestorInfo(requestorId);

        RefundLetter refundLetter = new RefundLetter();

        refundLetter.setTemplateFileId(requestorRefund.getTemplateId());
        refundLetter.setTemplateName(requestorRefund.getTemplateName());
        refundLetter.setNotes(requestorRefund.getNotes());

        refundLetter.setRequestorName(requestorInfo.getName());
        refundLetter.setRequestorTypeName(requestorInfo.getRequestorTypeName());
        refundLetter.setRequestorTypeId(requestorInfo.getRequestorTypeId());
        refundLetter.setAddress1(requestorInfo.getAddress1());
        refundLetter.setAddress2(requestorInfo.getAddress2());
        refundLetter.setAddress3(requestorInfo.getAddress3());
        refundLetter.setCity(requestorInfo.getCity());
        refundLetter.setState(requestorInfo.getState());
        refundLetter.setPostalCode(requestorInfo.getPostalCode());

        refundLetter.setRefundAmount(requestorRefund.getRefundAmount());

        User user = getUser();
        refundLetter.setUserName(user.getFullName());
        refundLetter.setUserInstanceId(user.getInstanceIdValue());

        return refundLetter;
    }

    /**
     * This method deletes the Requestor Adjustment.
     * @param adjustmentId
     * @param requestorDAO
     * @param requestorName
     */
    private void deleteRequestorAdjustment(long adjustmentId,
            RequestorDAO requestorDAO, String requestorName) {

        try {

            RequestorAdjustment dbAdjustment = requestorDAO
                    .retrieveRequestorAdjustment(adjustmentId);
            if (null == dbAdjustment
                    || dbAdjustment.getUnappliedAmount() != dbAdjustment.getUnappliedAmount()) {
                throw new ROIException(ROIClientErrorCodes.INVALID_REQUESTOR_ADJUSTMENT_ID);
            }
            //Adjustment removed - Add Corresponding Reversal Journal entries for Adjustment
            //Do the journal entries before deleting the Adjustment
            /*JournalService journalService =
                                    (JournalService) getService(ServiceName.JOURNEL_SERVICE);
            journalService.createRemoveAdjustmentJE(adjustmentId);*/

            //Delete the Adjustment
            requestorDAO.deleteRequestorAdjustment(adjustmentId);

            //Audits the Requestor Dlete Adjustment operation
            doAudit(dbAdjustment.constructDeleteAdjustmentAuditComment(
                    getUser().getFullName(), requestorName),
                    getUser().getInstanceId(), requestorDAO.getDate(),
                    ROIConstants.AUDIT_ACTION_CODE_ROI_REMOVE_ADJ,
                    ROIConstants.DEFAULT_FACILITY, null, null);

        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e,
                    ROIClientErrorCodes.DELETE_REQUESTOR_ADJUSTMENT_OPERATION_FAILED);
        }

    }

    /**
     * This method will creates the RequestEvent.
     * @param model
     * @param paymentType
     * @param eventName
     * @param requestId
     * @param date
     */
    private void createEvent(Object model, double oldAppliedAmount,
            String paymentType, String eventName, long requestId,
            Timestamp date, String adjType) {

        RequestCoreDAO dao  = (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
        RequestEvent event = new RequestEvent();
        event.setRequestId(requestId);
        setDefaultDetails(event, date, true);

        if (eventName.equals(ROIConstants.APPLY_PAYMENT_EVENT)) {
            RequestorPayment payment = (RequestorPayment)model;
            event.setType(TYPE.PAYMENT_APPLIED.name());
            event.setDescription(payment.constructApplyPaymentEventComment(
                    paymentType, getUser().getFullName()));
            dao.createRequestEvent(event);
        }

        if (eventName.equals(ROIConstants.UPDATE_PAYMENT_EVENT)) {
            RequestorPayment payment = (RequestorPayment)model;
            event.setType(TYPE.PAYMENT_MODIFIED.name());
            event.setDescription(payment.constructUpdatePaymentEventComment(
                    oldAppliedAmount, paymentType, getUser().getFullName()));
            dao.createRequestEvent(event);
        }

        if (eventName.equals(ROIConstants.APPLY_ADJUSTMENT_EVENT)) {
            RequestorInvoice adjustment = (RequestorInvoice)model;
            event.setType(TYPE.ADJUSTMENT_APPLIED.name());
            adjustment.setRequestId(requestId);
            event.setDescription(adjustment.constructApplyAdjustmentEventComment(adjType));
            dao.createRequestEvent(event);
        }

        if (eventName.equals(ROIConstants.UPDATE_ADJUSTMENT_EVENT)) {
            RequestorInvoice adjustment = (RequestorInvoice)model;
            event.setType(TYPE.ADJUSTMENT_MODIFIED.name());
            event.setDescription(adjustment.constructUpdateAdjustmentEventComment(
                    oldAppliedAmount, requestId, getUser().getFullName(), adjType));
            dao.createRequestEvent(event);
        }

        if (eventName.equals(ROIConstants.CLOSED_INVOICE_EVENT)) {
            long invoiceId = 0;
            if (model instanceof RequestorPayment) {
                invoiceId = ((RequestorPayment)model).getRequestCoreDeliveryChargesId();
            } else if (model instanceof RequestorInvoice) {
                invoiceId = ((RequestorInvoice)model).getId();
            }
            event.setType(TYPE.INVOICE_CLOSED.name());
            event.setDescription("Invoice "+invoiceId+" was closed.");
            dao.createRequestEvent(event);
        }

    }

    /**
     * This method will create entry into invoice and invoice mapping tables.
     * @param adjustmentInfo
     * @param requestorDAO
     * @param requestDeliveryDAO
     * @param adjustmentId
     */
    private void processInvoiceToAdjustment(AdjustmentInfo adjustmentInfo, long adjustmentId, Timestamp date) {

        RequestorInvoicesList requestorInvoicesList = adjustmentInfo.getRequestorInvoicesList();
        if (requestorInvoicesList == null) {
            return;
        }

        RequestorDAO requestorDAO  = (RequestorDAO) getDAO(DAOName.REQUESTOR_DAO);
        RequestCoreDeliveryDAO requestDeliveryDAO =
                (RequestCoreDeliveryDAO) getDAO(DAOName.REQUEST_CORE_DELIVERY_DAO);

        List<RequestorInvoice> invoiceList = requestorDAO.retrieveInvoiceIdsForAdjustments(adjustmentId);
        RequestorAdjustment requestorAdjustment = adjustmentInfo.getRequestorAdjustment();
        AdjustmentType adjType = requestorAdjustment.getAdjustmentType();

        // to create entry into delivery charges mapping table
        for (RequestorInvoice requestorInvoice : requestorInvoicesList.getRequestorInvoices()) {

             double totalAppliedAmount = requestorInvoice.getAppliedAmount();
             double applyAmount = requestorInvoice.getApplyAmount();
             long invoiceId = requestorInvoice.getId();
             double invoiceBalance = requestorInvoice.getBalance();
             boolean matchFound = false;
             long adjToInvoiceId = 0;

            for (RequestorInvoice mappedInvoice : invoiceList) {

                if (invoiceId == mappedInvoice.getId()) {

                    matchFound = true;

                    // Delete
                    Double oldAppliedAmount = mappedInvoice.getAppliedAmount();
                    if ((totalAppliedAmount == 0 || totalAppliedAmount == applyAmount)
                            && (oldAppliedAmount > 0)) {

                        List<Long> removedMappedIds = requestorDAO
                                .retrieveMappedInvoicesByAdjustmentAndInvoiceId(adjustmentId, invoiceId);
                        requestorDAO.deleteMappedInvoicesByAdjustmentAndInvoiceId(adjustmentId, invoiceId);

                        for (Long removedMapId : removedMappedIds) {
                            //Adjustment removed - Add Corresponding Journal entries for the invoice
                            createJournalEntryForAdjustment(
                                    ROIConstants.UNAPPLY_ADJ_FROM_INVOICE, adjType, removedMapId);
                        }
                    }

                    // create
                    if (applyAmount > 0) {

                        setDefaultDetails(adjustmentInfo.getRequestorAdjustment(), date, true);
                        adjToInvoiceId = requestorDAO.saveDeliveryChargesMapping(
                                        adjustmentInfo, applyAmount, invoiceId);
                        //Adjustment applied - Add Corresponding Journal entries for the invoice
                        createJournalEntryForAdjustment(
                                ROIConstants.APPLY_ADJ_TO_INVOICE, adjType, adjToInvoiceId);
                    }

                    //Audits the Requestor Update Adjustment operation
                    doAudit(requestorInvoice.constructUpdateAdjustmentAuditComment(
                            oldAppliedAmount, requestorAdjustment.getRequestorName(),
                            requestorAdjustment.getRequestorType(), adjType.toString()),
                            getUser().getInstanceId(), date,
                            ROIConstants.AUDIT_ACTION_CODE_ROI_MODIFY_ADJ,
                            requestorInvoice.getBillingLocation(), null, null);

                    //Event the Requestor Update Adjustment operation
                    createEvent(requestorInvoice, oldAppliedAmount, null,
                            ROIConstants.UPDATE_ADJUSTMENT_EVENT,
                            requestorInvoice.getRequestId(),  date, adjType.toString());

                    if (requestorInvoice.getBalance() == 0) {
                        //Event the Closed Invoice operation
                        createEvent(requestorInvoice, 0, null, ROIConstants.CLOSED_INVOICE_EVENT,
                                    requestorInvoice.getRequestId(), date, adjType.toString());
                    }

                    break;
                }
            }

            // to create entry in the mapping table only if the amount is greater than zero
            if (!matchFound && applyAmount != 0) {

                setDefaultDetails(adjustmentInfo.getRequestorAdjustment(), date, true);
                adjToInvoiceId = requestorDAO.saveDeliveryChargesMapping(
                                adjustmentInfo, applyAmount, invoiceId);
                //Adjustment applied - Add Corresponding Journal entries for the invoice
                createJournalEntryForAdjustment(
                        ROIConstants.APPLY_ADJ_TO_INVOICE, adjType, adjToInvoiceId);

                //Audits the Requestor Apply Adjustment operation
                doAudit(requestorInvoice.constructApplyAdjustmentAuditComment(
                        requestorAdjustment.getRequestorSeq(), getUser().getFullName(), adjType.toString()),
                        getUser().getInstanceId(), date,ROIConstants.AUDIT_ACTION_CODE_ROI_APPLY_ADJ,
                        requestorInvoice.getBillingLocation(), null, null);

                //Event the Requestor Apply Adjustment operation
                createEvent(requestorInvoice, 0, null, ROIConstants.APPLY_ADJUSTMENT_EVENT,
                            requestorInvoice.getRequestId(), date, adjType.toString());

                if (requestorInvoice.getBalance() == 0) {

                    //Event the Closed Invoice operation
                    createEvent(requestorInvoice, 0, null, ROIConstants.CLOSED_INVOICE_EVENT,
                                requestorInvoice.getRequestId(), date, adjType.toString());
                }
            }

            // to update the invoice balance in ROI_RequestCoreDeliveryCharges table
            requestDeliveryDAO.updateInvoiceBalance(invoiceId,
                                                     invoiceBalance,
                                                     date,
                                                     getUser());

        }

    }

}
