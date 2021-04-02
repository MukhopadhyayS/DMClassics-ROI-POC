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

package com.mckesson.eig.roi.base.dao;


import java.sql.Timestamp;
import java.util.List;

import com.mckesson.eig.roi.hpf.model.UserSecurity;
import com.mckesson.eig.roi.request.model.FreeFormFacility;


/**
 * @author OFS
 * @date   Sep 01, 2009
 * @since  HPF 13.1 [ROI]; Sep 15, 2008
 */
public interface ROIDAO {

    /**
     * This method processes the date to set the time
     * difference in milliseconds b/w Database server and JVM
     *
     * @return Database server date
     */
    Timestamp getDate();

    /**
     * This method is to retrieve the invoice due days details from sysparms global
     */
    List<Integer> retrieveGlobalInvoiceDueDays();

  /**
     * This method retrieves all the security of the given userId
     */
    UserSecurity retrieveROIUserSecurity(String userID);
    
    /**
     * This method will return all the FreeForm facilities for currently logged in user
     * @return List of FreeForm facilities
     */
    List<String> retrieveFreeFormFacilitiesByUser(long userId);
    
    /**
     * creates a free form facility for the ROI patients/ Documents 
     * @param freeformFacility
     * @return
     */
    FreeFormFacility createFreeFormFacilities(FreeFormFacility freeformFacility);
    
    /**
     * This method will return the FreeForm facilities
     * @return FreeForm facilities
     */
    FreeFormFacility retrieveFreeFormFacilitiesByName(String facility, long userId);
    
    public UserSecurity retrieveROIUserSecurityForOutputType(String userID);
    
    /**
     * This method retrieve the EIWDATAConfiguration location from database
     * @param key
     * @return configuration location
     */
    String retrieveEIWDATAConfiguration(String key);
}
