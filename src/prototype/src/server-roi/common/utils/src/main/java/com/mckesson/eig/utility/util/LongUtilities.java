/**
 * Copyright 2007-2008 McKesson Corporation and/or one of its subsidiaries. 
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
/**
 * A set of utilities that help with 'long' manipulations.
 */
public final class LongUtilities {
    
    /**
     * This method checks for the validity of the given object id.
     * 
     * @param id -
     *            Id of the object to be checked.
     * @return boolean - A boolean to indicate if the object id exists.
     */
    public static boolean isValidObjectId(long id) {
        return (id < 0) ? false : true;
    }

    private LongUtilities() {
    }

    /**
     * Checks whether the passed string value is valid long or not.
     *
     * @param longValue
     *            string value to be checked.
     * @return <code>true</code> if its a valid long value <code>false</code>
     *         otherwise.
     */
    public static boolean isValidPositiveLong(String longValue) {
        try {
            if (longValue != null) {
                longValue = checkForPlus(longValue);
                Long value = Long.parseLong(longValue);
                if (value.longValue() > 0) {
                    return true;
                }
            }
            return false;
        } catch (NumberFormatException exp) {
            return false;
        }
    }

    /**
     * Checks whether the string value starts with symbol "+" ,if so it removes
     * the prefix "+" and returns the string.
     *
     * @param longValue
     *            value to be checked for prefix "+".
     * @return  prefix "+" removed string.
     */
    public static String checkForPlus(String longValue) {
        String plusValue;
        if (longValue.startsWith("+")) {
            plusValue = longValue.substring(1, longValue.length());
            return plusValue;
        }
        return longValue;
    }
}
