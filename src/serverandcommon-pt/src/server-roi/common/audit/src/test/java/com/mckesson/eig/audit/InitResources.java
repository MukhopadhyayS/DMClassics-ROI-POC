/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Corporation and/or one of its subsidiaries and is protected 
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.audit;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.sourceforge.jtds.jdbcx.JtdsDataSource;
import oracle.jdbc.pool.OracleDataSource;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.mockejb.jndi.MockContextFactory;

//import com.mckesson.eig.utility.log.Log;
//import com.mckesson.eig.utility.log.LogFactory;

public final class InitResources {

    /**
     * Reference to the singleton object
     */
    private static InitResources _init;

    /**
     * Instance which holds the Logger for this class.
     */
    //private static final Log LOG = LogFactory.getLogger(InitResources.class);

    /**
     * Variable used for Oracle Connection.
     */
    private static final Object DATABASE_VENDOR_ORACLE = "Oracle";

    /**
     * Variable used for Sql Connection.
     */
    private static final Object DATABASE_VENDOR_SQLSERVER = "SQL Server";
    private Context _ctx = null;

    /**
     * Private constructor to create a singleton object
     * 
     */
    private InitResources() {
    }

    /**
     * Creates a singleton class object for the first time. Everyother
     * invokation returns the same reference
     * 
     * @return instance of this Class.
     */
    public static InitResources getInstance() {
        synchronized (InitResources.class) {
            if (_init == null) {
                _init = new InitResources();
            }
        }
        return _init;
    }

    /**
     * This method registers the datasource with a JNDI name.Sets up our mock
     * container, JNDI context and deploy the beans that we need.
     * 
     * @throws NamingException
     */
    public void initialiseContainer() throws NamingException, JMSException {
    
        // Setup the JNDI environment to use the MockEJB context factory
        MockContextFactory.setAsInitial();
        _ctx = new InitialContext();
    
        // Create the audit queue
        ConnectionFactory factory = new ActiveMQConnectionFactory(
                "vm://localhost");
//        _ctx.rebind("java:/ConnectionFactory", factory);
        _ctx.rebind("java:activemq/QueueConnectionFactory", factory);
        Queue queue = new ActiveMQQueue("queue.eigAudit");
        _ctx.rebind("java:comp/env/queue/eigAudit", queue);
    
        // Set the datasource
        try {
            if (ResourceData.getDatabaseVendor().equals(DATABASE_VENDOR_SQLSERVER)) {
    
                JtdsDataSource dataSource = new JtdsDataSource();
                dataSource.setDatabaseName(ResourceData.getDatabase());
                dataSource.setServerName(ResourceData.getDatabaseServer());
                dataSource.setUser(ResourceData.getDatabaseUser());
                dataSource.setPassword(ResourceData.getDatabasePassword());
                _ctx.rebind(ResourceData.getJndiQspDatasource(), dataSource);
            } else if (ResourceData.getDatabaseVendor().equals(DATABASE_VENDOR_ORACLE)) {
    
                OracleDataSource dataSource = new OracleDataSource();
                dataSource.setDataSourceName("connMyOracle");
                dataSource.setDatabaseName(ResourceData.getOraDatabase());
                dataSource.setServerName(ResourceData.getOraDatabaseServer());
                dataSource.setPortNumber(Integer
                        .parseInt(ResourceData.getOraDatabasePort()));
                dataSource.setUser(ResourceData.getOraDatabaseUser());
                dataSource.setPassword(ResourceData.getOraDatabasePassword());
                dataSource.setDriverType("oracle.jdbc.driver.OracleDriver");
                dataSource.setURL(ResourceData.getOraDatabaseUrl());
                _ctx.rebind(ResourceData.getJndiQspDatasource(), dataSource);
            }
        } catch (Exception e) {
            //LOG.error("initialiseContainer(),Exception in setting Datasource["+ e + "]");
        }
    }

    public void initialiseContainerRoi() throws NamingException, JMSException {
        
        // Setup the JNDI environment to use the MockEJB context factory
        MockContextFactory.setAsInitial();
        _ctx = new InitialContext();
    
        // Create the audit queue
        ConnectionFactory factory = new ActiveMQConnectionFactory(
                "vm://localhost");
        _ctx.rebind("java:/ConnectionFactory", factory);
        Queue queue = new ActiveMQQueue("queue.eigAudit");
        _ctx.rebind("java:comp/env/queue/eigAudit", queue);
    
        // Set the datasource
        try {
            JtdsDataSource dataSource = new JtdsDataSource();
            dataSource.setDatabaseName(ResourceData.getRoiDatabase());
            dataSource.setServerName(ResourceData.getRoiDatabaseServer());
            dataSource.setUser(ResourceData.getRoiDatabaseUser());
            dataSource.setPassword(ResourceData.getRoiDatabasePassword());
            _ctx.rebind(ResourceData.getRoiDatasource(), dataSource);
        } catch (Exception e) {
            //LOG.error("initialiseContainer(),Exception in setting Datasource[" + e + "]");
        }
    }
}
