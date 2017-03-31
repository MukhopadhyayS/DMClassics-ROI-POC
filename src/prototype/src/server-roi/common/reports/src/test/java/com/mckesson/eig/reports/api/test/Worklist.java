/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Information Solutions and is protected under United States and 
 * international copyright and other intellectual property laws. Use, 
 * disclosure, reproduction, modification, distribution, or storage 
 * in a retrieval system in any form or by any means is prohibited without 
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.reports.api.test;

import java.io.Serializable;

/**
 * @author sahuly
 * @date   Sep 9, 2008
 * @since  HECM 1.0; Sep 9, 2008
 */
public class Worklist implements Serializable {
    
    private long _worklistID;

    public long getWorklistID() {
        return _worklistID;
    }

    public void setWorklistID(long worklistid) {
        _worklistID = worklistid;
    }
}
