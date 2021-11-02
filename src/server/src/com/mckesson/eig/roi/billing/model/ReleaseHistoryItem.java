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
import java.util.Date;

import com.mckesson.eig.roi.utils.SecureStringAccessor;
import com.mckesson.eig.utility.util.StringUtilities;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ReleaseHistoryItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReleaseHistoryItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="datetime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="patientName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="enctr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isSelfPay" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="documentVersionSubtitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pages" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="requestPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="queuePassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="shippingMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="trackingNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="address1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="zipcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="outputMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReleaseHistoryItem", propOrder = {
    "datetime",
    "patientName",
    "patientSeq",
    "enctr",
    "selfPay",
    "documentVersionSubtitle",
    "pages",
    "requestPassword",
    "queuePassword",
    "shippingMethod",
    "trackingNumber",
    "userName",
    "userId",
    "address1",
    "address2",
    "address3",
    "city",
    "state",
    "zipcode",
    "outputMethod"
})
@SuppressWarnings("serial")
public class ReleaseHistoryItem
implements Serializable {

    public ReleaseHistoryItem() {};
    
    @XmlElement(required = true)
    private Date datetime;
    
    @XmlElement(required = true)
    private String patientName;
    
    private Long patientSeq;
    
    @XmlElement(required = true)
    private String enctr;
    
    @XmlElement(name="isSelfPay")
    private boolean selfPay;
    
    @XmlElement(required = true)
    private String documentVersionSubtitle;
    
    @XmlElement(required = true)
    private int pages;
    
    @XmlElement(required = true)
    private SecureStringAccessor requestPassword;
    
    @XmlElement(required = true)
    private SecureStringAccessor queuePassword;
    
    @XmlElement(required = true)
    private String shippingMethod;
    
    @XmlElement(required = true)
    private String trackingNumber;
    
    @XmlElement(required = true)
    private String userName;
    
    private Long userId;
    
    @XmlElement(required = true)
    private String address1; 
    
    @XmlElement(required = true)
    private String address2;
    
    @XmlElement(required = true)
    private String address3;
    
    @XmlElement(required = true)
    private String city;
    
    @XmlElement(required = true)
    private String state;
    
    @XmlElement(required = true)
    private String zipcode;
    
    @XmlElement(required = true)
    private String outputMethod;
    
    
    
    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getPatientName() {
        return patientName;
    }
    
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    
    public String getEnctr() {
        return enctr;
    }
    
    public void setEnctr(String enctr) {
        this.enctr = enctr;
    }
    
    public String getDocumentVersionSubtitle() {
        return documentVersionSubtitle;
    }
    
    public void setDocumentVersionSubtitle(String documentVersionSubtitle) {
        this.documentVersionSubtitle = documentVersionSubtitle;
    }
    
    public int getPages() {
        return pages;
    }
    
    public void setPages(int pages) {
        this.pages = pages;
    }
    
    public String getRequestPassword() {
        if (requestPassword == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        requestPassword.DoHylandAccess((chars, tempStr) -> {
            builder.append(chars);
        });
        return builder.toString();
    }
    
    public void setRequestPassword(String requestPassword) {
        requestPassword = StringUtilities.safe(requestPassword);
        this.requestPassword = new SecureStringAccessor(requestPassword.toCharArray());
    }
    
    public String getQueuePassword() {
        if (queuePassword == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        queuePassword.DoHylandAccess((chars, tempStr) -> {
            builder.append(chars);
        });
        return builder.toString();
    }
    
    public void setQueuePassword(String queuePassword) {
        queuePassword = StringUtilities.safe(queuePassword);
        this.queuePassword = new SecureStringAccessor(queuePassword.toCharArray());
    }
  
    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }
    
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Long getPatientSeq() {
        return patientSeq;
    }

    public void setPatientSeq(Long patientSeq) {
        this.patientSeq = patientSeq;
    }

    public boolean isSelfPay() {
        return selfPay;
    }

    public void setSelfPay(boolean selfPay) {
        this.selfPay = selfPay;
    }

    public String getOutputMethod() {
        return outputMethod;
    }

    public void setOutputMethod(String outputMethod) {
        this.outputMethod = outputMethod;
    }
   
}
