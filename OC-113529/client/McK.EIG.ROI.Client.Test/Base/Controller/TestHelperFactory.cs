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
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Test.Base.Model
{
    /// <summary>
    ///  Test class for HelperFactory.
    /// </summary>
    [TestFixture]
    public class TestHelperFactory
    {
        #region NUnit

        [SetUp]
        public void Init()
        {
        }

        [TearDown]
        public void Dispose()
        {

        }

        #endregion

        #region TestMethods

        #region TestGetHelper

        /// <summary>
        ///  Test GetHelper Method.
        /// </summary>
        [Test]
        public void TestGetHelper()
        {
            Assert.IsInstanceOfType(typeof(ROIHelper), HelperFactory.GetHelper(HelperFactory.RoiServiceType));
            Assert.IsInstanceOfType(typeof(HPFWHelper), HelperFactory.GetHelper(HelperFactory.HpfwServiceType));
        }

        #endregion

        #region TestGetHelperWithException

        /// <summary>
        ///  Test GetHelper Method which raises exception.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestGetHelperWithException()
        {
            Assert.IsInstanceOfType(typeof(ROIHelper), HelperFactory.GetHelper("test"));
        }

        #endregion

        #endregion

    }
}
