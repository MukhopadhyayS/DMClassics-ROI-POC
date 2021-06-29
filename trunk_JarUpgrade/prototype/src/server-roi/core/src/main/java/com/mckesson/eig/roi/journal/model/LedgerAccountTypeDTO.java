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
public class LedgerAccountTypeDTO {

    private String _code;
    private String _name;
    private long _id;
    private boolean _incStmtBalSheetFl;
    private boolean _normalBalFl;

    public String getCode() {
        return _code;
    }
    public void setCode(String code) {
        _code = code;
    }
    public String getName() {
        return _name;
    }
    public void setName(String name) {
        _name = name;
    }
    public long getId() {
        return _id;
    }
    public void setId(long id) {
        _id = id;
    }
    public boolean isIncStmtBalSheetFl() {
        return _incStmtBalSheetFl;
    }
    public void setIncStmtBalSheetFl(boolean incStmtBalSheetFl) {
        _incStmtBalSheetFl = incStmtBalSheetFl;
    }
    public boolean isNormalBalFl() {
        return _normalBalFl;
    }
    public void setNormalBalFl(boolean normalBalFl) {
        _normalBalFl = normalBalFl;
    }    
}
