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

package com.mckesson.eig.reports.dao.test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;

import org.hibernate.Session;

import com.mckesson.eig.utility.util.StringUtilities;

/**
 * This class is used to provide test case implementation for the report framework.
 *
 * @author Pranav Amarasekaran
 * @date   Jan 9, 2008
 * @since  HECM 1.0; Jan 9, 2008
 */
public class ReportDAOImpl
extends com.mckesson.eig.reports.dao.AbstractReportDAO {

    /**
     * @see com.mckesson.eig.reports.dao.ReportDAO
     *  #generateReport(java.lang.String, java.util.Map, java.io.OutputStream)
     */
    public void generateReport(String reportID, Map params,
                               OutputStream outputStream)
                               throws IOException {

        PrintStream out = new PrintStream(outputStream, true);
        Session session = getSessionFactory().getCurrentSession();
        processQuery(session.createQuery("select w.worklistID from "
                        + "com.mckesson.eig.reports.api.test.Worklist as w"));
        out.println("Report Header,Report Header,Report Header,Report Header,Report Header");
        out.println(StringUtilities.getCSV("Report\" Value")
                                             + ',' + StringUtilities.getCSV("Report\n Value")
                                             + ',' + StringUtilities.getCSV("Report,Value")
                                             + ',' + StringUtilities.getCSV(null)
                                             + ',' + StringUtilities.getCSV("Report Value"));
    }
}
