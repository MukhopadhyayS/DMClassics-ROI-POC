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

package com.mckesson.eig.roi.billing.xdocreport.model;

import java.io.Serializable;

import com.mckesson.eig.roi.billing.letter.model.RequestorAging;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author Karthik Easwaran
 * @date   Dec 17, 2012
 * @since  Dec 17, 2012
 */
public class XDocRequestorAging
implements Serializable {

    private static final long serialVersionUID = 1L;

    private String _balance30 = StringUtilities.EMPTYSTRING;
    private String _balance60 = StringUtilities.EMPTYSTRING;
    private String _balance90 = StringUtilities.EMPTYSTRING;
    private String _balanceOther = StringUtilities.EMPTYSTRING;
    
    public XDocRequestorAging(RequestorAging aging) {
        
        if (null == aging) {
            return;
        }
        
        setBalance30(StringUtilities.safe(aging.getBalance30()));
        setBalance60(StringUtilities.safe(aging.getBalance60()));
        setBalance90(StringUtilities.safe(aging.getBalance90()));
        setBalanceOther(StringUtilities.safe(aging.getBalanceOther()));
    }

    public String getBalance30() { return _balance30; }
    public void setBalance30(String balance30) { _balance30 = balance30; }

    public String getBalance60() { return _balance60; }
    public void setBalance60(String balance60) { _balance60 = balance60; }

    public String getBalance90() { return _balance90; }
    public void setBalance90(String balance90) { _balance90 = balance90; }

    public String getBalanceOther() { return _balanceOther; }
    public void setBalanceOther(String balanceOther) { _balanceOther = balanceOther; }

}
