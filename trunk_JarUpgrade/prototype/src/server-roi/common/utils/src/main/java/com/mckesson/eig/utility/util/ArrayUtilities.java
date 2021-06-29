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

public final class ArrayUtilities {

    private ArrayUtilities() {
    }

    public static boolean isEmpty(Object[] array) {
        return (array == null) || (array.length < 1);
    }

    public static int length(Object[] array) {
        return (array == null) ? 0 : array.length;
    }

    public static boolean isSelectionContinuous(int[] array) {
        if ((array == null) || (array.length < 1)) {
            return false;
        }
        for (int i = 0; i < array.length - 1; i++) {
            if (Math.abs(array[i] - array[i + 1]) != 1) {
                return false;
            }
        }

        return true;
    }
}
