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
    public class TestRequestPatients
    {
        private RequestPatients requestPatients;

        [SetUp]
        public void Initialize()
        {
            requestPatients = new RequestPatients();
        }

        [TearDown]
        public void Dispose()
        {
            requestPatients = null;
        }  

        [Test]
        public void TestRequestPatientList()
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
            requestPatients.RequestPatientList.Add(requestPatient);
            Assert.IsTrue(requestPatients.RequestPatientList.Count > 0);
        }

        [Test]
        public void TestPageStatus()
        {
            requestPatients.PageStatus.Add(1, true);
            requestPatients.PageStatus.Add(2, false);
            Assert.IsTrue(requestPatients.PageStatus.Count > 0);
        }
    }
}
