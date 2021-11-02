/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright � 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.base.service;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;

import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.request.dao.RequestCoreDAOImpl;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.session.CxfWsSession;


/**
 * @author OFS
 * @date   Sep 15, 2008
 * @since  HPF 13.1 [ROI]; Feb 14,2008
 */
public class BaseROIService {

    private static final OCLogger LOG = new OCLogger(RequestCoreDAOImpl.class);
    private static final String AUTHENTICATED_USER = "authenticated_roi_user";
    private static final String AUDIT_KEY          = "AuditManager";
    private static final NumberFormat CURRENCY_FORMAT_US = NumberFormat
                                            .getCurrencyInstance(ROIConstants.INVOICE_LOCALE);
    private static final int THREE = 3;
    private static final int NINE = 9;

    public BaseROIService() { }

    protected enum DAOName {

        MEDIA_TYPE_DAO("MediaTypeDAO"),
        FEE_TYPE_DAO("FeeTypeDAO"),
        PAYMENT_METHOD_DAO("PaymentMethodDAO"),
        DELIVERY_METHOD_DAO("DeliveryMethodDAO"),
        WEIGHT_DAO("WeightDAO"),
        BILLING_TEMPLATE_DAO("BillingTemplateDAO"),
        BILLING_TIER_DAO("BillingTierDAO"),
        REASON_DAO("ReasonDAO"),
        REQUESTOR_TYPE_DAO("RequestorTypeDAO"),
        DOCUMENT_TYPE_DAO("DocumentTypeDAO"),
        LETTER_TEMPLATE_DAO("LetterTemplateDAO"),
        REQUESTOR_DAO("RequestorDAO"),
        ROI_HPF_DAO("ROIHPFDAO"),
        ADMINLOV_DAO("AdminLoVDAO"),
        ROI_SUPPLEMENTARY_DAO("ROISupplementaryDAO"),
        OUTPUT_INTEGRATION_DAO("OutputIntegrationDAO"),
        ROI_ATTACHMENT_DAO("ROIAttachmentDAO"),
        ROI_COUNTRYCODECONFIG_DAO("CountryCodeConfigurationDAO"),

        // Added for CR# 346,503
        ATTACHMENT_DAO("AttachmentDAO"),
        SYSPARAM_DAO("SysParamDAO"),
        //-------------

        // Added for the HPF 15.2 Enhancement
        INVOICEDUEDAYS_DAO("InvoiceDueDaysDAO"),
        TAXPERFACILITY_DAO("TaxPerFacilityDAO"),

        //Added for 16.0
        REQUEST_PATIENT_DAO("RequestCorePatientDAO"),
        REQUEST_CORE_DAO("RequestCoreDAO"),
        REQUEST_CORE_CHARGES("RequestCoreChargesDAO"),
        OVERDUE_INVOICE_CORE_DAO("OverDueInvoiceCoreDAO"),

		//Added for RequestCoreDelivery
        REQUEST_CORE_DELIVERY_DAO("RequestCoreDeliveryDAO"),

        //For Ledger journal entry
        JOURNAL_DAO("JournalDAO"),
        JOURNAL_TEMPLATE_DAO("JournalTemplateDAO"),

        RELEASE_HISTORY_DAO("ReleaseHistoryDAO"),
        REQUESTOR_STATEMENT_DAO("RequestorStatementDAO");

        private final String _daoName;

        private DAOName(String name) { _daoName = name; }

        @Override
        public String toString() { return _daoName; }
    }

    /**
     * Defines the service name and its bean Id for the spring beans
     * @date   Dec 5, 2012
     * @since  Dec 5, 2012
     */
    protected enum ServiceName {

        // Admin related Services
        ROI_ADMIN_SERVICE("com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl"),
        BILLING_ADMIN_SERVICE("com.mckesson.eig.roi.admin.service.BillingAdminServiceImpl"),
        CONFIGURE_DAYS_SERVICE(
                        "com.mckesson.eig.roi.configuredays.service.ConfigureDaysServiceImpl"),

        // Request & billing Services
        REQUEST_CORE_SERVICE("com.mckesson.eig.roi.request.service.RequestCoreServiceImpl"),
        BILLING_CORE_SERVICE("com.mckesson.eig.roi.billing.service.BillingCoreServiceImpl"),
        REQUESTOR_SERVICE("com.mckesson.eig.roi.requestor.service.RequestorServiceImpl"),
        OVERDUE_INVOICE_CORE_SERVICE(
                        "com.mckesson.eig.roi.billing.service.OverDueInvoiceCoreServiceImpl"),

        // patient services
        CCD_CONVERSION_SERVICE("com.mckesson.eig.roi.ccd.service.CcdConversionServiceImpl"),
        ROI_ATTACHMENT_SERVICE("com.mckesson.eig.roi.admin.service.ROIAttachmentServiceImpl"),
        ROI_SUPPLEMENTARY_SERVICE(
                    "com.mckesson.eig.roi.supplementary.service.ROISupplementaryServiceImpl"),

        // Report services
        REPORT_SERVICE("com.mckesson.eig.reports.service.ReportService"),

        // Util services
        OPEN_OFFICE_UTILS_SERVICE("OpenOfficeUtils"),

        // MU services
        MU_UPDATE_ROIOUTBOUND_SERVICE(
                "com.mckesson.eig.roi.muroioutbound.service.MUUpdateROIOutboundServiceCoreImpl"),
        MU_ROIOUTBOUND_SERVICE(
                            "com.mckesson.eig.roi.muroioutbound.service.MUROIOutboundServiceImpl"),
        MU_CREATE_ROIOUTBOUND_SERVICE(
                 "com.mckesson.eig.roi.muroioutbound.service.MUCreateROIOutboundServiceCoreImpl"),

        // Journel services
        JOURNEL_TEMPLATE_SERVICE("com.mckesson.eig.roi.journal.service.JournalTemplateServiceImpl"),
        JOURNEL_SERVICE("com.mckesson.eig.roi.journal.service.JournalServiceImpl"),
        ROI_OUTPUT_SERVICE("com.mckesson.eig.roi.output.service.ROIOutputServiceImpl");

        private final String _serviceName;
        private ServiceName(String name) { _serviceName = name; }

        @Override
        public String toString() { return _serviceName; }
    }

    /**
     * This method is used to return the corresponding local service
     * instance for the specified service name by accessing the ROI Service Factory.
     *
     * @param daoName Name of the local DAO.
     * @return Instance of the DAO.
     */
    protected ROIDAO getDAO(DAOName daoName) {
        return (ROIDAO) SpringUtilities.getInstance().getBeanFactory().
                                                          getBean(daoName.toString());
    }

    protected void setDefaultValues(ROIDAO dao, BaseModel base, boolean isNew) {
        Timestamp date = dao.getDate();
        if (isNew) {
            base.setCreatedBy(getUser().getInstanceId());
            base.setCreatedDt(date);
        } else {
            base.setModifiedBy(getUser().getInstanceId());
        }
        base.setModifiedDt(date);
    }


    /**
     * This method returns the Logged in user details
     *
     * @return Authenticated user details
     */
    protected User getUser() {
        return (User) CxfWsSession.getSessionData(AUTHENTICATED_USER);
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
    protected void doAudit(String comment, int userId, Timestamp ts, String auditCode,
                            String facility, String mrn, String encounter) {

        ROIAuditManager auditMgr = getROIAuditManager();
        AuditEvent ae = auditMgr.createAuditEvent(auditCode,
                                                  userId,
                                                  comment,
                                                  facility,
                                                  ts,
                                                  AuditEvent.SUCCESS);
        ae.setMrn(mrn);
        ae.setEncounter(encounter);
        auditMgr.createAuditEntry(ae);
    }

    protected void auditAdmin(String comment, int userId, Timestamp ts) {
        doAudit(comment, userId, ts, ROIConstants.ADMIN_AUDIT_CODE,
                ROIConstants.DEFAULT_FACILITY, null, null);
    }

    /**
     * This method is used to audit the user operations
     * @param comment The audit comment
     * @param userId The user id of the user performing the operation
     * @param ts The time stamp of the operation audited
     */
    protected void audit(String comment, int userId, Timestamp ts) {
        doAudit(comment, userId, ts, ROIConstants.NORMAL_AUDIT_CODE,
                ROIConstants.DEFAULT_FACILITY, null, null);
    }

    protected void auditRequest(String comment, int userId, Timestamp ts, String auditCode) {
        doAudit(comment, userId, ts, auditCode, ROIConstants.DEFAULT_FACILITY, null, null);
    }

    protected BaseROIService getService(String serviceName) {
        return (BaseROIService) SpringUtilities.getInstance().getBeanFactory().getBean(serviceName);
    }

    protected BaseROIService getService(ServiceName serviceName) {
        return getService(serviceName.toString());
    }

    /**
     * This will append  <![CDATA[....]]> in xml
     * @param details details xml
     * @return
     */
    protected String appendCDATAInXML(String details) {

        try {

            StringBuffer sb = new StringBuffer(details);

            int index = sb.indexOf(">"); // find the index of first element
            sb.insert(index + 1, "<![CDATA[");
            index = sb.lastIndexOf("<");
            sb.insert(index, "]]>");

            return sb.toString();
        } catch (Throwable e) {
            //if only root node then CDATA not appended
            return details;
        }
    }

    /**
     * This method will remove CDATA from input xml string
     * @param xml
     * @return
     */
    protected String trimCDATAFromXML(String xml) {

        try {

            StringBuffer sb = new StringBuffer(xml);

            int index = sb.lastIndexOf("]]>");
            sb.delete(index, index + THREE);

            int ix = sb.indexOf("<![CDATA[");
            sb.delete(ix, ix + NINE);

            return sb.toString();
        } catch (Throwable e)  {
           return xml;
        }
    }


    protected double supressDollarSymbol(String amount) {

        try {

            return (StringUtilities.isEmpty(amount)) ? 0
                    : CURRENCY_FORMAT_US.parse(amount).doubleValue();
        } catch (ParseException e) {
            throw new ROIException(ROIClientErrorCodes.INVALID_CURRENCY_TYPE);
        }
    }

    /**
     * This method sets the default details for the given object
     * The default details includes the createdDate, createdBy, modified date and modifiedBy
     * @param object
     * @param date
     * @param isNew
     */
    public void setDefaultDetails(Object object, Date date, boolean isNew) {

        Integer userId = getUser().getInstanceId();
        String userDisplayName = getUser().getDisplayName();
        Class< ? > beanClazz = object.getClass();
        BeanInfo info = null;
        try {

            info = Introspector.getBeanInfo(beanClazz);
            PropertyDescriptor[] propDescriptors = info.getPropertyDescriptors();
            for (PropertyDescriptor property : propDescriptors) {

                String propertyName = property.getName();
                // if the method name is setModifiedBy, the user seq is set
                if ("modifiedBy".equalsIgnoreCase(propertyName)) {

                    Method writeMethod = property.getWriteMethod();
                    invokeMethod(object, writeMethod, new Object[]{userId});
                 // if the method name is setModifiedDate/ set ModifiedDt, the given date is set
                } else if ("modifiedDate".equalsIgnoreCase(propertyName)
                        || "modifiedDt".equalsIgnoreCase(propertyName)) {

                    Method writeMethod = property.getWriteMethod();
                    invokeMethod(object, writeMethod, new Object[]{date});
                }

                if (!isNew) { continue; }
                // if the method name is setCreatedBy, the user seq is set
                if ("createdBy".equalsIgnoreCase(propertyName)) {

                    Method writeMethod = property.getWriteMethod();
                    invokeMethod(object, writeMethod, new Object[]{userId});
                // if the method name is setcreatedDate/ setCreatedDt, the given date is set
                } else if ("createdDate".equalsIgnoreCase(propertyName)
                            || "createdDt".equalsIgnoreCase(propertyName)) {

                    Method writeMethod = property.getWriteMethod();
                    invokeMethod(object, writeMethod, new Object[]{date});

                // if the method name is setOriginator, the user name is set
                } else if ("originator".equalsIgnoreCase(propertyName)) {

                    Method writeMethod = property.getWriteMethod();
                    invokeMethod(object, writeMethod, new Object[]{userDisplayName});
                }
            }

        } catch (Exception ex) {
            throw new ROIException(ex,
                                   ROIClientErrorCodes.UNABLE_TO_LOAD_DEFAULT_VALUES,
                                   "Unable to set default details for the clazz" + beanClazz);
        }
    }

    /**
     * @param object
     * @param property
     * @param userId
     */
    private void invokeMethod(Object object, Method method, Object[] parameters) {

        try {


            if (null == method) {
                LOG.warn("Method to write the values cannot be null", new NullPointerException());
            }

            method.invoke(object, parameters);

        } catch (Exception ex) {
            throw new ROIException(ex, ROIClientErrorCodes.UNABLE_TO_INVOKE_METHOD);
        }
    }
}
