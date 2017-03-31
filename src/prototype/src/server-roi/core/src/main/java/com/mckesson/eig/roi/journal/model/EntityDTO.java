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

import java.util.List;

/**
 * 
 * @author edksi0l
 *
 */
public class EntityDTO {

    private String _code;
    private String _description;
    private String _name;
    private List<LineItemDTO> _lineItems;
    
    public List<LineItemDTO> getLineItems() {
        return _lineItems;
    }
    public void set_lineItems(List<LineItemDTO> lineItems) {
        _lineItems = lineItems;
    }
    public String getCode() {
        return _code;
    }
    public void setCode(String code) {
        _code = code;
    }
    public String getDescription() {
        return _description;
    }
    public void setDescription(String description) {
        _description = description;
    }
    public String getName() {
        return _name;
    }
    public void setName(String name) {
        _name = name;
    }
    
}
