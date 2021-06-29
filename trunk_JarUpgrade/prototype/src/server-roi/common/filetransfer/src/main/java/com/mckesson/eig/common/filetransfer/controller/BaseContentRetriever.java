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
package com.mckesson.eig.common.filetransfer.controller;

import com.mckesson.eig.common.filetransfer.services.BaseFileTransferData;

/**
 * @author eyy4ifv
 * @version 1.0
 * @created 09-Nov-2007 5:55:42 PM
 */
public abstract class BaseContentRetriever implements IValidUserCredentials, IContentRetriever {

	public BaseContentRetriever() {
	}

	/**
	 * find out if the username/password are valid
	 * 
	 * @param user    user id of the user
	 * @param password    password of the user
	 * @param ticket   the ticket to use (if valid) instead of the username/password
	 */
	public abstract boolean isValidUser(String user, String password, String ticket);

    /**
    * get the UNC path to the requested content
    *
    * @param data Provide access to request and request parameters
    */
    public abstract String retrieveContent(BaseFileTransferData data);
}
