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

import java.util.List;

import com.mckesson.eig.roi.journal.model.JournalDetail;
import com.mckesson.eig.roi.journal.model.JournalEntry;
import com.mckesson.eig.roi.journal.model.JournalTransaction;
import com.mckesson.eig.roi.journal.model.JournalConstants.TransactionEvent;

public interface JournalDAO {
    boolean insertJournals(List<JournalDetail> details);
    List<JournalTransaction> getJournalTransactions(String amountSql, long transactionId);
    long insertJournalEntry(JournalEntry entry);
    long getRequestorId(TransactionEvent type, long transactionId);
}
