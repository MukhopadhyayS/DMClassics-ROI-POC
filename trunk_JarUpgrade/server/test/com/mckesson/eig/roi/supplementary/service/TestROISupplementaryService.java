package com.mckesson.eig.roi.supplementary.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.model.SearchCondition;
import com.mckesson.eig.roi.base.model.SearchCondition.OPERATION;
import com.mckesson.eig.roi.base.model.SearchCriteria;
import com.mckesson.eig.roi.request.model.FreeFormFacility;
import com.mckesson.eig.roi.supplementary.model.ROIAttachmentCommon;
import com.mckesson.eig.roi.supplementary.model.ROISupplementalAttachment;
import com.mckesson.eig.roi.supplementary.model.ROISupplementalAttachments;
import com.mckesson.eig.roi.supplementary.model.ROISupplementalDocument;
import com.mckesson.eig.roi.supplementary.model.ROISupplementalDocuments;
import com.mckesson.eig.roi.supplementary.model.ROISupplementalPatient;
import com.mckesson.eig.roi.supplementary.model.ROISupplementalPatients;
import com.mckesson.eig.roi.supplementary.model.ROISupplementarityAttachment;
import com.mckesson.eig.roi.supplementary.model.ROISupplementarityAttachments;
import com.mckesson.eig.roi.supplementary.model.ROISupplementarityDocument;
import com.mckesson.eig.roi.supplementary.model.ROISupplementarityDocuments;
import com.mckesson.eig.roi.test.BaseROITestCase;
import com.mckesson.eig.roi.utils.AccessFileLoader;
import com.mckesson.eig.roi.utils.DirectoryUtil;
import com.mckesson.eig.utility.io.IOUtilities;
import com.mckesson.eig.utility.util.CollectionUtilities;

public class TestROISupplementaryService extends BaseROITestCase {

    protected static final String SUPPLEMENTARY_SERVICE = "com.mckesson.eig.roi.supplementary.service.ROISupplementaryServiceImpl";

    private static ROISupplementaryService _supplementalService;

    @Override
    public void initializeTestData() throws Exception {
        _supplementalService = (ROISupplementaryService) getService(SUPPLEMENTARY_SERVICE);
    }

    public void testUpdateSupplementalPatientWithInvalidDetails() {

        try {
            _supplementalService.updateSupplementalPatient(null);
            fail("Update Supplemental Patient with invalid details should throw Exception");
        } catch(ROIException e) {
            assertError(e, ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    public void testUpdateSupplementalDocumentWithInvalidDetails() {

        try {
            _supplementalService.updateSupplementalDocument(null);
            fail("Update Supplemental Document with invalid details should throw Exception");
        } catch(ROIException e) {
            assertError(e, ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    public void testUpdateSupplementarityAttachmentWithInvalidDetails() {

        try {
            _supplementalService.updateSupplementarityAttachment(null);
            fail("Update Supplementarity Attachment with invalid details should throw Exception");
        } catch(ROIException e) {
            assertError(e, ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    public void testSearchSupplementalPatientWithInvalidDetails() {

        try {
            _supplementalService.searchSupplementalPatient(null);
            fail("Search Supplementarity patient with invalid details should throw Exception");
        } catch(ROIException e) {
            assertError(e, ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    public void testUpdateSupplementarityDocumentWithInvalidDetails() {

        try {
            _supplementalService.updateSupplementarityDocument(null);
            fail("Update Supplementarity Document with invalid details should throw Exception");
        } catch(ROIException e) {
            assertError(e, ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    public void testdeleteSupplementarityDocument() {

        try {

            String mrn = "MS001";
            String facility = "Test";

            long patientId = _supplementalService.createSupplementalPatient(createNewPatient());
            ROISupplementarityDocument ROIsuppDoc = createNewSupplementarityDocument(mrn, facility);
            long docId =  _supplementalService.createSupplementarityDocument(ROIsuppDoc);
            _supplementalService.deleteSupplementarityDocument(docId, 1001, patientId);
        } catch(ROIException e) {
            fail("Delete Supplementarity Document with invalid details should throw Exception");
        }
    }

    public void testUpdateSupplementalAttachmentWithInvalidDetails() {

        try {
            _supplementalService.updateSupplementalAttachment(null);
            fail("Update Supplemental Attachment with invalid details should throw Exception");
        } catch(ROIException e) {
            assertError(e, ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    public void testCreateSupplementarityAttachmentWithInvalidDetails() {

        try {

            _supplementalService.createSupplementarityAttachment(null);
            fail("Create Supplementarity Attachment with invalid details should throw Exception");
        } catch(ROIException e) {
            assertError(e, ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    public void testCreateSupplementarityDocumentWithInvalidDetails() {

        try {

            _supplementalService.createSupplementarityDocument(null);
            fail("Create Supplementarity Document with invalid details should throw Exception");
        } catch(ROIException e) {
            assertError(e, ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    public void testCreateSupplementalPatientWithInvalidDetails() {

        try {

            _supplementalService.createSupplementalPatient(null);
            fail("Create supplemental patient with invalid details should throw Exception");
        } catch(ROIException e) {
            assertError(e, ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    public void testCreateSupplementalAttachmentWithInvalidDetails() {

        try {

            _supplementalService.createSupplementalAttachment(null);
            fail("Create supplemental attachment with invalid details should throw Exception");
        } catch(ROIException e) {
            assertError(e, ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    public void testCreateSupplementalAttachment() {

        try {

            long patientId = _supplementalService.createSupplementalPatient(createNewPatient());
            ROISupplementalAttachment suppAttachment = createNewSupplementalAttachment(patientId);
            suppAttachment.setExternalSource("Test");

            _supplementalService.createSupplementalAttachment(suppAttachment);

        } catch(ROIException e) {
            fail("Create supplemental attachment with invalid details should not throw Exception");
        }
    }

    public void testCreateSupplementarityAttachment() {

        try {

            String mrn = "MS001";
            String facility = "Test";

            ROISupplementarityAttachment suppAttachment = createNewSupplementarityAttachment(mrn, facility);
            suppAttachment.setExternalSource("Test");
            _supplementalService.createSupplementarityAttachment(suppAttachment);

        } catch(ROIException e) {
            fail("Create supplemental attachment with invalid details should not throw Exception");
        }
    }

    public void testCreateSupplementalDocumentWithInvalidDetails() {

        try {

            _supplementalService.createSupplementalDocument(null);
            fail("Create supplemental document with invalid details should throw Exception");
        } catch(ROIException e) {
            assertError(e, ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED);
        }
    }

    public void testCreateSupplementalDocumentWithFreeFormFacility() {

        try {

            long patientId = _supplementalService.createSupplementalPatient(createNewPatient());
            ROISupplementalDocument ROIsuppDoc = createNewSupplementalDocument(patientId);
            FreeFormFacility freeForm = new FreeFormFacility();
            freeForm.setId(1001);
            freeForm.setFreeFormFacilityName("TestFreeFormFacility");
            ROIsuppDoc.setFreeformFacility(freeForm);
            _supplementalService.createSupplementalDocument(ROIsuppDoc);

        } catch(ROIException e) {
            fail("Create supplemental document with Free form facility should not throw Exception");
        }
    }

    public void testDeleteSupplementalAttachment() {

        try {

            long patientId = _supplementalService.createSupplementalPatient(createNewPatient());

            ROISupplementalAttachments attachments =
                _supplementalService.createSupplementalAttachment(createNewSupplementalAttachment(patientId));

            ROISupplementalAttachment attachment = attachments.getAttachments().get(0);
           long id = _supplementalService.deleteSupplementalAttachment(attachment.getId(), 1001);

        } catch(Exception e) {
            fail("Delete roi supplemental attachment should not throw exception");
        }

    }

    public void testRetrieveAttachmentPath() {

        try {

            long patientId = _supplementalService.createSupplementalPatient(createNewPatient());

            ROISupplementalAttachments attachments =
                    _supplementalService.createSupplementalAttachment(createNewSupplementalAttachment(patientId));

            ROISupplementalAttachment attachment = attachments.getAttachments().get(0);

            ROISupplementalAttachment SuppAttach = createNewSupplementalAttachment(patientId);

          String attachmentPath =  _supplementalService.retrieveAttachmentPath(attachment.getUuid());

          assertNotNull(attachmentPath);

        } catch(Exception e) {
            fail("Retrieve roi supplemental attachment should not throw exception");
        }

    }

    public void testRetrieveAttachmentPathForInvalidUuid() {

        try {
             _supplementalService.retrieveAttachmentPath(null);
        } catch(ROIException e) {
            assertEquals(ROIClientErrorCodes.ROI_SUPPLEMENTAL_OPERATION_FAILED, e.getErrorCode());
        }

    }

    public void testSearchSupplementalPatient() {

        SearchCondition condition = new SearchCondition();
        condition.setKey("lastName");
        condition.setOperation("Equal");
        condition.setValue("lastName");
        SearchCriteria criteria = new SearchCriteria();
        criteria.addCondition(condition);
        ROISupplementalPatients patients = _supplementalService.searchSupplementalPatient(criteria);
        assertTrue(patients.getPatients().size() > 0);
    }

    public void testSearchSupplementalPatientWithEncounter() {

        SearchCondition condition = new SearchCondition();
        condition.setKey("encounter");
        condition.setOperation("Equal");
        condition.setValue("encounter");
        SearchCriteria criteria = new SearchCriteria();
        criteria.addCondition(condition);
        ROISupplementalPatients patients = _supplementalService.searchSupplementalPatient(criteria);
        assertTrue(patients.getPatients().size() > 0);
    }

    public void testSearchSupplementalPatientWithEncounterAndFreeFormFacility() {

        try {

            SearchCondition condition = new SearchCondition();
            condition.setKey("freeformfacility");
            condition.setOperation("Equal");
            condition.setValue("Test");
            SearchCondition Enccondition = new SearchCondition();
            Enccondition.setKey("encounter");
            Enccondition.setOperation("Equal");
            Enccondition.setValue("encounter");
            SearchCriteria criteria = new SearchCriteria();
            criteria.addCondition(condition);
            criteria.addCondition(Enccondition);
            _supplementalService.searchSupplementalPatient(criteria);
        } catch (ROIException e) {
            fail("Search ROI supplemental Patient with encounter & free form " +
            		                                        "facility should not throw exception");
        }
    }

    public void testSearchSupplementalPatientWithFreeFormFacility() {

        SearchCondition condition = new SearchCondition();
        condition.setKey("freeformfacility");
        condition.setOperation("Equal");
        condition.setValue("Test");
        SearchCriteria criteria = new SearchCriteria();
        criteria.addCondition(condition);
        criteria.addCondition(condition);
        ROISupplementalPatients patients = _supplementalService.searchSupplementalPatient(criteria);
        assertTrue(patients.getPatients().size() > 0);
    }

    /**
     * This test case to create the roi supplemental
     */
    public void testROISupplementalPatient() {
        try {
            long id = _supplementalService.createSupplementalPatient(createNewPatient());
            ROISupplementalPatient p = _supplementalService.getSupplementalPatient(id, false);
            assertNotNull(p);
            assertEquals("lastName", p.getLastName());
            assertEquals("firstName", p.getFirstName());
            assertEquals("Mrn", p.getMrn());
            assertEquals("AD", p.getFacility());
            boolean isDuplicate = _supplementalService.checkDuplicates("lastName", "firstName", 1);
            assertTrue(isDuplicate);

            SearchCriteria criteria = new SearchCriteria();
            SearchCondition condition = new SearchCondition();
            condition.setKey("mrn");
            condition.setValue(p.getMrn());
            condition.setOperation(OPERATION.Like.name());
            criteria.addCondition(condition);
            ROISupplementalPatients patients = _supplementalService.searchSupplementalPatient(criteria);
            assertTrue(patients.getPatients().size() > 0);
            p.setLastName("LastName");
            assertTrue(isDuplicate);
            _supplementalService.updateSupplementalPatient(p);
            p = _supplementalService.getSupplementalPatient(id, false);
            assertNotNull(p);
            assertEquals("LastName", p.getLastName());
            _supplementalService.deleteSupplementalPatient(id);
            p = _supplementalService.getSupplementalPatient(id, false);
            assertNull(p);
        } catch (ROIException e) {
            fail("Creating roi supplemental should not throw exception"
                    + e.getErrorCode());
        }
    }

    private ROISupplementalPatient createNewPatient() {
        ROISupplementalPatient patient = new ROISupplementalPatient();
        patient.setLastName("lastName");
        patient.setFirstName("firstName");
        patient.setMrn("Mrn");
        patient.setFacility("AD");
        return patient;
    }

    /**
     * This test case to create the roi supplemental
     */
    public void testROISupplementalDocument() {
        try {
            long id = _supplementalService.createSupplementalPatient(createNewPatient());
            long docId = _supplementalService.createSupplementalDocument(createNewSupplementalDocument(id));
            ROISupplementalDocuments docs = _supplementalService.getSupplementalDocuments(id);
            assertNotNull(docs);
            assertEquals(1, docs.getDocuments().size());
            ROISupplementalDocument doc = docs.getDocuments().get(0);
            assertEquals(docId, doc.getId());
            assertEquals("encounter", doc.getEncounter());
            assertEquals("AD", doc.getDocFacility());
            assertEquals("department", doc.getDepartment());
            doc.setDepartment("LastDepartment");
            _supplementalService.updateSupplementalDocument(doc);
            docs = _supplementalService.getSupplementalDocuments(id);
            assertNotNull(docs);
            assertEquals(1, docs.getDocuments().size());
            doc = docs.getDocuments().get(0);
            assertEquals("LastDepartment", doc.getDepartment());
            _supplementalService.deleteSupplementalDocument(docId,1000147);
            docs = _supplementalService.getSupplementalDocuments(id);
            assertNotNull(docs);
            assertEquals(0, docs.getDocuments().size());
            _supplementalService.deleteSupplementalPatient(id);
        } catch (ROIException e) {
            fail("Creating roi supplemental should not throw exception"
                    + e.getErrorCode());
        }
    }

    private ROISupplementalDocument createNewSupplementalDocument(long patientId) {
        ROISupplementalDocument doc = new ROISupplementalDocument();
        doc.setPatientId(patientId);
        doc.setEncounter("encounter");
        doc.setDocName("AAAAAAAA");
        doc.setDocFacility("AD");
        doc.setDepartment("department");
        return doc;
    }

    /**
     * This test case to create the roi supplemental
     */
    public void testROISupplementarityDocument() {
        try {
            String mrn = "Mrn";
            String facility = "Facility";
            deleteROISupplementarityDocument(mrn, facility);
            long docId = _supplementalService.createSupplementarityDocument(createNewSupplementarityDocument(mrn, facility));
            ROISupplementarityDocuments docs = _supplementalService.getSupplementarityDocuments(mrn, facility);
            assertNotNull(docs);
            assertEquals(1, docs.getDocuments().size());
            ROISupplementarityDocument doc = docs.getDocuments().get(0);
            assertEquals(docId, doc.getId());
            assertEquals("Encounter", doc.getEncounter());
            assertEquals("AD", doc.getDocFacility());
            assertEquals("department", doc.getDepartment());
            doc.setDepartment("LastDepartment");
            _supplementalService.updateSupplementarityDocument(doc);
            docs = _supplementalService.getSupplementarityDocuments(mrn, facility);
            assertNotNull(docs);
            assertEquals(1, docs.getDocuments().size());
            doc = docs.getDocuments().get(0);
            assertEquals("LastDepartment", doc.getDepartment());
            _supplementalService.deleteSupplementarityDocument(docId,1000147,264);
            docs = _supplementalService.getSupplementarityDocuments(mrn, facility);
            assertNotNull(docs);
            assertEquals(0, docs.getDocuments().size());
        } catch (ROIException e) {
            fail("Creating roi supplemental should not throw exception"
                    + e.getErrorCode());
        }
    }

    private void deleteROISupplementarityDocument(String mrn, String facility) {
        ROISupplementarityDocuments docs = _supplementalService.getSupplementarityDocuments(mrn, facility);
        if (!CollectionUtilities.isEmpty(docs.getDocuments())) {
            for (ROISupplementarityDocument doc : docs.getDocuments()) {
                _supplementalService.deleteSupplementarityDocument(doc.getId(),1000147,264);
            }
        }
    }

    private ROISupplementarityDocument createNewSupplementarityDocument(String mrn, String facility) {
        ROISupplementarityDocument doc = new ROISupplementarityDocument();
        doc.setMrn(mrn);
        doc.setFacility(facility);
        doc.setDocFacility("AD");
        doc.setDocName("AAAAAAAA");
        doc.setEncounter("Encounter");
        doc.setDepartment("department");
        return doc;
    }
    /**
     * This test case to create the roi supplemental
     */
    public void testROISupplementalAttachment() {
        try {
            long id = _supplementalService.createSupplementalPatient(createNewPatient());
            ROISupplementalAttachments attachments =
                    _supplementalService.createSupplementalAttachment(createNewSupplementalAttachment(id));

            long attId = 0;
            if (CollectionUtilities.hasContent(attachments.getAttachments())) {

                ROISupplementalAttachment attachment = attachments.getAttachments().get(0);
                attId = attachment.getId();
            }
            ROISupplementalAttachments atts = _supplementalService.getSupplementalAttachments(id);
            assertNotNull(atts);
            assertEquals(1, atts.getAttachments().size());
            ROISupplementalAttachment att = atts.getAttachments().get(0);
            assertEquals(attId, att.getId());
            assertEquals("encounter", att.getEncounter());
            assertEquals("AD", att.getDocFacility());
            assertEquals("Type", att.getType());
            att.setType("BBBBBBBBBB");
            _supplementalService.updateSupplementalAttachment(att);
            atts = _supplementalService.getSupplementalAttachments(id);
            assertNotNull(atts);
            assertEquals(1, atts.getAttachments().size());
            att = atts.getAttachments().get(0);
            assertEquals("BBBBBBBBBB", att.getType());
            _supplementalService.deleteSupplementalAttachment(attId,1000147);
            atts = _supplementalService.getSupplementalAttachments(id);
            assertNotNull(atts);
            assertEquals(0, atts.getAttachments().size());
            _supplementalService.deleteSupplementalPatient(id);
        } catch (ROIException e) {
            fail("Creating roi supplemental should not throw exception"
                    + e.getErrorCode());
        }
    }

    private ROISupplementalAttachment createNewSupplementalAttachment(long patientId) {

        try {

            String tempPath = DirectoryUtil.getCacheDirectory();
            String uuid = UUID.randomUUID().toString();
            File f = AccessFileLoader.getFile(tempPath, uuid);
            String t = "test";

            IOUtilities.writeObjectAndClose(AccessFileLoader.getFileOutputStream(f, false), t);

            ROISupplementalAttachment att = new ROISupplementalAttachment();
            att.setUuid(uuid);
            att.setPatientId(patientId);
            att.setEncounter("encounter");
            att.setDocFacility("AD");
            att.setType("Type");
            att.setPageCount("5");

            return att;

        } catch (Exception e) {
            fail("Create Attachment");
            return new ROISupplementalAttachment();
        }

    }

    /**
     * This test case to create the roi supplemental
     */
    public void testROISupplementarityAttachment() {

        try {

            String mrn = "MS001";
            String facility = "Test";

            long patientId = _supplementalService.createSupplementalPatient(createNewPatient());

            ROISupplementarityAttachments attachments =
                    _supplementalService.createSupplementarityAttachment(createNewSupplementarityAttachment(mrn, facility));

            long attId = 0;
            if (CollectionUtilities.hasContent(attachments.getAttachments())) {

                ROISupplementarityAttachment attachment = attachments.getAttachments().get(0);
                attId = attachment.getId();
            }

            ROISupplementarityAttachments atts = _supplementalService.getSupplementarityAttachments(mrn, facility);
            assertNotNull(atts);
            ROISupplementarityAttachment att = atts.getAttachments().get(0);
            assertEquals("eS1001", att.getEncounter());
            assertEquals("AD", att.getDocFacility());
            assertEquals("Type", att.getType());
            att.setType("BBBBBBBBBB");
            _supplementalService.updateSupplementarityAttachment(att);
            atts = _supplementalService.getSupplementarityAttachments(mrn, facility);
            assertNotNull(atts);
            att = atts.getAttachments().get(0);
            assertEquals("BBBBBBBBBB", att.getType());
            _supplementalService.deleteSupplementarityAttachment(attId,1001,patientId);
            deleteROISupplementarityAttachment(mrn, facility);
            atts = _supplementalService.getSupplementarityAttachments(mrn, facility);
            assertNotNull(atts);

        } catch (ROIException e) {
            fail("Creating roi supplemental should not throw exception"
                    + e.getErrorCode());
        }
    }

    private void deleteROISupplementarityAttachment(String mrn, String facility) {

        ROISupplementarityAttachments atts =
                                    _supplementalService.getSupplementarityAttachments(mrn, facility);

        long patientId = _supplementalService.createSupplementalPatient(createNewPatient());

        if (!CollectionUtilities.isEmpty(atts.getAttachments())) {
            for (ROISupplementarityAttachment att : atts.getAttachments()) {
                _supplementalService.deleteSupplementarityAttachment(att.getId(),1001,patientId);
            }
        }
    }

    private ROISupplementarityAttachment createNewSupplementarityAttachment(String mrn, String facility) {

        try {

            String tempPath = DirectoryUtil.getCacheDirectory();
            String uuid = UUID.randomUUID().toString();
            File f = AccessFileLoader.getFile(tempPath, uuid);
            String t = "test";

            IOUtilities.writeObjectAndClose(AccessFileLoader.getFileOutputStream(f, false), t);
            ROISupplementarityAttachment att = new ROISupplementarityAttachment();
            att.setMrn(mrn);
            att.setFacility(facility);
            att.setEncounter("eS1001");
            att.setType("Type");
            att.setUuid(uuid);
            att.setPageCount("2");
            att.setDocFacility("AD");
            return att;

    } catch (Exception e) {
        fail("Create Attachment");
        return new ROISupplementarityAttachment();
    }
    }


    @Override
    protected String getServiceURL(String serviceMethod) {
        throw new UnsupportedOperationException(
                "TestROISupplementalServiceImpl.getServiceURL()");
    }

    @Test
    public void testCreateNewPatient() {
        ROISupplementalPatient patient = new ROISupplementalPatient();
        patient.setLastName("Mathew");
        patient.setFirstName("John");
        patient.setFacility("AD");
        patient.setAddress1("987, 2nd street");
        patient.setCity("NewYork");
        patient.setState("Newyork");
        patient.setCountryCode("USA");
        patient.setGender("M");
        patient.setModifiedBy(1);
        patient.setCreatedBy(1);

       long id =  _supplementalService.createSupplementalPatient(patient);
        assertTrue(id > 0L);
    }

    public void testROISupplementalAttachmentModel() {

        ROISupplementalAttachment supplAttach = new ROISupplementalAttachment();

        supplAttach.setFreeFormFacilityId(1);
        supplAttach.setPatientId(2);

        FreeFormFacility freeFac = new FreeFormFacility();
        freeFac.setId(7);
        freeFac.setFreeFormFacilityName("Test");

        supplAttach.setFreeformFacility(freeFac);

        assertEquals(2, supplAttach.getPatientId());
        assertEquals(1, supplAttach.getFreeFormFacilityId());
        assertNotNull(supplAttach.getFreeformFacility());

    }

    public void testROIAttachmentCommonModel() {

        ROIAttachmentCommon attachCommon = new ROIAttachmentCommon();

        attachCommon.setFreeformfacility("Test");
        attachCommon.setExternalSource("Test");
        attachCommon.setFacility("Test");

        attachCommon.copy(attachCommon);

        assertEquals("Test", attachCommon.getFacility());
        assertEquals("Test", attachCommon.getExternalSource());
        assertEquals("Test", attachCommon.getFreeformfacility());
    }

    public void testROISupplementalPatientsmodel() {

        ROISupplementalPatients suppPatients = new ROISupplementalPatients();

        suppPatients.setMaxCountExceeded(true);

        List<ROISupplementalPatient> patients = new ArrayList<ROISupplementalPatient>();
        ROISupplementalPatient patient = createNewPatient();
        patients.add(createNewPatient());
        suppPatients.add(patient);
        suppPatients.setPatients(patients);

        assertEquals(true, suppPatients.getMaxCountExceeded());
        assertNotNull(suppPatients.getPatients());
    }

}
