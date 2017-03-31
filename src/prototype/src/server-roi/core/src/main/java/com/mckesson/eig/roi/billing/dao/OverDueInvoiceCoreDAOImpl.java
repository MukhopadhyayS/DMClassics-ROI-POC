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

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.billing.model.PastDueInvoice;
import com.mckesson.eig.roi.billing.model.RequestorLetter;
import com.mckesson.eig.roi.billing.model.RequestorLetterInvoice;
import com.mckesson.eig.roi.billing.model.SearchPastDueInvoiceCriteria;
import com.mckesson.eig.roi.billing.model.SearchPastDueInvoiceResult;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author Karthik Easwaran
 * @date   Aug 28, 2012
 * @since  Aug 28, 2012
 */
public class OverDueInvoiceCoreDAOImpl
extends ROIDAOImpl
implements OverDueInvoiceCoreDAO {


    private static final Log LOG = LogFactory.getLogger(OverDueInvoiceCoreDAOImpl.class);
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
            Session session = getSessionFactory().getCurrentSession();
            SQLQuery query;
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
            query.addScalar("billingLocation", StandardBasicTypes.STRING);
            query.addScalar("invoiceNumber", StandardBasicTypes.LONG);
            query.addScalar("requestId", StandardBasicTypes.LONG);
            query.addScalar("overdueAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("requestorName", StandardBasicTypes.STRING);
            query.addScalar("phoneNumber", StandardBasicTypes.STRING);
            query.addScalar("requestorType", StandardBasicTypes.STRING);
            query.addScalar("requestorId", StandardBasicTypes.LONG);
            query.addScalar("overDueDays", StandardBasicTypes.LONG);
            query.addScalar("invoiceAge", StandardBasicTypes.LONG);


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

            final Session session = getSessionFactory().getCurrentSession();
            final List<RequestorLetterInvoice> reqLetterInvoice =
                                            new ArrayList<RequestorLetterInvoice>();

            String queryString = session.getNamedQuery("insertRequestorLetter")
                                        .getQueryString();

            for (RequestorLetter letter : reqLetter) {


                SQLQuery query = session.createSQLQuery(queryString);
                query.addScalar("requestorLetterId", StandardBasicTypes.LONG);

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

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestorLetter").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
            query.addScalar("requestorLetterId", StandardBasicTypes.LONG);
            query.addScalar("requestorId", StandardBasicTypes.LONG);
            query.addScalar("requestorName", StandardBasicTypes.STRING);
            query.addScalar("requestorPhone", StandardBasicTypes.STRING);
            query.addScalar("requestorAddress1", StandardBasicTypes.STRING);
            query.addScalar("requestorAddress2", StandardBasicTypes.STRING);
            query.addScalar("requestorAddress3", StandardBasicTypes.STRING);
            query.addScalar("requestorCity", StandardBasicTypes.STRING);
            query.addScalar("requestorState", StandardBasicTypes.STRING);
            query.addScalar("requestorPostalCode", StandardBasicTypes.STRING);
            query.addScalar("requestorCountry", StandardBasicTypes.STRING);
            query.addScalar("resendDate", StandardBasicTypes.DATE);
            query.addScalar("outputMethod", StandardBasicTypes.STRING);
            query.addScalar("queuePassword", StandardBasicTypes.STRING);
            query.addScalar("templateName", StandardBasicTypes.STRING);
            query.addScalar("requestTemplateId", StandardBasicTypes.LONG);
            query.addScalar("createdDate", StandardBasicTypes.DATE);
            query.addScalar("createdBy", StandardBasicTypes.INTEGER);
            query.addScalar("modifiedDt", StandardBasicTypes.DATE);
            query.addScalar("modifiedBy", StandardBasicTypes.INTEGER);
            query.addScalar("notesString", StandardBasicTypes.STRING);
            query.addScalar("dateRangeAsString", StandardBasicTypes.STRING);

            query.addScalar("charges", StandardBasicTypes.DOUBLE);
            query.addScalar("adjustmentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("paymentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("balances", StandardBasicTypes.DOUBLE);
            query.addScalar("unAppliedAdjustment", StandardBasicTypes.DOUBLE);
            query.addScalar("unAppliedPayment", StandardBasicTypes.DOUBLE);

            query.addScalar("balance30", StandardBasicTypes.DOUBLE);
            query.addScalar("balance60", StandardBasicTypes.DOUBLE);
            query.addScalar("balance90", StandardBasicTypes.DOUBLE);
            query.addScalar("balanceOther", StandardBasicTypes.DOUBLE);

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
