/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.test;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.axis.Message;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.message.SOAPFault;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.exolab.castor.mapping.Mapping;
import org.junit.runner.RunWith;
import org.mockejb.jndi.MockContextFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.hpf.dao.UserSecurityHibernateDao;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.hpf.model.UserSecurity;
import com.mckesson.eig.roi.hpf.model.UserTypeLOV;
import com.mckesson.eig.roi.hpf.service.HPFAuthenticationStrategy;
import com.mckesson.eig.roi.hpf.service.SOAPMessenger;
import com.mckesson.eig.roi.utils.AccessFileLoader;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogContext;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.transaction.TransactionId;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.axis.CastorContext;
import com.mckesson.eig.wsfw.model.authentication.AuthenticatedResult;
import com.mckesson.eig.wsfw.session.WsSession;
import com.mckesson.eig.wsfw.test.axis.SoapRequestBuilder;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

import net.sourceforge.jtds.jdbcx.JtdsDataSource;


/**
 * @author manikandans
 * @date   Apr 13, 2009
 * @since  HPF 13.1 [ROI]; Feb 18, 2008
 */
@RunWith(ROITestClassRunner.class)
public abstract class  BaseROITestCase
extends junit.framework.TestCase {


    /**
     * Initialize the logger.
     */
    private static final Log LOG = LogFactory
            .getLogger(BaseROITestCase.class);

    protected static final String DEFAULT_TEST_USER = "roitester";
    protected static final String DEFAULT_TEST_PWD  = "roi";
    protected static final String ADMIN_USER = "ADMIN";
    protected static final String ADMIN_PWD  = "1234";
    protected static final String EPN_PREFIX = "EPN";
    protected static final String RVGROUP    = "ADMINISTRATION";
    private static boolean _initialized;
    private static Mapping _mapping;

    private static ServletUnitClient _client;
    private static IDatabaseConnection _conn = null;
    private static FlatXmlDataSet _dataSet = null;

    private static UserSecurityHibernateDao _dao;
    private static User _user;
    public static Properties props = new Properties();

    static {

        try {

            InputStream in = BaseROITestCase.class.getResourceAsStream("ds.properties");
            props.load(in);
            in.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void oneTimeSetup()
    throws Exception {

        // register DataSource with a JNDI Name
        initializeDataSource();

        // initialize Servlet container
        // this will also initialize the Spring's bean factory
        _client = new ServletRunner(AccessFileLoader.getFile("WEB-INF/web.xml")).newClient();

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

        InetAddress localHost = Inet4Address.getLocalHost();
        TransactionId transactionId = new TransactionId(getUser().getDisplayName(), localHost.getHostAddress());
        LogContext.put("transactionid", transactionId);

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

            JtdsDataSource dataSource = new JtdsDataSource();
            dataSource.setDatabaseName(props.getProperty("DATABASE"));
            dataSource.setServerName(props.getProperty("DATABASE_SERVER"));
            dataSource.setUser(props.getProperty("DATABASE_USER"));
            dataSource.setPassword(props.getProperty("DATABASE_PASSWORD"));
            dataSource.setInstance(props.getProperty("DATABASE_INSTANCE"));

            MockContextFactory.setAsInitial();
            Context ctx = new InitialContext();
            ctx.rebind(props.getProperty("JNDI_ROIDatasource"), dataSource);

        } catch (NamingException e) {
            LOG.debug("Could not register Data Source" + e.getMessage());
        } catch (Exception e) {
            LOG.debug(e.getMessage());
        }
    }

    protected static void setupUser(String username, String pwd) {

        User user = _dao.retrieveUser(username);

        if (user != null) {

            UserSecurity usId = new UserSecurity(user.getInstanceIdValue(),
                    UserSecurity.ENTERPRISE,
                    UserSecurity.ROI_VIP_STATUS);

            _dao.getHibernateTemplate().delete(usId);

            UserSecurity us = new UserSecurity(user.getInstanceIdValue(),
                                               UserSecurity.ENTERPRISE,
                                               6101);

            _dao.getHibernateTemplate().delete(us);
            _dao.getHibernateTemplate().delete(user);

        }
        user = new User(DEFAULT_TEST_USER, DEFAULT_TEST_PWD, "ROI TESTER");

        UserTypeLOV userLov =  (UserTypeLOV)_dao.getHibernateTemplate().find("select u from UserTypeLOV u where u.userTypeLOVId = -1").get(0);
        user.setUserTypeLovId(userLov);
        
        _dao.getHibernateTemplate().saveOrUpdate(user);
        _user = _dao.retrieveUser(DEFAULT_TEST_USER);
        _user.setEpnEnabled(true);
        _user.setEpnPrefix(EPN_PREFIX);
        _user.setRvGroup(RVGROUP);

        UserSecurity us = new UserSecurity(_user.getInstanceIdValue(),
                UserSecurity.ENTERPRISE,
                UserSecurity.ROI_VIP_STATUS);

        _dao.getHibernateTemplate().saveOrUpdate(us);
    }

    @Override
    public void setUp() throws Exception {

        try {

            if (!_initialized) {

                oneTimeSetup();
                initializeTestData();
                LOG.info("Initial setup has loaded succesfully");
            }
            initSession();
        } catch (Throwable e) {
            fail("Failed to load the Initial setup", e);
        }
    }

    /**
     * Since java 7 does not execute the test case in the written order,
     * we can write the initialization data in these methods
     * this method can be overrided to initialize the test case data
     * @throws Exception
     */
    public void initializeTestData() throws Exception { }

    protected void assertError(String response, String expectedErrorCode)
    throws Exception {

        boolean result = (response.indexOf("ROIException") > 0)
                          && (response.indexOf(">" + expectedErrorCode + "<") > 0);

        assertTrue("Should throw ROIException with error code:" + expectedErrorCode, result);
    }

    protected void assertError(String response, ArrayList<String> expectedErrorCodes)
    throws Exception {

        ArrayList<String> notFound = new ArrayList<String>(expectedErrorCodes);

        for (String code : expectedErrorCodes) {
            if (response.indexOf(">" + code + "<") > 0) {
                notFound.remove(code);
            }
        }

        boolean result = (response.indexOf("ROIException") > 0) && notFound.isEmpty();

        String msg = (result) ? null
                : "Should throw ROIException with error codes:" + expectedErrorCodes
                   + ". Following error codes not found:" + notFound;

        assertTrue(msg, result);
    }

    /**
     * This method is to check whether the ErrorCode is in the ROIException or not
     * @param e ROIException containing nested causes
     * @param errorCode Error Code for the exception
     * @return true if the ErrorCode is in the ROIException,
     *         false if the ErrorCode is not in the ROIException
     */
    protected boolean hasErrorcode(ROIException e, String errorCode) {

        if (e.getErrorCode().equalsIgnoreCase(errorCode)) {
            return true;
        }

        List<Throwable> nestedCause = e.getAllNestedCauses();
        for (Throwable roiException : nestedCause) {
            if (errorCode == ((ROIException) roiException).getErrorCode()) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is to check whether the ErrorCode is in the ROIException or not
     * @param e ROIException containing nested causes
     * @param errorCode Error Code for the exception
     * @return true if the ErrorCode is in the ROIException,
     *         false if the ErrorCode is not in the ROIException
     */
    protected boolean hasErrorcode(ROIException e, ROIClientErrorCodes errorCode) {
        return hasErrorcode(e, errorCode.toString());
    }

    /**
     * This method is to check whether the ErrorCode is in the ROIException or not
     * @param e ROIException containing nested causes
     * @param errorCode Error Code for the exception
     * @param extData  Error Data causing exception
     * @return true if the ErrorCode is in the ROIException,
     *         false if the ErrorCode is not in the ROIException
     */
    private boolean hasErrorcode(ROIException e, String errorCode, String extData) {

        if (e.getErrorCode().equalsIgnoreCase(errorCode)
            && extData.equalsIgnoreCase(e.getExtendedCode())) {
            return true;
        }

        List<Throwable> nestedCause = e.getAllNestedCauses();
        for (Throwable throwable : nestedCause) {
            ROIException roiException = (ROIException) throwable;
            if (errorCode.equalsIgnoreCase(roiException.getErrorCode())
                && extData.equalsIgnoreCase(roiException.getExtendedCode())) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is to check whether the ErrorCode is in the ROIException or not
     * @param e ROIException containing nested causes
     * @param errorCode Error Code for the exception
     * @param extData Error Data causing exception
     * @return true if the ErrorCode is in the ROIException,
     *         false if the ErrorCode is not in the ROIException
     */
    protected boolean hasErrorcode(ROIException e, ROIClientErrorCodes errorCode, String extData) {
        return hasErrorcode(e, errorCode.toString(), extData);
    }

    /**
     * This method is to assert whether the actual and expected error codes are same
     * @param e ROIException containing nested causes
     * @param expectedErrorCode Error Code for the exception
     */
    protected void assertError(ROIException e, ROIClientErrorCodes expectedErrorCode) {

        boolean result = hasErrorcode(e, expectedErrorCode.toString());
        assertTrue("Should throw ROIException with error code:" + expectedErrorCode, result);
    }

    /**
     * This method is to set the user details
     */
    protected static void initSession() {

        WsSession.initializeSession();
        WsSession.setSessionData("authenticated_roi_user", _user);
    }

    /**
     * This method is to set the invalid user details
     * @param user Details of the user
     */
    protected void initSession(User user) {

        WsSession.initializeSession();
        WsSession.setSessionData("authenticated_roi_user", user);
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
        BaseROITestCase._user = user;
    }

    public void loginUser(String userName, String pwd) {

        HPFAuthenticationStrategy strategy =
                            (HPFAuthenticationStrategy) getService("AuthenticationStrategy");

        AuthenticatedResult result = strategy.login(userName, pwd);
        result.setState(1);
        WsSession.setSessionData(WsSession.AUTHRESULT, result);

        if (null ==  WsSession.getSessionData("authenticated_roi_user")) {
            setUser(_dao.retrieveUser(userName));
        } else {
            setUser((User) WsSession.getSessionData("authenticated_roi_user"));
        }

        getUser().setEpnPrefix(EPN_PREFIX);
        WsSession.setSessionData("authenticated_roi_user", getUser());

    }

    public String appendString(String str) {
        return new StringBuffer().append(str)
                                 .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAA")
                                 .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAA")
                                 .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAA")
                                 .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAA")
                                 .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAA")
                                 .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAA")
                                 .append("ASDFKLAJSDKFJASDKLFJKLAJSDFKLJASDFKLJSSSAAAAAAAAAAAAAAAA")
               .toString();
    }

    public static ServletUnitClient getClient() {
        return _client;
    }

    public static void setClient(ServletUnitClient client) {
        BaseROITestCase._client = client;
    }

    @Override
    protected void tearDown() throws Exception {

    }

    public Connection getConnection() throws Exception {
        HibernateTransactionManager transactionManager =
            (HibernateTransactionManager) SpringUtilities.getInstance().getBeanFactory()
            .getBean("transactionManager");

        return transactionManager.getDataSource().getConnection();
    }

    protected void insertDataSet(String file) throws Exception {

        try {

            if (_conn == null) {

                Connection connection = getConnection();
                _conn = new DatabaseConnection(connection);
            }

            if (StringUtilities.isEmpty(file)) {
                file = "test/resources/reports/reportsDataSet.xml";
            }

            if (_dataSet == null) {

                // get insert data
                File dataSetFile = AccessFileLoader.getFile(file);
                _dataSet = new FlatXmlDataSetBuilder().build(dataSetFile);
            }

            InsertIdentityOperation.REFRESH.execute(_conn, _dataSet);
        } catch(Exception e) {

            LOG.error("Failed to initialize Dataset", e);
            e.printStackTrace();
        }
    }

    public void refreshTestData(String file) {

        List<String> query = new ArrayList<String>();
        query.add("DELETE FROM [dbo].[ROI_RequestCoreEvent] WHERE [ROI_RequestCore_Seq] = 1001");
        query.add("DELETE delpag FROM [dbo].[ROI_RequestCoreDeliverytoROI_Pages] delpag, [dbo].[ROI_RequestCoreDelivery] del WHERE [del].[ROI_RequestCoreDelivery_Seq] = [delpag].[ROI_RequestCoreDelivery_Seq] AND [del].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE delpag FROM [dbo].[ROI_RequestCoreDeliverytoROI_SupplementalAttachmentsCore] delpag, [dbo].[ROI_RequestCoreDelivery] del WHERE [del].[ROI_RequestCoreDelivery_Seq] = [delpag].[ROI_RequestCoreDelivery_Seq] AND [del].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE delpag FROM [dbo].[ROI_RequestCoreDeliverytoROI_SupplementalDocumentsCore] delpag, [dbo].[ROI_RequestCoreDelivery] del WHERE [del].[ROI_RequestCoreDelivery_Seq] = [delpag].[ROI_RequestCoreDelivery_Seq] AND [del].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE delpag FROM [dbo].[ROI_RequestCoreDeliverytoROI_SupplementarityAttachmentsCore] delpag, [dbo].[ROI_RequestCoreDelivery] del WHERE [del].[ROI_RequestCoreDelivery_Seq] = [delpag].[ROI_RequestCoreDelivery_Seq] AND [del].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE delpag FROM [dbo].[ROI_RequestCoreDeliverytoROI_SupplementarityDocumentsCore] delpag, [dbo].[ROI_RequestCoreDelivery] del WHERE [del].[ROI_RequestCoreDelivery_Seq] = [delpag].[ROI_RequestCoreDelivery_Seq] AND [del].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE invMapp FROM [dbo].[ROI_RequestCoreDelivery] del, [dbo].[ROI_RequestCoreDeliverytoROI_RequestCoreDeliveryCharges] invMapp WHERE [invMapp].[ROI_RequestCoreDelivery_Seq] = [invMapp].[ROI_RequestCoreDelivery_Seq] and [del].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE del FROM [dbo].[ROI_RequestCoreDelivery] del WHERE [del].[ROI_RequestCore_Seq] = 1001");

        query.add("DELETE doc FROM [dbo].[ROI_RequestCoreDeliverycharges] invoice, [dbo].[ROI_RequestCoreDeliveryChargesDocument] doc WHERE [doc].[ROI_RequestCoreDeliveryCharges_Seq] = [invoice].[ROI_RequestCoreDeliveryCharges_Seq] AND [invoice].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE fee FROM [dbo].[ROI_RequestCoreDeliverycharges] invoice, [dbo].[ROI_RequestCoreDeliveryChargesFee] fee WHERE [fee].[ROI_RequestCoreDeliveryCharges_Seq] = [invoice].[ROI_RequestCoreDeliveryCharges_Seq] AND [invoice].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE shipping FROM [dbo].[ROI_RequestCoreDeliverycharges] invoice, [dbo].[ROI_RequestCoreDeliveryChargesShipping] shipping WHERE [shipping].[ROI_RequestCoreDeliveryCharges_Seq] = [invoice].[ROI_RequestCoreDeliveryCharges_Seq] AND [invoice].[ROI_RequestCore_Seq] = 1001");

        query.add("DELETE doc FROM [dbo].[ROI_RequestCorecharges] invoice, [dbo].[ROI_RequestCoreChargesDocument] doc WHERE [doc].[ROI_RequestCoreCharges_Seq] = [invoice].[ROI_RequestCoreCharges_Seq] AND [invoice].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE fee FROM [dbo].[ROI_RequestCorecharges] invoice, [dbo].[ROI_RequestCoreChargesFee] fee WHERE [fee].[ROI_RequestCoreCharges_Seq] = [invoice].[ROI_RequestCoreCharges_Seq] AND [invoice].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE shipping FROM [dbo].[ROI_RequestCorecharges] invoice, [dbo].[ROI_RequestCoreChargesShipping] shipping WHERE [shipping].[ROI_RequestCoreCharges_Seq] = [invoice].[ROI_RequestCoreCharges_Seq] AND [invoice].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE shipping FROM [dbo].[ROI_RequestCorecharges] invoice, [dbo].[ROI_RequestCoreChargesSalesTaxReason] shipping WHERE [shipping].[ROI_RequestCoreCharges_Seq] = [invoice].[ROI_RequestCoreCharges_Seq] AND [invoice].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE charges FROM [dbo].[ROI_RequestCorecharges] charges WHERE [charges].[ROI_RequestCore_Seq] = 1001");

        query.add("DELETE pay FROM [dbo].[ROI_RequestCoreDeliverycharges] invoice, [dbo].[ROI_RequestCoreDeliveryChargestoROI_RequestorPayment] pay WHERE [invoice].[ROI_RequestCoreDeliveryCharges_Seq] = [pay].[ROI_RequestCoreDeliveryCharges_Seq] and [invoice].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE adj FROM [dbo].[ROI_RequestCoreDeliverycharges] invoice, [dbo].[ROI_RequestCoreDeliveryChargestoROI_RequestorAdjustment] adj WHERE [invoice].[ROI_RequestCoreDeliveryCharges_Seq] = [adj].[ROI_RequestCoreDeliveryCharges_Seq] and [invoice].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE pat FROM [dbo].[ROI_RequestCoreDeliverycharges] invoice, [dbo].[ROI_RequestCoreDeliveryChargesInvoicePatients] pat WHERE [pat].[ROI_RequestCoreDeliveryCharges_Seq] = [invoice].[ROI_RequestCoreDeliveryCharges_Seq] AND [invoice].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE invoice FROM [dbo].[ROI_RequestCoreDeliverycharges] invoice WHERE [invoice].[ROI_RequestCore_Seq] = 1001");

        query.add("DELETE pag FROM [dbo].[ROI_Pages] [pag], [dbo].[ROI_Versions] ver, [ROI_Documents] doc, [dbo].[ROI_RequestCoretoROI_Patients] pat WHERE [pag].[ROI_Versions_Seq] = [ver].[ROI_Versions_Seq] AND [ver].[ROI_Documents_Seq] = [doc].[ROI_Documents_Seq] AND [doc].[ROI_Patients_Seq] = [pat].[ROI_Patients_Seq] AND [pat].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE ver FROM [dbo].[ROI_Versions] ver, [ROI_Documents] doc, [dbo].[ROI_RequestCoretoROI_Patients] pat WHERE [ver].[ROI_Documents_Seq] = [doc].[ROI_Documents_Seq] AND [doc].[ROI_Patients_Seq] = [pat].[ROI_Patients_Seq] AND [pat].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE doc FROM [ROI_Documents] doc, [dbo].[ROI_RequestCoretoROI_Patients] pat WHERE [doc].[ROI_Patients_Seq] = [pat].[ROI_Patients_Seq] AND [pat].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE enc FROM [dbo].[ROI_ENCOUNTERS] enc, [dbo].[ROI_RequestCoretoROI_Patients] pat WHERE [enc].[ROI_Patients_Seq] = [pat].[ROI_Patients_Seq] AND [pat].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE att FROM [dbo].[ROI_SupplementarityAttachmentsCore] att, [dbo].[ROI_RequestCoretoROI_Patients] pat WHERE [pat].[ROI_Patients_Seq] = [att].[ROI_Patients_Seq] AND [pat].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE doc FROM [dbo].[ROI_SupplementarityDocumentsCore] doc, [dbo].[ROI_RequestCoretoROI_Patients] pat WHERE [pat].[ROI_Patients_Seq] = [doc].[ROI_Patients_Seq] AND [pat].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE pat FROM [dbo].[ROI_RequestCoretoROI_Patients] pat WHERE [pat].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE pat FROM [dbo].[ROI_PATIENTS] pat, [dbo].[ROI_RequestCoretoROI_Patients] patmap WHERE [pat].[ROI_Patients_Seq] = [patmap].[ROI_Patients_Seq] AND [patmap].[ROI_RequestCore_Seq] = 1001");

        query.add("DELETE att FROM [dbo].[ROI_SupplementalAttachmentsCore] att, [dbo].[ROI_RequestCoretoROI_SupplementalPatientsCore] pat WHERE [pat].[ROI_SupplementalPatientsCore_Seq] = [att].[ROI_SupplementalPatientsCore_Seq] AND [pat].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE doc FROM [dbo].[ROI_SupplementalDocumentsCore] doc, [dbo].[ROI_RequestCoretoROI_SupplementalPatientsCore] pat WHERE [pat].[ROI_SupplementalPatientsCore_Seq] = [doc].[ROI_SupplementalPatientsCore_Seq] AND [pat].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE pat FROM [dbo].[ROI_RequestCoretoROI_SupplementalPatientsCore] pat WHERE [pat].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE pat FROM [dbo].[ROI_SupplementalPatientsCore] pat, [dbo].[ROI_RequestCoretoROI_SupplementalPatientsCore] patMap WHERE [pat].[ROI_SupplementalPatientsCore_Seq] = [patMap].[ROI_SupplementalPatientsCore_Seq] AND [patMap].[ROI_RequestCore_Seq] = 1001");
        query.add("DELETE pat FROM [dbo].[ROI_CoverLetterCoretoROI_Patients] pat, [dbo].[ROI_CoverLetterCore] letter WHERE [letter].[ROI_RequestCore_Seq] = 1001 AND [letter].[ROI_CoverLetterCore_Seq] = [pat].[ROI_CoverLetterCore_Seq]");
        query.add("DELETE pat FROM [dbo].[ROI_CoverLetterCoretoROI_SupplementalPatientsCore] pat, [dbo].[ROI_CoverLetterCore] letter WHERE [letter].[ROI_RequestCore_Seq] = 1001 AND [letter].[ROI_CoverLetterCore_Seq] = [pat].[ROI_CoverLetterCore_Seq]");
        query.add("DELETE FROM [dbo].[ROI_CoverLetterCore] WHERE [ROI_RequestCore_Seq] = 1001");
        query.add("DELETE FROM [dbo].[ROI_RequestCoreRequestor] WHERE [ROI_RequestCore_Seq] = 1001");
        query.add("DELETE FROM [dbo].[ROI_RequestCore] WHERE [ROI_RequestCore_Seq] = 1001");

        try {

            for (String quer : query) {
                executeSqlQuery(quer);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.warn("failed to Refresh dataSet", ex);
        }

        try {

            if (StringUtilities.isEmpty(file)) {
                file = "test/resources/reports/reportsDataSet.xml";
            }

            insertDataSet(file);

        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.warn("failed to initialize dataSet", ex);
        }

    }

    /**
     * fails the test case and prints the stack trace
     * @param message
     * @param cause
     */
    protected void fail(String message, Throwable cause) {

        LOG.warn(message, cause);
        cause.printStackTrace();
        super.fail(message);
    }

    protected List<?> executeSqlQuery(String query) {

        try {

            if (null == query) {
                return null;
            }

            JdbcTemplate template = getJdbcTemplate();
            if (!query.trim().toUpperCase().startsWith("SELECT")) {

                template.execute(query);
                return null;
            }

            List queryForList = template.queryForList(query);
            List newList = new ArrayList();
            for (Object o :queryForList) {

                Map m = (Map) o;
                newList.addAll(m.values());
            }
            return newList;

        } catch(Exception e) {

            LOG.error("Failed to initialize Dataset", e);
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @return
     */
    protected JdbcTemplate getJdbcTemplate() {

        DataSource datasource = (DataSource) SpringUtilities.getInstance().getBeanFactory().getBean("dataSource");
        JdbcTemplate template = new JdbcTemplate(datasource);
        return template;
    }

    protected long getLetterTemplateFileId() {

        List<?> list = executeSqlQuery("SELECT ROI_LetterTemplateFile_Seq FROM ROI_LetterTemplate where ROI_LetterTemplate_Seq=-1");
        if (CollectionUtilities.isEmpty(list)){
            return 1001;
        }
        Number number = (Number) list.get(0);
        return number.longValue();
    }

    /**
     * send the soap request webservice to the given URL Endpoint
     * @param endpoint
     * @param param
     * @param serviceMethod
     * @param userName
     * @param password
     * @return response string
     */
    protected String sendSoapRequest(String endpoint, String param, String serviceMethod, String userName, String password) {

        try {

            Service service = new Service();
            Call call    = (Call) service.createCall();
            call.setTargetEndpointAddress( new java.net.URL(endpoint) );

            SoapRequestBuilder builder = new SoapRequestBuilder();
            builder.setOperationData(serviceMethod, "urn:eig.mckesson.com");
            InputStream in = builder.buildSoapRequestWithSecurityHeader(userName, password);

            byte[] b = new byte[in.available()];
            in.read(b);
            StringBuffer base = new StringBuffer(new String(b));
            int i = base.indexOf("/></soapenv:Body>");
            String msg = base.replace(i, i + 1, ">\n" + param + "</" + serviceMethod).toString();

            SOAPEnvelope response = call.invoke(new Message(msg));

            Node nResponse = response.getBody().getChildNodes().item(0);
            if (nResponse instanceof SOAPFault) { // SOAPException
                return nResponse.toString();
            }

            NodeList nResult = nResponse.getChildNodes();
            if (nResult.getLength() == 0) { // for void return type
                return "";
            }

            String responseString = nResponse.getChildNodes().item(0).toString();
            System.out.println("Response String: '" + responseString + "'");

            return responseString;


        } catch (Exception e) {

            e.printStackTrace();
            if (e instanceof org.apache.axis.AxisFault) {
                return ((org.apache.axis.AxisFault) e).getMessage();
            }

            throw new RuntimeException(e);
        }
    }

}
