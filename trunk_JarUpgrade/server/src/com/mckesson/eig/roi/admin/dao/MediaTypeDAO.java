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


import com.mckesson.eig.roi.admin.model.MediaType;
import com.mckesson.eig.roi.admin.model.MediaTypesList;
import com.mckesson.eig.roi.base.dao.ROIDAO;


/**
 * @author OFS
 * @date   Sep 15, 2008
 * @since  HPF 13.1 [ROI]; Sep 15, 2008
 */
public interface MediaTypeDAO
extends ROIDAO {

    /**
     * This method creates a new Media Type
     *
     * @param mediaType Media Type details to be created
     * @return Unique Id of the Media Type
     */
    long createMediaType(MediaType mediaType);

    /**
     * This method fetches the Media Type details
     *
     * @param mediaTypeId Unique id of the Media Type
     * @return Media Type details
     */
    MediaType retrieveMediaType(long mediaTypeId);

    /**
     * This method fetches all the Media Types
     *
     * @return List of all Media Types
     */
    MediaTypesList retrieveAllMediaTypes();

    /**
     * This method fetches name and ids of all Media Types
     *
     * @return List of all Media Types
     */
    MediaTypesList retrieveAllMediaTypeNames();

    /**
     * This method updates the Media Type details
     *
     * @param mediaType Details of the Media Type to be updated
     * @param old Details of the old Media Type
     * @return Updated Media Type
     */
    MediaType updateMediaType(MediaType mediaType, MediaType old);

    /**
     * This method fetches the Media Type details by name
     *
     * @param mediaTypeName Name of the Media Type
     * @return Media Type detail for the given name
     */
    MediaType getMediaTypeByName(String mediaTypeName);

    /**
     * This method deletes the selected Media Type
     *
     * @param mediaTypeId Id of the Media Type to be deleted
     */
    MediaType deleteMediaType(long mediaTypeId);

    /**
     * This method retrieves BillingTierIds which is associated with given mediaTypeId
     *
     * @param mediaTypeId Media Type Id to be checked for association
     * @return Number of Ids associated with BillingTier
     */
    long getAssociatedBillingTierCount(long mediaTypeId);
}
