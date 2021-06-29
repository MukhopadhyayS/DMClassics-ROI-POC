/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2014 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/

package com.mckesson.eig.roi.billing.xdocreport.model;

import java.io.Serializable;

import com.mckesson.eig.roi.billing.letter.model.Note;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 * @author OFS
 * @date   Mar 17, 2014
 * @since  HPF 16.2 [ROI]; Mar 17, 2014
 */
public class XDocNote
implements Serializable {

    
    private static final long serialVersionUID = 4128793643937197493L;
    private String _description = StringUtilities.EMPTYSTRING;
    
    public XDocNote(Note note) {        
        if (null == note) {
            return;
        }        
        setDescription(StringUtilities.safe(note.getDescription()));
    }

    public String getDescription() { return _description; }
    public void setDescription(String desc) { _description = desc; }

}
