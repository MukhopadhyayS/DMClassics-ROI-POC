/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/

package com.mckesson.eig.roi.base.service;


import java.util.Date;

import com.mckesson.eig.audit.dao.AuditDaoService;
import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.SpringUtilities;


/**
 * This class creates the audit event and sends the audit message to the audit queue
 *
 * @author manikandans
 * @date   Apr 28, 2008
 * @since  HPF 13.1 [ROI]; Mar 27, 2008
 */
public class ROIAuditManager {

    private static final OCLogger LOG = new OCLogger(ROIAuditManager.class);



    /**
     * This method creates the audit event entry by invoking the AuditLocalService.
     *
     * @param auditEvent The audit event object that has to be persisted.
     * @return Status of the audit entry creation.
     */
    public boolean createAuditEntry(AuditEvent auditEvent) {


        try {

            
            AuditDaoService service = (AuditDaoService) SpringUtilities.getInstance()
                                        .getBeanFactory().getBean(AuditDaoService.class.getName());
            
            boolean created = service.createAuditEntry(auditEvent);
            
            return created;
            
        } catch (Throwable e) {

            LOG.warn("Audit Failure: " + e);
            return false;
        }
    }

    /**
     * This method creates the audit event
     *
     * @param actionCode
     * @param userId Current userId
     * @param comment Description about the event
     * @param facility Facility
     * @param successState
     * @return Audit event
     */
    public AuditEvent createAuditEvent(String actionCode,
                                       long userId,
                                       String comments,
                                       String facility,
                                       Date eventStart,
                                       long successState) {

        AuditEvent auditEvent = new AuditEvent();
        auditEvent.setActionCode(actionCode);
        auditEvent.setUserId(userId);
        auditEvent.setFacility(facility);
        auditEvent.setEventStart(eventStart);
        auditEvent.setComment(comments);
        auditEvent.setEventStatus(successState);

        return auditEvent;
    }

}
