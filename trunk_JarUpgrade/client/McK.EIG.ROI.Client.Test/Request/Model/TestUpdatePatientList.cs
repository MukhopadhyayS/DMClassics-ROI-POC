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
    public class TestUpdatePatientList
    {
        private UpdateRequestPatients updateRequestPatients;

        [SetUp]
        public void Initialize()
        {
            updateRequestPatients = new UpdateRequestPatients();
        }

        [TearDown]
        public void Dispose()
        {
            updateRequestPatients = null;
        }               


        [Test]
        public void TestRequestPatients()
        {
            RequestPatientDetails requestPatient = new RequestPatientDetails();
            requestPatient.Id = 1;
            requestPatient.FullName = "Denmark,mark";
            requestPatient.Gender = "Male";
            requestPatient.MRN = "Markm01";
            requestPatient.EPN = "HNE001";
            requestPatient.SSN = "123456";
            requestPatient.DOB = DateTime.Now;
            requestPatient.FacilityCode = "AD";
            requestPatient.IsFreeformFacility = false;
            requestPatient.IsVip = false;
            requestPatient.IsHpf = false;
            requestPatient.EncounterLocked = false;
            requestPatient.IsLockedPatient = false;
            updateRequestPatients.RequestPatients.Add(requestPatient);
            Assert.IsTrue(updateRequestPatients.RequestPatients.Count > 0);
        }

        [Test]
        public void TestRequestEncounters()
        {   
            RequestEncounterDetails requestEncounter = new RequestEncounterDetails();
            requestEncounter.EncounterId = "E001";
            requestEncounter.Facility = "AD";
            requestEncounter.PatientType = "IP";
            requestEncounter.HasDeficiency = false;
            requestEncounter.IsLocked = false;
            requestEncounter.IsVip = false;
            requestEncounter.AdmitDate = DateTime.Now;
            requestEncounter.DischargeDate = DateTime.Now;            
            Assert.IsTrue(requestEncounter != null);
        }

        [Test]
        public void TestRequestDocuments()
        {   
            RequestDocumentDetails requestDocument = new RequestDocumentDetails();
            requestDocument.DocType = "101 cold data vault";
            requestDocument.Subtitle = "document";
            requestDocument.DocTypeId = 1;
            requestDocument.ChartOrder = "110";
            requestDocument.DocumentId = 1;
            requestDocument.DocumentDateTime = DateTime.Now;
            requestDocument.DateOfService = DateTime.Now;
            Assert.IsTrue(requestDocument != null);
        }

        [Test]
        public void TestRequestVersions()
        {   
            RequestVersionDetails requestVersion = new RequestVersionDetails();
            requestVersion.VersionNumber = 123;            
            Assert.IsTrue(requestVersion != null);
        }

        [Test]
        public void TestRequestPages()
        {   
            RequestPageDetails requestPage = new RequestPageDetails();
            requestPage.IMNetId = "123";
            requestPage.PageNumber = 1;
            requestPage.ContentCount = 123;
            Assert.IsTrue(requestPage != null);
        }

        public void TestDeleteRequestPatients()
        {
            updateRequestPatients.DeleteList = new DeleteList();
            Assert.IsInstanceOfType(typeof(DeleteList), updateRequestPatients.DeleteList);
        }
    }
}
