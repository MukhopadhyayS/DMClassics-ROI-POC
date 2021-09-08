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


import java.util.List;


/**
 * @author manikandans
 * @date   Feb 15, 2008
 * @since  HPF 13.1 [ROI]; Feb 15, 2008
 */
public class MediaTypesList {

    private List<MediaType> _mediaTypes;
    public MediaTypesList() { };

    public MediaTypesList(List<MediaType> list) { setMediaTypesList(list); }

    public List<MediaType> getMediaTypesList() { return _mediaTypes; }
    public void setMediaTypesList(List<MediaType> mediaTypesList) { _mediaTypes = mediaTypesList; }
}
