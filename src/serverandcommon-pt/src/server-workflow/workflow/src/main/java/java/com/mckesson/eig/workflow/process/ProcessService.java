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
package com.mckesson.eig.workflow.process;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.mckesson.eig.workflow.process.api.Process;
import com.mckesson.eig.workflow.process.api.ActionHandlerList;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.DomainList;
import com.mckesson.eig.workflow.service.WorkflowService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;


/**
 * @author McKesson Corporation
 * @version 1.0
 * @created 23-January-2009 9:57:00 AM
 */

@WebService(name = "ProcessPortType_v1_0",
    targetNamespace = "http://eig.mckesson.com/wsdl/process-v1")
@SOAPBinding(style = Style.DOCUMENT,
    use = Use.LITERAL,
    parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ProcessService extends WorkflowService {

    @WebMethod(operationName = "createProcess",
        action = "http://eig.mckesson.com/wsdl/process-v1/createProcess")
    void createProcess(@WebParam(name = "assignedTo")    Actor assignedTo,
                       @WebParam(name = "processDetails") Process processDetails);

    @WebMethod(operationName = "deployProcess",
            action = "http://eig.mckesson.com/wsdl/process-v1/deployProcess")
        void deployProcess(@WebParam(name = "assignedTo")    Actor assignedTo,
                           @WebParam(name = "processDetails") Process processDetails);

    @WebMethod(operationName = "saveProcess",
            action = "http://eig.mckesson.com/wsdl/process-v1/saveProcess")
        void saveProcess(@WebParam(name = "assignedTo")    Actor assignedTo,
                           @WebParam(name = "processDetails") Process processDetails);

    @WebMethod(operationName = "validateProcess",
            action = "http://eig.mckesson.com/wsdl/process-v1/validateProcess")
        void validateProcess(@WebParam(name = "assignedTo")    Actor assignedTo,
                           @WebParam(name = "processDetails") Process processDetails);

    @WebMethod(operationName = "getProcess",
            action = "http://eig.mckesson.com/wsdl/process-v1/getProcess")
    @WebResult(name = "Process")
    Process getProcess(@WebParam(name = "assignedTo")    Actor assignedTo,
                        @WebParam(name = "processId") long processId);

    @WebMethod(operationName = "getActionHandlers",
            action = "http://eig.mckesson.com/wsdl/process-v1/getActionHandlers")
    @WebResult(name = "ActionHandlerList")
    ActionHandlerList getActionHandlers(@WebParam(name = "domainId") long domainId);

    @WebMethod(operationName = "getDomains",
            action = "http://eig.mckesson.com/wsdl/process-v1/getDomains")
            @WebResult(name = "DomainList")
    DomainList getDomains();


}
