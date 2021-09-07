/**
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Corporation and/or one of its subsidiaries and is protected 
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.util;

public class ByteUtilitiesBigEndian extends ByteUtilities {

    private static final int BINARY_INT = 8;

    protected long extractNumber(byte[] bytes, int start, int maxBytes) {
        
        long number = convertToPositiveInt(bytes[start]);

        for (int i = start + 1; i < start + maxBytes; i++) {
            number = number << BINARY_INT;
            number += convertToPositiveInt(bytes[i]);
        }
        
        return number;
    }
}
