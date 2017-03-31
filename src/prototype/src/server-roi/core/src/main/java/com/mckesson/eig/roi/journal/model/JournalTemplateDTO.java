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

/**
 * 
 * @author edksi0l
 *
 */
public class JournalTemplateDTO {

    private String _creditDebitFl;
    private String _incDecFl;
    private long _ledgerAccount;
    private long _lineItemType; 

    public String getCreditDebitFl() {
        return _creditDebitFl;
    }
    public void setCreditDebitFl(String creditDebitFl) {
        _creditDebitFl = creditDebitFl;
    }
    public String getIncDecFl() {
        return _incDecFl;
    }
    public void setIncDecFl(String incDecFl) {
        _incDecFl = incDecFl;
    }
    public long getLedgerAccount() {
        return _ledgerAccount;
    }
    public void setLedgerAccount(long ledgerAccount) {
        _ledgerAccount = ledgerAccount;
    }
    public long getLineItemType() {
        return _lineItemType;
    }
    public void setLineItemType(long lineItemType) {
        _lineItemType = lineItemType;
    }
    
}
