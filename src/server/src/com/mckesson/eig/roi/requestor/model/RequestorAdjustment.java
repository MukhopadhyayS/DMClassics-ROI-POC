package com.mckesson.eig.roi.requestor.model;

import java.util.Date;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.utility.util.StringUtilities;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestorAdjustment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorAdjustment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestorSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestorName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceSeq" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="adjustmentType" type="{urn:eig.mckesson.com}AdjustmentType"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="unappliedAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="appliedAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="adjustmentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="delete" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="prebillAdjustment" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorAdjustment", propOrder = {
    "_id",
    "_requestorSeq",
    "_requestorName",
    "_requestorType",
    "_invoiceSeq",
    "_adjustmentType",
    "_reason",
    "_amount",
    "_unappliedAmount",
    "_appliedAmount",
    "_adjustmentDate",
    "_note",
    "_delete",
    "_prebillAdjustment"
})
public class RequestorAdjustment
extends BaseModel {

    private static final long serialVersionUID = 1L;
    
    @XmlElement(name="requestorSeq")
    private long _requestorSeq;
    
    @XmlElement(name="requestorName")
    private String _requestorName;
    
    @XmlElement(name="requestorType")
    private String _requestorType;
    
    @XmlElement(name="invoiceSeq")
    private long _invoiceSeq;

    @XmlElement(name="adjustmentType")
    private AdjustmentType _adjustmentType;
    

    @XmlElement(name="reason")
    private String _reason;
    
    @XmlElement(name="amount")
    private Double _amount;
    

    @XmlElement(name="unappliedAmount")
    private Double _unappliedAmount;
    
    @XmlElement(name="appliedAmount")
    private Double _appliedAmount;
    
    @XmlElement(name="adjustmentDate")
    private Date _adjustmentDate;
    
    @XmlElement(name="note")
    private String _note;
    
    @XmlElement(name="delete")
    private boolean _delete;
    
    @XmlElement(name="prebillAdjustment")
    private boolean _prebillAdjustment;

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
    
    public long getRequestorSeq() { return _requestorSeq; }
    public void setRequestorSeq(long requestorSeq) { this._requestorSeq = requestorSeq; }

    public String getReason() { return _reason; }
    public void setReason(String reason) { this._reason = reason; }

    public Double getAmount() { return _amount; }
    public void setAmount(Double amount) { this._amount = amount; }

    public Double getUnappliedAmount() { return _unappliedAmount; }
    public void setUnappliedAmount(Double unappliedAmount) {
        this._unappliedAmount = unappliedAmount;
    }

    public Date getAdjustmentDate() { return _adjustmentDate; }
    public void setAdjustmentDate(Date adjustmentDate) { this._adjustmentDate = adjustmentDate; }

    public String getNote() { return _note; }
    public void setNote(String note) { this._note = note; }

    public long getInvoiceSeq() { return _invoiceSeq; }
    public void setInvoiceSeq(long invoiceSeq) { this._invoiceSeq = invoiceSeq; }

    public Double getAppliedAmount() { return _appliedAmount; }
    public void setAppliedAmount(Double appliedAmount) { _appliedAmount = appliedAmount; }

    public String getRequestorName() {return _requestorName;}
    public void setRequestorName(String requestorName) {_requestorName = requestorName;}

    public String getRequestorType() { return _requestorType; }
    public void setRequestorType(String requestorType) { _requestorType = requestorType; }

    public boolean isDelete() { return _delete; }
    public void setDelete(boolean delete) { _delete = delete; }

    public AdjustmentType getAdjustmentType() { return _adjustmentType; }
    public void setAdjustmentType(AdjustmentType adjustmentType) {
        _adjustmentType = adjustmentType;
    }
    
    public boolean isPrebillAdjustment() {
        return _prebillAdjustment;
    }
    public void setPrebillAdjustment(boolean prebillAdjustment) {
        _prebillAdjustment = prebillAdjustment;
    }

    public String getAdjustmentTypeAsString() {
        return (null == _adjustmentType) ? null : _adjustmentType.name();
    }
    public void setAdjustmentTypeAsString(String adjustmentType) {
        _adjustmentType = AdjustmentType.valueOf(adjustmentType);
    }

    public String getAdjustmentTypeByValue() {
        return (null == _adjustmentType) ? null : _adjustmentType.toString();
    }
    public void setAdjustmentTypeByValue(String adjustmentType) {

        _adjustmentType = null;
        AdjustmentType[] values = AdjustmentType.values();
        for (AdjustmentType type : values) {

            if (type.toString().equalsIgnoreCase(adjustmentType)) {

                _adjustmentType = type;
                break;
            }
        }
    }
    
   

    /**
     * This method create audit comment for Requestor Post Adjustment
     * @return audit comment for retrieve event
     */
    public String constructPostAdjustmentAuditComment(String userName, String adjType) {

        return new StringBuffer().append("A ")
                .append(ROIConstants.CURRENCY_FORMAT.format(_amount))
                .append(" adjustment from ")
                .append(adjType)
                .append(" was recorded for ")
                .append(_requestorName)
                .append(" by ")
                .append(StringUtilities.safeTrim(userName))
                .toString();
    }

    /**
     * This method create audit comment for Requestor Delete Adjustment
     * @return audit comment for retrieve event
     */
    public String constructDeleteAdjustmentAuditComment(String userName, String requestorName) {

        return new StringBuffer().append("A ")
                .append(ROIConstants.CURRENCY_FORMAT.format(_unappliedAmount))
                .append(" adjustment was deleted from ")
                .append(requestorName)
                .append("'s account by ")
                .append(StringUtilities.safeTrim(userName))
                .append(".")
                .toString();
    }

}
