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
 * @author Pranav Amarasekaran
 * @date   Dec 16, 2010
 * @since  HPF 15.1 [ROI]; Dec 16, 2010
 */

public class AttachmentLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    public AttachmentLocation() {
        super();
    }
    
    public AttachmentLocation(long attachmentID, String attachmentLoc) {
        _attachmentID = attachmentID;
        _attachmentLocation = attachmentLoc;
    }
    
    public AttachmentLocation(String attachmentLoc) {
          _attachmentLocation = attachmentLoc;
    }

    private long _attachmentID;
    private String _attachmentLocation;
    
    public long getAttachmentID() { return _attachmentID; }
    public void setAttachmentID(long attachmentID) { _attachmentID = attachmentID; }
    
    public String getAttachmentLocation() { return _attachmentLocation; }
    public void setAttachmentLocation(String location) { _attachmentLocation = location; }    
}
