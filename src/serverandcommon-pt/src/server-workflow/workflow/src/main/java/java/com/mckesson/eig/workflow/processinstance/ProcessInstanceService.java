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

package com.mckesson.eig.workflow.processinstance;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.mckesson.eig.workflow.process.api.ProcessInstance;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.VariableList;

/**
 * @author McKesson
 * @date   Feb 12, 2009
 * @since  HECM 2.0; Feb 12, 2009
 */
@WebService(name = "ProcessInstancePortType_v1_0",
            targetNamespace = "http://eig.mckesson.com/wsdl/processinstance-v1")
@SOAPBinding(style = Style.DOCUMENT,
             use = Use.LITERAL,
             parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ProcessInstanceService {

	/**
	 *
	 * @param userActor
	 * @param aclActors
	 * @param processId
	 * @param variableList
	 * @return
	 */
    @WebMethod(operationName = "startProcessInstance",
               action = "http://eig.mckesson.com/wsdl/processinstance-v1/startProcessInstance")
    @WebResult(name = "processInstance")
    void startProcessInstance(
            @WebParam(name = "actor") Actor userActor,
            @WebParam(name = "actors") Actors aclActors,
            @WebParam(name = "processId") long processId,
            @WebParam(name = "variableList") VariableList variableList);


    @WebMethod(operationName = "notifyProcessInstance",
            action = "http://eig.mckesson.com/wsdl/processinstance-v1/notifyProcessInstance")
    void notifyProcessInstance(
            @WebParam(name = "notificationType") String notificationType,
            @WebParam(name = "actor") Actor userActor,
            @WebParam(name = "actors") Actors aclActors,
            @WebParam(name = "sourceProcInstId") long sourceProcInstId,
            @WebParam(name = "notifiedProcInstId") long notifiedProcInstId,
            @WebParam(name = "token") long token);


    /**
     *
     * @param userActor
     * @param aclActors
     * @param processInstanceId
     * @return
     */
    @WebMethod(operationName = "getProcessInstance",
            action = "http://eig.mckesson.com/wsdl/processinstance-v1/getProcessInstance")
    @WebResult(name = "processInstance")
    ProcessInstance getProcessInstance(
            @WebParam(name = "actor") Actor userActor,
            @WebParam(name = "actors") Actors aclActors,
            @WebParam(name = "processInstanceId") long processInstanceId);

    /**
     *
     * @param userActor
     * @param aclActors
     * @param processInstanceId
     */
     @WebMethod(operationName = "resumeProcessInstance",
            action = "http://eig.mckesson.com/wsdl/processinstance-v1/resumeProcessInstance")
	 void resumeProcessInstance(
	         @WebParam(name = "actor") Actor userActor,
	         @WebParam(name = "actors") Actors aclActors,
	         @WebParam(name = "processInstanceId") long processInstanceId);

    /**
     *
     * @param userActor
     * @param aclActors
     * @param processInstanceId
     */
     @WebMethod(operationName = "suspendProcessInstance",
            action = "http://eig.mckesson.com/wsdl/processinstance-v1/suspendProcessInstance")
	 void suspendProcessInstance(
	         @WebParam(name = "actor") Actor userActor,
	         @WebParam(name = "actors") Actors aclActors,
	         @WebParam(name = "processInstanceId") long processInstanceId);

    /**
     *
     * @param userActor
     * @param aclActors
     * @param processInstanceId
     */
     @WebMethod(operationName = "deleteProcessInstance",
            action = "http://eig.mckesson.com/wsdl/processinstance-v1/deleteProcessInstance")
	 void deleteProcessInstance(
	         @WebParam(name = "actor") Actor userActor,
	         @WebParam(name = "actors") Actors aclActors,
	         @WebParam(name = "processInstanceId") long processInstanceId);

    /**
     *
     * @param userActor
     * @param aclActors
     * @param processInstanceId
     */
     @WebMethod(operationName = "terminateProcessInstance",
            action = "http://eig.mckesson.com/wsdl/processinstance-v1/terminateProcessInstance")
	 void terminateProcessInstance(
	         @WebParam(name = "actor") Actor userActor,
	         @WebParam(name = "actors") Actors aclActors,
	         @WebParam(name = "processInstanceId") long processInstanceId);
}
