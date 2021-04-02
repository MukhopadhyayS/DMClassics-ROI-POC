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

package com.mckesson.eig.roi.billing.service;

import com.mckesson.eig.roi.billing.model.InvoiceAndLetterInfo;
import com.mckesson.eig.roi.billing.model.OverDueDocInfoList;
import com.mckesson.eig.roi.billing.model.SearchPastDueInvoiceCriteria;
import com.mckesson.eig.roi.billing.model.SearchPastDueInvoiceResult;

/**
 * @author Karthik Easwaran
 * @date   Aug 28, 2012
 * @since  Aug 28, 2012
 */
public interface OverDueInvoiceCoreService {
    
    /**
     * searches the overdue invoices such that the list of invoices 
     * which has exceeded the overdue date based on the invoice criteria 
     * @param criteria
     * @return
     */
    SearchPastDueInvoiceResult searchOverDueInvoices(SearchPastDueInvoiceCriteria criteria);
    
    /**
     * This method preview the invoices based the requestor
     * @param invLetterInfo - information of invoices to be regenerated
     * @param forPreview - parameter for whether it is preview or regenerate
     *              if true - the invoices are prepared and not regenerated(not persisted in DB)
     *              if false - the invoices are regenerated
     * @return DocInfoList
     */
    OverDueDocInfoList regenerateInvoiceCoreAndLetter(InvoiceAndLetterInfo invLetterInfo, 
                                                  boolean forPreview);
    
    /**
     * This method generates the requestor letter/ regenerated Invoice PDF file from the given Ids  
     * @param id
     * @param fileType
     * @param type
     * @return file path of the generated PDF File
     */
    String regenerateLetter(long id, String fileType, String type);

}
