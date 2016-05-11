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

package com.mckesson.eig.roi.admin.service;

import com.mckesson.eig.roi.admin.model.OutputServerProperties;
import com.mckesson.eig.roi.base.api.ROIConstants;

import junit.framework.TestCase;

/**
 * @author tl4r0qy
 *
 */
public class TestOutputServerProperties extends TestCase {

     public void testSaveOutputServerProperties() throws Exception {

        OutputServerProperties osp = new OutputServerProperties();
        osp.setHostName("Eigdev770");
        osp.setEnabled(true);
        osp.setPort(ROIConstants.PORT_NO);
        osp.setProtocol("http:////");

        String xml = osp.toXMLString();
        System.out.println(xml);
    }

    public void testGetOutputServerProperties() throws Exception {

        String xml = "<output-server enabled=\"true\"><hostName>Eigdev770</hostName>"
            + "<id>0</id><port>3030</port><protocol>http</protocol></output-server>";
        OutputServerProperties osp = OutputServerProperties.valueOf(xml);
        System.out.println(osp);
    }
}
