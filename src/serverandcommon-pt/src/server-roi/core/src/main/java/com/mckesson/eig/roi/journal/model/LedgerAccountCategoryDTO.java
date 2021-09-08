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

import java.util.List;

/**
 * 
 * @author edksi0l
 *
 */
public class LedgerAccountCategoryDTO {

    private long _id;
    private String _name;
    private List<LedgerAccountDTO> _ledgerAccounts;
    
    public long getId() {
        return _id;
    }
    public void setId(long id) {
        _id = id;
    }
    public String getName() {
        return _name;
    }
    public void set_name(String name) {
        _name = name;
    }
    public List<LedgerAccountDTO> getLedgerAccounts() {
        return _ledgerAccounts;
    }
    public void setLedgerAccounts(List<LedgerAccountDTO> ledgerAccounts) {
        _ledgerAccounts = ledgerAccounts;
    }
    
}
