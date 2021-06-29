/*
 * Copyright 2014 McKesson Corporation and/or one of its subsidiaries. 
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
package com.mckesson.eig.roi.docx.model;

/**
 * @author Eric Yu
 * 
 */
public final class Constants {

	private Constants() {
		// restrict instance creation
	}

	public static final String DB_SERVER_PROPERTY = "database.server";
	public static final String DB_USERNAME_PROPERTY = "database.username";
	public static final String DB_PASSWORD_PROPERTY = "database.password";
	public static final String DB_PORT_PROPERTY = "database.port";
	public static final String DB_DEFAULT_PORT_PROPERTY = "1433";
	public static final String DB_USERNAME = "sa";

	public static final String STATUS_COMPLETED = "Completed";
	public static final String STATUS_ERROR = "Error";

	public static final String QUERY_ALL_SUPPLEMENTAL = "getSupplementalsToConvert";
	public static final String QUERY_ALL_REQUEST = "getRequestsToConvert";
	public static final String QUERY_ALL_REQUESTORLETTER = "getRequestorLettersToConvert";

	public static final Object QUOTES = "'";	
}
