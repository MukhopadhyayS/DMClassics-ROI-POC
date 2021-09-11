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
package com.mckesson.eig.config.service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.config.constants.ConfigurationConstants;
import com.mckesson.eig.config.controller.ConfigureLogController;
import com.mckesson.eig.config.exception.ConfigureLogException;
import com.mckesson.eig.config.model.InfoDetailList;
import com.mckesson.eig.config.model.ListViewLogDetail;
import com.mckesson.eig.config.model.ListViewLogDetailList;
import com.mckesson.eig.config.model.ListViewLogInfoList;
import com.mckesson.eig.config.model.LogConfigurationDetail;
import com.mckesson.eig.config.model.LogConfigurationInfo;
import com.mckesson.eig.config.model.LogConfigurationInfoList;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * The implementation class containing methods to get the server components
 * logging information and to configure the same.
 * 
 */
@WebService(
        name              = "LogConfigurationPortType_v1_0",
        portName          = "LogConfigurationPort_v1_0",
        serviceName       = "LogConfigurationService_v1_0",
        targetNamespace   = "http://eig.mckesson.com/wsdl/configureLog-v1",
        endpointInterface = "com.mckesson.eig.config.service.ConfigureLog")
public class ConfigureLogService implements ConfigureLog {

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( ConfigureLogService.class);

    /**
     * The getAllServerComponentConfigInfo service retrieves all the installed
     * server component's log information. This method invokes the controller to
     * enable the processing of required information in order that a list of
     * LogConfigurationInfo objects are returned.
     * 
     * @return LogConfigurationInfoList installed components log information.
     */
    public LogConfigurationInfoList getAllServerComponentsConfigInfo() {
        
        LOG.debug("Method getAllServerComponentsConfigInfo() invoked.");
        
        ConfigureLogController logConfigurationController = getLogConfigurationController();
        
        LOG.debug("Method getAllServerComponentsConfigInfo() executed sucessfully.");
        return logConfigurationController.getAllServerComponentsConfigInfo();
    }

    /**
     * 
     * Configures logging information for the selected component. This service
     * takes in the LogConfigurationInfo object as input and persists the
     * obtained information to the given location. On occurrence of errors during
     * processing, a LogConfigurationException is thrown. This method also
     * returns a boolean indicating the success or failure of save operation.
     * 
     * @param logConfigurationInfo
     *            log configuration information.
     * @return <code>true</code> if saved successfully <code>false</code>
     *         otherwise.
     */
    public boolean saveServerComponentConfigInfo(LogConfigurationInfo logConfigurationInfo) {
        
        boolean savedSuccessfully = false;
        validateLogConfigurationInfo(logConfigurationInfo);
            
        savedSuccessfully = 
                        getLogConfigurationController().saveServerComponent(logConfigurationInfo);
        return savedSuccessfully;
    }
    
    /**
     * Method responsible for retrieving the list of log files and other
     * required details based on the component sequence.This method invokes the
     * controller to get the list of ListViewLogInfo objects holding the details
     * corresponding to the log files corresponding to the components.
     *
     * @param componentSequence -
     *            Sequence of the component installed.
     * @return Details of the log files corresponding to the components installed.
     */
    public ListViewLogInfoList getServerComponentLogList(long componentSequence) {
        
        LOG.debug("Method getServerComponentLogList() invoked.");
        
        validateComponentSequence(componentSequence);
        ListViewLogInfoList listViewLogInfoList = 
            getLogConfigurationController().getServerComponentLogList(componentSequence);
        
        LOG.debug("Method getServerComponentLogList() executed sucessfully.");
        return listViewLogInfoList;
    }
    
    /**
     * Returns the information of all installed server components.It retrieves
     * all the components defined in the context file and gets all the
     * components installed in the machine by checking the configuration file
     * existance.
     *
     * @return ListViewLogDetailList list of installed components.
     */
    public ListViewLogDetailList getAllServerComponentList() {
        
        LOG.debug("Method getAllServerComponentList() invoked.");
        
        LogConfigurationDetail logConfigurationDetail;
        List resultList = new ArrayList();
        ListViewLogDetail listViewLogDetail;
        List listViewLogInfo = getLogConfigurationController().getAllInstalledComponents();

        for (int i = listViewLogInfo.size(); --i >= 0;) {
            
            logConfigurationDetail = (LogConfigurationDetail) listViewLogInfo.get(i);
            listViewLogDetail      = new ListViewLogDetail();
            
            listViewLogDetail.setComponentName(logConfigurationDetail.getComponentName());
            listViewLogDetail.setComponentSeq(
                                        Long.parseLong(logConfigurationDetail.getComponentSeq()));
            resultList.add(listViewLogDetail);
        }
        
        LOG.debug("Method getAllServerComponentList() executed sucessfully.");
        return new ListViewLogDetailList(resultList);
    }
    
    /**
     * Method responsible for retrieving the list of log files and other
     * required details for all the server components installed in the machine.
     *
     * @return Details of the log files corresponding to all the components.
     */
    public InfoDetailList getAllServerComponentLogList() {
        
        LOG.debug("Method getAllServerComponentLogList() invoked.");
        
        InfoDetailList infoDetailList = 
                       getLogConfigurationController().getAllServerComponentLogList();
        
        LOG.debug("Method getAllServerComponentLogList() executed sucessfully.");
        return infoDetailList;
    }
    
    /**
     * 
     * @see com.mckesson.eig.config.service.ListViewLog#getLogFilePath(long, String)
     */
    public String getLogFilePath(long componentSequence, String fileName) {
        
        LOG.debug("Method getLogFilePath(componentSequence, fileName) invoked.");
        
        String filePath = 
               getLogConfigurationController().findLogFilePath(componentSequence, fileName);
        
        LOG.debug("Method getLogFilePath(componentSequence, fileName) executed sucessfully.");
        return filePath;
    }
    
 
    /**
     * Check null for log configuration config info.
     * 
     * @param logConfigurationInfo
     *        holds the information of log configurations.  
     */
    private void validateLogConfigurationInfo(LogConfigurationInfo logConfigurationInfo) {
        
        if (logConfigurationInfo == null) {
            
            LOG.debug(ConfigurationConstants.EMPTY_LOG_INFORMATION);
            throw new ConfigureLogException(ConfigurationConstants.EMPTY_LOG_INFORMATION,
                                            ClientErrorCodes.EMPTY_LOG_INFORMATION);
        }
    }
    
    /**
     * Check for positive value of component sequence.
     * 
     * @param componentSequence
     *        unique id for to identify the component.  
     */
    private void validateComponentSequence(long componentSequence) {
       
        if (componentSequence < 0) {
            throw new ConfigureLogException(ConfigurationConstants.INVALID_COMPONENT_SEQ,
                                            ClientErrorCodes.INVALID_COMPONENT_SEQ);
        }
    }
    
    /**
     * get the LogConfigurationController instance.
     * @return logConfigurationController
     */
    private ConfigureLogController getLogConfigurationController() {
        
        return (ConfigureLogController) SpringUtilities.getInstance()
                                                       .getBeanFactory()
                                                       .getBean("Configuration_Controller");
    }
}
