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


import java.util.List;

import com.mckesson.eig.roi.admin.model.FeeType;
import com.mckesson.eig.roi.admin.model.FeeTypesList;
import com.mckesson.eig.roi.admin.model.RelatedFeeType;
import com.mckesson.eig.roi.base.dao.ROIDAO;


/**
 * @author OFS
 * @date   Sep 15, 2008
 * @since  HPF 13.1 [ROI]; Sep 15, 2008
 */
public interface FeeTypeDAO
extends ROIDAO {

    /**
     * This method creates a new FeeType
     *
     * @param feeType FeeType Details to be created
     * @return Unique id of the FeeType
     */
    long createFeeType(FeeType feeType);

    /**
     * This method fetches the FeeType details
     *
     * @param feeTypeId Unique id of the FeeType
     * @return FeeType details
     */
    FeeType retrieveFeeType(long feeTypeId);

    /**
     * This method fetches all the FeeTypes
     *
     * @return list of all FeeTypes
     */
    FeeTypesList retrieveAllFeeTypes();

    /**
     * This method fetches all the FeeTypes by requestorId
     *
     * @return list of all FeeTypes
     */
    FeeTypesList retrieveAllFeeTypesByRequestorId(long requestorTypeId);

    /**
     * This method fetches all the related FeeTypes for the billing template by requestorId
     *
     * @return list of all FeeTypes
     */
    List<RelatedFeeType> retrieveAllRelatedFeeByRequestorType(long requestorTypeId);

    /**
     * This method fetches name and ids of all Fee Types
     *
     * @return list of all FeeTypes
     */
    FeeTypesList retrieveAllFeeTypeNames();

    /**
     * This method updates the FeeType Details
     *
     * @param feeType the details of FeeType to be updated
     * @param selectedFeeType details of the old FeeType
     * @return updated FeeType
     */
    FeeType updateFeeType(FeeType feeType, FeeType selectedFeeType);

    /**
     * This method deletes selected FeeType
     *
     * @param feeTypeId Id of the FeeType to be deleted
     * @return Name of the deleted Fee Type
     */
    String deleteFeeType(long feeTypeId);

    /**
     * This method fetches the FeeType by feeTypeName
     *
     * @param feeTypeName the details of FeeType
     * @return FeeType for the given feeTypeName
     */
    FeeType getFeeTypeByName(String feeTypeName);

    /**
     * This method fetches BillingTempateIds which is associated with given feeTypeId
     *
     * @param feeTypeId to be checked for association
     * @return Number of ids associated with BillingTemplate
     */
    long getAssociatedBillingTemplateCount(long feeTypeId);
    
}
