/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.workflow.datavault;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.workflow.process.datavault.ProcessDVHelper;
import com.mckesson.eig.workflow.process.datavault.ProcessDataVault;

public final class DVLogger {

	private static final OCLogger LOG     = new OCLogger(ProcessDataVault.class);
	private static final OCLogger CONSOLE = new OCLogger("sop");

    private DVLogger() {
    }

    /**
     * @param sheetName String
     * @param errorCode String
     */
    public static void log(String sheetName, String errorCode, int rowNum) {

        StringBuffer logMsg = new StringBuffer(sheetName);
        if (rowNum != -1) {
            logMsg.append(" : Row - ").append(rowNum);
        }
        logMsg.append(ProcessDVHelper.ERROR_CODE_DESC_MAP.get(errorCode));
        LOG.debug(logMsg.toString());
        CONSOLE.debug(logMsg.toString());
    }
}
