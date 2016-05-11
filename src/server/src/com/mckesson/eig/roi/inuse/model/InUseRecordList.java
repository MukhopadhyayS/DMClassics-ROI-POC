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

package com.mckesson.eig.roi.inuse.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author OFS
 * @date   Dec 8, 2008
 * @since  ROI HPF 13.1
 */
public class InUseRecordList implements Serializable {

    private List<InUseRecord> _list;

    public InUseRecordList() { }
    public InUseRecordList(List<InUseRecord> list) {
        _list = list;
    }

    public List<InUseRecord> getList() {
        return _list;
    }

    public void setList(List<InUseRecord> list) {
        _list = list;
    }
}
