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

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mckesson.eig.roi.conversion.config.Constants;
import com.mckesson.eig.roi.conversion.util.HibernateUtil;

/**
 * @author bhanu
 * 
 */
public class SupplementalUnitOfWork extends UnitOfWork {

	private static final Logger logger = Logger.getLogger(SupplementalUnitOfWork.class);
	private Integer supplementalId;

	public SupplementalUnitOfWork(Integer supplementalId) {
		this.supplementalId = supplementalId;
	}

	/**
	 * @param supplementalId
	 *            the supplementalId to set
	 */
	public void setSupplementalId(Integer supplementalId) {
		this.supplementalId = supplementalId;
	}

	/**
	 * @return the supplementalId
	 */
	public Integer getSupplementalId() {
		return this.supplementalId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mckesson.eig.roi.conversion.processor.UnitOfWork#execute()
	 */
	@Override
	public void execute() {
		SupplementalUnitOfWork.logger.debug("Converting Supplemental patient id: " + this.supplementalId);
		int total = ConversionStatus.getInstance().getSupplementalsTotal();
		int success = ConversionStatus.getInstance().getSupplementalsSuccess();
		int error = ConversionStatus.getInstance().getSupplementalsErrored();
		System.out.print("\rTotal: " + total + " Processed: " + (success + error) + " Success: " + success + " Error: " + error + " Supplemental id: " + this.supplementalId +"\t\t");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			migrateSupplemental(session);
			setConversionStatus(session, Constants.STATUS_COMPLETED, "0", "NULL");
			tx.commit();
			ConversionStatus.getInstance().incrementSupplementalsSuccess();
		} catch (Exception e) {
			SupplementalUnitOfWork.logger.error("Error when converting Supplemental patient with id: " + this.supplementalId, e);
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
			ConversionStatus.getInstance().incrementSupplementalsErrored();
		}
		total = ConversionStatus.getInstance().getSupplementalsTotal();
		success = ConversionStatus.getInstance().getSupplementalsSuccess();
		error = ConversionStatus.getInstance().getSupplementalsErrored();
		System.out.print("\rTotal: " + total + " Processed: " + (success + error) + " Success: " + success + " Error: " + error + " Supplemental id: " + this.supplementalId +"\t\t");
		SupplementalUnitOfWork.logger.debug("Completed converting Supplemental patient id: " + this.supplementalId);
	}
	
	private void migrateSupplemental(Session session) {
		String queryString = session.getNamedQuery("execSupplementalSP").getQueryString();
		queryString = queryString.replaceAll(":supplementalId", Integer.toString(this.supplementalId));
		SQLQuery query = session.createSQLQuery(queryString);
		query.executeUpdate();
	}
	
	private void setConversionStatus(Session session, String status, String numRetries, String errorMessage) throws Exception {
		String queryString = session.getNamedQuery(Constants.QUERY_STATUS_SUPPLEMENTAL).getQueryString();
		queryString = queryString.replaceAll(":supplementalId", String.valueOf(this.supplementalId)).replaceAll(":status", "'" + status + "'").replaceAll(":numRetries", numRetries)
				.replaceAll(":errorMessage", errorMessage);
		SQLQuery query = session.createSQLQuery(queryString);
		query.executeUpdate();
	}
}
