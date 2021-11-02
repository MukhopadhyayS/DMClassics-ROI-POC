
package com.mckesson.eig.roi.base.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.base.model.SearchCondition;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 * <p>Java class for SearchCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="maxCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="conditions" type="{urn:eig.mckesson.com}SearchCondition" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchCriteria", propOrder = {
    "maxCount",
    "conditions"
})
public class SearchCriteria implements Serializable{
    
    private static final String WHERE_CLAUSE = " where ";
    private static final String AND_CLAUSE = " and ";

    protected int maxCount;
    protected List<SearchCondition> conditions;

    /**
     * Gets the value of the maxCount property.
     * 
     */
    public int getMaxCount() {
        return maxCount;
    }

    /**
     * Sets the value of the maxCount property.
     * 
     */
    public void setMaxCount(int value) {
        this.maxCount = value;
    }

    /**
     * Gets the value of the conditions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the conditions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConditions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SearchCondition }
     * 
     * 
     */
    public List<SearchCondition> getConditions() {
        if (conditions == null) {
            conditions = new ArrayList<SearchCondition>();
        }
        return this.conditions;
    }
    
    public void addCondition(SearchCondition condition) { 
        if(conditions == null) {
            conditions = new ArrayList<SearchCondition>();
        }
        conditions.add(condition); 
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
