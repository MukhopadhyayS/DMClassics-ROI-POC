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
using System.Configuration;
using System.Collections.ObjectModel;
using System.Globalization;
using System.IO;
using System.Net;
using System.Resources;
using System.Reflection;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Xml;
using System.Web.Services.Protocols;

using NUnit.Framework;

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Test.Base.Exceptions
{
    /// <summary>
    ///  Test class for ROIException
    /// </summary>  
    [TestFixture]
    public class TestROIException
    {
        #region Fields

        private ROIException roiException;

        #endregion

        #region Setup

        [SetUp]
        public void Init()
        {
            roiException = new ROIException();
        }

        #endregion

        #region TearDown

        [TearDown]
        public void Dispose()
        {
            roiException = null;
        }

        #endregion

        #region TestMethods

        /// <summary>
        /// Test case for ROI exception without errorcode
        /// </summary>
        [Test]
        public void TestROIExceptionWithErrorCode()
        {
            roiException = new ROIException(ROIErrorCodes.ArgumentIsNull, new ArgumentNullException());
            Assert.IsInstanceOfType(typeof(Collection<ExceptionData>), roiException.ErrorCodes);
        }

        [Test]
        public void TestROIExceptionWithErrorCodeAndErrorData()
        {
            roiException = new ROIException(ROIErrorCodes.ArgumentIsNull, "Null");
            Assert.IsInstanceOfType(typeof(Collection<ExceptionData>), roiException.ErrorCodes);
        }

        /// <summary>
        /// Test case for web exception with null argument
        /// </summary>
        //[Test]
        [ExpectedException(typeof(ROIException))]
        public void TestWebExceptionWithNull()
        {
            WebException webEx = null;
            roiException = new ROIException(ROIErrorCodes.ConnectFailure, webEx);
        }

        /// <summary>
        /// Test case for get error message 
        /// </summary>
        [Test]
        public void TestGetErrorMessage()
        {

            roiException = new ROIException(ROIErrorCodes.ArgumentIsNull);
            Assembly assembly = Assembly.Load("McK.EIG.ROI.Client");
            string baseName = assembly.GetName().Name;
            ResourceManager rm = new ResourceManager(string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                                   "{0}{1}", baseName, ConfigurationManager.AppSettings["roi.messages.resource"]),
                                                   assembly);
            Collection<ExceptionData> exceptionData = roiException.GetErrorMessage(rm);
            Assert.IsInstanceOfType(typeof(Collection<ExceptionData>), exceptionData);
        }

        /// <summary>
        /// Test case for get error message 
        /// </summary>
        [Test]
        public void TestGetErrorMessageUnKnownException()
        {

            roiException = new ROIException(ROIErrorCodes.Unknown, new NullReferenceException());
            Assembly assembly = Assembly.Load("McK.EIG.ROI.Client");
            string baseName = assembly.GetName().Name;
            ResourceManager rm = new ResourceManager(string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                                   "{0}{1}", baseName, ConfigurationManager.AppSettings["roi.messages.resource"]),
                                                   assembly);
            Collection<ExceptionData> exceptionData = roiException.GetErrorMessage(rm);
            Assert.IsInstanceOfType(typeof(Collection<ExceptionData>), exceptionData);
        }

        /// <summary>
        /// Test case for Web Exception
        /// </summary>
        [Test]
        public void TestGetErrorMessageForWebException()
        {

            WebException webEx = new WebException();
            webEx.Source = "http://225.225.225.225:8080";
            roiException = new ROIException(ROIErrorCodes.ConnectFailure, webEx);
            Assembly assembly = Assembly.Load("McK.EIG.ROI.Client");
            string baseName = assembly.GetName().Name;
            ResourceManager rm = new ResourceManager(string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                                   "{0}{1}", baseName, ConfigurationManager.AppSettings["roi.messages.resource"]),
                                                   assembly);
            Collection<ExceptionData> exceptionData = roiException.GetErrorMessage(rm);
            Assert.IsInstanceOfType(typeof(Collection<ExceptionData>), exceptionData);
        }

        /// <summary>
        /// Test case for get error message with soap exception 
        /// </summary>
        [Test]
        public void TestGetErrorMessageWithSoapException()
        {

            StringBuilder xmldetail = new StringBuilder();
            xmldetail.Append("<detail>");
            xmldetail.Append("<exception>ROIException</exception>");
            xmldetail.Append("<message>PatientWorkList: File Not Found</message>");
            xmldetail.Append("<errorcode>A108</errorcode>");
            xmldetail.Append("<errorcodeext>Patient.doc</errorcodeext>");
            xmldetail.Append("<servername>sys025</servername>");
            xmldetail.Append("</detail>");
            XmlDocument document = new XmlDocument();
            document.LoadXml(xmldetail.ToString());
            XmlNodeList nodeList = document.GetElementsByTagName("detail");
            SoapException soapEx = new SoapException("Test Message",
                                    SoapException.ClientFaultCode,
                                    string.Empty,
                                    nodeList[0]);
            roiException = new ROIException(soapEx);
            Assembly assembly = Assembly.Load("McK.EIG.ROI.Client");
            string baseName = assembly.GetName().Name;
            ResourceManager rm = new ResourceManager(string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                                   "{0}{1}", baseName, ConfigurationManager.AppSettings["roi.messages.resource"]),
                                                   assembly);
            Collection<ExceptionData> exceptionData =  roiException.GetErrorMessage(rm);
            Assert.IsInstanceOfType(typeof(Collection<ExceptionData>), exceptionData);
        }

        /// <summary>
        /// Test case for object serialization 
        /// </summary>
        [Test]
        public void TestGetObjectData()
        {
            roiException = new ROIException(ROIErrorCodes.ArgumentIsNull, new ArgumentNullException());
            
            Stream stream = new MemoryStream();
            
            BinaryFormatter formatter = new BinaryFormatter();
            formatter.Serialize(stream, roiException);
            
            //Reset the stream position
            stream.Position = 0;

            ROIException formattedException = (ROIException)formatter.Deserialize(stream);
        
            Assert.AreEqual(roiException.ErrorCodes[0].ErrorCode, formattedException.ErrorCodes[0].ErrorCode);
        }
        #endregion
    }
}
