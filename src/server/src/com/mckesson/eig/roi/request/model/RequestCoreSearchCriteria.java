/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.utility.util.StringUtilities;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestCoreSearchCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestCoreSearchCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="patientLastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientFirstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientSsn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="encounter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientEpn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="patientDob" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestReason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="balanceDue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="balanceDueOperator" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorType" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestorId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="completedDateFrom" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="completedDateTo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="receiptDateFrom" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="receiptDateTo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="paginationData" type="{urn:eig.mckesson.com}PaginationData"/>
 *         &lt;element name="maxCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestCoreSearchCriteria", propOrder = {
    "_requestId",
    "_patientLastName",
    "_patientFirstName",
    "_mrn",
    "_patientSsn",
    "_encounter",
    "_patientEpn",
    "_patientId",
    "_patientDob",
    "_facility",
    "_requestStatus",
    "_requestReason",
    "_invoiceNumber",
    "_balanceDue",
    "_balanceDueOperator",
    "_requestorName",
    "_requestorTypeName",
    "_requestorType",
    "_requestorId",
    "_completedDateFrom",
    "_completedDateTo",
    "_receiptDateFrom",
    "_receiptDateTo",
    "_paginationData",
    "_maxCount"
})
public class RequestCoreSearchCriteria
implements Serializable {

    private static final long serialVersionUID = 1L;

    // patient based search criteria
    @XmlElement(name="patientLastName", required = true)
    private String _patientLastName;
    
    @XmlElement(name="patientFirstName", required = true)
    private String _patientFirstName;
    
    @XmlElement(name="patientId")
    private long _patientId;
    
    @XmlElement(name="mrn", required = true)
    private String _mrn;
    
    @XmlElement(name="patientSsn", required = true)
    private String _patientSsn;
    
    @XmlElement(name="patientEpn", required = true)
    private String _patientEpn;
    
    @XmlElement(name="patientDob", required = true, nillable = true)
    private Date _patientDob;
    
    @XmlElement(name="facility", required = true)
    private String _facility;

    // documents encounter baed criteria
    @XmlElement(name="encounter")
    private String _encounter;

    // request based search criteria
    @XmlElement(name="requestId")
    private long _requestId;
    
    @XmlElement(name="requestStatus", required = true)
    private String _requestStatus;
    
    @XmlElement(name="requestReason", required = true)
    private String _requestReason;
    
    @XmlElement(name="completedDateFrom", required = true, nillable = true)
    private Date _completedDateFrom;
    
    @XmlElement(name="completedDateTo", required = true, nillable = true)
    private Date _completedDateTo;
    
    @XmlElement(name="receiptDateFrom", required = true, nillable = true)
    private Date _receiptDateFrom;
    
    @XmlElement(name="receiptDateTo", required = true, nillable = true)
    private Date _receiptDateTo;

    // request delivery based search criteria
    @XmlElement(name="invoiceNumber")
    private long _invoiceNumber;
    
    @XmlElement(name="balanceDue")
    private double _balanceDue;
    
    @XmlElement(name="balanceDueOperator", required = true)
    private String _balanceDueOperator;


    // requestor based search criteria
    @XmlElement(name="requestorName", required = true)
    private String _requestorName;
    
    @XmlElement(name="requestorTypeName", required = true)
    private String _requestorTypeName;
    
    @XmlElement(name="requestorType")
    private long _requestorType;
    
    @XmlElement(name="requestorId")
    private long _requestorId;

    @XmlElement(name="paginationData", required = true)
    private PaginationData _paginationData;
    
    @XmlElement(name="maxCount")
    private long _maxCount;
    
    
    

    public String getPatientLastName() { return _patientLastName; }
    public void setPatientLastName(String patientLastName) { _patientLastName = patientLastName; }

    public String getPatientFirstName() { return _patientFirstName; }
    public void setPatientFirstName(String patientFirstName) {
        _patientFirstName = patientFirstName;
    }

    public String getMrn() { return _mrn; }
    public void setMrn(String mrn) { _mrn = mrn; }

    public String getEncounter() { return _encounter; }
    public void setEncounter(String encounter) { _encounter = encounter; }

    public String getPatientSsn() { return _patientSsn; }
    public void setPatientSsn(String patientSsn) { _patientSsn = patientSsn; }

    public String getPatientEpn() { return _patientEpn; }
    public void setPatientEpn(String patientEpn) { _patientEpn = patientEpn; }

    public Date getPatientDob() { return _patientDob; }
    public void setPatientDob(Date patientDob) { _patientDob = patientDob; }

    public String getFacility() { return _facility; }
    public void setFacility(String facility) { _facility = facility; }

    public String getRequestStatus() { return _requestStatus; }
    public void setRequestStatus(String requestStatus) { _requestStatus = requestStatus; }

    public String getRequestReason() { return _requestReason; }
    public void setRequestReason(String requestReason) { _requestReason = requestReason; }

    public long getInvoiceNumber() { return _invoiceNumber; }
    public void setInvoiceNumber(long invoiceNumber) { _invoiceNumber = invoiceNumber; }

    public long getRequestId() { return _requestId; }
    public void setRequestId(long requestId) { _requestId = requestId; }

    public double getBalanceDue() { return _balanceDue; }
    public void setBalanceDue(double balanceDue) { _balanceDue = balanceDue; }

    public String getBalanceDueOperator() { return _balanceDueOperator; }
    public void setBalanceDueOperator(String balanceDueOperator) {
        _balanceDueOperator = balanceDueOperator;
    }

    public Date getCompletedDateFrom() { return _completedDateFrom; }
    public void setCompletedDateFrom(Date completedDateFrom) {
        _completedDateFrom = completedDateFrom;
    }

    public Date getCompletedDateTo() { return _completedDateTo; }
    public void setCompletedDateTo(Date completedDateTo) { _completedDateTo = completedDateTo; }

    public Date getReceiptDateFrom() { return _receiptDateFrom; }
    public void setReceiptDateFrom(Date receiptDateFrom) { _receiptDateFrom = receiptDateFrom; }

    public Date getReceiptDateTo() { return _receiptDateTo; }
    public void setReceiptDateTo(Date receiptDateTo) { _receiptDateTo = receiptDateTo; }

    public String getRequestorName() { return _requestorName; }
    public void setRequestorName(String requestorName) { _requestorName = requestorName; }

    public long getRequestorId() { return _requestorId; }
    public void setRequestorId(long requestorId) { _requestorId = requestorId; }

    public long getRequestorType() { return _requestorType; }
    public void setRequestorType(long requestorType) { _requestorType = requestorType; }

    public PaginationData getPaginationData() { return _paginationData; }
    public void setPaginationData(PaginationData paginationData) {
        _paginationData = paginationData;
    }

    public long getMaxCount() { return _maxCount; }
    public void setMaxCount(long maxCount) { _maxCount = maxCount; }

    public long getPatientId() { return _patientId; }
    public void setPatientId(long patientId) { _patientId = patientId; }

    public String getRequestorTypeName() { return _requestorTypeName; }
    public void setRequestorTypeName(String requestorTypeName) {
        _requestorTypeName = requestorTypeName;
    }

    public boolean hasRequestBasedCriteria() {

        if (_requestId <= 0
                && null == _requestStatus
                && null == _requestReason
                && null == _completedDateFrom
                && null == _completedDateTo
                && null == _receiptDateFrom
                && null == _receiptDateTo) {

            return false;
        }
        return true;
    }

    public boolean hasPatientsBasedCriteria() {

        if (null == _patientLastName
                && null == _patientFirstName
                && null == _mrn
                && null == _patientSsn
                && null == _patientEpn
                && _patientId <= 0) {

            return false;
        }
        return true;
    }

    public boolean hasRequestorBasedcriteria() {

        if (null == _requestorName
                && _requestorId <= 0
                && _requestorType == 0) {
            return false;
        }
        return true;
    }

    public boolean hasInvoiceBasedCriteria() {

        if (_invoiceNumber <= 0
                && _balanceDueOperator == null) {
            return false;
        }
        return true;
    }

    public boolean hasEncounterCriteria() {

        if (null == _encounter) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return constructAuditSearchComment();
    }

    //This method will return user entered search criterias in the format of string.
    public String constructAuditSearchComment() {

        SimpleDateFormat sdf   = new SimpleDateFormat(ROIConstants.ROI_DATE_FORMAT);
        StringBuffer comment = new StringBuffer();

        comment.append(constructAuditString("PatientLastName", getPatientLastName()));
        comment.append(constructAuditString("PatientFirstName", getPatientFirstName()));

        if (null != getPatientDob()) {
            comment.append(constructAuditString("PatientDateOfBirth",
                                                sdf.format(getPatientDob())));
        }

        comment.append(constructAuditString("PatientSSN", getPatientSsn()));
        comment.append(constructAuditString("MRN", getMrn()));
        comment.append(constructAuditString("Encounter", getEncounter()));
        comment.append(constructAuditString("Facility", (null == getFacility()) ? "All"
                                                                        : getFacility()));
        comment.append(constructAuditString("Patient EPN", getPatientEpn()));
        comment.append(constructAuditString("Requestor Name", getRequestorName()));
        comment.append(constructAuditString("RequestorType",
                                                (0 == getRequestorType()) ? "All"
                                                        : getRequestorTypeName()));

        comment.append(constructAuditString("ReceiptDate",
                                (null == getReceiptDateFrom()) ? "All" : ""));

        comment.append(constructAuditString("ReceiptDateFrom", (null == getReceiptDateFrom()) ? ""
                : ROIConstants.DATE_FORMATTER.format(getReceiptDateFrom())));

        comment.append(constructAuditString("ReceiptDateTo", (null == getReceiptDateTo()) ? ""
                                      : ROIConstants.DATE_FORMATTER.format(getReceiptDateTo())));

        comment.append(constructAuditString("CompletedDate",
                            (null == getCompletedDateFrom()) ? "All" : ""));

        comment.append(constructAuditString("CompletedDateFrom",
                            (null == getCompletedDateFrom()) ? ""
                                    : ROIConstants.DATE_FORMATTER.format(getCompletedDateFrom())));

        comment.append(constructAuditString("CompletedDateTo",
                            (null == getCompletedDateTo()) ? ""
                                     : ROIConstants.DATE_FORMATTER.format(getCompletedDateTo())));

        String operator = null;
        if ("<".equals(StringUtilities.safeTrim(getBalanceDueOperator()))) {
            operator = "AtMost $";
        } else if (">".equals(StringUtilities.safeTrim(getBalanceDueOperator()))) {
            operator = "AtLeast $";
        } else if (">".equals(StringUtilities.safeTrim(getBalanceDueOperator()))) {
            operator = "Equals $";
        }

        comment.append(constructAuditString("BalanceDue",
                                (null == operator) ? ""
                                            : (operator + getBalanceDue())));

       comment.append(constructAuditString("RequestStatus",
                                   (null == getRequestStatus())
                                                       ? "All"
                                                       : getRequestStatus()));

       comment.append(constructAuditString("RequestReason",
                                   (null == getRequestReason())
                                                       ? "All"
                                                       : getRequestReason()));

       comment.append(constructAuditString("InvoiceNumber",
                                     (getInvoiceNumber() > 0) ? String.valueOf(getInvoiceNumber())
                                                              : null));
       comment.append(constructAuditString("RequestId",
                                       (getRequestId() > 0) ? String.valueOf(getRequestId())
                                                            : null));

       comment.replace(comment.length() - 2, comment.length() - 1, ROIConstants.SEPERATOR);

        return comment.toString();
    }

    /**
     * @param value
     * @param searchCriterias
     */
    private String constructAuditString(String label, String value) {

        if (StringUtilities.isEmpty(value)) {
            return "";
        }

        String comment = new StringBuffer()
                                .append(label)
                                .append(": ")
                                .append(value)
                                .append(ROIConstants.CSV_DELIMITER)
                                .append(" ")
                                .toString();

        return comment;
    }

}
