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

namespace McK.EIG.ROI.Client.Test.Admin.Controller
{
    /// <summary>
    /// Test Cases for Billing Tier Controller.
    /// </summary>
    [TestFixture]
    public class TestBillingTierController : TestBase
    {
        #region Fields

        private const string SupplementaryMedia = "Supplementary";

        private BillingAdminController billingAdminController;
        private long id;
        private string name;
        private string desc;
        private MediaTypeDetails mediaTypeDetail;
        private long pageTierId;
        
        #endregion Fields

        #region Constructor

        public TestBillingTierController()
        {
            name = "BillingTier" + TestBiilingAdminController.GetUniqueId();
            desc = "BillingTier Supplementry";
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

        # region TestMethods
        /// <summary>
        /// Create the billingtier
        /// </summary>
        [Test]
        public void Test01CreateBillingTier()
        {
        	mediaTypeDetail = GetMediaType();
        	
            BillingTierDetails billingTierDetail = new BillingTierDetails();
			
            billingTierDetail.Name = name;
            billingTierDetail.Description = desc;
            billingTierDetail.BaseCharge = 30;
            billingTierDetail.OtherPageCharge = 40.90;
            billingTierDetail.RecordVersionId = 0;
            billingTierDetail.MediaType = mediaTypeDetail;

            PageLevelTierDetails plt = new PageLevelTierDetails ();
            plt.StartPage = 1;
            plt.EndPage = 10;
            plt.PricePerPage = 20.00;
            billingTierDetail.PageTiers.Add(plt);

            billingTierDetail = BillingAdminController.Instance.CreateBillingTier(billingTierDetail);
            id = billingTierDetail.Id;
            Assert.IsTrue(id > 0);
        }

        /// <summary>
        /// Get the billingtier for the given id
        /// </summary>
        [Test]
        public void Test02GetBillingTier()
        {
            BillingTierDetails billingTier = null;
            billingTier = billingAdminController.GetBillingTier(id);
            pageTierId = billingTier.PageTiers[0].Id;
            Assert.IsInstanceOfType(typeof(long), billingTier.Id);
            Assert.IsInstanceOfType(typeof(string), billingTier.Name);
            Assert.IsInstanceOfType(typeof(string), billingTier.Description);
            Assert.IsInstanceOfType(typeof(MediaTypeDetails), billingTier.MediaType);
            Assert.IsInstanceOfType(typeof(double), billingTier.BaseCharge);
            Assert.IsInstanceOfType(typeof(double), billingTier.OtherPageCharge);
            Assert.IsInstanceOfType(typeof(int), billingTier.RecordVersionId);
        }
        
        /// <summary>
        /// Test case for create billing tier with existing name.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test03CreateBillingTierWithExistingName()
        {
            BillingTierDetails billingTierDetail = new BillingTierDetails();

            billingTierDetail.Name = name;
            billingTierDetail.Description = desc;
            billingTierDetail.BaseCharge = 30;
            billingTierDetail.OtherPageCharge = 40.90;
            billingTierDetail.RecordVersionId = 0;
            billingTierDetail.MediaType = mediaTypeDetail;


            PageLevelTierDetails plt = new PageLevelTierDetails();
            plt.StartPage = 1;
            plt.EndPage = 10;
            plt.PricePerPage = 20.00;
            billingTierDetail.PageTiers.Add(plt);

            billingTierDetail = BillingAdminController.Instance.CreateBillingTier(billingTierDetail);
            id = billingTierDetail.Id;
        }

        /// <summary>
        /// Update billingtier
        /// </summary>
        [Test]
        public void Test04UpdateBillingTier()
        {
            BillingTierDetails billingTierDetail = new BillingTierDetails();

            billingTierDetail.Id = id;
            billingTierDetail.Name = name;
            billingTierDetail.Description = desc + "test";
            billingTierDetail.BaseCharge = 30;
            billingTierDetail.OtherPageCharge = 40.90;
            billingTierDetail.RecordVersionId = 0;
            billingTierDetail.MediaType = mediaTypeDetail;

            PageLevelTierDetails plt = new PageLevelTierDetails();
            plt.Id = pageTierId;
            plt.BillTierId = id;
            plt.StartPage = 1;
            plt.EndPage = 10;
            plt.PricePerPage = 20.00;
            plt.RecordVersionId = 0;
            billingTierDetail.PageTiers.Add(plt);


            BillingTierDetails updateBillingTier = billingAdminController.UpdateBillingTier(billingTierDetail);
            Assert.AreNotEqual(billingTierDetail.RecordVersionId, updateBillingTier.RecordVersionId);
        }


        /// <summary>
        /// Update billingtier
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test05UpdateBillingTierWithWxisitingName()
        {
            BillingTierDetails billingTierDetail = new BillingTierDetails();

            billingTierDetail.Name = "Test billingtier100";
            billingTierDetail.Description = desc + "test";
            billingTierDetail.BaseCharge = 30;
            billingTierDetail.OtherPageCharge = 40.90;
            billingTierDetail.RecordVersionId = 1;
            billingTierDetail.MediaType = mediaTypeDetail;

            PageLevelTierDetails plt = new PageLevelTierDetails();
            plt.Id = pageTierId;
            plt.StartPage = 1;
            plt.EndPage = 10;
            plt.PricePerPage = 20.00;
            billingTierDetail.PageTiers.Add(plt);

            BillingTierDetails updateBillingTier = billingAdminController.UpdateBillingTier(billingTierDetail);
        }

      
        /// <summary>
        /// Retrieves all the billingtiers
        /// </summary>
        [Test]
        public void Test06RetrieveAllBillingTiers()
        {
            Collection<BillingTierDetails> billingTierDetails = billingAdminController.RetrieveAllBillingTiers();
            if (billingTierDetails.Count > 0)
            {
                BillingTierDetails billingTier = billingTierDetails[0];
                Assert.IsInstanceOfType(typeof(long), billingTier.Id);
                Assert.IsInstanceOfType(typeof(string), billingTier.Name);
                Assert.IsInstanceOfType(typeof(string), billingTier.Description);
                Assert.IsInstanceOfType(typeof(MediaTypeDetails), billingTier.MediaType);
                Assert.IsInstanceOfType(typeof(double), billingTier.BaseCharge);
                Assert.IsInstanceOfType(typeof(double), billingTier.OtherPageCharge);
                Assert.IsInstanceOfType(typeof(int), billingTier.RecordVersionId);
            }
            /* New testcases added for performance */
            billingTierDetails = billingAdminController.RetrieveAllBillingTiers(false);
            if (billingTierDetails.Count > 0)
            {
                BillingTierDetails billingTier = billingTierDetails[0];
                Assert.IsInstanceOfType(typeof(long), billingTier.Id);
                Assert.IsInstanceOfType(typeof(string), billingTier.Name);
                Assert.IsInstanceOfType(typeof(long), billingTier.MediaType.Id);
                Assert.IsInstanceOfType(typeof(string), billingTier.MediaType.Name);
            }
        }

        /// <summary>
        /// Deletes a selected billingtier
        /// </summary>
        [Test]
        public void Test07DeleteBillingTier()
        {
            billingAdminController.DeleteBillingTier(id);
            Assert.IsTrue(true);
        }

        /// <summary>
        /// Test case for delete billing tier with non existing id
        /// </summary>
        [ExpectedException(typeof(ROIException))]
        [Test]
        public void Test08DeleteNonExistingBillingTier()
        {
            billingAdminController.DeleteBillingTier(0);
        }

        /// <summary>
        /// Test case for Get BillingTier with non existing id.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test09GetBillingTierWithNonExistingId()
        {
            long billingTierId = 0;
            BillingTierDetails billingTier = billingAdminController.GetBillingTier(billingTierId);
        }


        /// <summary>
        /// Test case for create billing tier with validation.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test10CreateBillingTierWithValidation()
        {
            BillingTierDetails billingTierDetail = new BillingTierDetails();
            string tierName = name;
            while (tierName.Length <= 256)
            {
                tierName += tierName;
            }
            billingTierDetail.Name = tierName;
            billingTierDetail.Description = desc;
            billingTierDetail.BaseCharge = 99999999;
            billingTierDetail.OtherPageCharge = 40.90;
            billingTierDetail.RecordVersionId = 0;
            billingTierDetail.MediaType = mediaTypeDetail;

            PageLevelTierDetails plt = new PageLevelTierDetails();
            plt.StartPage = 1;
            plt.EndPage = 10;
            plt.PricePerPage = 20.00;
            billingTierDetail.PageTiers.Add(plt);

            billingAdminController.CreateBillingTier(billingTierDetail);
        }

        /// <summary>
        /// Test case for update billing tier with validation.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test11UpdateBillingTierWithValidation()
        {
            BillingTierDetails billingTierDetail = new BillingTierDetails();
            string tierName = name;
            while (tierName.Length <= 256)
            {
                tierName += tierName;
            }
            billingTierDetail.Name = tierName;
            billingTierDetail.Description = desc + "test";
            billingTierDetail.BaseCharge = 99999999;
            billingTierDetail.OtherPageCharge = 40111.90;
            billingTierDetail.RecordVersionId = 1;
            mediaTypeDetail.Id = 0;
            billingTierDetail.MediaType = mediaTypeDetail;

            PageLevelTierDetails plt = new PageLevelTierDetails();
            plt.Id = pageTierId;
            plt.StartPage = 0;
            plt.EndPage = 0;
            plt.PricePerPage = 2000.5500;
            billingTierDetail.PageTiers.Add(plt);

            PageLevelTierDetails plt1 = new PageLevelTierDetails();
            plt1.Id = pageTierId;
            plt1.StartPage = 12;
            plt1.EndPage = 10;
            plt1.PricePerPage = 20222.5500;
            billingTierDetail.PageTiers.Add(plt1);

            PageLevelTierDetails plt2 = new PageLevelTierDetails();
            plt2.Id = 0;
            plt2.StartPage = 12;
            plt2.EndPage = 10;
            plt2.PricePerPage = 20222.5500;
            billingTierDetail.PageTiers.Add(plt2);

            billingAdminController.UpdateBillingTier(billingTierDetail);
        }

        /// <summary>
        /// Test case for create billing tier with empty name.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void Test12CreateBillingTierWithEmptyName()
        {
            BillingTierDetails billingTier = new BillingTierDetails();
            billingTier.Name = string.Empty;
            billingTier.MediaType = mediaTypeDetail;

            PageLevelTierDetails plt = new PageLevelTierDetails();
            plt.StartPage = 1;
            plt.EndPage = 10;
            plt.PricePerPage = 20.00;
            billingTier.PageTiers.Add(plt);

            billingAdminController.CreateBillingTier(billingTier);
        }
       
        /// <summary>
        /// Test case for update seed billingtier.
        /// </summary>
        //[Test]
        [ExpectedException(typeof(ROIException))]
        public void Test13UpdateSeedData()
        {
            Collection<BillingTierDetails> billingTierDetail = null;
            billingTierDetail = billingAdminController.RetrieveAllBillingTiers();

            foreach (BillingTierDetails billingTier in billingTierDetail)
            {
                if (billingTier.Id < 0)
                {
                    billingTier.Name = "Electronis - Seed";
                    billingAdminController.UpdateBillingTier(billingTier);
                }
            }
        }

        /// <summary>
        /// Test case for Billing Tier sorter
        /// </summary>
        [Test]
        public void Test14BillingTierSorter()
        {
            Collection<BillingTierDetails> billingTiers = new Collection<BillingTierDetails>();
            BillingTierDetails billingTier1 = new BillingTierDetails();
            billingTier1.Name = "Tier1";

            PageLevelTierDetails plt1 = new PageLevelTierDetails();
            plt1.StartPage = 1;
            plt1.EndPage = 10;
            billingTier1.PageTiers.Add(plt1);
            billingTiers.Add(billingTier1);

            BillingTierDetails billingTier2 = new BillingTierDetails();
            billingTier1.Name = "Tier2";

            PageLevelTierDetails plt2 = new PageLevelTierDetails();
            plt2.StartPage = 11;
            plt2.EndPage = 20;
            billingTier2.PageTiers.Add(plt2);
            billingTiers.Add(billingTier2);

            List<BillingTierDetails> billingTierList = new List<BillingTierDetails>(billingTiers);
            billingTierList.Sort(BillingTierDetails.Sorter);
            Assert.IsTrue(billingTierList.Count == 2);
        }

        /// <summary>
        ///  Test Server To Client Model mapper with null argument.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelServerToClientWithNull()
        {
            BillingTier serverBillingTier = null;
            BillingTierDetails clientBillingTier = BillingAdminController.MapModel(serverBillingTier);
        }

        /// <summary>
        ///  Test Client To server Model mapper with null argument.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelClientToServerWithNull()
        {
            BillingTierDetails clientBillingTier = null;
            BillingTier serverBillingTier = BillingAdminController.MapModel(clientBillingTier);
        }

        /// <summary>
        ///  Test Server To Client Model mapper with null argument for list collection.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestMapModelListServerToClientWithNull()
        {
            BillingTier[] billingTiers = null;
            Collection<BillingTierDetails> clientBillingTierList = BillingAdminController.MapModel(billingTiers);
        }


         /// <summary>
        ///  Test Server To Client Model mapper with null argument.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestPageMapModelServerToClientWithNull()
        {
            PageLevelTier serverPageLevelTier = null;
            PageLevelTierDetails clientPageLevelTierDetails = BillingAdminController.MapModel(serverPageLevelTier);
        }

        /// <summary>
        ///  Test Client To server Model mapper with null argument.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestPageMapModelClientToServerWithNull()
        {
            PageLevelTierDetails clientPageLevelTierDetails = null;
            PageLevelTier serverPageLevelTier = BillingAdminController.MapModel(clientPageLevelTierDetails);
        }

        /// <summary>
        ///  Test Server To Client Model mapper with null argument for list collection.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestPageMapModelListServerToClientWithNull()
        {
            PageLevelTier[] pageLevelTiers = null;
            BillingAdminController.MapModel(pageLevelTiers, null);
        }

        /// <summary>
        ///  Test Client To Server Model mapper with null argument for list collection.
        /// </summary>
        [Test]
        [ExpectedException(typeof(ROIException))]
        public void TestPageMapModelListClientToServerToWithNull()
        {
            Collection<PageLevelTierDetails> pageLevelTierDetails = null;
            PageLevelTier[] serverPageLevelTiers = BillingAdminController.MapModel(pageLevelTierDetails);
        }

        private MediaTypeDetails GetMediaType()
        {
            Collection<MediaTypeDetails> mediaTypes = BillingAdminController.Instance.RetrieveAllMediaTypes();
            foreach (MediaTypeDetails mediaType in mediaTypes)
            {
                if (mediaType.Name.ToLower() != SupplementaryMedia.ToLower())
                { 
                    return mediaType;
                }
            }
            return new MediaTypeDetails();
        }

        # endregion
    }
}
