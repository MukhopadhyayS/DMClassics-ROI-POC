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

/**
 * @author OFS
 * @date JUL 03, 2012
 */
public class RequestPage
        extends BaseModel {

    private static final long serialVersionUID = 1L;

    // Page Details
    private long _pageSeq;
    private long _versionSeq;
    private long _contentCount;
    private String _imnetId;
    private boolean _isSelectedForRelease;
    private boolean _isReleased;
    private int _pageNumber;
    private long _versionNumber;
    private long _docId;
    private int _pageNumberRequested;
    private boolean _deleted;

    // used to identify whether this page belongs for global documents or not
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
