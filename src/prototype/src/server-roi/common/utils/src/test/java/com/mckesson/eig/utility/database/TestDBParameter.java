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

package com.mckesson.eig.utility.database;

import com.mckesson.eig.utility.testing.UnitTest;

public class TestDBParameter extends UnitTest {

    public static final String OBJ_5010 = "5010";
    public static final String OBJ_5129 = "5129";
    public static final String NULL_VALUE = "null";

    public TestDBParameter() {
    }

    public void testNonSubstitutionVariable() {
        DBParameter parm = new DBParameter("-5:5010:f");
        assertFalse(parm.isValueASubstitutionVariable());
        assertFalse(parm.isResultParameter());
        assertEquals(parm.getParameterObjectType(), DBParameter.LONG_OBJECT_TYPE);
        assertEquals(parm.getParameterValue(), OBJ_5010);
        assertEquals(parm.getParameterObjectValue(), Long.parseLong(OBJ_5010));
    }

    public void testSubstitutionVariable() {
        DBParameter parm = new DBParameter("4:${SelectedContent.0}:0");
        assertTrue(parm.isValueASubstitutionVariable());
        assertFalse(parm.isResultParameter());
        assertEquals(parm.getParameterObjectType(), DBParameter.INTEGER_OBJECT_TYPE);
        assertEquals(parm.getParsedSubstitutionVariable(), "SelectedContent.0");
        assertEquals(parm.getParameterObjectValue(), null);
    }

    public void testResultParameter() {
        DBParameter parm = new DBParameter("-10:null:t");
        assertFalse(parm.isValueASubstitutionVariable());
        assertTrue(parm.isResultParameter());
        assertEquals(parm.getParameterObjectType(), DBParameter.ORACLE_CURSOR_OBJECT_TYPE);
        assertEquals(parm.getParameterValue(), NULL_VALUE);
        assertEquals(parm.getParameterObjectValue(), null);
    }
}
