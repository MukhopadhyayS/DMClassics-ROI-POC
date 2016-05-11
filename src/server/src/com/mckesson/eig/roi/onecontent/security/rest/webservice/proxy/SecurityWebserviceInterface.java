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

package com.mckesson.eig.roi.onecontent.security.rest.webservice.proxy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.mckesson.eig.roi.onecontent.service.model.MPFUserSession;

/**
 * @author ais
 *
 */
@Path("/securityservice")
@Produces({"application/json" })
public interface SecurityWebserviceInterface {

    @GET
    @Path("/login")
    public MPFUserSession login();
}
