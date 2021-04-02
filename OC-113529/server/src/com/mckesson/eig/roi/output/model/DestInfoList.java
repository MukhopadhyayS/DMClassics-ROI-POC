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

package com.mckesson.eig.roi.output.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains destination information list
 * @author Karthik Easwaran(OFS)
 * @author Shahm Nattarshah.
 *
 */
public class DestInfoList {

    /** This holds the list of destination information list*/
    private List<DestInfo> _destInfoList = new ArrayList<DestInfo>();

    public List<DestInfo> getDestInfoList() {
        return _destInfoList;
    }

    public void setDestInfoList(List<DestInfo> destInfoList) {
        _destInfoList = destInfoList;
    }

    
    
}
