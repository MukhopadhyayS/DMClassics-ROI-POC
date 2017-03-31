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

package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;
import java.util.List;


/**
 *
 * @author OFS
 * @date   Oct 31, 2011
 * @since  ROI-15.1
 */
@SuppressWarnings("serial")
public class OverDueDocInfoList
implements Serializable {

    private List<OverDueDocInfo> _overDueDocInfos;
    private String _name;

    public OverDueDocInfoList() { }

    public OverDueDocInfoList(List<OverDueDocInfo> docInfos, String name) {

        setOverDueDocInfos(docInfos);
        setName(name);
    }

    public OverDueDocInfoList(List<OverDueDocInfo> infos) { setOverDueDocInfos(infos); }

      public String getName() { return _name; }
    public void setName(String name) { _name = name; }

    public void setOverDueDocInfos(List<OverDueDocInfo> overDueDocInfos) {
        _overDueDocInfos = overDueDocInfos;
    }

    public List<OverDueDocInfo> getOverDueDocInfos() {
        return _overDueDocInfos;
    }

}
