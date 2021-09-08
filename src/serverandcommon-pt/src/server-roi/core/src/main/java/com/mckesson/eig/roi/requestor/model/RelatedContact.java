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

package com.mckesson.eig.roi.requestor.model;


import java.io.Serializable;
import java.util.Date;


/**
 * @author ranjithr
 * @date   Jun 24, 2008
 * @since  HPF 13.1 [ROI]; Jun 17, 2008
 */
public class RelatedContact
implements Serializable {

    private long _id;
    private long _contactType;
    private long _createdBy;
    private long _modifiedBy;
    private Date _modifiedDate;
    private int _recordVersion;

    private Contact _contact;

    public RelatedContact() { }

    public RelatedContact(long contactTypeId) {
        setContactType(contactTypeId);
        _contact = new Contact();
    }

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public long getContactType() { return _contactType; }
    public void setContactType(long type) { _contactType = type; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date date) { _modifiedDate = date; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    public Contact getContact() { return _contact; }
    public void setContact(Contact contact) { _contact = contact; }
}
