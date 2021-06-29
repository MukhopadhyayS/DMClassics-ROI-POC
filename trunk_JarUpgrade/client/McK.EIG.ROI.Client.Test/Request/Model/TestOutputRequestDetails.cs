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
using System.Globalization;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for CommentDetails 
    /// </summary>
    [TestFixture]
    public class TestOutputRequestDetails
    {
        #region Fields

        private OutputRequestDetails outputRequestDetails;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            outputRequestDetails = new OutputRequestDetails(1090,
                                                            467,
                                                            "test",
                                                            DateTime.Now);
        }

        [TearDown]
        public void Dispose()
        {
            outputRequestDetails = null;
        }

        #endregion

        /// <summary>
        /// Test the destination.
        /// </summary>
        [Test]
        public void TestDestination()
        {
            string destinationName = "PRINTER";
            OutputDestinationDetails outputDestinationDetails = new OutputDestinationDetails();
            outputDestinationDetails.Name = destinationName;
            Assert.AreEqual(destinationName, outputDestinationDetails.Name);
        }

        /// <summary>
        /// Test request parts.
        /// </summary>
        [Test]
        public void TestRequestParts()
        {
            Assert.IsNotNull(outputRequestDetails.RequestParts);
        }

        /// <summary>
        /// Test request id.
        /// </summary>
        [Test]
        public void TestRequestId()
        {
            Assert.AreEqual(1090, outputRequestDetails.RequestId);
        }


        /// <summary>
        /// Test release id.
        /// </summary>
        [Test]
        public void TestReleaseId()
        {
            Assert.AreEqual(467, outputRequestDetails.ReleaseId);
        }

        /// <summary>
        /// Test request password.
        /// </summary>
        [Test]
        public void TestRequestPassword()
        {
            Assert.AreEqual("test", outputRequestDetails.RequestPassword);
        }
        #endregion
    }
}
