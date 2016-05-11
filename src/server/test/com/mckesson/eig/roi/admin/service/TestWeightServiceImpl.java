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

import com.mckesson.eig.roi.admin.model.Weight;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.test.BaseROITestCase;

/**
 * @author ranjithr
 * @date Jul 14, 2008
 * @since HPF 13.1 [ROI]
 */
public class TestWeightServiceImpl
extends BaseROITestCase {

    protected static final String ADMIN_SERVICE =
        "com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl";

    private static ROIAdminService _adminService;
    private static Weight          _weightMethodObj1;
    private static Weight          _weightMethodObj2;

    private static final int PAGE_MAX = 99;
    private static final int PAGE_MIN = 1;

    public void initializeTestData()
    throws Exception {

      _adminService = (ROIAdminService) getService(ADMIN_SERVICE);
    }

    /**
     * Tests retrieval of a weight method
     */
    public void testRetrieveweight() {

        try {

            Weight weight = _adminService.retrieveWeight();
            assertNotSame("The retrieved weight's unit per measure should not be equal to zero",
                          0,
                          weight.getUnitPerMeasure());

        } catch (ROIException e) {
            fail("Retrieve weight should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests update of an existing Weight method
     */
    public void testUpdateWeight() {

        try {

            final long unitPerMeasure = 50;
            Weight weight = _adminService.retrieveWeight();
            weight.setUnitPerMeasure(unitPerMeasure);
            Weight updatedWeight = _adminService.updateWeight(weight);
            assertEquals(weight.getUnitPerMeasure(), updatedWeight.getUnitPerMeasure());
        } catch (ROIException e) {
            fail("Updating weight should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests update of an existing Weight method with null user
     */
    public void testUpdateWeightWithNullUser() {

        try {

            initSession(null);
            final long unitPerMeasure = 50;
            Weight weight = _adminService.retrieveWeight();
            weight.setUnitPerMeasure(unitPerMeasure);
            Weight updatedWeight = _adminService.updateWeight(weight);
            fail("Updating the weight with null user is not permitted, but updated.");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.WEIGHT_METHOD_OPERATION_FAILED);
        }
    }

    /**
     * Tests update of weight method with minimum value
     */
    public void testUpdateWeightMethodWithMinValue() {

        try {

            initSession();
            final int unitPerMeasure = PAGE_MIN;
            Weight wt = _adminService.retrieveWeight();
            wt.setUnitPerMeasure(unitPerMeasure);
            Weight updatedWeight = _adminService.updateWeight(wt);
            assertEquals(wt.getUnitPerMeasure(), updatedWeight.getUnitPerMeasure());
        } catch (ROIException e) {
            fail("Updating unit per measure with permitted minimum value "
            	 + "should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests update of weight method with minimum value
     */
    public void testUpdateWeightMethodWithMaxValue() {

        try {

             final int unitPerMeasure = PAGE_MAX;
             Weight wt = _adminService.retrieveWeight();
             wt.setUnitPerMeasure(unitPerMeasure);
             Weight updatedWeight = _adminService.updateWeight(wt);
             assertEquals(wt.getUnitPerMeasure(), updatedWeight.getUnitPerMeasure());
        } catch (ROIException e) {
            fail("Updating unit per measure should not fail for permitted maximum value."
                 + e.getErrorCode());
        }
    }

    /**
     * Tests update of Weight method with zero unit Per measure
     */
    public void testUpdateWeightWithZeroUnitPerMeasure() {

         try {

             final long unitPerMeasure = 0;
             Weight wt = new Weight();
             wt.setUnitPerMeasure(unitPerMeasure);
             Weight updatedWeight = _adminService.updateWeight(wt);
             fail("Updating unit per measure value as zero is not permitted, but updated");
          } catch (ROIException e) {
                assertError(e, ROIClientErrorCodes.WEIGHT_INVALID_PAGE_VALUE);
          }
    }

    /**
     * Tests update of Weight method with negative case unit Per measure
     */
    public void testUpdateWeightWithNegativeUnitPerMeasure() {

        try {

            final long unitPerMeasure = -2;
            Weight wt = new Weight();
            wt.setUnitPerMeasure(unitPerMeasure);
            Weight updatedWeight = _adminService.updateWeight(wt);
            fail("Updating unit per measure with negative value is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.WEIGHT_INVALID_PAGE_VALUE);
        }
    }

    /**
     * Tests update of Weight method with invalid maximum unit Per measure
     */
    public void testUpdateWeightMethodWithInvalidMaxPage() {

        try {

            final long unitPerMeasure = 100;
            Weight wt = new Weight();
            wt.setUnitPerMeasure(unitPerMeasure);
            Weight updatedWeight = _adminService.updateWeight(wt);
            fail("Updating unit per measure with value > 99 is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.WEIGHT_INVALID_PAGE_VALUE);
        }
    }

   /**
    * Tests update of Weight method with minimum unit Per measure
    */
    public void testUpdateWeightMethodWithMinPage() {

        try {

            Weight wt = new Weight();
            wt.setUnitPerMeasure(Integer.MIN_VALUE);
            Weight updatedWeight = _adminService.updateWeight(wt);
            fail("Updating unit per measure with negative value is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.WEIGHT_INVALID_PAGE_VALUE);
        }
    }

    /**
     * Tests update of Weight method with maximum
     */
    public void testUpdateWeightMethodWithMaxPage() {

        try {

            Weight wt = new Weight();
            wt.setUnitPerMeasure(Integer.MAX_VALUE);
            Weight updatedWeight = _adminService.updateWeight(wt);
            fail("Updating unit per measure with value > 99 is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.WEIGHT_INVALID_PAGE_VALUE);
        }
    }

    /**
     * Deep copies an object
     */
    public void testConcurrentUpdateWeightMethod() {

        try {

            final long unitPerMeasure1 = 55;
            final long unitPerMeasure2 = 65;
            Weight retrievedWeight = new Weight();
            retrievedWeight = _adminService.retrieveWeight();

            _weightMethodObj1 = (Weight) deepCopy(retrievedWeight);
            _weightMethodObj1.setUnitPerMeasure(unitPerMeasure1);
            Weight wt = _adminService.updateWeight(_weightMethodObj1);

            _weightMethodObj2 = (Weight) deepCopy(retrievedWeight);
            _weightMethodObj2.setUnitPerMeasure(unitPerMeasure2);

            Weight wt2 = _adminService.updateWeight(_weightMethodObj2);
            fail("Updated stale Weight Object");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION);
        } catch (Exception e) {
            fail("General Operation Faillure");
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
