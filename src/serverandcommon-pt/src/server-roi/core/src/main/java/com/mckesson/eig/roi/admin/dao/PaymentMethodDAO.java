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

package com.mckesson.eig.roi.admin.dao;


import com.mckesson.eig.roi.admin.model.PaymentMethod;
import com.mckesson.eig.roi.admin.model.PaymentMethodList;
import com.mckesson.eig.roi.base.dao.ROIDAO;


/**
 * @author OFS
 * @date   Sep 15, 2008
 * @since  HPF 13.1 [ROI]; Sep 15, 2008
 */
public interface PaymentMethodDAO
extends ROIDAO {

    /**
     * This method creates a new Payment Method
     *
     * @param paymentMethod Payment Method details to be created
     * @return Unique Id of the Payment Method
     */
    long createPaymentMethod(PaymentMethod paymentMethod);

    /**
     * This method fetches the Payment Method details for the specified id.
     *
     * @param paymentMethodId Unique id of the Payment Method
     * @return Payment Method details
     */
    PaymentMethod retrievePaymentMethod(long paymentMethodId);

    /**
     * This method fetches all the  existing Payment Methods
     *
     * @return List of all Payment Methods
     */
    PaymentMethodList retrieveAllPaymentMethods();

    /**
     * This method deletes the payment method
     *
     * @param paymentMethodId Id of the payment method
     * @return payment method details
     */
    PaymentMethod deletePaymentMethod(long paymentMethodId);

    /**
     * This method updates the payment method
     *
     * @param paymentMethod The updated payment method
     * @param originalPaymentMethod The original payment method in the database.
     * @return Updated payment method
     */
    PaymentMethod updatePaymentMethod(PaymentMethod paymentMethod,
                                             PaymentMethod originalPaymentMethod);

    /**
     * This method fetches the Payment Method for the specified name
     *
     * @param paymentMethodName Name of the payment method
     * @return payment method for the given name or null if no
     * payment method exist with that name.
     */
    PaymentMethod getPaymentMethodByName(String paymentMethodName);

    /**
     * This method fetches name and ids of all Payment Methods
     * @return list of all PaymentMethods
     */
    PaymentMethodList retrieveAllPaymentMethodNames();
}
