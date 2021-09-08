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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.ccd.model.CcdConvertValue;
import com.mckesson.eig.roi.ccd.provider.local.CcdSourceConfigDto;
import com.mckesson.eig.roi.ccd.provider.local.CcdSourceDto;
import com.mckesson.eig.roi.ccd.provider.local.CcdSourceDtoList;
import com.mckesson.eig.roi.ccd.provider.local.RetrieveCCDDto;
import com.mckesson.eig.roi.ccd.provider.local.RetrieveCCDDtoList;
import com.mckesson.eig.roi.ccd.provider.model.AssigningAuthorityModel;
import com.mckesson.eig.roi.ccd.provider.model.CcdDocument;
import com.mckesson.eig.roi.ccd.provider.model.CcdDocumentList;
import com.mckesson.eig.roi.ccd.provider.model.CcdSourceConfigModel;
import com.mckesson.eig.roi.ccd.provider.model.CcdSourceModel;
import com.mckesson.eig.roi.ccd.provider.model.ExtFacilityMappingModel;
import com.mckesson.eig.roi.test.BaseROITestCase;
import com.mckesson.eig.roi.utils.AccessFileLoader;
import com.mckesson.eig.utility.util.CollectionUtilities;

/**
 * @author OFS
 * @date   Jun 4, 2013
 * @since  Jun 4, 2013
 */

public class TestCcdProviderModel
extends BaseROITestCase {

    protected static final String FACILITY_CODE = "TestFacility";
    protected static final String GPI = "TestGPI";
    protected static final String DOC_TYPE = "Test";
    protected static final String FILE_NAME = "Sample.rtf";
    protected static final Date SAMPLE_DATE = new Date("10/12/12");

    public void testAssigningAuthorityModel() {

        AssigningAuthorityModel assignedAuth = new AssigningAuthorityModel();

        assignedAuth.setFacCode(FACILITY_CODE);
        assignedAuth.setGpi(GPI);
        assignedAuth.setHostFacilityCode(FACILITY_CODE);
        assignedAuth.setHostSendingSystem("Test");
        assignedAuth.setMrn("TestM001");

        assertEquals(FACILITY_CODE, assignedAuth.getFacCode());
        assertEquals(GPI, assignedAuth.getGpi());
        assertEquals(FACILITY_CODE, assignedAuth.getHostFacilityCode());
        assertEquals("Test", assignedAuth.getHostSendingSystem());
        assertEquals("TestM001", assignedAuth.getMrn());

    }

    public void testCcdDocumentModel() {

        try {

            CcdDocument ccdDoc = new CcdDocument();

            ccdDoc.setDocumentType(DOC_TYPE);
            ccdDoc.setFileName(FILE_NAME);
            ccdDoc.setPageNumber(1);
            ccdDoc.setType("Test");
            ccdDoc.setReceivedDate(SAMPLE_DATE);
            ccdDoc.setServiceDate(SAMPLE_DATE);

            assertEquals(DOC_TYPE, ccdDoc.getDocumentType());
            assertEquals(FILE_NAME, ccdDoc.getFileName());
            assertEquals(1, ccdDoc.getPageNumber());
            assertEquals("Test", ccdDoc.getType());
            assertEquals(SAMPLE_DATE, ccdDoc.getReceivedDate());
            assertEquals(SAMPLE_DATE, ccdDoc.getServiceDate());

            FileOutputStream fileOut = AccessFileLoader.getFileOutputStream(FILE_NAME);
            ccdDoc.setOutputStream(fileOut);

            new CcdDocument(FILE_NAME, ccdDoc.getOutputStream(), DOC_TYPE, DOC_TYPE);

            ccdDoc.closeOutputStream();

            CcdDocumentList ccdDocList = new CcdDocumentList();
            List<CcdDocument> ccdDocs = new ArrayList<CcdDocument>();
            ccdDocs.add(ccdDoc);
            new CcdDocumentList(ccdDocs);
            ccdDocList.setCcdDocuments(ccdDocs);
            assertNotNull(ccdDocList.getCcdDocuments());

        } catch (Exception e) {
            fail("Testing CCD Document model operation failed");
        }
    }

    public void testCcdSourceConfigModel() {

        CcdSourceConfigModel ccdSrcConfig = constructCCDSourceConfigModel();

        assertEquals(1, ccdSrcConfig.getId());
        assertEquals(1, ccdSrcConfig.getSourceId());
        assertEquals(1, ccdSrcConfig.getCreatedBy());
        assertEquals(0, ccdSrcConfig.getModifiedBy());
        assertEquals(0, ccdSrcConfig.getRecordVersion());
        assertEquals("Test", ccdSrcConfig.getConfigKey());
        assertEquals("Testing", ccdSrcConfig.getConfigValue());
        assertEquals("For Testing", ccdSrcConfig.getDescription());
        assertEquals(SAMPLE_DATE, ccdSrcConfig.getCreatedDate());
        assertEquals(SAMPLE_DATE, ccdSrcConfig.getModifiedDate());

    }

    private CcdSourceConfigModel constructCCDSourceConfigModel() {
        CcdSourceConfigModel ccdSrcConfig = new CcdSourceConfigModel();

        ccdSrcConfig.setId(1);
        ccdSrcConfig.setSourceId(1);
        ccdSrcConfig.setConfigKey("Test");
        ccdSrcConfig.setConfigValue("Testing");
        ccdSrcConfig.setDescription("For Testing");
        ccdSrcConfig.setCreatedBy(1);
        ccdSrcConfig.setCreatedDate(SAMPLE_DATE);
        ccdSrcConfig.setModifiedDate(SAMPLE_DATE);
        ccdSrcConfig.setModifiedBy(0);
        ccdSrcConfig.setRecordVersion(0);
        return ccdSrcConfig;
    }

    public void testExtFacilityMappingModel() {

        ExtFacilityMappingModel extFac = new ExtFacilityMappingModel();

        extFac.setId(1);
        extFac.setFacCode(FACILITY_CODE);
        extFac.setSourceId(1);
        extFac.setDescription("Testing");
        extFac.setCreatedBy(0);
        extFac.setModifiedBy(0);
        extFac.setCreatedDate(SAMPLE_DATE);
        extFac.setModifiedDate(SAMPLE_DATE);
        extFac.setRecordVersion(1);

        assertEquals(1, extFac.getId());
        assertEquals(1, extFac.getRecordVersion());
        assertEquals(FACILITY_CODE, extFac.getFacCode());
        assertEquals(1, extFac.getSourceId());
        assertEquals("Testing", extFac.getDescription());
        assertEquals(0, extFac.getCreatedBy());
        assertEquals(0, extFac.getModifiedBy());
        assertEquals(SAMPLE_DATE, extFac.getModifiedDate());
        assertEquals(SAMPLE_DATE, extFac.getCreatedDate());

    }

    public void testCcdSourceModel() {

        CcdSourceModel ccdSrcModel = new CcdSourceModel();

        CcdSourceConfigModel ccdSrcConfig = constructCCDSourceConfigModel();

        ccdSrcModel.setId(1);
        ccdSrcModel.setSourceName(FILE_NAME);
        ccdSrcModel.setProviderName("Testing");
        ccdSrcModel.setDescription("Testing");
        ccdSrcModel.setExtType("Test");
        ccdSrcModel.setModifiedBy(0);
        ccdSrcModel.setRecordVersion(0);
        ccdSrcModel.setCreatedBy(1);
        ccdSrcModel.setCreatedDate(SAMPLE_DATE);
        ccdSrcModel.setModifiedDate(SAMPLE_DATE);

        List<CcdSourceConfigModel> ccdProviderConfigModel = new ArrayList<CcdSourceConfigModel>();
        ccdProviderConfigModel.add(ccdSrcConfig);

        ccdSrcModel.setCcdProviderConfigModel(ccdProviderConfigModel);

        assertEquals(1, ccdSrcModel.getId());
        assertEquals(FILE_NAME, ccdSrcModel.getSourceName());
        assertEquals("Testing", ccdSrcModel.getProviderName());
        assertEquals("Testing", ccdSrcModel.getDescription());
        assertEquals("Test", ccdSrcModel.getExtType());
        assertEquals(0, ccdSrcModel.getModifiedBy());
        assertEquals(1, ccdSrcModel.getCreatedBy());
        assertEquals(0, ccdSrcModel.getRecordVersion());
        assertEquals(SAMPLE_DATE, ccdSrcModel.getCreatedDate());
        assertEquals(SAMPLE_DATE, ccdSrcModel.getModifiedDate());
        assertNotNull(ccdSrcModel.getCcdProviderConfigModel());
    }

    public void testRetrieveCCDDtoList() {

        try {

            RetrieveCCDDtoList ccdDTOList = new RetrieveCCDDtoList();
            RetrieveCCDDto ccdDTO = new RetrieveCCDDto();
            ccdDTO.setRetrieveCCDKey("1");
            ccdDTO.setRetrieveCCDValue("CCD");
            List<RetrieveCCDDto> list = new ArrayList<RetrieveCCDDto>();
            list.add(ccdDTO);
            ccdDTOList = new RetrieveCCDDtoList(list);
            List<RetrieveCCDDto> paramsList = ccdDTOList.getRetrieveParameters();

            if (!CollectionUtilities.isEmpty(paramsList)) {
                assertEquals("1", paramsList.get(0).getRetrieveCCDKey());
                assertEquals("CCD", paramsList.get(0).getRetrieveCCDValue());
            }

        } catch (Exception e) {
           fail("RetrieveCCDDtoList should not thrown exception. " + e.getMessage());
        }
    }

    public void testCcdSourceDtoList() {

        try {
            CcdSourceDtoList ccdDTOList = new CcdSourceDtoList();
            CcdSourceDto ccdDTO = new CcdSourceDto();
            ccdDTO.setSourceId(100);
            ccdDTO.setSourceName("Source Name");
            ccdDTO.setProviderName("Provider Name");
            ccdDTO.setDescription("Description");

            CcdSourceConfigDto configDto = new CcdSourceConfigDto();
            configDto.setConfigKey("C1");
            configDto.setConfigValue("CValue");
            configDto.setConfigLabel("CLabel");
            List<CcdSourceConfigDto> configList = new ArrayList<CcdSourceConfigDto>();
            configList.add(configDto);

            ccdDTO.setCcdSourceConfigDto(configList);

            List<CcdSourceDto> ccdList = new ArrayList<CcdSourceDto>();
            ccdList.add(ccdDTO);

            ccdDTOList = new CcdSourceDtoList(ccdList);

            List<CcdSourceDto> ccdValues = ccdDTOList.getCcdSourceDtos();

            if (!CollectionUtilities.isEmpty(ccdValues)) {

                assertEquals(100, ccdValues.get(0).getSourceId());
                assertEquals("Source Name", ccdValues.get(0).getSourceName());
                assertEquals("Provider Name", ccdValues.get(0).getProviderName());
                assertEquals("Description", ccdValues.get(0).getDescription());

                if (!CollectionUtilities.isEmpty(ccdValues.get(0).getCcdSourceConfigDto())) {
                    assertEquals("C1", ccdValues.get(0).getCcdSourceConfigDto().get(0).getConfigKey());
                    assertEquals("CValue", ccdValues.get(0).getCcdSourceConfigDto().get(0).getConfigValue());
                    assertEquals("CLabel", ccdValues.get(0).getCcdSourceConfigDto().get(0).getConfigLabel());
                }

            }

        } catch (Exception e) {
           fail("CcdSourceDtoList should not thrown exception. " + e.getMessage());
        }
    }

    public void testCcdConvertValue() {

        CcdConvertValue ccdconvertValue =  new CcdConvertValue("Test", 0, "Type");
        CcdConvertValue ccdconvert=  new CcdConvertValue();
        ccdconvertValue.setFileName("Test");
        ccdconvertValue.setPageNumber(0);
        ccdconvertValue.setType("Type");

        assertEquals(ccdconvertValue.getFileName(), "Test");
        assertEquals(ccdconvertValue.getPageNumber(), 0);
        assertEquals(ccdconvertValue.getType(), "Type");

    }
    @Override
    protected String getServiceURL(String serviceMethod) {
        // TODO Auto-generated method stub
        return null;
    }


}
