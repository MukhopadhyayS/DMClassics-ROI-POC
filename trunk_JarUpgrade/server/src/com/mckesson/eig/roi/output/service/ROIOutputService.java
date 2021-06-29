/*
 * Copyright 2014 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.roi.output.service;

import com.mckesson.eig.roi.output.model.DestInfoList;
import com.mckesson.eig.roi.output.model.OutputFeature;
import com.mckesson.eig.roi.output.model.OutputRequest;



/**
 * This interface contains the services that are to be implemented in the ROIOutputServiceImpl
 * @author Karthik Easwaran(OFS)
 * @author Shahm Nattarshah.
 *
 */
public interface ROIOutputService {
    
    /**
     * This method is used to submit the output Request
     * @param request
     * @return
     */
    long submitOutputRequest(OutputRequest request);
    
    /**
     * This method is used to get Service properties
     * @return
     */
    OutputFeature getServiceProperties();
    
    /**
     * This method is used to get the output job object
     * @param jobID
     * @return
     */
    long queryOutputJob(int job);
    
    /**
     * This method is used to get the destinations
     * @param typeName
     * @return
     */
    DestInfoList getDestinations(String typeName);

}
