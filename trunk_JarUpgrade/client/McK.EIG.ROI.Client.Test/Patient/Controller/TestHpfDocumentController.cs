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

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;

namespace McK.EIG.ROI.Client.Test.Patient.Controller
{
    /// <summary>
    /// Test case for HpfDocumentController.
    /// </summary>
    [TestFixture]
    [Category("TestHpfDocumentController")]
    public class TestHpfDocumentController : TestBase
    {
        #region Fields

        private PatientController patientController;
        private ROIAdminController roiAdminController;

        #endregion

        #region Constructor

        public TestHpfDocumentController()
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
            roiAdminController = ROIAdminController.Instance;
        }

        [TearDown]
        public void Dispose()
        {
            patientController = null;
            roiAdminController = null;
        }

        #endregion

        #region Test Methods

        [Test]
        public void TestRetrieveAllRecordViews()
        {
            //Collection<RecordViewDetails> recordViewDetails = roiAdminController.RetrieveAllRecordViews(false);
            Collection<RecordViewDetails> recordViewDetails = UserData.Instance.RecordViews;
            Assert.IsNotNull(recordViewDetails);
        }

        [Test]
        public void TestRetrieveAllRecordViewsWithDocuments()
        {
            //Collection<RecordViewDetails> recordViewDetails = roiAdminController.RetrieveAllRecordViews(true);
            Collection<RecordViewDetails> recordViewDetails = UserData.Instance.RecordViews;
            Assert.IsNotNull(recordViewDetails);
        }


        [Test]
        public void TestRetrieveImnetsByImnet()
        {
            string ImNetId = "IMAGES1   05980700001";
            string ImNetIds = patientController.RetrieveImnetsByImnet(ImNetId);
            Assert.IsNotNull(ImNetIds);
        }

        [Test]
        public void TestRetrieveHpfDocuments()
        {
            FindPatientCriteria patientSearchCriteria = new FindPatientCriteria();
            PatientDetails hpfPatient = null;

            patientSearchCriteria.FirstName     = TestBase.PatientFirstName;
            patientSearchCriteria.LastName      = string.Empty;
            patientSearchCriteria.Dob           = null;
            patientSearchCriteria.EPN           = string.Empty;
            patientSearchCriteria.FacilityCode  = null;
            patientSearchCriteria.MRN           = string.Empty;
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

            Assert.IsNotNull(patientController.RetrieveHpfDocuments(hpfPatient));
        }

        [Test]
        public void TestRetrieveHpfEncounters()
        {
            FindPatientCriteria patientSearchCriteria = new FindPatientCriteria();
            PatientDetails hpfPatient = null;

            patientSearchCriteria.FirstName = TestBase.PatientFirstName;
            patientSearchCriteria.LastName = string.Empty;
            patientSearchCriteria.Dob = null;
            patientSearchCriteria.EPN = string.Empty;
            patientSearchCriteria.FacilityCode = null;
            patientSearchCriteria.MRN = string.Empty;
            patientSearchCriteria.SSN = string.Empty;
            patientSearchCriteria.Encounter = string.Empty;
            patientSearchCriteria.MaxRecord = Convert.ToInt32(ConfigurationManager.AppSettings["MaximumSearchResult"], System.Threading.Thread.CurrentThread.CurrentUICulture);

            FindPatientResult result = patientController.FindPatient(patientSearchCriteria);
            foreach (PatientDetails patientDetails in result.PatientSearchResult)
            {
                if (patientDetails.IsHpf)
                {
                    hpfPatient = patientDetails;
                    break;
                }
            }

            Assert.IsNotNull(patientController.RetrieveHpfEncounters(hpfPatient));
        }

        #endregion

        #endregion
    }
}
