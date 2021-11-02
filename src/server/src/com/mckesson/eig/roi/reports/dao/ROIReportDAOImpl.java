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

package com.mckesson.eig.roi.reports.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.dm.core.common.logging.OCLogger;

/**
*
* @author OFS
* @date   Oct 14, 2008
* @since  HPF 13.1 [ROI]; Oct 14, 2008
*/
@Transactional
public abstract class ROIReportDAOImpl
extends ROIDAOImpl
implements ROIReportDAO {

    private static final OCLogger LOG = new OCLogger(ROIReportDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    public static final String CSV_DELIM  = ",";
    public static final String DATA_DELIMITER = "@";
    public static final String ENTRY_DELIMITER = ";";

    public static final String DATEFORMAT = "MM/dd/yyyy";
    public static final SimpleDateFormat DATEFORMATTER = new SimpleDateFormat(DATEFORMAT);
    public static final String PROCEDURE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Method to retrieve the user details
     * with the given instnce id
     * @see com.mckesson.eig.roi.reports.dao.ROIReportDAO#retrieveUserDetails(int)
     */
    public User retrieveUserDetails(int userInstanceId) {

        final String logSM = "retrieveUserDetails(long userInstanceId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + userInstanceId);
        }

        User user = (User) get(User.class, userInstanceId);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Name:" + user.getFullName());
        }
        return user;
    }

    public List<Object[]> processNamedQuery(String queryName, Object[] params) {

        final String logSM = "processNamedQuery(queryName, params)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: Executing Procedure>>>> " + queryName);
        }

        @SuppressWarnings("unchecked") // Not supported by 3rdparty
        List<Object[]> results = (List<Object[]>) getHibernateTemplate().
        findByNamedQuery(queryName, params);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:No. of Records = " + results.size());
        }

        return results;
    }
}
