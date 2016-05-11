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

package com.mckesson.eig.roi.requestor.model;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;

/**
 * @author Karthik Easwaran
 * @date   Dec 17, 2012
 * @since  Dec 17, 2012
 */
public class RequestorAging
implements Serializable {

    private static final long serialVersionUID = 1L;

    private double _balance30;
    private double _balance60;
    private double _balance90;
    private double _balanceOther;

    public double getBalance30() { return _balance30; }
    public void setBalance30(double balance30) { _balance30 = balance30; }

    public double getBalance60() { return _balance60; }
    public void setBalance60(double balance60) { _balance60 = balance60; }

    public double getBalance90() { return _balance90; }
    public void setBalance90(double balance90) { _balance90 = balance90; }

    public double getBalanceOther() { return _balanceOther; }
    public void setBalanceOther(double balanceOther) { _balanceOther = balanceOther; }


    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void setBalance(double charges, String aging) {

        try {

            Class c = Class.forName(RequestorAging.class.getName());
            Method method = c.getMethod("set" + aging, Double.TYPE);
            method.invoke(this, charges);

        } catch (Exception ex) {
            throw new ROIException(ex, ROIClientErrorCodes.UNABLE_TO_INVOKE_METHOD, "");
        }

    }

}
