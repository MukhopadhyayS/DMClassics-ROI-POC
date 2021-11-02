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

package com.mckesson.eig.roi.hpf.service;

import com.mckesson.eig.roi.base.service.BaseROIService;

import javax.jws.WebService;

import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * @author Ganeshram
 * @date   Sep 02, 2008
 * @since  HPF 13.1 [ROI]; Jun 17, 2008
 */
@WebService(serviceName="SecurityLogoffService", endpointInterface="com.mckesson.eig.roi.hpf.service.SecurityLogoffService",
targetNamespace="urn:eig.mckesson.com", portName="SecurityLogoff", name="HPFSecurityAuthenticationService")
public class HPFSecurityAuthenticationService
extends BaseROIService implements SecurityLogoffService {

   /**
     * Initialize the logger.
     */
    private static final OCLogger LOG = new OCLogger(HPFSecurityAuthenticationService.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    public HPFSecurityAuthenticationService() { }

    /**
     * This service is invoked to log a user off the system.
     */
    public void logoff() {

        try {

            if (DO_DEBUG) {
                LOG.info("Logoff invoked for user: " +  getUser().getFullName());
            }
//            // audit logoff
//            auditRequest(request.viewAuditComment(),
      //                   getUser().getInstanceId(),
//                         dao.getDate(),
//                         ROIConstants.REQUEST_AUDIT_ACTION_CODE_VIEW);
//
//            ROIAuditManager auditMgr = new ROIAuditManager();
//            AuditEvent logoffEvent = auditMgr.prepareSucessAuditEvent(0,
//                    (WsSession.getSessionUserId()).longValue(),
//                    AuditEvent.LOGOFF, "User logged off");
//            auditMgr.createAuditEntry(logoffEvent);
        } catch (Exception e) {
            LOG.error("An error occurred while auditing logoff.", e);
        }
    }
}
