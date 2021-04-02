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
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test Class for Request Global Document.
    /// </summary>
    [TestFixture]
    public class TestRequestGlobalDocument
    {
        private RequestGlobalDocuments requestGlobalDocuments;

        [SetUp]
        public void Initialize()
        {
            requestGlobalDocuments = new RequestGlobalDocuments();
        }

        [TearDown]
        public void Dispose()
        {
            requestGlobalDocuments = null;
        }

        #region TestMethods

        /// <summary>
        /// Test Case for Name.
        /// </summary>
        [Test]
        public void TestName()
        {
            string inputName = requestGlobalDocuments.Name;
        }

        /// <summary>
        /// Test Case for key.
        /// </summary>
        [Test]
        public void TestKey()
        {
            string inputKey = requestGlobalDocuments.Key;
        }

        /// <summary>
        /// Test Case for key.
        /// </summary>
        [Test]
        public void TestIcon()
        {
            Assert.IsNotNull(requestGlobalDocuments.Icon);
        }

        #endregion
    }
}
