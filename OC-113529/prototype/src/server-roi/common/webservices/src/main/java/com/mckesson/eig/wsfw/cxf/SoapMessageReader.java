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

import java.io.IOException;
import java.io.InputStream;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

/**
 * @author sahuly
 * @date   Dec 18, 2008
 * 
 */
public class SoapMessageReader extends AbstractPhaseInterceptor<Message> {

    public SoapMessageReader() {
        super(Phase.RECEIVE); 
    }

    /**
     * 
     * @see org.apache.cxf.interceptor.Interceptor
     *              #handleMessage(org.apache.cxf.message.Message)
     */
    public void handleMessage(Message message) {

        InputStream is = message.getContent(InputStream.class);
        CachedOutputStream cos = new CachedOutputStream();

        if (is != null) {
            try {

                IOUtils.copy(is, cos);
                cos.flush();
                is.close();

                message.setContent(InputStream.class, cos.getInputStream());
                cos.close();
            } catch (IOException e) {
                throw new Fault(e);
            }
        }
    }
}
