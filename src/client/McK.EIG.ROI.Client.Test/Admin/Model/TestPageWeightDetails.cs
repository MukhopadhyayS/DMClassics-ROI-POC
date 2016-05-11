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
    ///  Test class for PageWeightDetails.
    /// </summary>
    [TestFixture]
    public class TestPageWeightDetails
    {
        private PageWeightDetails pageweight;

        #region Methods
        [SetUp]
        public void Initialize()
        {
            pageweight = new PageWeightDetails();
        }

        [TearDown]
        public void Dispose()
        {
            pageweight = null;
        }
        #region TestMethods

        [Test]
        public void TestPageWeightId()
        {
            int pageid = 1;
            pageweight.Id = pageid;
            long outputpagevalue = pageweight.Id;
            Assert.AreEqual(pageid, outputpagevalue);
        }
        [Test]
        public void TestPageWeight()
        {
            int inputpagevalue = 10;
            pageweight.PageWeight = inputpagevalue;
            long outputpagevalue = pageweight.PageWeight;
            Assert.AreEqual(inputpagevalue, outputpagevalue);
        }
        [Test]
        public void TestPageWeightRecordVersion()
        {
            int version = 50;
            pageweight.RecordVersionId = version;
            long outputpagevalue = pageweight.RecordVersionId;
            Assert.AreEqual(version, outputpagevalue);
        }
        #endregion

        #endregion



    }
}
