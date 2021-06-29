/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
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

package com.mckesson.eig.config.audit;

import com.mckesson.eig.audit.AuditException;
import com.mckesson.eig.audit.local.AuditLocalService;
import com.mckesson.eig.audit.model.AuditEvent;

/**
 * @author Sahul Hameed Y
 * @date   Apr 17, 2008
 * @since  HECM 1.0; Apr 17, 2008
 */
public class MockAuditLocalService extends AuditLocalService {
    
    private static boolean _passThrough = true;
    private static boolean _createSuccessful = true;
    
    public void setCreateSuccessful(boolean tf) {
        _createSuccessful = tf;
    }
    
    public void setPassThrough(boolean tf) {
        _passThrough = tf;
    }

    public boolean createAuditEntry(AuditEvent auditEvent) {
        if (_passThrough) {
            return super.createAuditEntry(auditEvent);
        }
        if (!_createSuccessful) {
            throw new AuditException("Flag is set to false!");
        }
        return true;
    }
}
