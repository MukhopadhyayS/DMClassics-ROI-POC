package com.mckesson.eig.roi.utils;

import org.hibernate.Query;
import org.hibernate.Session;
import org.owasp.esapi.ESAPI;

import com.mckesson.eig.utility.util.StringUtilities;

public final class SqlEncoderAdvanced {
private MSSQLCodecAdvanced _codec = new MSSQLCodecAdvanced();
    
    public String encodeForSql(String queryString) {
        if (queryString == null) {
            return null;
        }
        
        return ESAPI.encoder().encodeForSQL(_codec, queryString);
    }
    
    public String[] encodeForSql(String[] queryString) {
        if (queryString == null) {
            return null;
        }
        
        for (int i = 0; i < queryString.length; i++) {
        	 queryString[i] = ESAPI.encoder().encodeForSQL(_codec,  queryString[i]); 
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
