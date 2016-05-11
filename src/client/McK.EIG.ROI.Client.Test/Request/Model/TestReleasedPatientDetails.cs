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

namespace McK.EIG.ROI.Client.Test.Request.Model
{
    /// <summary>
    /// Test class for Release Patient Details 
    /// </summary>
    [TestFixture]
    public class TestReleasedPatientDetails
    {
        #region Fields

        private ReleasedPatientDetails releasePatientDetails;

        #endregion

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            releasePatientDetails = new ReleasedPatientDetails();
        }

        [TearDown]
        public void Dispose()
        {
            releasePatientDetails = null;
        }

        #endregion

        #region Methods

       

        /// <summary>
        /// Tests the patient released item details.
        /// </summary>
        [Test]
        public void TestReleaseItems()
        {
            
            ReleasedItemDetails releaseHistoryItem1 = new ReleasedItemDetails();
            releaseHistoryItem1.ReleasedDate = System.DateTime.Now;
            releaseHistoryItem1.Encounter = "Encounter";
            releaseHistoryItem1.DocumentVersionSubtitle = "Subtitle";
            releaseHistoryItem1.Pages = "1-6";
            releasePatientDetails.ReleaseItems.Add(releaseHistoryItem1);

            ReleasedItemDetails releaseHistoryItem2 = new ReleasedItemDetails();
            releaseHistoryItem2.ReleasedDate = System.DateTime.Now;
            releaseHistoryItem2.Encounter = "Encounter";
            releaseHistoryItem2.DocumentVersionSubtitle = "Subtitle";
            releaseHistoryItem2.Pages = "1-6";
            releasePatientDetails.ReleaseItems.Add(releaseHistoryItem2);

            Assert.AreEqual("Encounter", releasePatientDetails.ReleaseItems[0].Encounter);
        }


        /// <summary>
        /// Test Case for Patient key
        /// </summary>
        [Test]
        public void TestPatientKey()
        {
            ReleasedPatientDetails releasePatientDetails = new ReleasedPatientDetails();
            releasePatientDetails.MRN = "ROIM0000";
            releasePatientDetails.FacilityCode = "IE";
            releasePatientDetails.IsHpf = true;
            Assert.AreEqual("ROIM0000.IE", releasePatientDetails.Key);
        }

        #endregion

    }
}
