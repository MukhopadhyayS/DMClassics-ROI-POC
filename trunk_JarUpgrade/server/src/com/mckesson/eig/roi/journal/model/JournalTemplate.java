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

public class JournalTemplate extends BaseModel {
    private long _txnTypeId;
    private long _lineItemTypeId;
    private long _ledgerAccountId;
    private boolean _credit;
    private boolean _increase;
    private boolean _active;
    
    public long getTxnTypeId() {
        return _txnTypeId;
    }

    public void setTxnTypeId(long txnTypeId) {
        _txnTypeId = txnTypeId;
    }

    public long getLineItemTypeId() {
        return _lineItemTypeId;
    }

    public void setLineItemTypeId(long lineItemTypeId) {
        _lineItemTypeId = lineItemTypeId;
    }

    public long getLedgerAccountId() {
        return _ledgerAccountId;
    }

    public void setLedgerAccountId(long ledgerAccountId) {
        _ledgerAccountId = ledgerAccountId;
    }

    public boolean isCredit() {
        return _credit;
    }

    public void setCredit(boolean credit) {
        _credit = credit;
    }

    public boolean isIncrease() {
        return _increase;
    }

    public void setIncrease(boolean increase) {
        _increase = increase;
    }

    public boolean isActive() {
        return _active;
    }

    public void setActive(boolean active) {
        _active = active;
    }
}
