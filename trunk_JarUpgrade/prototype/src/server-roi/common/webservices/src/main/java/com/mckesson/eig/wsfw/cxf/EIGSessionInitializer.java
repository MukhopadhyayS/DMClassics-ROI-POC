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

import org.apache.cxf.binding.soap.interceptor.SoapActionInInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

/**
 * @author sahuly
 * @date   Dec 15, 2008
 * 
 * This class do the WsSession initialization, Management of transaction signature and setting the
 * header values like appID and transactionID into the WsSession.
 */
public class EIGSessionInitializer extends AbstractPhaseInterceptor<Message> {
    
    public EIGSessionInitializer() {

        super(Phase.READ);
        addAfter(SoapActionInInterceptor.class.getName());
    }

    /**
     * 
     * @see org.apache.cxf.interceptor.Interceptor
     *              #handleMessage(org.apache.cxf.message.Message)
     */
    public void handleMessage(Message message) {

        // Initialize the WsSession and also add TransactionSignature
        CXFUtil.prepareRequest(message);
        CXFUtil.setHeaderValuesInSession(message);
    }
}
