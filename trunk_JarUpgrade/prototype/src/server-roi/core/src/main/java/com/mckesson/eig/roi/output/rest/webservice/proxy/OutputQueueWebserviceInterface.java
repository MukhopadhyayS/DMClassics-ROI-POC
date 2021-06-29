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

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.mckesson.eig.roi.output.model.OutputQueue;

/**
 * This interface contains the methods that are available in the Output  class of
 * mpf core and it is used by the webClient to access the mpf core server
 *
 * @author Karthik Easwaran
 * @since Mar 26, 2014
 *
 */
@Path("/output")
public interface OutputQueueWebserviceInterface {

    /**
     * retrieves the list of outputqueues for the given queue Type
     * @param context
     * @param queueType
     * @return
     */
    @GET
    @Path("/getUserQueues/{queueType}")
    @Produces(MediaType.APPLICATION_JSON)
    List<OutputQueue> getQueuesByType(@PathParam("queueType") String queueType);
    
    /**
     * retrieves the list of labelTemplates for disc queues
     */
    @GET
    @Path("/labelTemplateFilenames")
    @Produces(MediaType.APPLICATION_JSON)
    List<String> getLabelTemplateFilenames(@QueryParam("templateLocation") String templateLocation);
}
