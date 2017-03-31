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

package com.mckesson.eig.wsfw.test.service;

/**
 * This is a sample service that we will use for testing.
 *
 */
public class HelloWorldService {

    private static int _callCount = 0;
    
    public String hello() {
        return "Hello World";
    }
    
    public String goodbye() {
        return "Goodbye cruel world";
    }
    
    public boolean isAnybodyOutThere() {
        System.out.println("HelloWorldService.isAnybodyOutThere() called");
        _callCount++;
        return false;
    }
    
    public static int getCallCount() {
        return _callCount;
    }
    
    public String yoDude() {
        return "Whatever";
    }
}
