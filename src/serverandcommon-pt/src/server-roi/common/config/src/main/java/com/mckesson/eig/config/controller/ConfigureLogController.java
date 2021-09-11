/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
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
package com.mckesson.eig.config.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.config.audit.LogConfigurationAuditManager;
import com.mckesson.eig.config.constants.ConfigurationConstants;
import com.mckesson.eig.config.exception.ConfigureLogException;
import com.mckesson.eig.config.model.InfoDetailList;
import com.mckesson.eig.config.model.ListViewLogInfo;
import com.mckesson.eig.config.model.ListViewLogInfoList;
import com.mckesson.eig.config.model.LogConfigurationDetail;
import com.mckesson.eig.config.model.LogConfigurationDetailList;
import com.mckesson.eig.config.model.LogConfigurationInfo;
import com.mckesson.eig.config.model.LogConfigurationInfoList;
import com.mckesson.eig.config.utils.LogConfigurationPersistence;
import com.mckesson.eig.config.validation.LogConfigurationValidator;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.utility.util.DateUtilities;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * LogConfigurationController is responsible for reading and writing the
 * configuration values into the logging config file.It gets the list of the
 * server components installed in the machine and retrieves the logging
 * information for all the components. It also configures the logging
 * information.
 * 
 */
public class ConfigureLogController {

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( ConfigureLogController.class);

    /**
     * Audit entry <code>Comment</code> that needs to be persisted in the
     * database.
     */
    private static final String AUDIT_COMMENT = "Logging Information Configured Sucessfully ";
    
    /**
     * Bean name for Componenets
     */
    private static final String COMPONENT_LIST = "component_list";
    

    /**
     * It retrieves all the server components installed in the machine and for
     * all components, it retrieves the corresponding configured logging
     * information.
     * 
     * @return <code>LogConfiguraitonInfoList</code> list of
     *         <code>LogConfigurationInfo</code>.
     */
    public LogConfigurationInfoList getAllServerComponentsConfigInfo() {
        
        LOG.debug("Invoking getAllServerComponentConfigIfo() in " + this.getClass().getName());
        
        List configInformation = new ArrayList();
        LogConfigurationDetail logConfigurationDetail;
        LogConfigurationPersistence logConfigurationPersistence = new LogConfigurationPersistence();
        List installedComponentsList = getAllInstalledComponents();
        
        validateComponentsInstalled(installedComponentsList.size());
        
        for (int i = installedComponentsList.size(); --i >= 0;) {
            
            logConfigurationDetail = (LogConfigurationDetail) installedComponentsList.get(i);
            configInformation.add(
                logConfigurationPersistence.readComponentConfigInfo(logConfigurationDetail));
        }
        return new LogConfigurationInfoList(configInformation);
    }

    /**
     * Saves the corresponding logging information for the desired component. It
     * also creates the audit entries if configured sucessfully.
     * 
     * @param configureLogInfo
     *            logging information to be updated.
     * @return <code>true</code> if saved successfully <code>false</code>
     *         otherwise.
     */
    public boolean saveServerComponent(LogConfigurationInfo configureLogInfo) {
        
        LOG.debug("Invoking saveServerComponent() in " + this.getClass().getName());
        
        boolean saveComponentResult = false;
        LogConfigurationValidator configValidator = new LogConfigurationValidator();
        LogConfigurationDetail validateDetail     =  
            getInstalledComponentInfo(configureLogInfo.getComponentSeq());
        
        if (configValidator.isValidConfigInfo(configureLogInfo)) {
            
            validateConfigInfoDetail(validateDetail, configureLogInfo.getComponentName());
            setValidLogConfigureInfo(configureLogInfo);

            LogConfigurationPersistence logConfigurationPersistence =
                new LogConfigurationPersistence();
            saveComponentResult = logConfigurationPersistence.saveConfigValues(configureLogInfo, 
                                                                               validateDetail);
            createAuditEntries();
        }
        return saveComponentResult;
    }

    /**
     * Returns the logging information of all installed server components.It
     * retrieves all the components defined in the context file and gets all the
     * components installed in the machine by checking the configuration file
     * existence.
     * 
     * @return list of installed components.(<code>LogConfigurationDetail</code>)
     */
    public List getAllInstalledComponents() {
        
        LOG.debug("Invoking getAllInstalledComponents() in " + this.getClass().getName());
        LogConfigurationDetailList logConfigurationDetailList = getLogConfigurationDetailList();
        
        LogConfigurationDetail configDetail;
        List availableComponents = new ArrayList();
        LogConfigurationValidator validator = new LogConfigurationValidator();
        List allComponentList = logConfigurationDetailList.getComponentList();
        
        for (int i = allComponentList.size(); --i >= 0;) {
            
            configDetail = (LogConfigurationDetail) allComponentList.get(i);
            if ((configDetail != null) && validator.isComponentInstalled(
                                                    configDetail.getLogFile())) {
                availableComponents.add(configDetail);
            }
        }
        return availableComponents;
    }

    /**
     * Returns a <code>LogConfigurationDetail</code> for the corresponding
     * componentSequence.It iterates through the list of components and
     * retrieves the component for the desired <code>componentSequence</code>
     * and checks for its configuration file existence.
     * 
     * @param componentSequence
     *            Sequence whose <code>LogConfigurationDetail</code> has to be
     *            retrieved.
     * @return <code>LogConfigurationDetail</code>. If the component is not
     *         installed it returns an <code>null</code> object.
     */
    private LogConfigurationDetail getInstalledComponentInfo(long componentSequence) {
        
        LOG.debug("Invoking getInstalledComponentInfo() in " + this.getClass().getName());
        LogConfigurationDetailList logConfigurationDetailList = getLogConfigurationDetailList();
        
        LogConfigurationDetail configDetail = null;
        LogConfigurationValidator validator = new LogConfigurationValidator();
        List installedComponent = logConfigurationDetailList.getComponentList();
        
        validateComponentsInstalled(installedComponent.size());
        
        for (int i = installedComponent.size(); --i >= 0;) {
            
            configDetail = (LogConfigurationDetail) installedComponent.get(i);
            if (componentSequence == Long.parseLong(configDetail.getComponentSeq())) {
                
                if (validator.isComponentInstalled(configDetail.getLogFile())) {
                    break;
                } else {
                    
                    LOG.debug(ConfigurationConstants.INVALID_COMPONENT_SEQ);
                    throw new ConfigureLogException(
                                                ConfigurationConstants.INVALID_COMPONENT_SEQ, 
                                                ClientErrorCodes.INVALID_COMPONENT_SEQ);
                }
            }
            configDetail = null;
        }
        return configDetail;
    }
    
    /**
     * Method responsible for retrieving the list of log files and other
     * required details (size, name etc.,) for all the components installed
     * in the machine.
     *
     * @param allComponentsList
     *                  List of server components installed in the machine.
     *
     * @return Details of the log files corresponding to the all the
     *         components installed.
     */
    public InfoDetailList getAllServerComponentLogList() {
        
        LOG.debug("Method getAllServerComponentLogList() invoked.");
        
        List allComponentsList = getAllInstalledComponents();
        List listViewLogInfoList = new ArrayList();
        LogConfigurationDetail logConfigurationDetail;

        for (int i = allComponentsList.size(); --i >= 0;) {
            
            logConfigurationDetail = (LogConfigurationDetail) allComponentsList.get(i);
            listViewLogInfoList.add(getServerComponentLogList(
                                    Long.parseLong(logConfigurationDetail.getComponentSeq())));
        }
        
        LOG.debug("Method getAllServerComponentLogList() executed sucessfully.");
        return new InfoDetailList(listViewLogInfoList);
    }
    
    /**
     * Method responsible for retrieving the list of log files and other
     * required details(size, name etc.,) based on the component sequence and
     * log configuration detail object.
     *
     * @param logConfigurationDetail
     *            logConfigurationDetail object that holds the details of
     *            the log file.
     * @param componentSeq
     *            Sequence of the component installed.
     *
     * @return Details of the log files corresponding to the components.
     */
     public ListViewLogInfoList getServerComponentLogList(long componentSeq) {

        LOG.debug("Method getServerComponentLogList() invoked.");
        List list = new ArrayList();
        String configuredLogFileName = StringUtilities.EMPTYSTRING;
        try {
            
            LogConfigurationDetail logConfigurationDetail = getInstalledComponentInfo(componentSeq);
            if (logConfigurationDetail != null) {
                
                ListViewLogInfoList listViewLogInfoList = 
                    new LogConfigurationPersistence().getLogFileName(logConfigurationDetail);
                List listViewLogsInfo = listViewLogInfoList.getListViewLogInfoList();
                ListViewLogInfo listViewLogInfo;
                for (Object listViewLogInfoObj : listViewLogsInfo) {
                    listViewLogInfo = (ListViewLogInfo) listViewLogInfoObj;
                    String logFileName = findLogFile(listViewLogInfo.getLogFileName())
                                                                    .getAbsolutePath();
                    File[] files = findLogFile(logFileName.substring(0, 
                                               logFileName.lastIndexOf(File.separator)))
                                                          .listFiles();
                    
                    int startIndex = logFileName.lastIndexOf(File.separator);
                    if (startIndex != -1) {
                        configuredLogFileName = logFileName.substring(startIndex + 1);
                    }
                    
                    for (int i = files.length; --i >= 0;) {
                        
                        if (files[i].isFile() 
                            && files[i].getName().startsWith(configuredLogFileName)) {
                            
                            listViewLogInfo = new ListViewLogInfo();
                            
                            listViewLogInfo.setComponentSequence(
                                       Integer.parseInt(logConfigurationDetail.getComponentSeq()));
                            listViewLogInfo.setLogFileName(files[i].getName());
                            listViewLogInfo.setLogFileSize(files[i].length());
                            listViewLogInfo.setLogFileDate(
                                    DateUtilities.formatISO8601(new Date(files[i].lastModified())));
                            listViewLogInfo.setComponentName(
                                    logConfigurationDetail.getComponentName());
                            listViewLogInfo.setComponentPath(logFileName);
                            
                            list.add(listViewLogInfo);
                        }
                    }
                }
            } else {
                throw new ConfigureLogException(ConfigurationConstants.ERROR_RETRIEVING_COMPONENTS,
                		                   ClientErrorCodes.NIL_COMPONENT_INSTALLED);
            }
        } catch (SecurityException exp) {
            
            LOG.error(ConfigurationConstants.SECURITY_EXCEPTION + exp);
            throw new ConfigureLogException(ConfigurationConstants.SECURITY_EXCEPTION,
                                           ClientErrorCodes.FILE_SECURITY);
        } catch (Exception exp) {
            
            LOG.error(ConfigurationConstants.ERROR_RETRIEVING_COMPONENTS + exp);
            throw new ConfigureLogException(ConfigurationConstants.ERROR_RETRIEVING_COMPONENTS,
                                           ClientErrorCodes.NIL_COMPONENT_INSTALLED);
        } 
        
        LOG.debug("Method getServerComponentLogList() executed sucessfully.");
        return new ListViewLogInfoList(list);
    }

    /**
     * Method used to check the availability of the log file in the required
     * path .
     *
     * @param fileName
     *            Name of the log file
     * @return Path of the log file.
     */
    public File findLogFile(String fileName) {
        
        LOG.debug("Method findlogFile() Invoked.");
        File file;
        try {
            
            if (!StringUtilities.isEmpty(fileName)) {
                file = new File(fileName);
            } else {
                
                LOG.debug(ConfigurationConstants.INVALID_FILE_NAME);
                throw new ConfigureLogException(ConfigurationConstants.INVALID_FILE_NAME,
                                               ClientErrorCodes.INVALID_FILE_NAME);
            }
            if ((file != null) && (file.isFile()) && (file.exists()) && file.canRead()) {
                LOG.debug("Log file :" + fileName + " found in the path :" + file.toString());
            }
        } catch (SecurityException secexp) {
            
            LOG.error(ConfigurationConstants.SECURITY_EXCEPTION + secexp);
            throw new ConfigureLogException(secexp , ClientErrorCodes.FILE_SECURITY);
        }
        LOG.debug("Method findlogFile() executed sucessfully.");
        return file;
    }
    
    /**
     * Method used to retrieve the path of the log file.
     *
     * @param logConfigurationDetail
     *            LogConfigurationDetail object holding the details of the log file.
     * @param fileName
     *            Name of the log file
     * @return log file path
     */
    public String findLogFilePath(long componentSequence, String fileName) {

        LOG.debug("Method findLogFilePath() Invoked.");
        String logFilePath = null;
        ListViewLogInfo listViewLogInfo;
        
        try {
            
            LogConfigurationDetail logConfigurationDetail = 
                                   getInstalledComponentInfo(componentSequence);
            if (logConfigurationDetail != null) {

                listViewLogInfo =
                    new LogConfigurationPersistence().getLogFileName(logConfigurationDetail)
                                                     .getListViewLogInfoList()
                                                     .get(0);
                File file = this.findLogFile(listViewLogInfo.getLogFileName());
                
                if (file != null) {
                    
                    logFilePath = file.getAbsolutePath();
                    if (!StringUtilities.isEmpty(logFilePath)
                        && logFilePath.lastIndexOf(File.separator) != -1) {
                        
                        logFilePath = logFilePath.substring(0, logFilePath
                                                              .lastIndexOf(File.separator) + 1);
                        logFilePath = logFilePath + fileName;
                    }
                }
            }
        } catch (Exception exp) {
            
            LOG.error(ConfigurationConstants.SECURITY_EXCEPTION + exp);
            throw new ConfigureLogException(ConfigurationConstants.ERROR_RETRIEVING_PATH);
        }
        
        LOG.debug("Method findLogFilePath() executed sucessfully.");
        return logFilePath;
    }
    
    /**
     * Set the valid values to maxLogSize and maxLogFiles
     * 
     * @param logConfigureInfo
     *        holds the information about the log configuration file  
     */
    private void setValidLogConfigureInfo(LogConfigurationInfo logConfigureInfo) {
        
        logConfigureInfo.setMaxLogFiles(
                         String.valueOf(Long.parseLong(logConfigureInfo.getMaxLogFiles())));
        int length = logConfigureInfo.getMaxLogSize().length();
        String fileUnit = logConfigureInfo.getMaxLogSize().substring(length - 2, length);
        String fileSize = logConfigureInfo.getMaxLogSize().substring(0, length - 2);
        logConfigureInfo.setMaxLogSize(String.valueOf(Long.parseLong(fileSize)) + fileUnit);
    }
    
    /**
     * Validate the log configuration detial for null and check the componenet name.
     *  
     * @param validateDetail
     *        LogConfigurationDetail which contains the details of log configuration.
     *          
     * @param componenetName
     *        name of the component.  
     */
    private void validateConfigInfoDetail(LogConfigurationDetail validateDetail, 
                                          String componenetName) {
        if (validateDetail == null) {
        
            LOG.debug(ConfigurationConstants.INVALID_COMPONENT_SEQ);
            throw new ConfigureLogException(ConfigurationConstants.INVALID_COMPONENT_SEQ,
                                            ClientErrorCodes.INVALID_COMPONENT_SEQ);
        } else if (!validateDetail.getComponentName().equals(componenetName)) {
        
            LOG.debug(ConfigurationConstants.INVALID_COMPONENT_NAME);
            throw new ConfigureLogException(ConfigurationConstants.INVALID_COMPONENT_NAME,
                                            ClientErrorCodes.INVALID_COMPONENT_NAME);
        }
    }
    
    /**
     * Check whether any componenets installed in this machine.
     * 
     * @param size
     *        number of components installed in this machine.  
     */
    private void validateComponentsInstalled(int size) {
        
        if (size == 0) {
            
            LOG.debug(ConfigurationConstants.NIL_COMPONENETS_INSTALLED);
            throw new ConfigureLogException(ConfigurationConstants.NIL_COMPONENETS_INSTALLED,
                                            ClientErrorCodes.NIL_COMPONENT_INSTALLED);
        }
    }

    /**
     * Creates Audit Entries for <code>LogConfigurationService</code> with a
     * success comment.
     */
    private static void createAuditEntries() {
        
        AuditEvent auditEvent = 
                   LogConfigurationAuditManager.prepareLogAuditEvent(0L, AUDIT_COMMENT);
       LogConfigurationAuditManager.createAuditEntry(auditEvent);
    }
    
    /**
     * Get the LogConfigurationDetailList instance.
     * @return logConfigurationDetailList
     */
    private LogConfigurationDetailList getLogConfigurationDetailList() {
        
        return (LogConfigurationDetailList) SpringUtilities.getInstance()
                                                           .getBeanFactory()
                                                           .getBean(COMPONENT_LIST);
    }
}
