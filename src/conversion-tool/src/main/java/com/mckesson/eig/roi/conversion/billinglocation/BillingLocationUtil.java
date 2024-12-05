package com.mckesson.eig.roi.conversion.billinglocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mckesson.eig.roi.conversion.exceptions.ValidationException;
import com.mckesson.eig.roi.conversion.util.HibernateUtil;

public class BillingLocationUtil {
	private static final Logger logger = LogManager.getLogger(BillingLocationUtil.class);

	public BillingLocation getBillingLocation(long requestId, Map<String, BillingLocation> userMap, Map<String, 
			BillingLocation> facilityMap)  throws ValidationException {
		List<String> facilities = getFacilitiesFromRequest(requestId);
		BillingLocation location = null;
		// No facility involved in the request, return defaultBillingLocation
		if(facilities == null || facilities.size() == 0) {
			location = getBillingLocationByUser(requestId, userMap, facilities);
			if(location == null) {
				logger.error("Cannot find a valid billing location for request: " + requestId);
				throw new ValidationException("Cannot find a valid billing location on facility for request: " + requestId);
			}
			return location;
		}
		location = getBillingLocationByFacilities(facilityMap, facilities);
		if (location != null) {
			return location;
		}
		location = getBillingLocationByUser(requestId, userMap, facilities);
		if (location != null) {
			return location;
		}
		logger.error("Cannot find a valid billing location on facility for request: " + requestId);
		throw new ValidationException("Cannot find a valid billing location for request: " + requestId);
	}
	
	private BillingLocation getBillingLocationByUser(long requestId, Map<String, BillingLocation> userMap, List<String> facilities) throws ValidationException {
		String username = getUserNameFromRequest(requestId);
		if(username == null) {
			logger.error("Cannot find a valid user for request: " + requestId);
			throw new ValidationException("Cannot find a valid billing location on user for request: " + requestId);
		}
		BillingLocation result = getBillingLocatioByUser(userMap, username);
		if (result == null) {
			logger.error("Cannot find a valid billing location for request: " + requestId);
			throw new ValidationException("Cannot find a valid billing location on user: " + username + " for request: " + requestId);
		}
		String log = getLoggingStr(requestId, username, facilities, result);
		logger.info(log);
		return result;
	}
	
	private String getLoggingStr(long requestId, String username, List<String> facilities, BillingLocation location) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(requestId + "|" );
		buffer.append(username+ "|" );
		if (facilities == null || facilities.size() == 0) {
			buffer.append("No Facility found in request|" );
		} else {
			int i = 0;
			int size = facilities.size();
			for(String facility : facilities) {
				buffer.append(facility);
				if (i < size - 1) {
					buffer.append(",");
				} else {
					buffer.append("|");
				}
				i++;
			}
		}
		buffer.append(location.getCode());
		return buffer.toString();
	}
	
	private List<String> getFacilitiesFromRequest(long requestId) {
		List<String> result = new ArrayList<String> ();
	    Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createSQLQuery("ROI_Conversion_Get_Facilities_RequestMain :requestId");
			query.setLong("requestId", requestId);
			List<String> list = query.list();
			if (list != null) {
				for (String s : list) {
					if(!s.equals("")) {
						result.add(s);
					}
				}
			}
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
	
	private String getUserNameFromRequest(long requestId) {
	    Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			String query = session.getNamedQuery("getUsernameFromRequestId").getQueryString();
			SQLQuery sqlQuery = session.createSQLQuery(query);
			sqlQuery.setLong("requestId", requestId);
			sqlQuery.addScalar("name", Hibernate.STRING);
			List<String> list =  sqlQuery.list();
			tx.commit();
			if(list == null || list.size() == 0) {
	        	return null;
	        }
	        return list.get(0).trim().toUpperCase();
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
	
	private BillingLocation getBillingLocationByFacilities( Map<String, BillingLocation> facilityMap, List<String> faciliites) {
		BillingLocation location = facilityMap.get(faciliites.get(0));
		if(faciliites.size() == 1) {
			return location;
		}
		for (String facility : faciliites) {
			BillingLocation location2 = facilityMap.get(facility);
			if(location != location2) {
				return null;
			}
		}
		return location;
	}

	private BillingLocation getBillingLocatioByUser( Map<String, BillingLocation> userMap, String userName) {
		return userMap.get(userName);
	}

	public static void logHeader() {
		logger.info("RequestId|UserId|Facilities|Billing Location");
	}
}
