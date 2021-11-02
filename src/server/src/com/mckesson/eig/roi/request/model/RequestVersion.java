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
import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.base.model.BaseModel;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestVersion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestVersion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="versionSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="documentSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="docId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="versionNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="globalDocument" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="roiPages" type="{urn:eig.mckesson.com}RequestPage" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestVersion", propOrder = {
    "_versionSeq",
    "_documentSeq",
    "_docId",
    "_versionNumber",
    "_globalDocument",
    "_roiPages"
})
public class RequestVersion extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //Version Details
    
    @XmlElement(name="versionSeq")
    private long  _versionSeq;
    
    @XmlElement(name="documentSeq")
    private long  _documentSeq;
    
    @XmlElement(name="docId")
    private long _docId;
    
    @XmlElement(name="versionNumber")
    private long  _versionNumber;
    
 // used to identify whether this page belongs for global documents or not
    @XmlElement(name="globalDocument")
    private boolean _globalDocument;
    
    @XmlElement(name="roiPages")
    private List<RequestPage> _roiPages;

    

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
