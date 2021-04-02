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
using System.ComponentModel;
using System.Drawing;
using System.Collections.Generic;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Model;


namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for TestRequestDocumentSubtitle
    /// </summary>
    [TestFixture]
    public class TestRequestDocumentSubtitle
    {
        private RequestDocumentSubtitle subtitle;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            subtitle = new RequestDocumentSubtitle();
        }

        /// <summary>
        /// Dispose TestRequestDocumentSubtitle.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            subtitle = null;
        }

        /// <summary>
        /// Test method for subtitle
        /// </summary>
        [Test]
        public void TestName()
        {
            string name = "MRI - Brain";
            subtitle.Subtitle = name;
            Assert.AreEqual(name, subtitle.Subtitle);
            Assert.AreEqual(name, subtitle.Name);
            Assert.AreEqual(name, subtitle.Key);
        }

        /// <summary>
        /// Test method for icon
        /// </summary>
        [Test]
        public void TestIcon()
        {
            Assert.IsInstanceOfType(typeof(Image), subtitle.Icon);
        }
    }
}
