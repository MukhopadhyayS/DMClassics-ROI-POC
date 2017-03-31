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

package com.mckesson.eig.roi.admin.service;

import com.mckesson.eig.roi.supplementary.model.AttachmentDeleteStatusList;
import com.mckesson.eig.roi.supplementary.model.AttachmentInfoList;
import com.mckesson.eig.roi.supplementary.model.AttachmentSequenceList;

/**
 * This is the ROI Attachment service to retrieve attachment details operations.
 * @author OFS
 * @date Jun 22, 2012
 */
public interface ROIAttachmentService {
    
    /** 
     * This FailedReasonCode is defined in class ImageFile of RecordsManagement
     * Code base make sure both are same.
     * 
     */
    public enum FailedReasonCode {

        FILE_MISSING("FILE_MISSING"),
        FILE_DELETE_ACCESS_DENIED("FILE_DELETE_ACCESS_DENIED"),
        DELETION_UNSUCCESSFUL("DELETION_UNSUCCESSFUL"),
        DELETION_SUCCESSFUL("DELETION_SUCCESSFUL"),
        FILE_EXISTS_AFTER_DELETION("FILE_EXISTS_AFTER_DELETION");
        
        String _deleteLog;
        FailedReasonCode(String log) {
            _deleteLog = log;
        }
        
        @Override
        public String toString() {
            return _deleteLog;
        }
    }
    
     /**
     * This method Retrieves an Attachment Details based on the given parameter.
     *
     * @param String mrn
     * @param String facilityCode
     * @param String encounter
     * @return AttachmentInfoList
     */
    AttachmentInfoList retrieveAttachmentsInfo(String mrn, 
                                                 String facilityCode,
                                                 String encounter);

    /**
     * This method Retrieves an Attachment Details based on the given parameter.
     *
     * @param String mrn
     * @param String facilityCode
     * @return AttachmentInfoList
     */
    AttachmentInfoList getAllAttachments(String mrn, String facilityCode);

    /**
     * This method deletes the attachment corresponding to the given attachment sequence
     *
     * @param AttachmentSequenceList - containing the attachmentsequences
     * @return ROIDeleteAttachmentInfosList - containing logs of the deleted attachments
     */
    AttachmentDeleteStatusList deleteROIAttachments(AttachmentSequenceList attachments);

}
