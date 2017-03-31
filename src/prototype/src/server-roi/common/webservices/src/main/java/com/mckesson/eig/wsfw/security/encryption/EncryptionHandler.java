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

package com.mckesson.eig.wsfw.security.encryption;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * @author ec7opip
 *
 */
public class EncryptionHandler {
	
	private static PasswordEncryptionStrategy _strategy = null;
	private static EncryptionHandler _instance = null;
	
    private static final Log LOG = LogFactory.getLogger(EncryptionHandler.class);
	
    protected EncryptionHandler() {    	
    }
    
    public static EncryptionHandler getInstance() {
    	if (_instance == null) {
    		_instance = new EncryptionHandler();
    		_instance.findStrategy();
    	}
    	return _instance;
    }
    
	protected void findStrategy() {
		try {
			PasswordEncryptionStrategy strategy = 
				(PasswordEncryptionStrategy) SpringUtilities
					.getInstance().getBeanFactory().getBean(
							"PasswordEncryptionStrategy");
			setStrategy(strategy);
		} catch (Exception ex) {
			throw new ApplicationException(
					"Unable to find PasswordEncryptionStrategy definition", ex);
		}
	}

	/**
	 * @return the _strategy
	 */
	public PasswordEncryptionStrategy getStrategy() {
		return _strategy;
	}
	
	public boolean isUnencryptedStrategy() {
		return (_strategy instanceof PassthroughStrategy);
	}

	/**
	 * @param _strategy the _strategy to set
	 */
	public void setStrategy(PasswordEncryptionStrategy strategy) {
		_strategy = strategy;
	}
	
	public String encryptText(String userName, String text, String timestamp) {
		return getStrategy().encryptPassword(userName, text, timestamp);
	}

	public String decryptText(String userName, String text, String timestamp) {
		return getStrategy().decryptPassword(userName, text, timestamp);
	}

}
