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

import java.util.ArrayList;
import java.util.List;

import com.mckesson.eig.roi.base.model.BaseModel;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SaveRequestPatientList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SaveRequestPatientList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="updatePatients" type="{urn:eig.mckesson.com}RequestPatient" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="updateEncounters" type="{urn:eig.mckesson.com}RequestEncounter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="updateDocuments" type="{urn:eig.mckesson.com}RequestDocument" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="updatePages" type="{urn:eig.mckesson.com}RequestPage" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="updateVersions" type="{urn:eig.mckesson.com}RequestVersion" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="deletePatient" type="{urn:eig.mckesson.com}DeletePatientList"/>
 *         &lt;element name="updateSupplementalDocument" type="{urn:eig.mckesson.com}RequestSupplementalDocument" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="updateSupplementalAttachement" type="{urn:eig.mckesson.com}RequestSupplementalAttachment" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SaveRequestPatientList", propOrder = {
    "_requestId",
    "_updatePatients",
    "_updateEncounters",
    "_updateDocuments",
    "_updatePages",
    "_updateVersions",
    "_deletePatient",
    "_updateSupplementalDocument",
    "_updateSupplementalAttachement"
})
public class SaveRequestPatientsList 
extends BaseModel {
    
    private static final long serialVersionUID = 1L;

    @XmlElement(name="requestId")
    private long _requestId;
    
    @XmlElement(name="updatePatients")
    private List<RequestPatient> _updatePatients;

    @XmlElement(name="updateEncounters")
    private List <RequestEncounter> _updateEncounters;
    
    @XmlElement(name="updateDocuments")
    private List <RequestDocument> _updateDocuments;
    
    @XmlElement(name="updatePages")
    private List <RequestPage> _updatePages;
    
    @XmlElement(name="updateVersions")
    private List <RequestVersion> _updateVersions;
    
    @XmlElement(name="deletePatient", required = true)
    private DeletePatientList _deletePatient;
    
    @XmlElement(name="updateSupplementalDocument")
    private List <RequestSupplementalDocument> _updateSupplementalDocument;
    
    @XmlElement(name="updateSupplementalAttachement")
    private List <RequestSupplementalAttachment> _updateSupplementalAttachement;
    
    
    
    
    
    public List<RequestPatient> getUpdatePatients() {  return _updatePatients;   }
    public void setUpdatePatients(List<RequestPatient> updatePatients) {
        _updatePatients = updatePatients;  }

    public void setRequestId(long requestId) { this._requestId = requestId; }
    public long getRequestId() { return _requestId; }

    public DeletePatientList getDeletePatient() { return _deletePatient; }
    public void setDeletePatient(DeletePatientList deletePatient) { 
        _deletePatient = deletePatient;
    }
    
    public void setUpdateEncounters(List <RequestEncounter> updateEncounters) {
        _updateEncounters = updateEncounters; }
    public List <RequestEncounter> getUpdateEncounters() { return _updateEncounters; }

    public void setUpdateDocuments(List <RequestDocument> updateDocuments) {
        _updateDocuments = updateDocuments; }
    public List <RequestDocument> getUpdateDocuments() { return _updateDocuments; }

    public void setUpdateVersions(List <RequestVersion> updateVersions) {
        _updateVersions = updateVersions; }
    public List <RequestVersion> getUpdateVersions() { return _updateVersions; }

    public void setUpdatePages(List <RequestPage> updatePages) {
        _updatePages = updatePages; }
    public List <RequestPage> getUpdatePages() { return _updatePages; }
    
    public List <RequestSupplementalDocument> getUpdateSupplementalDocument() {
        return _updateSupplementalDocument;
    }
    public void setUpdateSupplementalDocument(
            List <RequestSupplementalDocument> updateSupplementalDocument) {
        _updateSupplementalDocument = updateSupplementalDocument;
    }
    
    public List <RequestSupplementalAttachment> getUpdateSupplementalAttachement() {
        return _updateSupplementalAttachement;
    }
    public void setUpdateSupplementalAttachement(
            List <RequestSupplementalAttachment> updateSupplementalAttachement) {
        _updateSupplementalAttachement = updateSupplementalAttachement;
    }
    
    @Override
    public String toString() {
        
        return new StringBuffer()
                    .append("RequestId:")
                    .append(_requestId)
                    .append(" , UpdatePatients List size :")
                    .append(_updatePatients.size())
                    .append(" , UpdateEncounters List size :")
                    .append(getUpdateEncounters().size())
                    .append(" , UpdateDocuments List size :")
                    .append(getUpdateDocuments().size())
                    .append(" , UpdateVersions List size :")
                    .append(getUpdateVersions().size())
                    .append(" , UpdatePages List size :")
                    .append(getUpdatePages().size())
                    .append(" , DeletePatient :")
                    .append(getDeletePatient())
                .toString();
    }

}
