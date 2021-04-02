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

package com.mckesson.eig.roi.journal.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.PlainSqlBatchProcessor;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.journal.model.JournalConstants.TransactionEvent;
import com.mckesson.eig.roi.journal.model.JournalConstants.TransactionType;
import com.mckesson.eig.roi.journal.model.JournalDetail;
import com.mckesson.eig.roi.journal.model.JournalEntry;
import com.mckesson.eig.roi.journal.model.JournalTransaction;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;

public class JournalDAOImpl extends ROIDAOImpl implements JournalDAO {

    private static final Log LOG = LogFactory.getLogger(JournalDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    @Override
    public boolean insertJournals(final List<JournalDetail> details) {
        final String logSM = "insertJournals(JournalDetails)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: " + details);
        }
        try {
            final Session session = getSessionFactory().getCurrentSession();

            PlainSqlBatchProcessor processor = new PlainSqlBatchProcessor() {

                @Override
                protected Session getSession() {
                    return session;
                }

                @Override
                protected long getBatchSize() {
                    return ROIConstants.SQL_BATCH_SIZE;
                }

                @Override
                protected <T extends BaseModel> void addToBatch(
                        PreparedStatement pStmt, T object) throws SQLException {

                    int index = 1;
                    JournalDetail detail = (JournalDetail) object;
                    pStmt.setLong(index++, detail.getTxnTypeId());
                    pStmt.setLong(index++, detail.getLineItemTypeId());
                    pStmt.setLong(index++, detail.getLedgerAccountId());
                    pStmt.setLong(index++, detail.getTemplateId());
                    pStmt.setBoolean(index++, detail.isCredit());
                    pStmt.setBoolean(index++, detail.isIncrease());
                    pStmt.setLong(index++, detail.getInvoiceId());
                    pStmt.setLong(index++, detail.getAdjustmentId());
                    pStmt.setLong(index++, detail.getPaymentId());
                    pStmt.setLong(index++, detail.getRefundId());
                    pStmt.setLong(index++, detail.getAdjustmentToInvoiceId());
                    pStmt.setLong(index++, detail.getPaymentToInvoiceId());
                    pStmt.setLong(index++, detail.getJournalId());
                    pStmt.setTimestamp(index++,
                            getSQLTimeStamp(detail.getTransactionTime()));
                    pStmt.setDouble(index++, detail.getAmount());
                }
            };

            String sql = session.getNamedQuery("insertJournalDetail")
                    .getQueryString();
            processor.execute(details, sql);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

            return true;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED);
        }

    }

    @Override
    public List<JournalTransaction> getJournalTransactions(String sqlName,
            long transactionId) {
        final String logSM = "getJournalTransactions(sqlName, transactionId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>sqlName: " + sqlName + ", transactionId:"
                    + transactionId);
        }

        try {

            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery(sqlName)
                    .getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(queryString);
            sqlQuery.setParameter("id", transactionId, StandardBasicTypes.LONG);
            sqlQuery.addScalar("transactionDate", StandardBasicTypes.TIMESTAMP);
            sqlQuery.addScalar("amount", StandardBasicTypes.DOUBLE);
            sqlQuery.setResultTransformer(Transformers
                    .aliasToBean(JournalTransaction.class));

            List<JournalTransaction> transactions = sqlQuery
                    .list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End" + transactions);
            }
            if (transactions == null) {
                return new ArrayList<JournalTransaction>();
            }
            return transactions;
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    @Override
    public long insertJournalEntry(JournalEntry entry) {
        final String logSM = "insertJournalEntry(JournalEntry)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + entry);
        }
        try {
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("insertJournalEntry");
            query.setParameter("requestorId", entry.getRequestorId(),
                    StandardBasicTypes.LONG);
            query.setParameter("txnEventId", entry.getTransactionEventId(),
                    StandardBasicTypes.LONG);
            List<BigDecimal> entryId = query.list();
            long result = 0;
            if (entryId != null && entryId.size() > 0) {
                result = entryId.get(0).longValue();
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:insertJournalEntry - JournalEntryId: "
                        + result);
            }

            return result;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION,
                    e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    @Override
    public long getRequestorId(TransactionEvent type, long transactionId) {
        final String logSM = "getRequestorId(type, transactionId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>sqlName: " + type.eventCode() + ", transactionId:"
                    + transactionId);
        }

        try {
            String sql = getRequestorIdSQL(type);
            Session session = getSessionFactory().getCurrentSession();
            String queryString = session.getNamedQuery(sql).getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(queryString);
            sqlQuery.setParameter("transactionId", transactionId, StandardBasicTypes.LONG);
            List<Integer> requestorIds = sqlQuery.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End" + requestorIds);
            }
            if (CollectionUtilities.isEmpty(requestorIds)) {
                return -1;
            }
            return requestorIds.get(0).longValue();
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    private String getRequestorIdSQL(TransactionEvent event) {
        if (event.type() == TransactionType.INVOICE) {
            return "getRequestorIdByInvoice";
        } else if (event.type() == TransactionType.PAYMENT) {
            return "getRequestorIdByPayment";
        } else if (event.type() == TransactionType.REFUND) {
            return "getRequestorIdByRefund";
        } else if (event.type() == TransactionType.ADJUSTMENT) {
            return "getRequestorIdByAdjustment";
        } else if (event.type() == TransactionType.ADJUSTMENT_TO_INVOICE) {
            return "getRequestorIdByAdjustmentToInvoice";
        } else if (event.type() == TransactionType.PAYMENT_TO_INVOICE) {
            return "getRequestorIdByPaymentToInvoice";
        }
        return "";
    }

}
