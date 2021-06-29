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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.mckesson.eig.roi.admin.model.BillingTier;
import com.mckesson.eig.roi.admin.model.BillingTiersList;
import com.mckesson.eig.roi.admin.model.MediaType;
import com.mckesson.eig.roi.admin.model.PageLevelTier;
import com.mckesson.eig.roi.admin.model.RelatedBillingTier;
import com.mckesson.eig.roi.admin.model.RequestorType;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.test.BaseROITestCase;


/**
 * This class For testing all operations
 *
 * @author Ganeshramr
 * @date   Mar 31, 2009
 * @since  HPF 13.1 [ROI]; Apr 15, 2008
 */
public class TestBillingTierServiceImpl
extends BaseROITestCase {

    protected static final String  ADMIN_SERVICE =
        "com.mckesson.eig.roi.admin.service.BillingAdminServiceImpl";

    protected static final String ROI_SERVICE =
        "com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl";

    private static BillingAdminService _adminService;
    private static ROIAdminService     _roiService;

    private static String              _billingTierName;
    private static String              _billingTierNameInvalidName;
    private static String              _billingTierDesc = "";
    private static PageLevelTier       _pgt;
    private static Set<PageLevelTier> _bTDetails;
    private static Set<RelatedBillingTier> _rtBTier;

    private static final int HPF_BILLINGTIER_ID = -1;

    public void initializeTestData()
    throws Exception {

        long seed = System.currentTimeMillis();
        _billingTierName = "BillingTierName" + seed;
        _billingTierNameInvalidName = "!@#$%";
        _billingTierDesc = "BillingTierDesc." + seed;
        _adminService = (BillingAdminService) getService(ADMIN_SERVICE);
        _roiService = (ROIAdminService) getService(ROI_SERVICE);
    }

    /**
     * This test case for creating BillingTier and verifying if it returns the
     * newly created billingtierId
     */
    public void testCreateBillingTier() {

        try {

            BillingTier billingTier = constructBillingTier();
            billingTier.setName(_billingTierName + "New");
            BillingTier bt = _adminService.createBillingTier(billingTier);
            BillingTier bTier = _adminService.retrieveBillingTier(bt.getId());
            bt.getAssociated();
            assertNotNull(bTier);
            assertEquals(bt.getName(), bTier.getName());
           assertNotSame("Created billingtier id should be greater than zero", 0, bt.getId());
        } catch (ROIException e) {
            fail("Create billingTier should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case for creating BillingTier with invalid data
     */
    public void testCreateBillingTierWithInvalidData() {

        try {

            BillingTier billingTier = constructBillingTier();
            billingTier.setName(appendString(_billingTierNameInvalidName));
            billingTier.setDescription(appendString(_billingTierDesc));
            BillingTier bt = _adminService.createBillingTier(billingTier);
            fail("Creation of billing tier with Invalid data is not permitted");
        } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.BILLING_TIER_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.BILLING_TIER_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.BILLING_TIER_DESC_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case for creating BillingTierwith invalid mediaTypeId and verifying if it
     * returns the appropriate exception
     */
    public void testCreateBillingTierWithInvalidMediaTypeId() {

        try {

            BillingTier billingTier = constructBillingTier();
            final long mId = -26908;
            billingTier.getMediaType().setId(mId);

            _adminService.createBillingTier(billingTier);
            fail("Creating billingTier with invalid id is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This test case for creating BillingTier and verifying if it returns the
     * appropriate exception
     */
    public void testCreateBillingTierForInvalidPageLevelTier() {

        try {

            BillingTier billingTier = constructBillingTier();
            billingTier.setName(_billingTierName + "InvalidPage");

            PageLevelTier bTD = new PageLevelTier();
            bTD.setPage(1);
            final float pc = 90;
            bTD.setPageCharge(pc);
            billingTier.getPageLevelTier().add(bTD);

            billingTier = _adminService.createBillingTier(billingTier);

            assertTrue(billingTier.getId() > 0);
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION);
        }
    }

    /**
     * This test case for creating BillingTier and verifying if it returns the
     * appropriate exception
     */
    public void testCreateBillingTierWithDuplicateName() {

        try {

            BillingTier billingTier = constructBillingTier();

            _adminService.createBillingTier(billingTier);
            _adminService.createBillingTier(billingTier);
            fail("Creating billing tier with duplicate name is not permitted, but created.");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLNG_TIER_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * This test case for creating BillingTier and verifying if it returns the
     * appropriate exception
     */
    public void testCreateBillingTierWithNameBlank() {

        try {

            BillingTier billingTier = constructBillingTier();
            billingTier.setName("");

            _adminService.createBillingTier(billingTier);
            fail("Creating billing tier with name null is not permitted, but created.");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_NAME_IS_BLANK);
        }
    }

    /**
    * This test case for creating BillingTier and verifying if it returns the
    * appropriate exception
    */
   public void testCreateBillingTierWithNegativeAmount() {

       try {

           BillingTier billingTier = constructBillingTier();
           final float bc = -34;
           billingTier.setBaseCharge(bc);
           final float dbc = -15;
           billingTier.setDefaultPageCharge(dbc);

           _adminService.createBillingTier(billingTier);
           fail("Creating billing tier with amount negatives are not permitted, but created");
       } catch (ROIException e) {
           assertError(e, ROIClientErrorCodes.BILLING_TIER_AMOUNTS_SHOULD_BE_POSITIVE);
       }
   }

    /**
     * This test case for creating BillingTier and verifying if it returns the
     * appropriate exception
     */
    public void testCreateBillingTierWithNameNull() {

        try {

            BillingTier billingTier = constructBillingTier();
            billingTier.setName(null);
             _adminService.createBillingTier(billingTier);
            fail("Creating billing tier with name null is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_NAME_IS_BLANK);
        }
    }

    /**
     * This test case for creating BillingTier and verifying if it returns the
     * appropriate exception
     */
    public void testCreateBillingTierWithMoreNameLength() {

        try {

            BillingTier billingTier = constructBillingTier();
            billingTier.setName(_billingTierName
                                + new StringBuffer().append("TEST BILLING TIER NAME TEST NAME")
                                .append("TEST BILLING TIER NAME TEST BILLING TIER NAME TEST")
                                .append("TEST BILLING TIER NAME TEST BILLING TIER NAME TEST")
                                .append("TEST BILLING TIER NAME TEST BILLING TIER NAME TEST")
                                .append("TEST BILLING TIER NAME TEST BILLING TIER NAME TEST")
                                .append("TEST BILLING TIER NAME TEST BILLING TIER NAME TEST")
                                .append("TEST BILLING TIER NAME TEST BILLING TIER NAME TEST")
                                .toString());

            _adminService.createBillingTier(billingTier);
            fail("Creating billing tier with more name length is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_NAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case for creating BillingTier and verifying if it returns the
     * appropriate exception
     */
    public void testCreateBillingTierWithoutMediaTypeAssociation() {

        try {

            BillingTier billingTier = constructBillingTier();
            final long mId = 0;
            MediaType mt = new MediaType();
            mt.setId(mId);
            billingTier.setMediaType(mt);

             _adminService.createBillingTier(billingTier);
            fail("Creating billing tier without media type is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_DOESNOT_HAVE_MEDIA_TYPE_ASSOCIATION);
        }
    }

    /**
     * This test case for creating BillingTier and verifying if it returns the
     * appropriate exception
     */
    public void testCreateBillingTierWithoutPageTier() {

        try {

            Set<PageLevelTier> pt = new HashSet<PageLevelTier>();
            BillingTier billingTier = constructBillingTier();
            billingTier.setPageLevelTier(pt);

             _adminService.createBillingTier(billingTier);
            fail("Creating billingtier without page level tier is not permitted,but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_ATLEAST_HAVE_ONE_PAGE_LEVEL_TIER);
        }
    }

    /**
     * This test case for creating BillingTier and verifying if it returns the
     * appropriate exception
     */
    public void testCreateBillingTierWithMediaTypeNonHPF() {

        try {

            BillingTier billingTier = constructBillingTier();
            final long mId = -2;
            MediaType mt = new MediaType();
            mt.setId(mId);
            billingTier.setMediaType(mt);

            _adminService.createBillingTier(billingTier);
            fail("Creating billingtier association with nonhpf is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_ASSOCIATED_WITH_NON_HPF);
        }
    }

    /**
     * This test case for create BillingTier with null and verifying
     * if it returns the appropriate exception
     */
    public void testCreateBillingTierWithNull() {

        try {

             _adminService.createBillingTier(null);
             fail("creating billing tier with null is not permitted, but it is created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_OPERATION_FAILED);
        }
    }

    /**
     * This test case for create BillingTier with user session null and verifying
     * if it returns the appropriate exception
     */
    public void testCreateBillingTierWithUserNull() {

        try {

            initSession(null);
            BillingTier billingTier = constructBillingTier();
            billingTier.setName(_billingTierName + "usernull");
            _adminService.createBillingTier(billingTier);
            fail("Creating billing tier with null user is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_OPERATION_FAILED);
        }
    }

    /**
     * This test case for create BillingTier with invalid base charge amount
     */
    public void testCreateBillingTierWithInvalidBaseCharge() {

        try {

            BillingTier billingTier = new BillingTier();

            billingTier.setName(_billingTierName);
            billingTier.setDescription(_billingTierDesc);

            long mtId = -1;
            MediaType mt = new MediaType();
            mt.setId(mtId);
            billingTier.setMediaType(mt);

            final float bc = (float) 1111.555;
            billingTier.setBaseCharge(bc);
            final float dbc = (float) 10.78;
            billingTier.setDefaultPageCharge(dbc);

            PageLevelTier bTD = new PageLevelTier();
            bTD.setPage(1);
            final float pc = (float) 12.78;
            bTD.setPageCharge(pc);

            _bTDetails = new HashSet <PageLevelTier>();
            _bTDetails.add(bTD);
            billingTier.setPageLevelTier(_bTDetails);

            _adminService.createBillingTier(billingTier);
            fail("Creating billingtier with invalid base charge amount is not permitted, "
            	+ " but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_BASE_CHARGE_AMOUNT);
        }
    }

    /**
     * This test case for create BillingTier with invalid default page charge amount
     */
    public void testCreateBillingTierWithInvalidDefaultPageCharge() {

        try {

            BillingTier billingTier = new BillingTier();

            billingTier.setName(_billingTierName);
            billingTier.setDescription(_billingTierDesc);

            long mtId = -1;
            MediaType mt = new MediaType();
            mt.setId(mtId);
            billingTier.setMediaType(mt);

            final float bc = (float) 11.55;
            billingTier.setBaseCharge(bc);
            final float dbc = (float) 10454.7834;
            billingTier.setDefaultPageCharge(dbc);

            PageLevelTier bTD = new PageLevelTier();
            bTD.setPage(1);
            final float pc = (float) 12.78;
            bTD.setPageCharge(pc);
            _bTDetails = new HashSet <PageLevelTier>();
            _bTDetails.add(bTD);
            billingTier.setPageLevelTier(_bTDetails);

            _adminService.createBillingTier(billingTier);
            fail("Creating billing tier with invalid default page charge amount is not permitted"
            	  + ", but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_DEFAULT_PAGE_CHARGE_AMOUNT);
        }
    }

    /**
     * This test case for create BillingTier with invalid page charge amount
     */
    public void testCreateBillingTierWithInvalidPageCharge() {

        try {

            BillingTier billingTier = new BillingTier();

            billingTier.setName(_billingTierName);
            billingTier.setDescription(_billingTierDesc);

            long mtId = -1;
            MediaType mt = new MediaType();
            mt.setId(mtId);
            billingTier.setMediaType(mt);

            final float bc = (float) 11.55;
            billingTier.setBaseCharge(bc);
            final float dbc = (float) 10.34;
            billingTier.setDefaultPageCharge(dbc);

            PageLevelTier bTD = new PageLevelTier();
            bTD.setPage(1);
            final float pc = (float) 123.7845;
            bTD.setPageCharge(pc);

            _bTDetails = new HashSet <PageLevelTier>();
            _bTDetails.add(bTD);
            billingTier.setPageLevelTier(_bTDetails);

            _adminService.createBillingTier(billingTier);
            fail("Creating billing tier with invalid page charge amount is not permitted, "
            	  + "but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_PAGE_CHARGE_AMOUNT);
        }
    }

    /**
     * This test case for create BillingTier with invalid page number
     */
    public void testCreateBillingTierWithInvalidPageNumber() {

        try {

            BillingTier billingTier = new BillingTier();

            billingTier.setName(_billingTierName);
            billingTier.setDescription(_billingTierDesc);

            long mtId = -1;
            MediaType mt = new MediaType();
            mt.setId(mtId);
            billingTier.setMediaType(mt);

            final float bc = 23;
            billingTier.setBaseCharge(bc);
            final float dbc = 12;
            billingTier.setDefaultPageCharge(dbc);

            PageLevelTier bTD = new PageLevelTier();
            bTD.setPage(0);
            final float pc = 45;
            bTD.setPageCharge(pc);

            _bTDetails = new HashSet <PageLevelTier>();
            _bTDetails.add(bTD);
            billingTier.setPageLevelTier(_bTDetails);

            _adminService.createBillingTier(billingTier);
            fail("Creating billingtier with invalid page no is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_PAGE_NUMBER);
        }
    }

    /**
     * This test case for retrieve the billingTier
     */
    public void testRetrieveBillingTier() {

        try {

            initSession();
            BillingTier bt = _adminService.retrieveBillingTier(-1);
            bt.getMediaTypeName();
            bt.setMediaTypeId(bt.getMediaTypeId());
            assertNotNull(bt);
            assertEquals(bt.getId(), -1);
        } catch (ROIException e) {
            fail("Retrieve billingtier should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case for retrieveBillingTier withInvalidId
     * and verifying if it returns the appropriate exception
     */
    public void testRetrieveBillingTierWithInvalidId() {

        try {

            final long billingTierId = Integer.MAX_VALUE;
            _adminService.retrieveBillingTier(billingTierId);
            fail("Retrieve billing tier with invalid id is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This test case for retrieveAllBillingTier
     */
    public void testRetrieveAllBillingTier() {

        try {

            BillingTiersList bTList = new BillingTiersList();
            bTList = _adminService.retrieveAllBillingTiers(true);
            assertTrue(bTList.getBillingTiers().size() > 0);
        } catch (ROIException e) {
            fail("Retrieve all billingtier should not thrown exception." + e.getErrorCode());
        }
    }
    /**
     * This test case for retrieveAllBillingTier
     */
    public void testRetrieveAllBillingTierWithoutAssocation() {

        try {

            BillingTiersList bTList = new BillingTiersList();
            bTList = _adminService.retrieveAllBillingTiers(false);
            assertTrue(bTList.getBillingTiers().size() > 0);
        } catch (ROIException e) {
            fail("Retrieve all billingtier should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     *This test case for deleteBillingTier
     */
    public void testDeleteBillingTier() {

        try {

            BillingTier bt = constructBillingTier();
            bt.setName(_billingTierName + "Delete");
            bt = _adminService.createBillingTier(bt);

            _adminService.deleteBillingTier(bt.getId());
            _adminService.retrieveBillingTier(bt.getId());
            fail("Retrieving the deleted billing tier id is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     *This test case for deleteBillingTier
     *withNullSession
     */
    public void testDeleteBillingTierWithNullSession() {

        try {

            BillingTier bt = constructBillingTier();
            bt.setName(_billingTierName + System.currentTimeMillis());
            bt = _adminService.createBillingTier(bt);
            long id = bt.getId();
            initSession(null);

            _adminService.deleteBillingTier(id);
            fail("Deleting the billing tier with null user is not permitted, but deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_OPERATION_FAILED);
        }
    }
    /**
     *This test case for deleteBillingTier
     */
    public void testDeleteBillingTierWithInvalidId() {

        try {

            initSession();
            _adminService.deleteBillingTier(Integer.MAX_VALUE);
            fail("Delete billingtier with invalid id is not permitted, but deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     *This test case for deleteBillingTier
     */
    public void testDeleteBillingTierSeedData() {

        try {

            final long id = -2;
            _adminService.deleteBillingTier(id);
            fail("Delete billingtier with seed data is not permitted, but deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.SEED_DATA_IS_BEING_DELETED);
        }
    }

    /**
     * This test case for delete billingTier with requestor
     * type association and verify if it returns the appropriate
     * Exception
     */
    public void testDeleteBillingTierWithRequestorType() {

        try {

            BillingTier billingTier = constructBillingTier();
            billingTier.setName(_billingTierName + "Requestor");
            BillingTier bTier = _adminService.createBillingTier(billingTier);
            long id = bTier.getId();

            _roiService.createRequestorType(createRequestorType(id));

            _adminService.deleteBillingTier(id);

            fail("BillingTier association with requestortype deletion is not permitted, "
            	 + "but deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_ASSOCIATED_TO_REQUESTOR_TYPE_DELETED);
        }
    }

    /**
     * This test case for update BillingTierWithMorePageLevelTier
     */
    public void testUpdateBillingTierWithMorePageTier() {

        try {

            initSession();

            BillingTier bt = constructBillingTier();
            bt.setName(_billingTierName + "Update1");
            bt = _adminService.createBillingTier(bt);

            BillingTier updatedBT = _adminService.retrieveBillingTier(bt.getId());
            updatedBT.setName("forupdate" + System.currentTimeMillis());
            BillingTier uBT = _adminService.updateBillingTier(updatedBT);
            assertEquals(uBT.getName(), updatedBT.getName());

            BillingTier bT = _adminService.retrieveBillingTier(uBT.getId());

            Set<PageLevelTier> temp = bT.getPageLevelTier();

            for (Iterator<PageLevelTier> i = temp.iterator(); i.hasNext();) {

                _pgt = i.next();
                final long pc = 10;
                _pgt.setPageCharge(pc);
            }
            temp.remove(_pgt);
            PageLevelTier bTD = new PageLevelTier();
            final int pa = 2;
            bTD.setPage(pa);
            final float pc = 10;
            bTD.setPageCharge(pc);
            temp.add(bTD);
            _bTDetails = temp;

            bT.setName(_billingTierName + "updateName");

            final long mId = -1;
            MediaType mt = new MediaType();
            mt.setId(mId);
            bT.setMediaType(mt);

            final float bc = 34;
            bT.setBaseCharge(bc);
            final float dbc = 10;
            bT.setDefaultPageCharge(dbc);
            bT.setPageLevelTier(_bTDetails);

            BillingTier uBT1 = _adminService.updateBillingTier(bT);
            assertEquals(bt.getPageLevelTier().size(), uBT1.getPageLevelTier().size());
            assertEquals(uBT1.getName(), bT.getName());
        } catch (ROIException e) {
            fail("Update billingTier should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case for updateBillingTier with supplementary and verify
     * if it returns appropriate exception
     */
    public void testUpdateBillingTierWithSeedDataDesc() {

        try {

            final long id = -1;
            BillingTier billingTier = _adminService.retrieveBillingTier(id);
            billingTier.setDescription(_billingTierDesc + "updateseedDescription");

           BillingTier updatedBT = _adminService.updateBillingTier(billingTier);
           assertEquals(billingTier.getDescription(), updatedBT.getDescription());
        } catch (ROIException e) {
            fail("Update billingtier should not thrown exception." + e.getErrorCode());
        }
    }
    /**
     * This test case for updateBillingTier with supplementary and verify
     * if it returns appropriate exception
     */
    public void testUpdateBillingTierWithInvalidData() {

        try {

            final long id = -1;
            BillingTier billingTier = _adminService.retrieveBillingTier(id);
            billingTier.setName(appendString(_billingTierNameInvalidName));
            billingTier.setDescription(appendString(_billingTierDesc));
            BillingTier updatedBT = _adminService.updateBillingTier(billingTier);
           fail("billingtier update with invalid data is not permitted");
        } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.BILLING_TIER_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.BILLING_TIER_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.BILLING_TIER_DESC_LENGTH_EXCEEDS_LIMIT);
        }
    }
    /**
     * This test case for updateBillingTier with supplementary and verify
     * if it returns appropriate exception
     */
    public void testUpdateBillingTierWithSeedData() {

        try {

            BillingTier billingTier = constructBillingTier();
            final long id = -2;
            billingTier.setId(id);
            billingTier.setName(_billingTierName + "update");
            billingTier.setDescription(_billingTierDesc + "updateDesc");

            _adminService.updateBillingTier(billingTier);
            fail("billingtier seed data name update is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_SEED_DATA_NAME_IS_BEING_EDITED);
        }
    }

    /**
     * This test case for updateBillingTier with supplementary and verify
     * if it returns appropriate exception
     */
    public void testUpdateBillingTierWithBillingTierSeedData() {

        try {

            BillingTier billingTier = constructBillingTier();
            final long id = -2;
            billingTier.setId(id);
            billingTier.setName("Electronic");
            billingTier.setDescription(_billingTierDesc + "updateDesc");

            final long mId = -1;
            MediaType mt = new MediaType();
            mt.setId(mId);
            billingTier.setMediaType(mt);

            _adminService.updateBillingTier(billingTier);
            fail("Updating billingTier without media type is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_SEED_DATA_MEDIA_TYPE_IS_BEING_EDITED);
        }
    }

    /**
     * This test case for updateBillingTier with supplementary and verify
     * if it returns appropriate exception
     */
    public void testUpdateBillingTierWithDuplicateName() {

        try {

            BillingTier billingTier = constructBillingTier();
            final long id = -2;
            billingTier.setId(id);
            billingTier.setName("Electronic");
            billingTier.setDescription(_billingTierDesc + "updateDesc");

            _adminService.updateBillingTier(billingTier);
            fail("Updating billingTier with duplicate name is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLNG_TIER_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * This test case for update BillingTier and verify if
     * it returns the appropriate Exception
     */
    public void testUpdateBillingTierWithNegativeAmount() {

        try {

            BillingTier billingTier = constructBillingTier();
            billingTier.setName(_billingTierName + System.currentTimeMillis());
            billingTier.setDescription(_billingTierDesc);

            final float bc = -30;
            billingTier.setBaseCharge(bc);
            final float dbc = -15;
            billingTier.setDefaultPageCharge(dbc);

            _adminService.updateBillingTier(billingTier);
            fail("Updating billingTier with negative amounts is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_AMOUNTS_SHOULD_BE_POSITIVE);
        }
    }

    /**
     * This test case for update BillingTier and verify if
     * it returns the appropriate Exception
     */
    public void testUpdateBillingTierWithNegativeAmountInPageLevelTier() {

        try {

            BillingTier billingTier = constructBillingTier();
            billingTier.setName(_billingTierName + System.currentTimeMillis());
            billingTier.setDescription(_billingTierDesc);
            Set<PageLevelTier> temp = billingTier.getPageLevelTier();

            for (Iterator<PageLevelTier> i = temp.iterator(); i.hasNext();) {

                _pgt = i.next();
                final long pc = -1;
                _pgt.setPageCharge(pc);
            }
            _adminService.updateBillingTier(billingTier);
            fail("Updating billingTier with negative amounts is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_AMOUNTS_SHOULD_BE_POSITIVE);
        }
    }

    /**
     * This test case for update BillingTier and verifying if it returns the
     * appropriate exception
     */
    public void testUpdateBillingTierWithMoreNameLength() {

        try {

            BillingTier billingTier = constructBillingTier();
            billingTier.setName(_billingTierName
                                + new StringBuffer().append("TEST BILLING TIER NAME TEST NAME")
                                .append("TEST BILLING TIER NAME TEST BILLING TIER NAME TEST")
                                .append("TEST BILLING TIER NAME TEST BILLING TIER NAME TEST")
                                .append("TEST BILLING TIER NAME TEST BILLING TIER NAME TEST")
                                .append("TEST BILLING TIER NAME TEST BILLING TIER NAME TEST")
                                .append("TEST BILLING TIER NAME TEST BILLING TIER NAME TEST")
                                .append("TEST BILLING TIER NAME TEST BILLING TIER NAME TEST")
                                .toString());

            billingTier.setDescription(_billingTierDesc);

            _adminService.updateBillingTier(billingTier);
            fail("Updating billingTier with more name length is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_NAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case for update BillingTier and verify if it returns the appropriate
     * Exception
     */
    public void testUpdateBillingTierWithNameNull() {

        try {

            BillingTier billingTier = constructBillingTier();
            billingTier.setName(null);

            _adminService.updateBillingTier(billingTier);
            fail("Updating billingTier with name as null is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_NAME_IS_BLANK);
        }
    }

    /**
     * This test case for update BillingTier and verify if it returns the appropriate
     * Exception
     */
    public void testUpdateBillingTierWithNameBlank() {

        try {

            BillingTier billingTier = constructBillingTier();
            billingTier.setName("");

           _adminService.updateBillingTier(billingTier);
            fail("Updating billingTier with blank name is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_NAME_IS_BLANK);
        }
    }

    /**
     * This test case for update BillingTier and verify if it returns the appropriate
     * Exception
     */
    public void testUpdateBillingTierWithUserSessionNull() {

        try {

            BillingTier billingTier = constructBillingTier();
            billingTier.setName(_billingTierName + "for session");
            BillingTier bt = _adminService.createBillingTier(billingTier);
            initSession(null);
            billingTier.setId(bt.getId());
            billingTier.setName(_billingTierName + "updateusernull");

             _adminService.updateBillingTier(billingTier);
             fail("Updating billingtier with user as null is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_OPERATION_FAILED);
        }
    }

    public void testUpdateBillingTierForConcurrency() {

        try {

            initSession();
            BillingTier billingTierObject1;
            BillingTier billingTierObject2;

            BillingTier bt1 = constructBillingTier();
            bt1.setName(_billingTierName + "concurrency");
            BillingTier bt2 = _adminService.createBillingTier(bt1);
            BillingTier bt = _adminService.retrieveBillingTier(bt2.getId());

            billingTierObject1 = (BillingTier) deepCopy(bt);
            billingTierObject1.setDescription(_billingTierDesc + "TestingBillingTierupdateDesc");

            _adminService.updateBillingTier(billingTierObject1);

            billingTierObject2 = (BillingTier) deepCopy(bt);
            billingTierObject2.setDescription(_billingTierDesc + "TestingBillingTierDesc");
            _adminService.updateBillingTier(billingTierObject2);

            fail("Updating billingTier should thrown exception");
        } catch (ROIException e) {
           assertError(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION);
        } catch (Exception e) {
            fail("Updating billingTier should not thrown exception." + e.getMessage());
        }
    }

    public Object deepCopy(Object oldObj) throws Exception {

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            // serialize and pass the object
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

    /*
     * This test case for retrieveBillingTierByMediaTypeName
     */
    public void testRetrieveBillingTiersByMediaTypeName() {

        try {

            BillingTiersList btList = _adminService.
                                        retrieveBillingTiersByMediaTypeName(
                                                ROIConstants.ELECTRONIC_MEDIATYPE);
            assertTrue(btList.getBillingTiers().size() > 0);
        } catch (ROIException e) {
            fail("RetrieveBillingTiersByMediaTypeName should not thrown exception."
                    + e.getErrorCode());
        }
    }

    /*
     * This test case for retrieveBillingTierByMediaTypeName and verify if it returns the
     * appropriate exception
     */
    public void testRetrieveBillingTiersByMediaTypeNameForInvalidName() {

        try {

            BillingTiersList btList = _adminService.retrieveBillingTiersByMediaTypeName(null);
            fail("RetrieveBillingTiersByMediaTypeName with null is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TIER_OPERATION_FAILED);
        }
    }

    /**
     * This test case for update electronic billingTier
     * associated with requestorType
     * Exception
     */
    public void testUpdateRequestorAssociatedElectronicBillingTier() {

        try {

            BillingTier billingTier = constructBillingTier();
            billingTier.setName("Requestor" + System.currentTimeMillis());
            BillingTier bTier = _adminService.createBillingTier(billingTier);
            long id = bTier.getId();

            _roiService.createRequestorType(createRequestorType(id));

            final long mtId = -2;
            MediaType mt = new MediaType();
            mt.setId(mtId);

            billingTier.setMediaType(mt);

            _adminService.updateBillingTier(billingTier);

            fail("Update electronic billitier associate with requestortype is not permitted, "
                  + "but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.
                         ELECTRONIC_BILLING_TIER_ASSOCIATED_TO_REQUESTOR_TYPE_CANNOT_BE_MODIFIED);
        }
    }

    private BillingTier constructBillingTier() {

        BillingTier billingTier = new BillingTier();

        billingTier.setName(_billingTierName);
        billingTier.setDescription(_billingTierDesc);

        long mtId = -1;
        MediaType mt = new MediaType();
        mt.setId(mtId);
        billingTier.setMediaType(mt);

        final float bc = 11;
        billingTier.setBaseCharge(bc);
        final float dbc = 10;
        billingTier.setDefaultPageCharge(dbc);

        PageLevelTier bTD = new PageLevelTier();
        bTD.setPage(1);
        final float pc = 12;
        bTD.setPageCharge(pc);
        _bTDetails = new HashSet<PageLevelTier>();
        _bTDetails.add(bTD);
        billingTier.setPageLevelTier(_bTDetails);

        return billingTier;
    }

    private RequestorType createRequestorType(long id) {

        RequestorType rt = new RequestorType();

        rt.setName("requestorTypeName" + System.currentTimeMillis());
        rt.setDescription("requestorTypeDesc" + System.currentTimeMillis());
        rt.setRv("rv" + System.currentTimeMillis());
        rt.setRvDesc("rvDesc" + System.currentTimeMillis());

        _rtBTier = new HashSet<RelatedBillingTier>();
        RelatedBillingTier hpfBillingTier = new RelatedBillingTier();
        hpfBillingTier.setBillingTierId(HPF_BILLINGTIER_ID);
        hpfBillingTier.setIsHPF(true);
        hpfBillingTier.setIsHECM(false);
        hpfBillingTier.setIsCEVA(false);
        hpfBillingTier.setIsSupplemental(false);

        RelatedBillingTier nonHpfBillingTier = new RelatedBillingTier();
        nonHpfBillingTier.setBillingTierId(id);
        nonHpfBillingTier.setIsHPF(false);
        nonHpfBillingTier.setIsHECM(false);
        nonHpfBillingTier.setIsCEVA(false);
        nonHpfBillingTier.setIsSupplemental(true);

        _rtBTier.add(hpfBillingTier);
        _rtBTier.add(nonHpfBillingTier);

        rt.setRelatedBillingTier(_rtBTier);

        return rt;
    }
    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }
}
