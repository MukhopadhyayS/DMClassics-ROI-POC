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
package com.mckesson.eig.utility.util;

import java.net.URL;
import java.text.MessageFormat;

public final class AdminURLUtilities {

    private static final int HTTP_LEN = 4;
    private static final int HTTPS_LEN = 5;
    
    private AdminURLUtilities() {
    }

    public static String generateFormattedURL(String formatPattern,
            String protocol, String name, String port, String hpfcontext) {
        Object[] urlArgs = {protocol, name, port, hpfcontext};
        String generatedURL = MessageFormat.format(formatPattern, urlArgs);
        if (generatedURL != null) {
            if (generatedURL.length() > 0) {
                return generatedURL;
            }
        }
        return "";
    }

    public static String getProtocol(URL theURL) {
        return theURL.getProtocol();
    }

    public static String getName(URL theURL) {
        return theURL.getHost();
    }

    public static String getPort(URL theURL) {
        return Integer.toString(theURL.getPort());
    }

    public static boolean validateURLContext(URL theUrl) throws Exception {
        boolean bReturn = true;

        // validate context
        if (StringUtilities.isEmpty(theUrl.getPath())) {
            throw new Exception("URL - no hpfcontext found is invalid");
        }

        if (StringUtilities.isEmpty(extractURLContext(theUrl))) {
            throw new Exception("URL - hpfcontext is invalid");
        }

        return bReturn;
    }

    public static String extractURLContext(URL theUrl) {
        String theHpfContext = "";

        int beginIndex = (theUrl.getPath().indexOf('/', 0) == 0) ? 1 : 0;
        theHpfContext = theUrl.getPath().substring(beginIndex,
                theUrl.getPath().indexOf('/', beginIndex));
        if (!StringUtilities.isEmpty(theHpfContext)) {
        	return theHpfContext;
        }

        return "";
    }

    public static String extractLevelerContext(URL theUrl) {
        String theHpfContext = "";

        int endIndex = theUrl.getFile().lastIndexOf('=');
        if (endIndex != -1) {
            theHpfContext = theUrl.getFile().substring(endIndex + 1);
            if (!StringUtilities.isEmpty(theHpfContext)) {
            	return theHpfContext;
            }
        }
        return "";
    }

    public static boolean validateLevelerContext(URL theUrl) throws Exception {
        boolean bReturn = true;
        if (StringUtilities.isEmpty(theUrl.getPath())) {
            throw new Exception("URL - no hpfcontext found is invalid");
        }
        if (StringUtilities.isEmpty(extractURLContext(theUrl))) {
            throw new Exception("URL - hpfcontext is invalid");
        }

        return bReturn;
    }

    public static boolean validateURL(URL theUrl) throws Exception {
        String tmpProtocolUpperCase = null;
        boolean bReturn = true;

        if (theUrl == null) {
            throw new Exception("Url is null.");

        }
        // Validate Protocol
        String tmpProtocol = theUrl.getProtocol();
        if (StringUtilities.isEmpty(tmpProtocol)) {
             throw new Exception("URL - does not have a Protocol");
        }

        switch (tmpProtocol.length()) {
            case HTTP_LEN :
                tmpProtocolUpperCase = tmpProtocol.toUpperCase();
                if (!tmpProtocolUpperCase.equals("HTTP")) {
                    throw new Exception(
                            "URL has invalid Protocol during http validation ");
                }
                break;
            case HTTPS_LEN :
                tmpProtocolUpperCase = tmpProtocol.toUpperCase();
                if (!tmpProtocol.toUpperCase().equals("HTTPS")) {
                    throw new Exception(
                            "URL has invalid Protocol during https validation");
                }
                break;
            default :
                throw new Exception("URL - unsupported Protocol length = "
                        + tmpProtocol.length());
        }

        // validate Host aka) name
        if (theUrl.getHost() == null) {
            throw new Exception("URL - name is null");
        }
        if (theUrl.getHost().length() == 0) {
            throw new Exception("URL - name is zero bytes");
        }

        // validate port
        if (theUrl.getPort() == -1) {
            throw new Exception("URL - port is invalid");
        }

        return bReturn;

    }

}
