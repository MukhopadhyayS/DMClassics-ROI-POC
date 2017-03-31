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

package com.mckesson.eig.roi.admin.model;

import java.io.Serializable;
import java.util.Map;


/**
 * @author OFS
 * @date   May 29, 2008
 * @since  HPF 13.1 [ROI]
 */
public class RequestStatusMap
implements Serializable {

    private Map<Integer, String>    _statusMap;
    private int                     _loggedStatus;

    public RequestStatusMap() { }

    public Map<Integer, String> getStatusMap() { return _statusMap; }
    public void setStatusMap(Map<Integer, String> name) { _statusMap = name; }

    public int getLoggedStatus() { return _loggedStatus; }
    public void setLoggedStatus(int status) { _loggedStatus = status; }

}
