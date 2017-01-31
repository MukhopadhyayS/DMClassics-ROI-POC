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

/**
 * @author OFS
 * @date   JUL 03, 2012
 */
public class RequestDocument
extends BaseModel
implements Serializable {

    private static final long serialVersionUID = 1L;

    //Document Details
    private long    _documentSeq;
    private long    _encounterSeq;
    private long    _patientSeq;
    private boolean _globalDocument;
    private String  _name;
    private String  _subtitle;
    private String  _chartOrder;
    private long  _docId;
    private long  _docTypeId;
    private Date    _dateTime;

    // For Insertion of Documents
    private String _facility;
    private String _duid;
    private String _mrn;
    private String _encounter;

    private List<RequestVersion> _roiVersions;

    public long getDocumentSeq() { return _documentSeq; }
    public void setDocumentSeq(long documentSeq) { _documentSeq = documentSeq; }

    public long getEncounterSeq() { return _encounterSeq; }
    public void setEncounterSeq(long requestEncounterSeq) { _encounterSeq = requestEncounterSeq; }

    public String getFacility() { return _facility; }
    public void setFacility(String facility) { _facility = facility; }

    public String getMrn() { return _mrn; }
    public void setMrn(String mrn) { _mrn = mrn; }

    public String getEncounter() { return _encounter; }
    public void setEncounter(String encounter) { _encounter = encounter; }

    public List<RequestVersion> getRoiVersions() { return _roiVersions; }
    public void setRoiVersions(List<RequestVersion> roiVersions) { _roiVersions = roiVersions; }

    public String getName() { return _name; }
    public void setName(String name) { _name = name; }

    public String getSubtitle() { return _subtitle; }
    public void setSubtitle(String subtitle) { _subtitle = subtitle; }

    public String getChartOrder() { return _chartOrder; }
    public void setChartOrder(String chartOrder) { _chartOrder = chartOrder; }
    
    public int getChartOrderAsInt() {
        try {
            return Integer.valueOf(_chartOrder);
        } catch(Exception e) {
            return 0;
        }
    }

    public long getDocId() { return _docId; }
    public void setDocId(long docId) { _docId = docId; }

    public void setDateTime(Date dateTime) { _dateTime = dateTime; }
    public Date getDateTime() { return _dateTime; }

    public void setDocTypeId(long docTypeId) { _docTypeId = docTypeId; }
    public long getDocTypeId() { return _docTypeId; }

    public long getPatientSeq() { return _patientSeq; }
    public void setPatientSeq(long patientSeq) { _patientSeq = patientSeq; }

    public boolean isGlobalDocument() { return _globalDocument; }
    public void setGlobalDocument(boolean globalDocument) { _globalDocument = globalDocument; }

    public String getDuid() { return _duid; }
    public void setDuid(String duid) { _duid = duid; }

    @Override
    public String toString() {

        return new StringBuffer()
                    .append("Document Seq:")
                    .append(_documentSeq)
                    .append(" , RequestEncounter Seq:")
                    .append(_encounterSeq)
                    .append(" , Document Name:")
                    .append(_name)
                    .append(" , DocId:")
                    .append(_docId)
                    .append(" , Subtitle:")
                    .append(_subtitle)
                    .append(" , Chart Order:")
                    .append(_chartOrder)
                .toString();
    }

}
