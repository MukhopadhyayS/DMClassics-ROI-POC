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

import com.mckesson.eig.roi.admin.model.AdminLoV;
import com.mckesson.eig.roi.admin.model.Note;
import com.mckesson.eig.roi.admin.model.NotesList;
import com.mckesson.eig.roi.admin.model.SSNMask;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.test.BaseROITestCase;


/**
 * @author OFS
 * @date   May 23, 2009
 * @since  HPF 13.1 [ROI]; Sep 12, 2008
 */
public class TestNoteServiceImpl
extends BaseROITestCase {

    protected static final String  ADMIN_SERVICE =
        "com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl";

    private static ROIAdminService _adminService;
    private static String          _name;
    private static String          _invalidName;
    @Override
    public void initializeTestData()
    throws Exception {

        _adminService = (ROIAdminService) getService(ADMIN_SERVICE);
        _invalidName = "!@#&.(=";
    }

    /**
     * This test case to create the note
     */
    public void testCreateNote() {

        try {

            long id = _adminService.createNote(createNote(_name + System.nanoTime()));
            assertNotSame("The created note id should be greater than zero", 0, id);
        } catch (ROIException e) {
            fail("Creating note should not throw exception" + e.getErrorCode());
        }
    }

    /**
     * This test case to create the note with invalid data.
     */
    public void testCreateNoteWithInvalidData() {

        try {

            Note note = new Note();
            note.setName(appendString(_invalidName));
            note.setDescription(appendString("note description"));
            _adminService.createNote(note);
            fail("Creating note with invalid data not accepted.");
        } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.NOTE_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.NOTE_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.NOTE_DISPLAY_TEXT_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case to create the note with name null and verify if it returns the
     * appropriate exception
     */
    public void testCreateNoteWithNameNull() {

        try {

            _adminService.createNote(createNote(null));
            fail("Creating note with name null is not accepted, but it created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.NOTE_NAME_IS_NULL);
        }
    }

    /**
     * This test case to create the note with null description and verify if it returns the
     * appropriate exception
     */
    public void testCreateNoteWithDescNull() {

        try {

            Note note = createNote("test");
            note.setDescription(null);
            _adminService.createNote(note);
            fail("Creating note with name null is not accepted, but it created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.NOTE_DISPLAY_TEXT_IS_NULL);
        }
    }

    /**
     * This test case to create the note with maximum length for name field and verify if it returns
     * the appropriate exception
     */
    public void testCreateNoteWithMaxLengthFields() {

        try {

            Note note = new Note();
            note.setName("TESTNAME_TESTNAME_TESTNAME_TESTNAME_TESTNAME_TESTNAME_");
            note.setDescription("TESTDESCRIPTION_TESTDESCRIPTION_TESTDESCRIPTION_TESTDESCRIPTION"
                                + "TESTDESCRIPTION_TESTDESCRIPTION_TESTDESCRIPTION_TESTDESCRIPTION"
                                + "TESTDESCRIPTION_TESTDESCRIPTION_TESTDESCRIPTION_TESTDESCRIPTION"
                                + "TESTDESCRIPTION_TESTDESCRIPTION_TESTDESCRIPTION_TESTDESCRIPTION"
                                + "TESTDESCRIPTION_TESTDESCRIPTION_TESTDESCRIPTION_TESTDESCRIPTIO");
            _adminService.createNote(note);
            fail("Creating note with name null is not accepted, but it created");
        } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.NOTE_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.NOTE_DISPLAY_TEXT_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case to create the note with same name and verify if it returns the
     * appropriate exception
     */
    public void testCreateNoteWithSameName() {

        try {

            long id = _adminService.createNote(createNote(_name + System.nanoTime()));
            Note note = _adminService.retrieveNote(id);
            note.getType();
            _adminService.createNote(createNote(note.getName()));
            fail("Creating note with same name is not applicable, but it created");
        } catch (ROIException e) {
           assertError(e, ROIClientErrorCodes.NOTE_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * This test case to retrieve all note
     */
    public void testRetrieveAllNotes() {

        try {

            NotesList list = new NotesList();
            list =  _adminService.retrieveAllNotes();
            assertNotSame("The size of retrieved notes should be greater than zero",
                           0,
                           list.getNotesList().size());
        } catch (ROIException e) {
            fail("Retrieving all notes should not throw exception" + e.getErrorCode());
        }
    }

    /**
     * This test case to retrieve note
     */
    public void testRetrieveNote() {

        try {

            long id = _adminService.createNote(createNote(_name + System.nanoTime()));
            Note note = _adminService.retrieveNote(id);
            assertEquals(id, note.getId());
        } catch (ROIException e) {
            fail("Retrieving note should not throw exception" + e.getErrorCode());
        }
    }

    /**
     * This test case to retrieve the note with invalid id and verify if it returns the
     * appropriate exception
     */
    public void testRetrieveNoteWithInvalidId() {

        try {
            _adminService.retrieveNote(Integer.MAX_VALUE);
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This test case to delete the note
     */
    public void testDeleteNote() {

        try {

            long id = _adminService.createNote(createNote(_name + System.nanoTime()));
            _adminService.deleteNote(id);
        } catch (ROIException e) {
            fail("Deleting note should not throw exception" + e.getErrorCode());
        }
    }

    /**
     * This test case to retrieve the note with invalid id and verify if it returns the
     * appropriate exception
     */
    public void testDeleteNoteWithInvalidId() {

        try {
            _adminService.deleteNote(Integer.MAX_VALUE);
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This test case to update the note with name null and verify if it returns the
     * appropriate exception
     */
    public void testUpdateNoteWithNameNull() {

        try {

            _adminService.updateNote(createNote(null));
            fail("Updating note with name null is not accepted, but it updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.NOTE_NAME_IS_NULL);
        }
    }
    /**
     * This test case to update the note with invalid data
     */
    public void testUpdateNoteWithInvalidData() {

        try {

            long id = _adminService.createNote(createNote(_name + System.nanoTime()));
            Note note = _adminService.retrieveNote(id);
            note.setName(appendString(_invalidName));
            note.setDescription(appendString("note description"));
            _adminService.updateNote(note);
            fail("Updating note with invalid data is not accepted");
        } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.NOTE_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.NOTE_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.NOTE_DISPLAY_TEXT_LENGTH_EXCEEDS_LIMIT);
        }
    }
    /**
     * This test case to update the note with same name and verify if it returns the
     * appropriate exception
     */
    public void testUpdateNoteWithSameName() {

        try {

            long id = _adminService.createNote(createNote(_name + System.nanoTime()));
            Note note = _adminService.retrieveNote(id);
            _adminService.updateNote(createNote(note.getName()));
            fail("Updating note with same name is not applicable, but it updated");
        } catch (ROIException e) {
           assertError(e, ROIClientErrorCodes.NOTE_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * This test case to retrieve the ssn mask details
     */
    public void testRetrievSSNMask() {

        try {

            SSNMask  mask = _adminService.retrieveSSNMask();
            assertNotNull(mask);
        } catch (ROIException e) {
            fail("Retrieving mask should not throw exception" + e.getErrorCode());
        }
    }

    /**
     * This test case to update the ssn mask details
     */
    public void testUpdateSSNMASK() {

        try {

            SSNMask mask = _adminService.retrieveSSNMask();
            mask.setApplySSNMask(!mask.getApplySSNMask());
            SSNMask updated = _adminService.updateSSNMask(mask);
            assertTrue(mask.getRecordVersion() != updated.getRecordVersion());
        } catch (ROIException e) {
            fail("Updating ssn madk should not throw exception" + e.getErrorCode());
        }
    }

    /**
     * This test case to update the ssn mask details
     */
    public void testUpdateSSNMASKFalse() {

        try {

            SSNMask mask = _adminService.retrieveSSNMask();
            mask.setApplySSNMask(!mask.getApplySSNMask());
            SSNMask updated = _adminService.updateSSNMask(mask);
            assertTrue(mask.getRecordVersion() != updated.getRecordVersion());
        } catch (ROIException e) {
            fail("Updating ssn madk should not throw exception" + e.getErrorCode());
        }
    }

    /**
     * This test case to verify update ssn mask details with
     *  null user return the exception
     */
    public void testUpdateSSNMaskWithNullUser() {

        try {

            SSNMask mask = _adminService.retrieveSSNMask();
            mask.setApplySSNMask(!mask.getApplySSNMask());
            initSession(null);
            SSNMask updated = _adminService.updateSSNMask(mask);
            fail("Updating ssn mask with null user is not accepted, but it updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.ADMINLOV_OPERATION_FAILED);
        }
    }
    /**
     * This test case to update note
     */
    public void testUpdateNote() {

        try {

            initSession();
            long id = _adminService.createNote(createNote(_name + System.nanoTime()));
            Note note = _adminService.retrieveNote(id);
            note.setName("update" + System.nanoTime());
            note.setDescription("updateDescription");
            Note updatedNote = _adminService.updateNote(note);
            assertTrue(note.getRecordVersion() != updatedNote.getRecordVersion());
        } catch (ROIException e) {
           fail("Updating note should not throw exception" + e.getErrorCode());
        }
    }

    /**
     * This test case is to create note with null user and verify if it returns the
     * appropriate exception
     */
    public void testCreateNoteWithNullUser() {

        try {

            initSession(null);
            long id = _adminService.createNote(createNote(_name + System.nanoTime()));
            fail("Creating note with null user is not permitted, but it created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.ADMINLOV_OPERATION_FAILED);
        }
    }

    /**
     * This test case to delete the note with null user and verify if it returns the
     * appropriate exception
     */
    public void testDeleteNoteWithNullUser() {

        try {

            initSession();
            long id = _adminService.createNote(createNote("nulluser" + System.nanoTime()));
            initSession(null);
            _adminService.deleteNote(id);
            fail("Deleting note with null user is not permitted, but it deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.ADMINLOV_OPERATION_FAILED);
        }
    }

    /**
     * This test case to update note with null user and verify if it returns the appropriate
     * exception
     */
    public void testUpdateNoteWithNullUser() {

        try {

            initSession();
            long id = _adminService.createNote(createNote("updatenulluser" + System.nanoTime()));
            Note note = _adminService.retrieveNote(id);
            initSession(null);
            Note updated = _adminService.updateNote(note);
            fail("Updating note with null user is not permitted, but it updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.ADMINLOV_OPERATION_FAILED);
        }
    }

    private Note createNote(String key) {

        Note note = new Note();
        note.setName(key);
        note.setType(AdminLoV.Type.notes);
        note.setDescription("create new note");

        return note;
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        throw new UnsupportedOperationException("TestNoteServiceImpl.getServiceURL()");
    }

    public void testConcurrencyForUpdateNotes() {

        try {

            initSession();
            long id  = _adminService.createNote(createNote(_name + System.nanoTime()));
            Note note = _adminService.retrieveNote(id);

            Note noteObj1 = (Note) deepCopy(note);
            noteObj1.setDescription("testasdf");

            _adminService.updateNote(noteObj1);

            Note noteObj2 = (Note) deepCopy(note);
            noteObj2.setDescription("testasdf");
           _adminService.updateNote(noteObj2);

            fail("Service method updateNote should throw an ROIException");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION);
        } catch (Exception e) {
            fail("Service method updateNote should not throw an Exception"  + e.getMessage());
        }
    }

    public void testConcurrencyForUpdateSSNMask() {

        try {

            initSession();
            SSNMask ssnMask = _adminService.retrieveSSNMask();

            SSNMask noteObj1 = (SSNMask) deepCopy(ssnMask);
            noteObj1.setApplySSNMask(!ssnMask.getApplySSNMask());

            _adminService.updateSSNMask(noteObj1);

            SSNMask noteObj2 = (SSNMask) deepCopy(ssnMask);
            noteObj2.setApplySSNMask(!ssnMask.getApplySSNMask());
            _adminService.updateSSNMask(noteObj2);

            fail("UpdateNote should throw an ROIException");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION);
        } catch (Exception e) {
            fail("UpdateNote should not throw an Exception"  + e.getMessage());
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
     * This test case to create the note with null value
     */
    public void testCreateNoteWithNullValue() {

        try {

            _adminService.createNote(null);
            fail("Creating note witn null value is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.NOTE_OPERATION_FAILED);
        }
    }
}
