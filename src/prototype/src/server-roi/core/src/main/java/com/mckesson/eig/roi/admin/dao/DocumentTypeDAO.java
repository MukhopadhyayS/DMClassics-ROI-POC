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


import java.util.List;

import com.mckesson.eig.roi.admin.model.DocTypeDesignations;
import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.hpf.model.User;


/**
 * @author OFS
 * @date   Jan 5, 2009
 * @since  HPF 13.1 [ROI]; Sep 15, 2008
 */
public interface DocumentTypeDAO
extends ROIDAO {

    /**
     * This method is to create, update, and delete the DesignateDocumentTypes
     * @param codeSetId Id of the CodeSet
     * @param docTypes list of DocumentTypeDetails
     * @param designation type of the document
     * @param user details of the user
     * @return List of documentType details
     */
    void designateDocumentTypes(long codeSetId,
                                DocTypeDesignations newDesigs,
                                DocTypeDesignations currentDesigs,
                                User user);

    /**
     * This method fetches the designation details
     * @param codeSetId code set id to retrieve
     * @return DocTypeDesignation
     */
    DocTypeDesignations retrieveDesignations(long codeSetId);

    /**
     * This method is to retrieve the document types ids
     * @param designation
     * @return  List<Long>
     */
    List<Long> retrieveDocTypeIdsByDesignation(String designation);

    /**
     * This method is to retrieve the MU document types ids
     * @param 
     * @return  List<String>
     */
    List<String> retrieveMUDocTypes();
}
