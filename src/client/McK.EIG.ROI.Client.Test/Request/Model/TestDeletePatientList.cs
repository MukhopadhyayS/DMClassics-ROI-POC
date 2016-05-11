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
using System.Collections.Generic;
using System.Linq;
using System.Text;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    [TestFixture]
    public class TestDeletePatientList
    {
        private DeleteList deleteList;

        [SetUp]
        public void Initialize()
        {
            deleteList = new DeleteList();
        }

        [TearDown]
        public void Dispose()
        {
            deleteList = null;
        }

        [Test]
        public void TestRequestDeletedPatients()
        {
            deleteList.DeletedPatients.Add(1);
            deleteList.DeletedPatients.Add(2);
            Assert.IsTrue(deleteList.DeletedPatients.Count > 0);
        }

        [Test]
        public void TestRequestDeletedEncounters()
        {
            deleteList.DeletedEncounters.Add(1);
            deleteList.DeletedEncounters.Add(2);
            Assert.IsTrue(deleteList.DeletedEncounters.Count > 0);
        }

        [Test]
        public void TestRequestDeletedDocuments()
        {
            deleteList.DeletedDocuments.Add(1);
            deleteList.DeletedDocuments.Add(2);
            Assert.IsTrue(deleteList.DeletedDocuments.Count > 0);
        }

        [Test]
        public void TestRequestDeletedVersions()
        {
            deleteList.DeletedVersions.Add(1);
            deleteList.DeletedVersions.Add(2);
            Assert.IsTrue(deleteList.DeletedVersions.Count > 0);
        }

        [Test]
        public void TestRequestDeletedPages()
        {
            deleteList.DeletedPages.Add(1);
            deleteList.DeletedPages.Add(2);
            Assert.IsTrue(deleteList.DeletedPages.Count > 0);
        }

        [Test]
        public void TestDeleteDARSupplementalPatients()
        {
            deleteList.DeleteDARSupplementalPatients.Add(1);
            deleteList.DeleteDARSupplementalPatients.Add(2);
            Assert.IsTrue(deleteList.DeleteDARSupplementalPatients.Count > 0);
        }

        [Test]
        public void TestDeleteSupplementalPatients()
        {
            deleteList.DeletesupplementalPatients.Add(1);
            deleteList.DeletesupplementalPatients.Add(2);
            Assert.IsTrue(deleteList.DeletesupplementalPatients.Count > 0);
        }

        [Test]
        public void TestDeleteSupplementalDocuments()
        {
            deleteList.DeletesupplementalDocuments.Add(1);
            deleteList.DeletesupplementalDocuments.Add(2);
            Assert.IsTrue(deleteList.DeletesupplementalDocuments.Count > 0);
        }

        [Test]
        public void TestDeleteSupplementalAttachments()
        {
            deleteList.DeleteSupplementalAttachments.Add(1);
            deleteList.DeleteSupplementalAttachments.Add(2);
            Assert.IsTrue(deleteList.DeleteSupplementalAttachments.Count > 0);
        }

        [Test]
        public void TestDeleteSupplementaryDocuments()
        {
            deleteList.DeleteSupplementaryDocuments.Add(1);
            deleteList.DeleteSupplementaryDocuments.Add(2);
            Assert.IsTrue(deleteList.DeleteSupplementaryDocuments.Count > 0);
        }

        [Test]
        public void TestDeleteSupplementaryAttachments()
        {
            deleteList.DeleteSupplementaryAttachments.Add(1);
            deleteList.DeleteSupplementaryAttachments.Add(2);
            Assert.IsTrue(deleteList.DeleteSupplementaryAttachments.Count > 0);
        }

        [Test]
        public void TestClear()
        {
            deleteList.Clear();
            Assert.IsTrue(deleteList.DeletedPatients.Count == 0);

        }
    }
}
