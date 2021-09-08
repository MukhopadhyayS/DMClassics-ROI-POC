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
package com.mckesson.eig.audit.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

/**
 * Transport class that contains the web service call data.
 *
 */
@XmlRootElement(name = "auditEventList")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "AuditEventListType")
public class AuditEventList {

    // Logger used to record messages.
    /**
     * Loading the Configuration file for Spring and Hibernate.
     */
    private static final Log LOG = LogFactory.getLogger(AuditEventList.class
            .getName());

    /**
     * @return _log return reference of <code>config</code> file.
     */
    protected static final Log getLogger() {
        return LOG;
    }
    
    private List<AuditEvent> _auditEvent;

	public List<AuditEvent> getAuditEvent() {
		return _auditEvent;
	}

	 @XmlElement(name = "auditEventList")
	public void setAuditEvent(List<AuditEvent> auditEvent) {
		_auditEvent = auditEvent;
	}

   }
