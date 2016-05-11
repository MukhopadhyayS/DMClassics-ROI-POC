/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.config.util.controller;

import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mckesson.eig.roi.config.util.api.AutoConfigurator;
import com.mckesson.eig.roi.config.util.api.ConfigProps;
import com.mckesson.eig.roi.config.util.api.LogInitializer;

/**
 * @author karthike
 * @date   May 31, 2012
 * @since  May 31, 2012
 */
public class AutoConfigurationController {
	
	private static final Logger LOG = Logger.getLogger(AutoConfigurationController.class);

	private static ConfigurationControllerImpl _dbController;
	private static ConfigurationControllerImpl _faxController;
	private static ConfigurationControllerImpl _hpfwClientController;
	private static ConfigurationControllerImpl _roiController;
	private static ConfigurationControllerImpl _outputController;
	private static ConfigurationControllerImpl _outputClientController;
	private static ConfigurationControllerImpl _roiClientController;
	
	private static boolean _isJbossVersion4 = false;
	
	/**
	 * Initializes the configuration controllers
	 */
	private static void initializeControllers() {
		
		_dbController = new DBConnectionConfigControllerImpl();
		_faxController = new FaxServiceConfigControllerImpl();

		if (_isJbossVersion4) {
			_roiController = new JBoss4ServerConfigControllerImpl();
			_outputController = new Jboss4OutputServerConfigControllerImpl();
		} else {
			_roiController = new JBossServerConfigControllerImpl();
			_outputController = new OutputJBossServerConfigControllerImpl();
		}
		
		_hpfwClientController = new HPFWServiceConfigControllerImpl();
		_roiClientController = new ClientInstallerConfigControllerImpl();
		_outputClientController = new OutputServerConfigControllerImpl();
	}
	
	/**
	 * Loads all the ROI Configurations to the properties map and returns the properties
	 *  
	 * @return map of configurations.
	 */
	public static Properties loadROIConfigurations() {
		
		loadProperties(_dbController);

		if (ConfigProps.FAX_SERVER_INSTALLED) {
			loadProperties(_faxController);
		}
		
		if (ConfigProps.ROI_SERVER_INSTALLED) {
			loadProperties(_roiController);
		}
		
		if (ConfigProps.ROI_CLIENT_INSTALLER_INSTALLED) {
			
			loadProperties(_roiClientController);
			loadProperties(_hpfwClientController);
			loadProperties(_outputClientController);
		}
		
		if (ConfigProps.OUTPUT_SERVER_INSTALLED) {
			loadProperties(_outputController);
		}
		
		return AutoConfigurator.getProps();
	}

	/**
	 * Adds the given configurations to the properties
	 * prefixed with the tab source
	 * 
	 * @param map 
	 * @param tabSource 
	 * 
	 */
	@SuppressWarnings("unchecked")
	private static void loadProperties(ConfigurationControllerImpl controller) {
		
		try {
			
			Object prop = controller.load();
			if (null == prop) {
				return;
			}
			if (!(prop instanceof Map)) {
				LOG.error("The retrieved properties is not instance of the Map");
				return;
			}
			AutoConfigurator.addProperties((Map<String, String>) prop, 
											controller.getTabSource());
			
		} catch (Exception e) {
			LOG.debug("Unable to load Configuration for Controller: " + controller, e);
		}
	}
	
	public static void main(String[] args) {

		
		//Entry point of the application
		//Initializes logger
		LogInitializer.initializeLogger();
		
		if (args.length > 0) {
			
			String version = args[0];
			if (version.equalsIgnoreCase("4")
					|| version.equalsIgnoreCase("four")) {
				
				LOG.debug("Jboss Version 4 is Set");
				_isJbossVersion4 = true;
			}
			
		}
        initializeControllers();
		loadROIConfigurations();
		exportPropertiesToXML();
		
	}

	/**
	 * export the list of properties to the user's home directory
	 */
	private static void exportPropertiesToXML() {
		
		AutoConfigurator.exportXML();
	}
}
