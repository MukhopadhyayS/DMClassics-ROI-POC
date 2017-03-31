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



import java.io.InputStream;
import java.sql.Timestamp;

import com.mckesson.eig.common.filetransfer.services.BaseFileTransferData;
import com.mckesson.eig.roi.admin.model.LetterTemplate;
import com.mckesson.eig.roi.admin.model.LetterTemplateList;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.test.BaseROITestCase;
import com.meterware.httpunit.HttpException;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;


/**
 * @author OFS
 * @date   Jul 02, 2009
 * @since  HPF 13.1 [ROI]; May 16, 2008
 */
public class TestLetterTemplateServiceImpl
extends BaseROITestCase {

    protected static final String  ADMIN_SERVICE =
        "com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl";

    private static ROIAdminService  _adminService;
    private static String           _lettertemplateName;
    private static String           _invalidlettertemplateName;
    private static String           _letterTemplateDesc;

    private static final int PASS  = 200;
    private static final int FAIL  = 400;

    private ServletUnitClient _servletUntClient;
    private static final String SERVLET_NAME = "ROIFileUploadServlet";
    private static final String SAMPLE_TEMPLATE = "SampleLetterTemplate.rtf";
    private static final String INVOICE_TEMPLATE = "invoice";
    private static final String INVALID_TEMPLATE = "invalid";

    @Override
    public void initializeTestData()
    throws Exception {

        long seed = System.nanoTime();
        _lettertemplateName        = "Letter" + seed;
        _invalidlettertemplateName = "!@#.-";
        _letterTemplateDesc        = "LetterDesc." + seed;
        _adminService = (ROIAdminService) getService(ADMIN_SERVICE);
    }

   /** Creates a ServletUnitClient to invoke the registered servlet.
    * @throws Exception
    */
   @Override
   public void setUp()
   throws Exception {

       super.setUp();
       ServletRunner servletRunner = new ServletRunner();
       servletRunner.registerServlet(SERVLET_NAME, ROIFileUploadServlet.class.getName());

       _servletUntClient = servletRunner.newClient();
   }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * This test case is for create letter template and verify if it returns the newly
     *  created id
     * @throws Exception
     */
    public void testCreateLetterTemplate() throws Exception {

        try {

            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate lt = setLetterTemplateDetails("invoice", false);
            lt.setDocId(Integer.parseInt(value[0]));
            lt.setHasNotes(Boolean.parseBoolean(value[1]));
            initSession();
            long createdId = _adminService.createLetterTemplate(lt);
            LetterTemplate createdLT = _adminService.retrieveLetterTemplate(createdId);

            assertNotSame("Created lettertemplate id should be greater than zero", 0, createdId);
            assertEquals(true, createdLT.getHasNotes());
        } catch (ROIException e) {
            e.printStackTrace();
            fail("Create letter template should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case is for create letter template and verify if it returns the newly
     *  created id
     * @throws Exception
     */
    public void testCreateLetterTemplateWithInvalidData() throws Exception {

        try {

            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate lt = setLetterTemplateDetails("invoice", false);
            lt.setName(appendString(_invalidlettertemplateName));
            lt.setDesc(appendString(_letterTemplateDesc));
            lt.setDocId(Integer.parseInt(value[0]));
            lt.setHasNotes(Boolean.parseBoolean(value[1]));
            initSession();
             _adminService.createLetterTemplate(lt);
             fail("Create letter template with invalid data not allowed.");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.LETTER_TEMPLATE_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.LETTER_TEMPLATE_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.LETTER_TEMPLATE_DESC_LENGTH_EXCEEDS_LIMIT);

        }
    }

    /**
     * This test case is for create letter template and verify if it returns the new id
     * @throws Exception
     */
    public void testCreateLetterTemplateWithInvalidTemplate() throws Exception {

        try {

            String[] value = uploadDocument(0, "InvalidSampleLetterTemplate.rtf");
            LetterTemplate lt = setLetterTemplateDetails("invoice", false);
            lt.setDocId(Integer.parseInt(value[0]));
            lt.setHasNotes(Boolean.parseBoolean(value[1]));
            initSession();
            long createdId = _adminService.createLetterTemplate(lt);
            LetterTemplate createdLT = _adminService.retrieveLetterTemplate(createdId);

            assertNotSame("Created lettertemplate id should be greater than zero", 0, createdId);
            assertEquals(false, createdLT.getHasNotes());
        } catch (ROIException e) {
            e.printStackTrace();
            fail("Create letter template should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case is for create letter template and verify if it returns the newly
     *  created id
     * @throws Exception
     */
    public void testCreateLetterTemplateWithoutNotes() throws Exception {

        try {

            String[] value = uploadDocument(0, "SampleTemplateWithoutNotes.rtf");
            LetterTemplate lt = setLetterTemplateDetails("invoice", false);
            lt.setDocId(Integer.parseInt(value[0]));
            lt.setHasNotes(Boolean.parseBoolean(value[1]));
            initSession();
            long createdId = _adminService.createLetterTemplate(lt);
            LetterTemplate createdLT = _adminService.retrieveLetterTemplate(createdId);

            assertNotSame("Created lettertemplate id should be greater than zero", 0, createdId);
            assertEquals(false, createdLT.getHasNotes());
        } catch (ROIException e) {
            e.printStackTrace();
            fail("Create letter template should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case is for create letter template with Default is true
     * and verify if it returns newly created id
     */
    public void testCreateLetterTemplateWithDefault() {

        try {

            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate lt = setLetterTemplateDetails("Coverletter", true);
            lt.setDocId(Integer.parseInt(value[0]));
            lt.setHasNotes(Boolean.parseBoolean(value[1]));
            initSession();
            long createdId = _adminService.createLetterTemplate(lt);
            LetterTemplate createdLT = _adminService.retrieveLetterTemplate(createdId);
            assertEquals(true, createdLT.getHasNotes());
            assertNotSame("created lettertemplate id should be greater than zero", 0, createdId);
        } catch (ROIException e) {
            e.printStackTrace();
            fail("Create letter template should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This method tests ROIFileUpload servlet with a valid file to be uploaded
     *
     */
    public void testNewDocUpload() {

        try {

            String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
            strURLaddress = constructURL("LetterTemplate",
                                         0,
                                         SAMPLE_TEMPLATE,
                                         true,
                                         true);

            WebRequest request = new PostMethodWebRequest(strURLaddress,
                                                          getInputStream(SAMPLE_TEMPLATE),
                                                          "application/rtf");

            _servletUntClient.getProxyHost();
            WebResponse response = _servletUntClient.sendRequest(request);
            assertEquals(PASS, response.getResponseCode());
        } catch (Exception e) {
            e.printStackTrace();
            fail("ROIFileUpload failed");
        }
    }

    /**
     * This test case for createletterTemplate With other type Default is true and verify if it
     * returns appropriate exception
     */
    public void testCreateLetterTemplateWithLetterTypeOther() {

        try {

            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate lt = setLetterTemplateDetails("Other", true);
            lt.setDocId(Integer.parseInt(value[0]));
            lt.setHasNotes(Boolean.parseBoolean(value[1]));
            long createdId = _adminService.createLetterTemplate(lt);
            fail("Creating default letter template with type other is not permitted, but created");
        } catch (ROIException e) {
            e.printStackTrace();
           assertError(e, ROIClientErrorCodes.LETTER_TEMPLATE_LETTER_TYPE_OTHER_CHOOSE_DEFAULT);
        }
    }

    /**
     * This test case for CreateLetterTemplateWithDuplicateName and verify if it
     * returns appropriate exception
     */
    public void testCreateLetterTemplateWithDuplicateName() {

        try {

            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate lt = setLetterTemplateDetails("Prebill", true);
            lt.setDocId(Integer.parseInt(value[0]));
            lt.setHasNotes(Boolean.parseBoolean(value[1]));
            initSession();
            _adminService.createLetterTemplate(lt);
            LetterTemplate retrieved = _adminService.retrieveLetterTemplate(lt.getTemplateId());

            LetterTemplate duplicate = new LetterTemplate();
            duplicate.setName(retrieved.getName());
            duplicate.setDesc(_letterTemplateDesc);
            duplicate.setIsDefault(true);
            duplicate.setLetterType("PreBill");
            duplicate.setDocId(Integer.parseInt(value[0]));
            lt.setHasNotes(Boolean.parseBoolean(value[1]));

            long createdId = _adminService.createLetterTemplate(duplicate);
            fail("Creating letter template with duplicate name is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.LETTER_TEMPLATE_NAME_IS_NOT_UNIQUE);
        }
    }

    /**
     * This test case for createletterTemplateWithMoreNameLength is true and verify if it
     * returns appropriate exception
     */
    public void testCreateLetterTemplateWithMoreNameLength() {

        try {

            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate lt = new LetterTemplate();
            lt.setName("CoverPreBillInvoicePageCancelCoverPreBillInvoicePageCancelPreBillInvoice");
            lt.setDesc(_letterTemplateDesc);
            lt.setDocId(Integer.parseInt(value[0]));
            long createdId = _adminService.createLetterTemplate(lt);
            fail("Creating letter template with more name length not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.LETTER_TEMPLATE_NAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This test case for createletterTemplateWithSessionNull and verify if it
     * returns appropriate exception
     */
    public void testCreateLetterTemplateWithSessionNull() {

        try {

            initSession(null);
            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate lt = new LetterTemplate();
            lt.setName("Cover" + System.nanoTime());
            lt.setDesc(_letterTemplateDesc);
            lt.setIsDefault(true);
            lt.setLetterType("Coverletter");
            lt.setDocId(Integer.parseInt(value[0]));
            long createdId = _adminService.createLetterTemplate(lt);
            fail("Creating letter template with session null is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.LETTER_TEMPLATE_PROCESS_FAILED);
        }
    }

    /**
     * This test case for RetrieveLetterTemplate
     */
    public void testRetrieveLetterTemplate() {

        try {

            initSession();
            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate lt = setLetterTemplateDetails("Prebill", true);
            lt.setDocId(Integer.parseInt(value[0]));
            lt.setHasNotes(Boolean.parseBoolean(value[1]));
            initSession();
            long id = _adminService.createLetterTemplate(lt);
            LetterTemplate lTemplate = _adminService.retrieveLetterTemplate(id);

            assertEquals(lt.getTemplateId(), lTemplate.getTemplateId());
            assertEquals(true, lt.getHasNotes());
        } catch (ROIException e) {
            fail("Retrieve lettertemplate should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case for retrieveLetterTemplate with invaliId and verify
     * if it returns the appropriate exception
     */
    public void testRetrieveLetterTemplateWithInvalidId() {

        try {

            LetterTemplate lt = setLetterTemplateDetails("PreBill", true);
            LetterTemplate lTemplate = _adminService.retrieveLetterTemplate(Integer.MAX_VALUE);
            fail("Retrieving letter template with invalid id is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

     /**
     * This test case for deleteLetterTemplate
     */
    public void testDeleteLetterTemplate() {

        try {

            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate lt = setLetterTemplateDetails("Prebill", true);
            lt.setDocId(Integer.parseInt(value[0]));
            lt.setHasNotes(Boolean.parseBoolean(value[1]));
            initSession();
            long id = _adminService.createLetterTemplate(lt);
            _adminService.deleteLetterTemplate(id);
            _adminService.retrieveLetterTemplate(id);
        } catch (ROIException e) {
            e.printStackTrace();
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This test case for deleteLetterTemplate with InvaliId and verify
     * if it returns the appropriate exception
     */
    public void testDeleteLetterTemplateWithInvalidId() {

        try {

            final long id = Integer.MAX_VALUE;
             _adminService.deleteLetterTemplate(id);
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This test case for deleteLetterTemplate
     */
    public void testDeleteLetterTemplateWithNullUser() {

        try {

            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate lt = setLetterTemplateDetails("Prebill", true);
            lt.setDocId(Integer.parseInt(value[0]));
            lt.setHasNotes(Boolean.parseBoolean(value[1]));
            initSession();
            long id = _adminService.createLetterTemplate(lt);
            initSession(null);
            _adminService.deleteLetterTemplate(id);
        } catch (ROIException e) {
            e.printStackTrace();
            assertError(e, ROIClientErrorCodes.LETTER_TEMPLATE_PROCESS_FAILED);
        }
    }

    /**
     * This test case for retrieveAllLetterTemplate
     */
    public void testRetrieveAllLetterTemplate() {

        try {

            initSession();
            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate newTemplate = setLetterTemplateDetails("Coverletter", false);

            newTemplate.setDocId(Integer.parseInt(value[0]));
            initSession();
            _adminService.createLetterTemplate(newTemplate);

            LetterTemplateList lt = new LetterTemplateList();
            lt = _adminService.retrieveAllLetterTemplates();
            assertNotSame("The size of retrieving letter templates should be greater than zero",
                          0,
                          lt.getLetterTemplates().size());

        } catch (ROIException e) {
            e.printStackTrace();
            fail("Retrieve all lettertemplate should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case for updateLetterTemplate
     */
    public void testUpdateLetterTemplate() {

        try {

            initSession();
            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate lt = setLetterTemplateDetails("Coverletter", true);
            lt.setDocId(Integer.parseInt(value[0]));
            lt.setHasNotes(Boolean.parseBoolean(value[1]));
            initSession();
            long id = _adminService.createLetterTemplate(lt);
            LetterTemplate lTemp = _adminService.retrieveLetterTemplate(id);

            lTemp.setName(_lettertemplateName + "Updated");
            lTemp.setLetterType("Coverletter");
            lTemp.setIsDefault(true);
            LetterTemplate lTemplate = _adminService.updateLetterTemplate(lTemp);
            assertEquals(lTemp.getName(), lTemplate.getName());
        } catch (ROIException e) {
            e.printStackTrace();
            fail("Update lettertemplate should not throw an exception." + e.getErrorCode());
        }
    }

    /**
     * This test case for updateLetterTemplate
     */
    public void testUpdateLetterTemplateWithInvalidData() {

        try {

            initSession();
            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate lt = setLetterTemplateDetails("Coverletter", true);
            lt.setDocId(Integer.parseInt(value[0]));
            lt.setHasNotes(Boolean.parseBoolean(value[1]));
            initSession();
            long id = _adminService.createLetterTemplate(lt);
            LetterTemplate lTemp = _adminService.retrieveLetterTemplate(id);

            lTemp.setName(appendString(_invalidlettertemplateName));
            lTemp.setDesc(appendString(_letterTemplateDesc));
            lTemp.setLetterType("Coverletter");
            lTemp.setIsDefault(true);
            LetterTemplate lTemplate = _adminService.updateLetterTemplate(lTemp);
            fail("Update lettertemplate with invalid data not allowed.");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.LETTER_TEMPLATE_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.LETTER_TEMPLATE_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.LETTER_TEMPLATE_DESC_LENGTH_EXCEEDS_LIMIT);

        }
    }


    /**
     * This test case for updateLetterTemplate
     */
    public void testUpdateLetterTemplateAsDefault() {

        try {

            initSession();
            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate lt = setLetterTemplateDetails("Coverletter", false);
            lt.setDocId(Integer.parseInt(value[0]));
            lt.setHasNotes(Boolean.parseBoolean(value[1]));
            initSession();
            long id = _adminService.createLetterTemplate(lt);
            LetterTemplate lTemp = _adminService.retrieveLetterTemplate(id);

            lTemp.setIsDefault(true);
            LetterTemplate lTemplate = _adminService.updateLetterTemplate(lTemp);
            assertEquals(true, lTemplate.getHasNotes());
            assertEquals(lTemp.getName(), lTemplate.getName());
        } catch (ROIException e) {
            e.printStackTrace();
            fail("Update letterTemplate should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case  for updating letter template with letter type other is true
     */
    public void testUpdateLetterTemplateWithLetterTypeOtherIsTrue() {

        try {

            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate ltemplate = setLetterTemplateDetails("Other", false);

            ltemplate.setDocId(Integer.parseInt(value[0]));
            ltemplate.setHasNotes(Boolean.parseBoolean(value[1]));
            initSession();
            long id = _adminService.createLetterTemplate(ltemplate);
            LetterTemplate lt = _adminService.retrieveLetterTemplate(id);
            lt.setIsDefault(true);
            LetterTemplate lTemplate = _adminService.updateLetterTemplate(lt);
            fail("Updating letter template with type other is not permitted, but updated");
        } catch (ROIException e) {
           assertError(e, ROIClientErrorCodes.LETTER_TEMPLATE_LETTER_TYPE_OTHER_CHOOSE_DEFAULT);
        }
    }

  /**
  * This test case for UpdateLetterTemplateWithMoreNameLength and verify if it returns
  * the appropriate exception
  */
    public void testUpdateLetterTemplateWithMoreNameLength() {

        try {

            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate ltemplate = setLetterTemplateDetails("Other", false);
            ltemplate.setDocId(Integer.parseInt(value[0]));
            ltemplate.setHasNotes(Boolean.parseBoolean(value[1]));
            initSession();
            long id = _adminService.createLetterTemplate(ltemplate);
            LetterTemplate lt = _adminService.retrieveLetterTemplate(id);

            lt.setName(_lettertemplateName + "UpdatePreBillInvoicdCoverCancelMORELENGTH");
            lt.setLetterType("Other");
            lt.setIsDefault(true);
            LetterTemplate lTemplate = _adminService.updateLetterTemplate(lt);
            fail("Updating letter template with invalid name length not permitted, but it updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.LETTER_TEMPLATE_NAME_LENGTH_EXCEEDS_LIMIT);
        }
    }

  /**
  * This test case is for updating letter template with name blank
  */
    public void testUpdateLetterTemplateWithNameBlank() {

        try {

            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate ltemplate = setLetterTemplateDetails("Other", false);
            ltemplate.setDocId(Integer.parseInt(value[0]));
            ltemplate.setHasNotes(Boolean.parseBoolean(value[1]));
            initSession();
            long id = _adminService.createLetterTemplate(ltemplate);
            LetterTemplate lt = _adminService.retrieveLetterTemplate(id);

            lt.setName("");
            lt.setLetterType("Other");
            lt.setIsDefault(true);
            LetterTemplate lTemplate = _adminService.updateLetterTemplate(lt);
            fail("Updating letter template with blank name is not permitted, but updated.");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.LETTER_TEMPLATE_NAME_IS_BLANK);
        }
    }

    /**
     * This test case for updateLetterTemplate with name null
     */
    public void testUpdateLetterTemplateWithNameNull() {

        try {

            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate ltemplate = setLetterTemplateDetails("Other", false);
            ltemplate.setDocId(Integer.parseInt(value[0]));
            ltemplate.setHasNotes(Boolean.parseBoolean(value[1]));
            initSession();
            long id = _adminService.createLetterTemplate(ltemplate);
            LetterTemplate lt = _adminService.retrieveLetterTemplate(id);
            lt.setName(null);
            lt.setLetterType("Other");
            lt.setIsDefault(true);
            LetterTemplate lTemplate = _adminService.updateLetterTemplate(lt);
            fail("Updating letter template with name null is not permitted, but updated");
        } catch (ROIException e) {
           assertError(e, ROIClientErrorCodes.LETTER_TEMPLATE_NAME_IS_BLANK);
        }
    }

    /**
     * This test case for updateLetterTemplate with duplicate name
     */
    public void testUpdateLetterTemplateWithDuplicateName() {

        try {

            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate ltemplate1 = setLetterTemplateDetails("Other", false);
            ltemplate1.setDocId(Integer.parseInt(value[0]));
            initSession();
            long id1 = _adminService.createLetterTemplate(ltemplate1);
            LetterTemplate ltemplate2 = setLetterTemplateDetails("Other", false);
            ltemplate2.setDocId(Integer.parseInt(value[0]));
            initSession();
            long id2 = _adminService.createLetterTemplate(ltemplate2);

            LetterTemplate lt = _adminService.retrieveLetterTemplate(ltemplate1.getTemplateId());
            LetterTemplate ltemp = _adminService.retrieveLetterTemplate(ltemplate2.getTemplateId());

            ltemp.setName(lt.getName());
            ltemp.setLetterType(lt.getLetterType());
            LetterTemplate lTemplate = _adminService.updateLetterTemplate(ltemp);
            fail("Updating lettertemplate with duplicate name is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.LETTER_TEMPLATE_NAME_IS_NOT_UNIQUE);
        }
    }

  /**
  * This test case for updateLetterTemplate with session null
  */
    public void testUpdateLetterTemplateWithSessionNull() {

        try {

            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate ltemplate = setLetterTemplateDetails("Other", false);
            ltemplate.setDocId(Integer.parseInt(value[0]));
            long id = _adminService.createLetterTemplate(ltemplate);
            LetterTemplate lt = _adminService.retrieveLetterTemplate(ltemplate.getTemplateId());
            lt.setName(_lettertemplateName + "Session");
            initSession(null);
            LetterTemplate lTemplate = _adminService.updateLetterTemplate(lt);
            fail("Updating letter template with session null is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.LETTER_TEMPLATE_PROCESS_FAILED);
        }
    }

    /**
     * This method tests ROIFileUpload servlet with a valid file to be uploaded
     *
     */
    public void testNewDocUploadWithInvalidOwnerType() {

        try {

            String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
            strURLaddress = constructURL("Test", 0, "sample.rtf", true, true);
            WebRequest request = new PostMethodWebRequest(strURLaddress,
                                                          getInputStream(SAMPLE_TEMPLATE),
                                                          "application/rtf");
            _servletUntClient.getProxyHost();
            WebResponse response = _servletUntClient.sendRequest(request);
        } catch (Exception e) {
            assertEquals(FAIL, ((HttpException) e).getResponseCode());
        }
    }

   /**
    * This method tests ROIFileUpload servlet with a valid file to be uploaded
    *
    */
   public void testUpdateDocUploadWithInvalidOwnerType() {

       try {

           initSession();
           String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
           String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
           LetterTemplate ltemp = setLetterTemplateDetails("Prebill", false);
           ltemp.setDocId(Integer.parseInt(value[0]));
           ltemp.setHasNotes(Boolean.parseBoolean(value[1]));
           initSession();
           long id = _adminService.createLetterTemplate(ltemp);
           LetterTemplate lt = _adminService.retrieveLetterTemplate(id);

           strURLaddress = constructURL("Test", lt.getDocId(), "sample.rtf", true, true);
           WebRequest request = new PostMethodWebRequest(strURLaddress,
                                                         getInputStream(SAMPLE_TEMPLATE),
                                                         "application/rtf");
           _servletUntClient.getProxyHost();
           WebResponse response = _servletUntClient.sendRequest(request);
       } catch (Exception e) {
           assertEquals(FAIL, ((HttpException) e).getResponseCode());
       }
   }

   /**
    * This method tests ROIFileUpload servlet with a valid file to be uploaded
    *
    */
   public void testUpdateDocUploadWithInvalidFileName() {

       try {

           String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
           LetterTemplate ltemplate = setLetterTemplateDetails("Prebill", false);
           ltemplate.setDocId(Integer.parseInt(value[0]));
           ltemplate.setHasNotes(Boolean.parseBoolean(value[1]));
           initSession();
           long id = _adminService.createLetterTemplate(ltemplate);
           String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
           LetterTemplate lt = _adminService.retrieveLetterTemplate(id);

           strURLaddress = constructURL("Test", lt.getDocId(), "=-sample.rtf", true, true);
           WebRequest request = new PostMethodWebRequest(strURLaddress,
                                                         getInputStream(SAMPLE_TEMPLATE),
                                                         "application/rtf");

           _servletUntClient.getProxyHost();
           WebResponse response = _servletUntClient.sendRequest(request);

       } catch (Exception e) {
           assertEquals(FAIL, ((HttpException) e).getResponseCode());
       }
   }

   /**
    * This method tests ROIFileUpload servlet with a valid file to be uploaded
    *
    */
   public void testUpdateDocUploadWithInvalidDocId() {

       try {

           LetterTemplate ltemplate = setLetterTemplateDetails("Prebill", false);
           String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
           strURLaddress = constructURL("Test", Integer.MAX_VALUE, "=-sample.rtf", true, true);
           WebRequest request = new PostMethodWebRequest(strURLaddress,
                                                         getInputStream(SAMPLE_TEMPLATE),
                                                         "application/rtf");
           _servletUntClient.getProxyHost();
           WebResponse response = _servletUntClient.sendRequest(request);
       } catch (Exception e) {
           assertEquals(FAIL, ((HttpException) e).getResponseCode());
       }
   }

   /**
    * This method tests ROIFileUpload servlet with a valid file to be uploaded
    *
    */
   public void testNewDocUploadForLetterTemplateWithoutCache() {

       try {

           String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
           strURLaddress = constructURL("LetterTemplate", 0, "sample.rtf", false, false);
           WebRequest request = new PostMethodWebRequest(strURLaddress,
                                                         getInputStream(SAMPLE_TEMPLATE),
                                                         "application/rtf");
           _servletUntClient.getProxyHost();
           WebResponse response = _servletUntClient.sendRequest(request);
           assertEquals(PASS, response.getResponseCode());
       } catch (Exception e) {
           e.printStackTrace();
           fail("ROIFileUpload failed");
       }
   }

   /**
    * This method tests ROIFileUpload servlet with a valid file to be uploaded
    *
    */
   public void testNewDocUploadWithZeroLetterTemplateIdWithoutCache() {

       try {

           String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
           LetterTemplate ltemplate = setLetterTemplateDetails("Prebill", false);

           ltemplate.setDocId(Integer.parseInt(value[0]));
           ltemplate.setHasNotes(Boolean.parseBoolean(value[1]));
           initSession();
           long id = _adminService.createLetterTemplate(ltemplate);
           String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
           LetterTemplate lt = _adminService.retrieveLetterTemplate(id);
           strURLaddress = constructURL("LetterTemplate", 0, "sample.rtf", false, false);
           WebRequest request = new PostMethodWebRequest(strURLaddress,
                                                         getInputStream(SAMPLE_TEMPLATE),
                                                         "application/rtf");
           _servletUntClient.getProxyHost();
           WebResponse response = _servletUntClient.sendRequest(request);
           assertEquals(PASS, response.getResponseCode());
       } catch (Exception e) {
           e.printStackTrace();
           fail("ROIFileUpload failed");
       }
   }

   /**
    * This method tests ROIFileUpload servlet with a valid file to be uploaded
    *
    */
   public void testNewDocUploadWithInvalidDocIdWithoutCache() {

       try {

           String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
           LetterTemplate ltemplate = setLetterTemplateDetails("Prebill", false);
           ltemplate.setDocId(Integer.parseInt(value[0]));
           ltemplate.setHasNotes(Boolean.parseBoolean(value[1]));
           String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
           strURLaddress = constructURL("LetterTemplate",
                                        Integer.MAX_VALUE,
                                        "sample.rtf",
                                        false,
                                        false);
           WebRequest request = new PostMethodWebRequest(strURLaddress,
                                                         getInputStream(SAMPLE_TEMPLATE),
                                                         "application/rtf");
           _servletUntClient.getProxyHost();
           WebResponse response = _servletUntClient.sendRequest(request);
       } catch (Exception e) {
           assertEquals(FAIL, ((HttpException) e).getResponseCode());
       }
   }

   /**
    * This method tests ROIFileUpload servlet with a valid file to be uploaded
    *
    */
   public void testNewDocUploadWithInvalidOwnerTypeWithoutCache() {

       try {

           String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
           LetterTemplate ltemplate = setLetterTemplateDetails("Prebill", false);
           ltemplate.setDocId(Integer.parseInt(value[0]));
           ltemplate.setHasNotes(Boolean.parseBoolean(value[1]));
           String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
           strURLaddress = constructURL("Test", 0, "sample.rtf", false, false);
           WebRequest request = new PostMethodWebRequest(strURLaddress,
                                                         getInputStream(SAMPLE_TEMPLATE),
                                                         "application/rtf");
           _servletUntClient.getProxyHost();
           WebResponse response = _servletUntClient.sendRequest(request);
       } catch (Exception e) {
           assertEquals(FAIL, ((HttpException) e).getResponseCode());
       }
   }

   /**
    * This method tests ROIFileUpload servlet with a valid file to be uploaded
    *
    */
   public void testUpdateDocUploadForLetterTemplateWithoutCache() {

       try {

           initSession();
           String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
           LetterTemplate ltemplate = setLetterTemplateDetails("Prebill", false);
           ltemplate.setDocId(Integer.parseInt(value[0]));
           ltemplate.setHasNotes(Boolean.parseBoolean(value[1]));
           initSession();
           long id = _adminService.createLetterTemplate(ltemplate);
           String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
           LetterTemplate lt = _adminService.retrieveLetterTemplate(id);
           strURLaddress = constructURL("LetterTemplate",
                                        lt.getDocId(),
                                        "sample.rtf",
                                        false,
                                        false);
           WebRequest request = new PostMethodWebRequest(strURLaddress,
                                                         getInputStream(SAMPLE_TEMPLATE),
                                                         "application/rtf");
           _servletUntClient.getProxyHost();
           WebResponse response = _servletUntClient.sendRequest(request);
           assertEquals(PASS, response.getResponseCode());
       } catch (Exception e) {
           e.printStackTrace();
           fail("ROIFileUpload failed");
       }
   }

   /**
    * This method tests ROIFileUpload servlet with a valid file to be uploaded
    *
    */
   public void testUpdateDocUploadWithInvalidOwnerTypeWithoutCache() {

       try {

           String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
           String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
           LetterTemplate ltemplate = setLetterTemplateDetails("Prebill", false);
           ltemplate.setDocId(Integer.parseInt(value[0]));
           ltemplate.setHasNotes(Boolean.parseBoolean(value[1]));
           initSession();
           long id = _adminService.createLetterTemplate(ltemplate);
           LetterTemplate lt = _adminService.retrieveLetterTemplate(id);
           strURLaddress = constructURL("Test", lt.getDocId(),  "sample.rtf",  false,  false);
           WebRequest request = new PostMethodWebRequest(strURLaddress,
                                                         getInputStream(SAMPLE_TEMPLATE),
                                                         "application/rtf");
           _servletUntClient.getProxyHost();
           WebResponse response = _servletUntClient.sendRequest(request);
           response.getResponseMessage();
       } catch (Exception e) {
           assertEquals(FAIL, ((HttpException) e).getResponseCode());
       }
   }

   /**
    * This method tests ROIFileUpload servlet with a valid file to be uploaded
    *
    */
   public void testUpdateDocUploadWithInvalidFileNameWithoutCache() {

       try {

           String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
           String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
           LetterTemplate ltemplate = setLetterTemplateDetails("Prebill", false);
           ltemplate.setDocId(Integer.parseInt(value[0]));
           ltemplate.setHasNotes(Boolean.parseBoolean(value[1]));
           initSession();
           long id = _adminService.createLetterTemplate(ltemplate);
           LetterTemplate lt = _adminService.retrieveLetterTemplate(id);
           strURLaddress = constructURL("LetterTemplate", lt.getDocId(), null, false, false);
           WebRequest request = new PostMethodWebRequest(strURLaddress,
                                                         getInputStream(SAMPLE_TEMPLATE),
                                                         "application/rtf");
           _servletUntClient.getProxyHost();
           WebResponse response = _servletUntClient.sendRequest(request);
       } catch (Exception e) {
           assertEquals(FAIL, ((HttpException) e).getResponseCode());
       }
   }

   /**
    * This method tests ROIFileUpload servlet with a valid file to be uploaded
    */
   public void testUpdateDocUploadWithInvalidDocIdWithoutCache() {

       try {

           String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
           String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
           LetterTemplate ltemplate = setLetterTemplateDetails("Prebill", false);
           ltemplate.setDocId(Integer.parseInt(value[0]));
           ltemplate.setHasNotes(Boolean.parseBoolean(value[1]));
           initSession();
           long id = _adminService.createLetterTemplate(ltemplate);
           LetterTemplate retrieved = _adminService.retrieveLetterTemplate(id);

           strURLaddress = constructURL("LetterTemplate",
                                        Integer.MAX_VALUE,
                                        "sample.rtf",
                                        false,
                                        false);
           WebRequest request = new PostMethodWebRequest(strURLaddress,
                                                         getInputStream(SAMPLE_TEMPLATE),
                                                         "application/rtf");
           _servletUntClient.getProxyHost();
           WebResponse response = _servletUntClient.sendRequest(request);
       } catch (Exception e) {
           assertEquals(FAIL, ((HttpException) e).getResponseCode());
       }
   }

   /**
    * This method tests ROIFileUpload servlet with invalid file type to be uploaded.
    *
    */
   public void testDocUploadWithInvalidFileType() {

       try {

           String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
           strURLaddress = constructURL("LetterTemplate", 0, "sample.pdf", false, false);
           WebRequest request = new PostMethodWebRequest(strURLaddress,
                                                         getInputStream(SAMPLE_TEMPLATE),
                                                         "application/rtf");

           _servletUntClient.getProxyHost();
           WebResponse response = _servletUntClient.sendRequest(request);
       } catch (Exception e) {
           assertEquals(FAIL, ((HttpException) e).getResponseCode());
       }
   }

   /**
    * This method tests ROIFileUpload servlet with invalid file type to be uploaded.
    *
    */
    public void testDocUploadWithInvalidFileTypeCase2() {

        try {

            String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
            strURLaddress = constructURL("LetterTemplate",  0,  "sample.pdf.rtf", false, false);
            WebRequest request = new PostMethodWebRequest(strURLaddress,
                                                          getInputStream(SAMPLE_TEMPLATE),
                                                          "application/rtf");

            _servletUntClient.getProxyHost();
            WebResponse response = _servletUntClient.sendRequest(request);
        } catch (Exception e) {
            assertEquals(FAIL, ((HttpException) e).getResponseCode());
        }
    }

   /**
    * This method tests ROIFileUpload servlet with a valid Owner type.
    *
    */
    public void testDocUploadWithOwnerTypeAsNull() {

        try {

            String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
            strURLaddress = constructURL(null, 0, "sample.rtf", false, false);
            WebRequest request = new PostMethodWebRequest(strURLaddress,
                                                          getInputStream(SAMPLE_TEMPLATE),
                                                          "application/rtf");

            _servletUntClient.getProxyHost();
            WebResponse response = _servletUntClient.sendRequest(request);
        } catch (Exception e) {
            assertEquals(FAIL, ((HttpException) e).getResponseCode());
        }
    }

   /**
    * This test case for deleteLetterTemplateWithSessionNull and verify
    * if it returns the appropriate exception
    */
    public void testDeleteLetterTemplateWithSessionNull() {

       try {

           String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
           LetterTemplate ltemplate = setLetterTemplateDetails("Prebill", false);
           ltemplate.setDocId(Integer.parseInt(value[0]));
           ltemplate.setHasNotes(Boolean.parseBoolean(value[1]));
           long id = _adminService.createLetterTemplate(ltemplate);
           initSession(null);
            _adminService.deleteLetterTemplate(id);

       } catch (ROIException e) {
           assertTrue(hasErrorcode(e, ROIClientErrorCodes.LETTER_TEMPLATE_PROCESS_FAILED));
       }
    }

    /**
     * This test case is for create letter template with null values
     * @throws Exception
     */
    public void testCreateLetterTemplateWithNullValue() throws Exception {

        try {

            long createdId = _adminService.createLetterTemplate(null);
            fail("Create letter template with null value is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.LETTER_TEMPLATE_PROCESS_FAILED);
        }
    }

    private String constructURL(String ownerType,
                                long docId,
                                String fileName,
                                boolean isChunk,
                                boolean isFinal) {

        String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
        strURLaddress += "?" + BaseFileTransferData.PARAMETER_USER + "=" + DEFAULT_TEST_USER;
        strURLaddress += "&" + BaseFileTransferData.PARAMETER_PD   + "=" + DEFAULT_TEST_PWD;
        strURLaddress += "&" + BaseFileTransferData.PARAMETER_TIMESTAMP + "="
                             + new Timestamp(System.currentTimeMillis());

        strURLaddress += "&" + "OWNER_TYPE="  + ownerType;
        strURLaddress += "&" + "DOC_ID"  + "=" + Long.toString(docId);
        strURLaddress += "&" + "FILE_NAME="  + fileName;
        strURLaddress += "&" + "CHUNKENABLED="  + Boolean.toString(isChunk);
        strURLaddress += "&" + "FINALCHUNK="  + Boolean.toString(isFinal);
        strURLaddress += "&" + "USER_ID="  + Long.toString(0);
        return strURLaddress;

    }

    /**
     * This method returns the input stream of sample test file
     * @return input stream
     */
    private InputStream getInputStream(String fileName) {

       return TestLetterTemplateServiceImpl.class
       .getResourceAsStream(fileName);

   }

    @Override
    protected String getServiceURL(String serviceMethod) {
       return "";
    }

    private String[] uploadDocument(long id, String fileName) {

        String[] value = null;
        try {

          String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
          strURLaddress = constructURL("LetterTemplate",
                                       0,
                                       "SampleLetterTemplate" + System.nanoTime() + ".rtf",
                                       true,
                                       true);

          WebRequest request = new PostMethodWebRequest(strURLaddress,
                                                        getInputStream(fileName),
                                                        "application/rtf");

          _servletUntClient.getProxyHost();
          WebResponse response = _servletUntClient.sendRequest(request);
          int code = response.getResponseCode();
          String docIdHeader = "CHECKIN_ID";
          String idValue = response.getHeaderField(docIdHeader);
          value = idValue.split(ROIConstants.FIELD_DELIMITER);

      } catch (Exception e) {
          fail("ROIFileUpload failed");
      }
      return value;
    }

    private LetterTemplate setLetterTemplateDetails(String letterType, boolean isDefault) {

        LetterTemplate lt = new LetterTemplate();
        lt.setName(_lettertemplateName +  System.nanoTime());
        lt.setDesc(_letterTemplateDesc);
        lt.setIsDefault(isDefault);
        lt.setLetterType(letterType);
        return lt;
    }

    /**
     * This test case for hasLetterTemplate
     */
    public void testHasLetterTemplate() {

        try {

            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate lt = setLetterTemplateDetails("invoice", false);
            lt.setDocId(Integer.parseInt(value[0]));
            lt.setHasNotes(Boolean.parseBoolean(value[1]));
            initSession();
            long createdId = _adminService.createLetterTemplate(lt);

            boolean hasTemplate = _adminService.hasLetterTemplate(INVOICE_TEMPLATE);

            assertEquals(true, hasTemplate);
        } catch (ROIException e) {
            fail("Haslettertemplate should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This test case for hasLetterTemplate with invalid  lettertemplate type
     */
    public void testHasLetterTemplateWithInvalidType() {

        try {

            String[] value = uploadDocument(0, SAMPLE_TEMPLATE);
            LetterTemplate lt = setLetterTemplateDetails("invoice", false);
            lt.setDocId(Integer.parseInt(value[0]));
            lt.setHasNotes(Boolean.parseBoolean(value[1]));
            initSession();
            long createdId = _adminService.createLetterTemplate(lt);

            boolean hasTemplate = _adminService.hasLetterTemplate(INVALID_TEMPLATE);

            fail("Has letter template with invalid letter type is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_LETTER_TEMPLATE_TYPE);
        }
    }
}
