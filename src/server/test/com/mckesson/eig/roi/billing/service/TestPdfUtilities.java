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

package com.mckesson.eig.roi.billing.service;

import java.util.Date;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.billing.letter.model.LetterData;
import com.mckesson.eig.roi.billing.xdocreport.service.DocXLetterGenerator;
import com.mckesson.eig.roi.test.BaseROITestCase;


/**
 *
 * @author OFS
 * @date   Apr 13, 2009
 * @since  HPF 13.1 [ROI]; Apr 3, 2009
 */
public class TestPdfUtilities
extends BaseROITestCase {

    public void initializeTestData()
    throws Exception {
    }

    /**
     * Test case to cover the concatenate method in PdfUtilities
     */
    public void testPdfConcatenate() {

        try {
            PdfUtilities.concatenate(null, null);
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.UNABLE_TO_CONCATENATE_PDF);
        }
    }

    /**
     * Test case to cover the encrypt method in PdfUtilities
     */
    public void testPdfEncrypt() {

        try {
            PdfUtilities.encrypt(null);
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.UNABLE_TO_ENCRYPT_PDF);
        }
    }

    public void testParseData() {

        Date date = LetterData.parseDate("InvalidDate");
        assertNull(date);
    }

    public void testRTFLetterGenerator() {

        try {

            DocXLetterGenerator rtfLetterGenerator = new DocXLetterGenerator();
            rtfLetterGenerator.generateLetter(rtfLetterGenerator, rtfLetterGenerator, null);
            fail("Generating Rtf letter with null file name is not permitted, but generated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.UNABLE_TO_GENERATE_RTF);
        }
    }

    public void testConvertToPdf() {

        try {

            String tempDir = System.getProperty("java.io.tmpdir");
            DocXLetterGenerator.convertToPDF(tempDir, tempDir);
            fail("Converting Rtf file to pdf with null file name is not permitted, but generated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.UNABLE_TO_CONVERT_TO_PDF);
        }
    }


    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }


}
