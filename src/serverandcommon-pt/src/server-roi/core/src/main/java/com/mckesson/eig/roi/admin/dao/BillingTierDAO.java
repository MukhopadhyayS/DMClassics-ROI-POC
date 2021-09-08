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

package com.mckesson.eig.roi.admin.dao;


import java.util.List;

import com.mckesson.eig.roi.admin.model.BillingTier;
import com.mckesson.eig.roi.admin.model.BillingTiersList;
import com.mckesson.eig.roi.base.dao.ROIDAO;


/**
 * @author OFS
 * @date   Sep 15, 2008
 * @since  HPF 13.1 [ROI]; Sep 15, 2008
 */
public interface BillingTierDAO
extends ROIDAO {

    /**
     * This method will create a new Billing Tier
     *
     * @param billingTier new Billing Tier to be created
     * @return BillingTier newly created billing tier
     */
    BillingTier createBillingTier(BillingTier billingTier);

    /**
     * This method will retrieve the Billing Tier details for the id
     *
     * @param id Billing Tier Id
     * @return Billing Tier for the input id
     */
    BillingTier retrieveBillingTier(long billingTierId);

    /**
     * This method retrieve all the Billing Tiers from the DB
     *
     * @return List of Billing Tiers
     */
    BillingTiersList retrieveAllBillingTiers();

    /**
     * This method will persist the modifications in Billing Tier
     *
     * @param billingTier modified Billing Tier
     * @return latest updated Billing Tier object
     */
    BillingTier updateBillingTier(BillingTier billingTier);

    /**
     * This method will delete a Billing Tier
     *
     * @param id Billing Tier Id
     * @return deleted billingTier
     */
    BillingTier deleteBillingTier(long id);

    /**
     * This method retrieveBillingTier based on the billingTierName
     *
     * @param name
     * @return billingTier for the given name
     */
    BillingTier retrieveBillingTierByName(String name);

    /**
     * This method fetches the requestorTypeIds which is associated with billingTierId
     *
     * @param billingTierId to be checked for association
     * @return number of ids associated with billingTier
     */
    long getAssociatedRequestorTypeCount(long billingTierId);

    /**
     * This method will retrieve all BillingTiers with input Media Type name
     * @param mediaTypeName
     * @return List of BillingTiers
     */
    BillingTiersList retrieveBillingTiersByMediaTypeName(String mediaTypeName);

    /**
     * This method will load Billing Tier Name and Id with associated Media Type Name
     * @return List of BillingTier
     */
    BillingTiersList retrieveBillingTierAndMediaTypeName();
    
    /**
     * This method is used to retrieve All Standard BillingTiers By Names
     *
     * @return BillingTier
     */
    public List<BillingTier> retrieveAllStandardBillingTiers();
}
