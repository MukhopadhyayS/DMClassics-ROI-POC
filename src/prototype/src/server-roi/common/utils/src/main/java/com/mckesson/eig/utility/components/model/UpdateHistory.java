/*
 * Copyright 2008-2009 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.utility.components.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.utility.util.DateUtilities;

/**
 * This class holds the update history details of any entity.
 *
 * @author Sahul Hameed Y
 * @date   Apr 7, 2008
 * @since  Utils; Apr 7, 2008
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "update")
@XmlType(name = "UpdateHistory")
public class UpdateHistory implements Serializable {

	private static final long serialVersionUID = -3073606282100997790L;

	private String _updatedOn;
    private String _comments;
    private String _version;
    private String _versionName;
    private String _versionValue;
    private String _productType;

    public UpdateHistory() {

    }

    public UpdateHistory(String date, String version, String comments) {

        _updatedOn = date;
        _version = version;
        _comments = comments;
    }

    public String getVersionName() {
        return _versionName;
    }

    @XmlElement(name = "versionName")
    public void setVersionName(String name) {
        _versionName = name;
    }
    public String getVersionValue() {
        return _versionValue;
    }

    @XmlElement(name = "versionValue")
    public void setVersionValue(String value) {
        _versionValue = value;
    }

    public Date getUpdatedOn() {
        return DateUtilities.parseISO8601(_updatedOn);
    }

    public String getUpdatedOnAsString() {
        return _updatedOn;
    }

    @XmlElement(name = "updatedOn")
    public void setUpdatedOnAsString(String updatedOn) {
        _updatedOn = updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        if (updatedOn != null) {
            _updatedOn = DateUtilities.formatISO8601(updatedOn);
        }
    }

    public String getComments() {
        return _comments;
    }

    @XmlElement(name = "comments")
    public void setComments(String comments) {
        this._comments = comments;
    }
    public String getVersion() {
        return _version;
    }

    @XmlElement(name = "version")
    public void setVersion(String version) {
        this._version = version;
    }

    public String getProductType() {
        return _productType;
    }

    @XmlElement(name = "productType")
    public void setProductType(String type) {
        _productType = type;
    }

}
