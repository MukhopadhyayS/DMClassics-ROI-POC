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
using System.Drawing;

using NUnit.Framework;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    /// Test class for RequestReason Details.
    /// </summary>
    [TestFixture]
    public class TestReasonDetails
    {
        #region Fields

        //Holds the object of model class.
        private ReasonDetails requestReasonDetails;

        #endregion

        #region NUnit

        [SetUp]
        public void Initialize()
        {
            requestReasonDetails = new ReasonDetails();
        }

        [TearDown]
        public void Dispose()
        {
            requestReasonDetails = null;
        }

        #endregion

        #region TestMethods

        /// <summary>
        /// Test case for request reason id.
        /// </summary>
        [Test]
        public void TestRequestReasonId()
        {
            long inputRequestId     = 100;
            requestReasonDetails.Id = inputRequestId;
            long outputRequestId    = requestReasonDetails.Id;
            Assert.AreEqual(outputRequestId, inputRequestId);
        }

        /// <summary>
        /// Test case for Request Reason Name.
        /// </summary>
        [Test]
        public void TestRequestReasonName()
        {
            string inputReasonName = "Attorney Request";
            requestReasonDetails.Name = inputReasonName;
            string outputReasonName = requestReasonDetails.Name;
            Assert.AreEqual(outputReasonName, inputReasonName);
        }

        /// <summary>
        /// Test case for Request Reason Attribute.
        /// </summary>
        [Test]
        public void TestRequestReasonAttribute()
        {
            requestReasonDetails.Attribute = RequestAttr.Tpo;
            string outputReasonAttribute = requestReasonDetails.Attribute.ToString();
            Assert.AreEqual(outputReasonAttribute.ToUpper(), ReasonDetails.RequestListTpo);
        }

        /// <summary>
        /// Test case for Request Reason Display Text.
        /// </summary>
        [Test]
        public void TestReasonDisplayText()
        {
            string inputDisplayText = "Attorney Request Patient Documents";
            requestReasonDetails.DisplayText = inputDisplayText;
            string outputDisplayText = requestReasonDetails.DisplayText;
            Assert.AreEqual(outputDisplayText, inputDisplayText);
        }

        /// <summary>
        /// Test case for Request status.
        /// </summary>
        [Test]
        public void TestRequestStatus()
        {
            RequestStatus inputStatus = RequestStatus.Completed;
            requestReasonDetails.RequestStatus = inputStatus;
            RequestStatus outputStatus = requestReasonDetails.RequestStatus;
            Assert.AreEqual(inputStatus, outputStatus);
            Assert.AreEqual(EnumUtilities.GetDescription(inputStatus), requestReasonDetails.RequestStatusText);
        }

        /// <summary>
        /// Test case for Request status Text.
        /// </summary>
        [Test]
        public void TestRequestStatusText()
        {
            string inputRequestStatusText = "Pre-Billed";
            requestReasonDetails.RequestStatusText = inputRequestStatusText;
            string outputRequestStatusText = requestReasonDetails.RequestStatusText;
            Assert.AreEqual(inputRequestStatusText, outputRequestStatusText);
        }

        /// <summary>
        /// Test case for Request Type.
        /// </summary>
        [Test]
        public void TestReasonType()
        {
            ReasonType inputRequestType = ReasonType.Request;
            requestReasonDetails.Type = inputRequestType;
            ReasonType outputRequestType = requestReasonDetails.Type;
            Assert.AreEqual(outputRequestType, inputRequestType);
        }

        /// <summary>
        /// Test case for Request Type.
        /// </summary>
        [Test]
        public void TestAttributeName()
        {
            string inputAttributeName = "TPO";
            requestReasonDetails.AttributeName = inputAttributeName;
            string outputAttributeName = requestReasonDetails.AttributeName;
            Assert.AreEqual(outputAttributeName, inputAttributeName);
        }

        #endregion

    }
}
