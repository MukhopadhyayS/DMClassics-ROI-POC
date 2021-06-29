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

package com.mckesson.eig.roi.admin.dao;

import com.mckesson.eig.roi.admin.model.BillingTemplate;
import com.mckesson.eig.roi.admin.model.BillingTemplatesList;
import com.mckesson.eig.roi.base.dao.ROIDAO;


/**
 * @author OFS
 * @date   Sep 15, 2008
 * @since  HPF 13.1 [ROI]; Sep 15, 2008
 */
public interface BillingTemplateDAO
extends ROIDAO {

    /**
     * This method creates a new BillingTemplate
     *
     * @param billingTemplate BillingTemplate Details to be created
     * @return Unique id of the BillingTemplate
     */
    long createBillingTemplate(BillingTemplate billingTemplate);

    /**
     * This method fetches the BillingTemplate details
     *
     * @param billingTemplateId Unique id of the BillingTemplate
     * @return BillingTemplate details
     */
    BillingTemplate retrieveBillingTemplate(long billingTemplateId);

    /**
     * This method fetches all the BillingTemplates
     *
     * @return list of all BillingTemplates
     */
    BillingTemplatesList retrieveAllBillingTemplates();

    /**
     * This method fetches all the BillingTemplates for the given requestorId
     *
     * @return list of all BillingTemplates
     */
    BillingTemplatesList retrieveAllBillingTemplatesByRequestorType(long requestorTypeId);

    /**
     * This method deletes selected BillingTemplate
     * @param billingTemplateId
     * @return details of the billing template
     */
    BillingTemplate deleteBillingTemplate(long billingTemplateId);

    /**
     * This method updates the BillingTemplate Details
     *
     * @param billingTemplate the details of BillingTemplate to be updated
     * @param selectedBillingTemplate details of the old BillingTemplate
     * @return updated BillingTemplate
     */
    BillingTemplate updateBillingTemplate(BillingTemplate billingTemplate,
                                                 BillingTemplate originalBillingTemplate);

    /**
     * This method fetches RequestorTypeIds which is associated with given billingTemplateId
     *
     * @param billingTemplateId to be checked for association
     * @return Number of ids associated with RequestorType
     */
    long getAssociatedRequestorTypeCount(long billingTemplateId);

    /**
     * This method fetches the BillingTemplate by billingTemplateName
     *
     * @param billingTemplateName
     *            the details of BillingTemplate
     * @return BillingTemplate for the given billingTemplateName
     */
    BillingTemplate getBillingTemplateByName(String billingTemplateName);

    /**
     * This method retrieves Billing Template Names and Ids
     * @return List of Billing Templates
     */
    BillingTemplatesList retrieveAllBillingTemplateName();
}
