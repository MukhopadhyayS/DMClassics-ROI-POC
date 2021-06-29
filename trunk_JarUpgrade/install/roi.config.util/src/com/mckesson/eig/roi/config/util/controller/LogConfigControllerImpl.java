/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2011 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mckesson.eig.roi.config.util.api.ConfigProps;
import com.mckesson.eig.roi.config.util.api.ConfigUtilException;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.api.TabSource;


/**
 *
 * @author OFS
 * @date June 29, 2011
 * @since HPF 15.2
 *
 */

public class LogConfigControllerImpl
extends ConfigurationControllerImpl {

	private static final Logger LOG = Logger.getLogger(LogConfigControllerImpl.class);

	private String _jndiDataSourcePath =
		"//log4j:configuration/appender[contains(@name, 'Database')]/param[@name='dsSource']";

	private String _logDTDPath = "org/apache/log4j/xml";
	private String _targetAttr = "value";

	/**
	 * This method loads the Configuration from the XML file
	 * @see com.mckesson.eig.roi.config.util.controller.ConfigurationControllerImpl#load()
	 *
	 */
	@Override
	protected Object load() {
		final String logSM = "loadConfigParams()";
        LOG.debug(logSM + ">>Start:");

        try {

        	Map<String, String> configParams = new HashMap<String, String>();

            if (ConfigProps.ROI_SERVER_INSTALLED) {

                configParams.put(ConfigProps.ROI_JNDI_NAME,
                		readConfigParams(ConfigProps.ROI_LOG_PATH));
            }
            if (ConfigProps.OUTPUT_SERVER_INSTALLED) {

            	 configParams.put(ConfigProps.OUTPUT_JNDI_NAME,
                 		readConfigParams(ConfigProps.OUTPUT_LOG_PATH));
            }

            LOG.debug(logSM + "<<End:Existing Database Loggging Config Details: " +
            			logConfigDetails(configParams));

            return configParams;

        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.read.log.config"),
                                          e.getCause());
        }
	}

	/*
	 * Reads the configurations jndiName
	 * from the given configuration file
	 * @param logConfigFile
	 * @return
	 * @throws DocumentException
	 */
	private String readConfigParams(String logConfigFile) throws DocumentException {

		Document document = getXMLDocument(logConfigFile);
        String jndiName = getAttributeValue(document, _jndiDataSourcePath, _targetAttr);

        return jndiName;
    }

	/**
	 * Get the value of the given attribute name in the given Xpath
	 *
	 * @param doc
	 * @param xPath
	 * @param attrName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getAttributeValue(Document doc, String xPath, String attrName) {

	   List<Element> element = doc.selectNodes(xPath);
	   List<Attribute> attributes = null;
	   String attrValue = null;

	   for (Element node : element ) {

		   attributes = node.attributes();
		   for (Attribute attr : attributes) {

			   if (attrName.equals(attr.getName())) {
				   attrValue = attr.getValue();
			   }
		   }
	    }
	    return attrValue;
	}

	/**
	 * set given attibute value in the given xpath
	 * @param doc
	 * @param xPath
	 * @param attrName
	 * @param attrValue
	 */
	@SuppressWarnings("unchecked")
	public void setAttributeValue(Document doc, String xPath, String attrName, String attrValue) {

		List<Element> element = doc.selectNodes(xPath);
		List<Attribute> attributes = null;
		if (null != element) {

			for (Element node : element ) {

				attributes = node.attributes();
		        for (Attribute attr : attributes) {

		        	if (attrName.equals(attr.getName())) {
		        		attr.setValue(attrValue);
		        	}
		        }
			 }
		 }
	}


	/**
	 *
	 * Saves the jndiName configuration in the ROI and Output log Files
	 * @param params DBconfiguration map
	 */
	@Override
	public void save(Object params) {

		if (ConfigProps.ROI_SERVER_INSTALLED) {

			save(ConfigProps.ROI_LOG_PATH, ConfigProps.ROI_JNDI_NAME);
			save(ConfigProps.ROI_INUSE_LOG_PATH, ConfigProps.ROI_JNDI_NAME);
			save(ConfigProps.ROI_ALERT_LOG_PATH, ConfigProps.ROI_JNDI_NAME);
			// CR# - 381,475
//			save(ConfigProps.ROI_AUDIT_LOG_PATH, ConfigProps.ROI_JNDI_NAME);
		}

		if (ConfigProps.OUTPUT_SERVER_INSTALLED) {

			save(ConfigProps.OUTPUT_LOG_PATH, ConfigProps.OUTPUT_JNDI_NAME);
			save(ConfigProps.OUTPUT_AUDIT_LOG_PATH, ConfigProps.OUTPUT_JNDI_NAME);
			save(ConfigProps.OUTPUT_AUDIT_LOG_PATH, ConfigProps.OUTPUT_JNDI_NAME);
		}
	}

	/*
	 * Allows to save the configuration in the logging configuration XML
	 * @param configurationparams
	 * @param fileName
	 * @param Query
	 */
	private void save(String fileName, String jndiName) {

		final String logSM = "saveLogConfiguration(configParams)";
        LOG.debug(logSM + ">>Start:" + fileName + ">>JNDI Name:" + jndiName);

        try {

            Document document = getXMLDocument(fileName);
	        setAttributeValue(document, _jndiDataSourcePath, _targetAttr, jndiName);

	        updateXMLDocument(document, fileName);
            LOG.debug(logSM + "<<End: saved ");
        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.save.log.config"),
                                          e.getCause());
        }
	}


	/**
	 * This method parse the XML document with the DTD taken from the package org/apache/log4j/xml
	 */
	@Override
	public Document getXMLDocument(String fileName)
	throws DocumentException {

		File xmlFile = new File(fileName);
		SAXReader reader = new SAXReader();

		// Entity resolver is responsible for DOCTYPE dtd validation
		// By default the log4j.dtd file is searched in the location of the xml file itself
		// So modified to search the log4j.dtd in the classpath org/apache/log4j/xml.
		reader.setEntityResolver(new EntityResolver() {

			@Override
			public InputSource resolveEntity(String publicId, String systemId)
					throws SAXException, IOException {

				ClassLoader clazz = Thread.currentThread().getContextClassLoader();
				InputStream stream =
					clazz.getResourceAsStream(_logDTDPath +
												systemId.substring(systemId.lastIndexOf('/')));
				return new InputSource(stream);
			}
		});

	    return reader.read(xmlFile);
	}

	private String logConfigDetails(Map<String, String> configParam) {
		return new StringBuffer().append("ROI jndiName: ")
		                         .append(configParam.get(ConfigProps.ROI_JNDI))
		                         .append("OUTPUT jndiName: ")
		                         .append(configParam.get(ConfigProps.OUTPUT_JNDI))
		                         .toString();

	    }

	@Override
	protected TabSource getTabSource() {
		return TabSource.DB;
	}
}
