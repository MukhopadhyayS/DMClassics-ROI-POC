/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.roi.inuse.service;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mckesson.eig.roi.inuse.dao.InUseDAO;
import com.mckesson.eig.roi.inuse.model.InUseRecord;
import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * @author OFS
 * @date   Nov 11, 2008
 * @since  ROI HPF 13.1
 */
public class InUseQuartzJob implements Job {

    private static final OCLogger LOG = new OCLogger(InUseQuartzJob.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    public InUseQuartzJob() {
        super();
    }

    /**
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    public void execute(JobExecutionContext execContext) throws JobExecutionException {

        if (DO_DEBUG) {
            LOG.debug(execContext != null ? "execContext" : "execContext argument is null");
        }

        JobDataMap map = execContext.getMergedJobDataMap();
        InUseDAO inUseDAO = (InUseDAO) map.get("InUseDAO");
        int gracePM = map.getInt("gracePeriodMin");

        if (DO_DEBUG) {
            if (null == inUseDAO) {
                LOG.debug("inUseDAO service not found in context");
            }
        }

        List<InUseRecord> records = inUseDAO.retrieveExpiredRecords(gracePM);
        if (records.size() > 0) {
            inUseDAO.deleteRecords(records);
        }
    }
}
