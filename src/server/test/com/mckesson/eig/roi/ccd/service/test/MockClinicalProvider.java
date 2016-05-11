/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2013 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.ccd.service.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.ccd.local.clinical.ClinicalProvider;
import com.mckesson.eig.roi.ccd.provider.CcdProvider;
import com.mckesson.eig.roi.ccd.provider.CcdProviderConstants.CcdFileType;
import com.mckesson.eig.roi.ccd.provider.model.CcdDocument;
import com.mckesson.eig.roi.muroioutbound.model.ExternalSourceDocument;

/**
 * @author OFS
 * @date   Jun 28, 2013
 * @since  Jun 28, 2013
 */
public class MockClinicalProvider
extends ClinicalProvider {

    public CcdProvider newProvider() {
        return new MockClinicalProvider();
    }

    @Override
    public void retrieveCcd(ExternalSourceDocument document)
            throws ROIException {

        try {

            for (CcdDocument doc : document.getCcdDocuments()) {

                if (CcdFileType.XML.toString().equalsIgnoreCase(doc.getType())) {

                    InputStream resourceAsStream = MockClinicalProvider.class.getResourceAsStream("ccd.xml");
                    writeFileToStream(doc.getOutputStream(), resourceAsStream);
                    resourceAsStream.close();

                } else if (CcdFileType.PDF.toString().equalsIgnoreCase(doc.getType())) {

                    InputStream resourceAsStream = MockClinicalProvider.class.getResourceAsStream("ccd.pdf");
                    writeFileToStream(doc.getOutputStream(), resourceAsStream);
                    resourceAsStream.close();
                }

            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void writeFileToStream(OutputStream outputStream, InputStream inputStream) {

        try {

            byte[] byteArray = new byte[512];
            while (inputStream.read(byteArray) != -1) {
                outputStream.write(byteArray);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public boolean testConfiguration(Map<String, String> values) {
        return true;
    }

    public boolean needPeristanceExternalSourceDocument() {
        return true;
    }

    @Override
    public Set<CcdFileType> getCcdFileType() {

        Set<CcdFileType> result = new HashSet<CcdFileType>();
//        result.add(CcdFileType.PDF);
        result.add(CcdFileType.XML);
        return result;
    }
}
