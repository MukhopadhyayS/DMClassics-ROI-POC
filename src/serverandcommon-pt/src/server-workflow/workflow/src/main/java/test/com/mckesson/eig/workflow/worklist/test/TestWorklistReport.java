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

package com.mckesson.eig.workflow.worklist.test;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.test.AbstractWorkflowTestCase;
import com.mckesson.eig.workflow.worklist.api.Task;
import com.mckesson.eig.workflow.worklist.api.TaskACL;
import com.mckesson.eig.workflow.worklist.api.TaskACLs;
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.api.WorklistReportDO;
import com.mckesson.eig.workflow.worklist.service.TaskService;
import com.mckesson.eig.workflow.worklist.service.WorklistService;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author Sahul Hameed Y
 * @date   Feb 14, 2008
 * @since  HECM 1.0; Feb 14, 2008
 */
public class TestWorklistReport 
extends AbstractWorkflowTestCase {
    
    private static WorklistService _worklistService;
    private static TaskService _taskService;
    private static final String KEY_ACTOR = "Key_Actor";
    private static final String TASK_ACLS = "Task_Acls";

    private static final int APP_ID    = 1;
    private static final int ET_DOMAIN = 1;
    private static final int ET_USER   = 3;

    private static final int YEAR      = 2008;
    private static final int DAY       = 12;

    private static final String[] STATUS_ID = 
                                new String[] {"draft", "in progress", "new", "completed"};
    
    private static long     _worklistID;

    private static String   _wlName;
    private static String   _wlDesc;

    private static Actor    _owner;
    private static Actor    _user;
    private static TaskACL  _acl;
    
    private static Set<TaskACL> _aclSet;

    private static Actors   _owners;
    private static TaskACLs _acls;

    private static long     _taskID;
    private static long     _priorityID;
    private static String   _statusID;
    private static String   _taskName;
    private static String   _taskDescription;
    private static String   _comments;
    private static Date     _startDate;
    private static Date     _endDate;
    private static boolean  _canStartEarly;
    
    private static Actor    _domain;
    private static byte     _primarySort;
    private static boolean  _isDesc;
    private static boolean  _isAdmin;
    private static Actors   _ownerActors;
    private static long[]   _worklistIDs;
    private static String   _taskN;
    private static byte      _pID;
    private static String   _dateFilter;
    private static Date     _sDate;
    private static Date     _eDate;
    private static String   _csvFileName;
    private static boolean  _fetchAllWorklists;
    private static WorklistReportDO _worklistReportDO;
    
    public void testSetUp() {

        init();
        
        final int ten = 10; 
        
        _worklistIDs = new long[ten];
        long seed = System.currentTimeMillis();
        _owner    = new Actor(APP_ID, ET_DOMAIN, (seed + 1));
        _user     = new Actor(APP_ID, ET_USER, (seed + 1));

        _wlName = "name.1." + seed;
        _wlDesc = "desc.1." + seed;

        Set<Actor> set = new HashSet<Actor>(1);
        set.add(_owner);
        _owners = new Actors(set);

        _acl  = new TaskACL(true, true, true, true, _user);

        Set<TaskACL> aclSet = new HashSet<TaskACL>(1);
        aclSet.add(_acl);
        _acls = new TaskACLs(aclSet);
        
        Set<Actor> acts = new HashSet<Actor>();
        acts.add(_user);
        
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(_user.getEntityID());
        WsSession.setSessionData(KEY_ACTOR, _user);

        _worklistService = (WorklistService) getManager(WORKLIST_MANAGER);
        _taskService     = (TaskService)     getManager(TASK_MANAGER);

        _priorityID         = 2;
        _statusID           = "new";
        _taskName           = "Done" + seed;
        _taskDescription    = "follow certain principles" + seed;
        _comments           = "finish" + seed;
        _canStartEarly      = false;
        _startDate          = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY - 1).getTime();
        _endDate            = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY).getTime();
        
        _csvFileName        = "C:\\temp\\me\\" + System.currentTimeMillis() + ".csv";
        _dateFilter         = WorklistReportDO.START_DATE_FILTER;
        _eDate              = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY + 2).getTime();
        _fetchAllWorklists  = true;
        _isAdmin            = true;
        _isDesc             = true;
        _primarySort        = 1;
        _sDate              = new GregorianCalendar(YEAR, Calendar.DECEMBER, DAY - 2).getTime();
        _taskN              = "Done";
        _pID                = 2;
        setWorklistReportDO();
    }
    
    /**
     * 
     */
    public void setWorklistReportDO() {
        
        _worklistReportDO = new WorklistReportDO();
        _worklistReportDO.setCsvFileName(_csvFileName);
        _worklistReportDO.setDateFilter(_dateFilter);
        _worklistReportDO.setDomain(_domain);
        _worklistReportDO.setEndDate(_eDate);
        _worklistReportDO.setFetchAllWorklists(_fetchAllWorklists);
        _worklistReportDO.setIsAdmin(_isAdmin);
        _worklistReportDO.setIsDesc(_isDesc);
        _worklistReportDO.setOwnerActors(_ownerActors);
        _worklistReportDO.setPrimarySort(_primarySort);
        _worklistReportDO.setStartDate(_sDate);
        _worklistReportDO.setStatusIDs(STATUS_ID);
        _worklistReportDO.setTaskName(_taskN);
        _worklistReportDO.setPriorityID(_pID);
    }

    /**
     * This method is used to test create worklist functionality.
     */
    public void createWorklist(Actors owners, TaskACLs acls) {

       Worklist wl = new Worklist();
       wl.setName(_wlName);
       wl.setDesc(_wlDesc);
       wl.setOwners(owners);
       wl.setAssignedTo(acls);

       _worklistID = _worklistService.createWorklist(wl);
       assertTrue(_worklistID > 0);
       WsSession.setSessionData(TASK_ACLS,
       getUserTaskAcls((acls.getACLs().iterator().next()).getActor()));
   }
    
    /**
     * This method is used to test create worklist functionality.
     */
    public void createPersonlWorklist(Actors users, TaskACLs acls) {

       Worklist wl = new Worklist();
       wl.setName("My Worklist");
       wl.setDesc("Personal Worklist");
       wl.setOwners(users);
       wl.setAssignedTo(acls);

       _worklistID = _worklistService.createWorklist(wl);
       assertTrue(_worklistID > 0);
       WsSession.setSessionData(TASK_ACLS, 
       getUserTaskAcls((acls.getACLs().iterator().next()).getActor()));
   }
   
    /**
     * 
     * @return
     */
    private HashMap getUserTaskAcls(Actor actor) {
        
        Actors actors = new Actors();
        Set<Actor> acts = new HashSet<Actor>();
        acts.add(actor);
        actors.setActors(acts);
        return null;// _worklistService.getTaskAclsByActor(actors);
    }
    
    /**
     * This method is used to create a task.
     */
    public void createTaskWithNewStatusID() {

        Task task = new Task();

        task.setWorklistID(_worklistID);
        task.setTaskCreator(_user);
        task.setPriorityID(_priorityID);
        task.setStatusID(_statusID);
        task.setTaskName(_taskName);
        task.setTaskDescription(_taskDescription);
        task.setComments(_comments);
        task.setCanStartEarly(_canStartEarly);
        task.setStartDate(_startDate);
        task.setEndDate(_endDate);

        _taskID = _taskService.createTask(task);
        assertTrue(_taskID > 0);
    }
    
    public void createActors() {
        
        _user  = new Actor(APP_ID, ET_USER, System.currentTimeMillis());
        _owner = new Actor(APP_ID, ET_DOMAIN, System.currentTimeMillis());
        
        _acl      = new TaskACL(true, true, true, true, _user);
        
        Set<Actor> set = new HashSet<Actor>(1);
        set.add(_owner);
        
        _aclSet.add(_acl);
        
        _acls.setACLs(_aclSet);
        
        Set<Actor> actors = new HashSet<Actor>();
        actors.add(_owner);
        _owners.setActors(actors);
        
    }
    
    /**
     * 
     */
    public void testGenerateAllWorklistsReportWithAdminPrivilege() {
        
        _owners = new Actors();
        _aclSet = new HashSet<TaskACL>();
        _acls = new TaskACLs();
        
        Actors userActors = new Actors();
        Set<Actor> users = new HashSet<Actor>();
        users.add(_user);
        userActors.setActors(users);
        
        createActors();
        createPersonlWorklist(userActors, _acls);
        createTaskWithNewStatusID();
        _worklistIDs[0] = _worklistID;
        
        Actor actor2 = new Actor(APP_ID, ET_USER, 2);
        TaskACL taskACL2 = new TaskACL(true, true, true, true, actor2);
        
        Set<TaskACL> acls2 = new HashSet<TaskACL>();
        acls2.add(taskACL2);
        
        TaskACLs taskAcls2 = new TaskACLs();
        taskAcls2.setACLs(acls2);
        
        createWorklist(_owners, taskAcls2);
        createTaskWithNewStatusID();
        _worklistIDs[1] = _worklistID;
        
        Actor actor1 = new Actor(APP_ID, ET_USER,   System.currentTimeMillis());
        Actor owner1 = new Actor(APP_ID, ET_DOMAIN, System.currentTimeMillis());
        TaskACL taskACL1 = new TaskACL(true, true, true, true, actor1);
        
        Set<TaskACL> ownerAcls1 = new HashSet<TaskACL>();
        ownerAcls1.add(taskACL1);
        
        Set<Actor> set1 = new HashSet<Actor>(1);
        set1.add(owner1);
        Actors owners1 = new Actors(set1);
        
        TaskACLs taskAcls1 = new TaskACLs();
        taskAcls1.setACLs(ownerAcls1);
        createWorklist(owners1, taskAcls1);
        createTaskWithNewStatusID();
        _worklistIDs[2] = _worklistID;
        
        Set<Actor> ownerActorsSet = new HashSet<Actor>();
        
        ownerActorsSet.add(_owner);
        ownerActorsSet.add(owner1);
        Actors actors = new Actors();
        actors.setActors(ownerActorsSet);
        
        _worklistReportDO.setOwnerActors(actors);
        _worklistReportDO.setDomain(_user);
        _worklistReportDO.setPrimarySort(_primarySort);
        _taskService.generateWorklistReport(_worklistReportDO);
        File file = new File(_worklistReportDO.getCsvFileName());
        assertNotSame(0, file.length());
        
    }
    
    /**
     * 
     */
    public void testGenerateAllWorklistsReportWithoutAdminPrivilege() {
        
        _owners = new Actors();
        _aclSet = new HashSet<TaskACL>();
        _acls = new TaskACLs();
        
        final int three   = 3;
        final int four    = 4;
        final int five    = 5;
        
        Actors userActors = new Actors();
        Set<Actor> users = new HashSet<Actor>();
        users.add(_user);
        userActors.setActors(users);
        
        createActors();
        createPersonlWorklist(userActors, _acls);
        createTaskWithNewStatusID();
        _worklistIDs[three] = _worklistID;
        
        Actor actor2 = new Actor(APP_ID, ET_USER,   System.currentTimeMillis());
        TaskACL taskACL2 = new TaskACL(true, true, true, true, actor2);
        
        Set<TaskACL> acls2 = new HashSet<TaskACL>();
        acls2.add(taskACL2);
        
        TaskACLs taskAcls2 = new TaskACLs();
        taskAcls2.setACLs(acls2);
        
        createWorklist(_owners, taskAcls2);
        createTaskWithNewStatusID();
        _worklistIDs[four] = _worklistID;
        
        Actor actor1 = new Actor(APP_ID, ET_USER,   System.currentTimeMillis());
        Actor owner1 = new Actor(APP_ID, ET_DOMAIN, System.currentTimeMillis());
        TaskACL taskACL1 = new TaskACL(true, true, true, true, actor1);
        
        Set<TaskACL> ownerAcls1 = new HashSet<TaskACL>();
        ownerAcls1.add(taskACL1);
        
        Set<Actor> set1 = new HashSet<Actor>(1);
        set1.add(owner1);
        Actors owners1 = new Actors(set1);
        
        TaskACLs taskAcls1 = new TaskACLs();
        taskAcls1.setACLs(ownerAcls1);
        createWorklist(owners1, taskAcls1);
        createTaskWithNewStatusID();
        _worklistIDs[five] = _worklistID;
        
        Set<Actor> ownerActorsSet = new HashSet<Actor>();
        
        ownerActorsSet.add(_owner);
        ownerActorsSet.add(owner1);
        Actors actors = new Actors();
        actors.setActors(ownerActorsSet);
        
        String fileName = "c:\\temp\\me\\" + System.currentTimeMillis() + ".csv";
        final byte primarySort = 3;
        
        _worklistReportDO.setIsAdmin(false);
        _worklistReportDO.setOwnerActors(actors);
        _worklistReportDO.setDomain(_user);
        _worklistReportDO.setCsvFileName(fileName);
        _worklistReportDO.setPrimarySort(primarySort);
        _taskService.generateWorklistReport(_worklistReportDO);
    }
    
    public void testGenerateWorklistsReport() {
        
        String fileName = "c:\\temp\\me\\" + System.currentTimeMillis() + ".csv";
        
        Set<Actor> ownerActorsSet = new HashSet<Actor>();
        ownerActorsSet.add(_owner);
        Actors actors = new Actors();
        actors.setActors(ownerActorsSet);
        final byte primarySort = 4;
         
        _worklistReportDO.setFetchAllWorklists(false);
        _worklistReportDO.setCsvFileName(fileName);
        _worklistReportDO.setWorklistIDs(_worklistIDs);
        _worklistReportDO.setOwnerActors(actors);
        _worklistReportDO.setIsDesc(false);
        _worklistReportDO.setDomain(_user);
        _worklistReportDO.setCsvFileName(fileName);
        _worklistReportDO.setDateFilter("endDate");
        _worklistReportDO.setPrimarySort(primarySort);
        _taskService.generateWorklistReport(_worklistReportDO);
        
        File file = new File(fileName);
        assertNotSame(0, file.length());
    }
    
    public void testGenerateWorklistsReportWithOwnerActors() {
        
        String fileName = "c:\\temp\\me\\" + System.currentTimeMillis() + ".csv";
        
        Set<Actor> ownerActorsSet = new HashSet<Actor>();
        ownerActorsSet.add(_owner);
        Actors actors = new Actors();
        actors.setActors(ownerActorsSet);
        final byte primarySort = 4;
        
        _worklistReportDO.setFetchAllWorklists(false);
        _worklistReportDO.setCsvFileName(fileName);
        _worklistReportDO.setWorklistIDs(_worklistIDs);
        _worklistReportDO.setOwnerActors(actors);
        _worklistReportDO.setIsDesc(true);
        _worklistReportDO.setCsvFileName(fileName);
        _worklistReportDO.setDateFilter("endDate");
        _worklistReportDO.setPrimarySort(primarySort);
        _taskService.generateWorklistReport(_worklistReportDO);
        
        File file = new File(fileName);
        assertNotSame(0, file.length());
    }
    
    public void testGenerateWorklistsReportWithPrioritySortOrder() {
        
        String fileName = "c:\\temp\\me\\" + System.currentTimeMillis() + ".csv";
        
        Set<Actor> ownerActorsSet = new HashSet<Actor>();
        ownerActorsSet.add(_owner);
        Actors actors = new Actors();
        actors.setActors(ownerActorsSet);
        
        _worklistReportDO.setFetchAllWorklists(false);
        _worklistReportDO.setCsvFileName(fileName);
        _worklistReportDO.setWorklistIDs(null);
        _worklistReportDO.setOwnerActors(actors);
        _worklistReportDO.setIsDesc(true);
        _worklistReportDO.setCsvFileName(fileName);
        _worklistReportDO.setDateFilter("endDate");
        _worklistReportDO.setPrimarySort(_pID);
        _taskService.generateWorklistReport(_worklistReportDO);
        
        File file = new File(fileName);
        assertNotSame(0, file.length());
    }
    
    public void testGenerateWorklistsReportWithWorklistNameSortOrder() {
        
        String fileName = "c:\\temp\\me\\" + System.currentTimeMillis() + ".csv";
        
        Set<Actor> ownerActorsSet = new HashSet<Actor>();
        ownerActorsSet.add(_owner);
        Actors actors = new Actors();
        actors.setActors(ownerActorsSet);
        final byte primarySort = 6;
        
        _worklistReportDO.setFetchAllWorklists(false);
        _worklistReportDO.setCsvFileName(fileName);
        _worklistReportDO.setWorklistIDs(null);
        _worklistReportDO.setOwnerActors(actors);
        _worklistReportDO.setIsDesc(true);
        _worklistReportDO.setCsvFileName(fileName);
        _worklistReportDO.setDateFilter("endDate");
        _worklistReportDO.setPrimarySort(primarySort);
        _taskService.generateWorklistReport(_worklistReportDO);
        
        File file = new File(fileName);
        assertNotSame(0, file.length());
    }
    
    public void testGenerateWorklistsReportWithDefaultSortOrder() {
        
        String fileName = "c:\\temp\\me\\" + System.currentTimeMillis() + ".csv";
        
        Set<Actor> ownerActorsSet = new HashSet<Actor>();
        ownerActorsSet.add(_owner);
        Actors actors = new Actors();
        actors.setActors(ownerActorsSet);
        final byte primarySort = 12;
        
        Set<Actor> ownerActors = new HashSet<Actor>();
        ownerActors.add(_owner);
        Actors acts = new Actors();
        acts.setActors(ownerActors);
        
        _worklistReportDO.setFetchAllWorklists(false);
        _worklistReportDO.setCsvFileName(fileName);
        _worklistReportDO.setWorklistIDs(null);
        _worklistReportDO.setOwnerActors(actors);
        _worklistReportDO.setIsDesc(true);
        _worklistReportDO.setCsvFileName(fileName);
        _worklistReportDO.setDateFilter("endDate");
        _worklistReportDO.setPrimarySort(primarySort);
        _taskService.generateWorklistReport(_worklistReportDO);
        
        File file = new File(fileName);
        assertNotSame(0, file.length());
    }
    
    public void testGenerateWorklistsReportWithNullWorklistIDs() {
        
        String fileName = "c:\\temp\\me\\" + System.currentTimeMillis() + ".csv";
        
        Set<Actor> ownerActorsSet = new HashSet<Actor>();
        ownerActorsSet.add(_owner);
        Actors actors = new Actors();
        actors.setActors(ownerActorsSet);
        final byte primarySort = 5;
        
        Set<Actor> ownerActors = new HashSet<Actor>();
        Actors acts = new Actors();
        acts.setActors(ownerActors);
        
        _worklistReportDO.setFetchAllWorklists(false);
        _worklistReportDO.setCsvFileName(fileName);
        _worklistReportDO.setWorklistIDs(null);
        _worklistReportDO.setOwnerActors(actors);
        _worklistReportDO.setIsDesc(true);
        _worklistReportDO.setCsvFileName(fileName);
        _worklistReportDO.setDateFilter("endDate");
        _worklistReportDO.setPrimarySort(primarySort);
        _taskService.generateWorklistReport(_worklistReportDO);
        
        File file = new File(fileName);
        assertNotSame(0, file.length());
    }
}
