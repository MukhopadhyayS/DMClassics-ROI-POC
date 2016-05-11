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


import java.util.ArrayList;
import java.util.List;

import com.mckesson.eig.roi.admin.model.Designation;
import com.mckesson.eig.roi.admin.model.DocTypeAudit;
import com.mckesson.eig.roi.admin.model.DocTypeAuditList;
import com.mckesson.eig.roi.admin.model.DocTypeDesignations;
import com.mckesson.eig.roi.admin.model.MUDocTypeDto;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.test.BaseROITestCase;


/**
 * @author ganeshramr
 * @date    Apr 13, 2009
 * @since  HPF 13.1 [ROI]; May 12, 2008
 */

public class TestDocumentTypeImpl
extends BaseROITestCase {

    protected static final String  ADMIN_SERVICE =
        "com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl";

    private static String          _type;
    private static String           _type1;
    private static ROIAdminService _adminService;

    private static final long DISC_ID1  = 3;
    private static final long DISC_ID2 = 4;

    private static final long AUTH_ID1  = 5;
    private static long _codeSetId      = 2;

    public void initializeTestData()
    throws Exception {
        _type   = "disclosure";
        _type1 = "authorize";
        _adminService = (ROIAdminService) getService(ADMIN_SERVICE);
    }

    /**
     * This test case to test the designate document types
     */
    public void testCreatingDesignateDocumentTypes() {

        try {

            DocTypeDesignations forCreate = new DocTypeDesignations();
            List<Designation> desiList = new ArrayList<Designation>();

            Designation desi = new Designation();
            List<Long> ids = new ArrayList<Long>();
            ids.add(DISC_ID1);
            ids.add(DISC_ID2);
            desi.setDocTypeIds(ids);
            desi.setType(_type);
            desiList.add(desi);

            desi = new Designation();
            List<Long> ids1 = new ArrayList<Long>();
            ids1.add(AUTH_ID1);
            desi.setDocTypeIds(ids1);
            desi.setType(_type1);
            desiList.add(desi);

            // changes for mu doc type
            desi = new Designation();
            MUDocTypeDto muDocType1 = new MUDocTypeDto();
            muDocType1.setMuDocId(DISC_ID1);
            muDocType1.setMuDocName("Discharge Summary");

            List<MUDocTypeDto> list = new ArrayList<MUDocTypeDto>();
            list.add(0, muDocType1);

            MUDocTypeDto muDocType2 = new MUDocTypeDto();
            muDocType2.setMuDocId(DISC_ID2);
            muDocType2.setMuDocName("Discharge Summary");

            list.add(1, muDocType2);

            desi.setMuDocTypes(list);

            List<Long> ids3 = new ArrayList<Long>();
            ids3.add(DISC_ID1);
            ids3.add(DISC_ID2);
            desi.setDocTypeIds(ids);

            desi.setType("mu");
            desiList.add(desi);

            forCreate.setDesignation(desiList);

            _adminService.designateDocumentTypes(_codeSetId, forCreate, constructDocTypeAuditList());

           DocTypeDesignations designations = _adminService.retrieveDesignations(_codeSetId);
           assertTrue(designations.getDesignation().size() > 0);

        } catch (ROIException e) {
            fail("Creating designate document should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case to test the designate document types
     */
    public void testCreatingDesignateDocumentTypesWithAuthorization() {

        try {

            DocTypeDesignations forCreate = new DocTypeDesignations();
            List<Designation> desiList = new ArrayList<Designation>();
            Designation desi = new Designation();
            List<Long> ids1 = new ArrayList<Long>();
            ids1.add(AUTH_ID1);
            desi.setDocTypeIds(ids1);
            desi.setType(_type1);
            desiList.add(desi);

            forCreate.setDesignation(desiList);

            _adminService.designateDocumentTypes(_codeSetId, forCreate,constructDocTypeAuditList());
            DocTypeDesignations designations = _adminService.retrieveDesignations(_codeSetId);
            assertTrue(designations.getDesignation().size() > 0);

        } catch (ROIException e) {
            fail("Creating designate document should not thrown exception." + e.getErrorCode());
        }
    }

    public DocTypeAuditList constructDocTypeAuditList() {
        DocTypeAudit docTypeAudit = new DocTypeAudit();
        docTypeAudit.setCodeSetName("A");
        docTypeAudit.setDocType("Authorize");
        docTypeAudit.setFromValue("111 Tiff Datavault");
        docTypeAudit.setToValue("112 Tiff Datavault");
        List<DocTypeAudit> docTypeAuditList = new ArrayList<DocTypeAudit>();
        docTypeAuditList.add(docTypeAudit);

        DocTypeAuditList docAuditList = new DocTypeAuditList();
        docAuditList.setDocTypeAudit(docTypeAuditList);
        return docAuditList;
    }

    /**
     * This test case check the designate document types authorized doc have more than one
     * and verify if it returns the appropriate exception
     */
    public void testDesignateDocumentAuthorizationHavingMoreThanOne() {

        try {

            DocTypeDesignations forCreate = new DocTypeDesignations();
            List<Designation> desiList = new ArrayList<Designation>();
            Designation desi = new Designation();
            List<Long> ids1 = new ArrayList<Long>();
            ids1.add(AUTH_ID1);
            ids1.add(AUTH_ID1);
            desi.setDocTypeIds(ids1);
            desi.setType(_type1);
            desiList.add(desi);

            forCreate.setDesignation(desiList);
            DocTypeAuditList docTypeAuditLists = constructDocTypeAuditList();
            _adminService.designateDocumentTypes(_codeSetId, forCreate,docTypeAuditLists);
            fail("authorized doc having more than one is not permitted, but it accepted");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.AUTHORIZATION_DOC_TYPE_HAVE_MORE_THAN_ONE);
        }
    }

    /**
     * This test case to check the retrieve designation
     */
    public void testRetrieveDesignation() {

        try {

            DocTypeDesignations desi = _adminService.retrieveDesignations(_codeSetId);
            assertNotSame(0, desi.getDesignation().size());
        } catch (ROIException e) {
            fail("Retrieve designation should not throw exception" + e.getErrorCode());
        }
    }

    /**
     * Tests deleting the designated document types
     */
    public void testDeleteDesignateDocumentTypes() {

        try {

            DocTypeDesignations forCreate = new DocTypeDesignations();
            List<Designation> desiList = new ArrayList<Designation>();
            List<Long> ids1 = new ArrayList<Long>();

            Designation desi = new Designation();
            ids1.add(DISC_ID1);
            ids1.add(DISC_ID2);
            ids1.add(AUTH_ID1);
            desi.setDocTypeIds(ids1);
            desi.setType(_type);
            desiList.add(desi);

            desi = new Designation();
            desi.setDocTypeIds(null);
            desi.setType(_type1);
            desiList.add(desi);

            forCreate.setDesignation(desiList);
            DocTypeAuditList docTypeAuditLists = constructDocTypeAuditList();
            _adminService.designateDocumentTypes(_codeSetId, forCreate,docTypeAuditLists);
            DocTypeDesignations designations = _adminService.retrieveDesignations(_codeSetId);
            assertTrue(designations.getDesignation().size() == 1);

        } catch (ROIException e) {
            fail("Deleting designate document should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case update the document type
     */
    public void testUpdatingDesignatedocumentTypes() {

        try {

            Designation desi = new Designation();
            DocTypeDesignations forCreate = new DocTypeDesignations();
            List<Designation> desiList = new ArrayList<Designation>();
            List<Long> ids = new ArrayList <Long>();
            ids.add(DISC_ID1);
            ids.add(DISC_ID2);
            desi.setDocTypeIds(ids);
            desi.setType(_type);

            desiList.add(desi);

            desi = new Designation();
            desi.setType(_type1);
            desi.setDocTypeIds(null);
            desiList.add(desi);
            forCreate.setDesignation(desiList);
            DocTypeAuditList docTypeAuditLists = constructDocTypeAuditList();

            _adminService.designateDocumentTypes(_codeSetId, forCreate,docTypeAuditLists);

            DocTypeDesignations forUpdate = new DocTypeDesignations();
            List<Designation> desiList1 = new ArrayList<Designation>();

            List<Long> ids1 = new ArrayList <Long>();
            ids1.add(DISC_ID1);
            ids1.add(DISC_ID2);
            ids1.add(AUTH_ID1);
            desi.setDocTypeIds(ids1);
            desi.setType(_type);

            desiList1.add(desi);
            desi = new Designation();
            desi.setType(_type1);
            desi.setDocTypeIds(null);
            desiList1.add(desi);

            forUpdate.setDesignation(desiList1);

            _adminService.designateDocumentTypes(_codeSetId, forUpdate,constructDocTypeAuditList());

            DocTypeDesignations list = _adminService.retrieveDesignations(_codeSetId);
            assertTrue(list.getDesignation().size() > 0);
            assertNotNull(list);
        } catch (ROIException e) {
            fail("Updating designate document should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case to test the designate document types
     */
    public void testCreatingDesignateDocumentTypesWithNullUser() {

        try {

            DocTypeDesignations forCreate = new DocTypeDesignations();
            List<Designation> desiList = new ArrayList<Designation>();

            Designation desi = new Designation();
            List<Long> ids = new ArrayList<Long>();
            ids.add(DISC_ID1);
            ids.add(DISC_ID2);
            desi.setDocTypeIds(ids);
            desi.setType(_type);
            desiList.add(desi);

            desi = new Designation();
            List<Long> ids1 = new ArrayList<Long>();
            ids1.add(AUTH_ID1);
            desi.setDocTypeIds(ids1);
            desi.setType(_type1);
            desiList.add(desi);

            forCreate.setDesignation(desiList);

            initSession(null);
            DocTypeAuditList docTypeAuditLists = constructDocTypeAuditList();
            _adminService.designateDocumentTypes(_codeSetId, forCreate,docTypeAuditLists);
            fail("Designate document types with null user is not permitted, but it accepted");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.DISCLOSURE_DOCUMENT_OPERATION_FAILED);
        }
    }

    /**
     * Test case to retrieve docIds by designation as disclosure
     */
    public void testRetrieveDocIdsByDesignation() {

        try {

            Designation designation = _adminService.retrieveDocTypeIdsByDesignation("disclosure");
            assertNotNull(designation);
        } catch (ROIException e) {
            fail("Retrieve DocumentType Ids by designation should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * Test case to retrieve docIds by designation as disclosure
     */
    public void testRetrieveDocIdsByDesignationAsNull() {

        try {

            Designation designation = _adminService.retrieveDocTypeIdsByDesignation(null);
            fail("Retrieve DocIds by null designation is not permitted, but accepted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.DESIGNATION_FOR_DOCUMENT_TYPE_IS_NULL);
        }
    }
    /**
     * Test case to test Retrieval of MU DOC Types
     *
     */
    public void testRetrieveMUDocTypes() {

        try {
            _adminService.retrieveMUDocTypes();

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.RETRIEVAL_MUDOCTYPES);
        }
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }
}
