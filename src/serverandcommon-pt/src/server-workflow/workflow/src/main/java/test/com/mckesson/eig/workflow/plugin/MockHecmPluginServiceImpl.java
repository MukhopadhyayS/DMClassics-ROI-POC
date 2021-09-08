package com.mckesson.eig.workflow.plugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mckesson.eig.workflow.api.ProcessVariable;
import com.mckesson.eig.workflow.service.ProcessVariablePluginService;

public class MockHecmPluginServiceImpl implements ProcessVariablePluginService {

    public List<ProcessVariable> getProcessVariableValues(
            List<ProcessVariable> variableList) {

        List<ProcessVariable> outVariableList = new ArrayList<ProcessVariable>();

        Iterator<ProcessVariable> pvList = variableList.iterator();

        while (pvList.hasNext()) {
            ProcessVariable pv = pvList.next();
            pv.setValue(pv.getValueTypeSubtype() + "value");
            outVariableList.add(pv);
        }

        return outVariableList;
    }


}
