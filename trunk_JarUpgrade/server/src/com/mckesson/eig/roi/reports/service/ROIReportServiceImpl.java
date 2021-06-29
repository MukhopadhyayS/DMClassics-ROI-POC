/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.reports.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.reports.service.ReportServiceImpl;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.service.ROIAuditManager;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.reports.dao.ROIReportDAO;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;


/**
*
* @author OFS
* @date   Oct 14, 2008
* @since  HPF 13.1 [ROI]; Oct 14, 2008
*/
public class ROIReportServiceImpl
extends ReportServiceImpl {


    private static final OCLogger LOG = new OCLogger(ROIReportServiceImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final String AUDIT_KEY          = "AuditManager";

    private static final String KEY_USER_INSTANCE_ID = "userInstanceId";
    private static final String KEY_REPORT_ID = "reportId";
    private static final String KEY_PATIENT_NAME = "patientName";

    public static enum REPORTID {

        ID1  ("Accounting of Disclosure"),
        ID2  ("Account Receivable Aging"),
        ID3  ("Documents Released by MRN"),
        ID4  ("Request Status Summary"),
        ID5  ("Request Detail Report"),
        ID6  ("Outstanding Invoice Balances"),
        ID7  ("Pending Aging Summary"),
        ID8  ("Requests with Logged Status"),
        ID9  ("Processed Request Summary"),
        ID10 ("Posted Payment Summary"),
        ID11 ("Turn Around Times"),
        ID12 ("Sales Tax Report"),
		ID13 ("MU Release TurnAround Times"),
		ID14 ("Prebill Report"),
		ID15 ("Productivity Report"),
		ID16 ("BillableAndUnBillable Request Report"),
		ID17 ("Billing Report"),
        NA   ("NA");

        private String _type;

        private REPORTID(String type) {
            _type = type;
        }
        @Override
        public String toString() { return _type; }
   }

    @SuppressWarnings("rawtypes") //Not supported by framework
    @Override
    public void generateReport(String reportID, Map params, File file)
    throws IOException {

        final String logSourceMethod = "generateReport(reportID, params, file)";
        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + ">>Start");
        }

        try {

            super.generateReport(reportID, params, file);

        } catch (IOException e) {

            LOG.error(e.getMessage(), e);
            throw e;
        } catch (Throwable e) {

            LOG.error(e.getMessage(), e);
            throw new IOException("ROI Report Generation Failed", e);
        }

        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + "<<End");
        }
    }

    /**
     * This method is used to return the corresponding audit manager instance
     * by looking up the spring application config file.
     *
     * @return Instance of the Audit manager implementation class.
     */
    protected ROIAuditManager getROIAuditManager() {

        return (ROIAuditManager) SpringUtilities.getInstance().getBeanFactory().getBean(AUDIT_KEY);
    }

    /**
     * This method is used to audit the user operations
     * @param comment The audit comment
     * @param userId The user id of the user performing the operation
     * @param ts The time stamp of the operation audited
     */
    protected void doAudit(String comment, int userId, Timestamp ts, String auditCode) {

        ROIAuditManager auditMgr = getROIAuditManager();
        AuditEvent ae = auditMgr.createAuditEvent(auditCode,
                                                  userId,
                                                  comment,
                                                  ROIConstants.DEFAULT_FACILITY,
                                                  ts,
                                                  AuditEvent.SUCCESS);

        auditMgr.createAuditEntry(ae);
    }

    @SuppressWarnings("rawtypes")
    public void auditReport(Map params) {

        int userInstanceId = getUsernstanceID(params);
        int reportId = new Integer(getReportId(params)).intValue();

        ROIReportDAO dao = (ROIReportDAO) getReportDAO(getReportType(getReportId(params)));
        User user = dao.retrieveUserDetails(userInstanceId);
        String report = "ID" + String.valueOf(reportId);

        String comments = getComments(user.getFullName(), report);

        doAudit(comments.toString(),
                userInstanceId,
                dao.getDate(),
                ROIConstants.REPORTS_AUDIT_CODE);

   }

    private String getComments(String fullName,
                               String report) {

        StringBuffer buffer = null;

        // CR#376464 - Audit comment modified.
        buffer = new StringBuffer().append("Report generation was performed in ")
              .append("ROI")
              .append(" by ")
              .append(StringUtilities.safeTrim(fullName))
              .append("; Title:- ")
              .append(Enum.valueOf(REPORTID.class, report))
              .append(".");

        return buffer.toString();
    }

    @Override
    protected String getReportType(String reportID) {

        return new StringBuffer().append("roi.report.")
                                 .append(reportID)
                                 .append(".dao").toString();
    }

    @SuppressWarnings("rawtypes")
    protected int getUsernstanceID(Map params) {
        return ROIReportUtil.getIntegerParam(params, KEY_USER_INSTANCE_ID).intValue();
    }

    @SuppressWarnings("rawtypes")
    protected String getReportId(Map params) {
        return ROIReportUtil.getStringParam(params, KEY_REPORT_ID);
    }
}
