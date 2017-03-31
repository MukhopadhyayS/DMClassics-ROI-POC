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

package com.mckesson.eig.roi.hpf.model;

import java.io.Serializable;

/**
 * @author OFS
 * @date   Dec 7, 2011
 * @since  Dec 7, 2011
 */
public class SecurityRights
implements Serializable {

    private static final long serialVersionUID = -8102518158502411010L;

    private long _securityId;
    private String _securityDesc;
    private long _securityGroup;
    
    public long getSecurityId() { return _securityId; }
    public void setSecurityId(long securityId) { this._securityId = securityId; }
    
    public String getSecurityDesc() { return _securityDesc; }
    public void setSecurityDesc(String securityDesc) { _securityDesc = securityDesc; }
    
    public long getSecurityGroup() { return _securityGroup; }
    public void setSecurityGroup(long securityGroup) { _securityGroup = securityGroup; }
    
}
