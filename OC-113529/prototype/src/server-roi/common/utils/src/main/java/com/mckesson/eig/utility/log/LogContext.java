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

package com.mckesson.eig.utility.log;

import java.util.Map;

import org.apache.log4j.MDC;

public final class LogContext {

    private LogContext() {
    }

    public static void put(String key, Object value) {
        if (key == null || value == null) {
            return;
        }
        MDC.put(key, value);
    }

    public static void remove(String key) {
        if (key == null) {
            return;
        }
        MDC.remove(key);
    }

    public static Object get(String key) {
        if (key == null) {
            return null;
        }
        return MDC.get(key);
    }

    public static void clear() {
        Map<?, ?> context = MDC.getContext();
        if (context != null) {
            context.clear();
        }
    }
}
