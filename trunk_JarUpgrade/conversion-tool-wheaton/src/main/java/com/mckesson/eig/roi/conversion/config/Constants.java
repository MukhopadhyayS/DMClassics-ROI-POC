/*
 * Copyright 2012 McKesson Corporation and/or one of its subsidiaries. 
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
package com.mckesson.eig.roi.conversion.config;

/**
 * @author bhanu
 * 
 */
public final class Constants {

	private Constants() {
		// restrict instance creation
	}

	public static final String CONVERSION_PROPS_FILE = "config/conversion.properties";
	public static final String DB_SERVER_PROPERTY = "database.server";
	public static final String DB_USERNAME_PROPERTY = "database.username";
	public static final String DB_PASSWORD_PROPERTY = "database.password";
	public static final String NUM_THREADS_PROPERTY = "threads.num";
	public static final String BILLING_LOCATION_OPTION = "billing.location.option";
	public static final String DEFAULT_FACILITY_CODE_PROPERTY = "default.facilityCode";
	public static final String DEFAULT_FACILITY_NAME_PROPERTY = "default.facilityName";
	public static final String DEFAULT_INVOICE_DUE_DAYS_PROPERTY = "default.invoiceDueDays";
	public static final String DEFAULT_DATABASE_USERNAME_PROPERTY = "default.database.username";
	
	public static final Integer MAX_NUM_THREADS = 10;
	
	public static final String DB_USERNAME = "sa";

	public static final String STATUS_COMPLETED = "Completed";
	public static final String STATUS_ERROR = "Error";

	public static final String QUERY_ALL_SUPPLEMENTAL = "getSupplementalsToConvert";
	public static final String QUERY_ALL_REQUEST = "getRequestsToConvert";
	public static final String QUERY_ALL_REQUESTORLETTER = "getRequestorLettersToConvert";

	public static final String QUERY_TOTAL_FREEFORM_FACILITITES = "freeFormFacilititesToConvertCount";
	public static final String QUERY_TOTAL_SUPPLEMENTALS = "supplementalsToConvertCount";
	public static final String QUERY_TOTAL_REQUESTS = "requestsToConvertCount";
	public static final String QUERY_TOTAL_REQUESTORLETTERS = "requestorLettersToConvertCount";

	public static final String QUERY_STATUS_SUPPLEMENTAL = "supplementalStatusSaveOrUpdate";
	public static final String QUERY_STATUS_REQUEST = "requestStatusSaveOrUpdate";
	public static final String QUERY_STATUS_REQUESTORLETTER = "requestorLetterStatusSaveOrUpdate";
	public static final String QUERY_GET_FACILITY_INFO = "getFacilityInfoByCodes";
	public static final String QUERY_GET_CONFIGURED_BILLING_LOC = "getConfiguredBillingLocations";
	public static final String QUERY_ROI_PRECONVERSION_USER_MAPPING = "retrieve_ROI_PreConversion_UserToBillingLocation_Mapping";
	
	public static final String COMMA = ",";
	
	public static final String USER_BL_MAPPING_FILE_NAME = "user.bl.mapping.file.name";
	public static final String FACILITY_BL_MAPPING_FILENAME = "facility.bl.mapping.file.name";
	public static final Object QUOTES = "'";	
}
