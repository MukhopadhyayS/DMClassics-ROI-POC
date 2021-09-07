/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.inuse.service.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.util.ObjectUtils;

import com.mckesson.eig.inuse.base.api.InUseClientErrorCodes;
import com.mckesson.eig.inuse.base.api.InUseException;
import com.mckesson.eig.inuse.dao.InUseDAO;
import com.mckesson.eig.inuse.model.InUseRecord;
import com.mckesson.eig.inuse.model.InUseRecordList;
import com.mckesson.eig.inuse.service.InUseService;
import com.mckesson.eig.inuse.service.InUseServiceImpl;
import com.mckesson.eig.utility.util.SpringUtilities;

/**
 *
 * @author OFS
 * @date   Nov 11, 2008
 * @since  ROI HPF 13.1
 */
public class TestInUseService
extends BaseInUseTestCase {

    private static final long ONE_MINUTE = 60000;
    private static final String TEST_OBJECT_TYPE = "JUnitTestObjectType";
    private static final int TEST_EXPIRES_MINUTES = 1;
    private static final long TEST_USER_SEQUENCE = 151;
    private static final String TEST_USER_ID = "JUnit User";
    private static final String TEST_APPLICATION_ID = "TestRunner";
    private static final String TEST_OBJECT_ID = "TestObject";

    private InUseService _service;
    private InUseRecord _existingRecord;
    private InUseDAO _dao;

    private static int _counter = 1;

    @Override
    public void setUp() throws Exception {

        super.setUp();
        _service = (InUseService) SpringUtilities.getInstance().getBeanFactory().getBean(
                "com.mckesson.eig.inuse.service.InUseService");
        _service.setGracePeriodMinutes(1);
        _dao = (InUseDAO) SpringUtilities.getInstance().getBeanFactory().getBean("InUseDAO");

        _existingRecord = new InUseRecord();
        _existingRecord.setObjectType(TEST_OBJECT_TYPE);
        _existingRecord.setObjectID(createNewObjectID());
        _existingRecord.setApplicationID(createNewApplicationID());
        _existingRecord.setUserID(TEST_USER_ID);
        _existingRecord.setExpiresMinutes(TEST_EXPIRES_MINUTES);
        _existingRecord.setCreatedBy(TEST_USER_SEQUENCE);
        _existingRecord.setCreatedDate(new Date());
        _existingRecord.setModifiedBy(TEST_USER_SEQUENCE);
        _existingRecord.setModifiedDate(new Date());

        _existingRecord = _dao.createRecord(_existingRecord);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        List<InUseRecord> records = _dao.retrieveAllByType(TEST_OBJECT_TYPE);
        if (records.size() > 0) {
            _dao.deleteRecords(records);
        }
    }

    private String createNewObjectID() {
        return TEST_OBJECT_ID + System.currentTimeMillis() + "-" + (_counter++);
    }

    private String createNewApplicationID() {
        try {
            return TEST_APPLICATION_ID + "-"
            + InetAddress.getLocalHost() + "-"
            + System.currentTimeMillis() + "-"
            + (_counter++);
        } catch (UnknownHostException e) {
            return TEST_APPLICATION_ID + "-"
            + System.currentTimeMillis() + "-"
            + (_counter++);
        }
    }

    /**
     * Test method for {@link InUseServiceImpl#releaseInUseRecord(String, String, String)}.
     * @throws UnknownHostException
     */
    @Test
    public void testReleaseInUseRecord() throws UnknownHostException {

        try {
            _service.releaseInUseRecord(
                    _existingRecord.getObjectType(),
                    _existingRecord.getObjectID(),
                    _existingRecord.getApplicationID() + "-Invalid",
                    _existingRecord.getUserID());
            fail("Should have thrown exception");
        } catch (InUseException exception) {
            assertTrue(hasErrorcode(exception, InUseClientErrorCodes.INVALID_APPLICATION_DATA));
        }

        try {
            _service.releaseInUseRecord(
                    _existingRecord.getObjectType(),
                    _existingRecord.getObjectID(),
                    _existingRecord.getApplicationID(),
                    _existingRecord.getUserID() + "-Invalid");
            fail("Should have thrown exception");
        } catch (InUseException exception) {
            assertTrue(hasErrorcode(exception, InUseClientErrorCodes.INVALID_APPLICATION_DATA));
        }

        _service.releaseInUseRecord(
                _existingRecord.getObjectType(),
                _existingRecord.getObjectID(),
                _existingRecord.getApplicationID(),
                _existingRecord.getUserID());

        try {
            _service.releaseInUseRecord(
                    _existingRecord.getObjectType(),
                    _existingRecord.getObjectID(),
                    _existingRecord.getApplicationID(),
                    _existingRecord.getUserID());
            fail("Should have thrown exception");
        } catch (InUseException exception) {
            assertTrue(hasErrorcode(exception, InUseClientErrorCodes.RECORD_NOT_FOUND));
        }
    }

    /**
     * Test method for {@link InUseService#createInUseRecord(String, String, String, String, int)}.
     * @throws UnknownHostException
     */
    @Test
    public void testCreateInUseRecord() throws UnknownHostException {

        try {
            _service.createInUseRecord(
                    _existingRecord.getObjectType(),
                    _existingRecord.getObjectID(),
                    _existingRecord.getApplicationID(),
                    _existingRecord.getUserID(),
                    _existingRecord.getExpiresMinutes());
            fail("Should have thrown exception");
        } catch (InUseException exception) {
            assertTrue(hasErrorcode(exception, InUseClientErrorCodes.RECORD_ALREADY_IN_USE));
        }

        String newID = createNewObjectID();
        InUseRecord record = _service.createInUseRecord(
                _existingRecord.getObjectType(),
                newID,
                _existingRecord.getApplicationID(),
                _existingRecord.getUserID(),
                _existingRecord.getExpiresMinutes());

        assertNotNull(record);
        assertNotNull(record.getCreatedBy());
        assertNotNull(record.getCreatedDate());
        assertNotNull(record.getModifiedBy());
        assertNotNull(record.getModifiedDate());
        assertTrue(record.getRecordSequence() != _existingRecord.getRecordSequence());
        assertEquals(record.getObjectType(), _existingRecord.getObjectType());
        assertFalse(ObjectUtils.nullSafeEquals(
                record.getObjectID(),
                _existingRecord.getObjectID()));
        assertEquals(record.getApplicationID(), _existingRecord.getApplicationID());
        assertEquals(record.getUserID(), _existingRecord.getUserID());
        assertEquals(record.getExpiresMinutes(), _existingRecord.getExpiresMinutes());
    }

    /**
     * Test method for {@link InUseServiceImpl#retrieveInUseRecord(String, String)}.
     */
    @Test
    public void testRetrieveInUseRecord() {
        InUseRecord record = _service.retrieveInUseRecord(
                _existingRecord.getObjectType(),
                _existingRecord.getObjectID());
        assertNotNull(record);
        assertEquals(record.getCreatedBy(), _existingRecord.getCreatedBy());
        assertNotNull(
                datesEqualToMinute(
                        record.getCreatedDate(),
                        _existingRecord.getCreatedDate()));
        assertEquals(record.getModifiedBy(), _existingRecord.getModifiedBy());
        assertNotNull(
                datesEqualToMinute(
                    record.getModifiedDate(),
                    _existingRecord.getModifiedDate()));
        assertEquals(record.getRecordSequence(), _existingRecord.getRecordSequence());
        assertEquals(record.getObjectType(), _existingRecord.getObjectType());
        assertEquals(record.getObjectID(), _existingRecord.getObjectID());
        assertEquals(record.getApplicationID(), _existingRecord.getApplicationID());
        assertEquals(record.getUserID(), _existingRecord.getUserID());
        assertEquals(record.getExpiresMinutes(), _existingRecord.getExpiresMinutes());

        record = _service.retrieveInUseRecord(
                _existingRecord.getObjectType(),
                createNewObjectID());
        assertNull(record);
    }

    /**
     * Test method for {@link InUseServiceImpl#retrieveInUseRecordsByType(String)}.
     */
    @Test
    public void testRetrieveInUseRecordsByType() {
        List<InUseRecord> records = _service.retrieveInUseRecordsByType(
                "Some Bogus Type That Should Not Exist");
        assertNotNull(records);
        assertTrue(records.size() == 0);

        records = _service.retrieveInUseRecordsByType(TEST_OBJECT_TYPE);
        assertNotNull(records);
        assertTrue(records.size() == 1);
    }

    /**
     * Test method for {@link InUseServiceImpl#retrieveAllInUseRecords()}.
     */
    @Test
    public void testRetrieveAllInUseRecords() {
        List<InUseRecord> records = _service.retrieveAllInUseRecords();
        assertNotNull(records);
        assertTrue(records.size() > 0);
    }

    public void testRetrieveObjectIds() {

        try {
            String newID = createNewObjectID();
            InUseRecord record = _service.createInUseRecord(
                    _existingRecord.getObjectType(),
                    newID,
                    _existingRecord.getApplicationID(),
                    _existingRecord.getUserID(),
                    _existingRecord.getExpiresMinutes());

            String id2 = createNewObjectID();
            InUseRecord record1 = _service.createInUseRecord(
                    _existingRecord.getObjectType(),
                    id2,
                    _existingRecord.getApplicationID(),
                    _existingRecord.getUserID(),
                    _existingRecord.getExpiresMinutes());
            InUseRecordList list = _service.retrieveInUseRecordsByIDs(
                                                    _existingRecord.getObjectType(),
                                                    newID + "," + id2,
                                                    _existingRecord.getApplicationID(),
                                                    "temp");
            assertNotNull(list.getList().size() > 0);
            InUseRecordList lst = new InUseRecordList();
            lst.setList(new ArrayList<InUseRecord>());
        } catch (InUseException exception) {
            assertTrue(hasErrorcode(exception, InUseClientErrorCodes.RECORD_ALREADY_IN_USE));
        }
    }

    /**
     * Test method {@link InUseServiceImpl#touchInUseRecord(String, String, String, String, int)}.
     * @throws InterruptedException
     */
    @Test
    public void testTouchInUseRecord() throws InterruptedException {

        try {
            _service.touchInUseRecord(
                    _existingRecord.getObjectType(),
                    _existingRecord.getObjectID(),
                    _existingRecord.getApplicationID() + "-Invalid",
                    _existingRecord.getUserID());
            fail("Should have thrown exception");
        } catch (InUseException exception) {
            assertTrue(hasErrorcode(exception, InUseClientErrorCodes.INVALID_APPLICATION_DATA));
        }

        // make sure that when we touch the record, time has moved forward.
        Thread.sleep(ONE_MINUTE);

        InUseRecord record = _service.touchInUseRecord(
                _existingRecord.getObjectType(),
                _existingRecord.getObjectID(),
                _existingRecord.getApplicationID(),
                _existingRecord.getUserID());
        assertNotNull(record);

        assertNotNull(record);
        assertEquals(record.getCreatedBy(), _existingRecord.getCreatedBy());
        assertNotNull(record.getModifiedBy());

        assertEquals(record.getRecordSequence(), _existingRecord.getRecordSequence());
        assertEquals(record.getObjectType(), _existingRecord.getObjectType());
        assertEquals(record.getObjectID(), _existingRecord.getObjectID());
        assertEquals(record.getApplicationID(), _existingRecord.getApplicationID());
        assertEquals(record.getUserID(), _existingRecord.getUserID());
        assertEquals(record.getExpiresMinutes(), _existingRecord.getExpiresMinutes());
    }

    /**
     * Test method for {@link InUseServiceImpl#clearExpiredRecords()}.
     * @throws InterruptedException
     */
    @Test
    public void testClearExpiredRecords() throws InterruptedException {

        List<InUseRecord> expired = _service.clearExpiredRecords();
        assertNotNull(expired);
        assertEquals(expired.size(), 0);

        Thread.sleep((_existingRecord.getExpiresMinutes()
                        + _service.getGracePeriodMinutes())
                        * ONE_MINUTE);

        expired = _service.clearExpiredRecords();
        assertNotNull(expired);
    }

    public void testInUseRecordCreationWithNullValues() {

        try {
            _service.createInUseRecord(null, null, null, null, 0);
            fail("Should have thrown exception");
        } catch (InUseException e) {
            assertTrue(hasErrorcode(e, InUseClientErrorCodes.OBJECT_TYPE_SHOULD_NOT_BE_EMPTY));
            assertTrue(hasErrorcode(e, InUseClientErrorCodes.OBJECT_ID_SHOULD_NOT_BE_EMPTY));
            assertTrue(hasErrorcode(e, InUseClientErrorCodes.INVALID_APPLICATION_ID));
            assertTrue(hasErrorcode(e, InUseClientErrorCodes.
                                       EXPIRES_MINUTES_SHOULD_NOT_BE_LESS_THAN_ZERO));
        }
    }

    public void testRetrieveInUseRecordNullValues() {

        try {
            _service.retrieveInUseRecord(null, null);
            fail("Should have thrown exception");
        } catch (InUseException e) {
            assertTrue(hasErrorcode(e, InUseClientErrorCodes.OBJECT_TYPE_SHOULD_NOT_BE_EMPTY));
            assertTrue(hasErrorcode(e, InUseClientErrorCodes.OBJECT_ID_SHOULD_NOT_BE_EMPTY));
        }
    }

    public void testReleaseInUseRecordNullValues() {

        try {
            _service.releaseInUseRecord(null, null, null, null);
            fail("Should have thrown exception");
        } catch (InUseException e) {
            assertTrue(hasErrorcode(e, InUseClientErrorCodes.OBJECT_TYPE_SHOULD_NOT_BE_EMPTY));
            assertTrue(hasErrorcode(e, InUseClientErrorCodes.OBJECT_ID_SHOULD_NOT_BE_EMPTY));
            assertTrue(hasErrorcode(e, InUseClientErrorCodes.INVALID_APPLICATION_ID));
        }
    }

    public void testTouchInUseRecordNullValues() {

        try {
            //TEMP
            new InUseServiceImpl();
            _service.touchInUseRecord(null, null, null, null);
            fail("Should have thrown exception");
        } catch (InUseException e) {
            assertTrue(hasErrorcode(e, InUseClientErrorCodes.OBJECT_TYPE_SHOULD_NOT_BE_EMPTY));
            assertTrue(hasErrorcode(e, InUseClientErrorCodes.OBJECT_ID_SHOULD_NOT_BE_EMPTY));
            assertTrue(hasErrorcode(e, InUseClientErrorCodes.INVALID_APPLICATION_ID));
        }
    }

    public void testRetrieveInUseRecordsByTypeNullValues() {

        try {
            _service.retrieveInUseRecordsByType(null);
            fail("Should have thrown exception");
        } catch (InUseException e) {
            assertTrue(hasErrorcode(e, InUseClientErrorCodes.OBJECT_TYPE_SHOULD_NOT_BE_EMPTY));
        }
    }

    public void testRetrieveObjectIdsWithNull() {

        try {
            _service.retrieveInUseRecordsByIDs(null, null, null, null);
            fail("Should have thrown exception");
        } catch (InUseException e) {
            assertTrue(hasErrorcode(e, InUseClientErrorCodes.OBJECT_TYPE_SHOULD_NOT_BE_EMPTY));
            assertTrue(hasErrorcode(e, InUseClientErrorCodes.USER_ID_SHOULD_NOT_BE_EMPTY));
            assertTrue(hasErrorcode(e, InUseClientErrorCodes.INVALID_APPLICATION_ID));
            assertTrue(hasErrorcode(e, InUseClientErrorCodes.OBJECT_ID_SHOULD_NOT_BE_EMPTY));
        }
    }

    private boolean datesEqualToMinute(Date date1, Date date2) {

        if (date1 == null) {
            if (date2 == null) {
                return true;
            }
            return false;
        } else if (date2 == null) {
            return false;
        }

        long millis1 = date1.getTime();
        long millis2 = date2.getTime();

        long secs1 = (long) Math.ceil(millis1 / ONE_MINUTE);
        long secs2 = (long) Math.ceil(millis2 / ONE_MINUTE);
        System.out.println("************** " + secs1 + " = " + secs2);
        return secs1 == secs2;
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }
}
