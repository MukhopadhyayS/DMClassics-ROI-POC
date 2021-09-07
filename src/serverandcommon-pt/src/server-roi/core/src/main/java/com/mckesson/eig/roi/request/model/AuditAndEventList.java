/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2011 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.request.model;

import java.io.Serializable;
import java.util.List;

import com.mckesson.eig.audit.model.AuditEvent;

/**
 * This class is added for CR# 365598
 * @author OFS
 * @date   Dec 6, 2011
 * @since  Dec 6, 2011
 */
public class AuditAndEventList 
implements Serializable {
    
    private static final long serialVersionUID = -3742766980629480216L;
    
    private List<RequestEvent> _requestEvent;
    private List<AuditEvent> _auditEvent;
    
    public List<RequestEvent> getRequestEvent() { return _requestEvent; }
    public void setRequestEvent(List<RequestEvent> requestEvent) { _requestEvent = requestEvent; }
    
    public List<AuditEvent> getAuditEvent() { return _auditEvent; }
    public void setAuditEvent(List<AuditEvent> auditEvent) { _auditEvent = auditEvent; }
}
