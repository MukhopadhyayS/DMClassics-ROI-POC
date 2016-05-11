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

/**
 * This class contains the submit information
 * @author Karthik Easwaran(OFS)
 * @author Shahm Nattarshah.
 *
 */
public class SubmitInfo {
    
    /** This holds the application*/
    private String _application;
    /** This holds the submit date*/
    private Date _submitDate;
    /** This holds the submit machine*/
    private String _submitMachine;
    /** This holds the submitter data*/
    private String _submitterData;
    /** This holds the user*/
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
