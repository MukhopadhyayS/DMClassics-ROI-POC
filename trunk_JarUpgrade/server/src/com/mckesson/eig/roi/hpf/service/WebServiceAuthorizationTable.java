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

package com.mckesson.eig.roi.hpf.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.collections.ExtendedProperties;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.io.FileLoader;
import com.mckesson.eig.wsfw.model.authorization.AuthorizationException;

/**
 * @author Ganeshram
 * @date   Jun 23, 2008
 * @since  HPF 13.1 [ROI]; Jun 19, 2008
 */
public class WebServiceAuthorizationTable {

    private static final OCLogger LOG = new OCLogger(WebServiceAuthorizationTable.class);

    private ExtendedProperties _map;

    public void setMappingFile(String mappingFile) {
        
        try {
            
            InputStream stream = FileLoader.getResourceAsInputStream(mappingFile);
            _map = new ExtendedProperties();
            _map.load(stream);
            LOG.info("**** File loaded : " + mappingFile);
        } catch (Exception e) {
            throw new AuthorizationException(e);
        }
    }

    public List<Integer> getRequiredRight(String key) {

        @SuppressWarnings("unchecked") // generics not supported by 3rd party API
        Vector vector = _map.getVector(key);

        List<Integer> ids = new ArrayList<Integer>();
        for (Object securityId : vector) {
            ids.add(new Integer((String) securityId));
        }
        return ids;
    }
}
