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
    /// Test class for TurnaroundTimeDays.
    /// </summary>
    [TestFixture]
    class TestTurnaroundTimeDays
    {
        // Holds the model object.
        private TurnaroundTimeDay turnaroundTimeDay;

        /// <summary>
        /// Create a new TurnaroundTimeDay instance
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            turnaroundTimeDay = new TurnaroundTimeDay();
        }

        /// <summary>
        /// Dispose the object created 
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            turnaroundTimeDay = null;
        }

        /// <summary>
        /// Test case for Day
        /// </summary>
        [Test]
        public void TestDay()
        {
            string inputDay = "FRIDAY";
            turnaroundTimeDay.Day = inputDay;
            string outputDay = turnaroundTimeDay.Day;
            Assert.AreEqual(inputDay, outputDay);
        }

        /// <summary>
        /// Test case for StatusOfDay
        /// </summary>
        [Test]
        public void TestStatusOfDay()
        {
            string inputStatusOfDay = "FRIDAY";
            turnaroundTimeDay.StatusOfDay = inputStatusOfDay;
            string outputStatusOfDay = turnaroundTimeDay.StatusOfDay;
            Assert.AreEqual(inputStatusOfDay, outputStatusOfDay);
        }

        /// <summary>
        /// Test case for Clone
        /// </summary>
        [Test]
        public void TestClone()
        {
            turnaroundTimeDay = turnaroundTimeDay.Clone();
            Assert.IsNotNull(turnaroundTimeDay);
        }

    }
}
