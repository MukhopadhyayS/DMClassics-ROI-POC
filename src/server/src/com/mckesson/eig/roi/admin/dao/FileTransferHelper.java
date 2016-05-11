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

package com.mckesson.eig.roi.admin.dao;

import java.io.InputStream;

import com.mckesson.eig.common.filetransfer.services.BaseFileTransferData;


/**
 * @author Vidhys.C.s
 * @date   May 16, 2008
 * @since  HPF 13.1 [ROI]; May 20, 2008
 */
public interface FileTransferHelper {

   /**
    * This method retrieves the uploaded document either from cache or from stream depending on
    * CACHEENABLED flag. If a new letter template document is uploaded, then the created docId is
    * associated with the corresponding letter template passed in the request parameter.
    * @param serverData
    * @param ins
    * @param available
    * @param responsexml
    */
   void completeUpload(BaseFileTransferData serverData, InputStream ins,
                           int available, StringBuffer responsexml);
   /**
    * This method retrieves the document for the specified docId from the database and writes
    *  either to the stream or to a cache file depending on the CACHEENABLED flag.
    *
    * @param serverData
    * @return
    */
   String retrieve(BaseFileTransferData serverData);

   boolean isValidFileName(String fileName);
}
