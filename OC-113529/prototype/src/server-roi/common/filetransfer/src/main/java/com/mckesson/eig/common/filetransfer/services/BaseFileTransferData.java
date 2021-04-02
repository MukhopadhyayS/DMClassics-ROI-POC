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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.NumberUtilities;
import com.mckesson.eig.wsfw.security.encryption.EncryptionHandler;
import com.mckesson.eig.wsfw.security.encryption.PasswordEncryptionStrategy;
import com.mckesson.eig.wsfw.session.WsSession;

public class BaseFileTransferData {
    public static final String PARAMETER_USER = "USER";
    public static final String PARAMETER_USERNAME = "USERNAME";
    public static final String PARAMETER_PD = "PASSWORD";
    public static final String PARAMETER_FILE_ID = "FILE_ID";
    public static final String PARAMETER_TRANSACTION_ID = "TRANSACTION_ID";
    public static final String PARAMETER_REVISION = "REVISION";
    public static final String PARAMETER_BLOCKSIZE = "BLOCKSIZE";
    public static final String PARAMETER_OFFSET = "OFFSET";
    public static final String PARAMETER_TICKET = "TICKET";
    public static final String PARAMETER_TIMESTAMP = "TIMESTAMP";
    public static final String PARAMETER_CHECKIN_ID = "checkin_id";
    public static final String PARAMETER_FILENAME = "fileName";
    public static final String PARAMETER_CHUNKENABLED = "CHUNKENABLED";
    public static final String PARAMETER_FINALCHUNK = "FINALCHUNK";
    public static final String PARAMETER_MODE = "MODE";

    public static final String MODE_CANCEL = "CANCEL";

    private HttpServletRequest _request = null;
    private HttpServletResponse _response = null;
    private String _pd = null;

    private static final Log LOG = LogFactory.getLogger(BaseFileTransferData.class);

    public BaseFileTransferData(HttpServletRequest request, HttpServletResponse response) {
        _request = request;
        _response = response;
        _pd = evaluatePd();
        WsSession.initializeSession(request.getSession(true));
    }

    public String getUserName() {
        String userName = _request.getParameter(PARAMETER_USER);
        if (userName == null) {
            userName = _request.getParameter(PARAMETER_USERNAME);
        }
        return userName;
    }

    public String getPd() {
        return _pd;
    }

    public String getCheckInID() {
        return (_request.getParameter(PARAMETER_CHECKIN_ID));
    }

    public String getFileName() {
        return (_request.getParameter(PARAMETER_FILENAME));
    }

    public String getTicket() {
        // Randy Rice: cheap fix until Release Plan story # 2437 is done.
        // The incoming ticket string may have '+' as a character, snd
        // the http request processing will change it to a ' '.  We
        // can fix that. CR 260513
        String ticket = _request.getParameter(PARAMETER_TICKET);
        return switchSpaceToPlus(ticket);
    }

    public String getFileID() {
        return (_request.getParameter(PARAMETER_FILE_ID));
    }

    public String getRevision() {
        return (_request.getParameter(PARAMETER_REVISION));
    }
    
    public String getTransactionID() {
        return (_request.getParameter(PARAMETER_TRANSACTION_ID));
    }

    public int getBlockSize() {
        return (NumberUtilities.parse(_request
                .getParameter(PARAMETER_BLOCKSIZE), 0));
    }

    public int getOffset() {
        return (NumberUtilities.parse(_request.getParameter(PARAMETER_OFFSET),
                0));
    }

    public String getTimestamp() {
        return getRequest().getParameter(PARAMETER_TIMESTAMP);
    }

    public HttpServletResponse getResponse() {
        return _response;
    }

    public HttpServletRequest getRequest() {
        return _request;
    }

    public String getRequestProperty(String paramKey) {
        return _request.getParameter(paramKey);
    }

    public boolean getBooleanRequestProperty(String paramKey) {
        return Boolean.valueOf(_request.getParameter(paramKey));
    }

    public long getLongRequestProperty(String paramKey) {
        return Long.valueOf(_request.getParameter(paramKey));
    }
    
    protected String switchSpaceToPlus(String incomingParameter) {
		String switchedParameter = null;
		if (incomingParameter != null) {
			switchedParameter = incomingParameter.replace(' ', '+');
		}
		return switchedParameter;
	}

    protected String evaluatePd() {
    	EncryptionHandler handler = EncryptionHandler.getInstance();  
    	String adjustedPd = null;
    	String incomingPassword = _request.getParameter(PARAMETER_PD);

    	// don't try to decrypt unencrypted password
    	if (handler.isUnencryptedStrategy()) {
    		adjustedPd = incomingPassword;
    	} else {
    		// do break encrypted password into its parts
            String passwordPart = 
            	incomingPassword.substring(PasswordEncryptionStrategy.UTZ_FORMAT_LENGTH);
            passwordPart = switchSpaceToPlus(passwordPart);
            String timePart = 
            	incomingPassword.substring(0, PasswordEncryptionStrategy.UTZ_FORMAT_LENGTH);
            adjustedPd = timePart + passwordPart;    		
    	}
        String outgoingPassword = null;
        try {
            String userName = getUserName();
            String timeStamp = getTimestamp();
            outgoingPassword = handler.decryptText(userName, adjustedPd,
                    timeStamp);
        } catch (Exception ex) {
            LOG.info("Unable to decrypt password using defined PasswordEncryptionStrategy : "
                    + ex.getLocalizedMessage());
            outgoingPassword = incomingPassword;
        }
        return outgoingPassword;
    }

}
