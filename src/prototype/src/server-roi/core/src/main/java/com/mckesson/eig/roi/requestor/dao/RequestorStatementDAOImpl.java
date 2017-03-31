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

package com.mckesson.eig.roi.requestor.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.billing.model.PastInvoice;
import com.mckesson.eig.roi.requestor.model.RequestorAccount;
import com.mckesson.eig.roi.requestor.model.RequestorAging;
import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria.DateRange;
import com.mckesson.eig.roi.requestor.model.RequestorStatementInfo;
import com.mckesson.eig.roi.requestor.model.RequestorTransaction;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;

/**
 * @author Karthik Easwaran
 * @date   Dec 20, 2012
 * @since  Dec 20, 2012
 */
public class RequestorStatementDAOImpl
extends ROIDAOImpl
implements RequestorStatementDAO {

    private static final Log LOG = LogFactory.getLogger(RequestorStatementDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     *
     * @see com.mckesson.eig.roi.requestor.dao.RequestorStatementDAO
     * #retrieveRequestorPastInvoices(long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PastInvoice> retrieveRequestorPastInvoices(long requestorId) {

        final String logSM = "retrieveRequestorPastInvoices(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestorId" + requestorId);
        }
        try {

            Session session = getSessionFactory().getCurrentSession();
            String queryString =
                    session.getNamedQuery("retrieveRequestorPastInvoices").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestorId", requestorId);

            query.addScalar("invoiceId", StandardBasicTypes.LONG);
            query.addScalar("amount", StandardBasicTypes.DOUBLE);
            query.addScalar("createdDate", StandardBasicTypes.STRING);

            query.setResultTransformer(Transformers.aliasToBean(PastInvoice.class));
            List<PastInvoice> invoiceDetailsList = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

            return invoiceDetailsList;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * Retrieves the list of all transactions for the given requestor
     * and in between the given date range
     *
     * @param requestorId - requestorId to whom the transaction is to be retrieved
     * @param dateRange - the date renge where the transactoin is to retrieved
     *
     * @return - list of all transactions
     */
    @Override
    public List<RequestorTransaction> retrieveRequestorTransactions(long requestorId,
                                                                    DateRange dateRange) {


        final String logSM = "retrieveRequestorTransactions(requestorId, dateRange)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestorId" + requestorId + ", DateRange:" + dateRange);
        }
        try {

            Session session = getSessionFactory().getCurrentSession();

            boolean isAllTransactions = false;
            String queryName = "retrieveRequestorTransactions";
            if (DateRange.ALL.equals(dateRange)) {

                isAllTransactions = true;
                queryName = "retrieveAllRequestorTransactions";
            }

            String queryString = session.getNamedQuery(queryName).getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestorId", requestorId, StandardBasicTypes.LONG);
            if (!isAllTransactions) {
                query.setParameter("dateRange", dateRange.getAgingDays(), StandardBasicTypes.INTEGER);
            }

            query.addScalar("date", StandardBasicTypes.DATE);
            query.addScalar("invoiceId", StandardBasicTypes.LONG);
            query.addScalar("type", StandardBasicTypes.STRING);
            query.addScalar("description", StandardBasicTypes.STRING);
            query.addScalar("charges", StandardBasicTypes.DOUBLE);
            query.addScalar("balances", StandardBasicTypes.DOUBLE);
            // DE1560/CR# 384,396 - Fix
            query.addScalar("unbillable", StandardBasicTypes.BOOLEAN);
            query.addScalar("adjustment", StandardBasicTypes.DOUBLE);
            query.addScalar("payment", StandardBasicTypes.DOUBLE);
            query.addScalar("refund", StandardBasicTypes.DOUBLE);
            query.addScalar("invoiceAmount", StandardBasicTypes.DOUBLE);

            query.setResultTransformer(Transformers.aliasToBean(RequestorTransaction.class));
            @SuppressWarnings("unchecked")
            List<RequestorTransaction> requestorTransactions = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

            return requestorTransactions;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * Retrieves the requestor account balances details
     *
     * @param requestorId - requestorId to whom the transaction is to be retrieved
     *
     * @return - RequestorAccount - Account Balances
     */
    @Override
    public RequestorAccount retrieveRequestorAccountBalances(long requestorId) {


        final String logSM = "retrieveRequestorAccountBalances(requestorId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestorId" + requestorId);
        }
        try {

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestorAccountDetails")
                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestorId", requestorId, StandardBasicTypes.LONG);

            query.addScalar("charge", StandardBasicTypes.DOUBLE);
            query.addScalar("paymentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("creditAdjustmentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("debitAdjustmentAmount", StandardBasicTypes.DOUBLE);
            query.addScalar("balances", StandardBasicTypes.DOUBLE);
            query.addScalar("unAppliedPayment", StandardBasicTypes.DOUBLE);
            query.addScalar("unAppliedAdjustment", StandardBasicTypes.DOUBLE);

            query.setResultTransformer(Transformers.aliasToBean(RequestorAccount.class));
            @SuppressWarnings("unchecked")
            List<RequestorAccount> requestorAccount = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

            //CR# 377439 - fix
            return (CollectionUtilities.isEmpty(requestorAccount) ? new RequestorAccount()
                                                                  : requestorAccount.get(0));

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * Retrieves the requestor balances based on the aging
     *
     * @param requestorId - requestorId to whom the balances is to be retrieved
     *
     * @return - RequestorAging
     */
    @Override
    public RequestorAging retrieveRequestorAgingBalances(long requestorId) {


        final String logSM = "retrieveRequestorAgingBalances(requestorId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestorId" + requestorId);
        }
        try {

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestorAccountBalanceAging")
                    .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestorId", requestorId, StandardBasicTypes.LONG);

            query.addScalar("charges", StandardBasicTypes.DOUBLE);
            query.addScalar("aging", StandardBasicTypes.STRING);
            @SuppressWarnings("unchecked")
            List<Object[]> requestorAgingList = query.list();

            RequestorAging requestorAging = new RequestorAging();
            for (Object[] object : requestorAgingList) {

                Double balance = (Double) object[0];
                String aging = (String) object[1];
                requestorAging.setBalance(balance, aging);
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

            return requestorAging;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * @see com.mckesson.eig.roi.requestor.dao.RequestorStatementDAO#retrieveRequestorInfo(long)
     */
    @Override
    public RequestorStatementInfo retrieveRequestorInfo(long requestorId) {

        final String logSM = "retrieveRequestorInfo(requestorId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestorId" + requestorId);
        }
        try {

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery("retrieveRequestorDetails")
                                        .getQueryString();

            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestorId", requestorId, StandardBasicTypes.LONG);

            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("name", StandardBasicTypes.STRING);
            query.addScalar("requestorTypeName", StandardBasicTypes.STRING);
            query.addScalar("requestorTypeId", StandardBasicTypes.LONG);
            query.addScalar("cellPhone", StandardBasicTypes.STRING);
            query.addScalar("homePhone", StandardBasicTypes.STRING);
            query.addScalar("workPhone", StandardBasicTypes.STRING);
            query.addScalar("address1", StandardBasicTypes.STRING);
            query.addScalar("address2", StandardBasicTypes.STRING);
            query.addScalar("address3", StandardBasicTypes.STRING);
            query.addScalar("city", StandardBasicTypes.STRING);
            query.addScalar("state", StandardBasicTypes.STRING);
            query.addScalar("postalCode", StandardBasicTypes.STRING);
            query.addScalar("country", StandardBasicTypes.STRING);
            query.addScalar("countryCode", StandardBasicTypes.STRING);
            query.setResultTransformer(Transformers.aliasToBean(RequestorStatementInfo.class));

            @SuppressWarnings("unchecked")
            List<RequestorStatementInfo> requestorInfo = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

            return (CollectionUtilities.isEmpty(requestorInfo) ? null : requestorInfo.get(0));

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }

    }
}
