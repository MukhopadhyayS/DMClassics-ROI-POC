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

public class TestCreateWorklist
extends com.mckesson.eig.workflow.test.AbstractWorkflowTestCase {

    private static WorklistService _manager;

    private static long _worklistID;

    private static final int APP_ID    = 1;
    private static final int ET_DOMAIN = 1;

    private static Actor _domain1;
    private static Actor _domain2;

    private static Actor _owner;
    private static Actor _owner1;

    private static Actors  _owners;
    private static Actors  _owners1;

    private static String _wlName1;
    private static String _wlDesc1;

    public void testSetUp() {

        init();

        long seed = System.currentTimeMillis();

        _wlName1 = "name.1." + seed;
        _wlDesc1 = "desc.1." + seed;

        _domain1 = new Actor(APP_ID, ET_DOMAIN, (seed + 1));
        _domain2 = new Actor(APP_ID, ET_DOMAIN, (seed + 2));

        _owner  = _domain1;
        _owner1 = _domain2;

        Set<Actor> set = new HashSet<Actor>(1);
        set.add(_owner);
        _owners = new Actors(set);

        Set<Actor> set1 = new HashSet<Actor>(1);
        set1.add(_owner1);
        _owners1 = new Actors(set1);
        
        WsSession.initializeSession();
		WsSession.setSessionData("SESSION_ID", String.valueOf(System.currentTimeMillis()));
		WsSession.setSessionData("Actors", _owners);
		WsSession.setSessionData("Key_Actor", new Actor(1, Actor.USER_ENTITY_TYPE, 1));
        WsSession.setSessionUserId(_owner.getEntityID());
        _manager = (WorklistService) getManager(WORKLIST_MANAGER);
    }

    /**
     * This method is used to test create worklist functionality.
     */
    public void testCreateWorklist() {

        //create
       Worklist wl = new Worklist();
       wl.setName(_wlName1);
       wl.setDesc(_wlDesc1);
       wl.setOwners(_owners);

       _worklistID = _manager.createWorklist(wl);
       assertTrue(_worklistID > 0);
       log("created");
   }
    
    /**
     * This method is used to test create worklist with all Valid characters.
     */
    public void testValidCharactersForWorklist() {
        final String validCharacters = 
            "! \"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ" 
            + "[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

        _manager = (WorklistService) getManager(WORKLIST_MANAGER);

        int validDataLength = validCharacters.length();
        int currentIndex = 0;
        final int maxWorklistNameLength = 75;
        Worklist wl = null;
        while (currentIndex < validDataLength) {
            wl = new Worklist();

            if ((validDataLength - currentIndex) > maxWorklistNameLength) {
                wl.setName("test" + validCharacters
                        .substring(currentIndex, currentIndex + maxWorklistNameLength));
            } else {
                wl.setName("test" + validCharacters.substring(currentIndex));
            }

            wl.setDesc("testValidCharactersForWorklist");
            wl.setOwners(_owners);

            _worklistID = _manager.createWorklist(wl);
            assertTrue(_worklistID > 0);
            long [] deleteIds = new long [1];
            deleteIds[0] = _worklistID;
            _manager.deleteWorklists(deleteIds);
            currentIndex += maxWorklistNameLength;
        }
    }
    
   /**
    * This method is used to test create worklist method by specifying the
    * worklist with the same name and associated to the same owner.
    */
   public void testCreateWorklistWithSameName() {

        //create with same name.
       Worklist wl = new Worklist();
       wl.setName(_wlName1);
       wl.setDesc(_wlDesc1);
       wl.setOwners(_owners);

       try {
           _worklistID = _manager.createWorklist(wl);
           fail("Should have thrown EC_WORKLIST_EXISTS exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_WORKLIST_EXISTS, we.getErrorCode());
       }

       log("create worklist with same name verified");
   }

   /**
    * This method is used to test create worklist method by specifying the
    * worklist with the same name and associated to different owner.
    */
   public void testCreateWorklistWithSameNameWithDiffOwner() {

        //create with same name and different owner.
       Worklist wl = new Worklist();
       wl.setName(_wlName1);
       wl.setDesc(_wlDesc1);
       wl.setOwners(_owners1);

       _worklistID = _manager.createWorklist(wl);
       assertTrue(_worklistID > 0);
       log("created");
   }
   /**
    * This method is used to test create worklist method by specifying the
    * worklist with the same name (with upper case) and associated to the
    * same owner.
    */
   public void testCreateWorklistWithSameNameAndDifferentCase() {

        //create with same name in Different Case.
       Worklist wl = new Worklist();
       wl.setName(_wlName1.toUpperCase());
       wl.setDesc(_wlDesc1);
       wl.setOwners(_owners);

       try {
           _worklistID = _manager.createWorklist(wl);
           fail("Should have thrown EC_WORKLIST_EXISTS exception");
       } catch (WorklistException we) {
           assertEquals(WorklistEC.EC_WORKLIST_EXISTS, we.getErrorCode());
       }

       log("create worklist with same name verified");
   }
   /**
    * This method is used to test the create worklist method without
    * specifying a name.
    */
   public void testCreateWorklistWithoutName() {

       //create without name.
      Worklist wl = new Worklist();
      wl.setName(null);
      wl.setDesc(_wlDesc1);
      wl.setOwners(_owners);

      try {
          _worklistID = _manager.createWorklist(wl);
          fail("Should have thrown EC_NO_WLNAME exception");
      } catch (WorklistException we) {
          assertEquals(WorklistEC.EC_NO_WLNAME, we.getErrorCode());
      }

      log("create worklist without name verified");
   }

   /**
    * This method is used to test the create worklist method without specifying
    * a description.
    */
   public void testCreateWorklistWithoutDesc() {

       //create without desc.
      Worklist wl = new Worklist();
      wl.setName(_wlName1);
      wl.setDesc(null);
      wl.setOwners(_owners);

      try {
          _worklistID = _manager.createWorklist(wl);
          fail("Should have thrown EC_NO_WLDESC exception");
      } catch (WorklistException we) {
          assertEquals(WorklistEC.EC_NO_WLDESC, we.getErrorCode());
      }

      log("create worklist without description verified");
   }

   /**
    * This method is used to test the create worklist method without
    * specifying any owning actors.
    */
   @SuppressWarnings("unchecked")
public void testCreateWorklistWithoutOwners() {

       //create without owning actors.
      Worklist wl = new Worklist();
      wl.setName(_wlName1);
      wl.setDesc(_wlDesc1);

      Set set = _owners.getActors();
      _owners.getActors().remove(_owner);
      _owners.setActors(set);
      wl.setOwners(_owners);

      try {
          _worklistID = _manager.createWorklist(wl);
          fail("Should have thrown EC_NO_BELTO exception");
      } catch (WorklistException we) {
          assertEquals(WorklistEC.EC_NO_BELTO, we.getErrorCode());
      }

      set = new HashSet(1);
      set.add(_owner);
      _owners = new Actors(set);

      log("create worklist without owners verified");
   }

   /**
    * This method is used to test the create worklist method with worklist
    * name containing more characters than the maximum allowed.
    */
   public void testCreateWorklistWithInvalidNameLength() {

       //create with invalid name length.
      Worklist wl = new Worklist();
      final String nameMaxLength =
          "Length has to exceed more than fifty characters which is too long";

      wl.setName(_wlName1 + nameMaxLength);
      wl.setDesc(_wlDesc1);
      wl.setOwners(_owners);

      try {
          _worklistID = _manager.createWorklist(wl);
          fail("Should have thrown EC_INVALID_WORKLIST_NAME_LEN exception");
      } catch (WorklistException we) {
          assertEquals(WorklistEC.EC_INVALID_WORKLIST_NAME_LEN,
                  we.getErrorCode());
      }

      log("create worklist with invalid worklist name length verified");
   }

   /**
    * This method is used to test the create worklist method with worklist
    * description containing more characters than the maximum allowed.
    */
   public void testCreateWorklistWithInvalidDescLength() {

       //create with invalid desc length.
      Worklist wl = new Worklist();
      final String descMaxLength =
          "Length of the description has to exceed more than 256 characters";

      wl.setName(_wlName1);
      wl.setDesc(_wlDesc1 + descMaxLength + descMaxLength
                 + descMaxLength + descMaxLength);
      wl.setOwners(_owners);

      try {
          _worklistID = _manager.createWorklist(wl);
          fail("Should have thrown EC_INVALID_WORKLIST_DESC_LEN exception");
      } catch (WorklistException we) {
          assertEquals(WorklistEC.EC_INVALID_WORKLIST_DESC_LEN,
                  we.getErrorCode());
      }

      log("create worklist with invalid worklist decription length verified");
   }

   /**
    * This method is used to test the create worklist method with null worklist.
    */
   public void testCreateWorklistWithNullWorklist() {

       //create with null worklist.
      Worklist wl = null;

      try {
          _worklistID = _manager.createWorklist(wl);
          fail("Should have thrown EC_MSG_NULL_WL exception");
      } catch (WorklistException we) {
          assertEquals(WorklistEC.EC_NULL_WL,
                  we.getErrorCode());
      }

      log("create worklist with null worklist verified");
   }
}
