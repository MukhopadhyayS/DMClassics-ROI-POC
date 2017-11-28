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
package com.mckesson.eig.roi.docx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;
import org.hibernate.HibernateException;

import com.mckesson.eig.roi.docx.dao.ConversionDao;
import com.mckesson.eig.roi.docx.exceptions.ConversionException;
import com.mckesson.eig.roi.docx.exceptions.InitializationException;
import com.mckesson.eig.roi.docx.exceptions.ValidationException;
import com.mckesson.eig.roi.docx.model.Configuration;
import com.mckesson.eig.roi.docx.model.Constants;
import com.mckesson.eig.roi.docx.model.LetterTemplate;
import com.mckesson.eig.roi.docx.utils.ConsoleUtil;
import com.mckesson.eig.roi.docx.utils.HibernateUtil;

/**
 * @author Eric yu
 * 
 */
public class Main {

	private static final Logger logger = Logger.getLogger(Main.class);
	private static final RollingFileAppender fileAppender = (RollingFileAppender) Logger.getRootLogger().getAppender("fileAppender");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = null;
		try {
			main = new Main();
			main.initialize();
			main.start();
			main.destroy();
		} catch (Exception e) {
			logger.error("Error in conversion tool", e);
			logger.error("Error occurred. Check log for more details. Restart Conversion tool after all errors have been fixed.");
			//try to destroy again
			if(main != null) {
				main.destroy();
			}
		}
		System.out.print("Open conversion log file (Y/N)? ");
		String openLogFile = new java.util.Scanner(System.in).nextLine();
		if (openLogFile.equalsIgnoreCase("Y")) {
			ProcessBuilder pb = new ProcessBuilder("notepad.exe", new File(fileAppender.getFile()).getAbsolutePath());
			try {
				pb.start();
			} catch (IOException e) {
				logger.error("Unable to open conversion log file", e);
			}
		}
		logger.debug("Exiting");
	}

	private void initialize() throws InitializationException, ValidationException, 
		HibernateException, MalformedURLException, FileNotFoundException {
		System.out.print("The purpose of this tool is to convert previous letter templates ");
		System.out.print("which are stored in rtf format to docx format.\n");
		System.out.print("It is required the work station that run the tool can access the database server ");
		System.out.print("and with Microsoft Word installed.\n");
		rollLogFile();
		String dbServer = ConsoleUtil.readLine("Enter Database server name: ");
		String dbUsername = ConsoleUtil.readPassword("Enter database user : ");
		String dbPassword = ConsoleUtil.readPassword("Enter password for "+ dbUsername + " user: ");
		String port = ConsoleUtil.readLine("Enter database port number (default 1433): ");
		Configuration.getProperties().setProperty(Constants.DB_SERVER_PROPERTY, dbServer);
		Configuration.getProperties().setProperty(Constants.DB_USERNAME_PROPERTY, dbUsername);
		Configuration.getProperties().setProperty(Constants.DB_PASSWORD_PROPERTY, dbPassword);
		
		if(port == null || port.trim().equals("")) {
			port = Constants.DB_DEFAULT_PORT_PROPERTY;
		}
		try {
			Integer.valueOf(port);
		} catch(Exception e) {
			port = Constants.DB_DEFAULT_PORT_PROPERTY;
		}
		Configuration.getProperties().setProperty(Constants.DB_PORT_PROPERTY,port);
		try {
			logger.info("Initializing database connection");
			HibernateUtil.initialize();
			logger.info("Finish initializing database connection");
		} catch (HibernateException e) {
			logger.error("Error initializing database connection");
			throw e;
		}
		
	}

	private void start() throws ConversionException {
		boolean review = false;

		String reviewBeforeUpload = ConsoleUtil.readLine("Do you want to review the upload files before upload to database (Y/N)? ");
		if (reviewBeforeUpload.equalsIgnoreCase("Y")) {
			review = true;
		}

		System.out.print("\nStart conversion (Y/N)? ");
		String startConversion = new java.util.Scanner(System.in).nextLine();
		if (!startConversion.equalsIgnoreCase("Y")) {
			return;
		}
		logger.info("Starting conversion...");
		ConversionDao dao = new ConversionDao();
		List<LetterTemplate> list = dao.getAllLetterTempplate();
		if(list == null || list.size() == 0) {
			logger.info("There is no letter template to convert.\n Exit from the converter.");
		}
		ConversionProcessor processor = new ConversionProcessor(dao);
		logger.info("There are " + list.size() + " letter template(s) to convert.\n");
		int idx = 0;
		List<Long> idList = new ArrayList<Long>();
		for(LetterTemplate template : list) {
			String name = template.getName();
			idx ++;
			long id = template.getId();
			logger.info("Processing template [" + name + "] with id " + id + " - " + idx + " of " + list.size());
			boolean b = processor.convert(id, review);
			if(b) {
				logger.info("Template " + idx + " - Success");
			} else {
				logger.info("Template " + idx + " - failed");
			}
			try {
				Thread.sleep(10000);
			} catch(Exception e) {
				
			}
			idList.add(id);
		}
		processor.displayFolderLocation(review);
		if(review) {
			System.out.print("\nStart upload to database (Y/N)? ");
			String startUpload = new java.util.Scanner(System.in).nextLine();
			if (!startUpload.equalsIgnoreCase("Y")) {
				return;
			}
			for(Long id : idList) {
				processor.updateContentFileTodb(id);
			}
		}
	}

	private void destroy() {
		logger.info("Closing database connection");
		HibernateUtil.destroy();
	}
	
	private void rollLogFile() {
		System.out.println("\nRolling log file");
		File file = new File(fileAppender.getFile());
		if (file.length() > 0) {
			fileAppender.rollOver();
		}
		System.out.println("Conversion log file is at: " + file.getAbsolutePath());
	}

}
