/*
 * Copyright 2012 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.roi.thirdparty.licenses;

import com.mckesson.dm.core.common.logging.OCLogger;

public class AsposeLicenseImpl implements ThirdPartyLicenses {

    private static final OCLogger LOGGER = new OCLogger(AsposeLicenseImpl.class);

    public AsposeLicenseImpl() {
        super();
        setLicense();
    }

    @Override
    public void setLicense() {
        try {
            com.aspose.words.License license = new  com.aspose.words.License();
            LOGGER.info("Setting Aspose License for Word");
            license.setLicense("Aspose.Total.Java.lic");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Aspose License for Word Failed", e);
        }
//        try {
//            com.aspose.pdf.License pdfLicense = new com.aspose.pdf.License();
//            LOGGER.info("Setting Aspose License for PDF");
//            pdfLicense.setLicense("Aspose.Total.Java.lic");
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("Aspose License for PDF Failed", e);
//        }
    }

}
