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
 * @date   Apr 1, 2009
 * @since  HPF 13.1 [ROI]; Apr 1, 2009
 */
public class DocInfoList
implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<DocInfo> _docInfos;
    private String _name;

    public DocInfoList() { }

    public DocInfoList(List<DocInfo> docInfos, String name) {

        setDocInfos(docInfos);
        setName(name);
    }

    public DocInfoList(List<DocInfo> infos) { setDocInfos(infos); }

    public List<DocInfo> getDocInfos() { return _docInfos; }
    public void setDocInfos(List<DocInfo> infos) { _docInfos = infos; }

    public String getName() { return _name; }
    public void setName(String name) { _name = name; }

}
