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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mckesson.eig.roi.admin.dao.BillingTemplateDAO;
import com.mckesson.eig.roi.admin.dao.BillingTierDAO;
import com.mckesson.eig.roi.admin.dao.CountryCodeConfigurationDAO;
import com.mckesson.eig.roi.admin.dao.DeliveryMethodDAO;
import com.mckesson.eig.roi.admin.dao.FeeTypeDAO;
import com.mckesson.eig.roi.admin.dao.MediaTypeDAO;
import com.mckesson.eig.roi.admin.dao.PaymentMethodDAO;
import com.mckesson.eig.roi.admin.dao.ReasonDAO;
import com.mckesson.eig.roi.admin.dao.RequestorTypeDAO;
import com.mckesson.eig.roi.admin.dao.TaxPerFacilityDAO;
import com.mckesson.eig.roi.admin.dao.WeightDAO;
import com.mckesson.eig.roi.admin.model.BillingPaymentInfo;
import com.mckesson.eig.roi.admin.model.BillingTemplate;
import com.mckesson.eig.roi.admin.model.BillingTemplatesList;
import com.mckesson.eig.roi.admin.model.BillingTier;
import com.mckesson.eig.roi.admin.model.BillingTiersList;
import com.mckesson.eig.roi.admin.model.FeeType;
import com.mckesson.eig.roi.admin.model.FeeTypesList;
import com.mckesson.eig.roi.admin.model.MediaType;
import com.mckesson.eig.roi.admin.model.MediaTypesList;
import com.mckesson.eig.roi.admin.model.PageLevelTier;
import com.mckesson.eig.roi.admin.model.PaymentMethod;
import com.mckesson.eig.roi.admin.model.PaymentMethodList;
import com.mckesson.eig.roi.admin.model.RelatedFeeType;
import com.mckesson.eig.roi.admin.model.RequestorType;
import com.mckesson.eig.roi.admin.model.TaxPerFacility;
import com.mckesson.eig.roi.admin.model.TaxPerFacilityList;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.service.BaseROIService;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * This class implements all Billing Admin services
 *
 * @author OFS
 * @date   Sep 15, 2008
 * @since  HPF 13.1 [ROI]; Mar 25, 2008
 */
public class BillingAdminServiceImpl
extends BaseROIService
implements BillingAdminService {

    private static final OCLogger LOG = new OCLogger(BillingAdminServiceImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     * @see com.mckesson.eig.roi.admin.service.BillingAdminService
     *      #createMediaType(com.mckesson.eig.roi.admin.model.MediaType)
     */
    public long createMediaType(MediaType mediaType) {

        final String logSM = "createMediaType(mediaType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + mediaType);
        }

        try {

            MediaTypeDAO dao = (MediaTypeDAO) getDAO(DAOName.MEDIA_TYPE_DAO);
            BillingAdminServiceValidator validator = new BillingAdminServiceValidator();

            if (!validator.validateMediaType(mediaType, true, dao)) {
                throw validator.getException();
            }

            setDefaultMediaTypeDetails(mediaType, dao, true);

            long mediaTypeId = dao.createMediaType(mediaType);

            User user = getUser();
            auditAdmin(mediaType.toCreateAudit(user.getFullName()),
                                               user.getInstanceId(),
                                               dao.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End :" + mediaTypeId);
            }
            return mediaTypeId;
        } catch (ROIException e) {
            LOG.error("ROIException occured in createMediaType",e);
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in createMediaType",e);
            throw new ROIException(e, ROIClientErrorCodes.MEDIA_TYPE_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.BillingAdminService#deleteMediaType(long)
     */
    public void deleteMediaType(long id) {

        final String logSM = "deleteMediaType(mediaTypeId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + id);
        }

        try {

            MediaTypeDAO dao = (MediaTypeDAO) getDAO(DAOName.MEDIA_TYPE_DAO);
            BillingAdminServiceValidator validator = new BillingAdminServiceValidator();

            if (!validator.validateMediaTypeDeletion(id, dao)) {
                throw validator.getException();
            }

            MediaType mt = dao.deleteMediaType(id);

            User user = getUser();
            auditAdmin(mt.toDeleteAudit(user.getFullName()), user.getInstanceId(), dao.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
        } catch (ROIException e) {
            LOG.error("ROIException occured in deleteMediaType",e);
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in deleteMediaType",e);
            throw new ROIException(e, ROIClientErrorCodes.MEDIA_TYPE_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.BillingAdminService#retrieveMediaType(long)
     */
    public MediaType retrieveMediaType(long mediaTypeId) {

        final String logSM = "retrieveMediaType(mediaTypeId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        MediaTypeDAO dao = (MediaTypeDAO) getDAO(DAOName.MEDIA_TYPE_DAO);

        MediaType mediaType = dao.retrieveMediaType(mediaTypeId);
        long count = dao.getAssociatedBillingTierCount(mediaTypeId);
        mediaType.setIsAssociated(count > 0);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + mediaType);
        }
        return mediaType;
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.BillingAdminService#retrieveAllMediaTypes()
     */
    public MediaTypesList retrieveAllMediaTypes(boolean detailedFetch) {

        try {

            MediaTypeDAO dao = (MediaTypeDAO) getDAO(DAOName.MEDIA_TYPE_DAO);

            if (detailedFetch) {
                return dao.retrieveAllMediaTypes();
            }

            return dao.retrieveAllMediaTypeNames();
        } catch (Throwable e) {
            LOG.error("Exception occured in retrieveAllMediaTypes",e);
            throw new ROIException(e, ROIClientErrorCodes.MEDIA_TYPE_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.BillingAdminService
     *      #updateMediaType(com.mckesson.eig.roi.admin.model.MediaType)
     */
    public MediaType updateMediaType(MediaType mt) {

        final String logSM = "updateMediaType(mediaType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + mt);
        }

        try {

            MediaTypeDAO dao = (MediaTypeDAO) getDAO(DAOName.MEDIA_TYPE_DAO);
            BillingAdminServiceValidator validator = new BillingAdminServiceValidator();

            if (!validator.validateMediaType(mt, false, dao)) {
                throw validator.getException();
            }

            setDefaultMediaTypeDetails(mt, dao, false);
            MediaType oldMT = retrieveMediaType(mt.getId());

            User user = getUser();
            String comments = oldMT.toUpdateAudit(user.getFullName(), mt);

            MediaType uMT = dao.updateMediaType(mt, oldMT);

            auditAdmin(comments, user.getInstanceId(), dao.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + uMT);
            }
            return uMT;
        } catch (ROIException e) {
            LOG.error("ROIException occured in updateMediaType",e);
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in updateMediaType",e);
            throw new ROIException(e, ROIClientErrorCodes.MEDIA_TYPE_OPERATION_FAILED);
        }
    }

    /**
     * This method sets the default values while creating/updating a Media Type
     *
     * @param mediaType Media Type details to be set
     * @param dao used to get database date
     * @param isCreation decides to set values for creation/modification
     */
    private void setDefaultMediaTypeDetails(MediaType mediaType,
                                            MediaTypeDAO dao,
                                            boolean isCreation) {

        if (isCreation) {

            mediaType.setCreatedBy(getUser().getInstanceId());
            mediaType.setOrgId(1);
        } else {
            mediaType.setModifiedBy(getUser().getInstanceId());
        }
        mediaType.setModifiedDate(dao.getDate());
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.BillingAdminService
     *      #createFeeType(com.mckesson.eig.roi.admin.model.FeeType)
     */
    public long createFeeType(FeeType feeType) {

        final String logSM = "createFeeType(feeType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + feeType);
        }

        try {

            FeeTypeDAO dao = (FeeTypeDAO) getDAO(DAOName.FEE_TYPE_DAO);
            BillingAdminServiceValidator validator = new BillingAdminServiceValidator();

            if (!validator.validateFeeType(feeType, true, dao)) {
                throw validator.getException();
            }
            setDefaultFeeTypeDetails(feeType, dao, true);
            long feeTypeId = dao.createFeeType(feeType);

            User user = getUser();
            auditAdmin(feeType.toCreateAudit(), user.getInstanceId(), dao.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End :" + feeTypeId);
            }
            return feeTypeId;
        } catch (ROIException e) {
            LOG.error("ROIException occured in createFeeType",e);
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in createFeeType",e);
            throw new ROIException(e, ROIClientErrorCodes.FEE_TYPE_PROCESS_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.BillingAdminService#deleteFeeType
     */
    public void deleteFeeType(long feeTypeId) {

        final String logSM = "deleteFeeType(feeTypeId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + feeTypeId);
        }

        try {

            FeeTypeDAO dao = (FeeTypeDAO) getDAO(DAOName.FEE_TYPE_DAO);
            BillingAdminServiceValidator validator = new BillingAdminServiceValidator();

            if (!validator.validateFeeTypeDeletion(feeTypeId, dao)) {
                throw validator.getException();
            }

            String name = dao.deleteFeeType(feeTypeId);

            FeeType fType = new FeeType();
            fType.setId(feeTypeId);

            User user = getUser();
            auditAdmin(fType.toDeleteAudit(name), user.getInstanceId(), dao.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End :");
            }
        } catch (ROIException e) {
            LOG.error("ROIException occured in deleteFeeType",e);
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in deleteFeeType",e);
            throw new ROIException(e, ROIClientErrorCodes.FEE_TYPE_PROCESS_FAILED);
        }

    }

    /**
     * @see com.mckesson.eig.roi.admin.service.BillingAdminService#retrieveFeeType
     */
    public FeeType retrieveFeeType(long feeTypeId) {

        final String logSM = "retrieveFeeType(feeTypeId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        FeeTypeDAO dao = (FeeTypeDAO) getDAO(DAOName.FEE_TYPE_DAO);
        FeeType feeType = dao.retrieveFeeType(feeTypeId);
        long count = dao.getAssociatedBillingTemplateCount(feeTypeId);
        feeType.setAssociated(count > 0);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" +  feeType);
        }

        return feeType;
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.BillingAdminService#retrieveAllFeeType
     */
    public FeeTypesList retrieveAllFeeTypes(boolean detailedFetch) {

        try {

            FeeTypeDAO dao = (FeeTypeDAO) getDAO(DAOName.FEE_TYPE_DAO);

            if (detailedFetch) {
                return dao.retrieveAllFeeTypes();
            }

            return dao.retrieveAllFeeTypeNames();
        } catch (Throwable e) {
            LOG.error("Exception occured in retrieveAllFeeTypes",e);
            throw new ROIException(e, ROIClientErrorCodes.FEE_TYPE_PROCESS_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.BillingAdminService
     *      #updateFeeType(com.mckesson.eig.roi.admin.model.FeeType)
     */
    public FeeType updateFeeType(FeeType feeType) {

        final String logSM = "updateFeeType(feeType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + feeType);
        }

        try {

            FeeTypeDAO dao = (FeeTypeDAO) getDAO(DAOName.FEE_TYPE_DAO);
            BillingAdminServiceValidator validator = new BillingAdminServiceValidator();

            if (!validator.validateFeeType(feeType, false, dao)) {
                throw validator.getException();
            }

            setDefaultFeeTypeDetails(feeType, dao, false);

            FeeType old = retrieveFeeType(feeType.getId());
            String comments = feeType.toUpdateAudit(old);
            FeeType updatedFeeType = dao.updateFeeType(feeType, old);

            User user = getUser();
            auditAdmin(comments, user.getInstanceId(), dao.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End :" + updatedFeeType);
            }
            return updatedFeeType;
        } catch (ROIException e) {
            LOG.error("ROIException occured in updateFeeType",e);
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in updateFeeType",e);
            throw new ROIException(e, ROIClientErrorCodes.FEE_TYPE_PROCESS_FAILED);
        }

    }

    /**
     * This method sets the default values while creating a new FeeType - This method should set
     * actual values
     *
     * @param feeType
     * @param dao used to get the database date
     * @param isCreation decides to set values for creation/modification
     */
    private void setDefaultFeeTypeDetails(FeeType feeType, FeeTypeDAO dao, boolean isCreation) {

        if (isCreation) {

            feeType.setCreatedBy(getUser().getInstanceId());
            feeType.setOrgId(1);
        } else {
            feeType.setModifiedBy(getUser().getInstanceId());
        }
        feeType.setModifiedDate(dao.getDate());
    }

    /**
    *
    * @see com.mckesson.eig.roi.admin.service.BillingAdminService
    * #createPaymentMethod(com.mckesson.eig.roi.admin.model.PaymentMethod)
    */

   public long createPaymentMethod(PaymentMethod paymentMethod) {

       final String logSM = "createPaymentMethod(paymentMethod)";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start:" + paymentMethod);
       }

       try {

           PaymentMethodDAO dao = (PaymentMethodDAO) getDAO(DAOName.PAYMENT_METHOD_DAO);
           BillingAdminServiceValidator validator = new BillingAdminServiceValidator();

           if (!validator.validatePaymentMethod(paymentMethod, dao, true)) {
               throw validator.getException();
           }

           setPaymentMethodDetails(paymentMethod, dao, true);

           long paymentMethodId = dao.createPaymentMethod(paymentMethod);

           User user = getUser();
           auditAdmin(paymentMethod.toCreateAudit(), user.getInstanceId(), dao.getDate());

           if (DO_DEBUG) {
               LOG.debug(logSM + "<<End: Created PaymentMethodId: " + paymentMethodId);
           }
           return paymentMethodId;
       } catch (ROIException e) {
           LOG.error("ROIException occured in createPaymentMethod",e);
           throw e;
       } catch (Throwable e) {
           LOG.error("Exception occured in createPaymentMethod",e);
           throw new ROIException(e, ROIClientErrorCodes.PAYMENT_METHOD_OPERATION_FAILED);
       }
   }

   /**
    *
    * @see com.mckesson.eig.roi.admin.service.BillingAdminService#retrievePaymentMethod(long)
    */
   public PaymentMethod retrievePaymentMethod(long paymentMethodId) {

       final String logSM = "retrievePaymentMethod(paymentMethodId)";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start: PaymentMethodId : " + paymentMethodId);
       }

       PaymentMethodDAO dao = (PaymentMethodDAO) getDAO(DAOName.PAYMENT_METHOD_DAO);
       PaymentMethod paymentMethod = dao.retrievePaymentMethod(paymentMethodId);

       if (DO_DEBUG) {
           LOG.debug(logSM + "<<End:" + paymentMethod);
       }
       return paymentMethod;
   }

   /**
    *
    * @see com.mckesson.eig.roi.admin.service.BillingAdminService#retrieveAllPaymentMethods()
    */
   public PaymentMethodList retrieveAllPaymentMethods(boolean fetchDetails) {

       final String logSM = "retrieveAllPaymentMethods()";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start:");
       }

       try {

           PaymentMethodDAO dao = (PaymentMethodDAO) getDAO(DAOName.PAYMENT_METHOD_DAO);

           if (fetchDetails) {
               return dao.retrieveAllPaymentMethods();
           }

           return dao.retrieveAllPaymentMethodNames();
       } catch (Throwable e) {
           LOG.error("Exception occured in retrieveAllPaymentMethods",e);
           throw new ROIException(e, ROIClientErrorCodes.PAYMENT_METHOD_OPERATION_FAILED);
       }
   }

   /**
    *
    * @see com.mckesson.eig.roi.admin.service.BillingAdminService#deletePaymentMethod(long)
    */
   public void deletePaymentMethod(long paymentMethodId) {

       final String logSM = "deletePaymentMethod(paymentMethodId)";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start:" + paymentMethodId);
       }

       try {

           PaymentMethodDAO  dao = (PaymentMethodDAO) getDAO(DAOName.PAYMENT_METHOD_DAO);

           PaymentMethod pm = dao.deletePaymentMethod(paymentMethodId);
           User user = getUser();
           auditAdmin(pm.toDeleteAudit(), user.getInstanceId(), dao.getDate());

           if (DO_DEBUG) {
               LOG.debug(logSM + "<<End:");
           }
       } catch (ROIException e) {
           LOG.error("ROIException occured in deletePaymentMethod",e);
           throw e;
       } catch (Throwable e) {
           LOG.error("Exception occured in deletePaymentMethod",e);
           throw new ROIException(e, ROIClientErrorCodes.PAYMENT_METHOD_OPERATION_FAILED);
       }
   }

   /**
    *
    * @see com.mckesson.eig.roi.admin.service.BillingAdminService
    * #updatePaymentMethod(com.mckesson.eig.roi.admin.model.PaymentMethod)
    */
   public PaymentMethod updatePaymentMethod(PaymentMethod paymentMethod) {

       final String logSM = "updatePaymentMethod(paymentMethod)";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start:" + paymentMethod);
       }

       try {

           PaymentMethodDAO  dao = (PaymentMethodDAO) getDAO(DAOName.PAYMENT_METHOD_DAO);
           BillingAdminServiceValidator validator = new BillingAdminServiceValidator();

           if (!validator.validatePaymentMethod(paymentMethod, dao, false)) {
               throw validator.getException();
           }

           setPaymentMethodDetails(paymentMethod, dao, false);

           PaymentMethod originalPM = retrievePaymentMethod(paymentMethod.getId());
           String comments = paymentMethod.toUpdateAudit(originalPM);
           PaymentMethod updatedPM = dao.updatePaymentMethod(paymentMethod, originalPM);

           User user = getUser();
           auditAdmin(comments, user.getInstanceId(), dao.getDate());

           if (DO_DEBUG) {
               LOG.debug(logSM + "<<End: Updated PaymentMethod"  + updatedPM);
           }
           return updatedPM;
       } catch (ROIException e) {
           LOG.error("ROIException occured in updatePaymentMethod",e);
           throw e;
       } catch (Throwable e) {
           LOG.error("Exception occured in updatePaymentMethod",e);
           throw new ROIException(e, ROIClientErrorCodes.PAYMENT_METHOD_OPERATION_FAILED);
       }
   }

   /**
    * This method sets the default values while creating/updating a Payment Method
    *
    * @param paymentMethod Payment Method details to be set
    * @param payment method dao.
    * @param isCreate True for create and false for update
    */
   private void setPaymentMethodDetails(PaymentMethod paymentMethod,
                                        PaymentMethodDAO  dao,
                                        boolean isCreate) {

       if (isCreate) {

           paymentMethod.setCreatedBy(getUser().getInstanceId());
           paymentMethod.setOrgId(1);
       } else {
           paymentMethod.setModifiedBy(getUser().getInstanceId());
       }
       paymentMethod.setModifiedDt(dao.getDate());
       paymentMethod.setActive(true);
   }

   /**
    * @see com.mckesson.eig.roi.admin.service.BillingAdminService
    *      #createBillingTemplate(com.mckesson.eig.roi.admin.model.BillingTemplate)
    */
   public long createBillingTemplate(BillingTemplate billingTemplate) {

       final String logSM = "createBillingTemplate(billingTemplate)";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start:" + billingTemplate);
       }

       try {

           BillingTemplateDAO dao = (BillingTemplateDAO) getDAO(DAOName.BILLING_TEMPLATE_DAO);
           BillingAdminServiceValidator validator = new BillingAdminServiceValidator();

           if (!validator.validateBillingTemplate(billingTemplate, true, dao)) {
               throw validator.getException();
           }

           setDefaultBillingTemplateDetails(billingTemplate, true, dao);

           long billingTemplateId = dao.createBillingTemplate(billingTemplate);

           User user = getUser();
           auditAdmin(billingTemplate.toCreateAudit(), user.getInstanceId(), dao.getDate());

           if (DO_DEBUG) {
               LOG.debug(logSM + "<<End :" + billingTemplateId);
           }
           return billingTemplateId;
       } catch (ROIException e) {
           LOG.error("ROIException occured in createBillingTemplate",e);
           throw e;
       } catch (Throwable e) {
           LOG.error("Exception occured in createBillingTemplate",e);
           throw new ROIException(e, ROIClientErrorCodes.BILLING_TEMPLATE_PROCESS_FAILED);
       }
   }

   /**
    * @see com.mckesson.eig.roi.admin.service.BillingAdminService#retrieveFeeType
    */
   public BillingTemplate retrieveBillingTemplate(long billingTemplateId) {

       final String logSM = "retrieveBillingTemplate(billingTemplateId)";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start:" + billingTemplateId);
       }

       BillingTemplateDAO  dao = (BillingTemplateDAO) getDAO(DAOName.BILLING_TEMPLATE_DAO);
       BillingTemplate billingTemplate = dao.retrieveBillingTemplate(billingTemplateId);
       long count = dao.getAssociatedRequestorTypeCount(billingTemplateId);
       billingTemplate.setAssociated(count > 0);

       if (DO_DEBUG) {
           LOG.debug(logSM + "<<End:" + billingTemplate);
       }
       return billingTemplate;
   }

   /**
    * @see com.mckesson.eig.roi.admin.service.BillingAdminService
    * #retrieveAllBillingTemplates(boolean)
    */
   public BillingTemplatesList retrieveAllBillingTemplates(boolean loadAssociations) {

       final String logSM = "retrieveAllBillingTemplates(loadAssociations)";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start:");
       }

       try {

           BillingTemplateDAO  dao = (BillingTemplateDAO) getDAO(DAOName.BILLING_TEMPLATE_DAO);
           BillingTemplatesList bTList = new BillingTemplatesList();

           if (loadAssociations) {
               bTList = dao.retrieveAllBillingTemplates();
           } else {
               bTList = dao.retrieveAllBillingTemplateName();
           }

           if (DO_DEBUG) {
               LOG.debug(logSM + "<<End: No of Records : " + bTList.getBillingTemplates().size());
           }
           return bTList;
       } catch (Throwable e) {
           LOG.error("Exception occured in retrieveAllBillingTemplates",e);
           throw new ROIException(e, ROIClientErrorCodes.BILLING_TEMPLATE_PROCESS_FAILED);
       }
   }

   /**
    * @see com.mckesson.eig.roi.admin.service.BillingAdminService#deleteBillingTemplate
    */
   public void deleteBillingTemplate(long billingTemplateId) {

       final String logSM = "deleteBillingTemplate(billingTemplateId)";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start:" + billingTemplateId);
       }

       try {

           BillingTemplateDAO  dao = (BillingTemplateDAO) getDAO(DAOName.BILLING_TEMPLATE_DAO);
           BillingAdminServiceValidator validator = new BillingAdminServiceValidator();

           if (!validator.validateBillingTemplateDeletion(billingTemplateId, dao)) {
               throw validator.getException();
           }
           BillingTemplate billingTemplate = dao.deleteBillingTemplate(billingTemplateId);

           User user = getUser();
           auditAdmin(billingTemplate.toDeleteAudit(), user.getInstanceId(), dao.getDate());

           if (DO_DEBUG) {
               LOG.debug(logSM + "<<End : Billing Template Id " + billingTemplateId);
           }
       } catch (ROIException e) {
           LOG.error("ROIException occured in deleteBillingTemplate",e);
           throw e;
       } catch (Throwable e) {
           LOG.error("Exception occured in deleteBillingTemplate",e);
           throw new ROIException(e, ROIClientErrorCodes.BILLING_TEMPLATE_PROCESS_FAILED);
       }
   }

   /**
    * @see com.mckesson.eig.roi.admin.service.BillingAdminService
    *      #updateFeeType(com.mckesson.eig.roi.admin.model.BillingTemplate)
    */
   public BillingTemplate updateBillingTemplate(BillingTemplate billingTemplate) {

       final String logSM = "updateBillingTemplate(billingTemplate)";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start:" + billingTemplate);
       }

       try {

           BillingTemplateDAO  dao = (BillingTemplateDAO) getDAO(DAOName.BILLING_TEMPLATE_DAO);
           BillingAdminServiceValidator validator = new BillingAdminServiceValidator();

           if (!validator.validateBillingTemplate(billingTemplate, false, dao)) {
               throw validator.getException();
           }

           setDefaultBillingTemplateDetails(billingTemplate, false, dao);

           BillingTemplate origBillingTemplate = retrieveBillingTemplate(billingTemplate.getId());
           FeeTypeDAO feeTypeDao = (FeeTypeDAO) getDAO(DAOName.FEE_TYPE_DAO);

           FeeTypesList feeTypes = feeTypeDao.retrieveAllFeeTypes();
           String comments = billingTemplate.toUpdateAudit(origBillingTemplate, feeTypes);

           BillingTemplate updatedBillingTemplate = dao.updateBillingTemplate(billingTemplate,
                                                                          origBillingTemplate);

           User user = getUser();
           auditAdmin(comments, user.getInstanceId(), dao.getDate());

           if (DO_DEBUG) {
               LOG.debug(logSM + "<<End: Updated Billing Template " + updatedBillingTemplate);
           }
           return updatedBillingTemplate;
       } catch (ROIException e) {
           LOG.error("ROIException occured in updateBillingTemplate",e);
           throw e;
       } catch (Throwable e) {
           LOG.error("Exception occured in updateBillingTemplate",e);
           throw new ROIException(e, ROIClientErrorCodes.BILLING_TEMPLATE_PROCESS_FAILED);
       }
   }

   /**
    * This method sets the default values while creating a new BillingTemplate -
    * This method should set actual values
    *
    * @param BillingTemplate
    * @param dao used to get the database date
    * @param isCreation decides to set values for creation/modification
    */
   private void setDefaultBillingTemplateDetails(BillingTemplate billingTemplate,
                                                 boolean isNew,
                                                 BillingTemplateDAO  dao) {

       RelatedFeeType btf;
       if (isNew) {

           billingTemplate.setCreatedBy(getUser().getInstanceId());
           billingTemplate.setOrgId(1);
           billingTemplate.setActive(Boolean.TRUE);

           for (Iterator<RelatedFeeType> iter =
                         billingTemplate.getRelatedFeeTypes().iterator(); iter.hasNext();) {
               btf = iter.next();
               btf.setModifiedDate(dao.getDate());
               btf.setCreatedBy(getUser().getInstanceId());
           }
       } else {
           billingTemplate.setModifiedBy(getUser().getInstanceId());
           for (Iterator<RelatedFeeType> iter =
                         billingTemplate.getRelatedFeeTypes().iterator(); iter.hasNext();) {
               btf = iter.next();
               btf.setModifiedDate(dao.getDate());
               btf.setModifiedBy(getUser().getInstanceId());
           }

       }
       billingTemplate.setModifiedDate(dao.getDate());
   }

   /**
    * @see com.mckesson.eig.roi.admin.service.BillingAdminService
    * #createBillingTier(com.mckesson.eig.roi.admin.model.BillingTier)
    */
   public BillingTier createBillingTier(BillingTier billingTier) {

       final String logSM = "createBillingTier(billingTier)";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start:" + billingTier);
       }

       try {

           BillingTierDAO dao = (BillingTierDAO) getDAO(DAOName.BILLING_TIER_DAO);
           MediaTypeDAO mtdao = (MediaTypeDAO) getDAO(DAOName.MEDIA_TYPE_DAO);
           BillingAdminServiceValidator validator = new BillingAdminServiceValidator();

           if (!validator.validateBillingTier(billingTier, true, dao, mtdao)) {
               throw validator.getException();
           }
           setDefaultBillingTierValues(billingTier, dao, true);
           BillingTier bt = dao.createBillingTier(billingTier);

           User user = getUser();
           auditAdmin(billingTier.toCreateAudit(), user.getInstanceId(), dao.getDate());

           if (DO_DEBUG) {
               LOG.debug(logSM + "<<End:" + bt);
           }
           return bt;
       } catch (ROIException e) {
           LOG.error("ROIException occured in createBillingTier",e);
           throw e;
       } catch (Throwable e) {
           LOG.error("Exception occured in createBillingTier",e);
           throw new ROIException(e, ROIClientErrorCodes.BILLING_TIER_OPERATION_FAILED);
       }
   }

   /**
    * @see com.mckesson.eig.roi.admin.service.BillingAdminService
    * #retrieveBillingTier(com.mckesson.eig.roi.admin.model.BillingTier)
    */
   public BillingTier retrieveBillingTier(long billingTierId) {

       final String logSM = "retrieveBillingTier(billingTierId)";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start: Billing Tier Id = " + billingTierId);
       }

       BillingTierDAO dao = (BillingTierDAO) getDAO(DAOName.BILLING_TIER_DAO);

       BillingTier bt = dao.retrieveBillingTier(billingTierId);
       long count = dao.getAssociatedRequestorTypeCount(billingTierId);
       bt.setAssociated(count > 0);

       if (DO_DEBUG) {
           LOG.debug(logSM + "<<End:" + bt);
       }
       return bt;
   }

   /**
    *
    * @see com.mckesson.eig.roi.admin.service.BillingAdminService#retrieveAllBillingTiers(boolean)
    */
   public BillingTiersList retrieveAllBillingTiers(boolean loadAssociation) {

       try {

           final String logSM = "retrieveAllBillingTiers(loadAssociation)";
           if (DO_DEBUG) {
               LOG.debug(logSM + ">>Start: Load BillingTier Assocation = " + loadAssociation);
           }
           BillingTierDAO dao = (BillingTierDAO) getDAO(DAOName.BILLING_TIER_DAO);
           BillingTiersList results = new BillingTiersList();
           if (loadAssociation) {
               results = dao.retrieveAllBillingTiers();
           } else {
               results = dao.retrieveBillingTierAndMediaTypeName();
           }

           if (DO_DEBUG) {
               LOG.debug(logSM + "<<End:");
           }
           return results;
       } catch (Throwable e) {
           LOG.error("Exception occured in retrieveAllBillingTiers",e);
           throw new ROIException(e, ROIClientErrorCodes.BILLING_TIER_OPERATION_FAILED);
       }
   }

   /**
    * @see com.mckesson.eig.roi.admin.service.BillingAdminService
    * #updateBillingTier(com.mckesson.eig.roi.admin.model.BillingTier)
    */
   public BillingTier updateBillingTier(BillingTier bt) {

     final String logSM = "updateBillingTier(billingTier)";
     LOG.debug(logSM + ">>Start:" + bt);

     try {

         BillingTierDAO dao = (BillingTierDAO) getDAO(DAOName.BILLING_TIER_DAO);
         MediaTypeDAO mtdao = (MediaTypeDAO) getDAO(DAOName.MEDIA_TYPE_DAO);

         BillingAdminServiceValidator validator = new BillingAdminServiceValidator();
         if (!validator.validateBillingTier(bt, false, dao, mtdao)) {
             throw validator.getException();
         }
         setDefaultBillingTierValues(bt, dao, false);

         BillingTier old = retrieveBillingTier(bt.getId());
         for (Iterator<PageLevelTier> it = bt.getPageLevelTier().iterator(); it.hasNext();) {

             PageLevelTier pglt = it.next();
             pglt.setCreatedBy(old.getCreatedBy());
             if (pglt.getId() == 0) {

                 for (Iterator<PageLevelTier> i = old.getPageLevelTier().iterator(); i.hasNext();) {

                     PageLevelTier pg = i.next();
                     if ((pg.getPage() == pglt.getPage()) && (pg.getPageCharge() == pglt.getPageCharge()) && (old.getId() == bt.getId())) {
                         pglt.setId(pg.getId());
                         pglt.setRecordVersion(pg.getRecordVersion());
                     }
                 }
             }
         }

         bt.copyFrom(old);
         String comments = bt.toUpdateAudit(old);
         BillingTier updatedBTier = dao.updateBillingTier(bt);

         User user = getUser();
         auditAdmin(comments, user.getInstanceId(), dao.getDate());

         LOG.debug(logSM + "<<End:" + updatedBTier);

         return updatedBTier;
     } catch (ROIException e) {
         LOG.error("ROIException occured in updateBillingTier",e);
         throw e;
     } catch (Throwable e) {
         LOG.error("Exception occured in updateBillingTier",e);
         throw new ROIException(e, ROIClientErrorCodes.BILLING_TIER_OPERATION_FAILED);
     }
   }

   /**
    * @see com.mckesson.eig.roi.admin.service.BillingAdminService
    * #deleteBillingTier(com.mckesson.eig.roi.admin.model.BillingTier)
    */
   public void deleteBillingTier(long billingTierId) {

       final String logSM = "deleteBillingTier(billingTierId)";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start:" + billingTierId);
       }

       try {

           BillingTierDAO dao = (BillingTierDAO) getDAO(DAOName.BILLING_TIER_DAO);

           BillingAdminServiceValidator validator = new BillingAdminServiceValidator();
           if (!validator.validateBillingTierDeletion(billingTierId, dao)) {
               throw validator.getException();
           }
           BillingTier bt = dao.deleteBillingTier(billingTierId);

           User user = getUser();
           auditAdmin(bt.toDeleteAudit(bt.getName()), user.getInstanceId(), dao.getDate());

           if (DO_DEBUG) {
               LOG.debug(logSM + "<<End:");
           }
       } catch (ROIException e) {
           LOG.error("ROIException occured in deleteBillingTier",e);
           throw e;
       } catch (Throwable e) {
           LOG.error("Exception occured in deleteBillingTier",e);
           throw new ROIException(e, ROIClientErrorCodes.BILLING_TIER_OPERATION_FAILED);
       }
   }

   /**
    * This method will set the default values for Billing Tier
    *
    * @param billingTier input Billing Tier object
    * @param dao Billing Tier DAO object
    * @param isNew true for creation, false for updating
    */
   private void setDefaultBillingTierValues(BillingTier billingTier,
                                            BillingTierDAO  dao,
                                            boolean isNew) {

       Timestamp modifiedDt = dao.getDate();
       Integer userId = getUser().getInstanceId();

       if (isNew) {

           billingTier.setCreatedBy(userId);
           billingTier.setOrgId(1);
           billingTier.setActive(true);

       } else {
           billingTier.setModifiedBy(userId);
       }

       billingTier.setModifiedDate(modifiedDt);
       for (PageLevelTier pglt : billingTier.getPageLevelTier()) {

           if (isNew) {
               pglt.setCreatedBy(userId);
           } else {
               pglt.setModifiedBy(userId);
           }
           pglt.setModifiedDate(modifiedDt);
       }
   }

   /**
    *
    * @see com.mckesson.eig.roi.admin.service.BillingAdminService
    * #retrieveBillingTiersByMediaTypeName(java.lang.String)
    */
   public BillingTiersList retrieveBillingTiersByMediaTypeName(String mediaTypeName) {

       final String logSM = "retrieveBillingTiersByMediaTypeName(mediaTypeName)";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start:" + mediaTypeName);
       }

       try {
           BillingTierDAO dao = (BillingTierDAO) getDAO(DAOName.BILLING_TIER_DAO);

           BillingTiersList btList = dao.retrieveBillingTiersByMediaTypeName(mediaTypeName);

           if (DO_DEBUG) {
               LOG.debug(logSM + "<<End: No of records" + btList.getBillingTiers().size());
           }
           return btList;
       } catch (Throwable e) {
           LOG.error("Exception occured in retrieveBillingTiersByMediaTypeName",e);
           throw new ROIException(e, ROIClientErrorCodes.BILLING_TIER_OPERATION_FAILED);
       }
   }

    @Override
    public TaxPerFacility retrieveTaxPerFacility(long taxPerFacilityId) {

        final String logSM = "retrieveTaxPerFacility(taxPerFacilityId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        TaxPerFacilityDAO dao = (TaxPerFacilityDAO) getDAO(DAOName.TAXPERFACILITY_DAO);

        TaxPerFacility salesTaxFacility = dao.retrieveTaxPerFacility(taxPerFacilityId);
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + salesTaxFacility);
        }
        return salesTaxFacility;
    }

    @Override
    public TaxPerFacilityList retrieveAllTaxPerFacilities() {

        final String logSM = "retrieveAllTaxPerFacilities()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: retrieving SalesTaxFacilities");
        }

        try {

            TaxPerFacilityDAO dao = (TaxPerFacilityDAO) getDAO(DAOName.TAXPERFACILITY_DAO);
            return dao.retrieveAllTaxPerFacilities();

        } catch (Throwable e) {
            LOG.error("Exception occured in retrieveAllTaxPerFacilities",e);
            throw new ROIException(e, ROIClientErrorCodes.SALESTAX_OPERATION_FAILED);
        }
    }


    @Override
    public TaxPerFacilityList retrieveAllTaxPerFacilitiesByUser(String userId) {

        final String logSM = "retrieveAllTaxPerFacilitiesByUser()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: retrieving SalesTaxFacilities");
        }

        // Retrieve all Facilities for specified user
        List<String> userFacilities;

        try {

            TaxPerFacilityDAO dao = (TaxPerFacilityDAO) getDAO(DAOName.TAXPERFACILITY_DAO);
            userFacilities = dao.retrieveAllFacilitiesByUser(userId);

            if (null == userFacilities || userFacilities.size() <= 0) {
                return new TaxPerFacilityList();
            }


            // Retrieve all tax per facility objects configured in Admin
            List<TaxPerFacility> taxPerfacilities = retrieveAllTaxPerFacilities()
                                                                        .getSalesTaxFacilityList();

            List<TaxPerFacility> taxPerFacilityForUser = new ArrayList<TaxPerFacility>(
                                                                            userFacilities.size());

            // If Tax Per Facility is not configured, Then all the
            // user facilities should be created with zero percentage tax.
            if (taxPerfacilities == null || taxPerfacilities.size() <= 0) {
                return new TaxPerFacilityList();
            }

            // If Tax Per facility is configured then add appropriate percentage
            for (TaxPerFacility taxPerFac : taxPerfacilities) {

                int index = userFacilities.indexOf(taxPerFac.getCode().trim());
                if (index == -1) {
                    continue;
                } else {
                    taxPerFacilityForUser.add(taxPerFac);
                }
            }

            Collections.sort(taxPerFacilityForUser);
            return new TaxPerFacilityList(taxPerFacilityForUser);

        } catch (ROIException roie) {
            LOG.error("ROIException occured in retrieveAllTaxPerFacilitiesByUser",roie);
            throw roie;
        } catch (Throwable e) {
            LOG.error("Exception occured in retrieveAllTaxPerFacilitiesByUser",e);
            throw new ROIException(e, ROIClientErrorCodes.SALESTAX_OPERATION_FAILED);
        }
    }

    @Override
    public long createTaxPerFacility(TaxPerFacility taxPerFacility) {

        final String logSM = "createTaxPerFacility(taxPerFacility)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + taxPerFacility);
        }

        try {

            TaxPerFacilityDAO dao = (TaxPerFacilityDAO) getDAO(DAOName.TAXPERFACILITY_DAO);
            BillingAdminServiceValidator validator = new BillingAdminServiceValidator();

            if (!validator.validateTaxPerFacility(taxPerFacility, true, dao,
                                                  getUser().getLoginId())) {
                throw validator.getException();
            }

            TaxPerFacility defaultTaxFac = null;
            if (ROIConstants.Y == taxPerFacility.getDefault()) {

                defaultTaxFac = dao.getDefaultTaxPerFacility();
                if ((defaultTaxFac != null) && (taxPerFacility.getId() != defaultTaxFac.getId())) {
                    dao.clearDefaultTaxPerFacility(defaultTaxFac);
                }
            }

            setDefaultTaxPerFacilityDetails(taxPerFacility, dao, true);

            long mediaTypeId = dao.createTaxPerFacility(taxPerFacility);

            auditAdmin(taxPerFacility.toCreateAudit(),
                                               getUser().getInstanceId(),
                                               dao.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End :" + mediaTypeId);
            }
            return mediaTypeId;
        } catch (ROIException e) {
            LOG.error("ROIException occured in createTaxPerFacility",e);
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in createTaxPerFacility",e);
            throw new ROIException(e, ROIClientErrorCodes.SALESTAX_OPERATION_FAILED);
        }
    }

    @Override
    public TaxPerFacility updateTaxPerFacility(TaxPerFacility taxPerFacility) {

        final String logSM = "updateTaxPerFacility(taxPerFacility)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + taxPerFacility);
        }

        try {

            TaxPerFacilityDAO dao = (TaxPerFacilityDAO) getDAO(DAOName.TAXPERFACILITY_DAO);
            BillingAdminServiceValidator validator = new BillingAdminServiceValidator();

            if (!validator.validateTaxPerFacility(taxPerFacility, false, dao,
                                                  getUser().getLoginId())) {
                throw validator.getException();
            }

            TaxPerFacility defaultTaxFac = null;
            if (ROIConstants.Y == taxPerFacility.getDefault()) {

                defaultTaxFac = dao.getDefaultTaxPerFacility();
                if ((defaultTaxFac != null) && (taxPerFacility.getId() != defaultTaxFac.getId())) {
                    dao.clearDefaultTaxPerFacility(defaultTaxFac);
                }
            }
            setDefaultTaxPerFacilityDetails(taxPerFacility, dao, false);
            TaxPerFacility oldSTF = retrieveTaxPerFacility(taxPerFacility.getId());

            String comments = taxPerFacility.toUpdateAudit(oldSTF);

            TaxPerFacility updatedSTF = dao.updateTaxPerFacility(taxPerFacility, oldSTF);

            auditAdmin(comments, getUser().getInstanceId(), dao.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + updatedSTF);
            }
            return updatedSTF;
        } catch (ROIException e) {
            LOG.error("ROIException occured in updateTaxPerFacility",e);
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in updateTaxPerFacility",e);
            throw new ROIException(e, ROIClientErrorCodes.SALESTAX_OPERATION_FAILED);
        }
    }

    @Override
    public void deleteTaxPerFacility(long taxPerFacilityId) {

        final String logSM = "deleteTaxPerFacility(taxPerFacilityId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + taxPerFacilityId);
        }

        try {

            TaxPerFacilityDAO dao = (TaxPerFacilityDAO) getDAO(DAOName.TAXPERFACILITY_DAO);
            ROIAdminServiceValidator validator = new ROIAdminServiceValidator();

            if (!validator.validateSecurityRights(getUser().getLoginId(), dao)) {
                throw validator.getException();
            }

            TaxPerFacility salesTax = dao.deleteTaxPerFacility(taxPerFacilityId);

            auditAdmin(salesTax.toDeleteAudit(), getUser().getInstanceId(), dao.getDate());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
        } catch (ROIException e) {
            LOG.error("ROIException occured in deleteTaxPerFacility",e);
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in deleteTaxPerFacility",e);
            throw new ROIException(e, ROIClientErrorCodes.SALESTAX_OPERATION_FAILED);
        }
    }


    /*
     * This method sets the default values while creating/updating a salesTaxFacility
     *
     * @param salesTaxFacility SalesTaxFacility details to be set
     * @param dao used to get database date
     * @param isCreation decides to set values for creation/modification
     */
    private void setDefaultTaxPerFacilityDetails(TaxPerFacility salesTaxFacility,
                                        TaxPerFacilityDAO dao,
                                        boolean isCreation) {

        if (isCreation) {
            salesTaxFacility.setCreatedBy(getUser().getInstanceId());
        } else {
            salesTaxFacility.setModifiedBy(getUser().getInstanceId());
        }
        salesTaxFacility.setModifiedDate(dao.getDate());
    }

    /**
     * @see com.mckesson.eig.roi.admin.service.BillingAdminService
     * #retrieveBillingAndPaymentInfo(long)
     */
    public BillingPaymentInfo retrieveBillingAndPaymentInfo(long requestorTypeId) {

        final String logSM = "retrieveBillingAndPaymentInfo(requestorTypeId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestorTypeId" + requestorTypeId);
        }

        try {

            FeeTypeDAO feeTypeDao = (FeeTypeDAO) getDAO(DAOName.FEE_TYPE_DAO);
            WeightDAO weightDao = (WeightDAO) getDAO(DAOName.WEIGHT_DAO);
            PaymentMethodDAO payMethodDao = (PaymentMethodDAO) getDAO(DAOName.PAYMENT_METHOD_DAO);
            ReasonDAO reasonDAO = (ReasonDAO) getDAO(DAOName.REASON_DAO);
            RequestorTypeDAO requestorTypeDAO =
                                            (RequestorTypeDAO) getDAO(DAOName.REQUESTOR_TYPE_DAO);
            DeliveryMethodDAO deliveryMethodDao =
                                         (DeliveryMethodDAO) getDAO(DAOName.DELIVERY_METHOD_DAO);
            CountryCodeConfigurationDAO countryDao =
                           (CountryCodeConfigurationDAO) getDAO(DAOName.ROI_COUNTRYCODECONFIG_DAO);


            BillingPaymentInfo info = new BillingPaymentInfo();

            // retrieves all details of the fee types and billing templates
            FeeTypesList feeTypes = feeTypeDao.retrieveAllFeeTypesByRequestorId(requestorTypeId);
            List<BillingTemplate> templatesList =
                    retrieveBillingTemplateWithRelatedFeeTypes(requestorTypeId);

            info.setFeeTypes(feeTypes);
            info.setBillingTemplates(new BillingTemplatesList(templatesList));

            // retrieves the method names of the delivery and payment method
            info.setDeliveryMethods(deliveryMethodDao.retrieveAllDeliveryMethodNames());
            info.setPaymentMethods(payMethodDao.retrieveAllPaymentMethodNames());

            // requests the requestor type of the given requestorTypeId
            RequestorType requestorType = requestorTypeDAO.retrieveRequestorType(requestorTypeId);
            long count = requestorTypeDAO.getAssociatedRequestorCount(requestorTypeId);
            requestorType.setIsAssociated(count > 0);
            info.setRequestorType(requestorType);

            info.setReasons(reasonDAO.retrieveAllReasonsByType(ROIConstants.ADJUSTMENT_TYPE));
            info.setCountries(countryDao.retrieveAllCountries());
            info.setWeight(weightDao.retrieveWeight());

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return info;

        } catch(Exception e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.BILLING_CONFIGURATIONS_RETRIEVE_OPERATION_FAILED);
        }
    }

    /**
     * this method retrieves the list of all Billing templates with their related feetypes
     *
     * @param requestorTypeId
     * @return list of all billing templste with the related feeTypes
     */
    private List<BillingTemplate> retrieveBillingTemplateWithRelatedFeeTypes(long requestorTypeId) {

        FeeTypeDAO feeTypeDao = (FeeTypeDAO) getDAO(DAOName.FEE_TYPE_DAO);
        BillingTemplateDAO  billingTemplateDao =
                (BillingTemplateDAO) getDAO(DAOName.BILLING_TEMPLATE_DAO);

        // retrieves the list of all related feetypes
        List<RelatedFeeType> relatedFeeTypes =
                    feeTypeDao.retrieveAllRelatedFeeByRequestorType(requestorTypeId);

        BillingTemplatesList billingTemplates =
                billingTemplateDao.retrieveAllBillingTemplatesByRequestorType(requestorTypeId);

        // maps the list of all billing template into the hash map
        Map<Long, BillingTemplate> templateMap = new HashMap<Long, BillingTemplate>();
        for (BillingTemplate template : billingTemplates.getBillingTemplates()) {
            templateMap.put(template.getId(), template);
        }

        // loads the associated feetypes to the corresponding billing template
        for (RelatedFeeType feeType : relatedFeeTypes) {

            long billingTemplateId = feeType.getBillingTemplateId();
            if (templateMap.containsKey(billingTemplateId)) {

                BillingTemplate billingTemplate = templateMap.get(billingTemplateId);
                billingTemplate.addRelatedFeeTypes(feeType);
            }
        }

        Collection<BillingTemplate> values = templateMap.values();
        List<BillingTemplate> templatesList = new ArrayList<BillingTemplate>();
        templatesList.addAll(values);

        return templatesList;
    }

}
