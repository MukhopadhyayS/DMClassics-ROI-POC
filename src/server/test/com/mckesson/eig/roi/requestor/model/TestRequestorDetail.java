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

package com.mckesson.eig.roi.requestor.model;


import junit.framework.TestCase;

import com.mckesson.eig.roi.base.api.ROIException;


/**
 * @author ranjithr
 * @date   Jul 8, 2008
 * @since  HPF 13.1 [ROI]; Jul 8, 2008
 */
public class TestRequestorDetail
extends TestCase {

    private static long _requestorDetailId = 1;

    public void testSetUp()
    throws Exception {
        setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * This test case is to cover the requestor detail model
     */
    public void testRequestorDetailModel() {

        try {

            RequestorDetail reqDetail = new RequestorDetail();
            reqDetail.setId(_requestorDetailId);

            assertEquals(_requestorDetailId, reqDetail.getId());
        } catch (ROIException e) {
            fail("Testing requestor detail model");
        }
    }
}
