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

import com.mckesson.eig.roi.admin.model.Reason;
import com.mckesson.eig.roi.admin.model.Reasons;
import com.mckesson.eig.roi.admin.model.ReasonsList;
import com.mckesson.eig.roi.admin.model.RequestStatusMap;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.test.BaseROITestCase;


/**
 * @author manikandans
 * @date    Mar 31, 2009
 * @since  HPF 13.1 [ROI]; Apr 28, 2008
 */
public class TestReasonServiceImpl
extends BaseROITestCase {

    protected static final String  ADMIN_SERVICE  =
        "com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl";

    private static ROIAdminService _adminService;
    private static String          _reasonName;
    private static String          _invalidReasonName;
    private static String          _reasonDisplayText;
    private static int             _statusNotApplicable;

    @Override
    public void initializeTestData()
    throws Exception {

        setUp();
        _reasonName        = "name";
        _invalidReasonName = "!@.=-+";
        _reasonDisplayText = "dispText.";
        _statusNotApplicable = 0;
        _adminService      = (ROIAdminService) getService(ADMIN_SERVICE);
    }

    /**
     * Tests creation of new tpo Request Reason
     */
    public void testCreateTpoRequestReason() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.REQUEST_REASON);
            reason.setTpo(true);
            reason.setStatus(_statusNotApplicable);

            long id = _adminService.createReason(reason);
            assertNotSame("The created reason id should be greater than zero", 0, id);

        } catch (ROIException e) {
            fail("Creating a request reason should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests creation of new nontpo Request Reason
     */
    public void testCreateNonTpoRequestReason() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.REQUEST_REASON);
            reason.setNonTpo(true);
            reason.setStatus(_statusNotApplicable);

            long id = _adminService.createReason(reason);
            assertNotSame("The created reason id should be greater than zero", 0, id);

        } catch (ROIException e) {
            fail("Creating a request reason should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests creation of new tpo Request Reason with null value
     */
    public void testCreateTpoRequestReasonWithNull() {

        try {

            _adminService.createReason(null);
            fail("Creating a request reason with null value is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REASON_OPERATION_FAILED);
        }
    }

    /**
     * Tests creation of new Request Reason with name as blank
     */
    public void testCreateRequestReasonWithNameAsBlank() {

        try {

            Reason reason = new Reason();
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.REQUEST_REASON);
            reason.setTpo(true);
            reason.setStatus(_statusNotApplicable);

            long requestReasonId = _adminService.createReason(reason);
            fail("Creating a request reason with name as blank is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REASON_NAME_IS_BLANK);
        }
    }

    /**
     * Tests creation of new Request Reason with display text as blank
     */
    public void testCreateRequestReasonWithDispTextAsBlank() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setType(ROIConstants.REQUEST_REASON);
            reason.setTpo(true);
            reason.setStatus(_statusNotApplicable);

            long requestReasonId = _adminService.createReason(reason);
            fail("Creating a request reason with display text as blank is not permitted,"
                 + "but created");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REASON_DISPLAY_TEXT_IS_BLANK);
        }
    }

    /**
     * Tests creation of new Request Reason with attribute field as blank
     */
    public void testCreateRequestReasonWithAttributeAsBlank() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.REQUEST_REASON);
            reason.setStatus(_statusNotApplicable);

            long requestReasonId  = _adminService.createReason(reason);
            fail("Creating a request reason with attribute as blank is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUEST_REASON_ATTRIBUTE_IS_BLANK);
        }
    }

    /**
     * Tests creation of new Request Reason with  invalid length for name
     */
    public void testCreateRequestReasonWithInvalidNameLength() {

        try {

            StringBuffer s1 = new StringBuffer();
            for (int i = 0; i <= ROIConstants.REASON_NAME_DEF_MAX_LENGTH; i++) {
                s1.append("Name");
            }

            Reason reason = new Reason();
            reason.setName(s1.toString());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.REQUEST_REASON);
            reason.setTpo(true);
            reason.setStatus(_statusNotApplicable);

            long requestReasonId  = _adminService.createReason(reason);
            fail("Creating a request reason with invalid length for name is not permitted,"
                 + "but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REASON_NAME_LENGTH_EXCEEDS);
        }
    }

    /**
     * Tests creation of new Request Reason with  invalid length for display text
     */
    public void testCreateRequestReasonWithnvalidDispTextLength() {

        try {

            StringBuffer s1 = new StringBuffer();
            for (int i = 0; i <= ROIConstants.REASON_DISPLAY_TEXT_MAX_LENGTH; i++) {
                s1.append("TEST");
            }

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(s1.toString());
            reason.setType(ROIConstants.REQUEST_REASON);
            reason.setTpo(true);
            reason.setStatus(_statusNotApplicable);

            long requestReasonId  = _adminService.createReason(reason);
            fail("Creating a request reason with invalid length"
               + "for display text is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REASON_DISPLAY_TEXT_LENGTH_EXCEEDS);
        }
    }

    /**
     * Tests creation of new Adjustment Reason
     */
    public void testCreateAdjustmentReason() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.ADJUSTMENT_REASON);
            reason.setStatus(_statusNotApplicable);

            long id = _adminService.createReason(reason);
            assertNotSame("The created reason id should be greater than zero", 0, id);

        } catch (ROIException e) {
            fail("Creating an adjustment reason should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests creation of new Adjustment Reason with invalid data.
     */
    public void testCreateAdjustmentReasonWithInvalidData() {

        try {

            Reason reason = new Reason();
            reason.setName(appendString(_invalidReasonName));
            reason.setDisplayText(appendString(_reasonDisplayText));
            reason.setType(ROIConstants.ADJUSTMENT_REASON);
            reason.setStatus(_statusNotApplicable);

            _adminService.createReason(reason);
            fail("Creating an adjustment reason with invalid data not allowed.");
        } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.REASON_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.REASON_NAME_LENGTH_EXCEEDS);
            assertError(e, ROIClientErrorCodes.REASON_DISPLAY_TEXT_LENGTH_EXCEEDS);
        }
    }

    /**
     * Tests creation of new Request Reason with attribute field as both
     */
    public void testCreateRequestReasonWithAttributeAsBoth() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.REQUEST_REASON);
            reason.setTpo(true);
            reason.setNonTpo(true);
            reason.setStatus(_statusNotApplicable);

            long id = _adminService.createReason(reason);
            assertNotSame("The created reason id should be greater than zero", 0, id);

        } catch (ROIException e) {
            fail("Creating a request reason with attribute as both should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * Tests creation of new Status Reason with canceled status
     */
    public void testCreateCanceledStatusReason() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.STATUS_REASON);
            reason.setStatus(ROIConstants.STATUS_CANCELED_ID);

            long id = _adminService.createReason(reason);
            assertNotSame("The created reason id should be greater than zero", 0, id);

        } catch (ROIException e) {
            fail("Creating a status reason with canceled status should not thrown exception."
        		 + e.getErrorCode());
        }
    }

    /**
     * Tests creation of new Status Reason with completed status
     */
    public void testCreateCompletedStatusReason() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.STATUS_REASON);
            reason.setStatus(ROIConstants.STATUS_COMPLETED_ID);

            long id = _adminService.createReason(reason);
            assertNotSame("The created reason id should be greater than zero", 0, id);

        } catch (ROIException e) {
            fail("Creating a status reason with completed status should not thrown exception."
                + e.getErrorCode());
        }
    }

    /**
     * Tests creation of new Status Reason with denied status
     */
    public void testCreateDeniedStatusReason() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.STATUS_REASON);
            reason.setStatus(ROIConstants.STATUS_DENIED_ID);

            long id = _adminService.createReason(reason);
            assertNotSame("The created reason id should be greater than zero", 0, id);

        } catch (ROIException e) {
            fail("Creating a status reason with denied status should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * Tests creation of new Status Reason with logged status
     */
    public void testCreateLoggedStatusReason() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.STATUS_REASON);
            reason.setStatus(ROIConstants.STATUS_LOGGED_ID);

            long id = _adminService.createReason(reason);
            assertNotSame("The created reason id should be greater than zero", 0, id);

        } catch (ROIException e) {
            fail("Creating a status reason with logged status should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * Tests creation of new Status Reason with pended status
     */
    public void testCreatePendedStatusReason() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.STATUS_REASON);
            reason.setStatus(ROIConstants.STATUS_PENDED_ID);

            long id = _adminService.createReason(reason);
            assertNotSame("The created reason id should be greater than zero", 0, id);

        } catch (ROIException e) {
            fail("Creating a status reason with pended status should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * Tests creation of new Status Reason with PreBilled status
     */
    public void testCreatePreBilledStatusReason() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.STATUS_REASON);
            reason.setStatus(ROIConstants.STATUS_PREBILLED_ID);

            long id = _adminService.createReason(reason);
            assertNotSame("The created reason id should be greater than zero", 0, id);

        } catch (ROIException e) {
            fail("Creating a status reason with PreBilled status should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * Tests creation of new Status Reason with denied status with status field as blank
     */
    public void testCreateDeniedStatusReasonWithBlankStatus() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.STATUS_REASON);

            long requestReasonId  = _adminService.createReason(reason);
            fail("Creating a status reason with blank status field is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.STATUS_REASON_STATUS_FIELD_IS_BLANK);
        }
    }

    /**
     * Tests creation of new request Reason with duplicate name
     */
    public void testCreateRequestReasonWithDuplicateName() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.REQUEST_REASON);
            reason.setNonTpo(true);
            reason.setStatus(_statusNotApplicable);

            long id = _adminService.createReason(reason);

            Reason retrievedReason = _adminService.retrieveReason(id);
            Reason reason1 = new Reason();
            reason1.setName(retrievedReason.getName());
            reason1.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason1.setType(ROIConstants.REQUEST_REASON);
            reason1.setStatus(_statusNotApplicable);
            reason1.setTpo(true);

            long requestReasonId  = _adminService.createReason(reason1);
            fail("Creating a request reason with duplicate name is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUEST_REASON_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * Tests creation of new request Reason with duplicate display text
     */
    public void testCreateRequestReasonWithDuplicateDispText() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.REQUEST_REASON);
            reason.setNonTpo(true);
            reason.setStatus(_statusNotApplicable);

            long id = _adminService.createReason(reason);

            Reason retrievedReason = _adminService.retrieveReason(id);
            Reason reason1 = new Reason();
            reason1.setName(_reasonName + System.nanoTime());
            reason1.setDisplayText(retrievedReason.getDisplayText());
            reason1.setType(ROIConstants.REQUEST_REASON);
            reason1.setStatus(_statusNotApplicable);
            reason1.setTpo(true);

            long requestReasonId  = _adminService.createReason(reason1);
            fail("Creating a request reason with duplicate display text is not permitted,"
            	+ "but created");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REASON_DISPLAY_TEXT_IS_NOT_UNIQUE);
        }
    }

    /**
     * Tests creation of new Adjustment Reason with duplicate name
     */
    public void testCreateAdjustmentReasonWithDuplicateName() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.ADJUSTMENT_REASON);
            reason.setStatus(_statusNotApplicable);

            long id = _adminService.createReason(reason);

            Reason retrievedReason = _adminService.retrieveReason(id);
            Reason reason1 = new Reason();
            reason1.setName(retrievedReason.getName());
            reason1.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason1.setType(ROIConstants.ADJUSTMENT_REASON);
            reason1.setStatus(_statusNotApplicable);

            long requestReasonId  = _adminService.createReason(reason1);
            fail("Creating a adjustment reason with duplicate name is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.ADJUSTMENT_REASON_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * Tests creation of new Adjustment Reason with duplicate display text
     */
    public void testCreateAdjustmentReasonWithDuplicateDispText() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.ADJUSTMENT_REASON);
            reason.setStatus(_statusNotApplicable);

            long id = _adminService.createReason(reason);

            Reason retrievedReason = _adminService.retrieveReason(id);
            Reason reason1 = new Reason();
            reason1.setName(_reasonName + System.nanoTime());
            reason1.setDisplayText(retrievedReason.getDisplayText());
            reason1.setType(ROIConstants.ADJUSTMENT_REASON);
            reason1.setStatus(_statusNotApplicable);

            long requestReasonId  = _adminService.createReason(reason1);
            fail("Creating a adjustment reason with duplicate display text is not permitted,"
                 + " but created");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REASON_DISPLAY_TEXT_IS_NOT_UNIQUE);
        }
    }

    /**
     * Tests creation of new Status Reason with duplicate name in same status
     */
    public void testCreateStatusReasonWithDuplicateNameInSameStatus() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.STATUS_REASON);
            reason.setStatus(ROIConstants.STATUS_DENIED_ID);

            long id = _adminService.createReason(reason);

            Reason retrievedReason = _adminService.retrieveReason(id);
            Reason reason1 = new Reason();
            reason1.setName(retrievedReason.getName());
            reason1.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason1.setType(ROIConstants.STATUS_REASON);
            reason1.setStatus(ROIConstants.STATUS_DENIED_ID);

            long requestReasonId  = _adminService.createReason(reason1);
            fail("Creating a status reason with duplicate name in same status is not permitted,"
                 + " but created");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.STATUS_REASON_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * Tests creation of new Status Reason with duplicate dispText in same status
     */
    public void testCreateStatusReasonWithDuplicateDispTextInSameStatus() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.STATUS_REASON);
            reason.setStatus(ROIConstants.STATUS_DENIED_ID);

            long id = _adminService.createReason(reason);
            Reason retrievedReason = _adminService.retrieveReason(id);
            Reason reason1 = new Reason();
            reason1.setName(_reasonName + System.nanoTime());
            reason1.setDisplayText(retrievedReason.getDisplayText());
            reason1.setType(ROIConstants.STATUS_REASON);
            reason1.setStatus(ROIConstants.STATUS_DENIED_ID);

            long requestReasonId  = _adminService.createReason(reason1);
            fail("Creating a status reason with duplicate display text in same"
                 + "status is not permitted, but created");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.STATUS_REASON_DISPLAY_TEXT_IS_NOT_UNIQUE);
        }
    }

    /**
     * Tests creation of new Status Reason with duplicate name in different status
     */
    public void testCreateStatusReasonWithDuplicateNameInDiffStatus() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.STATUS_REASON);
            reason.setStatus(ROIConstants.STATUS_DENIED_ID);

            long id = _adminService.createReason(reason);
            Reason retrievedReason = _adminService.retrieveReason(id);
            Reason reason1 = new Reason();
            reason1.setName(retrievedReason.getName());
            reason1.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason1.setType(ROIConstants.STATUS_REASON);
            reason1.setStatus(ROIConstants.STATUS_COMPLETED_ID);

            long id1  = _adminService.createReason(reason1);
            assertNotSame("The created reason id should be greater than zero", 0, id1);

        } catch (ROIException e) {
            fail("Creating a status reason with duplicate name "
                 + "in different status should not throw exception." + e.getErrorCode());
        }
    }

    /**
     * Tests creation of new Status Reason with duplicate dispText in different status
     */
    public void testCreateStatusReasonWithDuplicateDispTextInDiffStatus() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.STATUS_REASON);
            reason.setStatus(ROIConstants.STATUS_COMPLETED_ID);

            long id = _adminService.createReason(reason);
            Reason retrievedReason = _adminService.retrieveReason(id);
            Reason reason1 = new Reason();
            reason1.setName(_reasonName + System.nanoTime());
            reason1.setDisplayText(retrievedReason.getDisplayText());
            reason1.setType(ROIConstants.STATUS_REASON);
            reason1.setStatus(ROIConstants.STATUS_DENIED_ID);

            long id1  = _adminService.createReason(reason1);
            assertNotSame("The created reason id should be greater than zero", 0, id1);

        } catch (ROIException e) {
            fail("Creating a status reason with duplicate "
                 + "disptext in different status should not throw exception." + e.getErrorCode());
        }
    }

    /**
     * Tests creation of new Request Reason with null user
     */
    public void testCreateRequestReasonWithNullUser() {

        try {

            initSession(null);
            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.REQUEST_REASON);
            reason.setTpo(true);
            reason.setStatus(_statusNotApplicable);

            long id = _adminService.createReason(reason);
            fail("Creating a status reason with null user is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REASON_OPERATION_FAILED);
        }
    }

    /**
     * Tests update of an existing request reason
     */
    public void testUpdateRequestReason() {

        try {

            initSession();
            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.REQUEST_REASON);
            reason.setTpo(true);
            reason.setStatus(_statusNotApplicable);
            long requestReasonId  = _adminService.createReason(reason);

            Reason retrievedReason = _adminService.retrieveReason(requestReasonId);

            retrievedReason.setName("TEST" + System.nanoTime());
            retrievedReason.setDisplayText("TEST" + System.nanoTime());
            retrievedReason.setTpo(false);
            retrievedReason.setNonTpo(true);

            Reason updatedReason = _adminService.updateReason(retrievedReason);

            Reason rea1 = _adminService.retrieveReason(updatedReason.getId());
            rea1.setName(_reasonName + System.nanoTime());
            Reason rea2 = _adminService.updateReason(rea1);

            assertNotSame(rea1.getRecordVersion(), rea2.getRecordVersion());
            assertEquals(retrievedReason.getName(), updatedReason.getName());
            assertEquals(retrievedReason.getDisplayText(), updatedReason.getDisplayText());
        } catch (ROIException e) {
            fail("Updating reason should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests update of an existing request reason
     */
    public void testUpdateRequestReasonWithInvalidData() {

        try {

            initSession();
            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.REQUEST_REASON);
            reason.setTpo(true);
            reason.setStatus(_statusNotApplicable);
            long id = _adminService.createReason(reason);

            Reason retrievedReason = _adminService.retrieveReason(id);

            retrievedReason.setName(appendString(_invalidReasonName));
            retrievedReason.setDisplayText(appendString(_reasonDisplayText));
            retrievedReason.setTpo(false);
            retrievedReason.setNonTpo(true);

            Reason updatedReason = _adminService.updateReason(retrievedReason);

            fail("Updating reason with invalid data not allowed.");
        } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.REASON_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.REASON_NAME_LENGTH_EXCEEDS);
            assertError(e, ROIClientErrorCodes.REASON_DISPLAY_TEXT_LENGTH_EXCEEDS);
        }
    }

    /**
     * Tests update of an existing request reason's attribute
     */
    public void testUpdateRequestReasonAttributeAsBoth() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.REQUEST_REASON);
            reason.setTpo(true);
            reason.setStatus(_statusNotApplicable);
            long requestReasonId  = _adminService.createReason(reason);

            Reason retrievedReason = _adminService.retrieveReason(requestReasonId);

            retrievedReason.setName("TEST1" + System.nanoTime());
            retrievedReason.setDisplayText("TEST1" + System.nanoTime());
            retrievedReason.setTpo(true);
            retrievedReason.setNonTpo(true);

            Reason updatedReason = _adminService.updateReason(retrievedReason);

            assertEquals(retrievedReason.getName(), updatedReason.getName());
            assertEquals(retrievedReason.getDisplayText(), updatedReason.getDisplayText());
        } catch (ROIException e) {
            fail("Service method updateReason should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests update of an existing request reason with duplicate name
     */
    public void testUpdateRequestReasonWithDuplicateName() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.REQUEST_REASON);
            reason.setTpo(true);
            reason.setStatus(_statusNotApplicable);
            long requestReasonId  = _adminService.createReason(reason);

            Reason retrievedReason  = _adminService.retrieveReason(requestReasonId);

            Reason reason1 = new Reason();
            reason1.setName(_reasonName + System.nanoTime());
            reason1.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason1.setType(ROIConstants.REQUEST_REASON);
            reason1.setNonTpo(true);
            reason1.setStatus(_statusNotApplicable);

            long id = _adminService.createReason(reason1);

            Reason retriveForUpdate = _adminService.retrieveReason(id);

            retrievedReason.setName(retriveForUpdate.getName());
            retrievedReason.setDisplayText(_reasonDisplayText + System.nanoTime());

            Reason updatedReason = _adminService.updateReason(retrievedReason);

            fail("Updating request reason with duplicate name is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUEST_REASON_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * Tests update of an existing request reason with duplicate display text
     */
    public void testUpdateRequestReasonWithDuplicateDispText() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.REQUEST_REASON);
            reason.setTpo(true);
            reason.setStatus(_statusNotApplicable);
            long requestReasonId  = _adminService.createReason(reason);

            Reason retrievedReason  = _adminService.retrieveReason(requestReasonId);

            Reason reason1 = new Reason();
            reason1.setName(_reasonName + System.nanoTime());
            reason1.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason1.setType(ROIConstants.REQUEST_REASON);
            reason1.setNonTpo(true);
            reason1.setStatus(_statusNotApplicable);

            long id = _adminService.createReason(reason1);

            Reason retriveForUpdate = _adminService.retrieveReason(id);

            retrievedReason.setName(_reasonName + System.nanoTime());
            retrievedReason.setDisplayText(retriveForUpdate.getDisplayText());

            Reason updatedReason = _adminService.updateReason(retrievedReason);

            fail("Updating request reason with duplicate display text is not permitted, "
        		+ "but updated");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REASON_DISPLAY_TEXT_IS_NOT_UNIQUE);
        }
    }

    /**
     * Tests update of an existing adjustment reason
     */
    public void testUpdateAdjustmentReason() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.ADJUSTMENT_REASON);
            reason.setStatus(_statusNotApplicable);

            long id = _adminService.createReason(reason);

            Reason retrievedReason  = _adminService.retrieveReason(id);


            retrievedReason.setName(_reasonName + System.nanoTime());
            retrievedReason.setDisplayText(_reasonDisplayText + System.nanoTime());

            Reason updatedReason = _adminService.updateReason(retrievedReason);

            assertEquals(retrievedReason.getName(), updatedReason.getName());
            assertEquals(retrievedReason.getDisplayText(), updatedReason.getDisplayText());
        } catch (ROIException e) {
            fail("Service method update adjustment reason should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * Tests update of an existing adjustment reason with duplicate name
     */
    public void testUpdateAdjustmentReasonWithDuplicateName() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.ADJUSTMENT_REASON);
            reason.setStatus(_statusNotApplicable);
            long id = _adminService.createReason(reason);

            Reason retrievedReason  = _adminService.retrieveReason(id);

            Reason reason1 = new Reason();
            reason1.setName(_reasonName + System.nanoTime());
            reason1.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason1.setType(ROIConstants.ADJUSTMENT_REASON);
            reason1.setStatus(_statusNotApplicable);

            long id1 = _adminService.createReason(reason1);

            Reason retriveForUpdate = _adminService.retrieveReason(id1);

            retrievedReason.setName(retriveForUpdate.getName());
            retrievedReason.setDisplayText(_reasonDisplayText + System.nanoTime());

            Reason updatedReason = _adminService.updateReason(retrievedReason);

            fail("Updating adjustment reason with duplicate name is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.ADJUSTMENT_REASON_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * Tests update of an existing adjustment reason with null user
     */
    public void testUpdateAdjustmentReasonWithNullUser() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.ADJUSTMENT_REASON);
            reason.setStatus(_statusNotApplicable);
            long id = _adminService.createReason(reason);

            initSession(null);
            Reason retrievedReason = _adminService.retrieveReason(id);

            retrievedReason.setName(_reasonName + System.nanoTime());
            retrievedReason.setDisplayText(_reasonDisplayText + System.nanoTime());

            Reason updatedReason = _adminService.updateReason(retrievedReason);

            fail("Updating adjustment reason with null user is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REASON_OPERATION_FAILED);
        }
    }

    /**
     * Tests update of an existing status reason
     */
    public void testUpdateStatusReason() {

        try {

            initSession();
            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.STATUS_REASON);
            reason.setStatus(ROIConstants.STATUS_DENIED_ID);
            long id = _adminService.createReason(reason);

            Reason retrievedReason  = _adminService.retrieveReason(id);

            retrievedReason.setName(_reasonName + System.nanoTime());
            retrievedReason.setDisplayText(_reasonDisplayText + System.nanoTime());

            Reason updatedReason = _adminService.updateReason(retrievedReason);

            assertEquals(retrievedReason.getName(), updatedReason.getName());
            assertEquals(retrievedReason.getDisplayText(), updatedReason.getDisplayText());
        } catch (ROIException e) {
            fail("Service method updateStatusReason should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * Tests update of an existing status reason with duplicate name
     */
    public void testUpdateStatusReasonWithDuplicateName() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.STATUS_REASON);
            reason.setStatus(ROIConstants.STATUS_DENIED_ID);
            long id = _adminService.createReason(reason);

            Reason retrievedReason  = _adminService.retrieveReason(id);

            Reason reason1 = new Reason();
            reason1.setName(_reasonName + System.nanoTime());
            reason1.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason1.setType(ROIConstants.STATUS_REASON);
            reason1.setStatus(ROIConstants.STATUS_DENIED_ID);

            long id1 = _adminService.createReason(reason1);
            Reason retriveForUpdate = _adminService.retrieveReason(id1);

            retrievedReason.setName(retriveForUpdate.getName());
            retrievedReason.setDisplayText(_reasonDisplayText + System.nanoTime());

            Reason updatedReason = _adminService.updateReason(retrievedReason);

            fail("Updating status reason with duplicate name is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.STATUS_REASON_NAME_IS_NOT_UNIQUE);

        }
    }

    /**
     * Tests update of an existing status reason with duplicate display text
     */
    public void testUpdateStatusReasonWithDuplicateDispText() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.STATUS_REASON);
            reason.setStatus(ROIConstants.STATUS_DENIED_ID);
            long id = _adminService.createReason(reason);

            Reason retrievedReason  = _adminService.retrieveReason(id);

            Reason reason1 = new Reason();
            reason1.setName(_reasonName + System.nanoTime());
            reason1.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason1.setType(ROIConstants.STATUS_REASON);
            reason1.setStatus(ROIConstants.STATUS_DENIED_ID);

            long id1 = _adminService.createReason(reason1);

            Reason retriveForUpdate = _adminService.retrieveReason(id1);

            retrievedReason.setName(_reasonName + System.nanoTime());
            retrievedReason.setDisplayText(retriveForUpdate.getDisplayText());

            Reason updatedReason = _adminService.updateReason(retrievedReason);

            fail("Updating status reason with duplicate display text is not permitted, "
                + "but updated");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.STATUS_REASON_DISPLAY_TEXT_IS_NOT_UNIQUE);
        }
    }

    /**
     * Tests retrieve of all reasons by type
     */
    public void testRetrieveAllReasonsByType() {

        ReasonsList list = new ReasonsList();
        list = _adminService.retrieveAllReasonsByType(ROIConstants.ADJUSTMENT_REASON);
        assertEquals(true, list.getReasons().size() > 0);
    }

    /**
     * Tests retrieve of all reasons by type
     */
    public void testRetrieveAllReasonsByTypeByStatusReason() {

        ReasonsList list = new ReasonsList();
        list = _adminService.retrieveAllReasonsByType(ROIConstants.STATUS_REASON);
        assertEquals(true, list.getReasons().size() > 0);
    }

    /**
     * Tests retrieve of a reason for a specified reason id
     */
    public void testRetrieveReasonById() {

        try {

            Reason reason1 = new Reason();
            reason1.setName(_reasonName + System.nanoTime());
            reason1.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason1.setType(ROIConstants.REQUEST_REASON);
            reason1.setNonTpo(true);
            reason1.setStatus(_statusNotApplicable);

            long id = _adminService.createReason(reason1);

            Reason retrievedReason = _adminService.retrieveReason(id);
            assertEquals(retrievedReason.getId(), id);
        } catch (ROIException e) {
            fail("Retrieve reason by id should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Tests retrieve of a reason with invalid Id
     */
    public void testRetrieveReasonByInvalidId() {

        try {

            final long id = 0;
            Reason retrievedReason = _adminService.retrieveReason(id);
            fail("Retrieve reason by invalid id is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * Tests delete of an existing reason
     */
    public void testDeleteReason() {

        try {

            Reason reason = new Reason();
            reason.setName(_reasonName + System.nanoTime());
            reason.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason.setType(ROIConstants.REQUEST_REASON);
            reason.setNonTpo(true);
            reason.setStatus(_statusNotApplicable);
            long requestReasonId  = _adminService.createReason(reason);
            _adminService.deleteReason(requestReasonId);

            Reason rea = _adminService.retrieveReason(requestReasonId);
            fail("Retrieving the deleted reason is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * Tests delete of an existing reason with invalid Id
     */
    public void testDeleteReasonWithInvalidId() {

        try {

            final long id = 0;
            _adminService.deleteReason(id);
            fail("Deleting the reason with invalid id is not permitted, but deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * Tests delete of an existing reason with null user
     */
    public void testDeleteReasonWithNullUser() {

        try {

            Reason reason1 = new Reason();
            reason1.setName(_reasonName + System.nanoTime());
            reason1.setDisplayText(_reasonDisplayText + System.nanoTime());
            reason1.setType(ROIConstants.REQUEST_REASON);
            reason1.setNonTpo(true);
            reason1.setStatus(_statusNotApplicable);

            long id = _adminService.createReason(reason1);

            initSession(null);
            _adminService.deleteReason(id);
            fail("Deleting the reason with null user is not permitted, but deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REASON_OPERATION_FAILED);
        }
    }

    /**
     * Tests retrieve all request status service
     */
    public void testRetrieveAllRequestStatus() {

        try {

            RequestStatusMap statusMap = _adminService.retrieveAllRequestStatus();
            assertNotNull(statusMap.getStatusMap());
            assertNotNull(statusMap.getLoggedStatus());
        } catch (ROIException e) {
            fail("Retrieve All Request Status should not have failed" + e.getCause());
        }
    }

    /**
     * This test case to retrieve the reasons by status
     */
    public void testRetrieveReaosnsByStatus() {

        Reasons reasons = _adminService.retrieveReasonsByStatus(ROIConstants
                                                                     .STATUS_AUTH_RECEIVED_ID);
        assertNotNull(reasons);
    }

    /**
     * This test case is to cover the Reasons model
     */
    public void testReasons() {

        Reasons rea = new Reasons();
        rea.setStatusReasons(new ArrayList<String>());
        assertNotNull(rea.getStatusReasons());
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }
}
