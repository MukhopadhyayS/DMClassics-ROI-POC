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

import com.mckesson.eig.roi.admin.model.DeliveryMethod;
import com.mckesson.eig.roi.admin.model.DeliveryMethodList;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.test.BaseROITestCase;


/**
 * @author ranjithr
 * @date  Apr 13, 2009
 * @since HPF 13.1 [ROI]
 */
public class TestDeliveryMethodServiceImpl
extends BaseROITestCase {

    protected static final String  ADMIN_SERVICE  =
        "com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl";

    private static DeliveryMethod  _deliveryMethodObj1;
    private static DeliveryMethod  _deliveryMethodObj2;
    private static ROIAdminService _adminService;

    private static String          _deliveryMethodName;
    private static String          _invalidDeliveryMethodName;
    private static String          _deliveryMethodDesc;
    private static String          _deliveryMethodUrl;

    @Override
    public void initializeTestData()
    throws Exception {

        _invalidDeliveryMethodName = "!@";
        _deliveryMethodName = "N";
        _deliveryMethodUrl  = "http://www.mckesson.com";
        _deliveryMethodDesc = "Desc.";
        _adminService       = (ROIAdminService) getService(ADMIN_SERVICE);
    }

    /**
     * Tests creation of new Non-default Delivery Method
     */
    public void testCreateNonDefaultDeliveryMethod() {

        try {

            DeliveryMethod deliverymethod = new DeliveryMethod();
            deliverymethod.setName(_deliveryMethodName + System.nanoTime());
            deliverymethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliverymethod.setUrl(_deliveryMethodUrl);
            deliverymethod.setIsDefault(false);

            long id = _adminService.createDeliveryMethod(deliverymethod);
            DeliveryMethod dm = _adminService.retrieveDeliveryMethod(id);
            assertEquals(deliverymethod.getName(), dm.getName());
            assertEquals(deliverymethod.getDesc(), dm.getDesc());
            assertEquals(deliverymethod.getUrl(), dm.getUrl());
            assertNotSame("The created delivery method id should be greater than zero", 0, id);

        } catch (ROIException e) {
            fail("Creating non-default delivery method should not thrown exception."
                 + e.getErrorCode());
        }
    }
     /**
     * Tests creation of new Non-default Delivery Method with invalid data
     */
    public void testCreateNonDefaultDeliveryMethodWithInvalidData() {

        try {

            DeliveryMethod deliverymethod = new DeliveryMethod();
            deliverymethod.setName(appendString(_invalidDeliveryMethodName));
            deliverymethod.setDesc(appendString(_deliveryMethodDesc));
            deliverymethod.setUrl(appendString(_deliveryMethodUrl));
            deliverymethod.setIsDefault(false);
            _adminService.createDeliveryMethod(deliverymethod);
            fail("Creating non-default delivery method with invalid data not allowed.");

        } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_DESC_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_URL_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * Tests creation of new Non-default Delivery Method with null user
     */
    public void testCreateNonDefaultDeliveryMethodWithNullUser() {

        try {

            initSession(null);
            DeliveryMethod deliverymethod = new DeliveryMethod();
            deliverymethod.setName(_deliveryMethodName + System.nanoTime());
            deliverymethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliverymethod.setUrl(_deliveryMethodUrl);
            deliverymethod.setIsDefault(false);

            long id = _adminService.createDeliveryMethod(deliverymethod);
            fail("Creation of delivery method with null user is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_OPERATION_FAILED);
        }
    }

    /**
     * Tests creation of default delivery method
     */
    public void testCreateDefaultDeliveryMethod() {

        try {

            initSession();
            DeliveryMethod deliverymethod = new DeliveryMethod();
            deliverymethod.setName(_deliveryMethodName + System.nanoTime());
            deliverymethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliverymethod.setUrl(_deliveryMethodUrl);
            deliverymethod.setIsDefault(true);

            long id = _adminService.createDeliveryMethod(deliverymethod);
            assertNotSame("The created delivery method id should be greater than zero", 0, id);

        } catch (ROIException e) {
            fail("Creating delivery method should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests creation of default delivery method with blank Desc and url
     */
    public void testCreateDefaultDeliveryMethodWithBlankDescAndUrl() {

        try {

            DeliveryMethod deliverymethod = new DeliveryMethod();
            deliverymethod.setName(_deliveryMethodName + System.nanoTime());
            deliverymethod.setDesc("");
            deliverymethod.setUrl("");

            long id = _adminService.createDeliveryMethod(deliverymethod);
            assertNotSame("The created delivery method id should be greater than zero", 0, id);

        } catch (ROIException e) {
            fail("Creating delivery method should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests creation of duplicate delivery method.
     */
    public void testCreateDuplicateDeliveryMethod() {

        try {

            DeliveryMethod deliverymethod = new DeliveryMethod();
            deliverymethod.setName(_deliveryMethodName + System.nanoTime());
            deliverymethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliverymethod.setUrl(_deliveryMethodUrl);
            deliverymethod.setIsDefault(false);

            long id = _adminService.createDeliveryMethod(deliverymethod);

            DeliveryMethod retrievedDM = _adminService.retrieveDeliveryMethod(id);

            DeliveryMethod dm = new DeliveryMethod();
            dm.setName(retrievedDM.getName());
            dm.setDesc(_deliveryMethodDesc + System.nanoTime());
            dm.setUrl(_deliveryMethodUrl);

            _adminService.createDeliveryMethod(dm);
            fail("Creating delivery method with duplicate name is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * Tests creation of delivery method with an invalid name.
     */
    public void testCreateDeliveryMethodWithInvalidLength() {

        try {

            StringBuffer s1 = new StringBuffer();
            for (int i = 0; i <= ROIConstants.DELIVERY_METHOD_NAME_MAX_LENGTH; i++) {
                s1.append("TestName");
            }

            DeliveryMethod deliverymethod = new DeliveryMethod();
            deliverymethod.setName(s1.toString());
            deliverymethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliverymethod.setUrl(_deliveryMethodUrl);

            _adminService.createDeliveryMethod(deliverymethod);
            fail("Creation of deliverymethod with invalid length for name is not permitted, "
                 + "but created");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_NAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * Tests creation of delivery method with an blank name.
     */
    public void testCreateDeliveryMethodWithNameAsBlank() {

        try {

            DeliveryMethod deliverymethod = new DeliveryMethod();
            deliverymethod.setName("");
            deliverymethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliverymethod.setUrl(_deliveryMethodUrl);

            _adminService.createDeliveryMethod(deliverymethod);
            fail("Creation of delivery method with name as blank is not permitted, but created");
       } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_NAME_IS_BLANK);
       }
    }

    /**
     * Tests creation of delivery method with an invalid name and invalid Url.
     */
    public void testCreateDeliveryMethodWithInvalidLengths() {

        try {

            StringBuffer s1 = new StringBuffer();
            for (int i = 0; i <= ROIConstants.DELIVERY_METHOD_NAME_MAX_LENGTH; i++) {
                s1.append("TestName");
            }

            StringBuffer s2 = new StringBuffer();
            for (int i = 0; i <= ROIConstants.DELIVERY_METHOD_URL_MAX_LENGTH; i++) {
                s2.append(_deliveryMethodUrl);
            }

            DeliveryMethod deliverymethod = new DeliveryMethod();
            deliverymethod.setName(s1.toString());
            deliverymethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliverymethod.setUrl(s2.toString());

            _adminService.createDeliveryMethod(deliverymethod);
            fail("Create delivery method with invalid length for name and url is not permitted "
                 + "but created");

        } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_URL_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * Tests creation of delivery method with url of length exceeding the permitted value
     */
    public void testCreateDeliveryMethodWithInvalidUrl() {

        try {

            StringBuffer s1 = new StringBuffer();
            String testUrl = "http://";
            for (int i = 0; i <= ROIConstants.DELIVERY_METHOD_URL_MAX_LENGTH; i++) {
                s1.append(testUrl);
            }

            DeliveryMethod deliverymethod = new DeliveryMethod();
            deliverymethod.setName(_deliveryMethodName + System.nanoTime());
            deliverymethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliverymethod.setUrl(s1.toString());

            long id = _adminService.createDeliveryMethod(deliverymethod);
            fail("Creating delivery method with invalid length for url is not permitted "
                 + "but created");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_URL_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * Tests creation of delivery method as Null
     */
    public void testCreateDeliveryMethodAsNull() {

        try {

            _adminService.createDeliveryMethod(null);
            fail("Creation of delivery method as null is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_OPERATION_FAILED);
        }
    }

    /**
     * Tests creation of delivery method with description as blank
     */
    public void testCreateDeliveryMethodWithDescriptionAsBlank() {

        try {

            DeliveryMethod deliverymethod = new DeliveryMethod();
            deliverymethod.setName(_deliveryMethodName + System.nanoTime());
            deliverymethod.setUrl(_deliveryMethodUrl);
            deliverymethod.setDesc("");

            long id = _adminService.createDeliveryMethod(deliverymethod);
            assertNotSame("The created delivery method id should be greater than zero", 0,  id);

        } catch (ROIException e) {
            fail("Creation of delivery method with blank description should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * Tests update of an existing delivery method Description and url
     */
    public void testUpdateDeliveryMethodDescAndUrl() {

        try {

            DeliveryMethod deliverymethod = new DeliveryMethod();
            deliverymethod.setName(_deliveryMethodName + System.nanoTime());
            deliverymethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliverymethod.setUrl(_deliveryMethodUrl);
            deliverymethod.setIsDefault(true);

            long id = _adminService.createDeliveryMethod(deliverymethod);

            DeliveryMethod retrievedDM = _adminService.retrieveDeliveryMethod(id);

            retrievedDM.setUrl("http://www.ups.com");
            retrievedDM.setDesc("FirstUpdate");

            DeliveryMethod updatedDM = _adminService.updateDeliveryMethod(retrievedDM);

            assertEquals(retrievedDM.getUrl(), updatedDM.getUrl());
            assertEquals(retrievedDM.getDesc(), updatedDM.getDesc());
        } catch (ROIException e) {
            fail("Updating delivery method should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests update of an existing default delivery method to non default delivery method
     */
    public void testUpdateDefaultToNonDefaultDeliveryMethod() {

        try {

            DeliveryMethod deliverymethod = new DeliveryMethod();
            deliverymethod.setName(_deliveryMethodName + System.nanoTime());
            deliverymethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliverymethod.setUrl(_deliveryMethodUrl);
            deliverymethod.setIsDefault(true);

            long id = _adminService.createDeliveryMethod(deliverymethod);

            DeliveryMethod retrievedDM = _adminService.retrieveDeliveryMethod(id);

            retrievedDM.setDesc("SecondUpdate");
            retrievedDM.setIsDefault(false);

            DeliveryMethod updatedDM = _adminService.updateDeliveryMethod(retrievedDM);

            assertEquals(retrievedDM.getUrl(), updatedDM.getUrl());
            assertEquals(retrievedDM.getDesc(), updatedDM.getDesc());
            assertEquals(retrievedDM.getIsDefault(), updatedDM.getIsDefault());
        } catch (ROIException e) {
            fail("Updating delivery method should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests update of an existing Non default delivery method to default delivery method
     */
    public void testUpdateNonDefaultToDefaultDeliveryMethod() {

        try {

            DeliveryMethod deliverymethod = new DeliveryMethod();
            deliverymethod.setName(_deliveryMethodName + System.nanoTime());
            deliverymethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliverymethod.setUrl(_deliveryMethodUrl);
            deliverymethod.setIsDefault(false);

            long id = _adminService.createDeliveryMethod(deliverymethod);

            DeliveryMethod retrievedDM =  _adminService.retrieveDeliveryMethod(id);

            retrievedDM.setDesc("FirstUpdate");
            retrievedDM.setIsDefault(true);

            DeliveryMethod updatedDM = _adminService.updateDeliveryMethod(retrievedDM);

            assertEquals(retrievedDM.getUrl(), updatedDM.getUrl());
            assertEquals(retrievedDM.getDesc(), updatedDM.getDesc());
            assertEquals(retrievedDM.getIsDefault(), updatedDM.getIsDefault());
        } catch (ROIException e) {
            fail("Updating delivery method should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests update of an existing default delivery method Isdefault
     */
    public void testUpdateDeliveryMethodIsDefault() {

        try {

            DeliveryMethod deliverymethod = new DeliveryMethod();
            deliverymethod.setName(_deliveryMethodName + System.nanoTime());
            deliverymethod.setDesc("");
            deliverymethod.setUrl("");

            long id = _adminService.createDeliveryMethod(deliverymethod);

            DeliveryMethod retrievedDM = _adminService.retrieveDeliveryMethod(id);

            retrievedDM.setDesc("NewDesc");
            retrievedDM.setUrl("http://www.newurl.com");
            retrievedDM.setIsDefault(true);

            DeliveryMethod updatedDM = _adminService.updateDeliveryMethod(retrievedDM);

            assertEquals(retrievedDM.getDesc(), updatedDM.getDesc());
            assertEquals(retrievedDM.getUrl(), updatedDM.getUrl());
            assertEquals(retrievedDM.getIsDefault(), updatedDM.getIsDefault());
        } catch (ROIException e) {
            fail("Updating delivery method should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests update of an existing default delivery method with Invalid data
     */
    public void testUpdateDeliveryMethodIsDefaultWithInvalidData() {

        try {

            DeliveryMethod deliverymethod = new DeliveryMethod();
            deliverymethod.setName(_deliveryMethodName + System.nanoTime());
            deliverymethod.setDesc(_deliveryMethodDesc);
            deliverymethod.setUrl(_deliveryMethodUrl);
            deliverymethod.setIsDefault(false);
            long id = _adminService.createDeliveryMethod(deliverymethod);

            DeliveryMethod retrievedDM = _adminService.retrieveDeliveryMethod(id);

            retrievedDM.setName(appendString(_invalidDeliveryMethodName));
            retrievedDM.setDesc(appendString(_deliveryMethodDesc));
            retrievedDM.setUrl(appendString(_deliveryMethodUrl));
            retrievedDM.setIsDefault(true);

            DeliveryMethod updatedDM = _adminService.updateDeliveryMethod(retrievedDM);
            fail("Updating default delivery method with invalid data not allowed.");

        } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_DESC_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_URL_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * Tests update of an existing delivery method by delivery method Id With Null User
     */
    public void testUpdateDeliveryMethodByIdWithNullUser() {

        try {

            DeliveryMethod deliverymethod = new DeliveryMethod();
            deliverymethod.setName(_deliveryMethodName + System.nanoTime());
            deliverymethod.setDesc("");
            deliverymethod.setUrl("");

            long id = _adminService.createDeliveryMethod(deliverymethod);

            initSession(null);
            DeliveryMethod retrievedDM =  _adminService.retrieveDeliveryMethod(id);

            retrievedDM.setUrl("http://www.UPDATED.com");
            retrievedDM.setDesc("UPDATED");

            DeliveryMethod updatedDM = _adminService.updateDeliveryMethod(retrievedDM);

            fail("Updating the payment method with null user is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_OPERATION_FAILED);
        }
    }

    /**
     * Tests update of an existing delivery method with a non-existing name
     */
    public void testUpdateDeliveryMethodNameWithNonExistingName() {

        try {

            initSession();
            DeliveryMethod deliveryMethod = new DeliveryMethod();
            deliveryMethod.setName(_deliveryMethodName + System.nanoTime());
            deliveryMethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliveryMethod.setUrl(_deliveryMethodUrl);
            deliveryMethod.setIsDefault(true);

            long id = _adminService.createDeliveryMethod(deliveryMethod);

            DeliveryMethod retrievedDM = _adminService.retrieveDeliveryMethod(id);

            retrievedDM.setName(System.nanoTime() + "new");
            _adminService.updateDeliveryMethod(retrievedDM);

            DeliveryMethod updatedDM = _adminService.retrieveDeliveryMethod(id);
            assertEquals(updatedDM.getName(), retrievedDM.getName());
        } catch (ROIException e) {
            fail("Updating a delivery method should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests update of an existing delivery method with an existing name
     */
    public void testUpdateDeliveryMethodNameWithExistingName() {

        try {

            DeliveryMethod deliveryMethod = new DeliveryMethod();
            deliveryMethod.setName(_deliveryMethodName + System.nanoTime());
            deliveryMethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliveryMethod.setUrl(_deliveryMethodUrl);
            deliveryMethod.setIsDefault(true);

            long id = _adminService.createDeliveryMethod(deliveryMethod);
            DeliveryMethod dm1 = _adminService.retrieveDeliveryMethod(id);

            DeliveryMethod dm = new DeliveryMethod();
            dm.setName(_deliveryMethodName + System.nanoTime());
            dm.setDesc(_deliveryMethodDesc + System.nanoTime());
            dm.setUrl(_deliveryMethodUrl);
            dm.setIsDefault(true);

            long id1 = _adminService.createDeliveryMethod(dm);

            DeliveryMethod retrievedDM = _adminService.retrieveDeliveryMethod(id1);

            retrievedDM.setName(dm1.getName());
            _adminService.updateDeliveryMethod(retrievedDM);
            fail("Updating a delivery method with an existing name is not permitted, but updated");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * Tests update of delivery method to null
     */
    public void testUpdateDeliveryMethodWithNull() {

        try {

            _adminService.updateDeliveryMethod(null);
            fail("Should not update a null delivery method object");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_OPERATION_FAILED);
        }
    }

    /**
     * Tests retrieval of a particular delivery method
     */
    public void testRetrieveDeliveryMethod() {

        try {

            DeliveryMethod deliverymethod = new DeliveryMethod();
            deliverymethod.setName(_deliveryMethodName + System.nanoTime());
            deliverymethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliverymethod.setUrl(_deliveryMethodUrl);
            deliverymethod.setIsDefault(true);

            long id = _adminService.createDeliveryMethod(deliverymethod);

            DeliveryMethod retrievedDM = _adminService.retrieveDeliveryMethod(id);
            assertEquals(retrievedDM.getId(), id);
        } catch (ROIException e) {
            fail("Retrieve delivery method should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests retrieve of delivery method with id zero
     */
    public void testRetrieveDeliveryMethodWithIdAsZero() {

        try {

            _adminService.retrieveDeliveryMethod(0);
            fail("Retrieving delivery method by invalid id is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * Tests retrieval of delivery method with invalid id
     */
    public void testRetrieveDeliveryMethodWithInvalidId() {

        try {

            _adminService.retrieveDeliveryMethod(Integer.MAX_VALUE);
            fail("Retrieving delivery method by invalid id is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * Tests retrieve of all delivery methods
     */
    public void testRetrieveAllDeliveryMethods() {

        DeliveryMethodList list = new DeliveryMethodList();
        list = _adminService.retrieveAllDeliveryMethods(true);
        assertNotSame("The size of retrieved delivery methods should be greater than zero",
                      0,
                      list.getDeliveryMethods().size());
    }

    /**
     * Tests retrieve of all delivery methods
     */
    public void testRetrieveAllDeliveryMethodsWithFalse() {

        DeliveryMethodList list = new DeliveryMethodList();
        list = _adminService.retrieveAllDeliveryMethods(false);
        assertNotSame("The size of retrieved delivery methods should be greater than zero",
                      0,
                      list.getDeliveryMethods().size());
    }

    /**
     * Tests delete of default delivery method
     */
    public void testDeleteDefaultDeliveryMethod() {

        try {

            DeliveryMethod deliveryMethod = new DeliveryMethod();
            deliveryMethod.setName(_deliveryMethodName + System.nanoTime());
            deliveryMethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliveryMethod.setUrl(_deliveryMethodUrl);
            deliveryMethod.setIsDefault(true);

            long id = _adminService.createDeliveryMethod(deliveryMethod);
            _adminService.deleteDeliveryMethod(id);

            DeliveryMethod dmethod = _adminService.retrieveDeliveryMethod(id);
            fail("Retrieving the deleted delivery method is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * Tests delete of non-default delivery method
     */
    public void testDeleteNonDefaultDeliveryMethod() {

        try {

            DeliveryMethod deliveryMethod = new DeliveryMethod();
            deliveryMethod.setName(_deliveryMethodName + System.nanoTime());
            deliveryMethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliveryMethod.setUrl(_deliveryMethodUrl);
            long id = _adminService.createDeliveryMethod(deliveryMethod);
            _adminService.deleteDeliveryMethod(id);

            DeliveryMethod dmethod = _adminService.retrieveDeliveryMethod(id);
            fail("Retrieving the deleted delivery method is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * Tests delete existing delivery method by Id with null user
     */
    public void testDeleteDeliveryMethodByIdWithNullUser() {

        try {

            DeliveryMethod deliveryMethod = new DeliveryMethod();
            deliveryMethod.setName(_deliveryMethodName + System.nanoTime());
            deliveryMethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliveryMethod.setUrl(_deliveryMethodUrl);
            long id = _adminService.createDeliveryMethod(deliveryMethod);

            initSession(null);
            _adminService.deleteDeliveryMethod(id);
            fail("Deleteing the DeliveryMethod with null user is not permitted, but accepted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.DELIVERY_METHOD_OPERATION_FAILED);
        }
    }

    /**
     * Tests delete of an invalid id.
     */
    public void testDeleteDeliveryMethodWithIdAsZero() {

        try {

            initSession();
            _adminService.deleteDeliveryMethod(0);
            fail("Delivery method with id zero does not exist");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * Tests concurrent Delete of Delivery method
     */
    public void testConcurrentDeleteOfDeliveryMethod() {

        try {

            DeliveryMethod deliveryMethod = new DeliveryMethod();
            deliveryMethod.setName(_deliveryMethodName + System.nanoTime());
            deliveryMethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliveryMethod.setUrl(_deliveryMethodUrl);
            long id = _adminService.createDeliveryMethod(deliveryMethod);

            DeliveryMethod retrievedDeliveryMethod = new DeliveryMethod();
            retrievedDeliveryMethod = _adminService.retrieveDeliveryMethod(id);

            _deliveryMethodObj1 = (DeliveryMethod) deepCopy(retrievedDeliveryMethod);
            _deliveryMethodObj1.setDesc("ModifiedDesc");
            _adminService.deleteDeliveryMethod(id);

            _deliveryMethodObj2 = (DeliveryMethod) deepCopy(retrievedDeliveryMethod);

            _adminService.deleteDeliveryMethod(id);
            fail("Deleted stale Delivery Method Object");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        } catch (Exception e) {
            fail("General operation faillure" + e.getMessage());
        }
    }

    /**
     * Tests concurrent Update of Delivery Method
     */

    public void testConcurrentUpdateDeliveryMethod() {

        try {

            DeliveryMethod deliveryMethod = new DeliveryMethod();
            deliveryMethod.setName(_deliveryMethodName + System.nanoTime());
            deliveryMethod.setDesc(_deliveryMethodDesc + System.nanoTime());
            deliveryMethod.setUrl(_deliveryMethodUrl);
            long id = _adminService.createDeliveryMethod(deliveryMethod);

            DeliveryMethod retrievedDeliveryMethod = new DeliveryMethod();
            retrievedDeliveryMethod = _adminService.
                                        retrieveDeliveryMethod(id);

            _deliveryMethodObj1 = (DeliveryMethod) deepCopy(retrievedDeliveryMethod);
            _deliveryMethodObj1.setDesc("ModifiedDesc2");
            _adminService.updateDeliveryMethod(_deliveryMethodObj1);

            _deliveryMethodObj2 = (DeliveryMethod) deepCopy(retrievedDeliveryMethod);
            _deliveryMethodObj2.setDesc("concurrency2");

            _adminService.updateDeliveryMethod(_deliveryMethodObj2);
            fail("Updated stale Delivery Method Object");
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
