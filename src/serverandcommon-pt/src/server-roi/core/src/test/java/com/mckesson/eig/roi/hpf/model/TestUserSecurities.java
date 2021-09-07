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

import junit.framework.TestCase;

public class TestUserSecurities extends TestCase {

    private User _user;
    private static final int HASH_VAL = 861101;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        _user = new User(1);
    }

    @Override
    protected void tearDown() throws Exception {
        _user = null;
        super.tearDown();
    }

    public void testUserSecurities() {

        UserSecurity securityId = new UserSecurity(_user.getInstanceIdValue(),
                                                   UserSecurity.ENTERPRISE,
                                                   UserSecurity.ROI_VIP_STATUS);

        assertEquals(securityId.getFacility(), UserSecurity.ENTERPRISE);
        assertEquals(securityId.getUserId(), _user.getInstanceIdValue());
        assertEquals(securityId.getSecurityId(), UserSecurity.ROI_VIP_STATUS);
    }

    public void testUserSecurityId() {

        UserSecurity securityId = new UserSecurity();
        int res = securityId.hashCode();

        assertEquals(HASH_VAL, res);
        securityId.setUserId(_user.getInstanceIdValue());
        securityId.setFacility(UserSecurity.ENTERPRISE);
        securityId.setSecurityId(UserSecurity.ROI_VIP_STATUS);

        assertEquals(securityId.getUserId(), _user.getInstanceIdValue());
        assertEquals(securityId.getSecurityId(),
                    UserSecurity.ROI_VIP_STATUS);
        assertEquals(securityId.getFacility(), UserSecurity.ENTERPRISE);

        UserSecurity id1 = new UserSecurity(_user.getInstanceIdValue(),
                UserSecurity.ENTERPRISE, UserSecurity.ROI_VIP_STATUS);

        assertFalse(securityId.equals(null));
        assertFalse(securityId.equals(new User()));

        assertEquals(id1, securityId);
    }
}
