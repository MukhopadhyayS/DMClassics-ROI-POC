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

import com.mckesson.eig.roi.base.model.BaseModel;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestPage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestPage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pageSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="versionSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="contentCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="imnetId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isSelectedForRelease" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isReleased" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="pageNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="globalDocument" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="pageNumberRequested" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="deleted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestPage", propOrder = {
    "_pageSeq",
    "_versionSeq",
    "_contentCount",
    "_imnetId",
    "_isSelectedForRelease",
    "_isReleased",
    "_pageNumber",
    "_globalDocument",
    "_pageNumberRequested",
    "_deleted"
})
public class RequestPage extends BaseModel  {

    private static final long serialVersionUID = 1L;

    // Page Details
    @XmlElement(name="pageSeq")
    private long _pageSeq;
    
    @XmlElement(name="versionSeq")
    private long _versionSeq;
    
    @XmlElement(name="contentCount")
    private long _contentCount;
    
    @XmlElement(name="imnetId", required = true)
    private String _imnetId;
    
    @XmlElement(name="isSelectedForRelease")
    private boolean _isSelectedForRelease;
    
    @XmlElement(name="isReleased")
    private boolean _isReleased;
    
    @XmlElement(name="pageNumber")
    private int _pageNumber;
    
    @XmlTransient
    private long _versionNumber;
    
    @XmlTransient
    private long _docId;
    
    @XmlElement(name="pageNumberRequested")
    private int _pageNumberRequested;
    
    @XmlElement(name="deleted")
    private boolean _deleted;

    // used to identify whether this page belongs for global documents or not
    @XmlElement(name="globalDocument")
    private boolean _globalDocument;
    
    
    
    public long getVersionNumber() {
        return _versionNumber;
    }
    public void setVersionNumber(long versionNumber) {
        _versionNumber = versionNumber;
    }

    public long getPageSeq() {
        return _pageSeq;
    }
    public void setPageSeq(long pageSeq) {
        _pageSeq = pageSeq;
    }

    public long getVersionSeq() {
        return _versionSeq;
    }
    public void setVersionSeq(long requestVersionSeq) {
        _versionSeq = requestVersionSeq;
    }

    public String getImnetId() {
        return _imnetId;
    }
    public void setImnetId(String imnetId) {
        _imnetId = imnetId;
    }

    public long getContentCount() {
        return _contentCount;
    }
    public void setContentCount(long contentCount) {
        _contentCount = contentCount;
    }

    public void setSelectedForRelease(boolean isSelectedForRelease) {
        _isSelectedForRelease = isSelectedForRelease;
    }
    public boolean isSelectedForRelease() {
        return _isSelectedForRelease;
    }

    public void setReleased(boolean isReleased) {
        _isReleased = isReleased;
    }
    public boolean isReleased() {
        return _isReleased;
    }

    public void setPageNumber(int page) {
        _pageNumber = page;
    }
    public int getPageNumber() {
        return _pageNumber;
    }

    public long getDocId() {
        return _docId;
    }
    public void setDocId(long versionId) {
        _docId = versionId;
    }

    public boolean isGlobalDocument() {
        return _globalDocument;
    }
    public void setGlobalDocument(boolean globalDocument) {
        _globalDocument = globalDocument;
    }

    @Override
    public String toString() {

        return new StringBuffer()
                .append("Page Seq:")
                .append(_pageSeq)
                .append(", Page Number:")
                .append(_pageNumber)
                .append(", Page Number Requested:")
                .append(_pageNumberRequested)
                .append(" , RequestVersion Seq:")
                .append(_versionSeq)
                .append(" , Imnet Id:")
                .append(_imnetId)
                .append(" , DocumentId:")
                .append(_docId)
                .append(" , PageSelectedForRelease:")
                .append(isSelectedForRelease())
                .append(" , PageReleased:")
                .append(isReleased())
                .append(" , deleted:")
                .append(isReleased())
                .toString();
    }

    public final int getPageNumberRequested() {
        return _pageNumberRequested;
    }

    public final void setPageNumberRequested(int pageNumberRequested) {
        this._pageNumberRequested = pageNumberRequested;
    }
    public boolean isDeleted() {
        return _deleted;
    }
    public void setDeleted(boolean deleted) {
        _deleted = deleted;
    }

}
