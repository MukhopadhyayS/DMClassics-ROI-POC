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

package com.mckesson.eig.roi.request.model;

import java.io.Serializable;
import java.util.List;


/**
 * @author OFS
 * @date   Aug 7, 2008
 * @since  HPF 13.1 [ROI]; Aug 7, 2008
 */
public class Comments
implements Serializable {

    private int _count;
    private List<Comment> _comments;

    public Comments() { }
    public Comments(List<Comment> comments) {
        setComments(comments);
    }

    public int getCount() { return _count; }
    public void setCount(int count) { _count = count; }

    public List<Comment> getComments() { return _comments; }
    public void setComments(List<Comment> comments) { _comments = comments; }

}
