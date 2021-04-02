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

public class JournalConstants {

    /**
     * Set of codes which describe the current run state of the OutputService
     */
    public static enum TransactionType {
        INVOICE(1),
        PAYMENT(2),
        ADJUSTMENT(3),
        REFUND(4),
        PAYMENT_TO_INVOICE(5),
        ADJUSTMENT_TO_INVOICE(6);

        private final int _type;

        TransactionType(int type) {
            _type = type;
        }

        public int type() {
            return _type;
        }
    }
    
    public static enum TransactionEvent {
        SEND_INVOICE("Send Invoice", -1, TransactionType.INVOICE),
//        VOID_INVOICE("Void Invoice", -2, TransactionType.INVOICE),
        ACCEPT_PAYMENT("Accept Payment", -3, TransactionType.PAYMENT),
        CREATE_ADJUSTMENT("Create Adjustment", -4, TransactionType.ADJUSTMENT),
        CREATE_REFUND("Issue Refund", -5, TransactionType.REFUND),
        VOID_PAYMENT("Void Payment", -6, TransactionType.PAYMENT),
        RECORD_RETURNED_CHECK("Record Returned Check", -7, TransactionType.INVOICE),
//      Not implement yet        
//        PAY_SALES_TAX("Pay Sales Tax", -8, TransactionType.INVOICE),
        RECORD_CUSTOMER_GOODWILL("Record Customer Goodwill", -9, TransactionType.ADJUSTMENT),
        RECORD_BAD_DEBT("Record Bad Debt", -10, TransactionType.ADJUSTMENT),
        REMOVE_FEE_ADJUSTMENT("Remove Fee Adjustment", -11, TransactionType.ADJUSTMENT),
        APPLY_ADJUSTMENT_TO_INVOICE("Apply Adjustment To Invoice", -12, TransactionType.ADJUSTMENT_TO_INVOICE),
        UNAPPLY_ADJUSTMENT_FROM_INVOICE("Unapply Adjustment From Invoice", -13, TransactionType.ADJUSTMENT_TO_INVOICE),
        APPLY_PAYMENT_TO_INVOICE("Apply Payment To Invoice", -14, TransactionType.PAYMENT_TO_INVOICE),
        UNAPPLY_PAYMENT_FROM_INVOICE("Unapply Payment From Invoice", -15, TransactionType.PAYMENT_TO_INVOICE),
        APPLY_CUSTOMER_GOODWILL_TO_INVOICE("Apply Customer Goodwill To Invoice", -16, TransactionType.ADJUSTMENT_TO_INVOICE),
        UNAPPLY_CUSTOMER_GOODWILL_FROM_INVOICE("Unapply Customer Goodwill From Invoice", -17, TransactionType.ADJUSTMENT_TO_INVOICE),
        APPLY_BAD_DEBT_TO_INVOICE("Apply Bad Debt To Invoice", -18, TransactionType.ADJUSTMENT_TO_INVOICE),
        UNAPPLY_BAD_DEBT_FROM_INVOICE("Unapply Bad Debt From Invoice", -19, TransactionType.ADJUSTMENT_TO_INVOICE),
        REMOVE_CUSTOMER_GOODWILL("Remove Customer Goodwill", -20, TransactionType.ADJUSTMENT),
        REMOVE_BAD_DEBT("Remove Bad Debt", -21, TransactionType.ADJUSTMENT),
        ACCEPT_PREBILL_PAYMENT("Accept Prebill Payment", -22, TransactionType.PAYMENT),
        REMOVE_PREBILL_PAYMENT("Remove Prebill Payment", -23, TransactionType.PAYMENT_TO_INVOICE),
        DELETE_PREBILL_PAYMENT("Delete Prebill Payment", -24, TransactionType.PAYMENT),
        APPLY_PAYMENT_TO_PREBILL("Apply Payment To Prebill", -25, TransactionType.PAYMENT_TO_INVOICE),
        UNAPPLY_PREBILL_PAYMENT("Unapply Payment From Prebill", -26, TransactionType.PAYMENT_TO_INVOICE);
        
        
        private final String _eventDescription;
        private final int _eventCode;
        private final TransactionType _type;

        TransactionEvent(String eventDescription, int eventCode, TransactionType type) {
            _eventDescription = eventDescription;
            _eventCode = eventCode;
            _type = type;
        }

        public String eventDescription() {
            return _eventDescription;
        }

        public int eventCode() {
            return _eventCode;
        }
        
        public TransactionType type() {
            return _type;
        }

    }
}
