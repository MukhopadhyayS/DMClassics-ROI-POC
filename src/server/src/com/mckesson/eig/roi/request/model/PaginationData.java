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

import com.mckesson.eig.utility.util.StringUtilities;


/**
 * @author OFS
 * @date   May 07, 2009
 * @since  HPF 13.1 [ROI]; Jul 23, 2008
 */
public class PaginationData
implements Serializable {

    public static enum SORT_COLUMNS {

        REQUEST_UPDATED_DATE        ("request.modified_Date"),
        REQUEST_UPDATED_BY          ("updatedBy"),
        REQUEST_ID                  ("request.roi_Requestmain_Seq"),
        REQUEST_BALANCE             ("request.roi_Requestmain_Seq");

        private final String _sortColumn;

        private SORT_COLUMNS(String column) { _sortColumn = column; }

        @Override
        public String toString() { return _sortColumn; }
    }

    private int      _startIndex;
    private int      _count;
    private String   _domainType;
    private boolean  _isDesc;
    private String   _sortBy;
    private boolean _isSortByLov;

    public int getStartIndex() { return _startIndex; }
    public void setStartIndex(int index) { _startIndex = index; }

    public int getCount() { return _count; }
    public void setCount(int count) { _count = count; }

    public boolean getIsDesc() { return _isDesc; }
    public void setIsDesc(boolean desc) { _isDesc = desc; }

    public String getSortBy() { return _sortBy; }
    public void setSortBy(String sortBy) {

        _isSortByLov = true;
        if (!StringUtilities.isEmpty(sortBy)) {
            for (SORT_COLUMNS cons : SORT_COLUMNS.values()) {
                if (sortBy.equalsIgnoreCase(cons.name())) {

                    _isSortByLov = false;
                    break;
                }
            }
        }
        if (!_isSortByLov) {
            _sortBy = Enum.valueOf(SORT_COLUMNS.class, sortBy).toString();
        } else {
            _sortBy = sortBy;
        }
    }

    public String getDomainType() { return _domainType; }
    public void setDomainType(String domainType) { _domainType = domainType; }

    public boolean getIsSortByLov() { return _isSortByLov; }
    public void setIsSortByLov(boolean sortByLov) { _isSortByLov = sortByLov; }
}
