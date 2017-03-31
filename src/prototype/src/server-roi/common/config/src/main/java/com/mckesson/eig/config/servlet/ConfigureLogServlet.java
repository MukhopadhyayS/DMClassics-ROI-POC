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

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.mckesson.eig.common.filetransfer.services.BaseFileTransferData;
import com.mckesson.eig.config.constants.ConfigurationConstants;
import com.mckesson.eig.config.service.ConfigureLogService;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.LongUtilities;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author Sahul Hameed Y
 * @date   Feb 11, 2008
 * @since  HECM 1.0; Feb 11, 2008
 */
public class ConfigureLogServlet
extends com.mckesson.eig.reports.servlet.ReportServlet {
    
    /**
     * Parameter key for transaction id which will be obtained from the client.
     */
    private static final String KEY_TRANS_ID = "transId";

    /**
     * Parameter key for application id which will be obtained from the client.
     */
    private static final String KEY_APP_ID = "AppId";

    /**
     * Parameter key for logged in user id which will be obtained from the client.
     */
    private static final String KEY_LOGGEDIN_ID = "UserName";
    
    /**
     * Object represents the Log4JWrapper object.
     */
    private static final Log LOG = LogFactory.getLogger(ConfigureLogServlet.class);

    /**
     * @see com.mckesson.eig.reports.servlet.ReportServlet#initConfig()
     */
    @Override
    protected void initConfig() {
    }
    
    /**
     * This method chunks the data and sends it back to the requester.
     */
    @Override
    public void getRemoteFile(BaseFileTransferData servData)
    throws IOException {

        final String logSourceMethod = "getRemoteFile(servData)";
        LOG.debug(logSourceMethod + ">>Start");

        HttpServletRequest req = servData.getRequest();
        validateParams(req);
        String filePath = getFileName(req);

        //Read the file if it contains data, else don't read the file at all.
        if (new File(filePath).length() > 0) {
            writeToOutputStream(servData, filePath);
        }
        LOG.debug(logSourceMethod + "<<End");
    }
    
    /**
     * This method reads the fileName and componentSeq from the request and get the filePath 
     * for the corresponding fileName.
     *
     * @param request
     *          Servlet Request.
     *
     * @return complete file path.
     * 
     * @throws IOException
     */
    @Override    
    public String getFileName(HttpServletRequest req) 
    throws IOException {
        
        String fileName        = req.getParameter(ConfigurationConstants.FILE_NAME);
        long componentSequence =    
                    Long.parseLong(req.getParameter(ConfigurationConstants.COMPONENT_SEQ));
        return getConfigureService().getLogFilePath(componentSequence, fileName);
    }

    /**
     * @see com.mckesson.eig.reports.servlet.ReportServlet
     * #getApplicationID(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected String getApplicationID(HttpServletRequest req) {
        return req.getParameter(KEY_APP_ID);
    }

    /**
     * @see com.mckesson.eig.reports.servlet.ReportServlet
     * #getCurrentUserID(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected String getCurrentUserID(HttpServletRequest req) {
        return req.getParameter(KEY_LOGGEDIN_ID);
    }

    /**
     * @see com.mckesson.eig.reports.servlet.ReportServlet
     * #getTransactionID(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected String getTransactionID(HttpServletRequest req) {
        return req.getParameter(KEY_TRANS_ID);
    }

    /**
     * @see com.mckesson.eig.reports.servlet.ReportServlet#initFilePath()
     */
    @Override
    protected String initFilePath() {
        return StringUtilities.EMPTYSTRING;
    }
    
    /**
     * Validate the file name, component sequence, offset and block size from the request
     * 
     * @param req
     * @throws IOException
     */
    private void validateParams(HttpServletRequest req) 
    throws IOException {
      
        if (StringUtilities.isEmpty(req.getParameter(ConfigurationConstants.FILE_NAME))) {
            throw new IOException(ConfigurationConstants.INVALID_FILE_NAME);
        } else if (StringUtilities.isEmpty(req.getParameter(ConfigurationConstants.OFFSET))) {
            throw new IOException(ConfigurationConstants.INVALID_OFFSET);
        } else if (StringUtilities.isEmpty(req.getParameter(ConfigurationConstants.BLOCK_SIZE))) {
            throw new IOException(ConfigurationConstants.INVALID_BLOCK_SIZE);
        } else if (!LongUtilities.isValidPositiveLong(
                    req.getParameter(ConfigurationConstants.COMPONENT_SEQ))) {
            throw new IOException(ConfigurationConstants.INVALID_COMPONENT_SEQ);
        }
    }
    
    /**
     * Get the ListViewLog instance.
     * @return listViewLog
     */
    private ConfigureLogService getConfigureService() {
        
        return (ConfigureLogService) SpringUtilities.getInstance()
                                            .getBeanFactory()
                                            .getBean(ConfigureLogService.class.getName());
    }
}

