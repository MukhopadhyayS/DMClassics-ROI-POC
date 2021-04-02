/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries.
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


import javax.jws.WebService;


/**
 * @author Sree Sabesh Rajkumar
 * @date Oct 01, 2009
 * @since WSClient 1.0
 */
@WebService(
        serviceName       = "TestWSService_v1_0",
        portName          = "testws_v1_0",
        targetNamespace   = "http://eig.mckesson.com/wsdl/testws-v1")
public class WSServiceImpl implements WSService {

    public String sayHellow() {
        return "Hellow";
    }
}
