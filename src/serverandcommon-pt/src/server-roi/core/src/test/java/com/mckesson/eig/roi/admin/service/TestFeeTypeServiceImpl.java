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
import java.util.Set;

import com.mckesson.eig.roi.admin.model.BillingTemplate;
import com.mckesson.eig.roi.admin.model.FeeType;
import com.mckesson.eig.roi.admin.model.FeeTypesList;
import com.mckesson.eig.roi.admin.model.RelatedFeeType;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.test.BaseROITestCase;


/**
 * This class for testing all methods
 *
 * @author ganeshramr
 * @date    Apr 13, 2009
 * @since  HPF 13.1 [ROI]Apr 4, 2008
 */
public class TestFeeTypeServiceImpl
extends BaseROITestCase {

    protected static final String  ADMIN_SERVICE =
        "com.mckesson.eig.roi.admin.service.BillingAdminServiceImpl";

    private static Set<RelatedFeeType> _relatedFeeTypeSet;
    private static BillingAdminService _adminService;
    private static String              _feeTypeName;
    private static String              _feeTypeInvalidName;
    private static String              _feeTypeDesc;

    public void initializeTestData()
    throws Exception {

        _feeTypeName = "FeeName";
        _feeTypeInvalidName = "@#$%";
        _feeTypeDesc = "FeeDesc.";
        _adminService = (BillingAdminService) getService(ADMIN_SERVICE);
    }

    /**
     * This test case is for creating the fee type and verifying if it returns the newly generated
     * id .
     */
    public void testCreateFeeType() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(_feeTypeName + System.nanoTime());
            feeType.setDescription(_feeTypeDesc + System.nanoTime());
            final double ca = 234.45;
            feeType.setChargeAmount(ca);

            long id = _adminService.createFeeType(feeType);
            FeeType ft = _adminService.retrieveFeeType(id);
            assertEquals(id, ft.getId());
            assertEquals(feeType.getName(), ft.getName());
            assertEquals(feeType.getDescription(), ft.getDescription());
            assertEquals(feeType.getChargeAmount(), ft.getChargeAmount());
            assertNotNull(ft);
            assertNotSame("Created feetype id should be greater than zero", 0, id);
        } catch (ROIException e) {
            fail("Creating feetype should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case is for creating the fee type and verifying if it returns the newly generated
     * id .
     */
    public void testCreateFeeTypeWithInvalidData() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(appendString(_feeTypeInvalidName));
            feeType.setDescription(appendString(_feeTypeDesc + System.nanoTime()));
            final double ca = 234.45;
            feeType.setChargeAmount(ca);
            long id = _adminService.createFeeType(feeType);
            fail("Creating feetype with invalid data not allowed.");
        } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.FEE_TYPE_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.FEE_TYPE_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.FEE_TYPE_DESC_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case is for creating the fee type with duplicate name and verifying if it returns
     * the appropriate ROI Exception .
     */
    public void testCreateFeeTypeWithDuplicateName() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(_feeTypeName + System.nanoTime());
            feeType.setDescription(_feeTypeDesc + System.nanoTime());
            final double ca = 234.0;
            feeType.setChargeAmount(ca);

            long id = _adminService.createFeeType(feeType);

            FeeType feeType1 = _adminService.retrieveFeeType(id);

            FeeType fee = new FeeType();
            fee.setName(feeType1.getName());
            fee.setDescription(_feeTypeDesc + System.nanoTime());
            final double amount = 235.0;
            fee.setChargeAmount(amount);

            long id1 = _adminService.createFeeType(fee);

            fail("Creating feetype with duplicate name is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FEE_TYPE_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * This test case is for creating the fee type without name and verifying if it returns the
     * appropriate ROI Exception .
     */
    public void testCreateFeeTypeWithoutName() {

        try {

            FeeType feeType = new FeeType();
            feeType.setDescription(_feeTypeDesc + System.nanoTime());
            final double ca = 111;
            feeType.setChargeAmount(ca);

            _adminService.createFeeType(feeType);
            fail("Creating feetype without name is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FEE_TYPE_NAME_IS_BLANK);
        }
    }

    /**
     * This test case is for creating the fee type without description
     * the appropriate ROI Exception .
     */
    public void testCreateFeeTypeWithoutDesc() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(_feeTypeName + System.nanoTime() + "without_desc");
            feeType.setDescription("");
            assertNotSame("Created id for feetype without desc should be greater than zero",
                           0,
                           _adminService.createFeeType(feeType));

        } catch (ROIException e) {
            fail("creating feeType without description should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * This test case is for creating the fee type null description
     * the appropriate ROI Exception .
     */
    public void testCreateFeeTypeNullDesc() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(_feeTypeName + System.nanoTime() + "null_desc");
            assertNotSame("Created id for feetype without desc should be greater than zero",
                           0,
                           _adminService.createFeeType(feeType));

        } catch (ROIException e) {
            fail("Creating feetype should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case is for creating the fee type with null and verifying if it returns the
     * appropriate ROI Exception .
     */
    public void testCreateFeeTypeWithNull() {

        try {

            _adminService.createFeeType(null);
            fail("Creating fee type with null is not permitted,, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FEE_TYPE_PROCESS_FAILED);
        }
    }

    /**
     * This test case check the fee type name length
     */
    public void testCreateFeeTypeForMoreLengthName() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(_feeTypeName + System.nanoTime()
                            + new StringBuffer().append("TEST NAME TEST NEMA TEST NAME TEST NAME")
                              .append("TEST NAME TEST NEMA TEST NAME TEST NAMETEST NAME TEST NAME")
                              .append("TEST NAME TEST NAMETEST NAME TEST NAMETEST NAME TEST NAME")
                              .append("TEST NAME TEST NAMETEST NAME TEST NAMETEST NAME TEST NAME")
                              .append("TEST NAME TEST NAMETEST NAME TEST NAMETEST NAME TEST NAME")
                              .append("TEST NAME TEST NAMETEST NAME TEST NAMETEST NAME TEST NAME")
                              .toString());

            feeType.setDescription(_feeTypeDesc + System.nanoTime());
            final double ca = 234.90;
            feeType.setChargeAmount(ca);

            _adminService.createFeeType(feeType);
            fail("Creating feetype with name length exceeded is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FEE_TYPE_NAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case is for creating the fee type with more amount length and verifying if it
     * returns the appropriate exception
     */
    public void testCreateFeeTypeChargeAmtWrongPrecision() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(_feeTypeName + System.nanoTime() + "Update");
            feeType.setDescription(_feeTypeDesc + System.nanoTime());
            final double ca = 334.1243;
            feeType.setChargeAmount(ca);

            long id = _adminService.createFeeType(feeType);
            fail("Creating feeType with invalid charge amount is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FEE_TYPE_INVALID_CHARGE_AMOUNT);
        }
    }

    /**
     * This test case is for creating the fee type with more amount length in prefix and
     * verifying if it returns the appropriate exception
     */
    public void testCreateFeeTypeChargeAmt() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(_feeTypeName + System.nanoTime() + "Update");
            feeType.setDescription(_feeTypeDesc + System.nanoTime());
            final double ca = 123454.12;
            feeType.setChargeAmount(ca);

            long id = _adminService.createFeeType(feeType);
            fail("Creating feetype with invalid charge amount is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FEE_TYPE_INVALID_CHARGE_AMOUNT);
        }
    }

    /**
     * This test case is for creating the fee type with more amount length and verifying if it
     * returns the appropriate ROI Exception
     */
    public void testCreateFeeTypeChargeAmtPrecision() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(_feeTypeName + System.nanoTime() + "validate");
            feeType.setDescription(_feeTypeDesc + System.nanoTime());
            final double ca = .12;
            feeType.setChargeAmount(ca);

            long id = _adminService.createFeeType(feeType);
            FeeType ft = _adminService.retrieveFeeType(id);
            assertNotNull(ft);
            assertEquals(feeType.getName(), ft.getName());
            assertEquals(feeType.getDescription(), ft.getDescription());
            assertEquals(feeType.getChargeAmount(), ft.getChargeAmount());
            assertNotSame("Created id for feetype should be greater than zero", 0, id);
        } catch (ROIException e) {
            fail("Creating feetype should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case is for creating the fee type with more amount length and verifying if it
     * returns the appropriate ROI Exception
     */
    public void testCreateFeeTypeChargeAmtPres() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(_feeTypeName + System.nanoTime() + "validate");
            feeType.setDescription(_feeTypeDesc + System.nanoTime());
            final double ca = 1234;
            feeType.setChargeAmount(ca);

            long id = _adminService.createFeeType(feeType);
            fail("Creating feeType with invalid amount precision not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FEE_TYPE_INVALID_CHARGE_AMOUNT);
        }
    }

    /**
     * This test case is for creating the fee type with null user
     */
    public void testCreateFeeTypeWithNullUser() {

        try {

            initSession(null);
            FeeType feeType = new FeeType();
            feeType.setName(_feeTypeName + System.nanoTime() + "Update1");
            feeType.setDescription(_feeTypeDesc + System.nanoTime());
            final double ca = 234.50;
            feeType.setChargeAmount(ca);

            long id = _adminService.createFeeType(feeType);
            fail("Creating the feetype with null user is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FEE_TYPE_PROCESS_FAILED);
        }
    }

    /**
     * This test case for deleting the fee type with feeTypeId
     */
    public void testDeleteFeeType() {

        try {

            initSession();
            FeeType feeType = new FeeType();
            feeType.setName(_feeTypeName + System.nanoTime() + "one");
            feeType.setDescription(_feeTypeDesc + System.nanoTime());
            final double ca = 124.30;
            feeType.setChargeAmount(ca);
            long id = _adminService.createFeeType(feeType);

            _adminService.deleteFeeType(id);
            _adminService.retrieveFeeType(id);
            fail("Retrieving the feetype with deleted id is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This test case for deleting the fee type with feeTypeId with null user
     */
    public void testDeleteFeeTypeWithNullUser() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(_feeTypeName + System.nanoTime() + "delete");
            feeType.setDescription(_feeTypeDesc + System.nanoTime());
            final double ca = 234.45;
            feeType.setChargeAmount(ca);

            long id  = _adminService.createFeeType(feeType);
            initSession(null);
            _adminService.deleteFeeType(id);
            fail("Deleting the feetype with null user is not permitted, but deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FEE_TYPE_PROCESS_FAILED);
        }
    }

    /**
     * This test case for deleting the fee type with invalid feeTypeId and verifying if it returns
     * the appropriate ROI Exception
     */
    public void testDeleteFeeTypeWithInvalidId() {

        try {

            initSession();
            _adminService.deleteFeeType(Integer.MAX_VALUE);
            fail("Deleting the feetype with invalid id is not permitted, but deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This test case for deleting the fee type AssociatedwithBillingTemplate and
     *  verifying if it returns
     * the appropriate ROI Exception
     */
    public void testDeleteFeeTypeAssociatedwithBillingTemplate() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(_feeTypeName + System.currentTimeMillis());
            feeType.setDescription(_feeTypeDesc + System.nanoTime());
            final double ca = 234.50;
            feeType.setChargeAmount(ca);
            long feeTypeId = _adminService.createFeeType(feeType);

            BillingTemplate bt = new BillingTemplate();
            bt.setName("BTName" + System.currentTimeMillis());
            RelatedFeeType relatedFeeType = new RelatedFeeType();
            relatedFeeType.setFeeTypeId(feeType.getId());

            _relatedFeeTypeSet = new HashSet <RelatedFeeType>();
            _relatedFeeTypeSet.add(relatedFeeType);
            bt.setRelatedFeeTypes(_relatedFeeTypeSet);

            long bTId = _adminService.createBillingTemplate(bt);

            _adminService.deleteFeeType(feeTypeId);
            fail("Deleting the feetype associated with billing template is not permitted, "
        		 + "but deleted");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FEE_TYPE_ASSOCIATED_WITH_BILLING_TEMPLATE);
        }
    }

    /**
     * This test case for Get the fee type with feeTypeId
     */
    public void testRetrievetFeeType() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(_feeTypeName + System.nanoTime() + "retrieve");
            feeType.setDescription(_feeTypeDesc + System.nanoTime() + "test");
            final double cha = 987;
            feeType.setChargeAmount(cha);
            long feeTypeId = _adminService.createFeeType(feeType);

            FeeType ft = _adminService.retrieveFeeType(feeTypeId);

            assertEquals(ft.getId(), feeTypeId);
            assertEquals(false, ft.isAssociated());
        } catch (ROIException e) {
            fail("Retrieving the feetype should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case for Retrieve the fee type with InvalidfeeTypeId and verifying if it returns
     * the appropriate ROI Exception .
     */
    public void testRetrieveFeeTypeWithInvalidId() {

        try {

            final long feeTypeId = -100;
            assertEquals(_adminService.retrieveFeeType(feeTypeId).getId(), feeTypeId);
            fail("Retrieving the feetype with invalid id is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This test case for retrieve All fee type
     */
    public void testRetrieveAllFeeTypes() {

        try {

            FeeTypesList fl = new FeeTypesList();
            fl = _adminService.retrieveAllFeeTypes(true);
            assertNotSame("The size of the retrieved fee types should be greater than zero",
                           0,
                           fl.getFeeTypesList().size());

        } catch (ROIException e) {
            fail("Retrieving all feetypes should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case for Update the fee type.
     */
    public void testUpdateFeeType() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(_feeTypeName + System.nanoTime() + "Update");
            feeType.setDescription(_feeTypeDesc + System.nanoTime());
            final double ca = 234.50;
            feeType.setChargeAmount(ca);
            long feeTypeId = _adminService.createFeeType(feeType);

            FeeType ft = _adminService.retrieveFeeType(feeTypeId);
            ft.setName("UPDATE FEETYPE" + System.currentTimeMillis());
            ft.setDescription("UPDATE THE FEETYPE DESCRIPTION");
            FeeType updatedfeeType = _adminService.updateFeeType(ft);

            FeeType ft1 = _adminService.retrieveFeeType(updatedfeeType.getId());
            ft1.setName(_feeTypeName + "FEETYPE" + System.nanoTime());
            FeeType ft2 = _adminService.updateFeeType(ft1);

            assertNotSame(ft1.getRecordVersion(), ft2.getRecordVersion());
            assertEquals(updatedfeeType.getChargeAmount(), feeType.getChargeAmount());
            assertEquals(updatedfeeType.getName(), ft.getName());
        } catch (ROIException e) {
            fail("Updating feetype should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case for Update the fee type with invalid data.
     */
    public void testUpdateFeeTypeWithInvalidData() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(appendString(_feeTypeInvalidName));
            feeType.setDescription(appendString(_feeTypeDesc + System.nanoTime()));
            final long ca = 345;
            feeType.setChargeAmount(ca);
            FeeType updatedfeeType = _adminService.updateFeeType(feeType);
            fail("Updating feetype with invalid data not allowed.");
        } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.FEE_TYPE_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.FEE_TYPE_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.FEE_TYPE_DESC_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case for Update the fee type with null user.
     */
    public void testUpdateFeeTypeWithNullUser() {

        try {

            initSession(null);
            FeeType feeType = new FeeType();
            feeType.setName("UPDATED" + System.currentTimeMillis());
            feeType.setDescription("UPDATED FEETYPE DESCRIPTION");
            final long ca = 345;
            feeType.setChargeAmount(ca);

            FeeType updatedfeeType = _adminService.updateFeeType(feeType);
            fail("Updating the feetype with null user is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FEE_TYPE_PROCESS_FAILED);
        }
    }

    /**
     * This test case for Update the fee type with InvalidId and verifying if it returns the
     * appropriate ROI Exception
     */
    public void testUpdateFeeTypeWithInvalidId() {

        try {

            initSession();
            final long id = -111;
            FeeType feeType = new FeeType();
            feeType.setId(id);
            feeType.setName("UPDATE FEETYPE" + System.currentTimeMillis());
            feeType.setDescription("UPDATE FEETYPE DESCRIPTION");
            final double ca = 542;
            feeType.setChargeAmount(ca);

            _adminService.updateFeeType(feeType);
            fail("Updating the feetype with invalid id is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This test case for Update the fee type with DuplicateName and verifying if it returns the
     * appropriate ROI Exception
     */
    public void testUpdateFeeTypeWithDuplicateName() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(_feeTypeName + System.nanoTime() + "Update");
            feeType.setDescription(_feeTypeDesc + System.nanoTime());
            final double ca = 234.50;
            feeType.setChargeAmount(ca);

            long id = _adminService.createFeeType(feeType);

            final long sleepTime = 30000;
            Thread.sleep(sleepTime);

            FeeType feeType2 = new FeeType();
            feeType2.setName(_feeTypeName + System.nanoTime());
            feeType2.setDescription(_feeTypeDesc + System.nanoTime());
            final double ca2 = 234.50;
            feeType2.setChargeAmount(ca2);

            long id2 = _adminService.createFeeType(feeType2);
            FeeType feeType1 = _adminService.retrieveFeeType(id);
            feeType1.setName(feeType2.getName());
            feeType1.setDescription("UPDATE THE FEETYPE DESCRIPTION");
            final double ca1 = 679.90;
            feeType1.setChargeAmount(ca1);

            _adminService.updateFeeType(feeType1);
            fail("Updating the feetype with duplicate name is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FEE_TYPE_NAME_IS_NOT_UNIQUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This test case for Update the fee type with Name Null and verifying if it returns the
     * appropriate ROI Exception
     */
    public void testUpdateFeeTypeWithNameNull() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(null);
            feeType.setDescription("UPDATE THE FEETYPE DESCRIPTION");
            final double ca = 122.80;
            feeType.setChargeAmount(ca);

            _adminService.updateFeeType(feeType);
            fail("Updating the feetype with name null is not permitted, but updated");
        } catch (ROIException e) {
           assertError(e, ROIClientErrorCodes.FEE_TYPE_NAME_IS_BLANK);
        }
    }

    /**
     * This test case is for updating the fee type name with exceeded length.
     */
    public void testUpdateFeeTypeWithNameMoreSize() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(_feeTypeName + System.nanoTime()
                            + new StringBuffer().append("TEST NAME TEST NAME TEST NAME")
                              .append("TEST NAME TEST NAME TEST NAME TEST NAME TEST NAME")
                              .append("TEST NAME TEST NAME TEST NAME TEST NAME TEST NAME")
                              .append("TEST NAME TEST NAME TEST NAME TEST NAME TEST NAME")
                              .append("TEST NAME TEST NAME TEST NAME TEST NAME TEST NAME")
                              .append("TEST NAME TEST NAME TEST NAME TEST NAME TEST NAME")
                              .toString());

            feeType.setDescription(_feeTypeDesc);
            final double ca = 242.80;
            feeType.setChargeAmount(ca);

            _adminService.updateFeeType(feeType);
            fail("Updating the feetype with more name length is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FEE_TYPE_NAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case is for updating the given fee type with empty.
     */
    public void testUpdateFeeTypeWithSpace() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName("  ");
            feeType.setDescription(_feeTypeDesc + System.nanoTime());
            _adminService.updateFeeType(feeType);

           fail("Updating the feetype with name empty is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FEE_TYPE_NAME_IS_BLANK);
        }
    }

    /**
     * This test case for Update the fee type with Name Null and verifying if it returns the
     * appropriate ROI Exception
     */
    public void testUpdateFeeTypeWithNameEmpty() {

        try {

            FeeType feeType = new FeeType();
            feeType.setDescription("UPDATE THE FEETYPE DESCRIPTION");
            final double ca = 424;
            feeType.setChargeAmount(ca);

            _adminService.updateFeeType(feeType);
            fail("Updating the feetype with name blank is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FEE_TYPE_NAME_IS_BLANK);
        }
    }

    /**
     * This test case is for creating the fee type with null and verifying if it returns the
     * appropriate ROI Exception .
     */
    public void testupdateFeeTypeWithNull() {

        try {
            _adminService.updateFeeType(null);
            fail("Updating the feetype with null is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.FEE_TYPE_PROCESS_FAILED);
        }
    }

    private static FeeType _feeTypeobj1;
    private static FeeType _feeTypeobj2;

    public void testConcurrencyForUpdate() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName(_feeTypeName + System.nanoTime() + "con");
            feeType.setDescription(_feeTypeDesc + System.nanoTime());
            final double ca = 12.90;
            feeType.setChargeAmount(ca);

            long id = _adminService.createFeeType(feeType);

            FeeType feetype = _adminService.retrieveFeeType(id);

            _feeTypeobj1 = (FeeType) deepCopy(feetype);
            _feeTypeobj1.setDescription("DESCRIPTION");
            final double ca1 = 242.80;
            _feeTypeobj1.setChargeAmount(ca1);
            _adminService.updateFeeType(_feeTypeobj1);

            _feeTypeobj2 = (FeeType) deepCopy(feetype);
            _feeTypeobj2.setDescription("testdescription");

            _adminService.updateFeeType(_feeTypeobj2);

            fail("Updating the feetype concurrently updated by anothter user is not permitted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION);
        } catch (Exception e) {
            fail("Update feetype should not thrown exception." + e.getMessage());
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

    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }
}
