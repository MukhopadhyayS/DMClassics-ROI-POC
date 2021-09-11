/*
 * Copyright 2007-2008 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.workflow.dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.mckesson.dm.core.common.logging.OCLogger;
import org.hibernate.Session;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.persistence.db.DbPersistenceServiceFactory;
import org.jbpm.svc.Services;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.WorkflowException;
import com.mckesson.eig.workflow.worklist.api.WorklistEC;

/**
 * @author Pranav Amarasekaran
 * @date   Dec 6, 2007
 * @since  HECM 1.0; Aug 30, 2007
 *
 * This class holds all the common methods which have to be used in the
 * local service layer of the workflow component. All the implementation
 * classes of local service in the workflow component extend this
 * abstract class.
 */
public abstract class AbstractWorkflowDAO
extends HibernateDaoSupport
implements WorkflowDAO {

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( AbstractWorkflowDAO.class);

    public static final String WORKLIST_PROCESS_NAME = "eig.worklist";

    private static JbpmConfiguration _jc;

    protected AbstractWorkflowDAO() {
        super();
    }

    /**
     * @see com.mckesson.eig.workflow.service.WorkflowDAO#loadActor
     * (com.mckesson.eig.workflow.api.Actor)
     */
    public Actor loadActor(final Actor actor) {

        Object[] args = new Object[] {actor.getAppID(),
                                      actor.getEntityType(),
                                      actor.getEntityID()};

        List< ? > actors = getHibernateTemplate().findByNamedQuery("retrieveActor", args);

        if (actors != null && actors.size() > 0) {
            return (Actor) actors.get(0);
        }

        create(actor);
        LOG.debug("actor created : " + actor.toString());
        return actor;
    }
    
    /**
     * This method loads all the actors. If the actor is not available in
     * the system then a new actor is created with the specified details.
     *
     * @param actors
     *          Actors that are to be loaded.
     */
    protected void loadActors(Actors actors) {

        if (actors == null) {
            return;
        }

        HashSet<Actor> loadedActors = new HashSet<Actor>(actors.getActors().size());
        for (Iterator<Actor> i = actors.getActors().iterator(); i.hasNext();) {
            Actor actor = i.next();
            loadedActors.add(loadActor(actor));
        }
        actors.setActors(loadedActors);
    }

    /**
     * This method is used for storing individual objects and handling data
     * integrity violation exceptions.
     *
     * @param object
     *          Object to create in the database.
     *
     * @return Serializable The primary key value of the object
     *         inserted.
     */
    public Serializable create(Object object) {

        try {
            return getHibernateTemplate().save(object);
        } catch (DataIntegrityViolationException e) {
            throw new WorkflowException(e, ClientErrorCodes.DATA_INTEGRITY_VIOLATION);
        }
    }

    /**
     * This method is used for delete of individual objects.
     *
     * @param object
     *            Object to delete in the database.
     *
     * @return void
     */
    public void delete(Object object) {

        try {
            getHibernateTemplate().delete(object);
        } catch (DataIntegrityViolationException e) {
            throw new WorkflowException(e, ClientErrorCodes.DATA_INTEGRITY_VIOLATION);
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new WorkflowException(e, ClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION);
        }
    }

    /**
    * Copy the state of the given object onto the persistent object with the same
    * identifier. If there is no persistent instance currently associated with
    * the session, it will be loaded. Return the persistent instance. If the
    * given instance is unsaved, save a copy of and return it as a newly persistent
    * instance. The given instance does not become associated with the session.
    * This operation cascades to associated instances if the association is mapped
    * with cascade="merge".
    *
    * @param object
    *           a detached instance with state to be copied
    * @return
    *           an updated persistent instance
    */
    public Object merge(Object object) {
        try {
            return getHibernateTemplate().merge(object);
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new WorkflowException(e, ClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION);
        }
    }

    /**
     * Either save() or update() the given instance, depending upon the value of
     * its identifier property. By default the instance is always saved. This behaviour may be
     * adjusted by specifying an unsaved-value attribute of the identifier property
     * mapping. This operation cascades to associated instances if the association is mapped 
     * with cascade="save-update".
     *
     * @param object a transient or detached instance containing new or updated state
     * 
     * @throws HibernateOptimisticLockingFailureException
     */
    public void saveOrUpdate(Object object) {
        try {
            getHibernateTemplate().saveOrUpdate(object);
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new WorkflowException(e, ClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION);
        }
    }

    /**
     * This method basically acts as a accessor method for jbpm configuration. if the configuration
     * is not loaded this method loads the configuration and sets the persistence factory that will
     * be used by the jbpm for further transactions
     *
     * @return JbpmConfiguration
     */
    protected JbpmConfiguration getJC() {

        if (_jc == null) {

            _jc = JbpmConfiguration.getInstance();
            ((DbPersistenceServiceFactory) _jc.getServiceFactory(Services.SERVICENAME_PERSISTENCE))
                  .setSessionFactory(getSessionFactory());
        }
        return _jc;
    }

    /**
     * This method creates the JbpmContext from the Jbpm  configuration.
     *
     * @return JbpmContext
     */
    public JbpmContext createJbpmContext() {
        
        JbpmContext jbpmContext = getJC().createJbpmContext();
        jbpmContext.setSession(getHibernateTemplate().getSessionFactory().getCurrentSession());
        return jbpmContext;
    }

    /**
     * Helper method used to retrieve individual object
     *
     * @param cls Object to retrieve in the database.
     * @return id The primary key value of the object to be retrieved.
     */
    public Object get(Class< ? > cls, Serializable id) {

        Object obj = getHibernateTemplate().get(cls, id);

        if (obj == null) {
           throw new WorkflowException(WorklistEC.ERROR_CODE_DESC_MAP.get(WorklistEC.INVALID_ID),
                                       WorklistEC.INVALID_ID);
        }
        return obj;
    }

    /**
     * Method to get the application name for the given application id
     * @param applnId
     * @return
     */
    public String getApplicationName(final long applnId) {

    	final String rawSQL =  "SELECT NAME FROM HECM_WLIST.wf_app WHERE APP_ID = ?";
    	 String applicationName = (String) getHibernateTemplate().execute(
             new HibernateCallback() {
                 public String doInHibernate(Session s) {
                     String appName = "";
                	 List list = s.createSQLQuery(rawSQL).setParameter(0, applnId).list();
                     if (list != null && list.size() > 0) {
                    	 appName = (String) list.get(0);
                     }
                     return appName;
                 }
             });
    	 return applicationName;
    }

    /**
     * Helper method used to fetch the actor id.
     * 
     * @param actor
     * @return
     */
    public long getActorID(Actor actor) {

        Object[] args = new Object[] {actor.getAppID(),
                                      actor.getEntityType(),
                                      actor.getEntityID()};

        List< ? > actors = getHibernateTemplate().findByNamedQuery("retrieveActor", args);
        Actor a = null;
        if (actors != null && actors.size() > 0) {
            a = (Actor) actors.get(0);
        }

        return a.getActorID();
    }
}
