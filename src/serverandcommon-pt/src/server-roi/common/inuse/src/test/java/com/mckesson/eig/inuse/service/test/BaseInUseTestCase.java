/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.inuse.service.test;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

import net.sourceforge.jtds.jdbcx.JtdsDataSource;

import org.exolab.castor.mapping.Mapping;
import org.mockejb.jndi.MockContextFactory;

import com.mckesson.eig.inuse.base.api.InUseClientErrorCodes;
import com.mckesson.eig.inuse.base.api.InUseException;
import com.mckesson.eig.inuse.hpf.dao.UserSecurityHibernateDao;
import com.mckesson.eig.inuse.hpf.model.User;
import com.mckesson.eig.inuse.hpf.service.SOAPMessenger;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.wsfw.axis.CastorContext;
import com.mckesson.eig.wsfw.session.WsSession;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;


/**
 *
 * @author OFS
 * @date   Jun 15, 2009
 * @since  ROI HPF 13.1
 */
public abstract class  BaseInUseTestCase extends TestCase {


    protected static final String DEFAULT_TEST_USER = "inusetester";
    protected static final String DEFAULT_TEST_PWD  = "inuse";
    protected static final String ADMIN_USER = "ADMIN";
    protected static final String ADMIN_PWD  = "1234";
    protected static final String EPN_PREFIX = "EPN";
    protected static final String RVGROUP    = "ADMINISTRATION";
    private static boolean _initialized;
    private static Mapping _mapping;

    private static ServletUnitClient _client;

    private static UserSecurityHibernateDao _dao;
    private static User _user;

    private static void oneTimeSetup()
    throws Exception {

        // register DataSource with a JNDI Name
        initializeDataSource();

        // initialize Servlet container
        // this will also initialize the Spring's bean factory
        _client = new ServletRunner(new File("war/WEB-INF/web.xml")).newClient();

        // to return SOAPFaults; instead of HTTPInternalException ErrorCode:500
        _client.setExceptionsThrownOnErrorStatus(false);

        // initialize Castor
        // Spring would have been initialized by com.mckesson.eig.wsfw.ConfigurationServlet
        CastorContext context = (CastorContext) SpringUtilities.getInstance().getBeanFactory()
                                                .getBean("castor_context");
        _mapping = context.getMapping();

       _dao = (UserSecurityHibernateDao) SpringUtilities.getInstance()
        .getBeanFactory().getBean(UserSecurityHibernateDao.class.getName());

        // init default test user
        setupUser(DEFAULT_TEST_USER, DEFAULT_TEST_PWD);

       // set user data to session
        initSession();

        _initialized = true;

    }

    /**
     * This method registers the datasource with a JNDI name.Sets up our mock
     * container, JNDI context and deploy the beans that we need.
     * @return
     *
     * @throws NamingException
     */
    private static void initializeDataSource() {
        try {

            InputStream in = BaseInUseTestCase.class.getResourceAsStream("ds.properties");
            Properties props = new Properties();
            props.load(in);
            in.close();

            JtdsDataSource dataSource = new JtdsDataSource();
            dataSource.setDatabaseName(props.getProperty("DATABASE"));
            dataSource.setServerName(props.getProperty("DATABASE_SERVER"));
            dataSource.setUser(props.getProperty("DATABASE_USER"));
            dataSource.setPassword(props.getProperty("DATABASE_PASSWORD"));
            dataSource.setInstance(props.getProperty("DATABASE_INSTANCE"));

            MockContextFactory.setAsInitial();
            Context ctx = new InitialContext();
            ctx.rebind(props.getProperty("JNDI_INUSEDatasource"), dataSource);

        } catch (NamingException e) {
            //LOG.debug("Could not register Data Source" + e.getMessage());
        	fail(e.getMessage());
        } catch (Exception e) {
            //LOG.debug(e.getMessage());
        	fail(e.getMessage());
        }
    }

    private static void setupUser(String username, String pwd) {

        User user = _dao.retrieveUser(username);

        if (user != null) {

            _dao.getHibernateTemplate().delete(user);
        }
        user = new User(DEFAULT_TEST_USER, DEFAULT_TEST_PWD, "INUSE TESTER");

        _dao.getHibernateTemplate().saveOrUpdate(user);
        _user = _dao.retrieveUser(DEFAULT_TEST_USER);
    }

    @Override
    public void setUp() throws Exception {

        if (!_initialized) {
            oneTimeSetup();
        }
    }

    protected void assertError(String response, String expectedErrorCode)
    throws Exception {

        boolean result = (response.indexOf("InUseException") > 0)
                          && (response.indexOf(">" + expectedErrorCode + "<") > 0);

        assertTrue("Should throw InUseException with error code:" + expectedErrorCode, result);
    }

    protected void assertError(String response, ArrayList<String> expectedErrorCodes)
    throws Exception {

        ArrayList<String> notFound = new ArrayList<String>(expectedErrorCodes);

        for (String code : expectedErrorCodes) {
            if (response.indexOf(">" + code + "<") > 0) {
                notFound.remove(code);
            }
        }

        boolean result = (response.indexOf("InUseException") > 0) && notFound.isEmpty();

        String msg = (result) ? null
                : "Should throw InUseException with error codes:" + expectedErrorCodes
                   + ". Following error codes not found:" + notFound;

        assertTrue(msg, result);
    }

    /**
     * This method is to check whether the ErrorCode is in the InUseException or not
     * @param e InUseException containing nested causes
     * @param errorCode Error Code for the exception
     * @return true if the ErrorCode is in the InUseException,
     *         false if the ErrorCode is not in the InUseException
     */
    protected boolean hasErrorcode(InUseException e, String errorCode) {

        if (e.getErrorCode().equalsIgnoreCase(errorCode)) {
            return true;
        }

        List<Throwable> nestedCauses = e.getAllNestedCauses();
        for (Throwable t : nestedCauses) {
			InUseException inUseException = (InUseException) t;
            if (errorCode == inUseException.getErrorCode()) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is to check whether the ErrorCode is in the InUseException or not
     * @param e InUseException containing nested causes
     * @param errorCode Error Code for the exception
     * @return true if the ErrorCode is in the InUseException,
     *         false if the ErrorCode is not in the InUseException
     */
    protected boolean hasErrorcode(InUseException e, InUseClientErrorCodes errorCode) {
        return hasErrorcode(e, errorCode.toString());
    }

    /**
     * This method is to check whether the ErrorCode is in the InUseException or not
     * @param e InUseException containing nested causes
     * @param errorCode Error Code for the exception
     * @param extData  Error Data causing exception
     * @return true if the ErrorCode is in the InUseException,
     *         false if the ErrorCode is not in the InUseException
     */
    private boolean hasErrorcode(InUseException e, String errorCode, String extData) {

        if (e.getErrorCode().equalsIgnoreCase(errorCode)
            && extData.equalsIgnoreCase(e.getExtendedCode())) {
            return true;
        }

        List<Throwable> nestedCauses = e.getAllNestedCauses();
        for (Throwable t : nestedCauses) {
			InUseException inUseException = (InUseException) t;
            if (errorCode.equalsIgnoreCase(inUseException.getErrorCode())
                && extData.equalsIgnoreCase(inUseException.getExtendedCode())) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is to check whether the ErrorCode is in the InUseException or not
     * @param e InUseException containing nested causes
     * @param errorCode Error Code for the exception
     * @param extData Error Data causing exception
     * @return true if the ErrorCode is in the InUseException,
     *         false if the ErrorCode is not in the InUseException
     */
    protected boolean hasErrorcode(InUseException e,
                                   InUseClientErrorCodes errorCode,
                                   String extData) {
        return hasErrorcode(e, errorCode.toString(), extData);
    }

    /**
     * This method is to assert whether the actual and expected error codes are same
     * @param e InUseException containing nested causes
     * @param expectedErrorCode Error Code for the exception
     */
    protected void assertError(InUseException e, InUseClientErrorCodes expectedErrorCode) {

        boolean result = hasErrorcode(e, expectedErrorCode.toString());
        assertTrue("Should throw InUseException with error code:" + expectedErrorCode, result);
    }

    /**
     * This method is to set the user details
     */
    protected static void initSession() {

        WsSession.initializeSession();
        WsSession.setSessionData("authenticated_inuse_user", _user);
    }

    /**
     * This method is to set the invalid user details
     * @param user Details of the user
     */
    protected void initSession(User user) {

        WsSession.initializeSession();
        WsSession.setSessionData("authenticated_inuse_user", user);
    }

    /**
     * This method is to get the instance of the corresponding service name
     * @param serviceName Name of the Service
     * @return object
     */
    protected Object getService(String serviceName) {
        return SpringUtilities.getInstance().getBeanFactory().getBean(serviceName);
    }

    public SOAPMessenger getMessenger() {
        return new SOAPMessenger(_mapping, _client);
    }

    protected String getTestUsername(String serviceMethod) { return DEFAULT_TEST_USER; }
    protected String getTestPassword(String serviceMethod) { return DEFAULT_TEST_PWD; }

    protected abstract String getServiceURL(String serviceMethod);


    public static User getUser() {
        return _user;
    }

    public static void setUser(User user) {
        _user = user;
    }
}
