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
package com.mckesson.eig.wsfw.test.service;

import javax.jws.WebService;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.wsfw.exception.UsernameTokenException;
import com.mckesson.eig.wsfw.test.BaseValidator;

/**
 * @author OFS
 * @date   Dec 13, 2008
 */
@WebService(
name              = "EmployeePortType",
portName          = "employee",
serviceName       = "EmployeeService",
targetNamespace   = "urn:eig.mckesson.com",
endpointInterface = "com.mckesson.eig.wsfw.test.service.EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {

    private static final OCLogger LOG = new OCLogger(EmployeeServiceImpl.class);

    /**
     * Instantiates this implementation of business service.
     */
    public EmployeeServiceImpl() {
        super();
    }

    public Employee getEmployeeInfo(long employeeId) {

        final String logSourceMethod = "getEmployeeInfo(employeeId)";
        LOG.debug(logSourceMethod + ">>Start");

        try {

            validateEmployee(employeeId);
            Employee emp = new Employee();
            emp.setId(1);
            emp.setEmployeeNumber("1");
            emp.setFirstName("mck");
            emp.setLastName("eig");
            emp.setDesignation("SE");
            return emp;
        } catch (ApplicationException e) {

            LOG.error("ApplicationException occurred in getEmployeeInfo ",e);
            throw e;
        } catch (Exception e) {
            
            LOG.error("Exception occurred in getEmployeeInfo ",e);
            throw new RuntimeException(e);
        }
    }

    private void validateEmployee(long employeeID) {
        
        BaseValidator validator = new BaseValidator();
        boolean isErrorExist = false;

        if (employeeID < 0) {
            validator.addError("T002", "Invalid ID");
            isErrorExist = true;
        }
        if (employeeID != 1) {
            validator.addError("T003", "Employee Info not found");
            isErrorExist = true;
        }
        
        if (isErrorExist) {
            throw validator.createException();
        }
    }

    public boolean createEmployee(Employee emp) {

        final String logSourceMethod = "createEmployee(Employee emp)";
        LOG.debug(logSourceMethod + ">> Start");
        return true;
    }
    
    public Employee throwUsernameTokenException() {
        throw new UsernameTokenException("This exception is always thrown", 
                                          ClientErrorCodes.SECURITY_TOKEN_MISSING_INFORMATION);
    }
    
    public Employee throwError() {
        throw new Error("This error is always thrown");
    }
}
