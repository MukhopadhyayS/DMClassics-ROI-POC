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
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Test.Patient.Model
{
    /// <summary>
    /// Test class for DeficiencyDetails
    /// </summary>
    [TestFixture]
    public class TestDeficiencyDetails
    {
        #region Fields

        private DeficiencyDetails deficiencyDetails;

        #endregion

        #region Method

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            deficiencyDetails = new DeficiencyDetails();
        }

        [TearDown]
        public void Dispose()
        {
            deficiencyDetails = null;
        }

        #endregion

        /// <summary>
        /// Test the Document.
        /// </summary>
        [Test]
        public void TestDocument()
        {
            string input = "Cold";
            deficiencyDetails.Document = input;
            string output = deficiencyDetails.Document;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Test the IsLinked.
        /// </summary>
        [Test]
        public void TestIsLinked()
        {   
            deficiencyDetails.IsLinked = true;
            Assert.AreEqual(true, deficiencyDetails.IsLinked);
        }

        /// <summary>
        /// Test the Document.
        /// </summary>
        [Test]
        public void TestLockeBy()
        {
            string input = "Locked";
            deficiencyDetails.LockedBy = input;
            string output = deficiencyDetails.LockedBy;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Test the Document.
        /// </summary>
        [Test]
        public void TestPhysician()
        {
            string input = "Physician";
            deficiencyDetails.Physician = input;
            string output = deficiencyDetails.Physician;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Test the Document.
        /// </summary>
        [Test]
        public void TestReason()
        {
            string input = "Cold";
            deficiencyDetails.Reason = input;
            string output = deficiencyDetails.Reason;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Test the Document.
        /// </summary>
        [Test]
        public void TestStatus()
        {
            string input = "Beginning";
            deficiencyDetails.Status = input;
            string output = deficiencyDetails.Status;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Test the Document.
        /// </summary>
        [Test]
        public void TestType()
        {
            string input = "General";
            deficiencyDetails.Type = input;
            string output = deficiencyDetails.Type;
            Assert.AreEqual(input, output);
        }

        #endregion
    }
}
