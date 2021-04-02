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
import java.util.List;

import com.mckesson.eig.roi.base.model.BaseModel;

/**
 * @author OFS
 * @date   JUL 03, 2012
 */
public class RequestVersion extends BaseModel
implements Serializable {

    private static final long serialVersionUID = 1L;

    //Version Details
    private long  _versionSeq;
    private long  _documentSeq;
    private long  _versionNumber;
    private long _docId;
    private List<RequestPage> _roiPages;

    // used to identify whether this page belongs for global documents or not
    private boolean _globalDocument;

    public long getDocId() { return _docId; }
    public void setDocId(long docId) { _docId = docId; }

    public long getVersionSeq() { return _versionSeq; }
    public void setVersionSeq(long versionId) { _versionSeq = versionId; }

    public long getDocumentSeq() { return _documentSeq; }
    public void setDocumentSeq(long documentSeq) { _documentSeq = documentSeq; }

    public List<RequestPage> getRoiPages() { return _roiPages; }
    public void setRoiPages(List<RequestPage> roiPages) { _roiPages = roiPages; }

    public long getVersionNumber() { return _versionNumber; }
    public void setVersionNumber(long versionNumber) { _versionNumber = versionNumber; }

    public boolean isGlobalDocument() { return _globalDocument; }
    public void setGlobalDocument(boolean globalDocument) { _globalDocument = globalDocument; }

    @Override
    public String toString() {

        return new StringBuffer()
                    .append("Version Seq:")
                    .append(_versionSeq)
                    .append(" , RequestDocument Seq:")
                    .append(_documentSeq)
                    .append(" , Version Number:")
                    .append(_versionNumber)
                .toString();
    }

}
