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

package com.mckesson.eig.roi.base.model;

import java.io.Serializable;
import java.util.List;



/**
 * @author OFS
 * @date   Aug 19, 2008
 * @since  HPF 13.1 [ROI]; Jul 23, 2008
 */
public class SearchLoVCriteria
implements Serializable {

    private int _maxCount;
    private List<SearchLoV> _lovs;

    public int getMaxCount() { return _maxCount; }
    public void setMaxCount(int maxCount) { _maxCount = maxCount; }

    public List<SearchLoV> getLovs() { return _lovs; }
    public void setLovs(List<SearchLoV> lovs) { _lovs = lovs; }

    /**
     * This method creates audit message for search supplemental
     * @return String audit message
     */
    public String toSearchAudit() {

        StringBuffer message = new StringBuffer("ROI Supplemental Search - Criteria: ");
        if (_lovs != null) {

            for (SearchLoV lov : _lovs) {

                message.append(" Key: ").append(lov.getKey())
                       .append(" Value: ")
                       .append(lov.getValue());
            }
        }
        return message.toString();
    }
}
