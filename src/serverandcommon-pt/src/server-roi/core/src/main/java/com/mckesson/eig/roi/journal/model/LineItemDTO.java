/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/

package com.mckesson.eig.roi.journal.model;

/**
 * 
 * @author edksi0l
 *
 */
public class LineItemDTO {

    private String _query;
    private String _name;
    
    public String getQuery() {
        return _query;
    }
    public void setQuery(String query) {
        _query = query;
    }
    public String getName() {
        return _name;
    }
    public void setName(String name) {
        _name = name;
    }
    
}
