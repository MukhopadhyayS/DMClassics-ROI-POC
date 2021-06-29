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

package com.mckesson.eig.roi.base.service;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;


/**
 * @author manikandans
 * @date   Aug 12, 2008
 * @since  HPF 13.1 [ROI]; Apr 8, 2008
 */
public class TestBaseROIValidator
extends junit.framework.TestCase {

    private ROIClientErrorCodes _errorCode1 =
                                ROIClientErrorCodes.MEDIA_TYPE_SEED_DATA_NAME_IS_BEING_EDITED;
    private ROIClientErrorCodes _errorCode2 = ROIClientErrorCodes.MEDIA_TYPE_NAME_IS_BLANK;
    private ROIClientErrorCodes _errorCode3 =
                                ROIClientErrorCodes.MEDIA_TYPE_SEED_DATA_IS_BEING_DELETED;

    /**
     * This method check the addError() method
     */
    public void testAddError() {

        BaseROIValidator val = new BaseROIValidator();
        val.addError(_errorCode1);
        assertFalse(val.hasNoErrors());
    }

    /**
     * This method check the addError() method
     */
    public void testAddErrorMore() {

        BaseROIValidator val = new BaseROIValidator();
        val.addError(_errorCode1);
        val.addError(_errorCode2);
        val.addError(_errorCode1);
        assertFalse(val.hasNoErrors());
    }

    /**
     * This method checks addError(param1,param2)
     */
    public void testAddErrorCheck() {

        BaseROIValidator val = new BaseROIValidator();
        val.addError(_errorCode1, "_errorCode2");
        assertFalse(val.hasNoErrors());
    }

    /**
     * This method checks addError(param1,param2)
     */
    public void testAddErrorMoreCheck() {

        BaseROIValidator val = new BaseROIValidator();
        val.addError(_errorCode1, "_errorCode2");
        val.addError(_errorCode2, "_errorCode3");
        val.addError(_errorCode3, "_errorCode1");
        assertFalse(val.hasNoErrors());
    }

    /**
     * This method checks getException()
     */
    public void testGetException() {

        BaseROIValidator val = new BaseROIValidator();
        assertTrue(val.getException() == null);
    }

    /**
     * This method checks getException()
     */
    public void testGetExceptionWithoutNull() {

        BaseROIValidator val = new BaseROIValidator();
        val.addError(_errorCode1, "_errorCode2");
        val.addError(_errorCode2, "_errorCode3");
        val.addError(_errorCode3, "_errorCode1");
        assertTrue(val.getException() != null);
    }

    /**
     * This method checks the hasError()
     */
    public void testHasNoError() {

        BaseROIValidator val = new BaseROIValidator();
        val.addError(_errorCode1);
        assertEquals(false, val.hasNoErrors());
    }

    /**
     * Test case to cover the validateFields method in BaseROIValidator
     */
    public void testvalidateFields() {

        try {

            BaseROIValidator val = new BaseROIValidator();
            val.validateFields(null);
        } catch (ROIException e) {
            assertNotNull(e);
        }

    }
}
