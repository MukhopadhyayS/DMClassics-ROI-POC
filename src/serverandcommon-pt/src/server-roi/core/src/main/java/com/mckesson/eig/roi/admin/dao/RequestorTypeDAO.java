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


import com.mckesson.eig.roi.admin.model.RequestorType;
import com.mckesson.eig.roi.admin.model.RequestorTypesList;
import com.mckesson.eig.roi.base.dao.ROIDAO;


/**
 * @author OFS
 * @date   Feb 10, 2009
 * @since  HPF 13.1 [ROI]; Sep 15, 2008
 */
public interface RequestorTypeDAO
extends ROIDAO {

    /**
     * This method create a new RequestorType
     * @param requestorType new RequestorType to be created
     * @return id of unique RequestorType identifier
     */
     RequestorType createRequestorType(RequestorType requestorType);

    /**
     * This method fetches RequestorType based on the Id
     * @param id RequestorType Id
     * @return requstorType for the input
     */
     RequestorType retrieveRequestorType(long id);

    /**
     * This method retrieves all RequstorType from the DB
     * @return List of requestorTypes
     */
     RequestorTypesList retrieveAllRequestorTypes();

    /**
     * This method update the RequestorType
     * @param requestorType modified RequestorType
     * @return updated RequestorType
     */
     RequestorType updateRequestorType(RequestorType requestorType);

    /**
     * This method deletes requestorType
     * @param id RequestorType Id
     * @return deleted RequestorType
     */
     RequestorType deleteRequestorType(long id);

    /**
     * This method retrieve requestorType
     * @param requestorTypeName
     * @return requestorType for the given Name
     */
     RequestorType getRequestorTypeByName(String requestorTypeName);

     /**
      * This method retrieves all RequstorTypeNames from the DB
      * @return List of requestorTypes
      */
      RequestorTypesList retrieveAllRequestorTypeNames();

      /**
       * This method fetches the association count for this requestorTypeId
       * @param requestorTypeId
       * @return count of the association with requestor
       */
     long getAssociatedRequestorCount(long requestorTypeId);
}
