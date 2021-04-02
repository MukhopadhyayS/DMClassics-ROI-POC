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
 * @author Pranav Amarasekaran
 * @date   Dec 16, 2010
 * @since  HPF 15.1 [ROI]; Dec 16, 2010
 */
public class SysParam 
implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private long _id;

    private Integer _globalParentID;
    private Integer _globalValueID;
    private String _globalVariant;
    private String _globalName;
    private String _globalText;
    private long _createdBy;    
    private Date _createdDate;
    private long _modifiedBy;
    private Date _modifiedDate;
    private int  _recordVersion;
    
    public long getId() { return _id; }
    public void setId(long id) { _id = id; }
    
    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }
    
    public Date getCreatedDate() { return _createdDate; }
    public void setCreatedDate(Date date) { _createdDate = date; }    

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date date) { _modifiedDate = date; }
    
    public Integer getGlobalParentID() { return _globalParentID; }
    public void setGlobalParentID(Integer parentID) { _globalParentID = parentID; }
    
    public Integer getGlobalValueID() { return _globalValueID; }
    public void setGlobalValueID(Integer valueID) { _globalValueID = valueID; }
    
    public String getGlobalVariant() { return _globalVariant; }
    public void setGlobalVariant(String variant) { _globalVariant = variant; }
    
    public String getGlobalName() { return _globalName; }
    public void setGlobalName(String name) { _globalName = name; }    
    
    public String getGlobalText() { return _globalText; }
    public void setGlobalText(String text) {  _globalText = text; }   
}
