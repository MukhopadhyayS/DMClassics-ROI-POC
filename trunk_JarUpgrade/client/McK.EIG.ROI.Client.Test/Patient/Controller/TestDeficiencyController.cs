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
using System.Collections.ObjectModel;
using System.Configuration;
using System.Globalization;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;

namespace McK.EIG.ROI.Client.Test.Patient.Controller
{
    /// <summary>
    /// Test class for DeficiencyController.
    /// </summary>
    [TestFixture]
    [Category("TestDeficiencyController")]
    public class TestDeficiencyController : TestBase
    {
        #region Fields

        private PatientController patientController;

        #endregion

        #region Constructor

        public TestDeficiencyController()
        {
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);

        }

        #endregion

        #region Methods

        #region NUnit

        [SetUp]
        public void Init()
        {
            patientController = PatientController.Instance;
        }

        [TearDown]
        public void Dispose()
        {
            patientController = null;
        }

        #endregion

        #region Service Methods

        [Test]
        public void TestRetrieveHpfDocuments()
        {
            FindPatientCriteria patientSearchCriteria = new FindPatientCriteria();
            PatientDetails hpfPatient = null;

            patientSearchCriteria.FirstName     = string.Empty;
            patientSearchCriteria.LastName      = string.Empty;
            patientSearchCriteria.Dob           = null;
            patientSearchCriteria.EPN           = string.Empty;
            patientSearchCriteria.FacilityCode  = null;
            patientSearchCriteria.MRN           = TestBase.DeficiencyPatientMrn;
            patientSearchCriteria.SSN           = string.Empty;
            patientSearchCriteria.Encounter     = string.Empty;
            patientSearchCriteria.MaxRecord     = Convert.ToInt32(ConfigurationManager.AppSettings["MaximumSearchResult"], System.Threading.Thread.CurrentThread.CurrentUICulture);

            FindPatientResult result = patientController.FindPatient(patientSearchCriteria);
            foreach (PatientDetails patientDetails in result.PatientSearchResult)
            {
                if (patientDetails.IsHpf)
                {
                    hpfPatient = patientDetails;
                    break;
                }
            }
            patientController.RetrieveHpfDocuments(hpfPatient);
            int deficiencyCount = 0;
            foreach (EncounterDetails enc in hpfPatient.Encounters)
            {
                if (enc.HasDeficiency)
                {
                    Collection<DeficiencyDetails> deficiencyDetails = patientController.RetrieveDeficiencyDetails(enc.EncounterId, enc.Facility);
                    deficiencyCount = deficiencyDetails.Count;
                }
            }

            Assert.IsTrue(deficiencyCount >= 0);
        }

        #endregion

        #endregion
    }
}
