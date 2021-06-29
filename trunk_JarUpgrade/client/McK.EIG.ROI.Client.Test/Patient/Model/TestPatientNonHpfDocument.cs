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
using McK.EIG.ROI.Client.Patient.Model;
using System.Collections.Generic;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Test.Patient.Model
{
    /// <summary>
    /// Test class for PatientNonHpfDocument
    /// </summary>
    [TestFixture]
    public class TestPatientNonHpfDocument
    {
        private PatientNonHpfDocument document;
        private NonHpfDocumentDetails nonHpfDocument;
        private int count;

        [SetUp]
        public void Initialize()
        {
            document = new PatientNonHpfDocument();
        }

        [TearDown]
        public void Dispose()
        {
            document = null;
        }

        /// <summary>
        /// Test method for Icon
        /// </summary>
        [Test]
        public void Test01Icon()
        {
            Assert.IsInstanceOfType(typeof(Image), document.Icon);
        }

        /// <summary>
        /// Test method for Key.
        /// </summary>
        [Test]
        public void Test02Key()
        {
            Assert.AreEqual(ROIConstants.NonHpfDocument, document.Key);
        }

        /// <summary>
        /// Test method for CompareProperty.
        /// </summary>
        [Test]
        public void Test03CompareProperty()
        {
            Assert.IsNull(document.CompareProperty);
        }

        [Test]
        public void Test04Name()
        {
            Assert.AreEqual(ROIConstants.NonHpfDocument, document.Name);
        }

        /// <summary>
        /// Test method to add NonHpfDocumentDetails
        /// </summary>
        [Test]
        public void Test06AddDocument()
        {
            nonHpfDocument = new NonHpfDocumentDetails();
            nonHpfDocument.Id = 2;
            nonHpfDocument.DocType = "Discharge Summary";
            nonHpfDocument.Encounter = "Enc123";
            nonHpfDocument.Department = "Nursing";
            nonHpfDocument.FacilityCode = "AWL";
            nonHpfDocument.PageCount = 4;
            nonHpfDocument.DateOfService = DateTime.Now.Date;

            document.AddDocument(nonHpfDocument);

            Assert.IsTrue(document.ChildrenKeys.Contains(nonHpfDocument.Parent.Key));

            nonHpfDocument.Department = "Radiology";
            document.AddDocument(nonHpfDocument);

            Assert.IsTrue(document.ChildrenKeys.Contains(nonHpfDocument.Parent.Key));
        }

        /// <summary>
        /// Test method to add NonHpfDocumentDetails
        /// </summary>
        [Test]
        public void Test07RemoveDocument()
        {
            document.RemoveChild(nonHpfDocument.Parent.Key);
            Assert.IsTrue(document.GetChildren.Count == 0);
        }
        
    }
}
