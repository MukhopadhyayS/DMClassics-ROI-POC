/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries.
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

import java.util.HashSet;
import java.util.Set;

import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.workflow.test.AbstractWorkflowTestCase;
import com.mckesson.eig.workflow.worklist.api.Worklist;
import com.mckesson.eig.workflow.worklist.api.WorklistEC;
import com.mckesson.eig.workflow.worklist.api.WorklistException;
import com.mckesson.eig.workflow.worklist.service.WorklistService;
import com.mckesson.eig.wsfw.session.WsSession;


/**
 * @author Pranav Amarasekaran
 * @date   Sep 09, 2007
 * @since  HECM 1.0
 */

public class TestUpdateWorklist
extends AbstractWorkflowTestCase {

    private static WorklistService _manager;

    private static long _worklistID1;
    private static long _worklistID2;

    private static final int APP_ID    = 1;
    private static final int ET_DOMAIN = 1;

    private static Actor _domain1;

    private static Actor _owner;

    private static Actors  _owners;

    private static String _wlName1;
    private static String _wlDesc1;
    private static String _wlName2;
    private static String _wlDesc2;
    private static String _updatedName;

    public void testSetUp() {

        init();

        long seed = System.currentTimeMillis();

        _wlName1 = "name.1." + seed;
        _wlDesc1 = "desc.1." + seed;
        _wlName2 = "name.2." + seed;
        _wlDesc2 = "desc.2." + seed;
        _updatedName = "updated.name" + seed;

        _domain1 = new Actor(APP_ID, ET_DOMAIN, (seed + 1));

        _owner  = _domain1;
        
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
        WsSession.setSessionUserId(_owner.getEntityID());
        
        Set<Actor> set = new HashSet<Actor>(1);
        set.add(_owner);
        _owners = new Actors(set);

        _manager = (WorklistService) getManager(WORKLIST_MANAGER);
        createWorklists();
    }

    /**
     * This method is used to test create worklist functionality.
     */
    public void createWorklists() {

        //create
       Worklist wl1 = new Worklist();
       wl1.setName(_wlName1);
       wl1.setDesc(_wlDesc1);
       wl1.setOwners(_owners);

       _worklistID1 = _manager.createWorklist(wl1);
       assertTrue(_worklistID1 > 0);

       Worklist wl2 = new Worklist();
       wl2.setName(_wlName2);
       wl2.setDesc(_wlDesc2);
       wl2.setOwners(_owners);

       _worklistID2 = _manager.createWorklist(wl2);
       assertTrue(_worklistID2 > 0);
       log("created");
   }

    /**
     * This method is used to test update worklist functionality with
     * different name.
     */
    public void testUpdateWorklistName() {

        //update worklist with the name
       Worklist wl = new Worklist();
       wl.setName(_updatedName);
       wl.setDesc(_wlDesc1);
       wl.setOwners(_owners);
       wl.setWorklistID(_worklistID1);

       _manager.updateWorklist(wl);
       Worklist updatedWorklist =  _manager.getWorklist(_worklistID1);
       assertEquals(_updatedName, updatedWorklist.getName());
       log("updated the worklist with the name");
   }

    /**
     * This method is used to test update worklist functionality with an
     * already existing worklist name.
     */
    public void testUpdateWorklistExistingName() {

        //update worklist with existing name.
       Worklist wl = new Worklist();
       wl.setName(_updatedName);
       wl.setDesc(_wlDesc1);
       wl.setOwners(_owners);
       wl.setWorklistID(_worklistID2);

       try {
           _manager.updateWorklist(wl);
           fail("Should have thrown EC_WORKLIST_EXISTS exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_WORKLIST_EXISTS, we.getErrorCode());
       }

       log("update worklist with existing name verified");
    }

    /**
     * This method is used to test update worklist functionality with an
     * already existing worklist name in different case.
     */
    public void testUpdateWorklistExistingNameDifferentCase() {

        //create
       Worklist wl = new Worklist();
       wl.setName(_updatedName.toUpperCase());
       wl.setDesc(_wlDesc1);
       wl.setOwners(_owners);
       wl.setWorklistID(_worklistID2);

       try {
           _manager.updateWorklist(wl);
           fail("Should have thrown EC_WORKLIST_EXISTS exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_WORKLIST_EXISTS, we.getErrorCode());
       }

       log("update worklist with existing name verified");
   }

   /**
    * This method is used to test the update worklist method without
    * specifying a name.
    */
   public void testUpdateWorklistWithoutName() {

       //update without name.
      Worklist wl = new Worklist();
      wl.setName(null);
      wl.setDesc(_wlDesc1);
      wl.setOwners(_owners);
      wl.setWorklistID(_worklistID1);

      try {
          _manager.updateWorklist(wl);
          fail("Should have thrown EC_NO_WLNAME exception");
      } catch (WorklistException we) {
          assertEquals(WorklistEC.EC_NO_WLNAME, we.getErrorCode());
      }

      log("update worklist without name verified");
   }

   /**
    * This method is used to test the create worklist method without specifying
    * a description.
    */
   public void testUpdateWorklistWithoutDesc() {

       //create without desc.
      Worklist wl = new Worklist();
      wl.setName(_wlName1);
      wl.setDesc(null);
      wl.setOwners(_owners);
      wl.setWorklistID(_worklistID1);

      try {
           _manager.updateWorklist(wl);
          fail("Should have thrown EC_NO_WLDESC exception");
      } catch (WorklistException we) {
          assertEquals(WorklistEC.EC_NO_WLDESC, we.getErrorCode());
      }

      log("update worklist without description verified");
   }

   /**
    * This method is used to test the update worklist method without
    * specifying any owning actors.
    */
   @SuppressWarnings("unchecked")
public void testUpdateWorklistWithoutOwners() {

       //create without owning actors.
      Worklist wl = new Worklist();
      wl.setName(_wlName1);
      wl.setDesc(_wlDesc1);
      wl.setWorklistID(_worklistID1);

      Set set = _owners.getActors();
      _owners.getActors().remove(_owner);
      _owners.setActors(set);
      wl.setOwners(_owners);

      try {
           _manager.updateWorklist(wl);
          fail("Should have thrown EC_NO_BELTO exception");
      } catch (WorklistException we) {
          assertEquals(WorklistEC.EC_NO_BELTO, we.getErrorCode());
      }

      set = new HashSet(1);
      set.add(_owner);
      _owners = new Actors(set);

      log("update worklist without owners verified");
   }

   /**
    * This method is used to test the update worklist method with worklist
    * name containing more characters than the maximum allowed.
    */
   public void testUpdateWorklistWithInvalidNameLength() {

       //create with invalid name length.
      Worklist wl = new Worklist();
      final String nameMaxLength =
          "Length has to exceed more than fifty characters which is too long";

      wl.setName(_wlName1 + nameMaxLength);
      wl.setDesc(_wlDesc1);
      wl.setOwners(_owners);
      wl.setWorklistID(_worklistID1);

      try {
           _manager.updateWorklist(wl);
          fail("Should have thrown EC_INVALID_WORKLIST_NAME_LEN exception");
      } catch (WorklistException we) {
          assertEquals(WorklistEC.EC_INVALID_WORKLIST_NAME_LEN,
                  we.getErrorCode());
      }

      log("update worklist with invalid worklist name length verified");
   }

   /**
    * This method is used to test the update worklist method with worklist
    * description containing more characters than the maximum allowed.
    */
   public void testUpdateWorklistWithInvalidDescLength() {

       //create with invalid desc length.
      Worklist wl = new Worklist();
      final String descMaxLength =
          "Length of the description has to exceed more than 128 characters";

      wl.setName(_wlName1);
      wl.setDesc(_wlDesc1 + descMaxLength + descMaxLength
              + descMaxLength + descMaxLength);
      wl.setOwners(_owners);
      wl.setWorklistID(_worklistID1);

      try {
          _manager.updateWorklist(wl);
          fail("Should have thrown EC_INVALID_WORKLIST_DESC_LEN exception");
      } catch (WorklistException we) {
          assertEquals(WorklistEC.EC_INVALID_WORKLIST_DESC_LEN,
                  we.getErrorCode());
      }

      log("update worklist with invalid worklist decription length verified");
   }

   /**
    * This method is used to test the update worklist method with null worklist.
    */
   public void testUpdateWorklistWithNoID() {

       //create with no worklist ID.
       Worklist wl = new Worklist();
       wl.setName(_wlName1);
       wl.setDesc(_wlDesc1);
       wl.setOwners(_owners);

       try {
           _manager.updateWorklist(wl);
           fail("Should have thrown EC_INVALID_WORKLIST_ID exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_INVALID_WORKLIST_ID,
               we.getErrorCode());
       }
   }


   /**
    * This method is used to test the update worklist method with null worklist.
    */
   public void testUpdateWorklistWithNullWorklist() {

       //create with null worklist.
      Worklist wl = null;

      try {
           _manager.updateWorklist(wl);
          fail("Should have thrown EC_MSG_NULL_WL exception");
      } catch (WorklistException we) {
          assertEquals(WorklistEC.EC_NULL_WL,
                  we.getErrorCode());
      }

      log("update worklist with null worklist verified");
   }
}
