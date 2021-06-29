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

package com.mckesson.eig.roi.admin.service;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import com.mckesson.eig.roi.admin.dao.AdminLoVDAO;
import com.mckesson.eig.roi.admin.dao.BillingTemplateDAO;
import com.mckesson.eig.roi.admin.dao.BillingTierDAO;
import com.mckesson.eig.roi.admin.dao.DeliveryMethodDAO;
import com.mckesson.eig.roi.admin.dao.InvoiceDueDaysDAO;
import com.mckesson.eig.roi.admin.dao.LetterTemplateDAO;
import com.mckesson.eig.roi.admin.dao.LetterTemplateDAOImpl;
import com.mckesson.eig.roi.admin.dao.ReasonDAO;
import com.mckesson.eig.roi.admin.dao.RequestorTypeDAO;
import com.mckesson.eig.roi.admin.model.AdminLoV;
import com.mckesson.eig.roi.admin.model.DeliveryMethod;
import com.mckesson.eig.roi.admin.model.Designation;
import com.mckesson.eig.roi.admin.model.DocTypeDesignations;
import com.mckesson.eig.roi.admin.model.DocTypeRelation;
import com.mckesson.eig.roi.admin.model.InvoiceDueDays;
import com.mckesson.eig.roi.admin.model.LetterTemplate;
import com.mckesson.eig.roi.admin.model.Note;
import com.mckesson.eig.roi.admin.model.OutputServerProperties;
import com.mckesson.eig.roi.admin.model.Reason;
import com.mckesson.eig.roi.admin.model.RelatedBillingTemplate;
import com.mckesson.eig.roi.admin.model.RelatedBillingTier;
import com.mckesson.eig.roi.admin.model.RequestorType;
import com.mckesson.eig.roi.admin.model.Weight;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.base.service.BaseROIValidator;
import com.mckesson.eig.roi.hpf.model.UserSecurity;
import com.mckesson.eig.roi.supplementary.model.AttachmentSequenceList;
import com.mckesson.eig.roi.utils.AccessFileLoader;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * This class performs the business validation for all the ROI admin services
 *
 * @author OFS
 * @date Jul 28, 2009
 * @since HPF 13.1 [ROI]
 */
public class ROIAdminServiceValidator
extends BaseROIValidator {
    
    private static final OCLogger LOG = new OCLogger(LetterTemplateDAOImpl.class);

    /**
     * This method validates delivery method create/update Operation
     *
     * @param deliveryMethod DeliveryMethod to be validated
     * @param isNew true implies creation of new delivery method;
     *              false implies updating an existing delivery method
     * @param dao DAO to be used for validation
     * @return true if there are no validation failures
     */
    public boolean validateDeliveryMethod(DeliveryMethod deliveryMethod,
                                          DeliveryMethodDAO dao,
                                          boolean isNew) {

        if (!validateDeliveryMethodFields(deliveryMethod)) {
            return false;
        }

        if (isNew) {
            if (dao.getDeliveryMethodByName(deliveryMethod.getName()) != null) {
                addError(ROIClientErrorCodes.DELIVERY_METHOD_NAME_IS_NOT_UNIQUE,
                         deliveryMethod.getName());
            }
        } else {

            DeliveryMethod orgDeliveryMethod = dao.
                                                 getDeliveryMethodByName(deliveryMethod.getName());
            if ((orgDeliveryMethod != null)
                                && (orgDeliveryMethod.getId() != deliveryMethod.getId())) {
                addError(ROIClientErrorCodes.DELIVERY_METHOD_NAME_IS_NOT_UNIQUE);
            }
        }
        return hasNoErrors();
    }

    /**
     * This method validates delivery method name and URL.
     *
     * @param deliveryMethod DeliveryMethod to be validated
     * @return true if there are no validation failures
     */
    private boolean validateDeliveryMethodFields(DeliveryMethod deliveryMethod) {

        if (deliveryMethod == null) {
            addError(ROIClientErrorCodes.DELIVERY_METHOD_OPERATION_FAILED);
            return false;
         }

        validateFields(deliveryMethod);

        return hasNoErrors();
    }


    /**
     * This method validates the unit per measure value of weight.
     *
     * @param weight
     * @return true if validation is successful validation otherwise false.
     */
    public boolean validateWeight(Weight weight) {

        validateFields(weight);
        if (((weight.getUnitPerMeasure() <= ROIConstants.WEIGHT_MIN_NO_PAGES) || (weight
                .getUnitPerMeasure() > ROIConstants.WEIGHT_MAX_NO_PAGES))) {

            addError(ROIClientErrorCodes.WEIGHT_INVALID_PAGE_VALUE);
        }
        return hasNoErrors();
    }

    /**
     * This method validates reason create/update Operation
     *
     * @param reason Reason to be validated
     * @param dao DAO to be used for validation
     * @param isNew true implies creation of new reason
     *              false implies updating an existing reason
     * @return true if there are no validation failures
     */
    public boolean validateReason(Reason reason, ReasonDAO dao, boolean isNew) {

        if (!validateReasonFields(reason)) {
            return false;
        }

        if ((ROIConstants.REQUEST_REASON.equalsIgnoreCase(reason.getType()))
            || (ROIConstants.ADJUSTMENT_REASON.equalsIgnoreCase(reason.getType()))) {

            validateRequestAndAdjustmentReason(reason, dao, isNew);
        }

        if (ROIConstants.STATUS_REASON.equalsIgnoreCase(reason.getType())) {
            validateStatusReason(reason, dao, isNew);
        }

        return hasNoErrors();
    }

    /**
     * This method validates RequestAndAdjustment reason create/update Operation
     *
     * @param reason RequestAndAdjustmentReason to be validated
     * @param dao DAO to be used for validation
     * @param isNew true implies creation of new RequestAndAdjustment reason
     *              false implies updating an existing RequestAndAdjustment reason
     * @return true if there are no validation failures
     */
    private boolean validateRequestAndAdjustmentReason(Reason reason,
                                                       ReasonDAO dao,
                                                       boolean isNew) {

        Reason origReason = dao.getReasonByName(reason.getName(), reason.getType());

        if (isNew && (origReason != null)) {

            if (ROIConstants.REQUEST_REASON.equalsIgnoreCase(origReason.getType())) {
               addError(ROIClientErrorCodes.REQUEST_REASON_NAME_IS_NOT_UNIQUE);
            } else {
               addError(ROIClientErrorCodes.ADJUSTMENT_REASON_NAME_IS_NOT_UNIQUE);
            }
        }

        if (!isNew && (origReason != null) && (reason.getId() != origReason.getId())) {

            if (ROIConstants.REQUEST_REASON.equalsIgnoreCase(origReason.getType())) {
                addError(ROIClientErrorCodes.REQUEST_REASON_NAME_IS_NOT_UNIQUE);
            } else {
                addError(ROIClientErrorCodes.ADJUSTMENT_REASON_NAME_IS_NOT_UNIQUE);
            }
        }

        Reason originalReason = dao.getReasonByDispText(reason.getDisplayText(), reason.getType());
        validateDisplayText(originalReason,
                            ROIClientErrorCodes.REASON_DISPLAY_TEXT_IS_NOT_UNIQUE,
                            reason,
                            isNew);


        return hasNoErrors();
    }

    /**
     * This method validates status reason create/update Operation
     *
     * @param reason StatusReason to be validated
     * @param dao DAO to be used for validation
     * @param isNew true implies creation of new status reason
     *              false implies updating an existing status reason
     * @return true if there are no validation failures
     */
    private boolean validateStatusReason(Reason reason, ReasonDAO dao, boolean isNew) {

        if (reason.getStatus() == ROIConstants.STATUS_NOT_APPLICABLE_ID) {
            addError(ROIClientErrorCodes.STATUS_REASON_STATUS_FIELD_IS_BLANK);
        }

        Reason origStatusReason = dao.getStatusReasonByName(reason);

        if (isNew && (origStatusReason != null)) {
            addError(ROIClientErrorCodes.STATUS_REASON_NAME_IS_NOT_UNIQUE);
        }
        if (!isNew && (origStatusReason != null)
                          && (reason.getId() != origStatusReason.getId())) {
            addError(ROIClientErrorCodes.STATUS_REASON_NAME_IS_NOT_UNIQUE);
        }

        Reason originalStatusReason = dao.getStatusReasonByDispText(reason);
        validateDisplayText(originalStatusReason,
                            ROIClientErrorCodes.STATUS_REASON_DISPLAY_TEXT_IS_NOT_UNIQUE,
                            reason,
                            isNew);

        return hasNoErrors();
    }

    /**
     * This method validates reason DisplayText field for create/update Operation
     *

     * @param originalReason Reason which is retrieved from database
     * @param er ROIClientErrorCodes corresponding to reason
     * @param reason Reason to be validated
     * @param isNew true implies creation of new reason
     *              false implies updating an existing reason
     */
    private void validateDisplayText(Reason originalReason,
                                     ROIClientErrorCodes er,
                                     Reason reason,
                                     boolean isNew) {

        if (isNew && (originalReason != null)) {
            addError(er);
        }
        if (!isNew && (originalReason != null)
                          && (reason.getId() != originalReason.getId())) {
            addError(er);
        }
    }

    /**
     * This method validates reason name and display text.
     *
     * @param reason Reason to be validated
     * @return true if there are no validation failures
     */
    private boolean validateReasonFields(Reason reason) {

        if (reason == null) {
            addError(ROIClientErrorCodes.REASON_OPERATION_FAILED);
            return false;
        }

        validateFields(reason);

        if (ROIConstants.REQUEST_REASON.equalsIgnoreCase(reason.getType())
            && !reason.getTpo()
            && !reason.getNonTpo()) {

            addError(ROIClientErrorCodes.REQUEST_REASON_ATTRIBUTE_IS_BLANK);
        }

        return hasNoErrors();
    }

    /**
     * This methods performs validation for RequestorType create or update
     *
     * @param isNew decides validation decision for create or update
     *              isNew is true for RequestorType create validation
     *              isNew is false for RequestorType update validation
     * @param rt RequestorType to be validated
     * @param dao RequestorTypeDAO to be used for validation
     * @param btdao BillingTierDAO to be used for validation
     * @return validation result true or false
     */
    public boolean validateRequestorType(RequestorTypeDAO dao,
                                         BillingTierDAO btDAO,
                                         BillingTemplateDAO btmpDAO,
                                         RequestorType rt,
                                         boolean isNew) {

        if (rt == null) {
            addError(ROIClientErrorCodes.REQUESTOR_TYPE_OPERATION_FAILED);
            return false;
        }

        if (StringUtilities.isEmpty(rt.getName())) {
            addError(ROIClientErrorCodes.REQUESTOR_TYPE_NAME_IS_BLANK);
            return false;
         }

        if (rt.getId() < 0) {
            RequestorType origRType = dao.retrieveRequestorType(rt.getId());
            if (!origRType.getName().equals(rt.getName())) {
                addError(ROIClientErrorCodes.REQUESTOR_TYPE_SEED_DATA_IS_MODIFIED);
                return hasNoErrors();
            }
        }

        if (!validateRequestorTypeFields(rt, btDAO, btmpDAO)) {
            return false;
        }

        if (isNew) {
            if (dao.getRequestorTypeByName(rt.getName()) != null) {
                addError(ROIClientErrorCodes.REQUESTOR_TYPE_NAME_IS_NOT_UNIQUE, rt.getName());
            }
        } else {
            RequestorType rType = dao.getRequestorTypeByName(rt.getName());
            if ((rType != null) && (rType.getId() != rt.getId())) {
                addError(ROIClientErrorCodes.REQUESTOR_TYPE_NAME_IS_NOT_UNIQUE, rt.getName());
            }
        }
        return hasNoErrors();
    }

    /**
     * This method validates the requestorTypeFields
     * @param rt to be validated
     * @return true for successful validation else false
     */
    private boolean validateRequestorTypeFields(RequestorType rt,
                                                BillingTierDAO btDAO,
                                                BillingTemplateDAO btmpDAO) {

        validateFields(rt);

        if ((rt.getRelatedBillingTier() == null) || (rt.getRelatedBillingTier().size() == 0)) {
            addError(ROIClientErrorCodes.REQUESTOR_TYPE_MUST_HAVE_HPF_AND_NON_HPF_BILLING_TIER);
            return false;
        }

        if (!validateBillingTierIds(rt, btDAO)) {
            return false;
        }

        for (RelatedBillingTier bt : rt.getRelatedBillingTier()) {

            if (bt.isIsHPF()
                && !btDAO.retrieveBillingTiersByMediaTypeName(ROIConstants.ELECTRONIC_MEDIATYPE)
                         .getBillingTiers().contains(bt.getBillingTierId())) {

                addError(ROIClientErrorCodes.REQUESTOR_TYPE_BTIER_NAME_IS_NOT_ELECTRONIC);

            }
        }

        if (StringUtilities.isEmpty(rt.getRv())) {
           addError(ROIClientErrorCodes.REQUESTOR_TYPE_MUST_HAVE_ONE_RECORD_VIEW);
           return false;
        }

        validateBillingTemplate(rt, btmpDAO);

        return hasNoErrors();
    }

    private boolean validateBillingTemplate(RequestorType rt, BillingTemplateDAO btmpDAO) {

        validateBillingTemplateIds(rt, btmpDAO);

        if (rt.getRelatedBillingTemplate() != null) {
            int count = 0;
            for (Iterator <RelatedBillingTemplate> i = rt.getRelatedBillingTemplate().
                                                                iterator();
                                                                i.hasNext();) {
                RelatedBillingTemplate rb = i.next();
                if (rb.isIsDefault()) {
                    count++;
                }
            }
            if (count > 1) {
                addError(ROIClientErrorCodes.
                         REQUESTOR_TYPE_BILLING_TEMPLATE_HAVE_MORE_THAN_ONE_DEFAULT);
            }
        }

        return hasNoErrors();
    }

    private boolean validateBillingTemplateIds(RequestorType rt, BillingTemplateDAO btmpDAO) {

        try {

            if (rt.getRelatedBillingTemplate() != null) {
                for (RelatedBillingTemplate rBtm : rt.getRelatedBillingTemplate()) {
                    btmpDAO.retrieveBillingTemplate(rBtm.getBillingTemplateId());
                }
            }
        } catch (ROIException e) {
            addError(ROIClientErrorCodes.INVALID_BILLING_TEMPLATE_ASSOCIATION);
            return false;
        }
        return hasNoErrors();
    }

    /**
     * This method validates the business rules for requestorTypeDelete
     * @param requestorTypeId
     * @return true for successful validation else false
     */
    public boolean validateRequestorTypeDeletion(long requestorTypeId, RequestorTypeDAO dao) {

        if (requestorTypeId < 0) {
            addError(ROIClientErrorCodes.REQUESTOR_TYPE_SEED_DATA_IS_DELETED);
            return false;
        }

        if (dao.getAssociatedRequestorCount(requestorTypeId) > 0) {
            addError(ROIClientErrorCodes.REQUESTOR_TYPE_ASSOCIATED_WITH_REQUESTOR_IS_DELETED);
        }
        return hasNoErrors();
    }

    public boolean validateLetterTemplate(LetterTemplate lt, LetterTemplateDAO dao, boolean isNew) {

        if (lt == null) {
            addError(ROIClientErrorCodes.LETTER_TEMPLATE_PROCESS_FAILED);
            return false;
        }

        if (lt.getDocId() == 0) {
            addError(ROIClientErrorCodes.LETTER_TEMPLATE_HAS_NO_ASSOCIATED_DOCUMENT);
        }
        if (validateLetterTemplateFields(lt)) {

            if (isNew) {
                if (dao.getLetterTemplateByName(lt.getName(), lt.getLetterType()) != null) {
                    addError(ROIClientErrorCodes.LETTER_TEMPLATE_NAME_IS_NOT_UNIQUE);
                }
            } else {
                LetterTemplate lTemplate = dao.getLetterTemplateByName(lt.getName(),
                        lt.getLetterType());
                if ((lTemplate != null) && (lTemplate.getTemplateId() != lt.getTemplateId())) {
                    addError(ROIClientErrorCodes.LETTER_TEMPLATE_NAME_IS_NOT_UNIQUE);
                }
            }
        }

        return hasNoErrors();
    }

    private boolean validateLetterTemplateFields(LetterTemplate lt) {

        validateFields(lt);

        if (ROIConstants.LETTER_TYPE.equalsIgnoreCase(lt.getLetterType())) {
            if (lt.getIsDefault()) {
                addError(ROIClientErrorCodes.LETTER_TEMPLATE_LETTER_TYPE_OTHER_CHOOSE_DEFAULT);
                return false;
            }
        }
        return hasNoErrors();
    }

    /**
     * This method validates doc types
     * @param docTypes list of designation
     * @return true/ false
     */
    public boolean validateDocType(DocTypeDesignations docTypes) {

        String type = DocTypeRelation.RealtionType.authorize.toString();
        for (Designation desi : docTypes.getDesignation()) {

            if (type.equalsIgnoreCase(desi.getType()) && (desi.getDocTypeIds() != null)) {

                if (desi.getDocTypeIds().size() > 1) {
                    addError(ROIClientErrorCodes.AUTHORIZATION_DOC_TYPE_HAVE_MORE_THAN_ONE);
                }
            }
        }

        return hasNoErrors();
    }

    public boolean validateNote(Note note, AdminLoVDAO dao, boolean isNew) {

        if (note == null) {
            addError(ROIClientErrorCodes.NOTE_OPERATION_FAILED);
            return false;
        }

        if (!validateFields(note)) {
            return false;
        }

        AdminLoV origLov = dao.getNoteByName(note.getName());

        if (isNew && (origLov != null)) {
            addError(ROIClientErrorCodes.NOTE_NAME_IS_NOT_UNIQUE, note.getName());
        } else if ((origLov != null) && (origLov.getId() != note.getId())) {
            addError(ROIClientErrorCodes.NOTE_NAME_IS_NOT_UNIQUE);
        }

        return hasNoErrors();
    }

    private boolean validateBillingTierIds(RequestorType rt, BillingTierDAO btDAO) {

        boolean isHpfBillingTier = false;
        boolean isNonHpfBillingTier = false;

        for (RelatedBillingTier rtBt : rt.getRelatedBillingTier()) {

            if (rtBt.isIsHPF()) {
                isHpfBillingTier = true;
                break;
            }
            isHpfBillingTier = false;
        }

        if (!isHpfBillingTier) {
            addError(ROIClientErrorCodes.REQUESTOR_TYPE_MUST_HAVE_ONE_HPF_BILLING_TIER);
            return false;
        }

        for (RelatedBillingTier rtBt : rt.getRelatedBillingTier()) {

            if (rtBt.isIsSupplemental()) {
                isNonHpfBillingTier = true;
                break;
            }
            isNonHpfBillingTier = false;
        }

        if (!isNonHpfBillingTier) {
            addError(ROIClientErrorCodes.REQUESTOR_TYPE_MUST_HAVE_ONE_NON_HPF_BILLING_TIER);
            return false;
        }

        try {
            for (RelatedBillingTier rtBt : rt.getRelatedBillingTier()) {
				btDAO.retrieveBillingTier(rtBt.getBillingTierId());
            }
        } catch (ROIException e) {
            addError(ROIClientErrorCodes.INVALID_BILLING_TIER_ASSOCIATION);
            return false;
        }
        return hasNoErrors();
    }

    /**
     * This method is to validate the designation type
     * @param designation
     * @return true if there are no validation failures
     */
    public boolean validateDesignationType(String designation) {

        if (designation == null) {
            addError(ROIClientErrorCodes.DESIGNATION_FOR_DOCUMENT_TYPE_IS_NULL);
            return false;
        }

        return hasNoErrors();
    }

    /**
     * This method check whether letter template is available for the letter type
     * @param letterType
     * @return true if there are no validation failures
     */
    public boolean validateLetterTemplateType(String letterType) {

        if (letterType != null) {

            letterType = letterType.toLowerCase();

            if (!((LetterTemplate.LetterType.coverletter.toString().equals(letterType))
                    || (LetterTemplate.LetterType.prebill.toString().equals(letterType))
                    || (LetterTemplate.LetterType.invoice.toString().equals(letterType))
                    || (LetterTemplate.LetterType.requestorstatement.toString().equals(letterType))
                    || (LetterTemplate.LetterType.other.toString().equals(letterType)))) {

                addError(ROIClientErrorCodes.INVALID_LETTER_TEMPLATE_TYPE);
                return false;
            }
        }
        return hasNoErrors();
    }

    public boolean validateOutputServerProperties(OutputServerProperties outputServerProperties) {

        if (outputServerProperties == null) {

            addError(ROIClientErrorCodes.DELIVERY_METHOD_OPERATION_FAILED);
            return false;
         }
        validateFields(outputServerProperties);
        return hasNoErrors();
    }

    /**
     * This method validates if the attachment location is valid or not.
     *
     * @param attachmentLoc
     * @return
     */
    public boolean validateAttachmentLocation(String attachmentLoc) {

        if (attachmentLoc == null) {

            addError(ROIClientErrorCodes.ATTACHMENT_LOCATION_NULL);
            return false;
        }

        return validateAttachmentDirectory(attachmentLoc);
    }

    /**
     * TODO - Check if the method exists in the FileUtils and DirUtils to be checked
     * in by Eric
     *
     * This method validates if the java api is able to create and delete the
     * files in the attachment location configured. This method also creates
     * the directories if the directory is not available and it is still a valid
     * shared location.
     *
     * @param attachmentLoc
     * @return
     */
    private boolean validateAttachmentDirectory(String attachmentLoc) {

        File dir = null;
        String path = null;
        try {
		//DE7315 External Control of File Name or Path
            dir = AccessFileLoader.getFile(attachmentLoc);
        } catch (IOException e) {
                 LOG.error("Exception in validateAttachmentDirectory "+e.getLocalizedMessage());
        }
        if(dir != null) {
            path = dir.getAbsolutePath();
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    addError(ROIClientErrorCodes.INSUFICIENT_PERMISION_ON_ATTACHMENT_FOLDER);
                    return false;
                }
            } else if (!dir.isDirectory()) {
                addError(ROIClientErrorCodes.INSUFICIENT_PERMISION_ON_ATTACHMENT_FOLDER);
                return false;
            } else if (!dir.canRead() || !dir.canWrite()) {
                addError(ROIClientErrorCodes.INSUFICIENT_PERMISION_ON_ATTACHMENT_FOLDER);
                return false;
            }
        }
        return validateFileCreateandDelete(path);
    }

    /**
     * Creates a file in the path and deletes the same to confirm the directory access.
     *
     * @param path
     * @return
     */
    private boolean validateFileCreateandDelete(String path) {

        File createdFile = null;
        try {
            createdFile = File.createTempFile("test", ".roi", AccessFileLoader.getFile(path));
        } catch (IOException e) {
            addError(ROIClientErrorCodes.INSUFICIENT_PERMISION_ON_ATTACHMENT_FOLDER);
            return false;
        }
        if (!createdFile.delete()) {
            addError(ROIClientErrorCodes.INSUFICIENT_PERMISION_ON_ATTACHMENT_FOLDER);
            return false;
        }

        return true;
    }

    public boolean validateInvoiceDueDays(InvoiceDueDays invoiceDue,
                                          String userId,
                                          InvoiceDueDaysDAO dao) {

        if (!validateSecurityRights(userId, dao)) {
            return false;
        }

        validateFields(invoiceDue);
        if (((invoiceDue.getInvoiceDueDays()) < ROIConstants.INVOICE_DUE_MIN_NO_DAYS)
                || ((invoiceDue.getInvoiceDueDays()) > ROIConstants.INVOICE_DUE_MAX_NO_DAYS)) {

            addError(ROIClientErrorCodes.INVOICE_INVALID_DUE_DAYS_VALUE);
        }
        return hasNoErrors();
    }

    public boolean validateSecurityRights(String userId, ROIDAO dao) {

      UserSecurity userSecurity = dao.retrieveROIUserSecurity(userId);

      if (null == userSecurity) {
          addError(ROIClientErrorCodes.USER_HAS_NO_SECURITY_RIGHTS);
          return false;
      }
      return true;
    }

 /**
     * This method validates if the attachment Info is valid or not.
     *
     * @param mrn
     * @param encounter
     * @param facilityCode
     * @return boolean
     */
    public boolean validateAttachmentInfos(String mrn,String facilityCode,String encounter) {

        if (mrn == null) {
            addError(ROIClientErrorCodes.ROI_ATTACHMENT_MRN_CANNOT_BE_NULL);
            return false;
        } else if (facilityCode == null) {
            addError(ROIClientErrorCodes.ROI_ATTACHMENT_FACILITY_CANNOT_BE_NULL);
            return false;
        } else if (encounter == null) {
            addError(ROIClientErrorCodes.ROI_ATTACHMENT_ENCOUNTER_CANNOT_BE_NULL);
            return false;
        }

        return true;
    }

    /**
     * This method validates if the attachment Info is valid or not.
     *
     * @param mrn
     * @param encounter
     * @param facilityCode
     * @return boolean
     */
    public boolean validateAttachmentInfos(String mrn, String facilityCode) {

        if (mrn == null) {
            addError(ROIClientErrorCodes.ROI_ATTACHMENT_MRN_CANNOT_BE_NULL);
            return false;
        } else if (facilityCode == null) {
            addError(ROIClientErrorCodes.ROI_ATTACHMENT_FACILITY_CANNOT_BE_NULL);
            return false;
        }
        return true;
    }

    /**
     * This method validates id.
     *
     * @param id
     * @return boolean
     */
    public boolean validateAttachmentIds(AttachmentSequenceList attachments) {

        if (null == attachments
                || null == attachments.getAttachmentIds()
                || attachments.getAttachmentIds().size() <= 0) {

            addError(ROIClientErrorCodes.INVALID_ID);
        }
        return hasNoErrors();
    }

}
