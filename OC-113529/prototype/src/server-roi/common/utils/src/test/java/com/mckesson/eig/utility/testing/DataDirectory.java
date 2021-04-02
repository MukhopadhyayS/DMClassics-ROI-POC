/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.testing;

import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author Kenneth Partlow
 */
public class DataDirectory {

    private final String _directory;

    public DataDirectory(Class< ? > c) {
        _directory = getDirectory(c);
    }

    private String getDirectory(Class< ? > c) {
        return convert(retrieve(c.getName()));
    }

    private String retrieve(String name) {
        return retrieve(name, name.lastIndexOf("."));
    }

    private String retrieve(String name, int index) {
        return name.substring(0, index + 1) + "data.";
    }

    private String convert(String name) {
        return StringUtilities.replace(name, ".", "/");
    }

    public String getFullPath(String fileName) {
        return _directory + fileName;
    }
}
