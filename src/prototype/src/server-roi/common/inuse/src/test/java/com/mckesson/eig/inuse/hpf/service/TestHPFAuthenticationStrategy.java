/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */
package com.mckesson.eig.inuse.hpf.service;

import com.mckesson.eig.inuse.hpf.dao.UserSecurityHibernateDao;
import com.mckesson.eig.inuse.hpf.model.User;
import com.mckesson.eig.inuse.service.test.BaseInUseTestCase;
import com.mckesson.eig.wsfw.model.authentication.AuthenticatedResult;
import com.mckesson.eig.wsfw.session.WsSession;

public class TestHPFAuthenticationStrategy extends BaseInUseTestCase {

    private HPFAuthenticationStrategy _strategy;
    private UserSecurityHibernateDao _securityDao;

    public TestHPFAuthenticationStrategy()
    throws Exception {
        setUp();
        _strategy = (HPFAuthenticationStrategy) getService("AuthenticationStrategy");
        _securityDao =
            (UserSecurityHibernateDao) getService(UserSecurityHibernateDao.class.getName());

    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }

    public void testHPFAuthenticationStrategy() {

        AuthenticatedResult result = _strategy.login(DEFAULT_TEST_USER, DEFAULT_TEST_PWD);
        assertTrue(result.isAuthenticated());
        WsSession.setSessionData(HPFAuthenticationStrategy.AUTHENTICATED_INUSE_USER, null);
        result = _strategy.login("no_such_a_user", "roi");
        assertFalse(result.isAuthenticated());

        //invalid password
        result = _strategy.login(DEFAULT_TEST_USER, "invalidID");
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
