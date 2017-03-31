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

public final class IntegerUtilities {

    private IntegerUtilities() {
    }

    public static int decode(String s, int defaultValue) {
        try {
            return Integer.decode(s).intValue();
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static boolean parseBoolean(int i) {
        return (i > 0) ? true : false;
    }

    public static boolean parseBoolean(Integer i) {
        return (i != null) ? parseBoolean(i.intValue()) : false;
    }

    public static int toInt(boolean b) {
        return b ? 1 : 0;
    }

    public static Integer toInteger(boolean b) {
        return new Integer(toInt(b));
    }

    public static Integer or(Integer one, Integer two) {
        return new Integer(one.intValue() | two.intValue());
    }
}
