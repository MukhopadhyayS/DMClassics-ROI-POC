package com.mckesson.eig.utility.database;

import com.mckesson.eig.utility.testing.UnitTest;

public class TestDBResult extends UnitTest {
    
    public static final int OBJ_5010 = 5010;
    public static final int OBJ_5129 = 5129;
    public static final String NULL_VALUE = "null";
    public static final int TWO = 2;
    public static final String KEY = "1:OBJ_ID";

    public TestDBResult() {
        super();
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testAddResultDefaultConstructor() {
        DBResult result = new DBResult();
        result.setRowsAffected(1);
        result.addResult(1, "OBJ_ID", "BIGINT", OBJ_5129);
        result.incrementRowsAffected();
        result.addResult(2, "PARENT", "BIGINT", OBJ_5010);
        assertEquals("OBJ_ID", result.getResultColumnNames().get(0));
        assertEquals("PARENT", result.getResultColumnNames().get(1));
        assertEquals("BIGINT", result.getResultColumnTypes().get(0));
        assertEquals("BIGINT", result.getResultColumnTypes().get(1));
        assertEquals(OBJ_5010, result.getResult(2, "PARENT"));
        assertEquals(TWO, result.getRowsAffected());
    }
    
    public void testAddResultRowsAffectedConstructor() {
        DBResult result = new DBResult(2);
        result.addResult(1, "OBJ_ID", "BIGINT", OBJ_5129);
        result.addResult(2, "PARENT", "BIGINT", OBJ_5010);
        assertEquals("OBJ_ID", result.getResultColumnNames().get(0));
        assertEquals("PARENT", result.getResultColumnNames().get(1));
        assertEquals("BIGINT", result.getResultColumnTypes().get(0));
        assertEquals("BIGINT", result.getResultColumnTypes().get(1));
        assertEquals(OBJ_5010, result.getResult(2, "PARENT"));
        assertEquals(TWO, result.getRowsAffected());
        assertEquals(result.getResultMap().get(KEY), OBJ_5129);
    }
}
