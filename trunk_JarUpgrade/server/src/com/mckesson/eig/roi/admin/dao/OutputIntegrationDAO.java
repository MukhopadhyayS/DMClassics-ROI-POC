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


import javax.xml.bind.JAXBException;

import com.mckesson.eig.roi.admin.model.OutputServerProperties;
import com.mckesson.eig.roi.base.dao.ROIDAO;

/**
 * @author OFS
 * @date   Feb 24, 2010
 * @since  HPF 15.1 [ROI];
 */
public interface OutputIntegrationDAO
extends ROIDAO {

    /**
     * This method is used to enable the output service form ROI application.
     * @param doEnable
     * @throws JAXBException
     */
    void enableOutputService(boolean doEnable) throws JAXBException;

    /**
     * This method is used to retrieve output service properties.
     * @return
     * @throws JAXBException
     */
    OutputServerProperties retrieveOutputServerProperties() throws JAXBException;

    /**
     * This Method is used to save output service properties.
     * @param outputServerProperties
     * @throws JAXBException
     */
    void saveOutputServerProperties(OutputServerProperties outputServerProperties)
                                    throws JAXBException;
}
