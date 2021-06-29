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
 *
 * @author ganeshramr
 * @date   Sep 01, 2008
 * @since  HPF 13.1 [ROI]; May 12, 2008
 */
public class DocTypeRelation implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private long _id;
    private long _docTypeId;
    private long _codeSetId;
    private String _type;
    private int _createdBy;
    private int _modifiedBy;
    private Date _modifiedDt;
    private int _orgId;
    private int _recordVersion;
    // changes for mu doc type
    private int _muDocumentId;

    public DocTypeRelation() { }

    public DocTypeRelation(long id, long codeSetId, String type) {

        _docTypeId = id;
        _codeSetId = codeSetId;
        _type = Enum.valueOf(RealtionType.class, type.toLowerCase()).toString();
    }
    // changes for mu doc type
    public static enum RealtionType {
        disclosure, authorize, mu
    }

    public long getId() {
        return _id;
    }
    public void setId(long id) {
        _id = id;
    }

    public long getDocTypeId() {
        return _docTypeId;
    }
    public void setDocTypeId(long docTypeId) {
        _docTypeId = docTypeId;
    }

    public long getCodeSetId() {
        return _codeSetId;
    }
    public void setCodeSetId(long codeSetId) {
        _codeSetId = codeSetId;
    }

    public String getType() {
        return _type;
    }
    public void setType(String type) {
        _type = Enum.valueOf(RealtionType.class, type.toLowerCase()).toString();
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

    public Date getModifiedDt() {
        return _modifiedDt;
    }
    public void setModifiedDt(Date modifiedDt) {
        _modifiedDt = modifiedDt;
    }

    public int getOrgId() {
        return _orgId;
    }
    public void setOrgId(int orgId) {
        _orgId = orgId;
    }

    public int getRecordVersion() {
        return _recordVersion;
    }
    public void setRecordVersion(int version) {
        _recordVersion = version;
    }
    // changes for mu doc type

    public int getMuDocumentId() {
        return _muDocumentId;
    }

    public void setMuDocumentId(int documentId) {
        _muDocumentId = documentId;
    }

}
