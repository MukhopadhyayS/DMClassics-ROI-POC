/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.reports.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author Pranav Amarasekaran
 * @date   Dec 13, 2007
 * @since  HECM 1.0
 *
 * This interface declares the business methods the client will invoke in order to view the reports.
 */
public interface ReportService {

    /**
     * This methods recognizes the type of data to be prepared based on the reportID, parameters and
     * retrieves  report data, generates file with the report data and returns the file path.
     *
     * @param reportID
     *          report type.
     *
     * @param params
     *          parameter map.
     *
     * @param file
     *          File to which the stream has to be written.
     *
     * @return file path of the generated CSV file.
     */
    void generateReport(String reportID, Map params, File file) throws IOException;
}
