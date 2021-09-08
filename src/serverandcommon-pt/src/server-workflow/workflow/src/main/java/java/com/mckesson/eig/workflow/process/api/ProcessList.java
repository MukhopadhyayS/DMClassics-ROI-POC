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
 * This class represents  a list of Process objects 
 * 
 * Model  class representing a list of workflow process objects 
 * designed and installed in the workflow engine

 * @author McKesson
 * @version 1.0
 * @created 22-Jan-2009 10:33:59 AM
 */
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ProcessList")
public class ProcessList {

    private List<Process> _processList;
    
    public ProcessList() {
        _processList = new ArrayList<Process>();
    }

    public ProcessList(List<Process> value) {
        _processList = value;
    }

    public List<Process> getProcessList() {
        return _processList;
    }
    
    @XmlElement(name = "processList", type = Process.class)
    public void setProcessList(List<Process> value) {
        _processList = value;
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
        theProcessList.append("ProcessList["); 
        if (_processList != null) {
            int size = _processList.size();
            for (int i = 0; i < size; i++) {
                theProcessList.append(" " + _processList.get(i).toString() + " ");
            }
        }
        theProcessList.append("]");
        return theProcessList.toString();
   }
}
