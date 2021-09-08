/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Corporation and/or one of its subsidiaries and is protected 
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.audit;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.audit.model.AuditEventList;

/**
 * The interface class that defines the SOAP call.
 * 
 */
@WebService(
name            = "AuditPortType_v1_0",
targetNamespace = "http://eig.mckesson.com/wsdl/audit-v1")
public interface Auditable {

    /**
     * @param auditEvent -
     *            The model object to be made persistent
     * @return <code>true</code> if entry is created successfully in the
     *         AUDIT_TRAIL table <code>false</code> otherwise
     */
    @WebMethod(operationName = "createAuditEntry", 
    		   action = "http://eig.mckesson.com/wsdl/audit-v1/createAuditEntry")
    @WebResult(name   = "createAuditEntryResult")
    boolean createAuditEntry(@WebParam(name = "auditEvent") AuditEvent auditEvent);
    
    /**
     * @param auditEventList -
     *            The model object to be made persistent
     * @return <code>true</code> if entry is created successfully in the
     *         AUDIT_TRAIL table <code>false</code> otherwise
     */
    @WebMethod(operationName = "createAuditEntryList", 
    		   action = "http://eig.mckesson.com/wsdl/audit-v1/createAuditEntryList")
    @WebResult(name   = "createAuditEntryListResult")
    public boolean createAuditEntryList(@WebParam(name = "auditEventList")AuditEventList auditEventList);

}
