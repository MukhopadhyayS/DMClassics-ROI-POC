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

package com.mckesson.eig.workflow.engine.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.workflow.api.ProcessVariable;
import com.mckesson.eig.workflow.service.ProcessVariablePluginService;

/**
 * Helper class for expressions.
 *
 * @author McKesson
 * @date   March 27, 2009
 * @since  HECM 2.0; March 27, 2009
 */
public class ExpressionHelper {

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( ExpressionHelper.class);

    /**
     * Represents starting part of a variable
     */
    private static final String VAR_START_STRING = "contextInstance.variables['";

    /**
     * Represents ending part of a variable
     */
    private static final String VAR_END_STRING = "']";

    /**
     * Represents length of contextInstance.variables['
     */
    private static final int VAR_START_INDEX_LENGTH = 27;

    /**
     * Represents length of ']
     */
    private static final int VAR_END_INDEX_LENGTH = 2;

    /**
     * Variable subtype used for this expression helper.
     */
    private String _variableSubtype = null;


    public ExpressionHelper(String variableSubtype) {

        _variableSubtype = variableSubtype;
    }
    /**
     * Resolves conditions in an expression through two step processing.
     *
     * @param expression
     * @return String - resolved expression.
     */
    public String resolveExpression(String expression) {

        /**
         * Calculate list of conditions that need additional processing.
         * group non basic types into multiple lists of ProcessVariable based on type.
         */
        Map<String, List<ProcessVariable>> groupedVariables =
            getProcessVariableMap(expression);

        // resolve variables
        String resolvedExpression = resolveVariables(expression, groupedVariables);

        return resolvedExpression;
    }

    /**
     * Processes expression to extract process variables needing lookup against
     * external systems.
     *
     * Returns ProcessVariable map with keys and ProcessVariable list.
     *
     * @param expression
     * @return Map<String, List<ProcessVariable>>
     */
    public Map<String, List<ProcessVariable>>
            getProcessVariableMap(String expression) {

        // See if it's an empty String or null
        if (expression == null || expression.length() == 0) {
          return null;
        }

        ArrayList<String> variables = extractVariables(expression);

        /**
         * If there are no variables to resolve return null
         */
        if (variables == null || variables.size() == 0) {
            return null;
        }

        Map<String, List<ProcessVariable>> groupedVariables = groupVariables(variables);

        return groupedVariables;
    }

    /**
     * Resolves variables by invoking api's of ProcessVariable owning systems.
     *
     * @param expression
     * @param groupedVariables
     * @return String - Resolved expression.
     */
    public String resolveVariables(String expression,
                                   Map<String, List<ProcessVariable>> groupedVariables) {
        /**
         * For each map constructed, lookup spring bean for ProcessVariableValues
         * dynamically. Then, invoke getProcessVariableValues
         */
        List<ProcessVariable> resolvedVariables = null;
        Set<String> keys = groupedVariables.keySet();
        for (Iterator<String> itr = keys.iterator(); itr.hasNext();) {
            String pvType = itr.next();
            ProcessVariablePluginService pvResolver =
              (ProcessVariablePluginService) SpringUtilities.getInstance().getBeanFactory().getBean(
                    pvType + "ProcessVariablePluginService");

            LOG.debug("Invoking plugin: " + pvType + "ProcessVariablePluginService bean");
            LOG.debug("Request variable size = " + groupedVariables.get(pvType).size());
            resolvedVariables =
                pvResolver.getProcessVariableValues(groupedVariables.get(pvType));
            LOG.debug("Plugin: " + pvType + "ProcessVariablePluginService bean" + " responded.");
            LOG.debug("Response variable size = " + resolvedVariables.size());

            /**
             * use resolvedVariables to update expression with actual values.
             */
            for (Iterator<ProcessVariable> pvItr = resolvedVariables.iterator();
                    pvItr.hasNext();) {
                ProcessVariable pv = pvItr.next();
                expression = expression.replace(VAR_START_STRING
                        + pv.toKeyString() + VAR_END_STRING,
                        "'" + pv.getValue() + "'");
            }
        }
        return expression;
    }

    /**
     * Assembles variable list. Variables are wrapped between contextInstance.variables[' and ']
     *
     * @param expression
     * @return
     */
    public ArrayList<String> extractVariables(String expression) {

        if (expression == null || expression.length() == 0) {
            return null;
        }

        ArrayList<String> variableList = new ArrayList<String>();

        int startIndex = -1;
        int endIndex = -1;
        boolean moreExists = true;

        while (moreExists) {
            startIndex =  expression.indexOf(VAR_START_STRING);
            if (startIndex != -1) {
                endIndex = expression.indexOf(VAR_END_STRING);
            } else {
                moreExists = false;
            }

            if (startIndex != -1 && endIndex != 1) {
                String variable = expression.substring(startIndex + VAR_START_INDEX_LENGTH,
                                                       endIndex);
                variableList.add(variable);
                expression = expression.substring(endIndex + VAR_END_INDEX_LENGTH);
            }
        }

        return variableList;
    }

    /**
     * Groups variables based on
     * <<ProcessId>>.<<ActivityName>>.<<VariableName>>.<<VariableType:VariableSubtype>>
     * .<<ValueType:ValueSubtype>>.<<index>>
     *
     * @param variables
     * @return
     */
    public Map<String, List<ProcessVariable>> groupVariables(ArrayList<String> variables) {

        Map<String, List<ProcessVariable>> variableMap =
            new HashMap<String, List<ProcessVariable>>();

        Iterator<String> itr = variables.iterator();

        while (itr.hasNext()) {

            String variable = itr.next();
            String variableCopy = variable;
            ProcessVariable pv = new ProcessVariable();
            //store variableType for later use in map
            String variableType = "";

            try {
            pv.setProcessId(variable.substring(0, variable.indexOf(".")));
            variable = variable.substring(variable.indexOf(".") + 1);

            pv.setActivityName(variable.substring(0, variable.indexOf(".")));
            variable = variable.substring(variable.indexOf(".") + 1);

            pv.setVariableName(variable.substring(0, variable.indexOf(".")));
            variable = variable.substring(variable.indexOf(".") + 1);

            //store variableTypeSubtype for later use in map
            variableType = variable.substring(0, variable.indexOf("."));
            pv.setVariableTypeSubtype(variableType + ":" + _variableSubtype);
            variable = variable.substring(variable.indexOf(".") + 1);

            pv.setValueTypeSubtype(variable);
                LOG.debug("Variable " + variableCopy + " applicable for resolving");
            } catch (Exception e) {
                LOG.debug("Variable " + variableCopy + " not applicable for resolving");
            }

            List<ProcessVariable> pvList = variableMap.get(variableType);
            if (pvList == null) {
                /**
                 * Basic data types do not need to be resolved.
                 */
                if (!("int".equalsIgnoreCase(variableType)
                        || "long".equalsIgnoreCase(variableType)
                        || "String".equalsIgnoreCase(variableType)
                        || "".equalsIgnoreCase(variableType)
                        || "Date".equalsIgnoreCase(variableType))) {
                    List<ProcessVariable> newPVList = new ArrayList<ProcessVariable>();
                    //create new entry in map for variable type
                    variableMap.put(variableType, newPVList);
                    pvList = newPVList;
                }
            }

            //add proces variable to it's list
            if (pvList != null) {
                pvList.add(pv);
            }
        }
        return variableMap;
    }
}
