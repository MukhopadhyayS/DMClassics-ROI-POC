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
package com.mckesson.eig.workflow.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mckesson.eig.workflow.api.ProcessVariable;

/**
 * Dummy implementation for ContentProcessVariableValues
 *
 * @author eo837ew
 *
 */
public class MockContentProcessVariablePluginService implements ProcessVariablePluginService {

    public List<ProcessVariable> getProcessVariableValues(
            List<ProcessVariable> variableList) {

        List<ProcessVariable> outVariableList = new ArrayList<ProcessVariable>();

        Iterator<ProcessVariable> pvList = variableList.iterator();

        while (pvList.hasNext()) {
            ProcessVariable pv = pvList.next();
            String ts = pv.getValueTypeSubtype();
            String subtype = ts.substring(ts.indexOf(":") + 1);
            if ("boolean".equals(subtype)) {
                pv.setValue("true");
            } else if (("int".equals(subtype))) {
                pv.setValue("1");
            } else if (("long".equals(subtype))) {
                pv.setValue("1");
            } else if (("String".equals(subtype))) {
                pv.setValue("value");
            } else {
                pv.setValue("value");
            }

            outVariableList.add(pv);
        }

        return outVariableList;
    }

}
