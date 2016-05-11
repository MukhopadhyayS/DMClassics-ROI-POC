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

#region NameSpace
using System;

using NUnit.Framework;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;

#endregion Namespace

namespace McK.EIG.ROI.Client.Test.Base.Exceptions
{
    /// <summary>
    /// Test class for ExceptionData
    /// </summary>
    [TestFixture]
    public class TestExceptionData : TestBase
    {
        private ExceptionData exceptionData;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>

        [TestFixtureSetUp]
        public void Initialize()
        {
            exceptionData = new ExceptionData();
        }

        /// <summary>
        /// Dispose FeeTypeDetails.
        /// </summary>
        [TestFixtureTearDown]
        public void Dispose()
        {
            base.LogOff();
            exceptionData = null;
        }

        /// <summary>
        /// Test method for errorcode
        /// </summary>
        [Test]
        public void TestErrorCode()
        {
            string errorCode = ROIErrorCodes.ArgumentIsNull;
            exceptionData.ErrorCode = errorCode;
            Assert.AreEqual(errorCode, exceptionData.ErrorCode);
        }


        /// <summary>
        /// Test method for errordata
        /// </summary>
        [Test]
        public void TestErrorData()
        {
            string errroData = "Argument is null";
            exceptionData.ErrorData = errroData;
            Assert.AreEqual(errroData, exceptionData.ErrorData);
        }

        /// <summary>
        /// Test method for error message
        /// </summary>
        [Test]
        public void TestErrorMessage()
        {
            string errroMessage = "Argument is null";
            exceptionData.ErrorMessage = errroMessage;
            Assert.AreEqual(errroMessage, exceptionData.ErrorMessage);
        }


        /// <summary>
        /// Test method for initializing ExceptionData with error code
        /// </summary>
        [Test]
        public void TestExceptionDataWithErrorCode()
        {
            exceptionData = new ExceptionData(ROIErrorCodes.ArgumentIsNull, "Argument is null");
            Assert.IsInstanceOfType(typeof(ExceptionData), exceptionData);
        }
    }
}
