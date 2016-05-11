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

public class TestUserFacility extends TestCase {

    private static Integer _userId = 1;
    private static String _facility = "E_P_R_S";
    private static UserFacility _userFacility = new UserFacility();

    private static final int HASH_VAL1 = 0;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        _userFacility.setFacility(_facility);
        _userFacility.setUserId(_userId);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testUserFacilities() {

        UserFacility facility = _userFacility;
        assertEquals(facility.getFacility(), _facility);
        assertEquals(facility.getUserId(), _userId);
    }

    public void testUserSecurityId() {

        UserFacility facility1  = _userFacility;
        UserFacility facility2  = new UserFacility();

        int  res = facility1.hashCode();
        User user = new User();
        assertTrue(res < 0);

        assertEquals(false, facility1.equals(facility2));
        assertEquals(false, facility1.equals(null));
        assertEquals(false, facility1.equals(user));

        facility2.setFacility("facility1");
        facility2.setUserId(2);
        assertEquals(false, facility1.equals(facility2));

        facility2.setUserId(2);
        assertEquals(false, facility1.equals(facility2));

        facility2.setFacility(null);
        assertEquals(HASH_VAL1, facility2.hashCode());

        facility2.setUserId(null);
        assertTrue(facility2.hashCode() > 0);

        facility2.setFacility(facility1.getFacility());
        facility2.setUserId(facility1.getUserId());
        assertEquals(true, facility1.equals(facility2));

    }
}
