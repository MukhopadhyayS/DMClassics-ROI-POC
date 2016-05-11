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

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Test.Base.Model
{
    /// <summary>
    ///  Test class for User configuration data.
    /// </summary>
    [TestFixture]
    public class TestConfigurationData
    {
        #region Fields

        private ConfigurationData configurationData;

        #endregion


        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            configurationData = new ConfigurationData();
        }

        /// <summary>
        /// Dispose ConfigurationData.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            configurationData = null;
        }

        /// <summary>
        /// Test method for LoadLeveler
        /// </summary>
        [Test]
        public void TestLoadLeveler()
        {
            string loadLeveler = "LoadLeveler";
            configurationData.LoadLeveler = loadLeveler;                      
            Assert.AreEqual(loadLeveler, configurationData.LoadLeveler);
        }

        /// <summary>
        /// Test method for LoadLevelerUrl
        /// </summary>
        [Test]
        public void TestLoadLevelerUrl()
        {
            Uri Url = new Uri("http://127.0.0.1:8080/roi/services");
            configurationData.LoadLevelerUrl = Url;
            Assert.AreEqual(Url, configurationData.LoadLevelerUrl);
        }

        /// <summary>
        /// Test method for ContentWebServiceUrl
        /// </summary>
        [Test]
        public void TestContentWebServiceUrl()
        {
            Uri Url = new Uri("http://127.0.0.1:8080/roi/services");
            configurationData.ContentWebServiceUrl = Url;
            Assert.AreEqual(Url, configurationData.ContentWebServiceUrl);
        }

        /// <summary>
        /// Test method for PageCountUrl
        /// </summary>
        [Test]
        public void TestPageCountUrl()
        {
            Uri Url = new Uri("http://127.0.0.1:8080/roi/services");
            configurationData.PageCountUrl = Url;
            Assert.AreEqual(Url, configurationData.PageCountUrl);
        }

    }
}
