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
using System.ComponentModel;
using System.Drawing;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Configuration;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Test.Base.Controller;

namespace McK.EIG.ROI.Client.Test.Patient.Model
{
    /// <summary>
    /// Test class for MismatchComparer
    /// </summary>
    [TestFixture]
    public class TestMismatchComparer : TestBase
    {
        #region Fields
        
        private PatientDetails patientDetail;
        
        #endregion

        #region Nunit

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>
        [SetUp]
        public void Initialize()
        {
            patientDetail = new PatientDetails();
            UserData.Instance.UserId = ConfigurationManager.AppSettings["UserId"];
            UserData.Instance.Password = ConfigurationManager.AppSettings["Password"];
            UserData.Instance.ConfigurationData = ConfigurationData.Instance;
            ROIController.Instance.GetConfiguration();
        }

        /// <summary>
        /// Dispose PatientDetail.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            patientDetail = null;
        }

        #endregion

        #region TestMethods

        /// <summary>
        /// Test Case for Mismatch comparer.
        /// </summary>
        [Test]
        public void TestComparer01()
        {
            PatientDetails objectA = new PatientDetails();
            objectA.FullName = "john";
            objectA.DOB = DateTime.Now.Date;
            objectA.MRN = "123455";
            objectA.EPN = "3434";
            objectA.SSN = "2";

            PatientDetails objectB = new PatientDetails();

            objectB.FullName = "Smith";
            objectB.DOB = DateTime.Now.Date;
            objectB.MRN = "121231";
            objectB.EPN = "3434";
            objectB.SSN = "3";

            bool isEpnEnabled = UserData.Instance.EpnEnabled;

            if (!isEpnEnabled)
            {
                UserData.Instance.EpnEnabled = true;
            }

            Assert.AreEqual(2, PatientDetails.FieldMatchComparer.Compare(objectA, objectB));

            UserData.Instance.EpnEnabled = isEpnEnabled;
        }


        /// <summary>
        /// Test Case for Mismatch comparer.
        /// </summary>
        [Test]
        public void TestComparer02()
        {
            PatientDetails objectA = new PatientDetails();
            objectA.FullName = "john";
            objectA.DOB = DateTime.Now.Date;
            objectA.MRN = "123455";
            objectA.EPN = "76890";
            objectA.SSN = "2";

            PatientDetails objectB = new PatientDetails();
            objectB.FullName = "john";
            objectB.DOB = DateTime.Now.Date;
            objectB.MRN = "123455";
            objectB.EPN = "76890";
            objectB.SSN = "2";

            bool isEpnEnabled = UserData.Instance.EpnEnabled;

            if(isEpnEnabled)
            {
                UserData.Instance.EpnEnabled = false;
            }

            Assert.AreEqual(4,PatientDetails.FieldMatchComparer.Compare(objectA, objectB));            

            UserData.Instance.EpnEnabled = isEpnEnabled;
        }

        #endregion
    }
}
