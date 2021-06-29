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
import java.util.List;


/**
 * @author Karthik Easwaran
 * @date   Jul 20, 2012
 * @since  Jul 20, 2012
 */
public class AttachmentDeleteStatusList
implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private List<AttachmentDeleteStatus> _deletedAttachmentList;
    
    public AttachmentDeleteStatusList() { }
    public AttachmentDeleteStatusList(List<AttachmentDeleteStatus> deletedAttachmentList) {
        setDeletedAttachmentList(deletedAttachmentList);
    }

    /**
     * getter and setter for attachmentList
     */
    public List<AttachmentDeleteStatus> getDeletedAttachmentList() {
        return _deletedAttachmentList;
    }
    public void setDeletedAttachmentList(List<AttachmentDeleteStatus> deletedAttachmentList) {
        _deletedAttachmentList = deletedAttachmentList;
    }

}
