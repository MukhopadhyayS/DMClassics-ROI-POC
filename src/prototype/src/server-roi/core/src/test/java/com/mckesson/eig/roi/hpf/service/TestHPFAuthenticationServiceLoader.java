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
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.model.authentication.AuthenticatedResult;

public class TestHPFAuthenticationServiceLoader
extends BaseROITestCase {

    @Override
    protected String getServiceURL(String serviceMethod) {

        String server = props.getProperty("HPF_SERVER_IP_ADDRESS");
        String port = props.getProperty("HPF_SERVER_PORT_NUMBER");
        String url = MessageFormat.format("http://{0}:{1}/portal/eig/iservices/signin", server, port);
        return url;
    }

    @Override
    public void initializeTestData() throws Exception {

        HPFAuthenticationServiceLoader loader
                    = (HPFAuthenticationServiceLoader) getBean("HPFAuthenticationServiceLoader");
        loader.setUrl(getServiceURL(null));
    }

    public void testAuthenticateWithValidURL() {

        try {
            HPFAuthenticationServiceLoader loader
            = (HPFAuthenticationServiceLoader) getBean("HPFAuthenticationServiceLoader");

            AuthenticatedResult res = loader.authenticate(ADMIN_USER, ADMIN_PWD);
            assertNotNull(res);
        } catch (Exception e) {
            fail("Should not have thrown ROIException");
        }
    }
    public void testAuthenticateWithValidURLInvalidPassword() {

        try {
            HPFAuthenticationServiceLoader loader
            = (HPFAuthenticationServiceLoader) getBean("HPFAuthenticationServiceLoader");

            AuthenticatedResult res = loader.authenticate(ADMIN_USER, "****");
            assertNotNull(res);
        } catch (Exception e) {
            fail("Should not have thrown ROIException");
        }
    }
    public void testLoginWithValidURL() {

        HPFAuthenticationServiceLoader loader
        = (HPFAuthenticationServiceLoader) getBean("HPFAuthenticationServiceLoader");
        try {
            AuthenticatedResult res = loader.login(ADMIN_USER, ADMIN_PWD);
            assertNotNull(res);
        } catch (Exception e) {
            fail("Should not have thrown ROIException");
        }
    }
    public void testAuthenticateWithInvalidURL() {

        HPFAuthenticationServiceLoader loader = new HPFAuthenticationServiceLoader("");
        try {
            AuthenticatedResult res = loader.authenticate(ADMIN_USER, ADMIN_PWD);
            fail("Should have thrown ROIException");
        } catch (Exception e) {
            assertNotNull(e);

        }
    }
    public void testLoginWithInvalidURL() {

        HPFAuthenticationServiceLoader loader = new HPFAuthenticationServiceLoader("");
        try {
            AuthenticatedResult res = loader.login(ADMIN_USER, ADMIN_PWD);
            fail("Should have thrown ROIException");
        } catch (Exception e) {
            assertNotNull(e);
        }
    }
    public void testauthenticateWithInvalidURL() {

        HPFAuthenticationServiceLoader loader = new HPFAuthenticationServiceLoader("");
        try {
            loader.authenticate(ADMIN_USER);
        } catch (Exception e) {
            fail("Should have thrown ROIException");
        }
    }
    public Object getBean(String bean) {

        HPFAuthenticationServiceLoader loader = (HPFAuthenticationServiceLoader)
                SpringUtilities.getInstance().getBeanFactory().getBean(bean);

        loader.setUrl(StringUtilities.isEmpty(loader.getUrl())
                ? getServiceURL("") : loader.getUrl());


        return loader;
    }
}
