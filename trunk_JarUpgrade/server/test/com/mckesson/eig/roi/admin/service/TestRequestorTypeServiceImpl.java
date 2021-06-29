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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mckesson.eig.roi.admin.model.BillingTemplate;
import com.mckesson.eig.roi.admin.model.BillingTemplatesList;
import com.mckesson.eig.roi.admin.model.FeeType;
import com.mckesson.eig.roi.admin.model.RelatedBillingTemplate;
import com.mckesson.eig.roi.admin.model.RelatedBillingTier;
import com.mckesson.eig.roi.admin.model.RelatedFeeType;
import com.mckesson.eig.roi.admin.model.RequestorType;
import com.mckesson.eig.roi.admin.model.RequestorTypesList;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.model.Address;
import com.mckesson.eig.roi.requestor.model.Requestor;
import com.mckesson.eig.roi.requestor.service.RequestorService;
import com.mckesson.eig.roi.test.BaseROITestCase;
import com.mckesson.eig.utility.util.DateUtilities;



/**
 * @author ranjithr
 * @date   Jun 2, 2009
 * @since  HPF 13.1 [ROI]; May 5, 2008
 */
public class TestRequestorTypeServiceImpl
extends BaseROITestCase {

    protected static final String  ADMIN_SERVICE =
        "com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl";

    protected static final String  AD_SERVICE =
        "com.mckesson.eig.roi.admin.service.BillingAdminServiceImpl";

    protected static final String REQUESTOR_SERVICE =
        "com.mckesson.eig.roi.requestor.service.RequestorServiceImpl";

    private static final int CHILD_COUNT = 3;
    private static ROIAdminService       _adminService;
    private static BillingAdminService   _service;
    private static String                _requestorTypeName;
    private static String               _invalidRequestorTypeName;
    private static String                _requstorTypeDesc;
    private static String                _rv;
    private static String                _rvDesc;
    private static BillingTemplatesList  _btList;
    private static Set<RelatedBillingTemplate> _rtBTemplate;
    private static Set<RelatedBillingTier> _rtBTier;
    private static RequestorService     _requestorService;

    private static int    _requestorCount = 1;
    private static Date   _validDob;

    private static long _feeTypeId;
    private static String _billingTemplateName;
    private static Set<RelatedFeeType> _btfSet;

    private static final int HPF_BILLINGTIER_ID = -1;
    private static final int NON_HPF_BILLINGTIER_ID = -2;

    public void initializeTestData() throws Exception {

        long seed = System.nanoTime();
        _requestorTypeName = "Name" + seed;
        _billingTemplateName = "BTName" + seed;
        _invalidRequestorTypeName = "!@(&";
        _requstorTypeDesc = "RequestorDesc" + seed;
        _rv   = "All Documents";
        _rvDesc = "All Documents";
        _adminService = (ROIAdminService) getService(ADMIN_SERVICE);
        _service = (BillingAdminService) getService(AD_SERVICE);
        createFeeTypeForTesting();
        createBillingTemplate();
        createBillingTemplate();
        createBillingTemplate();
        _btList = _service.retrieveAllBillingTemplates(true);
        _validDob = DateUtilities.parseDate(new SimpleDateFormat(ROIConstants
                                                                 .ROI_DATE_FORMAT),
                                                                 "10/09/2008");
        _requestorService = (RequestorService) getService(REQUESTOR_SERVICE);

    }

    /**
     * This test case for createRequestorType and verify if it returns the newly created
     * requestorTypeId
     */
    public void testCreateRequestorType() {

        try {

            RequestorType rt = _adminService.createRequestorType(baseCreation());
            RequestorType rType = _adminService.retrieveRequestorType(rt.getId());
            assertNotNull(rType);
            assertEquals(rt.getId(), rType.getId());
            assertNotSame("Created requestor type id should be greater than zero", 0, rt.getId());
        } catch (ROIException e) {
            fail("Creating requestor type should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case for createRequestorType and verify if it returns the newly created
     * requestorTypeId
     */
    public void testCreateRequestorTypeWithInvalidData() {

        try {
            RequestorType rt = baseCreation();
            rt.setName(appendString(_invalidRequestorTypeName));
            RequestorType reqType = _adminService.createRequestorType(rt);
            fail("Creating requestor type with Invalid Data not allowed.");
        } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_NAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case for createRequestorType with null value
     */
    public void testCreateRequestorTypeWithNull() {

        try {

            RequestorType rt = _adminService.createRequestorType(null);
            fail("Creating requestor type with null value is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_OPERATION_FAILED);
        }
    }

    /**
     * This test case for createRequestorTypeWithNameBlank and verify if it returns
     * appropriate Exception
     */
    public void testCreateRequestorTypeWithNameBlank() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(" ");
            _adminService.createRequestorType(rt);
            fail("Creating the requestor type with name blank is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_NAME_IS_BLANK);
        }
    }

    /**
     * This test case for createRequestorTypeWithNameNull and verify if it returns
     * appropriate Exception
     */
    public void testCreateRequestorTypeWithNameNull() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(null);
            _adminService.createRequestorType(rt);
            fail("Creating the requestor type with name null is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_NAME_IS_BLANK);
        }
    }

    /**
     * This test case for createRequestorTypeWithSessionNull and verify if it returns
     * appropriate Exception
     */
    public void testCreateRequestorTypeWithSessionNull() {

        try {

            initSession(null);
            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName + "null");
            _adminService.createRequestorType(rt);
            fail("Creating the requestor type with null user is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_OPERATION_FAILED);
        }
    }

    /**
     * This test case for createRequestorTypeWithDuplicateName and verify if it returns
     * appropriate Exception
     */
    public void testCreateRequestorTypeWithDuplicateName() {

        try {

            initSession();
            RequestorType rt = _adminService.createRequestorType(baseCreation());
            rt.setName(_requestorTypeName + "forduplicate");
            RequestorType rType = _adminService.createRequestorType(rt);
            RequestorType reqType = _adminService.retrieveRequestorType(rType.getId());

            RequestorType rt1 = baseCreation();
            rt1.setName(reqType.getName());
            _adminService.createRequestorType(rt1);
            fail("Creating requestor type with duplicate name is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * This test case for createRequestorTypeMoreNameLength and verify if it returns
     * appropriate Exception
     */
    public void testCreateRequestorTypeWithNameLengthExceeds() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName  + new StringBuffer()
            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
            .toString());
            _adminService.createRequestorType(rt);
            fail("Creating requestor type with more name length is not permitted, but created");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_NAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case for createRequestorTypeWithoutElectronic and verify if it returns
     * appropriate Exception
     */
    public void testCreateRequestorTypeWithoutElectronic() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName + "Elec");

            _rtBTier = new HashSet<RelatedBillingTier>();
            RelatedBillingTier hpfBillingTier = new RelatedBillingTier();
            hpfBillingTier.setBillingTierId(NON_HPF_BILLINGTIER_ID);
            hpfBillingTier.setIsHPF(true);
            hpfBillingTier.setIsHECM(false);
            hpfBillingTier.setIsCEVA(false);
            hpfBillingTier.setIsSupplemental(false);

            RelatedBillingTier nonHpfBillingTier = new RelatedBillingTier();
            nonHpfBillingTier.setBillingTierId(NON_HPF_BILLINGTIER_ID);
            nonHpfBillingTier.setIsHPF(false);
            nonHpfBillingTier.setIsHECM(false);
            nonHpfBillingTier.setIsCEVA(false);
            nonHpfBillingTier.setIsSupplemental(true);

            _rtBTier.add(hpfBillingTier);
            _rtBTier.add(nonHpfBillingTier);

            rt.setRelatedBillingTier(_rtBTier);

            _adminService.createRequestorType(rt);
            fail("Creating requestor type without electronic is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_BTIER_NAME_IS_NOT_ELECTRONIC);
        }
    }

    /**
     * This test case for createRequestorTypeWithbillingTier and verify if it returns
     * appropriate Exception
     */
    public void testCreateRequestorTypeWithoutBillingTier() {

        try {

            RequestorType rt = baseCreation();
            rt.setRelatedBillingTier(null);
            _adminService.createRequestorType(rt);
            fail("Creating requestor type without billingtier is not permitted, but created");
        } catch (ROIException e) {
            assertError(e,
                        ROIClientErrorCodes.REQUESTOR_TYPE_MUST_HAVE_HPF_AND_NON_HPF_BILLING_TIER);

        }
    }

    /**
     * This test case for createRequestorTypeWithoutNonHpfbillingTier and verify if it returns
     * appropriate Exception
     */
    public void testCreateRequestorTypeWithoutNonHpfBillingTier() {

        try {

            RequestorType rt = baseCreation();

            _rtBTier = new HashSet<RelatedBillingTier>();

            RelatedBillingTier hpfBillingTier = new RelatedBillingTier();
            hpfBillingTier.setBillingTierId(HPF_BILLINGTIER_ID);
            hpfBillingTier.setIsHPF(true);
            hpfBillingTier.setIsHECM(false);
            hpfBillingTier.setIsCEVA(false);
            hpfBillingTier.setIsSupplemental(false);

            _rtBTier.add(hpfBillingTier);
            rt.setRelatedBillingTier(_rtBTier);

            _adminService.createRequestorType(rt);
            fail("Creating requestor type without nonhpf billingtier is not permitted,but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_MUST_HAVE_ONE_NON_HPF_BILLING_TIER);
        }
    }

    /**
     * This test case for createRequestorTypeWithoutHpfbillingTier and verify if it returns
     * appropriate Exception
     */
    public void testCreateRequestorTypeWithoutHpfBillingTier() {

        try {

            RequestorType rt = baseCreation();

            _rtBTier = new HashSet<RelatedBillingTier>();

            RelatedBillingTier nonHpfBillingTier = new RelatedBillingTier();
            nonHpfBillingTier.setBillingTierId(NON_HPF_BILLINGTIER_ID);
            nonHpfBillingTier.setIsHPF(false);
            nonHpfBillingTier.setIsHECM(false);
            nonHpfBillingTier.setIsCEVA(false);
            nonHpfBillingTier.setIsSupplemental(true);

            _rtBTier.add(nonHpfBillingTier);
            rt.setRelatedBillingTier(_rtBTier);

            _adminService.createRequestorType(rt);
            fail("Creating requestor type without nonhpf billingtier is not permitted,but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_MUST_HAVE_ONE_HPF_BILLING_TIER);
        }
    }


    /**
     * This test case for createRequestorTypeWithoutbillingTier and verify if it returns
     * appropriate Exception
     */
    public void testCreateRequestorTypeWithoutRecordView() {

        try {

            RequestorType rt = baseCreation();
            rt.setRv("");
            _adminService.createRequestorType(rt);
            fail("Creating requestor type without recordview is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_MUST_HAVE_ONE_RECORD_VIEW);
        }
    }

    /**
     * This test case for createRequestorTypeWithoutbillingTemplate
     */
    public void testCreateRequestorTypeWithoutBillingTemplate() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName + "bTemplate");

            _rtBTemplate = new HashSet <RelatedBillingTemplate>();
            rt.setRelatedBillingTemplate(_rtBTemplate);

            RequestorType rType = _adminService.createRequestorType(rt);
            RequestorType reqType = _adminService.retrieveRequestorType(rType.getId());
            assertNotSame("Created requestortype id should be greater than zero", 0, rType.getId());
            assertEquals(rType.getId(), reqType.getId());
        } catch (ROIException e) {
            fail("Create requestortype without billing template should not thrown exception."
                    + e.getErrorCode());
        }
    }

    /**
     * This test case for createRequestorTypeWithoutbillingTemplate null
     */
    public void testCreateRequestorTypeBillingTemplateAsNull() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName + "bT");
            rt.setRelatedBillingTemplate(null);

            RequestorType rType = _adminService.createRequestorType(rt);
            RequestorType reqType = _adminService.retrieveRequestorType(rType.getId());
            assertEquals(rType.getId(), reqType.getId());
            assertNotSame("Created requestortype id should be greater than zero", 0, rType.getId());
        } catch (ROIException e) {
            fail("Creating requestortype with billing template is null should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * This test case for createRequestorTypeWithMorebillingTemplate
     */
    public void testCreateRequestorTypeWithMoreBillingTemplate() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName + "More");
            _rtBTemplate = new HashSet <RelatedBillingTemplate>();

            for (int count = 0; (count < CHILD_COUNT)
            && (count < _btList.getBillingTemplates().size()); count++) {

                BillingTemplate bTemplate = _btList.getBillingTemplates().get(count);

                RelatedBillingTemplate rtBT = new RelatedBillingTemplate();
                rtBT.setBillingTemplateId(bTemplate.getId());
                _rtBTemplate.add(rtBT);
            }

            rt.setRelatedBillingTemplate(_rtBTemplate);
            RequestorType rType = _adminService.createRequestorType(rt);
            RequestorType reqType = _adminService.retrieveRequestorType(rType.getId());
            assertEquals(rType.getId(), reqType.getId());
            assertNotSame("Created requestortype id should be greater than zero", 0, rType.getId());
        } catch (ROIException e) {
            fail("Create requestortype with more billingTemplate should not thrown exception."
                    + e.getErrorCode());
        }

    }

    /**
     * This test case for retrieveRequesetorType
     */
    public void testRetrieveRequestorTypeById() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName + "get");
            RequestorType rType = _adminService.createRequestorType(rt);
            RequestorType reqType = _adminService.retrieveRequestorType(rType.getId());
            assertEquals(rType.getId(), reqType.getId());
        } catch (ROIException e) {
            fail("Retrieve requestortype should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case to test the billing template id associated with the requestor type
     */
    public void testRequestorTypeAssociationWithBillingTemplate() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName + "getassociationtemplate");
            RequestorType rType = _adminService.createRequestorType(rt);
            RequestorType reqType = _adminService.retrieveRequestorType(rType.getId());
            long btId = 0;
            for (RelatedBillingTemplate bt : reqType.getRelatedBillingTemplate()) {
                btId = bt.getBillingTemplateId();
            }
            _service.deleteBillingTemplate(btId);
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TEMPLATE_IS_ASSOCIATED_WITH_REQUESTOR_TYPE);
        }
    }

    /**
     * This test case for retrieveRequesetorTypeWithInvalidId and verify
     * if it returns the appropriate Exception
     */
    public void testRetrieveRequestorTypeWithInvalidId() {

        try {

            RequestorType reqType = _adminService.retrieveRequestorType(Integer.MAX_VALUE);
            fail("Retrieving requestor type with invalid id is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This test case for deleteRequestorType
     */
    public void testDeleteRequestorType() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName + "Delete");
            RequestorType rType = _adminService.createRequestorType(rt);
            _adminService.deleteRequestorType(rType.getId());
            _adminService.retrieveRequestorType(rType.getId());
            fail("retrieving the deleted requestor id is not permitted, but rereieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This test case for deleteRequesetorTypeWithSession Null and verify
     * if it returns the appropriate Exception
     */
    public void testDeleteRequestorTypeWithSessionNull() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName + "session");
            RequestorType  rType = _adminService.createRequestorType(rt);
            initSession(null);

            _adminService.deleteRequestorType(rType.getId());
            fail("Deleting requestortype with null user is not permitted, but deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_OPERATION_FAILED);
        }
    }

    /**
     * This test case for deleteRequesetorTypeWithInvalidId and verify
     * if it returns the appropriate Exception
     */
    public void testDeleteRequestorTypeWithInvalidId() {

        try {

            initSession();
            _adminService.deleteRequestorType(Integer.MAX_VALUE);
            fail("Deleting the requestortype with invalid id is not permitted, but deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This test case for deleteRequesetorTypeWithInvalidId and verify
     * if it returns the appropriate Exception
     */
    public void testDeleteRequestorTypeSeedData() {

        try {

            final long id = -1;
            _adminService.deleteRequestorType(id);
            fail("Deleting the requestortype seed data is not permitted, but deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_SEED_DATA_IS_DELETED);
        }
    }

    /**
     * This test case for RetrieveAllRequestorTypes
     */
    public void testRetrieveAllRequestorTypes() {

        try {

            RequestorTypesList rtList = new RequestorTypesList();
            rtList = _adminService.retrieveAllRequestorTypes(true);
            assertNotSame("The size of retrieved requestor types should be greater than zero",
                          0,
                          rtList.getRequestorTypes().size());

        } catch (ROIException e) {
            fail("Retrieve all requestortypes should not thrown exception" + e.getErrorCode());
        }
    }

    /**
     * This test case for RetrieveAllRequestorTypes
     */
    public void testRetrieveAllRequestorTypesNames() {

        try {

            RequestorTypesList rtList = new RequestorTypesList();
            rtList = _adminService.retrieveAllRequestorTypes(false);
            assertNotSame("The size of retrieved requestor types should be greater than zero",
                          0,
                          rtList.getRequestorTypes().size());

        } catch (ROIException e) {
            fail("Retrieve all requestortypes should not thrown exception" + e.getErrorCode());
        }
    }

    /**
     * This test case for updateRequestorType
     *
     */
    public void testUpdateRequestorType() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName + "Update1");
            RequestorType rType = _adminService.createRequestorType(rt);

            RequestorType retrieved = _adminService.retrieveRequestorType(rType.getId());
            retrieved.setName(_requestorTypeName + "UPDATED");
            retrieved.setDescription("desc updated");

            RequestorType updated = _adminService.updateRequestorType(retrieved);
            RequestorType reqType = _adminService.retrieveRequestorType(updated.getId());

            reqType.setName(_requestorTypeName + "UPDATE REQUESTORTYPE");
            RequestorType updatedType = _adminService.updateRequestorType(reqType);
            RequestorType type1 = _adminService.retrieveRequestorType(updatedType.getId());
            assertNotSame(updatedType.getRecordVersion(), updated.getRecordVersion());
            assertEquals(updated.getName(), retrieved.getName());
        } catch (ROIException e) {
            fail("Update requestortype should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case for updateRequestorType
     *
     */
    public void testUpdateRequestorTypeWithInvalidData() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName + "Update2");
            RequestorType rType = _adminService.createRequestorType(rt);

            RequestorType retrieved = _adminService.retrieveRequestorType(rType.getId());
            retrieved.setName(appendString(_invalidRequestorTypeName));
            retrieved.setDescription("desc updated");

            RequestorType updated = _adminService.updateRequestorType(retrieved);
            fail("Update requestortype with invalid data not allowed.");
        } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_NAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case for updateRequestorType
     *
     */
    public void testUpdateRequestorTypeForMoreThanOneDefault() {

        try {

            BillingTemplate bt = _btList.getBillingTemplates().get(0);
            BillingTemplate b = _btList.getBillingTemplates().get(1);

            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName + "forMoreDefault");
            RequestorType rType = _adminService.createRequestorType(rt);

            RequestorType retrieved = _adminService.retrieveRequestorType(rType.getId());
            retrieved.setName(_requestorTypeName + "default");
            retrieved.setDescription("desc updated");

            _rtBTemplate = new HashSet <RelatedBillingTemplate>();
            RelatedBillingTemplate rtBT = new RelatedBillingTemplate();
            rtBT.setBillingTemplateId(bt.getId());
            rtBT.setIsDefault(true);
            _rtBTemplate.add(rtBT);
            retrieved.setRelatedBillingTemplate(_rtBTemplate);

            RelatedBillingTemplate rtB = new RelatedBillingTemplate();
            rtB.setBillingTemplateId(b.getId());
            rtB.setIsDefault(true);
            retrieved.getRelatedBillingTemplate().add(rtB);

            RequestorType updated = _adminService.updateRequestorType(retrieved);
            fail("Update requestortype with morethan one default is not permitted, "
                    + "but it is updated");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.
                    REQUESTOR_TYPE_BILLING_TEMPLATE_HAVE_MORE_THAN_ONE_DEFAULT);
        }
    }

    /**
     * This test case for updateRequestorType For SeedData and verify if it returns
     * appropriate Exception
     */
    public void testUpdateRequestorTypeSeedData() {

        try {

            RequestorType retrieved = baseCreation();
            retrieved.setId(-1);
            retrieved.setName("updating");
            _adminService.updateRequestorType(retrieved);
            fail("Updating the requestortype seed data is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_SEED_DATA_IS_MODIFIED);
        }
    }

    /**
     * This test case for updateRequestorTypeWithoutbillingTier and verify if it returns
     * appropriate Exception
     */
    public void testUpdateRequestorTypeWithoutRecordView() {

        try {

            RequestorType rt = baseCreation();
            rt.setRv("");
            _adminService.updateRequestorType(rt);
            fail("Updating requestortype without recordview is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_MUST_HAVE_ONE_RECORD_VIEW);
        }
    }

    /**
     * This test case for updateRequestorTypeWithoutbillingTier and verify if it returns
     * appropriate Exception
     */
    public void testUpdateRequestorTypeWithoutHpfBillingTier() {

        try {

            RequestorType rt = baseCreation();

            _rtBTier = new HashSet<RelatedBillingTier>();

            RelatedBillingTier nonHpfBillingTier = new RelatedBillingTier();
            nonHpfBillingTier.setBillingTierId(NON_HPF_BILLINGTIER_ID);
            nonHpfBillingTier.setIsHPF(false);
            nonHpfBillingTier.setIsHECM(false);
            nonHpfBillingTier.setIsCEVA(false);
            nonHpfBillingTier.setIsSupplemental(true);

            _rtBTier.add(nonHpfBillingTier);
            rt.setRelatedBillingTier(_rtBTier);

            _adminService.updateRequestorType(rt);
            fail("Updating requestortype without billingtier is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_MUST_HAVE_ONE_HPF_BILLING_TIER);
        }
    }

    /**
     * This test case for updateRequestorTypeWithoutbillingTier and verify if it returns
     * appropriate Exception
     */
    public void testUpdateRequestorTypeWithoutElectronic() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName + "Elec");

            _rtBTier = new HashSet<RelatedBillingTier>();
            RelatedBillingTier hpfBillingTier = new RelatedBillingTier();
            hpfBillingTier.setBillingTierId(NON_HPF_BILLINGTIER_ID);
            hpfBillingTier.setIsHPF(true);
            hpfBillingTier.setIsHECM(false);
            hpfBillingTier.setIsCEVA(false);
            hpfBillingTier.setIsSupplemental(false);

            RelatedBillingTier nonHpfBillingTier = new RelatedBillingTier();
            nonHpfBillingTier.setBillingTierId(NON_HPF_BILLINGTIER_ID);
            nonHpfBillingTier.setIsHPF(false);
            nonHpfBillingTier.setIsHECM(false);
            nonHpfBillingTier.setIsCEVA(false);
            nonHpfBillingTier.setIsSupplemental(true);

            _rtBTier.add(hpfBillingTier);
            _rtBTier.add(nonHpfBillingTier);

            rt.setRelatedBillingTier(_rtBTier);

            _adminService.updateRequestorType(rt);
            fail("Updating requestortype without electronic is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_BTIER_NAME_IS_NOT_ELECTRONIC);
        }
    }

    /**
     * This test case for updateRequestorTypeWithoutbillingTemplate
     *
     */
    public void testUpdateRequestorTypeWithoutBillingTemplate() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName + "Billing");
            RequestorType requestorType = _adminService.createRequestorType(rt);
            RequestorType rType = _adminService.retrieveRequestorType(requestorType.getId());

            rType.setName(_requestorTypeName + "template");
            _rtBTemplate = new HashSet <RelatedBillingTemplate>();
            rType.setRelatedBillingTemplate(_rtBTemplate);
            RequestorType rt1 = _adminService.updateRequestorType(rType);
            assertEquals(rType.getName(), rt1.getName());
        } catch (ROIException e) {
            fail("Update requestortype without billing template should not thrown exception"
                    + e.getErrorCode());
        }
    }

    /**
     * This test case for updateRequestorTypeWithMore NameLength and verify if it returns
     * appropriate Exception
     */
    public void testUpdateRequestorTypeWithNameLengthExceeds() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName  + new StringBuffer()
            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
            .toString());

            _adminService.updateRequestorType(rt);
            fail("Updating requestor type with more name length is not permitted, "
                    + "but it is updated");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_NAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case for updateRequestorTypeWithDuplicateName and verify if it returns
     * appropriate Exception
     */
    public void testUpdateRequestorTypeWithDuplicateName() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName + "Update3");
            RequestorType rType = _adminService.createRequestorType(rt);

            RequestorType retrieved = _adminService.retrieveRequestorType(rType.getId());
            String name = retrieved.getName();
            RequestorType rt1 = baseCreation();
            rt1.setName(name);
            _adminService.updateRequestorType(rt1);
            fail("Updating requestortype with duplicate name is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * This test case for UpdateRequestorTypeWithNameNull and verify if it returns
     * appropriate Exception
     */
    public void testUpdateRequestorTypeWithNameNULL() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(null);
            _adminService.updateRequestorType(rt);
            fail("Upating the requestortype with name as null is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_NAME_IS_BLANK);
        }
    }

    /**
     * This test case for updateRequestorTypeWithName Blank and verify if it returns
     * appropriate Exception
     */
    public void testUpdateRequestorTypeWithNameBlank() {

        try {

            RequestorType rt = baseCreation();
            rt.setName(" ");
            _adminService.updateRequestorType(rt);
            fail("Upating the requestortype with blank name is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_NAME_IS_BLANK);
        }
    }

    /**
     * This test case to delete the requestor type associated with requestor and verify if it
     * returns the appropriate exception
     */
    public void testDeleteRequestorTypeAsoociatedWithRequestor() {

        try {

            RequestorType r = baseCreation();
            r.setName(_requestorTypeName + 1);
            RequestorType rt = _adminService.createRequestorType(r);
            assertTrue(rt.getIsAssociated());
            _requestorService.createRequestor(createRequestor(rt.getId()));
            _adminService.deleteRequestorType(rt.getId());
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_ASSOCIATED_WITH_REQUESTOR_IS_DELETED);
        }
    }

    /**
     * This test case for updateRequestorTypeWithSession Null and verify if it returns
     * appropriate Exception
     */
    public void testUpdateRequestorTypeWithSessionNull() {

        try {

            initSession(null);
            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName + "sss");
            _adminService.updateRequestorType(rt);
            fail("Upating the requestortype with null session is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_TYPE_OPERATION_FAILED);
        }
    }

    public void testUpdateRequestorTypeForConcurrency() {

        try {

            initSession();
            RequestorType rt1;
            RequestorType rt2;

            RequestorType rt = baseCreation();
            rt.setName(_requestorTypeName + "forConcurrency");
            RequestorType rType = _adminService.createRequestorType(rt);
            RequestorType reqType = _adminService.retrieveRequestorType(rType.getId());

            rt1 = (RequestorType) deepCopy(reqType);
            rt1.setDescription(_requstorTypeDesc + "TestingRequestorTypeupdateDesc");

            _adminService.updateRequestorType(rt1);

            rt2 = (RequestorType) deepCopy(reqType);
            rt2.setDescription(_requstorTypeDesc + "TestingRequestorTypeDesc");
            _adminService.updateRequestorType(rt2);

            fail("Update requestortype should thrown exception, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION);
        } catch (Exception e) {
            fail("Update requestortype should not thrown exception");
        }
    }

    public Object deepCopy(Object oldObj)
    throws Exception {

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            //serialize and pass the object
            oos.writeObject(oldObj);
            oos.flush();
            ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bin);
            // return the new object
            return ois.readObject();
        } catch (Exception e) {
            System.out.println("Exception in ObjectCloner = " + e);
            throw (e);
        } finally {
            oos.close();
            ois.close();
        }
    }

    /**
     * This test case for createRequestorTypeWithInvalidBillingTier and verify if it returns
     * appropriate Exception
     */
    public void testCreateRequestorTypeWithInvalidBillingTierId() {

        try {

            final long invalidId = 10000;
            RequestorType rt = baseCreation();

            _rtBTier = new HashSet<RelatedBillingTier>();
            RelatedBillingTier hpfBillingTier = new RelatedBillingTier();
            hpfBillingTier.setBillingTierId(invalidId);
            hpfBillingTier.setIsHPF(true);
            hpfBillingTier.setIsHECM(false);
            hpfBillingTier.setIsCEVA(false);
            hpfBillingTier.setIsSupplemental(false);

            RelatedBillingTier nonHpfBillingTier = new RelatedBillingTier();
            nonHpfBillingTier.setBillingTierId(invalidId);
            nonHpfBillingTier.setIsHPF(false);
            nonHpfBillingTier.setIsHECM(false);
            nonHpfBillingTier.setIsCEVA(false);
            nonHpfBillingTier.setIsSupplemental(true);

            _rtBTier.add(hpfBillingTier);
            _rtBTier.add(nonHpfBillingTier);

            rt.setRelatedBillingTier(_rtBTier);

            _adminService.createRequestorType(rt);
            fail("Creating the requestor type with invalid billingtier is not permitted,"
                 + " but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_BILLING_TIER_ASSOCIATION);
        }
    }

    /**
     * This test case for createRequestorTypeWithInvalidBillingTemplate and verify if it returns
     * appropriate Exception
     */
    public void testCreateRequestorTypeWithInvalidBillingTemplateId() {

        try {

            final long invalidId = 10000;
            RequestorType rt = baseCreation();

            _rtBTemplate = new HashSet <RelatedBillingTemplate>();

            RelatedBillingTemplate rtBTemp = new RelatedBillingTemplate();
            rtBTemp.setBillingTemplateId(invalidId);
            rtBTemp.setIsDefault(true);
            _rtBTemplate.add(rtBTemp);
            rt.setRelatedBillingTemplate(_rtBTemplate);

            _adminService.createRequestorType(rt);
            fail("Creating the requestor type with invalid billingtemplate is not permitted,"
                 + " but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_BILLING_TEMPLATE_ASSOCIATION);
        }
    }

    private RequestorType baseCreation() {

        List<BillingTemplate> btList = _btList.getBillingTemplates();

        RequestorType rt = new RequestorType();
        rt.setName(_requestorTypeName);
        rt.setDescription(_requstorTypeDesc);
        rt.setRv(_rv);
        rt.setRvDesc(_rvDesc);
        rt.setIsAssociated(true);

        _rtBTemplate = new HashSet <RelatedBillingTemplate>();

        RelatedBillingTemplate rtBT = new RelatedBillingTemplate();
        rtBT.setBillingTemplateId(btList.get(0).getId());
        rtBT.setIsDefault(true);

        RelatedBillingTemplate rtBTem = new RelatedBillingTemplate();
        rtBTem.setBillingTemplateId(btList.get(1).getId());
        rtBTem.setIsDefault(false);

        _rtBTemplate.add(rtBT);
        _rtBTemplate.add(rtBTem);
        rt.setRelatedBillingTemplate(_rtBTemplate);

        _rtBTier = new HashSet<RelatedBillingTier>();
        RelatedBillingTier hpfBillingTier = new RelatedBillingTier();
        hpfBillingTier.setBillingTierId(HPF_BILLINGTIER_ID);
        hpfBillingTier.setIsHPF(true);
        hpfBillingTier.setIsHECM(false);
        hpfBillingTier.setIsCEVA(false);
        hpfBillingTier.setIsSupplemental(false);

        RelatedBillingTier nonHpfBillingTier = new RelatedBillingTier();
        nonHpfBillingTier.setBillingTierId(NON_HPF_BILLINGTIER_ID);
        nonHpfBillingTier.setIsHPF(false);
        nonHpfBillingTier.setIsHECM(false);
        nonHpfBillingTier.setIsCEVA(false);
        nonHpfBillingTier.setIsSupplemental(true);

        _rtBTier.add(hpfBillingTier);
        _rtBTier.add(nonHpfBillingTier);

        rt.setRelatedBillingTier(_rtBTier);

        return rt;
    }

    private Requestor createRequestor(long id) {

        Requestor req = new Requestor();
        req.setPrePayRequired(true);
        req.setCertLetterRequired(true);
        req.setType(id);
        req.setLastName("Requestor" + _requestorCount);
        req.setEpn(getUser().getEpnPrefix() + "ABC" + _requestorCount);
        req.setSsn("SSN" + _requestorCount);
        req.setDob(_validDob);
        req.setHomePhone("11111" + _requestorCount);
        req.setWorkPhone("22222" + _requestorCount);
        req.setCellPhone("33333" + _requestorCount);
        req.setEmail("requestor" + _requestorCount + "@mck.com");
        req.setFax("5555555" + _requestorCount);
        req.setContactName("Contact" + _requestorCount);
        req.setContactEmail("contact" + _requestorCount + "@mck.com");
        req.setContactPhone("4444444" + _requestorCount);

        Address main = new Address();
        main.setAddress1("MainAddress1" + _requestorCount);
        main.setAddress2("MainAddress2" + _requestorCount);
        main.setAddress3("MainAddress3" + _requestorCount);
        main.setCity("Atlanta");
        main.setState("Alpahretta");
        main.setPostalCode("11006-1278");
        main.setCountryCode("USA");
        main.setCountrySeq(new Long(-273));

        Address alt = new Address();
        alt .setAddress1("AltAddress1" + _requestorCount);
        alt .setAddress2("AltAddress2" + _requestorCount);
        alt .setAddress3("AltAddress3" + _requestorCount);
        alt .setCity("Atlanta");
        alt .setState("Alpharetta");
        alt .setPostalCode("11006-1278");
        alt.setCountryCode("USA");
        alt.setCountrySeq(new Long(-273));

        req.setAltAddress(alt);
        req.setMainAddress(main);
        _requestorCount++;
        return req;
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }

    /**
     * Create a Fee Type for testing Billing Template
     */
    public void createFeeTypeForTesting() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName("FeeName" + System.nanoTime());
            feeType.setDescription("FeeDesc");
            final double ca = 234.45;
            feeType.setChargeAmount(ca);

            _feeTypeId = _service.createFeeType(feeType);
        } catch (ROIException e) {
            fail("create feetype should not thrown exception" + e.getErrorCode());
        }
    }

    /**
     * Tests creation of Billing Template
     */
    public void createBillingTemplate() {

        try {

            BillingTemplate billingTemplate = new BillingTemplate();
            billingTemplate.setName(_billingTemplateName + System.nanoTime());

            _btfSet = new HashSet<RelatedFeeType>();
            RelatedFeeType btf = new RelatedFeeType();
            btf.setFeeTypeId(_feeTypeId);
            _btfSet.add(btf);

            billingTemplate.setRelatedFeeTypes(_btfSet);

            long btId = _service.createBillingTemplate(billingTemplate);
        } catch (ROIException e) {
            fail("Creating billing template should not thrown exception." + e.getErrorCode());
        }
    }

}
