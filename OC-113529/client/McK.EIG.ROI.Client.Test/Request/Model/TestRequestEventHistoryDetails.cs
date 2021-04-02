#region Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/
#endregion
using System;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for RequestDetails 
    /// </summary>
    [TestFixture]
    public class TestRequestEventHistoryDetails
    {
        #region Fields

        private RequestEventHistoryDetails eventHistoryDetails;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            eventHistoryDetails = new RequestEventHistoryDetails();
        }

        [TearDown]
        public void Dispose()
        {
            eventHistoryDetails = null;
        }

        #endregion

        /// <summary>
        /// Test the Request Id.
        /// </summary>
        [Test]
        public void TestRequestId()
        {
            long input = 1;
            eventHistoryDetails.RequestId = input;
            long output = eventHistoryDetails.RequestId;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Test the Event Date.
        /// </summary>
        [Test]
        public void TestEventDate()
        {            
            eventHistoryDetails.EventDate = null;
            Assert.IsNull(eventHistoryDetails.EventDate);
        }

        /// <summary>
        /// Test the Event Remarks.
        /// </summary>
        [Test]
        public void TestEventRemarks()
        {
            string inputevent = "Comment Updated";
            eventHistoryDetails.EventRemarks = inputevent;
            string outputevent = eventHistoryDetails.EventRemarks;
            Assert.AreEqual(inputevent, outputevent);
        }

        /// <summary>
        /// Test the Originator.
        /// </summary>
        [Test]
        public void TestOriginator()
        {
            string inputevent = "Admin";
            eventHistoryDetails.Originator = inputevent;
            string outputevent = eventHistoryDetails.Originator;
            Assert.AreEqual(inputevent, outputevent);
        }

        /// <summary>
        /// Test the OriginatorId.
        /// </summary>
        [Test]
        public void TestOriginatorId()
        {
            long inputevent = 1;
            eventHistoryDetails.OriginatorId = inputevent;
            long outputevent = eventHistoryDetails.OriginatorId;
            Assert.AreEqual(inputevent, outputevent);
        }

        /// <summary>
        /// Test the RequestEvent.
        /// </summary>
        [Test]
        public void TestRequestEvent()
        {
            string inputevent = "Comment added";
            eventHistoryDetails.RequestEvent = inputevent;
            string outputevent = eventHistoryDetails.RequestEvent;
            Assert.AreEqual(inputevent, outputevent);
        }

        /// <summary>
        /// Test the EventType.
        /// </summary>
        [Test]
        public void TestRequestEventType()
        {
            eventHistoryDetails.EventType = EventType.AdjustmentPosted;
            Assert.AreEqual(eventHistoryDetails.EventType, EventType.AdjustmentPosted);
        }

        #endregion

    }
}
