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

package com.mckesson.eig.wsfw.test;

import com.mckesson.eig.wsfw.exception.ErrorHandler;

/**
 * @author sahuly
 * @date   Dec 31, 2008
 * @since  HECM 1.0; Dec 31, 2008
 */
public class MockErrorHandler implements ErrorHandler {
    
    public void processErrorMessage(String errorXMLMessage, Throwable parseError) {
        System.out.println("processiong the error message");
    }
}
