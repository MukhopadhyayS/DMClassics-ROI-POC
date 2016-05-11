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

import com.mckesson.eig.roi.hpf.dao.UserSecurityHibernateDao;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.test.BaseROITestCase;
import com.mckesson.eig.wsfw.model.authentication.AuthenticatedResult;
import com.mckesson.eig.wsfw.session.WsSession;

public class TestHPFAuthenticationStrategy extends BaseROITestCase {

    private static HPFAuthenticationStrategy _strategy;
    private static UserSecurityHibernateDao _securityDao;

    @Override
    public void initializeTestData() {

        _strategy = (HPFAuthenticationStrategy) getService("AuthenticationStrategy");

        _strategy.setAuthenticationUrl(getServiceURL(""));
        _securityDao =
            (UserSecurityHibernateDao) getService(UserSecurityHibernateDao.class.getName());

    }

    @Override
    protected String getServiceURL(String serviceMethod) {

        String server = props.getProperty("HPF_SERVER_IP_ADDRESS");
        String port = props.getProperty("HPF_SERVER_PORT_NUMBER");
        String url = MessageFormat.format("http://{0}:{1}/portal/eig/iservices/configuration?wsdl", server, port);

        return url;
    }

    public void testHPFAuthenticationStrategy() {

        AuthenticatedResult result = _strategy.login(DEFAULT_TEST_USER, DEFAULT_TEST_PWD);
        assertTrue(result.isAuthenticated());
        WsSession.setSessionData(HPFAuthenticationStrategy.AUTHENTICATED_ROI_USER, null);
        result = _strategy.login("no_such_a_user", "roi");
        assertFalse(result.isAuthenticated());

        //invalid username
        result = _strategy.login("invalidID", "roi");
        assertFalse(result.isAuthenticated());
    }

    public void testHPFAuthenticationStrategyWithURL() {

        HPFAuthenticationStrategy strategy = new HPFAuthenticationStrategy();
        strategy.setAuthenticationUrl("http://invalidServer:80/portal/eig/iservices/configuration?wsdl");
        AuthenticatedResult result = strategy.login(DEFAULT_TEST_USER, null);

        assertFalse(result.isAuthenticated());
    }

    public void testSecurityDaoInvalidUser() {
        try {
            User user = _securityDao.retrieveUser("InvalidUser");
            assertNull(user);
        } catch (Exception e) {
            fail();
        }
    }
}
