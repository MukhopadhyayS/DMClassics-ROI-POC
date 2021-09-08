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

package com.mckesson.eig.roi.output.rest.webservice.proxy;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * This interface contains the methods that are available in the ROIOutputServiceImpl class of
 * mpf core and it is used by the webClient to access the mpf core server
 * @author Karthik Easwaran
 *
 */
@Path("/ROIoutput")
public interface ROIOutputWebServiceInterface {

    /**
     * Retrieves the status of all the task submitted to the Output server
     * @return list of all tasks
     */
    @POST
    @Path("/ROIsubmitTask")
    @Produces("application/json")
    long submitTask(@FormParam("contents") String contents, @FormParam("queue") String queue,
                    @FormParam("taskProperties") String attributes);
}
