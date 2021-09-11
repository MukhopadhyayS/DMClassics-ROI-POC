/**
 * Copyright � 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.config.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.mckesson.dm.core.common.logging.OCLogger;
import org.apache.log4j.xml.Log4jEntityResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mckesson.eig.config.constants.ConfigurationConstants;
import com.mckesson.eig.config.exception.ConfigureLogException;
import com.mckesson.eig.config.model.ListViewLogInfo;
import com.mckesson.eig.config.model.ListViewLogInfoList;
import com.mckesson.eig.config.model.LogConfigurationDetail;
import com.mckesson.eig.config.model.LogConfigurationInfo;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.utility.io.IOUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * Uses <code>DOMParser</code> for traversing through the log configuration
 * files. It collects the necessary logging attributes defined in the
 * <code>configureApplicationContext</code> and retrieves/writes information
 * accordingly.
 */
public class LogConfigurationPersistence {

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( LogConfigurationPersistence.class);

    /**
     * Holding instance of Log Configuration files absolute path.
     */
    private static String _absolutePath;

    /**
     * Holding an instance of <code>LogConfigurationInfo</code>.
     */
    private LogConfigurationInfo _configureLogInfo;

    /**
     * Holding an instance of <code>Document</code>.
     */
    private Document _document = null;

    /**
     * This retrieves the logging information from the log configuration.It uses
     * DOMParser for traversing through the configuration file.
     *
     * @param configDetail
     *            installed components detail.
     * @return <code>LogConfigurationInfo</code> logging information about an
     *         installed component.
     */
    public LogConfigurationInfo readComponentConfigInfo(LogConfigurationDetail configDetail) {


        loadConfigurationFile(configDetail.getLogFile());
        LogConfigurationInfo logInformation = traverseXml(configDetail);

        if (logInformation != null) {

            logInformation.setComponentName(configDetail.getComponentName());
            logInformation.setComponentSeq(Long.parseLong(configDetail.getComponentSeq()));
            _document = null;
            return logInformation;
        } else {

            LOG.debug(ConfigurationConstants.LOG_MESSAGE);
            throw new ConfigureLogException(
                                            ConfigurationConstants.UNABLE_RETRIEVE_INFO_EXCEPTION,
                                            ClientErrorCodes.UNABLE_TO_RETRIEVE_LOG_INFORMATION);
        }
    }

    /**
     * Saves the logging information into Log Configuration file.
     *
     * @param configureInfo
     *            logging information to be configured.
     *
     * @param configDetail
     *            LogConfigurationDetail object holding logging information.
     *
     * @return <code>true</code> if configured sucessfully <code>false</code>
     *         otherwise.
     */
    public boolean saveConfigValues(LogConfigurationInfo configureInfo,
                                    LogConfigurationDetail configDetail) {

        boolean returnValue = false;
        loadConfigurationFile(configDetail.getLogFile());
        returnValue   = setLoggingLevel(configureInfo, configDetail);
        configureInfo = checkForPlus(configureInfo);

        if (returnValue && setMaxFileSizeAndMaxLogFiles(configureInfo, configDetail)) {
            transformSource();
        }

        return returnValue;
    }

    /**
     * This method takes in the <code>configFilePath</code> as input, loads
     * and parses the logging file for the desired component.
     *
     * @param configFilePath
     *            configuration file path.
     */
    private void loadConfigurationFile(String configFilePath) {

        File file;
        try {

            LOG.info("Loading logging file from the path " + configFilePath);
            file = new File(ClassLoader.getSystemResource(configFilePath).toURI());

            if ((file != null) && (file.exists()) && file.canRead()) {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                documentBuilder.setEntityResolver(setLog4JDTD());

                _absolutePath = file.toString();
                InputStream inputStream = new FileInputStream(_absolutePath);
                try {
                    inputStream = new BufferedInputStream(inputStream);
                    _document = documentBuilder.parse(inputStream);
                } finally {
                    try { inputStream.close(); } catch (Exception e) {}
                }
            }
        } catch (SAXException e) {

            LOG.debug("Error Occured While Parsing :" + configFilePath + " file");
            throw new ConfigureLogException("Unable to read the config file",
                                                ClientErrorCodes.UNABLE_TO_READ_FILE);
        } catch (SecurityException e) {

            LOG.debug("User do not have enough security to access the log file");
            throw new ConfigureLogException(
                    "User do not have enough security to access the log file",
                    ClientErrorCodes.FILE_SECURITY);
        } catch (Exception exp) {

            LOG.debug("Unable to find the config file in path: : ");
            throw new ConfigureLogException(
                    "Unable to find the config file in path: " + configFilePath,
                    ClientErrorCodes.UNABLE_TO_FIND_FILE);
        } finally {
            file = null;
        }
    }

    /**
     * Traverses through the logging file to retrieve the logging information
     * and returns LogConfigurationInfo with details set.
     *
     * @param configDetail LogConfigurationDetail object holding logging information.
     *
     * @return <code>LogConfigurationInfo</code>.
     */
    private LogConfigurationInfo traverseXml(LogConfigurationDetail configDetail) {

        try {

            _configureLogInfo = getLoggingLevel(configDetail);

            if (_configureLogInfo != null && !StringUtilities.isEmpty(
                                             _configureLogInfo.getLoggingLevel())) {

                _configureLogInfo = getMaxFileSizeAndFiles(_configureLogInfo, configDetail);
                if (_configureLogInfo != null
                        && !StringUtilities.isEmpty(_configureLogInfo.getMaxLogFiles())
                        && !StringUtilities.isEmpty(_configureLogInfo.getMaxLogSize())) {
                    return _configureLogInfo;
                }
            }

            _configureLogInfo = null;
            return _configureLogInfo;
        } catch (Exception exp) {

            LOG.debug("Unable to retrieve information." + exp.getMessage());
            throw new ConfigureLogException(
                                            ConfigurationConstants.UNABLE_RETRIEVE_INFO_EXCEPTION,
                                            ClientErrorCodes.UNABLE_TO_RETRIEVE_LOG_INFORMATION);
        }
    }

    /**
     * This method retrieves the logging level.It parses through the category
     * elements and retrieves the logging level for the categories.
     *
     * @param configDetail
     *            LogConfigurationDetail object holding logging information.
     * @return <code>LogConfigurationInfo</code> with logging level set.
     */
    private LogConfigurationInfo getLoggingLevel(LogConfigurationDetail configDetail) {

        _configureLogInfo = new LogConfigurationInfo();
        if (_document != null) {

            NodeList nodeList = _document.getElementsByTagName(ConfigurationConstants.CATEGORY);
            if ((nodeList != null) && (nodeList.getLength() > 0)) {

                Element loggingElement =
                    getloggingLevelElement(nodeList, (String)
                                           configDetail.getCategoryList().get(0));
                _configureLogInfo.setLoggingLevel(loggingElement.getAttribute(
                                                  ConfigurationConstants.VALUE));
            }
        }
        return _configureLogInfo;
    }

    /**
     * Retrieves the Log File Name.Parses through the appender element and sets
     * the value to the nodes <code>LogFileName</code>.
     *
     * @param configDetail
     *            LogConfigurationDetail object holding logging information.
     * @return <code>LogConfigurationInfo</code> with log file name set.
     */
    public ListViewLogInfoList getLogFileName(LogConfigurationDetail configDetail) {

        Element checkElement;
        ListViewLogInfo listViewLogInfo = null;
        ListViewLogInfoList listViewLogInfoList = new ListViewLogInfoList();
        List listViewLogs = new ArrayList();
        loadConfigurationFile(configDetail.getLogFile());

        if (_document != null) {

            List appenderList = configDetail.getAppenderList();
            NodeList nodeList = _document.getElementsByTagName(ConfigurationConstants.APPENDER);

            for (int j = nodeList.getLength(); --j >= 0;) {
                
                if(!(nodeList.item(j) instanceof Element)) {
                    continue;
                }
                Element firstElement = (Element) nodeList.item(j);
                for (Object appenderObj : appenderList) {
                    if (firstElement.getAttribute(ConfigurationConstants.NAME)
                                    .equalsIgnoreCase((String) appenderObj)) {
                        checkElement = getElementByAttribute(firstElement,
                                                             ConfigurationConstants.LOG_FILE_NAME);
                        if (checkElement != null) {

                            listViewLogInfo = new ListViewLogInfo();
                            listViewLogInfo.setLogFileName(
                                           checkElement.getAttribute(ConfigurationConstants.VALUE));
                            listViewLogs.add(listViewLogInfo);
                            break;
                        }
                    }
                }
            }
            listViewLogInfoList.setListViewLogInfoList(listViewLogs);
        }
        return listViewLogInfoList;
    }

    /**
     * Retrieves the Max File Size and Max Log Files.Parses through the appender
     * element and sets the value to the nodes
     * <code>MaxFileSize</code> and <code>MaxBackupIndex</code>.
     *
     * @param configureLogInfo
     *            with set logging level
     *
     * @param configDetail LogConfigurationDetail object holding logging information.
     *
     * @return same <code>configureLogInfo</code> with MaxFileSize and
     *         MaxLogFiles set.
     */
    private LogConfigurationInfo getMaxFileSizeAndFiles(
            LogConfigurationInfo configureLogInfo, LogConfigurationDetail configDetail) {

        Element firstElement;
        Element checkElement;

        if (_document != null) {

            NodeList nodeList = _document.getElementsByTagName(ConfigurationConstants.APPENDER);
            if ((nodeList != null) && (nodeList.getLength() > 0)) {

                for (int j = 0; j < nodeList.getLength(); j++) {

                    if(!(nodeList.item(j) instanceof Element)) {
                        continue;
                    }
                    firstElement = (Element) nodeList.item(j);
                    if (firstElement.getAttribute(ConfigurationConstants.NAME).equalsIgnoreCase(
                            (String) configDetail.getAppenderList().get(0))) {

                        checkElement = getElementByAttribute(firstElement,
                                                             ConfigurationConstants.MAX_FILE_SIZE);
                        if (checkElement != null) {

                            configureLogInfo.setMaxLogSize(
                                    checkElement.getAttribute(ConfigurationConstants.VALUE));
                            checkElement = getElementByAttribute(firstElement,
                                                                 ConfigurationConstants.MAX_BCKUP);
                            if (checkElement != null) {

                                configureLogInfo.setMaxLogFiles(
                                    checkElement.getAttribute(ConfigurationConstants.VALUE));
                                return configureLogInfo;
                            }
                        }
                    }
                }
            }
        }
        return configureLogInfo;
    }

    /**
     * Sets the logging level in to the logging level.Parses through the
     * category element to set the logging level. This method sets the Logging
     * level specified for configuration. It set the desired logging level to
     * the <code>priority</code> node.
     *
     * @param logInfoCategory
     *            object holding the logging level.
     *
     * @param configDetail
     *            LogConfigurationDetail object holding logging information.
     *
     *
     * @return boolean <code>true</code> if the logging level could be set and
     *         <code>false</code> if otherwise.
     */
    private boolean setLoggingLevel(LogConfigurationInfo logInfoCategory,
                                    LogConfigurationDetail configDetail) {

        int checkListSize = 0;
        String categoryName = "";
        int categoryListSize = configDetail.getCategoryList().size();
        if (_document != null) {

            NodeList nodeList = _document.getElementsByTagName(ConfigurationConstants.CATEGORY);
            if ((nodeList != null) && (nodeList.getLength() > 0)) {

                for (int i = 0; i < categoryListSize; i++) {

                    categoryName = (String) configDetail.getCategoryList().get(i);
                    Element setLoggingLevel = getloggingLevelElement(nodeList,
                            categoryName);
                    if (setLoggingLevel != null) {

                        setLoggingLevel.setAttribute(ConfigurationConstants.VALUE,
                                                     logInfoCategory.getLoggingLevel()
                                                                    .toLowerCase());
                        checkListSize++;
                    }
                }
            } else {

                LOG.debug("Log4J element CATEGORY not found in the configuration file.");
                throw new ConfigureLogException(ConfigurationConstants.SAVE_CONFIGURATION_EXCEPTION,
                                                ClientErrorCodes.UNABLE_TO_SAVE_LOG_INFORMATION);
            }
        }
        if (checkListSize == categoryListSize) {
            return true;
        } else {

            LOG.debug(ConfigurationConstants.INVALID_CATEGORY_LOGGING);
            throw new ConfigureLogException("Unable to save the configuration ",
                                             ClientErrorCodes.UNABLE_TO_SAVE_LOG_INFORMATION);
        }
    }

    /**
     * Writes the MaxFileSize and MaxLogFiles to the logging file.Parses through
     * the appender element and sets the value to the nodes
     * <code>MaxFileSize</code> and <code>MaxBackupIndex</code>.
     *
     * @param logInfoAppender
     *            object holding the logging information.
     *
     * @param configDetail LogConfigurationDetail object holding logging information.
     *
     * @return <code>true</code> if stored successfully <code>false</code>
     *         otherwise.
     */
    private boolean setMaxFileSizeAndMaxLogFiles(LogConfigurationInfo logInfoAppender,
                                                 LogConfigurationDetail configDetail) {

        boolean result = false;
        int checkAppenderList = 0;
        int appenderListLength = 0;
        Element firstElement;
        Element checkElement;
        if (_document != null) {

            NodeList nodeList = _document.getElementsByTagName(ConfigurationConstants.APPENDER);
            if ((nodeList != null) && (nodeList.getLength() > 0)) {

                int nodeListLength = nodeList.getLength();
                appenderListLength = configDetail.getAppenderList().size();
                for (int i = 0; i < appenderListLength; i++) {

                    for (int j = 0; j < nodeListLength; j++) {

                        if(!(nodeList.item(j) instanceof Element)) {
                            continue;
                        }
                        firstElement = (Element) nodeList.item(j);
                        if (firstElement.getAttribute(ConfigurationConstants.NAME).equalsIgnoreCase(
                                (String) configDetail.getAppenderList().get(i))) {
                            checkAppenderList++;
                            checkElement = getElementByAttribute(firstElement,
                                    ConfigurationConstants.MAX_FILE_SIZE);
                            if (checkElement != null) {

                                checkElement.setAttribute(ConfigurationConstants.VALUE,
                                                          logInfoAppender.getMaxLogSize()
                                                                         .toUpperCase());
                                checkElement =
                                    getElementByAttribute(firstElement,
                                                          ConfigurationConstants.MAX_BCKUP);
                                if (checkElement != null) {

                                    checkElement.setAttribute(ConfigurationConstants.VALUE,
                                                              logInfoAppender.getMaxLogFiles());
                                    result = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            } else {

                LOG.debug("Log4J element APPENDER not found in the configuration file.");
                throw new ConfigureLogException(ConfigurationConstants.SAVE_CONFIGURATION_EXCEPTION,
                                                ClientErrorCodes.UNABLE_TO_SAVE_LOG_INFORMATION);
            }
        }
        if (checkAppenderList == appenderListLength) {
            result = true;
        } else {

            LOG.debug(ConfigurationConstants.INVALID_APPENDER_LOGGING);
            throw new ConfigureLogException(ConfigurationConstants.RETRIEVE_EXCEPTION ,
                                            ClientErrorCodes.UNABLE_TO_SAVE_LOG_INFORMATION);
        }
        return result;
    }

    /**
     * This method takes in the node list and the category name as input. It
     * returns the logging level element.It Parses through the category element
     * and retrieves the logging level from the <code>priority</code> node.
     *
     * @param nodeList
     *            list from which the desired category value has to be fetched.
     * @param categoryName
     *            Name of the category. (For example: com.quickstream..
     *            ,com.mckesson.hecm and so on)
     * @return Element Which is the desired nodename for the given category.
     */
    private Element getloggingLevelElement( NodeList nodeList,
                                            String categoryName) {

        Element element;
        int nodeListLength = nodeList.getLength();
        if ((nodeList != null) && (nodeList.getLength() > 0)) {

            for (int i = 0; i < nodeListLength; i++) {
                
                if(!(nodeList.item(i) instanceof Element)) {
                    continue;
                }
                element = (Element) nodeList.item(i);
                if (element != null
                    && element.getAttribute(ConfigurationConstants.NAME).equals(categoryName)) {
                    return getElementByNodeName(element, ConfigurationConstants.PRIORITY);
                }
            }
            element = null;
        }

        LOG.debug(
                ConfigurationConstants.INVALID_CATEGORY_LOGGING + ConfigurationConstants.PRIORITY);
        throw new ConfigureLogException(ConfigurationConstants.RETRIEVE_EXCEPTION,
                                        ClientErrorCodes.INVALID_LOG4J_ELEMENT);
    }

    /**
     * Returns the element for the required node name.
     *
     * @param element
     *            category element to be traversed.
     * @param attribute
     *            category attributes.
     * @return the desired attributes element.
     */
    private Element getElementByNodeName(Element element, String attribute) {

        LOG.debug("Invoking getElementByNodeName() in " + this.getClass().getName());
        Element returnElement = null;
        int childNodesLength = element.getChildNodes().getLength();
        for (int j = 0; j < childNodesLength; j++) {
            
            if(!(element.getChildNodes().item(j) instanceof Element)) {
                continue;
            }
            
            returnElement = (Element) element.getChildNodes().item(j);
            if (returnElement != null
                    && returnElement.getAttribute(ConfigurationConstants.VALUE) != null
                    && returnElement.getNodeName().equals(attribute)) {
                return returnElement;
            }
            returnElement = null;
        }
       return returnElement;
    }

    /**
     * Returns the element for the required attribute.It takes
     * <code>Element</code> and attribute name as parameter and returns the
     * element for the corresponding attribute.
     *
     * @param element
     *            either appender or category element to be traversed.
     * @param attribute
     *            appender or category attributes.
     * @return the desired attributes element.
     */
    private Element getElementByAttribute(Element element, String attribute) {

        LOG.debug("Invoking getElementByAttribute() in " + this.getClass().getName());
        Element returnElement = null;
        int childNodesLength = element.getChildNodes().getLength();
        for (int j = 0; j < childNodesLength; j++) {
            
            if (!(element.getChildNodes().item(j) instanceof Element)) {
                continue;
            }
            returnElement = (Element) element.getChildNodes().item(j);
            if (returnElement != null
                    && returnElement.getAttribute(ConfigurationConstants.VALUE) != null
                    && returnElement.getAttribute(ConfigurationConstants.NAME).equals(attribute)) {
                return returnElement;
            }
            returnElement = null;
        }
       LOG.debug(ConfigurationConstants.LOG4J_MESSAGE + attribute);
       throw new ConfigureLogException(ConfigurationConstants.RETRIEVE_EXCEPTION ,
                                        ClientErrorCodes.INVALID_LOG4J_ELEMENT);
    }

    /**
     * Checks whether the <code>maxFileSize</code> and
     * <code>maxLogFiles</code> starts with plus, if so it removes the prefix plus
     * and sets the rest to the <code>LogConfigurationInfo</code> object.
     *
     * @param configInfo
     *            LogConfigurationInfo object whose fields has to be tested for
     *            symbol "+".
     * @return LogConfigurationInfo with removed "+" prefix fields.
     */
    public LogConfigurationInfo checkForPlus(LogConfigurationInfo configInfo) {

        String logFileSize;
        String maxFiles;
        logFileSize = configInfo.getMaxLogSize();
        maxFiles = configInfo.getMaxLogFiles();
        if (logFileSize.startsWith("+")) {

            logFileSize = logFileSize.substring(1, logFileSize.length());
            configInfo.setMaxLogSize(logFileSize);
        }
        if (maxFiles.startsWith("+")) {

            maxFiles = maxFiles.substring(1, maxFiles.length());
            configInfo.setMaxLogFiles(maxFiles);
        }
        return configInfo;
    }

    /**
     * The file to be transformed is obtained from the
     * <code>_absolutePath</code> The XMLSerializer serializes the configured
     * content and stores the file in the absolute path after proper indentation
     * and formatting.
     *
     * @throws IOException
     *             if the file is not found.
     */
    private void transformSource() {

        LOG.debug("Transforming Configured Log File to " + _absolutePath);
        OutputStream outputStream = null;
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            outputStream = new FileOutputStream(_absolutePath);
            outputStream = new BufferedOutputStream(outputStream);

            transformer.transform(new DOMSource(_document), new StreamResult(outputStream));

            LOG.debug("Configured Log File sucessfully");
        } catch (Exception e) {
            LOG.debug("Exception occured while transforming the configured log file");
            throw new ConfigureLogException(ConfigurationConstants.SAVE_CONFIGURATION_EXCEPTION ,
                                            ClientErrorCodes.UNABLE_TO_SAVE_LOG_INFORMATION);
        } finally {
            IOUtilities.close(outputStream);
        }
    }

    /**
     * This method returns the <code>log4j.dtd</code> which is embedded within
     * log4j.jar file.
     *
     * @return Log4jEntityResolver.
     */
    private Log4jEntityResolver setLog4JDTD() {

        Log4jEntityResolver resolver = new Log4jEntityResolver();
        resolver.resolveEntity("", "log4j.dtd");
        return resolver;
    }
}
