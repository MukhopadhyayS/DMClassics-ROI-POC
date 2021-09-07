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


import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.hpf.model.Facility;

import junit.framework.TestCase;


/**
 * @author ranjithr
 * @date   Sep 02, 2008
 * @since  HPF 13.1 [ROI]; Jul 8, 2008
 */
public class TestFacility
extends TestCase {

    private static String _facilityCode = "FaciCode";
    private static String _facilityName = "FaciName";
    private static Integer _documentSet = 1;

    public void testSetUp()
    throws Exception {
        setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * This test case is to cover the facility model
     */
    public void testFacilityModel() {

        try {

            Facility facility = new Facility();
            facility.setDocumentSet(_documentSet);
            facility.setFacilityCode(_facilityCode);
            facility.setFacilityName(_facilityName);

            assertEquals(_documentSet, facility.getDocumentSet());
            assertEquals(_facilityCode, facility.getFacilityCode());
            assertEquals(_facilityName, facility.getFacilityName());
        } catch (ROIException e) {
            fail("Testing facility model");
        }
    }
}
