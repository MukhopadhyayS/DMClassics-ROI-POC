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

package com.mckesson.eig.roi.request.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Hibernate;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.request.model.RequestCoreCharges;
import com.mckesson.eig.roi.request.model.RequestCoreChargesBilling;
import com.mckesson.eig.roi.request.model.RequestCoreChargesDocument;
import com.mckesson.eig.roi.request.model.RequestCoreChargesFee;
import com.mckesson.eig.roi.request.model.RequestCoreChargesShipping;

/**
 * @author Keane
 * @date July 24, 2012
 */
public class RequestCoreChargesDAOImpl
extends ROIDAOImpl
implements RequestCoreChargesDAO {

    private static final OCLogger LOG = new OCLogger(RequestCoreChargesDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     * This method retrieves the RequestCoreCharges values
     *
     * @param requestId
     * @return RequestCoreCharges
     */

    public RequestCoreCharges retrieveRequestCoreCharges(long requestCoreId) {

        final String logSM = "retrieveRequestCoreCharges(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreId);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreCharges")
                                        .getQueryString();

            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestCoreSeq", requestCoreId, LongType.INSTANCE);
            query.addScalar("id", LongType.INSTANCE);
            query.addScalar("requestCoreSeq", LongType.INSTANCE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);
            query.addScalar("previouslyReleasedCost", DoubleType.INSTANCE);
            query.addScalar("releaseDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("releaseCost", DoubleType.INSTANCE);
            query.addScalar("totalRequestCost", DoubleType.INSTANCE);
            query.addScalar("totalPages", IntegerType.INSTANCE);
            query.addScalar("balanceDue", DoubleType.INSTANCE);
            query.addScalar("totalPagesReleased", IntegerType.INSTANCE);
            query.addScalar("salesTaxAmount", DoubleType.INSTANCE);
            query.addScalar("salesTaxPercentage", DoubleType.INSTANCE);
            query.addScalar("billingType", StringType.INSTANCE);
            query.addScalar("paymentAmount", DoubleType.INSTANCE);
            query.addScalar("creditAdjustmentAmount", DoubleType.INSTANCE);
            query.addScalar("debitAdjustmentAmount", DoubleType.INSTANCE);
            query.addScalar("billingLocCode", StringType.INSTANCE);
            query.addScalar("billingLocName", StringType.INSTANCE);
            query.addScalar("applySalesTax", BooleanType.INSTANCE);
            query.addScalar("hasInvoices", BooleanType.INSTANCE);
            query.addScalar("unbillable", BooleanType.INSTANCE);
            query.addScalar("displayBillingPaymentInfo", BooleanType.INSTANCE);
            query.addScalar("hasUnReleasedInvoices", BooleanType.INSTANCE);
            query.addScalar("invoicesBalance", DoubleType.INSTANCE);
            query.addScalar("invoicesSalesTaxAmount", DoubleType.INSTANCE);
            query.addScalar("totalUnappliedPaymentAmount", DoubleType.INSTANCE);
            query.addScalar("totalUnappliedAdjustmentAmount", DoubleType.INSTANCE);
            query.addScalar("invoiceBaseCharge", DoubleType.INSTANCE);
            
            query.setResultTransformer(Transformers.aliasToBean(RequestCoreCharges.class));

            RequestCoreCharges requestCoreCharges = (RequestCoreCharges) query.uniqueResult();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End" + requestCoreCharges);
            }
            return requestCoreCharges;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                   e.getMessage());
        }
    }
    /**
     * This method retrieves the RequestCoreCharges values for Shipping
     *
     * @param requestCoreChargesSeq
     * @return RequestCoreChargesShipping
     */
    public RequestCoreChargesShipping retrieveRequestCoreChargesShipping(
                                                            Long requestCoreChargesSeq) {
        final String logSM = "retrieveRequestCoreChargesShipping(requestCoreChargesSeq)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreChargesSeq);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreChargesShipping")
                                        .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestCoreChargesSeq", requestCoreChargesSeq, LongType.INSTANCE);
            query.addScalar("shippingCharge", DoubleType.INSTANCE);
            query.addScalar("postalCode", StringType.INSTANCE);
            query.addScalar("addressType", StringType.INSTANCE);
            query.addScalar("state", StringType.INSTANCE);
            query.addScalar("shippingUrl", StringType.INSTANCE);
            query.addScalar("address1", StringType.INSTANCE);
            query.addScalar("address2", StringType.INSTANCE);
            query.addScalar("shippingWeight", DoubleType.INSTANCE);
            query.addScalar("trackingNumber", StringType.INSTANCE);
            query.addScalar("address3", StringType.INSTANCE);
            query.addScalar("city", StringType.INSTANCE);
            query.addScalar("countryCode", StringType.INSTANCE);
            query.addScalar("countryName", StringType.INSTANCE);
            query.addScalar("willReleaseShipped", BooleanType.INSTANCE);
            query.addScalar("shippingMethod", StringType.INSTANCE);
            query.addScalar("outputMethod", StringType.INSTANCE);
            query.addScalar("shippingMethodId", LongType.INSTANCE);
            query.addScalar("nonPrintableAttachmentQueue", StringType.INSTANCE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);
            query.setResultTransformer(Transformers.aliasToBean(RequestCoreChargesShipping.class));

            RequestCoreChargesShipping requestCoreChargesShipping =
                                                (RequestCoreChargesShipping) query.uniqueResult();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + requestCoreChargesShipping);
            }
            return requestCoreChargesShipping;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                   e.getMessage());
        }

    }
    /**
     * This method retrieves the RequestCoreCharges values for Fee
     *
     * @param requestCoreChargesSeq
     * @return RequestCoreChargesFee
     */
    @SuppressWarnings("unchecked")
    private List<RequestCoreChargesFee>  retrieveRequestCoreChargesFee(Long requestCoreChargesSeq) {

        final String logSM = "retrieveRequestCoreChargesFee(requestCoreChargesSeq)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreChargesSeq);
        }
        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreChargesFee")
                                        .getQueryString();

            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestCoreChargesSeq", requestCoreChargesSeq, LongType.INSTANCE);
            query.addScalar("amount", DoubleType.INSTANCE);
            query.addScalar("isCustomFee", BooleanType.INSTANCE);
            query.addScalar("feeType", StringType.INSTANCE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);
            query.addScalar("hasSalesTax", BooleanType.INSTANCE);
            query.addScalar("SalesTaxAmount", DoubleType.INSTANCE);

            query.setResultTransformer(Transformers.aliasToBean(RequestCoreChargesFee.class));

            List<RequestCoreChargesFee> rcChargeFeeList = query.list();
//            requestCoreChargesFeeSet.addAll(rcChargeFeeList);
//            requestCoreChargesBilling.setRequestCoreChargesFee(requestCoreChargesFeeSet);
//            requestCoreChargesBillingInfo.setRequestCoreChargesBilling(requestCoreChargesBilling);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + requestCoreChargesSeq);
            }

            return rcChargeFeeList;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                   e.getMessage());
        }
    }
    /**
     * This method retrieves the RequestCoreCharges values for Document
     *
     * @param requestCoreChargesSeq
     * @return RequestCoreChargesDocument
     */
    @SuppressWarnings("unchecked")
    private List<RequestCoreChargesDocument> retrieveRequestCoreChargesDocument(
                                                                        Long requestChargesSeq) {

        final String logSM = "retrieveRequestCoreChargesDocument(requestSeq)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestChargesSeq);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreChargesDocument")
                                        .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestCoreChargesSeq", requestChargesSeq, LongType.INSTANCE);
            query.addScalar("amount", DoubleType.INSTANCE);
            query.addScalar("copies", IntegerType.INSTANCE);
            query.addScalar("billingTierName", StringType.INSTANCE);
            query.addScalar("totalPages", IntegerType.INSTANCE);
            query.addScalar("pages", IntegerType.INSTANCE);
            query.addScalar("billingtierId", StringType.INSTANCE);
            query.addScalar("releaseCount", IntegerType.INSTANCE);
            query.addScalar("isElectronic", BooleanType.INSTANCE);
            query.addScalar("removeBaseCharge", BooleanType.INSTANCE);
            query.addScalar("hasSalesTax", BooleanType.INSTANCE);
            query.addScalar("SalesTaxAmount", DoubleType.INSTANCE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);

            query.setResultTransformer(Transformers.aliasToBean(RequestCoreChargesDocument.class));
            List<RequestCoreChargesDocument> rcChargesDocument = query.list();

//           requestCoreChargesDocumentSet.addAll(rcChargesDocument);
//           requestCoreChargesBilling.setRequestCoreChargesDocument(requestCoreChargesDocumentSet);
//           requestCoreChargesBillingInfo.setRequestCoreChargesBilling(requestCoreChargesBilling);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:No. of Records:" + rcChargesDocument.size());
            }

            return rcChargesDocument;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                                   ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                                   ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                   e.getMessage());
        }
    }

    /**
     * This method updates the RequestBillingPayment values
     *
     * @param requestCoreChargesSeq
     * @return RequestCoreChargesBillingInfo
     */
    @Override
    public RequestCoreCharges retrieveRequestCoreBillingPaymentInfo(long requestId) {

        final String logSM = "retrieveRequestBillingPaymentInfo(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }

        try {

            RequestCoreCharges requestCoreCharges = retrieveRequestCoreCharges(requestId);

            if (requestCoreCharges != null) {

                // retrieves the requestCore Document Charges Info
                List<RequestCoreChargesDocument> documentCharges =
                        retrieveRequestCoreChargesDocument(requestCoreCharges.getId());

                // retrieves the requestCore fee Charges Info
                List<RequestCoreChargesFee> feeCharges =
                        retrieveRequestCoreChargesFee(requestCoreCharges.getId());

                // retrieves the shipping charges
                RequestCoreChargesShipping requestCoreChargesShipping =
                        retrieveRequestCoreChargesShipping(requestCoreCharges.getId());

                RequestCoreChargesBilling requestBillingCharges =
                                                            new RequestCoreChargesBilling();

                requestBillingCharges.setRequestCoreChargesFee(
                                            new LinkedHashSet<RequestCoreChargesFee>(feeCharges));
                requestBillingCharges.setRequestCoreChargesDocument(
                                          new HashSet<RequestCoreChargesDocument>(documentCharges));
                requestCoreCharges.setRequestCoreChargesBilling(requestBillingCharges);
                requestCoreCharges.setRequestCoreChargesShipping(requestCoreChargesShipping);


//                RequestCoreChargesBillingInfo requestCoreChargesBillingInfo =
//                        new RequestCoreChargesBillingInfo();
//                BeanUtilities.copyProperties(requestCoreCharges, requestCoreChargesBillingInfo);
//                requestCoreChargesBillingInfo.setRequestCoreChargesBilling(
//                                                                requestCoreChargesBilling);
//                requestCoreChargesBillingInfo.setRequestCoreChargesShipping(
//                                                                requestCoreChargesShipping);

             }
             if (DO_DEBUG) {
                 LOG.debug(logSM + ">>End:");
             }
             return requestCoreCharges;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                   e.getMessage());
        }
    }


    /**
     * This method will delete the RequestCoreCharges for the particular request.
     * @param requestId
     */
    public void deleteRequestCoreCharges(long  requestId) {
        final String logSM = "deleteRequestCoreCharges(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }
        try{
            Session session = getSession();
            Query query = session.getNamedQuery("deleteRequestCoreCharges");

            query.setParameter("requestCoreSeq", requestId, LongType.INSTANCE);

            query.executeUpdate();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * This method will delete the RequestCoreChargesDocument for the particular request.
     * @param requestCoreChargesId
     */
    public void deleteRequestCoreChargesDocument(long  requestCoreChargesId){
        final String logSM = "deleteRequestCoreChargesDocument(requestCoreChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreChargesId);
        }
        try{
            Session session = getSession();
            Query query = session.getNamedQuery("deleteRequestCoreChargesDocument");

            query.setParameter("requestCoreChargesSeq", requestCoreChargesId, LongType.INSTANCE);

            query.executeUpdate();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * This method will delete the RequestCoreChargesFee for the particular request.
     * @param requestCoreChargesId
     */
    public void deleteRequestCoreChargesFee(long  requestCoreChargesId){
        final String logSM = "deleteRequestCoreChargesFee(requestCoreChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreChargesId);
        }
        try{
            Session session = getSession();
            Query query = session.getNamedQuery("deleteRequestCoreChargesFee");

            query.setParameter("requestCoreChargesSeq", requestCoreChargesId, LongType.INSTANCE);

            query.executeUpdate();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     *This method will delete the RequestCoreChargesShipping for the particular request.
     * @param requestCoreChargesId
     */
    public void deleteRequestCoreChargesShipping(long  requestCoreChargesId){
        final String logSM = "deleteRequestCoreChargesShipping(requestCoreChargesId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreChargesId);
        }
        try{
            Session session = getSession();
            Query query = session.getNamedQuery("deleteRequestCoreChargesShipping");

            query.setParameter("requestCoreChargesSeq", requestCoreChargesId, LongType.INSTANCE);

            query.executeUpdate();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * This method creates the RequestCoreCharges values
     *
     * @param requestCoreCharges
     * @return RequestCoreChargesId
     */
    @SuppressWarnings("unchecked")
    public long saveRequestCoreCharges(RequestCoreCharges requestCoreCharges) {
        final String logSM = "saveRequestCoreCharges(requestCoreCharges)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreCharges);
        }
        try {
            Session session = getSession();
            Query query = session.getNamedQuery("createRequestCoreCharges");

            query.setParameter("requestCoreSeq", requestCoreCharges.getRequestCoreSeq(),
                                                 LongType.INSTANCE);
            query.setParameter("releaseDate", requestCoreCharges.getReleaseDate(),
                                              StandardBasicTypes.TIMESTAMP);
            query.setParameter("releaseCost", requestCoreCharges.getReleaseCost(),
                                              DoubleType.INSTANCE);
            query.setParameter("totalPagesReleased", requestCoreCharges.getTotalPagesReleased(),
                                                     IntegerType.INSTANCE);
            query.setParameter("salesTaxPercentage", requestCoreCharges.getSalesTaxPercentage(),
                                                     DoubleType.INSTANCE);
            query.setParameter("billingType", requestCoreCharges.getBillingType(),
                                              StringType.INSTANCE);
            query.setParameter("billingLocCode", requestCoreCharges.getBillingLocCode(),
                                                 StringType.INSTANCE);
            query.setParameter("billingLocName", requestCoreCharges.getBillingLocName(),
                                                 StringType.INSTANCE);
            query.setParameter("applySalesTax", requestCoreCharges.getApplySalesTax(),
                                                BooleanType.INSTANCE);
            query.setParameter("unbillable", requestCoreCharges.getUnbillable(),
                                             BooleanType.INSTANCE);
            query.setParameter("displayBillingInfo",
                                requestCoreCharges.isDisplayBillingPaymentInfo(),
                                BooleanType.INSTANCE);
            query.setParameter("totalPages", requestCoreCharges.getTotalPages(), IntegerType.INSTANCE);
            query.setParameter("createdDt", requestCoreCharges.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", requestCoreCharges.getCreatedBy(), LongType.INSTANCE);
            query.setParameter("modifiedDt", requestCoreCharges.getModifiedDt(),
                                             StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreCharges.getModifiedBy(), LongType.INSTANCE);

            List<BigDecimal> requestCoreChargesList = query.list();

            long requestCoreChargesId = requestCoreChargesList.get(0)
                    .longValue();


            if (requestCoreCharges.getRequestCoreChargesShipping() != null) {

                requestCoreCharges.getRequestCoreChargesShipping()
                                  .setRequestCoreChargesSeq(requestCoreChargesId);

                createRequestCoreChargesShipping(
                                        requestCoreCharges.getRequestCoreChargesShipping());
            }
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:RequestCoreChargesId"
                        + requestCoreChargesId);
            }
            return requestCoreChargesId;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }


    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestCoreChargesDAO
     * #updateRequestCoreChargesAsReleased(long)
     */
    public void updateRequestCoreChargesAsReleased(long requestId) {

        final String logSM = "updateRequestCoreChargesAsReleased(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: requestId:" + requestId);
        }

        try {

            Session session = getSession();
            Query query = session.getNamedQuery("updateRequestCoreChargesAsReleased");

            query.setParameter("requestId", requestId, LongType.INSTANCE);
            int noOfRowsAffected = query.executeUpdate();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:noOfRowsAffected:" + noOfRowsAffected);
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                                   ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.FAILED_TO_UPDATE_REQUESTCORE_CHARGES_AS_RELEASED,
                    e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestCoreChargesDAO
     * #updateRequestCoreChargesAsReleased(long)
     */
    public void updateRequestCoreChargesAsUnReleased(long requestId) {

        final String logSM = "updateRequestCoreChargesAsReleased(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: requestId:" + requestId);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("updateRequestCoreChargesAsUnReleased")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, LongType.INSTANCE);

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.FAILED_TO_UPDATE_REQUESTCORE_CHARGES_AS_RELEASED,
                    e.getMessage());
        }
    }

    /**
     * This method creates the RequestCoreCharges values for Shipping info
     *
     * @param requestCoreChargesShipping
     * @return
     */
    private void createRequestCoreChargesShipping(RequestCoreChargesShipping requestCoreChargesShipping) {
        final String logSM = "createRequestCoreChargesShipping(requestCoreChargesShipping)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreChargesShipping);
        }
        try {
            Session session = getSession();
            Query query = session
                    .getNamedQuery("createRequestCoreChargesShipping");

            query.setParameter("requestCoreChargesSeq", requestCoreChargesShipping.getRequestCoreChargesSeq(), LongType.INSTANCE);
            query.setParameter("shippingCharge", requestCoreChargesShipping.getShippingCharge(), DoubleType.INSTANCE);
            query.setParameter("postalCode", requestCoreChargesShipping.getPostalCode(), StringType.INSTANCE);
            query.setParameter("addressType", requestCoreChargesShipping.getAddressType(), StringType.INSTANCE);
            query.setParameter("state", requestCoreChargesShipping.getState(), StringType.INSTANCE);
            query.setParameter("shippingUrl", requestCoreChargesShipping.getShippingUrl(), StringType.INSTANCE);
            query.setParameter("address1", requestCoreChargesShipping.getAddress1(), StringType.INSTANCE);
            query.setParameter("address2", requestCoreChargesShipping.getAddress2(), StringType.INSTANCE);
            query.setParameter("shippingWeight", requestCoreChargesShipping.getShippingWeight(), DoubleType.INSTANCE);
            query.setParameter("trackingNumber", requestCoreChargesShipping.getTrackingNumber(), StringType.INSTANCE);
            query.setParameter("address3", requestCoreChargesShipping.getAddress3(), StringType.INSTANCE);
            query.setParameter("city", requestCoreChargesShipping.getCity(), StringType.INSTANCE);
            query.setParameter("countryCode", requestCoreChargesShipping.getCountryCode(), StringType.INSTANCE);
            query.setParameter("countryName", requestCoreChargesShipping.getCountryName(), StringType.INSTANCE);
            query.setParameter("willReleaseShipped", requestCoreChargesShipping.isWillReleaseShipped(), BooleanType.INSTANCE);
            query.setParameter("shippingMethod", requestCoreChargesShipping.getShippingMethod(), StringType.INSTANCE);
            query.setParameter("outputMethod", requestCoreChargesShipping.getOutputMethod(), StringType.INSTANCE);
            query.setParameter("shippingMethodId", requestCoreChargesShipping.getShippingMethodId(), LongType.INSTANCE);
            query.setParameter("nonPrintableAttachmentQueue", requestCoreChargesShipping.getNonPrintableAttachmentQueue(), StringType.INSTANCE);
            query.setParameter("createdDt", requestCoreChargesShipping.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", requestCoreChargesShipping.getCreatedBy(), LongType.INSTANCE);
            query.setParameter("modifiedDt", requestCoreChargesShipping.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreChargesShipping.getModifiedBy(), LongType.INSTANCE);

            query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }
    /**
     * This method creates the RequestCoreCharges values for Fee Charges info
     *
     * @param requestCoreChargesFee
     * @return
     */
    public void createRequestCoreChargesFee(
            RequestCoreChargesFee requestCoreChargesFee) {
        final String logSM = "createRequestCoreChargesFee(requestCoreChargesFee)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreChargesFee);
        }
        try {
            Session session = getSession();
            Query query = session.getNamedQuery("createRequestCoreChargesFee");

            query.setParameter("requestCoreChargesSeq", requestCoreChargesFee.getRequestCoreChargesSeq(), LongType.INSTANCE);
            query.setParameter("amount", requestCoreChargesFee.getAmount(), DoubleType.INSTANCE);
            query.setParameter("isCustomFee", requestCoreChargesFee.getIsCustomFee(), BooleanType.INSTANCE);
            query.setParameter("hasSalesTax", requestCoreChargesFee.getHasSalesTax(), BooleanType.INSTANCE);
            query.setParameter("salesTaxAmount", requestCoreChargesFee.getSalesTaxAmount(), DoubleType.INSTANCE);
            query.setParameter("feeType", requestCoreChargesFee.getFeeType(), StringType.INSTANCE);
            query.setParameter("createdDt", requestCoreChargesFee.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", requestCoreChargesFee.getCreatedBy(), LongType.INSTANCE);
            query.setParameter("modifiedDt", requestCoreChargesFee.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreChargesFee.getModifiedBy(), LongType.INSTANCE);

            query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }

    }
    /**
     * This method creates the RequestCoreCharges values for Document Charges
     * info
     *
     * @param requestCoreChargesDocument
     * @return
     */
    public void createRequestCoreChargesDocument(
            RequestCoreChargesDocument requestCoreChargesDocument) {
        final String logSM = "createRequestCoreChargesDocument(requestCoreChargesDocument)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreChargesDocument);
        }
        try {
            Session session = getSession();
            Query query = session.getNamedQuery("createRequestCoreChargesDocument");

            query.setParameter("requestCoreChargesSeq", requestCoreChargesDocument.getRequestCoreChargesSeq(), LongType.INSTANCE);
            query.setParameter("amount", requestCoreChargesDocument.getAmount(), DoubleType.INSTANCE);
            query.setParameter("copies", requestCoreChargesDocument.getCopies(), IntegerType.INSTANCE);
            query.setParameter("billingTierName", requestCoreChargesDocument.getBillingTierName(), StringType.INSTANCE);
            query.setParameter("totalPages", requestCoreChargesDocument.getTotalPages(), IntegerType.INSTANCE);
            query.setParameter("pages", requestCoreChargesDocument.getPages(), IntegerType.INSTANCE);
            query.setParameter("billingtierId", requestCoreChargesDocument.getBillingtierId(), StringType.INSTANCE);
            query.setParameter("releaseCount", requestCoreChargesDocument.getPages(), IntegerType.INSTANCE);
            query.setParameter("isElectronic", requestCoreChargesDocument.isIsElectronic(), BooleanType.INSTANCE);
            query.setParameter("removeBaseCharge", requestCoreChargesDocument.isRemoveBaseCharge(), BooleanType.INSTANCE);
            query.setParameter("hasSalesTax", requestCoreChargesDocument.getHasSalesTax(), BooleanType.INSTANCE);
            query.setParameter("salesTaxAmount", requestCoreChargesDocument.getSalesTaxAmount(), DoubleType.INSTANCE);
            query.setParameter("createdDt", requestCoreChargesDocument.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", requestCoreChargesDocument.getCreatedBy(), LongType.INSTANCE);
            query.setParameter("modifiedDt", requestCoreChargesDocument.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreChargesDocument.getModifiedBy(), LongType.INSTANCE);

            query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }

    }
    /**
     * This method retrieves the output type
     *
     * @param requestId
     * @return outputType
     *
     */
    public String retrieveOutputType(long requestId)
    {
        final String logSM = "retrieveOutputType(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }
        String outputType = null;
        try {
            Session session = getSession();
            String query = session.getNamedQuery("retrieveOutputType").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.addScalar("outputMethod", StringType.INSTANCE);

            sqlQuery.setParameter("requestId", requestId);

            outputType = (String) sqlQuery.uniqueResult();

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:" + requestId);
            }
            return outputType;


         } catch (DataIntegrityViolationException e) {
           throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                        e.getMessage());
         } catch (HibernateOptimisticLockingFailureException e) {
           throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                        e.getMessage());
         } catch (Throwable e) {
           throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                        e.getMessage());
         }

      }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestCoreChargesDAO#clearRequestInvoiceCharges(long)
     */
    public void clearRequestReleaseCost(long requestId) {

        final String logSM = "clearRequestInvoiceCharges(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }
        try {

            Session session = getSession();
            String query = session.getNamedQuery("clearRequestReleaseCost").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", requestId);
            sqlQuery.executeUpdate();

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:" + requestId);
            }


         } catch (DataIntegrityViolationException e) {
           throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                        e.getMessage());
         } catch (HibernateOptimisticLockingFailureException e) {
           throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                        e.getMessage());
         } catch (Throwable e) {
           throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                        e.getMessage());
         }
    }

    /**
     * @see com.mckesson.eig.roi.request.dao.RequestCoreChargesDAO
     * #revertRequestInvoiceCharges(long, double)
     */
    public void revertRequestReleaseCost(long requestId, double releaseCost) {

        final String logSM = "revertRequestReleaseCost(invoiceId, releaseCost)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }
        try {

            Session session = getSession();
            String query = session.getNamedQuery("revertRequestReleaseCost").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", requestId);
            sqlQuery.setParameter("releaseCost", releaseCost);
            sqlQuery.executeUpdate();

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:" + requestId);
            }


         } catch (DataIntegrityViolationException e) {
           throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                        e.getMessage());
         } catch (HibernateOptimisticLockingFailureException e) {
           throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                        e.getMessage());
         } catch (Throwable e) {
           throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                        e.getMessage());
         }

    }
    
    /**
     * @see com.mckesson.eig.roi.request.dao.RequestCoreChargesDAO
     * #updateRequestInvoiceCharges(long, double)
     */
    public void updateRequestReleaseCost(long requestId, double releaseCost,Date modifiedDt,int modifiedBySeq) {

        final String logSM = "updateRequestReleaseCost(requestId, releaseCost)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }
        try {

            Session session = getSession();
            String query = session.getNamedQuery("updateRequestReleaseCost").getQueryString();
            NativeQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", requestId,LongType.INSTANCE);
            sqlQuery.setParameter("releaseCost", releaseCost,DoubleType.INSTANCE);
            sqlQuery.setParameter("modifiedDt", modifiedDt,StandardBasicTypes.TIMESTAMP);
            sqlQuery.setParameter("modifiedBySeq", modifiedBySeq,IntegerType.INSTANCE);
            sqlQuery.executeUpdate();

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:" + requestId);
            }


         } catch (DataIntegrityViolationException e) {
           throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                        e.getMessage());
         } catch (HibernateOptimisticLockingFailureException e) {
           throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                        e.getMessage());
         } catch (Throwable e) {
           throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                        e.getMessage());
         }

    }

    /**
     *  This method updates the request balance for the adjustments made
     *  @param requestCoreId
     *  @param balanceDue
     *  @param creditAdjAmt
     *  @param date
     *  @param user
     */
    /*public void updateRequestBalanceForAdjustments(long requestCoreId,double balanceDue,double creditAdjAmt,Timestamp date,User user)
    {
        final String logSM = "updateRequestBalanceForAdjustments";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreId);
        }
        try {

            Session session = getSession();
            Query query = session.getNamedQuery("updateRequestBalanceForAdjustments");

            query.setParameter("requestCoreId", requestCoreId, LongType.INSTANCE);
            query.setParameter("balanceDue", balanceDue, DoubleType.INSTANCE);
            query.setParameter("creditAdjAmt", creditAdjAmt, DoubleType.INSTANCE);
            query.setParameter("modifiedDt", date,StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", user.getInstanceId(),IntegerType.INSTANCE);
            query.executeUpdate();

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:");
            }

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                                   ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                                   ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                   e.getMessage());
        }
    }*/
}
