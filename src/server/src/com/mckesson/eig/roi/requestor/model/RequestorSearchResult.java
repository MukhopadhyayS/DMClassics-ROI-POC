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

package com.mckesson.eig.roi.requestor.model;


import java.io.Serializable;
import java.util.List;


/**
 * @author ranjithr
 * @date   Jun 24, 2008
 * @since  HPF 13.1 [ROI]; Jun 17, 2008
 */
public class RequestorSearchResult
implements Serializable {

    private List<Requestor> _searchResults;
    private boolean _maxCountExceeded;
    private long _requestorType;

    public List<Requestor> getSearchResults() { return _searchResults; }
    public void setSearchResults(List<Requestor> results) { _searchResults = results; }

    public boolean isMaxCountExceeded() { return _maxCountExceeded; }
    public void setMaxCountExceeded(boolean countExceeded) { _maxCountExceeded = countExceeded; }

    public long getRequestorType() { return _requestorType; }
    public void setRequestorType(long type) { _requestorType = type; }
}
