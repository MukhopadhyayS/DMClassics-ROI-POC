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
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.BeanFactory;

import com.mckesson.eig.common.filetransfer.controller.BaseContentRetriever;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * base class for services layer file downloading
 *
 * @author eyy4ifv
 * @version 1.0
 * @created 09-Nov-2007 5:55:42 PM
 */
public class BaseFileDownloader extends HttpServlet implements IDownloadContent {

    private static final String CONTENT_TYPE = "application/octet-stream";
    private static final Log LOG = LogFactory.getLogger(BaseFileDownloader.class);

    private String _retrieverKey;

    public BaseFileDownloader() {
        _retrieverKey = getClass().getName();
    }

    public BaseFileDownloader(String retrieverKey) {
        _retrieverKey = retrieverKey;
    }

    protected String getRetrieverKey(BaseFileTransferData data) {
        return _retrieverKey + "_Retriever";
    }

    /**
     * This method calls the BaseFileDownloaderData constructor to initialize the request
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

        if (request.getParameterMap().size() > 0) {
            getRemoteFile(new BaseFileTransferData(request, response));
        } else {
            LOG.error("Invalid parameters passed to BaseFileDownloader servlet");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
        }
    }

    /**
     * Calls doGet method if the request is of post type.
     *
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
     *                                            javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        doGet(request, response);
    }

    protected BaseContentRetriever getRetriever(BaseFileTransferData data) {
        BeanFactory beanFactory = SpringUtilities.getInstance().getBeanFactory();
        String key = getRetrieverKey(data);
        BaseContentRetriever retriever = (BaseContentRetriever) beanFactory.getBean(key);
        return retriever;
    }

    /**
     * send the file to the requester
     *
     * @param servData Provide access to request and request parameters
     */
    public void getRemoteFile(BaseFileTransferData servData) throws IOException {

        LOG.debug("enter getRemoteFile");

        try {

            BaseContentRetriever retriever = getRetriever(servData);

            if (retriever.isValidUser(servData.getUserName(),
                                      servData.getPd(),
                                      servData.getTicket())) {

                String filePath = retriever.retrieveContent(servData);

                if (StringUtilities.hasContent(filePath)) {
                    writeToOutputStream(servData, filePath);
                }
            } else {
                LOG.error("Invalid credentials passed to BaseFileDownloader servlet");
                servData.getResponse().sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                                  "Invalid credentials");
            }

        } catch (Exception ex) {
            LOG.error("Error processing BaseFileDownloader request : " + ex.getMessage(), ex);
            servData.getResponse().sendError(
                    HttpServletResponse.SC_BAD_REQUEST);
        }

        LOG.debug("exit getRemoteFile");
    }

    public boolean writeToOutputStream(BaseFileTransferData servData, String filePath)
    throws IOException {

        boolean isCompleted = false;
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
			//DE7315 External Control of File Name or Path 
            File theFile = AccessFileLoader.getFile(filePath);

            if (!theFile.exists()) {
                servData.getResponse().sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "A file does not exist for file id="
                                + servData.getFileID() + " revision="
                                + servData.getRevision());
                return isCompleted;
            }

            long theContentSize = theFile.length() - servData.getOffset();
            if (theContentSize < 0) {
                servData.getResponse().sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid offset parameter. The offset is larger than the file size "
                                + "for file id=" + servData.getFileID()
                                + " revision=" + servData.getRevision());
                return isCompleted;
            }

            // Init servlet response.
            servData.getResponse().reset();
            //In order to show status bar loading process, ROI requires total file size
            servData.getResponse().setHeader("FILE_SIZE", theFile.length() + "");
            long theReadLength = 0;
            if (theContentSize > servData.getBlockSize()) {
                theReadLength = servData.getBlockSize();
                servData.getResponse().setStatus(
                        HttpServletResponse.SC_PARTIAL_CONTENT);
            } else {
                isCompleted = true;
                theReadLength = theContentSize;
                servData.getResponse().setStatus(HttpServletResponse.SC_OK);
            }

            servData.getResponse().setContentLength(
                    Integer.valueOf((int) theReadLength));
            servData.getResponse().setContentType(CONTENT_TYPE);

            // Open input stream for the file.
            input = new BufferedInputStream(AccessFileLoader.getFileInputStream(theFile));
            output = new BufferedOutputStream(servData.getResponse().getOutputStream());

            int maxRead = servData.getBlockSize();
            byte[] readBytes = new byte[maxRead];

            input.skip(servData.getOffset());

            int cur = 0;
            int result = 0;

            for (cur = 0, result = input.read();
                (result != -1) && (cur < maxRead);
                result = input.read()) {
                readBytes[cur++] = (byte) result;
            }

            output.write(readBytes);

            // Finalize task.
            output.flush();
            return isCompleted;
        } catch (IOException e) {
            // Init servlet response.
            servData.getResponse().setStatus(Integer.parseInt(
            		ESAPI.encoder().encodeForHTML(String.valueOf(HttpServletResponse.SC_BAD_REQUEST))));
            servData.getResponse().sendError(HttpServletResponse.SC_BAD_REQUEST,
                    ESAPI.encoder().encodeForHTML("An I/O error occurred for the provided file id= " + servData.getFileID()
                            + " revision=" + servData.getRevision() + " " + e.getMessage()));
            return false;
        } finally {
            // Gently close streams.
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
        }
    }
}
