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
package com.mckesson.eig.reports.dao;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author Pranav Amarasekaran
 * @date   Dec 13, 2007
 * @since  HECM 1.0
 *
 * This interface declares the business methods, which the service implementation will invoke in
 * order to view the reports.
 */
public interface ReportDAO {

    /**
     * This method queries the report data based on the report ID, params and writes it into the
     * output stream.
     *
     * @param reportID
     * @param params
     * @param outputStream
     */
    void generateReport(String reportID, Map params, OutputStream outputStream) throws IOException;
}
