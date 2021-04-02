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

package com.mckesson.eig.roi.admin.service;

import org.springframework.beans.factory.BeanFactory;

import com.mckesson.eig.common.filetransfer.controller.BaseContentRetriever;
import com.mckesson.eig.common.filetransfer.services.BaseFileTransferData;
import com.mckesson.eig.roi.admin.dao.FileTransferHelper;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;
/**
 * This class extents BaseContentRetriever and contains methods to retrieve the file from database
 *
 * @author OFS
 * @date   Aug 12, 2008
 * @since  HPF 13.1 [ROI]; May 16, 2008
 */

public class ROIContentRetriever
extends BaseContentRetriever {

    private static final Log LOG = LogFactory.getLogger(ROIContentRetriever.class);

    @Override
    public boolean isValidUser(String user, String password, String ticket) {
        return true;
    }

    @Override
    public String retrieveContent(BaseFileTransferData serverData) {

        final String logSM = "retrieveContent(serverData)";
        LOG.debug(logSM + ">>Start:");

        String ownerType = serverData.getRequestProperty("OWNER_TYPE");
        String fileTransferhelper = ownerType + "_FTHelper";

        BeanFactory beanFactory = SpringUtilities.getInstance().getBeanFactory();
        FileTransferHelper fHelper = (FileTransferHelper) beanFactory.getBean(fileTransferhelper);
        String docPath = fHelper.retrieve(serverData);

        LOG.debug(logSM + "<<End:");
        return docPath;
    }
}
