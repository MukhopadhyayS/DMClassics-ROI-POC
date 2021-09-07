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
package com.mckesson.eig.audit.dao;

import java.util.List;

import javax.jws.WebService;

import com.mckesson.eig.audit.AuditException;
import com.mckesson.eig.audit.Auditable;
import com.mckesson.eig.audit.dao.hecm.AuditDao4Hecm;
import com.mckesson.eig.audit.dao.hpf.AuditDao4Hpf;
import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.audit.model.AuditEventList;

/**
 * This component handles writing the data that was stored in the JMS queue into
 * the database.
 * 
 */
@WebService(
name              = "AuditPortType_v1_0",
portName          = "audit_v1_0",
serviceName       = "AuditService_v1_0",
targetNamespace   = "http://eig.mckesson.com/wsdl/audit-v1",
endpointInterface = "com.mckesson.eig.audit.Auditable")
public class AuditDaoService implements Auditable {

    /**
     * Instance of AudiDAO class.
     */
    private AuditDao4Hecm _auditDaoHecm;
    private AuditDao4Hpf _auditDaoHpf;


    /**
     * This CreateAuditEntry method is used for inserting entries in AUDIT_TRIAL
     * table.It inserts a row to AUDIT_TRAIL_DETAIL table for each of objectId ,
     * revision1 , revision2 , revision3 and workflowreason of AuditEvent
     * object.
     * 
     * @param auditEvent -
     *            the AuditEvent Object to be made persistence
     * @return <code>true</code> if AuditEvent object is successfully created
     *         in AUDIT_TRAIL. else returns <code>false</code>
     */
    public boolean createAuditEntry(AuditEvent auditEvent) {
    	String auditAction = auditEvent.getActionCode();
    	if (auditAction == null) {
    		return getAuditDao4Hecm().insertEntry(auditEvent);
    	}
        return getAuditDao4Hpf().insertEntry(auditEvent);
    }
    
    /**
     * This createAuditEntryList method is used for inserting entries in AUDIT_TRIAL
     * table.It inserts multiple rows to AUDIT_TRAIL_DETAIL table for each of objectId ,
     * revision1 , revision2 , revision3 and workflowreason of AuditEventList
     * object.
     * 
     * @param auditEventList -
     *            the AuditEventList Object to be made persistence
     * @return <code>true</code> if AuditEventList object is successfully created
     *         in AUDIT_TRAIL. else returns <code>false</code>
     */
    public boolean createAuditEntryList(AuditEventList auditEventList) {

        try {
        	List<AuditEvent> auditEvents = auditEventList.getAuditEvent();
            
			if (null != auditEvents && !auditEvents.isEmpty()) {
			    
			    for (AuditEvent audit : auditEvents) {
			        createAuditEntry(audit);
			    }  
			}
			 return true;
         } catch (Exception e) {
            throw new AuditException(e);
        }
    } 	
    
    private synchronized AuditDao4Hecm getAuditDao4Hecm() {
    	if (_auditDaoHecm == null) {
    		_auditDaoHecm = new AuditDao4Hecm(); 
    	}
		return _auditDaoHecm;
	}

    private synchronized AuditDao4Hpf getAuditDao4Hpf() {
    	if (_auditDaoHpf == null) {
    		_auditDaoHpf = new AuditDao4Hpf(); 
    	}
		return _auditDaoHpf;
	}
}
