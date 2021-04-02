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

package com.mckesson.eig.roi.request.model;

import java.io.Serializable;


/**
 * @author OFS
 * @date   Aug 7, 2008
 * @since  HPF 13.1 [ROI]; Aug 7, 2008
 */
public class RequestEventCriteria
implements Serializable {

    private long _requestId;
    private RequestEvent.TYPE _type;

    public RequestEventCriteria(long id, RequestEvent.TYPE type) {
        _requestId = id;
        setType(type);
    }

    public long getRequestId() { return _requestId; }
    public void setRequestId(long requestId) { _requestId = requestId; }

    public RequestEvent.TYPE getType() { return _type; }
    public void setType(RequestEvent.TYPE type) { _type = type; }

}
