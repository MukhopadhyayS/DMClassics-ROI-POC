/*
 * Copyright 2012 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Information Solutions and is protected under United States and 
 * international copyright and other intellectual property laws. Use, 
 * disclosure, reproduction, modification, distribution, or storage 
 * in a retrieval system in any form or by any means is prohibited without 
 * the prior express written permission of McKesson Information Solutions.
 */
package com.mckesson.eig.roi.conversion.processor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.SessionFactoryImplementor;

import com.mckesson.eig.roi.conversion.billinglocation.BillingLocation;
import com.mckesson.eig.roi.conversion.billinglocation.BillingLocationUtil;
import com.mckesson.eig.roi.conversion.config.Configuration;
import com.mckesson.eig.roi.conversion.config.Constants;
import com.mckesson.eig.roi.conversion.util.HibernateUtil;

/**
 * @author bhanu
 * 
 */
public class RequestUnitOfWork extends UnitOfWork {
	private static final Logger logger = Logger.getLogger(RequestUnitOfWork.class);
	private Integer requestId;
	private Map<String, BillingLocation> _facilityMapper;
	private Map<String, BillingLocation> _userMapper;
	
	public RequestUnitOfWork(Integer requestId) {
		this.requestId = requestId;
	}

	public RequestUnitOfWork(Integer requestId, Map<String, BillingLocation> facilityMapper,  Map<String, BillingLocation> userMapper) {
		this(requestId);
		_facilityMapper = facilityMapper;
		_userMapper = userMapper;
	}

	/**
	 * @param requestId
	 *            the requestId to set
	 */
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the requestId
	 */
	public Integer getRequestId() {
		return requestId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mckesson.eig.roi.conversion.processor.UnitOfWork#execute()
	 */
	@Override
	public void execute() {
		RequestUnitOfWork.logger.debug("Converting request id: " + this.requestId);
		int total = ConversionStatus.getInstance().getRequestsTotal();
		int success = ConversionStatus.getInstance().getRequestsSuccess();
		int error = ConversionStatus.getInstance().getRequestsErrored();
		System.out.print("\rTotal: " + total + " Processed: " + (success + error) + " Success: " + success + " Error: " + error + " Request id: " + this.requestId +"\t\t");
		
		Connection c = null;
		PreparedStatement cs= null;
		try {
			String version = Configuration.getProperties().getProperty("EPRS_Version");
			String isWheaton = Configuration.getProperties().getProperty("is_wheaton");
			boolean isAdvanedOption = Configuration.isAdvancedBillingLocationOptions();
			String facilityName = null;
			String facilityCode = null;
			if(!isAdvanedOption) {
				facilityName = Configuration.getProperties().getProperty(Constants.DEFAULT_FACILITY_NAME_PROPERTY);
				facilityCode = Configuration.getProperties().getProperty(Constants.DEFAULT_FACILITY_CODE_PROPERTY);
			} else {
				BillingLocationUtil util = new BillingLocationUtil();
				BillingLocation location = util.getBillingLocation(requestId, _userMapper, _facilityMapper);
				facilityName = location.getName();
				facilityCode = location.getCode();
			}
			int defaultInvoiceDueDays = Integer.parseInt(Configuration.getProperties().getProperty(Constants.DEFAULT_INVOICE_DUE_DAYS_PROPERTY));
			String query = "exec ROI_Conversion_RequestMain ?, ?, ?, ?, ?, ?";
			c = ((SessionFactoryImplementor)HibernateUtil.getSessionFactory()).getConnectionProvider().getConnection();
			c.setAutoCommit(true);
			cs = c.prepareStatement( query );
			cs.setEscapeProcessing(true);
			cs.setLong(1, requestId);
			cs.setString(2, version);
			cs.setString(3, isWheaton);
			cs.setString(4, facilityCode);
			cs.setString(5, facilityName);
			cs.setInt(6, defaultInvoiceDueDays);
			cs.execute();
			setConversionStatus(Constants.STATUS_COMPLETED, "0", "NULL");
			ConversionStatus.getInstance().incrementRequestsSuccess();
		} catch (Exception e) {
			RequestUnitOfWork.logger.error("Error when converting request with id: " + this.requestId, e);
			String errorMessage;
			if (e.getCause() != null && e.getCause().getMessage() != null) {
				errorMessage = e.getCause().getMessage();
			} else {
				errorMessage = e.getMessage();
			}
			errorMessage = errorMessage.replaceAll("'", "");
			setConversionStatus(Constants.STATUS_ERROR, "1", "'" + errorMessage + "'");
			ConversionStatus.getInstance().setErrored(true);
			ConversionStatus.getInstance().incrementRequestsErrored();
		} finally {
			if(cs != null) {
				try {
					cs.close();
				} catch (Exception ee) {
					
				}
			}
			if(c != null) {
				try {
					c.close();
				} catch (Exception ee) {
					
				}
			}
		}
		total = ConversionStatus.getInstance().getRequestsTotal();
		success = ConversionStatus.getInstance().getRequestsSuccess();
		error = ConversionStatus.getInstance().getRequestsErrored();
		System.out.print("\rTotal: " + total + " Processed: " + (success + error) + " Success: " + success + " Error: " + error + " Request id: " + this.requestId +"\t\t");
		RequestUnitOfWork.logger.debug("Completed converting request id: " + this.requestId);
	}
	

	private void setConversionStatus(String status, String numRetries, String errorMessage) {
		Transaction tx = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = (Transaction) session.beginTransaction();
			String queryString = session.getNamedQuery(Constants.QUERY_STATUS_REQUEST).getQueryString();
			queryString = queryString.replaceAll(":requestId", String.valueOf(this.requestId)).replaceAll(":status", "'" + status + "'").replaceAll(":numRetries", numRetries)
					.replaceAll(":errorMessage", errorMessage);
			SQLQuery query = session.createSQLQuery(queryString);
			query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			if( tx != null) {
				try { 
					tx.rollback();
				} catch (Exception ee) {
					
				}
			}
		}
	}

}
