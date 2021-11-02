/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.billing.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.dao.DataIntegrityViolationException;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.billing.model.PastDueInvoice;
import com.mckesson.eig.roi.billing.model.RequestorLetter;
import com.mckesson.eig.roi.billing.model.RequestorLetterInvoice;
import com.mckesson.eig.roi.billing.model.SearchPastDueInvoiceCriteria;
import com.mckesson.eig.roi.billing.model.SearchPastDueInvoiceResult;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Karthik Easwaran
 * @date   Aug 28, 2012
 * @since  Aug 28, 2012
 */
@Transactional
public class OverDueInvoiceCoreDAOImpl
extends ROIDAOImpl
implements OverDueInvoiceCoreDAO {


    private static final OCLogger LOG = new OCLogger(OverDueInvoiceCoreDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     * @see com.mckesson.eig.roi.billing.dao.OverDueInvoiceCoreDAO
     * #searchOverDueInvoices(com.mckesson.eig.roi.billing.model.SearchPastDueInvoiceCriteria)
     */
    @Override
    public SearchPastDueInvoiceResult searchOverDueInvoices(
                                                    SearchPastDueInvoiceCriteria searchCriteria) {

        final String logSM = "searchOverDueInvoices(searchCriteria)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + searchCriteria);
        }

        try {

            // session object is retrieved from the hibernate template and the
            // query is retrieved from the ssession
            Session session = getSession();
            NativeQuery query;
            String queryString;
            switch(searchCriteria.getOverDueRestriction()) {

                case BETWEEN:
                    queryString =
                            session.getNamedQuery("retrieveOverDueInvoicesWithBetweenRestriction")
                                   .getQueryString();
                    query = session.createSQLQuery(queryString);
                    query.setParameter("overDueTo", searchCriteria.getOverDueTo());
                    break;

                case GREATER:
                default:
                    queryString =
                            session.getNamedQuery("retrieveOverDueInvoicesWithGreaterRestriction")
                                   .getQueryString();
                    query = session.createSQLQuery(queryString);
                    break;
            }

            String requestorName = getSpecialCharSearchStr(searchCriteria.getRequestorName());
//            query.setProperties(new PastDueInvoice());
            query.addScalar("billingLocation", StringType.INSTANCE);
            query.addScalar("invoiceNumber", LongType.INSTANCE);
            query.addScalar("requestId", LongType.INSTANCE);
            query.addScalar("overdueAmount", DoubleType.INSTANCE);
            query.addScalar("requestorName", StringType.INSTANCE);
            query.addScalar("phoneNumber", StringType.INSTANCE);
            query.addScalar("requestorType", StringType.INSTANCE);
            query.addScalar("requestorId", LongType.INSTANCE);
            query.addScalar("overDueDays", LongType.INSTANCE);
            query.addScalar("invoiceAge", LongType.INSTANCE);


            query.setParameterList("facility", searchCriteria.getBillingLocations());
            query.setParameterList("requestorType", searchCriteria.getRequestorTypes());
            query.setParameter("overDueFrom", searchCriteria.getOverDueFrom());
            query.setParameter("requestorName",
                                ((StringUtilities.isEmpty(requestorName)) ? "" : requestorName)
                                        + ROIConstants.QUERY_LIKE);

            query.setResultTransformer(Transformers.aliasToBean(PastDueInvoice.class));

            @SuppressWarnings("unchecked")
            List<PastDueInvoice> result = query.list();

            // constructs the result object from the obtained query
            SearchPastDueInvoiceResult pastInvoicesResult = new SearchPastDueInvoiceResult();
            pastInvoicesResult.setPastDueInvoices(result);
            pastInvoicesResult.setMaxCountExceeded(result.size()
                                                    > ROIConstants.PASTDUE_INVOICES_MAX_COUNT);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + pastInvoicesResult);
            }

            return pastInvoicesResult;

        } catch (DataIntegrityViolationException e) {

            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {

            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e, ROIClientErrorCodes.DATABASE_OPERATION_FAILED);
        }
    }

    /**
     * @see com.mckesson.eig.roi.billing.dao.OverDueInvoiceCoreDAO
     * #createRequestorLetter(java.util.List)
     */
    public List<RequestorLetter> createRequestorLetter(List<RequestorLetter> reqLetter) {

        final String logSM = "createRequestorLetter(reqLetter)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: " + reqLetter);
        }

        if (CollectionUtilities.isEmpty(reqLetter)) {
            return reqLetter;
        }

        try {

            final Session session = getSession();
            final List<RequestorLetterInvoice> reqLetterInvoice =
                                            new ArrayList<RequestorLetterInvoice>();

            String queryString = session.getNamedQuery("insertRequestorLetter")
                                        .getQueryString();

            for (RequestorLetter letter : reqLetter) {


                NativeQuery query = session.createSQLQuery(queryString);
                query.addScalar("requestorLetterId", LongType.INSTANCE);

                query.setParameter("requestorId", letter.getRequestorId());
                query.setParameter("requestorName", letter.getRequestorName());
                query.setParameter("requestorPhone", letter.getRequestorPhone());
                query.setParameter("requestorAddress1", letter.getRequestorAddress1());
                query.setParameter("requestorAddress2", letter.getRequestorAddress2());
                query.setParameter("requestorAddress3", letter.getRequestorAddress3());
                query.setParameter("requestorCity", letter.getRequestorCity());
                query.setParameter("requestorState", letter.getRequestorState());
                query.setParameter("requestorPostalCode", letter.getRequestorPostalCode());
                query.setParameter("requestorCountry", letter.getRequestorCountry());
                query.setParameter("resendDate", letter.getResendDate());
                query.setParameter("outputMethod", letter.getOutputMethod());
                query.setParameter("queuePassword", letter.getQueuePassword());
                query.setParameter("templateName", letter.getTemplateName());
                query.setParameter("templateId", letter.getRequestTemplateId());
                query.setParameter("notes", letter.getNotesString());
                query.setParameter("dateRange", letter.getDateRange().name());

                query.setParameter("charges", letter.getCharges());
                query.setParameter("adjustmentAmount", letter.getAdjustmentAmount());
                query.setParameter("RequestorBalance", letter.getBalances());
                query.setParameter("UnAppliedAdjustment", letter.getUnAppliedAdjustment());
                query.setParameter("unAppliedPayment", letter.getUnAppliedPayment());
                query.setParameter("paymentAmount", letter.getPaymentAmount());

                query.setParameter("agingBalance30", letter.getBalance30());
                query.setParameter("agingBalance60", letter.getBalance60());
                query.setParameter("agingBalance90", letter.getBalance90());
                query.setParameter("agingBalanceOther", letter.getBalanceOther());

                query.setParameter("createdDate", letter.getCreatedDate());
                query.setParameter("createdBy", letter.getCreatedBy());
                query.setParameter("modifiedDate", letter.getModifiedDt());
                query.setParameter("modifiedBy", letter.getModifiedBy());

                @SuppressWarnings("unchecked")
                List<Long> list = query.list();
                if (list.size() == 1) {

                    long id = list.get(0).longValue();
                    letter.setRequestorLetterId(id);
                }

                List<RequestorLetterInvoice> invoices = letter.getInvoices();
                if (CollectionUtilities.hasContent(invoices)) {
                    reqLetterInvoice.addAll(letter.getInvoices());
                }
            }

//            if (CollectionUtilities.hasContent(reqLetterInvoice)) {
//                createRequestorLetterInvoices(session, reqLetterInvoice);
//            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

            return reqLetter;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e, ROIClientErrorCodes.DATABASE_OPERATION_FAILED);
        }

    }

    /**
     * Retrieve requestor letter.
     *
     * @param requestorLetterId the requestor letter id
     * @return the requestor letter
     * @see com.mckesson.eig.roi.billing.dao.OverDueInvoiceCoreDAO#retrieveRequestorLetter(long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public RequestorLetter retrieveRequestorLetter(long requestorLetterId) {

        final String logSM = "retrieveRequestorLetters(requestorLetterId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: " + requestorLetterId);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestorLetter").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.addScalar("requestorLetterId", LongType.INSTANCE);
            query.addScalar("requestorId", LongType.INSTANCE);
            query.addScalar("requestorName", StringType.INSTANCE);
            query.addScalar("requestorPhone", StringType.INSTANCE);
            query.addScalar("requestorAddress1", StringType.INSTANCE);
            query.addScalar("requestorAddress2", StringType.INSTANCE);
            query.addScalar("requestorAddress3", StringType.INSTANCE);
            query.addScalar("requestorCity", StringType.INSTANCE);
            query.addScalar("requestorState", StringType.INSTANCE);
            query.addScalar("requestorPostalCode", StringType.INSTANCE);
            query.addScalar("requestorCountry", StringType.INSTANCE);
            query.addScalar("resendDate", DateType.INSTANCE);
            query.addScalar("outputMethod", StringType.INSTANCE);
            query.addScalar("queuePassword", StringType.INSTANCE);
            query.addScalar("templateName", StringType.INSTANCE);
            query.addScalar("requestTemplateId", LongType.INSTANCE);
            query.addScalar("createdDate", DateType.INSTANCE);
            query.addScalar("createdBy", IntegerType.INSTANCE);
            query.addScalar("modifiedDt", DateType.INSTANCE);
            query.addScalar("modifiedBy", IntegerType.INSTANCE);
            query.addScalar("notesString", StringType.INSTANCE);
            query.addScalar("dateRangeAsString", StringType.INSTANCE);

            query.addScalar("charges", DoubleType.INSTANCE);
            query.addScalar("adjustmentAmount", DoubleType.INSTANCE);
            query.addScalar("paymentAmount", DoubleType.INSTANCE);
            query.addScalar("balances", DoubleType.INSTANCE);
            query.addScalar("unAppliedAdjustment", DoubleType.INSTANCE);
            query.addScalar("unAppliedPayment", DoubleType.INSTANCE);

            query.addScalar("balance30", DoubleType.INSTANCE);
            query.addScalar("balance60", DoubleType.INSTANCE);
            query.addScalar("balance90", DoubleType.INSTANCE);
            query.addScalar("balanceOther", DoubleType.INSTANCE);

            query.setResultTransformer(Transformers.aliasToBean(RequestorLetter.class));

            query.setParameter("requestorLetterId", requestorLetterId);
            List<RequestorLetter> letterList = query.list();

            RequestorLetter letter = null;
            if (letterList.size() > 0) {
                letter = letterList.get(0);
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }


           if (DO_DEBUG) {
               LOG.debug(logSM + "<<End:");
           }
           return letter;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                   e.getMessage());
        }
    }


}
