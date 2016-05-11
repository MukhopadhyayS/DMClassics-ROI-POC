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
using System.Drawing;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    /// Test Class for Delivery Method DTO
    /// </summary>
    [TestFixture]
    public class TestTaxPerFacilityDetails
    {

        private TaxPerFacilityDetails taxPerFacilityDetails;

        [SetUp]
        public void Initialize()
        {
            taxPerFacilityDetails = new TaxPerFacilityDetails();
        }

        [TearDown]
        public void Dispose()
        {
            taxPerFacilityDetails = null;
        }

        /// <summary>
        /// Test case for tax rate id.
        /// </summary>
        [Test]
        public void TestTaxPerFacilityId()
        {
            long inputTaxRateId = 1;
            taxPerFacilityDetails.Id = inputTaxRateId;
            long outputTaxRateId = taxPerFacilityDetails.Id;
            Assert.AreEqual(inputTaxRateId, outputTaxRateId);
        }

        /// <summary>
        /// Test case for Facility code
        /// </summary>
        [Test]
        public void TestTaxPerFacilityCode()
        {
            string inputTaxPerFacilityCode = "AD";
            taxPerFacilityDetails.FacilityCode = inputTaxPerFacilityCode;
            string outputTaxPerFacilityCode = taxPerFacilityDetails.FacilityCode;
            Assert.AreEqual(inputTaxPerFacilityCode, outputTaxPerFacilityCode);
        }

        /// <summary>
        /// Test case for tax rate description.
        /// </summary>
        [Test]
        public void TestTaxPerFacilityDescription()
        {
            string inputTaxPerFacilityDescription = "Demo Tax Rate";
            taxPerFacilityDetails.Description = inputTaxPerFacilityDescription;
            string outputTaxPerFacilityDescription = taxPerFacilityDetails.Description;
            Assert.AreEqual(inputTaxPerFacilityDescription, outputTaxPerFacilityDescription);

        }

        /// <summary>
        /// Test case for tax rate default status
        /// </summary>
        [Test]
        public void TestTaxPerFacilityDefault()
        {
            bool inputIsDefault = true;
            taxPerFacilityDetails.IsDefault = inputIsDefault;
            bool outputIsDefault = taxPerFacilityDetails.IsDefault;
            Assert.AreEqual(inputIsDefault, outputIsDefault);
        }

        /// <summary>
        /// Test case for tax rate Image.
        /// </summary>
        [Test]
        public void TestTaxPerFacilityImage()
        {
            taxPerFacilityDetails.Image = null;
            Assert.IsNull(taxPerFacilityDetails.Image);
        }

        /// <summary>
        /// Test case for tax rate recordversion.
        /// </summary>
        [Test]
        public void TestTaxPerFacilityRecordVersion()
        {
            int recordVersionId = 0;
            taxPerFacilityDetails.RecordVersionId = recordVersionId;
            Assert.AreEqual(recordVersionId, taxPerFacilityDetails.RecordVersionId);
        }

        /// <summary>
        /// Test case for tax rate percentage
        /// </summary>
        [Test]
        public void TestTaxPerFaciliyTaxRate()
        {
            double inputTaxPercentage = 30.50;
            taxPerFacilityDetails.TaxPercentage = inputTaxPercentage;
            double outputTaxPercentage = taxPerFacilityDetails.TaxPercentage;
            Assert.AreEqual(inputTaxPercentage, outputTaxPercentage);
        }

        /// <summary>
        /// Test case for tax rate Equals.
        /// </summary>
        [Test]
        public void TestEquals()
        {
            Assert.IsTrue(taxPerFacilityDetails.Equals(taxPerFacilityDetails));
        }

    }
}
