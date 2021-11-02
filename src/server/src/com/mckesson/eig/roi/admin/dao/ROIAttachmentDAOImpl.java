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

package com.mckesson.eig.roi.admin.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StringType;
import org.springframework.transaction.annotation.Transactional;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.supplementary.model.ROISupplementarityAttachment;
import com.mckesson.eig.utility.util.CollectionUtilities;

/**
 * @author OFS
 * @date Jun 22, 2012
 */
@Transactional
public class ROIAttachmentDAOImpl 
extends ROIDAOImpl 
implements ROIAttachmentDAO {

    private static final OCLogger LOG = new OCLogger(ROIAdminServiceImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    
    @SuppressWarnings("unchecked")
    public List<ROISupplementarityAttachment> getAttachmentsInfo(String mrn, 
                                                  String facilityCode,
                                                  String encounter) {
        
        String logSM = "getAttachmentsInfo(String mrn, String facilityCode, String encounter)";
        
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + mrn + "," + facilityCode + "," + encounter);
        }
        try {
        
            Session session = getSession();
            String namedQuery = session.getNamedQuery("retrieveSupplementarityAttachmentInfos").getQueryString();
            NativeQuery createSQLQuery = super.prepareSQLQuery(session, namedQuery, ROISupplementarityAttachment.class);
            createSQLQuery.setParameter("mrn", mrn, StringType.INSTANCE);
            createSQLQuery.setParameter("facility", facilityCode, StringType.INSTANCE);
            createSQLQuery.setParameter("encounter", encounter, StringType.INSTANCE);
            createSQLQuery.addScalar("volume", StringType.INSTANCE);
            createSQLQuery.addScalar("fileext", StringType.INSTANCE);
            createSQLQuery.addScalar("path", StringType.INSTANCE);
            createSQLQuery.addScalar("uuid", StringType.INSTANCE);
            List<ROISupplementarityAttachment> attachmentInfo = (List<ROISupplementarityAttachment>) createSQLQuery.list();
            
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            
            if (CollectionUtilities.isEmpty(attachmentInfo)) {
                return null;
            } else {
                return attachmentInfo;
            }
        } catch (Exception e) {
            throw new ROIException(e, ROIClientErrorCodes.RERTRIEVE_ATTACHMENT_INFO_FAILED);
        }
    }

    @SuppressWarnings("unchecked")
    public List<ROISupplementarityAttachment> retrieveAllAttachments(String mrn, String facilityCode) {
        
        String logSM = "getAllAttachments(String mrn, String facilityCode)";
        
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + mrn + "," + facilityCode);
        }
        try {
            
            Session session = getSession();
            String namedQuery = session.getNamedQuery("retrieveAllSupplementarityAttachments").getQueryString();
            NativeQuery createSQLQuery = super.prepareSQLQuery(session, namedQuery, ROISupplementarityAttachment.class);
            createSQLQuery.setParameter("mrn", mrn, StringType.INSTANCE);
            createSQLQuery.setParameter("facility", facilityCode, StringType.INSTANCE);
            createSQLQuery.addScalar("volume", StringType.INSTANCE);
            createSQLQuery.addScalar("fileext", StringType.INSTANCE);
            createSQLQuery.addScalar("path", StringType.INSTANCE);
            createSQLQuery.addScalar("uuid", StringType.INSTANCE);
            List<ROISupplementarityAttachment> attachmentInfo = (List<ROISupplementarityAttachment>) createSQLQuery.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            
            if (CollectionUtilities.isEmpty(attachmentInfo)) {
                return null;
            } else {
                return attachmentInfo;
            }
        } catch (Exception e) {
            throw new ROIException(e, ROIClientErrorCodes.RERTRIEVE_ATTACHMENT_INFO_FAILED);
        }
    }

    public List<ROISupplementarityAttachment> retrieveROIAttachment(List<Long> attachmentIds) {
        
        String logSM = "getROIAttachment(long attachmentId)";
        
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: atttachmentId:" + attachmentIds);
        }
        try {
            
            Session session = getSession();
            String namedQuery = session.getNamedQuery("retrieveSupplementarityAttachment").getQueryString();
            NativeQuery createSQLQuery = super.prepareSQLQuery(session, namedQuery, ROISupplementarityAttachment.class);
            createSQLQuery.setParameterList("attachmentIds", attachmentIds);
            createSQLQuery.addScalar("volume", StringType.INSTANCE);
            createSQLQuery.addScalar("fileext", StringType.INSTANCE);
            createSQLQuery.addScalar("path", StringType.INSTANCE);
            createSQLQuery.addScalar("uuid", StringType.INSTANCE);
            List attachments = createSQLQuery.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
            return attachments;
        } catch (Exception e) {
            throw new ROIException(e, ROIClientErrorCodes.RERTRIEVE_ATTACHMENT_FAILED);
        }
    }
}
