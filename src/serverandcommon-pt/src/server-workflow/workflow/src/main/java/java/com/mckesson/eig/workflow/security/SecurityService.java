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
package com.mckesson.eig.workflow.security;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.mckesson.eig.workflow.security.api.SecurityProperties;
import com.mckesson.eig.workflow.service.WorkflowService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;


/**
 * @author McKesson Corporation
 * @version 1.0
 * @created 19-March-2009 8:57:00 AM
 */

@WebService(name = "SecurityPortType_v1_0",
    targetNamespace = "http://eig.mckesson.com/wsdl/security-v1")
@SOAPBinding(style = Style.DOCUMENT,
    use = Use.LITERAL,
    parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface SecurityService extends WorkflowService {

    @WebMethod(operationName = "logon",
        action = "http://eig.mckesson.com/wsdl/security-v1/logon")
   @WebResult(name = "SecurityProperties")
    SecurityProperties logon(@WebParam(name = "application")    String application,
                       @WebParam(name = "environment") String environment);


    @WebMethod(operationName = "logoff",
            action = "http://eig.mckesson.com/wsdl/security-v1/logoff")
     void logoff();
}
