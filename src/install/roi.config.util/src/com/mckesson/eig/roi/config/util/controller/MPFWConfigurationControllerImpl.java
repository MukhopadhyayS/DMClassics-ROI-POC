/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2013 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.InetAddress;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.MessageFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.mckesson.eig.roi.config.util.api.ConfigProps;
import com.mckesson.eig.roi.config.util.api.ConfigUtilException;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.api.TabSource;
import com.mckesson.eig.utility.util.CollectionUtilities;

/**
 * @author Karthik Easwaran
 * @date   Feb 19, 2013
 * @since  Feb 19, 2013
 */
public class MPFWConfigurationControllerImpl
extends ConfigurationControllerImpl {

	private static final Logger LOG = Logger.getLogger(MPFWConfigurationControllerImpl.class);
	private static final boolean DO_DEBUG = LOG.isDebugEnabled();

	private static String newLine = System.getProperty("line.separator");
	private final String _encryptionXpath = "/context/object";
	private final String _configuratioServletXPath = "/web-app/servlet/servlet-name[.='EIGConfigurationServlet']";


	/**
	 * @see com.mckesson.eig.roi.config.util.controller.ConfigurationControllerImpl#save(java.lang.Object)
	 */
	@Override
	public void save(Object object) {

		if (DO_DEBUG) {
			LOG.debug("Save MPFW Configuration>>Start");
		}
		if (ConfigProps.ROI_SERVER_INSTALLED) {
			updateMpfwConfiguration(ConfigProps.JBOSS_HOME, true);
		}

		if (ConfigProps.OUTPUT_SERVER_INSTALLED) {
			updateMpfwConfiguration(ConfigProps.OUTPUT_JBOSS_HOME, false);
		}

		if (DO_DEBUG) {
			LOG.debug("Save MPFW Configuration<<End");
		}

	}

	/**
	 * @see com.mckesson.eig.roi.config.util.controller.ConfigurationControllerImpl#load()
	 */
	@Override
	protected Object load() {
		return null;
	}

	/**
	 * @see com.mckesson.eig.roi.config.util.controller.ConfigurationControllerImpl#getTabSource()
	 */
	@Override
	protected TabSource getTabSource() {
		return null;
	}


	public void updateMpfwConfiguration(String jbossLocation, boolean isRoi) {

		updateMpfwApplicationConfig(jbossLocation, isRoi);
		updateMpfwEncryptionClass(jbossLocation);
		updateMpfwLoggingConfiguration(isRoi);
		copyLoginConfigFile(jbossLocation);

	}

	private void copyLoginConfigFile(String jbossLocation) {
		
		URL resource = MPFWConfigurationControllerImpl.class.getResource("/hpfwConfig/log-config.xml");
		File readerFile = null;
		File writerFile = null;
		FileChannel src = null;
		FileChannel dest = null;
		try {
			
			readerFile = new File(resource.toURI().getPath());
			writerFile = new File(jbossLocation, ConfigProps.SERVER_LOGIN_CONFIG_FILE);
			
			File parentFile = writerFile.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			
			src = new FileInputStream(readerFile).getChannel();
			dest = new FileOutputStream(writerFile).getChannel();
			
			dest.transferFrom(src, 0, src.size());

		} catch (Exception ex) {
			LOG.error("Exception occurred while updating File:" + writerFile.getAbsolutePath(), ex);
			throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.update.log.config"), ex);
		} finally {

			close(src);
			close(dest);
		}
	}

	private void updateMpfwEncryptionClass(String jbossLocation) {

		String fileName = jbossLocation + ConfigProps.MPFW_ENCRYPTION_CONFIG_PATH;
		try {

			Document document = getXMLDocument(fileName);
			setEncryptionElementValue(document, _encryptionXpath, ConfigProps.MPFW_ENCRYPTION_CLASS);
			updateXMLDocument(document, fileName);

		} catch (Exception ex) {
			LOG.error("Exception occurred while updating File:" + fileName, ex);
			throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.update.log.config"), ex);
		}

	}

	private void updateMpfwApplicationConfig(String jbossLocation, boolean isRoi) {

		String fileName = jbossLocation + ConfigProps.MPFW_WEB_XML_PATH;
		try {

			Document document = getXMLDocument(fileName);

			List<Element> elements = document.selectNodes(_configuratioServletXPath);
			if (CollectionUtilities.hasContent(elements)) {


				Element ele = elements.get(0);
				Element parent = ele.getParent();
				List<Element> selectNodes = parent.selectNodes("init-param/param-name");

				if (CollectionUtilities.hasContent(selectNodes)) {

					for (Element ele1 : selectNodes) {

						if ("application".equalsIgnoreCase(ele1.getTextTrim())) {

							Element applicationParent = ele1.getParent();
							Node selectSingleNode = applicationParent.selectSingleNode("param-value");
							if (isRoi) {
								selectSingleNode.setText(ConfigProps.ROI_MPFW_APPLICATION_NAME);
							} else {
								selectSingleNode.setText(ConfigProps.OUTPUT_MPFW_APPLICATION_NAME);
							}
						}
					}
				}
			}

			updateXMLDocument(document, fileName);

		} catch (Exception ex) {
			
			LOG.error("Exception occurred while updating File:" + fileName, ex);
			throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.update.log.config"), ex);
		}

	}

	/**
	 * updates the MPFW Logging configuration such as Global Name and Log File location
	 * @param jbossLocation
	 */
	private void updateMpfwLoggingConfiguration(boolean isRoi) {

		String fileLocation = ConfigProps.MPFW_LOGGING_PRODUCTION_PATH;
		updateLogConfiguration(fileLocation, isRoi);

		fileLocation = ConfigProps.MPFW_LOGGING_STAGING_PATH;
		updateLogConfiguration(fileLocation, isRoi);
	}

	/**
	 * updates the log location for File logging
	 * and the global name for the SQL Logging
	 *
	 * @param jbossLocation
	 * @param fileLocation
	 */
	private void updateLogConfiguration(String fileLocation, boolean isRoi) {

		String applicationName;
		String jbossLocation;
		if (isRoi) {

			applicationName = ConfigProps.ROI_MPFW_APPLICATION_NAME;
			jbossLocation = ConfigProps.JBOSS_HOME;
		} else {

			applicationName = ConfigProps.OUTPUT_MPFW_APPLICATION_NAME;
			jbossLocation = ConfigProps.OUTPUT_JBOSS_HOME;
		}
		File file = new File(jbossLocation, fileLocation);
		if (!file.exists()) {
			return;
		}

		FileReader reader = null;
		FileWriter writer = null;
		BufferedReader bufReader = null;
		try {

			reader = new FileReader(file);
			bufReader = new BufferedReader(reader);

			String lineData;
			StringBuffer updatedData = new StringBuffer();
			while ((lineData = bufReader.readLine()) != null) {

				String trimmedData = lineData.trim();
				if (trimmedData.startsWith("#")) {

					updatedData.append(trimmedData + newLine);
					continue;
				}

				if (trimmedData.startsWith("log4j.appender.eiglog.File=")) {

					// backward slash is a escape character in java, hence
					// replace the backward slash with forward slash
					String location = (jbossLocation + ConfigProps.MPFW_LOG_FILE_LOCATION).replace("\\", "/");
					String data = "log4j.appender.eiglog.File=" + location;
					updatedData.append(data);

				} else if (trimmedData.startsWith("log4j.appender.DB.sql=")) {


					int index = trimmedData.lastIndexOf("=");
					String substring = trimmedData.substring(0, index + 1);

					String hostName = InetAddress.getLocalHost().getHostName();
					Object[] args = new Object[] { applicationName, hostName };

					String globalName = MessageFormat.format(ConfigProps.MPFW_LOGGING_GLOBAL_NAME_FORMAT, args);
					substring = substring + " '" + globalName + "'";
					updatedData.append(substring);

				} else {
					updatedData.append(trimmedData);
				}
				updatedData.append(newLine);
			}

			writer = new FileWriter(file);
			writer.write(updatedData.toString());

		} catch (Exception ex) {
			LOG.error("Exception occurred while updating File:" + fileLocation, ex);
			throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.update.log.config"), ex);
		} finally {

			close(bufReader);
			close(reader);
			close(writer);
		}
	}

	public static void main(String[] args) {

		MPFWConfigurationControllerImpl object = new MPFWConfigurationControllerImpl();
		object.save(null);
	}



}
