/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright � 2011 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/
package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;
import java.util.Date;

import com.mckesson.eig.roi.utils.SecureStringAccessor;
import com.mckesson.eig.utility.util.StringUtilities;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestorLetterHistory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorLetterHistory">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestorLetterId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createdDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="resendDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="outputMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="createdBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="templateUsed" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="queuePassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorLetterHistory", propOrder = {
    "_requestorLetterId",
    "_createdDate",
    "_resendDate",
    "_outputMethod",
    "_createdBy",
    "_requestTemplateId",
    "_templateUsed",
    "_requestPassword",
    "_queuePassword"
})
public class RequestorLetterHistory 
implements Serializable {
    
    private static final long serialVersionUID = 9086690815566101758L;
    
    @XmlElement(name="requestorLetterId")
    private long _requestorLetterId;
    
    @XmlElement(name="createdDate", required= true)
    private Date _createdDate;
    
    @XmlElement(name="resendDate", required= true)
    private String _resendDate;
    
    @XmlElement(name="outputMethod", required= true)
    private String _outputMethod;
    
    @XmlElement(name="createdBy", required= true)
    private String _createdBy;
    
    @XmlElement(name="requestTemplateId")
    private long _requestTemplateId;
    
    @XmlElement(name="templateUsed", required= true)
    private String _templateUsed;
    
    @XmlElement(name="requestPassword", required= true)
    private SecureStringAccessor _requestPassword;
    
    @XmlElement(name="queuePassword", required= true)
    private SecureStringAccessor _queuePassword;
    
    public long getRequestorLetterId() { return _requestorLetterId; }
    public void setRequestorLetterId(long requestorLetterId) {
        _requestorLetterId = requestorLetterId;
    }
    
    public Date getCreatedDate() { return _createdDate; }
    public void setCreatedDate(Date createdDate) { _createdDate = createdDate; }
    
    public String getResendDate() { return _resendDate; }
    public void setResendDate(String resendDate) { _resendDate = resendDate; }
    
    public String getOutputMethod() { return _outputMethod; }
    public void setOutputMethod(String outputMethod) { _outputMethod = outputMethod; }
    
    public String getCreatedBy() { return _createdBy; }
    public void setCreatedBy(String createdBy) { _createdBy = createdBy; }
    
    public long getRequestTemplateId() { return _requestTemplateId; }
    public void setRequestTemplateId(long requestTemplateId) {
        _requestTemplateId = requestTemplateId;
    }
    
    public String getTemplateUsed() { return _templateUsed; }
    public void setTemplateUsed(String templateUsed) { _templateUsed = templateUsed; }
    
    public String getRequestPassword() {
        if (_requestPassword == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        _requestPassword.DoHylandAccess((chars, tempStr) -> {
            builder.append(chars);
        });
        return builder.toString();
    }
    public void setRequestPassword(String requestPassword) {
        requestPassword = StringUtilities.safe(requestPassword);
        _requestPassword =  new SecureStringAccessor(requestPassword.toCharArray());
    }
    
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

}
