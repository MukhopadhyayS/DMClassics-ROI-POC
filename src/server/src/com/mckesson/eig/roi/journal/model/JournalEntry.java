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

import com.mckesson.eig.roi.base.model.BaseModel;

public class JournalEntry  extends BaseModel {
    
    private long _journalEntryId;
    private long _transactionEventId;
    private long _requestorId;
    
    public JournalEntry(long transactionEventId, long requestorId) {
        _requestorId = requestorId;
        _transactionEventId = transactionEventId;
    }
    
    public JournalEntry() {
        
    }
    
   public long getJournalEntryId() {
        return _journalEntryId;
    }
    
    public void setJournalEntryId(long journalEntryId) {
        _journalEntryId = journalEntryId;
    }

    public long getTransactionEventId() {
        return _transactionEventId;
    }

    public void setTransactionEventId(long transactionEventId) {
        _transactionEventId = transactionEventId;
    }

    public long getRequestorId() {
        return _requestorId;
    }

    public void setRequestorId(long requestorId) {
        _requestorId = requestorId;
    }

}
