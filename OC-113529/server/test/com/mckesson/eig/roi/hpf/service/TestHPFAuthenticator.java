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


import com.mckesson.eig.iws.security.Ticket;
import com.mckesson.eig.roi.test.BaseROITestCase;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.wsfw.session.WsSession;

public class TestHPFAuthenticator extends BaseROITestCase {

    private static HPFAuthenticator _authenticator;

    public void initializeTestData() {

        _authenticator = new HPFAuthenticator();
        HPFAuthenticationServiceLoader loader
            = (HPFAuthenticationServiceLoader) getBean("HPFAuthenticationServiceLoader");

        loader.setUrl(getServiceURL(""));

    }

    @Override
    protected String getServiceURL(String serviceMethod) {

        String server = props.getProperty("HPF_SERVER_IP_ADDRESS");
        String port = props.getProperty("HPF_SERVER_PORT_NUMBER");
        String url = MessageFormat.format("http://{0}:{1}/portal/eig/iservices/signin", server, port);

        return url;
    }

    public void testAuthenticateFailure() {

        WsSession.setSessionData("AuthTicket", "adfadf");
        _authenticator.getAppID();
        try {
            _authenticator.validate();
            fail("Should have thrown Exception");
        } catch (Exception ex) {
            assertTrue(WsSession.getSessionData(WsSession.AUTHRESULT) == null);
        }
   }
    public void testAuthenticateWithValidTicket() {

        initSession(getUser());
        WsSession.setSessionData("AuthTicket", Ticket.getTicket(DEFAULT_TEST_USER));
        WsSession.setSessionData("USERNAME", DEFAULT_TEST_USER);
        _authenticator.validate();
        assertTrue(WsSession.getSessionData(WsSession.AUTHRESULT) == null);
   }

   public void testAuthenticateSuccess() {

       initSession(getUser());
       WsSession.setSessionData("AuthTicket", "test");
       WsSession.setSessionData("USERNAME", DEFAULT_TEST_USER);
       WsSession.setSessionData("PASSWORD", DEFAULT_TEST_PWD);

       _authenticator.validate();
       assertTrue(WsSession.getSessionData(WsSession.AUTHRESULT) != null);


   }
   public void testAuthenticateWithInvalidUsers() {

       initSession(getUser());
       WsSession.setSessionData("AuthTicket", "test");
       WsSession.setSessionData("USERNAME", "clerkkkkkkk");
       WsSession.setSessionData("PASSWORD", DEFAULT_TEST_PWD);
       try {
           _authenticator.validate();
           fail("Should have thrown Exception");
       } catch (Exception e) {
           assertTrue(WsSession.getSessionData(WsSession.AUTHRESULT) == null);
       }
   }

   public void testAuthenticateFaliure() {

       initSession(getUser());
       HPFAuthenticationServiceLoader loader
       = (HPFAuthenticationServiceLoader) getBean("HPFAuthenticationServiceLoader");
       loader.setUrl("");
       WsSession.setSessionData("AuthTicket", "test");
       WsSession.setSessionData("USERNAME", DEFAULT_TEST_USER);
       WsSession.setSessionData("PASSWORD", DEFAULT_TEST_PWD);
       try {
           _authenticator.validate();
           fail("Should have thrown Exception");
        } catch (Exception e) {
            assertNotNull(e);
        }
   }
   public Object getBean(String bean) {
       return SpringUtilities.getInstance().getBeanFactory().getBean(bean);
   }
}
