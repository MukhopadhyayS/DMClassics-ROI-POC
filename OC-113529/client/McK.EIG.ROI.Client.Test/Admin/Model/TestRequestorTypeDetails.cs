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
using System.ComponentModel;
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
    /// This class provides testing validation methods for requestortype details.   
    /// </summary>
    [TestFixture]
    public class TestRequestorTypeDetails
    {
        #region Fields

        // Holds the object of model class.
        private RequestorTypeDetails requestorTypeDetails;

        #endregion

        #region Nunit

        [SetUp]
        public void Initialize()
        {
            requestorTypeDetails = new RequestorTypeDetails();
        }

        [TearDown]
        public void Dispose()
        {
            requestorTypeDetails = null;
        }

        #endregion

        #region Test Methods

        /// <summary>
        /// Test case for Requestor Type id.
        /// </summary>
        [Test]
        public void TestRequestorTypeId()
        {
            long inputRequestorTypeId = 1;
            requestorTypeDetails.Id = inputRequestorTypeId;
            long outputRequestorTypeId = requestorTypeDetails.Id;
            Assert.AreEqual(inputRequestorTypeId, outputRequestorTypeId);
        }

        /// <summary>
        /// Test case for Requestor Type name.
        /// </summary>
        [Test]
        public void TestRequestorTypeName()
        {
            string inputRequestorTypeName = "RequestorTypeName";
            requestorTypeDetails.Name = inputRequestorTypeName;
            string outputRequestorTypeName = requestorTypeDetails.Name;
            Assert.AreEqual(inputRequestorTypeName, outputRequestorTypeName);
        }

        /// <summary>
        /// Test case for Requestor Type Image.
        /// </summary>
        [Test]
        public void TestRequestorTypeImage()
        {
            requestorTypeDetails.Image = null;
            Assert.IsInstanceOfType(typeof(Image), requestorTypeDetails.Image);
        }

        /// <summary>
        /// Test case for RecordView details.
        /// </summary>
        [Test]
        public void TestRecordViewDetails()
        {
            RecordViewDetails inputRecordView = new RecordViewDetails();
            inputRecordView.Name = "RecordViewName";
            requestorTypeDetails.RecordViewDetails = inputRecordView;            
            Assert.IsInstanceOfType(typeof(RecordViewDetails), requestorTypeDetails.RecordViewDetails);
            Assert.AreEqual(inputRecordView.Name, requestorTypeDetails.RecordViewName);
        }

        /// <summary>
        /// Test case for HPF BillingTier details.
        /// </summary>
        [Test]
        public void TestHpfBillingTier()
        {
            BillingTierDetails hpfBillingTier = new BillingTierDetails();
            hpfBillingTier.Id = 100;
            hpfBillingTier.Name = "Electronic";
            requestorTypeDetails.HpfBillingTier = hpfBillingTier;
            Assert.AreEqual(hpfBillingTier.Name, requestorTypeDetails.HpfBillingTierName); 
        }

        /// <summary>
        /// Test case for Non-HPF BillingTier details.
        /// </summary>
        [Test]
        public void TestNonHpfBillingTier()
        {
            BillingTierDetails nonHpfBillingTier = new BillingTierDetails();
            nonHpfBillingTier.Id = 100;
            nonHpfBillingTier.Name = "NON HPF";
            requestorTypeDetails.NonHpfBillingTier = nonHpfBillingTier;
            Assert.AreEqual(nonHpfBillingTier.Name, requestorTypeDetails.NonHpfBillingTierName);
        }

        /// <summary>
        /// Test case for RequestorType BillingTemplate Values.
        /// </summary>
        [Test]
        public void TestRequestorTypeBillingTemplatesValue()
        {
            string billingTemplates = "BillingTemplate1,BillingTemplate2";
            requestorTypeDetails.BillingTemplateValues = billingTemplates;
            Assert.AreEqual(billingTemplates, requestorTypeDetails.BillingTemplateValues);
        }

        /// <summary>
        /// Test case for RequestorType Equals.
        /// </summary>
        [Test]
        public void TestEquals()
        {
            Assert.IsTrue(requestorTypeDetails.Equals(requestorTypeDetails));
        }

        /// <summary>
        /// Test case for Getting hash code.
        /// </summary>
        [Test]
        public void TestGethashCode()
        {
            Assert.IsInstanceOfType(typeof(int), requestorTypeDetails.GetHashCode());
        }

        /// <summary>
        /// Test case for RequestorType recordversion.
        /// </summary>
        [Test]
        public void TestRequestorTypeRecordVersion()
        {
            int recordVersionId = 0;
            requestorTypeDetails.RecordVersionId = recordVersionId;
            Assert.AreEqual(recordVersionId, requestorTypeDetails.RecordVersionId);
        }

        /// <summary>
        /// Test case for record version id.
        /// </summary>
        [Test]
        public void TestRecordVersionId()
        {
            int recordVersionId = 0;
            requestorTypeDetails.RecordVersionId = recordVersionId;
            Assert.AreEqual(recordVersionId, requestorTypeDetails.RecordVersionId);
        }

        /// <summary>
        /// Test case for IsAssociated
        /// </summary>
        [Test]
        public void TestIsAssociated()
        {
            requestorTypeDetails.IsAssociated = true;
            Assert.IsTrue(requestorTypeDetails.IsAssociated);
        }

        private string GetUniqueId()
        {
            return DateTime.Now.TimeOfDay.TotalMilliseconds.ToString();
        }

        #endregion
    }
}
