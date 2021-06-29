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
package com.mckesson.eig.inuse.base.api;

import java.util.List;
import junit.framework.Assert;
import org.springframework.dao.DataIntegrityViolationException;

public class TestInUseException
extends junit.framework.TestCase {

    /**
     * This test case is for checking business validation exception scenario.
     * InUseException thrown will contain errorCode and errorData.
     */
    public void testExceptionWithErrorDataAndCode() {


        try {
            addErrorCodeAndData(1);
            fail("Service method checkEmpId()should have thrown InUseException");
        } catch (InUseException e) {

            assertEquals(InUseClientErrorCodes.INVALID_ID.toString(), e.getErrorCode());
            assertEquals(String.valueOf(1), e.getExtendedCode());
        }
    }

    /**
     * This test case is for validating api generated exception scenario.
     * InUseException thrown will contain api exception cause and errorCode.
     */
    public void testExceptionErrorCode() {

        try {
            addErrorCode();
            fail("Service method createEmployee should have thrown InUseException");
        } catch (InUseException e) {
            assertEquals(InUseClientErrorCodes.DATA_INTEGRITY_VIOLATION.toString().toString(),
                                e.getErrorCode());
        }
    }

    /**
     * This test case is for validating nested cause.
     * InUseException thrown will have nested cause and errorCodes.
     */
    public void testNestedException() {

        String[] clientErrorCodes = {InUseClientErrorCodes.INVALID_ID.toString(),
                                     InUseClientErrorCodes.DATA_INTEGRITY_VIOLATION.toString()};
        try {
            addNestedException();
            fail("Service method checkEmpDetails should have thrown nested InUseException");
        } catch (InUseException e) {

            @SuppressWarnings("unchecked") // not supported by 3rdParty API
            List<Throwable> nestedCause = e.getAllNestedCauses();
            for (int i = 0; i < nestedCause.size(); i++) {
                InUseException inUseException = (InUseException) nestedCause.get(i);
                Assert.assertEquals(clientErrorCodes[i], inUseException.getErrorCode());
            }
        }
    }

    /**
     * This test case is for validating nested cause.
     * InUseException thrown will have nested cause and errorCodes.
     */
    public void testNestedExceptionWithErrorData() {

        String[] clientErrorCodes = {InUseClientErrorCodes.INVALID_ID.toString(),
                                     InUseClientErrorCodes.DATA_INTEGRITY_VIOLATION.toString()};
        try {
            addNestedExceptionWithErrorData("1");
            Assert.fail("Service method checkEmpDetails should have thrown nested InUseException");
        } catch (InUseException e) {

            @SuppressWarnings("unchecked") // not supported by 3rdParty API
            List<Throwable> nestedCause = e.getAllNestedCauses();
            for (int i = 0; i < nestedCause.size(); i++) {
                InUseException inUseException = (InUseException) nestedCause.get(i);
                Assert.assertEquals(clientErrorCodes[i], inUseException.getErrorCode());
                Assert.assertEquals(String.valueOf(1), e.getExtendedCode());
            }
        }
    }

    /**
     * This methods throws a InUseException with error code and error data
     */
    private void addErrorCodeAndData(long id) {
            throw new InUseException(InUseClientErrorCodes.INVALID_ID, String.valueOf(id));
    }

    /**
     * This methods throws a InUseException with error code
     */
    private void addErrorCode() {

        try {
            throw new DataIntegrityViolationException("Write Exception");
        } catch (DataIntegrityViolationException e) {
            throw new InUseException(InUseClientErrorCodes.DATA_INTEGRITY_VIOLATION, "DB_001");
        }
    }

    /**
     * This methods throws a nested InUseException
     */
    private void addNestedException() {

        InUseException cause = new InUseException();
        cause = new InUseException(InUseClientErrorCodes.INVALID_ID);
        cause = (cause == null)
                ? new InUseException(InUseClientErrorCodes.INVALID_ID)
                : new InUseException(cause, InUseClientErrorCodes.DATA_INTEGRITY_VIOLATION);

        throw cause;

    }

    /**
     * This methods throws a InUseException with error code and data
     */
    private void addNestedExceptionWithErrorData(String data) {

        InUseException cause = new InUseException();
        cause = new InUseException(InUseClientErrorCodes.INVALID_ID, data);
        cause = (cause == null)
                ? new InUseException(InUseClientErrorCodes.INVALID_ID, data)
                : new InUseException(cause, InUseClientErrorCodes.DATA_INTEGRITY_VIOLATION, data);

        throw cause;
    }


}
