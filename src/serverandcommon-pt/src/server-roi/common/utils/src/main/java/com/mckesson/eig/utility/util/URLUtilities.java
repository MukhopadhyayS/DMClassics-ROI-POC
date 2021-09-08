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

package com.mckesson.eig.utility.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public final class URLUtilities {

    public static final int STR_LENGTH = 80;

    public static final int STR_LENGTH1 = 443;

    private URLUtilities() {
    }

    /**
     * Only using deprecated tag to avoid compiler warnings.
     */
	static String encode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (Throwable t) {
            throw new UtilitiesException(t);
        }
    }

    /**
     * @param s
     *            Passed as a parameter of type <code>String</code>.
     * @return object of type <code>URL</code> .
     */
    public static URL create(String s) {
        try {
            return new URL(s);
        } catch (MalformedURLException e) {
            throw new UtilitiesException(e);
        }
    }

    /**
     * @param fullURL
     *            Passed as an argument of type <code>URL</code>.
     * @return
     */
    static String buildServerURLAsString(URL fullURL) {
        StringBuilder result = new StringBuilder(STR_LENGTH);
        result
            .append(fullURL.getProtocol())
            .append("://")
            .append(fullURL.getHost());
        if ((!isStandardHTTPPort(fullURL)) && fullURL.getPort() > 0) {
            result
                .append(":")
                .append(fullURL.getPort());
        }
        return result.toString();
    }

    /**
     * @param url
     *            Passed as an argument of type URL.
     * @param protocol
     *            Passed as an argument of type String.
     * @return
     */
    public static boolean isProtocol(URL url, String protocol) {
        return StringUtilities.equalsIgnoreCaseWithTrim(url.getProtocol(), protocol);
    }

    /**
     * @param url
     *            Passed as an argument of type URL.
     * @param protocol
     *            Passed as an argument of type String.
     * @param port
     *            Passed as an argument of type integer.
     * @return
     */
    static boolean isProtocol(URL url, String protocol, int port) {
        // If port is not specified (getPort() == -1), then assume the protocol
        // is defining the port (e.g. a standardized port is being used).
        return isProtocol(url, protocol) && (url.getPort() == port || url.getPort() < 0);
    }

    /**
     * @param url
     *            Passed as an argument of type URL.
     * @return
     */
    static boolean isStandardHTTPPort(URL url) {
        return isProtocol(url, "http", STR_LENGTH) || isProtocol(url, "https", STR_LENGTH1);
    }

    /**
     * @param url
     *            Passed as an argument of type String.
     * @param parameters
     *            Passed as an argument of type MAP.
     * @return
     */
    static String concat(String url, Map<String, String> parameters) {
        if (CollectionUtilities.isEmpty(parameters)) {
            return url;
        }
        StringBuilder buffer = new StringBuilder();
        appendUrl(url, buffer);
        appendSeparator(url, buffer);
        encode(parameters, buffer);
        return buffer.toString();
    }

	private static void encode(Map<String, String> parameters, StringBuilder buffer) {
        boolean previous = false;
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            if (ObjectUtilities.isEmptyAsString(key)) {
                continue;
            }
            if (previous) {
                buffer.append('&');
            }
            String value = entry.getValue();
            buffer.append(encode(key));
            buffer.append('=');
            buffer.append(encode(value));
            previous = true;
        }
    }

    /**
     * @param url
     *            Passed as an argument of type String.
     * @param buffer
     *            Passed as an argument of type StringBuilder.
     */
    private static void appendUrl(String url, StringBuilder buffer) {
        buffer.append(url);
    }

    /**
     * @param url
     *            Passed as an argument of type String.
     * @param buffer
     *            Passed as an argument of StringBuilder.
     */
    private static void appendSeparator(String url, StringBuilder buffer) {
        if (url.lastIndexOf('?') > 0) {
            if (!url.endsWith("?")) {
                buffer.append('&');
            }
        } else {
            buffer.append('?');
        }
    }
}
