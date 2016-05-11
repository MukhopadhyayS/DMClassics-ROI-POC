/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.supplementary.model;

import java.io.Serializable;

/**
 * @author karthik easawaran
 * @date   Jul 20, 2012
 * @since  Jul 20, 2012
 */
public class AttachmentDeleteStatus 
implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private long _attachmentId;
    private String _deleteLog;
    private boolean _isDeleted;
    
    /**
     * getter and setter for attachmentId
     */
    public long getAttachmentId() { return _attachmentId; }
    public void setAttachmentId(long attachmentId) { _attachmentId = attachmentId; }

    /**
     * getter and setter for deleteLog
     */
    public String getDeleteLog() { return _deleteLog; }
    public void setDeleteLog(String deleteLog) { _deleteLog = deleteLog; }
    
    /**
     * getter and setter for deleted
     */
    public void setDeleted(boolean isDeleted) { _isDeleted = isDeleted; }
    public boolean isDeleted() { return _isDeleted; }

}
