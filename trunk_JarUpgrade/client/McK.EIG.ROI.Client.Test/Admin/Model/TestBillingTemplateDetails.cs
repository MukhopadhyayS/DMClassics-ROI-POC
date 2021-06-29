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
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Model;
using McK.EIG.ROI.Client.Admin.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    /// Test class for BillingTemplateDetails.    
    /// </summary>
    [TestFixture]
    public class TestBillingTemplateDetails
    {
        // Holds the object of model class.
        private BillingTemplateDetails billingTemplate;

        [SetUp]
        public void Initialize()
        {
            billingTemplate = new BillingTemplateDetails();
        }

        [TearDown]
        public void Dispose()
        {
            billingTemplate = null;
        }

        #region Test Methods

        /// <summary>
        /// Test case for BillingTemplate Id.
        /// </summary>
        [Test]
        public void TestBillingTemplateId()
        {
            long inputBillingTemplateId = 100;
            billingTemplate.Id = inputBillingTemplateId;
            long outputBillingTemplateId = billingTemplate.Id;
            Assert.AreEqual(inputBillingTemplateId, outputBillingTemplateId);
        }

        /// <summary>
        /// Test case for BillingTemplate Name.
        /// </summary>
        [Test]
        public void TestBillingTemplateName()
        {
            string inputBillingTemplateName = "Sample1";
            billingTemplate.Name = inputBillingTemplateName;
            string outputBillingTemplateName = billingTemplate.Name;
            Assert.AreEqual(inputBillingTemplateName, outputBillingTemplateName);
        }


        /// <summary>
        /// Test case for BillingTemplate IsAssociated.
        /// </summary>
        [Test]
        public void TestBillingTemplateIsAssociated()
        {
            bool inputStatus = true;
            billingTemplate.IsAssociated = inputStatus;
            bool outputStatus = billingTemplate.IsAssociated;
            Assert.AreEqual(inputStatus, outputStatus);
        }


        /// <summary>
        /// Test case for BillingTemplate recordversion.
        /// </summary>
        [Test]
        public void TestBillingTemplateRecordVersion()
        {
            int recordVersionId = 0;
            billingTemplate.RecordVersionId = recordVersionId;
            Assert.AreEqual(recordVersionId, billingTemplate.RecordVersionId);
        }

        /// <summary>
        /// Test case for BillingTemplate feeTypesValue.
        /// </summary>
        [Test]
        public void TestBillingTemplateFeeTypesValue()
        {
            string feeTypes = "Cash, Cheque, CreditCard, DebitCard";
            billingTemplate.FeeTypesValue = feeTypes;
            Assert.AreEqual(feeTypes, billingTemplate.FeeTypesValue);
        }
               

        /// <summary>
        /// Test case for BillingTemplate Equals.
        /// </summary>
        [Test]
        public void TestEquals()
        {
            Assert.IsTrue(billingTemplate.Equals(billingTemplate));
        }

        /// <summary>
        /// Test case for Getting hash code.
        /// </summary>
        [Test]
        public void TestGethashCode()
        {
            Assert.IsInstanceOfType(typeof(int), billingTemplate.GetHashCode());
        }

        #endregion
    }
}
