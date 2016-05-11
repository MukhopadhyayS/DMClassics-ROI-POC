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

import com.mckesson.eig.roi.admin.model.PaymentMethod;
import com.mckesson.eig.roi.admin.model.PaymentMethodList;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.test.BaseROITestCase;


/**
 *
 * @author ranjithr
 * @date    Mar 31, 2009
 * @since  HPF 13.1 [ROI]; Apr 16, 2008
 */
public class TestPaymentMethodServiceImpl
extends BaseROITestCase {

    protected static final String  ADMIN_SERVICE  =
        "com.mckesson.eig.roi.admin.service.BillingAdminServiceImpl";

    private static BillingAdminService _adminService;
    private static String              _paymentMethodName;
    private static String              _invalidPaymentMethodName;
    private static String              _paymentMethodDesc;
    private static PaymentMethod       _paymentMethodObj1;
    private static PaymentMethod       _paymentMethodObj2;

    public void initializeTestData()
    throws Exception {

        _paymentMethodName = "";
        _invalidPaymentMethodName = "!$%@?>";
        _paymentMethodDesc = "Desc.";
        _adminService = (BillingAdminService) getService(ADMIN_SERVICE);
    }

    /**
     * Tests creation of new Payment Method
     */
    public void testCreatePaymentMethod() {

        try {

            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(_paymentMethodName + System.nanoTime());
            paymentmethod.setDesc(_paymentMethodDesc + System.nanoTime());
            paymentmethod.setIsDisplay(true);

            long id = _adminService.createPaymentMethod(paymentmethod);
            assertNotSame("The created payment method id should be greater than zero", 0, id);

        } catch (ROIException e) {
            fail("Creating a new payment method should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests creation of new Payment Method
     */
    public void testCreatePaymentMethodWithInvalidData() {

        try {

            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(appendString(_invalidPaymentMethodName));
            paymentmethod.setDesc(appendString(_paymentMethodDesc));
            paymentmethod.setIsDisplay(true);

            _adminService.createPaymentMethod(paymentmethod);
            fail("Creating a new payment method invalid data not allowed, but it created");
        } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.PAYMENT_METHOD_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.PAYMENT_METHOD_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.PAYMENT_METHOD_DESC_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * Tests creation of new Payment Method with valid payment method name
     */
    public void testCreatePaymentMethodWithNameOnly() {

        try {

            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(_paymentMethodName + System.nanoTime());

            long id = _adminService.createPaymentMethod(paymentmethod);
            assertNotSame("The created payment method id should be greater than zero", 0, id);

        } catch (ROIException e) {
            fail("Creating new payment method should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests creation of new Payment Method blank Description
     */
    public void testCreatePaymentMethodWithDescAsBlank() {

        try {

            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(_paymentMethodName + System.nanoTime());
            paymentmethod.setDesc("");
            long id = _adminService.createPaymentMethod(paymentmethod);
            assertNotSame("The created payment method id should be greater than zero", 0, id);

        } catch (ROIException e) {
            fail("Creating payment method with blank description should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * Tests creation of duplicate payment method
     */
    public void testCreateDuplicatePaymentMethod() {

        try {

            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(_paymentMethodName + System.nanoTime());
            paymentmethod.setDesc(_paymentMethodDesc + System.nanoTime());
            paymentmethod.setIsDisplay(true);
            long id = _adminService.createPaymentMethod(paymentmethod);

            PaymentMethod retrievedPaymentMethod = _adminService.retrievePaymentMethod(id);

            PaymentMethod payment = new PaymentMethod();
            payment.setName(retrievedPaymentMethod.getName());
            paymentmethod.setDesc(_paymentMethodDesc + System.nanoTime());

            long id1 = _adminService.createPaymentMethod(paymentmethod);
            fail("Creation of payment method with duplicate name is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.PAYMENT_METHOD_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * Tests creation of payment method with payment method name as blank.
     */
    public void testCreatePaymentMethodWithBlankName() {

        try {

            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName("");
            paymentmethod.setDesc(_paymentMethodDesc + System.nanoTime());

            _adminService.createPaymentMethod(paymentmethod);
            fail("Creation of payment method with blank name is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.PAYMENT_METHOD_NAME_IS_BLANK);
        }
    }

    /**
     * Tests creation of payment method by passing null
     */
    public void testCreatePaymentMethodAsNull() {

        try {

            _adminService.createPaymentMethod(null);
            fail("Creation of payment method as null is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.PAYMENT_METHOD_OPERATION_FAILED);
        }
    }

    /**
     * Tests creation of Payment Method with an invalid length for name.
     */
    public void testCreatePaymentMethodWithInvalidNameLength() {

        try {

            StringBuffer s1 = new StringBuffer();
            for (int i = 0; i <= ROIConstants.PAY_METHOD_NAME_MAX_LENGTH; i++) {
                s1.append("Name");
            }

            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(s1.toString());
            paymentmethod.setDesc(_paymentMethodDesc + System.nanoTime());

            _adminService.createPaymentMethod(paymentmethod);
            fail("Creation of payment method with invalid length for name is not permitted, "
                 + "but created ");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.PAYMENT_METHOD_NAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * Tests creation of Payment Method with an invalid length for Desc.
     */
    public void testCreatePaymentMethodWithInvalidDescLength() {

        try {

            StringBuffer s1 = new StringBuffer();
            for (int i = 0; i <= ROIConstants.DESC_MAX_LENGTH; i++) {
                s1.append("Desc");
            }

            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(_paymentMethodName + System.nanoTime());
            paymentmethod.setDesc(s1.toString());

            _adminService.createPaymentMethod(paymentmethod);
            fail("Creation of payment method with invalid length for description is not permitted,"
                 + " but created");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.PAYMENT_METHOD_DESC_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * Tests creation of new Payment Method with null user
     */
    public void testCreatePaymentMethodWithNullUser() {

        try {

            initSession(null);
            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(_paymentMethodName + System.nanoTime());
            paymentmethod.setDesc(_paymentMethodDesc + System.nanoTime());
            paymentmethod.setIsDisplay(true);
            _adminService.createPaymentMethod(paymentmethod);
            fail("Creation of payment method with null user is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.PAYMENT_METHOD_OPERATION_FAILED);
        }
    }

    /**
     * Tests retrieval of a particular payment method
     */
    public void testRetrievePaymentMethod() {

        try {

            initSession();
            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(_paymentMethodName + System.nanoTime());
            paymentmethod.setDesc(_paymentMethodDesc + System.nanoTime());
            long id = _adminService.createPaymentMethod(paymentmethod);

            PaymentMethod retrievedPaymentMethod = _adminService.retrievePaymentMethod(id);

            assertEquals(retrievedPaymentMethod.getId(), id);
        } catch (ROIException e) {
            fail("Retrieving payment method should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests retrieve of all payment methods
     */
    public void testRetrieveAllPaymentMethods() {

        PaymentMethodList list = new PaymentMethodList();
        list = _adminService.retrieveAllPaymentMethods(true);
        assertNotSame("The size of retrieved payment method should be greater than zero",
                      0,
                      list.getPaymentMethods().size());
    }

    /**
     * Tests retrieve of all payment methods
     */
    public void testRetrieveAllPaymentMethodsWithFalse() {

        PaymentMethodList list = new PaymentMethodList();
        list = _adminService.retrieveAllPaymentMethods(false);
        assertNotSame("The size of retrieved payment method should be greater than zero",
                      0,
                      list.getPaymentMethods().size());
    }

    /**
     * Tests retrieve of payment method with id zero
     */
    public void testRetrievePaymentMethodWithIdAsZero() {

        try {

            final long id = 0;
            _adminService.retrievePaymentMethod(id);
            fail("Retrieving payment method by id as zero is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * Tests retrieval of delivery method with invalid id
     */
    public void testRetrieveDeliveryMethodWithInvalidId() {

        try {

            _adminService.retrievePaymentMethod(Integer.MAX_VALUE);
            fail("Retrieving payment method by invalid id is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * Tests delete of default payment method
     */
    public void testDeletePaymentMethod() {

        try {

            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(_paymentMethodName + System.nanoTime());
            paymentmethod.setDesc(_paymentMethodDesc + System.nanoTime());
            long id = _adminService.createPaymentMethod(paymentmethod);

            _adminService.deletePaymentMethod(id);

            PaymentMethod retrievedPaymentMethod =  _adminService.retrievePaymentMethod(id);

            fail("Retrieving the deleted payment method is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * Tests delete of an invalid id.
     */
    public void testDeletePaymentMethodWithIdAsZero() {

        try {

            final long id = 0;
            _adminService.deletePaymentMethod(id);
            fail("Payment method with id zero does not exist, but deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * Tests delete payment method with null user
     */
    public void testDeletePaymentMethodWithNullUser() {

        try {

            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(_paymentMethodName + System.nanoTime());
            long id = _adminService.createPaymentMethod(paymentmethod);

            initSession(null);
            _adminService.deletePaymentMethod(id);
            fail("Deleting the payment method with null user is not permitted, but deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.PAYMENT_METHOD_OPERATION_FAILED);
        }
    }

    /**
     * Tests update of an existing payment method
     */
    public void testUpdatePaymentMethod() {

        try {

            initSession();
            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(_paymentMethodName + System.nanoTime());
            paymentmethod.setDesc(_paymentMethodDesc + System.nanoTime());
            long id = _adminService.createPaymentMethod(paymentmethod);

            PaymentMethod retrievedPM = _adminService.retrievePaymentMethod(id);

            retrievedPM.setDesc("Updated" + System.nanoTime());
            retrievedPM.setIsDisplay(true);
            PaymentMethod updatedPM = _adminService.updatePaymentMethod(retrievedPM);

            updatedPM.setName(_paymentMethodName + System.nanoTime());
            PaymentMethod pMethod1 = _adminService.updatePaymentMethod(updatedPM);

            assertNotSame(updatedPM.getRecordVersion(), pMethod1.getRecordVersion());
            assertEquals(retrievedPM.getDesc(), updatedPM.getDesc());
            assertEquals(retrievedPM.getIsDisplay(), updatedPM.getIsDisplay());
        } catch (ROIException e) {
            fail("Updating payment method should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests update of an existing payment method
     */
    public void testUpdatePaymentMethodWithInvalidData() {

        try {

            initSession();
            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(_paymentMethodName + System.nanoTime());
            paymentmethod.setDesc(_paymentMethodDesc + System.nanoTime());
            long id = _adminService.createPaymentMethod(paymentmethod);

            PaymentMethod retrievedPM = _adminService.retrievePaymentMethod(id);

            retrievedPM.setName(appendString(_invalidPaymentMethodName));
            retrievedPM.setDesc(appendString(_paymentMethodDesc));
            retrievedPM.setIsDisplay(true);

            PaymentMethod updatedPaymentMethod = _adminService.updatePaymentMethod(retrievedPM);

            fail("Updating payment method invalid data not allowed.");
        } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.PAYMENT_METHOD_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.PAYMENT_METHOD_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.PAYMENT_METHOD_DESC_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * Tests update of an existing payment method by passing payment method id
     */
    public void testUpdatePaymentMethodById() {

        try {

            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(_paymentMethodName + System.nanoTime());
            paymentmethod.setDesc(_paymentMethodDesc + System.nanoTime());
            paymentmethod.setIsDisplay(true);

            long id = _adminService.createPaymentMethod(paymentmethod);

            PaymentMethod retrievedPM = _adminService.retrievePaymentMethod(id);

            retrievedPM.setName("Test" + System.nanoTime());
            retrievedPM.setDesc("Test" + System.nanoTime());
            retrievedPM.setIsDisplay(false);

            PaymentMethod updatedPaymentMethod = _adminService.updatePaymentMethod(retrievedPM);

            assertEquals(retrievedPM.getName(), updatedPaymentMethod.getName());
            assertEquals(retrievedPM.getDesc(), updatedPaymentMethod.getDesc());
        } catch (ROIException e) {
            fail("Updating payment method should not throw exception." + e.getErrorCode());
        }
    }

    /**
     * Tests update of an existing payment method by passing payment method id
     */
    public void testUpdatePaymentMethodByIdWithNullUser() {

        try {

            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(_paymentMethodName + System.nanoTime());
            paymentmethod.setDesc(_paymentMethodDesc + System.nanoTime());
            paymentmethod.setIsDisplay(true);

            long id = _adminService.createPaymentMethod(paymentmethod);

            initSession(null);
            PaymentMethod retrievedPM = _adminService.retrievePaymentMethod(id);

            retrievedPM.setName("Test" + System.nanoTime());
            retrievedPM.setDesc("Test" + System.nanoTime());
            retrievedPM.setIsDisplay(false);

            PaymentMethod updatedPaymentMethod = _adminService.updatePaymentMethod(retrievedPM);

            fail("Updating payment method with null user is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.PAYMENT_METHOD_OPERATION_FAILED);
        }
    }

    /**
     * Tests update of an existing payment method
     */
    public void testUpdatePaymentMethodWithNonExistingName() {

        try {

            initSession();
            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(_paymentMethodName + System.nanoTime());
            paymentmethod.setDesc(_paymentMethodDesc + System.nanoTime());
            long id = _adminService.createPaymentMethod(paymentmethod);

            PaymentMethod retrievedPM =  _adminService.retrievePaymentMethod(id);
            retrievedPM.setName("New" + System.nanoTime());

            PaymentMethod updatedPaymentMethod = _adminService.updatePaymentMethod(retrievedPM);

            assertEquals(retrievedPM.getName(), updatedPaymentMethod.getName());
            assertEquals(retrievedPM.getDesc(), updatedPaymentMethod.getDesc());
        } catch (ROIException e) {
            fail("Updating payment method with non existing name should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * Tests update of an existing payment method with Existing payment method name
     */
    public void testUpdatePaymentMethodWithExistingName() {

        try {

            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(_paymentMethodName + System.nanoTime());
            paymentmethod.setDesc(_paymentMethodDesc + System.nanoTime());
            long id = _adminService.createPaymentMethod(paymentmethod);

            PaymentMethod retrievedPM = _adminService.retrievePaymentMethod(id);


            PaymentMethod pm = new PaymentMethod();
            pm.setName(_paymentMethodName + System.nanoTime());
            pm.setDesc(_paymentMethodDesc + System.nanoTime());
            long id1 = _adminService.createPaymentMethod(pm);

            PaymentMethod oldPm = _adminService.retrievePaymentMethod(id1);

            retrievedPM.setName(oldPm.getName());
            retrievedPM.setDesc("New" + System.nanoTime());

            PaymentMethod updatedPaymentMethod = _adminService.updatePaymentMethod(retrievedPM);

            fail("Updating payment method with an existing name is not permitted, but updated");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.PAYMENT_METHOD_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * Tests update of an existing payment method with Invalid length for name
     */
    public void testUpdatePaymentMethodWithInvlidNameLength() {

        try {

            StringBuffer s1 = new StringBuffer();
            for (int i = 0; i <= ROIConstants.PAY_METHOD_NAME_MAX_LENGTH; i++) {
                s1.append("Name");
            }

            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(_paymentMethodName + System.nanoTime());
            paymentmethod.setDesc(_paymentMethodDesc + System.nanoTime());
            long id = _adminService.createPaymentMethod(paymentmethod);

            PaymentMethod retrievedPM = _adminService.retrievePaymentMethod(id);

            retrievedPM.setName(s1.toString());

            PaymentMethod updatedPaymentMethod = _adminService.updatePaymentMethod(retrievedPM);
            fail("Updating a payment method with invalid length for name is not permitted "
                 + "but updated");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.PAYMENT_METHOD_NAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * Tests update of an existing payment method with Invalid length for desc
     */
    public void testUpdatePaymentMethodWithInvlidDescLength() {

        try {

            StringBuffer s1 = new StringBuffer();
            for (int i = 0; i <= ROIConstants.DESC_MAX_LENGTH; i++) {
                s1.append("Desc");
            }
            PaymentMethod paymentmethod = new PaymentMethod();
            paymentmethod.setName(_paymentMethodName + System.nanoTime());
            paymentmethod.setDesc(_paymentMethodDesc + System.nanoTime());
            long id = _adminService.createPaymentMethod(paymentmethod);

            PaymentMethod retrievedPM = _adminService.retrievePaymentMethod(id);

            retrievedPM.setDesc(s1.toString());

            PaymentMethod updatedPaymentMethod = _adminService.updatePaymentMethod(retrievedPM);
            fail("Updating a payment method with invalid length for desc is not permitted "
                 + "but updated");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.PAYMENT_METHOD_DESC_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * Tests concurrent Update of Payment Method
     */
    public void testConcurrentUpdatePaymentMethod() {

        try {

            PaymentMethod paymentMethod = new PaymentMethod();
            paymentMethod.setName(_paymentMethodName + System.nanoTime());
            paymentMethod.setDesc(_paymentMethodDesc + System.nanoTime());

            long id = _adminService.createPaymentMethod(paymentMethod);

            PaymentMethod pm = _adminService.retrievePaymentMethod(id);

            _paymentMethodObj1 = (PaymentMethod) deepCopy(pm);
            _paymentMethodObj1.setDesc("TestDesc");
            _adminService.updatePaymentMethod(_paymentMethodObj1);

            _paymentMethodObj2 = (PaymentMethod) deepCopy(pm);
            _paymentMethodObj2.setDesc("Payment Method description");

            _adminService.updatePaymentMethod(_paymentMethodObj2);

            fail("Updated stale Payment Method Object");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION);
        } catch (Exception e) {
            fail("General Operation Faillure" + e.getMessage());
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
            ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray()); // E
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
