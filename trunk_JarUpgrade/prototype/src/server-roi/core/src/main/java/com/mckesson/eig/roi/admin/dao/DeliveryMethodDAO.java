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


import com.mckesson.eig.roi.admin.model.DeliveryMethod;
import com.mckesson.eig.roi.admin.model.DeliveryMethodList;
import com.mckesson.eig.roi.base.dao.ROIDAO;

/**
 * @author OFS
 * @date   Sep 15, 2008
 * @since  HPF 13.1 [ROI]; Sep 15, 2008
 */
public interface DeliveryMethodDAO
extends ROIDAO {

    /**
     * This method creates a new Delivery Method
     *
     * @param deliveryMethod Delivery Method Details to be created
     * @return Unique id of the Delivery Method
     */
    long createDeliveryMethod(DeliveryMethod deliveryMethod);

    /**
     * This method fetches the delivery method for the specified id
     *
     * @param deliveryMethodId Unique id of the delivery method
     * @return Delivery Method details
     */
    DeliveryMethod retrieveDeliveryMethod(long deliveryMethodId);

    /**
     * This method fetches all the delivery methods
     *
     * @return List of all Delivery Methods
     */
    DeliveryMethodList retrieveAllDeliveryMethods();

    /**
     * This method updates the delivery method
     *
     * @param deliveryMethod The details of the delivery method to be updated
     * @param originalDeliveryMethod The details of the delivery method in DB
     * @return The details of the updated delivery method
     */
    DeliveryMethod updateDeliveryMethod(DeliveryMethod deliveryMethod,
                                               DeliveryMethod originalDeliveryMethod);
    /**
     * This method fetches the delivery method details by name
     *
     * @param deliveryMethodName The details of the delivery method
     * @return Delivery Method
     */
    DeliveryMethod getDeliveryMethodByName(String deliveryMethodName);

    /**
     * This method deletes the delivery method
     *
     * @param deliveryMethodId Delivery Method id to be deleted
     * @return Details of DeliveryMethod
     */
    DeliveryMethod deleteDeliveryMethod(long deliveryMethodId);

    /**
     * This method will retrieve the default Delivery Method
     * @return Default Delivery Method
     */
    DeliveryMethod getDefaultDeliveryMethod();

    /**
     * This method clears the existing default delivery method
     * @param DeliveryMethod
     */
    void clearDefaultDeliveryMethod(DeliveryMethod dm);

    /**
     * This methos fetches the ids and names for all delivery methods
     * @return list of Delivery Methods
     */
    DeliveryMethodList retrieveAllDeliveryMethodNames();
}
