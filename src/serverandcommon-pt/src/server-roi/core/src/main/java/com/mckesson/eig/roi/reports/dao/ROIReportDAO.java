/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/

package com.mckesson.eig.roi.reports.dao;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.mckesson.eig.reports.dao.ReportDAO;
import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.hpf.model.User;

/**
*
* @author OFS
* @date   Oct 14, 2008
* @since  HPF 13.1 [ROI]; Oct 14, 2008
*/
public interface ROIReportDAO
extends ROIDAO, ReportDAO {

    /**
     * This method queries the report data based on the report ID, params and writes it into the
     * output stream.
     *
     * @param reportID
     * @param params
     * @param outputStream
     */
    @SuppressWarnings("unchecked") //Not supported by framework
    void generateReport(String reportID, Map params, OutputStream outputStream) throws IOException;

    User retrieveUserDetails(int userInstanceId);
}
