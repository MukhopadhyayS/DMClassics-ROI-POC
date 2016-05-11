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

public class FinancialLineItemType extends BaseModel {
    private String _query;
    private String _name;
    private String _code;
    private long _txnTypeId;
    private long _entityId;

    public String getQuery() {
        return _query;
    }
    
    public void setQuery(String query) {
        _query = query;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getCode() {
        return _code;
    }

    public void setCode(String code) {
        _code = code;
    }

    public long getTxnTypeId() {
        return _txnTypeId;
    }

    public void setTxnTypeId(long txnTypeId) {
        _txnTypeId = txnTypeId;
    }

    public long getEntityId() {
        return _entityId;
    }

    public void setEntityId(long entityId) {
        _entityId = entityId;
    }

}
