/**
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Corporation and/or one of its subsidiaries and is protected 
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.net;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class HttpTimeRetriever implements TimeRetriever {

    private static final LenientHostnameVerifier HOSTNAME_VERIFIER =
                                findCorrectHostnameVerifier();

    private URL _url;

    public HttpTimeRetriever(URL url) {
        if (url == null) {
            throw new NullPointerException("url can not be null");
        }
        _url = url;
    }

    public Date getGMTDate() {
        HttpURLConnection conn = null;
        try {
            if (_url.getProtocol().equals("https")) {
                if (HOSTNAME_VERIFIER != null) {
                    HOSTNAME_VERIFIER.install();
                }
            }
            conn = (HttpURLConnection) _url.openConnection();
            conn.connect();
            return new Date(conn.getDate());
        } catch (Exception e) {
            throw new TimeRetrievalException(
                    "Could not retrieve the current time from HTTP server: "
                            + _url.toString(), e);
        } finally {
            close(conn);
        }
    }

    public String getGMTString() {
        Date serverTime = getGMTDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(serverTime);
    }

    private void close(HttpURLConnection connection) {
        try {
            if (connection != null) {
                connection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static LenientHostnameVerifier findCorrectHostnameVerifier() {
        return (attemptLoadOfLenientHostnameVerifier());
     }

    private static LenientHostnameVerifier attemptLoadOfLenientHostnameVerifier() {
        return new Java14PlusLenientHostnameVerifier();
    }
}
