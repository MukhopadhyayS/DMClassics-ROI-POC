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

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Model;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Test.Requestors.Model
{
    /// <summary>
    /// Test class for RequestorDetails
    /// </summary>
    [TestFixture]
    public class TestRequestorStatementDetail
    {
        //Holds the object of model class.
        private RequestorStatementDetail requestorStatementDetail;

        [SetUp]
        public void Initialize()
        {
            requestorStatementDetail = new RequestorStatementDetail();
        }

        [TearDown]
        public void Dispose()
        {
            requestorStatementDetail = null;
        }

        #region TestMethods

        /// <summary>
        /// Holds the requestor id
        /// </summary>
        [Test]
        public void RequestorId()
        {
            long inputRequestorId = 100;
            requestorStatementDetail.RequestorId = inputRequestorId;
            long outputRequestorId = requestorStatementDetail.RequestorId;
            Assert.AreEqual(inputRequestorId, outputRequestorId);
        }

        /// <summary>
        /// Holds the Template File Id
        /// </summary>
        [Test]
        public void TemplateFileId()
        {
            long inputTemplateFileId = 100;
            requestorStatementDetail.TemplateFileId = inputTemplateFileId;
            long outputTemplateFileId = requestorStatementDetail.TemplateFileId;
            Assert.AreEqual(inputTemplateFileId, outputTemplateFileId);
        }

        /// <summary>
        /// Holds the Template Name
        /// </summary>
        [Test]
        public void TemplateName()
        {
            string inputTemplateName = "Requestor Letter Template";
            requestorStatementDetail.TemplateName = inputTemplateName;
            string outputTemplateName = requestorStatementDetail.TemplateName;
            Assert.AreEqual(inputTemplateName, outputTemplateName);
        }

        /// <summary>
        /// Holds the Output Method
        /// </summary>
        [Test]
        public void OutputMethod()
        {
            string inputOutputMethod = "Save as file";
            requestorStatementDetail.OutputMethod = inputOutputMethod;
            string outputOutputMethod = requestorStatementDetail.OutputMethod;
            Assert.AreEqual(inputOutputMethod, outputOutputMethod);
        }

        /// <summary>
        /// Holds the Queue Password
        /// </summary>
        [Test]
        public void QueuePassword()
        {
            string inputQueuePassword = "1234";
            requestorStatementDetail.QueuePassword = inputQueuePassword;
            string outputQueuePassword = requestorStatementDetail.QueuePassword;
            Assert.AreEqual(inputQueuePassword, outputQueuePassword);
        }

        /// <summary>
        /// Holds the DateRange
        /// </summary>
        [Test]
        public void DateRange()
        {
            DateRange inputDateRange = McK.EIG.ROI.Client.Requestors.Model.DateRange.DAYS_30;
            requestorStatementDetail.DateRange = inputDateRange;
            DateRange outputDateRange = requestorStatementDetail.DateRange;
            Assert.AreEqual(inputDateRange, outputDateRange);

        }

        /// <summary>
        /// Holds the notes
        /// </summary>
        [Test]
        public void Notes()
        {
            string[] inputNotes = new string[] { "Notes1", "Notes2" };
            requestorStatementDetail.Notes = inputNotes;
            string[] outputNotes = requestorStatementDetail.Notes;
            Assert.AreEqual(inputNotes, outputNotes);
        }

        /// <summary>
        /// Holds the past invoice Id's
        /// </summary>
        [Test]
        public void PastInvIds()
        {
            long[] inputPastInvIds = new long[] { 1, 2 };
            requestorStatementDetail.PastInvIds = inputPastInvIds;
            long[] outputPastInvIds = requestorStatementDetail.PastInvIds;
            Assert.AreEqual(inputPastInvIds, outputPastInvIds);
        }
        #endregion

    }
}
