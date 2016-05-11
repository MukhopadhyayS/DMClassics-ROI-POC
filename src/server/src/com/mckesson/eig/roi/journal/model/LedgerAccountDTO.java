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
public class LedgerAccountDTO {

    private long _balance;
    private String _code;
    private long _id;
    private long _liquidityOrder;
    private String _name;
    
    public long getBalance() {
        return _balance;
    }
    public void setBalance(long balance) {
        _balance = balance;
    }
    public String getCode() {
        return _code;
    }
    public void setCode(String code) {
        _code = code;
    }
    public long getId() {
        return _id;
    }
    public void setId(long id) {
        _id = id;
    }
    public long getLiquidityOrder() {
        return _liquidityOrder;
    }
    public void setLiquidityOrder(long liquidityOrder) {
        _liquidityOrder = liquidityOrder;
    }
    public String getName() {
        return _name;
    }
    public void setName(String name) {
        _name = name;
    }    
}
