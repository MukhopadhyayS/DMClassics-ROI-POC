package com.mckesson.eig.roi.base.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.mckesson.eig.utility.util.StringUtilities;

public class SearchCriteria implements Serializable {
    
    private static final String WHERE_CLAUSE = " where ";
    private static final String AND_CLAUSE = " and ";
    
    private int _maxCount;
    private List<SearchCondition> _conditions;

    public int getMaxCount() {
        return _maxCount;
    }
    
    public void setMaxCount(int maxCount) {
        _maxCount = maxCount; 
    }

    public List<SearchCondition> getConditions() { 
        return _conditions;
    }
    
    public void setConditions(List<SearchCondition> conditions) { 
        _conditions = conditions; 
    }
    
    public void addCondition(SearchCondition condition) { 
        if(_conditions == null) {
            _conditions = new ArrayList<SearchCondition>();
        }
        _conditions.add(condition); 
    }


    private void putConditionClause(SearchCondition condition, StringBuffer clause, HashMap<String, String> parameters) {
        
        clause.append(condition.getKey());
        clause.append(" " + condition.getOperation());
        
        if (SearchCondition.OPERATION.In.toString().equalsIgnoreCase(condition.getOperation())) {
            
             String key = putParameters(parameters, condition.getValue(), condition.getKey());
             clause.append(" (");
             clause.append(":");
             clause.append(key);
             clause.append(") ");
             
        } else if (SearchCondition.OPERATION.Like.toString().equalsIgnoreCase(condition.getOperation())) {
            
            String key = putParameters(parameters, condition.getValue() + "%", condition.getKey());
            clause.append(" (");
            clause.append(":");
            clause.append(key);
            clause.append(") ");
            
        } else if (SearchCondition.OPERATION.Between.toString().equalsIgnoreCase(condition.getOperation()))  {
            
            String keyFrom = putParameters(parameters, condition.getValue(), condition.getKey()+"valueFrom");
            String keyTo = putParameters(parameters, condition.getValueTo(), condition.getKey()+"ValueTo");
            
                   clause.append(" :")
                  .append(keyFrom)
                  .append(" AND :")
                  .append(keyTo)
                  .append(" ");
        } else  {
            
            String key = putParameters(parameters, condition.getValue(), condition.getKey());
            clause.append(" :")
            .append(key)
            .append(" ");
        }
        
    }

    /**
     * This method will format input string to be supplied to IN condition
     * @param value Input string ex.(A,B,C)
     * @return Formatted output string ex.('A','B','C')
     */
    private String formatParam(String value) {

        StringBuffer values = new StringBuffer(" (");

        if (!StringUtilities.isEmpty(value)) {
            for (String s : value.split(",")) {
                values.append("'").append(s).append("'").append(",");
            }
        }
        values.deleteCharAt(values.length() - 1).append(") ");

        return values.toString();
    }
    
    public String getWhereClause(HashMap<String, String> parameters)  {
        if (getConditions() == null || getConditions().size() == 0) {
            return "";
        }
        StringBuffer result = new StringBuffer(WHERE_CLAUSE);
        int i = 0;
        for(SearchCondition c : getConditions()) {
            putConditionClause(c, result, parameters);
            i++;
            if ( i < getConditions().size()) {
                result.append(AND_CLAUSE);
            }
        }
        return result.toString();
    }
    
    
    private String putParameters(HashMap<String, String> map, String value, String key) {
        String updatedKey  = key + (UUID.randomUUID().toString().replace("-", "_"));
        map.put(updatedKey, value);
        return updatedKey;
        
    }
}
