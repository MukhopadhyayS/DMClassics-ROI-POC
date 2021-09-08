/*
 * Copyright 2009-2010 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.workflow.process.api;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import junit.framework.TestCase;

import org.xml.sax.SAXException;

import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.workflow.api.Actor;
import com.mckesson.eig.workflow.api.Domain;
import com.mckesson.eig.workflow.api.DomainList;

/**
 * Test class for ProcessVersion. It tests the methods of ProcessVersion class
 *
 */
public class TestProcessVersion extends TestCase {

    private String _simplePd;
    private String _expectedString;

    /**
     * setUp method for the test case.
     *
     * @throws Exception
     *             of type Exception.
     */
    protected void setUp() throws Exception {
        super.setUp();
        _simplePd =
                  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        		+ "<process-definition name='JunitTest'>"
                + "<start-state name='start'>"
                + "<transition to='s' />" + "  </start-state>"
                + "<state name='s'>" + "    <transition to='end' />"
                + "</state>" + "  <end-state name='end' />"
                + "</process-definition>";

        try {
            _expectedString = StringUtilities
                    .domToString(StringUtilities
                    .stringToDom(this._simplePd));
        } catch (TransformerException e) {
            fail();
        } catch (SAXException e) {
            fail();
        } catch (ParserConfigurationException e) {
            fail();
        } catch (IOException e) {
            fail();
        }


     }

    /**
     * Method tearDown() removes the data from Database.
     *
     * @throws Exception
     *             of type Exception.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * tests the ProcessVersion constructors
     */
    public void testConstructors() {

        ProcessVersion process = new ProcessVersion();
        assertNotNull(process);
        assertEquals(0L, process.getProcess().getProcessId());
        assertNull(process.getProcessName());
        assertNull(process.getProcessDescription());
    }

    /**
     * tests the getter and setter methods
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public void testGettersAndSetters() throws SAXException,
            ParserConfigurationException, IOException {

        final int entityType = 3;
        Actor actor = new Actor(1, entityType, 1);
        ProcessActorACL acl = new ProcessActorACL();
        acl.setPermissionName("assigned");
        acl.setActor(actor);
        acl.setProcessId(1);
        Set<ProcessActorACL> processACLS = new HashSet<ProcessActorACL>();
        processACLS.add(acl);

        Date createdDT = new Date();

        ProcessVersion process = new ProcessVersion();
        process.setProcessVersionId(new Long(1));
        process.setCreatedBy("12-11-12");
        process.setCreatedTS(createdDT);

        Domain domain = new Domain();
        domain.setDomainDescription("desc");
        domain.setDomainId(1);
        domain.setDomainName("Org");
        List<Domain> domains = new ArrayList<Domain>();
        domains.add(domain);
        DomainList domainList = new DomainList(domains);
        domainList.setSize(1);

        process.setDomainList(domainList);
        process.setExceptionEmailAddress("test@abc.com");
        process.setExpirationDate(createdDT);
        process.setIsValid(true);
        process.setLockedBy("shan");
        process.setMaxInstanceDuration(1);
        process.setNotifyExceptions('n');

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(actor);

        ProcessAttribute pa = new ProcessAttribute();
        pa.setAttributeName("PROCES_TYPE");
        pa.setAttributeType("BOTH");
        pa.setAttributeValue("String");
        List<ProcessAttribute> paList = new ArrayList<ProcessAttribute>();
        ProcessAttributeList processAttributeList = new ProcessAttributeList(paList);
        processAttributeList.setSize(1);

        Set<ProcessAttribute> processAttributeSet = new HashSet<ProcessAttribute>();
        processAttributeSet.add(pa);

        process.setProcessAttributeList(processAttributeList);
        process.setProcessAttributes(processAttributeSet);
        try {
            process.setProcessDefinition(_simplePd);
            process.setProcessGraph(_simplePd);
        } catch (SAXException e) {
            fail();
        } catch (ParserConfigurationException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
        process.setProcessDescription("processDesc");

        process.getProcess().setProcessId(1);
        process.setProcessName("TaskProcess");
        process.setRetentionPeriod(1);
        process.setStatus("current");
        process.setUpdatedBy("12-11-12");
        process.setUpdatedTS(createdDT);
        process.setVersionId(new Long(1));

        assertEquals(process.getProcess().getProcessId(), 1);
        assertEquals(process.getProcessVersionId(), new Long(1));
        assertEquals(process.getCreatedBy(), "12-11-12");
        assertEquals(process.getCreatedTS(), createdDT);
        assertTrue(process.getDomainList().getSize() > 0);
        assertEquals(process.getExceptionEmailAddress(), "test@abc.com");
        assertEquals(process.getExpirationDate(), createdDT);
        assertTrue(process.getIsValid());
        assertEquals(process.getLockedBy(), "shan");
        assertEquals(process.getMaxInstanceDuration(), new Integer(1));
        assertEquals(process.getNotifyExceptions(), new Character('n'));
        assertTrue(process.getProcessAttributeList().getSize() > 0);
        assertTrue(process.getProcessAttributes().size() > 0);

        try {
            String expectedString = StringUtilities.domToString(StringUtilities
                    .stringToDom(this._simplePd));
            assertEquals(process.getProcessDefinition(), expectedString);
            assertEquals(process.getProcessGraph(), expectedString);
        } catch (TransformerException e) {
            fail();
        }
        assertEquals(process.getProcessDescription(), "processDesc");

        assertEquals(process.getProcessName(), "TaskProcess");
        assertEquals(process.getRetentionPeriod(), new Integer(1));
        assertEquals(process.getStatus(), "current");
        assertEquals(process.getUpdatedBy(), "12-11-12");
        assertEquals(process.getUpdatedTS(), createdDT);
        assertEquals(process.getVersionId(), new Long(1));
    }

    /**
     * tests the getter and setter methods
     */
    public void testToString() {
        ProcessVersion testProcess = new ProcessVersion();
        String expected = "ProcessVersion[processId=0, processName=null, processDescription=null, "
        + "processDefinition=null, processGraph=null, status=null, exceptionEmailAddress=null, "
        + "lockedBy=null, retentionPeriod=null, notifyExceptions=null, isValid=FALSE, "
        + "effectiveDate=NULL, "
        + "expirationDate=NULL, maxInstanceDuration=null, domainList=DomainList[], "
        + "owners=set of Actors, actors=set of Actors, "
        + "processAttributeList=ProcessAttributeList[], updatedBy=null, updatedTS=NULL, "
        + "createdBy=null, createdTS=NULL]";
        assertEquals(expected.toString(), testProcess.toString());

        final int year = 2009;
        final int month = 12;
        final int day = 25;
        final int hour = 11;
        final int minute = 12;
        final int second = 13;

        Calendar testCalendar =
            new GregorianCalendar(year, month, day, hour, minute, second);
        Date testDate = testCalendar.getTime();

        final long long88 = 88L;
        testProcess.getProcess().setProcessId(long88);
        testProcess.setProcessName("processName");
        testProcess.setProcessDescription("processDescription");

        try {
            testProcess.setProcessDefinition(_simplePd);
            testProcess.setProcessGraph(_simplePd);
        } catch (SAXException e) {
            fail();
        } catch (ParserConfigurationException e) {
            fail();
        } catch (IOException e) {
            fail();
        }

        testProcess.setStatus("status");
        testProcess.setExceptionEmailAddress("exceptionEmailAddress");
        testProcess.setLockedBy("lockedBy");
        final int int8 = 8;
        testProcess.setRetentionPeriod(int8);
        testProcess.setNotifyExceptions('Y');
        testProcess.setIsValid(true);
        testProcess.setEffectiveDate(testDate);
        testProcess.setExpirationDate(testDate);
        testProcess.setMaxInstanceDuration(int8);
        testProcess.setCreatedBy("CreatedBy");
        testProcess.setCreatedTS(testDate);
        testProcess.setUpdatedBy("UpdatedBy");
        testProcess.setUpdatedTS(testDate);

        //DomainList
        List<Domain> theList = new ArrayList<Domain>();
        Domain theDomain =  new Domain();
        final long long77 = 77L;
        theDomain.setDomainId(long77);
        theDomain.setDomainName("Domain Name 77");
        theDomain.setDomainDescription("Domain Description 77");
        theList.add(theDomain);

        theDomain =  new Domain();
        theDomain.setDomainId(long88);
        theDomain.setDomainName("Domain Name 88");
        theDomain.setDomainDescription("Domain Description 88");
        theList.add(theDomain);
        DomainList theDomainList = new DomainList();
        theDomainList.setDomainList(theList);
        theDomainList.setSize(theList.size());
        testProcess.setDomainList(theDomainList);

        //ProcessAttributes
        List<ProcessAttribute> anAttributeList = new ArrayList<ProcessAttribute>();

        ProcessAttribute anAttribute = new ProcessAttribute();
        final long long10 = 10L;
        anAttribute.setProcessId(long10);


        anAttribute.setCreatedTS(testDate);
        anAttribute.setUpdatedTS(testDate);
        anAttribute.setAttributeName("attributeName10");
        anAttribute.setAttributeType("0");
        anAttribute.setAttributeValue("attributeValue10");

        anAttributeList.add(anAttribute);

        anAttribute = new ProcessAttribute();

        anAttribute.setProcessId(long10);
        anAttribute.setCreatedTS(testDate);
        anAttribute.setUpdatedTS(testDate);
        anAttribute.setAttributeName("attributeName11");
        anAttribute.setAttributeType("1");
        anAttribute.setAttributeValue("attributeValue11");

        anAttributeList.add(anAttribute);

        ProcessAttributeList attributeList = new ProcessAttributeList();
        attributeList.setProcessAttributeList(anAttributeList);
        attributeList.setSize(2);
        testProcess.setProcessAttributeList(attributeList);

        String expected2 = "ProcessVersion[processId=88, processName=processName, "
                + "processDescription=processDescription, "
                + "processDefinition="
                + _expectedString
                + ", "
                + "processGraph="
                + _expectedString
                + ", "
                + "status=status, "
                + "exceptionEmailAddress=exceptionEmailAddress, lockedBy=lockedBy, "
                + "retentionPeriod=8, notifyExceptions=Y, isValid=TRUE, "
                + "effectiveDate=2010-01-25T11:12:13-05:00, "
                + "expirationDate=2010-01-25T11:12:13-05:00, "
                + "maxInstanceDuration=8, "
                + "domainList=DomainList[ Domain[domainId=77, domainName=Domain Name 77, "
                + "domainDescription=Domain Description 77]  Domain[domainId=88, "
                + "domainName=Domain Name 88, domainDescription=Domain Description 88] ], "
                + "owners=set of Actors, actors=set of Actors, "
                + "processAttributeList=ProcessAttributeList[ Process_Attribute[processId=10, "
                + "attributeName=attributeName10, attributeValue=attributeValue10, "
                + "attributeType=0, updatedTS=2010-01-25T11:12:13-05:00, "
                + "createdTS=2010-01-25T11:12:13-05:00]  Process_Attribute[processId=10, "
                + "attributeName=attributeName11, attributeValue=attributeValue11, "
                + "attributeType=1, updatedTS=2010-01-25T11:12:13-05:00, "
                + "createdTS=2010-01-25T11:12:13-05:00] ], updatedBy=UpdatedBy, "
                + "updatedTS=2010-01-25T11:12:13-05:00, createdBy=CreatedBy, "
                + "createdTS=2010-01-25T11:12:13-05:00]";
        assertEquals(expected2, testProcess.toString());
    }
}
