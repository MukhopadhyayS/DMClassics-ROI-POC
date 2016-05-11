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
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;

namespace McK.EIG.ROI.Client.Test.Base.Model
{
    /// <summary>
    ///  Test class for UserSecurities.
    /// </summary>
    [TestFixture]
    public class TestUserSecurities : TestBase
    {
        #region Fields

        private UserSecurities userSecurities;

        #endregion

        #region Setup

        [TestFixtureSetUp]
        public void Init()
        {
            userSecurities = new UserSecurities();
        }

        #endregion

        #region TearDown

        [TestFixtureTearDown]
        public void Dispose()
        {
            base.LogOff();
            userSecurities = null;
        }

        #endregion

        #region TestMethods

        /// <summary>
        /// To test Add method
        /// </summary>
        /// <returns></returns>
        [Test]
        public void TestAdd()
        {
            int rightsCount = userSecurities.Rights.Count;
            userSecurities.Add("100", true);
            Assert.AreEqual(rightsCount + 1, userSecurities.Rights.Count);
        }

        /// <summary>
        /// To test IsAllowed method
        /// </summary>
        /// <returns></returns>
        [Test]
        public void TestIsAllowed()
        {
            Assert.IsNotNull(userSecurities.IsAllowed("100"));
        }

        /// <summary>
        /// To test retrieve list of rights
        /// </summary>
        /// <returns></returns>
        [Test]
        public void TestRights()
        {
            Assert.IsNotNull(userSecurities.Rights);
        }

        #endregion
    }
}
