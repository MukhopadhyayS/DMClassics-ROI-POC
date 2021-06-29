/*
 * Copyright 2008-2009 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.api;

import javax.xml.soap.SOAPException;

import org.apache.cxf.endpoint.Client;

import com.mckesson.eig.wsfw.cxf.WSSecurityUtil;

/**
 * @author Sree Sabesh Rajkumar
 * @date Aug 24, 2009
 * @since WSClient 1.0
 */
public class APIWebServiceBase {

    /**
     * Method used to set the headers
     *
     * @param  userInfo
     * @return boolean
     * @throws SOAPException 
     * @throws Exception
     */
    public void initializeService(Client client, String userName, String password)
    throws SOAPException {
        
        WSSecurityUtil.addHeaderInformation(client, userName, password);
    }
    
    /**
     * Method used to set the headers with secondary sesson
     *
     * @param  userInfo
     * @return boolean
     * @throws SOAPException 
     * @throws Exception
     */
    public void initializeService(Client client, 
                                  String userName, 
                                  String password, 
                                  boolean useSecondarySessionID)
    throws SOAPException {
        
        WSSecurityUtil.addHeaderInformation(client, userName, password, useSecondarySessionID);
    }
}
