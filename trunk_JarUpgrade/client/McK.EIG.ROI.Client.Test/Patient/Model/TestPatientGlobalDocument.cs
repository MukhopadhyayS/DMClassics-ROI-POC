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
using System.Drawing;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Test.Patient.Model
{
    /// <summary>
    /// Test class for PatientGlobalDocument
    /// </summary>
    [TestFixture]
    public class TestPatientGlobalDocument
    {
        private PatientGlobalDocument documentDetails;

        [SetUp]
        public void Initialize()
        {
            documentDetails = new PatientGlobalDocument();
        }

        [TearDown]
        public void Dispose()
        {
            documentDetails = null;
        }

        /// <summary>
        /// Test method for TestDocuments.
        /// </summary>
        [Test]
        public void TestDocuments()
        {
            Assert.IsNotNull(documentDetails.Documents);
        }

        /// <summary>
        /// Test method for Icon
        /// </summary>
        [Test]
        public void TestIcon()
        {
            Assert.IsInstanceOfType(typeof(Image), documentDetails.Icon);
        }

        /// <summary>
        /// Test method for Key.
        /// </summary>
        [Test]
        public void TestKey()
        {
            Assert.IsNull(documentDetails.Key);
        }

        /// <summary>
        /// Test method for Key.
        /// </summary>
        [Test]
        public void TestCompareProperty()
        {
            Assert.IsNull(documentDetails.CompareProperty);
        }

        [Test]
        public void TestName()
        {
            Assert.AreEqual(ROIConstants.GlobalDocument, documentDetails.Name);
        }
    }
}
