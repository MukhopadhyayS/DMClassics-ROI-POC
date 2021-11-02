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

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.model.BaseModel;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestorPaymentList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorPaymentList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestorId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestorName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paymentId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="paymentAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="unAppliedAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="paymentMode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paymentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="paymentList" type="{urn:eig.mckesson.com}RequestorPayment" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorPaymentList", propOrder = {
    "_requestorId",
    "_requestorName",
    "_requestorType",
    "_paymentId",
    "_paymentAmount",
    "_unAppliedAmount",
    "_paymentMode",
    "_description",
    "_paymentDate",
    "_paymentList"
})
public class RequestorPaymentList extends BaseModel {
    
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name="requestorId")
    private long _requestorId;
    
    @XmlElement(name="requestorName", required = true)
    private String _requestorName;
    
    @XmlElement(name="requestorType", required = true)
    private String _requestorType;
    
    @XmlElement(name="paymentId")
    private long _paymentId;
    
    @XmlElement(name="paymentAmount")
    private double _paymentAmount;
    
    @XmlElement(name="unAppliedAmount")
    private double _unAppliedAmount;
    
    @XmlElement(name="paymentMode", required = true)
    private String _paymentMode;
    
    @XmlElement(name="description", required = true)
    private String _description;
    
    @XmlElement(name="paymentDate", required = true)
    private Date _paymentDate;
    
    @XmlElement(name="paymentList")
    private List<RequestorPayment> _paymentList;
       
    public long getRequestorId() { return _requestorId; }
    public void setRequestorId(long requestorId) { _requestorId = requestorId; }
    
    public long getPaymentId() { return _paymentId; }
    public void setPaymentId(long paymentId) { _paymentId = paymentId; }
    
    public double getPaymentAmount() { return _paymentAmount; }
    public void setPaymentAmount(double paymentAmount) { _paymentAmount = paymentAmount; }
   
    public double getUnAppliedAmount() { return _unAppliedAmount; }
    public void setUnAppliedAmount(double unAppliedAmount) { _unAppliedAmount = unAppliedAmount; }
    
    public String getPaymentMode() { return _paymentMode; }
    public void setPaymentMode(String paymentMode) { _paymentMode = paymentMode; }
    
    public String getDescription() { return _description; }
    public void setDescription(String description) { _description = description; }
    
    public Date getPaymentDate() { return _paymentDate; }
    public void setPaymentDate(Date paymentDate) { _paymentDate = paymentDate; }
    
    public List<RequestorPayment> getPaymentList() { return _paymentList; }
    public void setPaymentList(List<RequestorPayment> paymentList) { _paymentList = paymentList; }
    
    public String getRequestorName() { return _requestorName; }
    public void setRequestorName(String requestorName) { _requestorName = requestorName; }
    
    public String getRequestorType() { return _requestorType; }
    public void setRequestorType(String requestorType) { _requestorType = requestorType; }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    /**
     * This method create audit comment for Requestor Post Payment
     * @return audit comment for retrieve event
     */
    public String constructPostPaymentAuditComment(String userName) {
        return new StringBuffer().append("A  ")
                .append(_paymentMode)
                .append(" payment of ")
                .append(ROIConstants.CURRENCY_FORMAT.format(_paymentAmount))
                .append(" was posted for  ")
                .append(_requestorName)
                .append(" by ")
                .append(userName.trim())
                .append(".")
                .toString();
    }
    
    /**
     * This method create audit comment for Requestor Delete Payment
     * @return audit comment for retrieve event
     */
    public String constructDeletePaymentAuditComment() {
        return new StringBuffer().append("A  ")
                .append(ROIConstants.CURRENCY_FORMAT.format(_unAppliedAmount))
                .append(" payment was deleted from ")
                .append(_requestorName)
                .append("'s account.")
                .toString();
    }

}
