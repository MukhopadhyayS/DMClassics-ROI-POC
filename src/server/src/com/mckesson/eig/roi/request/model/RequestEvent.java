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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.base.api.ROIConstants;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestEvent", propOrder = {
    "_requestId",
    "_name",
    "_type",
    "_description",
    "_originator",
    "_modifiedDate"
})
public class RequestEvent
implements Serializable {

    private static final long serialVersionUID = -1144313588595302858L;

    @XmlType(name = "TYPE")
    @XmlEnum
    public static enum TYPE {

        COMMENT_ADDED       ("Comment Added"),
        REASON_ADDED        ("Status Reason Added"),
        CHANGE_OF_STATUS    ("Change of Status"),
        DOCUMENTS_RELEASED  ("Documents Released"),
        DOCUMENTS_RE_RELEASED ("Documents Re-released"),
        DOCUMENTS_RESEND    ("Documents Resend"),
        INVOICE_SEND        ("Invoice Sent"),
        PRE_BILL_SENT       ("Pre-Bill Sent"),
        LETTER_SENT         ("Letter Sent"),
        PAYMENT_POSTED      ("Payment Posted"),
        ADJUSTMENT_POSTED   ("Adjustment Posted"),
        AUTO_ADJUSTMENT_POSTED   ("Auto Adjustment Posted"),
        WRITE_OFF           ("Write Off"),
        SALESTAX_CHANGES    ("Sales Tax Changes"),
        CHANGE_OF_BILLING_LOCATION ("Change of Billing Location"),
        OVERWRITE_INVOICE_DUE_DAYS ("Overwrite Invoice Due Days"),
        OVERDUE_INVOICE_SENT    ("Overdue Invoice Sent"),
        INVOICE_RESENT          ("Invoice Resent"),
        NA                  ("NA"),
        PAYMENT_APPLIED     ("Payment Applied"),
        PAYMENT_MODIFIED    ("Payment Modified"),
        ADJUSTMENT_APPLIED  ("Adjustment Applied"),
        ADJUSTMENT_MODIFIED ("Adjustment Modified"),
        INVOICE_CLOSED      ("Invoice Closed");

        private String _type;

        private TYPE(String type) {
            _type = type;
        }
        @Override
        public String toString() { return _type; }
   }
    
    @XmlElement(name="requestId")
    private long    _requestId;
    
    @XmlElement(name="name")
    private String  _name;
    
    @XmlElement(name="type")
    private String  _type;
    
    @XmlElement(name="description")
    private String  _description;
    
    @XmlElement(name="originator")
    private String _originator;
    
    @XmlElement(name="modifiedDate")
    private Date    _modifiedDate;
    
    @XmlTransient
    private long    _id;
    
    @XmlTransient
    private int     _createdBy;
    
    @XmlTransient
    private int     _modifiedBy;
    
    @XmlTransient
    private int     _recordVersion;
    
    

    public RequestEvent() { }

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getName() { return _name; }
    public void setName(String name) { _name = name; }

    public long getRequestId() { return _requestId; }
    public void setRequestId(long requestId) { _requestId = requestId; }

    public String getDescription() { return _description; }
    public void setDescription(String description) { _description = description; }

    public int getCreatedBy() { return _createdBy; }
    public void setCreatedBy(int createdBy) { _createdBy = createdBy; }

    public int getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(int modifiedBy) { _modifiedBy = modifiedBy; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date modifiedBy) { _modifiedDate = modifiedBy; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int recordVersion) { _recordVersion = recordVersion; }

    public String getType() { return _type; }
    public void setType(String type) {

        if (type == null) {
            _name = TYPE.NA.toString();
        } else {
            _name =  Enum.valueOf(TYPE.class, type).toString();
        }
    }

    public String getOriginator() { return _originator; }
    public void setOriginator(String originator) { _originator = originator; }

    @Override
    public String toString() {

        return new StringBuffer("RequestEventId: ")
                   .append(_id)
                   .append(", Request Id: ")
                   .append(_requestId)
                   .append(", Name: ")
                   .append(_name)
                   .append(", Description: ")
                   .append(_description)

        .toString();
    }

    /**
     * This method is to set the values for name and description field while the status is changed
     * @param statusName old status name
     * @param newName new status name
     */
    public void setStatusChange(String statusName, String newName) {

        _name = TYPE.CHANGE_OF_STATUS.toString();
        _description = ROIConstants.STATUS_DESC + newName + " from " + statusName;
    }

    /**
     * This method is to set the values for name and description field
     * while the status reason is changed
     * @param eventType old status name
     * @param statusReasons name of the status reasons
     */
    public void setStatusReasonsChange(String eventType, String statusReasons) {

        _name = TYPE.REASON_ADDED.toString();
        if ((statusReasons != null) && (statusReasons.length() != 0)) {
            _description = ROIConstants.STATUS_REASON_DESC + eventType + " , " + statusReasons;
        } else {
            _description = ROIConstants.STATUS_REASON_DESC + eventType;
        }
    }

    /**
     * This method will retrieve all the Request Event Type names
     * @return List of Request event type name
     */
    public static List<String> getEventTypes() {

        List<String> types = new ArrayList<String>();

        for (TYPE eventType : TYPE.values()) {
            types.add(eventType.toString());
        }

        types.remove(TYPE.NA.toString());

        return types;
    }

    public void setAdjustmentAndPaymentType(String type) {

        if (type.equals(ROIConstants.PAYMENT_TYPE)) {
            _name = TYPE.PAYMENT_POSTED.toString();
        } else if (type.equals(ROIConstants.AUTO_ADJ_TYPE)) {
            _name = TYPE.AUTO_ADJUSTMENT_POSTED.toString();
        } else {
            _name = TYPE.ADJUSTMENT_POSTED.toString();
        }
    }

    /**
     * This method is to set the values for name and description field while the letter is sent
     * @param ltName letter template name
     * @param noteNames note names
     */
    public void setLetterSentDetails(String ltName, String noteNames) {

        _name = TYPE.LETTER_SENT.toString();

        if ((noteNames != null) && (noteNames.length() != 0)) {
            _description = ltName + ": " + noteNames;
        } else {
            _description = ltName;
        }
    }

    /**
     * This method will set the event name from Enum constant TYPE
     */
    public void setEventName() {

        if (_name == null) {
            _name = TYPE.NA.toString();
        } else {
            _name =  Enum.valueOf(TYPE.class, _name).toString();
        }
    }
}
