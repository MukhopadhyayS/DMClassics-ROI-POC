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

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.VariableList;
import com.mckesson.eig.workflow.process.api.ProcessInfoList;

/**
 * @author sahuly
 * @date   Feb 10, 2009
 * @since  HECM 2.0; Feb 10, 2009
 */
@WebService(name = "ProcessListPortType_v1_0",
            targetNamespace = "http://eig.mckesson.com/wsdl/processlist-v1")
@SOAPBinding(style = Style.DOCUMENT,
             use = Use.LITERAL,
             parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ProcessListService {

    /**
     * This method returns the list of available process irrespective of the domain.
     * Based on the filter property it will returns the list of process of type manual,
     * auto or both and also it will returns only the active processes based on the
     * canIncludeExpired value.
     *
     * @param applicationId
     *        Unique application ID
     *
     * @param canIncludeExpired
     *        Whether to list the expired process or only the active process
     *
     * @param filterByProperties
     *        Holding the key,value like triggerType=Both.Based on this, return the list of process
     */
    @WebMethod(operationName = "getAllProcesses",
               action = "http://eig.mckesson.com/wsdl/processlist-v1/getAllProcesses")
    @WebResult(name = "processInfoList")
    ProcessInfoList getAllProcesses(
                            @WebParam(name = "applicationId") int applicationId,
                            @WebParam(name = "canIncludeExpired") boolean canIncludeExpired,
                            @WebParam(name = "variableList") VariableList variableList);

    /**
     * This Method returns the list of process which belongs to the current actor
     *
     * @param applicationID
     *        Unique application ID
     *
     * @param assignedTo
     *        The assigned actor of the process
     */
    @WebMethod(operationName = "getAssignedProcesses",
               action = "http://eig.mckesson.com/wsdl/processlist-v1/getAssignedProcesses")
    @WebResult(name = "processInfoList")
    ProcessInfoList getAssignedProcesses(@WebParam(name = "applicationId") int applicationID,
                                     @WebParam(name = "assignedTo")    Actor assignedTo);

    /**
     * This Method will assign the specified actor to the list of specified processes
     *
     * @param assignedTo
     *        actor need to associate with list of specified processes
     *
     * @param processListIds
     *        list of process unique IDs
     */
    @WebMethod(operationName = "updateProcessAssignements",
               action = "http://eig.mckesson.com/wsdl/processlist-v1/updateProcessAssignements")
    void updateProcessAssignments(@WebParam(name = "assignedTo") Actor assignedTo,
                                   @WebParam(name = "processListIds") List<Long> processListIds);


    /**
     * Returns a list of all applicable processes which are assigned to the list of users & groups
     * specified by the assignedTo parameter. It is also specific to the list of domains
     * (specified by the ownedBy parameter).It could be further filtered out by the
     * filterByProperties parameter which has has a key, value pair used to filter out processes
     * basis its attributes.
     *
     * @param ownedBy
     *        list of domain Actors
     *
     * @param assignedTo
     *        list of users & groups Actors
     *
     * @param filterByProperties
     *        Holding the key,value like triggerType=Both.Based on this, return the list of process
     */
    @WebMethod(operationName = "getProcesses",
            action = "http://eig.mckesson.com/wsdl/processlist-v1/getProcesses")
    @WebResult(name = "processInfoList")
    ProcessInfoList getProcesses(
                        @WebParam(name = "ownedBy") Actors ownedBy,
                		@WebParam(name = "assignedTo") Actors assignedTo,
                	    @WebParam(name = "filterByProperties") VariableList filterByProperties);


    /**
     * This method returns the list of processes Owned by the list of domains.
     * Based on the filter property it will returns the list of process of type manual,
     * auto or both and also it will return only the active/ expired processes based on the
     * canIncludeExpired value.
     *
     * @param applicationId
     *        Unique application ID
     *
     * @param canIncludeExpired
     *        Whether to list the expired process or only the active process
     *
     * @param ownedBy
     *        List of domain Actors
     *
     * @param variableList
     *        Holding the key,value like triggerType=Both.Based on this, return the list of process
     */
    @WebMethod(operationName = "getOwnedProcesses",
               action = "http://eig.mckesson.com/wsdl/processlist-v1/getOwnedProcesses")
    @WebResult(name = "processInfoList")
    ProcessInfoList getOwnedProcesses(
                            @WebParam(name = "applicationId") int applicationId,
                            @WebParam(name = "canIncludeExpired") boolean canIncludeExpired,
                            @WebParam(name = "ownedBy") Actors ownedBy,
                            @WebParam(name = "variableList") VariableList variableList);


    /**
     * This method returns the list of ProcessInfo Owned by the list of domains.
     * Based on the filter property it will returns the list of process info for
     * containing process type either manual, auto or both.  The returned list
     * will include active or both active and expired processes based on the
     * canIncludeExpired flag.
     *
     * @param applicationId
     *        Unique application ID
     *
     * @param canIncludeExpired
     *        Whether to list the expired process or only the active process
     *
     * @param ownedBy
     *        List of domain Actors
     *
     * @param variableList
     *        Holding the key,value like triggerType=Both.Based on this, return the list of process
     */
    @WebMethod(operationName = "getOwnedProcessesInfo",
               action = "http://eig.mckesson.com/wsdl/processlist-v1/getOwnedProcessesInfo")
    @WebResult(name = "processInfoList")
    ProcessInfoList getOwnedProcessesInfo(
                            @WebParam(name = "applicationId") int applicationId,
                            @WebParam(name = "canIncludeExpired") boolean canIncludeExpired,
                            @WebParam(name = "ownedBy") Actors ownedBy,
                            @WebParam(name = "variableList") VariableList variableList);
}
