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

/*
 * The transaction of this service need to be provider by the caller
 */

public interface JournalService {
    boolean createSendInvoiceJE(long invoiceId);
    //boolean createVoidInvoiceJE(long invoiceId);  - not implemented
    boolean createAcceptPaymentJE(long paymentId);
    boolean createVoidPaymentJE(long paymentId);
    //boolean createRecordReturnedCheckJE(long paymentId); - not implemented
    boolean createCreateAdjustmentJE(long adjustmentId);
    boolean createRemoveAdjustmentJE(long adjustmentId);
    boolean createRecordCustomerGoodwillJE(long adjustmentId);
    boolean createRecordBadDebtJE(long adjustmentId);
    boolean createRemoveCustomerGoodwillJE(long adjustmentId);
    boolean createRemoveBadDebtJE(long adjustmentId);
    boolean createIssueRefundJE(long refundId);
    // boolean createPaySalesTaxJE(long taxId); - not implemented
    
    boolean createApplyPaymentToInvoiceJE(long paymentToInvoiceId);
    boolean createUnApplyPaymentFromInvoiceJE(long paymentToInvoiceId);
    boolean createApplyAdjustmentToInvoiceJE(long adjustmentToInvoiceId);
    boolean createUnapplyAdjustmentFromInvoiceJE(long adjustmentToInvoiceId);
    boolean createApplyBadDebtAdjustmentToInvoiceJE(long adjustmentToInvoiceId);
    boolean createUnapplyBadDebtAdjustmentFromInvoiceJE(long adjustmentToInvoiceId);
    boolean createApplyGoodWillAdjustmentToInvoiceJE(long adjustmentToInvoiceId);
    boolean createUnapplyGoodWillAdjustmentFromInvoiceJE(long adjustmentToInvoiceId);

}
