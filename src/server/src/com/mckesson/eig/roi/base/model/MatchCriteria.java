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

package com.mckesson.eig.roi.base.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Nithyanandhan
 * @date   Jul 16, 2008
 * @since  HPF 13.1 [ROI]; Jul 16, 2008
 */
public class MatchCriteria
implements Serializable {

    private String _lastName;
    private String _firstName;
    private String _epn;
    private String _ssn;
    private String _mrn;
    private String _facility;
    private Date   _dob;

    public String getLastName() { return _lastName; }
    public void setLastName(String name) { _lastName = name; }

    public String getEpn() { return _epn; }
    public void setEpn(String epn) { _epn = epn; }

    public String getSsn() { return _ssn; }
    public void setSsn(String ssn) { _ssn = ssn; }

    public Date getDob() { return _dob; }
    public void setDob(Date dob) { _dob = dob; }

    public String getMrn() { return _mrn; }
    public void setMrn(String mrn) { _mrn = mrn; }

    public String getFacility() { return _facility; }
    public void setFacility(String facility) { _facility = facility; }

    public String getFirstName() { return _firstName; }
    public void setFirstName(String name) { _firstName = name; }

}
