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

package com.mckesson.eig.roi.admin.model;


import java.util.List;


/**
 * @author manikandans
 * @date   Apr 20, 2008
 * @since  HPF 13.1 [ROI]; Apr 16, 2008
 */
public class PaymentMethodList {

    private List<PaymentMethod> _paymentMethods;
    public PaymentMethodList() { };

    public PaymentMethodList(List<PaymentMethod> paymentMethods) {
        setPaymentMethods(paymentMethods);
    }

    public List<PaymentMethod> getPaymentMethods() { return _paymentMethods; }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        _paymentMethods = paymentMethods;
    }
}
