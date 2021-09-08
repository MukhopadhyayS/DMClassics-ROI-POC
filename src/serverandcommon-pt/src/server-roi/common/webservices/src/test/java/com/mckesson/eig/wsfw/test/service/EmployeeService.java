/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. All
 * Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of McKesson
 * Information Solutions and is protected under United States and international
 * copyright and other intellectual property laws. Use, disclosure,
 * reproduction, modification, distribution, or storage in a retrieval system in
 * any form or by any means is prohibited without the prior express written
 * permission of McKesson Information Solutions.
 */
package com.mckesson.eig.wsfw.test.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.mckesson.eig.wsfw.EIGConstants;


/**
 * @author OFS
 * @date   Dec 13, 2007
 */
@WebService(
name              = "EmployeePortType",
targetNamespace   = EIGConstants.TYPE_NS_V1)
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public interface EmployeeService {

    @WebMethod(operationName = "getEmployeeInfo")
    @WebResult(name = "employee")
    Employee getEmployeeInfo(@WebParam(name = "employeeId") long employeeId);

    @WebMethod(operationName = "createEmployee")
    @WebResult(name = "isCreated")
    boolean createEmployee(@WebParam(name = "employee") Employee emp);
    
    @WebMethod(operationName = "throwUsernameTokenException")
    @WebResult(name = "employee")
    Employee throwUsernameTokenException();
    
    @WebMethod(operationName = "throwError")
    @WebResult(name = "employee")
    Employee throwError();

}
