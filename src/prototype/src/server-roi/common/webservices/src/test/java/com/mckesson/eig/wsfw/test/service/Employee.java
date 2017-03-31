/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Information Solutions and is protected under United States and 
 * international copyright and other intellectual property laws. Use, 
 * disclosure, reproduction, modification, distribution, or storage 
 * in a retrieval system in any form or by any means is prohibited without 
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.wsfw.test.service;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author sahuly
 * @date   Dec 24, 2008
 * @since  HECM 1.0; Dec 24, 2008
 */
@XmlRootElement(name = "employee")
public class Employee {
    
    private long _id;
    private String _firstName;
    private String _lastName;
    private String _designation;
    private String _employeeNumber;

    public long getId() {
        return _id;
    }
    public void setId(long id) {
        this._id = id;
    }
    public String getDesignation() {
        return _designation;
    }
    public void setDesignation(String designation) {
        _designation = designation;
    }
    public String getEmployeeNumber() {
        return _employeeNumber;
    }
    public void setEmployeeNumber(String number) {
        _employeeNumber = number;
    }
    public String getFirstName() {
        return _firstName;
    }
    public void setFirstName(String name) {
        _firstName = name;
    }
    public String getLastName() {
        return _lastName;
    }
    public void setLastName(String name) {
        _lastName = name;
    }
}
