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


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;

import com.mckesson.eig.common.filetransfer.services.BaseFileDownloader;
import com.mckesson.eig.common.filetransfer.services.BaseFileTransferData;
import com.mckesson.eig.roi.admin.model.LetterTemplate;
import com.mckesson.eig.roi.admin.model.LetterTemplateList;
import com.mckesson.eig.roi.test.BaseROITestCase;
import com.mckesson.eig.roi.utils.AccessFileLoader;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpException;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;


/**
 * @author Vidhya.C.S
 * @date   Jul 14, 2008
 * @since  HPF 13.1 [ROI]; May 27, 2008
 */
public class TestROIFileDownloader
extends BaseROITestCase {


    /**
     * Holds the instance of ServletUnitClient.
     */
    private ServletUnitClient _servletUntClient;
    private static final int FAIL  = 400;

    /**
     * Member variable initialized.
     */
    private static final String SERVLET_NAME = "BaseFileDownloader";

    protected static final String  ADMIN_SERVICE =
        "com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl";

    private static ROIAdminService  _adminService;

    /**
     * Creates a ServletUnitClient to invoke the registered servlet.
     * @throws Exception
     */
    @Override
    public void setUp()
    throws Exception  {

        super.setUp();
        ServletRunner servletRunner = new ServletRunner();
        servletRunner.registerServlet(SERVLET_NAME, BaseFileDownloader.class.getName());

        _servletUntClient = servletRunner.newClient();
        File cache = AccessFileLoader.getFile(getCacheRootDir(), "DocCache");
        if (!cache.exists()) {
            cache.mkdir();
        }
        _adminService = (ROIAdminService) getService(ADMIN_SERVICE);
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {

        super.tearDown();
        File cache = AccessFileLoader.getFile(getCacheRootDir(), "DocCache");
        if (!cache.exists()) {
            cache.delete();
        }
    }

    /**
     * This method tests ROIFileDownload servlet with a valid file to be downloaded
     *
     */
    public void testNewFileDownloadWithCache() { //not working

        try {

            String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
            String strFilePath = getFileSourcePath("Sample.rtf");
            LetterTemplate lt = retrieveAllLetterTemplateForNonZeroDocIds();
            if (lt != null) {

                strURLaddress = constructURL("LetterTemplate", lt.getDocId(), true);
                WebRequest request = new GetMethodWebRequest(strURLaddress);
                _servletUntClient.getProxyHost();
                WebResponse response = _servletUntClient.sendRequest(request);
                if (response.getInputStream().available() > 0) {
                    assertTrue(true);
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
            fail("ROIFileDownload failed");
        }
    }

    /**
     * This method tests ROIFileDownload servlet with a valid file to be downloaded
     *
     */
    public void testNewFileDownloadWithoutCache() {

        try {

            String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
            String strFilePath = getFileSourcePath("Sample.rtf");
            LetterTemplate lt = retrieveAllLetterTemplateForNonZeroDocIds();
            if (lt != null) {

                strURLaddress = constructURL("LetterTemplate", lt.getDocId(), false);
                WebRequest request = new GetMethodWebRequest(strURLaddress);
                _servletUntClient.getProxyHost();
                WebResponse response = _servletUntClient.sendRequest(request);

                if (response.getInputStream().available() > 0) {
                    assertTrue(true);
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
            fail("ROIFileDownload failed");
        }
    }

    /**
     * This method tests ROIFileDownload servlet with a valid file to be downloaded
     *
     */
    public void testNewFileDownloadWithCacheWithInvalidDocId() {

        try {

            String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
            String strFilePath = getFileSourcePath("Sample.rtf");
            LetterTemplate lt = retrieveAllLetterTemplateForNonZeroDocIds();

            if (lt != null) {

                strURLaddress = constructURL("LetterTemplate", Integer.MAX_VALUE, true);
                WebRequest request = new GetMethodWebRequest(strURLaddress);
                _servletUntClient.getProxyHost();
                WebResponse response = _servletUntClient.sendRequest(request);
            }

            fail("ROIFileDownload failed");
        } catch (Exception e) {

            e.printStackTrace();
            assertEquals(FAIL, ((HttpException) e).getResponseCode());
        }
    }

    /**
     * This method tests ROIFileDownload servlet with a valid file to be downloaded
     *
     */
    public void testNewFileDownloadWithCacheWithZeroDocId() {

        try {

            String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
            String strFilePath = getFileSourcePath("Sample.rtf");
            strURLaddress = constructURL("LetterTemplate", 0, true);
            WebRequest request = new GetMethodWebRequest(strURLaddress);
            _servletUntClient.getProxyHost();
            WebResponse response = _servletUntClient.sendRequest(request);
            fail("ROIFileDownload failed");
        } catch (Exception e) {
            assertEquals(FAIL, ((HttpException) e).getResponseCode());
        }
    }

    /**
     * This method tests ROIFileDownload servlet with a valid file to be downloaded
     *
     */
    public void testNewFileDownloadWithCacheInvalidOwnerType() {

        try {

            String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
            String strFilePath = getFileSourcePath("Sample.rtf");
            LetterTemplate lt = retrieveAllLetterTemplateForNonZeroDocIds();

            if (lt != null) {

                strURLaddress = constructURL("Testing", lt.getDocId(), true);
                WebRequest request = new GetMethodWebRequest(strURLaddress);
                _servletUntClient.getProxyHost();
                WebResponse response = _servletUntClient.sendRequest(request);
            }
        } catch (Exception e) {
            assertEquals(FAIL, ((HttpException) e).getResponseCode());
        }
    }

    // Following are the test case for file downlaod wihtout cache.
    /**
     * This method tests ROIFileDownload servlet with a valid file to be downloaded
     *
     */
    public void testNewFileDownloadWithoutCacheWithInvalidDocId() {

        try {

            String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
            String strFilePath = getFileSourcePath("Sample.rtf");
            strURLaddress = constructURL("LetterTemplate", Integer.MAX_VALUE, false);
            WebRequest request = new GetMethodWebRequest(strURLaddress);
            _servletUntClient.getProxyHost();
            WebResponse response = _servletUntClient.sendRequest(request);
            fail("ROIFileDownload failed");
        } catch (Exception e) {
            assertEquals(FAIL, ((HttpException) e).getResponseCode());
        }
    }

    /**
     * This method tests ROIFileDownload servlet with a valid file to be downloaded
     *
     */
    public void testNewFileDownloadWithoutCacheWithZeroDocId() {

        try {

            String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
            String strFilePath = getFileSourcePath("Sample.rtf");
            strURLaddress = constructURL("LetterTemplate", 0, false);
            WebRequest request = new GetMethodWebRequest(strURLaddress);
            _servletUntClient.getProxyHost();
            WebResponse response = _servletUntClient.sendRequest(request);
            fail("ROIFileDownload failed");
        } catch (Exception e) {
            assertEquals(FAIL, ((HttpException) e).getResponseCode());
        }
    }

    /**
     * This test case  ROIFileDownload servlet with a valid file to be downloaded
     *
     */
    public void testNewFileDownloadWithoutCacheInvalidOwnerType() {

        try {

            String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
            String strFilePath = getFileSourcePath("Sample.rtf");
            LetterTemplate lt = retrieveAllLetterTemplateForNonZeroDocIds();
            if (lt != null) {

                strURLaddress = constructURL("Testing", lt.getDocId(), false);
                WebRequest request = new GetMethodWebRequest(strURLaddress);
                _servletUntClient.getProxyHost();
                WebResponse response = _servletUntClient.sendRequest(request);
            }

        } catch (Exception e) {
            assertEquals(FAIL, ((HttpException) e).getResponseCode());
        }
    }

    /**
     * This method gets the file from the source path
     * @param fileName name of the file
     * @return file
     */
    private String getFileSourcePath(String fileName)throws Exception {

        String userHome = System.getProperty("user.home");
        try {

            File f = AccessFileLoader.getFile(userHome + "\\" + fileName);
            if (!f.exists()) {

                f.createNewFile();
                BufferedOutputStream bos = new BufferedOutputStream(AccessFileLoader.getFileOutputStream(f, false));
                bos.write(new String("Creating sample file for testing "
                         + "file upload operation").getBytes());
                bos.close();
            }

        } catch (IOException ioe) {
            throw new Exception("Unable to create temporary in user home dir");
        }
        return userHome + "\\" + fileName;
    }

    /**
     * This method gets the cache root dir
     * @return string
     */
    private String getCacheRootDir() {

        return BaseFileDownloader.class.getProtectionDomain().
                                        getCodeSource().getLocation().getPath();
    }

    /**
     * This method construct a string
     * @param ownerType name of the ownerType
     * @param l id of the LetterTemplateFile
     * @return string
     */
    private String constructURL(String ownerType, long docId, boolean isCache) {

        String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
        strURLaddress += "?" + BaseFileTransferData.PARAMETER_USERNAME   + "=user";
        strURLaddress += "&" + BaseFileTransferData.PARAMETER_PD   + "=password";
        strURLaddress += "&" + BaseFileTransferData.PARAMETER_TIMESTAMP  + "=timestamp";

        strURLaddress += "&" + "OWNER_TYPE="  + ownerType;
        strURLaddress += "&" + "DOC_ID"  + "=" + Long.toString(docId);
        strURLaddress += "&" + "CHUNKENABLED"  + "=" + Boolean.toString(isCache);
        return strURLaddress;
    }

    /**
     * This method retrievesallLetterTemplates
     * @return List of LetterTemplate which one file is not null
     */
    public LetterTemplate retrieveAllLetterTemplateForNonZeroDocIds() {

        LetterTemplate lt = null;
        LetterTemplateList lTemplateList = _adminService.retrieveAllLetterTemplates();

        for (LetterTemplate l : lTemplateList.getLetterTemplates()) {

            if (l.getFile() != null) {
                lt = l;
            }
        }

        return  lt;
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }
}
