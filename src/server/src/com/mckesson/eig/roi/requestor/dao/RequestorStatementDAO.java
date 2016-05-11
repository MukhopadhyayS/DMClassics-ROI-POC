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

import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.billing.model.PastInvoice;
import com.mckesson.eig.roi.requestor.model.RequestorAccount;
import com.mckesson.eig.roi.requestor.model.RequestorAging;
import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria.DateRange;
import com.mckesson.eig.roi.requestor.model.RequestorStatementInfo;
import com.mckesson.eig.roi.requestor.model.RequestorTransaction;

/**
 * @author Karthik Easwaran
 * @date   Dec 20, 2012
 * @since  Dec 20, 2012
 */
public interface RequestorStatementDAO
extends ROIDAO {

    /**
     * retrieves the list of past invoices i.e regenerated invoices for the given requestorId
     *
     * @param requestorId
     * @return list of past invoices
     */
    List<PastInvoice> retrieveRequestorPastInvoices(long requestorId);

    /**
     * Retrieves the list of all transactions for the given requestor
     * and in between the given date range
     *
     * @param requestorId - requestorId to whom the transaction is to be retrieved
     * @param dateRange - the date renge where the transactoin is to retrieved
     *
     * @return - list of all transactions
     */
    RequestorAccount retrieveRequestorAccountBalances(long requestorId);

    /**
     * Retrieves the requestor account balances details
     *
     * @param requestorId - requestorId to whom the transaction is to be retrieved
     *
     * @return - RequestorAccount - Account Balances
     */
    List<RequestorTransaction> retrieveRequestorTransactions(long requestorId, DateRange dateRange);

    /**
     * Retrieves the requestor balances based on the aging
     *
     * @param requestorId - requestorId to whom the balances is to be retrieved
     *
     * @return - RequestorAging
     */
    RequestorAging retrieveRequestorAgingBalances(long requestorId);

    /**
     * retrieves the requestor information such as address, phone number details from the database
     * @param requestorId
     * @return requestor information
     *
     */
    RequestorStatementInfo retrieveRequestorInfo(long requestorId);

}
