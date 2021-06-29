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

package com.mckesson.eig.wsfw.exception;

/**
 * @author sahuly
 * @date   Dec 22, 2008
 * @since  HECM 1.0; Dec 22, 2008
 * 
 * This interface declares the methods that are used to process the error request message.
 */
public interface ErrorHandler {
    
    /**
     * Processing the error service request whether the message has to be save in a file as
     * XML or just throw the exception without processing the message etc. 
     *  
     * @param errorXMLMessage
     *        request message  
     * 
     * @param parseError
     *        Throwable(cause).  
     */
    void processErrorMessage(String errorXMLMessage, Throwable parseError);

}
