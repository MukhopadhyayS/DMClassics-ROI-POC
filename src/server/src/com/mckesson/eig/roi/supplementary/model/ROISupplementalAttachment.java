package com.mckesson.eig.roi.supplementary.model;

import com.mckesson.eig.roi.request.model.FreeFormFacility;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SupplementalAttachment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupplementalAttachment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="patientId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dateOfService" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="attachmentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dateReceived" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="docFacility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="encounter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fileext" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="filename" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="filetype" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isDeleted" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pageCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="printable" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="submittedBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="subtitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="volume" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="externalSource" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="freeformFacility" type="{urn:eig.mckesson.com}FreeFormFacility"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplementalAttachment", propOrder = {
    "_id",
    "_patientId",
    "_comment",
    "_dateOfService",
    "_attachmentDate",
    "dateReceived",
    "_docFacility",
    "_encounter",
    "_fileext",
    "_filename",
    "_filetype",
    "_isDeleted",
    "_pageCount",
    "_path",
    "_printable",
    "_submittedBy",
    "_subtitle",
    "_type",
    "_uuid",
    "_volume",
    "_externalSource",
    "_freeformFacility"
})
public class ROISupplementalAttachment 
extends ROIAttachmentCommon {
    
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name="patientId")
    private long _patientId;
    
    @XmlTransient
    private long _freeFormFacilityId;
    
    @XmlElement(name="freeformFacility", required = true)
    private FreeFormFacility _freeformFacility;
    
    private Date dateReceived;
    

    
    @XmlElement(name="id")
    private long _id;
    @XmlTransient
    private long _createdBy;
    @XmlTransient
    private long _modifiedBy;
    @XmlTransient
    private Date _createdDt;
    @XmlTransient
    private Date _modifiedDt;
    @XmlTransient
    private int _recordVersion; 


    public long getId() {
        return _id;
    }

    public void setId(long id) {
        _id = id;
    }

    public long getCreatedBy() {
        return _createdBy;
    }

    public void setCreatedBy(long createdBy) {
        _createdBy = createdBy;
    }

    public long getModifiedBy() {
        return _modifiedBy;
    }

    public void setModifiedBy(long modifiedBy) {
        _modifiedBy = modifiedBy;
    }

    public Date getCreatedDt() {
        return _createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        _createdDt = createdDt;
    }

    public Date getModifiedDt() {
        return _modifiedDt;
    }

    public void setModifiedDt(Date modifiedDt) {
        _modifiedDt = modifiedDt;
    }

    public int getRecordVersion() {
        return _recordVersion;
    }

    public void setRecordVersion(int recordVersion) {
        _recordVersion = recordVersion;
    }
    
    
    public long getPatientId() { return _patientId; }
    public void setPatientId(long patientId) { _patientId = patientId; }

    public long getFreeFormFacilityId() { return _freeFormFacilityId; }
    public void setFreeFormFacilityId(long freeFormFacilityId) {
        _freeFormFacilityId = freeFormFacilityId;
    }
    
    public FreeFormFacility getFreeformFacility() { return _freeformFacility; }
    public void setFreeformFacility(FreeFormFacility freeformFacility) {
        _freeformFacility = freeformFacility;
    }
    
    
    public Date getDateReceived() {
        return dateReceived;
    }
    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }
    
    
}
