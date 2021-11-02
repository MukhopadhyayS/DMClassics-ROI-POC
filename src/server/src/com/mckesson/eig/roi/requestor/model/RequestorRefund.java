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


package com.mckesson.eig.roi.requestor.model;

import java.util.Date;
import java.util.List;

import com.mckesson.dm.core.common.util.StringUtilities;
import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.utils.SecureStringAccessor;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestorRefund complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorRefund">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestorId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestorName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="refundDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="refundAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="templateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="templateName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="outputMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="queuePassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="statementCriteria" type="{urn:eig.mckesson.com}RequestorStatementCriteria"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorRefund", propOrder = {
    "_requestorId",
    "_requestorName",
    "_requestorType",
    "_note",
    "_refundDate",
    "_refundAmount",
    "_templateId",
    "_templateName",
    "_outputMethod",
    "_queuePassword",
    "_notes",
    "_statementCriteria"
})
public class RequestorRefund
extends BaseModel {

    private static final long serialVersionUID = 1L;
    
    @XmlElement(name="requestorId")
    private long _requestorId;
    
    @XmlElement(name="note", required = true)
    private String _note;
    
    @XmlElement(name="requestorName", required = true)
    private String _requestorName;
    
    @XmlElement(name="requestorType", required = true)
    private String _requestorType;
    
    @XmlElement(name="refundDate", required = true)
    private Date _refundDate;
    
    @XmlElement(name="refundAmount")
    private double _refundAmount;

    
    @XmlElement(name="statementCriteria", required = true)
    private RequestorStatementCriteria _statementCriteria;

    @XmlElement(name="templateId")
    private long _templateId;
    
    @XmlElement(name="templateName", required = true)
    private String _templateName;
    
    @XmlElement(name="outputMethod", required = true)
    private String _outputMethod;
    
    @XmlElement(name="queuePassword", required = true)
    private SecureStringAccessor _queuePassword;
    
    @XmlElement(name="notes")
    private List<String> _notes;

    public long getRequestorId() { return _requestorId; }
    public void setRequestorId(long requestorId) { _requestorId = requestorId; }

    public String getNote() { return _note; }
    public void setNote(String note) { _note = note; }

    public String getRequestorName() { return _requestorName; }
    public void setRequestorName(String requestorName) { _requestorName = requestorName; }

    public String getRequestorType() { return _requestorType; }
    public void setRequestorType(String requestorType) { _requestorType = requestorType; }

    public Date getRefundDate() { return _refundDate; }
    public void setRefundDate(Date refundDate) { _refundDate = refundDate; }

    public double getRefundAmount() { return _refundAmount; }
    public void setRefundAmount(double refundAmount) { _refundAmount = refundAmount; }

    public long getTemplateId() { return _templateId; }
    public void setTemplateId(long templateId) { _templateId = templateId; }

    public String getTemplateName() { return _templateName; }
    public void setTemplateName(String templateName) { _templateName = templateName; }

    public String getOutputMethod() { return _outputMethod; }
    public void setOutputMethod(String outputMethod) { _outputMethod = outputMethod; }

    public String getQueuePassword() { 
        if (_queuePassword == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        _queuePassword.DoHylandAccess((chars, tempStr) -> {
            builder.append(chars);
        });
        return builder.toString();
    }
    public void setQueuePassword(String queuePassword) {  
        queuePassword = StringUtilities.safe(queuePassword);
        _queuePassword = new SecureStringAccessor(queuePassword.toCharArray());
    }


    public RequestorStatementCriteria getStatementCriteria() { return _statementCriteria; }
    public void setStatementCriteria(RequestorStatementCriteria statementCriteria) {
        _statementCriteria = statementCriteria;
    }

    public List<String> getNotes() { return _notes; }
    public void setNotes(List<String> notes) { _notes = notes; }

    /**
     * This method create audit commend for create Requestor Refund
     * @return audit comment for retrieve event
     */
    public String constructRefundAuditComment() {

        return new StringBuffer().append("Refund has been applied to ")
                .append(_requestorName)
                .append(" belonging to  ")
                .append(_requestorType + ".")
                .append("Refunded amount = ")
                .append(_refundAmount)
                .append(".")
                .toString();

    }

}
