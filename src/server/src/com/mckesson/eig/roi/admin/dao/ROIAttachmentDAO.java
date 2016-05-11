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


/**
 * This class implements all ROI Attachment services
 * @author OFS
 * @date Jun 22, 2012
 * @since Jun 22, 2012
 */

package com.mckesson.eig.roi.admin.dao;

/**
 * @author OFS
 * @date Jun 22, 2012
 */
import java.util.List;

import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.supplementary.model.ROISupplementarityAttachment;

public interface ROIAttachmentDAO 
extends ROIDAO {

    /**
     * This method retrieves an attachment Info list.
     * @param mrn
     * @param facility
     * @param encounter
     * @return List<ROIAttachment>
     */
    List<ROISupplementarityAttachment> getAttachmentsInfo(String mrn, String facility, String encounter);
    
    /**
     * This method retrieves an attachment Info list.
     * @param mrn
     * @param facility
     * @return List<ROIAttachment>
     */
    List<ROISupplementarityAttachment> retrieveAllAttachments(String mrn, String facilityCode);

    /**
     * This method retrieves attachment corresponding to the given attachment sequence.
     * @param attachmentId
     * @return ROIAttachment
     */
    List<ROISupplementarityAttachment> retrieveROIAttachment(List<Long> attachmentId);
    
}
