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
using System.Collections.ObjectModel;
using System.Collections.Generic;
//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Model;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Test.Requestors.Model
{
    /// <summary>
    /// Test Class for Find Requestor Results.
    /// </summary>
    [TestFixture]
    public class TestFindRequestorResult
    {
        private FindRequestorResult findRequestorResult;

        [SetUp]
        public void Initialize()
        {
            findRequestorResult = new FindRequestorResult();
        }

        [TearDown]
        public void Dispose()
        {
            findRequestorResult = null;
        }

        #region TestMethods

        /// <summary>
        /// Test Case for MaxCountExceeded.
        /// </summary>
        [Test]
        public void TestMaxCountExceeded()
        {
            bool inputMaxCountExceeded = true;
            findRequestorResult.MaxCountExceeded = inputMaxCountExceeded;
            bool outputMaxCountExceeded = findRequestorResult.MaxCountExceeded;
            Assert.AreEqual(inputMaxCountExceeded, outputMaxCountExceeded);
        }

        /// <summary>
        /// Test case for Requestor Result
        /// </summary>
        [Test]
        public void TestRequestorResult()
        {
            Collection<RequestorDetails> details = findRequestorResult.RequestorSearchResult;
            Assert.IsInstanceOfType(typeof(Collection<RequestorDetails>), details);
        }

        /// <summary>
        /// Test Method for Requestor type Id.
        /// </summary>
        [Test]
        public void TestRequestorType()
        {
            long inputRequestorType = 0;
            findRequestorResult.RequestorTypeId = inputRequestorType;
            long outputrequestorType = findRequestorResult.RequestorTypeId;
            Assert.AreEqual(inputRequestorType, outputrequestorType);
        }
        #endregion
    }
}
