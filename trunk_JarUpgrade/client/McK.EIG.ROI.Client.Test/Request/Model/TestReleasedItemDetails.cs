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

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for Release History Item Details 
    /// </summary>
    [TestFixture]
    public class TestReleasedItemDetails
    {
        #region Fields

        private ReleasedItemDetails releaseItemDetails;

        #endregion

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            releaseItemDetails = new ReleasedItemDetails();
        }

        [TearDown]
        public void Dispose()
        {
            releaseItemDetails = null;
        }

        #endregion

        #region Methods

        /// <summary>
        /// Tests the released date.
        /// </summary>
        [Test]
        public void TestReleasedDate()
        {
            DateTime inputDateTime = DateTime.Now;
            releaseItemDetails.ReleasedDate = inputDateTime;
            Nullable<DateTime> outputDateTime = releaseItemDetails.ReleasedDate;
            Assert.AreEqual(inputDateTime, outputDateTime.Value);
        }

        /// <summary>
        /// Test method for ReleaseDate with null.
        /// </summary>
        [Test]
        public void TestReleaseDateWithNull()
        {
            releaseItemDetails.ReleasedDate = null;
            Assert.IsNull(releaseItemDetails.ReleasedDate);
        }

        /// <summary>
        /// Tests the Encounter.
        /// </summary>
        [Test]
        public void TestEncounter()
        {
            string inputEncounter = "ENC7889";
            releaseItemDetails.Encounter = inputEncounter;
            string outputEncounter = releaseItemDetails.Encounter;
            Assert.AreEqual(inputEncounter, outputEncounter);
        }

        /// <summary>
        /// Tests the Document Information.
        /// </summary>
        [Test]
        public void TestDocumentVersionSubtitle()
        {
            string inputDocDetail = "ABC - DEF - LMN";
            releaseItemDetails.DocumentVersionSubtitle = inputDocDetail;
            string outputDocDetail = releaseItemDetails.DocumentVersionSubtitle;
            Assert.AreEqual(inputDocDetail, outputDocDetail);
        }

        /// <summary>
        /// Tests the document pages released.
        /// </summary>
        [Test]
        public void TestPages()
        {
            string inputPages = "1-56, 34-67";
            releaseItemDetails.Pages = inputPages;
            string outputPages = releaseItemDetails.Pages;
            Assert.AreEqual(inputPages, outputPages);
        }

        /// <summary>
        /// Tests the shipping address.
        /// </summary>
        [Test]
        public void TestShippingAddress()
        {
            string inputAddress = "ABC, New York, GA";
            releaseItemDetails.ShippingAddress = inputAddress;
            string outputAddress = releaseItemDetails.ShippingAddress;
            Assert.AreEqual(inputAddress, outputAddress);
        }

        /// <summary>
        /// Tests the shipping method.
        /// </summary>
        [Test]
        public void TestShippingMethod()
        {
            string inputMethod = "ABC, New York, GA";
            releaseItemDetails.ShippingMethod = inputMethod;
            string outputMethod = releaseItemDetails.ShippingMethod;
            Assert.AreEqual(inputMethod, outputMethod);
        }

        /// <summary>
        /// Tests the traking number.
        /// </summary>
        [Test]
        public void TestTrackingNumber()
        {
            string inputTrackNo = "ABC, New York, GA";
            releaseItemDetails.TrackingNumber = inputTrackNo;
            string outputTrackNo = releaseItemDetails.TrackingNumber;
            Assert.AreEqual(inputTrackNo, outputTrackNo);
        }

        /// <summary>
        /// Tests the user id.
        /// </summary>
        [Test]
        public void TestUserId()
        {
            long inputId = 121;
            releaseItemDetails.UserId = inputId;
            long outputId = releaseItemDetails.UserId;
            Assert.AreEqual(inputId, outputId);
        }

        /// <summary>
        /// Tests the user name.
        /// </summary>
        [Test]
        public void TestUserName()
        {
            string inputUserName = "John Mathew";
            releaseItemDetails.UserName = inputUserName;
            string outputUserName = releaseItemDetails.UserName;
            Assert.AreEqual(inputUserName, outputUserName);
        }

        /// <summary>
        /// Tests the patient who released.
        /// </summary>
        [Test]
        public void TestReleasePatient()
        {
            ReleasedPatientDetails releasePatient = new ReleasedPatientDetails();            
            releasePatient.DOB = DateTime.Now;
            Assert.AreEqual(DateTime.Now, releasePatient.DOB);
        }

        /// <summary>
        /// Tests the patient name who released.
        /// </summary>
        [Test]
        public void TestReleasePatientName()
        {
            ReleasedItemDetails releaseHistoryItemDetails = new ReleasedItemDetails();
            releaseHistoryItemDetails.PatientName = "John";
            Assert.AreEqual("John", releaseHistoryItemDetails.PatientName);
        }

        /// <summary>
        /// Tests release patient who released.
        /// </summary>
        [Test]
        public void TestReleasePatientKey()
        {
            ReleasedItemDetails releaseHistoryItemDetails = new ReleasedItemDetails();
            ReleasedPatientDetails releasePatient = new ReleasedPatientDetails();            
            releasePatient.MRN      = "ROIM000";
            releasePatient.FacilityCode = "IE";
            releasePatient.IsHpf = true;
            releaseHistoryItemDetails.ReleasePatient = releasePatient;
            Assert.AreEqual("ROIM000.IE", releaseHistoryItemDetails.Key);
        }

        /// <summary>
        /// Tests the page count.
        /// </summary>
        [Test]
        public void TestPageCount()
        {
            int input = 50;
            releaseItemDetails.PageCount = input;
            int output = releaseItemDetails.PageCount;
            Assert.AreEqual(input, output);
        }

        #endregion
    }
}
