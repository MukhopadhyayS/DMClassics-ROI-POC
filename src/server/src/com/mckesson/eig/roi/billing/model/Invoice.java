/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright � 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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

import com.mckesson.eig.roi.utils.SecureStringAccessor;
import com.mckesson.eig.utility.util.StringUtilities;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Invoice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Invoice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="creatorName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="createdDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="queuePassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="resendDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="outputMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceDueDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="baseCharge" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalAdjustments" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalPayments" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="currentBalance" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Invoice", propOrder = {
    "_id",
    "_type",
    "_creatorName",
    "_createdDate",
    "_queuePassword",
    "_resendDate",
    "_outputMethod",
    "_invoiceDueDate",
    "_baseCharge",
    "_totalAdjustments",
    "_totalPayments",
    "_currentBalance"
})
public class Invoice
implements Serializable {

    private static final long serialVersionUID = 8865820133570176997L;
    
    @XmlElement(name="id")
    private long _id;
    
    @XmlElement(name="type", required = true)
    private String _type;
    
    @XmlElement(name="creatorName", required = true)
    private String _creatorName;
    
    @XmlElement(name="createdDate", required = true)
    private String _createdDate;
    
    @XmlElement(name="queuePassword", required = true)
    private SecureStringAccessor _queuePassword;
    
    @XmlElement(name="resendDate", required = true)
    private String _resendDate;
    
    @XmlElement(name="outputMethod", required = true)
    private String _outputMethod;
    
    @XmlElement(name="invoiceDueDate", required = true)
    private String _invoiceDueDate;
    
    @XmlElement(name="baseCharge")
    private double _baseCharge;
    
    @XmlElement(name="totalAdjustments")
    private double _totalAdjustments;
    
    @XmlElement(name="totalPayments")
    private double _totalPayments;
    
    @XmlElement(name="currentBalance")
    private double _currentBalance;
    
    

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getType() { return _type; }
    public void setType(String type) { _type = type; }

    public String getCreatorName() { return _creatorName; }
    public void setCreatorName(String creatorName) { _creatorName = creatorName; }

    public String getCreatedDate() { return _createdDate; }
    public void setCreatedDate(String createdDate) { _createdDate = createdDate; }
    
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
    
    public String getResendDate() { return _resendDate; }
    public void setResendDate(String resendDate) { _resendDate = resendDate; }

    public String getOutputMethod() { return _outputMethod; }
    public void setOutputMethod(String outputMethod) { _outputMethod = outputMethod; }
    
    public String getInvoiceDueDate() { return _invoiceDueDate; }
    public void setInvoiceDueDate(String invoiceDueDate) { _invoiceDueDate = invoiceDueDate; }
    
    public double getBaseCharge() { return _baseCharge; }
    public void setBaseCharge(double baseCharge) { _baseCharge = baseCharge; }
    
    public double getTotalAdjustments() { return _totalAdjustments; }
    public void setTotalAdjustments(double totalAdjustments) { 
        _totalAdjustments = totalAdjustments; }
    
    public double getTotalPayments() { return _totalPayments; }
    public void setTotalPayments(double totalPayments) { _totalPayments = totalPayments; }
    
    public double getCurrentBalance() { return _currentBalance; }
    public void setCurrentBalance(double currentBalance) { _currentBalance = currentBalance; }
    
}
