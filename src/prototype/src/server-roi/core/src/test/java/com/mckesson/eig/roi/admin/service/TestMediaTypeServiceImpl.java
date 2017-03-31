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
import java.util.Random;
import java.util.Set;

import com.mckesson.eig.roi.admin.model.BillingTier;
import com.mckesson.eig.roi.admin.model.MediaType;
import com.mckesson.eig.roi.admin.model.MediaTypesList;
import com.mckesson.eig.roi.admin.model.PageLevelTier;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.test.BaseROITestCase;


/**
 * @author manikandans
 * @date   Mar 31, 2009
 * @since  HPF 13.1 [ROI]; Mar 26, 2008
 */
public class TestMediaTypeServiceImpl
extends BaseROITestCase {

    protected static final String ADMIN_SERVICE =
        "com.mckesson.eig.roi.admin.service.BillingAdminServiceImpl";

    private static BillingAdminService _adminService;
    private static String              _mediaTypeName;
    private static String              _mediaTypeInvalidName;
    private static String              _mediaTypeDesc;
    private static Set<PageLevelTier>  _bTDetails;
    private static Random random = new Random();


    @Override
    public void initializeTestData()
    throws Exception {

        _mediaTypeName = "Media";
        _mediaTypeInvalidName = "@$^";
        _mediaTypeDesc = "MediaDesc." + random.nextInt();
        _adminService = (BillingAdminService) getService(ADMIN_SERVICE);
    }

    private static long _forDelete;
    private static long _forUpdate;
    private static long _mediaTypeIdForUpdate;
    private static long _mediaTypeIdForDelete;

    /**
     * This test case is for creating the media type and verifying if it returns the newly generated
     * id .
     */
    public void testCreateMediaTypeWithvalid() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setName(_mediaTypeName + random.nextInt());
            mediaType.setDescription(_mediaTypeDesc);
            _forUpdate = _adminService.createMediaType(mediaType);
           assertNotSame("Created mediatype id should be greater than zero", 0, _forUpdate);
        } catch (ROIException e) {
            fail("Create mediaType should not thrown exception." + e.getErrorCode());
        }
    }
    /**
     * This test case is for creating the media type with invalid data.
     * id .
     */
    public void testCreateMediaTypeWithInvalidData() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setName(appendString(_mediaTypeInvalidName));
            mediaType.setDescription(appendString(_mediaTypeDesc));
            _forUpdate = _adminService.createMediaType(mediaType);
            fail("Creation of media type with Invalid data is not permitted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_DESC_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This method create id for delete
     */
    public void testCreateMediaTypeForId() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setName(_mediaTypeName + random.nextInt() + "one");
            mediaType.setDescription(_mediaTypeDesc);
            _forDelete = _adminService.createMediaType(mediaType);
            assertNotSame("Created mediatype id should be greater than zero", 0, _forDelete);
        } catch (ROIException e) {
            fail("Create mediaType should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case is for creating the media type with valid input
     * and verifying if it returns the newly generated id for delete .
     */
    public void testCreateMediaTypeIdForDelete() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setName(_mediaTypeName + random.nextInt() + "test");
            mediaType.setDescription(_mediaTypeDesc);
            _mediaTypeIdForDelete = _adminService.createMediaType(mediaType);
            assertNotSame("Created mediatype id should be greater than zero",
                          0,
                          _mediaTypeIdForDelete);

        } catch (ROIException e) {
            fail("Create mediaType should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case is for creating the media type with valid input
     * and verifying if it returns the newly generated id for update .
     */
    public void testCreateMediaTypeIdForUpdate() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setName(_mediaTypeName + random.nextInt() + "test1");
            mediaType.setDescription(_mediaTypeDesc);
            _mediaTypeIdForUpdate = _adminService.createMediaType(mediaType);
            assertNotSame("Created mediatype id should be greater than zero",
                          0,
                          _mediaTypeIdForUpdate);

        } catch (ROIException e) {
            fail("Create mediaType should not throw an exception." + e.getErrorCode());
        }
    }

    /**
     * this method create media type with null user
     */
    public void testCreateMediaTypeWithNullUser() {

        try {

            initSession(null);
            MediaType mediaType = new MediaType();
            mediaType.setName(_mediaTypeName + random.nextInt() + "test2");
            mediaType.setDescription(_mediaTypeDesc);
            long mediaTypeSeq  = _adminService.createMediaType(mediaType);
            fail("Creation of media type with null user is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_OPERATION_FAILED);
        }
    }

    /**
     * This test case is for creating the media type with duplicate name and verifying if it returns
     * the appropriate ROIException .
     */
    public void testCreateMediaTypeWithDuplicateName() {

        try {

            initSession();
            MediaType mediaType = new MediaType();
            mediaType.setName(_mediaTypeName);
            mediaType.setDescription(_mediaTypeDesc);

            _adminService.createMediaType(mediaType);
            _adminService.createMediaType(mediaType);
            fail("Create media type with duplicate name is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_NAME_IS_NOT_UNIQUE);

        }
    }

    /**
     * This test case is for creating the media type with no name and verifying if it returns the
     * appropriate ROIException .
     */
    public void testCreateMediaTypeWithoutName() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setName(null);
            mediaType.setDescription(_mediaTypeDesc);
            long mediaTypeSeq = _adminService.createMediaType(mediaType);
            fail("Create media type without name is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_NAME_IS_BLANK);
        }
    }

    /**
     * This test case is for creating the media type with no description and verifying if it returns
     * the appropriate ROIException .
     */
    public void testCreateMediaTypeWithoutDescription() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setName(_mediaTypeName + random.nextInt() + "desc");
            long mediaTypeId = _adminService.createMediaType(mediaType);
            assertNotSame("Created media type id should be greater than zero",
                          0,
                          mediaTypeId);

        } catch (ROIException e) {
            fail("Create mediaType should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case is for creating the media type with no description and verifying if it returns
     * the appropriate ROIException .
     */
    public void testCreateMediaTypeWithBlankDesc() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setName(_mediaTypeName + random.nextInt() + "Name");
            mediaType.setDescription("");
            long mediaTypeId = _adminService.createMediaType(mediaType);
            assertNotSame("Created media type id should be greater than zero",
                          0,
                          mediaTypeId);

        } catch (ROIException e) {
            fail("Create mediaType should not throw an exception." + e.getErrorCode());
        }
    }

    /**
     * This test case is for creating the media type with null media type and verifying if it
     * returns the appropriate ROIException .
     */
    public void testCreateMediaTypeWithNull() {

        try {

            long mediaTypeSeq = _adminService.createMediaType(null);
            fail("Create mediaType with null is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_OPERATION_FAILED);
        }
    }

    /**
     * This test case is for creating the media type with duplicate name and verifying if it returns
     * the appropriate ROIException .
     */
    public void testCreateMediaTypeElectronic() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setName("Electronic");
            mediaType.setDescription(_mediaTypeDesc);

            long mediaTypeSeq = _adminService.createMediaType(mediaType);
            fail("Create mediaType with duplicate name is not permitted, but created");
        } catch (ROIException e) {
           assertError(e, ROIClientErrorCodes.MEDIA_TYPE_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * This test case check the media type name length
     */
    public void testCreateMediaTypeWithMoreLengthName() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setName(_mediaTypeName + random.nextInt() + "TEST TESATASTEASDTEASTASDTAE");
            mediaType.setDescription(_mediaTypeDesc);

            long mediaTypeSeq = _adminService.createMediaType(mediaType);
            fail("Create mediaType with more name length is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_NAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case check the media type name length
     */
    public void testCreateMediaTypeWithMoreLengthDesc() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setName(_mediaTypeName + random.nextInt() + "DESC");
            mediaType
            .setDescription(_mediaTypeDesc  + new StringBuffer()
                            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
                            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
                            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
                            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
                            .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAAAA")
                            .toString());

            long mediaTypeSeq = _adminService.createMediaType(mediaType);
            fail("Create mediaType with more desc length is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_DESC_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case check the media type name and desc length
     */
    public void testCreateMediaTypeWithMoreLength() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setName(_mediaTypeName + random.nextInt() + new StringBuffer()
                             .append("MEDIA TYPE NAMEMEDIA TYPE NAMEMEDIA TYPE NAMEMEDIA TYPE NAME")
                             .append("MEDIA TYPE NAMEMEDIA TYPE NAMEMEDIA TYPE NAMEMEDIA TYPE NAME")
                             .toString());
            mediaType.setDescription(_mediaTypeDesc  + new StringBuffer()
                            .append("MEDIA TYPE DESC MEDIA TYPE DESC MEDIA TYPE DESC MEDIA TYPE ")
                            .append("MEDIA TYPE DESC MEDIA TYPE DESC MEDIA TYPE DESC MEDIA TYPE ")
                            .append("MEDIA TYPE DESCMEDIA TYPE DESCMEDIA TYPE DESCMEDIA TYPE DESC")
                            .append("MEDIA TYPE DESCMEDIA TYPE DESCMEDIA TYPE DESCMEDIA TYPE DESC")
                            .append("MEDIA TYPE DESCMEDIA TYPE DESCMEDIA TYPE DESCMEDIA TYPE DESC")
                            .toString());

            long mediaTypeSeq = _adminService.createMediaType(mediaType);
            fail("Create mediaType with more name length is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_DESC_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case is for deleting the given media type id and to check whether it return nothing
     * or it throws any exception
     */
    public void testDeleteMediaType() {

        try {

            initSession();
            _adminService.deleteMediaType(_forDelete);
            _adminService.retrieveMediaType(_forDelete);
            fail("Retrieving the deleted mediatype id is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This test case is for deleting the given media type id with null user
     */
    public void testDeleteMediaTypeWithNullUser() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setName(_mediaTypeName + random.nextInt()+"del");
            mediaType.setDescription(_mediaTypeDesc+"del");
            _mediaTypeIdForDelete = _adminService.createMediaType(mediaType);
            initSession(null);
            _adminService.deleteMediaType(_mediaTypeIdForDelete);
            fail("Deleting media type with null user is not permitted, but it is deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_OPERATION_FAILED);
        }
    }

    /**
     * This method test seed data deletion
     */
    public void testDeleteSeedData() {

        try {

            initSession();
            final long seedDataId = -1;
            _adminService.deleteMediaType(seedDataId);
            fail("Deleting mediaType with seed data is not permitted, but deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_SEED_DATA_IS_BEING_DELETED);
        }
    }

    /**
     * This method checks the deletion with invalid id ROIException should be thrown with error code
     */
    public void testDeleteMediaTypeWithInvalidId() {

        try {

            final long invalidId = 100001;
            _adminService.deleteMediaType(invalidId);
            fail("Delete mediaType with invalid id is not permitted, but deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This test case is for updating the given media type.
     */
    public void testUpdateMediaType() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setName(_mediaTypeName + random.nextInt());
            mediaType.setDescription(_mediaTypeDesc);
            _forUpdate = _adminService.createMediaType(mediaType);
            mediaType.setId(_forUpdate);
            mediaType.setName(_mediaTypeName + random.nextInt());
            mediaType.setDescription("Update descript");
            MediaType updatedMT = _adminService.updateMediaType(mediaType);
            assertEquals(updatedMT.getName(), mediaType.getName());
            assertEquals(updatedMT.getDescription(), mediaType.getDescription());
        } catch (ROIException e) {
            fail("Deleting media type should not thrown exception" + e.getErrorCode());
        }
    }

    /**
     * This test case is for creating the media type with invalid data.
     * id .
     */
    public void testUpdateMediaTypeWithInvalidData() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setId(_forUpdate);
            mediaType.setName(appendString(_mediaTypeInvalidName));
            mediaType.setDescription(appendString(_mediaTypeDesc));
            MediaType updatedMT = _adminService.updateMediaType(mediaType);
            fail("Updation of media type with Invalid data is not permitted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_DESC_LENGTH_EXCEEDS_LIMIT);
        }
    }
    /**
     * This test case is for updating the given media type with null user.
     */
    public void testUpdateMediaTypeWitNullUser() {

        try {

            initSession(null);
            MediaType mediaType =  _adminService.retrieveMediaType(_mediaTypeIdForUpdate);
            mediaType.setName("updatemediatype" + System.currentTimeMillis());
            mediaType.setDescription("Update descript");
            MediaType updatedMT = _adminService.updateMediaType(mediaType);
            fail("Updating the media type with null user is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_OPERATION_FAILED);
        }
    }

    /**
     * This test case is for updating the given media type with modified seed data name.
     */
    public void testUpdateMediaTypeSeedDataName() {

        try {

            initSession();
            final long id = -1;
            MediaType mediaType = new MediaType();
            mediaType.setId(id);
            mediaType.setName("ElectroMechanic");
            mediaType.setDescription("Update descript");
            _adminService.updateMediaType(mediaType);
            fail("Udating media type seed data name is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_SEED_DATA_NAME_IS_BEING_EDITED);
        }
    }

    /**
     * This test case is for updating the given media type with modified seed data name.
     */
    public void testUpdateMediaTypeSeedDataDescription() {

        try {

            final long id = -1;
            MediaType mediaType = _adminService.retrieveMediaType(id);
            mediaType.setDescription(_mediaTypeDesc + "Seed Data");
            MediaType updatedMT = _adminService.updateMediaType(mediaType);
            assertEquals(updatedMT.getName(), mediaType.getName());
            assertEquals(updatedMT.getDescription(), mediaType.getDescription());
        } catch (ROIException e) {
            fail("Updating mediatype should not throw an exception" + e.getErrorCode());
        }
    }

    /**
     * This test case is for updating the given media type with modified seed data name.
     */
    public void testUpdateMediaTypeWithDuplication() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setName(ROIConstants.MEDIA_TYPE_NAME);
            mediaType.setDescription(_mediaTypeDesc);
            _adminService.createMediaType(mediaType);
            _adminService.updateMediaType(mediaType);
            fail("updating mediatype with duplicate name is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * This test case is for updating the given media type with null.
     */
    public void testUpdateMediaTypeWithNull() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setId(_forUpdate);
            mediaType.setName(null);
            mediaType.setDescription(_mediaTypeDesc + "Seed Data");
            _adminService.updateMediaType(mediaType);
            fail("Updating mediatype with name as null is not permitted, but updated");
            } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_NAME_IS_BLANK);
        }
    }

    /**
     * This test case is for updating the given media type with empty.
     */
    public void testUpdateMediaTypeWithEmpty() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setId(_forUpdate);
            mediaType.setName("");
            mediaType.setDescription(_mediaTypeDesc + "Seed Data");
            _adminService.updateMediaType(mediaType);
            fail("Updating media type with name as blank is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_NAME_IS_BLANK);
        }
    }

    /**
     * This test case is for updating the media type name with exceeded length.
     */
    public void testUpdateMediaTypeWithMoreSize() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setId(_forUpdate);
            mediaType.setName(_mediaTypeName + random.nextInt() + "asdkflasdklfaslkdfjalskdfjlask");
            mediaType.setDescription(_mediaTypeDesc);
            _adminService.updateMediaType(mediaType);
            fail("Updating media type with name more length is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_NAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case is for updating the given media type with empty.
     */
    public void testUpdateMediaTypeWithSpace() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setId(_forUpdate);
            mediaType.setName("  ");
            mediaType.setDescription(_mediaTypeDesc);
            _adminService.updateMediaType(mediaType);
            fail("Updating media type with name empty is not permitted, but updated.");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MEDIA_TYPE_NAME_IS_BLANK);
        }
    }

    /**
     * This test case is to get the media type.
     */
    public void testRetrieveMediaType() {

        try {

            MediaType mediaType = new MediaType();
            mediaType.setName(_mediaTypeName + random.nextInt() + "get");
            mediaType.setDescription(_mediaTypeDesc + "test");
            long mediaTypeId = _adminService.createMediaType(mediaType);
            MediaType mt =  _adminService.retrieveMediaType(mediaTypeId);
            assertEquals(mt.getId(), mediaTypeId);
            assertEquals(false, mt.getIsAssociated());
        } catch (ROIException e) {
            fail("Retrieve mediaType should not throw an exception." + e.getErrorCode());
        }
    }

    /**
     * This test case is to get the media type.
     */
    public void testRetrieveMediaTypeWithInvalidId() {

        try {

            MediaType mt = _adminService.retrieveMediaType(Integer.MAX_VALUE);
            fail("Retrieving mediaType with invalid id is not permitted, but retrieved.");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This method retrieve all the media types successfully
     */
    public void testRetrieveAllMediaType() {

        try {

            MediaTypesList ml = new MediaTypesList();
            ml = _adminService.retrieveAllMediaTypes(true);
            assertNotSame("The size of retrieved media types should be greater than zero",
                          0,
                          ml.getMediaTypesList().size());

        } catch (ROIException e) {
            fail("Service method test retrieveAllMediaType should not thrown exception");
        }
    }

    /**
     * This method retrieve all the media types successfully
     */
    public void testRetrieveAllMediaTypeNames() {

        try {

            MediaTypesList ml = new MediaTypesList();
            ml = _adminService.retrieveAllMediaTypes(false);
            assertNotSame("The size of retrieved media types should be greater than zero",
                          0,
                          ml.getMediaTypesList().size());

        } catch (ROIException e) {
            fail("Service method test retrieveAllMediaType should not thrown exception");
        }
    }


    private static MediaType _mediaTypeObj1;
    private static MediaType _mediaTypeObj2;

    public void testConcorrency() {

        try {

            MediaType mt = _adminService.retrieveMediaType(_forUpdate);

            _mediaTypeObj1 = (MediaType) deepCopy(mt);
            _mediaTypeObj1.setDescription("testasdf");
            _adminService.updateMediaType(_mediaTypeObj1);

            _mediaTypeObj2 = (MediaType) deepCopy(mt);
            _mediaTypeObj2.setDescription("MediaType description");

            _adminService.updateMediaType(_mediaTypeObj2);

            fail("Service method updateMediaType should throw an ROIException");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION);
        } catch (Exception e) {
            fail("Service method updateMediaType should not throw an Exception"  + e.getMessage());
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

    /**
     * This test case for delete the media type associate with billingTier
     * and verify if it returns the appropriate exception
     */
    public void testMediaTypeAssociationForDelete() {

       try {
           MediaType mt =  new MediaType();
           mt.setName(_mediaTypeName + random.nextInt() + "aaa");
           mt.setDescription(_mediaTypeDesc + "forassociation");
           long id = _adminService.createMediaType(mt);

           BillingTier billingTier = new BillingTier();

           billingTier.setName("a" + System.nanoTime());
           billingTier.setDescription("btdesc");

           mt.setId(id);
           billingTier.setMediaType(mt);

           final float bc = 11;
           billingTier.setBaseCharge(bc);
           final float dbc = 10;
           billingTier.setDefaultPageCharge(dbc);

           PageLevelTier bTD = new PageLevelTier();
           bTD.setPage(1);
           final float pc = 12;
           bTD.setPageCharge(pc);
           _bTDetails = new HashSet <PageLevelTier>();
           _bTDetails.add(bTD);
           billingTier.setPageLevelTier(_bTDetails);
           BillingTier bt = _adminService.createBillingTier(billingTier);

           _adminService.deleteMediaType(id);
           fail("Deleting the media type association with billing template is not deleted, "
           		 + "but deleted");
       } catch (ROIException e) {
           assertError(e, ROIClientErrorCodes.MEDIA_TYPE_ASSOCIATED_WITH_BILLING_TIER);
       }

    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }
}
