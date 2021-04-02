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
using McK.EIG.ROI.Client.Web_References.HPFPatientWS;

namespace McK.EIG.ROI.Client.Test.Patient.Controller
{
    /// <summary>
    /// Test case for PatientController.
    /// </summary>
    [TestFixture]
    [Category("TestPatientController")]
    public class TestPatientController : TestBase
    {
        #region Fields

        private long id;
        private string name;
        private string firstName;
        private string lastName;
        private PatientController patientController;
        private PatientDetails patientDetails;
        private PatientDetails newPatient;
        public PatientDetails hpfPatient;

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

        #region TestMethods

        public void Initlogon()
        {
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);

            patientController = PatientController.Instance;

            name = "Patient" + GetUniqueId();
            lastName = "LastPatient" + GetUniqueId();
            firstName = "FirstPatient" + GetUniqueId();

            patientDetails = new PatientDetails();
            AddressDetails addressDetail = new AddressDetails();

            patientDetails.Id = id;
            patientDetails.FirstName = firstName;
            patientDetails.LastName = lastName;
            patientDetails.FullName = patientDetails.LastName + ", " + patientDetails.FirstName;
            patientDetails.Gender = Gender.Unknown;
            patientDetails.DOB = DateTime.Now.Date;
            patientDetails.IsVip = true;
            patientDetails.SSN = "111-11-1111";
            patientDetails.EPN = (UserData.Instance.EpnEnabled && UserData.Instance.EpnPrefix != null) ? UserData.Instance.EpnPrefix + "1234" : string.Empty;
            patientDetails.FacilityCode = "Test";
            patientDetails.IsFreeformFacility = true;
            patientDetails.MRN = "2222";
            patientDetails.HomePhone = "1111-1111";
            patientDetails.WorkPhone = "2222-2222";

            addressDetail.Address1 = "Address 1";
            addressDetail.Address2 = "Address 2";
            addressDetail.Address3 = "Address 3";
            addressDetail.City = "Test City";
            addressDetail.State = "Test State";
            addressDetail.PostalCode = "11111";

            patientDetails.Address = addressDetail;
            
        }

        /// <summary>
        /// Retrieve a Patient.
        /// </summary>
        [Test]
        public void Test01CreatePatient()
        {
            Initlogon();
            patientController = PatientController.Instance;
            newPatient = new PatientDetails();
            AddressDetails addressDetail = new AddressDetails();

            newPatient.FirstName     = "FirstPatient" + GetUniqueId();
            newPatient.LastName      = "LastPatient" + GetUniqueId();
            newPatient.FullName      = newPatient.LastName + ", " + newPatient.FirstName;
            newPatient.Gender        = Gender.Male;
            newPatient.DOB           = DateTime.Now.Date;
            newPatient.IsVip         = true;
            newPatient.SSN           = "111-11-1111";
            newPatient.EPN           = "1234";
            newPatient.FacilityCode  = "Test";
            newPatient.IsFreeformFacility = true;
            newPatient.MRN           = "2222";
            newPatient.HomePhone     = "1111-1111";
            newPatient.WorkPhone     = "2222-2222";

            addressDetail.Address1 = "Address 1";
            addressDetail.Address2 = "Address 2";
            addressDetail.Address3 = "Address 3";
            addressDetail.City = "Test City";
            addressDetail.State = "Test State";
            addressDetail.PostalCode = "11111";            

            newPatient.Address = addressDetail;
            newPatient.AuditMessage = PatientDetails.PrepareAuditMessageForCreateSupplemental(newPatient);

            id = patientController.CreateSupplementalPatient(newPatient);
            newPatient.Id = id;

            Assert.IsTrue(newPatient.Id > 0);
        }

       
        /// <summary>
        /// Get the patient for the given id
        /// </summary>
        [Test]
        public void Test02RetrievePatient()
        {
       
            PatientDetails retrievedPatientInfo = patientController.RetrieveSupplementalPatient(id, false);

            Assert.AreEqual(newPatient.Id, retrievedPatientInfo.Id);
            Assert.AreEqual(newPatient.Gender, retrievedPatientInfo.Gender);

            Assert.AreEqual(newPatient.DOB.HasValue, retrievedPatientInfo.DOB.HasValue);
            if (retrievedPatientInfo.DOB.HasValue) 
                Assert.AreEqual(newPatient.DOB.Value.Date, retrievedPatientInfo.DOB.Value.Date);

            Assert.AreEqual(newPatient.IsVip, retrievedPatientInfo.IsVip);
            Assert.AreEqual(newPatient.SSN, retrievedPatientInfo.SSN);

            if (UserData.Instance.EpnEnabled)
            {
                Assert.AreEqual(newPatient.EPN, retrievedPatientInfo.EPN);
            }

            Assert.AreEqual(newPatient.FacilityCode, retrievedPatientInfo.FacilityCode);
            Assert.AreEqual(newPatient.MRN, retrievedPatientInfo.MRN);
            Assert.AreEqual(newPatient.HomePhone, retrievedPatientInfo.HomePhone);
            Assert.AreEqual(newPatient.WorkPhone, retrievedPatientInfo.WorkPhone);
            Assert.AreEqual(newPatient.Address.Address1, retrievedPatientInfo.Address.Address1);
            
        }

        /// <summary>
        /// Check for a duplicate name.
        /// </summary>
        [Test]
        public void Test03CheckDuplicateName()
        {
            patientDetails.Id = id;
            patientDetails.FullName = lastName + ", " + firstName;

            patientController.CheckDuplicateName(patientDetails);
            Assert.IsTrue(true);
        }

        /// <summary>
        /// Update a Patient.
        /// </summary>
        [Test]
        public void Test04UpdatePatient()
        {
            PatientDetails patientInfo = new PatientDetails();
            AddressDetails addressDetail = new AddressDetails();

            patientInfo.Id            = id;
            patientInfo.FirstName     = firstName;
            patientInfo.LastName      = lastName;
            patientInfo.FullName      = lastName + ", " + firstName;
            patientInfo.Gender        = Gender.Female;
            patientInfo.DOB           = DateTime.Now.Date; 
            patientInfo.IsVip         = false;
            patientInfo.SSN           = "111-11-1111";
            patientInfo.EPN           = (UserData.Instance.EpnEnabled && UserData.Instance.EpnPrefix != null) ? UserData.Instance.EpnPrefix + "4589" : string.Empty;
            patientInfo.FacilityCode  = "World Vision";
            patientInfo.IsFreeformFacility = true;
            patientInfo.MRN           = "2222";
            patientInfo.HomePhone     = "1111-1111";
            patientInfo.WorkPhone     = "2222-2222";

            addressDetail.Address1   = "Address 11";
            addressDetail.Address2   = "Address 21";
            addressDetail.Address3   = "Address 31";
            addressDetail.City       = "Test City1";
            addressDetail.State      = "Test State1";
            addressDetail.PostalCode = "11111";

            patientInfo.Address = addressDetail;

            patientInfo.AuditMessage = PatientDetails.PrepareAuditMessageForUpdateSupplemental(patientInfo, newPatient);
            patientController.UpdateSupplementalPatient(patientInfo);

            Assert.IsTrue(patientInfo.Id > 0);
        }

        /// <summary>
        /// Test case for Find Patient with 
        /// First Name = "Pat"
        /// Facility = "A"
        /// MaxRecord = Set in App.config file.
        /// </summary>
        [Test]
        public void Test05FindPatientwithSpecfiedFacility()
        {
            UserSecurities userSecurity = UserData.Instance.Security[ROISecurityRights.DefaultFacility];
            userSecurity.Rights[ROISecurityRights.ROIVipStatus] = false;

            FindPatientCriteria patientInfo = new FindPatientCriteria();

            patientInfo.FirstName = newPatient.FullName;
            patientInfo.LastName = newPatient.FullName;
            patientInfo.Dob = newPatient.DOB;
            patientInfo.EPN = newPatient.EPN;
            patientInfo.FacilityCode = newPatient.FacilityCode;
            patientInfo.MRN = newPatient.MRN;
            patientInfo.SSN = newPatient.SSN;
            patientInfo.Encounter = string.Empty;
            patientInfo.MaxRecord = Convert.ToInt32(ConfigurationManager.AppSettings["MaximumSearchResult"], System.Threading.Thread.CurrentThread.CurrentUICulture);
            FindPatientResult result = patientController.FindPatient(patientInfo);
            userSecurity.Rights[ROISecurityRights.ROIVipStatus] = true;
            
            Assert.IsInstanceOfType(typeof(FindPatientResult), result);
        }


        /// <summary>
        /// Test case for Get Patient with non existing id.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test06RetrievePatientWithNonExistingId()
        {
            long patientId = 0;
            PatientDetails patientInfo = patientController.RetrieveSupplementalPatient(patientId, false);
        }

        /// <summary>
        /// Test case for create Patient with empty name.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test07CreatePatientWithEmptyName()
        {
            PatientDetails patientInfo = new PatientDetails();
            AddressDetails addressDetail = new AddressDetails();

            patientInfo.FullName      = "";
            patientInfo.Gender        = Gender.Unknown;
            patientInfo.DOB           = DateTime.Now; 
            patientInfo.IsVip         = true;
            patientInfo.SSN           = "111-11-1111";
            patientInfo.EPN           = (UserData.Instance.EpnEnabled && UserData.Instance.EpnPrefix != null) ? UserData.Instance.EpnPrefix + "1234" : string.Empty;
            patientInfo.FacilityCode  = "Test";
            patientInfo.IsFreeformFacility = true;
            patientInfo.MRN           = "2222";
            patientInfo.HomePhone     = "1111-1111";
            patientInfo.WorkPhone     = "2222-2222";

            addressDetail.Address1   = "Address 1";
            addressDetail.Address2   = "Address 2";
            addressDetail.Address3   = "Address 3";
            addressDetail.City       = "Test City";
            addressDetail.State      = "Test State";
            addressDetail.PostalCode = "111111";            

            patientInfo.Address = addressDetail;

            patientController.CreateSupplementalPatient(patientInfo);
        }

        /// <summary>
        /// Test case for update Patient with empty name.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test08UpdatePatientWithEmptyName()
        {
            PatientDetails patientInfo = new PatientDetails();
            AddressDetails addressDetail = new AddressDetails();

            patientInfo.Id            = id;
            patientInfo.FullName      = "";
            patientInfo.Gender        = Gender.None;
            patientInfo.DOB           = DateTime.Now; 
            patientInfo.IsVip         = false;
            patientInfo.SSN           = "111-11-1111";
            patientInfo.EPN           = "1234";
            patientInfo.FacilityCode  = "Test";
            patientInfo.IsFreeformFacility = true;
            patientInfo.MRN           = "2222";
            patientInfo.HomePhone     = "1111-1111";
            patientInfo.WorkPhone     = "2222-2222";

            addressDetail.Address1   = "Address 11";
            addressDetail.Address2   = "Address 21";
            addressDetail.Address3   = "Address 31";
            addressDetail.City       = "Test City1";
            addressDetail.State      = "Test State1";
            addressDetail.PostalCode = "11111";

            patientInfo.Address = addressDetail;

            patientController.UpdateSupplementalPatient(patientInfo);

        }        

        /// <summary>
        /// Test case for delete patient with non existing id
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test09DeleteNonExistingPatient()
        {
            PatientDetails patient = new PatientDetails();
            patient.FullName = "Test Patient";
            patient.Id = 0;
            patientController.DeleteSupplementalPatient(patient.Id);
        }

        /// <summary>
        /// Test case for Find Patient with 
        /// First Name = "Pat"
        /// Facility = "All Facility"
        /// MaxRecord = 0
        /// </summary>
        [Test]
        public void Test10FindPatientwithAllFacility()
        {
            FindPatientCriteria patientInfo = new FindPatientCriteria();

            patientInfo.FirstName = "Pat";
            patientInfo.LastName = string.Empty;
            patientInfo.Dob = null;
            patientInfo.EPN = string.Empty;
            patientInfo.FacilityCode = null;
            patientInfo.MRN = string.Empty;
            patientInfo.SSN = string.Empty;
            patientInfo.Encounter = string.Empty;
            patientInfo.MaxRecord = 0;
            FindPatientResult result = patientController.FindPatient(patientInfo);
            Assert.IsInstanceOfType(typeof(FindPatientResult), result);
        }

        
        /// <summary>
        /// Delete a Patient
        /// </summary>
        [Test]
        public void Test11DeletePatient()
        {
            patientController.DeleteSupplementalPatient(newPatient.Id);
        }

        /// <summary>
        /// Test case for Find Patient.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test12FindPatientwithInvalidInput()
        {
            FindPatientCriteria patientInfo = new FindPatientCriteria();
            patientInfo.Encounter = "Enc@$";
            patientInfo.FacilityCode = string.Empty;
            patientInfo.FirstName = string.Empty;
            patientInfo.LastName = string.Empty;
            patientInfo.MRN = string.Empty;
            patientInfo.SSN = "1-23-4";
            patientInfo.EPN = "12";
            FindPatientResult result = patientController.FindPatient(patientInfo);
        }

        /// <summary>
        /// Check for a duplicate name.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test13CheckDuplicateNameWithValidation()
        {
            patientDetails.Id = id;
            string patientLastname = lastName;
            string patientFirstName = firstName;
            while (patientLastname.Length <= 256)
            {
                patientLastname += patientLastname;
            }
            while (patientFirstName.Length <= 256)
            {
                patientFirstName += patientFirstName;
            }            
            patientDetails.LastName = patientLastname;
            patientDetails.FirstName = patientFirstName;
            patientController.CheckDuplicateName(patientDetails);            
        }

        /// <summary>
        /// Create Patient with Gender = Unknown
        /// </summary>
        [Test]        
        public void Test14CreatePatientWithGenderUnknown()
        {
            patientDetails.LastName = "TestLastPatient" + GetUniqueId();
            patientDetails.FirstName = "TestFirstPatient" + GetUniqueId();
            patientDetails.FullName = patientDetails.LastName + ", " + patientDetails.FirstName;
            patientDetails.Id = patientController.CreateSupplementalPatient(patientDetails);
            PatientDetails details = patientController.RetrieveSupplementalPatient(patientDetails.Id, false);
            Assert.IsInstanceOfType(typeof(PatientDetails), details);
            patientController.DeleteSupplementalPatient(patientDetails.Id);
        }

        /// <summary>
        /// Create Patient with Gender = None
        /// </summary>
        [Test]
        public void Test15CreatePatientWithGenderNone()
        {
            patientDetails.LastName = "TestLastPatient" + GetUniqueId();
            patientDetails.FirstName = "TestFirstPatient" + GetUniqueId();
            patientDetails.FullName = patientDetails.LastName + ", " + patientDetails.FirstName;
            patientDetails.Gender = Gender.None;
            patientDetails.Id = patientController.CreateSupplementalPatient(patientDetails);
            PatientDetails details = patientController.RetrieveSupplementalPatient(patientDetails.Id, false);
            Assert.IsInstanceOfType(typeof(PatientDetails), details);
            patientController.DeleteSupplementalPatient(patientDetails.Id);
        }

        /// <summary>
        /// Retrieve HPF Patient with name
        /// </summary>
        [Test]
        public void Test16RetrieveHPFPatientWithName()
        {
            FindPatientCriteria patientSearchCriteria = new FindPatientCriteria();

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
            PatientDetails retrievedHPFPatientInfo = patientController.RetrieveHpfPatient(hpfPatient.MRN, hpfPatient.FacilityCode);
            Assert.AreEqual(hpfPatient.MRN, retrievedHPFPatientInfo.MRN);
            Assert.AreEqual(hpfPatient.FacilityCode, retrievedHPFPatientInfo.FacilityCode);
        }
        /// <summary>
        /// Retrieve HPF Patient with name
        /// </summary>
        [Test]
        public void Test17RetrieveHPFPatientWithMRN()
        {
            FindPatientCriteria patientSearchCriteria = new FindPatientCriteria();

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
            PatientDetails retrievedHPFPatientInfo = patientController.RetrieveHpfPatient(hpfPatient.MRN, hpfPatient.FacilityCode);
            Assert.AreEqual(hpfPatient.MRN, retrievedHPFPatientInfo.MRN);
            Assert.AreEqual(hpfPatient.FacilityCode, retrievedHPFPatientInfo.FacilityCode);
        }

        /// <summary>
        /// Retrieve HPF Patient with name
        /// </summary>
        [Test]
        public void Test18RetrieveHPFPatientWithFacility()
        {
            FindPatientCriteria patientSearchCriteria = new FindPatientCriteria();

            patientSearchCriteria.FirstName = string.Empty;
            patientSearchCriteria.LastName = string.Empty;
            patientSearchCriteria.Dob = null;
            patientSearchCriteria.EPN = string.Empty;
            patientSearchCriteria.FacilityCode = TestBase.PatientFacility;
            patientSearchCriteria.MRN = TestBase.PatientMrn ;
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
            PatientDetails retrievedHPFPatientInfo = patientController.RetrieveHpfPatient(hpfPatient.MRN, hpfPatient.FacilityCode);
            Assert.AreEqual(hpfPatient.MRN, retrievedHPFPatientInfo.MRN);
            Assert.AreEqual(hpfPatient.FacilityCode, retrievedHPFPatientInfo.FacilityCode);
        }

        /// <summary>
        /// Test case for Find Patient with 
        /// First Name = "Pat"
        /// Facility = "All Facility"
        /// MaxRecord = 0
        /// </summary>
        [Test]
        public void Test19FindPatientwithDOB()
        {
            FindPatientCriteria patientInfo = new FindPatientCriteria();

            patientInfo.FirstName = "Pat";
            patientInfo.LastName = string.Empty;
            patientInfo.Dob = new DateTime(1995, 04, 26);
            patientInfo.EPN = string.Empty;
            patientInfo.FacilityCode = null;
            patientInfo.MRN = string.Empty;
            patientInfo.SSN = string.Empty;
            patientInfo.Encounter = string.Empty;
            patientInfo.MaxRecord = 0;
            FindPatientResult result = patientController.FindPatient(patientInfo);            
        }
        
        /// <summary>
        /// Patient Details.
        /// </summary>
        [Test]
        public void Test20PatientDetails()
        {
            facilityPatientDetail server = new facilityPatientDetail();
            AddressDetails addressDetail = new AddressDetails();            
            server.fullName = "Jhon";
            server.gender = "U";
            server.dob = "05/05/1983";
            server.vip = true;
            server.ssn = "111-11-1111";
            server.epn = "EPN";
            server.facility = "Test";
            server.mrn = "2222";
            server.homephone = "1111-1111";
            server.workphone = "2222-2222";

            server.address1 = "Address 1";
            server.address2 = "Address 2";
            server.address3 = "Address 3";
            server.city = "Test City";
            server.state = "Test State";
            server.zip = "11111";
            Assert.IsInstanceOfType(typeof(PatientDetails), PatientController.MapModel(server));

            facilityPatient facilityPatient = new facilityPatient();
            facilityPatient.fullName = "Jhon";
            facilityPatient.gender = "U";
            facilityPatient.dob = "05/05/1983";
            facilityPatient.vip = true;
            facilityPatient.ssn = "111-11-1111";
            facilityPatient.epn = "EPN";
            facilityPatient.facility = "Test";
            facilityPatient.mrn = "2222";
            FindPatientResult result = new FindPatientResult();
            PatientController.MapModel(new facilityPatient[] { facilityPatient }, result);
            Assert.IsTrue(result.PatientSearchResult.Count > 0);
        }

        /// <summary>
        /// Patient Details.
        /// </summary>
        [Test]
        public void Test21PatientDetailsWithEPN()
        {
            bool isEpnEnabled = UserData.Instance.EpnEnabled;
            
            FindPatientResult result = new FindPatientResult();
            patientList hpfSearchResult = new patientList();

            facilityPatient[] facilityPatienlist = new facilityPatient[1];
            facilityPatient facilitypatient = new facilityPatient();
            facilitypatient.dob = "05/05/1983";
            facilitypatient.encounterLocked = true;
            facilitypatient.epn = "EPN";
            facilitypatient.facility = "A";
            facilitypatient.fullName = "Smith";
            facilitypatient.mrn = "MRN";
            facilitypatient.patientLocked = true;
            facilitypatient.ssn = "SSN";
            facilitypatient.vip = true;
            facilityPatienlist[0] = facilitypatient;

            UserData.Instance.EpnEnabled = true;
            if (UserData.Instance.EpnEnabled)
            {
                UserData.Instance.EpnEnabled = false;
                hpfSearchResult.facilityPatients = facilityPatienlist;                
                PatientController.MapModel(hpfSearchResult, result);
            }

            if (!UserData.Instance.EpnEnabled)
            {
                UserData.Instance.EpnEnabled = true;
                enterprisePatient[] patientlist = new enterprisePatient[1];
                enterprisePatient patient = new enterprisePatient();
                patient.facilityPatients = facilityPatienlist;
                patientlist[0] = patient;
                hpfSearchResult.enterprisePatients = patientlist;
                PatientController.MapModel(hpfSearchResult, result);
            }
            
            UserData.Instance.EpnEnabled = isEpnEnabled;
        }
        /// <summary>
        /// Patient Details.
        /// </summary>
        [Test]
        public void Test22PatientDetailsWithEPN()
        {
            bool isEpnEnabled = UserData.Instance.EpnEnabled;
            FindPatientResult result = new FindPatientResult();
            patientList hpfSearchResult = new patientList();

            UserData.Instance.EpnEnabled = true;
            if (UserData.Instance.EpnEnabled)
            {
                hpfSearchResult.enterprisePatients = null;
                PatientController.MapModel(hpfSearchResult, result);
                UserData.Instance.EpnEnabled = false;
            }

            if (!UserData.Instance.EpnEnabled)
            {                
                hpfSearchResult.facilityPatients = null;
                PatientController.MapModel(hpfSearchResult, result);
            }
            UserData.Instance.EpnEnabled = isEpnEnabled;
        }
       
        /// <summary>
        /// Retrieve HPF Patient with name
        /// </summary>
        [Test]
        public void Test23RetrieveHPFPatientWithAllFacility()
        {
            FindPatientCriteria patientSearchCriteria = new FindPatientCriteria();

            patientSearchCriteria.FirstName = string.Empty;
            patientSearchCriteria.LastName = "Last";
            patientSearchCriteria.Dob = null;
            patientSearchCriteria.EPN = string.Empty;
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
            PatientDetails retrievedHPFPatientInfo = patientController.RetrieveHpfPatient(hpfPatient.MRN, hpfPatient.FacilityCode);
            Assert.AreEqual(hpfPatient.MRN, retrievedHPFPatientInfo.MRN);
            Assert.AreEqual(hpfPatient.FacilityCode, retrievedHPFPatientInfo.FacilityCode);
        }

        /// <summary>
        /// Test case for Find Patient with invalid input.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test24FindPatientwithInvalidInput()
        {
            FindPatientCriteria patientSearchInfo = new FindPatientCriteria();
            FindPatientResult result = patientController.FindPatient(patientSearchInfo);
        }

        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test25ValidationData()
        {
            PatientDetails patientInfo = new PatientDetails();
            AddressDetails addressDetail = new AddressDetails();

            patientInfo.Id = id;
            patientInfo.LastName = "lname";
            patientInfo.FirstName = "fname";
            patientInfo.FullName = patientInfo.LastName + ", " + patientInfo.FirstName;
            patientInfo.Gender = Gender.None;
            patientInfo.DOB = DateTime.Now;
            patientInfo.IsVip = false;
            patientInfo.SSN = "111-11-1111";
            patientInfo.EPN = "1234";
            patientInfo.FacilityCode = "Test";
            patientInfo.MRN = "2222";
            patientInfo.HomePhone = "aaaa";
            patientInfo.WorkPhone = "bbbb";

            addressDetail.Address1 = "Address 11&^";
            addressDetail.Address2 = "Address 21&&";
            addressDetail.Address3 = "Address 31";
            addressDetail.City = "1333^&&*";
            addressDetail.State = "1555";
            addressDetail.PostalCode = "aaaa";            

            patientInfo.Address = addressDetail;

            patientController.UpdateSupplementalPatient(patientInfo);

        }

        /// <summary>
        /// Retrieve a Patient.
        /// </summary>
        [Test]
        public void Test26CreateNonVipPatient()
        {
            patientController = PatientController.Instance;
            newPatient = new PatientDetails();
            AddressDetails addressDetail = new AddressDetails();

            newPatient.LastName = "LastPatient" + GetUniqueId();
            newPatient.FirstName = "FirstPatient" + GetUniqueId();
            newPatient.FullName = newPatient.LastName + ", " + newPatient.FirstName;
            newPatient.Gender = Gender.Male;
            newPatient.DOB = DateTime.Now.Date;
            newPatient.IsVip = false;
            newPatient.SSN = "111-11-1111";
            newPatient.EPN = "1234";
            newPatient.FacilityCode = "Test";
            newPatient.IsFreeformFacility = true;
            newPatient.MRN = "2222";
            newPatient.HomePhone = "1111-1111";
            newPatient.WorkPhone = "2222-2222";

            addressDetail.Address1 = "Address 1";
            addressDetail.Address2 = "Address 2";
            addressDetail.Address3 = "Address 3";
            addressDetail.City = "Test City";
            addressDetail.State = "Test State";
            addressDetail.PostalCode = "11111";

            newPatient.Address = addressDetail;
            newPatient.AuditMessage = PatientDetails.PrepareAuditMessageForCreateSupplemental(newPatient);

            id = patientController.CreateSupplementalPatient(newPatient);
            newPatient.Id = id;

            Assert.IsTrue(newPatient.Id > 0);
        }


        private string GetUniqueId()
        {
            return DateTime.Now.TimeOfDay.TotalMilliseconds.ToString().Replace('.',' ');
        }

        public PatientDetails PatientDetails
        {
            get
            {
                return newPatient;
            }
        }

        #endregion

        #endregion
    }
}