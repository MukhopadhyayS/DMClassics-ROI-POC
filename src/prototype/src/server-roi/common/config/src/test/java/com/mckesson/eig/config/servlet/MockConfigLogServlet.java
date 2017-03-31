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

package com.mckesson.eig.config.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mckesson.eig.common.filetransfer.services.BaseFileTransferData;

/**
 * @author Sahul Hameed Y
 * @date   Apr 17, 2008
 * @since  HECM 1.0; Apr 17, 2008
 */
public class MockConfigLogServlet extends ConfigureLogServlet {

    /**
     * This method calls the BaseFileTransferData constructor to initialize the request
     * parameters and processes the request.
     *
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     *
     * @throws IOException
     *             IOException
     * @throws ServletException
     *             ServletException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        getRemoteFile(new BaseFileTransferData(request, response));
        getApplicationID(request);
        getCurrentUserID(request);
        getTransactionID(request);
        initFilePath();
    }
}
