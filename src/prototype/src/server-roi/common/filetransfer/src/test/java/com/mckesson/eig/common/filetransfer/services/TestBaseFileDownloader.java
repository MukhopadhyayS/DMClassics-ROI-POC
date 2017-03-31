/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Corporation and/or one of its subsidiaries and is protected 
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.common.filetransfer.services;

import java.io.File;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.mckesson.eig.common.filetransfer.controller.BaseContentRetrieverTester;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.testing.UnitTest;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpException;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

public class TestBaseFileDownloader extends UnitTest {

    /**
     * Holds the instance of ServletUnitClient.
     */
    private ServletUnitClient _servletUntClient;

    /**
     * The current servlet runner
     */
    private ServletRunner _servletRunner;

    /**
     * Member variable initialized.
     */
    private static final String SERVLET_NAME = "getContentServlet_Test";

    /**
     * Member variable initialized.
     */
    private static final String MOCK_SERVLET_NAME = "getMockContentServlet_Test";

    /**
     * LOG - Instance of Logger used to log messages.
     */
    private static final Log LOG = LogFactory
            .getLogger(TestBaseFileDownloader.class);


    private static final String SPRING_CONFIG_FILE =
                                "com/mckesson/eig/common/filetransfer/config/spring_config.xml";

    private static final BeanFactory BEAN_FACTORY = new XmlBeanFactory(
                                                    new ClassPathResource(SPRING_CONFIG_FILE));

    private static final String USER_NAME = "harpo";
    private static final String TIMESTAMP = "2008-03-06T22:19:56Z";

    // password = swordfish
    private static final String PD_ENC = "2008-03-06T22:19:56Z" 
                                        + "ybQQD3v7DCKFHqLf9r/pzBNbFCEbUk2Mo//qC5dJMyE=";
    private String _encodedPwd;

    /**
     * Creates a ServletUnitClient to invoke the registered servlet.
     * 
     * @throws Exception
     */
    protected void setUp() throws Exception {
        super.setUp();
        _servletRunner = new ServletRunner();
        _servletRunner.registerServlet(SERVLET_NAME,
                                       BasetFileDownloaderTester.class.getName());
        _servletRunner.registerServlet(MOCK_SERVLET_NAME,
                                       MockBaseFileDownloader.class.getName());

        _servletUntClient = _servletRunner.newClient();

        SpringUtilities.getInstance().setBeanFactory(BEAN_FACTORY);

        if (_encodedPwd == null) {
            _encodedPwd = URLEncoder.encode(PD_ENC, "UTF-8");
        }
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    protected void deleteFile(String fileName) {
        File f = new File(fileName);
        
        if (f.exists()) {
            f.delete();
        }
    }
    protected String getURLString(String contentId) {
        return (getURLString(contentId, "0"));
    }
    
    protected String getURLString(String contentId, String offset) {
        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
        + BaseFileTransferData.PARAMETER_USER + "=" + USER_NAME + "&"
        + BaseFileTransferData.PARAMETER_PD + "=" + _encodedPwd + "&"
        + BaseFileTransferData.PARAMETER_FILE_ID + "="
        + contentId + "&"
        + BaseFileTransferData.PARAMETER_REVISION + "= &"
        + BaseFileTransferData.PARAMETER_BLOCKSIZE + "=10240&"
        + BaseFileTransferData.PARAMETER_TIMESTAMP + "=" + TIMESTAMP + "&"
        + BaseFileTransferData.PARAMETER_TICKET + "=ATicket&"
        + BaseFileTransferData.PARAMETER_OFFSET + "="
        + offset;
        
        return (url);
    }
    
    protected String getURLString2(String contentId, String offset) {
        String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?"
        + BaseFileTransferData.PARAMETER_USERNAME + "=" + USER_NAME + "&"
        + BaseFileTransferData.PARAMETER_PD + "=" + _encodedPwd + "&"
        + BaseFileTransferData.PARAMETER_FILE_ID + "="
        + contentId + "&"
        + BaseFileTransferData.PARAMETER_REVISION + "= &"
        + BaseFileTransferData.PARAMETER_BLOCKSIZE + "=10240&"
        + BaseFileTransferData.PARAMETER_TIMESTAMP + "=" + TIMESTAMP + "&"
        + BaseFileTransferData.PARAMETER_TICKET + "=ATicket&"
        + BaseFileTransferData.PARAMETER_OFFSET + "="
        + offset;
        
        return (url);
    }
    /*
     * Test method for
     * 'com.mckesson.eig.common.viewer.services.BaseFileDownloader.
     * getRemoteFile(BaseFileTransferData)'
     */
    public void testGetRemoteFileSuccess() {
        LOG.debug("Enter testGetRemoteFileSuccess");
        try {
            WebRequest request = new PostMethodWebRequest(
                    getURLString(BaseContentRetrieverTester.SMALL_FILE));
            WebResponse response = _servletUntClient.sendRequest(request);
            assertNotNull("response was null", response);
            assertEquals(HttpServletResponse.SC_OK, response.getResponseCode());

        } catch (Exception e) {
            LOG.error("tesGetRemoteFileSuccess failed", e);
            fail(e.getMessage());
        }
    }

    /*
     * Test method for
     * 'com.mckesson.eig.common.viewer.services.BaseFileDownloader.
     * getRemoteFile(BaseFileTransferData)'
     */
    public void testGetRemoteInvalidFile() {
        LOG.debug("Enter testGetRemoteInvalidFile");
        try {
            String url = "http://127.0.0.1:8080/" + MOCK_SERVLET_NAME + "?"
            + BaseFileTransferData.PARAMETER_USER + "=" + USER_NAME + "&"
            + BaseFileTransferData.PARAMETER_PD + "=" + _encodedPwd + "&"
            + BaseFileTransferData.PARAMETER_FILE_ID + "="
            + BaseContentRetrieverTester.SMALL_FILE + "&"
            + BaseFileTransferData.PARAMETER_FILENAME + "=filename"
            + BaseFileTransferData.PARAMETER_REVISION + "= &"
            + BaseFileTransferData.PARAMETER_BLOCKSIZE + "=10240&"
            + BaseFileTransferData.PARAMETER_TIMESTAMP + "=2008-03-10T11:07:07Z&"
            + BaseFileTransferData.PARAMETER_TICKET + "=ATicket&"
            + BaseFileTransferData.PARAMETER_OFFSET + "=0";
            
            WebRequest request = new GetMethodWebRequest(url);
            _servletUntClient.sendRequest(request);
        } catch (HttpException e) {
            LOG.error("testGetRemoteInvalidFile failed", e);
            assertEquals(HttpServletResponse.SC_BAD_REQUEST, e.getResponseCode());
        }  catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testWithInvalidUser() {
        LOG.debug("Enter testInvalidUser");
        
        try {
            String url = "http://127.0.0.1:8080/" + SERVLET_NAME + "?user=" + "&";
            url +=  BaseFileTransferData.PARAMETER_PD  + "=" + _encodedPwd + "&";
            url += BaseFileTransferData.PARAMETER_TIMESTAMP + "=" + TIMESTAMP;

            WebRequest request = new GetMethodWebRequest(url);
            _servletUntClient.sendRequest(request);
            fail("HttpException should have been thrown");
        } catch (HttpException e) {
            assertEquals(HttpServletResponse.SC_UNAUTHORIZED, e.getResponseCode());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testNoParams() {
        LOG.debug("Enter testNoParams");
        
        try {
            String url = "http://127.0.0.1:8080/" + SERVLET_NAME;
            WebRequest request = new GetMethodWebRequest(url);
            _servletUntClient.sendRequest(request);
            fail("HttpException should have been thrown");
        } catch (HttpException e) {
            LOG.error("testGetRemoteFileNotExist failed", e);
            assertEquals(HttpServletResponse.SC_BAD_REQUEST, e.getResponseCode());
        } catch (Exception e) {
            LOG.error("testGetRemoteFileNotExist failed", e);
            fail(e.getMessage());
        }
    }
    
    public void testGetRemoteFileLarge() {
        LOG.debug("Enter testGetRemoteFileSuccess");
        try {

            WebRequest request = new GetMethodWebRequest(
                    getURLString(BaseContentRetrieverTester.LARGE_FILE));
            WebResponse response = _servletUntClient.sendRequest(request);
            assertNotNull("response was null", response);
            assertEquals(HttpServletResponse.SC_PARTIAL_CONTENT, response.getResponseCode());

        } catch (Exception e) {
            LOG.error("tesGetRemoteFileSuccess failed", e);
            fail(e.getMessage());
        }
    }

    public void testGetRemoteFileBadOffset() {
        LOG.debug("Enter testGetRemoteFileNotExist");
        responseFailure(BaseContentRetrieverTester.MED_FILE, "10241024");
    }

    public void testGetRemoteFileNotExist() {
        LOG.debug("Enter testGetRemoteFileNotExist");
        responseFailure(BaseContentRetrieverTester.NOT_EXIST_FILE);
    }
    
    public void testGetRemoteFileZeroByte() {
        LOG.debug("Enter testGetRemoteFileZeroByte");
        responseFailure(BaseContentRetrieverTester.ZERO_BYTE_FILE);
    }
    
    public void testOtherBaseFileDownloadDataAccess() {
        LOG.debug("Enter testGetRemoteFileSuccess");
        try {
            WebRequest request = new GetMethodWebRequest(
                    getURLString2(BaseContentRetrieverTester.SMALL_FILE, "0"));
            WebResponse response = _servletUntClient.sendRequest(request);
            assertNotNull("response was null", response);
            assertEquals(HttpServletResponse.SC_OK, response.getResponseCode());

        } catch (Exception e) {
            LOG.error("tesGetRemoteFileSuccess failed", e);
            fail(e.getMessage());
        }        
    }
    
    protected void responseFailure(String contentID) {
        responseFailure(contentID, "0");
    }
    
    protected void responseFailure(String contentID, String offset) {
        try {
            WebRequest request = new GetMethodWebRequest(
                    getURLString(contentID, offset));
            _servletUntClient.sendRequest(request);
            fail("IOException should have been thrown");
        } catch (HttpException e) {
            LOG.error("testGetRemoteFileNotExist failed", e);
            assertEquals(HttpServletResponse.SC_BAD_REQUEST, e.getResponseCode());
        } catch (Exception e) {
            LOG.error("testGetRemoteFileNotExist failed", e);
            fail(e.getMessage());
        }
    }
}
