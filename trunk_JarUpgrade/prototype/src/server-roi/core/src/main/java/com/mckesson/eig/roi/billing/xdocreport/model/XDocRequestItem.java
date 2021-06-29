/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

 * Copyright © 2014 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
 * Use of this software and related documentation is governed by a license agreement. 
 * This material contains confidential, proprietary and trade secret information of 
 * McKesson Information Solutions and is protected under United States
 * and international copyright and other intellectual property laws. 
 * Use, disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without the 
 * prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
 */

package com.mckesson.eig.roi.billing.xdocreport.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mckesson.eig.roi.billing.letter.model.LetterData;
import com.mckesson.eig.roi.billing.letter.model.RequestItem;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author OFS
 * @date Mar 17, 2014
 * @since HPF 16.2 [ROI]; Mar 17, 2014
 */
public class XDocRequestItem {

    private static final Log LOG = LogFactory.getLogger(XDocRequestItem.class);

    public static final String DATEFORMAT = "yyyy-MM-dd";
    public static final String DATEFORMAT1 = "EEEE, MMMMM dd, yyyy";
    public static final String DATEFORMAT2 = "MMMMM dd, yyyy";
    public static final String DATEFORMAT3 = "MM/dd/yyyy";
    public static final SimpleDateFormat DATEFORMATTER = new SimpleDateFormat(DATEFORMAT);

    private String _name = StringUtilities.EMPTYSTRING;
    private Date _dob ;
    private String _epn = StringUtilities.EMPTYSTRING;
    private String _mrn = StringUtilities.EMPTYSTRING;
    private String _ssn = StringUtilities.EMPTYSTRING;
    private String _facility = StringUtilities.EMPTYSTRING;
    private String _encounterFacility = StringUtilities.EMPTYSTRING;

    public XDocRequestItem(RequestItem requestItem) {

        if (null == requestItem) {
            return;
        }

        setName(StringUtilities.safe(requestItem.getName()));
        setDob(requestItem.getDob());
        setEpn(StringUtilities.safe(requestItem.getEpn()));
        setMrn(StringUtilities.safe(requestItem.getMrn()));
        setSsn(StringUtilities.safe(requestItem.getSsn()));
        setFacility(StringUtilities.safe(requestItem.getFacility()));
        setEncounterFacility(StringUtilities.safe(requestItem
                .getEncounterFacility()));
    }

    public String getName() {
        return _name;
    }
    public void setName(String name) {
        _name = name;
    }

    public Date getDob() {
        return _dob;
    }
    public void setDob(Date dob) {
        _dob = dob;
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

    public String getEncounterFacility() {
        return _encounterFacility;
    }
    public void setEncounterFacility(String facility) {
        _encounterFacility = facility;
    }

    public static String formatDate(Date date, String format) {
        String formattedDate = "";
        if ((date != null) && (format != null)) {
            try {
                formattedDate = new SimpleDateFormat(format).format(date);
            } catch (Throwable e) {
                LOG.error("Error occured while formating date of birth");
            }
        }
        return formattedDate;
    }

    public String getDobDateFmt1() {
        if (_dob != null) {
            return formatDate(_dob, DATEFORMAT1);
        }
        return "";
    }

    public String getDobDateFmt2() {
        if (_dob != null) {
            return formatDate(_dob, DATEFORMAT2);
        }
        return "";
    }

    public String getDobDateFmt3() {
        if (_dob != null) {
            return formatDate(_dob, DATEFORMAT3);
        }
        return "";
    }

}
