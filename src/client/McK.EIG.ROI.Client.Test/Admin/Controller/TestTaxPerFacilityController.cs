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
using System.Configuration;

//NUnit
using NUnit.Framework;

//McK
using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Web_References.BillingAdminWS;
using McK.EIG.ROI.Client.Test.Base.Controller;

namespace McK.EIG.ROI.Client.Test.Admin.Controller
{
    /// <summary>
    /// Test Cases for Sales Tax Per Facility Controller.
    /// </summary>
    [TestFixture]
    public class TestTaxPerFacilityController : TestBase
    {

        #region Fields

        private BillingAdminController billingAdminController;

        private long id;
        private string facilityCode;
        private string description;
        private double taxPercentage;

        #endregion

        #region Constructor

        public TestTaxPerFacilityController()
        {
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);

            facilityCode = "AD";
            description  = "Tax rate for AD";
            taxPercentage = 12;            
        }

        #endregion

        #region NUnit

        [SetUp]
        public void Init()
        {
            billingAdminController = BillingAdminController.Instance;
        }

        [TearDown]
        public void Dispose()
        {
            billingAdminController = null;
        }

        #endregion

        #region TestMethods

        /// <summary>
        /// Test case for Creation of default tax rate for a facility.
        /// </summary>
        [Test]
        public void Test01CreationWithDefault()
        {
            TaxPerFacilityDetails taxPerFacilityDetails = new TaxPerFacilityDetails();

            taxPerFacilityDetails.FacilityCode = facilityCode;
            taxPerFacilityDetails.FacilityName = facilityCode;
            taxPerFacilityDetails.TaxPercentage = taxPercentage;
            taxPerFacilityDetails.Description = description;
            taxPerFacilityDetails.IsDefault = true;

            id = billingAdminController.CreateTaxPerFacility(taxPerFacilityDetails);
            Assert.IsTrue(id > 0);
        }


        /// <summary>
        /// Test case for creation of non-default tax rate for a facility.
        /// </summary>
        [Test]
        public void Test02CreateWithoutDefault()
        {
            TaxPerFacilityDetails taxPerFacilityDetails = new TaxPerFacilityDetails();

            taxPerFacilityDetails.FacilityCode = "Test";
            taxPerFacilityDetails.FacilityName = "Test";
            taxPerFacilityDetails.TaxPercentage = taxPercentage;
            taxPerFacilityDetails.Description = description;
            taxPerFacilityDetails.IsDefault = false;

            id = billingAdminController.CreateTaxPerFacility(taxPerFacilityDetails);            
            Assert.IsTrue(id > 0);
        }

        /// <summary>
        /// Get the tax rate for the given facility id.
        /// </summary>
        [Test]
        public void Test03RetrieveTaxRate()
        {
            TaxPerFacilityDetails taxPerFacilityDetails = null;
            taxPerFacilityDetails = billingAdminController.RetrieveTaxPerFacility(id);            

            Assert.IsInstanceOfType(typeof(long), taxPerFacilityDetails.Id);
            Assert.IsInstanceOfType(typeof(string), taxPerFacilityDetails.FacilityCode);
            Assert.IsInstanceOfType(typeof(string), taxPerFacilityDetails.Description);            
            Assert.IsInstanceOfType(typeof(bool), taxPerFacilityDetails.IsDefault);
            Assert.IsInstanceOfType(typeof(double), taxPerFacilityDetails.TaxPercentage);
            Assert.IsInstanceOfType(typeof(string), taxPerFacilityDetails.FacilityName);
        }

        /// <summary>
        /// Tese case for update tax rate for a facility
        /// </summary>
        [Test]
        public void Test04UpdateTaxRate()
        {
            TaxPerFacilityDetails taxPerFacilityDetails = new TaxPerFacilityDetails();

            taxPerFacilityDetails.Id = id;
            taxPerFacilityDetails.FacilityCode = "Test";
            taxPerFacilityDetails.FacilityName = "Test";
            taxPerFacilityDetails.Description = description + "test";
            taxPerFacilityDetails.IsDefault = false;
            taxPerFacilityDetails.TaxPercentage = 15.5;

            TaxPerFacilityDetails updateTaxRate = billingAdminController.UpdateTaxPerFacility(taxPerFacilityDetails);            
            Assert.AreNotEqual(taxPerFacilityDetails.RecordVersionId, updateTaxRate.RecordVersionId);
        }

        /// <summary>
        /// Test case for retrieve all tax rates.
        /// </summary>
        [Test]
        public void Test05RetrieveAllTaxRates()
        {
            Collection<TaxPerFacilityDetails> taxPerFacilityDetailsList = billingAdminController.RetrieveAllTaxPerFacilities();

            if (taxPerFacilityDetailsList.Count > 0)
            {
                TaxPerFacilityDetails taxPerFacilityDetails = taxPerFacilityDetailsList[0];                
                Assert.IsInstanceOfType(typeof(long), taxPerFacilityDetails.Id);
                Assert.IsInstanceOfType(typeof(string), taxPerFacilityDetails.FacilityCode);
                Assert.IsInstanceOfType(typeof(string), taxPerFacilityDetails.FacilityName);
                Assert.IsInstanceOfType(typeof(string), taxPerFacilityDetails.Description);                
                Assert.IsInstanceOfType(typeof(bool), taxPerFacilityDetails.IsDefault);
                Assert.IsInstanceOfType(typeof(double), taxPerFacilityDetails.TaxPercentage);
            }
        }

        /// <summary>
        /// Test case for deletion of selected facility.
        /// </summary>
        [Test]
        public void Test06DeleteTaxRate()
        {
            billingAdminController.DeleteTaxPerFacility(id);            
            Assert.IsTrue(true);
        }

        /// <summary>
        /// Test case for get a tax rate for a facility with non Existing id
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test08RetrieveTaxRateWithNonExistingId()
        {
            TaxPerFacilityDetails taxPerFacilityDetails = billingAdminController.RetrieveTaxPerFacility(0);            
        }

        /// <summary>
        ///  Test Create tax rate for a facility With Existing facility code
        /// </summary>
        //[Test]
        [ExpectedException(typeof(ROIException))]
        public void Test09CreqteDeliveryMethodWithExistingName()
        {
            TaxPerFacilityDetails taxPerFacilityDetails = new TaxPerFacilityDetails();
            taxPerFacilityDetails.FacilityCode = facilityCode;
            taxPerFacilityDetails.FacilityName = facilityCode;
            taxPerFacilityDetails.TaxPercentage = taxPercentage;
            taxPerFacilityDetails.Description = description;
            taxPerFacilityDetails.IsDefault = true;

            id = billingAdminController.CreateTaxPerFacility(taxPerFacilityDetails);            
        }

        /// <summary>
        /// Test case for create tax rate for a facility with validation.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test10CreateTaxRateWithValidation()
        {

            TaxPerFacilityDetails taxPerFacilityDetails = new TaxPerFacilityDetails();
            
            string facCode = "test,kkk";
            string desc = this.description;
            while (desc.Length <= 256)
            {
                desc += desc;
            }
            taxPerFacilityDetails.FacilityCode = facCode;
            taxPerFacilityDetails.FacilityName = facCode;
            taxPerFacilityDetails.Description = desc;
            taxPerFacilityDetails.TaxPercentage = 1234.33;
            billingAdminController.CreateTaxPerFacility(taxPerFacilityDetails);            
        }

        /// <summary>
        /// Test case for update tax rate with validation
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test11UpdateTaxRateWithValidation()
        {

            TaxPerFacilityDetails taxPerFacilityDetails = new TaxPerFacilityDetails();

            string facCode = "test,kkk";
            string desc = this.description;
            while (desc.Length <= 256)
            {
                desc += desc;
            }
            taxPerFacilityDetails.FacilityCode = facCode;
            taxPerFacilityDetails.FacilityName = facCode;
            taxPerFacilityDetails.Description = desc;
            taxPerFacilityDetails.TaxPercentage = 1234.33;
            billingAdminController.UpdateTaxPerFacility(taxPerFacilityDetails);            
        }

        /// <summary>
        /// Test case for create tax rate with empty facility code
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test12CreateTaxRateWithEmptyFacility()
        {
            TaxPerFacilityDetails taxPerFacilityDetails = new TaxPerFacilityDetails();
            taxPerFacilityDetails.FacilityCode = string.Empty;
            billingAdminController.CreateTaxPerFacility(taxPerFacilityDetails);            
        }

        /// <summary>
        /// Test case for delete tax rate with non existing id.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test13DeleteNonExistingTaxRate()
        {
            billingAdminController.DeleteTaxPerFacility(0);            
        }

        /// <summary>
        ///  Test Server To Client Model mapper with null argument.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelServerToClientWithNull()
        {
            TaxPerFacility serverTaxPerFacility = null;
            TaxPerFacilityDetails clientTaxPerFacilityDetails = BillingAdminController.MapModel(serverTaxPerFacility);            
        }

        /// <summary>
        ///  Test Client To server Model mapper with null argument.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelClientToServerWithNull()
        {
            TaxPerFacilityDetails clientTaxPerFacilityDetails = null;
            TaxPerFacility serverTaxPerFacility = BillingAdminController.MapModel(clientTaxPerFacilityDetails);            
        }

        /// <summary>
        ///  Test Server To Client Model mapper with null argument for list collection.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelListServerToClientWithNull()
        {
            TaxPerFacility[] serverSalesTaxFacilities = null;
            Collection<TaxPerFacilityDetails> clientTaxPerFacilityList = BillingAdminController.MapModel(serverSalesTaxFacilities);            
        }

        #endregion
    }
}
