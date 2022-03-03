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
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mckesson.eig.roi.conversion.config.Constants;
import com.mckesson.eig.roi.conversion.util.HibernateUtil;

/**
 * @author bhanu
 * 
 */
public class RequestorLetterUnitOfWork extends UnitOfWork {
	private static final Logger logger = LogManager.getLogger(RequestorLetterUnitOfWork.class);
	private Integer requestorLetterId;

	public RequestorLetterUnitOfWork(Integer requestorLetterId) {
		this.requestorLetterId = requestorLetterId;
	}

	/**
	 * @param requestorLetterId
	 *            the requestorLetterId to set
	 */
	public void setRequestorLetterId(Integer requestorLetterId) {
		this.requestorLetterId = requestorLetterId;
	}

	/**
	 * @return the requestorLetterId
	 */
	public Integer getRequestorLetterId() {
		return requestorLetterId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mckesson.eig.roi.conversion.processor.UnitOfWork#execute()
	 */
	@Override
	public void execute() {
		RequestorLetterUnitOfWork.logger.debug("Converting requestor letter id: " + this.requestorLetterId);
		int total = ConversionStatus.getInstance().getRequestorLettersTotal();
		int success = ConversionStatus.getInstance().getRequestorLettersSuccess();
		int error = ConversionStatus.getInstance().getRequestorLettersErrored();
		System.out.print("\rTotal: " + total + " Processed: " + (success + error) + " Success: " + success + " Error: " + error + " RequestorLetter id: " + this.requestorLetterId);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			migrateRequestorLetter(session);
			setConversionStatus(session, Constants.STATUS_COMPLETED, "0", "NULL");
			tx.commit();
			ConversionStatus.getInstance().incrementRequestorLettersSuccess();
		} catch (Exception e) {
			RequestorLetterUnitOfWork.logger.error("Error when converting requestor letter id: " + this.requestorLetterId);
			if (tx != null) {
				tx.rollback();
			}
			String errorMessage;
			if (e.getCause() != null && e.getCause().getMessage() != null) {
				errorMessage = e.getCause().getMessage();
			} else {
				errorMessage = e.getMessage();
			}
			errorMessage = errorMessage.replaceAll("'", "");
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			try {
				setConversionStatus(session, Constants.STATUS_ERROR, "1", "'"+errorMessage+"'");
				tx.commit();
			} catch (Exception e1) {
				if (tx != null) {
					tx.rollback();
				}
			}
			ConversionStatus.getInstance().setErrored(true);
			ConversionStatus.getInstance().incrementRequestorLettersErrored();
		}
		total = ConversionStatus.getInstance().getRequestorLettersTotal();
		success = ConversionStatus.getInstance().getRequestorLettersSuccess();
		error = ConversionStatus.getInstance().getRequestorLettersErrored();
		System.out.print("\rTotal: " + total + " Processed: " + (success + error) + " Success: " + success + " Error: " + error + " RequestorLetter id: " + this.requestorLetterId);
		RequestorLetterUnitOfWork.logger.debug("Completed converting requestor letter id: " + this.requestorLetterId);
	}
	
	private void migrateRequestorLetter(Session session)  throws Exception {
		String queryString = session.getNamedQuery("execRequestorLetterSP").getQueryString();
		queryString = queryString.replaceAll(":requestorLetterId", Integer.toString(requestorLetterId));
		SQLQuery query = session.createSQLQuery(queryString);
		query.executeUpdate();
	}
	
	private void setConversionStatus(Session session, String status, String numRetries, String errorMessage) throws Exception {
		String queryString = session.getNamedQuery(Constants.QUERY_STATUS_REQUESTORLETTER).getQueryString();
		queryString = queryString.replaceAll(":requestorLetterId", String.valueOf(this.requestorLetterId)).replaceAll(":status", "'" + status + "'").replaceAll(":numRetries", numRetries)
				.replaceAll(":errorMessage", errorMessage);
		SQLQuery query = session.createSQLQuery(queryString);
		query.executeUpdate();
	}

}
