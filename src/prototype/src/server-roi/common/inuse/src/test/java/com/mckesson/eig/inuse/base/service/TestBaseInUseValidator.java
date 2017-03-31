/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.inuse.base.service;

import com.mckesson.eig.inuse.base.api.InUseClientErrorCodes;


/**
 * @author OFS
 * @date   Nov 11, 2008
 * @since  ROI HPF 13.1
 */
public class TestBaseInUseValidator
extends junit.framework.TestCase {

    private InUseClientErrorCodes _errorCode1 = InUseClientErrorCodes.RECORD_NOT_FOUND;
    private InUseClientErrorCodes _errorCode2 = InUseClientErrorCodes.RECORD_ALREADY_IN_USE;
    private InUseClientErrorCodes _errorCode3 = InUseClientErrorCodes.INVALID_APPLICATION_DATA;

    /**
     * This method check the addError() method
     */
    public void testAddError() {

        BaseInUseValidator val = new BaseInUseValidator();
        val.addError(_errorCode1);
        assertFalse(val.hasNoErrors());
    }

    /**
     * This method check the addError() method
     */
    public void testAddErrorMore() {

        BaseInUseValidator val = new BaseInUseValidator();
        val.addError(_errorCode1);
        val.addError(_errorCode2);
        val.addError(_errorCode1);
        assertFalse(val.hasNoErrors());
    }

    /**
     * This method checks addError(param1,param2)
     */
    public void testAddErrorCheck() {

        BaseInUseValidator val = new BaseInUseValidator();
        val.addError(_errorCode1, "_errorCode2");
        assertFalse(val.hasNoErrors());
    }

    /**
     * This method checks addError(param1,param2)
     */
    public void testAddErrorMoreCheck() {

        BaseInUseValidator val = new BaseInUseValidator();
        val.addError(_errorCode1, "_errorCode2");
        val.addError(_errorCode2, "_errorCode3");
        val.addError(_errorCode3, "_errorCode1");
        assertFalse(val.hasNoErrors());
    }

    /**
     * This method checks getException()
     */
    public void testGetException() {

        BaseInUseValidator val = new BaseInUseValidator();
        assertTrue(val.getException() == null);
    }

    /**
     * This method checks getException()
     */
    public void testGetExceptionWithoutNull() {

        BaseInUseValidator val = new BaseInUseValidator();
        val.addError(_errorCode1, "_errorCode2");
        val.addError(_errorCode2, "_errorCode3");
        val.addError(_errorCode3, "_errorCode1");
        assertTrue(val.getException() != null);
    }

    /**
     * This method checks the hasError()
     */
    public void testHasNoError() {

        BaseInUseValidator val = new BaseInUseValidator();
        val.addError(_errorCode1);
        assertEquals(false, val.hasNoErrors());
    }
}
