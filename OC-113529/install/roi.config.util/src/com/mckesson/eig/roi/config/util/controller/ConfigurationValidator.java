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
import java.util.Map;

import com.mckesson.eig.roi.config.util.model.SourceType;


/**
 *
 * @author OFS
 * @date   Sep 29, 2008
 * @since  HPF 13.1 [ROI]; Sep 29, 2008
 */
public interface ConfigurationValidator {

    /**
     * This method is to validate the each JTextField's value in JBoss Server page
     * @param Map<String, String>
     */
    void validateJbossParams(Map<String, String> configParams);

    /**
     * This method is to validate the each JTextField's value in DBConnection page
     * @param Map<String, String>
     */
    void validateDBConnectionParams(Map<String, String> configParams);

    /**
     * This method is to validate the each JTextField's value
     * in HPFW service & Client Installer page
     * Map<String, String>
     */
    void validateConfigParams(Map<String, String> configParams);

    /**
     * This method is to validate the each JTextField's value in FaxService page
     * @param Map<String, String>
     */
    void validateFaxServiceParams(Map<String, String> configParams);

    /**
     * This method is to validate the source type check boxes for selection
     * @param sourceTypes
     */
    void validateSourceTypeSelection(List<SourceType> sourceTypes);

    /**
     * This method is to validate the source type check boxes for selection
     * @param sourceTypes
     */
	void validateDBCredentials(Map<String, String> configParams);

}
