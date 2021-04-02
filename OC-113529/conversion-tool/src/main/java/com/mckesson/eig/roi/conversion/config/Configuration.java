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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mckesson.eig.roi.conversion.exceptions.InitializationException;
import com.mckesson.eig.roi.conversion.exceptions.ValidationException;

/**
 * @author bhanu
 *
 */
public class Configuration {

	private static final Logger logger = Logger.getLogger(Configuration.class);
	private static Properties props;
	
	/**
	 * 
	 */
	public static void initialize() throws InitializationException, ValidationException, MalformedURLException {
		Configuration.logger.debug("Reading configuration - start");
		String configFilePath = getResourceAsUrl(Constants.CONVERSION_PROPS_FILE).getPath();
		File propsFile = new File(configFilePath);
		System.out.println("Reading configuration from: " + propsFile.getAbsolutePath());
		props = new Properties();
		try {
			props.load(new FileInputStream(propsFile));
		} catch (FileNotFoundException e) {
			Configuration.logger.error("Conversion properties file: " + Constants.CONVERSION_PROPS_FILE + " missing.", e);
			throw new InitializationException("File: "+propsFile.getAbsolutePath()+" not found", e);
		} catch (IOException e) {
			Configuration.logger.error("Unable to read conversion properties file: " + Constants.CONVERSION_PROPS_FILE, e);
			throw new InitializationException("Unable to read file: "+propsFile.getAbsolutePath(), e);
		}
		Configuration.logger.debug("Reading configuration - end");
	}


	public static URL getResourceAsUrl(String path) {
		return ClassLoader.getSystemResource(path);
	}


	/**
	 * 
	 */
	@SuppressWarnings("unused")
	public static void validate() throws ValidationException{
		String numThreadsStr = props.getProperty(Constants.NUM_THREADS_PROPERTY);
		int numThreads;
		try {
			numThreads = Integer.parseInt(numThreadsStr);
		} catch(NumberFormatException e) {
			throw new ValidationException("invalid threads.num configuration in file: " + new File(Constants.CONVERSION_PROPS_FILE).getAbsolutePath(), e);
		}
		if(numThreads > Constants.MAX_NUM_THREADS) {
			throw new ValidationException("threads.num configuration cannot be greater than "+ Constants.MAX_NUM_THREADS);
		}
		
		String defaultInvoiceDueDays = props.getProperty(Constants.DEFAULT_INVOICE_DUE_DAYS_PROPERTY);
		try {
			int numDefaultInvoiceDueDays = Integer.parseInt(defaultInvoiceDueDays);
		} catch(NumberFormatException e) {
			throw new ValidationException("invalid default.invoiceDueDays configuration in file: " + new File(Constants.CONVERSION_PROPS_FILE).getAbsolutePath(), e);
		}
	}
	
	public static File getUserMapper() throws FileNotFoundException {
		String f = props.getProperty(Constants.USER_BL_MAPPING_FILE_NAME);
		try {
			File file = new File(getResourceAsUrl(f).getFile());
			return file;
		} catch (Exception e) {
			Configuration.logger.error("User Mapping file: " + f + " missing.", e);
			throw new FileNotFoundException("File not exist:" + f);
		}
	}
	
	public static File getFacilityMapper() throws FileNotFoundException  {
		String f = props.getProperty(Constants.FACILITY_BL_MAPPING_FILENAME);
		try {
			File file = new File(getResourceAsUrl(f).getFile());
			return file;
		} catch (Exception e) {
			Configuration.logger.error("Facility Mapping file: " + f + " missing.", e);
			throw new FileNotFoundException("File not exist:" + f);
		}
	}
	
	public static boolean isAdvancedBillingLocationOptions() {
		String f = props.getProperty(Constants.BILLING_LOCATION_OPTION);
		if((f == null) || (!f.toLowerCase().equals("advanced"))) {
			return false;
		}
		return true;
	}
	
	/**
	 * @return
	 */
	public static Properties getProperties() {
		return props;
	}
	
	/**
	 * Get the property value for the key provided.
	 * @param key
	 * @return
	 */
	public static String getProperty (String key) {
		return props.getProperty(key);
	}
	
	/**
	 * Setting the property.
	 * @param key
	 * @param value
	 */
	public static void setProperty(String key, String value) {
		props.setProperty(key, value);
	}
}
