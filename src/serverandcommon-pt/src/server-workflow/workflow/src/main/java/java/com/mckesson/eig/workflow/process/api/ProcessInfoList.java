/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries. All
 * Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of McKesson
 * Information Solutions and is protected under United States and international
 * copyright and other intellectual property laws. Use, disclosure,
 * reproduction, modification, distribution, or storage in a retrieval system in
 * any form or by any means is prohibited without the prior express written
 * permission of McKesson Information Solutions.
 */
package com.mckesson.eig.workflow.process.api;
/**
 * This DTO class reperesents  a list of Process info objects
 *
 * @author McKesson
 * @version 1.0
 * @created 19-Feb-2009 02:14:59 PM
 */
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ProcessInfoList")
public class ProcessInfoList {

    private List<ProcessInfo> _processInfoList;

    public ProcessInfoList() {
        _processInfoList = new ArrayList<ProcessInfo>();
    }

    public ProcessInfoList(List<ProcessInfo> value) {
        _processInfoList = value;
    }

    public List<ProcessInfo> getProcessList() {
        return _processInfoList;
    }

    @XmlElement(name = "processInfoList", type = ProcessInfo.class)
    public void setProcessList(List<ProcessInfo> value) {
        _processInfoList = value;
    }

    public void finalize() throws Throwable {

    }

    /**
     * This method returns a string representation of the Process List that contains
     * Process objects.
     *
     * @return strBuff
     */
    public String toString() {

        StringBuffer theProcessList = new StringBuffer();
        theProcessList.append("ProcessInfoList[");
        if (_processInfoList != null) {
            int size = _processInfoList.size();
            for (int i = 0; i < size; i++) {
                theProcessList.append(" " + _processInfoList.get(i).toString() + " ");
            }
        }
        theProcessList.append("]");
        return theProcessList.toString();
   }
}
