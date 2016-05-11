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
using McK.EIG.ROI.Client.Admin.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    ///  Test class for SSNMasking.
    /// </summary>
    [TestFixture]
    public class TestSSNMaskDetails
    {
        private SSNMaskDetails maskDetails;

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            maskDetails = new SSNMaskDetails();
        }

        [TearDown]
        public void Dispose()
        {
            maskDetails = null;
        }

        #endregion

        #region TestMethods

        [Test]
        public void TestSsnMaskId()
        {
            long id = 100;
            maskDetails.Id = id;
            long outputId = maskDetails.Id;
            Assert.AreEqual(id, outputId);
        }

        [Test]
        public void TestIsSsnMask()
        {
            maskDetails.IsMasking = true;
            Assert.IsTrue(maskDetails.IsMasking);
        }

        [Test]
        public void TestSsnMaskRecordVersion()
        {
            int version = 50;
            maskDetails.RecordVersionId = version;
            long outputvalue = maskDetails.RecordVersionId;
            Assert.AreEqual(version, outputvalue);
        }

        #endregion



    }
}
