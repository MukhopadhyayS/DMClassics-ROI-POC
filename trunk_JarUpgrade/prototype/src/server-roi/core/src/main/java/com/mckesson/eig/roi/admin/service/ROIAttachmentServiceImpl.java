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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mckesson.eig.roi.admin.dao.ROIAttachmentDAO;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.service.BaseROIService;
import com.mckesson.eig.roi.supplementary.model.AttachmentDeleteStatus;
import com.mckesson.eig.roi.supplementary.model.AttachmentDeleteStatusList;
import com.mckesson.eig.roi.supplementary.model.AttachmentInfoList;
import com.mckesson.eig.roi.supplementary.model.AttachmentSequenceList;
import com.mckesson.eig.roi.supplementary.model.ROISupplementarityAttachment;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

/**
 * This class implements all ROI Attachment services
 * @author OFS
 * @date Jun 22, 2012
 */
public class ROIAttachmentServiceImpl 
extends BaseROIService
implements ROIAttachmentService {
    

    private static final Log LOG = LogFactory.getLogger(ROIAdminServiceImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    
    @Override
    public AttachmentInfoList retrieveAttachmentsInfo(String mrn, 
                                                        String facilityCode,
                                                        String encounter) {
        
        final String logSM = "retrieveAttachmentsInfo(mrn,facilityCode,encounter)";
        
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + mrn + "," + facilityCode + "," + encounter);
        }
        
        try {
            
            ROIAttachmentDAO dao = (ROIAttachmentDAO) getDAO(DAOName.ROI_ATTACHMENT_DAO);
            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();
            
            if (!validator.validateAttachmentInfos(mrn, facilityCode, encounter)) {
                throw validator.getException();
            }
            
            //Retrieves an attachment Info details 
            List<ROISupplementarityAttachment> attachmentDetails = 
                            dao.getAttachmentsInfo(mrn, facilityCode, encounter);
            
            return new AttachmentInfoList(attachmentDetails);
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            throw new ROIException(e, ROIClientErrorCodes.RERTRIEVE_ATTACHMENT_INFO_FAILED);
        }
        
    }  

    @Override
    public AttachmentInfoList getAllAttachments(String mrn, String facilityCode) {
        
        final String logSM = "getAllAttachments(mrn,facilityCode)";
        
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + mrn + "," + facilityCode);
        }
        
        try {
            
            ROIAttachmentDAO dao = (ROIAttachmentDAO) getDAO(DAOName.ROI_ATTACHMENT_DAO);
            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();
            
            if (!validator.validateAttachmentInfos(mrn, facilityCode)) {
                throw validator.getException();
            }
            
            //Retrieves an attachment Info details 
            List<ROISupplementarityAttachment> attachmentDetails = dao.retrieveAllAttachments(mrn, facilityCode);
            
            return new AttachmentInfoList(attachmentDetails);
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            throw new ROIException(e, ROIClientErrorCodes.RERTRIEVE_ATTACHMENT_INFO_FAILED);
        }
        
    }  

    /**
     * The ROIAttachments are retrieved from the database 
     * and the attachments are deleted only from the physical location 
     * The entries in the database would not be deleted from the database.
     * 
     * @see com.mckesson.eig.roi.admin.service.ROIAttachmentService
     * #deleteROIAttachments(com.mckesson.eig.roi.supplementary.model.AttachmentDeleteStatusList)
     */
    @Override
    public AttachmentDeleteStatusList deleteROIAttachments(AttachmentSequenceList attachments) {
        
        final String logSM = "deleteROIAttachment(attachments)";
        
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: attachmentId:" + attachments);
        }
        
        try {
            
            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();
            if (!validator.validateAttachmentIds(attachments)) {
                throw validator.getException();
            }
            
            List<Long> attachmentIds = attachments.getAttachmentIds();
            ROIAttachmentDAO dao = (ROIAttachmentDAO) getDAO(DAOName.ROI_ATTACHMENT_DAO);

            //Retrieves an attachments for the given attachment ids.  
            List<ROISupplementarityAttachment> retrievedAttachments = dao.retrieveROIAttachment(attachmentIds);
            return new AttachmentDeleteStatusList(deletePhysicalAttachment(retrievedAttachments));
            
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            throw new ROIException(e, ROIClientErrorCodes.DELETE_ROI_ATTACHMENT_FAILED);
        }
    }

    /**
     * The ROiAttachments are deleted from its physical locations
     * and the deleted logs for all the attachments are returned
     * @param attachments
     * @return
     */
    private List<AttachmentDeleteStatus> deletePhysicalAttachment(
                                                    List<ROISupplementarityAttachment> attachments) {
        
        if (null == attachments || attachments.size() <= 0) {
            throw new ROIException(ROIClientErrorCodes.INVALID_ROI_ATTACHMENT,
                                   "Attachments is empty");
        }
        
        Iterator<ROISupplementarityAttachment> iterator = attachments.iterator();
        List<AttachmentDeleteStatus> deleteLogs = new ArrayList<AttachmentDeleteStatus>();
        AttachmentDeleteStatus deleteLog = null;
        FailedReasonCode log = null;
        while (iterator.hasNext()) {
            
            ROISupplementarityAttachment attachment = iterator.next();
            try {
                
                // trying to delete the attachment from its physical location
                log = attachment.delete();
            } catch (Exception ex) {
                // if any exception occurs during the deletion of the attachment,
                // the log is sent to the user
                log = FailedReasonCode.DELETION_UNSUCCESSFUL;
            }
            
            deleteLog = new AttachmentDeleteStatus();
            deleteLog.setAttachmentId(attachment.getId());
            deleteLog.setDeleteLog(log.toString());
            switch (log) {
                
                case DELETION_SUCCESSFUL:
                    deleteLog.setDeleted(true);
                    break;
                default:
                    deleteLog.setDeleted(false);
                    break;
            }
            
            deleteLogs.add(deleteLog);
        }
        return deleteLogs;
    }
    
}
