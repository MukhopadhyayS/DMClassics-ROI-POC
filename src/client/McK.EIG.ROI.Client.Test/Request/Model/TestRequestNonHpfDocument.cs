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
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Patient.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for RequestNonHpfDocument.
    /// </summary>
    [TestFixture]
    public class TestRequestNonHpfDocument
    {
        private RequestNonHpfDocuments requestNonHpfDocuments;

        [SetUp]
        public void Initialize()
        {
            requestNonHpfDocuments = new RequestNonHpfDocuments();
        }

        [TearDown]
        public void Dispose()
        {
            requestNonHpfDocuments = null;
        }

        #region TestMethods

        /// <summary>
        /// Test Case for Name.
        /// </summary>
        [Test]
        public void TestName()
        {
            string inputName = ROIConstants.NonHpfDocument;
            Assert.AreEqual(inputName, requestNonHpfDocuments.Name);
            
        }

        /// <summary>
        /// Test Case for Key.
        /// </summary>
        [Test]
        public void TestKey()
        {
            Assert.AreEqual(ROIConstants.NonHpfDocument, requestNonHpfDocuments.Key);
        }


        /// <summary>
        /// Test Case for Icon.
        /// </summary>
        [Test]
        public void TestIcon()
        {
            Assert.IsInstanceOfType(typeof(Image), requestNonHpfDocuments.Icon);
        }

       

        #endregion
    }
}
