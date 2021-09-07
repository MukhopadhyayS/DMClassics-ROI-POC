/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.workflow.process.datavault.model;

import java.util.Date;

/**
 * @author OFS
 *
 * @date Apr 2, 2009
 * @since HECM 1.0.3; Apr 2, 2009
 */
public class ProcessVersionDVInfo extends ProcessDVInfo {

    private int _versionId;
    private int _lockedById;
    private int _maxInstanceDuration;
    private int _retentionPeriod;

    private String _name;
    private String _description;
    private String _status;
    private String _notifyException;
    private String _notifyEmailId;
    private String _active;

    private Date _createdDate;
    private Date _effectiveDate;
    private Date _expireDate;

    private String	_defContent;
    private String _graphContent;

    private ProcessOwnerDVInfo _processOwner;

    /**
     * @return versionId
     */
    public int getVersionId() {
        return _versionId;
    }

    /**
     * @param versionId
     */
    public void setVersionId(int versionId) {
        this._versionId = versionId;
    }

    /**
     * @return lockedById
     */
    public int getLockedById() {
        return _lockedById;
    }

    /**
     * @param lockedById
     */
    public void setLockedById(int lockedById) {
        this._lockedById = lockedById;
    }

    /**
     * @return maxInstanceDuration
     */
    public int getMaxInstanceDuration() {
        return _maxInstanceDuration;
    }

    /**
     * @param maxInstanceDuration
     */
    public void setMaxInstanceDuration(int maxInstanceDuration) {
        this._maxInstanceDuration = maxInstanceDuration;
    }

    /**
     * @return retentionPeriod
     */
    public int getRetentionPeriod() {
        return _retentionPeriod;
    }

    /**
     * @param retentionPeriod
     */
    public void setRetentionPeriod(int retentionPeriod) {
        this._retentionPeriod = retentionPeriod;
    }

    /**
     * @return name
     */
    public String getName() {
        return _name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this._name = name;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return _description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        _description = description;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return _status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this._status = status;
    }

    /**
     * @return notifyException
     */
    public String getNotifyException() {
        return _notifyException;
    }

    /**
     * @param notifyException
     */
    public void setNotifyException(String notifyException) {
        this._notifyException = notifyException;
    }

    /**
     * @return notifyEmailId
     */
    public String getNotifyEmailId() {
        return _notifyEmailId;
    }

    /**
     * @param notifyEmailId
     */
    public void setNotifyEmailId(String notifyEmailId) {
        this._notifyEmailId = notifyEmailId;
    }

    /**
     * @return createdDate
     */
    public Date getCreatedDate() {
        return _createdDate;
    }

    /**
     * @param createdDate
     */
    public void setCreatedDate(Date createdDate) {
        this._createdDate = createdDate;
    }

    /**
     * @return effectiveDate
     */
    public Date getEffectiveDate() {
        return _effectiveDate;
    }

    /**
     * @param effectiveDate
     */
    public void setEffectiveDate(Date effectiveDate) {
        this._effectiveDate = effectiveDate;
    }

    /**
     * @return expireDate
     */
    public Date getExpireDate() {
        return _expireDate;
    }

    /**
     * @param expireDate
     */
    public void setExpireDate(Date expireDate) {
        this._expireDate = expireDate;
    }

    /**
     * @return active
     */
    public String getActive() {
        return _active;
    }

    /**
     * @param active
     */
    public void setActive(String active) {
        this._active = active;
    }

    public String getDefContent() {
        return _defContent;
    }

    public void setDefContent(String content) {
        _defContent = content;
    }

    public String getGraphContent() {
        return _graphContent;
    }

    public void setGraphContent(String content) {
        _graphContent = content;
    }

    /**
     * @return
     */
    public ProcessOwnerDVInfo getProcessOwner() {
		return _processOwner;
	}

	/**
	 * @param processOwner
	 */
	public void setProcessOwner(ProcessOwnerDVInfo processOwner) {
		this._processOwner = processOwner;
	}
}
