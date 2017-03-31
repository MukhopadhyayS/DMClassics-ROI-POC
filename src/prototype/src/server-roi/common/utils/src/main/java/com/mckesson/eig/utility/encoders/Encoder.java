/*
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

package com.mckesson.eig.utility.encoders;

import java.io.Serializable;

/**
 * This should be implemented by a class which needs to get its new instance and
 * perform encoding.
 * 
 */
public interface Encoder extends Serializable {

    /**
     * Performs an HexaDeciaml conversion.
     * 
     * @param bytes
     *            byte array representation.
     * @return character after conversion.
     */
    String encode(byte[] bytes);

    /**
     * Method which gives the new instance of the implemented class.
     * 
     * @return new instance.
     */
    Encoder newInstance();
}
