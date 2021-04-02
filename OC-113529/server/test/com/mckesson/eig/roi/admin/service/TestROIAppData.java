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

package com.mckesson.eig.roi.admin.service;

import com.mckesson.eig.roi.admin.model.ROIAppData;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.test.BaseROITestCase;


/**
 * @author OFS
 * @date   May 26, 2009
 * @since  HPF 13.1 [ROI]; Sep 1, 2008
 */
public class TestROIAppData
extends BaseROITestCase {

    protected static final String ROI_ADMIN_SERVICE =
        "com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl";

    private static ROIAdminService _adminService;
    protected static final String AUTHENTICATED_ROI_USER = "authenticated_roi_user";

    public void initializeTestData()
    throws Exception {

        _adminService = (ROIAdminService) getService(ROI_ADMIN_SERVICE);
    }

    /**
     * This test case for retrieve FreeFormFacilities
     */
    public void testRetrieveFreeFormFacilities() {

        try {

            ROIAppData data = _adminService.retrieveROIAppData();
            assertNotNull(data);
        } catch (ROIException e) {
            fail("Retrieve FreeFormFacilities should not thrown exception." + e.getErrorCode());
        }
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        return " ";
    }

}
