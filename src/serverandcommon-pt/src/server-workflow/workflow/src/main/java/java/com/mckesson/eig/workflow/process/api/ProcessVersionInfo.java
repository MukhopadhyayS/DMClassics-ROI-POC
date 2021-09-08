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
package com.mckesson.eig.workflow.process.api;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * @author OFS
 *
 * @date Apr 17, 2009
 * @since HECM 1.0.3; Apr 17, 2009
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ProcessVersionInfo")
public class ProcessVersionInfo implements Serializable {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = -7777710584484493467L;

    private long _versionId;

    private String _processName;
    private String _processDescription;

    private Date _expireDateTime;
    private Date _effectiveDateTime;

    private ProcessInfo _processInfo;
    private ProcessAttributeList _processAttributeList;

    private Set<ProcessAttribute> _processAttributesSet;

    public ProcessVersionInfo() {

        _processInfo = new ProcessInfo();
        _processAttributeList = new ProcessAttributeList();
    }

    /**
     * @return
     */
    public long getVersionId() {
        return _versionId;
    }

    /**
     * @param version
     */
    @XmlElement(name = "versionId")
    public void setVersionId(long version) {
        _versionId = version;
    }

    /**
     * This method is used to retrieve the process name value.
     * @return _processName
     */
    public String getProcessName() {
        return _processName;
    }

    /**
     * This method is used to set the process name value.
     * @param processName
     */
    @XmlElement(name = "processName")
    public void setProcessName(String processName) {
        _processName = processName;
    }

    /**
     * This method is used to retrieve the Process Description value.
     * @return _processDescription
     */
   public String getProcessDescription() {
        return _processDescription;
    }

   /**
    * This method is used to set the process description value.
    * @param processDescription
    */
    @XmlElement(name = "processDescription")
    public void setProcessDescription(String processDescription) {
        _processDescription = processDescription;
    }

    /**
     * @return expireDateTime
     */
    public Date getExpireDateTime() {
        return _expireDateTime;
    }

    /**
     * @param expireDateTime
     */
    @XmlElement(name = "expireDateTime")
    public void setExpireDateTime(Date expireDateTime) {
        this._expireDateTime = expireDateTime;
    }

    /**
     * @return effectiveDateTime
     */
    public Date getEffectiveDateTime() {
        return _effectiveDateTime;
    }

    /**
     * @param effectiveDateTime
     */
    @XmlElement(name = "effectiveDateTime")
    public void setEffectiveDateTime(Date effectiveDateTime) {
        this._effectiveDateTime = effectiveDateTime;
    }

    /**
     * @return processAttributeList
     */
    public ProcessAttributeList getProcessAttributeList() {

        if (_processAttributeList == null) {
            _processAttributeList = new ProcessAttributeList();
        }
        return _processAttributeList;
    }

    /**
     * @param processAttributeList
     */
    @XmlElement(name = "processAttributeList", type = ProcessAttributeList.class)
    public void setProcessAttributeList(ProcessAttributeList processAttributeList) {
        this._processAttributeList = processAttributeList;
    }

	/**
	 * @return processAttributesSet
	 */
	public Set<ProcessAttribute> getProcessAttributesSet() {
		return _processAttributesSet;
	}

	/**
	 * @param processAttributesSet
	 */
	@XmlElement(name = "processAttributesSet")
	public void setProcessAttributesSet(Set<ProcessAttribute> processAttributesSet) {
		this._processAttributesSet = processAttributesSet;
	}

    /**
     * @return processInfo
     */
    public ProcessInfo getProcessInfo() {
        return _processInfo;
    }

    /**
     * @param processInfo
     */
    @XmlTransient
    public void setProcessInfo(ProcessInfo processInfo) {
        this._processInfo = processInfo;
    }

    /**
     * This method returns a string representation of the Process List that contains
     * Process objects.
     *
     * @return strBuff
     */
    public String toString() {

    	StringBuffer theProcess = new StringBuffer("ProcessVersionInfo[");
        theProcess.append("versionId=" + _versionId
                + ", processName="  + _processName
                + ", processDescription=" + _processDescription
                + ", expireDateTime=" + _expireDateTime
                + ", effectiveDateTime=" + _effectiveDateTime
                + "]");

        return theProcess.toString();
   }
}
