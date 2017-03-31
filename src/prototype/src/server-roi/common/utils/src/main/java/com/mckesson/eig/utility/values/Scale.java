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

package com.mckesson.eig.utility.values;

public class Scale {

    private int _verticalSize;

    private int _horizontalSize;

    public Scale() {
    }

    /**
     * Constructor with two arguments passed as a type <code>integer</code>
     * and save in instant variables.
     * 
     * @param verticalSize
     *            Passed as an argument of type <code>int</code>.
     * @param horizontalSize
     *            Passed as an argument of type <code>int</code>.
     */

    public Scale(int verticalSize, int horizontalSize) {
        _verticalSize = verticalSize;
        _horizontalSize = horizontalSize;
    }

    /**
     * Calling Bean.
     * 
     * @return Scale Type <code>Object</code>.
     */
    public int getVerticalSize() {
        return _verticalSize;
    }

    /**
     * Calling Bean.
     * 
     * @return Scale Type <code>Object</code>.
     */
    public int getHorizontalSize() {
        return _horizontalSize;
    }
}
