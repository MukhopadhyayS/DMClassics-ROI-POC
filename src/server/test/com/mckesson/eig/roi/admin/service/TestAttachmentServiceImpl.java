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

package com.mckesson.eig.roi.admin.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mckesson.eig.roi.admin.model.AttachmentLocation;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.supplementary.model.AttachmentDeleteStatus;
import com.mckesson.eig.roi.supplementary.model.AttachmentDeleteStatusList;
import com.mckesson.eig.roi.supplementary.model.AttachmentInfoList;
import com.mckesson.eig.roi.supplementary.model.AttachmentSequenceList;
import com.mckesson.eig.roi.supplementary.model.ROISupplementarityAttachment;
import com.mckesson.eig.roi.supplementary.model.ROISupplementarityAttachments;
import com.mckesson.eig.roi.supplementary.service.ROISupplementaryService;
import com.mckesson.eig.roi.test.BaseROITestCase;
import com.mckesson.eig.roi.utils.AccessFileLoader;
import com.mckesson.eig.roi.utils.DirectoryUtil;
import com.mckesson.eig.utility.io.IOUtilities;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * @author Pranav Amarasekaran
 * @date   Dec 16, 2010
 * @since  HPF 15.1 [ROI]; Dec 16, 2010
 */

public class TestAttachmentServiceImpl
extends BaseROITestCase {

    protected static final String ADMIN_SERVICE =
            "com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl";
    private static ROIAdminService _adminService;
    private static ROIAttachmentService _attachmentService;
    private static ROISupplementaryService _supplementalService;
    private static String _validAttachmentLocation = "\\\\eigdev772\\temp";
    private static String _attachmentLocationWithNoCreateAccess = "\\\\eigdev772\\nocreate";
    private static String _attachmentLocationWithNoDeleteAccess = "\\\\eigdev772\\nodelete";
    private static long _attachmentID = 0;
    private static String _attachmentLocation;
    private static long _supplementalId = 0;
    protected static final String SUPPLEMENTARY_SERVICE = "com.mckesson.eig.roi.supplementary.service.ROISupplementaryServiceImpl";

    public void initializeTestData()
    throws Exception {
        setUp();
        _adminService = (ROIAdminService)
                getService("com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl");

        _attachmentService = (ROIAttachmentService)
                getService("com.mckesson.eig.roi.admin.service.ROIAttachmentServiceImpl");

        _supplementalService = (ROISupplementaryService) getService(SUPPLEMENTARY_SERVICE);

        AttachmentLocation attachmentLoc = _adminService.retrieveAttachmentLocation();
        _attachmentID =  attachmentLoc.getAttachmentID();
        _attachmentLocation = attachmentLoc.getAttachmentLocation().trim();

    }

    private ROISupplementarityAttachment createNewSupplementarityAttachment(String mrn, String facility, String uuid) {
        ROISupplementarityAttachment att = new ROISupplementarityAttachment();
        att.setMrn(mrn);
        att.setFacility(facility);
        att.setEncounter("encounter");
        att.setType("Type");
        att.setDocFacility("AD");
        att.setUuid(uuid);
        return att;
    }

    private long createAttachment() {
        try {
            String tempPath = DirectoryUtil.getCacheDirectory();
            String uuid = UUID.randomUUID().toString();
            File f = AccessFileLoader.getFile(tempPath, uuid);
            String t = "test";
            IOUtilities.writeObjectAndClose(AccessFileLoader.getFileOutputStream(f, false), t);
            String mrn = "Mrn";
            String facility = "Facility";

            ROISupplementarityAttachments attachments =
                    _supplementalService.createSupplementarityAttachment(createNewSupplementarityAttachment(mrn, facility, uuid));

            long attId = 0;
            if (CollectionUtilities.hasContent(attachments.getAttachments())) {

                ROISupplementarityAttachment attachment = attachments.getAttachments().get(0);
                attId = attachment.getId();
            }
            return attId;
        } catch (Exception e) {
            fail("Create Attachment");
        }
        return -1;
    }

    public void testRetrieveAttachmentsInfo() {
         try {
            createAttachment();

            AttachmentInfoList list =   _attachmentService.getAllAttachments("Mrn", "Facility");
            assertNotNull(list);
            assertTrue(list.getAttachments().size() > 0);
        } catch (Exception e) {
            fail("Create Supplemental should not throw exception");
        }
    }

    public void testDeleteAttachment() {

        try {
            long id = createAttachment();
            AttachmentInfoList list =   _attachmentService.getAllAttachments("Mrn", "Facility");
            assertNotNull(list);
            List<ROISupplementarityAttachment> attachments = list.getAttachments();
            List<Long> ids = new ArrayList<Long>();
            for (ROISupplementarityAttachment attac : attachments) {
                ids.add(attac.getId());
            }
            AttachmentSequenceList attach = new AttachmentSequenceList(ids);
            AttachmentDeleteStatusList result = _attachmentService.deleteROIAttachments(attach);
            boolean success = false;
            for(AttachmentDeleteStatus status : result.getDeletedAttachmentList()) {
                if( status.getAttachmentId() == id) {
                    assertTrue(status.isDeleted());
                    success = true;
                }
            }
            assertTrue(success);
        } catch (ROIException e) {
            fail("Update Attachment operation failed");
            assertError(e, ROIClientErrorCodes.ATTACHMENT_OPERATION_FAILED);
        }catch (Exception e) {
            fail("testDeleteAttachment failed");
        }
    }

    public void testRetrieveAttachmentsInfoByEncounter() {

        try {
            createAttachment();
            AttachmentInfoList list = _attachmentService.retrieveAttachmentsInfo("Mrn", "Facility", "encounter");
            List<ROISupplementarityAttachment> attachments = list.getAttachments();
            assertNotNull(attachments);
            for (ROISupplementarityAttachment attac : attachments) {
                if (attac.getId() <= 0) {
                    fail("Attachment Id should be greater than Zero");
                }
            }

        } catch (ROIException e) {
            fail("Update Attachment operation failed");
            assertError(e, ROIClientErrorCodes.ATTACHMENT_OPERATION_FAILED);
        }
    }

    public void testRetrieveAttachmentsInfoWithInvalidData() {

        try {

           _attachmentService.getAllAttachments(null, "AD");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.ROI_ATTACHMENT_MRN_CANNOT_BE_NULL);
        }
    }

    public void testRetrieveAttachmentsInfoByEncWithInvalidData() {

        try {

            _attachmentService.retrieveAttachmentsInfo(null, "AD", "1000");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.ROI_ATTACHMENT_MRN_CANNOT_BE_NULL);
        }
    }

    private void cleanupAttachmentLocation(String attachmentLocation) throws IOException {

        File dir = AccessFileLoader.getFile(attachmentLocation);
        if (dir.exists()) {
            dir.delete();
        }
    }

    public void testUpdateAttachmentLocationWithNullValue() {

        try {
            AttachmentLocation loc = new AttachmentLocation(_attachmentID, null);
            _adminService.updateAttachmentLocation(loc);
            fail("Should not update a NULL attachment location");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.ATTACHMENT_LOCATION_NULL);
        }
    }

    public void testUpdateAttachmentLocationWithNoWritePrivelage() {

        try {

            AttachmentLocation loc = new AttachmentLocation(1L,
                                                            _attachmentLocationWithNoCreateAccess);
            _adminService.updateAttachmentLocation(loc);
            fail("Should not update the attachment location with no write privelage");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INSUFICIENT_PERMISION_ON_ATTACHMENT_FOLDER);
        }
    }

    public void testUpdateAttachmentLocationWithNoDeletePrivelage() {

        try {

            AttachmentLocation loc = new AttachmentLocation(1L,
                                                            _attachmentLocationWithNoDeleteAccess);
            _adminService.updateAttachmentLocation(loc);
            fail("Should not update the attachment location with no write privelage");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INSUFICIENT_PERMISION_ON_ATTACHMENT_FOLDER);
        }
    }

    protected Object getService(String serviceName) {
        return SpringUtilities.getInstance().getBeanFactory().getBean(serviceName);
    }

    protected String getServiceURL(String serviceMethod) {
        return "";
    }
}
