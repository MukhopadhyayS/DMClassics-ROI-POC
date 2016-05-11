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

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Admin.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    ///  Test class for InvoiceDueDetails.
    /// </summary>
    [TestFixture]
    public class TestInvoiceDueDetails
    {
        private InvoiceDueDetails invoicedue;

        #region Methods
        [SetUp]
        public void Initialize()
        {
            invoicedue = new InvoiceDueDetails();
        }

        [TearDown]
        public void Dispose()
        {
            invoicedue = null;
        }
        #region TestMethods

        [Test]
        public void TestInvoiceDueId()
        {
            int daysid = 1;
            invoicedue.Id = daysid;
            long outputduedaysvalue = invoicedue.Id;
            Assert.AreEqual(daysid, outputduedaysvalue);
        }
        [Test]
        public void TestInvoiceDueDays()
        {
            int inputduedaysvalue = 10;
            invoicedue.DueDateInDays = inputduedaysvalue;
            long outputduedaysvalue = invoicedue.DueDateInDays;
            Assert.AreEqual(inputduedaysvalue, outputduedaysvalue);
        }
        [Test]
        public void TestInvoiceDueRecordVersion()
        {
            int version = 50;
            invoicedue.RecordVersionId = version;
            long outputduedaysvalue = invoicedue.RecordVersionId;
            Assert.AreEqual(version, outputduedaysvalue);
        }
        #endregion

        #endregion



    }
}
