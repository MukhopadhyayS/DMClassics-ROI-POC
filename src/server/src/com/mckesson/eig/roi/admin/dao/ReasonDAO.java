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

import com.mckesson.eig.roi.admin.model.Reason;
import com.mckesson.eig.roi.admin.model.ReasonsList;
import com.mckesson.eig.roi.base.dao.ROIDAO;


/**
 * @author OFS
 * @date   Sep 15, 2008
 * @since  HPF 13.1 [ROI]; Sep 15, 2008
 */
public interface ReasonDAO
extends ROIDAO {

    /**
     * This method creates a new Reason
     *
     * @param Reason Reason details to be created
     * @return Unique Id of the reason
     */
    long createReason(Reason reason);

    /**
     * This method fetches all the reasons
     *
     * @return List of all reasons
     */
    ReasonsList retrieveAllReasonsByType(String reasonType);

    /**
     * This method fetches the reason details by name
     *
     * @param reasonName The name of the reason
     * @param reasonType The type of the reason
     * @return reason for the given display text or null if no
     * reason exist with that name and type
     */
    Reason getReasonByName(String reasonName, String reasonType);

    /**
     * This method fetches the reason for the specified display text
     *
     * @param reasonDisplayText displayText of the reason
     * @param reasonType type of the reason
     * @return reason for the given display text or null if no
     * reason exist with that display text and type
     */
    Reason getReasonByDispText(String reasonDisplayText, String reasonType);

    /**
     * This method fetches the status reason details by name,type,and status
     *
     * @param reason The details of the status reason
     * @return status reason for the given name,type,status or null if no
     * reason exist with that name,type and status
     */
    Reason getStatusReasonByName(Reason reason);

    /**
     * This method fetches the status reason details by display text,type,and status
     *
     * @param reason The details of the status reason
     * @return status reason for the given display text,type,status or null if no
     * reason exist with that displaytext,type and status
     */
    Reason getStatusReasonByDispText(Reason reason);

    /**
     * This method fetches the reason for the specified id
     *
     * @param reasonId Unique id of the reason
     * @return reason details
     */
      Reason retrieveReason(long reasonId);

    /**
     * This method deletes the reason
     *
     * @param reasonId Reason id to be deleted
     * @return detail of Reasons
     */
      Reason deleteReason(long reasonId);

    /**
     * This method updates the reason
     *
     * @param reason The updated reason
     * @return Updated reason
     */
      Reason updateReason(Reason reason);

    /**
     * This method will get reasons by status
     * @param statusId
     * @return
     */
      List <String> getReasonsByStatus(int statusId);
}
