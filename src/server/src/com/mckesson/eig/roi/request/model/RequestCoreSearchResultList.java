/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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
import java.util.List;

/**
 * @author Karthik Easwaran
 * @date   Aug 10, 2012
 * @since  Aug 10, 2012
 */
public class RequestCoreSearchResultList 
implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<RequestCoreSearchResult> _requestCoreSearchResult;
    private boolean _maxCountExceeded; 
    
    public RequestCoreSearchResultList() { }
    public RequestCoreSearchResultList(List<RequestCoreSearchResult> searchResult) {
        setRequestCoreSearchResult(searchResult);
    }
    
    public boolean isMaxCountExceeded() { return _maxCountExceeded; }
    public void setMaxCountExceeded(boolean maxCountExceeded) { 
        _maxCountExceeded = maxCountExceeded;
    }
    
    public List<RequestCoreSearchResult> getRequestCoreSearchResult() {
        return _requestCoreSearchResult;
    }
    public void setRequestCoreSearchResult(List<RequestCoreSearchResult> requestCoreSearchResult) {
        _requestCoreSearchResult = requestCoreSearchResult;
    }

}
