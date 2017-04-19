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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mckesson.eig.roi.conversion.billinglocation.BillingLocation;
import com.mckesson.eig.roi.conversion.config.Configuration;
import com.mckesson.eig.roi.conversion.config.Constants;
import com.mckesson.eig.roi.conversion.exceptions.ValidationException;
import com.mckesson.eig.roi.conversion.util.HibernateUtil;
import com.mckesson.eig.roi.conversion.util.Worker;
import com.mckesson.eig.roi.conversion.util.WorkerPool;

/**
 * @author bhanu
 * 
 */
public class ConversionProcessor implements Runnable {
	private static final Logger logger = Logger.getLogger(ConversionProcessor.class);
    private static final int SLEEP_TIME = 100;
    private static Map<String, BillingLocation> _facilityMapper;
    private static Map<String, BillingLocation> _userMapper;
    private int _requestId = 0;
    
	public int getRequestId() {
		return _requestId;
	}

	public void setRequestId(int requestId) {
		_requestId = requestId;
	}

	private enum TASK {
		FREEFORM_FACILITY("Non-HPF Freeform Facilities"), SUPPLEMENTAL("Non-HPF Patients"), REQUEST("Requests"), REQUESTOR_LETTER("Requestor letters");
		
		private String name;
		private TASK(String name) {
			this.name = name;
		}
		public String getName() {
			return this.name;
		}
	}
	
	public ConversionProcessor() {
	}
	
	public ConversionProcessor( Map<String, BillingLocation> facilityMapper,  Map<String, BillingLocation> userMapper) {
		_facilityMapper = facilityMapper;
		_userMapper = userMapper;
	}

	public void initialize() throws ValidationException {
		ConversionProcessor.logger.debug("Entering");
		ConversionStatus.getInstance().setFreeformFacilitiesTotal(this.totalTasksToConvert(Constants.QUERY_TOTAL_FREEFORM_FACILITITES));
		ConversionStatus.getInstance().setSupplementalsTotal(this.totalTasksToConvert(Constants.QUERY_TOTAL_SUPPLEMENTALS));
		
		ConversionStatus.getInstance().setRequestsTotal(this.totalTasksToConvert(Constants.QUERY_TOTAL_REQUESTS));
//		ConversionStatus.getInstance().setRequestorLettersTotal(this.totalTasksToConvert(Constants.QUERY_TOTAL_REQUESTORLETTERS));

		Configuration.getProperties().setProperty("is_wheaton", String.valueOf(this.isWheaton()));
		Configuration.getProperties().setProperty("EPRS_Version", this.getEPRSVersion());
		
//		this.createStoredProceduresTemporary();
		ConversionProcessor.logger.debug("Exiting");
	}
	
	public void destroy() {
		ConversionProcessor.dropStoredProceduresTemporary();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		ConversionProcessor.logger.debug("Entering");
		if (ConversionStatus.getInstance().getStarted()) {
			// Processor already started. return.
			return;
		}
		ConversionStatus.getInstance().setStarted(Boolean.TRUE);
		try {
			cleanupRequestCoreTables();
			if(ConversionStatus.getInstance().getFreeformFacilitiesTotal() > 0) {
				this.convertFreeFormFacilities();
			}
			if(ConversionStatus.getInstance().getSupplementalsTotal() > 0) {
				this.convert(TASK.SUPPLEMENTAL);
			}
			if(ConversionStatus.getInstance().getRequestsTotal() > 0) {
				this.convertRequest();
			}
//			if(ConversionStatus.getInstance().getRequestorLettersTotal() > 0) {
//				this.convert(TASK.REQUESTOR_LETTER);
//			}
			this.audit();
		} catch (Exception e) {
			ConversionStatus.getInstance().setErrored(Boolean.TRUE);
			ConversionProcessor.logger.error("Error in Conversion Processor: ", e);
		}
		ConversionStatus.getInstance().setCompleted(Boolean.TRUE);
		ConversionProcessor.logger.debug("Exiting");
	}

	private void convertFreeFormFacilities() {
		ConversionProcessor.logger.debug("Entering");
		if(ConversionStatus.getInstance().getErrored()) {
			return;
		}
		UnitOfWork task = new FreeFormFacilityUnitOfWork();
		System.out.print("Converting Non-HPF Freeform facilities...");
		task.execute();
		if(ConversionStatus.getInstance().getErrored()) {
			System.out.println("\rConverting Non-HPF Freeform facilities... ERROR");
		} else {
			System.out.println("\rConverting Non-HPF Freeform facilities... SUCCESS");
		}
		ConversionProcessor.logger.debug("Exiting");
	}

	private void convert(TASK task) {
		ConversionProcessor.logger.debug("Entering");
		if(ConversionStatus.getInstance().getErrored()) {
			return;
		}
		Queue<Integer> queue = getTasksToConvert(getTasksToConvertQuery(task));
		if (queue == null) {
			return;
		}
		Integer taskId;
		System.out.println("Converting "+ task.getName()+"...");
		while ((taskId = queue.poll()) != null) {
			UnitOfWork unitOfWork = getUnitOfWork(task, taskId);
			unitOfWork.execute();
		}
		
		if(ConversionStatus.getInstance().getErrored()) {
			System.out.println("\nConverting "+ task.getName()+"... ERROR");
		} else {
			System.out.println("\nConverting "+ task.getName()+"... SUCCESS");
		}

		// wait for all threads to complete
		ConversionProcessor.logger.debug("Exiting");
	}

	private void convertRequest() {
		ConversionProcessor.logger.debug("Entering");
		if(ConversionStatus.getInstance().getErrored()) {
			return;
		}
		int numThreads = Integer.parseInt(Configuration.getProperties().getProperty(Constants.NUM_THREADS_PROPERTY));
        WorkerPool pool = new WorkerPool(numThreads);
		List<RequestUnitOfWork> tasks = null;
        if (_requestId == 0) {
    		System.out.println("\nConverting Requests...");
        	tasks = createConvertTask();
        } else {
    		System.out.println("\nConverting Request:" + _requestId);
        	tasks = createConvertTask(_requestId);
        }
        if(tasks.size() > 0) {
			Iterator<RequestUnitOfWork> i = tasks.iterator();
			
			// convert first request within this thread. this would allow for sql server to compile stored procedure
			RequestUnitOfWork work = i.next();
			work.run();
			
			while (i.hasNext()) {
	            while (!pool.isWorkerAvailable()) {
	    			try {
	    				// remove lock
	    				Thread.sleep(SLEEP_TIME);
	    			} catch (InterruptedException ie) {
	    				// ignored
	    			}
	            }
	            work = i.next();
	            int retry = 0;
	            Worker worker  = pool.getWorkerForWorkload(work);
	            while (worker == null && retry < 5) {
	            	try {
	            		ConversionProcessor.logger.warn("Fail to get work. Request Id = " + work.getRequestId() + " . Retry " + (retry + 1));
	    				Thread.sleep(SLEEP_TIME);
	                	worker  = pool.getWorkerForWorkload(work);
		   			} catch (Throwable  ie) {
		    				// ignored
	    			}
		            retry ++;
	            	if (retry == 5) {
	            		ConversionProcessor.logger.error("Thread pool is corrupted. Creating new pool. Some requests might not be converted. Rerun conversion tool");
	            		pool.finishAll();
	            		pool = new WorkerPool(numThreads);
	            		ConversionStatus.getInstance().incrementRequestsErrored();
	             	}
	            }
			
		        boolean isFinished = false;
		        while (!isFinished) {
		        	if (pool.isAllWorkersAvailable()) {
		        		isFinished = true;
		        	} else {
		 				try {
							// remove lock
							Thread.sleep(SLEEP_TIME);
						} catch (InterruptedException ie) {
							// ignored
						}
		        	}
		        }
				if(ConversionStatus.getInstance().getErrored()) {
					System.out.println("\nConverting Requests... ERROR");
				} else {
					System.out.println("\nConverting Requests... SUCCESS");
				}
	        } 
        }else {
    		System.out.println("\nNo request is found.");
         }
	}
	
	private List<RequestUnitOfWork> createConvertTask() {
		Queue<Integer> queue = getTasksToConvert(getTasksToConvertQuery(TASK.REQUEST));
	   	List<RequestUnitOfWork> result = new ArrayList<RequestUnitOfWork>();
	   	for(Integer requestId : queue) {
	   		RequestUnitOfWork work = new RequestUnitOfWork(requestId, _facilityMapper, _userMapper);
	   		result.add(work);
	   	}
		return result;
	}
	
	private List<RequestUnitOfWork> createConvertTask(int requestId) {
		Queue<Integer> queue = getTasksToConvert(requestId);
	   	List<RequestUnitOfWork> result = new ArrayList<RequestUnitOfWork>();
	   	if (queue.size() > 0) {
	   		RequestUnitOfWork work = new RequestUnitOfWork(requestId, _facilityMapper, _userMapper);
	   		result.add(work);
	   	}
		return result;
	}

	private UnitOfWork getUnitOfWork(TASK task, Integer taskId) {
		if (task == TASK.SUPPLEMENTAL) {
			return new SupplementalUnitOfWork(taskId);
		}
		if (task == TASK.REQUEST) {
			return new RequestUnitOfWork(taskId, _facilityMapper, _userMapper);
		}
		if (task == TASK.REQUESTOR_LETTER) {
			return new RequestorLetterUnitOfWork(taskId);
		}
		return null;
	}

	private String getTasksToConvertQuery(TASK task) {
		if (task == TASK.SUPPLEMENTAL) {
			return Constants.QUERY_ALL_SUPPLEMENTAL;
		}
		if (task == TASK.REQUEST) {
			return Constants.QUERY_ALL_REQUEST;
		}
		if (task == TASK.REQUESTOR_LETTER) {
			return Constants.QUERY_ALL_REQUESTORLETTER;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Queue<Integer> getTasksToConvert(String namedQuery) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.getNamedQuery(namedQuery);
		List<Integer> list = (List<Integer>) query.list();
		tx.commit();
		if (list == null) {
			return null;
		}
		Queue<Integer> queue = new LinkedList<Integer>(list);
		return queue;
	}

	private Queue<Integer> getTasksToConvert(int requestId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.getNamedQuery("getRequestToConvert");
		query.setParameter("requestId", requestId);
		List<Integer> list = (List<Integer>) query.list();
		tx.commit();
		if (list == null) {
			return null;
		}
		Queue<Integer> queue = new LinkedList<Integer>(list);
		return queue;
	}
	
	
	private int totalTasksToConvert(String namedQuery) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.getNamedQuery(namedQuery);
		Integer count = (Integer) query.uniqueResult();
		tx.commit();
		return count;
	}

	private int totalRequestToConvert(int requestId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.getNamedQuery("getRequestToConvert");
		query.setParameter("requestId", requestId);
		Integer count = (Integer) query.uniqueResult();
		tx.commit();
		return count;
	}	
	
	private boolean isWheaton() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.getNamedQuery("isWheaton");
		Integer count = (Integer) query.uniqueResult();
		tx.commit();
		ConversionProcessor.logger.debug("is_wheaton flag: " + (count > 0));
		return (count > 0);
	}

	private String getEPRSVersion() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.getNamedQuery("getLatestEPRSVersion");
		String eprsVersion = (String) query.uniqueResult();
		tx.commit();
		ConversionProcessor.logger.debug("EPRS_Version: "+ eprsVersion);
		return eprsVersion;
	}
	
	private void cleanupRequestCoreTables() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.getNamedQuery("cleanupRequestCoreTables");
		query.executeUpdate();
		tx.commit();
	}
	
	private static void dropStoredProceduresTemporary() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = null;
		query = session.getNamedQuery("drop_ROI_Conversion_RequestMain_SP");
		query.executeUpdate();
		query = session.getNamedQuery("drop_ROI_Conversion_Supplemental_SP");
		query.executeUpdate();
//		query = session.getNamedQuery("drop_ROI_Conversion_RequestorLetter_SP");
//		query.executeUpdate();
		query = session.getNamedQuery("drop_ROI_Conversion_DeleteRequestCore_SP");
		query.executeUpdate();
		query = session.getNamedQuery("drop_ROI_Conversion_Get_Facilities_RequestMain_SP");
		query.executeUpdate();
		query = session.getNamedQuery("drop_ROI_Conversion_Get_All_Facilities_RequestMain_SP");
		query.executeUpdate();
		tx.commit();
	}
	
	public static void createStoredProceduresTemporary() {
		dropStoredProceduresTemporary();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = getRequestMainSP(session);
		query.executeUpdate();
		query = session.getNamedQuery("create_ROI_Conversion_Supplemental_SP");
		query.executeUpdate();
//		query = session.getNamedQuery("create_ROI_Conversion_RequestorLetter_SP");
//		query.executeUpdate();
		query = session.getNamedQuery("create_ROI_Conversion_DeleteRequestCore_SP");
		query.executeUpdate();
		query = session.getNamedQuery("create_ROI_Conversion_Get_Facilities_RequestMain_SP");
		query.executeUpdate();
		query = session.getNamedQuery("create_ROI_Conversion_Get_All_Facilities_RequestMain_SP");
		query.executeUpdate();
		tx.commit();
	}
	
	public static Query getRequestMainSP(Session session) {
		String requestSP = session.getNamedQuery("create_ROI_Conversion_RequestMain_SP").getQueryString();
		
		String requestMainDeclares = session.getNamedQuery("ROI_Conversion_RequestMain_SP_declares").getQueryString();
		requestSP = requestSP.replaceAll("####ROI_Conversion_RequestMain_SP_declares####", requestMainDeclares);
		
		String requestMain = session.getNamedQuery("ROI_Conversion_RequestMain_SP_RequestMainXMLConversion").getQueryString();
		requestSP = requestSP.replaceAll("####ROI_Conversion_RequestMain_SP_RequestMainXMLConversion####", requestMain);
		
		String draftInvoice = session.getNamedQuery("ROI_Conversion_RequestMain_SP_DraftInvoiceXMLConversion").getQueryString();
		draftInvoice = Matcher.quoteReplacement(draftInvoice);
		requestSP = requestSP.replaceAll("####ROI_Conversion_RequestMain_SP_DraftInvoiceXMLConversion####", draftInvoice);
		
		String nonWheatonInvoice = session.getNamedQuery("ROI_Conversion_RequestMain_SP_NonWheatonInvoiceXMLConversion").getQueryString();
		nonWheatonInvoice = Matcher.quoteReplacement(nonWheatonInvoice);
		requestSP = requestSP.replaceAll("####ROI_Conversion_RequestMain_SP_NonWheatonInvoiceXMLConversion####", nonWheatonInvoice);

		String nonWheatonPreBill = session.getNamedQuery("ROI_Conversion_RequestMain_SP_NonWheatonPrebillXMLConversion").getQueryString();
		nonWheatonPreBill = Matcher.quoteReplacement(nonWheatonPreBill);
		requestSP = requestSP.replaceAll("####ROI_Conversion_RequestMain_SP_NonWheatonPrebillXMLConversion####", nonWheatonPreBill);

		String wheatonInvoice = session.getNamedQuery("ROI_Conversion_RequestMain_SP_WheatonInvoiceXMLConversion").getQueryString();
		wheatonInvoice = Matcher.quoteReplacement(wheatonInvoice);
		requestSP = requestSP.replaceAll("####ROI_Conversion_RequestMain_SP_WheatonInvoiceXMLConversion####", wheatonInvoice);
		
		String unbillableInvoice = session.getNamedQuery("ROI_Conversion_RequestMain_SP_UnbillableConversion").getQueryString();
		wheatonInvoice = Matcher.quoteReplacement(unbillableInvoice);
		requestSP = requestSP.replaceAll("####ROI_Conversion_RequestMain_SP_UnbillableConversion####", unbillableInvoice);

		String releaseInvoice = session.getNamedQuery("ROI_Conversion_RequestMain_SP_ReleaseXMLConversion").getQueryString();
		requestSP = requestSP.replaceFirst("####ROI_Conversion_RequestMain_SP_ReleaseXMLConversion####", releaseInvoice);
		logger.trace("\n"+requestSP+"\n");
		return session.createSQLQuery(requestSP);
	}
	
	public boolean validateDefaultBillingFacilityCode(String facilityName, String facilityCode) {
	    Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			String query = session.getNamedQuery("validateDefaultBillingFacilityCode").getQueryString();
			SQLQuery sqlQuery = session.createSQLQuery(query);
			sqlQuery.setString("facilityCode", facilityCode);
			sqlQuery.setString("facilityName", facilityName);
			sqlQuery.addScalar("count", Hibernate.INTEGER);
			Integer count = (Integer) sqlQuery.uniqueResult();
			tx.commit();
	        return (count>0);
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			return false;
		} finally {
			if(session != null && session.isOpen()) {
				session.close();
			}
		}
	}
	
	public void createDefaultBillingFacilityCode(String facilityCode) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		String queryString = session.getNamedQuery("createDefaultBillingFacilityCode").getQueryString();
		queryString = queryString.replaceAll(":facilityCode", facilityCode);
		Query query = session.createSQLQuery(queryString);
		query.executeUpdate();
		tx.commit();
	}
	
	private void audit() {
		if(ConversionStatus.getInstance().getFreeformFacilitiesTotal() > 0 ||
				ConversionStatus.getInstance().getSupplementalsTotal() > 0 ||
				ConversionStatus.getInstance().getRequestsTotal() > 0 ||
				ConversionStatus.getInstance().getRequestorLettersTotal() > 0) {
			
			String auditRemark = Configuration.getProperties().getProperty("EPRS_Version")+" upgrade to 16.0";
			if(ConversionStatus.getInstance().getRequestsErrored() > 0) {
				auditRemark +=". "+ConversionStatus.getInstance().getRequestsErrored()+" requests failed to migrate.";
			}
			
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			Transaction tx = session.beginTransaction();
			String queryString = session.getNamedQuery("auditConversion").getQueryString();
			queryString = queryString.replaceAll(":auditRemark", auditRemark);
			Query query = session.createSQLQuery(queryString);
			query.executeUpdate();
			tx.commit();
		}
	}
}
