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

import com.mckesson.eig.roi.base.api.ROIException;

import junit.framework.TestCase;

/**
 * @author OFS
 * @date   May 27, 2013
 * @since  May 27, 2013
 */
public class TestSecurityRights 
extends TestCase {
    
    private static final long SECURITY_ID = 1L;
    private static String SECURITY_DESCRIPTION = "Security Description";
    private static final long SECURITY_GROUP = 1L;
    private static SecurityRights _securityRights = new SecurityRights();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        _securityRights.setSecurityDesc(SECURITY_DESCRIPTION);
        _securityRights.setSecurityGroup(SECURITY_GROUP);
        _securityRights.setSecurityId(SECURITY_ID);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testSecurityRightsModel() {
        
        try {
            
            assertEquals(SECURITY_DESCRIPTION, _securityRights.getSecurityDesc());
            assertEquals(SECURITY_GROUP, _securityRights.getSecurityGroup());
            assertEquals(SECURITY_ID, _securityRights.getSecurityId());
            
        } catch (ROIException e) {
            fail("SecurityRights model setup will not throw exception. " + e.getErrorCode());
        }
    }

}
