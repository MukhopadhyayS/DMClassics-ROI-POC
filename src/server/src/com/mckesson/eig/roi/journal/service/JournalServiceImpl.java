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

package com.mckesson.eig.roi.journal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.service.BaseROIService;
import com.mckesson.eig.roi.journal.dao.JournalDAO;
import com.mckesson.eig.roi.journal.model.JournalConstants.TransactionEvent;
import com.mckesson.eig.roi.journal.model.JournalConstants.TransactionType;
import com.mckesson.eig.roi.journal.model.JournalDetail;
import com.mckesson.eig.roi.journal.model.JournalEntry;
import com.mckesson.eig.roi.journal.model.JournalTemplate;
import com.mckesson.eig.roi.journal.model.JournalTransaction;
import com.mckesson.eig.roi.utils.SpringUtil;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;

public class JournalServiceImpl extends BaseROIService implements JournalService {
    private static final Log LOG = LogFactory.getLogger(JournalServiceImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private boolean createJournalEntries(TransactionEvent type,
            long transactionId) {
        List<JournalTemplate> templates = getJournalTemplateByTransactionType(type) ;
        if (!CollectionUtilities.isEmpty(templates)) {
            List<JournalDetail> details = generateJournalDetail(templates, type, transactionId);
            if (!CollectionUtilities.isEmpty(details)) {
                return insertJournals(details);
            }
        }
        return true;
    }
    
    @Override
    public boolean createSendInvoiceJE(long invoiceId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.SEND_INVOICE, invoiceId);
    }

   /* @Override
    public boolean createVoidInvoiceJE(long invoiceId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.VOID_INVOICE, invoiceId);
    }*/

    @Override
    public boolean createAcceptPaymentJE(long paymentId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.ACCEPT_PAYMENT, paymentId);
    }
    
    @Override
    public boolean createVoidPaymentJE(long paymentId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.VOID_PAYMENT, paymentId);
    }

//    @Override
//    public boolean createRecordReturnedCheckJE(long paymentId) {
//        // TODO Auto-generated method stub
//        return createJournalEntries(TransactionEvent.RECORD_RETURNED_CHECK,
//                paymentId);
//    }

    @Override
    public boolean createCreateAdjustmentJE(long adjustmentId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.CREATE_ADJUSTMENT,
                adjustmentId);
    }

    @Override
    public boolean createRecordCustomerGoodwillJE(long adjustmentId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.RECORD_CUSTOMER_GOODWILL,
                adjustmentId);
    }

    @Override
    public boolean createRecordBadDebtJE(long adjustmentId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.RECORD_BAD_DEBT,
                adjustmentId);
    }

    @Override
    public boolean createIssueRefundJE(long refundId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.CREATE_REFUND, refundId);
    }

/*  This is not implemented until latest release.
 *  @Override
    public boolean createPaySalesTaxJE(long taxId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.PAY_SALES_TAX, taxId);
    }
*/
    @Override
    public boolean createRemoveAdjustmentJE(long adjustmentId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.REMOVE_FEE_ADJUSTMENT, adjustmentId);
    }

    private List<JournalTemplate> getJournalTemplateByTransactionType(
            TransactionEvent type) {
      return getJournalTemplateService().getJournalTemplateByTransactionType(type);
    }

    private String getJournalTransactionTypeSQL(long TransactionTypeId) {
        return getJournalTemplateService().getJournalTransactionTypeSQL(TransactionTypeId);
    }

    private List<JournalDetail> generateJournalDetail(
            List<JournalTemplate> templates, TransactionEvent type,
            long transactionId) {
        if(!CollectionUtilities.isEmpty(templates)) {
            long journalId = createJournalEntryRecord(type, transactionId);
            List<JournalDetail> details = new ArrayList<JournalDetail>();
            Map<String, List<JournalTransaction>> map = new HashMap<String, List<JournalTransaction>>();
            for(JournalTemplate template : templates ) {
                List<JournalTransaction> transactions = getJournalTransactions(template, transactionId, map);
                if(!CollectionUtilities.isEmpty(transactions)) {
                    List<JournalDetail> journalDetails = createJournalDetail(template, transactions, transactionId, type, journalId);
                    if(!CollectionUtilities.isEmpty(journalDetails)) {
                        for(JournalDetail detail : journalDetails) {
                            details.add(detail);
                        }
                    }
                }
            }
            return details;
        }
        return null;
    }
    
    private long createJournalEntryRecord(TransactionEvent type, long transactionId) {
        final String logSM = "createJournalEntryRecord(type, transactionId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + type.eventCode() + ", " + transactionId);
        }
        try {
            long requestorId = getRequestorId(type, transactionId);
            JournalDAO dao = (JournalDAO) getDAO(DAOName.JOURNAL_DAO);
            JournalEntry entry = new JournalEntry(type.eventCode(), requestorId);
            long b = dao.insertJournalEntry(entry);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + b);
            }
            return b;
        } catch (ROIException e) {
            LOG.warn(e);
            throw e;
        } catch (Throwable e) {
            LOG.warn(e);
            throw new ROIException(e,
                    ROIClientErrorCodes.FAILED_TO_INSERT_JOURNAL_ENTRIES);
        }
    }
    
    private long getRequestorId(TransactionEvent type, long transactionId) {
        final String logSM = "getRequestorId(type, transactionId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + type.type().type() + ", " + transactionId);
        }
        try {
            JournalDAO dao = (JournalDAO) getDAO(DAOName.JOURNAL_DAO);
            long result = dao.getRequestorId(type, transactionId);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + result);
            }
            return result;
        } catch (ROIException e) {
            LOG.warn(e);
            throw e;
        } catch (Throwable e) {
            LOG.warn(e);
            throw new ROIException(e,
                    ROIClientErrorCodes.FAILED_TO_INSERT_JOURNAL_ENTRIES);
        }
    }

    private List<JournalTransaction> getJournalTransactions(JournalTemplate template, long transactionId, Map<String, List<JournalTransaction>> map) {
        final String logSM = "getJournalTransactions(template, transactionId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: Template" + template + ", transactionId" + transactionId);
        }
        try {
            long lineTypeId = template.getLineItemTypeId();
            String sqlName = getJournalTransactionTypeSQL(lineTypeId);
            if (map.containsKey(sqlName)) {
                return map.get(sqlName);
            }

            JournalDAO dao = (JournalDAO) getDAO(DAOName.JOURNAL_DAO);
            List<JournalTransaction> transactions = dao.getJournalTransactions(sqlName, transactionId);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + transactions);
            }
            map.put(sqlName, transactions);
            return transactions;
        } catch (ROIException e) {
            LOG.warn(e);
            throw e;
        } catch (Throwable e) {
            LOG.warn(e);
            throw new ROIException(e,
                    ROIClientErrorCodes.FAILED_TO_INSERT_JOURNAL_ENTRIES);
        }
    }
   
    private List<JournalDetail> createJournalDetail(JournalTemplate template, List<JournalTransaction> transactions, long transactionId, TransactionEvent event, long journalId) {
        if (!CollectionUtilities.isEmpty(transactions)) {
            List<JournalDetail> details = new ArrayList<JournalDetail>();
            for(JournalTransaction  transaction : transactions) {
                JournalDetail detail = null;
                if (event.type() == TransactionType.INVOICE) {
                    detail = JournalDetail.createByInvoiceId(template, transaction.getTransactionDate(), transaction.getAmount(), transactionId, journalId);
                } else if (event.type() == TransactionType.PAYMENT) {
                    detail = JournalDetail.createByPaymentId(template, transaction.getTransactionDate(), transaction.getAmount(), transactionId, journalId);
                } else if (event.type() == TransactionType.REFUND) {
                    detail = JournalDetail.createByRefundId(template, transaction.getTransactionDate(), transaction.getAmount(), transactionId, journalId);
                } else if (event.type() == TransactionType.ADJUSTMENT) {
                    detail = JournalDetail.createByAdjustmentId(template, transaction.getTransactionDate(), transaction.getAmount(), transactionId, journalId);
                } else if (event.type() == TransactionType.ADJUSTMENT_TO_INVOICE) {
                    detail = JournalDetail.createByAdjustmentToInvoiceId(template, transaction.getTransactionDate(), transaction.getAmount(), transactionId, journalId);
                } else if (event.type() == TransactionType.PAYMENT_TO_INVOICE) {
                    detail = JournalDetail.createByPaymentToInvoiceId(template, transaction.getTransactionDate(), transaction.getAmount(), transactionId, journalId);
                }
                details.add(detail);
            }
            return details;
        }
        return null;
    }      
    
   
    private boolean insertJournals(List<JournalDetail> details) {
        final String logSM = "insertJournals(details)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + details);
        }
        try {
            JournalDAO dao = (JournalDAO) getDAO(DAOName.JOURNAL_DAO);
            boolean b = dao.insertJournals(details);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + b);
            }
            return b;
        } catch (ROIException e) {
            LOG.warn(e);
            throw e;
        } catch (Throwable e) {
            LOG.warn(e);
            throw new ROIException(e,
                    ROIClientErrorCodes.FAILED_TO_INSERT_JOURNAL_ENTRIES);
        }
    }

    /**
     * Method to get the Dao information
     *
     * @return CcdProviderDAOImpl
     */
    private JournalTemplateService getJournalTemplateService() {
        return (JournalTemplateService) SpringUtil
                .getObjectFromSpring("com.mckesson.eig.roi.journal.service.JournalTemplateServiceImpl");
    }

    @Override
    public boolean createRemoveCustomerGoodwillJE(long adjustmentId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.REMOVE_CUSTOMER_GOODWILL, adjustmentId);
    }

    @Override
    public boolean createRemoveBadDebtJE(long adjustmentId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.REMOVE_BAD_DEBT, adjustmentId);
    }

    @Override
    public boolean createApplyAdjustmentToInvoiceJE(long adjustmentToInvoiceId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.APPLY_ADJUSTMENT_TO_INVOICE, adjustmentToInvoiceId);
    }

    @Override
    public boolean createUnapplyAdjustmentFromInvoiceJE(long adjustmentToInvoiceId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.UNAPPLY_ADJUSTMENT_FROM_INVOICE, adjustmentToInvoiceId);
    }

    @Override
    public boolean createApplyPaymentToInvoiceJE(long paymentToInvoiceId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.APPLY_PAYMENT_TO_INVOICE, paymentToInvoiceId);
    }

    @Override
    public boolean createUnApplyPaymentFromInvoiceJE(long paymentToInvoiceId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.UNAPPLY_PAYMENT_FROM_INVOICE, paymentToInvoiceId);
    }

    @Override
    public boolean createApplyBadDebtAdjustmentToInvoiceJE(
            long adjustmentToInvoiceId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.APPLY_BAD_DEBT_TO_INVOICE, adjustmentToInvoiceId);
    }

    @Override
    public boolean createUnapplyBadDebtAdjustmentFromInvoiceJE(
            long adjustmentToInvoiceId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.UNAPPLY_BAD_DEBT_FROM_INVOICE, adjustmentToInvoiceId);
    }

    @Override
    public boolean createApplyGoodWillAdjustmentToInvoiceJE(
            long adjustmentToInvoiceId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.APPLY_CUSTOMER_GOODWILL_TO_INVOICE, adjustmentToInvoiceId);
    }

    @Override
    public boolean createUnapplyGoodWillAdjustmentFromInvoiceJE(
            long adjustmentToInvoiceId) {
        // TODO Auto-generated method stub
        return createJournalEntries(TransactionEvent.UNAPPLY_CUSTOMER_GOODWILL_FROM_INVOICE, adjustmentToInvoiceId);
    }

}
