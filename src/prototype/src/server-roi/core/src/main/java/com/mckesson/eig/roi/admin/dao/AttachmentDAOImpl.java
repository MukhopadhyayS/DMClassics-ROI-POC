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

package com.mckesson.eig.roi.admin.dao;

import java.util.List;

import com.mckesson.eig.roi.admin.model.AttachmentLocation;
import com.mckesson.eig.roi.admin.model.SysParam;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;

/**
 * @author Pranav Amarasekaran
 * @date Dec 16, 2010
 * @since HPF 15.1 [ROI]; Dec 16, 2010
 */
public class AttachmentDAOImpl extends ROIDAOImpl implements AttachmentDAO {

    private static final Log LOG = LogFactory.getLogger(AttachmentDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private SysParam retrieveSysParamForAttachmentLocation() {

    	final String logSM = "retrieveAttachmentLocation()";
    	if (DO_DEBUG) {
    	    LOG.debug(logSM + ">>Start:");
    	}
    
    	final String key = "roi.attachment.location";
    
    	@SuppressWarnings("unchecked")
    	// not supported by 3rdParty API
    	List<SysParam> sysparams = (List<SysParam>) getHibernateTemplate().findByNamedQuery(
    		"retrieveAttachmentLocation", key);
    
    	if (DO_DEBUG) {
    	    LOG.debug(logSM + "<<End:");
    	}
    	if (CollectionUtilities.isEmpty(sysparams)) {
    	    return null;
    	}
    	return (SysParam) sysparams.get(0);
    }

    @Override
    public AttachmentLocation retrieveAttachmentLocation() {
        
    	String attachmentLocation = retrieveEIWDATAConfiguration("ROI_ATTACHMENT_FILE_DIR");
    	if (attachmentLocation == null) {
    	    return null;
    	}
    	AttachmentLocation location = new AttachmentLocation(attachmentLocation);
    	return location;
    }
    
    @Override
    public void updateAttachmentLocation(String attachmentLocation,
	    int modifiedBy) {

    	final String logSM = "updateAttachmentLocation(attachmentLocation)";
    	if (DO_DEBUG) {
    	    LOG.debug(logSM + ">>Start:" + attachmentLocation);
    	}
    
    	SysParam sysParam = retrieveSysParamForAttachmentLocation();
    	sysParam.setGlobalVariant(attachmentLocation);
    	sysParam.setModifiedDate(getDate());
    	sysParam.setModifiedBy(modifiedBy);
    
    	merge(sysParam);
    
    	if (DO_DEBUG) {
    	    LOG.debug(logSM + "<<End:");
    	}
    }
}
