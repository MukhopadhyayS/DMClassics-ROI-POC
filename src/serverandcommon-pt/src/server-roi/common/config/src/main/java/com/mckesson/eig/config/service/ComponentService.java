/*
 * Copyright 2008-2009 McKesson Corporation and/or one of its subsidiaries. 
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

import com.mckesson.eig.utility.components.model.ComponentInfo;
import com.mckesson.eig.utility.components.model.ComponentList;

/**
 * This interface declares the various methods that are used to fetch the details of the 
 * components that are deployed in the server. 
 * 
 * @author Sahul Hameed Y
 * @date   Apr 8, 2008
 * @since  HECM 1.0; Apr 8, 2008
 */
@WebService(name            = "ComponentePortType_v1_0",
        	targetNamespace = "http://eig.mckesson.com/wsdl/configservercomponents-v1")
@SOAPBinding(style          = Style.DOCUMENT,
    	     use            = Use.LITERAL,
        	 parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ComponentService {
    
    /**
     * This method is used to return all the components which are deployed in the current server
     * along with the details of the component.
     *  
     * @return componentList
     *         list of component details.         
     */
    @WebMethod(operationName = "getAllComponents", 
    		action = "http://eig.mckesson.com/wsdl/configservercomponents-v1/getAllComponents")
    @WebResult(name = "components")
    ComponentList getAllComponents();
    
    /**
     * This method is used to fetch the details of the specified component.
     * 
     * @param componentID
     *        specific component ID.
     *          
     * @return componentInfo
     *         Component information for the particular component ID. 
     */
    @WebMethod(operationName = "getComponent", 
    		action = "http://eig.mckesson.com/wsdl/configservercomponents-v1/getComponent")
    @WebResult(name = "componentInfo")
    ComponentInfo getComponent(@WebParam(name = "componentID") String componentID);
}
