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
 * @author N.Shah Ghazni
 * @date   Jun 6, 2008
 * @since  HECM 1.0; Jun 6, 2008
 */
public class MockBaseFileDownloader extends BaseFileDownloader {

    public MockBaseFileDownloader() {
        super("com.mckesson.eig.common.filetransfer.services.BasetFileDownloaderTester");
    }

    /**
     * @see com.mckesson.eig.common.filetransfer.services.BaseFileDownloader#
     * writeToOutputStream(com.mckesson.eig.common.filetransfer.services.BaseFileTransferData, 
     *                    java.lang.String)
     */
    @Override
    public boolean writeToOutputStream(BaseFileTransferData servData, String filePath) 
    throws IOException {

        servData.getRequestProperty(BaseFileTransferData.PARAMETER_USER);
        servData.getLongRequestProperty(BaseFileTransferData.PARAMETER_OFFSET);
        return super.writeToOutputStream(servData,  filePath 
                                                    + servData.getFileName() 
                                                    + String.valueOf(System.currentTimeMillis()));
    }
}
