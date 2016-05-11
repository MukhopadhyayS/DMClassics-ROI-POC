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
using System.Configuration;
using System.Collections.ObjectModel;
//NUnit
using NUnit.Framework;

//McK
using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;
using McK.EIG.ROI.Client.Web_References.BillingAdminWS;

namespace McK.EIG.ROI.Client.Test.Admin.Controller
{
    /// <summary>
    /// Test cases for FeeType Controller
    /// </summary>
    [TestFixture]
    public class TestFeeTypeController : TestBase
    {
        #region Fields

        private long feeTypeId;
        private string feeTypeName;

        private BillingAdminController billingAdminController;

        #endregion

        #region Constructor

        public TestFeeTypeController()
        {
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);
            feeTypeName = "Service Tax" + TestBiilingAdminController.GetUniqueId();
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

        #region Test Methods

        /// <summary>
        /// Creates new fee type
        /// </summary>
        [Test]
        public void Test01CreateFeeType()
        {
            FeeTypeDetails feeTypeDetails = new FeeTypeDetails();
            feeTypeDetails.Name        = feeTypeName;
            feeTypeDetails.Description = "Service Tax";
            feeTypeDetails.Amount      = 45;
            feeTypeDetails.SalesTax = "Y";
            feeTypeId = billingAdminController.CreateFeeType(feeTypeDetails);
            Assert.IsTrue(feeTypeId > 0);
        }

        /// <summary>
        /// Creating FeeType details with Existing name.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test02CreateFeeTypeWithExistingName()
        {
            FeeTypeDetails feeTypeDetails = new FeeTypeDetails();
            feeTypeDetails.Name = feeTypeName;
            feeTypeDetails.Description = "Service Tax";
            feeTypeDetails.Amount = 45;
            feeTypeDetails.SalesTax = "Y";
            feeTypeId = billingAdminController.CreateFeeType(feeTypeDetails);
        }

        /// <summary>
        /// Updates the Fee Type details for selected fee type.
        /// </summary>
        [Test]
        public void Test03UpdateFeeType()
        {
            FeeTypeDetails feeTypeDetails = new FeeTypeDetails();
            feeTypeDetails.Id = feeTypeId;
            feeTypeDetails.Name = "EX Service tax" + TestBiilingAdminController.GetUniqueId();
            feeTypeDetails.Description = "Updated Test desc";
            feeTypeDetails.Amount = 452.55;
            feeTypeDetails.SalesTax = "Y";
            FeeTypeDetails updatedFeeType = billingAdminController.UpdateFeeType(feeTypeDetails);
            Assert.AreNotEqual(feeTypeDetails.RecordVersionId, updatedFeeType.RecordVersionId);            
        }

        /// <summary>
        /// Update FeeType details with Existing name.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test04UpdateFeeTypeWithExistingName()
        {
            FeeTypeDetails feeTypeDetails = new FeeTypeDetails();
            feeTypeDetails.Id = feeTypeId;
            feeTypeDetails.Name = feeTypeName;
            feeTypeDetails.Description = "Updated Test1";
            feeTypeDetails.Amount = 45;
            feeTypeDetails.SalesTax = "Y";
            billingAdminController.UpdateFeeType(feeTypeDetails);
        }

        /// <summary>
        /// Retrieves the Fee Type details for given fee type id.
        /// </summary>
        [Test]
        public void Test05RetrieveFeeType()
        {
            FeeTypeDetails feeTypeDetails = billingAdminController.RetrieveFeeType(feeTypeId);
            Assert.IsInstanceOfType(typeof(long), feeTypeDetails.Id);
            Assert.IsInstanceOfType(typeof(string), feeTypeDetails.Name);
            Assert.IsInstanceOfType(typeof(string), feeTypeDetails.Description);
            Assert.IsInstanceOfType(typeof(double), feeTypeDetails.Amount);
            Assert.IsInstanceOfType(typeof(bool), feeTypeDetails.IsAssociated);
        }

        /// <summary>
        /// Retrieves all the Fee Type details.
        /// </summary>
        [Test]
        public void Test06RetrieveAllFeeType()
        {
            Collection<FeeTypeDetails> feeTypeDetails = null;
            feeTypeDetails = billingAdminController.RetrieveAllFeeTypes();

            foreach (FeeTypeDetails feeTypeDetail in feeTypeDetails)
            {

                Assert.IsInstanceOfType(typeof(long), feeTypeDetail.Id);
                Assert.IsInstanceOfType(typeof(string), feeTypeDetail.Name);
                Assert.IsInstanceOfType(typeof(string), feeTypeDetail.Description);
                Assert.IsInstanceOfType(typeof(double), feeTypeDetail.Amount);
                Assert.IsInstanceOfType(typeof(bool), feeTypeDetail.IsAssociated);
                Assert.IsInstanceOfType(typeof(string), feeTypeDetail.SalesTax);
                break;
            }

            Collection<FeeTypeDetails> feeTypeDetailsList = null;
            feeTypeDetailsList = billingAdminController.RetrieveAllFeeTypes(false);

            foreach (FeeTypeDetails feeTypeDetail in feeTypeDetailsList)
            {
                Assert.IsInstanceOfType(typeof(long), feeTypeDetail.Id);
                Assert.IsInstanceOfType(typeof(string), feeTypeDetail.Name);
                break;
            }
        }

        /// <summary>
        /// Deletes the Fee Type details for selected fee type.
        /// </summary>
        [Test]
        public void Test07DeleteFeeType()
        {
            billingAdminController.DeleteFeeType(feeTypeId);
        }

        /// <summary>
        /// Delete Fee Type details with non existing id.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test08DeleteFeeTypeIdWithNonExistingId()
        {
            billingAdminController.DeleteFeeType(0);
        }

        /// <summary>
        /// Retrieving Fee Type details with non existing id.
        /// </summary>
        [Test]
        [ExpectedException (typeof(ROIException))]
        public void Test09RetrieveFeeTypeIdWithNonExistingId()
        {
            FeeTypeDetails feeTypeDetails = billingAdminController.RetrieveFeeType(0);
        }

        /// <summary>
        ///  Test Create mediatype With validation
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test10CreateFeeTypeWithValidation()
        {
            FeeTypeDetails feeType = new FeeTypeDetails();
            string feeName = feeTypeName;
            while (feeName.Length <= 256 )
            {
                feeName += feeName;
            }
            feeType.Name = feeName;
            feeType.Amount = 123454;
            feeType.Description = "Service Tax";
            billingAdminController.CreateFeeType(feeType);
        }

        /// <summary>
        ///  Test update feetype With Null Argument
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test11UpdateFeeTypeWithValidation()
        {
            FeeTypeDetails feeType = new FeeTypeDetails();
            string feeName = feeTypeName;
            while (feeName.Length <= 256)
            {
                feeName += feeName;
            }
            feeType.Name = feeName;

            feeType.Description = "Service Tax";
            feeType.Amount = 123454;
            billingAdminController.UpdateFeeType(feeType);
        }

        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test12CreateFeeTypeWithEmptyName()
        {
            FeeTypeDetails feeType = new FeeTypeDetails();
            feeType.Name = string.Empty;
            billingAdminController.CreateFeeType(feeType);
        }

        /// <summary>
        ///  Test Server To Client Model mapper with null argument.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelServerToClientWithNull()
        {
            FeeType serverFeeType = null;
            FeeTypeDetails clientFeeType = BillingAdminController.MapModel(serverFeeType);
        }

        /// <summary>
        ///  Test Client To server Model mapper with null argument.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelClientToServerWithNull()
        {
            FeeTypeDetails clientFeeType = null;
            FeeType serverFeeType = BillingAdminController.MapModel(clientFeeType);
        }

        /// <summary>
        ///  Test Server To Client Model mapper with null argument for list collection.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelListServerToClientWithNull()
        {
            FeeType[] feeTypes = null;
            Collection<FeeTypeDetails> clientFeeTypeList = BillingAdminController.MapModel(feeTypes);
        }

        #endregion
    }
}
