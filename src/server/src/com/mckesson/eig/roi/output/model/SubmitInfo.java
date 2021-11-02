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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SubmitInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SubmitInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="application" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="submitDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="submitMachine" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="submitterData" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubmitInfo", propOrder = {
    "_application",
    "_submitDate",
    "_submitMachine",
    "_submitterData",
    "_user"
})
public class SubmitInfo {
    
    @XmlElement(name="application", required = true)
    private String _application;
    
    @XmlElement(name="submitDate", required = true)
    private Date _submitDate;
    
    @XmlElement(name="submitMachine", required = true)
    private String _submitMachine;
    
    @XmlElement(name="submitterData", required = true)
    private String _submitterData;
    
    @XmlElement(name="user", required = true)
    private String _user;
    
    public String getApplication() {
        return _application;
    }
    
    public void setApplication(String application) {
        _application = application;
    }
    
    public Date getSubmitDate() {
        return _submitDate;
    }
    
    public void setSubmitDate(Date submitDate) {
        _submitDate = submitDate;
    }
    
    public String getSubmitMachine() {
        return _submitMachine;
    }
    
    public void setSubmitMachine(String submitMachine) {
        _submitMachine = submitMachine;
    }
    
    public String getSubmitterData() {
        return _submitterData;
    }
    
    public void setSubmitterData(String submitterData) {
        _submitterData = submitterData;
    }
    
    public String getUser() {
        return _user;
    }
    
    public void setUser(String user) {
        _user = user;
    }
    
}
