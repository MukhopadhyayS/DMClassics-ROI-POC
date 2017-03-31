/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Information Solutions and is protected under United States and 
 * international copyright and other intellectual property laws. Use, 
 * disclosure, reproduction, modification, distribution, or storage 
 * in a retrieval system in any form or by any means is prohibited without 
 * the prior express written permission of McKesson Information Solutions.
 */
package com.mckesson.eig.common.filetransfer.services;

import javax.servlet.http.HttpSession;

/**
 * @author N.Shah Ghazni
 * @date   May 8, 2008
 * @since  HECM 1.0; May 8, 2008
 */
public class BaseFileUploaderTester extends BaseFileUploader {

    /**
     * @see com.mckesson.eig.common.filetransfer.services.BaseFileUploader#
     *      getCacheDirectory(javax.servlet.http.HttpSession)
     */
    @Override
    protected String getCacheDirectory(HttpSession session) {
        return "FileCache";
    }
}
