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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mckesson.eig.roi.conversion.util.HibernateUtil;

/**
 * @author bhanu
 *
 */
public class FreeFormFacilityUnitOfWork extends UnitOfWork {

	private static final Logger logger = Logger.getLogger(FreeFormFacilityUnitOfWork.class);
	
	/* (non-Javadoc)
	 * @see com.mckesson.eig.roi.conversion.processor.impl.UnitOfWork#execute()
	 */
	@Override
	public void execute() {
		FreeFormFacilityUnitOfWork.logger.debug("Entering");
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.getNamedQuery("freeFormFacilititesToConvertCount");
			Integer count = (Integer) query.uniqueResult();
			if(count == 0) {
				session.close();
				return;
			}
			query = session.getNamedQuery("migrateFreeFormFacilities");
			query.executeUpdate();
			tx.commit();
		} catch(Exception e) {
			FreeFormFacilityUnitOfWork.logger.error("Error when converting free form facilities", e);
			if(tx != null) {
				tx.rollback();
			}
			ConversionStatus.getInstance().setErrored(true);
			// throw e;
		}
		FreeFormFacilityUnitOfWork.logger.debug("Exiting");
	}

}
