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

package com.mckesson.eig.roi.request.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.request.model.RequestCoreCharges;
import com.mckesson.eig.roi.request.model.RequestCoreChargesBilling;
import com.mckesson.eig.roi.request.model.RequestCoreChargesDocument;
import com.mckesson.eig.roi.request.model.RequestCoreChargesFee;
import com.mckesson.eig.roi.request.model.RequestCoreChargesShipping;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

/**
 * @author Keane
 * @date July 24, 2012
 */
public class RequestCoreChargesDAOImpl
extends ROIDAOImpl
implements RequestCoreChargesDAO {

    private static final Log LOG = LogFactory.getLogger(RequestCoreChargesDAOImpl.class);
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

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreCharges")
                                        .getQueryString();

            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestCoreSeq", requestCoreId, StandardBasicTypes.LONG);
            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("requestCoreSeq", StandardBasicTypes.LONG);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("recordVersion", StandardBasicTypes.INTEGER);
            query.addScalar("previouslyReleasedCost", StandardBasicTypes.DOUBLE);
            query.addScalar("releaseDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("releaseCost", StandardBasicTypes.DOUBLE);
            query.addScalar("totalPages", StandardBasicTypes.INTEGER);
            query.addScalar("balanceDue", StandardBasicTypes.DOUBLE);
            query.addScalar("totalPagesReleased", StandardBasicTypes.INTEGER);
            query.addScalar("salesTaxAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("salesTaxPercentage", StandardBasicTypes.DOUBLE);
            query.addScalar("billingType", StandardBasicTypes.STRING);
            query.addScalar("paymentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("creditAdjustmentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("debitAdjustmentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("billingLocCode", StandardBasicTypes.STRING);
            query.addScalar("billingLocName", StandardBasicTypes.STRING);
            query.addScalar("applySalesTax", StandardBasicTypes.BOOLEAN);
            query.addScalar("hasInvoices", StandardBasicTypes.BOOLEAN);
            query.addScalar("unbillable", StandardBasicTypes.BOOLEAN);
            query.addScalar("displayBillingPaymentInfo", StandardBasicTypes.BOOLEAN);
            query.addScalar("hasUnReleasedInvoices", StandardBasicTypes.BOOLEAN);
            query.addScalar("invoicesBalance", StandardBasicTypes.DOUBLE);
            query.addScalar("invoicesSalesTaxAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("totalUnappliedPaymentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("totalUnappliedAdjustmentAmount", StandardBasicTypes.DOUBLE);

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
            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreChargesShipping")
                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestCoreChargesSeq", requestCoreChargesSeq, StandardBasicTypes.LONG);
            query.addScalar("shippingCharge", StandardBasicTypes.DOUBLE);
            query.addScalar("postalCode", StandardBasicTypes.STRING);
            query.addScalar("addressType", StandardBasicTypes.STRING);
            query.addScalar("state", StandardBasicTypes.STRING);
            query.addScalar("shippingUrl", StandardBasicTypes.STRING);
            query.addScalar("address1", StandardBasicTypes.STRING);
            query.addScalar("address2", StandardBasicTypes.STRING);
            query.addScalar("shippingWeight", StandardBasicTypes.DOUBLE);
            query.addScalar("trackingNumber", StandardBasicTypes.STRING);
            query.addScalar("address3", StandardBasicTypes.STRING);
            query.addScalar("city", StandardBasicTypes.STRING);
            query.addScalar("countryCode", StandardBasicTypes.STRING);
            query.addScalar("countryName", StandardBasicTypes.STRING);
            query.addScalar("willReleaseShipped", StandardBasicTypes.BOOLEAN);
            query.addScalar("shippingMethod", StandardBasicTypes.STRING);
            query.addScalar("outputMethod", StandardBasicTypes.STRING);
            query.addScalar("shippingMethodId", StandardBasicTypes.LONG);
            query.addScalar("nonPrintableAttachmentQueue", StandardBasicTypes.STRING);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("recordVersion", StandardBasicTypes.INTEGER);
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

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreChargesFee")
                                        .getQueryString();

            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestCoreChargesSeq", requestCoreChargesSeq, StandardBasicTypes.LONG);
            query.addScalar("amount", StandardBasicTypes.DOUBLE);
            query.addScalar("isCustomFee", StandardBasicTypes.BOOLEAN);
            query.addScalar("feeType", StandardBasicTypes.STRING);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("recordVersion", StandardBasicTypes.INTEGER);
            query.addScalar("hasSalesTax", StandardBasicTypes.BOOLEAN);
            query.addScalar("SalesTaxAmount", StandardBasicTypes.DOUBLE);

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

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestCoreChargesDocument")
                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestCoreChargesSeq", requestChargesSeq, StandardBasicTypes.LONG);
            query.addScalar("amount", StandardBasicTypes.DOUBLE);
            query.addScalar("copies", StandardBasicTypes.INTEGER);
            query.addScalar("billingTierName", StandardBasicTypes.STRING);
            query.addScalar("totalPages", StandardBasicTypes.INTEGER);
            query.addScalar("pages", StandardBasicTypes.INTEGER);
            query.addScalar("billingtierId", StandardBasicTypes.STRING);
            query.addScalar("releaseCount", StandardBasicTypes.INTEGER);
            query.addScalar("isElectronic", StandardBasicTypes.BOOLEAN);
            query.addScalar("removeBaseCharge", StandardBasicTypes.BOOLEAN);
            query.addScalar("hasSalesTax", StandardBasicTypes.BOOLEAN);
            query.addScalar("SalesTaxAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("createdBy", StandardBasicTypes.LONG);
            query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", StandardBasicTypes.LONG);
            query.addScalar("recordVersion", StandardBasicTypes.INTEGER);

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
                                            new HashSet<RequestCoreChargesFee>(feeCharges));
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
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("deleteRequestCoreCharges");

            query.setParameter("requestCoreSeq", requestId, StandardBasicTypes.LONG);

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
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("deleteRequestCoreChargesDocument");

            query.setParameter("requestCoreChargesSeq", requestCoreChargesId, StandardBasicTypes.LONG);

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
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("deleteRequestCoreChargesFee");

            query.setParameter("requestCoreChargesSeq", requestCoreChargesId, StandardBasicTypes.LONG);

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
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("deleteRequestCoreChargesShipping");

            query.setParameter("requestCoreChargesSeq", requestCoreChargesId, StandardBasicTypes.LONG);

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
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("createRequestCoreCharges");

            query.setParameter("requestCoreSeq", requestCoreCharges.getRequestCoreSeq(),
                                                 StandardBasicTypes.LONG);
            query.setParameter("releaseDate", requestCoreCharges.getReleaseDate(),
                                              StandardBasicTypes.TIMESTAMP);
            query.setParameter("releaseCost", requestCoreCharges.getReleaseCost(),
                                              StandardBasicTypes.DOUBLE);
            query.setParameter("totalPagesReleased", requestCoreCharges.getTotalPagesReleased(),
                                                     StandardBasicTypes.INTEGER);
            query.setParameter("salesTaxPercentage", requestCoreCharges.getSalesTaxPercentage(),
                                                     StandardBasicTypes.DOUBLE);
            query.setParameter("billingType", requestCoreCharges.getBillingType(),
                                              StandardBasicTypes.STRING);
            query.setParameter("billingLocCode", requestCoreCharges.getBillingLocCode(),
                                                 StandardBasicTypes.STRING);
            query.setParameter("billingLocName", requestCoreCharges.getBillingLocName(),
                                                 StandardBasicTypes.STRING);
            query.setParameter("applySalesTax", requestCoreCharges.getApplySalesTax(),
                                                StandardBasicTypes.BOOLEAN);
            query.setParameter("unbillable", requestCoreCharges.getUnbillable(),
                                             StandardBasicTypes.BOOLEAN);
            query.setParameter("displayBillingInfo",
                                requestCoreCharges.isDisplayBillingPaymentInfo(),
                                StandardBasicTypes.BOOLEAN);
            query.setParameter("totalPages", requestCoreCharges.getTotalPages(), StandardBasicTypes.INTEGER);
            query.setParameter("createdDt", requestCoreCharges.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", requestCoreCharges.getCreatedBy(), StandardBasicTypes.LONG);
            query.setParameter("modifiedDt", requestCoreCharges.getModifiedDt(),
                                             StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreCharges.getModifiedBy(), StandardBasicTypes.LONG);

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

            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("updateRequestCoreChargesAsReleased");

            query.setParameter("requestId", requestId, StandardBasicTypes.LONG);
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

            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("updateRequestCoreChargesAsUnReleased");

            query.setParameter("requestId", requestId, StandardBasicTypes.LONG);
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
            Session session = getSessionFactory().getCurrentSession();
            Query query = session
                    .getNamedQuery("createRequestCoreChargesShipping");

            query.setParameter("requestCoreChargesSeq", requestCoreChargesShipping.getRequestCoreChargesSeq(), StandardBasicTypes.LONG);
            query.setParameter("shippingCharge", requestCoreChargesShipping.getShippingCharge(), StandardBasicTypes.DOUBLE);
            query.setParameter("postalCode", requestCoreChargesShipping.getPostalCode(), StandardBasicTypes.STRING);
            query.setParameter("addressType", requestCoreChargesShipping.getAddressType(), StandardBasicTypes.STRING);
            query.setParameter("state", requestCoreChargesShipping.getState(), StandardBasicTypes.STRING);
            query.setParameter("shippingUrl", requestCoreChargesShipping.getShippingUrl(), StandardBasicTypes.STRING);
            query.setParameter("address1", requestCoreChargesShipping.getAddress1(), StandardBasicTypes.STRING);
            query.setParameter("address2", requestCoreChargesShipping.getAddress2(), StandardBasicTypes.STRING);
            query.setParameter("shippingWeight", requestCoreChargesShipping.getShippingWeight(), StandardBasicTypes.DOUBLE);
            query.setParameter("trackingNumber", requestCoreChargesShipping.getTrackingNumber(), StandardBasicTypes.STRING);
            query.setParameter("address3", requestCoreChargesShipping.getAddress3(), StandardBasicTypes.STRING);
            query.setParameter("city", requestCoreChargesShipping.getCity(), StandardBasicTypes.STRING);
            query.setParameter("countryCode", requestCoreChargesShipping.getCountryCode(), StandardBasicTypes.STRING);
            query.setParameter("countryName", requestCoreChargesShipping.getCountryName(), StandardBasicTypes.STRING);
            query.setParameter("willReleaseShipped", requestCoreChargesShipping.isWillReleaseShipped(), StandardBasicTypes.BOOLEAN);
            query.setParameter("shippingMethod", requestCoreChargesShipping.getShippingMethod(), StandardBasicTypes.STRING);
            query.setParameter("outputMethod", requestCoreChargesShipping.getOutputMethod(), StandardBasicTypes.STRING);
            query.setParameter("shippingMethodId", requestCoreChargesShipping.getShippingMethodId(), StandardBasicTypes.LONG);
            query.setParameter("nonPrintableAttachmentQueue", requestCoreChargesShipping.getNonPrintableAttachmentQueue(), StandardBasicTypes.STRING);
            query.setParameter("createdDt", requestCoreChargesShipping.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", requestCoreChargesShipping.getCreatedBy(), StandardBasicTypes.LONG);
            query.setParameter("modifiedDt", requestCoreChargesShipping.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreChargesShipping.getModifiedBy(), StandardBasicTypes.LONG);

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
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("createRequestCoreChargesFee");

            query.setParameter("requestCoreChargesSeq", requestCoreChargesFee.getRequestCoreChargesSeq(), StandardBasicTypes.LONG);
            query.setParameter("amount", requestCoreChargesFee.getAmount(), StandardBasicTypes.DOUBLE);
            query.setParameter("isCustomFee", requestCoreChargesFee.getIsCustomFee(), StandardBasicTypes.BOOLEAN);
            query.setParameter("hasSalesTax", requestCoreChargesFee.getHasSalesTax(), StandardBasicTypes.BOOLEAN);
            query.setParameter("salesTaxAmount", requestCoreChargesFee.getSalesTaxAmount(), StandardBasicTypes.DOUBLE);
            query.setParameter("feeType", requestCoreChargesFee.getFeeType(), StandardBasicTypes.STRING);
            query.setParameter("createdDt", requestCoreChargesFee.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", requestCoreChargesFee.getCreatedBy(), StandardBasicTypes.LONG);
            query.setParameter("modifiedDt", requestCoreChargesFee.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreChargesFee.getModifiedBy(), StandardBasicTypes.LONG);

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
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("createRequestCoreChargesDocument");

            query.setParameter("requestCoreChargesSeq", requestCoreChargesDocument.getRequestCoreChargesSeq(), StandardBasicTypes.LONG);
            query.setParameter("amount", requestCoreChargesDocument.getAmount(), StandardBasicTypes.DOUBLE);
            query.setParameter("copies", requestCoreChargesDocument.getCopies(), StandardBasicTypes.INTEGER);
            query.setParameter("billingTierName", requestCoreChargesDocument.getBillingTierName(), StandardBasicTypes.STRING);
            query.setParameter("totalPages", requestCoreChargesDocument.getTotalPages(), StandardBasicTypes.INTEGER);
            query.setParameter("pages", requestCoreChargesDocument.getPages(), StandardBasicTypes.INTEGER);
            query.setParameter("billingtierId", requestCoreChargesDocument.getBillingtierId(), StandardBasicTypes.STRING);
            query.setParameter("releaseCount", requestCoreChargesDocument.getPages(), StandardBasicTypes.INTEGER);
            query.setParameter("isElectronic", requestCoreChargesDocument.isIsElectronic(), StandardBasicTypes.BOOLEAN);
            query.setParameter("removeBaseCharge", requestCoreChargesDocument.isRemoveBaseCharge(), StandardBasicTypes.BOOLEAN);
            query.setParameter("hasSalesTax", requestCoreChargesDocument.getHasSalesTax(), StandardBasicTypes.BOOLEAN);
            query.setParameter("salesTaxAmount", requestCoreChargesDocument.getSalesTaxAmount(), StandardBasicTypes.DOUBLE);
            query.setParameter("createdDt", requestCoreChargesDocument.getCreatedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("createdBy", requestCoreChargesDocument.getCreatedBy(), StandardBasicTypes.LONG);
            query.setParameter("modifiedDt", requestCoreChargesDocument.getModifiedDt(), StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreChargesDocument.getModifiedBy(), StandardBasicTypes.LONG);

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
            Session session = getSessionFactory().getCurrentSession();
            String query = session.getNamedQuery("retrieveOutputType").getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.addScalar("outputMethod", StandardBasicTypes.STRING);

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

            Session session = getSessionFactory().getCurrentSession();
            String query = session.getNamedQuery("clearRequestReleaseCost").getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);
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

            Session session = getSessionFactory().getCurrentSession();
            String query = session.getNamedQuery("revertRequestReleaseCost").getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);
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

            Session session = getSessionFactory().getCurrentSession();
            String query = session.getNamedQuery("updateRequestReleaseCost").getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", requestId,StandardBasicTypes.LONG);
            sqlQuery.setParameter("releaseCost", releaseCost,StandardBasicTypes.DOUBLE);
            sqlQuery.setParameter("modifiedDt", modifiedDt,StandardBasicTypes.TIMESTAMP);
            sqlQuery.setParameter("modifiedBySeq", modifiedBySeq,StandardBasicTypes.INTEGER);
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

            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("updateRequestBalanceForAdjustments");

            query.setParameter("requestCoreId", requestCoreId, StandardBasicTypes.LONG);
            query.setParameter("balanceDue", balanceDue, StandardBasicTypes.DOUBLE);
            query.setParameter("creditAdjAmt", creditAdjAmt, StandardBasicTypes.DOUBLE);
            query.setParameter("modifiedDt", date,StandardBasicTypes.TIMESTAMP);
            query.setParameter("modifiedBy", user.getInstanceId(),StandardBasicTypes.INTEGER);
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
