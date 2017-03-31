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

import com.mckesson.eig.roi.admin.model.BillingTemplate;
import com.mckesson.eig.roi.admin.model.BillingTemplatesList;
import com.mckesson.eig.roi.admin.model.FeeType;
import com.mckesson.eig.roi.admin.model.FeeTypesList;
import com.mckesson.eig.roi.admin.model.MediaType;
import com.mckesson.eig.roi.admin.model.RelatedFeeType;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.test.BaseROITestCase;


/**
 * @author Vidhya.C.S
 * @date Mar 31, 2009
 * @since HPF 13.1 [ROI]
 */
public class TestBillingTemplateServiceImpl
extends BaseROITestCase {

    protected static final String ADMIN_SERVICE =
        "com.mckesson.eig.roi.admin.service.BillingAdminServiceImpl";

    private static BillingAdminService  _adminService;
    private static String               _billingTemplateName;
    private static String               _billingTemplateInvalidName;

    private static BillingTemplate     _billingTemplateObj1;
    private static BillingTemplate     _billingTemplateObj2;
    private static RelatedFeeType      _btf;
    private static FeeTypesList        _feeTypesList;
    private static Set<RelatedFeeType> _btfSet;

    private static final int NAME_MAX_LENGTH = 256;
    private static final int CHILD_COUNT     = 3;

    public void initializeTestData()
    throws Exception {

        _billingTemplateName = "BillingName";
        _billingTemplateInvalidName = "$#?";
        _adminService = (BillingAdminService) getService(ADMIN_SERVICE);
        createFeeTypeForTesting();
        _feeTypesList = _adminService.retrieveAllFeeTypes(false);
    }

    /**
     * Tests creation of Billing Template
     */
    public void testCreateBillingTemplate() {

        try {

            FeeType feeType = _feeTypesList.getFeeTypesList().iterator().next();

            BillingTemplate billingTemplate = new BillingTemplate();
            billingTemplate.setName(_billingTemplateName + System.nanoTime());

            _btfSet = new HashSet<RelatedFeeType>();
            RelatedFeeType btf = new RelatedFeeType();
            btf.setFeeTypeId(feeType.getId());
            _btfSet.add(btf);

            billingTemplate.setRelatedFeeTypes(_btfSet);

            long id = _adminService.createBillingTemplate(billingTemplate);
            BillingTemplate temp = _adminService.retrieveBillingTemplate(id);
            assertNotSame("The created billing template id should be greater than zero", 0, id);
            assertEquals(billingTemplate.getName(), temp.getName());
            assertNotNull(temp);
        } catch (ROIException e) {
            fail("Creating billing template should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests creation of Billing Template with Invalid Data
     */
    public void testCreateBillingTemplateWithInvalidData() {

        try {

            FeeType feeType = _feeTypesList.getFeeTypesList().iterator().next();

            BillingTemplate billingTemplate = new BillingTemplate();
            billingTemplate.setName(appendString(_billingTemplateInvalidName));
            _btfSet = new HashSet<RelatedFeeType>();
            RelatedFeeType btf = new RelatedFeeType();
            btf.setFeeTypeId(feeType.getId());
            _btfSet.add(btf);

            billingTemplate.setRelatedFeeTypes(_btfSet);

            _adminService.createBillingTemplate(billingTemplate);
            fail("Creating billing template with invalid data not allowed");
        } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.BILLING_TEMPLATE_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.BILLING_TEMPLATE_NAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * Tests creation of Billing Template
     */
    public void testRelatedFeeType() {

        try {

            FeeType feeType = _feeTypesList.getFeeTypesList().iterator().next();
            RelatedFeeType btf;

            BillingTemplate billingTemplate = new BillingTemplate();
            billingTemplate.setName(_billingTemplateName + System.nanoTime());

            _btfSet = new HashSet<RelatedFeeType>();
            btf = new RelatedFeeType();
            btf.setFeeTypeId(feeType.getId());
            _btfSet.add(btf);

            _btfSet = new HashSet<RelatedFeeType>();
            btf = new RelatedFeeType();
            btf.setFeeTypeId(feeType.getId());
            btf.equals(btf);
            btf.equals(new MediaType());
            btf.equals(null);
            _btfSet.add(btf);

            billingTemplate.setRelatedFeeTypes(_btfSet);

            long id = _adminService.createBillingTemplate(billingTemplate);
            BillingTemplate bt = _adminService.retrieveBillingTemplate(id);
            assertNotSame("The created billing template id should be greater than zero", 0, id);
            assertEquals(billingTemplate.getName(), bt.getName());
            assertNotNull(bt);

        } catch (ROIException e) {
            fail("Creating billing template should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests creation of Billing Template without Association
     */
    public void testCreateBillingTemplateWithoutAssociation() {

        try {

            BillingTemplate billingTemplate = new BillingTemplate();
            billingTemplate.setName(_billingTemplateName + System.nanoTime());

            long id = _adminService.createBillingTemplate(billingTemplate);
            BillingTemplate bt = _adminService.retrieveBillingTemplate(id);
            assertNotSame("The created billing template id should be greater than zero", 0, id);
            assertEquals(billingTemplate.getName(), bt.getName());
            assertNotNull(bt);

        } catch (ROIException e) {
            fail("Creating billing template without association should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * Tests creation of Billing Template By Setting One Or More Child
     */
    public void testCreateBillingTemplateWithMoreChild() {

        try {

            BillingTemplate billingTemplate = new BillingTemplate();
            RelatedFeeType btf;
            billingTemplate.setName(_billingTemplateName + System.nanoTime());
            _btfSet.clear();

            for (int count = 0; (count < CHILD_COUNT)
                                && (count < _feeTypesList.getFeeTypesList().size()); count++) {

                FeeType feeType = _feeTypesList.getFeeTypesList().get(count);
                btf = new RelatedFeeType();
                btf.setFeeTypeId(feeType.getId());
                _btfSet.add(btf);
            }

            billingTemplate.setRelatedFeeTypes(_btfSet);
            long id = _adminService.createBillingTemplate(billingTemplate);
            BillingTemplate bt = _adminService.retrieveBillingTemplate(id);
            assertNotSame("The created billing template id should be greater than zero", 0, id);
            assertEquals(billingTemplate.getName(), bt.getName());
            assertNotNull(bt);

        } catch (ROIException e) {
            fail("Creating billing template should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests creation of duplicate Billing Template
     * @throws Exception
     */
    public void testCreateDuplicateBillingTemplate()
    throws Exception {

        try {

            long createdBillingTemplateId = createBillingTemplateForTest();
            BillingTemplate bt = _adminService.retrieveBillingTemplate(createdBillingTemplateId);
            RelatedFeeType btf = new RelatedFeeType();

            FeeType feeType = _feeTypesList.getFeeTypesList().iterator().next();

            BillingTemplate billingTemplate = new BillingTemplate();
            billingTemplate.setName(bt.getName());

            _btfSet = new HashSet <RelatedFeeType>();
            btf.setFeeTypeId(feeType.getId());
            _btfSet.add(btf);

            billingTemplate.setRelatedFeeTypes(_btfSet);

            _adminService.createBillingTemplate(billingTemplate);
            fail("Creation of billing template with duplicate name is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TEMPLATE_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * Tests creation of  Billing Template name as blank
     */
    public void testCreateBillingTemplateNameAsBlank() {

        try {

            FeeType feeType = _feeTypesList.getFeeTypesList().iterator().next();

            BillingTemplate billingTemplate = new BillingTemplate();
            RelatedFeeType btf = new RelatedFeeType();

            _btfSet = new HashSet <RelatedFeeType>();
            btf.setFeeTypeId(feeType.getId());
            _btfSet.add(btf);

            billingTemplate.setRelatedFeeTypes(_btfSet);

            _adminService.createBillingTemplate(billingTemplate);
            fail("Creation of billing template with name as blank is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TEMPLATE_NAME_IS_BLANK);
        }
    }

    /**
     * This method is to create billing template with invalid fee type id
     */
    public void testCreateBillingTemplateWithInvalidFeeTypeId() {

        try {

            FeeType feeType = new FeeType();
            feeType.setId(Integer.MAX_VALUE);

            BillingTemplate billingTemplate = new BillingTemplate();
            RelatedFeeType btf = new RelatedFeeType();
            billingTemplate.setName(_billingTemplateName + System.nanoTime());

            _btfSet = new HashSet <RelatedFeeType>();
            btf.setFeeTypeId(feeType.getId());
            _btfSet.add(btf);

            billingTemplate.setRelatedFeeTypes(_btfSet);

            _adminService.createBillingTemplate(billingTemplate);
            fail("Creation of billing template with invalid feetype is not permitted,but created");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION);
        }
    }

    /**
     * Tests creation of Billing Template with an invalid name
     */
    public void testCreateBillingTemplateWithInvalidLength() {

        try {

            FeeType feeType = _feeTypesList.getFeeTypesList().iterator().next();
            long fId = feeType.getId();

            StringBuffer s1 = new StringBuffer();
            for (int i = 0; i <= NAME_MAX_LENGTH; i++) {
                s1.append("TestName");
            }

            BillingTemplate billingTemplate = new BillingTemplate();
            RelatedFeeType btf = new RelatedFeeType();

            billingTemplate.setName(s1.toString());

            _btfSet = new HashSet <RelatedFeeType>();
            btf.setFeeTypeId(feeType.getId());
            _btfSet.add(btf);

            billingTemplate.setRelatedFeeTypes(_btfSet);

            _adminService.createBillingTemplate(billingTemplate);
            fail("Creation of billing template with invalid length for name is not permitted, "
                 + "but created");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TEMPLATE_NAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * Tests creation of billing template as Null
     */
    public void testCreateBillingTemplateAsNull() {

        try {

            _adminService.createBillingTemplate(null);
            fail("Creation of billing template with null is not permitted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TEMPLATE_PROCESS_FAILED);
        }
    }

    /**
     * This test case for retrieving the billing template with billingTemplateId
     */
    public void testRetrieveBillingTemplate() {

        try {

            long btId = createBillingTemplateForTest();
            BillingTemplate retrievedBT = _adminService.retrieveBillingTemplate(btId);
            retrievedBT.isAssociated();
            assertEquals(retrievedBT.getId(), btId);
        } catch (Exception e) {
            fail("Retrieving billing template should not thrown exception." + e.getMessage());
        }
    }

    /**
     *  Tests retrieval of billing template with invalid id
     */
    public void testRetrieveBillingTemplateWithInvalidId() {

        try {

            _adminService.retrieveBillingTemplate(Integer.MAX_VALUE);
            fail("Retrieving billing temlate with invalid id is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This test case for retrieve All billing template
     * @throws Exception
     */
    public void testRetrieveAllBillingTemplate()
    throws Exception {

        try {

            BillingTemplatesList billingTemplateList = new BillingTemplatesList();
            billingTemplateList = _adminService.retrieveAllBillingTemplates(true);
            assertTrue(billingTemplateList.getBillingTemplates().size() > 0);
            assertNotSame("The size of the retrieved billingtemplate should be greater than zero",
                          0,
                          billingTemplateList.getBillingTemplates().size());
        } catch (ROIException e) {
            fail("RetrieveAllBillingTemplate should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case for retrieve All billing template
     * @throws Exception
     */
    public void testRetrieveAllBillingTemplateNames()
    throws Exception {

        try {

            BillingTemplatesList billingTemplateList = new BillingTemplatesList();
            billingTemplateList = _adminService.retrieveAllBillingTemplates(false);
            assertTrue(billingTemplateList.getBillingTemplates().size() > 0);
            assertNotSame("The size of the retrieved billingtemplate should be greater than zero",
                          0,
                          billingTemplateList.getBillingTemplates().size());
        } catch (ROIException e) {
            fail("RetrieveAllBillingTemplate should not thrown exception."  + e.getErrorCode());
        }
    }


    /**
     * This test case for deleting the billing template with billingTemplateId
     */
    public void testDeleteBillingTemplate() {

        try {

            long bTId = createBillingTemplateForTest();
            _adminService.deleteBillingTemplate(bTId);
        } catch (Exception e) {
            fail("Delete BillingTemplate should have deleted the entries");
        }
    }

    /**
     * This test case for deleting the billing template with invalid billingTemplateId
     */
    public void testDeleteBillingTemplateWithInvalidId() {

        try {

            _adminService.deleteBillingTemplate(Integer.MAX_VALUE);
            fail("Deleting billing template with invalid id is not permitted, but accepted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This test case for Update the billing template.
     */
    public void testUpdateBillingTemplate() {

        try {

            long bTId = createBillingTemplateForTest();
            BillingTemplate bT = _adminService.retrieveBillingTemplate(bTId);

            Set<RelatedFeeType> temp = bT.getRelatedFeeTypes();

            for (Iterator <RelatedFeeType> i = temp.iterator(); i.hasNext();) {

                _btf = i.next();
                //for code coverage
                _btf.setBillingTemplateId(_btf.getBillingTemplateId());
            }

            temp.remove(_btf);
            _btfSet = temp;

            bT.setId(bTId);
            bT.setRelatedFeeTypes(_btfSet);
            bT.setName(_billingTemplateName + System.nanoTime());
            BillingTemplate updatedbT = _adminService.updateBillingTemplate(bT);

            BillingTemplate bt1 = _adminService.retrieveBillingTemplate(updatedbT.getId());
            bt1.setName(_billingTemplateName + System.nanoTime());
            BillingTemplate bt2 = _adminService.updateBillingTemplate(bt1);

            assertNotSame(bt1.getRecordVersion(), bt2.getRecordVersion());
            assertEquals(updatedbT.getName(), bT.getName());
            assertEquals(updatedbT.getRelatedFeeTypes().size(), bT.getRelatedFeeTypes().size());
        } catch (Exception e) {
            fail("Update billingtemplate should not thrown exception" + e.getMessage());
        }
    }

    /**
     * This test case for Update the billing template.
     * @throws Exception
     */
    public void testUpdateBillingTemplateWithInvalidData() throws Exception {

        try {

            long btId = createBillingTemplateForTest();
            BillingTemplate retrievedBT = _adminService.retrieveBillingTemplate(btId);

            Set<RelatedFeeType> temp = retrievedBT.getRelatedFeeTypes();

            for (Iterator <RelatedFeeType> i = temp.iterator(); i.hasNext();) {

                _btf = i.next();
                //for code coverage
                _btf.setBillingTemplateId(_btf.getBillingTemplateId());
            }

            temp.remove(_btf);
            _btfSet = temp;

            retrievedBT.setId(btId);
            retrievedBT.setRelatedFeeTypes(_btfSet);
            retrievedBT.setName(appendString(_billingTemplateInvalidName));

            _adminService.updateBillingTemplate(retrievedBT);
            fail("Update billingtemplate with invalid data is not permitted, but accepted.");
         } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.BILLING_TEMPLATE_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.BILLING_TEMPLATE_NAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case for Update the billing template with already existing name.
     */
    public void testUpdateBillingTemplateNameWithExistingName()
    throws Exception {

        try {

            long btId = createBillingTemplateForTest();
            BillingTemplate retrievedBT = _adminService.retrieveBillingTemplate(btId);

            BillingTemplate bt = new BillingTemplate();
            bt.setName(retrievedBT.getName());

            _adminService.updateBillingTemplate(bt);

            fail("Updating billing template with existing name is not permitted, but accepted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TEMPLATE_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * Tests update of billing template to null.
     */
    public void testUpdateBillingTemplateWithNull() {

        try {

            _adminService.updateBillingTemplate(null);
            fail("Should not update a NULL billing template object");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.BILLING_TEMPLATE_PROCESS_FAILED);
        }
    }

    /**
     * Tests concurrent Update of Billing Template
     */
    public void testConcurrentUpdateBillingTemplate() {

        try {

            long bTId = createBillingTemplateForTest();

            BillingTemplate retrievedBT = _adminService.retrieveBillingTemplate(bTId);

            _billingTemplateObj1 = (BillingTemplate) deepCopy(retrievedBT);
            _adminService.updateBillingTemplate(_billingTemplateObj1);

            _billingTemplateObj2 = (BillingTemplate) deepCopy(retrievedBT);

            _adminService.updateBillingTemplate(_billingTemplateObj2);
            fail("Updated stale Billing Template Object");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION);
        } catch (Exception e) {
            fail("General operation faillure" + e.getMessage());
        }
    }

    /**
     * Deep copies an object
     */
    private Object deepCopy(Object oldObj) throws Exception {

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(oldObj);
            oos.flush();
            ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bin);
            return ois.readObject();
        } catch (Exception e) {
            System.out.println("Exception in objectcloner = " + e);
            throw (e);
        } finally {
            oos.close();
            ois.close();
        }
    }

    /**
     *  creation of Billing Template
     */
    private long createBillingTemplateForTest()
    throws Exception {

        try {

            BillingTemplate billingTemplate = new BillingTemplate();
            RelatedFeeType btf;
            billingTemplate.setName(_billingTemplateName + System.nanoTime());
            _btfSet = new HashSet <RelatedFeeType>();
            for (int count = 0; (count < CHILD_COUNT)
                             && (count < _feeTypesList.getFeeTypesList().size()); count++) {

                FeeType feeType = _feeTypesList.getFeeTypesList().get(count);
                btf = new RelatedFeeType();
                btf.setFeeTypeId(feeType.getId());
                _btfSet.add(btf);
            }

            billingTemplate.setRelatedFeeTypes(_btfSet);

            long id = _adminService.createBillingTemplate(billingTemplate);
            return id;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }

    /**
     * Create a Fee Type
     */
    public void createFeeTypeForTesting() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName("FeeName" + System.nanoTime());
            feeType.setDescription("FeeDesc");
            final double ca = 234.45;
            feeType.setChargeAmount(ca);

            long id = _adminService.createFeeType(feeType);
        } catch (ROIException e) {
            fail("create feetype should not thrown exception" + e.getErrorCode());
        }
    }
}
