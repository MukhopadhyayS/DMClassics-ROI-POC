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

package com.mckesson.eig.common.filetransfer.services;

import java.io.File;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * This class tests the working of DocFMPutCacheFileServlet in different
 * scenarios. There are three methods depending upon three different type of
 * requests sent to the servlet.
 *
 */
public class TestBaseFileUploader extends TestCase {

    /**
     * Static variable for the class itself.
     */
    private static String _cName = TestBaseFileUploader.class.getName();

    /**
     * Holds the instance of ServletUnitClient.
     */
    private ServletUnitClient _servletUntClient;

    /**
     * Member variable initialized.
     */
    private static final String CONTENT_TYPE = "application/octet-stream";

    /**
     * Member variable initialized.
     */
    private static final String SERVLET_NAME = "putCacheServlet";

    /**
     * Member variable initialized.
     */
    private static final String MOCK_SERVLET_NAME = "putMockCacheServlet";

    /**
     * Member variable initialized.
     */
    private static final String TESTER_SERVLET_NAME = "putTesterCacheServlet";

    /**
     * Member variable initialized.
     */
    private static final String RESEND_SERVLET_NAME = "putResendCacheServlet";

    /**
     * LOG - Instance of Logger used to log messages.
     */
    private static final Log LOG = LogFactory.getLogger(TestBaseFileUploader.class);

    private static final String SPRING_CONFIG_FILE =
                                "com/mckesson/eig/common/filetransfer/config/spring_config.xml";

    private static final BeanFactory BEAN_FACTORY = new XmlBeanFactory(
                                                    new ClassPathResource(SPRING_CONFIG_FILE));

    private static final String USER_NAME = "harpo";
    private static final String TIMESTAMP = "2008-03-06T22:19:56Z";

    // password = swordfish
    private static final String PD_ENC = "2008-03-06T22:19:56Z"
                                        + "n7bRrkbr54JxuDsHqzsOLjoMDNJreU5D0CxTjKY2oTs=";
    private String _encodedPwd;

    /**
     * Creates a ServletUnitClient to invoke the registered servlet.
     *
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {

        super.setUp();

        System.setProperty("cache.cleanup.interval", "1000");

        ServletRunner servletRunner = new ServletRunner();
        servletRunner.registerServlet(SERVLET_NAME, BaseFileUploader.class.getName());
        servletRunner.registerServlet(MOCK_SERVLET_NAME, MockBaseFileUploader.class.getName());
        servletRunner.registerServlet(TESTER_SERVLET_NAME, BaseFileUploaderTester.class.getName());
        servletRunner.registerServlet(RESEND_SERVLET_NAME, 
                                      MockResendBaseFileUploader.class.getName());

        _servletUntClient = servletRunner.newClient();
        File cache = new File(getCacheRootDir(), "FileCache");
        if (!cache.exists()) {
            cache.mkdir();
        }
        initSpringConfig();

        if (_encodedPwd == null) {
            _encodedPwd = URLEncoder.encode(PD_ENC, "UTF-8");
        }
    }

    private void initSpringConfig() {

        if (SpringUtilities.getInstance().getBeanFactory() == null) {
            SpringUtilities.getInstance().setBeanFactory(BEAN_FACTORY);
        }
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        File cache = new File(getCacheRootDir(), "FileCache");
        if (!cache.exists()) {
            cache.delete();
        }
    }

    /**
     * This method tests the invocation of BaseFileUploader in a scenario,
     * wherein web request has an invalid filele.
     *
     */
    public void testServletInvalidFile() {
        try {
            LOG.debug(_cName
                    + "testServletInvalidFile, Entering : Timestamp["
                    + new Timestamp(new Date().getTime()) + "]");

            String strURLaddress = "http://127.0.0.1:8080/" + SERVLET_NAME;
            String strCacheDir = "FileCache";

            File newFile = new File(strCacheDir);

            if (newFile.isDirectory()) {
                File[] arrayFile = newFile.listFiles();
                for (int i = 0; i < arrayFile.length; i++) {
                    arrayFile[i].delete();
                }
                newFile.delete();
            }
            strURLaddress += "?" + BaseFileTransferData.PARAMETER_CHECKIN_ID + "=ABC123";
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_USERNAME   + "=" + USER_NAME;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_PD   + "=" + _encodedPwd;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_TIMESTAMP  + "=" + TIMESTAMP;

            WebRequest request =
                new PostMethodWebRequest(strURLaddress,
                                         getClass().getResourceAsStream("/Default_File.txt"),
										 null);
            WebResponse response = _servletUntClient.sendRequest(request);

            String theMessage = response.getText();
            assertTrue(theMessage.contains("File does not exist"));

        } catch (Exception e) {

            LOG.error(_cName + " testServletInvalidFile has error : " + e.getMessage()
                    + " : Timestamp[" + new Timestamp(new Date().getTime())
                    + "]");
        }
        LOG.debug(_cName
                + "testServletInvalidFile, Exiting : Timestamp["
                + new Timestamp(new Date().getTime()) + "]");
    }

    /**
     * This method tests the invocation of BaseFileUploader in a positive
     * scenario. A web request is sent along with a FileInputStream object that
     * carries a file which has to be transported to a new Directory. As a
     * response new directory is created and it puts the file inside it. At the
     * end, the directory is deleted at the successfully transportation of file.
     */
    public void testPutFileServlet() {

        try {
            String strURLaddress = "http://127.0.0.1:8080/" + SERVLET_NAME  + "?";
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_USERNAME  + "=" + USER_NAME;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_PD  + "=" + _encodedPwd;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_TIMESTAMP + "=" + TIMESTAMP;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_FINALCHUNK + "=true";

            File newFile    = new File(getCacheRootDir(), "FileCache");

            if (newFile.isDirectory()) {
                File[] arrayFile = newFile.listFiles();
                for (int i = 0; i < arrayFile.length; i++) {
                    arrayFile[i].delete();
                }
            }

            WebRequest request =
                new PostMethodWebRequest(strURLaddress, 
                                         getClass().getResourceAsStream("/Default_File.txt"),
                                         null);
            LOG.debug(_cName
                            + "Trying to call doPost() of the servlet, Entering : Timestamp["
                            + new Timestamp(new Date().getTime()) + "]");

            WebResponse response = _servletUntClient.sendRequest(request);
            LOG.debug(_cName
                    + "Getting the response out of the request. : Timestamp["
                    + new Timestamp(new Date().getTime()) + "]");

            assertTrue("New Directory and File is Created ", newFile
                    .isDirectory()
                    && response.getContentType().equals(CONTENT_TYPE));

            if (newFile.isDirectory()) {
                File[] f = newFile.listFiles();
                for (int i = 0; i < f.length; i++) {
                    f[i].delete();
                }
                newFile.delete();
            }
            LOG.debug(_cName
                            + "Directory with all its files inside it,are deleted : Timestamp["
                            + new Timestamp(new Date().getTime()) + "]");
        } catch (Exception e) {

            LOG.error(_cName + " has error : " + e.getMessage()
                    + " : Timestamp[" + new Timestamp(new Date().getTime())
                    + "]");
        }
    }

    /**
     * This method tests the invocation of BaseFileUploader in a negative
     * scenario. The web request calls an invalid URL for the invocation of the
     * servlet. Hence results to an exception.
     */
    public void testServletInvalidURL() {

        try {
            String strInvalidURL = "http://127.0.0.1:8080/";
            WebRequest request = new PostMethodWebRequest(strInvalidURL);
            LOG.debug(_cName
                      + "Trying to call doPost()of the servlet, Entering : Timestamp["
                      + new Timestamp(new Date().getTime()) + "]");

            _servletUntClient.sendRequest(request);
            LOG.debug(_cName
                    + "Getting the response out of the request : Timestamp["
                    + new Timestamp(new Date().getTime()) + "]");
        } catch (Exception e) {

            assertFalse("URL Address is not proper. ", false);
            LOG.debug(_cName
                    + "Expected exception. Thrown intentionally : Timestamp["
                    + new Timestamp(new Date().getTime()) + "]");
        }
    }

    /**
     * This method tests the invocation of BaseFileUploader in a scenario,
     * wherein web request does not carry a file. As a result servlet creates a
     * new file under FileCache Directory. The contents of the file will be the
     * key-value pair setted to the request.
     *
     */
    public void testServletWithNoFile() {

        try {
            String strURLaddress = "http://127.0.0.1:8080/" + SERVLET_NAME;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_USERNAME + "=" + USER_NAME;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_PD + "=" + _encodedPwd;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_TIMESTAMP + "=" + TIMESTAMP;

            WebRequest request = new PostMethodWebRequest(strURLaddress);
            LOG.debug(_cName
                      + "Trying to call doPost() of the servlet, Entering : Timestamp["
                      + new Timestamp(new Date().getTime()) + "]");

            request.setParameter(strURLaddress, SERVLET_NAME);

            WebResponse response = _servletUntClient.sendRequest(request);
            LOG.debug(_cName
                      + "Getting the response out of the request : Timestamp["
                      + new Timestamp(new Date().getTime()) + "]");

            assertNotSame(response.getContentType(), CONTENT_TYPE);

        } catch (Exception e) {
            LOG.debug(_cName + e.getMessage() + " : Timestamp["
                    + new Timestamp(new Date().getTime()) + "]");
        }
    }

    /**
     * This method tests the invocation of BaseFileUploader in a scenario,
     *
     */
    public void testServletWithInvalidUserCredientials() {

        try {
            String strURLaddress = "http://127.0.0.1:8080/" + SERVLET_NAME + "?";
            strURLaddress += BaseFileTransferData.PARAMETER_USERNAME + "=";
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_TIMESTAMP + "=";
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_PD  + "=" + _encodedPwd;

            WebRequest request = new PostMethodWebRequest(strURLaddress);
            LOG.debug(_cName
                      + "Trying to call doPost() of the servlet, Entering : Timestamp["
                      + new Timestamp(new Date().getTime()) + "]");

            request.setParameter(strURLaddress, SERVLET_NAME);

            WebResponse response = _servletUntClient.sendRequest(request);
            LOG.debug(_cName
                      + "Getting the response out of the request : Timestamp["
                      + new Timestamp(new Date().getTime()) + "]");
            assertNotNull(response.getText());
            assertTrue(response.getText().contains("Invalid credentials for user."));

        } catch (Exception e) {
            LOG.debug(_cName + e.getMessage() + " : Timestamp["
                    + new Timestamp(new Date().getTime()) + "]");
        }
    }
    /**
     * This method tests the invocation of BaseFileUploader in a scenario,
     * Here we are sending a request to servlet with no parameters
     */
    public void testServletWithInvalidParameters() {

        try {
            String strURLaddress = "http://127.0.0.1:8080/" + SERVLET_NAME;
            WebRequest request = new PostMethodWebRequest(strURLaddress);
            LOG.debug(_cName
                    + "Trying to call doPost() of the servlet, Entering : Timestamp["
                    + new Timestamp(new Date().getTime()) + "]");

            _servletUntClient.sendRequest(request);
            fail("Should have to thrown an exception");
        } catch (Exception e) {
            LOG.debug(_cName + e.getMessage() + " : Timestamp["
                    + new Timestamp(new Date().getTime()) + "]");
            assert (true);
        }
    }

    public void testUploadFileWithException() {

        try {
            String strURLaddress = "http://127.0.0.1:8080/" + TESTER_SERVLET_NAME + "?";
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_USERNAME  + "=" + USER_NAME;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_PD  + "=" + _encodedPwd;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_TIMESTAMP + "=" + TIMESTAMP;

            File newFile    = new File(getCacheRootDir(), "FileCache");

            if (newFile.isDirectory()) {
                File[] arrayFile = newFile.listFiles();
                for (int i = 0; i < arrayFile.length; i++) {
                    arrayFile[i].delete();
                }
                newFile.delete();
            }

            WebRequest request =
                new PostMethodWebRequest(strURLaddress, 
                                        getClass().getResourceAsStream("/Default_File.txt"), 
                                        null);
            WebResponse response = _servletUntClient.sendRequest(request);
            assertNotNull(response.getText());
            assertTrue(response.getText().contains("400")); // denotes bad request

        } catch (Exception e) {
            assert (true);
        }
    }

    public void testWithInvalidFileName() {

        try {
            String strURLaddress = "http://127.0.0.1:8080/" + SERVLET_NAME;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_USERNAME  + "=" + USER_NAME;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_PD  + "=" + _encodedPwd;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_TIMESTAMP + "=" + TIMESTAMP;

            File newFile    = new File(getCacheRootDir(), "FileCache");

            if (newFile.isDirectory()) {
                File[] arrayFile = newFile.listFiles();
                for (int i = 0; i < arrayFile.length; i++) {
                    arrayFile[i].delete();
                }
                newFile.delete();
            }

            WebRequest request =
                new PostMethodWebRequest(strURLaddress, 
                                         getClass().getResourceAsStream("/Default_File.txt"), 
                                         null);
            WebResponse response = _servletUntClient.sendRequest(request);
            assertNotNull(response.getText());
            assertTrue(response.getText().contains("400")); // denotes bad request

        } catch (Exception e) {
            assert (true);
        }
    }

    public void testUploadCancelation() {

        try {

            String strURLaddress = "http://127.0.0.1:8080/" + SERVLET_NAME + "?";
            strURLaddress += BaseFileTransferData.PARAMETER_USERNAME  + "=" + USER_NAME;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_PD  + "=" + _encodedPwd;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_TIMESTAMP + "=" + TIMESTAMP;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_MODE + "=" 
                                 + BaseFileTransferData.MODE_CANCEL;

            WebRequest request = new PostMethodWebRequest(strURLaddress);
            _servletUntClient.sendRequest(request);
        } catch (Exception e) {
            fail("Should have not thrown exception" + e.getMessage());
        }
    }

    public void testUploadWithInvalidFileName() {

        try {
            String strURLaddress = "http://127.0.0.1:8080/" + MOCK_SERVLET_NAME + "?";
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_USERNAME  + "=" + USER_NAME;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_PD  + "=" + _encodedPwd;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_TIMESTAMP + "=" + TIMESTAMP;

            File newFile    = new File(getCacheRootDir(), "FileCache");

            if (newFile.isDirectory()) {
                File[] arrayFile = newFile.listFiles();
                for (int i = 0; i < arrayFile.length; i++) {
                    arrayFile[i].delete();
                }
                newFile.delete();
            }

            WebRequest request =
                new PostMethodWebRequest(strURLaddress, 
                                         getClass().getResourceAsStream("/Default_File.txt"), 
                                         null);
            WebResponse response = _servletUntClient.sendRequest(request);
            assertNotNull(response.getText());
            assertTrue(response.getText().contains("400")); // denotes bad request

        } catch (Exception e) {
            assert (true);
        }
    }

    public void testUploader() {

        try {

            String strURLaddress = "http://127.0.0.1:8080/" + MOCK_SERVLET_NAME;
            WebRequest request = new GetMethodWebRequest(strURLaddress);
            _servletUntClient.sendRequest(request);
        } catch (Exception e) {
            assert (true);
        }
    }

    public void testPutFileServletWithResendData() {

        try {
            String strURLaddress = "http://127.0.0.1:8080/" + RESEND_SERVLET_NAME  + "?";
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_USERNAME  + "=" + USER_NAME;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_PD  + "=" + _encodedPwd;
            strURLaddress += "&" + BaseFileTransferData.PARAMETER_TIMESTAMP + "=" + TIMESTAMP;

            File newFile    = new File(getCacheRootDir(), "FileCache");

            if (newFile.isDirectory()) {
                File[] arrayFile = newFile.listFiles();
                for (int i = 0; i < arrayFile.length; i++) {
                    arrayFile[i].delete();
                }
            }

            WebRequest request =
                new PostMethodWebRequest(strURLaddress, 
                                         getClass().getResourceAsStream("/Default_File.txt"), 
                                         null);
            LOG.debug(_cName
                            + "Trying to call doPost() of the servlet, Entering : Timestamp["
                            + new Timestamp(new Date().getTime()) + "]");

            WebResponse response = _servletUntClient.sendRequest(request);
            assertNotNull(response.getText());
            assertTrue(response.getText().contains("400")); // denotes bad request

            if (newFile.isDirectory()) {
                File[] f = newFile.listFiles();
                for (int i = 0; i < f.length; i++) {
                    f[i].delete();
                }
                newFile.delete();
            }
            LOG.debug(_cName
                            + "Directory with all its files inside it,are deleted : Timestamp["
                            + new Timestamp(new Date().getTime()) + "]");
        } catch (Exception e) {

            LOG.error(_cName + " has error : " + e.getMessage()
                    + " : Timestamp[" + new Timestamp(new Date().getTime())
                    + "]");
        }
    }

    private String getCacheRootDir() {
        return System.getProperty("java.io.tmpdir");
    }
}
