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

package com.mckesson.eig.roi.config.util.controller;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;


/**
 *
 * @author OFS
 * @date   Jun 01, 2012
 * @since  Jun 01, 2012
 */

public class JBoss4ServerConfigControllerImpl
extends JBossServerConfigControllerImpl {

    private String _portXPath = "//Server/Service/Connector/@port";
    
    /**
     * This method is to get the attribute value from the corresponding file
     * @param doc Document
     * @param xPath Attribute location
     * @return
     */
    public String getConditionedAttributeValue(Document doc, String xPath) {

        @SuppressWarnings("unchecked") //not supported by 3PartyAPI
        List<Attribute> atts = doc.selectNodes(_portXPath);
        Attribute att = atts.get(0);
        return att.getText();
    }
}
