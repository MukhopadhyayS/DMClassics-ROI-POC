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

import java.text.MessageFormat;

import com.mckesson.eig.roi.test.BaseROITestCase;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * Testcase which tests the service without deploying it in any container.
 *
 * TODO Tested without security headers.Once the respective handlers are ready,
 * we need to send the request with header.
 */
public class TestSecurityService extends BaseROITestCase {

    private static String _serviceMethod = "logoff";

    /**
     * Sets up the data required for testing the service.
     *
     * @throws Exception
     *             General Exception.
     * @see junit.framework.TestCase#setUp()
     */
    public void initializeTestData() {
    }

    @Override
    protected String getServiceURL(String serviceMethod) {

        String server = props.getProperty("HPF_SERVER_IP_ADDRESS");
        String port = props.getProperty("HPF_SERVER_PORT_NUMBER");
        String url = MessageFormat.format("http://{0}:{1}/roi/services/securitylogoff", server, port);

        return url;

    }

    public void testSecurityLogOff() {

        try {

            String res = sendSoapRequest(getServiceURL(_serviceMethod), "", _serviceMethod, ADMIN_USER, ADMIN_PWD);
            assertEquals("", res);

         } catch (Exception e) {
            fail(e.getMessage(), e);
        }
    }

    public void testSecurityLogOffInvalidUsernameAndPassword() {

        try {

            WsSession.setSessionData(HPFAuthenticationStrategy.AUTHENTICATED_ROI_USER, null);
            String res = sendSoapRequest(getServiceURL(_serviceMethod), "", _serviceMethod, "Invalid_user", "Invalid_Password");
            assertTrue(res.contains("NotAuthenticatedException"));

        } catch (Exception e) {
            fail("Should not throw Exception", e);
        }
    }
}
