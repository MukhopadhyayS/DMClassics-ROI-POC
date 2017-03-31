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

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;

import com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.supplementary.model.ROISupplementarityAttachment;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;

/**
 * @author OFS
 * @date Jun 22, 2012
 */
public class ROIAttachmentDAOImpl 
extends ROIDAOImpl 
implements ROIAttachmentDAO {

    private static final Log LOG = LogFactory.getLogger(ROIAdminServiceImpl.class);
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
        
            Session session = getSessionFactory().getCurrentSession();
            String namedQuery = session.getNamedQuery("retrieveSupplementarityAttachmentInfos").getQueryString();
            SQLQuery createSQLQuery = super.prepareSQLQuery(session, namedQuery, ROISupplementarityAttachment.class);
            createSQLQuery.setString("mrn", mrn);
            createSQLQuery.setString("facility", facilityCode);
            createSQLQuery.setString("encounter", encounter);
            createSQLQuery.addScalar("volume", StandardBasicTypes.STRING);
            createSQLQuery.addScalar("fileext", StandardBasicTypes.STRING);
            createSQLQuery.addScalar("path", StandardBasicTypes.STRING);
            createSQLQuery.addScalar("uuid", StandardBasicTypes.STRING);
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
            
            Session session = getSessionFactory().getCurrentSession();
            String namedQuery = session.getNamedQuery("retrieveAllSupplementarityAttachments").getQueryString();
            SQLQuery createSQLQuery = super.prepareSQLQuery(session, namedQuery, ROISupplementarityAttachment.class);
            createSQLQuery.setString("mrn", mrn);
            createSQLQuery.setString("facility", facilityCode);
            createSQLQuery.addScalar("volume", StandardBasicTypes.STRING);
            createSQLQuery.addScalar("fileext", StandardBasicTypes.STRING);
            createSQLQuery.addScalar("path", StandardBasicTypes.STRING);
            createSQLQuery.addScalar("uuid", StandardBasicTypes.STRING);
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
            
            Session session = getSessionFactory().getCurrentSession();
            String namedQuery = session.getNamedQuery("retrieveSupplementarityAttachment").getQueryString();
            SQLQuery createSQLQuery = super.prepareSQLQuery(session, namedQuery, ROISupplementarityAttachment.class);
            createSQLQuery.setParameterList("attachmentIds", attachmentIds);
            createSQLQuery.addScalar("volume", StandardBasicTypes.STRING);
            createSQLQuery.addScalar("fileext", StandardBasicTypes.STRING);
            createSQLQuery.addScalar("path", StandardBasicTypes.STRING);
            createSQLQuery.addScalar("uuid", StandardBasicTypes.STRING);
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
