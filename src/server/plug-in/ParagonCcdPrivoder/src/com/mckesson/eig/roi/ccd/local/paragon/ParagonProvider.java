package com.mckesson.eig.roi.ccd.local.paragon;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.ccd.provider.CcdProvider;
import com.mckesson.eig.roi.ccd.provider.CcdProviderConstants;
import com.mckesson.eig.roi.ccd.provider.CcdProviderConstants.CcdFileType;
import com.mckesson.eig.roi.ccd.provider.CcdProviderConstants.CcdType;
import com.mckesson.eig.roi.ccd.provider.dao.CcdProviderDAO;
import com.mckesson.eig.roi.muroioutbound.model.ExternalSourceDocument;
import com.mckesson.eig.roi.muroioutbound.model.MUROIOutboundStatistics;
import com.mckesson.eig.roi.utils.DSWrapper;
import com.mckesson.eig.roi.utils.SpringUtil;
import com.mckesson.eig.utility.util.StringUtilities;

public class ParagonProvider implements CcdProvider {
	private static final OCLogger LOG = new OCLogger(ParagonProvider.class);

	private static final String DB_URL = "jdbc:jtds:sqlserver://{0};DatabaseName={1}";
	private static final String QUERY = "{call dbo.usp_hpf_ccd_request(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String JDBC_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
	private static final int RETRIEVE_CCD = 1;
	private static final int OUTBOUND_STATISTIC = 2;
	private static final String CCD_STYLE = "paragon.ccd.xslt.file";

	private static final String PROVIDER = "Paragon Provider";
	private static final int CONFIG_ITEMS = 4;
	private static final String SERVER = "database.server";
	private static final String DATABASE = "database.name";
	private static final String USERNAME = "database.username";
	private static final String PD = "database.password.masked";

	private static boolean _isRegister = false;

	private Map<String, String> _configValues;
	private DSWrapper _dsWrapper;

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

	public CcdProvider newProvider() {
		return new ParagonProvider();
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

	private void logParameter(Map<Integer, Object> logMap) {
		LOG.debug("*********Parameters***********");
		for (Integer log : logMap.keySet()) {
			LOG.debug(log.toString() + ":" + logMap.get(log));
		}
		LOG.debug("*********End Parameters***********");
	}

	/**
	 * Method to get the Ccd information
	 * 
	 * @return
	 */
	@Override
	public void retrieveCcd(ExternalSourceDocument document) {
		Connection c = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			Map<Integer, Object> logMap = new HashMap<Integer, Object>();
			String mrn = document.getMrn();
			String encounter = document.getEncounter();
			String facility = getFacility(document);
			String refId = document.getReferenceId();
			String patientType = isPatientRequest(document);
			Timestamp receiveDate = getReceivedDate(document);
			LOG.debug("Start recieve CCD from Paragon");
			logMap.put(new Integer(1), RETRIEVE_CCD);
			logMap.put(new Integer(2), encounter);
			logMap.put(new Integer(3), facility);
			logMap.put(new Integer(4), mrn);
			logMap.put(new Integer(5), refId);
			logMap.put(new Integer(6), receiveDate);
			logMap.put(new Integer(7), null);
			logMap.put(new Integer(8), patientType);
			logMap.put(new Integer(9), null);
			logMap.put(new Integer(10), null);
			logParameter(logMap);
			c = getConnection();
			stmt = c.prepareCall(QUERY);
			stmt.setInt(1, RETRIEVE_CCD);
			stmt.setString(2, encounter);
			stmt.setString(3, facility);
			stmt.setString(4, mrn);
			stmt.setString(5, refId);
			stmt.setObject(6, receiveDate);
			stmt.setObject(7, null);
			stmt.setString(8, patientType);
			stmt.setString(9, null);
			stmt.setString(10, null);
			stmt.registerOutParameter(11, Types.VARCHAR);
			stmt.execute();
			rs = stmt.getResultSet();
			boolean hasException = false;
			if (rs.next()) {
				try {
					Clob clobObject = (Clob) rs.getObject(1);
					if (clobObject == null) {
						hasException = true;
					} else {
						InputStream in = clobObject.getAsciiStream();
						IOUtils.copy(in, document.getOutputStream());
					}
				} catch (SQLException e) {
					hasException = true;
					LOG.error("Unable to retreieve CCD document: "
							+ e.toString());
				}
			} else {
				hasException = true;
			}
			if (hasException) {
				stmt.getMoreResults();
				String param2 = stmt.getString(11);
                String message = null;
				if (!StringUtilities.isEmpty(param2) && !param2.equals("0")) {
					LOG.error("Unable to retrieve CCD document: " + param2);
                    if(param2.startsWith("Could not find a visit with PatNum")) {
                           message = "The system could not find a patient match." + param2;
                        throw new ROIException(
                            ROIClientErrorCodes.CCD_CCR_RETRIEVECCD, message);
                    }
                        else
                        {
                            throw new ROIException(
                                    ROIClientErrorCodes.CCD_CCR_RETRIEVECCD, param2);  
                        }
				}
			}
		} catch (ROIException e) {
			throw e;
		} catch (Exception e) {
			throw new ROIException(e, ROIClientErrorCodes.CCD_CCR_RETRIEVECCD);
		} finally {
			closeConnection(c, rs, stmt);
		}
	}

	public boolean needPeristanceExternalSourceDocument() {
		return true;
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
		Connection c = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		try {
			Map<Integer, Object> logMap = new HashMap<Integer, Object>();
			c = getConnection();
			String mrn = document.getMrn();
			String encounter = document.getEncounter();
			String facility = getExternalFacility(document.getFacility());
			String refId = document.getReferenceId();
			Timestamp receiveDate = new java.sql.Timestamp(getDateTimeFromStr(
					document.getReceivedDate()).getTime());
			Timestamp fulFillDate = new java.sql.Timestamp(getDateTimeFromStr(
					document.getFulfillDate()).getTime());
			String status = isCancelRequest(document);
			String patientType = isPatientRequest(document);
			String outputType = isDigitalRequest(document);
			LOG.debug("Start sending Statistics to Paragon");
			logMap.put(new Integer(1), Integer.toString(OUTBOUND_STATISTIC));
			logMap.put(new Integer(2), encounter);
			logMap.put(new Integer(3), facility);
			logMap.put(new Integer(4), mrn);
			logMap.put(new Integer(5), refId);
			logMap.put(new Integer(6), receiveDate.toString());
			logMap.put(new Integer(7), fulFillDate.toString());
			logMap.put(new Integer(8), patientType);
			logMap.put(new Integer(9), status);
			logMap.put(new Integer(10), outputType);
			stmt = c.prepareCall(QUERY);
			stmt.setInt(1, OUTBOUND_STATISTIC);
			stmt.setString(2, encounter);
			stmt.setString(3, facility);
			stmt.setString(4, mrn);
			stmt.setString(5, refId);
			stmt.setObject(6, receiveDate);
			stmt.setObject(7, fulFillDate);
			stmt.setString(8, patientType);
			stmt.setString(9, status);
			stmt.setString(10, outputType);
			logParameter(logMap);
			stmt.registerOutParameter(11, Types.VARCHAR);
			stmt.execute();
		} catch (Exception e) {
			LOG.warn("Failed to sendExternalDocumentNotice: " + e.toString());
			throw new ROIException(e, ROIClientErrorCodes.CCD_CCR_RETRIEVECCD);
		} finally {
			closeConnection(c, rs, stmt);
		}
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

	private Date getDateTimeFromStr(String s) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(
					ROIConstants.DEFAULT_ROI_DATETIME_FORMAT);
			return df.parse(s);
		} catch (Exception e) {
			return new Date();
		}
	}

	private String isCancelRequest(ExternalSourceDocument document) {
		String status = document.getReqStatus();
		if (ROIConstants.CANCELED_STATUS.equals(status)) {
			return "Y";
		}
		return "N";
	}

	private String isDigitalRequest(ExternalSourceDocument document) {
		String reqType = document.getOutputType();
		if (ROIConstants.SAVE_AS_FILE.equalsIgnoreCase(reqType)) {
			return "Y";
		}
		return "N";
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
			return "Database Name:";
		} else if (key.equals(USERNAME)) {
			return "Database User Name:";
		} else if (key.equals(PD)) {
			return "Database User Password:";
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

	/**
	 * Method to register the driver information
	 * 
	 * @return
	 */
	private synchronized void registerDriver() throws ClassNotFoundException {
		if (!_isRegister) {
			Class clazz = Class.forName("net.sourceforge.jtds.jdbc.Driver");
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

	private String getFacility(ExternalSourceDocument document) {
		String facility = document.getExtFacility();
		if (StringUtilities.isEmpty(facility)) {
			facility = document.getFacility();
		}
		return facility;
	}

	private String getExternalFacility(String facility) {
		String externalFacitlity = getDao().getExternalFacilityFromAuthority(
				facility);
		if (StringUtilities.isEmpty(externalFacitlity)) {
			return facility;
		}
		return externalFacitlity;
	}

	private String isPatientRequest(ExternalSourceDocument document) {
		String reqType = document.getReqType();
		if (CcdProviderConstants.PatientRequest.equalsIgnoreCase(reqType)) {
			return "Y";
		}
		return "N";
	}

	private Timestamp getReceivedDate(ExternalSourceDocument document) {
		String receiveDate = document.getReceivedDate();
		if (!StringUtilities.isEmpty(receiveDate)) {
			try {
				SimpleDateFormat s = new SimpleDateFormat(
						ROIConstants.ROI_DATETIME_FORMAT);
				Date d = s.parse(receiveDate);
				return new Timestamp(d.getTime());
			} catch (ParseException e) {
				LOG.warn("Failed to parase receive date :" + receiveDate);
			}
		}
		return null;
	}

	/**
	 * Method to get the Dao information
	 * 
	 * @return CcdProviderDAOImpl
	 */
	private CcdProviderDAO getDao() {
		return (CcdProviderDAO) SpringUtil
				.getObjectFromSpring("CcdProviderDAO");
	}

	private void closeConnection(Connection c, ResultSet rs,
			CallableStatement stmt) {
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
		return CCD_STYLE;
	}
}
