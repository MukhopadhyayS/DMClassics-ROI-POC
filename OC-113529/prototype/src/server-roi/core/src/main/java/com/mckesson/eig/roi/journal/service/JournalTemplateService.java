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

import java.util.List;

import com.mckesson.eig.roi.journal.model.JournalTemplate;
import com.mckesson.eig.roi.journal.model.JournalConstants.TransactionEvent;

public interface JournalTemplateService {
    
    void init();

//    public List<LedgerAccountTypeDTO> getAccountTypes(boolean incomeStmtBalSheetFl);
    
//    public List<LineItemDTO> getAccountLineItemsBasedOnJournalEntries(long accountSeq, boolean debitCreditSideFl, boolean increaseDecreaseFl);
    
//    public long getFinancialEntityId(String code);
    
//    public long getFinancialTxnTypeId(String code);
    String getJournalTransactionTypeSQL(long TransactionTypeId);
    List<JournalTemplate> getJournalTemplateByTransactionType(TransactionEvent type);
    
//    public long getLedgerAccountCategoryId(String categoryId);
    
//    public long getLedgerAccountId(String accountId);
    
//    public long getLedgerAccountTypeId(String ledgerAccountTypeCode);

}
