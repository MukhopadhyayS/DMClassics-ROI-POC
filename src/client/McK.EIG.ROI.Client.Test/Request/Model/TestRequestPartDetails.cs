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
    /// Test class for CommentDetails 
    /// </summary>
    [TestFixture]
    public class TestRequestPartDetails
    {
        #region Fields

        private RequestPartDetails requestPartDetails;

        #endregion

        #region Methods

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            requestPartDetails = new RequestPartDetails();
        }

        [TearDown]
        public void Dispose()
        {
            requestPartDetails = null;
        }

        #endregion

        /// <summary>
        /// Test the content id.
        /// </summary>
        [Test]
        public void TestContentId()
        {
            string contentId = "1";
            requestPartDetails.ContentId = contentId;
            Assert.AreEqual(contentId, requestPartDetails.ContentId);
        }

        /// <summary>
        /// Test the content source.
        /// </summary>
        [Test]
        public void TestContentSource()
        {
            string source = "HPF";
            requestPartDetails.ContentSource = source;
            Assert.AreEqual(source, requestPartDetails.ContentSource);
        }

        [Test]
        public void TestPropertyLists()
        {
            Assert.IsNotNull(requestPartDetails.PropertyLists);
        }

        #endregion
    }
}
