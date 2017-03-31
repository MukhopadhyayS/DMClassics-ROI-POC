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
import java.text.DecimalFormat;
import java.util.Date;

import com.mckesson.eig.roi.admin.dao.RequestorTypeDAO;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.model.Address;
import com.mckesson.eig.roi.base.model.MatchCriteria;
import com.mckesson.eig.roi.base.model.MatchCriteriaList;
import com.mckesson.eig.roi.base.service.BaseROIValidator;
import com.mckesson.eig.roi.billing.model.RegeneratedInvoiceInfo;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.requestor.dao.RequestorDAO;
import com.mckesson.eig.roi.requestor.model.Requestor;
import com.mckesson.eig.roi.requestor.model.RequestorCharges;
import com.mckesson.eig.roi.requestor.model.RequestorRefund;
import com.mckesson.eig.roi.requestor.model.RequestorSearchCriteria;
import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 * This class performs the business validation for all the Requestor services
 *
 * @author ranjithr
 * @date   Mar 17, 2009
 * @since  HPF 13.1 [ROI]; Jun 17, 2008
 */
public class RequestorServiceValidator
extends BaseROIValidator {

    private final int _minLength = 3;
    /**
     * This method validates the requestor search criteria
     * @param searchCriteria Requestor search criteria to be validated
     * @return true if there are no validation failures
     */
    public boolean  validateSearchCriteria(RequestorSearchCriteria searchCriteria,
                                           RequestorTypeDAO reqTypeDao, User user) {



        /*if (!StringUtilities.isEmpty(searchCriteria.getLastName())
            && (searchCriteria.getLastName().length() < ROIConstants.REQUESTOR_NAME_MIN_LENGTH)) {
            addError(ROIClientErrorCodes.REQUESTOR_NAME_SHOULD_HAVE_ATLEAST_TWO_CHARACTERS);
        }

        if (!StringUtilities.isEmpty(searchCriteria.getFirstName())
            && (searchCriteria.getFirstName().length() < ROIConstants.REQUESTOR_NAME_MIN_LENGTH)) {
            addError(ROIClientErrorCodes.REQUESTOR_NAME_SHOULD_HAVE_ATLEAST_TWO_CHARACTERS);
        }*/
        long patReqId = reqTypeDao.getRequestorTypeByName(ROIConstants.REQUESTOR_TYPE_PATIENT)
                                  .getId();

        if (searchCriteria.getType() == patReqId && validateMinLength(searchCriteria, user)) {
            return hasNoErrors();
        }

        if ((searchCriteria.getType() == patReqId) || (searchCriteria.getType() == 0)) {
            validateSearchCriteriaFields(searchCriteria, user);
        }

        validateEmptyFields(searchCriteria, patReqId);
        return hasNoErrors();
    }

    private boolean validateMinLength(RequestorSearchCriteria searchCriteria, User user) {

        int firstNameLength = getLength(searchCriteria.getFirstName());
        int lastNameLength = getLength(searchCriteria.getLastName());
        int epnLength = getLength(searchCriteria.getEpn());
        int ssnLength = getLength(searchCriteria.getSsn());
        int mrnLength = getLength(searchCriteria.getMrn());
        int totalNameLength = firstNameLength + lastNameLength;

        int patientEpnLength = 0;
        if (epnLength > 2) {
            if (user.getEpnEnabled()) {
                patientEpnLength = searchCriteria.getEpn().
                        substring(user.getEpnPrefix().length()).length();
            } else {
                patientEpnLength = epnLength;
            }
        }

        if (patientEpnLength >= _minLength
                || ssnLength >= _minLength
                || mrnLength >= _minLength
                || firstNameLength >= _minLength
                || lastNameLength >= _minLength
                || totalNameLength >= _minLength) {
                return true;
            }
        return false;

    }

    private int getLength(String searchString) {

        int nameLength = (null != searchString) ? searchString.length() : 0;
        return nameLength;
    }

    /**
     * @param searchCriteria
     * @param patientRequestorId
     */
    private void validateEmptyFields(RequestorSearchCriteria searchCriteria,
                                     long patientRequestorId) {

        if (((searchCriteria.getType() != patientRequestorId) && (searchCriteria.getType() != 0))) {
            if (!StringUtilities.isEmpty(searchCriteria.getEpn())
                || !StringUtilities.isEmpty(searchCriteria.getSsn())
                || !StringUtilities.isEmpty(searchCriteria.getMrn())
                || !StringUtilities.isEmpty(searchCriteria.getFacility())
                || (searchCriteria.getDob() != null)) {
                addError(ROIClientErrorCodes.REQUESTOR_INVALID_SEARCH_CRITERIA);
            }
        }
    }

    /**
     * This method is to validates the requestor fields while selecting requestor type
     * as patient or all
     * @param searchCriteria Requestor search criteria to be validated
     */
    private void validateSearchCriteriaFields(RequestorSearchCriteria searchCriteria, User user) {

        validateEPN(searchCriteria, user);

        if ((!StringUtilities.isEmpty(searchCriteria.getSsn()))
            && (searchCriteria.getSsn().length() < ROIConstants.SSN_MIN_LENGTH)) {
            addError(ROIClientErrorCodes.REQUESTOR_SSN_SHOULD_HAVE_ATLEAST_THREE_CHARACTERS);
        }

        if ((!StringUtilities.isEmpty(searchCriteria.getMrn()))
        && (searchCriteria.getMrn().length() < ROIConstants.MRN_MIN_LENGTH)) {
        addError(ROIClientErrorCodes.REQUESTOR_PATIENT_MRN_ATLEAST_THREE_CHARACTERS);
        }
    }

    /**
     * This method validates requestor details to be created
     * @param requestor Requestor details to be validate
     * @param dao Requestor DAO to be used for validation
     * @param isNew
     *              decides validation decision for Update/Create
     *              isNew = true for Requestor creation validation
     *              isNew = false for Requestor update validation
     * @return validation result (true/false)
     */
    public boolean validateRequestor(Requestor requestor,
                                     RequestorDAO dao,
                                     boolean isNew,
                                     User user) {
        if (requestor == null) {
            addError(ROIClientErrorCodes.REQUESTOR_OPERATION_FAILED);
            return false;
        }

        validateRequestorFields(requestor, dao.getDate());

       if (isNew && (requestor.getType() == 0)) {
            addError(ROIClientErrorCodes.REQUESTOR_TYPE_IS_MANDATORY);
        }

       validateEPN(user.getEpnEnabled(), user.getEpnPrefix(), requestor.getEpn());

       return hasNoErrors();
    }

   /**
    * This method validates the requestor details to be updated
    * @param requestor requestor details to be validate
    * @param old requestor details
    * @param dao Requestor DAO to be used for validation
    * @param isNew  decides validation decision for Update/Create
     *              isNew = true for Requestor creation validation
     *              isNew = false for Requestor update validation
    * @return validation result (true/false)
    */
    public boolean validateRequestor(Requestor requestor,
                                     Requestor old,
                                     RequestorDAO dao,
                                     boolean isNew,
                                     User user) {

        validateRequestorFields(requestor, dao.getDate());
        validateEPN(user.getEpnEnabled(), user.getEpnPrefix(), requestor.getEpn());

        return hasNoErrors();
    }

    /**
     * This method validates the requestor details to be deleted
     * @param id id of the requestor
     * @param dao Requestor DAO to be used for validation
     * @return validation result (true/false)
     */
    public boolean validateRequestorDeletion(long id, RequestorDAO dao) {

        long count = dao.getAssociatedRequestCount(id);

        if (count > 0) {
            addError(ROIClientErrorCodes.REQUESTOR_ASSOCIATED_WITH_REQUEST_IS_DELETED);
        }

        return hasNoErrors();
    }

    /**
     * This method is to validate duplicate requestor name
     * @param name requestor name
     * @return validation result (true/false)
     */
    public boolean validateName(String name) {

        if (StringUtilities.isEmpty(name)) {
            addError(ROIClientErrorCodes.REQUESTOR_NAME_IS_MANDATORY);
        }
        return hasNoErrors();
    }

    private void validateEPN(RequestorSearchCriteria criteria, User user) {

        if ((criteria.getEpn() != null)) {

            String epn = criteria.getEpn();
            validateEPN(user.getEpnEnabled(), user.getEpnPrefix(), epn);
            boolean error = user.getEpnEnabled()
                            ? StringUtilities.hasContent(user.getEpnPrefix())
                                ? epn.length() < (ROIConstants.EPN_MIN_LENGTH
                                                 + user.getEpnPrefix().length())
                                : epn.length() < ROIConstants.EPN_MIN_LENGTH
                            : true;

            if (error) {
                addError(ROIClientErrorCodes.REQUESTOR_EPN_SHOULD_HAVE_ATLEAST_THREE_CHARACTERS);
            }
        }
    }

    /**
     * This method validates the MatchCriteriaList
     * @param mcl
     * @return
     */
    public boolean validateMatchCriteriaList(MatchCriteriaList mcl) {

        if ((mcl.getMatchCriteria() == null) || (mcl.getMatchCriteria().size() == 0)) {
            addError(ROIClientErrorCodes.EMPTY_MATCH_CRITERIA_LIST);
            return false;
        }
        // commented to incorporate requirement changes to allow multi-select patients
//        if (user.getEpnEnabled() && (mcl.getMatchCriteria().size() > 1)) {
//            addError(ROIClientErrorCodes.MATCH_CRITERIA_LIST_INVALID);
//            return false;
//        }

        return validateMatchCriteria(mcl);
    }

    public boolean validateMatchCriteria(MatchCriteriaList mcl) {

        for (MatchCriteria mc : mcl.getMatchCriteria()) {

            if ((mc.getLastName() == null)
               && (mc.getFirstName() == null)
               && (mc.getSsn() == null)
               && (mc.getEpn() == null)
               && (mc.getDob() == null)
               && (mc.getMrn() == null)
               && (mc.getFacility() == null)) {

                addError(ROIClientErrorCodes.MATCH_CRITERIA_LIST_CONTAINS_EMPTY_MATCH_CRITERIA);
                return false;
            }
        }

        return hasNoErrors();
    }

    private void validateRequestorFields(Requestor req, Date dt) {

        validateFields(req);
        Date dob = req.getDob();
        if ((dob != null) && new Timestamp(dob.getTime()).after(dt)) {
            addError(ROIClientErrorCodes.REQUESTOR_PATIENT_DOB_CONTAINS_FUTURE_DATE);
        }
        Address main = req.getMainAddress();
        if (main != null) {
            validateFields(main);
        }

        Address alt = req.getAltAddress();
        if (alt != null) {
            validateFields(alt);
        }
    }

    public boolean validateRequestorId(long requestorId) {

        if (requestorId <= 0) {
            addError(ROIClientErrorCodes.INVALID_REQUESTOR_ID);
        }

        return hasNoErrors();
    }

    public boolean validateRequestorLetterId(long requestorLetterId) {

        if (requestorLetterId <= 0) {
            addError(ROIClientErrorCodes.INVALID_ID);
            return false;
        }

        return hasNoErrors();
    }

    /**
     * This method validates the Refund details
     * @param requestorRefund
     * @return boolean
     */
    public boolean validateRefundDetails(RequestorRefund requestorRefund, RequestorDAO dao) {

        DecimalFormat df = new DecimalFormat("###.##");
        double balance = 0.0;
        
        if (requestorRefund == null) {
            addError(ROIClientErrorCodes.INVALID_REFUND_DETAILS);
            return hasNoErrors();
        }
        if (requestorRefund.getRefundAmount() <= 0) {
            addError(ROIClientErrorCodes.INVALID_REFUND_AMOUNT);
        }
        if (requestorRefund.getRequestorId() <= 0) {
            addError(ROIClientErrorCodes.INVALID_REQUESTOR_ID);
        }

        RequestorCharges requestorCharges =
                dao.retrieveRequestorCharges(requestorRefund.getRequestorId());

        balance = Double.valueOf(df.format(requestorCharges.getTotalUnapplied()
                                        - requestorCharges.getInvoiceBalance()));
        if (requestorCharges.getInvoiceBalance() > requestorCharges.getTotalUnapplied()
                || (balance < requestorRefund.getRefundAmount())
                || requestorCharges.getUnAppliedPayment() < requestorRefund.getRefundAmount()) {
            
            addError(ROIClientErrorCodes.CREATE_REQUESTOR_REFUND_OPERTARION_FAILED);
        }
        return hasNoErrors();
    }

    public boolean validateRequestorInvoiceInfo(RegeneratedInvoiceInfo regeneratedInvoiceInfo) {

        if (regeneratedInvoiceInfo == null || regeneratedInvoiceInfo.getId() <= 0) {
            addError(ROIClientErrorCodes.INVALID_ID);
        }
        if (regeneratedInvoiceInfo == null || regeneratedInvoiceInfo.getProperties() == null) {
            addError(ROIClientErrorCodes.INVALID_PROPERTIES);
        }

        return hasNoErrors();
    }

    public boolean validateStatementCriteria(RequestorStatementCriteria statementCriteria) {

        if (statementCriteria == null) {

            addError(ROIClientErrorCodes.INVALID_STATEMENT_CRITERIA);
            return false;
        }

        if (statementCriteria.getRequestorId() <= 0) {
            addError(ROIClientErrorCodes.INVALID_REQUESTOR_ID);
        }

        if (statementCriteria.getTemplateFileId() == 0) {
            addError(ROIClientErrorCodes.INVALID_TEMPLATE_ID);
        }
        if (statementCriteria.getDateRange() == null) {
            addError(ROIClientErrorCodes.INVALID_DATE_RANGE);
        }

        return hasNoErrors();
    }
}
