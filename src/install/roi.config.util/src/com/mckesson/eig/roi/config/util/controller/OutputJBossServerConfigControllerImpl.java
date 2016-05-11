/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import com.mckesson.eig.roi.config.util.api.ConfigProps;
import com.mckesson.eig.roi.config.util.api.ConfigUtilException;
import com.mckesson.eig.roi.config.util.api.ConfigUtilMessages;
import com.mckesson.eig.roi.config.util.api.TabSource;



/**
 *
 * @author OFS
 * @date   Oct 13, 2009
 * @since  HPF 13.1 [ROI]; Oct 13, 2009
 */
public class OutputJBossServerConfigControllerImpl
extends ConfigurationControllerImpl {


    private static final Logger LOG = Logger.getLogger(JBossServerConfigControllerImpl.class);

    private String _portXPath = "//Server/Service/Connector/@port";
    private static final String OUTPUT_SERVER_PORT_KEY = "cws.server.port";
    
    @Override
	protected Object load() {

		final String logSM = "loadJbossConfigParams()";
        LOG.debug(logSM + ">>Start:");

        try {

            Map<String, String> configParams = new HashMap<String, String>();
            loadMemoryDetails(configParams);
            loadJbossPort(configParams);

            LOG.debug(logSM + "<<End: Existing Output Jboss Memory Details: "
                      + jbossMemoryDetails(configParams));

            return configParams;
        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        }
	}

    @Override
	protected void save(Object object) {

		final String logSM = "save(configParams)";

        @SuppressWarnings("unchecked") //Not supported by 3rd party API
        Map<String, String> configParams = (Map<String, String>) object;
        LOG.debug(logSM + ">>Start: New Jboss Config Details: " + jbossMemoryDetails(configParams));

        try {

            ConfigurationValidatorImpl validator = new ConfigurationValidatorImpl();
            validator.validateJbossParams(configParams);

            saveMemoryDetails(configParams);
            saveServerPort(configParams);
            saveLoggingServerPort(configParams);
            saveOutputSettingsProperties( configParams);

            String ipAddress = Inet4Address.getLocalHost().getHostAddress();
			configParams.put(ConfigProps.IP_ADDRESS, ipAddress);

            HPFWAuthConfigControllerImpl hpfwAuthconfig = new HPFWAuthConfigControllerImpl();
            hpfwAuthconfig.save(configParams);

            LOG.debug(logSM + "<<End:");

        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (UnknownHostException cause) {

        	LOG.error("Unable to get Host IP Address", cause);
			throw new ConfigUtilException("Unable to get Host IP Address", cause);
		}
	}

    private void loadMemoryDetails(Map<String, String> configParams) {

        final String logSM = "loadMemoryDetails(configParams)";
        LOG.debug(logSM + ">>Start:");

        try {

            FileInputStream fstream =
                new FileInputStream(ConfigProps.OUTPUT_JBOSS_SERVER_RUN_BAT_FILE);

            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {

                if (strLine.startsWith("set JAVA_OPTS=%JAVA_OPTS% -Xm")) {

                    configParams.put(ConfigProps.MIN_MEMORY,
                                     getJbossMemoryDetails(strLine, "-Xms"));

                    /*configParams.put(ConfigProps.MAX_MEMORY,
                                    getJbossMemoryDetails(strLine, "-Xmx"));*/

                    if (strLine.contains("-XX:MaxPermSize")) {

                        configParams.put(ConfigProps.PERM_SIZE,
                                         getJbossMemoryDetails(strLine, "-XX:MaxPermSize="));

                    } else {

                        configParams.put(ConfigProps.PERM_SIZE, "");
                    }
                }
            }
            // added to show the default value as 5120
            configParams.put(ConfigProps.MAX_MEMORY, ConfigProps.OUTPUT_MAX_MEMORY);
            fstream.close();

            LOG.debug(logSM + "<<End: ");

        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.read.jboss.config"),
                                          e.getCause());
        }
    }

    private void loadJbossPort(Map<String, String> configParams) {

        final String logSM = "loadJbossPort(configParams)";
        LOG.debug(logSM + ">>Start:");

        try {

            Document document = getXMLDocument(ConfigProps.OUTPUT_JBOSS_SERVER_SERVER_XML_FILE);
            // CR #368,304
            configParams.put(ConfigProps.PORT_NO, getConditionedAttributeValue(document, _portXPath));

            LOG.debug(logSM + "<<End:");

        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.read.jboss.config"),
                                          e.getCause());
        }
    }

    private void saveLoggingServerPort(Map<String, String> configParams) {

		try {

			Document doc = getXMLDocument(ConfigProps.OUTPUT_SPRING_FILE);
			Element e = doc.getRootElement();
	        for (Iterator<Element> i = e.elementIterator(); i.hasNext();) {

	            Element element = i.next();
	            Attribute attribute = element.attribute("id");

	            if ((attribute != null)
	            && "globalIDInitializer".equalsIgnoreCase(attribute.getValue())) {

	                List children = element.elements();
	                Object[] child = children.toArray();
	                for (int k = 0; k < child.length; k++) {

	                    DefaultElement de = (DefaultElement) child[k];
	                    Attribute attr = de.attribute("name");
	                    Attribute attrValue = de.attribute("value");

	                    if ((attr != null) && "serverPort".equalsIgnoreCase(attr.getValue())) {
	                    	attrValue.setText(configParams.get(ConfigProps.PORT_NO));
	                    }
	                }
	            }
	        }

	        updateXMLDocument(doc, ConfigProps.OUTPUT_SPRING_FILE);
		} catch (ConfigUtilException cue) {

			LOG.error(cue);
			throw cue;
		} catch (Throwable e) {

			LOG.error(e);
			throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.save.jboss.config"),
											e.getCause());
		}

	}

    private void saveMemoryDetails(Map<String, String> configParams) {

        final String logSM = "saveMemoryDetails(configParams)";
        LOG.debug(logSM + ">>Start:");

        try {

            FileInputStream fin = new FileInputStream(ConfigProps.OUTPUT_JBOSS_SERVER_RUN_BAT_FILE);
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            StringBuffer sb = new StringBuffer();
            String strLine = null;
            //Reads File Line By Line and updates the memory details
            while ((strLine = br.readLine()) != null)   {

                if (strLine.contains("set JAVA_OPTS=%JAVA_OPTS% -Xm")) {

                    strLine = updateJbossMemoryDetails(strLine,
                                                       "-Xms[0-9]+m",
                                                       "-Xms",
                                                       configParams.get(ConfigProps.MIN_MEMORY));

                    strLine =  updateJbossMemoryDetails(strLine,
                                                        "-Xmx[0-9]+m",
                                                        "-Xmx",
                                                        configParams.get(ConfigProps.MAX_MEMORY));

                    strLine = updateJbossMemoryDetails(strLine,
                                                       "-XX:MaxPermSize=[0-9]+m",
                                                       "-XX:MaxPermSize=",
                                                       configParams.get(ConfigProps.PERM_SIZE));

                    if (!strLine.contains("-XX:MaxPermSize")
                        && (configParams.get(ConfigProps.PERM_SIZE).length() > 0)) {
                        strLine = strLine + " -XX:MaxPermSize="
                                          + configParams.get(ConfigProps.PERM_SIZE) + "m";
                    }
                }
                sb.append(strLine);
                sb.append("~@~");
            }
            fin.close();

            //writes the output.run.bat file with the updated details
            FileOutputStream fop =
                new FileOutputStream(ConfigProps.OUTPUT_JBOSS_SERVER_RUN_BAT_FILE);

            String value = sb.toString();
            String[] s1 = value.split("~@~");
            for (int i = 0; i < s1.length; i++) {

                fop.write(s1[i].getBytes());
                fop.write("\n".getBytes());
            }
            fop.flush();
            fop.close();

            LOG.debug(logSM + "<<End:");
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.save.jboss.config"),
                                          e.getCause());
        }
    }

    private void saveServerPort(Map<String, String> configParams) {

        final String logSM = "saveServerPort(configParams)";
        LOG.debug(logSM + ">>Start:");

        try {

            Document document = getXMLDocument(ConfigProps.OUTPUT_JBOSS_SERVER_SERVER_XML_FILE);
            setAttributeValue(document, _portXPath, configParams.get(ConfigProps.PORT_NO));
            updateXMLDocument(document, ConfigProps.OUTPUT_JBOSS_SERVER_SERVER_XML_FILE);

            LOG.debug(logSM + "<<End:");

        } catch (ConfigUtilException cue) {

            LOG.debug(cue);
            throw cue;
        } catch (Throwable e) {

            LOG.debug(e);
            throw new ConfigUtilException(ConfigUtilMessages.getMessage("unable.save.jboss.config"),
                                          e.getCause());
        }
    }

    private String jbossMemoryDetails(Map<String, String> configParams) {

        return new StringBuffer().append(", Port No : ")
                                 .append(configParams.get(ConfigProps.PORT_NO))
                                 .append(", Min Memory : ")
                                 .append(configParams.get(ConfigProps.MIN_MEMORY))
                                 .append(", Max Memory : ")
                                 .append(configParams.get(ConfigProps.MAX_MEMORY))
                                 .append(", Perm Size : ")
                                 .append(configParams.get(ConfigProps.PERM_SIZE))
                                 .toString();

    }

	@Override
	protected TabSource getTabSource() {
		return TabSource.OUTPUT;
	}
	
	/**
     * This method will update the output settings to output-settings properties
     * @param configParams 
	 */
	private void saveOutputSettingsProperties(Map<String, String> configParams) {
		
		File file = new File(ConfigProps.OUTPUT_JBOSS_HOME,
				ConfigProps.SERVER_EIG_OUTPUT_SETTINGS_FILE);
		saveProperties(file, OUTPUT_SERVER_PORT_KEY, configParams.get(ConfigProps.PORT_NO));
		
	}

}
