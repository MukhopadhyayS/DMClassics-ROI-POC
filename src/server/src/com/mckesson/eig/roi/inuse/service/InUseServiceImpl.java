/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.roi.inuse.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.util.ObjectUtils;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.base.service.BaseROIService;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.inuse.base.api.InUseClientErrorCodes;
import com.mckesson.eig.roi.inuse.base.api.InUseException;
import com.mckesson.eig.roi.inuse.dao.InUseDAO;
import com.mckesson.eig.roi.inuse.model.InUseRecord;
import com.mckesson.eig.roi.inuse.model.InUseRecordList;
import com.mckesson.eig.roi.request.dao.RequestCoreDAO;
import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.wsfw.session.CxfWsSession;



/**
 * @author OFS
 * @date   Nov 11, 2008
 * @since  ROI HPF 13.1
 */
@WebService(serviceName="InUseService", endpointInterface="com.mckesson.eig.roi.inuse.service.InUseService",
targetNamespace="urn:eig.mckesson.com", portName="InUsePort", name="InUseServiceImpl")
public class InUseServiceImpl implements InUseService {

    private static final OCLogger LOG = new OCLogger(InUseServiceImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static final int MILLIS_IN_MINUTE = 60000;
    private static final int DEFAULT_GRACE_PERIOD = 5;
    private static final String QUARTZ_GROUP_NAME = "com.mckesson.eig.roi.inuse.service";
    private static final String QUARTZ_JOB_NAME = "InUseQuartzJob";
    private static final String AUTHENTICATED_USER = "authenticated_roi_user";

    private Scheduler _scheduler;
    private int _gracePeriodMinutes = DEFAULT_GRACE_PERIOD;
    private Object _sem = new Object();
    private boolean _initialized;

    public InUseServiceImpl() {
        super();
    }
    
    private InUseDAO getDAO(String name) {
        return (InUseDAO) SpringUtilities.getInstance().getBeanFactory().
                                                          getBean(name);
    }

  
    @Override
    @WebMethod(exclude = true)
    public void initService() throws SchedulerException {
                
        InUseDAO _inUseDAO = (InUseDAO) getDAO("InUseDAO");
        synchronized (_sem) {
            if (_initialized)  {
                return;
            }
            if (_scheduler == null) {
                throw new IllegalStateException("scheduler not set");
            }
            if (_inUseDAO == null) {
                throw new IllegalStateException("inUseDAO not set");
            }

            List<InUseRecord> records =
                _inUseDAO.retrieveExpiredRecords(_gracePeriodMinutes);
            if (records.size() > 0) {
                _inUseDAO.deleteRecordsOnInit(records);
            }

            try {                
                JobDetail job = JobBuilder.newJob(InUseQuartzJob.class)
                        .withIdentity(QUARTZ_JOB_NAME, QUARTZ_GROUP_NAME)
                        .usingJobData("gracePeriodMin", getGracePeriodMinutes())
                        .storeDurably(true)
                        .build();
                job.getJobDataMap().put("InUseDAO", _inUseDAO);
                
                _scheduler.addJob(job, true);
                _scheduler.start();

                records = _inUseDAO.retrieveAll();
                Iterator<InUseRecord> recordsIterator = records.iterator();
                while (recordsIterator.hasNext()) {

                    InUseRecord record = recordsIterator.next();
                    createTimerForRecord(record);
                }
                _initialized = true;
            } catch (SchedulerException e) {
                throw new ApplicationException(e);
            } catch (Throwable t) {
                throw new InUseException(InUseClientErrorCodes.IN_USE_OPERATION_FAILED);
            }
        }
    }
    

    /**
     * @see InUseService#releaseInUseRecord(String, String, String, String)
     */
    @Override
    public void releaseInUseRecord(String objectType,
                                   String objectID,
                                   String applicationID,
                                   String userID) {

        InUseDAO _inUseDAO = (InUseDAO) getDAO("InUseDAO");
        final String logSM = "releaseInUseRecord(objectType, objectID, applicationID, userID)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:"
                        + objectType + ":"
                        + objectID + ":"
                        + applicationID + ":"
                        + userID);
        }

        try {
            
                 
            InUseServiceValidator validator = new InUseServiceValidator();
            if (!validator.validateFields(objectType, objectID, applicationID, userID)) {
                throw validator.getException();
            }

            synchronized (_sem) {

                if (!_initialized) {
                    throw new IllegalStateException("service not initialized");
                }

                InUseRecord record = _inUseDAO.retrieveByObjectTypeAndID(objectType, objectID);
                if (record == null) {
                    throw new InUseException(InUseClientErrorCodes.RECORD_NOT_FOUND);
                }

                if (!ObjectUtils.nullSafeEquals(userID, record.getUserID())
                    || !ObjectUtils.nullSafeEquals(applicationID, record.getApplicationID())) {
                    throw new InUseException(InUseClientErrorCodes.INVALID_APPLICATION_DATA);
                }

                 cancelTimerForRecord(record);
                _inUseDAO.deleteRecord(record);
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }
        } catch (InUseException ie) {
            throw ie;
        } catch (Throwable t) {
            throw new InUseException(InUseClientErrorCodes.IN_USE_OPERATION_FAILED);
        }
    }

    /**
     * @see InUseService#createInUseRecord(String, String, String, String, int)
     */
    @Override
    public InUseRecord createInUseRecord(String objectType,
                                         String objectID,
                                         String applicationID,
                                         String userID,
                                         int expMin) {

        InUseDAO _inUseDAO = (InUseDAO) getDAO("InUseDAO");
        final String logSM = "createInUseRecord(objectType, "
                                                + "objectID, "
                                                + "applicationID, "
                                                +  "userID, "
                                                + "expiresMinutes)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:"
                    + objectType + ":"
                    + objectID + ":"
                    + applicationID + ":"
                    + userID);
        }

        InUseRecord record = null;
        try {
            

            InUseServiceValidator validator = new InUseServiceValidator();
            if (!validator.validateFields(objectType, objectID, applicationID, userID, expMin)) {
                throw validator.getException();
            }

            synchronized (_sem) {

                if (!_initialized) {
                    throw new IllegalStateException("service not initialized");
                }

                record = _inUseDAO.retrieveByObjectTypeAndID(objectType, objectID);
                if (record != null) {
                    throw new InUseException(InUseClientErrorCodes.RECORD_ALREADY_IN_USE);
                }

                record = _inUseDAO.retrieveRecordByAppIdAndUserID(applicationID, userID);
                if ((record != null) && ObjectUtils.nullSafeEquals(userID, record.getUserID())) {
                    cancelTimerForRecord(record);
                    _inUseDAO.deleteRecord(record);
                }

                record = new InUseRecord();
                record.setExpiresMinutes(expMin);
                record.setApplicationID(applicationID);
                record.setObjectID(objectID);
                record.setObjectType(objectType);
                record.setUserID(userID);

                User user = getUser();
                Calendar cal = Calendar.getInstance();
                Date d = cal.getTime();
                record.setModifiedDate(d);
                record.setModifiedBy(user.getInstanceId());
                record.setCreatedDate(d);
                record.setCreatedBy(user.getInstanceId());
                _inUseDAO.createRecord(record);
                createTimerForRecord(record);
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + record);
            }
            return record;
        } catch (InUseException ie) {
            throw ie;
        } catch (Throwable t) {
            _inUseDAO.deleteRecord(record);
            throw new InUseException(InUseClientErrorCodes.IN_USE_OPERATION_FAILED);
        }
    }

    /**
     * @see InUseService#retrieveInUseRecord(String, String)
     */
    @Override
    public InUseRecord retrieveInUseRecord(String objectType, String objectID) {

        InUseDAO _inUseDAO = (InUseDAO) getDAO("InUseDAO");
        final String logSM = "retrieveInUseRecord(objectType, objectID)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + objectType + ":" + objectID);
        }

        try {

            InUseServiceValidator validator = new InUseServiceValidator();
            if (!validator.validateFields(objectType, objectID)) {
                throw validator.getException();
            }

            synchronized (_sem) {
                if (!_initialized) {
                    throw new IllegalStateException("service not initialized");
                }
            }

            InUseRecord record = _inUseDAO.retrieveByObjectTypeAndID(objectType, objectID);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + record);
            }
            return record;
        } catch (InUseException ie) {
            throw ie;
        }
    }

    /**
     * @see InUseService#retrieveInUseRecordsByType(java.lang.String)
     */
    @Override
    public InUseRecordList retrieveInUseRecordsByType(String objectType) {

        InUseDAO _inUseDAO = (InUseDAO) getDAO("InUseDAO");
        final String logSM = "retrieveInUseRecordsByType(objectType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + objectType);
        }

        try {

            InUseServiceValidator validator = new InUseServiceValidator();
            if (!validator.validateFields(objectType)) {
                throw validator.getException();
            }

            synchronized (_sem) {
                if (!_initialized) {
                    throw new IllegalStateException("service not initialized");
                }
            }
            List<InUseRecord> list =  _inUseDAO.retrieveAllByType(objectType);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + list.size());
            }
            return new InUseRecordList(list);
        } catch (InUseException ie) {
            throw ie;
        }
    }

    /**
     *
     * @see com.mckesson.eig.inuse.service.InUseService#retrieveAllInUseRecords()
     */
    @Override
    public InUseRecordList retrieveAllInUseRecords() {

        InUseDAO _inUseDAO = (InUseDAO) getDAO("InUseDAO");
        final String logSM = "retrieveAllInUseRecords()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        try {

            synchronized (_sem) {
                if (!_initialized) {
                    throw new IllegalStateException("service not initialized");
                }
            }
            List<InUseRecord> list = _inUseDAO.retrieveAll();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + list.size());
            }
            return new InUseRecordList(list);
        } catch (InUseException ie) {
            throw ie;
        }
    }

    /**
     * @see InUseService#touchInUseRecord(String, String, String, String)
     */
    @Override
    public InUseRecord touchInUseRecord(String objectType,
                                        String objectID,
                                        String applicationID,
                                        String userID) {

        InUseDAO _inUseDAO = (InUseDAO) getDAO("InUseDAO");
        final String logSM = "touchInUseRecord(objectType, objectID, applicationID, userID)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        InUseRecord record = null;
        try {

            InUseServiceValidator validator = new InUseServiceValidator();
            if (!validator.validateFields(objectType, objectID, applicationID, userID)) {
                throw validator.getException();
            }

            synchronized (_sem) {

                if (!_initialized) {
                    throw new IllegalStateException("service not initialized");
                }

                record = _inUseDAO.retrieveByObjectTypeAndID(objectType, objectID);
                if (record == null) {
                    throw new InUseException(InUseClientErrorCodes.RECORD_NOT_FOUND);
                }

                if (!ObjectUtils.nullSafeEquals(userID, record.getUserID())
                    || !ObjectUtils.nullSafeEquals(applicationID, record.getApplicationID())) {
                    throw new InUseException(InUseClientErrorCodes.INVALID_APPLICATION_DATA);
                }

                User user = getUser();
                record.setModifiedDate(new Date());
                record.setModifiedBy(user.getInstanceId());
                record = _inUseDAO.updateRecord(record);
                updateTimerForRecord(record);
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + record);
            }

            return record;
        } catch (InUseException ie) {
            throw ie;
        } catch (Throwable thrown) {
            _inUseDAO.deleteRecord(record);
            throw new ApplicationException(thrown);
        }
    }


   /**
    *
    * @see com.mckesson.eig.inuse.service.InUseService#clearExpiredRecords()
    */
    @WebMethod(exclude = true)
    @Override
    public List<InUseRecord> clearExpiredRecords() {

        InUseDAO _inUseDAO = (InUseDAO) getDAO("InUseDAO");
        List<InUseRecord> records;
        synchronized (_sem) {
            if (!_initialized) {
                throw new IllegalStateException("service not initialized");
            }

            records = _inUseDAO.retrieveExpiredRecords(_gracePeriodMinutes);
            if (records.size() > 0) {
                _inUseDAO.deleteRecords(records);
            }
        }

        return records;
    }

    /**
     * @see com.mckesson.eig.inuse.service.InUseService
     * #retrieveObjectIds(java.lang.String, java.lang.String)
     */
    @Override
    public InUseRecordList retrieveInUseRecordsByIDs(String objectType,
                                                       String idsCSV,
                                                       String appId,
                                                       String userId) {

        InUseDAO _inUseDAO = (InUseDAO) getDAO("InUseDAO");
        final String logSM = "retrieveObjectIds(objectType, idsCSV, appId, userId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + objectType + ":" + idsCSV + ":" + appId + ":" + userId);
        }

        try {

            InUseServiceValidator validator = new InUseServiceValidator();
            if (!validator.validateFields(objectType, idsCSV, appId, userId)) {
                throw validator.getException();
            }

            List<InUseRecord> records = _inUseDAO.retrieveInUseRecordsByIds(objectType,
                                                                            idsCSV,
                                                                            appId,
                                                                            userId);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + records.size());
            }

            return new InUseRecordList(records);

            } catch (InUseException ie) {
                throw ie;
            }
    }

   /**
    *
    * @see com.mckesson.eig.inuse.service.InUseService#getGracePeriodMinutes()
    */
    @WebMethod(exclude = true)
    @Override
    public int getGracePeriodMinutes() {
        return _gracePeriodMinutes;
    }

    /**
     *
     * @see com.mckesson.eig.inuse.service.InUseService#setGracePeriodMinutes(int)
     */
    @WebMethod(exclude = true)
    @Override
    public void setGracePeriodMinutes(int gracePeriodMinutes) {
        _gracePeriodMinutes = gracePeriodMinutes;
    }

    /**
     * @param scheduler
     */
    @WebMethod(exclude = true)
    @Override
    public void setScheduler(Scheduler scheduler)  {
        _scheduler = scheduler;
    }

    /**
     * @param dao for record persistence
     */
    /*
     * @WebMethod(exclude = true)
     * 
     * @Override public void setInUseDAO(InUseDAO dao) { _inUseDAO = dao; }
     */

    private void cancelTimerForRecord(InUseRecord record) throws SchedulerException {        
        if (DO_DEBUG) {
            if (null == record) {
                LOG.info("Cancel Timer Job:record argument was null");
            }
        }
        String name = Long.toString(record.getRecordSequence());
        TriggerKey triggerKey = TriggerKey.triggerKey(name, QUARTZ_GROUP_NAME);
        _scheduler.unscheduleJob(triggerKey);
    }

    private void createTimerForRecord(InUseRecord record) throws SchedulerException {

        InUseDAO _inUseDAO = (InUseDAO) getDAO("InUseDAO");
        if (DO_DEBUG) {
            if (null == record) {
                LOG.info("Create Timer Job:record argument was null");
            }
        }
        String name = Long.toString(record.getRecordSequence());
        long fireTime = record.getModifiedDate().getTime()
                        + ((record.getExpiresMinutes() + _gracePeriodMinutes) * MILLIS_IN_MINUTE);
        
        JobDetail job = JobBuilder.newJob(InUseQuartzJob.class)
                .withIdentity(name, QUARTZ_GROUP_NAME)
                .usingJobData("gracePeriodMin", getGracePeriodMinutes())
                .build();
        job.getJobDataMap().put("InUseDAO", _inUseDAO);
        
        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder
                .newTrigger()
                .withIdentity(name, QUARTZ_GROUP_NAME)
                .startAt(new Date(fireTime)) // some Date
                .withSchedule(simpleSchedule().withRepeatCount(0))
                .build();
        
        _scheduler.scheduleJob(job,trigger);
    }


    private void updateTimerForRecord(InUseRecord record) throws SchedulerException {

        if (DO_DEBUG) {
            if (null == record) {
                LOG.info("Update Timer Job:record argument was null");
            }
        }

        String name = Long.toString(record.getRecordSequence());
        long fireTime = record.getModifiedDate().getTime()
                        + ((record.getExpiresMinutes() + _gracePeriodMinutes) * MILLIS_IN_MINUTE);
        
        
        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder
                .newTrigger()
                .withIdentity(name, QUARTZ_GROUP_NAME)
                .startAt(new Date(fireTime)) // some Date
                .forJob(name, QUARTZ_GROUP_NAME)
                .build();
        TriggerKey triggerKey = TriggerKey.triggerKey(name, QUARTZ_GROUP_NAME);
        _scheduler.rescheduleJob(triggerKey, trigger);
        
    }

    /**
     * This method returns the Logged in user details
     * @return Authenticated user details
     */
    private User getUser() {
        return (User) CxfWsSession.getSessionData(AUTHENTICATED_USER);
    }

}
