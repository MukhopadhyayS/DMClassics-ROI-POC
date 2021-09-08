/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries.
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

import java.util.HashMap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.mckesson.eig.config.model.ApplicationSetting;
import com.mckesson.eig.config.model.ApplicationSettingList;


/**
 * @author kayalvizhik
 * @date   Mar 25, 2009
 * @since  HECM 2.0; Mar 25, 2009
 */
@WebService(name = "ApplicationSettingPortType_v1_0",
            targetNamespace = "http://eig.mckesson.com/wsdl/applicationsetting-v1")
@SOAPBinding(style = Style.DOCUMENT,
             use = Use.LITERAL,
             parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ApplicationSettingService extends ConfigService {

    /**
     * Retrieving the global setting information in which the configuration
     * data irrespective of the applications like email server IP, port No etc are present
     *
     * @return applicationSetting
     */
    @WebMethod(operationName = "getGlobalSetting",
               action = "http://eig.mckesson.com/wsdl/applicationsetting-v1/getGlobalSetting")
    @WebResult(name = "applicationSetting")
    ApplicationSetting getGlobalSetting();

    /**
     * Updating/ saving the global setting  information in which the configuration data
     * irrespective of the applications like email server IP, port No etc are present
     *
     * @return applicationSetting
     *
     */
    @WebMethod(operationName = "updateGlobalSetting",
               action = "http://eig.mckesson.com/wsdl/applicationsetting-v1/updateGlobalSetting")
    @WebResult(name = "applicationSetting")
    ApplicationSetting updateGlobalSetting(
            @WebParam(name = "applicationSetting") ApplicationSetting applicationSetting);


    /**
     * Retrieving  the list of application configuration data which are specific to the
     * applications like email sender name, email sender mail Id etc are present
     *
     * @return applicationSettingList
     *         list of application settings
     */
    @WebMethod(operationName = "getApplicationSettings",
               action = "http://eig.mckesson.com/wsdl/applicationsetting-v1/getApplicationSettings")
    @WebResult(name = "applicationSettingList")
    ApplicationSettingList getApplicationSettings();

    /**
     * Updating/ saving  application configuration data which are specific to the applications
     * like email sender name, email sender mail Id etc are present
     *
     * @return applicationSetting
     *         updated applicationSetting
     */
    @WebMethod(operationName = "updateApplicationSetting",
             action = "http://eig.mckesson.com/wsdl/applicationsetting-v1/updateApplicationSetting")
    @WebResult(name = "applicationSetting")
    ApplicationSetting updateApplicationSetting(
            @WebParam(name = "applicationSetting") ApplicationSetting applicationSetting);

    /**
     * API Method (not exposed in Webservice) for retrieving the global setting information in
     * which the configuration data irrespective of the applications like email server IP,
     * port No etc are present. The setting information would be given to the caller  in the
     * form of key and value pair
     *
     * @return globalSettingInfoMap
     *         returns the globalSettingInfoMap with settings name as key and the
     *         corresponding value
     *
     */
    HashMap<String, HashMap<String, String>> getGlobalSettingInfo();

    /**
     * API Method (not exposed in Webservice)  for retrieving  the application configuration data
     * for the given application id which are specific to the applications like email sender name,
     * email sender mail Id etc are present.The setting information would be given to the caller
     * in the form of key and value pair
     *
     * @param applicationId
     *        unique identification of the application
     *
     * @return applicationSettingMap
     *         returns the applicationSettingMap with settings name as key and the
     *         corresponding value
     *
     */
    HashMap<String, HashMap<String, String>> getApplicationSetting(long applicationId);

    /**
     * API Method (not exposed in Webservice)  for retrieving  the all application settings
     * data  like email sender name, email sender mail Id etc.The setting information would be
     * given to the caller in the form of key and value pair.
     *
     * @return allApplicationSettingMap
     *         returns the allApplicationSettingMap with application name as key and the
     *         corresponding settings value
     */
    HashMap<String, HashMap<String, HashMap<String, String>>> getAllApplicationSettings();
}
