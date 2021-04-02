/*
 * Copyright 2008-2009 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.api;

import com.mckesson.eig.common.filetransfer.client.FileUploadHandler;


/**
 * @author Sree Sabesh Rajkumar
 * @date Aug 24, 2009
 * @since WSClient 1.0
 */
public class APIFileUploadHandler {

	private String _userName;
	private String _password;
	private String _serverURL;
	private FileUploadHandler _uploadHandler;
    
	public APIFileUploadHandler(String userName, String password, String serverURL) {
	    
	    this._userName  = userName;
	    this._password  = password;
	    this._serverURL = serverURL;
	    _uploadHandler = new FileUploadHandler();
	}

	/**
	 * Method used to cancel the file upload.
	 */
	public void cancelFileUpload() {
	    _uploadHandler.cancelFileUpload();
	}

	/**
	 * method to create url string
	 * @return url string
	 */
	public String createParameters() {
	    return _uploadHandler.createParameters(_userName, _password, _serverURL);
	}

	/**
	 * Method to upload file.
	 * @param filePath
	 * @param downloadURL
	 * @return fileID
	 * @throws Exception
	 */
	public String fileUpload(String filePath, String downloadURL) throws Exception {
		return _uploadHandler.fileUpload(filePath, downloadURL);
	}
}
