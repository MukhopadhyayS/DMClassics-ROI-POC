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


import com.mckesson.eig.roi.admin.model.BillingPaymentInfo;
import com.mckesson.eig.roi.admin.model.BillingTemplate;
import com.mckesson.eig.roi.admin.model.BillingTemplatesList;
import com.mckesson.eig.roi.admin.model.BillingTier;
import com.mckesson.eig.roi.admin.model.BillingTiersList;
import com.mckesson.eig.roi.admin.model.FeeType;
import com.mckesson.eig.roi.admin.model.FeeTypesList;
import com.mckesson.eig.roi.admin.model.MediaType;
import com.mckesson.eig.roi.admin.model.MediaTypesList;
import com.mckesson.eig.roi.admin.model.PaymentMethod;
import com.mckesson.eig.roi.admin.model.PaymentMethodList;
import com.mckesson.eig.roi.admin.model.TaxPerFacility;
import com.mckesson.eig.roi.admin.model.TaxPerFacilityList;


/**
 * This is the Billing Admin services to perform Media Type operations.
 *
 * @author ganeshramr
 * @date   Apr 28, 2008
 * @since  HPF 13.1 [ROI]; Mar 25, 2008
 */
public interface BillingAdminService {


    /**
     * This method retrieves the media type based on the passed Media Type Id
     *
     * @param mediaTypeId id of the Media Type
     * @return MediaType
     */
    MediaType retrieveMediaType(long mediaTypeId);

    /**
     * This method retrieves the available Media Types
     *
     * @return MediaTypeList
     */
    MediaTypesList retrieveAllMediaTypes(boolean detailedFetch);

    /**
     * This method creates a media type and return the Media Type Id
     *
     * @param mediaType Media Type to be created
     * @return Id of the created Media Type
     */
    long createMediaType(MediaType mediaType);

    /**
     * This method will update a Media Type
     *
     * @param mediaType Media Type to be updated
	 * @return Updated Media Type
     */
    MediaType updateMediaType(MediaType mediaType);

    /**
     * This method deletes the media type id
     *
     * @param mediaTypeId Id of the Media Type to be deleted
     */
    void deleteMediaType(long mediaTypeId);

    /**
     * This method creates a FeeType
     *
     * @param feeType FeeType to be created
     * @return Id of the created FeeType
     */
    long createFeeType(FeeType feeType);

    /**
     * This method deletes the FeeType
     *
     * @param feeTypeId Id of FeeType to be deleted
     */
    void deleteFeeType(long feeTypeId);

    /**
     * This method retrieves the FeeType based on the passed feeTypeId
     *
     * @param feeTypeId Id of the FeeType
     * @return FeeType
     */
    FeeType retrieveFeeType(long feeTypeId);

    /**
     * This method will list the available FeeTypes
     *
     * @return List of FeeType
     */
    FeeTypesList retrieveAllFeeTypes(boolean detailedFetch);

    /**
     * This method will update a FeeType
     *
     * @param feeType FeeType to be updated
     * @return FeeType
     */
    FeeType updateFeeType(FeeType feeType);

    /**
     * This method creates a new PaymentMethod
     *
     * @param paymentMethod PaymentMethod to be created
     * @return Id of the created PaymentMethod
     */
    long createPaymentMethod(PaymentMethod paymentMethod);

    /**
     * This method fetches the payment method for the specified id
     *
     * @param paymentMethodId id of the Payment Method
     * @return PaymentMethod
     */
    PaymentMethod retrievePaymentMethod(long paymentMethodId);

    /**
     * This method retrieves all the available Payment Methods
     *
     * @return List of payment methods
     */
    PaymentMethodList retrieveAllPaymentMethods(boolean fetchDetails);

    /**
     * This method deletes the Payment Method for the specified id
     *
     * @param paymentMethodId Id of the Payment Method to be deleted
     */
    void deletePaymentMethod(long paymentMethodId);

    /**
     * This method updates an existing Payment Method after validating the business rules
     *
     * @param paymentMethod Payment Method to be updated
     * @return Updated Payment Method
     */
    PaymentMethod updatePaymentMethod(PaymentMethod paymentMethod);

    /**
     * This method creates a billing template and return the Billing Template Id
     *
     * @param billingTemplate Billing Template to be created
     * @return Id of the created Billing Template
     */
    long createBillingTemplate(BillingTemplate billingTemplate);

    /**
     * This method retrieves the BillingTemplate based on the passed billingTemplateId
     *
     * @param billingTemplateId Id of the BillingTemplate
     * @return BillingTemplate
     */
    BillingTemplate retrieveBillingTemplate(long billingTemplateId);

    /**
     * This method will list the available BillingTemplates
     * @param loadAssociations true/false for loading associated models
     * @return List of BillingTemplate
     */
    BillingTemplatesList retrieveAllBillingTemplates(boolean loadAssociations);

    /**
     * This method deletes the BillingTemplate
     *
     * @param billingTemplateId Id of BillingTemplate to be deleted
     */
    void deleteBillingTemplate(long billingTemplateId);

    /**
     * This method will update a BillingTemplate
     *
     * @param BillingTemplate BillingTemplate to be updated
     * @return BillingTemplate
     */
    BillingTemplate updateBillingTemplate(BillingTemplate billingTemplate);

    /**
     * This method creates a new Billing Tier and returns the created Billing Tier Id
     *
     * @param billingTier
     * @return Billing Tier Id
     */
    BillingTier createBillingTier(BillingTier billingTier);

    /**
     * This method retrieves a Billing Tier based on the input Billing Tier Id
     *
     * @param billingTierId id of a Billing Tier
     * @return BillingTier
     */
    BillingTier retrieveBillingTier(long billingTierId);

    /**
     * This method will retrieve all the available Billing Tiers
     * @param loadAssociation
     * @return BillingTier List
     */
    BillingTiersList retrieveAllBillingTiers(boolean loadAssociation);

    /**
     * This method will persist the update changes
     *
     * @param billingTier
     * @return BillingTier updated Billing Tier
     */
    BillingTier updateBillingTier(BillingTier billingTier);

    /**
     * This method will delete a Billing Tier
     *
     * @param billingTierId
     */
    void deleteBillingTier(long billingTierId);

    /**
     * This method retrieveBillingTierByMediaTypeName Based on the mediaType name
     * @param mediaTypeName
     * @return List of BillingTier
     */
    BillingTiersList retrieveBillingTiersByMediaTypeName(String mediaTypeName);

    /**
     * This method retrieves the SalesTaxFacility based on the passed salesTaxFacilityId
     *
     * @param salesTaxFacility id of the SalesTaxFacility
     * @return SalesTaxFacility
     */
    TaxPerFacility retrieveTaxPerFacility(long salesTaxFacilityId);

    /**
     * This method retrieves the available SalesTaxFacility
     *
     * @return SalesTaxFacilityList
     */
    TaxPerFacilityList retrieveAllTaxPerFacilities();

    /**
     * This method retrieves the available SalesTaxFacility for an user
     *
     * @param user id
     * @return SalesTaxFacilityList
     */
    TaxPerFacilityList retrieveAllTaxPerFacilitiesByUser(String userId);

    /**
     * This method creates a salesTaxFacility and return the salesTaxFacility Id
     *
     * @param salesTaxFacility SalesTaxFacility to be created
     * @return Id of the created SalesTaxFacility
     */
    long createTaxPerFacility(TaxPerFacility salesTaxFacility);

    /**
     * This method will update a SalesTaxFacility
     *
     * @param salesTaxFacility SalesTaxFacility to be updated
     * @return Updated SalesTaxFacility
     */
    TaxPerFacility updateTaxPerFacility(TaxPerFacility salesTaxFacility);

    /**
     * This method deletes the SalesTaxFacility id
     *
     * @param salesTaxFacilityId Id of the SalesTaxFacility to be deleted
     */
    void deleteTaxPerFacility(long salesTaxFacilityId);

    /**
     * This method retrieves the list of all the billing payment information
     * such as billingTemplates, feeTypes, DeliveryMethods,
     * Payment Methods, Weight, ReasonTypes, countries, requestorType
     *
     * @param requestorTypeId to retrieve the requestorType
     * @return BillingPaymentInfo object
     */
    BillingPaymentInfo retrieveBillingAndPaymentInfo(long requestorTypeId);

}

