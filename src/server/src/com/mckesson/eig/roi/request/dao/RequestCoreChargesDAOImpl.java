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
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

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

            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestCoreSeq", requestCoreId, Hibernate.LONG);
            query.addScalar("id", Hibernate.LONG);
            query.addScalar("requestCoreSeq", Hibernate.LONG);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);
            query.addScalar("previouslyReleasedCost", Hibernate.DOUBLE);
            query.addScalar("releaseDate", Hibernate.TIMESTAMP);
            query.addScalar("releaseCost", Hibernate.DOUBLE);
            query.addScalar("totalRequestCost", Hibernate.DOUBLE);
            query.addScalar("totalPages", Hibernate.INTEGER);
            query.addScalar("balanceDue", Hibernate.DOUBLE);
            query.addScalar("totalPagesReleased", Hibernate.INTEGER);
            query.addScalar("salesTaxAmount", Hibernate.DOUBLE);
            query.addScalar("salesTaxPercentage", Hibernate.DOUBLE);
            query.addScalar("billingType", Hibernate.STRING);
            query.addScalar("paymentAmount", Hibernate.DOUBLE);
            query.addScalar("creditAdjustmentAmount", Hibernate.DOUBLE);
            query.addScalar("debitAdjustmentAmount", Hibernate.DOUBLE);
            query.addScalar("billingLocCode", Hibernate.STRING);
            query.addScalar("billingLocName", Hibernate.STRING);
            query.addScalar("applySalesTax", Hibernate.BOOLEAN);
            query.addScalar("hasInvoices", Hibernate.BOOLEAN);
            query.addScalar("unbillable", Hibernate.BOOLEAN);
            query.addScalar("displayBillingPaymentInfo", Hibernate.BOOLEAN);
            query.addScalar("hasUnReleasedInvoices", Hibernate.BOOLEAN);
            query.addScalar("invoicesBalance", Hibernate.DOUBLE);
            query.addScalar("invoicesSalesTaxAmount", Hibernate.DOUBLE);
            query.addScalar("totalUnappliedPaymentAmount", Hibernate.DOUBLE);
            query.addScalar("totalUnappliedAdjustmentAmount", Hibernate.DOUBLE);
            query.addScalar("invoiceBaseCharge", Hibernate.DOUBLE);
            
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
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestCoreChargesSeq", requestCoreChargesSeq, Hibernate.LONG);
            query.addScalar("shippingCharge", Hibernate.DOUBLE);
            query.addScalar("postalCode", Hibernate.STRING);
            query.addScalar("addressType", Hibernate.STRING);
            query.addScalar("state", Hibernate.STRING);
            query.addScalar("shippingUrl", Hibernate.STRING);
            query.addScalar("address1", Hibernate.STRING);
            query.addScalar("address2", Hibernate.STRING);
            query.addScalar("shippingWeight", Hibernate.DOUBLE);
            query.addScalar("trackingNumber", Hibernate.STRING);
            query.addScalar("address3", Hibernate.STRING);
            query.addScalar("city", Hibernate.STRING);
            query.addScalar("countryCode", Hibernate.STRING);
            query.addScalar("countryName", Hibernate.STRING);
            query.addScalar("willReleaseShipped", Hibernate.BOOLEAN);
            query.addScalar("shippingMethod", Hibernate.STRING);
            query.addScalar("outputMethod", Hibernate.STRING);
            query.addScalar("shippingMethodId", Hibernate.LONG);
            query.addScalar("nonPrintableAttachmentQueue", Hibernate.STRING);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);
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

            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestCoreChargesSeq", requestCoreChargesSeq, Hibernate.LONG);
            query.addScalar("amount", Hibernate.DOUBLE);
            query.addScalar("isCustomFee", Hibernate.BOOLEAN);
            query.addScalar("feeType", Hibernate.STRING);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);
            query.addScalar("hasSalesTax", Hibernate.BOOLEAN);
            query.addScalar("SalesTaxAmount", Hibernate.DOUBLE);

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
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestCoreChargesSeq", requestChargesSeq, Hibernate.LONG);
            query.addScalar("amount", Hibernate.DOUBLE);
            query.addScalar("copies", Hibernate.INTEGER);
            query.addScalar("billingTierName", Hibernate.STRING);
            query.addScalar("totalPages", Hibernate.INTEGER);
            query.addScalar("pages", Hibernate.INTEGER);
            query.addScalar("billingtierId", Hibernate.STRING);
            query.addScalar("releaseCount", Hibernate.INTEGER);
            query.addScalar("isElectronic", Hibernate.BOOLEAN);
            query.addScalar("removeBaseCharge", Hibernate.BOOLEAN);
            query.addScalar("hasSalesTax", Hibernate.BOOLEAN);
            query.addScalar("SalesTaxAmount", Hibernate.DOUBLE);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);

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

            query.setParameter("requestCoreSeq", requestId, Hibernate.LONG);

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

            query.setParameter("requestCoreChargesSeq", requestCoreChargesId, Hibernate.LONG);

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

            query.setParameter("requestCoreChargesSeq", requestCoreChargesId, Hibernate.LONG);

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

            query.setParameter("requestCoreChargesSeq", requestCoreChargesId, Hibernate.LONG);

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
                                                 Hibernate.LONG);
            query.setParameter("releaseDate", requestCoreCharges.getReleaseDate(),
                                              Hibernate.TIMESTAMP);
            query.setParameter("releaseCost", requestCoreCharges.getReleaseCost(),
                                              Hibernate.DOUBLE);
            query.setParameter("totalPagesReleased", requestCoreCharges.getTotalPagesReleased(),
                                                     Hibernate.INTEGER);
            query.setParameter("salesTaxPercentage", requestCoreCharges.getSalesTaxPercentage(),
                                                     Hibernate.DOUBLE);
            query.setParameter("billingType", requestCoreCharges.getBillingType(),
                                              Hibernate.STRING);
            query.setParameter("billingLocCode", requestCoreCharges.getBillingLocCode(),
                                                 Hibernate.STRING);
            query.setParameter("billingLocName", requestCoreCharges.getBillingLocName(),
                                                 Hibernate.STRING);
            query.setParameter("applySalesTax", requestCoreCharges.getApplySalesTax(),
                                                Hibernate.BOOLEAN);
            query.setParameter("unbillable", requestCoreCharges.getUnbillable(),
                                             Hibernate.BOOLEAN);
            query.setParameter("displayBillingInfo",
                                requestCoreCharges.isDisplayBillingPaymentInfo(),
                                Hibernate.BOOLEAN);
            query.setParameter("totalPages", requestCoreCharges.getTotalPages(), Hibernate.INTEGER);
            query.setParameter("createdDt", requestCoreCharges.getCreatedDt(), Hibernate.TIMESTAMP);
            query.setParameter("createdBy", requestCoreCharges.getCreatedBy(), Hibernate.LONG);
            query.setParameter("modifiedDt", requestCoreCharges.getModifiedDt(),
                                             Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreCharges.getModifiedBy(), Hibernate.LONG);

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

            query.setParameter("requestId", requestId, Hibernate.LONG);
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
            Query query = session.getNamedQuery("updateRequestCoreChargesAsUnReleased");

            query.setParameter("requestId", requestId, Hibernate.LONG);
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
            Session session = getSession();
            Query query = session
                    .getNamedQuery("createRequestCoreChargesShipping");

            query.setParameter("requestCoreChargesSeq", requestCoreChargesShipping.getRequestCoreChargesSeq(), Hibernate.LONG);
            query.setParameter("shippingCharge", requestCoreChargesShipping.getShippingCharge(), Hibernate.DOUBLE);
            query.setParameter("postalCode", requestCoreChargesShipping.getPostalCode(), Hibernate.STRING);
            query.setParameter("addressType", requestCoreChargesShipping.getAddressType(), Hibernate.STRING);
            query.setParameter("state", requestCoreChargesShipping.getState(), Hibernate.STRING);
            query.setParameter("shippingUrl", requestCoreChargesShipping.getShippingUrl(), Hibernate.STRING);
            query.setParameter("address1", requestCoreChargesShipping.getAddress1(), Hibernate.STRING);
            query.setParameter("address2", requestCoreChargesShipping.getAddress2(), Hibernate.STRING);
            query.setParameter("shippingWeight", requestCoreChargesShipping.getShippingWeight(), Hibernate.DOUBLE);
            query.setParameter("trackingNumber", requestCoreChargesShipping.getTrackingNumber(), Hibernate.STRING);
            query.setParameter("address3", requestCoreChargesShipping.getAddress3(), Hibernate.STRING);
            query.setParameter("city", requestCoreChargesShipping.getCity(), Hibernate.STRING);
            query.setParameter("countryCode", requestCoreChargesShipping.getCountryCode(), Hibernate.STRING);
            query.setParameter("countryName", requestCoreChargesShipping.getCountryName(), Hibernate.STRING);
            query.setParameter("willReleaseShipped", requestCoreChargesShipping.isWillReleaseShipped(), Hibernate.BOOLEAN);
            query.setParameter("shippingMethod", requestCoreChargesShipping.getShippingMethod(), Hibernate.STRING);
            query.setParameter("outputMethod", requestCoreChargesShipping.getOutputMethod(), Hibernate.STRING);
            query.setParameter("shippingMethodId", requestCoreChargesShipping.getShippingMethodId(), Hibernate.LONG);
            query.setParameter("nonPrintableAttachmentQueue", requestCoreChargesShipping.getNonPrintableAttachmentQueue(), Hibernate.STRING);
            query.setParameter("createdDt", requestCoreChargesShipping.getCreatedDt(), Hibernate.TIMESTAMP);
            query.setParameter("createdBy", requestCoreChargesShipping.getCreatedBy(), Hibernate.LONG);
            query.setParameter("modifiedDt", requestCoreChargesShipping.getModifiedDt(), Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreChargesShipping.getModifiedBy(), Hibernate.LONG);

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

            query.setParameter("requestCoreChargesSeq", requestCoreChargesFee.getRequestCoreChargesSeq(), Hibernate.LONG);
            query.setParameter("amount", requestCoreChargesFee.getAmount(), Hibernate.DOUBLE);
            query.setParameter("isCustomFee", requestCoreChargesFee.getIsCustomFee(), Hibernate.BOOLEAN);
            query.setParameter("hasSalesTax", requestCoreChargesFee.getHasSalesTax(), Hibernate.BOOLEAN);
            query.setParameter("salesTaxAmount", requestCoreChargesFee.getSalesTaxAmount(), Hibernate.DOUBLE);
            query.setParameter("feeType", requestCoreChargesFee.getFeeType(), Hibernate.STRING);
            query.setParameter("createdDt", requestCoreChargesFee.getCreatedDt(), Hibernate.TIMESTAMP);
            query.setParameter("createdBy", requestCoreChargesFee.getCreatedBy(), Hibernate.LONG);
            query.setParameter("modifiedDt", requestCoreChargesFee.getModifiedDt(), Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreChargesFee.getModifiedBy(), Hibernate.LONG);

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

            query.setParameter("requestCoreChargesSeq", requestCoreChargesDocument.getRequestCoreChargesSeq(), Hibernate.LONG);
            query.setParameter("amount", requestCoreChargesDocument.getAmount(), Hibernate.DOUBLE);
            query.setParameter("copies", requestCoreChargesDocument.getCopies(), Hibernate.INTEGER);
            query.setParameter("billingTierName", requestCoreChargesDocument.getBillingTierName(), Hibernate.STRING);
            query.setParameter("totalPages", requestCoreChargesDocument.getTotalPages(), Hibernate.INTEGER);
            query.setParameter("pages", requestCoreChargesDocument.getPages(), Hibernate.INTEGER);
            query.setParameter("billingtierId", requestCoreChargesDocument.getBillingtierId(), Hibernate.STRING);
            query.setParameter("releaseCount", requestCoreChargesDocument.getPages(), Hibernate.INTEGER);
            query.setParameter("isElectronic", requestCoreChargesDocument.isIsElectronic(), Hibernate.BOOLEAN);
            query.setParameter("removeBaseCharge", requestCoreChargesDocument.isRemoveBaseCharge(), Hibernate.BOOLEAN);
            query.setParameter("hasSalesTax", requestCoreChargesDocument.getHasSalesTax(), Hibernate.BOOLEAN);
            query.setParameter("salesTaxAmount", requestCoreChargesDocument.getSalesTaxAmount(), Hibernate.DOUBLE);
            query.setParameter("createdDt", requestCoreChargesDocument.getCreatedDt(), Hibernate.TIMESTAMP);
            query.setParameter("createdBy", requestCoreChargesDocument.getCreatedBy(), Hibernate.LONG);
            query.setParameter("modifiedDt", requestCoreChargesDocument.getModifiedDt(), Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy", requestCoreChargesDocument.getModifiedBy(), Hibernate.LONG);

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
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.addScalar("outputMethod", Hibernate.STRING);

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

            Session session = getSession();
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

            Session session = getSession();
            String query = session.getNamedQuery("updateRequestReleaseCost").getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("requestId", requestId,Hibernate.LONG);
            sqlQuery.setParameter("releaseCost", releaseCost,Hibernate.DOUBLE);
            sqlQuery.setParameter("modifiedDt", modifiedDt,Hibernate.TIMESTAMP);
            sqlQuery.setParameter("modifiedBySeq", modifiedBySeq,Hibernate.INTEGER);
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

            query.setParameter("requestCoreId", requestCoreId, Hibernate.LONG);
            query.setParameter("balanceDue", balanceDue, Hibernate.DOUBLE);
            query.setParameter("creditAdjAmt", creditAdjAmt, Hibernate.DOUBLE);
            query.setParameter("modifiedDt", date,Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy", user.getInstanceId(),Hibernate.INTEGER);
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
