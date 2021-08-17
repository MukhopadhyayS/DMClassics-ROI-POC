package com.mckesson.eig.roi.ccd.local.clinical;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.ccd.provider.CcdProvider;
import com.mckesson.eig.roi.ccd.provider.CcdProviderConstants.CcdFileType;
import com.mckesson.eig.roi.ccd.provider.CcdProviderConstants.CcdType;
import com.mckesson.eig.roi.ccd.provider.model.CcdDocument;
import com.mckesson.eig.roi.muroioutbound.model.ExternalSourceDocument;
import com.mckesson.eig.roi.muroioutbound.model.MUROIOutboundStatistics;
import com.mckesson.eig.roi.utils.DSWrapper;
import com.mckesson.eig.utility.util.StringUtilities;

public class ClinicalProvider implements CcdProvider {
    private static final OCLogger LOG = new OCLogger(ClinicalProvider.class);
    private static final String DB_URL = "jdbc:oracle:thin:@//{0}:1521/{1}";
    private static final String REPORT_ID_QUERY = "select CCDBA.req_report_seq.nextval from Dual";
    private static final String INSERT_REPORT_QUERY = "insert into CCDBA.REQ_REPORT " + 
                                "(FACILITY_ID, SINGLE_ID, REQUEST_DATE, START_DT, END_DT, REPORT_SNAME, REPORT_ARGS, PERM_FLAG, RPT_COPIES, RPT_PRINTED, RPT_SEQNO, REQ_ID, PRINT_ID, PRINT_DDT, PROXY_ID, PRINT_DT_UTC, END_DT_TZ, PRINT_DDT_TZ, START_DT_TZ) "+
                                " VALUES (?, ?, sysdate, sysdate-1, sysdate, ?, NULL, ?, ?, ?, ?, ?, ?, ?, ?, utc_sysdate, ?, ?, ?)";
    private static final String REPORT_QUERY = "select DOCUMENT, DOCUMENT_FORMAT from anc_document where document_number = ? and PAT_SEQ > 0";
    private static final String ERROR_QUERY = "select RPT_PRINTED from CCDBA.REQ_REPORT where RPT_SEQNO = ?";

    private static final String FROMDATE_FUNC = "{? = call ddt.fromdate(sysdate)}";
   
    private static final String PROVIDER = "Clinical Provider";
    private static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";

    private static final int CONFIG_ITEMS = 5;
    private static final String SERVER = "database.server";
    private static final String USERNAME = "database.username";
    private static final String PD = "database.password.masked";
    private static final String DATABASE = "database";
    private static final String STAFF_ID = "staffId";

    private static final long TIMEOUT = 2 * 60 * 1000;
    private static final long WAIT_TIME = 10 * 1000;

    private Map<String, String> _configValues;
    private static boolean _isRegister = false;
    private DSWrapper _dsWrapper;

    public ClinicalProvider() {
    }

    public CcdProvider newProvider() {
        return new ClinicalProvider();
    }

    /**
     * Method to get the provider name
     * 
     * @return String
     */
    @Override
    public String getProviderName() {
        // TODO Auto-generated method stub
        return PROVIDER;
    }

    /**
     * Method to get the CcdType
     * 
     * @return String
     */
    @Override
    public CcdType getCcdType() {
        return CcdType.CCD;
    }

    /**
     * Method to get the Ccd information
     * 
     * @return
     */
    @Override
    public void retrieveCcd(ExternalSourceDocument document)
            throws ROIException {
        int reportId = getReportId();
        if (reportId < 0) {
            throw new ROIException(
                    ROIClientErrorCodes.CCD_CCR_RETRIEVECCD_CLINICAL_REPORT);
        }
        insertReportRequest(document, reportId);
        waitAndGetReport(document, reportId);
    }

    public boolean needPeristanceExternalSourceDocument() {
        return false;
    }

    /**
     * Method to send the external document
     * 
     * @param name
     * ,data
     * @return
     */
    @Override
    public void sendExternalDocumentNotice(ExternalSourceDocument document) {
        // TODO Auto-generated method stub
    }

    /**
     * Method to send the statistics
     * 
     * @param name
     * ,data
     * @return
     */
    @Override
    public void sendStatisticsNotice(MUROIOutboundStatistics statistic) {
        // TODO Auto-generated method stub
    }

    /**
     * Method to get the configuration label information
     * 
     * @param key
     * @return String
     */
    @Override
    public String getConfigurationLabel(String key) {
        if (key.equals(SERVER)) {
            return "Database Server:";
        } else if (key.equals(DATABASE)) {
            return "Database Type:";
        }else if (key.equals(USERNAME)) {
            return "Database User name:";
        } else if (key.equals(PD)) {
            return "Database Password:";
        } else if (key.equals(STAFF_ID)) {
            return "Staff Id:";
        }
        return null;
    }

    /**
     * Method to get the configuration keys information
     *  
     * @return List<String>
     */
    @Override
    public List<String> getAllConfigurationKeys() {
        // TODO Auto-generated method stub
        List<String> result = new ArrayList<String>();
        result.add(SERVER);
        result.add(DATABASE);
        result.add(USERNAME);
        result.add(PD);
        result.add(STAFF_ID);
        return result;
    }

    /**
     * Method to get the number of configuration items information
     * 
     * @return int
     */
    @Override
    public int getNbrConfigurationItems() {
        // TODO Auto-generated method stub
        return CONFIG_ITEMS;
    }

    /**
     * Method to set the Configuration Values information
     * 
     * @param values
     * @return int
     */
    @Override
    public void setConfigurationValues(Map<String, String> values) {
        _configValues = values;
        if (_dsWrapper != null) {
            _dsWrapper.close();
            _dsWrapper = null;
        }
    }

    /**
     * Method to Test the Configurtion details
     * 
     * @param providerName
     * @param configValues
     * @return boolean
     */
    @Override
    public boolean testConfiguration(Map<String, String> values) {
        Connection c = null;
        try {
            c = getConnection(values);
        } catch (Exception e) {
            return false;
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (Exception e) {
                    //
                }
            }
        }
        return true;
    }

    @Override
    public Set<CcdFileType> getCcdFileType() {
        Set<CcdFileType> result = new HashSet<CcdFileType>();
        result.add(CcdFileType.PDF);
        result.add(CcdFileType.XML);
        return result;
    }

    /**
     * Method to get the config property information
     * 
     * @param providerName
     * @param configValues
     * @return String
     */
    private String getConfigProperty(Map<String, String> values, String key) {
        return values.get(key);
    }

    /**
     * Method to get the connection information
     * 
     * @param providerName
     * @param configValues
     * @return Connection
     */
    private Connection getConnection(Map<String, String> values)
            throws SQLException, ClassNotFoundException {
        String server = getConfigProperty(values, SERVER);
        String database = getConfigProperty(values, DATABASE);
        String username = getConfigProperty(values, USERNAME);
        String password = getConfigProperty(values, PD);
        registerDriver();
        return DriverManager.getConnection(
                getDbConnectionUrL(server, database), username, password);
    }

    private synchronized void initialDataSource(Map<String, String> values) {
        if (_dsWrapper != null) {
            _dsWrapper.close();
        }
        String server = getConfigProperty(values, SERVER);
        String database = getConfigProperty(values, DATABASE);
        String username = getConfigProperty(values, USERNAME);
        String password = getConfigProperty(values, PD);
        String dbUrl = getDbConnectionUrL(server, database);
        _dsWrapper = new DSWrapper(JDBC_DRIVER, dbUrl, username, password);
    }

    private Connection getConnection() {
        if (_dsWrapper == null) {
            initialDataSource(_configValues);
        }
        try {
            return _dsWrapper.getConnection();
        } catch (SQLException e) {
            throw new ROIException(ROIClientErrorCodes.CCD_CCR_CONNECTIONFAILED);
        }
    }

    /**
     * Method to register the driver information
     * 
     * @return
     */
    private synchronized void registerDriver() throws ClassNotFoundException {
        if (!_isRegister) {
            Class.forName(JDBC_DRIVER);
        }
        _isRegister = true;
    }

    /**
     * Method to get the DB connection UrL information
     * 
     * @param server
     * ,database
     * @return String
     */
    private String getDbConnectionUrL(String server, String database) {
        Object[] args = { server, database };
        MessageFormat mf = new MessageFormat(DB_URL);
        String s = mf.format(args);
        return s;
    }

    private int getReportId() {
        
        Connection c = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            c = getConnection();
            stmt = c.createStatement();
            stmt.executeQuery(REPORT_ID_QUERY);
            rs = stmt.getResultSet();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            throw new ROIException(e, ROIClientErrorCodes.CCD_CCR_RETRIEVECCD);
        } finally {
            closeConnection(c, rs, stmt);
        }
        return -1;
    }

    /*
     * make a function call to retrieve a String from  ddt.fromdate(sysdate)
     * */
    private String getFromDateFunctionEntry(){

        Connection c = null;
        CallableStatement callableStmt = null;
        String message = null;
        ResultSet rs = null;
        try {
            c = getConnection();
            callableStmt = c.prepareCall(FROMDATE_FUNC);
            callableStmt.registerOutParameter(1, Types.VARCHAR);
            callableStmt.execute();
            return callableStmt.getString(1);
        } catch (Exception e) {  
            message = "Error while retrieving data via ddt.fromdate(sysdate) call.";
            throw new ROIException(ROIClientErrorCodes.CCD_CCR_RETRIEVECCD, message);
        } finally {
            closeConnection(c, rs, callableStmt);
        }
        
    }
   
    
    /**
     * Method to get the Ccd information
     * 
     * @return
     */
    private void insertReportRequest(ExternalSourceDocument document, int rptId) {

        Connection c = null;
        PreparedStatement stmt =  null;
        String staffId = getConfigProperty(_configValues, STAFF_ID);
        String print_ddt_parm  = getFromDateFunctionEntry();
        String message = null;
        ResultSet rs = null;

        try {
            c = getConnection();
            String encounter = document.getEncounter();
            String facility = getFacility(document);
            
            stmt = c.prepareStatement(INSERT_REPORT_QUERY);
            stmt.setString(1, facility);
            stmt.setString(2, encounter);
            stmt.setString(3, "CCD_pat_dc");
            stmt.setString(4, "Y");
            stmt.setInt(5, 1);
            stmt.setString(6, "N");
            stmt.setInt(7, rptId);
            stmt.setString(8, staffId);
            stmt.setString(9, "ARCHIVE");
            stmt.setString(10, print_ddt_parm);
            stmt.setString(11, "HPFUSER");
            stmt.setString(12, "EST");
            stmt.setString(13, "EST");
            stmt.setString(14, "EST");
            stmt.executeUpdate();
            
        } catch (Throwable e) {  
        if(e.getMessage().contains("no data found")){
            message = "The system could not find a patient match." + e.getMessage();
            message = message.replaceAll("\n", " ");  
            throw new ROIException(ROIClientErrorCodes.CCD_CCR_RETRIEVECCD,message);
            }
            else
            {
              throw new ROIException(e, ROIClientErrorCodes.CCD_CCR_RETRIEVECCD);
            }
        } finally {
            closeConnection(c, rs, stmt);
        }
    }

    private void waitAndGetReport(ExternalSourceDocument document, int rptId)
            throws ROIException {
        long endTime = System.currentTimeMillis() + TIMEOUT;
        boolean isDone = false;
        while (!isDone) {
            sleep();
            isDone = retrieveCcdByRptId(document.getCcdDocuments(), rptId);
            if (!isDone) {
                boolean isError = isErrorByRptId(rptId);
                if (isError) {
                    throw new ROIException(
                            ROIClientErrorCodes.CCD_CCR_RETRIEVECCD_CLINICAL_ERROR);
                }
                long now = System.currentTimeMillis();
                if (now > endTime) {
                    throw new ROIException(
                            ROIClientErrorCodes.CCD_CCR_RETRIEVECCD_CLINICAL_TIMEOUT);
                }
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(WAIT_TIME);
        } catch (InterruptedException e) {

        }
    }

    /**
     * Method to get the Ccd information
     * 
     * @return
     */
    private boolean isErrorByRptId(int rptId) {
        Connection c = null;
        PreparedStatement stmt =  null;
        
        ResultSet rs = null;
        try {
            c = getConnection();
            stmt = c.prepareStatement(ERROR_QUERY);
            stmt.setInt(1, rptId);
            stmt.execute();
            rs = stmt.getResultSet();
            if (rs.next()) {
                String s = rs.getString(1);
                if ("E".equalsIgnoreCase(s)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        } finally {
            closeConnection(c, rs, stmt);
        }
        return false;
    }

    /**
     * Method to get the Ccd information
     * 
     * @return
     */
    private boolean retrieveCcdByRptId(List<CcdDocument> docuList, int rptId) {
        Connection c = null;
        PreparedStatement stmt =  null;
        ResultSet rs = null;
        try {
            c = getConnection();
            stmt = c.prepareStatement(REPORT_QUERY);
            stmt.setInt(1, rptId);
            stmt.execute();
            rs = stmt.getResultSet();
            
            boolean isCopy = false;
            while (rs.next()) {
                try {
                    Blob blobObject = (Blob) rs.getObject(1);
                    String type = rs.getString(2);
                    CcdDocument ccd = getCcdDocument(docuList, type);
                    if ((ccd != null) && (blobObject != null)) {
                        InputStream in = blobObject.getBinaryStream();
                        int copyBytes = IOUtils.copy(in, ccd.getOutputStream());
                        if (copyBytes > 0) {
                            isCopy = true;
                        }
                    }
                } catch (SQLException e) {
                    LOG.error("Unable to retreieve CCD document: "
                            + e.toString());
                }
            }
            if (isCopy) {
                return true;
            }
        } catch (Exception e) {
            return false;
        } finally {
            closeConnection(c, rs, stmt);
        }
        return false;
    }

    private CcdDocument getCcdDocument(List<CcdDocument> docuList, String type) {
        for (CcdDocument c : docuList) {
            if (c.getType().equalsIgnoreCase(type)) {
                return c;
            }
        }
        return null;
    }

    private String getFacility(ExternalSourceDocument document) {
        String facility = document.getExtFacility();
        if (StringUtilities.isEmpty(facility)) {
            facility = document.getFacility();
        }
        return facility;
    }

    private void closeConnection(Connection c, ResultSet rs,
            Statement stmt) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                //
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                //
            }
        }
        if (c != null) {
            try {
                c.close();
            } catch (Exception e) {
                //
            }
        }
    }

    @Override
    public String getCcdConvertStyleSheet() {
        return null;
    }
}
