/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Corporation and/or one of its subsidiaries and is protected 
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.common.filetransfer.controller;

import com.mckesson.eig.common.filetransfer.services.BaseFileTransferData;

/**
 * Interface used for specific content retrieval functionality
 * @author eyy4ifv
 * @version 1.0
 * @created 09-Nov-2007 5:55:42 PM
 */
public interface IContentRetriever {

    /**
    * get the UNC path to the requested content
    *
    * @param data Provide access to request and request parameters
    */
    String retrieveContent(BaseFileTransferData data);
}
