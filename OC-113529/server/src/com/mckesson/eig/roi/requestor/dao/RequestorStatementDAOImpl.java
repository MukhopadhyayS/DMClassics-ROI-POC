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

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.billing.model.PastInvoice;
import com.mckesson.eig.roi.requestor.model.RequestorAccount;
import com.mckesson.eig.roi.requestor.model.RequestorAging;
import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria.DateRange;
import com.mckesson.eig.roi.requestor.model.RequestorStatementInfo;
import com.mckesson.eig.roi.requestor.model.RequestorTransaction;
import com.mckesson.eig.utility.util.CollectionUtilities;

/**
 * @author Karthik Easwaran
 * @date   Dec 20, 2012
 * @since  Dec 20, 2012
 */
public class RequestorStatementDAOImpl
extends ROIDAOImpl
implements RequestorStatementDAO {

    private static final OCLogger LOG = new OCLogger(RequestorStatementDAOImpl.class);
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

            Session session = getSession();
            String queryString =
                    session.getNamedQuery("retrieveRequestorPastInvoices").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestorId", requestorId, LongType.INSTANCE);

            query.addScalar("invoiceId", LongType.INSTANCE);
            query.addScalar("amount", DoubleType.INSTANCE);
            query.addScalar("createdDate", StringType.INSTANCE);

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

            Session session = getSession();

            boolean isAllTransactions = false;
            String queryName = "retrieveRequestorTransactions";
            if (DateRange.ALL.equals(dateRange)) {

                isAllTransactions = true;
                queryName = "retrieveAllRequestorTransactions";
            }

            String queryString = session.getNamedQuery(queryName).getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestorId", requestorId, LongType.INSTANCE);
            if (!isAllTransactions) {
                query.setParameter("dateRange", dateRange.getAgingDays(), IntegerType.INSTANCE);
            }

            query.addScalar("date", DateType.INSTANCE);
            query.addScalar("invoiceId", LongType.INSTANCE);
            query.addScalar("type", StringType.INSTANCE);
            query.addScalar("description", StringType.INSTANCE);
            query.addScalar("charges", DoubleType.INSTANCE);
            query.addScalar("balances", DoubleType.INSTANCE);
            // DE1560/CR# 384,396 - Fix
            query.addScalar("unbillable", BooleanType.INSTANCE);
            query.addScalar("adjustment", DoubleType.INSTANCE);
            query.addScalar("payment", DoubleType.INSTANCE);
            query.addScalar("refund", DoubleType.INSTANCE);
            query.addScalar("invoiceAmount", DoubleType.INSTANCE);

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

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestorAccountDetails")
                                        .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestorId", requestorId, LongType.INSTANCE);

            query.addScalar("charge", DoubleType.INSTANCE);
            query.addScalar("paymentAmount", DoubleType.INSTANCE);
            query.addScalar("creditAdjustmentAmount", DoubleType.INSTANCE);
            query.addScalar("debitAdjustmentAmount", DoubleType.INSTANCE);
            query.addScalar("balances", DoubleType.INSTANCE);
            query.addScalar("unAppliedPayment", DoubleType.INSTANCE);
            query.addScalar("unAppliedAdjustment", DoubleType.INSTANCE);

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

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestorAccountBalanceAging")
                    .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestorId", requestorId, LongType.INSTANCE);

            query.addScalar("charges", DoubleType.INSTANCE);
            query.addScalar("aging", StringType.INSTANCE);
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

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestorDetails")
                                        .getQueryString();

            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestorId", requestorId, LongType.INSTANCE);

            query.addScalar("id", LongType.INSTANCE);
            query.addScalar("name", StringType.INSTANCE);
            query.addScalar("requestorTypeName", StringType.INSTANCE);
            query.addScalar("requestorTypeId", LongType.INSTANCE);
            query.addScalar("cellPhone", StringType.INSTANCE);
            query.addScalar("homePhone", StringType.INSTANCE);
            query.addScalar("workPhone", StringType.INSTANCE);
            query.addScalar("address1", StringType.INSTANCE);
            query.addScalar("address2", StringType.INSTANCE);
            query.addScalar("address3", StringType.INSTANCE);
            query.addScalar("city", StringType.INSTANCE);
            query.addScalar("state", StringType.INSTANCE);
            query.addScalar("postalCode", StringType.INSTANCE);
            query.addScalar("country", StringType.INSTANCE);
            query.addScalar("countryCode", StringType.INSTANCE);
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
