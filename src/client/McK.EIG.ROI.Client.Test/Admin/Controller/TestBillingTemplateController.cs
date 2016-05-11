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
using McK.EIG.ROI.Client.Test.Base.Controller;
using McK.EIG.ROI.Client.Web_References.BillingAdminWS;

namespace McK.EIG.ROI.Client.Test.Admin.Controller
{
    /// <summary>
    /// Test cases for billingtemplate Controller
    /// </summary>
    [TestFixture]   
    public class TestBillingTemplateController : TestBase
    {
        #region Fields

        private BillingAdminController billingAdminController;
        private ROIAdminController roiAdminController;
        private long id;
        private string name;        

        #endregion Fields

        #region Constructor

        public TestBillingTemplateController()
        {
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);
            name = "Attorney" + TestBiilingAdminController.GetUniqueId();
        }

        #endregion

        #region NUnit

        [SetUp]
        public void Init()
        {
            billingAdminController = BillingAdminController.Instance;
            roiAdminController = ROIAdminController.Instance;
        }

        [TearDown]
        public void Dispose()
        {
            billingAdminController = null;
        }

        #endregion

        #region TestMethods

        /// <summary>
        ///  Creates the billingtemplate
        /// </summary>
        [Test]
        public void Test01CreateBillingTemplate()
        {
            BillingTemplateDetails billingTemplate = new BillingTemplateDetails();
            billingTemplate.Name = name;
            PrepareAssociatedFeeTypes(billingTemplate.AssociatedFeeTypes);
           
            id = billingAdminController.CreateBillingTemplate(billingTemplate);
            Assert.IsTrue(id > 0);            
        }

        /// <summary>
        /// Get the billingtemplate for the given billingTemplateId
        /// </summary>
        [Test]
        public void Test02GetBillingTemplate()
        {
            BillingTemplateDetails billingTemplate = null;

            billingTemplate = billingAdminController.GetBillingTemplate(id);

            Assert.IsInstanceOfType(typeof(long), billingTemplate.Id);
            Assert.IsInstanceOfType(typeof(string), billingTemplate.Name);
            Collection<AssociatedFeeType> associatedFeeTypes = new Collection<AssociatedFeeType>();
            Collection<FeeTypeDetails> feeTypeDetails = BillingAdminController.Instance.RetrieveAllFeeTypes();
            if (feeTypeDetails.Count > 0)
            {
                AssociatedFeeType associatedFeeType = new AssociatedFeeType();
                associatedFeeType.FeeTypeId = feeTypeDetails[0].Id;
                associatedFeeTypes.Add(associatedFeeType);
            }

            Assert.IsInstanceOfType(typeof(Collection<AssociatedFeeType>), associatedFeeTypes);                        
        }

        /// <summary>
        ///  Update the billingtemplate
        /// </summary>
        [Test]
        public void Test03UpdateBillingTemplate()
        {
            BillingTemplateDetails billingTemplate = new BillingTemplateDetails();
            billingTemplate.Id = id;
            billingTemplate.Name = name + "test";
            PrepareAssociatedFeeTypes(billingTemplate.AssociatedFeeTypes);
            
            BillingTemplateDetails updatedBillingTemplate =  billingAdminController.UpdateBillingTemplate(billingTemplate);
            Assert.AreNotEqual(billingTemplate.RecordVersionId, updatedBillingTemplate.RecordVersionId);
        }

        /// <summary>
        /// Get all the BillingTemplates
        /// </summary>
        [Test]
        public void Test04RetrieveAllBillingTemplates()
        {
            Collection<BillingTemplateDetails> billingTemplates = billingAdminController.RetrieveAllBillingTemplates();
            
            BillingTemplateDetails billingTemplate = billingTemplates[0];
            Assert.IsInstanceOfType(typeof(long), billingTemplate.Id);
            Assert.IsInstanceOfType(typeof(string), billingTemplate.Name);

            Collection<AssociatedFeeType> associatedFeeTypes = new Collection<AssociatedFeeType>();
            Collection<FeeTypeDetails> feeTypeDetails = BillingAdminController.Instance.RetrieveAllFeeTypes();
            if (feeTypeDetails.Count > 0)
            {
                AssociatedFeeType associatedFeeType = new AssociatedFeeType();
                associatedFeeType.FeeTypeId = feeTypeDetails[0].Id;
                associatedFeeTypes.Add(associatedFeeType);
            }

            if (billingTemplate.AssociatedFeeTypes != null)
            {
                Assert.IsInstanceOfType(typeof(Collection<AssociatedFeeType>), associatedFeeTypes);
            }

            /* New testcases added for performance */
            billingTemplates = billingAdminController.RetrieveAllBillingTemplates();

            billingTemplate = billingTemplates[0];
            Assert.IsInstanceOfType(typeof(long), billingTemplate.Id);
            Assert.IsInstanceOfType(typeof(string), billingTemplate.Name);

            Collection<BillingTemplateDetails> billingTemplatesList = billingAdminController.RetrieveAllBillingTemplates(false);
            Assert.IsInstanceOfType(typeof(long), billingTemplate.Id);
            Assert.IsInstanceOfType(typeof(string), billingTemplate.Name);
            Assert.IsInstanceOfType(typeof(long), billingTemplate.AssociatedFeeTypes[0].FeeTypeId);            
        }

        /// <summary>
        /// Test case for get billingtemplate with non Existing id
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test05GetBillingTemplateWithNonExistingId()
        {
            long billingTemplateId = 0;
            BillingTemplateDetails billingTemplate = billingAdminController.GetBillingTemplate(billingTemplateId);
        }

        /// <summary>
        ///  Test Create billingtemplate With Existing Name
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test06CreateBillingTemplateWithExistingName()
        {
            BillingTemplateDetails billingTemplate = new BillingTemplateDetails();
            billingTemplate.Name = name + "test";
            PrepareAssociatedFeeTypes(billingTemplate.AssociatedFeeTypes);

            id = billingAdminController.CreateBillingTemplate(billingTemplate);
        }

        /// <summary>
        /// Test Create billingtemplate With Null Argument
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test07CreateBillingTemplateWithValidation()
        {
            BillingTemplateDetails billingTemplate = new BillingTemplateDetails();
            string billingTemplateName = name;
            while (billingTemplateName.Length <= 256)
            {
                billingTemplateName+= billingTemplateName;
            }
            billingTemplate.Name = billingTemplateName;            
            billingAdminController.CreateBillingTemplate(billingTemplate);
        }

        /// <summary>
        ///  Test Create billingtemplate With Null Argument
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test08UpdateBillingTemplateWithValidation()
        {
            BillingTemplateDetails billingTemplate = new BillingTemplateDetails();           
            billingTemplate.Name = string.Empty;            
            billingAdminController.UpdateBillingTemplate(billingTemplate);
        }


        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test09CreateBillingTemplateWithEmptyName()
        {
            BillingTemplateDetails billingTemplate = new BillingTemplateDetails();
            billingTemplate.Name = string.Empty;
            billingAdminController.CreateBillingTemplate(billingTemplate);
        }

        /// <summary>
        /// test case for delete media type with non existing id
        /// </summary>
        [ExpectedException(typeof(ROIException))]
        [Test]
        public void Test10DeleteNonExistingBillingTemplate()
        {
            billingAdminController.DeleteBillingTemplate(0);        
        }

        /// <summary>
        ///  Deletes a selected billingtemplate
        /// </summary>
        [Test]
        public void Test11DeleteBillingTemplate()
        {
            billingAdminController.DeleteBillingTemplate(id);
            Assert.IsTrue(true);
        }

        /// <summary>
        ///  Test Case for Retrieve Billing And Payment Info
        /// </summary>
        [Test]
        public void Test12RetrieveBillingAndPaymentInfo()
        {
            Collection<RequestorTypeDetails> requestorTypes = roiAdminController.RetrieveAllRequestorTypes(false);
            RequestorTypeDetails requestorType = requestorTypes[0];
            BillingPaymentInfoDetails billingPaymentInfoDetails = billingAdminController.RetrieveBillingAndPaymentInfo(requestorType.Id);
            Assert.IsNotNull(billingPaymentInfoDetails.BillingTemplateDetails);
            Assert.IsNotNull(billingPaymentInfoDetails.FeeTypeDetails);
        }

        /// <summary>
        ///  Test Server To Client Model mapper with null argument.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelServerToClientWithNull()
        {
            BillingTemplate serverBillingTemplate = null;
            BillingTemplateDetails clientBillingTemplate = BillingAdminController.MapModel(serverBillingTemplate);
        }

        /// <summary>
        ///  Test Client To server Model mapper with null argument.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelClientToServerWithNull()
        {
            BillingTemplateDetails clientBillingTemplate = null;
            BillingTemplate serverBillingTemplate = BillingAdminController.MapModel(clientBillingTemplate);
        }

        /// <summary>
        ///  Test Server To Client Model mapper with null argument for list collection.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelListServerToClientWithNull()
        {
            BillingTemplate[] billingTemplates = null;
            Collection<BillingTemplateDetails> clientBillingTemplateList = BillingAdminController.MapModel(billingTemplates);
        }
       
        /// <summary>
        /// Create Associated FeeType
        /// </summary>
        
        public void CreateFeeTypes()
        {
            FeeTypeDetails feeTypeDetails = new FeeTypeDetails();
            long feeTypeId;
            feeTypeDetails.Name = "Test";
            feeTypeDetails.Description = "Test desc";
            feeTypeDetails.Amount = 45;
            feeTypeDetails.IsAssociated = true;
            feeTypeId = billingAdminController.CreateFeeType(feeTypeDetails);            
        }

        private void PrepareAssociatedFeeTypes(Collection<AssociatedFeeType> associatedFeeTypes)
        {            
            Collection<FeeTypeDetails> feeTypeDetails = BillingAdminController.Instance.RetrieveAllFeeTypes();
            if (feeTypeDetails.Count == 0)
            {               
                CreateFeeTypes();
                feeTypeDetails = BillingAdminController.Instance.RetrieveAllFeeTypes();
            }
            AssociatedFeeType associatedFeeType = new AssociatedFeeType();
            associatedFeeType.FeeTypeId = feeTypeDetails[0].Id;            
            associatedFeeTypes.Add(associatedFeeType);
        }

        #endregion
    }
}
