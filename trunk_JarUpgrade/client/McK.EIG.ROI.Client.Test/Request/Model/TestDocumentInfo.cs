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
    /// Test class for FeeChargeDetails 
    /// </summary>
    [TestFixture]
    public class TestDocumentInfo
    {
        #region Fields

        private DocumentInfo documentInfo;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            documentInfo = new DocumentInfo();
        }

        [TearDown]
        public void Dispose()
        {
            documentInfo = null;
        }

        #endregion

        /// <summary>
        /// Test the Id
        /// </summary>
        [Test]
        public void TestId()
        {
            long input = 2;
            documentInfo.Id = input;
            Assert.AreEqual(input, documentInfo.Id);
        }

        /// <summary>
        /// Test the Name
        /// </summary>
        [Test]
        public void TestName()
        {
            string input = "Pre-Bill Letter Template";
            documentInfo.Name = input;
            Assert.AreEqual(input, documentInfo.Name);
        }

        /// <summary>
        /// Test the type
        /// </summary>
        [Test]
        public void TestType()
        {
            string input = "Invoice";
            documentInfo.Type = input;
            Assert.AreEqual(input, documentInfo.Type);
        }  
        /// <summary>
        /// Test the Requestor Grouping Key
        /// </summary>
        [Test]
        public void TestRequestorGroupingKey()
        {
            string inputRequestorGroupingKey = "RequestorName";
            documentInfo.RequestorGroupingKey = inputRequestorGroupingKey;
            string outputRequestorGroupingKey = documentInfo.RequestorGroupingKey;
            Assert.AreEqual(inputRequestorGroupingKey, outputRequestorGroupingKey);
        }
        #endregion
    }
}
