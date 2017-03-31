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

package com.mckesson.eig.roi.reports.dao;

import com.mckesson.eig.roi.reports.service.ROIReportUtil;

import junit.framework.TestCase;



/**
 *
 * @author OFS
 * @date   Aug 12, 2009
 * @since  HPF 13.1 [ROI]; Aug 12, 2009
 */
public class TestROIReportUtil
extends TestCase {

    public void testSetUp()
    throws Exception {
        setUp();

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test case to cover the constructListContentXML method in ROIReportUtil
     */
    public void testROIReportUtil() {

        String value = ROIReportUtil.constructListContentXML("", "test");
        assertEquals(0, value.length());
    }
}
