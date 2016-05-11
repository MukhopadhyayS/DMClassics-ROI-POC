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
using System.Collections.Generic;
using System.Collections.ObjectModel;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Test.Admin.Model
{
    /// <summary>
    ///  Test class for BillingPaymentInfoDetails
    /// </summary>
    [TestFixture]
    class TestBillingPaymentInfoDetails
    {

        private BillingPaymentInfoDetails billingPaymentInfoDetails;

        /// <summary>
        /// Provide a common set of functions that are performed just before each test method is called.
        /// </summary>

        [SetUp]
        public void Initialize()
        {
            billingPaymentInfoDetails = new BillingPaymentInfoDetails();
        }

        /// <summary>
        /// Dispose CodeSet Details.
        /// </summary>
        [TearDown]
        public void Dispose()
        {
            billingPaymentInfoDetails = null;
        }

        /// <summary>
        /// Test case for PaymentMethodDetails.
        /// </summary>
        [Test]
        public void TestPaymentMethodDetails()
        {
            Collection<PaymentMethodDetails> inputPaymentMethodDetails = new Collection<PaymentMethodDetails>();
            billingPaymentInfoDetails.PaymentMethodDetails = inputPaymentMethodDetails;
            Collection<PaymentMethodDetails> outputPaymentMethodDetails = billingPaymentInfoDetails.PaymentMethodDetails;
            Assert.IsNotNull(outputPaymentMethodDetails);
        }

        /// <summary>
        /// Test case for BillingTemplateDetails.
        /// </summary>
        [Test]
        public void TestBillingTemplateDetails()
        {
            Collection<BillingTemplateDetails> inputBillingTemplateDetails = new Collection<BillingTemplateDetails>();
            billingPaymentInfoDetails.BillingTemplateDetails = inputBillingTemplateDetails;
            Collection<BillingTemplateDetails> outputBillingTemplateDetail = billingPaymentInfoDetails.BillingTemplateDetails;
            Assert.IsNotNull(outputBillingTemplateDetail);
        }

        /// <summary>
        /// Test case for FeeTypeDetails.
        /// </summary>
        [Test]
        public void TestFeeTypeDetails()
        {
            Collection<FeeTypeDetails> inputFeeTypeDetails = new Collection<FeeTypeDetails>();
            billingPaymentInfoDetails.FeeTypeDetails = inputFeeTypeDetails;
            Collection<FeeTypeDetails> outputFeeTypeDetails = billingPaymentInfoDetails.FeeTypeDetails;
            Assert.IsNotNull(outputFeeTypeDetails);
        }

        /// <summary>
        /// Test case for DeliveryMethodDetails.
        /// </summary>
        [Test]
        public void TestDeliveryMethodDetails()
        {
            Collection<DeliveryMethodDetails> inputDeliveryMethodDetails = new Collection<DeliveryMethodDetails>();
            billingPaymentInfoDetails.DeliveryMethodDetails = inputDeliveryMethodDetails;
            Collection<DeliveryMethodDetails> outputDeliveryMethodDetails = billingPaymentInfoDetails.DeliveryMethodDetails;
            Assert.IsNotNull(outputDeliveryMethodDetails);
        }

        /// <summary>
        /// Test case for ReasonDetails.
        /// </summary>
        [Test]
        public void TestReasonDetails()
        {
            Collection<ReasonDetails> inputReasonDetails = new Collection<ReasonDetails>();
            billingPaymentInfoDetails.ReasonDetails = inputReasonDetails;
            Collection<ReasonDetails> outputReasonDetails = billingPaymentInfoDetails.ReasonDetails;
            Assert.IsNotNull(outputReasonDetails);
        }

        /// <summary>
        /// Test case for RequestorTypeDetails.
        /// </summary>
        [Test]
        public void TestRequestorTypeDetails()
        {
            RequestorTypeDetails inputRequestorTypeDetails = new RequestorTypeDetails();
            billingPaymentInfoDetails.RequestorTypeDetails = inputRequestorTypeDetails;
            RequestorTypeDetails outputRequestorTypeDetails = billingPaymentInfoDetails.RequestorTypeDetails;
            Assert.IsNotNull(outputRequestorTypeDetails);
        }

        /// <summary>
        /// Test case for CountryCodeDetails.
        /// </summary>
        [Test]
        public void TestCountryCodeDetails()
        {
            List<CountryCodeDetails> inputCountryCodeDetails = new List<CountryCodeDetails>();
            billingPaymentInfoDetails.CountryCodeDetails = inputCountryCodeDetails;
            List<CountryCodeDetails> outputCountryCodeDetails = billingPaymentInfoDetails.CountryCodeDetails;
            Assert.IsNotNull(outputCountryCodeDetails);
        }

        /// <summary>
        /// Test case for PageWeightDetails.
        /// </summary>
        [Test]
        public void TestPageWeightDetails()
        {
            PageWeightDetails inputPageWeightDetails = new PageWeightDetails();
            billingPaymentInfoDetails.PageWeightDetails = inputPageWeightDetails;
            PageWeightDetails outputPageWeightDetails = billingPaymentInfoDetails.PageWeightDetails;
            Assert.IsNotNull(outputPageWeightDetails);
        }

        /// <summary>
        /// Test case for PaymentMethodDetails.
        /// </summary>
        [Test]
        public void TestPaymentMethodDetailsWithEmptyValue()
        {
            Collection<PaymentMethodDetails> inputPaymentMethodDetails = null;
            billingPaymentInfoDetails.PaymentMethodDetails = inputPaymentMethodDetails;
            Collection<PaymentMethodDetails> outputPaymentMethodDetails = billingPaymentInfoDetails.PaymentMethodDetails;
            Assert.IsNotNull(outputPaymentMethodDetails);
        }

        /// <summary>
        /// Test case for BillingTemplateDetails.
        /// </summary>
        [Test]
        public void TestBillingTemplateDetailsWithEmptyValue()
        {
            Collection<BillingTemplateDetails> inputBillingTemplateDetails = null;
            billingPaymentInfoDetails.BillingTemplateDetails = inputBillingTemplateDetails;
            Collection<BillingTemplateDetails> outputBillingTemplateDetail = billingPaymentInfoDetails.BillingTemplateDetails;
            Assert.IsNotNull(outputBillingTemplateDetail);
        }

        /// <summary>
        /// Test case for FeeTypeDetails.
        /// </summary>
        [Test]
        public void TestFeeTypeDetailsWithEmptyValue()
        {
            Collection<FeeTypeDetails> inputFeeTypeDetails = null;
            billingPaymentInfoDetails.FeeTypeDetails = inputFeeTypeDetails;
            Collection<FeeTypeDetails> outputFeeTypeDetails = billingPaymentInfoDetails.FeeTypeDetails;
            Assert.IsNotNull(outputFeeTypeDetails);
        }

        /// <summary>
        /// Test case for DeliveryMethodDetails.
        /// </summary>
        [Test]
        public void TestDeliveryMethodDetailsWithEmptyValue()
        {
            Collection<DeliveryMethodDetails> inputDeliveryMethodDetails = null;
            billingPaymentInfoDetails.DeliveryMethodDetails = inputDeliveryMethodDetails;
            Collection<DeliveryMethodDetails> outputDeliveryMethodDetails = billingPaymentInfoDetails.DeliveryMethodDetails;
            Assert.IsNotNull(outputDeliveryMethodDetails);
        }

        /// <summary>
        /// Test case for ReasonDetails.
        /// </summary>
        [Test]
        public void TestReasonDetailsWithEmptyValue()
        {
            Collection<ReasonDetails> inputReasonDetails = null;
            billingPaymentInfoDetails.ReasonDetails = inputReasonDetails;
            Collection<ReasonDetails> outputReasonDetails = billingPaymentInfoDetails.ReasonDetails;
            Assert.IsNotNull(outputReasonDetails);
        }

        /// <summary>
        /// Test case for CountryCodeDetails.
        /// </summary>
        [Test]
        public void TestCountryCodeDetailsWithEmptyValue()
        {
            List<CountryCodeDetails> inputCountryCodeDetails = null;
            billingPaymentInfoDetails.CountryCodeDetails = inputCountryCodeDetails;
            List<CountryCodeDetails> outputCountryCodeDetails = billingPaymentInfoDetails.CountryCodeDetails;
            Assert.IsNotNull(outputCountryCodeDetails);
        }
    }
}
