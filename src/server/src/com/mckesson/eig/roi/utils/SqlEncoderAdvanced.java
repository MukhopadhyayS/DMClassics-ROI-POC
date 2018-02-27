package com.mckesson.eig.roi.utils;

import org.hibernate.Query;
import org.hibernate.Session;

import com.mckesson.dm.core.common.util.sanitize.EncoderUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

public final class SqlEncoderAdvanced {
    
    public String encodeForSql(String queryString) {
        if (queryString == null) {
            return null;
        }
        
        return EncoderUtilities.encodeForSQL(queryString);
    }
    
    public String[] encodeForSql(String[] queryString) {
        if (queryString == null) {
            return null;
        }
        
        for (int i = 0; i < queryString.length; i++) {
        	 queryString[i] = EncoderUtilities.encodeForSQL(queryString[i]); 
                }
        
        return queryString;
    }
    
    public String safeEncodeForSql(String queryString) {
        return encodeForSql(StringUtilities.safe(queryString));
    }
    
    public Query createEncodedQuery(Session session, String queryString) {
        if (session == null || StringUtilities.isEmpty(queryString)) {
            return null;
        }
        
        return session.createSQLQuery(encodeForSql(queryString));
    }
}
