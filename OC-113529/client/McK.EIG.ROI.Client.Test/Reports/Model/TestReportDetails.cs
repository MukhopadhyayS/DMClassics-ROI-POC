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
using System.Collections.Generic;

using McK.EIG.ROI.Client.Reports.Model;

//NUnit
using NUnit.Framework;

//McK

namespace McK.EIG.ROI.Client.Test.Reports.Model
{
    /// <summary>
    /// Test class for ReportDetails 
    /// </summary>
    [TestFixture]
    public class TestReportDetails
    {
        private ReportDetails reportDetails;

        [SetUp]
        public void Initialize()
        {
            reportDetails = new ReportDetails();
        }

        [TearDown]
        public void Dispose()
        {
            reportDetails = null;
        }

        #region Test Methods

        /// <summary>
        /// Test case for FilePath.
        /// </summary>
        [Test]
        public void FilePath()
        {
            string filePath= @"c:\";
            reportDetails.FilePath = filePath;
            string outputfilePath = reportDetails.FilePath;
            Assert.AreEqual(filePath, outputfilePath);
        }

        /// <summary>
        /// Test case for Report Type.
        /// </summary>
        [Test]
        public void TestReportType()
        {
            ReportType inputReportType = ReportType.AccountingDisclosure ;
            reportDetails.ReportType = inputReportType;
            ReportType outputReportType = reportDetails.ReportType;
            Assert.AreEqual(inputReportType, outputReportType);
        }
        #endregion
    }
}
