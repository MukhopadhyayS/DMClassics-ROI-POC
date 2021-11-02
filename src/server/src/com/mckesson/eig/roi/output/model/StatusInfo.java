/*
 * Copyright 2014 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.roi.output.model;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for StatusInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StatusInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="statusCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="detail" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="statusDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="statusSource" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="statusData" type="{urn:eig.mckesson.com}MapModel" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatusInfo", propOrder = {
    "_statusCode",
    "_status",
    "_detail",
    "_statusDate",
    "_statusSource",
    "_statusData"
})
public class StatusInfo {
    
    @XmlElement(name="statusCode")
    private int _statusCode;
    
    @XmlElement(name="status", required = true)
    private String _status;
    
    @XmlElement(name="detail", required = true)
    private String _detail;
    
    @XmlElement(name="statusDate", required = true)
    private Date _statusDate;
    
    @XmlElement(name="statusSource", required = true)
    private String _statusSource;
    
    @XmlElement(name="statusData")
    private List<MapModel> _statusData;

    public int getStatusCode() {
        return _statusCode;
    }

    public void setStatusCode(int statusCode) {
        _statusCode = statusCode;
    }

    public String getStatus() {
        return _status;
    }

    public void setStatus(String status) {
        _status = status;
    }

    public String getDetail() {
        return _detail;
    }

    public void setDetail(String detail) {
        _detail = detail;
    }

    public Date getStatusDate() {
        return _statusDate;
    }

    public void setStatusDate(Date statusDate) {
        _statusDate = statusDate;
    }

    public String getStatusSource() {
        return _statusSource;
    }

    public void setStatusSource(String statusSource) {
        _statusSource = statusSource;
    }

    public List<MapModel> getStatusData() {
        return _statusData;
    }

    public void setStatusData(List<MapModel> statusData) {
        _statusData = statusData;
    }
    
}
