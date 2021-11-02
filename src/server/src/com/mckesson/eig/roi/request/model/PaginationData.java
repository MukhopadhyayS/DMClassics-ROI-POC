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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaginationData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaginationData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="startIndex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="isDesc" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="domainType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sortBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaginationData", propOrder = {
    "_startIndex",
    "_count",
    "_isDesc",
    "_domainType",
    "_sortBy"
})
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

    @XmlElement(name="startIndex")
    private int      _startIndex;
    
    @XmlElement(name="count")
    private int      _count;
    
    @XmlElement(name="domainType", required = true)
    private String   _domainType;
    
    @XmlElement(name="isDesc")
    private boolean  _isDesc;
    
    @XmlElement(name="sortBy", required = true)
    private String   _sortBy;
    
    @XmlTransient
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
