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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestDocument">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="encounterSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="encounter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="subtitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="chartOrder" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="docId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="docTypeId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="dateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="patientSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="globalDocument" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="roiVersions" type="{urn:eig.mckesson.com}RequestVersion" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestDocument", propOrder = {
    "_documentSeq",
    "_encounterSeq",
    "_mrn",
    "_encounter",
    "_facility",
    "_name",
    "_subtitle",
    "_chartOrder",
    "_docId",
    "_docTypeId",
    "_dateTime",
    "_patientSeq",
    "_globalDocument",
    "_roiVersions"
})
public class RequestDocument extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //Document Details
    @XmlElement(name="documentSeq")
    private long    _documentSeq;
    
    @XmlElement(name="encounterSeq")
    private long    _encounterSeq;
    
    @XmlElement(name="patientSeq")
    private long    _patientSeq;
    
    @XmlElement(name="globalDocument")
    private boolean _globalDocument;
    
    @XmlElement(name="name", required = true)
    private String  _name;
    
    @XmlElement(name="subtitle", required = true)
    private String  _subtitle;
    
    @XmlElement(name="chartOrder", required = true)
    private String  _chartOrder;
    
    @XmlElement(name="docId")
    private long  _docId;
    
    @XmlElement(name="docTypeId")
    private long  _docTypeId;
    
    @XmlElement(name="dateTime", required = true, nillable = true)
    private Date    _dateTime;

    // For Insertion of Documents
    
    @XmlElement(name="facility", required = true)
    private String _facility;
    
    @XmlTransient
    private String _duid;
    
    @XmlElement(name="mrn", required = true)
    private String _mrn;
    
    @XmlElement(name="encounter", required = true)
    private String _encounter;

    @XmlElement(name="roiVersions")
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
