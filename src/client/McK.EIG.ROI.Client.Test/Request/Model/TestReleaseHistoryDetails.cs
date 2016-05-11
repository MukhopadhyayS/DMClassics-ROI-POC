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
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for Release History Details 
    /// </summary>
    [TestFixture]
    public class TestReleaseHistoryDetails
    {
        #region Fields

        private ReleaseHistoryDetails releaseHistoryDetails;

        #endregion

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            releaseHistoryDetails = new ReleaseHistoryDetails();
        }

        [TearDown]
        public void Dispose()
        {
            releaseHistoryDetails = null;
        }

        #endregion

        #region Methods

        /// <summary>
        /// Gets or sets the total count of the release histories.
        /// </summary>
        [Test]
        public void TestTotalCount()
        {
            int input = 10;
            releaseHistoryDetails.TotalCount = input;
            int output = releaseHistoryDetails.TotalCount;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Tests the total page count of the release histories.
        /// </summary>
        [Test]
        public void TestTotalPageCount()
        {
            int input = 100;
            releaseHistoryDetails.TotalPageCount = input;
            int output = releaseHistoryDetails.TotalPageCount;
            Assert.AreEqual(input, output);
        }

        /// <summary>
        /// Tests the released patients.
        /// </summary>
        [Test]
        public void TestReleasePatientDetails()
        {
            ReleasedPatientDetails releasePatientDetails = new ReleasedPatientDetails();
            releasePatientDetails.FacilityCode = "facility";
            releasePatientDetails.EPN = "EPN";
            releaseHistoryDetails.ReleasedPatients.Add("1", releasePatientDetails);
            Assert.AreEqual(releasePatientDetails.FacilityCode, releaseHistoryDetails.ReleasedPatients["1"].FacilityCode);
        }

        /// <summary>
        /// Tests the usernames
        /// </summary>
        [Test]
        public void TestUserNames()
        {   
            releaseHistoryDetails.UserNames.Add("1", "John");
            SortedList output = new SortedList();
            output = releaseHistoryDetails.UserNames;
            Assert.AreEqual(output.GetKey(0).ToString(),releaseHistoryDetails.UserNames.GetKey(0).ToString());
        }

        /// <summary>
        /// Tests the release date
        /// </summary>
        [Test]
        public void TestReleaseDate()
        {               
            releaseHistoryDetails.ReleaseDate.Add("1", DateTime.Now);
            SortedList output = new SortedList();
            output = releaseHistoryDetails.ReleaseDate;
            Assert.AreEqual(output.GetKey(0).ToString(),releaseHistoryDetails.ReleaseDate.GetKey(0).ToString());
        }

        /// <summary>
        /// Tests the released item details.
        /// </summary>
        [Test]
        public void TestReleasedItemDetails()
        {
            
            ReleasedItemDetails releaseHistoryItem1 = new ReleasedItemDetails();
            releaseHistoryItem1.ReleasedDate = System.DateTime.Now;
            releaseHistoryItem1.Encounter = "Encounter";
            releaseHistoryItem1.DocumentVersionSubtitle = "Subtitle";
            releaseHistoryItem1.Pages = "1-6";
            releaseHistoryDetails.ReleaseItems.Add(releaseHistoryItem1);

            ReleasedItemDetails releaseHistoryItem2 = new ReleasedItemDetails();
            releaseHistoryItem2.ReleasedDate = System.DateTime.Now;
            releaseHistoryItem2.Encounter = "Encounter";
            releaseHistoryItem2.DocumentVersionSubtitle = "Subtitle";
            releaseHistoryItem2.Pages = "1-6";
            releaseHistoryDetails.ReleaseItems.Add(releaseHistoryItem2);

            Assert.AreEqual("Encounter", releaseHistoryDetails.ReleaseItems[0].Encounter);
        }

        /// <summary>
        /// Tests the releases
        /// </summary>
        [Test]
        public void TestReleases()
        {
            //Assert.IsNotNull(releaseHistoryDetails.Releases);
        }

        #endregion
    }
}
