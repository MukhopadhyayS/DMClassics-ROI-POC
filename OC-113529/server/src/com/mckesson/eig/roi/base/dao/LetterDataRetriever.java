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

package com.mckesson.eig.roi.base.dao;

import com.mckesson.eig.roi.admin.model.LetterTemplateDocument;


/**
 * @author OFS
 * @date   Nov 19, 2008
 * @since  HPF 13.1 [ROI]; Nov 19, 2008
 */
public interface LetterDataRetriever {

    /**
     * For the given invoiceId/ regeneratedInvoiceId/ requestorLetterId the template data model 
     * @param invoiceId
     * @param requestCoreId
     * @return letter template Data model
     */
    Object retrieveLetterData(long Id, long requestCoreId);

    /**
     * retrieves the letter template File for the given letterTemplate File Id
     * @param templateId
     * @return
     */
    LetterTemplateDocument retrieveLetterTemplate(long templateId);
    

    /**
     * Construct the letter template data model from the given persistent object model
     * for the letter template generation 
     * @param persistentModel
     * @return letterTemplateData model
     */
    Object constructTemplateDataModel(Object persistentModel);
}
