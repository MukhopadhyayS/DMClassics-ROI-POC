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
using System.Configuration;
using System.Drawing;

//NUnit
using NUnit.Framework;

//McK

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Test.Base.Controller;
using McK.EIG.ROI.Client.Web_References.BillingAdminWS;
using McK.EIG.ROI.Client.Web_References.ROIAdminWS;

namespace McK.EIG.ROI.Client.Test.Admin.Controller
{
    /// <summary>
    /// Test cases for RequestorType Controller
    /// </summary>
    [TestFixture]
    [Category("TestRequestorTypeController")]
    public class TestRequestorTypeController : TestBase
    {
        #region Fields

        private long id;
        private long billingTierId;
        private string name;
        private string recordViewName;
        
        private ROIAdminController roiAdminController;
        private BillingAdminController billingAdminController;       
        private BillingTierDetails billingTierDetails;
        private RecordViewDetails recordViewDetails;
        private RequestorTypeDetails newRequestorType;

        #endregion

        #region Constructor

        public TestRequestorTypeController()
        {
            LogFactory.SetConfigFileName(ConfigurationManager.AppSettings["LogConfigFileName"]);
            name = "Attorney" + TestBiilingAdminController.GetUniqueId();  
        }

        #endregion

        #region NUnit

        [SetUp]
        public void Init()
        {   
            roiAdminController = ROIAdminController.Instance;
            billingAdminController = BillingAdminController.Instance;
        }

        [TearDown]
        public void Dispose()
        {
            roiAdminController = null;
            billingAdminController = null;
        }

        #endregion

        #region TestMethods

        /// <summary>
        ///  Creates the RequestorType
        /// </summary>
        [Test]
        public void Test01CreateRequestorType()
        {
            RequestorTypeDetails requestorTypeDetails = new RequestorTypeDetails();
            requestorTypeDetails.HpfBillingTier = new BillingTierDetails();
            requestorTypeDetails.NonHpfBillingTier = new BillingTierDetails();
            requestorTypeDetails.RecordViewDetails = new RecordViewDetails();            
            billingTierDetails = GetBillingTier();
            recordViewDetails  = GetRecordView();

            requestorTypeDetails.Name = name;
            requestorTypeDetails.HpfBillingTier.Id = billingTierDetails.Id;
            requestorTypeDetails.NonHpfBillingTier.Id = billingTierDetails.Id;
            requestorTypeDetails.RecordViewDetails.Name = "Test";
            requestorTypeDetails.SalesTax = "Y";
            PrepareAssociatedBillingTemplates(requestorTypeDetails.AssociatedBillingTemplates);

            requestorTypeDetails = roiAdminController.CreateRequestorType(requestorTypeDetails);

            newRequestorType = requestorTypeDetails;

            id                   = requestorTypeDetails.Id;
            billingTierId        = requestorTypeDetails.HpfBillingTier.Id;
            recordViewName       = requestorTypeDetails.RecordViewDetails.Name;

            Assert.IsTrue(id > 0);
        }

        /// <summary>
        /// Get the RequestorType for the given RequestorTypeId
        /// </summary>
        [Test]
        public void Test02GetRequestorType()
        {
            
            RequestorTypeDetails requestorType = roiAdminController.GetRequestorType(id);

            Assert.AreEqual(newRequestorType.Id, requestorType.Id);
            Assert.AreEqual(newRequestorType.Name, requestorType.Name);
            Assert.AreEqual(newRequestorType.RecordViewDetails.Name, requestorType.RecordViewDetails.Name);
            Assert.AreEqual(newRequestorType.HpfBillingTier.Id, requestorType.HpfBillingTier.Id);
            Assert.AreEqual(newRequestorType.NonHpfBillingTier.Id, requestorType.NonHpfBillingTier.Id);

            Collection<BillingTierDetails> billingTierDetail = new Collection<BillingTierDetails>();

            Collection<AssociatedBillingTemplate> associatedBillingTemplates = new Collection<AssociatedBillingTemplate>();
            Collection<BillingTemplateDetails> billingTemplateDetails = new Collection<BillingTemplateDetails>();

            if (billingTemplateDetails.Count > 0)
            {
                AssociatedBillingTemplate associatedBillingTemplate = new AssociatedBillingTemplate();
                associatedBillingTemplate.BillingTemplateId = billingTemplateDetails[0].Id;
                associatedBillingTemplates.Add(associatedBillingTemplate);
            }

            Assert.IsInstanceOfType(typeof(Collection<AssociatedBillingTemplate>), associatedBillingTemplates);   
        }

        /// <summary>
        ///  Update the RequestorType
        /// </summary>
        [Test]
        public void Test03UpdateRequestorType()
        {
            RequestorTypeDetails requestorType = new RequestorTypeDetails();
            requestorType.HpfBillingTier    = new BillingTierDetails();
            requestorType.NonHpfBillingTier = new BillingTierDetails();
            requestorType.RecordViewDetails = new RecordViewDetails();

            requestorType.Id = id;
            requestorType.Name = "Attorney" + TestBiilingAdminController.GetUniqueId();
            requestorType.HpfBillingTier.Id = billingTierId;
            requestorType.RecordViewDetails.Name = recordViewName;
            requestorType.NonHpfBillingTier.Id = billingTierId;
            requestorType.SalesTax = "Y";

            PrepareAssociatedBillingTemplates(requestorType.AssociatedBillingTemplates);

            RequestorTypeDetails updateRequestorType = roiAdminController.UpdateRequestorType(requestorType);
            name = updateRequestorType.Name;
            Assert.AreNotEqual(requestorType.RecordVersionId, updateRequestorType.RecordVersionId);
        }

        /// <summary>
        /// Get all the BillingTemplates
        /// </summary>
        [Test]
        public void Test04RetrieveAllRequestorTypes()
        {
            Collection<RequestorTypeDetails> requestorTypes = roiAdminController.RetrieveAllRequestorTypes();

            RequestorTypeDetails requestorType = requestorTypes[0];

            Assert.IsInstanceOfType(typeof(long), requestorType.Id);
            Assert.IsInstanceOfType(typeof(string), requestorType.Name);
            Assert.IsInstanceOfType(typeof(long), requestorType.HpfBillingTier.Id);
            Assert.IsInstanceOfType(typeof(string), requestorType.RecordViewDetails.Name);
            Assert.IsInstanceOfType(typeof(string), requestorType.SalesTax);
            
            Collection<AssociatedBillingTemplate> associatedBillingTemplates = new Collection<AssociatedBillingTemplate>();
            Collection<BillingTemplateDetails> billingTemplateDetails = billingAdminController.RetrieveAllBillingTemplates();

            if (billingTemplateDetails.Count > 0)
            {
                AssociatedBillingTemplate associatedBillingTemplate = new AssociatedBillingTemplate();
                associatedBillingTemplate.BillingTemplateId = billingTemplateDetails[0].Id;
                associatedBillingTemplates.Add(associatedBillingTemplate);
            }

            if (requestorType.AssociatedBillingTemplates != null)
            {
                Assert.IsInstanceOfType(typeof(Collection<AssociatedBillingTemplate>), associatedBillingTemplates);
            }

            requestorTypes = roiAdminController.RetrieveAllRequestorTypes(false);
            requestorType = requestorTypes[0];

            Assert.IsInstanceOfType(typeof(long), requestorType.Id);
            Assert.IsInstanceOfType(typeof(string), requestorType.Name);
        }

        /// <summary>
        /// Get Requestortype with non Existing id
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test05GetRequestorTypeWithNonExistingId()
        {
            long requestorTypeId = 0;
            RequestorTypeDetails requestorTypeDetails = roiAdminController.GetRequestorType(requestorTypeId);
        }

        /// <summary>
        ///  Test Create requestor type With Existing Name
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test06CreateRequestorTypeWithExistingName()
        {
            RequestorTypeDetails requestorType = new RequestorTypeDetails();
            requestorType.HpfBillingTier       = new BillingTierDetails();
            requestorType.RecordViewDetails    = new RecordViewDetails();
            requestorType.NonHpfBillingTier    = new BillingTierDetails();

            requestorType.Name = name;
            requestorType.HpfBillingTier.Id = billingTierId;
            requestorType.NonHpfBillingTier.Id = billingTierId;
            requestorType.RecordViewDetails.Name = recordViewName;
            requestorType.SalesTax = "Y";

            PrepareAssociatedBillingTemplates(requestorType.AssociatedBillingTemplates);

            requestorType = roiAdminController.CreateRequestorType(requestorType);
            id = requestorType.Id;
        }
     
        /// <summary>
        /// Create Requestor Type with validation
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test07CreateRequestorTypeWithValidation()
        {
            RequestorTypeDetails requestorType = new RequestorTypeDetails();
            requestorType.HpfBillingTier       = new BillingTierDetails();
            requestorType.RecordViewDetails    = new RecordViewDetails();
            requestorType.NonHpfBillingTier    = new BillingTierDetails();

            string requestorTypeName = name;
            while (requestorTypeName.Length <= 256)
            {
                requestorTypeName += requestorTypeName;
            }

            requestorType.Name = requestorTypeName;
            requestorType.HpfBillingTier.Id = billingTierId;
            requestorType.NonHpfBillingTier.Id = billingTierId;
            requestorType.RecordViewDetails.Name = recordViewName;
            requestorType.SalesTax = "Y";

            roiAdminController.CreateRequestorType(requestorType);
        }
       
        /// <summary>
        /// Update Requestor Type with empty name
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test08UpdateRequestorTypeWithEmptyName()
        {
            RequestorTypeDetails requestorType = new RequestorTypeDetails();
            requestorType.HpfBillingTier       = new BillingTierDetails();
            requestorType.RecordViewDetails    = new RecordViewDetails();
            requestorType.NonHpfBillingTier    = new BillingTierDetails();
            
            requestorType.Name = string.Empty;
            requestorType.HpfBillingTier.Id = billingTierId;
            requestorType.NonHpfBillingTier.Id = billingTierId;
            requestorType.RecordViewDetails.Name = recordViewName;
            requestorType.SalesTax = "Y";  

            roiAdminController.UpdateRequestorType(requestorType);
        }

        /// <summary>
        /// Create Requestor Type with empty name
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test09CreateRequestorTypeWithEmptyName()
        {
            RequestorTypeDetails requestorType = new RequestorTypeDetails();
            requestorType.HpfBillingTier       = new BillingTierDetails();
            requestorType.RecordViewDetails    = new RecordViewDetails();
            requestorType.NonHpfBillingTier    = new BillingTierDetails();
            
            requestorType.Name = string.Empty;
            requestorType.HpfBillingTier.Id = 0;
            requestorType.NonHpfBillingTier.Id = 0;
            requestorType.RecordViewDetails.Name = "";

            roiAdminController.CreateRequestorType(requestorType);
        }

        /// <summary>
        /// Delete requestor type with non existing id
        /// </summary>
        [ExpectedException(typeof(ROIException))]
        [Test]
        public void Test10DeleteNonExistingRequestorType()
        {
            roiAdminController.DeleteRequestorType(0);
        }

        /// <summary>
        ///  Deletes a selected RequestorType
        /// </summary>
        [Test]
        public void Test11DeleteRequestorType()
        {
            roiAdminController.DeleteRequestorType(id);
            Assert.IsTrue(true);
        }

        /// <summary>
        ///  Test Server To Client Model mapper with null argument.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelServerToClientWithNull()
        {
            McK.EIG.ROI.Client.Web_References.ROIAdminWS.RequestorType serverRequestorType = null;
            RequestorTypeDetails clientRequestorType = ROIAdminController.MapModel(serverRequestorType);
        }

        /// <summary>
        ///  Test Client To server Model mapper with null argument.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelClientToServerWithNull()
        {
            RequestorTypeDetails clientRequestorType = null;
            McK.EIG.ROI.Client.Web_References.ROIAdminWS.RequestorType serverRequestorType = ROIAdminController.MapModel(clientRequestorType);
        }


        ///// <summary>
        /////  Test Client To server Model mapper with null argument.
        ///// </summary>
        //[Test]
        //[ExpectedException(typeof(ROIException))]
        //public void TestMapModelServerToClientRecordViewListWithNull()
        //{
        //    RecordView[] serverRecordView = null;
        //    Collection<RecordViewDetails> clientRecordViewDetails = ROIAdminController.MapModelserverRecordView);
        //}
        
        /// <summary>
        ///  Test Server To Client Model mapper with null argument for list collection.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelListServerToClientWithNull()
        {
            McK.EIG.ROI.Client.Web_References.ROIAdminWS.RequestorType[] requestorTypes = null;
            Collection<RequestorTypeDetails> clientRequestorTypeList = ROIAdminController.MapModel(requestorTypes);
        }

        /// <summary>
        /// Retrieve record views.
        /// </summary>
        /// <returns></returns>
        private RecordViewDetails GetRecordView()
        {
            Collection<RecordViewDetails> recordViews = UserData.Instance.RecordViews;

            foreach (RecordViewDetails recordViewDetail in recordViews)
            {
                return recordViewDetail;
            }
            return new RecordViewDetails();
        }

        /// <summary>
        /// Retrieve billing tiers associated with electronic media type.
        /// </summary>
        /// <returns></returns>
        private BillingTierDetails GetBillingTier()
        {
            Collection<BillingTierDetails> billingTiers = billingAdminController.RetrieveAllBillingTiers();
            
            foreach (BillingTierDetails billingTier in billingTiers)
            {
                if (billingTier.MediaType.Name == "Electronic")
                {
                    return billingTier;
                }
            }
            return new BillingTierDetails();
        }

        /// <summary>
        /// Prepare associated billing templates of the RequestorType
        /// </summary>
        /// <param name="associatedBillingTemplates"></param>
        private void PrepareAssociatedBillingTemplates(Collection<AssociatedBillingTemplate> associatedBillingTemplates)
        {
            Collection<BillingTemplateDetails> billingTemplateDetails = billingAdminController.RetrieveAllBillingTemplates();
            if (billingTemplateDetails.Count == 0)
            {
                CreateBillingTemplates();
                billingTemplateDetails = billingAdminController.RetrieveAllBillingTemplates();
            }
            AssociatedBillingTemplate associatedBillingTemplate = new AssociatedBillingTemplate();
            associatedBillingTemplate.BillingTemplateId = billingTemplateDetails[0].Id;
            associatedBillingTemplate.Name              = billingTemplateDetails[0].Name;
            associatedBillingTemplate.IsDefault = false;
            associatedBillingTemplates.Add(associatedBillingTemplate);
        }

        /// <summary>
        /// Create Associated Billing Templates of the RequestorType
        /// </summary>
        public void CreateBillingTemplates()
        {
            BillingTemplateDetails billingTemplateDetails = new BillingTemplateDetails();
            long billingTemplateId;
            billingTemplateDetails.Name = "Test";
            billingTemplateDetails.IsAssociated = true;
            Collection<FeeTypeDetails> feeTypeDetails = billingAdminController.RetrieveAllFeeTypes();
            if (feeTypeDetails.Count == 0)
            {
                createFeeTypes();
                feeTypeDetails = billingAdminController.RetrieveAllFeeTypes();
            }
            AssociatedFeeType associatedFeeType = new AssociatedFeeType();
            associatedFeeType.FeeTypeId = feeTypeDetails[0].Id;           
            billingTemplateDetails.AssociatedFeeTypes.Add(associatedFeeType);

            billingTemplateId = billingAdminController.CreateBillingTemplate(billingTemplateDetails);
        }

        private void createFeeTypes()
        {
            FeeTypeDetails feeTypeDetails = new FeeTypeDetails();
            long feeTypeId;
            feeTypeDetails.Name = "Test";
            feeTypeDetails.Description = "Test desc";
            feeTypeDetails.Amount = 45;
            feeTypeDetails.IsAssociated = false;
            feeTypeId = billingAdminController.CreateFeeType(feeTypeDetails);
        }

        #endregion
    }
}
