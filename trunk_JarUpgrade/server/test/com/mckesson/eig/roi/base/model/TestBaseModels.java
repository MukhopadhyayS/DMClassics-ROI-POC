package com.mckesson.eig.roi.base.model;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.mckesson.eig.roi.base.model.SearchCondition.OPERATION;
import com.mckesson.eig.roi.base.model.SearchLoV.CONDITION;
import com.mckesson.eig.roi.base.model.SearchLoV.DATATYPE;
import com.mckesson.eig.utility.util.CollectionUtilities;

public class TestBaseModels 
extends TestCase {
    
    private static final int MAX_COUNT = 100;
    
    public void testSetUp()
            throws Exception {
                setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testToSearchAudit() {
        
        try {
            
            SearchLoVCriteria slvCriteria = new SearchLoVCriteria();
            SearchLoV searchLov = new SearchLoV();
            searchLov.setChild(MAX_COUNT);
            searchLov.setCondition(null);
            searchLov.setCreatedBy(1);
            searchLov.setDataType(null);
            searchLov.setDomainSource("source");
            searchLov.setDomainType("Domain Type");
            searchLov.setId(1);
            searchLov.setKey("MAX_COUNT1");
            searchLov.setOrgId(2);
            searchLov.setParent(1);
            searchLov.setValue("Patient");
            searchLov.setValueTo("Value To");
            
            List<SearchLoV> lovs = new ArrayList<SearchLoV>();
            lovs.add(searchLov);
            slvCriteria.setLovs(lovs);
            slvCriteria.setMaxCount(MAX_COUNT);
            
            String message = slvCriteria.toSearchAudit();
            
            assertNotNull(message);
            assertTrue(message.contains("Patient"));
            assertEquals(MAX_COUNT, slvCriteria.getMaxCount());
        } catch (Exception e) {
            fail("ToSearchAudit should not thrown exception. " + e.getMessage());
        }
        
    }
    
    public void testToSearchAuditWithAllInputs() {
        
        try {
            
            SearchLoVCriteria slvCriteria = new SearchLoVCriteria();
            SearchLoV searchLov = new SearchLoV();
            searchLov.setChild(MAX_COUNT);
            searchLov.setCondition(CONDITION.AtLeast.name());
            searchLov.setCreatedBy(1);
            searchLov.setDataType(DATATYPE.Integer.name());
            searchLov.setDomainSource("source");
            searchLov.setDomainType("Domain Type");
            searchLov.setId(1);
            searchLov.setKey("MAX_COUNT1");
            searchLov.setOrgId(2);
            searchLov.setParent(1);
            searchLov.setValue("Patient");
            searchLov.setValueTo("Patient Name");
            
            List<SearchLoV> lovs = new ArrayList<SearchLoV>();
            lovs.add(searchLov);
            slvCriteria.setLovs(lovs);
            slvCriteria.setMaxCount(MAX_COUNT);
            
            String message = slvCriteria.toSearchAudit();
            
            assertNotNull(message);
            assertTrue(message.contains("Patient"));
            assertEquals(MAX_COUNT, slvCriteria.getMaxCount());
            
            if (!CollectionUtilities.isEmpty(slvCriteria.getLovs())) {                
                assertEquals("int", slvCriteria.getLovs().get(0).getDataType());
                assertEquals(">=", slvCriteria.getLovs().get(0).getCondition());
                assertEquals("Patient Name", slvCriteria.getLovs().get(0).getValueTo());
            }
            
        } catch (Exception e) {
            fail("ToSearchAuditWithAllInputs should not thrown exception. " + e.getMessage());
        }
        
    }
    
    public void testSearchCriteria() {
        
        try {
            
            SearchCondition searchCondition = constrcutSearchCondition();
            List<SearchCondition> conditions = new ArrayList<SearchCondition>();
            searchCondition.setOperation(OPERATION.In.name());
            conditions.add(searchCondition);
            SearchCriteria searchCriteria = constructSearchCriteria(conditions);
            searchCriteria.addCondition(searchCondition);
            
            assertEquals(MAX_COUNT, searchCriteria.getMaxCount());
            if (!CollectionUtilities.isEmpty(searchCriteria.getConditions())) {
                assertEquals("freeformfacility", searchCriteria.getConditions().get(0).getKey());
                assertEquals("Northern", searchCriteria.getConditions().get(0).getValue());
                assertEquals("Northern AD", searchCriteria.getConditions().get(0).getValueTo());
                assertEquals(OPERATION.In.toString(), searchCriteria.getConditions().get(0).getOperation());
            }
            String whereCluse = searchCriteria.getWhereClause();
            assertNotNull(whereCluse);
            
        } catch (Exception e) {
            fail("SearchCriteria should not thrown exception. " + e.getMessage());
        }
        
    }
    
    public void testSearchCriteriaWithSearchConditionLike() {
        
        try {
            
            SearchCondition searchCondition = constrcutSearchCondition();
            searchCondition.setOperation(OPERATION.Like.name());
            List<SearchCondition> conditions = new ArrayList<SearchCondition>();
            conditions.add(searchCondition);
            SearchCriteria searchCriteria = constructSearchCriteria(conditions);
            
            assertEquals(MAX_COUNT, searchCriteria.getMaxCount());
            if (!CollectionUtilities.isEmpty(searchCriteria.getConditions())) {
                assertEquals("freeformfacility", searchCriteria.getConditions().get(0).getKey());
                assertEquals("Northern", searchCriteria.getConditions().get(0).getValue());
                assertEquals("Northern AD", searchCriteria.getConditions().get(0).getValueTo());
                assertEquals(OPERATION.Like.toString(), searchCriteria.getConditions().get(0).getOperation());
            }
            
            String whereCluse = searchCriteria.getWhereClause();
            assertNotNull(whereCluse);
            
        } catch (Exception e) {
            fail("SearchCriteriaWithSearchConditionLike should not thrown exception. " + e.getMessage());
        }
        
    }
    
    public void testSearchCriteriaWithSearchConditionBetween() {
        
        try {
            
            SearchCondition searchCondition = constrcutSearchCondition();
            searchCondition.setOperation(OPERATION.Between.name());
            SearchCondition searchCondition2 = constrcutSearchCondition();
            searchCondition2.setOperation(OPERATION.Like.name());
            SearchCondition searchCondition3 = constrcutSearchCondition();
            searchCondition3.setOperation(OPERATION.Equal.name());
            List<SearchCondition> conditions = new ArrayList<SearchCondition>();
            conditions.add(searchCondition);
            conditions.add(searchCondition2);
            conditions.add(searchCondition3);
            SearchCriteria searchCriteria = constructSearchCriteria(conditions);
            searchCriteria.addCondition(searchCondition);
            
            assertEquals(MAX_COUNT, searchCriteria.getMaxCount());
            if (!CollectionUtilities.isEmpty(searchCriteria.getConditions())) {
                assertEquals("freeformfacility", searchCriteria.getConditions().get(0).getKey());
                assertEquals("Northern", searchCriteria.getConditions().get(0).getValue());
                assertEquals("Northern AD", searchCriteria.getConditions().get(0).getValueTo());
            }
            
            String whereCluse = searchCriteria.getWhereClause();
            assertNotNull(whereCluse);
            
        } catch (Exception e) {
            fail("SearchCriteriaWithSearchConditionBetween should not thrown exception. " + e.getMessage());
        }
        
    }
    
    public void testROILovModel() {
        
        try {
            
            ROILoV roiLov = new ROILoV();
            roiLov.setChild(1);
            roiLov.setCreatedBy(1);
            roiLov.setDomainSource("source");
            roiLov.setDomainType("type");
            roiLov.setId(100);
            roiLov.setKey("roi");
            roiLov.setOrgId(101);
            roiLov.setParent(2);
            roiLov.setValue("Patient");
            
            assertEquals(1, roiLov.getChild());
            assertEquals(1, roiLov.getCreatedBy());
            assertEquals("source", roiLov.getDomainSource());
            assertEquals("type", roiLov.getDomainType());
            assertEquals(100, roiLov.getId());
            assertEquals("roi", roiLov.getKey());
            assertEquals(101, roiLov.getOrgId());
            assertEquals(2, roiLov.getParent());
            assertEquals("Patient", roiLov.getValue());
            
        } catch (Exception e) {
            fail("ROILovModel should not thrown exception. " + e.getMessage());
        }
    }
    
    public void testROILovModelWithMinimumInput() {
        
        try {
            
            ROILoV roiLov = new ROILoV("Source", "roi", "Patient");
            
            ROILoV lov = new ROILoV();
            lov.setDomainSource("source");
            lov.setKey("roi");
            lov.setValue("Patient");
            
            assertTrue(roiLov.equals(lov));
            assertNotNull(roiLov.hashCode());            
            
        } catch (Exception e) {
            fail("ROILovModelWithMinimumInput should not thrown exception. " + e.getMessage());
        }
    }
    
    private SearchCondition constrcutSearchCondition() {
        SearchCondition searchCondition = new SearchCondition();
        searchCondition.setKey("freeformfacility");
        searchCondition.setValue("Northern");
        searchCondition.setValueTo("Northern AD");
        return searchCondition;
    }
    
    private SearchCriteria constructSearchCriteria(List<SearchCondition> conditions) {
        SearchCriteria searchCriteria = new SearchCriteria();
        List<SearchCondition> searchConditions = new ArrayList<SearchCondition>();
        searchConditions.addAll(conditions);
        searchCriteria.setConditions(searchConditions);
        searchCriteria.setMaxCount(MAX_COUNT);
        return searchCriteria;
    }
    
    

}
