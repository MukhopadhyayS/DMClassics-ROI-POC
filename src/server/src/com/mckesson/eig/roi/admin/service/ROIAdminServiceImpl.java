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


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.mckesson.eig.Security;
import com.mckesson.eig.User;
import com.mckesson.eig.roi.admin.dao.AdminLoVDAO;
import com.mckesson.eig.roi.admin.dao.AdminLoVDAOImpl;
import com.mckesson.eig.roi.admin.dao.AttachmentDAO;
import com.mckesson.eig.roi.admin.dao.BillingTemplateDAO;
import com.mckesson.eig.roi.admin.dao.BillingTierDAO;
import com.mckesson.eig.roi.admin.dao.CountryCodeConfigurationDAO;
import com.mckesson.eig.roi.admin.dao.DeliveryMethodDAO;
import com.mckesson.eig.roi.admin.dao.DocumentTypeDAO;
import com.mckesson.eig.roi.admin.dao.InvoiceDueDaysDAO;
import com.mckesson.eig.roi.admin.dao.LetterTemplateDAO;
import com.mckesson.eig.roi.admin.dao.OutputIntegrationDAO;
import com.mckesson.eig.roi.admin.dao.ReasonDAO;
import com.mckesson.eig.roi.admin.dao.RequestorTypeDAO;
import com.mckesson.eig.roi.admin.dao.SysParamDAO;
import com.mckesson.eig.roi.admin.dao.WeightDAO;
import com.mckesson.eig.roi.admin.model.AttachmentLocation;
import com.mckesson.eig.roi.admin.model.BillingTemplatesList;
import com.mckesson.eig.roi.admin.model.Country;
import com.mckesson.eig.roi.admin.model.DeliveryMethod;
import com.mckesson.eig.roi.admin.model.DeliveryMethodList;
import com.mckesson.eig.roi.admin.model.Designation;
import com.mckesson.eig.roi.admin.model.DocTypeAudit;
import com.mckesson.eig.roi.admin.model.DocTypeAuditList;
import com.mckesson.eig.roi.admin.model.DocTypeDesignations;
import com.mckesson.eig.roi.admin.model.InvoiceDueDays;
import com.mckesson.eig.roi.admin.model.LetterTemplate;
import com.mckesson.eig.roi.admin.model.LetterTemplateList;
import com.mckesson.eig.roi.admin.model.Note;
import com.mckesson.eig.roi.admin.model.NotesList;
import com.mckesson.eig.roi.admin.model.OutputServerProperties;
import com.mckesson.eig.roi.admin.model.ROIAppData;
import com.mckesson.eig.roi.admin.model.Reason;
import com.mckesson.eig.roi.admin.model.Reasons;
import com.mckesson.eig.roi.admin.model.ReasonsList;
import com.mckesson.eig.roi.admin.model.RelatedBillingTemplate;
import com.mckesson.eig.roi.admin.model.RelatedBillingTier;
import com.mckesson.eig.roi.admin.model.RequestStatusMap;
import com.mckesson.eig.roi.admin.model.RequestorType;
import com.mckesson.eig.roi.admin.model.RequestorTypesList;
import com.mckesson.eig.roi.admin.model.SSNMask;
import com.mckesson.eig.roi.admin.model.Weight;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.service.BaseROIService;
import com.mckesson.eig.roi.hpf.dao.UserSecurityHibernateDao;
import com.mckesson.eig.roi.hpf.model.UserSecurity;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 * This class implements all ROI Admin services
 *
 * @author OFS
 * @date Jul 02, 2009
 * @since HPF 13.1 [ROI]; Apr 04,2008
 */
public class ROIAdminServiceImpl
extends BaseROIService
implements ROIAdminService {

    private static final Log LOG = LogFactory.getLogger(ROIAdminServiceImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    public static final String ENTERPRISE = "E_P_R_S";

    /**
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     *      #createDeliveryMethod(com.mckesson.eig.roi.admin.model.DeliveryMethod)
     */
    public long createDeliveryMethod(DeliveryMethod dm) {

        final String logSM = "createDeliveryMethod(deliveryMethod)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + dm);
        }

        try {

            DeliveryMethodDAO dao = (DeliveryMethodDAO) getDAO(DAOName.DELIVERY_METHOD_DAO);
            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();

            if (!validator.validateDeliveryMethod(dm, dao, true)) {
                throw validator.getException();
            }

            if (dm.getIsDefault()) {
                DeliveryMethod defaultDm = dao.getDefaultDeliveryMethod();
                if (defaultDm != null) {
                    dao.clearDefaultDeliveryMethod(defaultDm);
                }
            }

            Timestamp date = dao.getDate();
            setDeliveryMethodDetails(dm, date, true);
            long deliveryMethodId = dao.createDeliveryMethod(dm);

            auditAdmin(dm.toCreateAudit(getUser().getFullName()), getUser().getInstanceId(), date);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End: Delivery Method : " + dm);
            }

            return deliveryMethodId;

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.DELIVERY_METHOD_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService #retrieveDeliveryMethod(long)
     */
    public DeliveryMethod retrieveDeliveryMethod(long id) {

        final String logSM = "retrieveDeliveryMethod(id)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + id);
        }

        DeliveryMethodDAO dao = (DeliveryMethodDAO) getDAO(DAOName.DELIVERY_METHOD_DAO);
        DeliveryMethod deliveryMethod = dao.retrieveDeliveryMethod(id);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:"  + deliveryMethod);
        }

        return deliveryMethod;
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService#retrieveAllMediaTypes()
     */
    public DeliveryMethodList retrieveAllDeliveryMethods(boolean fetchDetails) {

        final String logSM = "retrieveAllDeliveryMethods()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {

            DeliveryMethodDAO dao = (DeliveryMethodDAO) getDAO(DAOName.DELIVERY_METHOD_DAO);
            if (fetchDetails) {
             return dao.retrieveAllDeliveryMethods();
            }
             return dao.retrieveAllDeliveryMethodNames();
            } catch (Throwable e) {
                LOG.error(e);
                
                throw new ROIException(e, ROIClientErrorCodes.DELIVERY_METHOD_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     *      #updateDeliveryMethod(com.mckesson.eig.roi.admin.model.DeliveryMethod)
     */
    public DeliveryMethod updateDeliveryMethod(DeliveryMethod deliveryMethod) {

        final String logSM = "updateDeliveryMethod(deliveryMethod)";
        LOG.debug(logSM + ">>Start:" + deliveryMethod);

        try {

            DeliveryMethodDAO dao = (DeliveryMethodDAO) getDAO(DAOName.DELIVERY_METHOD_DAO);
            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();

            if (!validator.validateDeliveryMethod(deliveryMethod, dao, false)) {
                throw validator.getException();
            }

            DeliveryMethod defaultDm = null;
            if (deliveryMethod.getIsDefault()) {

                defaultDm = dao.getDefaultDeliveryMethod();
                if ((defaultDm != null) && (deliveryMethod.getId() != defaultDm.getId())) {
                    dao.clearDefaultDeliveryMethod(defaultDm);
                }
            }

            Timestamp date = dao.getDate();
            setDeliveryMethodDetails(deliveryMethod, date, false);

            DeliveryMethod originalDM = null;
            if (defaultDm != null) {

                if (deliveryMethod.getId() != defaultDm.getId()) {
                    originalDM = retrieveDeliveryMethod(deliveryMethod.getId());
                } else {
                    originalDM = defaultDm;
                }
            } else {
                originalDM = retrieveDeliveryMethod(deliveryMethod.getId());
            }

            String comment = deliveryMethod.toUpdateAudit(getUser().getFullName(), originalDM);
            DeliveryMethod newDM = dao.updateDeliveryMethod(deliveryMethod, originalDM);

            auditAdmin(comment, getUser().getInstanceId(), date);
            LOG.debug(logSM + "<<End: Updated Delivery Method " + newDM);

            return newDM;

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.DELIVERY_METHOD_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService#deleteDeliveryMethod(long)
     */
    public void deleteDeliveryMethod(long deliveryMethodId) {

        final String logSM = "deleteDeliveryMethod(deliveryMethodId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + deliveryMethodId);
        }

        try {

            DeliveryMethodDAO dao = (DeliveryMethodDAO) getDAO(DAOName.DELIVERY_METHOD_DAO);
            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();

            DeliveryMethod dm = dao.deleteDeliveryMethod(deliveryMethodId);

            Timestamp date = dao.getDate();
            auditAdmin(dm.toDeleteAudit(getUser().getFullName()), getUser().getInstanceId(), date);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.DELIVERY_METHOD_OPERATION_FAILED);
        }
    }

    /**
     * This method sets the default values while creating/updating a Delivery Method
     *
     * @param delivery method details to be set
     * @param dao used to get the database date
     * @param isNew decides to set values for creation/modification
     */
    private void setDeliveryMethodDetails(DeliveryMethod dm, Timestamp date, boolean isNew) {

        if (isNew) {

            dm.setOrgId(1);
            dm.setCreatedBy(getUser().getInstanceId());
        } else {
            dm.setModifiedBy(getUser().getInstanceId());
        }
        dm.setModifiedDt(date);
    }


    /**
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService#retrieveWeight()
     */
    public Weight retrieveWeight() {

        final String logSM = "retrieveWeight()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {

            WeightDAO dao = (WeightDAO) getDAO(DAOName.WEIGHT_DAO);
            Weight wt = dao.retrieveWeight();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

            return wt;

        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.WEIGHT_METHOD_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     *      #updateWeight(com.mckesson.eig.roi.admin.model.Weight)
     */
    public Weight updateWeight(Weight modifiedWt) {

        final String logSM = "updateWeight(wt)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + modifiedWt);
        }

        try {

            WeightDAO dao = (WeightDAO) getDAO(DAOName.WEIGHT_DAO);
            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();

            if (!validator.validateWeight(modifiedWt)) {
                throw validator.getException();
            }

            Weight originalWt = dao.retrieveWeight();

            setWeightDetails(modifiedWt, dao);
            String comment = modifiedWt.toUpdateAudit(originalWt);

            Weight updatedWt = dao.updateWeight(modifiedWt);

            auditAdmin(comment, getUser().getInstanceId(), dao.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

            return updatedWt;

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.WEIGHT_METHOD_OPERATION_FAILED);
        }
    }

    /**
     * This method sets the non-UI values for the updated weight
     *
     * @param Weight updated weight to be set
     * @param dao WeightDAO to access database
     *
     */
    private void setWeightDetails(Weight wt, WeightDAO dao) {

        wt.setModifiedBy(getUser().getInstanceId());
        Timestamp date = dao.getDate();
        wt.setModifiedDate(date);
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     * #createReason(com.mckesson.eig.roi.admin.model.Reason)
     */
    public long createReason(Reason reason) {

        final String logSM = "createReason(reason)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {

            ReasonDAO dao = (ReasonDAO) getDAO(DAOName.REASON_DAO);
            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();

            if (!validator.validateReason(reason, dao, true)) {
                throw validator.getException();
            }
            Timestamp date = dao.getDate();
            setDefaultReasonDetails(reason, date, true);
            long reasonId = dao.createReason(reason);

            auditAdmin(reason.toCreateAudit(), getUser().getInstanceId(), date);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End: Created Reason ID : " + reasonId);
            }

            return reasonId;

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.REASON_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     * #retrieveAllReasonsByType(java.lang.String)
     */
    public ReasonsList retrieveAllReasonsByType(String reasonType) {

        final String logSM = "retrieveAllReasonsByType(reasonType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: Reason Type : " + reasonType);
        }

        try {

            ReasonDAO dao = (ReasonDAO) getDAO(DAOName.REASON_DAO);
            ReasonsList reasons = dao.retrieveAllReasonsByType(reasonType);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

            return reasons;

        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.REASON_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService#retrieveReason(long)
     */

    public  Reason retrieveReason(long reasonId) {

        final String logSM = "retrieveReason(reasonId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: Reason Id : " + reasonId);
        }

        ReasonDAO dao = (ReasonDAO) getDAO(DAOName.REASON_DAO);
        Reason retrievedReason = dao.retrieveReason(reasonId);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + retrievedReason);
        }

        return retrievedReason;
    }


    /**
     * This method sets the default values while creating/updating a Reason
     *
     * @param reason details to be set
     * @param dao used to get the database date
     * @param isNew decides to set values for creation/modification
     */
    private void setDefaultReasonDetails(Reason reason, Timestamp date, boolean isNew) {

        if (isNew) {

            reason.setOrgId(1);
            reason.setCreatedBy(getUser().getInstanceId());
        } else {
            reason.setModifiedBy(getUser().getInstanceId());
        }

        if (Reason.ReasonType.request.toString().equalsIgnoreCase(reason.getType())
                || Reason.ReasonType.adjustment.toString().equalsIgnoreCase(reason.getType())) {

            reason.setStatus(ROIConstants.STATUS_NOT_APPLICABLE_ID);
        }
        reason.setModifiedDt(date);
        reason.setActive(true);
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService#deleteReason(long)
     */
    public void deleteReason(long reasonId) {

        final String logSM = "deleteReason(reasonId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: Id to be deleted :" + reasonId);
        }

        try {

            ReasonDAO dao = (ReasonDAO) getDAO(DAOName.REASON_DAO);
            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();

            Reason reason = dao.deleteReason(reasonId);
            auditAdmin(reason.toDeleteAudit(), getUser().getInstanceId(), dao.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.REASON_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     * #updateReason(com.mckesson.eig.roi.admin.model.Reason)
     */
    public Reason updateReason(Reason reason) {

        final String logSM = "updateReason(reason)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + reason);
        }

        try {

            ReasonDAO dao = (ReasonDAO) getDAO(DAOName.REASON_DAO);
            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();

            if (!validator.validateReason(reason, dao, false)) {
                throw validator.getException();
            }

            Timestamp date = dao.getDate();
            setDefaultReasonDetails(reason, date, false);
            Reason oldReason = retrieveReason(reason.getId());
            reason.copyFrom(oldReason);
            String comment = reason.toUpdateAudit(oldReason);
            Reason updatedReason = dao.updateReason(reason);

            auditAdmin(comment, getUser().getInstanceId(), date);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + updatedReason);
            }

            return updatedReason;

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.REASON_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     * #createRequestorType(com.mckesson.eig.roi.admin.model.RequestorType)
     */
    public RequestorType createRequestorType(RequestorType requestorType) {

        final String logSM = "createRequestorType(requestorType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestorType);
        }

        try {

            RequestorTypeDAO dao = (RequestorTypeDAO) getDAO(DAOName.REQUESTOR_TYPE_DAO);
            BillingTierDAO  btDAO = (BillingTierDAO) getDAO(DAOName.BILLING_TIER_DAO);
            BillingTemplateDAO  btmpDAO = (BillingTemplateDAO) getDAO(DAOName.BILLING_TEMPLATE_DAO);
            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();

            if (!validator.validateRequestorType(dao, btDAO, btmpDAO, requestorType, true)) {
                throw validator.getException();
            }

            Timestamp date = dao.getDate();
            setDefaultRequestorTypeDetails(requestorType, date, true);
            RequestorType rt = dao.createRequestorType(requestorType);

            auditAdmin(requestorType.toCreateAudit(), getUser().getInstanceId(), date);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End: New RequestorTypeId == " + rt);
            }

            return rt;

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.REQUESTOR_TYPE_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     * #retrieveRequestorType(long)
     */
    public RequestorType retrieveRequestorType(long requestorTypeId) {

        final String logSM = "retrieveRequestorType(requestorTypeId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestorTypeId);
        }

        RequestorTypeDAO dao = (RequestorTypeDAO) getDAO(DAOName.REQUESTOR_TYPE_DAO);
        RequestorType requestorType = dao.retrieveRequestorType(requestorTypeId);
        long count = dao.getAssociatedRequestorCount(requestorTypeId);
        requestorType.setIsAssociated(count > 0);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + requestorType);
        }

        return requestorType;
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     * #retrieveAllRequestorTypes(boolean)
     */
    public RequestorTypesList retrieveAllRequestorTypes(boolean loadAssociations) {

        final String logSM = "retrieveAllRequestorTypes()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:LoadAssociation = " + loadAssociations);
        }

        try {

            RequestorTypeDAO dao = (RequestorTypeDAO) getDAO(DAOName.REQUESTOR_TYPE_DAO);
            RequestorTypesList rTypes = new RequestorTypesList();

            if (loadAssociations) {
                rTypes = dao.retrieveAllRequestorTypes();
            } else {
                rTypes = dao.retrieveAllRequestorTypeNames();
            }
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End: No of records " + rTypes.getRequestorTypes().size());
            }

            return rTypes;

        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.REQUESTOR_TYPE_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     * #updateRequestorType(com.mckesson.eig.roi.admin.model.RequestorType)
     */
    public RequestorType updateRequestorType(RequestorType requestorType) {

        final String logSM = "updateRequestorType(requestorType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestorType);
        }

        try {

            RequestorTypeDAO dao = (RequestorTypeDAO) getDAO(DAOName.REQUESTOR_TYPE_DAO);
            BillingTierDAO  btDAO = (BillingTierDAO) getDAO(DAOName.BILLING_TIER_DAO);
            BillingTemplateDAO bDAO = (BillingTemplateDAO) getDAO(DAOName.BILLING_TEMPLATE_DAO);

            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();
            if (!validator.validateRequestorType(dao, btDAO, bDAO, requestorType, false)) {
                throw validator.getException();
            }

            Timestamp date = dao.getDate();
            setDefaultRequestorTypeDetails(requestorType, date, false);

            RequestorType rt = dao.retrieveRequestorType(requestorType.getId());
            BillingTemplatesList bt = bDAO.retrieveAllBillingTemplates();

            requestorType.copyFrom(rt);
            String oldBTierNames = getRelatedBillingTierNames(rt);
            String newBTierNames = getRelatedBillingTierNames(requestorType);
            String comment = requestorType.toUpdateAudit(rt, bt, oldBTierNames, newBTierNames);

            RequestorType updatedRT = dao.updateRequestorType(requestorType);

            auditAdmin(comment, getUser().getInstanceId(), date);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End: Updated RequestorType " + updatedRT);
            }
            return updatedRT;

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.REQUESTOR_TYPE_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     * #deleteRequestorType(long)
     */
    public void deleteRequestorType(long requestorTypeId) {

        final String logSM = "deleteRequestorType(requestorTypeId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestorTypeId);
        }

        try {

            RequestorTypeDAO dao = (RequestorTypeDAO) getDAO(DAOName.REQUESTOR_TYPE_DAO);
            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();

            if (!validator.validateRequestorTypeDeletion(requestorTypeId, dao)) {
                throw validator.getException();
            }

            RequestorType rt = dao.deleteRequestorType(requestorTypeId);
            auditAdmin(rt.toDeleteAudit(rt.getName()), getUser().getInstanceId(), dao.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.REQUESTOR_TYPE_OPERATION_FAILED);
        }
    }

    /**
     * This method sets the default values while creating/updating a requestorType
     *
     * @param rt RequestorType details to be set
     * @param dao used to get the database date
     * @param isNew decides to set the values
     */
    private void setDefaultRequestorTypeDetails(RequestorType rt, Timestamp date, boolean isNew) {

        if (isNew) {

            rt.setOrgId(1);
            rt.setCreatedBy(getUser().getInstanceId());
            rt.setActive(true);
        } else {
            rt.setModifiedBy(getUser().getInstanceId());
        }
        rt.setModifiedDate(date);

        //In case if there is no billing template associated with a RequestorType
        if (rt.getRelatedBillingTemplate() == null) {
            rt.setRelatedBillingTemplate(new HashSet <RelatedBillingTemplate>());
        }
        for (RelatedBillingTemplate rtBillingtemplate : rt.getRelatedBillingTemplate()) {

            if (isNew) {
                rtBillingtemplate.setCreatedBy(getUser().getInstanceId());
            } else {
                rtBillingtemplate.setModifiedBy(getUser().getInstanceId());
            }
            rtBillingtemplate.setCreatedBy(getUser().getInstanceId());
            rtBillingtemplate.setModifiedDate(date);
        }

        for (RelatedBillingTier rtBillingtier : rt.getRelatedBillingTier()) {

            if (isNew) {
                rtBillingtier.setCreatedBy(getUser().getInstanceId());
            } else {
                rtBillingtier.setModifiedBy(getUser().getInstanceId());
            }
            rtBillingtier.setCreatedBy(getUser().getInstanceId());
            rtBillingtier.setModifiedDate(date);
        }

    }

    /**
     * This method will get the Billing Tier Name for the input
     * Billing Tier Id
     * @param id Billing Tier Id
     * @return Name of the Billing Tier
     */
    private String getBillingTierName(long id) {

        final String logSM = "getBillingTierName(id)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + id);
        }

        BillingTierDAO dao = (BillingTierDAO) getDAO(DAOName.BILLING_TIER_DAO);
        String name = dao.retrieveBillingTier(id).getName();

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + name);
        }

        return name;
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     * #designateDocumentTypes(long, com.mckesson.eig.roi.hpf.model.DocTypeList, java.lang.String)
     */
    public void designateDocumentTypes(long codeSetId, DocTypeDesignations newDesig,DocTypeAuditList docTypeAuditList) {

        final String logSM = "designateDocumentTypes(codeSetId, newDesig,docTypeAuditList)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: codeSetId:" + codeSetId + newDesig + docTypeAuditList);
        }

        try {

            DocumentTypeDAO dao = (DocumentTypeDAO) getDAO(DAOName.DOCUMENT_TYPE_DAO);
            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();
            if (!validator.validateDocType(newDesig)) {
                throw validator.getException();
            }

            com.mckesson.eig.roi.hpf.model.User user = getUser();
            DocTypeDesignations oldDesig = dao.retrieveDesignations(codeSetId);
            dao.designateDocumentTypes(codeSetId, newDesig, oldDesig, user);
   
            if(CollectionUtilities.hasContent(docTypeAuditList.getDocTypeAudit()))
            {
               for(DocTypeAudit docTypeAudit : docTypeAuditList.getDocTypeAudit())
               {
                   String comment = docTypeAudit.createAuditComment(docTypeAudit);
                   Timestamp date = dao.getDate();
                   auditAdmin(comment, user.getInstanceId(), date);
               }
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {

            LOG.error(logSM + "Error :" + e);
            throw new ROIException(e, ROIClientErrorCodes.DISCLOSURE_DOCUMENT_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService#retrieveDesignations(long)
     */
    public DocTypeDesignations retrieveDesignations(long codeSetId) {

        final String logSM = "retrieveDesigantions(codeSetId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + codeSetId);
        }

        try {

            DocumentTypeDAO dao = (DocumentTypeDAO) getDAO(DAOName.DOCUMENT_TYPE_DAO);
            DocTypeDesignations desi = dao.retrieveDesignations(codeSetId);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + desi.getDesignation().size());
            }

            return desi;

        } catch (Throwable e) {

            LOG.error(logSM + "Error :" + e);
            throw new ROIException(e, ROIClientErrorCodes.DISCLOSURE_DOCUMENT_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     * #createLetterTemplate(com.mckesson.eig.roi.admin.model.LetterTemplate)
     */
    public long createLetterTemplate(LetterTemplate lt) {

        final String logSM = "createLetterTemplate(lt)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start" + lt);
        }

        try {

            LetterTemplateDAO dao = (LetterTemplateDAO) getDAO(DAOName.LETTER_TEMPLATE_DAO);
            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();

            if (!validator.validateLetterTemplate(lt, dao, true)) {
                deleteDocument(dao, lt);
                throw validator.getException();
            }

            if (lt.getIsDefault()) {

                LetterTemplate lTemplate = dao.getDefaultLetterTemplate(lt.getLetterType());
                if ((lTemplate != null) && (lt.getTemplateId() != lTemplate.getTemplateId())) {
                    dao.clearDefault(lTemplate);
                }
            }
            Timestamp date = dao.getDate();
            setDefaultLetterTemplateDetails(lt, date, true);
            Long id = dao.createLetterTemplate(lt);

            auditAdmin(lt.toCreateAudit(), getUser().getInstanceId(), date);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Created LetterTemplate Id = " + id);
            }

            return id;

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.LETTER_TEMPLATE_PROCESS_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     * #retrieveLetterTemplate(long)
     */
    public LetterTemplate retrieveLetterTemplate(long id) {

        final String logSM = "retrieveLetterTemplate(id)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + id);
        }

        LetterTemplateDAO dao = (LetterTemplateDAO) getDAO(DAOName.LETTER_TEMPLATE_DAO);
        LetterTemplate lt = dao.retrieveLetterTemplate(id);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + lt);
        }

        return lt;
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     * #retrieveAllLetterTemplates()
     */
    public LetterTemplateList retrieveAllLetterTemplates() {

        final String logSM = "retrieveAllLetterTemplates()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {

            LetterTemplateDAO dao = (LetterTemplateDAO) getDAO(DAOName.LETTER_TEMPLATE_DAO);
            LetterTemplateList ltList = dao.retrieveAllLetterTemplates();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End No of records:" + ltList.getLetterTemplates().size());
            }

            return ltList;

        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.LETTER_TEMPLATE_PROCESS_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     * #deleteLetterTemplate(long)
     */
    public void deleteLetterTemplate(long id) {

        final String logSM = "deleteLetterTemplate(id)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + id);
        }

        try {

            LetterTemplateDAO dao = (LetterTemplateDAO) getDAO(DAOName.LETTER_TEMPLATE_DAO);
            LetterTemplate lt = dao.deleteLetterTemplate(id);

            String name = lt.toDeleteAudit(lt.getName());
            auditAdmin(lt.toDeleteAudit(lt.getName()), getUser().getInstanceId(), dao.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.LETTER_TEMPLATE_PROCESS_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     * #updateLetterTemplate(com.mckesson.eig.roi.admin.model.LetterTemplate)
     */
    public LetterTemplate updateLetterTemplate(LetterTemplate lt) {

        final String logSM = "updateLetterTemplate(lt)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + lt);
        }

        try {

            LetterTemplateDAO dao = (LetterTemplateDAO) getDAO(DAOName.LETTER_TEMPLATE_DAO);

            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();
            if (!validator.validateLetterTemplate(lt, dao, false)) {
                throw validator.getException();
            }

            if (lt.getIsDefault()) {

                LetterTemplate lTemplate = dao.getDefaultLetterTemplate(lt.getLetterType());
                if ((lTemplate != null) && (lTemplate.getTemplateId() != lt.getTemplateId())) {
                    dao.clearDefault(lTemplate);
                }
            }

            Timestamp date = dao.getDate();
            setDefaultLetterTemplateDetails(lt, date, false);
            LetterTemplate orig = dao.retrieveLetterTemplate(lt.getTemplateId());
            lt.copyFrom(orig);

            String  comment = lt.toUpdateAudit(orig);
            LetterTemplate updated =  dao.updateLetterTemplate(lt);

            auditAdmin(comment, getUser().getInstanceId(), date);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + updated);
            }

            return updated;

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.LETTER_TEMPLATE_PROCESS_FAILED);
        }
    }

    /**
     * This method retrieves all the available request status
     */
    public RequestStatusMap retrieveAllRequestStatus() {

        final String logSM = "retrieveAllRequestStatus()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        RequestStatusMap requestStatusMap = new RequestStatusMap();
        requestStatusMap.setStatusMap(ROIConstants.STATUS_MAP);
        requestStatusMap.setLoggedStatus(ROIConstants.STATUS_LOGGED_ID);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return requestStatusMap;
    }

    /**
     * This method sets the default values while creating/updating a letterTemplate
     *
     * @param lt LetterTEmplate details to be set
     * @param dao used to get the database date
     * @param isNew decides to set the values
     */
    private void setDefaultLetterTemplateDetails(LetterTemplate lt, Timestamp date, boolean isNew) {

        if (isNew) {

            lt.setActive(true);
            lt.setCreatedBy(getUser().getInstanceId());
            lt.setOrgId(1);
        } else {
            lt.setModifiedBy(getUser().getInstanceId());
        }
        lt.setModifiedDate(date);
    }

    private void deleteDocument(LetterTemplateDAO dao, LetterTemplate lt) {
        dao.deleteDocument(lt.getDocId());
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService#retrieveReasonsByStatus(int)
     */
    public Reasons retrieveReasonsByStatus(int statusId) {

        final String logSM = "retrieveReasonsByStatus(statusId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:StatusId: " + statusId);
        }

        ReasonDAO dao = (ReasonDAO) getDAO(DAOName.REASON_DAO);
        List<String> name = dao.getReasonsByStatus(statusId);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:Size Of Reasons:" + name.size());
        }

        return new Reasons(name);
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     * #createNote(com.mckesson.eig.roi.admin.model.Note)
     */
    public long createNote(Note note) {

        final String logSM = "createNote(note)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + note);
        }

        try {

            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();
            AdminLoVDAO dao = (AdminLoVDAO) getDAO(DAOName.ADMINLOV_DAO);

            if (!validator.validateNote(note, dao, true)) {
                throw validator.getException();
            }

            setDefaultNoteDetails(note, dao,  true);
            Long id = dao.createNote(note);

            auditAdmin(note.toCreateAudit(), getUser().getInstanceId(), dao.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + id);
            }

            return id;

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {

            LOG.error(logSM + "Error :" + e);
            throw new ROIException(e, ROIClientErrorCodes.ADMINLOV_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService#retrieveNote(long)
     */
    public Note retrieveNote(long noteId) {

        final String logSM = "retrieveNotes(notesId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + noteId);
        }

        try {

            AdminLoVDAO dao = (AdminLoVDAO) getDAO(DAOName.ADMINLOV_DAO);
            Note note = (Note) dao.retrieveLoV(noteId);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + note);
            }

            return note;

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {

            LOG.error(logSM + "Error :" + e);
            throw new ROIException(e, ROIClientErrorCodes.ADMINLOV_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService#retrieveAllNotes()
     */
    public NotesList retrieveAllNotes() {

        final String logSM = "retrieveAllNotes()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        try {

            AdminLoVDAO dao = (AdminLoVDAO) getDAO(DAOName.ADMINLOV_DAO);
            @SuppressWarnings("unchecked") // not supported by 3rd party API
            List<Note> notes = (List<Note>) dao.retrieveAllNotes();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + notes.size());
            }

            return new NotesList(notes);

        } catch (Throwable e) {

            LOG.error(logSM + "Error :" + e);
            throw new ROIException(e, ROIClientErrorCodes.ADMINLOV_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService#deleteNote(long)
     */
    public void deleteNote(long noteId) {

        final String logSM = "deleteNote(noteId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + noteId);
        }

        try {

            AdminLoVDAOImpl dao = (AdminLoVDAOImpl) getDAO(DAOName.ADMINLOV_DAO);
            Note nte = (Note) dao.deleteNote(noteId);

            auditAdmin(nte.toDeleteAudit(nte.getName()), getUser().getInstanceId(), dao.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {

            LOG.error(logSM + "Error :" + e);
            throw new ROIException(e, ROIClientErrorCodes.ADMINLOV_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     * #updateNote(com.mckesson.eig.roi.admin.model.Note)
     */
    public Note updateNote(Note note) {

        final String logSM = "updateNote(note)";
        LOG.debug(logSM + ">>Start:" + note);

        try {

            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();
            AdminLoVDAO dao = (AdminLoVDAO) getDAO(DAOName.ADMINLOV_DAO);

            if (!validator.validateNote(note, dao, false)) {
                throw validator.getException();
            }

            Note lov = (Note) dao.retrieveLoV(note.getId());
            note.setCreatedBy(lov.getCreatedBy());
            setDefaultNoteDetails(note, dao, false);
            Note nte = (Note) dao.updateNote(note);

            auditAdmin(note.toUpdateAudit(nte.getName()), getUser().getInstanceId(), dao.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + nte);
            }

            return nte;

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {

            LOG.error(logSM + "Error :" + e);
            throw new ROIException(e, ROIClientErrorCodes.ADMINLOV_OPERATION_FAILED);
        }
    }

    private void setDefaultNoteDetails(Note note, AdminLoVDAO dao, boolean isNew) {

        if (isNew) {
            note.setCreatedBy(getUser().getInstanceId());
        } else {
            note.setModifiedBy(getUser().getInstanceId());
        }

        note.setOrgId(1);
        note.setActive(true);
        note.setModifiedDt(dao.getDate());
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService#retrieveSSNMask()
     */
    public SSNMask retrieveSSNMask() {

        final String logSM = "retrieveSSNMask()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {

            AdminLoVDAO dao = (AdminLoVDAO) getDAO(DAOName.ADMINLOV_DAO);
            SSNMask mask = (SSNMask) dao.retrieveLoV(ROIConstants.SSN_MASK_SEQ);
            mask.setApplySSNMask(SSNMask.SSN_MASK_TRUE.equals(mask.getDescription()));
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + mask);
            }

            return mask;

        } catch (Throwable e) {

            LOG.error(logSM + "Error :" + e);
            throw new ROIException(ROIClientErrorCodes.ADMINLOV_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService#updateSSNMask(boolean)
     */
    public SSNMask updateSSNMask(SSNMask ssnMask) {

        final String logSM = "updateSSNMask(applySSNMask)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + ssnMask);
        }

        try {

            AdminLoVDAO dao = (AdminLoVDAO) getDAO(DAOName.ADMINLOV_DAO);
            SSNMask oldSSNMask = (SSNMask) dao.retrieveLoV(ROIConstants.SSN_MASK_SEQ);
            ssnMask.setSSNMaskDetails(oldSSNMask, getUser().getInstanceId(), dao.getDate());
            String comment = ssnMask.toUpdateAudit();
            SSNMask mask =  (SSNMask) dao.updateNote(ssnMask);
            mask.setApplySSNMask(SSNMask.SSN_MASK_TRUE.equals(mask.getDescription()));
            auditAdmin(comment, getUser().getInstanceId(), dao.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + mask);
            }

            return mask;

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {

            LOG.error(logSM + "Error :" + e);
            throw new ROIException(ROIClientErrorCodes.ADMINLOV_OPERATION_FAILED);
        }
    }

    private String getRelatedBillingTierNames(RequestorType rt) {

        String billingTierNames = "";
        if ((rt.getRelatedBillingTier() != null)
            && (rt.getRelatedBillingTier().size() != 0)) {

            for (RelatedBillingTier bTier : rt.getRelatedBillingTier()) {
                billingTierNames = billingTierNames
                                   + getBillingTierName(bTier.getBillingTierId())
                                   + ",";
            }
        }
        if ((billingTierNames != null) && (billingTierNames.lastIndexOf(',') != -1)) {
            billingTierNames = billingTierNames.substring(0, billingTierNames.lastIndexOf(','));
        }

        return billingTierNames;
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     * #retrieveDocTypeIdsByDesignation(java.lang.String)
     */
    public Designation retrieveDocTypeIdsByDesignation(String designation) {

        try {

            final String logSM = "retrieveDocTypeIdsByDesignation(designation)";
            if (DO_DEBUG) {
                LOG.debug(logSM + ">>Start:Designation of documents to be retrieved : "
                                + designation);
            }

            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();
            DocumentTypeDAO dao = (DocumentTypeDAO) getDAO(DAOName.DOCUMENT_TYPE_DAO);

            if (!validator.validateDesignationType(designation)) {
                throw validator.getException();
            }

            List<Long> docIds = dao.retrieveDocTypeIdsByDesignation(designation);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Size of retrieved documents: " + docIds.size());
            }
            return new Designation(designation, docIds);
        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable exe) {
            LOG.error(exe);
            throw new ROIException(exe, ROIClientErrorCodes.DISCLOSURE_DOCUMENT_OPERATION_FAILED);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService#hasLetterTemplate(java.lang.String)
     */
    public boolean hasLetterTemplate(String letterType) {

        final String logSM = "hasLetterTemplate(letterType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Letter Type : " + letterType);
        }

        try {

            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();
            LetterTemplateDAO letterTemplateDAO = (LetterTemplateDAO)
                                                       getDAO(DAOName.LETTER_TEMPLATE_DAO);

            if (!validator.validateLetterTemplateType(letterType)) {
                throw validator.getException();
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

           return letterTemplateDAO.hasLetterTemplate(letterType);

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable exe) {
            LOG.error(exe);
            throw new ROIException(exe, ROIClientErrorCodes.LETTER_TEMPLATE_PROCESS_FAILED);
        }
    }
    /**
    *
    * @see com.mckesson.eig.roi.admin.service.ROIAdminService#retrieveSSNMask()
    */
   public ROIAppData retrieveROIAppData() {

       final String logSM = "retrieveROIAppData()";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start");
       }

       try {

           AdminLoVDAO dao = (AdminLoVDAO) getDAO(DAOName.ADMINLOV_DAO);
           SSNMask mask = (SSNMask) dao.retrieveLoV(ROIConstants.SSN_MASK_SEQ);

           ROIAppData appData = new ROIAppData();
           appData.setHasSSNMasking(SSNMask.SSN_MASK_TRUE.equals(mask.getDescription()));
           appData.setInvoiceDueDays(dao.retrieveGlobalInvoiceDueDays());
           
           Integer userId = getUser().getInstanceId();
           List<String> formFacilities = dao.retrieveFreeFormFacilitiesByUser(userId);
           appData.setFreeFormFacilities(formFacilities);
           
           if (DO_DEBUG) {

               StringBuffer info
               = new StringBuffer().append("SSN Masking = ")
                                   .append(appData.getHasSSNMasking() + " \n")
                                   .append(" Total No. of FreeFormFacilities = ")
                                   .append(appData.getFreeFormFacilities().size());

               LOG.debug(logSM + "<<End:" + info.toString());
           }

           return appData;
       } catch (Throwable e) {

           LOG.error(logSM + "Error :" + e);
           throw new ROIException(ROIClientErrorCodes.ADMINLOV_OPERATION_FAILED);
       }
   }

   /**
    * @see com.mckesson.eig.roi.admin.service.ROIAdminService#getUser(java.lang.String)
    */
    public User getUser(String userId) {

        final String logsM = "getUser(userId)";
        if (DO_DEBUG) {
            LOG.debug(logsM + ">>Start:" + userId);
        }

        try {

            if (StringUtilities.isEmpty(userId)) {
                throw new ROIException(ROIClientErrorCodes.USER_ID_CANNOT_BE_EMPTY);
            }

            String id = getUser().getLoginId();

            UserSecurityHibernateDao userSecurityDao = getUserSecurityHibernateDao();
            com.mckesson.eig.roi.hpf.model.User retrievedUser = userSecurityDao.retrieveUser(id);

            User user = new User();
            setUserDetails(retrievedUser, user);

            // Retrieving user SecurityRights
            List<UserSecurity> userSecurities = userSecurityDao
                                                    .getSecurityRight(getUser().getInstanceId());

            setUserSecurity(userSecurities, user);

            return user;
        } catch (Throwable e) {

            LOG.error(logsM + "Error :" + e);
            throw new ROIException(ROIClientErrorCodes.COULD_NOT_GET_USER);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService#enableOutputService(boolean)
     */
    public void enableOutputService(boolean doEnable) {

        final String logsM = "enableOutputService(doEnable)";
        if (DO_DEBUG) {
            LOG.debug(logsM + ">>Start:" + doEnable);
        }

        try {

            OutputIntegrationDAO dao = (OutputIntegrationDAO)
                                                getDAO(DAOName.OUTPUT_INTEGRATION_DAO);
            dao.enableOutputService(doEnable);
        } catch (Throwable e) {

            LOG.error(logsM + "Error :" + e);
            throw new ROIException(ROIClientErrorCodes.COULD_NOT_GET_USER);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService#retrieveOutputServerProperties()
     */
    public OutputServerProperties retrieveOutputServerProperties() {

        final String logsM = "retrieveOutputServerProperties()";
        if (DO_DEBUG) {
            LOG.debug(logsM + ">>Start:");
        }

        try {

            OutputIntegrationDAO dao = (OutputIntegrationDAO)
                                                getDAO(DAOName.OUTPUT_INTEGRATION_DAO);
            return dao.retrieveOutputServerProperties();

        } catch (Throwable e) {

            LOG.error(logsM + "Error :" + e);
            throw new ROIException(ROIClientErrorCodes.COULD_NOT_GET_USER);
        }
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     * #saveOutputServerProperties(com.mckesson.eig.roi.admin.model.OutputServerProperties)
     */
    public void saveOutputServerProperties(OutputServerProperties outputServerProperties) {

        final String logsM = "saveOutputServerProperties(outputServerProperties)";
        if (DO_DEBUG) {
            LOG.debug(logsM + ">>Start: " + outputServerProperties);
        }

        try {

            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();
            if (!validator.validateOutputServerProperties(outputServerProperties)) {
                throw validator.getException();
            }

            OutputIntegrationDAO dao = (OutputIntegrationDAO)
                                            getDAO(DAOName.OUTPUT_INTEGRATION_DAO);

            dao.saveOutputServerProperties(outputServerProperties);
        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {

            LOG.error(logsM + "Error :" + e);
            throw new ROIException(e, ROIClientErrorCodes.COULD_NOT_GET_USER);
        }
    }

//    private void setUserFacility(List<UserFacility> userFacilities, User user) {
//
//        Facility[] facilities = new Facility[userFacilities.size()];
//        Facility facility = null;
//
//        for (UserFacility fac : userFacilities) {
//            facility = new Facility();
//            fac.getFacility();
//            facility.set
//        }
//        Security[] securities = new Security[1];
//        Security security = new Security();
//        security.setFacility(ENTERPRISE);
//
//        security.setCodes(getListAsArray(codeList));
//
//    }

    private void setUserSecurity(List<UserSecurity> userSecurities, User user) {

        List<Integer> codeList = new ArrayList<Integer>();
        for (UserSecurity security : userSecurities) {
            if (security.getFacility().trim().equalsIgnoreCase(ENTERPRISE)) {
                codeList.add(security.getSecurityId());
            }
        }
        Security[] securities = new Security[1];

        Security security = new Security();
        security.setFacility(ENTERPRISE);
        security.setCodes(getListAsArray(codeList));

        securities[0] = security;

        user.setSecurities(securities);
    }

    private int[] getListAsArray(List<Integer> codeList) {

        int[] codes = new int[codeList.size()];
        int index = 0;
        for (Integer code : codeList) {
            codes[index++] = code;
        }
        return codes;
    }

    private void setUserDetails(com.mckesson.eig.roi.hpf.model.User src,
        User user) {

        user.setFullName(src.getFullName().trim());
        user.setInstanceId(src.getInstanceId());
        user.setLoginId(src.getLoginId().trim());
        user.setIdle(src.getIdle());
        user.setValidateCode(src.getValidateCode());
    }

    private UserSecurityHibernateDao getUserSecurityHibernateDao() {
        return (UserSecurityHibernateDao) SpringUtilities.getInstance().getBeanFactory()
                    .getBean(UserSecurityHibernateDao.class.getName());
    }

    @Override
    public AttachmentLocation retrieveAttachmentLocation() {
        
        final String logSM = "retrieveAttachmentLocation()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {
            
            AttachmentDAO dao = (AttachmentDAO) getDAO(DAOName.ATTACHMENT_DAO);
            AttachmentLocation location = dao.retrieveAttachmentLocation();
          
            if (location == null) {
                throw new ROIException(ROIClientErrorCodes.DATABASE_ATTACHMENT_CONFIG_MISSING);
            }
            
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

            return location;
        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.ATTACHMENT_OPERATION_FAILED);
        }  
    }

    @Override
    public void updateAttachmentLocation(AttachmentLocation attachmentLoc) {
        
        final String logSM = "updateAttachmentLocation(attachmentLoc)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + attachmentLoc);
        }

        try {

            AttachmentDAO dao = (AttachmentDAO) getDAO(DAOName.ATTACHMENT_DAO);
            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();
            String location = attachmentLoc.getAttachmentLocation();

            if (!validator.validateAttachmentLocation(location)) {
                throw validator.getException();
            }
            
            int modifiedBy = getUser().getInstanceId();

            // Retrieving the previous Attachment location for Audit
            AttachmentLocation loc = dao.retrieveAttachmentLocation();
            String prevLoc = loc.getAttachmentLocation();
            
            dao.updateAttachmentLocation(location, modifiedBy);

            Timestamp date = dao.getDate();
            auditAdmin(toAuditUpdateAttachmentLoc(prevLoc, location), 
                       modifiedBy, date);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.ATTACHMENT_OPERATION_FAILED);
        }       
    }


    /**
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService#retrieveInvoiceDueDays()
     */
    public InvoiceDueDays retrieveInvoiceDueDays() {

        final String logSM = "retrieveInvoiceDueDays()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {

            InvoiceDueDaysDAO dao = (InvoiceDueDaysDAO) getDAO(DAOName.INVOICEDUEDAYS_DAO);
            InvoiceDueDays invoiceDue = dao.retrieveInvoiceDueDays(1);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

            return invoiceDue;

        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.INVOICE_DUE_DAYS_METHOD_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.ROIAdminService
     *      #updateWeight(com.mckesson.eig.roi.admin.model.Weight)
     */
    public InvoiceDueDays updateInvoiceDueDays(InvoiceDueDays invoiceDate) {

        final String logSM = "updateInvoiceDueDays(invoiceDue)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + invoiceDate);
        }

        try {

            InvoiceDueDaysDAO dao = (InvoiceDueDaysDAO) getDAO(DAOName.INVOICEDUEDAYS_DAO);
            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();
            
            if (!validator.validateInvoiceDueDays(invoiceDate, getUser().getLoginId(), dao)) {
                throw validator.getException();
            }

            InvoiceDueDays originalInvoiceDue = dao.retrieveInvoiceDueDays(1);

            setInvoiceDetails(invoiceDate, dao);
            String comment = invoiceDate.toUpdateAudit(originalInvoiceDue);

            InvoiceDueDays updatedInvoiceDue = dao.updateInvoiceDue(invoiceDate);

            auditAdmin(comment, getUser().getInstanceId(), dao.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

            return updatedInvoiceDue;

        } catch (ROIException e) {
            LOG.error(e);
            throw e;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(e, ROIClientErrorCodes.INVOICE_DUE_DAYS_METHOD_OPERATION_FAILED);
        }
    }

    private void setInvoiceDetails(InvoiceDueDays invoiceDue,
                                    InvoiceDueDaysDAO dao) {

        invoiceDue.setModifiedBy(getUser().getInstanceId());
        Timestamp date = dao.getDate();
        invoiceDue.setModifiedDate(date);
    }

    /**
     * This method creates the audit comments to update Attachment
     *
     * @param prevLoc Previous attachment location
     * @param attachmentLoc Full path of the attachment location
     * @return the audit comments for attachment location update
     */
    public String toAuditUpdateAttachmentLoc(String prevLoc, String attachmentLoc) {
        return "Updated the Attachment location from " + prevLoc + " to " + attachmentLoc;
    }  
	
	
	/**
     * This method is to retrieve the MU document types ids
     * 
     * @param
     * @return List<String>
     */
    public List<String> retrieveMUDocTypes() {

        final String logSM = "retrieveMUDocTypes";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {

            DocumentTypeDAO dao = (DocumentTypeDAO) getDAO(DAOName.DOCUMENT_TYPE_DAO);
            List<String> muDocList = dao.retrieveMUDocTypes();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + muDocList.size());
            }

            return muDocList;

        } catch (Throwable e) {
            LOG.error(e);
          throw new ROIException(ROIClientErrorCodes.RETRIEVAL_MUDOCTYPES);
        }
    }  

    /** 
     * This method is used to update the country code details
     * 
     * @param country
     * 
     */
    public void updateCountryCode(Country country) {
        final String logSM = "updateCountryCode";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {
            CountryCodeConfigurationDAO dao = (CountryCodeConfigurationDAO) getDAO(DAOName.ROI_COUNTRYCODECONFIG_DAO);            
            dao.updateCountryCode(country);
            String remark = "Country Code-Country set to " + country.getCountryCode() +" - "+country.getCountryName();
            audit(remark, getUser().getInstanceIdValue(), dao.getDate());
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" );
            }
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(ROIClientErrorCodes.COUNTRY_CODE_CONFIG_UPDATE_OPERATION_FAILED);
        }
    }
    
    /** 
     * This method retrieveAllCountries is used to retrieve the master
     * list of countries.
     * 
     * @return countryList
     */
    public List<Country> retrieveAllCountries() {
        final String logSM = "retrieveAllCountries";
        List<Country> countryList = new ArrayList<Country>();
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {
            CountryCodeConfigurationDAO dao = (CountryCodeConfigurationDAO) getDAO(DAOName.ROI_COUNTRYCODECONFIG_DAO);           
            countryList = dao.retrieveAllCountries();           
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" );
            }
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(ROIClientErrorCodes.COUNTRY_CODE_CONFIG_RETRIEVE_OPERATION_FAILED);
        }
        
        return countryList;
    }
    
    /** 
     * This method is used to update the Unbillable RequestFlag in SysParms_Global table
     * 
     * @param checked
     * 
     */
    public void updateUnbillableRequestFlag(boolean checked) {
        final String logSM = "updateUnbillableRequestFlag";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {
            SysParamDAO dao = (SysParamDAO) getDAO(DAOName.SYSPARAM_DAO);            
            dao.updateUnbillableRequestFlag(checked);
            String remark = "ROI Unbillable Request Flag set to " + checked + " in SysParms_Global table. ";
            audit(remark, getUser().getInstanceIdValue(), dao.getDate());
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" );
            }
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(ROIClientErrorCodes.UNABLE_TO_UPDATE_UNBILLABLE_REQUEST_FLAG);
        }
    }
    
    /** 
     * This method is used to retrieve the Unbillable RequestFlag in SysParms_Global table
     * 
     * 
     */
    public boolean retrieveUnbillableRequestFlag() {
        final String logSM = "retrieveUnbillableRequestFlag";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        try {
            SysParamDAO dao = (SysParamDAO) getDAO(DAOName.SYSPARAM_DAO);            
            boolean checked = dao.retrieveUnbillableRequestFlag();
            String remark = " Retrieved ROI Unbillable Request Flag from SysParms_Global table. ";
            audit(remark, getUser().getInstanceIdValue(), dao.getDate());
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" );
            }
            return checked;
        } catch (Throwable e) {
            LOG.error(e);
            throw new ROIException(ROIClientErrorCodes.UNABLE_TO_RETRIEVE_UNBILLABLE_REQUEST_FLAG);
        }
    }
}
