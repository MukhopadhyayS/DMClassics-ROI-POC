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

using NUnit.Framework;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Test.Base.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    ///  Test class for PageleveltierDetails.
    /// </summary>
    [TestFixture]
    public class TestPageLevelTierDetails
    {
        private PageLevelTierDetails pageLevelTierDetails;

        [SetUp]
        public void Initialize()
        {
            pageLevelTierDetails = new PageLevelTierDetails();
        }
        
        /// <summary>
        /// Dispose the Page level tier details.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            pageLevelTierDetails = null;
        }

        /// <summary>
        /// Test case for start page.
        /// </summary>
        [Test]
        public void TestStartPage()
        {
            int inputStartPage = 1;
            pageLevelTierDetails.StartPage = inputStartPage;
            int outputStartPage = pageLevelTierDetails.StartPage;
            Assert.AreEqual(inputStartPage, outputStartPage);
        }

        /// <summary>
        /// Test case for end page.
        /// </summary>
        [Test]
        public void TestEndPage()
        {
            int inputEndPage = 20;
            pageLevelTierDetails.EndPage = inputEndPage;
            int outputendPage = pageLevelTierDetails.EndPage;
            Assert.AreEqual(inputEndPage, outputendPage);
        }

        /// <summary>
        /// Test case for Price Per Page.
        /// </summary>
        [Test]
        public void TestPricePerPage()
        {
            double inputPricePerPage = 2.00;
            pageLevelTierDetails.PricePerPage = inputPricePerPage;
            double outputPricePerPage = pageLevelTierDetails.PricePerPage;
            Assert.AreEqual(inputPricePerPage, outputPricePerPage);
        }

        /// <summary>
        /// Test case for Billing tier id.
        /// </summary>
        [Test]
        public void TestBillingTierId()
        {
            long inputBillingTierId = 1;
            pageLevelTierDetails.BillTierId = inputBillingTierId;
            long outputBillingTierId = pageLevelTierDetails.BillTierId;
            Assert.AreEqual(inputBillingTierId, outputBillingTierId);
        }

        /// <summary>
        /// Test case for Pageleveltier id.
        /// </summary>
        [Test]
        public void TestPageLevelTierId()
        {
            long inputPageLevelTierId = 10;
            pageLevelTierDetails.Id = inputPageLevelTierId;
            long outputPageLevelTierId = pageLevelTierDetails.Id;
            Assert.AreEqual(inputPageLevelTierId, outputPageLevelTierId);
        }
    }
}
