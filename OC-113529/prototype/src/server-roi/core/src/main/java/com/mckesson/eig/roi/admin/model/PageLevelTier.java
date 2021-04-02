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
import java.util.Date;


/**
 * @author ranjithr
 * @date   Apr 15, 2008
 * @since  HPF 13.1 [ROI]; Apr 15, 2008
 */
public class PageLevelTier
implements Serializable {

    private long    _id;
    private int     _recordVersion;
    private int    _page;
    private float   _pageCharge;
    private long    _createdBy;
    private long    _modifiedBy;
    private Date    _modifiedDate;


    public int getPage() { return _page; }
    public void setPage(int page) { _page = page; }

    public float getPageCharge() { return _pageCharge; }
    public void setPageCharge(float charge) { _pageCharge = charge; }

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date date) { _modifiedDate = date; }

    @Override
    public String toString() {
        return "PageLevelTierId = " + _id + " , "
               + "Page = " + _page + " , "
               + "Page Charge = " +  _pageCharge;
       }
}
