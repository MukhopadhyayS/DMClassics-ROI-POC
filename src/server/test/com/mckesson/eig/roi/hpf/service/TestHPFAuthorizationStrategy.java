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

package com.mckesson.eig.roi.hpf.service;

import com.mckesson.eig.roi.test.BaseROITestCase;
import com.mckesson.eig.wsfw.model.authentication.AuthenticatedResult;
import com.mckesson.eig.wsfw.session.WsSession;


public class TestHPFAuthorizationStrategy extends BaseROITestCase {

    private HPFAuthenticationStrategy _strategy;
    private HPFAuthorizationStrategy _authorizationStrategy;

    public TestHPFAuthorizationStrategy()
    throws Exception {
       setUp();
       _strategy = (HPFAuthenticationStrategy) getService("AuthenticationStrategy");
       _authorizationStrategy =  (HPFAuthorizationStrategy) getService("AuthorizationStrategy");
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }

    public void testHPFAuthorizationStrategy() {
        try {

            AuthenticatedResult result = _strategy.login(DEFAULT_TEST_USER, DEFAULT_TEST_PWD);
            assertTrue(result.isAuthenticated());
            assertFalse(_authorizationStrategy.authorize("roi_administration",
                                                         "createRequest",
                                                         null));
            assertTrue(_authorizationStrategy.authorize("ROIPatientService", "vip", null));

        } catch (Exception e) {
            System.out.println("Exception");
            e.printStackTrace();
        }
    }

    public void testWebServiceAuthorizationTable() {

        try {

            WebServiceAuthorizationTable mgr = new WebServiceAuthorizationTable();
            mgr.setMappingFile(null);
        } catch (Throwable e) {
            assertTrue(e != null);
        }
    }

    public void testWebServiceSecurityManager() {

        WebServiceSecurityManager mgr = new WebServiceSecurityManager();
        assertEquals(true, mgr.canAccessWebservice("test", "test"));

        try {

            WsSession.setUserSecurityRights(null);
            assertFalse(mgr.canAccessWebservice("roi_administration", "deleteRequest"));

        } catch (Exception e) {
            assertTrue(e != null);
        }
    }
}
