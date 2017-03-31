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
package com.mckesson.eig.roi.billing.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.BaseLetterDataRetriever;
import com.mckesson.eig.roi.billing.letter.model.LetterData;
import com.mckesson.eig.roi.billing.letter.model.Note;
import com.mckesson.eig.roi.billing.letter.model.RequestorAccount;
import com.mckesson.eig.roi.billing.letter.model.RequestorAging;
import com.mckesson.eig.roi.billing.letter.model.RequestorInfo;
import com.mckesson.eig.roi.billing.letter.model.RequestorTransaction;
import com.mckesson.eig.roi.billing.model.RequestorLetter;
import com.mckesson.eig.roi.requestor.dao.RequestorStatementDAO;
import com.mckesson.eig.roi.requestor.model.RequestorStatementInfo;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;

/**
*
* @author rajeshkumarg
* @date   Nov 10, 2011
* @since  HPF 15.1 [ROI]; Nov 10, 2011
*/
public class RequestorLetterDataRetriever
extends BaseLetterDataRetriever {

    private static final Log LOG = LogFactory.getLogger(RequestorLetterDataRetriever.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     *  This method is to retrieve the data required to generate requestor letter
     *  for the given requestor letter id
     *
     */
    @Override
    public Object retrieveLetterData(long requestorLetterId, long requestId) {

        final String logSM = "retrieveLetterData(RequestorLetterId, RequestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: RequestorLetterId:" + requestorLetterId
                            + ", RequestId:" + requestId);
        }

        RequestorStatementInfo requestorLetter = retrieveRequestorLetterDetails(requestorLetterId);
        Object templateData = constructTemplateDataModel(requestorLetter);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End");
        }

        return templateData;

    }

    /**
     * This method is to retrieve the requestor letter details for the given requestor letter id
     * @param invoiceId
     * @return Object[]
     */
    private RequestorStatementInfo retrieveRequestorLetterDetails(long requestorLetterId) {

        final String logSM = "retrieveRequestorLetterDetails(requestorLetterId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: Requestor Letter Id : " + requestorLetterId);
        }
        try {

            OverDueInvoiceCoreDAO overDueDao =
                                (OverDueInvoiceCoreDAO) getDAO("OverDueInvoiceCoreDAO");

            RequestorLetter letter = overDueDao.retrieveRequestorLetter(requestorLetterId);

            RequestorStatementDAO dao = getRequestorStatementDAO();
            long requestorId = letter.getRequestorId();

            List<com.mckesson.eig.roi.requestor.model.RequestorTransaction> requestorTransactions =
                        dao.retrieveRequestorTransactions(requestorId,
                                                         letter.getDateRange());

            com.mckesson.eig.roi.requestor.model.RequestorAccount accountBalances =
                                       new com.mckesson.eig.roi.requestor.model.RequestorAccount();
            accountBalances.setCharge(letter.getCharges());
            accountBalances.setPaymentAmount(letter.getPaymentAmount());
            accountBalances.setUnAppliedAdjustment(letter.getUnAppliedAdjustment());
            accountBalances.setUnAppliedPayment(letter.getUnAppliedPayment());
            accountBalances.setAdjustmentAmount(letter.getAdjustmentAmount());
            accountBalances.setBalances(letter.getBalances());

            com.mckesson.eig.roi.requestor.model.RequestorAging agingBalances =
                                         new com.mckesson.eig.roi.requestor.model.RequestorAging();
            agingBalances.setBalance30(letter.getBalance30());
            agingBalances.setBalance60(letter.getBalance60());
            agingBalances.setBalance90(letter.getBalance90());
            agingBalances.setBalanceOther(letter.getBalanceOther());


            RequestorStatementInfo requestorInfo = new RequestorStatementInfo();

            requestorInfo.setNotes(letter.getNotes());
            requestorInfo.setTemplateFileId(letter.getRequestTemplateId());
            requestorInfo.setTemplateName(letter.getTemplateName());
            requestorInfo.setOutputMethod(letter.getOutputMethod());
            requestorInfo.setQueuePassword(letter.getQueuePassword());
            requestorInfo.setResendDate(letter.getResendDate());
            requestorInfo.setCreatedDate(letter.getCreatedDate());

            requestorInfo.setAddress1(letter.getRequestorAddress1());
            requestorInfo.setAddress2(letter.getRequestorAddress2());
            requestorInfo.setAddress3(letter.getRequestorAddress3());
            requestorInfo.setCity(letter.getRequestorCity());
            requestorInfo.setState(letter.getRequestorState());
            requestorInfo.setCountry(letter.getRequestorCountry());
            requestorInfo.setId(letter.getRequestorId());
            requestorInfo.setName(letter.getRequestorName());
            requestorInfo.setHomePhone(letter.getRequestorPhone());
            requestorInfo.setWorkPhone(letter.getRequestorPhone());
            requestorInfo.setPostalCode(letter.getRequestorPostalCode());

            requestorInfo.setRequestorAccount(accountBalances);
            requestorInfo.setRequestorAging(agingBalances);
            requestorInfo.setTransactions(requestorTransactions);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

            return requestorInfo;
        } catch (Exception ex) {
            throw new ROIException(ROIClientErrorCodes.GENERATE_REQUESTOR_LETTER_FAILED);
        }
    }

    /**
     * Constructs the requestorletters object for the RTF from the persistent model
     * @param data
     * @return
     */
    @Override
    public Object constructTemplateDataModel(Object data) {

        RequestorStatementInfo reqStmt = (RequestorStatementInfo) data;
        LetterData letterData = new LetterData();

        letterData.setTemplateName(reqStmt.getTemplateName());
        letterData.setTemplateFileId(String.valueOf(reqStmt.getTemplateFileId()));
        List<String> notes = reqStmt.getNotes();
        if (CollectionUtilities.hasContent(notes)) {

            List<Note> notesList = new ArrayList<Note>();
            Note note;
            for (String noteString : notes) {

                note = new Note();
                note.setDescription(noteString);
                notesList.add(note);
            }

            List<Note> templateNotes = new ArrayList<Note>();
            Note tNote = new Note();
            tNote.setChildItems(notesList);
            templateNotes.add(tNote);
            letterData.setNotesList(templateNotes);
        }

        letterData.setOutputMethod(reqStmt.getOutputMethod());
        letterData.setQueuePassword(reqStmt.getQueuePassword());

        Date resendDate = reqStmt.getResendDate();
        if (null != resendDate) {

            String date = ROIConstants.DATETIME_FORMATTER.format(resendDate);
            letterData.setResendDate(date);
        }

        RequestorInfo requestorInfo = new RequestorInfo();
        requestorInfo.setId(String.valueOf(reqStmt.getId()));
        requestorInfo.setName(reqStmt.getName());
        requestorInfo.setPhone(reqStmt.getPhone());
        requestorInfo.setCellPhone(reqStmt.getCellPhone());
        requestorInfo.setHomePhone(reqStmt.getHomePhone());
        requestorInfo.setWorkPhone(reqStmt.getWorkPhone());
        requestorInfo.setType(reqStmt.getRequestorTypeName());
        requestorInfo.setAddress1(reqStmt.getAddress1());
        requestorInfo.setAddress2(reqStmt.getAddress2());
        requestorInfo.setAddress3(reqStmt.getAddress3());
        requestorInfo.setCity(reqStmt.getCity());
        requestorInfo.setState(reqStmt.getState());
        requestorInfo.setCountry(reqStmt.getCountry());
        requestorInfo.setCountryCode(reqStmt.getCountryCode());
        requestorInfo.setPostalCode(reqStmt.getPostalCode());
        
        // DE1560/CR# 384,396 - Fix
        Date createdDate = reqStmt.getCreatedDate();
        if (null == createdDate) {
            createdDate = Calendar.getInstance().getTime();
        }
        requestorInfo.setCreatedDate(ROIConstants.DATE_FORMATTER.format(createdDate));

        com.mckesson.eig.roi.requestor.model.RequestorAging aging = reqStmt.getRequestorAging();
        RequestorAging requestorAging = new RequestorAging();

        if (null != aging) {

            requestorAging.setBalance30(formatToCurrency(aging.getBalance30()));
            requestorAging.setBalance60(formatToCurrency(aging.getBalance60()));
            requestorAging.setBalance90(formatToCurrency(aging.getBalance90()));
            requestorAging.setBalanceOther(formatToCurrency(aging.getBalanceOther()));
        }
        requestorInfo.setAging(requestorAging);

        com.mckesson.eig.roi.requestor.model.RequestorAccount account =
                                                                reqStmt.getRequestorAccount();

        RequestorAccount requestorAccount = new RequestorAccount();

        double unappliedAmount = 0.00;
        if (null != account) {

            unappliedAmount = account.getUnAppliedAmount();
            
            requestorAccount.setBalances(formatToCurrency(account.getBalances()));
            requestorAccount.setCharge(formatToCurrency(account.getCharge()));
            requestorAccount.setPaymentAmount(formatToCurrency(toNegative(account.getPaymentAmount())));
            requestorAccount.setDebitAdjustmentAmount(
                                            formatToCurrency(account.getDebitAdjustmentAmount()));

            requestorAccount.setAdjustmentAmount(formatToCurrency(
                                                        toNegative(account.getAdjustmentAmount())));
            requestorAccount.setCreditAdjustmentAmount(
                                   formatToCurrency(toNegative(account.getCreditAdjustmentAmount())));
            requestorAccount.setUnAppliedAdjustment(
                                   formatToCurrency(toNegative(account.getUnAppliedAdjustment())));
            requestorAccount.setUnAppliedAmount(
                                    formatToCurrency(toNegative(account.getUnAppliedAmount())));
            requestorAccount.setUnAppliedPayment(
                                    formatToCurrency(toNegative(account.getUnAppliedPayment())));

        }
        requestorInfo.setAccount(requestorAccount);

        List<com.mckesson.eig.roi.requestor.model.RequestorTransaction> txns =
                                                                reqStmt.getTransactions();

        List<RequestorTransaction> txnsList = new ArrayList<RequestorTransaction>();
        if (CollectionUtilities.hasContent(txns)) {

            double broughtForwardBal = (account.getBalances() + reqStmt.getAdjustmentPaymentTotal() 
                                        - reqStmt.getRefundAmountTotal() - reqStmt.getInvoiceAmountTotal()
                                        - unappliedAmount);

            RequestorTransaction transactions = new RequestorTransaction();
            transactions.setDescription("Balance Brought Forward");
            transactions.setDate(ROIConstants.DATE_FORMATTER.format(new Date()));
            transactions.setBalances(formatToCurrency(broughtForwardBal));
            txnsList.add(transactions);

            for (com.mckesson.eig.roi.requestor.model.RequestorTransaction txn : txns) {

                transactions = new RequestorTransaction();
                transactions.setType(txn.getType());
                transactions.setDescription(txn.getDescription());

                transactions.setAmount(formatAmount(txn.getInvoiceAmount(), false));
                transactions.setCharges(formatAmount(txn.getCharges(), false));
                transactions.setAdjPay(formatAmount(txn.getAdjPay(), true));
                transactions.setAdjustment(formatAmount(txn.getAdjustment(), true));
                transactions.setPayment(formatAmount(txn.getPayment(), true));
                transactions.setRefund(formatAmount(txn.getRefund(), false));
                transactions.setDate(ROIConstants.DATE_FORMATTER.format(txn.getDate()));

                // DE1560/CR# 384,396 - Fix
                transactions.setUnBillable(txn.isUnbillable() ? ROIConstants.UNBILLABLE : "");
                // CR # 375,537 Fix
                if (ROIConstants.INVOICE.equalsIgnoreCase(txn.getType()) && !txn.isUnbillable()) {

                    broughtForwardBal += txn.getCharges();

                } else if (ROIConstants.PAYMENT_TYPE.equalsIgnoreCase(txn.getType())
                            || ROIConstants.ADJUSTMENT_TYPE.equalsIgnoreCase(txn.getType())) {

                    broughtForwardBal -= txn.getCharges();
                } else if (ROIConstants.REFUND_TYPE.equalsIgnoreCase(txn.getType())) {
                    // CR# 375,537 FIX
                    broughtForwardBal += txn.getCharges();
                    transactions.setAdjPay(formatAmount(txn.getRefund(), false));
                }

                transactions.setBalances(formatToCurrency(broughtForwardBal));
                txnsList.add(transactions);
            }
            requestorInfo.setTransactions(txnsList);
        }

        letterData.setRequestor(requestorInfo);

        return letterData;
    }

    /**
     * format the given amount to the currency if the double value is 0 or null,
     * it return as null string
     *
     * @param amount
     * @param isnegative
     * @return formatted currency value
     */
    private String formatAmount(Double amount, boolean isnegative) {

        if (null == amount || amount.doubleValue() == 0) {
            return null;
        }

        if (isnegative) {
            amount = toNegative(amount);
        }
        return formatToCurrency(amount);
    }

}
