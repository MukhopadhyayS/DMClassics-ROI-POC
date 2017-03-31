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

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class Java14PlusLenientHostnameVerifier
        implements
            HostnameVerifier,
            LenientHostnameVerifier {

    public Java14PlusLenientHostnameVerifier() {
    }

    public boolean verify(String hostname, SSLSession session) {
        return true;
    }

    public void install() {
        HttpsURLConnection.setDefaultHostnameVerifier(this);
    }
}
