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


import java.util.Iterator;
import java.util.List;

import com.mckesson.eig.roi.admin.dao.BillingTemplateDAO;
import com.mckesson.eig.roi.admin.dao.BillingTierDAO;
import com.mckesson.eig.roi.admin.dao.FeeTypeDAO;
import com.mckesson.eig.roi.admin.dao.MediaTypeDAO;
import com.mckesson.eig.roi.admin.dao.PaymentMethodDAO;
import com.mckesson.eig.roi.admin.dao.TaxPerFacilityDAO;
import com.mckesson.eig.roi.admin.model.BillingTemplate;
import com.mckesson.eig.roi.admin.model.BillingTier;
import com.mckesson.eig.roi.admin.model.FeeType;
import com.mckesson.eig.roi.admin.model.MediaType;
import com.mckesson.eig.roi.admin.model.PageLevelTier;
import com.mckesson.eig.roi.admin.model.PaymentMethod;
import com.mckesson.eig.roi.admin.model.TaxPerFacility;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.base.service.BaseROIValidator;
import com.mckesson.eig.roi.hpf.model.UserSecurity;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 * This class performs the business validation for all the billing admin services
 *
 * @author OFS
 * @date   Mar 16, 2009
 * @since  HPF 13.1 [ROI]; Mar 25, 2008
 */
public class BillingAdminServiceValidator
extends BaseROIValidator {

    /**
     * This method performs validation for Media Type create/update
     *
     * @param mediaType Media Type to be validated
     * @param isNew decides validation decision for Update/Create
     *              isNew = true for Media Type creation validation
     *              isNew = false for Media Type update validation
     * @param dao DAO to be used for validation
     * @return validation result (true/false)
     */
    public boolean validateMediaType(MediaType mediaType, boolean isNew, MediaTypeDAO dao) {

        if (!validateMediaTypeFields(mediaType)) {
            return false;
        }

        if (isNew) {
            if (dao.getMediaTypeByName(mediaType.getName()) != null) {
                addError(ROIClientErrorCodes.MEDIA_TYPE_NAME_IS_NOT_UNIQUE, mediaType.getName());
            }
        } else {
            if (mediaType.getId() < 0) {
                if (!dao.retrieveMediaType(mediaType.getId())
                                                     .getName().equals(mediaType.getName())) {

                    addError(ROIClientErrorCodes.MEDIA_TYPE_SEED_DATA_NAME_IS_BEING_EDITED);
                }
            }

            MediaType orgMediaType = dao.getMediaTypeByName(mediaType.getName());
            if ((orgMediaType != null) && (orgMediaType.getId() != mediaType.getId())) {
                addError(ROIClientErrorCodes.MEDIA_TYPE_NAME_IS_NOT_UNIQUE, mediaType.getName());
            }
        }

        return hasNoErrors();
    }

    /**
     * This method checks the common validation for Media Type operations
     *
     * @param mt Media Type to be validated
     * @return validation result (true/false)
     */
    private boolean validateMediaTypeFields(MediaType mt) {

        if (mt == null) {
            addError(ROIClientErrorCodes.MEDIA_TYPE_OPERATION_FAILED);
            return false;
        }

        validateFields(mt);

        return hasNoErrors();
    }

    /**
     * This method validates business rules for Media Type deletion
     *
     * @param mediaTypeId
     * @param dao DAO to be used for validation
     * @return validation status (true/false)
     */
    public boolean validateMediaTypeDeletion(long mediaTypeId, MediaTypeDAO dao) {

        if ((mediaTypeId < 0) && (dao.retrieveMediaType(mediaTypeId).getId() < 0)) {
            addError(ROIClientErrorCodes.MEDIA_TYPE_SEED_DATA_IS_BEING_DELETED);
            return false;
        }

        if (dao.getAssociatedBillingTierCount(mediaTypeId) > 0) {
            addError(ROIClientErrorCodes.MEDIA_TYPE_ASSOCIATED_WITH_BILLING_TIER);
        }

        return hasNoErrors();
    }

    /**
     * This method performs validation for fee type create/update
     *
     * @param feeType FeeType to be validated
     * @param isNew
     *            decides validation decision for Update or Create if isNew = true feeType create
     *            validation will take place if isNew = false feeType update validation will take
     *            place
     * @param dao DAO to be used for validation
     * @return true for successful validation else false
     */
    public boolean validateFeeType(FeeType feeType, boolean isNew, FeeTypeDAO dao) {

        if (!validateFeeType(feeType)) {
            return false;
        }

        // create feeType Validation
        if (isNew) {
            if (dao.getFeeTypeByName(feeType.getName()) != null) {
                addError(ROIClientErrorCodes.FEE_TYPE_NAME_IS_NOT_UNIQUE);
            }
         // update feeType validation
        } else {
            FeeType orgfeeType = dao.getFeeTypeByName(feeType.getName());
            if ((orgfeeType != null) && (orgfeeType.getId() != feeType.getId())) {
                addError(ROIClientErrorCodes.FEE_TYPE_NAME_IS_NOT_UNIQUE);
            }
        }

        return hasNoErrors();
    }

    /**
     * this method validates the feeType fields
     *
     * @param feeType feeType to be validated
     * @return true for successful validation else false
     */
    private boolean validateFeeType(FeeType feeType) {

        if (feeType == null) {
            addError(ROIClientErrorCodes.FEE_TYPE_PROCESS_FAILED);
            return false;
        }

        validateFields(feeType);

        String[] temp = String.valueOf(feeType.getChargeAmount()).split("\\.");
        if (temp.length >= 2) {
            if (!(StringUtilities.isValidLength(temp[0], ROIConstants.FEE_TYPE_AMOUNT_PREFIX)
            && StringUtilities.isValidLength(temp[1], ROIConstants.FEE_TYPE_AMOUNT_SUFFIX))) {
                addError(ROIClientErrorCodes.FEE_TYPE_INVALID_CHARGE_AMOUNT);
            }
       }

        return hasNoErrors();
    }

    /**
     * This method validates business rules for fee type deletion
     *
     * @param feeTypeId
     * @param dao DAO to be used for validation
     * @return true for successful validation else false
     */
    public boolean validateFeeTypeDeletion(long feeTypeId, FeeTypeDAO dao) {

        if (dao.getAssociatedBillingTemplateCount(feeTypeId) > 0) {
            addError(ROIClientErrorCodes.FEE_TYPE_ASSOCIATED_WITH_BILLING_TEMPLATE);
        }
        return hasNoErrors();
    }

    /**
     * This method performs validation for Payment Method create/update
     *
     * @param paymentMethod Payment Method to be validated
     * @param isNew  true when a new payment method needs to be validated
     *               false when updating an existing payment method needs to be validated
     * @param dao paymentMethodDAO
     * @return validation result (true/false)
     */
    public boolean validatePaymentMethod(PaymentMethod paymentMethod,
                                         PaymentMethodDAO dao,
                                         boolean isNew) {

        if (!validatePaymentMethodFields(paymentMethod)) {
            return false;
        }

         if (isNew) {
            if (dao.getPaymentMethodByName(paymentMethod.getName()) != null) {
               addError(ROIClientErrorCodes.PAYMENT_METHOD_NAME_IS_NOT_UNIQUE,
                        paymentMethod.getName());
            }

        } else {
            PaymentMethod orgPaymentMethod = dao.getPaymentMethodByName(paymentMethod.getName());

            if ((orgPaymentMethod != null) && (orgPaymentMethod.getId() != paymentMethod.getId())) {
                addError(ROIClientErrorCodes.PAYMENT_METHOD_NAME_IS_NOT_UNIQUE,
                         paymentMethod.getName());
            }
        }
        return hasNoErrors();
    }

    /**
     * This method validates Payment Method name and description
     *
     * @param paymentMethod Payment Method to be validated
     * @return validation result (true/false)
     */
    private boolean validatePaymentMethodFields(PaymentMethod paymentMethod) {

        if (paymentMethod == null) {
            addError(ROIClientErrorCodes.PAYMENT_METHOD_OPERATION_FAILED);
            return false;
        }

        validateFields(paymentMethod);

        return hasNoErrors();
    }



    /**
     * This method performs validation for Billing Template create/update
     *
     * @param billingTemplate Billing Template to be validated
     * @param isNew
     *            decides validation decision for Update/Create isNew = true for
     *            Billing Template creation validation isNew = false for Billing
     *            Template update validation
     * @param dao DAO to be used for validation
     * @return validation result (true/false)
     */
    public boolean validateBillingTemplate(BillingTemplate billingTemplate,
                                           boolean isNew,
                                           BillingTemplateDAO dao) {

        if (!validateBillingTemplateFields(billingTemplate)) {
            return false;
        }

        if (isNew) {
            if (dao.getBillingTemplateByName(billingTemplate.getName()) != null) {
                addError(ROIClientErrorCodes.BILLING_TEMPLATE_NAME_IS_NOT_UNIQUE,
                         billingTemplate.getName());
            }
        } else {

                BillingTemplate origBT = dao.getBillingTemplateByName(billingTemplate.getName());
                if ((origBT != null) && (origBT.getId() != billingTemplate.getId())) {
                    addError(ROIClientErrorCodes.BILLING_TEMPLATE_NAME_IS_NOT_UNIQUE);
                }
        }

        return hasNoErrors();
    }

    /**
     * This method validates business rules for fee type deletion
     *
     * @param billingTemplateId
     * @param dao DAO to be used for validation
     * @return true for successful validation else false
     */
    public boolean validateBillingTemplateDeletion(long billingTemplateId, BillingTemplateDAO dao) {

        if (dao.getAssociatedRequestorTypeCount(billingTemplateId) > 0) {
            addError(ROIClientErrorCodes.BILLING_TEMPLATE_IS_ASSOCIATED_WITH_REQUESTOR_TYPE);
        }

        return hasNoErrors();
    }

    /**
     * This method validates billing template name and desc.
     *
     * @param billingTemplate BillingTemplate to be validated
     * @return true if there are no validation failures
     */
    private boolean validateBillingTemplateFields(BillingTemplate billingTemplate) {

        if (billingTemplate == null) {
            addError(ROIClientErrorCodes.BILLING_TEMPLATE_PROCESS_FAILED);
            return false;
         }

        validateFields(billingTemplate);

        return hasNoErrors();
    }

    /**
     * This methods performs validation for Billing Tier create or update
     *
     * @param bt BillingTier to be validated
     * @param isNew decides validation decision for create or update
     *              isNew is true for BillingTier create validation
     *              isNew is false for BillingTier update validation
     * @param dao BillingTierDAO to be used for validation
     * @param mtdao MediaTypeDAO to be used for validation
     * @return validation result true or false
     */
    public boolean validateBillingTier(BillingTier bt,
                                       boolean isNew,
                                       BillingTierDAO dao,
                                       MediaTypeDAO mtdao) {

        if (!validateBillingTierFields(bt)) {
            return false;
        }

        if (isNew) {
            if (dao.retrieveBillingTierByName(bt.getName()) != null) {
                addError(ROIClientErrorCodes.BILLNG_TIER_NAME_IS_NOT_UNIQUE, bt.getName());
            }
            if (ROIConstants.MEDIA_TYPE_NAME.
                equalsIgnoreCase(mtdao.retrieveMediaType(bt.getMediaType().getId()).getName())) {

                addError(ROIClientErrorCodes.BILLING_TIER_ASSOCIATED_WITH_NON_HPF);
            }
        } else {

            BillingTier old = dao.retrieveBillingTier(bt.getId());

            if (bt.getId() < 0) {

                if (!old.getName().equals(bt.getName())) {
                   addError(ROIClientErrorCodes.BILLING_TIER_SEED_DATA_NAME_IS_BEING_EDITED);
                }
                if (old.getMediaType().getId() != bt.getMediaType().getId()) {
                    addError(ROIClientErrorCodes.BILLING_TIER_SEED_DATA_MEDIA_TYPE_IS_BEING_EDITED);
                }
            }
            checkElectronicBillingTierUpdation(dao, old, bt);

            BillingTier btr = dao.retrieveBillingTierByName(bt.getName());
            if ((btr != null) && (btr.getId() != bt.getId())) {
                addError(ROIClientErrorCodes.BILLNG_TIER_NAME_IS_NOT_UNIQUE, bt.getName());
            }
        }
        return hasNoErrors();
    }

    /**
     * This method validates business rules for Billing Tier validation
     *
     * @param billingTierId
     * @param dao DAO to be used for validation
     * @return true for successful validation else false
     */
    public boolean validateBillingTierDeletion(long billingTierId, BillingTierDAO dao) {

        if ((billingTierId < 0) && (dao.retrieveBillingTier(billingTierId).getId() < 0)) {
            addError(ROIClientErrorCodes.SEED_DATA_IS_BEING_DELETED);
            return false;
        }

        if (dao.getAssociatedRequestorTypeCount(billingTierId) > 0) {
            addError(ROIClientErrorCodes.BILLING_TIER_ASSOCIATED_TO_REQUESTOR_TYPE_DELETED);
        }

        return hasNoErrors();
    }

    /**
     * This method validates the billingTier fields
     *
     * @param btr billingTier to be validated
     * @return true for successful validation else false
     */
    private boolean validateBillingTierFields(BillingTier btr) {

        if (btr == null) {
            addError(ROIClientErrorCodes.BILLING_TIER_OPERATION_FAILED);
            return false;
         }

        validateFields(btr);

        if ((btr.getBaseCharge() < 0) || (btr.getDefaultPageCharge() < 0)) {
            addError(ROIClientErrorCodes.BILLING_TIER_AMOUNTS_SHOULD_BE_POSITIVE);
            return false;
        }

        if (btr.getMediaType().getId() == 0) {
            addError(ROIClientErrorCodes.BILLING_TIER_DOESNOT_HAVE_MEDIA_TYPE_ASSOCIATION);
            return false;
        }

        if ((btr.getPageLevelTier() == null) || (btr.getPageLevelTier().size() == 0)) {
            addError(ROIClientErrorCodes.BILLING_TIER_ATLEAST_HAVE_ONE_PAGE_LEVEL_TIER);
            return false;
        }

        validateChargeAmountFields(btr);
        validatePageTierFields(btr);
        return hasNoErrors();
    }

    /**
     * This method validates the page tier fields
     *
     * @param btr billingTier to be validated
     */
    private void validatePageTierFields(BillingTier btr) {

        PageLevelTier pt;
        for (Iterator<PageLevelTier> itr = btr.getPageLevelTier().iterator(); itr.hasNext();) {
            pt = itr.next();

            if (pt.getPageCharge() < 0) {
                addError(ROIClientErrorCodes.BILLING_TIER_AMOUNTS_SHOULD_BE_POSITIVE);
            }

            String[] pagecharge = String.valueOf(pt.getPageCharge()).split("\\.");

            if (pagecharge.length >= 2) {
                if (!(StringUtilities.isValidLength(pagecharge[0],
                                                    ROIConstants.BILLING_TIER_PAGE_CHARGE_PREFIX)
                    && StringUtilities.isValidLength(pagecharge[1],
                                                 ROIConstants.BILLING_TIER_PAGE_CHARGE_SUFFIX))) {
                    addError(ROIClientErrorCodes.INVALID_PAGE_CHARGE_AMOUNT);
                }
            }

            if (pt.getPage() <= 0) {
                addError(ROIClientErrorCodes.INVALID_PAGE_NUMBER);
            }
        }
    }

    /**
     * This method validates the page tier charge amount fields
     *
     * @param btr billingTier to be validated
     */
    private void validateChargeAmountFields(BillingTier btr) {

        String[] temp = String.valueOf(btr.getBaseCharge()).split("\\.");

        if (temp.length >= 2) {
            if (!(StringUtilities.isValidLength(temp[0],
                                                ROIConstants.BILLING_TIER_BASE_CHARGE_PREFIX)
            && StringUtilities.isValidLength(temp[1],
                                             ROIConstants.BILLING_TIER_BASE_CHARGE_SUFFIX))) {
                addError(ROIClientErrorCodes.INVALID_BASE_CHARGE_AMOUNT);
            }
        }

        String[] defaultcharge = String.valueOf(btr.getDefaultPageCharge()).split("\\.");

        if (defaultcharge.length >= 2) {
            if (!(StringUtilities.isValidLength(defaultcharge[0],
                                                ROIConstants.BILLING_TIER_PAGE_CHARGE_PREFIX)
            && StringUtilities.isValidLength(defaultcharge[1],
                                             ROIConstants.BILLING_TIER_PAGE_CHARGE_SUFFIX))) {
                addError(ROIClientErrorCodes.INVALID_DEFAULT_PAGE_CHARGE_AMOUNT);
            }
        }
    }

    /**
     * This method checks whether an Electronic BillingTier
     * associated with RequestorType is modified to NonElectronic BillingTier
     *
     * @param dao BillingTierDAO
     * @param old Existing BillingTier values
     * @param bt  Updated BillingTier values
     */
    private void checkElectronicBillingTierUpdation(BillingTierDAO dao,
                                                    BillingTier old,
                                                    BillingTier bt) {

        if (dao.getAssociatedRequestorTypeCount(bt.getId()) > 0) {

            String oldMediaType = old.getMediaType().getName();
            String updatedMediaType = bt.getMediaType().getName();

            if (ROIConstants.ELECTRONIC_MEDIATYPE.equalsIgnoreCase(oldMediaType)
               && !ROIConstants.ELECTRONIC_MEDIATYPE.equalsIgnoreCase(updatedMediaType)) {

                addError(ROIClientErrorCodes.
                        ELECTRONIC_BILLING_TIER_ASSOCIATED_TO_REQUESTOR_TYPE_CANNOT_BE_MODIFIED);
            }
        }
    }

    /**
     * This method performs validation for SalesTax per Facility create/update
     *
     * @param salesTaxFacility to be validated
     * @param isNew decides validation decision for Update/Create
     *              isNew = true for salesTaxFacility creation validation
     *              isNew = false for salesTaxFacility update validation
     * @param dao DAO to be used for validation
     * @param userId 
     * @return validation result (true/false)
     */

    public boolean validateTaxPerFacility(TaxPerFacility salesTaxFacility,
                                          boolean isNew, 
                                          TaxPerFacilityDAO dao, String userId) {

        ROIAdminServiceValidator validator = new ROIAdminServiceValidator();
        
        if (!validator.validateSecurityRights(userId, dao)) {
            return false;
        }
        
        if (!validateSalesTaxFacilityFields(salesTaxFacility)) {
            return false;
        }

        if (isNew) {
            if (dao.getSalesTaxFacilityByCode(salesTaxFacility.getCode()) != null) {
                addError(ROIClientErrorCodes.SALES_TAX_FACILITY_CODE_IS_NOT_UNIQUE,
                         salesTaxFacility.getCode());
            }
        } else {

            TaxPerFacility orgMediaType = dao.getSalesTaxFacilityByCode(salesTaxFacility.getCode());
            if ((orgMediaType != null) && (orgMediaType.getId() != salesTaxFacility.getId())) {
                addError(ROIClientErrorCodes.SALES_TAX_FACILITY_CODE_IS_NOT_UNIQUE,
                         salesTaxFacility.getCode());
            }
        }

        return hasNoErrors();
    }

    /**
     * This method validates the SalesTaxFacility fields
     *
     * @param salesTaxFacility to be validated
     * @return true for successful validation else false
     */
    private boolean validateSalesTaxFacilityFields(TaxPerFacility salesTaxFacility) {

        if (salesTaxFacility == null) {
            addError(ROIClientErrorCodes.SALESTAX_OPERATION_FAILED);
            return false;
         }

        validateFields(salesTaxFacility);

        if (salesTaxFacility.getTaxPercentage() < 0) {
            addError(ROIClientErrorCodes.SALESTAX_PERCENTAGE_CONTAINS_INVALID_CHAR);
            return false;
        }
        return hasNoErrors();
    }
    
    /**
     * This method validates if the logged in user has the permission to release a document/attachment
     * 
     */
    public boolean validateSecurityRightsForOutputMethod(String userID, ROIDAO dao)
    {
        boolean hasSecurityRights = false;
        if(userID != null)
        {
           UserSecurity userSecurity = dao.retrieveROIUserSecurityForOutputType(userID);
           if(userSecurity != null)
           {
              hasSecurityRights = true;
           }
        }
        return hasSecurityRights;
    }
}
