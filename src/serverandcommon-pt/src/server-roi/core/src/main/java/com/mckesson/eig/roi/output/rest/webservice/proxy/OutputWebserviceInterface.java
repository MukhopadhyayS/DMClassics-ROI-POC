/*
 * Copyright 2013 McKesson Corporation and/or one of its subsidiaries.
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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.mckesson.eig.roi.output.model.OutputFeature;


/**
 * This class acts as a proxy interface for the core server,
 * Using the interface the request to the integration services creates a proxy rest client service
 * and comnunicate to the Core server
 *
 * @author Karthik Eswaran (OFS)
 * @author Nattarshah Mohammed
 *
 * @since 16.2
 *
 */
@Path("/output")
public interface OutputWebserviceInterface {

    /**
     * This is used to query output job
     * @param req
     * @return
     */
    @GET
    @Path("/queryOutputJob/{jobId}")
    @Produces("application/json")
    public long queryOutputJob(@PathParam("jobId") int jobId);

    /**
     * This method is used to get service properties
     * @return
     */
    @GET
    @Path("/getServiceProperties/{featureName}")
    @Produces("application/json")
    public OutputFeature getServiceProperties(@PathParam("featureName") String name);
}
