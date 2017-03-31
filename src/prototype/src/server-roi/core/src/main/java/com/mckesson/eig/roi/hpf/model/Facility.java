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

package com.mckesson.eig.roi.hpf.model;

import java.io.Serializable;


/**
 * @author OFS
 * @date   Sep 02, 2008
 * @since  HPF 13.1 [ROI]; Aug 27, 2008
 */
public class Facility
implements Serializable {

    private String _facilityCode;
    private String _facilityName;
    private Integer _documentSet;

    public String getFacilityCode() { return _facilityCode; }
    public void setFacilityCode(String facilityCode) { _facilityCode = facilityCode; }

    public String getFacilityName() { return _facilityName; }
    public void setFacilityName(String facilityName) { _facilityName = facilityName; }

    public Integer getDocumentSet() { return _documentSet; }
    public void setDocumentSet(Integer documentSet) { _documentSet = documentSet; }

}
