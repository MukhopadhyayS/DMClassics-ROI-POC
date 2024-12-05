/*
 * Copyright 2014 McKesson Corporation and/or one of its subsidiaries. 
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
package com.mckesson.eig.roi.docx.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.mckesson.eig.roi.docx.model.LetterTemplate;
import com.mckesson.eig.roi.docx.utils.HibernateUtil;

/**
 * @author Eric yu
 *
 */
public class ConversionDao {
	private static final Logger logger = LogManager.getLogger(ConversionDao.class);
	
	public LetterTemplate getLetterTempplate(long id) {
	    Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
            Query query = session.getNamedQuery("getLetterTemplateSource");
            query.setResultTransformer(Transformers.aliasToBean(LetterTemplate.class));
            // Setting the result transformer.
            query.setParameter("id", id);
            List<LetterTemplate> files = query.list();
			tx.commit();
            if (files != null && files.size() > 0) {
                LetterTemplate file = files.get(0);
                return file;
            }
        } catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
            if (logger.isDebugEnabled()) {
            	logger.debug("Error LetterTemplate: getLetterTempplate: ", e);
            }
		} finally {
			if(session != null && session.isOpen()) {
				session.close();
			}
		}
        return null;
	}

	public List<LetterTemplate> getAllLetterTempplate() {
	    Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
            Query query = session.getNamedQuery("getAllLetterTemplates");
            query.setResultTransformer(Transformers.aliasToBean(LetterTemplate.class));
            List<LetterTemplate> files = query.list();
			tx.commit();
			return files;
        } catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
            if (logger.isDebugEnabled()) {
            	logger.debug("Error LetterTemplate: getAllLetterTempplate: ", e);
            }
		} finally {
			if(session != null && session.isOpen()) {
				session.close();
			}
		}
        return null;
	}

	public void updateLetterTempplate(long id, InputStream source) {
	    Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
            Query query = session.getNamedQuery("updateLetterTemplateSource");
            query.setParameter("id", id);
            query.setBinary("source", getByteArrayFromInputStream(source));
            query.executeUpdate();
			tx.commit();
        } catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
            if (logger.isDebugEnabled()) {
            	logger.debug("Error LetterTemplate: updateLetterTempplate: ", e);
            }
		} finally {
			if(session != null && session.isOpen()) {
				session.close();
			}
		}
	}
	
	public byte[] getByteArrayFromInputStream(InputStream is) {
	    try {
	    	ByteArrayOutputStream os = new ByteArrayOutputStream();
	        byte[] buffer = new byte[0xFFFF];
	        for (int len; (len = is.read(buffer)) != -1;) {
	            os.write(buffer, 0, len);
	        }
	        os.flush();
	        return os.toByteArray();
	    }
	    catch (IOException e) {
	        return null;
	    }
	}

}
