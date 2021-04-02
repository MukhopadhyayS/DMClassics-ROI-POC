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

import java.util.List;

import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.billing.model.RequestorLetter;
import com.mckesson.eig.roi.billing.model.SearchPastDueInvoiceCriteria;
import com.mckesson.eig.roi.billing.model.SearchPastDueInvoiceResult;

/**
 * @author Karthik Easwaran
 * @date   Aug 28, 2012
 * @since  Aug 28, 2012
 */
public interface OverDueInvoiceCoreDAO 
extends ROIDAO {

    /**
     * Search the overdue invoices based on the given search criteria
     * The Overdue invoices includes the list of invoices whose overDue date is completed
     * and till the invoice contains some amount to be paid
     *  
     * @param criteria
     * @return list of overdue invoices
     */
    SearchPastDueInvoiceResult searchOverDueInvoices(SearchPastDueInvoiceCriteria criteria);
    
    /**
     * this method saves the list of requestor letter into the database.
     *   
     * @param reqLetter
     * @return list of requestor Letters with updated requestor letterId
     */
    List<RequestorLetter> createRequestorLetter(List<RequestorLetter> reqLetter);
    
    /**
     * This method retrieves the requestor letter model from the database
     * @param requestorLetterId
     * @return RequestorLetters
     */
    RequestorLetter retrieveRequestorLetter(long requestorLetterId);
    
}
