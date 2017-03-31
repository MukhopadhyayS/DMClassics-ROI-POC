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

package com.mckesson.eig.roi.base.model;

import java.io.Serializable;
import java.util.Date;



/**
 * @author manikandans
 * @date   Jun 13, 2008
 * @since  HPF 13.1 [ROI]; Jun 13, 2008
 */
public class EmailPhone
implements Serializable {

    private long _id;
    private String _emailPhone;
    private long _createdBy;
    private long _modifiedBy;
    private Date _modifiedDate;
    private int _recordVersion;

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getEmailPhone() { return _emailPhone; }
    public void setEmailPhone(String emailPhone) { _emailPhone = emailPhone; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long createdBy) { _createdBy = createdBy; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long modifiedBy) { _modifiedBy = modifiedBy; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date modifiedDate) { _modifiedDate = modifiedDate; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int recordVersion) { _recordVersion = recordVersion; }


}
