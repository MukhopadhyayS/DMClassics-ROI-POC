package com.mckesson.eig.roi.supplementary.model;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.mckesson.eig.roi.admin.dao.LetterTemplateDAOImpl;
import com.mckesson.eig.roi.admin.service.ROIAttachmentService.FailedReasonCode;
import com.mckesson.eig.roi.utils.AccessFileLoader;
import com.mckesson.dm.core.common.logging.OCLogger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;



/**
 * <p>Java class for SupplementarityAttachment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupplementarityAttachment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="attachmentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dateReceived" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dateOfService" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplementarityAttachment", propOrder = {
    "_id",
    "_mrn",
    "_facility",
    "_attachmentDate",
    "dateReceived",
    "documentSource",
    "_comment",
    "_dateOfService",
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
    "_externalSource"
})
public class ROISupplementarityAttachment extends ROIAttachmentCommon {
    private static final OCLogger LOG = new OCLogger(LetterTemplateDAOImpl.class);
    
    @XmlElement(name="facility", required = true)
    private String _facility;
    
    @XmlElement(name="mrn", required = true)
    private String _mrn;

    @XmlElement(nillable = true)
    private Date dateReceived;
    
    private String  documentSource;
    
    
    
    public String getDocumentSource() {
        return documentSource;
    }

    public void setDocumentSource(String documentSource) {
        this.documentSource = documentSource;
    }

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
   
    public String getMrn() {
        return _mrn;
    }
    
    public void setMrn(String mrn) {
        _mrn = mrn;
    }

    public String getFacility() {
        return _facility;
    }

    public void setFacility(String facility) {
        _facility = facility;
    }

    
    
    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public FailedReasonCode delete() {
        
        FailedReasonCode deleteLog = FailedReasonCode.DELETION_SUCCESSFUL;
        File file = null;
        try {
		//DE7315 External Control of File Name or Path
            file = AccessFileLoader.getFile(getVolume(),  (getPath() + getUuid()));
        } catch (IOException e) {
                 LOG.error("Exception in delete() "+e.getLocalizedMessage());
        }
        if(file != null) {
            if (!file.exists()) {
                deleteLog = FailedReasonCode.FILE_MISSING;
            } else if (!file.canWrite()) {
                deleteLog = FailedReasonCode.FILE_DELETE_ACCESS_DENIED;
            } else {        
                boolean d = file.delete();
                if (!d) {
                    deleteLog = FailedReasonCode.DELETION_UNSUCCESSFUL;
                } else {
                    if (file.exists()) {
                        deleteLog = FailedReasonCode.FILE_EXISTS_AFTER_DELETION;
                    }
                }
            }
        }
        return deleteLog;
    }
}
