package com.mckesson.eig.workflow.worklist.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.api.Attribute;
import com.mckesson.eig.workflow.api.SortOrder;
import com.mckesson.eig.workflow.test.AbstractWorkflowTestCase;
import com.mckesson.eig.workflow.worklist.api.AssignedWLCriteria;
import com.mckesson.eig.workflow.worklist.api.CreateWLCriteria;
import com.mckesson.eig.workflow.worklist.api.ListWorklist;
import com.mckesson.eig.workflow.worklist.api.TaskACL;
import com.mckesson.eig.workflow.worklist.api.TaskACLs;
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.service.WorklistService;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author sahuly
 * @date   Oct 21, 2009
 * @since  HECM 1.0.0
 */
public class TestFetchingWorklistWithChunking extends AbstractWorkflowTestCase {
    
    private static WorklistService _manager;
    
    private static final int APP_ID    = 1;
    private static final int ET_DOMAIN = 1;
    private static final int ET_USER   = 3;
    
    private static Actors  _owners;
    
    private static Actor _owner;
    
    private static Actors _assignedActors;
    
    private static Actors _users;
    
    private static Actors _createdBy;
    
    private static Actor _user1;
    
    private static Actor _user2;
    
    private static TaskACL _acl3;
    
    private static Actor _domain1;
    
    private static TaskACLs _acls2;
    
    private static AssignedWLCriteria _criteria;
    
    private static final String TASK_ACLS = "Task_Acls";
    
    private static final String KEY_ACTOR = "Key_Actor";
    
    private static long _seed = System.currentTimeMillis();
    
    private static final long WORKLIST_COUNT = 1200;
    public void testSetUp() {
        
        init();

        _domain1 = new Actor(APP_ID, ET_DOMAIN, _seed);
        _owner  = _domain1;

        Set<Actor> set = new HashSet<Actor>(1);
        set.add(_owner);
        _owners = new Actors(set);

        _user1   = new Actor(APP_ID, ET_USER, 1);
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(_user1.getEntityID());
        WsSession.setSessionData(KEY_ACTOR, _user1);
        Set<Actor> created = new HashSet<Actor>(1);
        created.add(_user1);
        _createdBy = new Actors();
        _createdBy.setActors(created);

        _manager      = (WorklistService) getManager(WORKLIST_MANAGER);

        createWorklists();
    }
    
    public void createWorklists() {
        Set<Actor> userSet = new HashSet<Actor>();
        Set<Actor> acts = new HashSet<Actor>();
        
        for (int i = 0; i < WORKLIST_COUNT; i++) {
            
            _user2   = new Actor(APP_ID, ET_USER, _seed + i);
            acts.add(_user2);
           userSet.add(_user2);
           
           _acl3  = new TaskACL(false, true,  false, true,  _user1);

           Set<TaskACL> aclSet2 = new HashSet<TaskACL>();
           aclSet2.add(_acl3);
           _acls2 = new TaskACLs(aclSet2);
           
        
           Worklist wl3 = new Worklist();
           wl3.setName("Worklist-Name" + _seed + i);
           wl3.setDesc("Desc" + _seed + i);
           wl3.setOwners(_owners);
           wl3.setAssignedTo(_acls2);
           
           _manager.createWorklist(wl3);
		}	
        _users = new Actors();
        _users.setActors(acts);
        
        _assignedActors = new Actors(userSet);
        _criteria = new AssignedWLCriteria(_owners, _assignedActors, true);
       
	    resolveTaskAclsByActors();
   }
    
   private void resolveTaskAclsByActors() {
   	   //WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
       HashSet<Actor> set = new HashSet<Actor>();
       set.add(_user1);
       _manager.resolveTaskAclsByActors(new Actors(set));
   }

    
    /**
     * This method is used to test get assigned worklists count.
     */
    public void testGetAssignedWorklistsWithShowEmptyWorklistsCount() {

       _criteria = new AssignedWLCriteria(_owners, _assignedActors, true);
       long count = _manager.getAssignedWorklistsCount(_criteria);
       assertEquals(WORKLIST_COUNT, count);
    }
    
    
    /**
     * This method is used to test get assigned worklists.
     */
    public void testGetAssignedWorklistsWithShowEmptyWorklists() {

       SortOrder order = new SortOrder(new Attribute("name"));
       final int startValue = 0;
       final int count = (int) WORKLIST_COUNT;

       _criteria = new AssignedWLCriteria(_owners, _assignedActors, true);
       ListWorklist wl = _manager.getAssignedWorklists(_criteria,
                                                       startValue,
                                                       count,
                                                       order);
       List<Worklist> wlList = wl.getWorklists();
       assertEquals(WORKLIST_COUNT, wlList.size());
    }
    
    /**
     * This method is used to test get creatable worklists count.
     */
    public void testGetCreatableWorklistsWithShowEmptyWorklistsCount() {

        CreateWLCriteria criteria = new CreateWLCriteria(_owners, _createdBy, true);
       long count = _manager.getCreatableWorklistsCount(criteria);
       assertEquals(WORKLIST_COUNT, count);
    }
    
    
    /**
     * This method is used to test get creatable worklists.
     */
    public void testGetCreatableWorklistsWithShowEmptyWorklists() {

       SortOrder order = new SortOrder(new Attribute("name"));
       final int startValue = 0;
       final int count = (int) WORKLIST_COUNT;

       CreateWLCriteria criteria = new CreateWLCriteria(_owners, _createdBy, true);
       ListWorklist wl = _manager.getCreatableWorklists(criteria, startValue, count, order);
       List<Worklist> wlList = wl.getWorklists();
       assertEquals(WORKLIST_COUNT, wlList.size());
    }

}
