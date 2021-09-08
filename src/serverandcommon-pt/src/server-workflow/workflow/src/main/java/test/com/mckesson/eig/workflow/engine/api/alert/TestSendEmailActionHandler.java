/*
 * Copyright 2007-2009 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.workflow.engine.api.alert;

import java.util.ArrayList;
import java.util.List;

import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;

import com.mckesson.eig.utility.util.ReflectionUtilities;
import com.mckesson.eig.workflow.api.WorkflowEngineException;
import com.mckesson.eig.workflow.test.AbstractWorkflowTestCase;

/**
 *
 */
public class TestSendEmailActionHandler extends AbstractWorkflowTestCase {

    private static final int PROCESS_ID = 103;
    
    private SendEmailActionHandler  _handler  = null;
    
    protected void setUp() throws Exception {
    	
    	super.setUp();
    	init();
        _handler = new SendEmailActionHandler();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        _handler = null;
    }
       
    public void atestExecute() {
    	
    	ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
                "<process-definition name='" + PROCESS_ID + "'>"
                + "<start-state name='start-state1'>"
                + "<transition to='state1'/>"
                + "</start-state>"
                + "<state name='state1'>"
                + "<event type='node-enter'>"
                + "<action name='testSendEmail' "
                + "class="
                + "'com.mckesson.eig.workflow.engine.api.alert.SendEmailActionHandler'"
                + " config-type='bean'>"
                + "<subject>Workflow test email subject</subject>"
                + "<body>Hi"
                + ""
                + " Your email box has been choosen for test by workflow process id 103."
                + "Have a great day :)"
                + ""
                + "Best wishes"
                + "process id 103</body>"
                + "<to>"
                + "ronnie.andrews@mckesson.com;srinivas.paduri@mckesson.com;"
                + "alan.shealy@mckesson.com"
                + "</to>"
                + "</action>"
                + "</event>"
                + "<transition to='end' />"
                + "</state>"
                + "<end-state name='end' />"
                + "</process-definition>"
              );

         //start a new execution for the process definition.
         ProcessInstance processInstance = new ProcessInstance(processDefinition);
         assertNotNull(processInstance);
         
         ContextInstance contextInstance = processInstance.getContextInstance();
         assertNotNull(contextInstance);

         // set  process variables in the context of the process instance.
         contextInstance.setVariable(SendEmailActionHandler.JBPM_NOTIFICATION_SENDER_EMAIL,
                 "from.user@mckesson.com");
         
         List<String> emailIds = new ArrayList<String>();
         emailIds.add("Recipient1@mckesson.com");
         emailIds.add("Recipient2@mckesson.com");

         contextInstance.setVariable(SendEmailActionHandler.JBPM_NOTIFICATION_RECIPIENTS,
                 emailIds);
         
         contextInstance.setVariable(SendEmailActionHandler.JBPM_NOTIFICATION_SUMMARY,
         "Summary: we want to replace ${Literal1} and ${Literal2}");
         
         contextInstance.setVariable(SendEmailActionHandler.JBPM_NOTIFICATION_DETAIL,
         "Detail: we want to replace ${Literal1} and ${Literal2}");
          
         contextInstance.setVariable("Literal1", "Literal_1_Value");
         contextInstance.setVariable("Literal2", "Literal_2_Value");

         
         contextInstance.setVariable(SendEmailActionHandler.JBPM_APPLICATION_NAME, 
                 "UnitTest Name");
                 
         contextInstance.setVariable(SendEmailActionHandler.JBPM_APPLICATION_DESCRIPTION, 
                 "UnitTest Description");     
     
         // signal to enter the state 'sendEmail'
         processInstance.signal();
         
	     // test that the main path of execution is positioned in the state 'sendEmail'
	     assertSame(processDefinition.getNode("sendEmail"),
	                 processInstance.getRootToken().getNode());
     
	     // trigger the execution of the root token. 
	     processInstance.signal();
     }

    public void testPopulate() {
        ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
           "<process-definition>"
           + "<start-state>"
           + "<transition to='sendEmail' />"
           + "</start-state>"
           + "<state name='sendEmail'>"
           + "<transition to='end'>"
           + "<action class='com.mckesson.eig.workflow.engine.api.alert.SendEmailActionHandler'/>"
           + "</transition>"
           + "</state>"
           + "<end-state name='end' />"
           + "</process-definition>"
         );
        //start a new execution for the process definition.
        ProcessInstance processInstance = new ProcessInstance(processDefinition);
        assertNotNull(processInstance);
        
        ContextInstance contextInstance = processInstance.getContextInstance();
        assertNotNull(contextInstance);

        // set  process variables in the context of the process instance.
        contextInstance.setVariable(SendEmailActionHandler.JBPM_NOTIFICATION_SENDER_EMAIL,
                "from.user@mckesson.com");
        
        List<String> emailIds = new ArrayList<String>();
        emailIds.add("Recipient1@mckesson.com");
        emailIds.add("Recipient2@mckesson.com");

        contextInstance.setVariable(SendEmailActionHandler.JBPM_NOTIFICATION_RECIPIENTS,
                emailIds);
        
        contextInstance.setVariable(SendEmailActionHandler.JBPM_NOTIFICATION_SUMMARY,
        "Summary: we want to replace ${Literal1} and ${Literal2}");
        
        contextInstance.setVariable(SendEmailActionHandler.JBPM_NOTIFICATION_DETAIL,
        "Detail: we want to replace ${Literal1} and ${Literal2}");
         
        contextInstance.setVariable("Literal1", "Literal_1_Value");
        contextInstance.setVariable("Literal2", "Literal_2_Value"); 
    
        ExecutionContext executionContext = new ExecutionContext(processInstance.getRootToken());
        Class[] argType = {ExecutionContext.class};
        Object[] argValue = {executionContext};
        try {        
            String testValue = (String) ReflectionUtilities
                    .callPrivateMethod(SendEmailActionHandler.class, _handler, "populate",  
                                        argType, argValue);
         } catch (WorkflowEngineException e) {
            fail("testPopulate() should not throw WorkflowException(). " + e.toString());
        }
    
        assertEquals("from.user@mckesson.com", _handler.getFrom());
        assertSame(emailIds, _handler.getTo());
        assertEquals("Summary: we want to replace Literal_1_Value and Literal_2_Value", 
                _handler.getSubject());
        assertEquals("Detail: we want to replace Literal_1_Value and Literal_2_Value", 
                _handler.getBody());

    }
    
    private Object callPrivateMethod(String method) {
        return ReflectionUtilities.callPrivateMethod(SendEmailActionHandler.class,
                _handler, method);
    }
    

    public void testGoodEmailSender() {
        
        try {      
            String emailValues = "the.user@mckesson.com";
            _handler.setTo(emailValues);
            _handler.setFrom("from.user@mckesson.com");
            _handler.setBody("This is this body");
            _handler.setSubject("This is the subject");            
 
            _handler.validate();
            
            callPrivateMethod("doNotify");
        
        } catch (WorkflowEngineException e) {
            fail("testGoodEmailSender() threw a WorkflowException [" + e.toString() + "]");
        }
    }
    
   public void testBadEmailSender() {
        
        try {            
            String emailValues = "the.user@mckesson.com";
            _handler.setTo(emailValues);
            _handler.setFrom("from.user@mckesson.com");
            _handler.setBody("This is this body");
            _handler.setSubject("This is the subject");            
            _handler.setReplyTo("replyto.user@mckesson.com");
            _handler.validate();

            callPrivateMethod("doNotify");
            fail("testBadEmailSender() should throw WorkflowException(invalid host)");
        
        } catch (WorkflowEngineException e) {
            System.out.println("Expected testBadEmailSender(invalid host) exception occurred");
        }
    } 

   public void testGetEventComments() {

       try {

           _handler.setFrom("from.user@mckesson.com");
           _handler.setBody("This is this body");
           _handler.setSubject("This is the subject");            
           assertNotNull(callPrivateMethod("getEventComments"));

           _handler.setTo("the.user@mckesson.com");
           _handler.setReplyTo("replyto.user@mckesson.com");
           assertNotNull(callPrivateMethod("getEventComments"));

       } catch (WorkflowEngineException e) {
           fail("tetGetEventComments() should not throw exception" + e.getMessage());
       }
   }
}
