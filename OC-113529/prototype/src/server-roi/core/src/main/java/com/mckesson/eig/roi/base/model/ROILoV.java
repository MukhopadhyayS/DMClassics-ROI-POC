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


/**
 * @author OFS
 * @date   May 06, 2009
 * @since  HPF 13.1 [ROI]; Sep 25, 2008
 */
public class ROILoV
implements Serializable {

    private long _id;
    private String _domainType;
    private String _domainSource;
    private String _key;
    private String _value;
    private int _orgId;
    private long _parent;
    private long _child;
    private long _createdBy;

    public ROILoV() {
        super();
    }
    public ROILoV(String domainSource, String key, String value) {

        _domainSource = domainSource;
        _key = key;
        _value = value;
    }

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getKey() { return _key; }
    public void setKey(String key) { _key = key; }

    public String getValue() { return _value; }
    public void setValue(String value) { _value = value; }

    public int getOrgId() { return _orgId;   }
    public void setOrgId(int orgId) { _orgId = orgId; }

    public String getDomainType() { return _domainType; }
    public void setDomainType(String type) { _domainType = type; }

    public String getDomainSource() { return _domainSource; }
    public void setDomainSource(String source) { _domainSource = source; }

    public long getParent() { return _parent; }
    public void setParent(long parent) { _parent = parent; }

    public long getChild() { return _child; }
    public void setChild(long child) { _child = child; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long createdBy) {  _createdBy = createdBy; }

    @Override
    public boolean equals(Object lov) {

        if (lov == null) {
            return false;
        }
        if (!(lov instanceof ROILoV)) {
            return false;
        }

        ROILoV roiLov = (ROILoV) lov;

        return ((getKey().equalsIgnoreCase(roiLov.getKey()))
                && (getValue().equalsIgnoreCase(roiLov.getValue()))
                && (getDomainSource().equalsIgnoreCase(roiLov.getDomainSource())));
    }

    @Override
    public int hashCode() {
        return String.valueOf(getKey() + getValue() + getDomainSource()).hashCode();

    }
}
