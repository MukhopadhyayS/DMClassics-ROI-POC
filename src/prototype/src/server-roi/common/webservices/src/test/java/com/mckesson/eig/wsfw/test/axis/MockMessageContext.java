/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
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

package com.mckesson.eig.wsfw.test.axis;

import org.apache.axis.AxisEngine;
import org.apache.axis.MessageContext;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.handlers.soap.SOAPService;
import org.apache.axis.transport.http.HTTPConstants;

/**
 *
 */
public class MockMessageContext extends MessageContext {

    private OperationDesc _operationDesc;
    private SOAPService _service;
    
    /**
     * @param engine
     */
    public MockMessageContext(AxisEngine engine) {
        super(engine);
     }

    public OperationDesc getOperation() {
        return _operationDesc;
    }
    
    public void setOperationDesc(OperationDesc desc) {
        _operationDesc = desc;
    }
    
    public SOAPService getService() {
        return _service;
    }
    
    public void setService(SOAPService service) {
        _service = service;
    }
    
    public Object getProperty(String name) {
        if (name == HTTPConstants.MC_HTTP_SERVLETREQUEST) {
            return new MockHttpServletRequest();
        }
        return null;
    }
}
