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
import java.util.List;


/**
 * @author Vidhya.C.S
 * @date   May 16, 2008
 * @since  HPF 13.1 [ROI]; May 16, 2008
 */
public class LetterTemplateList
implements Serializable {

    private List<LetterTemplate> _letterTemplates;
    public LetterTemplateList() { }

    public LetterTemplateList(List<LetterTemplate> list) { setLetterTemplates(list); }

    public List <LetterTemplate> getLetterTemplates() {
        return _letterTemplates;
    }

    public void setLetterTemplates(List <LetterTemplate> templates) {
        _letterTemplates = templates;
    };
}
