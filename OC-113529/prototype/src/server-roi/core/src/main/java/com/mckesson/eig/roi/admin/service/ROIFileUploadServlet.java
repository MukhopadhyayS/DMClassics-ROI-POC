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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.BeanFactory;

import com.mckesson.eig.common.filetransfer.services.BaseFileTransferData;
import com.mckesson.eig.common.filetransfer.services.BaseFileUploader;
import com.mckesson.eig.roi.admin.dao.FileTransferHelper;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.utils.AccessFileLoader;
import com.mckesson.eig.roi.utils.DirectoryUtil;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;


/**
 * This class extents BaseFileUploader and contains methods to store the file in the database
 *
 * @author Vidhya.C.s
 * @date   May 29, 2008
 * @since  HPF 13.1 [ROI]; May 16, 2008
 */
public class ROIFileUploadServlet
extends BaseFileUploader {

    private static final Log LOG = LogFactory.getLogger(ROIFileUploadServlet.class);
    private static final String FILE_NAME = "FILE_NAME";

    @Override
    public void putRemoteFile(BaseFileTransferData data) throws IOException {

        final String logSM = "putRemoteFile(data)";
        LOG.debug(logSM + ">>Start");

        try {

            String fileName = data.getRequestProperty(FILE_NAME);

            FileTransferHelper fHelper = getFileTransferHelper(data);

            if ((fileName == null) || !fHelper.isValidFileName(fileName)) {

                sendFailReturn(data,
                               ROIClientErrorCodes.LETTER_TEMPLATE_INVALID_FILE_FORMAT.toString());
            }

            if (!data.getBooleanRequestProperty(BaseFileTransferData.PARAMETER_CHUNKENABLED)) {

                completeUpload(data, null);
            } else {
                super.putRemoteFile(data);
            }
        } catch (Throwable e) {
            sendFailReturn(data,
                           ROIClientErrorCodes.LETTER_TEMPLATE_INVALID_FILE_FORMAT.toString());
        }
        LOG.debug(logSM + "<<End");
    }

    @Override
    public void completeUpload(BaseFileTransferData data,
                               String cacheFile) {

        final String logSM = "completeUpload(data, cacheFile, responseXML)";
        LOG.debug(logSM + ">>Start");

        InputStream in = null;
        int available = 0;
        try {
            if (!data.getBooleanRequestProperty(BaseFileTransferData.PARAMETER_CHUNKENABLED)) {

                in = data.getRequest().getInputStream();
                available = data.getRequest().getContentLength();
            } else {

                in = AccessFileLoader.getFileInputStream(AccessFileLoader.getFile(DirectoryUtil.getCacheDirectory()
                                                   + File.separator
                                                   + cacheFile));
                available = in.available();
            }

            FileTransferHelper fHelper = getFileTransferHelper(data);
            if (available > 0) {
                fHelper.completeUpload(data, in, available, null);
            } else {
                sendFailReturn(data, "Invalid content length");
            }

        } catch (Throwable e) {

            sendFailReturn(data, e.getMessage());
        } finally {

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOG.error("Closing the stream in method " + logSM + " failed", e);
                }
            }
        }

        LOG.debug(logSM + "<<End");
    }

    @Override
    public String getFileName() {
         return "ROI_" + System.nanoTime() + ".cache";
    }

    /**
     * This method retrieves the FileTransferHelper class from the spring configuration
     * @param servletData
     * @return fHelper
     */
    private FileTransferHelper getFileTransferHelper(BaseFileTransferData servletData) {

        final String logSM = "getFileTransferHelper(servletData)";
        LOG.debug(logSM + ">>Start");

        String ownerType = servletData.getRequestProperty("OWNER_TYPE");
        String fileTransferhelper = ownerType + "_FTHelper";

        BeanFactory beanFactory = SpringUtilities.getInstance().getBeanFactory();
        FileTransferHelper fHelper = (FileTransferHelper) beanFactory.getBean(fileTransferhelper);

        LOG.debug(logSM + "<<End " + fHelper);
        return fHelper;
    }
        }
