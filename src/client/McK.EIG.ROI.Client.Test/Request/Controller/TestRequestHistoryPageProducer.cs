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
using McK.EIG.Common.Utility.Pagination.Model;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Test.Base.Controller;

namespace McK.EIG.ROI.Client.Test.Request.Controller
{
    /// <summary>
    /// Test case for Request Controller.
    /// </summary>
    [TestFixture]
    [Category("TestRequestHistoryPageProducer")]
    public class TestRequestHistoryPageProducer : TestBase
    {
        #region Fields

        private PatientController patientController;
        
        private RequestHistoryPageProducer pageProducer;

        private FindPatientCriteria patientSearchCriteria;
        private PatientDetails hpfPatient;
        private FindRequestCriteria requestCriteria;

        #endregion Fields

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

        #region Service Method

        public void Initlogon()
        {
            patientController = PatientController.Instance;

            //Retrive Patient
            patientSearchCriteria = new FindPatientCriteria();
            hpfPatient = new PatientDetails();

            patientSearchCriteria.FirstName = string.Empty;
            patientSearchCriteria.LastName = string.Empty;
            patientSearchCriteria.Dob = null;
            patientSearchCriteria.EPN = string.Empty;
            patientSearchCriteria.FacilityCode = TestBase.PatientFacility;
            patientSearchCriteria.MRN = TestBase.PatientMrn;
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
            requestCriteria = new FindRequestCriteria();
            requestCriteria.PatientFirstName = string.Empty;
            requestCriteria.PatientLastName = string.Empty;
            requestCriteria.PatientSsn = string.Empty;
            requestCriteria.Encounter = string.Empty;
            requestCriteria.EPN = string.Empty;
            requestCriteria.RequestorName = string.Empty;

            requestCriteria.Facility = hpfPatient.FacilityCode;
            requestCriteria.IsSearch = false;
            requestCriteria.MRN = hpfPatient.MRN;
        }

        /// <summary>
        ///  Test WorkflowPageProducer
        /// </summary>
        [Test]
        public void Test01RequestHistoryPageProducerConstructor()
        {
            Initlogon();
            pageProducer = new RequestHistoryPageProducer(requestCriteria);
            Assert.IsNotNull(pageProducer);
        }

         /// <summary>
        ///  Test WorkflowPageProducer sort order
        /// </summary>
        [Test]
        public void Test02SortOrder()
        {
            pageProducer = new RequestHistoryPageProducer(requestCriteria);
            pageProducer.SetSortProperties(RequestDetails.RequestorNameKey,ROIConstants.RequestorDomainType, false);
            Assert.IsNotNull(pageProducer);
        }

        [Test]
        public void Test03GetTotalSize()
        {
            FindRequestCriteria requestSearchCriteria = new FindRequestCriteria();
            requestSearchCriteria.Facility = hpfPatient.FacilityCode;
            requestSearchCriteria.IsSearch = false;
            requestSearchCriteria.MRN = hpfPatient.MRN;  

            pageProducer = new RequestHistoryPageProducer(requestSearchCriteria);
            long count = pageProducer.TotalSize;
            Assert.IsTrue(count >= 0);
        }

        [Test]
        public void Test04GetPage()
        {
            pageProducer = new RequestHistoryPageProducer(requestCriteria);
            pageProducer.SetSortProperties(RequestDetails.RequestorNameKey, ROIConstants.RequestorDomainType, false);
            PageContent page = pageProducer.GetPage(0,1);
            Assert.IsInstanceOfType(typeof(PageContent), page);
        }

        [Test]
        public void Test05Release()
        {
            pageProducer = new RequestHistoryPageProducer(requestCriteria);
            pageProducer.Release();
            Assert.IsFalse(pageProducer.IsPaginationInProgress);
        }
        #endregion

        #endregion
    }
}
