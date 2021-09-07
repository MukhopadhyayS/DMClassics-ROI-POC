/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
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

package com.mckesson.eig.workflow.worklist.service;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.mckesson.eig.workflow.service.WorkflowService;

/**
 * This interface declares the various methods that are used to fetch the details of the 
 * components that are deployed in the server. 
 * 
 * @author Sahul Hameed Y
 * @date   Apr 7, 2008
 * @since  HECM 1.0; Apr 7, 2008
 */
@WebService(name            = "ComponentPortType_v1_0",
            targetNamespace = "http://eig.mckesson.com/wsdl/workflowcomponent-v1")
@SOAPBinding(style          = Style.DOCUMENT,
             use            = Use.LITERAL,
             parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ComponentService extends WorkflowService {
    
    /**
     * This will get the component details (component XML as String) of Workflow component, 
     * this will return with updated component version.
     * 
     * @return componentDetails
     *         component details of Workflow component. 
     */
    @WebMethod(operationName = "getComponentDetails",
               action = "http://eig.mckesson.com/wsdl/workflowcomponent-v1/getComponentDetails")
    @WebResult(name   = "componentDetails")
    String getComponentDetails();

}
