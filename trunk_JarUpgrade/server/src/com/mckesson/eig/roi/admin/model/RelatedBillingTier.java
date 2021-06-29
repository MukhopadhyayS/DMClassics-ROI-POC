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

package com.mckesson.eig.roi.admin.model;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author OFS
 * @date   Mar 30, 2009
 * @since  HPF 13.1 [ROI]; Mar 24, 2009
 */
public class RelatedBillingTier
implements Serializable {

    private long    _id;
    private long    _billingTierId;
    private long    _createdBy;
    private long    _modifiedBy;
    private Date    _modifiedDate;
    private int     _recordVersion;
    private boolean _isHPF;
    private boolean _isHECM;
    private boolean _isCEVA;
    private boolean _isSupplemental;

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public long getBillingTierId() { return _billingTierId; }
    public void setBillingTierId(long tierId) { _billingTierId = tierId; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date date) { _modifiedDate = date; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    public boolean isIsHPF() { return _isHPF; }
    public void setIsHPF(boolean ishpf) { _isHPF = ishpf; }

    public boolean isIsHECM() { return _isHECM; }
    public void setIsHECM(boolean ishecm) { _isHECM = ishecm; }

    public boolean isIsCEVA() { return _isCEVA; }
    public void setIsCEVA(boolean isceva) { _isCEVA = isceva; }

    public boolean isIsSupplemental() { return _isSupplemental; }
    public void setIsSupplemental(boolean supplemental) { _isSupplemental = supplemental; }

}
