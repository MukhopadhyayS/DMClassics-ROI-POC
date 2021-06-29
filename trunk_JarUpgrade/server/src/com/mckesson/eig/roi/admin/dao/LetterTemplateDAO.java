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

import com.mckesson.eig.roi.admin.model.LetterTemplate;
import com.mckesson.eig.roi.admin.model.LetterTemplateDocument;
import com.mckesson.eig.roi.admin.model.LetterTemplateFile;
import com.mckesson.eig.roi.admin.model.LetterTemplateList;
import com.mckesson.eig.roi.base.dao.ROIDAO;


/**
 * @author OFS
 * @date   Jul 02, 2009
 * @since  HPF 13.1 [ROI]; Sep 15, 2008
 */
public interface LetterTemplateDAO
extends ROIDAO {

    /**
     * This method creates new LetterTemplate
     * @param lt LetterTemplate details to be created
     * @return unique id of the LetterTemplate
     */
    long createLetterTemplate(LetterTemplate lt);

    /**
     * This method fetches the letterTemplate
     * @param id Id of the letterTemplate
     * @return letterTemplate
     */
    LetterTemplate retrieveLetterTemplate(long id);

    /**
     * This method retrieves All LetterTemplate from DB
     * @return List of letterTemplates
     */
    LetterTemplateList retrieveAllLetterTemplates();

    /**
     * This method delete LetterTemplate
     * @param id LetterTemplateId
     */
    LetterTemplate deleteLetterTemplate(long id);

    /**
     * This method will delete document with input document Id
     * @param id
     */
    void deleteDocument(long id);

    /**
     * This method update the letterTemplate
     * @param lt details of the letterTemplate to be updated
     * @return updated LetterTemplate
     */
    LetterTemplate updateLetterTemplate(LetterTemplate lt);

    /**
     * This method clears the existing default LetterTemplate
     * @param lt details of the letterTemplate
     */
    void clearDefault(LetterTemplate lt);

    /**
     * This method retrieve LetterTemplate based on the letterTemplatename,lettterTemplateType
     * @param name name of the letterTemplate
     * @param type type of the letterTemplate
     * @return selected letterTemplate
     */
    LetterTemplate getLetterTemplateByName(String name, String type);

    /**
     * This method retrieve LetterTemplate based on the letterTeype
     * @param letterType type of the LetterTemplate
     * @return selected letterTemplate
     */
    LetterTemplate getDefaultLetterTemplate(String letterType);

    /**
     * This method will check for valid file name
     * @param fileName
     * @return
     */
    boolean isValidFileName(String fileName);

    /**
     * This method fetches the letter template file details
    * @param id Id of the letterTemplateFile
     * @return letterTemplatefile
     */
    LetterTemplateFile retrieveLetterTemplateFile(long id);

    /**
     * This method check whether letter template is available for the letter type
     * @param letterType
     * @return
     */
    boolean hasLetterTemplate(String letterType);
    
    /**
     * This method retrieves the letter template document from the given template file id
     * @param templateFileId
     * @return
     */
    LetterTemplateDocument retrieveLetterTemplateDocument(long templateFileId);
}
