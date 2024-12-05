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
package com.mckesson.eig.roi.preconversion;

import java.io.File;
import java.net.MalformedURLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mckesson.eig.roi.conversion.config.Configuration;
import com.mckesson.eig.roi.conversion.config.Constants;
import com.mckesson.eig.roi.conversion.exceptions.InitializationException;
import com.mckesson.eig.roi.conversion.exceptions.PreConversionException;
import com.mckesson.eig.roi.conversion.exceptions.ValidationException;
import com.mckesson.eig.roi.conversion.util.ConsoleUtil;
import com.mckesson.eig.roi.conversion.util.HibernateUtil;
import com.mckesson.eig.roi.conversion.util.StringUtil;

/**
 * @author OFS
 * @date   May 14, 2014
 * @since  HPF 16.0 [ROI]; May 14, 2014
 */
public class PreConversionMain {
	
	private static final Logger LOGGER = LogManager.getLogger(PreConversionMain.class);
	private static final String CMD_ARGS_FOR_USER_BL_MAPPING = "blfm";
	private static final String CMD_ARGS_FOR_FACILITY_BL_MAPPING = "fblm";
	
	
	private PreConversionProcessor processor;
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		new PreConversionMain().preConvert(args);		
	}

	private void preConvert(String[] args) {
		try {
			
			initialize();

			//Create temporary stored procedure
			getPreConversionProcessor().createTemporaryStoredProcedure();
			
			logConsole("Verifying the Billing location configuration...");
			if (0 == getSalestaxFacilities()) {
				String msg = "Billing location is not configured.. Please contact" 
									+ " System Administrator... Configure and rerun it...";
				logConsole(msg);
				throw new ValidationException(msg);
			}
			logConsole("Billing location is configured...");
			
			
			
			generateMappingTxtFiles(args);			
			logConsole("Pre Conversion of Mapping files is generated successfully.");
			LOGGER.debug("Pre Conversion of Mapping files is generated successfully.");
			
			// Drop temporary stored procedures
			getPreConversionProcessor().dropTemporaryStoredProcedure();
			
			destroy();
			
		} catch(Exception e) {
			LOGGER.error("Error while creating the mapping text files", e);
			logConsole("Error occurred. Check PreConversion log for more details.");
			destroy();
		}
	}
	
	
	private void generateMappingTxtFiles(String[] args) {
		
		try { 
			if (StringUtil.isEmpty(args)) {
				
				getPreConversionProcessor().createUserToBillinglocationMappingTxtFile();
				getPreConversionProcessor().createFacilityToBillinglocationMappingTxtFile(); 	
				
    		} else if (CMD_ARGS_FOR_USER_BL_MAPPING.equalsIgnoreCase(args[0])) {  
    			
    			getPreConversionProcessor().createUserToBillinglocationMappingTxtFile();  
    			
    		} else if (CMD_ARGS_FOR_FACILITY_BL_MAPPING.equalsIgnoreCase(args[0])) {
    			
    			getPreConversionProcessor().createFacilityToBillinglocationMappingTxtFile(); 
			}
		} catch (Exception e) {
			
		}
	}

	/*
	 * Initialize the LogFile , Database configurations
	 */
	private void initialize() throws InitializationException,
			ValidationException, HibernateException, MalformedURLException {
		
		// Log file initialization
		
		// Initialize and load the configuration, conversion.properties
		try {
			Configuration.initialize();
			//Configuration.validate();
		} catch (InitializationException e) {
			System.err.println(e);
			throw e;
		} catch (ValidationException e) {
			System.err.println(e);
			throw e;
		}
		
		// Fetch the Database credentials from user.
		String dbUsername = Configuration.getProperty(Constants.DEFAULT_DATABASE_USERNAME_PROPERTY);
		if(StringUtil.isEmpty(dbUsername)) {
			dbUsername = Constants.DB_USERNAME;
		}
		String dbServer = ConsoleUtil.readLine("Enter Database server name: ");
		String dbPassword = ConsoleUtil.readPassword("Enter password for "+dbUsername+" user: ");
		Configuration.setProperty(Constants.DB_SERVER_PROPERTY, dbServer);
		Configuration.setProperty(Constants.DB_USERNAME_PROPERTY, dbUsername);
		Configuration.setProperty(Constants.DB_PASSWORD_PROPERTY, dbPassword);
		
		// Database initialization
		try {
			logConsole("Initializing database connection");
			HibernateUtil.initialize();
		} catch (HibernateException e) {
			logConsole("Error initializing database connection");
			throw e;
		}	
		
		
	}
	
	/*
	 * This method will retrieve the facilities which maps to salestax is greater than zero
	 */
	private static Integer getSalestaxFacilities() throws PreConversionException {	
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		String queryString = session.getNamedQuery(
				"retrieveSalestaxBillingLocation").getQueryString();
		
		LOGGER.debug("Verifying the Billing location configuration...");
		Integer salestaxFacilityCount;
		try {
			Query query = session.createSQLQuery(queryString);
			salestaxFacilityCount = (Integer) query.uniqueResult();
			tx.commit();
		} catch (HibernateException e) {
			
			throw new PreConversionException("Unable to retrieve the Billing locations : ", e);
		}
		
		LOGGER.debug("Billing locations are retrieved successfully.");
		return salestaxFacilityCount;
	}
	
	
	private PreConversionProcessor getPreConversionProcessor() {
		if (null == processor) {
			processor = new PreConversionProcessor();
		}
		
		return processor;
	}
	
	private static void destroy() {
		LOGGER.debug("Closing database connection");
		HibernateUtil.destroy();
	}
	
	/*
	 * This method will show the log message in console.
	 */
	private void logConsole(String message) {
		// SOP is for logging to console
		System.out.println(message);
		
		// to log the message to log file.
		LOGGER.info(message);		
	}
}
