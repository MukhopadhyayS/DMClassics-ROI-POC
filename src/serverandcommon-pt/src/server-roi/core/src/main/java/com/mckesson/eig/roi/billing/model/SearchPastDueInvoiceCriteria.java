/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2011 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.billing.model;

import java.util.Arrays;

/**
 * @author karthike
 * @date   Sep 22, 2011
 * @since  Sep 22, 2011
 */
public class SearchPastDueInvoiceCriteria {
    
    private String[] _billingLocations;
    private Long[] _requestorTypes;
    private String[] _requestorTypeNames;
    private String _requestorName;
    private long _overDueFrom;
    private long _overDueTo;
    private OverDueRestriction _overdueRestriction;
    
    public String[] getRequestorTypeNames() {
        return _requestorTypeNames;
    }
    public void setRequestorTypeNames(String[] requestorTypeNames) {
        this._requestorTypeNames = requestorTypeNames;
    }
    
    public String[] getBillingLocations() { return _billingLocations; }
    public void setBillingLocations(String[] billingLocs) { _billingLocations = billingLocs; }
    
    public Long[] getRequestorTypes() { return _requestorTypes; }
    public void setRequestorTypes(Long[] requestortypes) { _requestorTypes = requestortypes; }
    
    public String getRequestorName() { return _requestorName; }
    public void setRequestorName(String requestorName) { _requestorName = requestorName; }
    
    public long getOverDueFrom() { return _overDueFrom; }
    public void setOverDueFrom(long overDueFrom) { _overDueFrom = overDueFrom; }
    
    public long getOverDueTo() { return _overDueTo; }
    public void setOverDueTo(long overDueTo) { _overDueTo = overDueTo; }
    
    public OverDueRestriction getOverDueRestriction() { return _overdueRestriction; }
    public void setOverDueRestriction(OverDueRestriction overdueRestriction) { 
        _overdueRestriction = overdueRestriction;
    }
    
    @Override
    public String toString() {
        return new StringBuffer()
                    .append("Billing Locations:")
                    .append(Arrays.toString(_billingLocations))
                    .append(", RequestorTypes:")
                    .append(Arrays.toString(_requestorTypes))
                    .append(", RequestoName:")
                    .append(_requestorName)
                    .append(", OverDue Days ")
                    .append(_overdueRestriction)
                    .append(_overDueFrom + " TO ")
                    .append(_overDueTo)
                    .toString();
    }

}
