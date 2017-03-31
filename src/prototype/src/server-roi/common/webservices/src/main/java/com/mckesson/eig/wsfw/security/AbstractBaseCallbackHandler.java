/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
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

package com.mckesson.eig.wsfw.security;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.metric.TimedMetric;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.exception.UsernameTokenException;
import com.mckesson.eig.wsfw.security.encryption.EncryptionHandler;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * This is an abstract base class for the security callback handler classes that
 * are called by Axis and WSS4J to allow us to validate a username & password
 * that are passed in the SOAP Header according to the WS-Security standard.
 */
public abstract class AbstractBaseCallbackHandler implements CallbackHandler {

    /**
     * Gets the logger for this class.
     */
    private static final Log LOG = LogFactory
            .getLogger(AbstractBaseCallbackHandler.class);

    /**
     * <p>
     * Retrieve the information requested in the provided Callbacks.
     * 
     * <p>
     * The <code>handle</code> method implementation checks the instance(s) of
     * the <code>Callback</code> object(s) passed in to retrieve the requested
     * information.
     * 
     * @param callbacks
     *            an array of <code>Callback</code> objects provided by an
     *            underlying security service which contains the information
     *            requested to be retrieved or displayed.
     * 
     * @throws IOException
     *             if an IO error occurs.
     * @throws UnsupportedCallbackException
     *             if callback is unreognized.
     * 
     * @see javax.security.auth.callback.CallbackHandler#handle(javax.
     *      security.auth.callback.Callback[])
     */
    public void handle(Callback[] callbacks) throws IOException,
            UnsupportedCallbackException {

        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof WSPasswordCallback) {
                TimedMetric tm = TimedMetric.start();
                handlePasswords((WSPasswordCallback) callbacks[i]);
                tm.logMetric("Callback Handler Invocation: " + this.getClass().getName());
            } else {
                throw new UnsupportedCallbackException(callbacks[i],
                        "Unrecognized Callback");
            }
        }
    }

    /**
     * This method is called to do the processing of WSPasswordCallback object.
     * 
     * @param pc
     *            WSPasswordCallback.
     */
    protected void handlePasswords(WSPasswordCallback pc) {
        String userId = pc.getIdentifer();
        String password = pc.getPassword();

        validateHeader(userId, password);
        String decryptedPassword = validateEncryption(userId, password);
        processCallback(userId, decryptedPassword);
    }

    /**
     * The base WSE Security header value validation. Should be over written in
     * extended classes if the standard validation isn't the desired
     * functionality.
     * 
     * @param userId
     *            String
     * @param password
     *            String
     */
    protected void validateHeader(String userName, String password) {
        LOG.debug("Validating the WSE Security Header");
        if (StringUtilities.isEmpty(userName)
                || StringUtilities.isEmpty(password)) {
            throw new UsernameTokenException(
                    "Username and/or password is NULL!!",
                    ClientErrorCodes.SECURITY_TOKEN_MISSING_INFORMATION);
        }
    }
    
    protected String validateEncryption(String userName, String password) {
        String decryptedPassword = null;
        try {
            decryptedPassword = validatePasswordAsConfigured(userName, password);
        } catch (Exception ex) {
            LOG.debug("Unable to decrypt with configured PasswordEncryptionStrategy context, "
                    + "trying clear text. " + ex.getMessage());
            decryptedPassword = validatePasswordAsClear(userName, password);
        }

        return decryptedPassword;
    }

    protected String validatePasswordAsConfigured(String userName,
            String password) {
        String decryptedPassword = null;
        EncryptionHandler handler = EncryptionHandler.getInstance();
        decryptedPassword = handler.decryptText(userName, password,
                (String) WsSession.getSessionData(WsSession.MESSAGE_TIMESTAMP));

        return decryptedPassword;
    }

    protected String validatePasswordAsClear(String userName, String password) {
        return password;
    }

    /**
     * Abstract method used for the extend classes to add there callback
     * functionality.
     * 
     * @param userId
     *            String
     * @param password
     *            String
     */
    protected abstract void processCallback(String userName, String password);
}
