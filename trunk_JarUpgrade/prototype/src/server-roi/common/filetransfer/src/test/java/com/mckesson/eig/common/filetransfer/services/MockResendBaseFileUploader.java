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

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author N.Shah Ghazni
 * @date   May 9, 2008
 * @since  HECM 1.0; May 9, 2008
 */
public class MockResendBaseFileUploader extends BaseFileUploader {

    /**
     * @see com.mckesson.eig.common.filetransfer.services.BaseFileUploader#
     *      doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {

        BaseFileTransferData data = new BaseFileTransferData(request, response);
        putRemoteFile(data);
    }

    /**
     * @see com.mckesson.eig.common.filetransfer.services.BaseFileUploader#
     *      writeLocal(com.mckesson.eig.common.filetransfer.services.BaseFileTransferData, 
     *                 java.io.InputStream, java.lang.String, java.lang.String, int, boolean)
     */
    @Override
    protected int writeFileBuffered(BaseFileTransferData data,
                             InputStream inputStream,
                             String strDirPath,
                             String strFileName,
                             int dataLength,
                             boolean boolAppend) throws Exception {
        super.writeFileBuffered(data, inputStream, strDirPath, strFileName, dataLength, boolAppend);
        return RESEND;
    }

    /**
     * @see com.mckesson.eig.common.filetransfer.services.BaseFileUploader#
     *      isValidUser(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    protected boolean isValidUser(String user, String password, String timestamp) {
        return true;
    }
}
