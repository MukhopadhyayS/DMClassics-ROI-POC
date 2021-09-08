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
 * @author OFS
 * @date   Feb 24, 2010
 * @since  HPF 15.1 [ROI];
 */
public class ROIParameter
implements Serializable {

    private int _id;

    private String _itemName;
    private String _itemValue;
    private String _itemDescription;

    private int _createdBy;
    private int _modifiedBy;
    private Date _createdDt;
    private Date _modifiedDt;

    private int _recordVersion;

    private int _parentItem;
    private int _childItem;


    public int getParentItem() {
        return _parentItem;
    }
    public void setParentItem(int parentItem) {
        _parentItem = parentItem;
    }
    public int getChildItem() {
        return _childItem;
    }
    public void setChildItem(int childItem) {
        _childItem = childItem;
    }
    public String getItemName() { return _itemName; }
    public void setItemName(String itemName) { _itemName = itemName; }

    public String getItemValue() { return _itemValue; }
    public void setItemValue(String itemValue) { _itemValue = itemValue; }

    public int getId() {
        return _id;
    }
    public void setId(int id) {
        _id = id;
    }

    public String getItemDescription() {
        return _itemDescription;
    }
    public void setItemDescription(String itemDescription) {
        _itemDescription = itemDescription;
    }

    public int getCreatedBy() {
        return _createdBy;
    }
    public void setCreatedBy(int createdBy) {
        _createdBy = createdBy;
    }

    public int getModifiedBy() {
        return _modifiedBy;
    }
    public void setModifiedBy(int modifiedBy) {
        _modifiedBy = modifiedBy;
    }

    public Date getCreatedDt() {
        return _createdDt;
    }
    public void setCreatedDt(Date createdDt) {
        _createdDt = createdDt;
    }
    public Date getModifiedDt() {
        return _modifiedDt;
    }

    public void setModifiedDt(Date modifiedDt) {
        _modifiedDt = modifiedDt;
    }

    public int getRecordVersion() {
        return _recordVersion;
    }
    public void setRecordVersion(int recordVersion) {
        _recordVersion = recordVersion;
    }
}
