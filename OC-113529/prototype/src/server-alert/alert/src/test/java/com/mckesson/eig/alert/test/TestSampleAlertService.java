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

package com.mckesson.eig.alert.test;

import junit.framework.TestCase;

import org.mockejb.jms.TextMessageImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mckesson.eig.alert.service.AlertHandler;
import com.mckesson.eig.utility.log.LogEvent;
import com.mckesson.eig.utility.util.SpringUtilities;

public final class TestSampleAlertService extends TestCase {

    private static final String SPRING_CONFIG = "com/mckesson/eig/alert/spring.xml";
    private static BeanFactory _beanFactory;
    private static AlertHandler _alertService;

    private static void init() {

        TestSampleAlertService._beanFactory =
            new ClassPathXmlApplicationContext(TestSampleAlertService.SPRING_CONFIG);
        TestSampleAlertService._beanFactory.getBean("log_initializer");
        SpringUtilities.getInstance().setBeanFactory(TestSampleAlertService._beanFactory);
    }

    public static void testSetup() {

        TestSampleAlertService.init();
        TestSampleAlertService._alertService =
            ((AlertHandler) SpringUtilities
                    .getInstance()
                    .getBeanFactory()
                    .getBean(AlertHandler.class.getName()));
    }

    public void testDoAlertSuccess() throws Exception {

        LogEvent le = new LogEvent();
        le.setAppID("roi");
        le.setAuthUser("sethurman");
        le.setCode("EIDS234");
        le.setMessage("Test message");
        le.setDetails("details about error");
        _alertService.alert(le.toString());
    }

    public void testDoAlertFail() throws Exception {
        _alertService.alert("alert would fail");
    }

}
