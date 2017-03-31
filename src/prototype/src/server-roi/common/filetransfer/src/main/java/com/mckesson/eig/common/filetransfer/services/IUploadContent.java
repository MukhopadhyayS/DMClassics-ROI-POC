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

import java.io.IOException;


/**
 * Interface for uploading content to the server
 * 
 * @author N.Shah Ghazni
 * @date   May 7, 2008
 * @since  1.0;
 */
public interface IUploadContent {

    void putRemoteFile(BaseFileTransferData data) throws IOException;
}
