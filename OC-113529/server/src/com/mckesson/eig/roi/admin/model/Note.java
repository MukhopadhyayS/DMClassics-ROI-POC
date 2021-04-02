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

package com.mckesson.eig.roi.admin.model;

import java.io.Serializable;


/**
 * @author OFS
 * @date   Sep 12, 2008
 * @since  HPF 13.1 [ROI]; Sep 12, 2008
 */
public class Note
extends AdminLoV
implements Serializable {

    /**
     * This method create the audit comment to create note
     * @param name name of note created
     * @return string
     */
    public String toCreateAudit() {
        return "Note " + getName() + " created";
    }

    /**
     * This method create the audit comment for update
     * @param name name of note updated
     * @return string
     */
    public String toUpdateAudit(String name) {
        return "Note " + name + " updated";
    }
}
