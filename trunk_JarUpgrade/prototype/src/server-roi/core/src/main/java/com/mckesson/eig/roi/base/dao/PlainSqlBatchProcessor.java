/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/
package com.mckesson.eig.roi.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

/**
 * @author Shah Mohamed.N
 * @date   Aug 7, 2012
 * @since  Aug 7, 2012
 */
public abstract class PlainSqlBatchProcessor {

    private PreparedStatement _pStmt;
    private long _currentBatchSize;
    private static final Log LOG = LogFactory.getLogger(PlainSqlBatchProcessor.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    
    public void createPreparedStatement(Session session, final String sqlQuery) {
        
        /**
         * the doWork method is used to create the prepared statement with in the 
         * same transaction.
         */
        session.doWork(new Work() {

            @Override
            public void execute(Connection conn) throws SQLException {
                _pStmt = conn.prepareStatement(sqlQuery);
            }
        });
    }

    
    public <T extends BaseModel> void execute(List<T> batchItems, String sql) 
    throws SQLException {
        
        if (DO_DEBUG) {
            LOG.debug("Batch Execution starts >> SQL: " + sql);
        }
      
        //Create Prepared statement _pStmt
        createPreparedStatement(getSession(), sql);
        
        long totalBatch = 0;
        for (T  t : batchItems) {
            addToBatch(_pStmt, t);
            
            _pStmt.addBatch();
            _currentBatchSize++;
            
            
            if (_currentBatchSize == getBatchSize()) {
                
                /**
                 * Execute the batch if it reaches a limit on each batch size.
                 */
                _pStmt.executeBatch();
                
                // Clear the batch to free memory.
                _pStmt.clearBatch();
                
                totalBatch++;
                _currentBatchSize = 0;
                
            }
        }
        
        if (_currentBatchSize > 0 && _currentBatchSize < getBatchSize()) {
            _pStmt.executeBatch();
            _pStmt.clearBatch();
            
        }
        
        _pStmt.close();
        
        if (DO_DEBUG) {
            LOG.debug("Batch Execution Successfully Completed: Total Batch:" + totalBatch 
                       + ", Total BatchItems:" + batchItems.size());
        }
        
    }
    
    protected abstract long getBatchSize();
    protected abstract Session getSession();
    protected abstract <T extends BaseModel> void addToBatch(PreparedStatement pStmt, T object)
                                                                        throws SQLException;

}
