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

package com.mckesson.eig.utility.decoders;

import java.io.Serializable;

/**
 * class which decodes needs to get this implemented.
 * 
 */
public interface Decoder extends Serializable {

    /**
     * Method which Converts a String of hexadecimal digits into the
     * corresponding byte array by decoding each two hexadecimal digits as a
     * byte.
     * 
     * @param digits
     *            HexaDecimal digits representation.
     * @return current contents.
     */
    byte[] decode(String digits);
}
