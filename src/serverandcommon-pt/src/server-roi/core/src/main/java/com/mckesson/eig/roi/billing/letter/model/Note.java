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

package com.mckesson.eig.roi.billing.letter.model;

import java.io.Serializable;
import java.util.List;


/**
 * @author OFS
 * @date   Aug 17, 2009
 * @since  HPF 13.1 [ROI]; Feb 19, 2009
 */
public class Note
implements Serializable {

    private static final long serialVersionUID = 4128793643937197493L;
    private String _description;
    private List<Note> _childItems;

    public String getDescription() { return _description; }
    public void setDescription(String desc) { _description = desc; }

    public List<Note> getChildItems() { return _childItems; }
    public void setChildItems(List<Note> childItems) { _childItems = childItems; }
}
