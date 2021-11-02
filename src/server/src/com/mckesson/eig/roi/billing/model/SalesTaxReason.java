package com.mckesson.eig.roi.billing.model;

import java.util.Date;

import com.mckesson.eig.roi.base.model.BaseModel;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SalesTaxReason complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesTaxReason">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createdBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="modifiedBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createdDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="modifiedDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="requestCoreChargesSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reasonDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesTaxReason", propOrder = {
    "_id",
    "_createdBy",
    "_modifiedBy",
    "_createdDt",
    "_modifiedDt",
    "_recordVersion",
    "_requestCoreChargesSeq",
    "_reason",
    "_reasonDate"
})
public class SalesTaxReason extends BaseModel{
    
    @XmlElement(name="requestCoreChargesSeq")
    private long _requestCoreChargesSeq;
    
    @XmlElement(name="reason", required = true)
    private String _reason;
    
    @XmlElement(name="reasonDate", required = true, nillable = true)
    private Date _reasonDate;
    
    @XmlElement(name="id")
    private long _id;
    
    @XmlElement(name="createdBy")
    private long _createdBy;
    
    @XmlElement(name="modifiedBy")
    private long _modifiedBy;
    
    @XmlElement(name="createdDt", required = true, nillable = true)
    private Date _createdDt;
    
    @XmlElement(name="modifiedDt", required = true, nillable = true)
    private Date _modifiedDt;
    
    @XmlElement(name="recordVersion")
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
    
    
    public long getRequestCoreChargesSeq() {
        return _requestCoreChargesSeq;
    }
    public void setRequestCoreChargesSeq(long requestCoreChargesSeq) {
        this._requestCoreChargesSeq = requestCoreChargesSeq;
    }
    
    public String getReason() {
        return _reason;
    }
    public void setReason(String reason) {
        this._reason = reason;
    }
    public Date getReasonDate() {
        return _reasonDate;
    }
    public void setReasonDate(Date reasonDate) {
        this._reasonDate = reasonDate;
    }

}
