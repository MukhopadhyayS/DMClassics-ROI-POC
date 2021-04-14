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

package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;
import java.util.Date;

import com.mckesson.eig.roi.utils.SecureStringAccessor;

@SuppressWarnings("serial")
public class ReleaseHistoryItem
implements Serializable {

    public ReleaseHistoryItem() {};
    
    private Date datetime;
    private String patientName;
    private Long patientSeq;
    private String enctr;
    private boolean selfPay;
    private String documentVersionSubtitle;
    private int pages;
    private SecureStringAccessor requestPassword;
    private SecureStringAccessor queuePassword;
    private String shippingMethod;
    private String trackingNumber;
    private String userName;
    private Long userId;
    private String address1; 
    private String address2;
    private String address3;
    private String city;
    private String state;
    private String zipcode;
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
        StringBuilder builder = new StringBuilder();
        requestPassword.DoHylandAccess((chars, tempStr) -> {
            builder.append(chars);
        });
        return builder.toString();
    }
    
    public void setRequestPassword(String requestPassword) {
        this.requestPassword = new SecureStringAccessor(requestPassword.toCharArray());
    }
    
    public String getQueuePassword() {
        StringBuilder builder = new StringBuilder();
        queuePassword.DoHylandAccess((chars, tempStr) -> {
            builder.append(chars);
        });
        return builder.toString();
    }
    
    public void setQueuePassword(String queuePassword) {
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
