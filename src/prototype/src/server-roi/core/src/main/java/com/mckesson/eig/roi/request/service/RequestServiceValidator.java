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

package com.mckesson.eig.roi.request.service;


import java.util.Collection;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.base.service.BaseROIValidator;
import com.mckesson.eig.roi.request.model.RequestCore;
import com.mckesson.eig.roi.request.model.RequestCoreSearchCriteria;
import com.mckesson.eig.roi.request.model.RequestEvent;
import com.mckesson.eig.roi.request.model.SaveRequestPatientsList;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 * @author OFS
 * @date   Feb 24,2009
 * @since  HPF 13.1 [ROI]; Jul 17, 2008
 */
public class RequestServiceValidator
extends BaseROIValidator {

    /**
     * This method will validate the input request event fields for valid entries
     * @param event Request Event
     * @return True/False
     */
    public boolean validRequestEventFields(RequestEvent event) {

        if (event.getRequestId() <= 0) {
            addError(ROIClientErrorCodes.INVALID_REQUEST_ID, String.valueOf(event.getRequestId()));
            return false;
        }

        if (StringUtilities.isEmpty(event.getDescription())) {
            addError(ROIClientErrorCodes.EMPTY_REQUEST_EVENT_DESCRIPTION);
            return false;
        }
        return hasNoErrors();
    }


    /**
     * This method validates the RequestCoreDetail model
     * @param RequestCoreDetail Request details
     * @return validation result (true/false)
     */
    public boolean validateRequestCore(RequestCore request) {

        if (request == null) {
            addError(ROIClientErrorCodes.REQUEST_MODEL_IS_EMPTY);
            return false;
        }

        validateFields(request);

        if (null == request.getRequestorDetail()) {
            addError(ROIClientErrorCodes.REQUESTOR_MODEL_IS_EMPTY);
            return false;
        }

        validateFields(request.getRequestorDetail());
        return hasNoErrors();
    }

    /**
     * This method validates the RequestCoreDetail model
     * @param RequestCoreDetail Request details
     * @return validation result (true/false)
     */
    public boolean validateUpdateRequestCore(RequestCore request) {

        if (request == null) {
            addError(ROIClientErrorCodes.REQUEST_MODEL_IS_EMPTY);
            return false;
        }
        validateFields(request);

        if (request.getId() <= 0) {
            addError(ROIClientErrorCodes.INVALID_REQUEST_ID);
            return false;
        }

        if (null == request.getRequestorDetail()) {
            addError(ROIClientErrorCodes.REQUESTOR_MODEL_IS_EMPTY);
            return false;
        }
        validateFields(request.getRequestorDetail());

        return hasNoErrors();
    }

    /**
     * This method validates the RequestCoreDetail model
     * @param RequestCoreDetail Request details
     * @return validation result (true/false)
     */
    public boolean validateRequestPatient(SaveRequestPatientsList patients) {

        if (null == patients
                || (isInValidCollection(patients.getUpdatePatients()))
                        && isInValidCollection(patients.getUpdateEncounters())
                        && isInValidCollection(patients.getUpdateDocuments())
                        && isInValidCollection(patients.getUpdateVersions())
                        && isInValidCollection(patients.getUpdatePages())
                        && isInValidCollection(patients.getUpdateSupplementalDocument())
                        && isInValidCollection(patients.getUpdateSupplementalAttachement())
                        && patients.getDeletePatient() == null) {

            addError(ROIClientErrorCodes.REQUEST_PATIENT_IS_EMPTY);
            return false;
        }

        if (patients.getRequestId() <= 0) {
            addError(ROIClientErrorCodes.REQUEST_CORE_ID_IS_EMPTY);
        }

        return hasNoErrors();
    }

    /**
     * validates whether the given collection is null
     * or contains any object
     *
     * @param collection
     * @return true if given collection is valid and false otherwise
     */
    private boolean isInValidCollection(Collection<? extends BaseModel> collection) {

        if (null == collection || collection.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * This method validates the RequestCoreDetail model
     * @param RequestCoreDetail Request details
     * @return validation result (true/false)
     */
    public boolean validateUpdatePatient(SaveRequestPatientsList patients) {

        if (null == patients
                || patients.getDeletePatient() == null) {

            addError(ROIClientErrorCodes.REQUEST_PATIENT_IS_EMPTY);
            return false;
        }

        if (patients.getRequestId() <= 0) {
            addError(ROIClientErrorCodes.REQUEST_CORE_ID_IS_EMPTY);
        }
        return hasNoErrors();
    }

    /**
     * This method validates the RequestCoreSearchCriteria model
     * @param RequestCoreSearchCriteria details
     * @return validation result (true/false)
     */
    public boolean validateSearchCriteria(RequestCoreSearchCriteria searchCriteria) {

        if (null == searchCriteria) {

            addError(ROIClientErrorCodes.REQUEST_PATIENT_IS_EMPTY);
            return false;
        }

        if (((null == searchCriteria.getPatientDob()
                    && null != searchCriteria.getFacility())
                || (null != searchCriteria.getPatientDob()
                        && null == searchCriteria.getFacility()))
                 && isCriteriaEmpty(searchCriteria)) {

            addError(ROIClientErrorCodes.REQUESTSEARCH_CANNOT_BE_PERFORMED_WITH_GIVEN_CRITERIA);
            return false;
        }

        if (null == searchCriteria.getPatientDob()
                && null == searchCriteria.getFacility()
                && isCriteriaEmpty(searchCriteria)) {

            addError(ROIClientErrorCodes.REQUESTSEARCH_CANNOT_BE_PERFORMED_WITH_GIVEN_CRITERIA);
            return false;
        }
        return true;
    }

    /**
     * @param searchCriteria
     */
    private boolean isCriteriaEmpty(RequestCoreSearchCriteria searchCriteria) {

        if (searchCriteria.hasRequestBasedCriteria()
                || searchCriteria.hasPatientsBasedCriteria()
                || searchCriteria.hasRequestorBasedcriteria()
                || searchCriteria.hasInvoiceBasedCriteria()
                || searchCriteria.hasEncounterCriteria()) {

            return false;
        }
        return true;
    }
}
