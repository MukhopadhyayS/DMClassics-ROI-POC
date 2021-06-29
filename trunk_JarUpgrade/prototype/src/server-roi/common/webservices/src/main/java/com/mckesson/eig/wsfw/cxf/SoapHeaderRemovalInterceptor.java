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

import java.lang.reflect.Method;
import java.util.List;

import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.wsdl.interceptors.DocLiteralInInterceptor;

/**
 * @author sahuly
 * @date   Dec 15, 2008
 * 
 * This class is used to remove the unnecessary information from the header and also 
 * remove the unnecessary interceptors.
 */
public class SoapHeaderRemovalInterceptor extends AbstractPhaseInterceptor<Message> {
    
    public SoapHeaderRemovalInterceptor() {

        super(Phase.UNMARSHAL);
        addAfter(DocLiteralInInterceptor.class.getName());
    }
    
    /**
     * @see org.apache.cxf.interceptor.Interceptor
     *          #handleMessage(org.apache.cxf.message.Message)
     */
    @SuppressWarnings("unchecked")
    public void handleMessage(Message message) {

        removeHeaderParameter(message);
    }
    
    /**
     * This method is used to remove the Soap Header from the param list because 
     * service method doesn't have the header parameter.
     *  
     * @param message
     */
    private void removeHeaderParameter(Message message) {
 
        List< ? > parameters     = message.getContent(List.class);
        Exchange exchange        = message.getExchange();
        BindingOperationInfo bop = exchange.get(BindingOperationInfo.class);

        Class< ? >[] paramTypes  = (((Method) bop.getOperationInfo()
                                                   .getProperty(Method.class.getName()))
                                                   .getParameterTypes());
        
        if (parameters == null || parameters.size() == 0) {
            return;
        }

        int headerCount = parameters.size() - paramTypes.length;
        for (int i = headerCount; --i >= 0;) {
            parameters.remove(parameters.size() - 1);
        }
    }
}
