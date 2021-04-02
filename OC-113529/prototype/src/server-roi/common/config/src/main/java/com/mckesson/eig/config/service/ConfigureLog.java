/*
 * Copyright 2007-2009 McKesson Corporation and/or one of its subsidiaries. 
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

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.mckesson.eig.config.model.InfoDetailList;
import com.mckesson.eig.config.model.ListViewLogDetailList;
import com.mckesson.eig.config.model.ListViewLogInfoList;
import com.mckesson.eig.config.model.LogConfigurationInfo;
import com.mckesson.eig.config.model.LogConfigurationInfoList;

/**
 * Interfaces that declares method for log configuration.
 * 
 */
@WebService(name            = "LogConfigurationPortType_v1_0",
            targetNamespace = "http://eig.mckesson.com/wsdl/configureLog-v1")
@SOAPBinding(style          = Style.DOCUMENT,
             use            = Use.LITERAL,
             parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ConfigureLog {

    /**
     * Method declaration. To be implemented by class that implements this
     * interface.Returns list of log configuration information for all the 
     * installed components.
     * 
     * @return log configuration information for the installed server
     *         components.
     */
    @WebMethod(operationName = "getAllServerComponentsConfigInfo",
    	action = "http://eig.mckesson.com/wsdl/configureLog-v1/getAllServerComponentsConfigInfo")
    @WebResult(name = "logConfigurationInfoList")
    LogConfigurationInfoList getAllServerComponentsConfigInfo();

    /**
     * Method declaration. To be implemented by class that implements this
     * interface.Configures the log information.
     * 
     * @param logConfigurationInfo
     *            log configuration information.
     * @return <code>true</code> if saved successfully <code>false</code>
     *         otherwise.
     */
    @WebMethod(operationName = "saveServerComponentConfigInfo", 
          action = "http://eig.mckesson.com/wsdl/configureLog-v1/saveServerComponentConfigInfo")
    @WebResult(name = "saveConfigResult")
    boolean saveServerComponentConfigInfo(
            @WebParam(name = "logConfigurationInfo") LogConfigurationInfo logConfigurationInfo);
    
    /**
     * Method declaration. To be implemented by class that implements this
     * interface. Returns ListViewLogDetailList of all server components
     * installed in the machine.
     *
     * @return ListViewLogDetailList
     *               listViewLogDetailList for the installed server components.
     */
    @WebMethod(operationName = "getAllServerComponentList",
          action = "http://eig.mckesson.com/wsdl/configureLog-v1/getAllServerComponentList")
    @WebResult(name = "listViewLogDetailList")
    ListViewLogDetailList getAllServerComponentList();
    
    /**
     * Method declaration. To be implemented by class that implements this
     * interface.Returns list of log information for specified server
     * component.
     *
     * @param componentSequence
     *            Sequence corresponding to the component installed
     *            in the machine.
     * @return ListViewLogInfoList
     *             log details for the corresponding installed server
     *             component.
     */
    @WebMethod(operationName = "getServerComponentLogList", 
          action = "http://eig.mckesson.com/wsdl/configureLog-v1/getServerComponentLogList")
    @WebResult(name = "listViewLogInfoList")
    ListViewLogInfoList getServerComponentLogList(
            @WebParam(name = "componentSeq") long componentSequence);
    
    /**
     * Method declaration. To be implemented by class that implements this
     * interface.Returns list of log information for all the installed server
     * components.
     *
     * @return InfoDetailList
     *              log details for all installed server components.
     */
    @WebMethod(operationName = "getAllServerComponentLogList", 
          action = "http://eig.mckesson.com/wsdl/configureLog-v1/getAllServerComponentLogList")
    @WebResult(name = "infoDetailList")
    InfoDetailList getAllServerComponentLogList();
}
