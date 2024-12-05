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
package com.mckesson.eig.roi.preconversion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.mckesson.eig.roi.conversion.billinglocation.BillingLocationBuilder;
import com.mckesson.eig.roi.conversion.config.Configuration;
import com.mckesson.eig.roi.conversion.config.Constants;
import com.mckesson.eig.roi.conversion.util.HibernateUtil;
import com.mckesson.eig.roi.conversion.util.StringUtil;


/**
 * @author OFS
 * @date   May 14, 2014
 * @since  HPF 16.0 [ROI]; May 14, 2014
 */
public class PreConversionProcessor {
	
	private static final Logger LOGGER = LogManager.getLogger(PreConversionProcessor.class);
	
	public static final String FILE_DELIM  = "|";
    private static final int FACILITY = 0;
    private static final int FACILITY_CODE = 1;
    private static final int BILLING_LOCATION = 2;
    private static final int USERNAME = 0;
    private static final int USERID = 1;
	
    
    public void dropTemporaryStoredProcedure() {
    	
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.getNamedQuery("drop_ROI_Conversion_Get_All_Facilities_RequestMain_SP");
		query.executeUpdate();
		tx.commit();
    }
    
    public void createTemporaryStoredProcedure() {
    	
    	dropTemporaryStoredProcedure();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.getNamedQuery("create_ROI_Conversion_Get_All_Facilities_RequestMain_SP");
		query.executeUpdate();
		tx.commit();
	}
    
    
    /*
     * This method will generates FacilityToBillingLocation mapping text file.
     */
	public void createFacilityToBillinglocationMappingTxtFile() {
		
		try {	
			
			
			// Create the facility to billing location text file.
			File file = new File(Configuration.getProperty(Constants.FACILITY_BL_MAPPING_FILENAME));
			String absolutePath = file.getAbsolutePath();
			FileOutputStream fos = new FileOutputStream(absolutePath);
	        PrintStream out = new PrintStream(fos, true);
			logConsole("Start generating the FACILITY to BILLING LOCATION MAPPING FILE located in "
					+ absolutePath.substring(0,absolutePath.lastIndexOf(File.separator))
					+ " named " + file.getName());
	        
			logConsole("StartTime : " + Calendar.getInstance().getTime());
	        List<Object[]> results = getFacilitiesToBillingLocationMappingFileData();
	        
	        
	        // Generate and write the response to the TXT file. 
			generateFacilityToBillingLocationTxtFile(results, out,
					getFacilityToBillingLocationMappingHeaders());	
			
			
			logConsole("End Time : " + Calendar.getInstance().getTime());
					
	        logConsole("Generated Successfully. " );
			LOGGER.debug("Facility To BillingLocation mapping file is generated successfully. - ");
			
			
		} catch (Exception e) {
			logConsole("Generated Failed. Please see PreConversion.log file for more details...." 
																			+ e.getMessage());					
			e.printStackTrace();
		}
	}
	
	/*
     * This method will generates UserToBillingLocation mapping text file.
     */
	public void createUserToBillinglocationMappingTxtFile() {
		
		try {	
			logConsole("Start Time : " + Calendar.getInstance().getTime());
			
			File file = new File(Configuration.getProperty(
					Constants.USER_BL_MAPPING_FILE_NAME));
			String absolutePath = file.getAbsolutePath();
			FileOutputStream fos = new FileOutputStream(absolutePath);
	        
			PrintStream out = new PrintStream(fos, true);
			
			logConsole("Start generating the USER to BILLING LOCATION MAPPING FILE located in " 
					+ absolutePath.substring(0,absolutePath.lastIndexOf(File.separator))
					+ " named " + file.getName());
			
	        List<Object[]> results = getUserToBillingLocation();
			
	        generateUserToBillingLocationTxtFile(results, out,
					getUserToBillingLocationMappingHeaders());
	        
	        logConsole("End Time : " + Calendar.getInstance().getTime());
					
	        logConsole("Generated Successfully.");
			LOGGER.debug("User To Billing Location mapping file is generated successfully.");
			
		} catch (Exception e) {
			logConsole("Generated Failed. Please see PreConversion.log file for more details...." 
																	+ e.getMessage());					
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is to to construct and return all the configured 
	 * facility to billing location mapping file data.
	 * @return
	 */
	private List<Object[]> getFacilitiesToBillingLocationMappingFileData() {		

		/*
		 * 1. Get all the facilities (Patient Facility) from all the Request.
		 * 2. Get the Facility info, this will be only available for HPF facility. 
		 * 3. Split the Free form facilities
		 * 4. Convert the Configured billing locaitons to CSV.
		 * 5. Construct object Array result. 
		 */
		
		// Get all the Facilities in Requests.
		BillingLocationBuilder builder = new BillingLocationBuilder(); 
		List<String> allFacilities= builder.getFacilitiesFromAllRequests();
		
		// Retrieved all the HPF facilites 
		List<FacilityInfo> hpfFacilities = getFaciltyInfo(allFacilities);
		
		// Strip Freeform facilites from list.
		List<String> freeFormFacilities = getFreeFormFacilites(allFacilities, hpfFacilities);

		// Get the Configured billing locations.
		List<String> billingLocations = getConfiguredBillingLocations();
		
		//Construct the CSV of the configured billing locations.
		String billingLocCSV = getCSVFromListData(billingLocations);
		
		// Returns the 
		return constructObjectArrayForTXTGeneration(hpfFacilities, 
										 			freeFormFacilities, 
										 			billingLocCSV);
	}

	private List<Object[]> constructObjectArrayForTXTGeneration(
									List<FacilityInfo> hpfFacilities,
									List<String> freeFormFacilities,
									String billingLocCSV) {
		
		int size = hpfFacilities.size() + freeFormFacilities.size();
		List<Object[]> result = new ArrayList<Object[]>(size); 
		
		Object[] row = null;
		for (FacilityInfo facilityInfo : hpfFacilities) {
			
			row = new Object[3];
			row[0] = facilityInfo.getFacilityCode();
			row[1] = facilityInfo.getFacilityName();
			row[2] = billingLocCSV;
			
			result.add(row);
		}

		for (String freeFormFacility : freeFormFacilities) {
			
			row = new Object[3];
			row[0] = freeFormFacility;
			row[1] = freeFormFacility;
			row[2] = billingLocCSV;
			
			result.add(row);
		}		
		
		return result;
	}

	/**
	 * This method will return the list of string into CSV string 
	 * @param listData
	 * @return
	 */
	private String getCSVFromListData(List<String> listData) {
		
		StringBuffer buffer = new StringBuffer();
		for (String bLoc : listData) {
			buffer.append(bLoc).append(Constants.COMMA);
		}
 		
		return buffer.substring(0, buffer.length() - 1);
	}

	/**
	 * This method will find remove the hpf facility and will return all the 
	 * FreeForm facilities.
	 * @param allFacilities
	 * @param hpfFacilities
	 * @return
	 */
	private List<String> getFreeFormFacilites(List<String> allFacilities,
			List<FacilityInfo> hpfFacilities) {
		
		List<String> freeFormFacilities = new ArrayList<String>();
		freeFormFacilities.addAll(allFacilities);
		
		for (FacilityInfo fi : hpfFacilities) {
			
			freeFormFacilities.remove(fi.getFacilityCode());
		}
		
		return freeFormFacilities;
	}

	private List<FacilityInfo> getFaciltyInfo(List<String> facilityCodes) {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<FacilityInfo> list = null;
		String queryString = session.getNamedQuery(
				Constants.QUERY_GET_FACILITY_INFO).getQueryString();

		try {
			Query query = session.createSQLQuery(queryString)
					.addScalar("facilityCode", Hibernate.STRING)
					.addScalar("facilityName", Hibernate.STRING);
			query.setParameterList("facilityCodes", facilityCodes);
			
			list = query.setResultTransformer(Transformers.aliasToBean(FacilityInfo.class)).list();
			tx.commit();
		} catch (HibernateException e) {
			LOGGER.debug("Unable to retrieve Facility Info :", e);
		}
		LOGGER.info("Retrieved the Facility Info from database.");
		return list;
	}
	
	private List<String> getConfiguredBillingLocations() {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<String> list = null;
		String queryString = session.getNamedQuery(
				Constants.QUERY_GET_CONFIGURED_BILLING_LOC).getQueryString();

		try {
			Query query = session.createSQLQuery(queryString)
					.addScalar("facilityCode", Hibernate.STRING);
			list = query.list();
			tx.commit();
		} catch (HibernateException e) {
			LOGGER.debug("Unable to retrieve Facility Info :", e);
		}
		LOGGER.info("Retrieved the Facility Info from database.");
		return list;
	}
	
	/*
	 * This method will retrieve the user to billinglocation mapping details.
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> getUserToBillingLocation() {		
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		String queryString = session.getNamedQuery(
				Constants.QUERY_ROI_PRECONVERSION_USER_MAPPING).getQueryString();
		
		Query query = session.createSQLQuery(queryString);
		List<Object[]> list = null;
		try {
			list = (List<Object[]>) query.list();
			tx.commit();
		} catch (HibernateException e) {
			LOGGER.debug("Unable to retrieve User to Billing Location data :", e);
		}
		LOGGER.info("User To Billing Location is retrieved from database.");
		return list;		
	}
	
	/**
	 * 
	 * This method will generate facility to billinglocation mapping text file.
	 */
	private void generateFacilityToBillingLocationTxtFile(
			List<Object[]> results, PrintStream out, String headers) {
		
		out.println(headers);

        for (Object[] result : results) {

	        String facilityData =
	           new StringBuffer()
	                   .append(StringUtil.getStringTxt(result, FACILITY))
	                   .append(FILE_DELIM)
	                   .append(StringUtil.getStringTxt(result, FACILITY_CODE))
	                   .append(FILE_DELIM)
	                   .append(StringUtil.getStringTxt(result, BILLING_LOCATION))
	                   .toString();
	
	        out.print(facilityData);
	        out.println();	        
        }
        
        out.close();
        LOGGER.debug("Facility To Billing Location text file is constructed.");
	}
	
	/*
	 * This method will generate user to billinglocation mapping text file.
	 */
	private void generateUserToBillingLocationTxtFile(
			List<Object[]> results, PrintStream out, String headers) {
		
		out.println(headers);

        for (Object[] result : results) {

	        String userData =
	           new StringBuffer()
	                   .append(StringUtil.getStringTxt(result, USERNAME))
	                   .append(FILE_DELIM)
	                   .append(StringUtil.getStringTxt(result, USERID))
	                   .append(FILE_DELIM)
	                   .append(StringUtil.getStringTxt(result, BILLING_LOCATION))
	                   .toString();
	
	        out.print(userData);
	        out.println();	        
        }
        
        out.close();
        LOGGER.debug("User To Billing Location text file is constructed.");
	}
	
	/*
	 * This method will generate facility to billinglocation mapping text file headers.
	 */
	private String getFacilityToBillingLocationMappingHeaders() {

        return new StringBuffer()
               .append("FacilityCode|FacilityName|Billing Location")
               .toString();
    }
	
	/*
	 * This method will generate user to billinglocation mapping text file headers.
	 */
	private String getUserToBillingLocationMappingHeaders() {

        return new StringBuffer()
               .append("Username|User ID|BillingLocation")
               .toString();
    }
	
	/*
	 * This method will show the log message in console.
	 */
	private void logConsole(String message) {
		LOGGER.info(message);	
		System.out.println(message);
	}
}