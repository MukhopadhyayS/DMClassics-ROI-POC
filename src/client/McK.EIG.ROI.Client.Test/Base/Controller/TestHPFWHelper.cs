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
using System.Configuration;
using System.Globalization;
using System.Security.Cryptography;
using System.Text;

using NUnit.Framework;

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Web_References.ConfigurationWS;
using McK.EIG.ROI.Client.Web_References.HPFPatientWS;

using Microsoft.Web.Services3;

namespace McK.EIG.ROI.Client.Test.Base.Controller
{
    /// <summary>
    ///  Test class for HPFWHelper
    /// </summary>  
    [TestFixture]
    public class TestHPFWHelper : TestBase
    {
        #region Fields

        private ROIController roiControlller;
        private PatientServiceWse hpfPatientService;
        private ConfigurationServiceWse configurationService;
        private int timeOut = 100000;

        #endregion

        #region Setup

        [SetUp]
        public void Init()
        {
            roiControlller = ROIController.Instance;
            hpfPatientService = new PatientServiceWse();
            configurationService = new ConfigurationServiceWse();
            //SetUserData();
        }

        #endregion

        #region TearDown

        [TearDown]
        public void Dispose()
        {
            roiControlller = null;
            hpfPatientService = null;
        }

        #endregion

        #region TestMethods

        /// <summary>
        /// Invoke with Timeout exception
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestInvoke01WithTimeOutException()
        {   
            IWsHelper helper = HelperFactory.GetHelper(HelperFactory.HpfwServiceType);
            ROIController.Timeout = 1;
            object response = helper.Invoke(hpfPatientService, "searchPatients", PrepareHPFPatientParams());
        }

        /// <summary>
        /// Invoke with invalid params
        /// </summary>
        [Test]
        [ExpectedException(typeof(MissingMethodException))]
        public void TestInvoke02WithGeneralException()
        {
            ROIController.Timeout = timeOut;
            IWsHelper helper = HelperFactory.GetHelper(HelperFactory.HpfwServiceType);
            object[] mParams = new object[] {"test"};
            object response = helper.Invoke(hpfPatientService, "searchPatients", PrepareHPFWParams(mParams));
        }

        /// <summary>
        /// Invoke with invalid URL
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestInvoke03WithWebException()
        {
            IWsHelper helper = HelperFactory.GetHelper(HelperFactory.HpfwServiceType);
            hpfPatientService.Url = "http://192.168.0.211:7080/portal/eig/iservices/patientt";
            object response = helper.Invoke(hpfPatientService, "searchPatients", PrepareHPFPatientParams());
            
        }

        /// <summary>
        /// Invoke with invalid host name
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestInvoke04WithInvalidHostName()
        {
            IWsHelper helper = HelperFactory.GetHelper(HelperFactory.HpfwServiceType);
            hpfPatientService.Url = "http://blackhole:7080/portal/eig/iservices/patient";
            object response = helper.Invoke(hpfPatientService, "searchPatients", PrepareHPFPatientParams());
        }

        /// <summary>
        /// test case for invalid user credentails
        /// </summary>
        //[Test]
        //[ExpectedException(typeof(ROIException))]
        //public void TestInvoke05WithInvalidCredentials()
        //{
        //    base.LogOff();
        //    IWsHelper helper = HelperFactory.GetHelper(HelperFactory.HpfwServiceType);
        //    UserData.Instance.UserId = "admin1234";
        //    UserData.Instance.Password = "xyz";
        //    helper.Invoke(hpfPatientService, "searchPatients", PrepareHPFPatientParams());
        //}

        /// <summary>
        /// Invoke the HPFW service method
        /// </summary>
        [Test]
        public void TestInvoke06HPFWService()
        {
            UserData.Instance.Reset();
            base.LogOn();
            IWsHelper helper = HelperFactory.GetHelper(HelperFactory.HpfwServiceType);
            object response = helper.Invoke(hpfPatientService, "searchPatients", PrepareHPFPatientParams());
            Assert.IsNotNull(response);
        }

        /// <summary>
        /// Invoke the HPFW service method
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestInvoke07HPFWServiceWithException()
        {
            IWsHelper helper = HelperFactory.GetHelper(HelperFactory.HpfwServiceType);
            object[] hpfRequestParams = new object[] {string.Empty, 
                                                       null,
                                                       null,
                                                       null,
                                                       null,
                                                       null,
                                                       null,
                                                       null,
                                                       string.Empty};

            object response = helper.Invoke(hpfPatientService, "searchPatients", PrepareHPFWParams(hpfRequestParams));
        }

        /// <summary>
        /// Test case for HPFW Service with soap exception.
        /// </summary>
//      [Test]
//      [ExpectedException(typeof(ROIException))]
        public void TestInvoke08HPFWServiceWithSoapException()
        {
            IWsHelper helper = HelperFactory.GetHelper(HelperFactory.HpfwServiceType);            
            object[] requestParams = new object[] { "admintest123", "adm12345" };
            object response = helper.Invoke(configurationService, "getConfiguration", requestParams);            
        }

        /// <summary>
        /// This test case is purely written for coverage purpose. 
        /// The CreateServiceInputFilter method in the CustomSoapPolicyAssertion class will not be covered by service call. 
        /// </summary>
        [Test]
        public void TestCreateServiceInputFilter()
        {
            CustomSoapPolicyAssertion policy = new CustomSoapPolicyAssertion();
            Assert.IsNull(policy.CreateServiceInputFilter(null));
        }

        /// <summary>
        /// This test case is purely written for coverage purpose. 
        /// The CreateServiceOutputFilter method in the CustomSoapPolicyAssertion class will not be covered by service call. 
        /// </summary>
        [Test]
        public void TestCreateServiceOutputFilter()
        {
            CustomSoapPolicyAssertion policy = new CustomSoapPolicyAssertion();
            Assert.IsNull(policy.CreateServiceOutputFilter(null));
        }

        #endregion 

        private void SetUserData()
        {
            UserData userData = UserData.Instance;
            userData.UserId = ConfigurationManager.AppSettings["UserId"];
            userData.Password = ConfigurationManager.AppSettings["Password"];
        }

        private object[] PrepareHPFPatientParams()
        {
            object[] hpfRequestParams = new object[] {string.Empty, 
                                                      "ROI",
                                                       null,
                                                       ROIConstants.DateFormat,
                                                       string.Empty,
                                                       string.Empty,
                                                       string.Empty,
                                                       "All",
                                                       string.Empty};
            return PrepareHPFWParams(hpfRequestParams);
        }

        /// <summary>
        /// Prepare hpfw parameters with security token & time key
        /// </summary>
        /// <param name="requestParams"></param>
        /// <returns></returns>
        private object[] PrepareHPFWParams(object[] requestParams)
        {
            object[] newParams = new object[4 + requestParams.Length];
            UserData userData = UserData.Instance;
            newParams[0] = userData.UserId;
            newParams[1] = EncryptPassword(userData.Password);

            //TODO Need to have seperate key in app.config file which will hold the HPFW server and port
            //Uri timeServerUrl = userData.ConfigurationData.ContentWebServiceUrl;
            Uri timeServerUrl = new Uri(McK.EIG.ROI.Client.Base.Controller.INIFile.getURLWithINIValues("PORTAL", ConfigurationManager.AppSettings["ContentServiceUrl"]));
            string serverDateTime = HttpTimerRetriever.GetGmtString(HttpTimerRetriever.GetTimeserverUrl(timeServerUrl));
            newParams[2] = serverDateTime;
            newParams[3] = Encrypt(new string[] { userData.UserId, EncryptPassword(userData.Password), serverDateTime, userData.ConfigurationData.PrivateKeyToken });
            Array.Copy(requestParams, 0, newParams, 4, requestParams.Length);
            return newParams;
        }

        /// <summary>
        /// Encrypt Password
        /// </summary>
        /// <param name="password"></param>
        /// <returns></returns>
        private static string EncryptPassword(string password)
        {
            return Encrypt(new string[1] { password });
        }

        /// <summary>
        /// Encrypt Password
        /// </summary>
        /// <param name="dataToEncrypt"></param>
        /// <returns></returns>
        private static string Encrypt(string[] dataToEncrypt)
        {
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < dataToEncrypt.Length; i++)
            {
                if (dataToEncrypt[i] != null)
                {
                    strBuilder.Append(dataToEncrypt[i]);
                }
            }

            MD5 md5 = MD5.Create();

            byte[] hashBytes = md5.ComputeHash(Encoding.Default.GetBytes(strBuilder.ToString()));
            StringBuilder encryptedKey = new StringBuilder();
            foreach (byte hashByte in hashBytes)
            {
                encryptedKey.Append(hashByte.ToString("x2", System.Threading.Thread.CurrentThread.CurrentUICulture));
            }
            return encryptedKey.ToString().ToUpper(System.Threading.Thread.CurrentThread.CurrentUICulture);
        }
    }
}
