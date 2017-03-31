/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.net;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mckesson.eig.utility.testing.UnitTest;

public class TestHttpTimeRetriever extends UnitTest {

    private static final String HOST = "www.cnn.com";
    private static final String PORT = "80";
    private static final String URL_STRING = "http://" + HOST + ":" + PORT;
    private static final String SSL_KEYSTORE = System.getenv("JBOSS_HOME")
            + "\\server\\default\\conf" + "\\ssl.keystore";

    public void testHttpTimeRetrieverConstructor() throws Exception {
        URL url = new URL(URL_STRING);
        new HttpTimeRetriever(url);
    }

    public void testGetGMTString() throws Exception {
        URL url = new URL(URL_STRING);
        TimeRetriever tr = new HttpTimeRetriever(url);
        System.out.println("gmtstring" + tr.getGMTString());
        assertNotNull(tr.getGMTString());
    }

    public void testGetGMTTime() throws Exception {
        URL url = new URL(URL_STRING);
        TimeRetriever tr = new HttpTimeRetriever(url);
        Date gmtTime = tr.getGMTDate();
        assertNotNull(gmtTime);
    }

    public void testGetGMTTimeNonGMTTime() throws Exception {
        URL url = new URL(URL_STRING);
        TimeRetriever tr = new HttpTimeRetriever(url);
        String gmtTime = tr.getGMTString();
        assertNotNull(gmtTime);

        // current time
        Date localTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String slocalTime = sdf.format(localTime);
        assertTrue("the time wont be the same", !gmtTime.equals(slocalTime));
    }

    public void testGetGMTTimeURLisNULL() {
        try {
            URL url = null;
            new HttpTimeRetriever(url);
            fail("it should fail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetGMTTimeNotHTTPURL() {
        try {
            URL url = new URL("ftp://127.0.0.1");
            TimeRetriever tr = new HttpTimeRetriever(url);
            tr.getGMTString();
            fail("it should fail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetGMTTimeNotValidURL() {
        try {
            URL url = new URL("http://foobardoesntexist");
            TimeRetriever tr = new HttpTimeRetriever(url);
            tr.getGMTString();
            fail("it should fail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
