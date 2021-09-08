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

import junit.framework.TestCase;
/**
 * 
 * This class tests the utilities provided by the LongUtilities class.
 * 
 */
public class TestLongUtilities extends TestCase {

    /**
     * Member variable VALID_ID of type long.
     */
    public static final long VALID_ID = 123;
    /**
     * Member variable INVALID_ID of type long.
     */
    public static final long INVALID_ID = -1;
    /**
     * This method tests the scenarios when passing valid and invalid object
     * ids.
     * 
     */
    public void testIsValidObjectId() {
        assertTrue(LongUtilities.isValidObjectId(VALID_ID));
        assertFalse(LongUtilities.isValidObjectId(INVALID_ID));
    }

}
