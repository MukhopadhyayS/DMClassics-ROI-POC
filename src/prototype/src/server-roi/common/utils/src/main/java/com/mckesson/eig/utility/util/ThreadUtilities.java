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

import com.mckesson.eig.utility.concurrent.ConcurrencyInterruptedException;

public final class ThreadUtilities {

    /**
     * No argument constructor.
     */
    private ThreadUtilities() {
    }

    /**
     * @param ms
     *            Passed as an argument of type integer.And calls 
     *            super class <code>Thread<code>method.
     */
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new ConcurrencyInterruptedException(e);
        }
    }
}
