/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Information Solutions and is protected under United States and 
 * international copyright and other intellectual property laws. Use, 
 * disclosure, reproduction, modification, distribution, or storage 
 * in a retrieval system in any form or by any means is prohibited without 
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.wsfw.cxf;

import org.apache.cxf.interceptor.ServiceInvokerInterceptor;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

/**
 * @author sahuly
 * @date   Dec 19, 2008
 * 
 * This class is used to set one way in message which tells response is not needed
 */
public class RouteOneWay extends AbstractPhaseInterceptor<Message> {

    public RouteOneWay() {
        
        super(Phase.PRE_INVOKE);
        addBefore(ServiceInvokerInterceptor.class.getName());
    }

    /**
     * 
     * @see org.apache.cxf.interceptor.Interceptor
     *              #handleMessage(org.apache.cxf.message.Message)
     */
    public void handleMessage(Message message) {

        Exchange exchange = message.getExchange();
        exchange.setOneWay(true);
    }
}
