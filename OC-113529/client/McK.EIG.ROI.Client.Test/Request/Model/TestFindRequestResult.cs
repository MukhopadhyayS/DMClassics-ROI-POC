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
    /// Test class for FindRequestResult 
    /// </summary>
    [TestFixture]
    public class TestFindRequestResult
    {
        #region Fields

        private FindRequestResult requestResult;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            requestResult = new FindRequestResult();
        }

        [TearDown]
        public void Dispose()
        {
            requestResult = null;
        }

        #endregion

        /// <summary>
        /// Test the RequestSearchResult.
        /// </summary>
        [Test]
        public void TestRequestSearchResult()
        {
            RequestDetails requestdetails = new RequestDetails();
            requestdetails.Id = 1;
            requestResult.RequestSearchResult.Add(requestdetails);
            Assert.Greater(requestResult.RequestSearchResult.Count, 0);
        }
        /// <summary>
        /// Test the MaxCountExceeded.
        /// </summary>
        [Test]
        public void TestMaxCountExceeded()
        {   
            requestResult.MaxCountExceeded = true;
            Assert.IsTrue(requestResult.MaxCountExceeded);
        }

        #endregion
    }
}
