/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/

package com.mckesson.eig.roi.base.api;

import java.util.List;
import junit.framework.Assert;
import org.springframework.dao.DataIntegrityViolationException;

public class TestROIException
extends junit.framework.TestCase {

    /**
     * This test case is for checking business validation exception scenario.
     * ROIException thrown will contain errorCode and errorData.
     */
    public void testExceptionWithErrorDataAndCode() {


        try {
            addErrorCodeAndData(1);
            Assert.fail("Service method checkEmpId()should have thrown ROIException");
        } catch (ROIException e) {

            Assert.assertEquals(ROIClientErrorCodes.INVALID_ID.toString(), e.getErrorCode());
            Assert.assertEquals(String.valueOf(1), e.getExtendedCode());
        }
    }

    /**
     * This test case is for validating api generated exception scenario.
     * ROIException thrown will contain api exception cause and errorCode.
     */
    public void testExceptionErrorCode() {

        try {
            addErrorCode();
            Assert.fail("Service method createEmployee should have thrown ROIException");
        } catch (ROIException e) {
            Assert.assertEquals(ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION.toString().toString(),
                                e.getErrorCode());
        }
    }

    /**
     * This test case is for validating nested cause.
     * ROIException thrown will have nested cause and errorCodes.
     */
    public void testNestedException() {

        String[] clientErrorCodes = {ROIClientErrorCodes.INVALID_ID.toString(),
                                     ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION.toString()};
        try {
            addNestedException();
            Assert.fail("Service method checkEmpDetails should have thrown nested ROIException");
        } catch (ROIException e) {

            @SuppressWarnings("unchecked") // not supported by 3rdParty API
            List<Throwable> nestedCause = e.getAllNestedCauses();
            for (int i = 0; i < nestedCause.size(); i++) {
                ROIException roiException = (ROIException) nestedCause.get(i);
                Assert.assertEquals(clientErrorCodes[i], roiException.getErrorCode());
            }
        }
    }

    /**
     * This test case is for validating nested cause.
     * ROIException thrown will have nested cause and errorCodes.
     */
    public void testNestedExceptionWithErrorData() {

        String[] clientErrorCodes = {ROIClientErrorCodes.INVALID_ID.toString(),
                                     ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION.toString()};
        try {
            addNestedExceptionWithErrorData("1");
            Assert.fail("Service method checkEmpDetails should have thrown nested ROIException");
        } catch (ROIException e) {

            @SuppressWarnings("unchecked") // not supported by 3rdParty API
            List<Throwable> nestedCause = e.getAllNestedCauses();
            for (int i = 0; i < nestedCause.size(); i++) {
                ROIException roiException = (ROIException) nestedCause.get(i);
                Assert.assertEquals(clientErrorCodes[i], roiException.getErrorCode());
                Assert.assertEquals(String.valueOf(1), e.getExtendedCode());
            }
        }
    }

    /**
     * This methods throws a ROIException with error code and error data
     */
    private void addErrorCodeAndData(long id) {
            throw new ROIException(ROIClientErrorCodes.INVALID_ID, String.valueOf(id));
    }

    /**
     * This methods throws a ROIException with error code
     */
    private void addErrorCode() {

        try {
            throw new DataIntegrityViolationException("Write Exception");
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, "DB_001");
        }
    }

    /**
     * This methods throws a nested ROIException
     */
    private void addNestedException() {

        ROIException cause = new ROIException();
        cause = new ROIException(ROIClientErrorCodes.INVALID_ID);
        cause = (cause == null)
                ? new ROIException(ROIClientErrorCodes.INVALID_ID)
                : new ROIException(cause, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION);

        throw cause;

    }

    /**
     * This methods throws a ROIException with error code and data
     */
    private void addNestedExceptionWithErrorData(String data) {

        ROIException cause = new ROIException();
        cause = new ROIException(ROIClientErrorCodes.INVALID_ID, data);
        cause = (cause == null)
                ? new ROIException(ROIClientErrorCodes.INVALID_ID, data)
                : new ROIException(cause, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, data);

        throw cause;
    }


}
