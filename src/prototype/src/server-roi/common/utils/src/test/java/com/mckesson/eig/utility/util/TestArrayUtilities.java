/*
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

import junit.framework.TestSuite;

import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.testing.UnitTest;

public class TestArrayUtilities extends UnitTest {

    public TestArrayUtilities(String name) {
        super(name);
    }

    public static TestSuite suite() {
        return new CoverageSuite(TestArrayUtilities.class, ArrayUtilities.class);
    }

    public void testIsEmpty() {
        assertTrue(ArrayUtilities.isEmpty(null));
        assertTrue(ArrayUtilities.isEmpty(new String[]{}));

        assertFalse(ArrayUtilities.isEmpty(new Object[]{"foo"}));
    }

    public void testSize() {
        assertEquals(0, ArrayUtilities.length(null));
        assertEquals(0, ArrayUtilities.length(new String[]{}));

        assertEquals(1, ArrayUtilities.length(new Object[]{"foo"}));
    }

    public void testIsContinuous() {
        int[] emptyArray = {};
        int[] contArray = {ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX};
        int[] nonContArray = {ZERO, ONE, THREE, TWO, SEVEN, SIX};

        assertFalse(ArrayUtilities.isSelectionContinuous(null));
        assertFalse(ArrayUtilities.isSelectionContinuous(emptyArray));
        assertTrue(ArrayUtilities.isSelectionContinuous(contArray));
        assertFalse(ArrayUtilities.isSelectionContinuous(nonContArray));
    }
}
