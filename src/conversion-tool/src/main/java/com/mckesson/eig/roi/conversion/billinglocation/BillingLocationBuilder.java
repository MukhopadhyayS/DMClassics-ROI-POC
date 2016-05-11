package com.mckesson.eig.roi.conversion.billinglocation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.mckesson.eig.roi.conversion.config.Configuration;
import com.mckesson.eig.roi.conversion.exceptions.ValidationException;
import com.mckesson.eig.roi.conversion.util.HibernateUtil;
import com.mckesson.eig.roi.conversion.util.StringUtil;

public class BillingLocationBuilder {
	
	private static List<BillingLocation> _locations;
	
	
	public Map<String, BillingLocation> getFacilityMapper() throws FileNotFoundException, ValidationException {
		File f = Configuration.getFacilityMapper();
		if (!f.exists()) {
			System.out.print("\nFacility Mapping file is not exists:-" + f.getAbsolutePath());
			throw new FileNotFoundException("Facility Mapping file is not exists");
		}
		System.out.print("\nValidate Facility Mapping file. It may take a few minutes...");
		List<String> facilities = readFile(f);
		return generateFacilitiesMapper(facilities);
	}
	
	private  Map<String, BillingLocation> generateFacilitiesMapper(List<String> facilities) throws ValidationException {
		Map<String, BillingLocation> result = new HashMap<String, BillingLocation>();
		if(facilities == null || facilities.size() == 0) {
			throw new ValidationException("Facility Mapping file is empty.");
		}
		List<String> mapperFacilities = getMappingFacilities(facilities);
		List<String> requestFacilities = getFacilitiesFromAllRequests();
		List<String> errorFacilities = new ArrayList<String>();
		for(String facility: requestFacilities) {
			if(!mapperFacilities.contains(facility)) {
				errorFacilities.add(facility);
			}
		}
		if (errorFacilities.size() > 0) {
			String errorMsg = "Facilities are not found in Facility Mapping file: ";
			for (String s : errorFacilities) {
				errorMsg += s + ",";
			}
			errorMsg = errorMsg.substring(0, errorMsg.length() - 1) + ".";
			throw new ValidationException(errorMsg);
		}
		for(String s : facilities) {
			if(!s.trim().equals("")) {
				String[] strs = s.split("\\|");
				if (strs.length != 3) {
					throw new ValidationException("Facility Mapping file has wrong format.");
				}
				String facility = strs[0].trim();
				if (requestFacilities.contains(facility)) {
					BillingLocation b = getFacilityBillingLocation(strs[2].trim());
					if (b != null) {
						result.put(facility, b);
					} else {
						errorFacilities.add(facility);
					}
				}
			}
		}
		if (errorFacilities.size() > 0) {
			String errorMsg = "Facilities have invalid Billing Location in Facility Mapping file: ";
			for (String s : errorFacilities) {
				errorMsg += s + ",";
			}
			errorMsg = errorMsg.substring(0, errorMsg.length() - 1) + ".";
			throw new ValidationException(errorMsg);
		}
		return result;
	}
	
	private List<String> getMappingFacilities(List<String> facilities) throws ValidationException {
		List<String> result =  new ArrayList<String>();
		for(String s : facilities) {
			if(!s.trim().equals("")) {
				String[] strs = s.split("\\|");
				if (strs.length != 3) {
					throw new ValidationException("Facility Mapping file has wrong format.");
				}
				String facility = strs[0].trim();
				if (result.contains(facility)) {
					throw new ValidationException("Facility " + facility + " is duplicate in Facility Mapping file.");
				}
				result.add(facility);
			}
		}
		return result;
	}

	public  Map<String, BillingLocation> getUserMapper() throws FileNotFoundException, ValidationException {
		File f = Configuration.getUserMapper();
		if (!f.exists()) {
			System.out.print("\nUser Mapping file is not exists:-" + f.getAbsolutePath());
			throw new FileNotFoundException("User Mapping file is not exists");
		}
		System.out.print("\nValidate User Mapping file ...");
		List<String> users = readFile(f);
		return generateUserMapper(users);
	}
	
	private  Map<String, BillingLocation> generateUserMapper(List<String> users) throws ValidationException {
		Map<String, BillingLocation> result = new HashMap<String, BillingLocation>();
		if(users == null || users.size() == 0) {
			throw new ValidationException("User Mapping file is empty.");
		}
		List<Long> invalidRequestIds = getInvalidUserRequests();
		if (invalidRequestIds.size() > 0) {
			String errorMsg = "Request(s) has invalid userinstance id: ";
			for (Long id : invalidRequestIds) {
				errorMsg += id + ",";
			}
			errorMsg = errorMsg.substring(0, errorMsg.length() - 1) + ".";
			throw new ValidationException(errorMsg);
		}
		List<String> mapperUserIds = getMappingUserIds(users);
		List<String> requestUsers = getUserNamesFromAllRequest();
		List<String> errorUsers = new ArrayList<String>();
		for(String userId: requestUsers) {
			if(!mapperUserIds.contains(userId)) {
				errorUsers.add(userId);
			}
		}
		if (errorUsers.size() > 0) {
			String errorMsg = "User(s) is not found in User Mapping file: ";
			for (String s : errorUsers) {
				errorMsg += s + ",";
			}
			errorMsg = errorMsg.substring(0, errorMsg.length() - 1) + ".";
			throw new ValidationException(errorMsg);
		}
		for(String s : users) {
			if(!s.trim().equals("")) {
				String[] strs = s.split("\\|");
				if (strs.length != 3) {
					throw new ValidationException("User Mapping file has wrong format.");
				}
				String userId = strs[1].trim().toUpperCase();
				if(requestUsers.contains(userId)) {
					BillingLocation b = getUserBillingLocation(strs[2].trim());
					if (b != null) {
						result.put(userId, b);
					} else {
						errorUsers.add(userId);
					}
				}
			}
		}
		if (errorUsers.size() > 0) {
			String errorMsg = "User(s) has invalid Billing Location in User Mapping file: ";
			for (String s : errorUsers) {
				errorMsg += s + ",";
			}
			errorMsg = errorMsg.substring(0, errorMsg.length() - 1) + ".";
			throw new ValidationException(errorMsg);
		}
		return result;
	}
	
	private List<String> getMappingUserIds(List<String> users) throws ValidationException {
		List<String> result =  new ArrayList<String>();
		for(String s : users) {
			if(!s.trim().equals("")) {
				String[] strs = s.split("\\|");
				if (strs.length != 3) {
					throw new ValidationException("User Mapping file has wrong format.");
				}
				String user = strs[1].trim().toUpperCase();
				if(result.contains(user)) {
					throw new ValidationException("Userid " + user + " is duplicate in User Mapping file.");
				}
				result.add(user);
			}
		}
		return result;
	}
	
	
	private List<Long> getInvalidUserRequests() {
	    Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			String query = session.getNamedQuery("getInvalidUserRequests").getQueryString();
			SQLQuery sqlQuery = session.createSQLQuery(query);
			sqlQuery.addScalar("requestId", Hibernate.LONG);
			List<Long> list =  sqlQuery.list();
			tx.commit();
			if (list == null) {
				return new ArrayList<Long>();
			}
	        return list;
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			if(session != null && session.isOpen()) {
				session.close();
			}
		}
	}
	
	private List<String> getUserNamesFromAllRequest() {
		List<String> result = new ArrayList<String> ();
	    Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			String query = session.getNamedQuery("getUsernamesFromAllRequests").getQueryString();
			SQLQuery sqlQuery = session.createSQLQuery(query);
			sqlQuery.addScalar("name", Hibernate.STRING);
			List<String> list =  sqlQuery.list();
			tx.commit();
			if (list != null) {
				for(String s : list) {
					result.add(s.trim().toUpperCase());
				}
			}
	        return result;
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			if(session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	private BillingLocation getFacilityBillingLocation(String facility) {
		List<BillingLocation> locations = getAllBillingLocation();
		for(BillingLocation location : locations) {
			if(facility.equals(location.getCode())) {
				return location;
			}
		}
		return null;
	}
	
	private BillingLocation getUserBillingLocation(String username) {
		List<BillingLocation> locations = getAllBillingLocation();
		for(BillingLocation location : locations) {
			if(username.equals(location.getCode())) {
				return location;
			}
		}
		return null;
	}
	
	private synchronized List<BillingLocation> getAllBillingLocation() {
		if (_locations != null) {
			return _locations;
		} 
	    Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
            String queryString = session.getNamedQuery("getBillingLocations")
                    .getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(queryString);
            sqlQuery.addScalar("name", Hibernate.STRING);
            sqlQuery.addScalar("code", Hibernate.STRING);
            sqlQuery.setResultTransformer(Transformers
                    .aliasToBean(BillingLocation.class));
            List<BillingLocation> result = (List<BillingLocation>) sqlQuery.list();
            _locations = result;
			tx.commit();
           return result;
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			if(session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	private List<String> readFile(File f) {
		InputStream fis = null;
		BufferedReader br = null;
		List<String> result = new ArrayList<String>();
		String line; 
		try{
			fis = new FileInputStream(f);
			br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
		} catch (Exception e) {
			
		} finally {
			// Done with the file
			try {
				if(br != null) {
					br.close();
					br = null;
				}
			} catch(Exception ee) {
				
			}
			if (fis != null) {
				fis = null;	
			}
		}
		return result;
	}

	public List<String> getFacilitiesFromAllRequests() {
		List<String> result = new ArrayList<String> ();
	    Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createSQLQuery("ROI_Conversion_Get_All_Facilities_RequestMain");
			List<String> list = query.list();
			if (list != null) {
				for (String s : list) {
					if(!StringUtil.isEmpty(s)) {
						result.add(s);
					}
				}
			}
			tx.commit();
           return result;
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
		} finally {
			if(session != null && session.isOpen()) {
				session.close();
			}
		}
		return null;
	}
	
}
