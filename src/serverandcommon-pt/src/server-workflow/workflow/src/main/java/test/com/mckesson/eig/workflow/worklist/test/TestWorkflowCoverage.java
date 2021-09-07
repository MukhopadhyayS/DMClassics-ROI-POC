/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.workflow.worklist.test;

import com.mckesson.eig.workflow.api.WorkflowEC;
import com.mckesson.eig.workflow.api.WorkflowException;
import com.mckesson.eig.workflow.worklist.api.WorklistEC;
import com.mckesson.eig.workflow.worklist.api.WorklistException;

/**
 * @author Pranav Amarasekaran
 * @date   Sep 27, 2007
 * @since  HECM 1.0; Sep 27, 2007
 */
public class TestWorkflowCoverage
extends  com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

    private static final String ERROR_CODE = "error.code";

    public void testWorkflowErrorConstants() {

        try {
            new WorkflowEC();
            fail("Should have thrown UnsupportedOperationException");
        } catch (Exception expected) {
            assertEquals(UnsupportedOperationException.class, expected.getClass());
        }
    }

    public void testWorklistErrorConstants() {

        try {
            new WorklistEC();
            fail("Should have thrown UnsupportedOperationException");
        } catch (Exception expected) {
            assertEquals(UnsupportedOperationException.class, expected.getClass());
        }
    }

    public void testWorkflowException() {

        WorkflowException e = new WorkflowException(new RuntimeException(), ERROR_CODE);
        assertEquals(RuntimeException.class, e.getExtendedCause().getClass());
        assertEquals(RuntimeException.class, e.getNestedCause().getClass());
        assertEquals(ERROR_CODE, e.getErrorCode());
//        assertNotNull("Should return the original cause", e.getCause());
//        assertEquals(RuntimeException.class, e.getCause().getClass());
    }

    public void testWorklistException() {

        WorkflowException e = new WorklistException(new RuntimeException());
        assertEquals(RuntimeException.class, e.getExtendedCause().getClass());
        assertEquals(RuntimeException.class, e.getNestedCause().getClass());
//        assertNotNull("Should return the original cause", e.getCause());
//        assertEquals(RuntimeException.class, e.getCause().getClass());
    }
}
