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

package com.mckesson.eig.roi.journal.model;

import java.util.Date;

public class JournalDetail extends JournalTemplate {
    private long _journalId;
    private long _templateId;
    private double _amount;
    private Date _transactionTime;
    private long _invoiceId;
    private long _adjustmentId;
    private long _paymentId;
    private long _refundId;
    private long _adjustmentToInvoiceId;
    private long _paymentToInvoiceId;

    public JournalDetail() {
    }

    private JournalDetail(JournalTemplate template) {
        setTemplateId(template.getId());
        setTxnTypeId(template.getTxnTypeId());
        setLineItemTypeId(template.getLineItemTypeId());
        setLedgerAccountId(template.getLedgerAccountId());
        setCredit(template.isCredit());
        setIncrease(template.isIncrease());
    }

    public long getJournalId() {
        return _journalId;
    }

    public void setJournalId(long journalId) {
        _journalId = journalId;
    }

    public long getTemplateId() {
        return _templateId;
    }

    public void setTemplateId(long templateId) {
        _templateId = templateId;
    }

    public double getAmount() {
        return _amount;
    }

    public void setAmount(double amount) {
        _amount = amount;
    }

    public Date getTransactionTime() {
        return _transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        _transactionTime = transactionTime;
    }

    public long getInvoiceId() {
        return _invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        _invoiceId = invoiceId;
    }

    public long getAdjustmentId() {
        return _adjustmentId;
    }

    public void setAdjustmentId(long adjustmentId) {
        _adjustmentId = adjustmentId;
    }

    public long getPaymentId() {
        return _paymentId;
    }

    public void setPaymentId(long paymentId) {
        _paymentId = paymentId;
    }

    public long getRefundId() {
        return _refundId;
    }

    public void setRefundId(long refundId) {
        _refundId = refundId;
    }

    public long getAdjustmentToInvoiceId() {
        return _adjustmentToInvoiceId;
    }

    public void setAdjustmentToInvoiceId(long adjustmentToInvoiceId) {
        _adjustmentToInvoiceId = adjustmentToInvoiceId;
    }

    public long getPaymentToInvoiceId() {
        return _paymentToInvoiceId;
    }

    public void setPaymentToInvoiceId(long paymentToInvoiceId) {
        _paymentToInvoiceId = paymentToInvoiceId;
    }

    private static JournalDetail createJournalDetail(JournalTemplate template, Date transactionTime, double amount, long journalId) {
        JournalDetail detail = new JournalDetail(template);
        detail.setAmount(amount);
        detail.setTransactionTime(transactionTime);
        detail.setJournalId(journalId);
        return detail;

    }

    public static JournalDetail createByInvoiceId(JournalTemplate template,
            Date transactionTime, double amount, long invoiceId, long journalId) {
        JournalDetail detail = createJournalDetail(template, transactionTime,
                amount, journalId);
        detail.setInvoiceId(invoiceId);
        return detail;
    }

    public static JournalDetail createByPaymentId(JournalTemplate template,
            Date transactionTime, double amount, long paymentId, long journalId) {
        JournalDetail detail = createJournalDetail(template, transactionTime,
                amount, journalId);
        detail.setPaymentId(paymentId);
        return detail;
    }

    public static JournalDetail createByAdjustmentId(JournalTemplate template,
            Date transactionTime, double amount, long adjustmentId,
            long journalId) {
        JournalDetail detail = createJournalDetail(template, transactionTime,
                amount, journalId);
        detail.setAdjustmentId(adjustmentId);
        return detail;
    }

    public static JournalDetail createByRefundId(JournalTemplate template,
            Date transactionTime, double amount, long refundId, long journalId) {
        JournalDetail detail = createJournalDetail(template, transactionTime,
                amount, journalId);
        detail.setRefundId(refundId);
        return detail;
    }

    public static JournalDetail createByAdjustmentToInvoiceId(
            JournalTemplate template, Date transactionTime, double amount,
            long aAdjustmentToInvoiceId, long journalId) {
        JournalDetail detail = createJournalDetail(template, transactionTime,
                amount, journalId);
        detail.setAdjustmentToInvoiceId(aAdjustmentToInvoiceId);
        return detail;
    }

    public static JournalDetail createByPaymentToInvoiceId(
            JournalTemplate template, Date transactionTime, double amount,
            long paymentToInvoiceId, long journalId) {
        JournalDetail detail = createJournalDetail(template, transactionTime,
                amount, journalId);
        detail.setPaymentToInvoiceId(paymentToInvoiceId);
        return detail;
    }
}
