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
package com.mckesson.eig.roi.conversion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;
import org.hibernate.HibernateException;

import com.mckesson.eig.roi.conversion.billinglocation.BillingLocation;
import com.mckesson.eig.roi.conversion.billinglocation.BillingLocationBuilder;
import com.mckesson.eig.roi.conversion.billinglocation.BillingLocationUtil;
import com.mckesson.eig.roi.conversion.config.Configuration;
import com.mckesson.eig.roi.conversion.config.Constants;
import com.mckesson.eig.roi.conversion.exceptions.ConversionException;
import com.mckesson.eig.roi.conversion.exceptions.InitializationException;
import com.mckesson.eig.roi.conversion.exceptions.ValidationException;
import com.mckesson.eig.roi.conversion.processor.ConversionProcessor;
import com.mckesson.eig.roi.conversion.processor.ConversionStatus;
import com.mckesson.eig.roi.conversion.util.ConsoleUtil;
import com.mckesson.eig.roi.conversion.util.HibernateUtil;
import com.mckesson.eig.roi.conversion.util.StringUtil;

/**
 * @author bhanu
 * 
 */
public class Main {

	private static final Logger logger = Logger.getLogger(Main.class);
	private static final RollingFileAppender fileAppender = (RollingFileAppender) Logger.getRootLogger().getAppender("fileAppender");
	private static ConversionProcessor processor;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Main.initialize();
			Main.start();
			Main.destroy();
		} catch (Exception e) {
			Main.logger.error("Error in conversion tool", e);
			System.err.println("\nError occurred. Check log for more details. Restart Conversion tool after all errors have been fixed.");
			//try to destroy again
			Main.destroy();
		}
		System.out.print("Open conversion log file (Y/N)? ");
		String openLogFile = new java.util.Scanner(System.in).nextLine();
		if (openLogFile.equalsIgnoreCase("Y")) {
			ProcessBuilder pb = new ProcessBuilder("notepad.exe", new File(fileAppender.getFile()).getAbsolutePath());
			try {
				pb.start();
			} catch (IOException e) {
				System.err.println("Unable to open conversion log file");
				Main.logger.error("Unable to open conversion log file", e);
			}
		}
		Main.logger.debug("Exiting");
	}

	private static void initialize() throws InitializationException, ValidationException, 
		HibernateException, MalformedURLException, FileNotFoundException {
		Main.rollLogFile();
		try {
			Configuration.initialize();
			Configuration.validate();
		} catch (InitializationException e) {
			System.err.println(e.getMessage());
			throw e;
		} catch (ValidationException e) {
			System.err.println(e.getMessage());
			throw e;
		}
		
		String dbUsername = Configuration.getProperties().getProperty(Constants.DEFAULT_DATABASE_USERNAME_PROPERTY);
		if(StringUtil.isEmpty(dbUsername)) {
			dbUsername = Constants.DB_USERNAME;
		}
		String dbServer = ConsoleUtil.readLine("Enter Database server name: ");
		String dbPassword = ConsoleUtil.readPassword("Enter password for "+dbUsername+" user: ");
		Configuration.getProperties().setProperty(Constants.DB_SERVER_PROPERTY, dbServer);
		Configuration.getProperties().setProperty(Constants.DB_USERNAME_PROPERTY, dbUsername);
		Configuration.getProperties().setProperty(Constants.DB_PASSWORD_PROPERTY, dbPassword);
		
		try {
			System.out.println("Initializing database connection");
			HibernateUtil.initialize();
		} catch (HibernateException e) {
			System.out.println("Error initializing database connection");
			throw e;
		}
		
		ConversionProcessor.createStoredProceduresTemporary();
		BillingLocationBuilder builder = new BillingLocationBuilder();
		Map<String, BillingLocation> facilityMapper = null;
		Map<String, BillingLocation> userMapper = null;
		if(Configuration.isAdvancedBillingLocationOptions()) {
			System.out.print("\nBilling Location Configuration is advanced. ");
			System.out.print("\n2 mapping files are required to support this option.");
			try {
				userMapper = builder.getUserMapper();
			} catch (FileNotFoundException e) {
				System.err.println("\nError to Get User Billing Location Mapping File.");
				throw e;
			}
			try {
				facilityMapper = builder.getFacilityMapper();
			} catch (FileNotFoundException e) {
				System.err.println("\nError to Get Facility Billing Location Mapping File.");
				throw e;
			}
			processor = new ConversionProcessor(facilityMapper, userMapper);
			BillingLocationUtil.logHeader();
		} else {
			System.out.print("\nBilling Location Configuration is simple. ");
			Properties props = Configuration.getProperties();
			String defaultFacilityCode = props.getProperty(Constants.DEFAULT_FACILITY_CODE_PROPERTY);
			if(StringUtil.isEmpty(defaultFacilityCode)) {
				throw new ValidationException("default.facilityCode not set in file: " + new File(Constants.CONVERSION_PROPS_FILE).getAbsolutePath());
			}
			System.out.print("\nThe default Billing Location facility code is: " + defaultFacilityCode);
			
			String defaultFacilityName = props.getProperty(Constants.DEFAULT_FACILITY_NAME_PROPERTY);
			if(StringUtil.isEmpty(defaultFacilityName)) {
				throw new ValidationException("default.facilityName not set in file: " + new File(Constants.CONVERSION_PROPS_FILE).getAbsolutePath());
			}
			System.out.print("\nThe default Billing Location facility name is: " + defaultFacilityName);
			processor = new ConversionProcessor();
			boolean validBillingCode = processor.validateDefaultBillingFacilityCode(defaultFacilityName, defaultFacilityCode);
			if(!validBillingCode) {
				String error = "Invalid default facilityCode or facilityName in configuration under file: " + new File(Constants.CONVERSION_PROPS_FILE).getAbsolutePath() +". "
									+ defaultFacilityCode + " or " + defaultFacilityName + " do not match in cabinet..facility_file table.";
				System.err.println(error);
				throw new ValidationException(error);
			}
			Main.logger.info("Creating billing location if it does not exist");
			processor.createDefaultBillingFacilityCode(defaultFacilityCode);
			Main.logger.debug("Creating billing location if it does not exist - Success");
		}
		
		processor.initialize();
		
		System.out.println("\nNon-HPF Freeform Facilities to Convert: " + ConversionStatus.getInstance().getFreeformFacilitiesTotal());
		Main.logger.debug("Non-HPF Freeform Facilities to Convert: " + ConversionStatus.getInstance().getFreeformFacilitiesTotal());
		
		System.out.println("Non-HPF Patients to Convert: " + ConversionStatus.getInstance().getSupplementalsTotal());
		Main.logger.debug("Non-HPF Patients to Convert: " + ConversionStatus.getInstance().getSupplementalsTotal());
		
		System.out.println("Requests to Convert: " + ConversionStatus.getInstance().getRequestsTotal());
		Main.logger.debug("Requests to Convert: " + ConversionStatus.getInstance().getRequestsTotal());
		
//		System.out.println("Requestor Letters to Convert: " + ConversionStatus.getInstance().getRequestorLettersTotal());
//		Main.logger.debug("Requestor Letters to Convert: " + ConversionStatus.getInstance().getRequestorLettersTotal());
	}

	private static void start() throws ConversionException {
		
		System.out.print("\nEnter requestid to convert: A for all failed or unconverted requests? ");
		String requestIdString = new java.util.Scanner(System.in).nextLine();
		int requestId = 0;
		try {
			requestId = Integer.valueOf(requestIdString.trim());
		} catch (Exception e) {
			
		}
		if(requestId > 0) {
			processor.setRequestId(requestId);
			System.out.print("\nStart conversion for request " + requestId + " (Y/N)? ");
		} else {
			System.out.print("\nStart conversion all requests (Y/N)? ");
		}
		String startConversion = new java.util.Scanner(System.in).nextLine();
		if (!startConversion.equalsIgnoreCase("Y")) {
			return;
		}
		System.out.println("Starting conversion...");
		Thread conversionProcessorThread = new Thread(processor);
		conversionProcessorThread.start();

		// wait for conversion process to complete
		while (!ConversionStatus.getInstance().getCompleted()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(ConversionStatus.getInstance().getErrored()) {
			throw new ConversionException("Error in conversion-tool");
		} else {
			System.out.println("\nConversion completed");
		}
	}

	private static void destroy() {
		System.out.println("\nClosing database connection");
		if(processor != null) {
			processor.destroy();
		}
		HibernateUtil.destroy();
	}
	
	private static void rollLogFile() {
		System.out.println("\nRolling log file");
		File file = new File(fileAppender.getFile());
		if (file.length() > 0) {
			fileAppender.rollOver();
		}
		System.out.println("Conversion log file is at: " + file.getAbsolutePath());
	}

}
