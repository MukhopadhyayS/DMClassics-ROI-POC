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

package com.mckesson.eig.roi.billing.letter.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author OFS
 * @date Sep 28, 2009
 * @since HPF 13.1 [ROI]; Feb 19, 2009
 */
public class RequestItem {



    private String _name;
    private String _epn;
    private String _mrn;
    private String _ssn;
    private Date _dob;
    private String _facility;
    private String _encounterFacility;
    private List<RequestItem> _requestItems;

    public String getName() {
        return _name;
    }
    public void setName(String name) {
        _name = name;
    }

    public String getEpn() {
        return _epn;
    }
    public void setEpn(String epn) {
        _epn = epn;
    }

    public String getMrn() {
        return _mrn;
    }
    public void setMrn(String mrn) {
        _mrn = mrn;
    }

    public String getSsn() {
        return _ssn;
    }
    public void setSsn(String ssn) {
        _ssn = ssn;
    }

    public String getFacility() {
        return _facility;
    }
    public void setFacility(String facility) {
        _facility = facility;
    }

    public List<RequestItem> getRequestItems() {
        return _requestItems;
    }
    public void setRequestItems(List<RequestItem> requestItems) {
        _requestItems = requestItems;
    }

    public String getEncounterFacility() {
        return _encounterFacility;
    }
    public void setEncounterFacility(String facility) {
        _encounterFacility = facility;
    }

    public Date getDob() {
        return _dob;
    }
    public void setDob(Date dob) {
        _dob = dob;
    }

}
